package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.ProductDao;
import com.dongzhic.o2o.dao.ProductImgDao;
import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ProductExecution;
import com.dongzhic.o2o.enums.ProductStateEnum;
import com.dongzhic.o2o.exception.ProductOperationException;
import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.pojo.ProductImg;
import com.dongzhic.o2o.service.ProductService;
import com.dongzhic.o2o.util.ImageUtil;
import com.dongzhic.o2o.util.PageCalculator;
import com.dongzhic.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dongzc
 * @date 2018.04.26
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 1.处理缩略图，获取缩略图的相对路径，并赋值给product
     * 2.往tb_product写入商品信息，获取productId
     * 3.结合productId批量处理商品详细图
     * 4.将商品详细图列表批量插入tb_product_img
     * @param product 商品
     * @param thumbnail 图片
     * @param productImgList 图片List
     * @return 商品处理dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                       List<ImageHolder> productImgList) {
        if (product != null && product.getShop() != null
                && product.getShop().getShopId() != null) {
            //给商品设置默认属性，设置默认上架状态
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认上架状态
            product.setEnableStatus(1);
            //若商品缩略图不为空，则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0 ) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败"+e.toString());
            }
            //若商品详细图不为空，则添加
            if (productImgList != null &&  productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            //传参为空，则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转换成数据库的形码，并调用dao获取指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //基于同样的查询条件，查询当前条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setCount(count);
        pe.setProductList(productList);
        return pe;
    }


    @Override
    public Product getProductById(long productId) {
        return productDao.getProductById(productId);
    }

    /**
     * 1.若缩略图参数有值，则处理缩略图
     *      若原先存在缩略图则先删除在添加新图，之后获取缩略图相对路径并赋值给product
     * 2.若商品详情图参数有值，对商品详细图列表进行同样操作
     * 3.将tb_product_img以及tb_product下面的该商品原先的商品详情图记录全部清除
     * 4.跟新tb_product信息
     * @param product 商品
     * @param thumbnail 图片流
     * @param productImgList 图片List
     * @return
     * @throws ProductOperationException
     */
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null
                && product.getShop().getShopId() != null) {
            //设置商品默认属性
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                //1.删除原来缩略图文件
                Product tempProduct = productDao.getProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                //2.将保存新的图片
                addThumbnail(product, thumbnail);
            }
            //如果有新存入的商品详情图，则将原先的删除，并添加新的图片
            if (productImgList != null && productImgList.size()>0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgList);
            }
            try {
                int effectNum = productDao.updateProduct(product);
                if (effectNum < 0) {
                    throw new ProductOperationException("跟新商品失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw  new ProductOperationException("跟新商品信息失败"+e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除指定商品的详情图片
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片
        List<ProductImg> productImgs = productImgDao.queryProductImgList(productId);
        //删除原来的图片
        for (ProductImg productImg : productImgs) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里图片的信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加商品缩略图片
     * @param product 商品
     * @param thumbnail 图片
     */
    private void addThumbnail (Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 添加商品详情图片
     * @param product 商品
     * @param imageHolderList 图片List
     */
    private void addProductImgList (Product product, List<ImageHolder> imageHolderList ) {
        //获取图片的存储路径，直接存放在相应路径的文件夹下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次处理，并添加到ProductImg中
        for (ImageHolder productImageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);
        }

        //如果有图片需要添加，就执行批量添加操作
        if (imageHolderList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详细图失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详细图失败"+e.toString());
            }
        }

    }
}
