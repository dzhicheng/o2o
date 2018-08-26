package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.ShopDao;
import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ShopExecution;
import com.dongzhic.o2o.enums.ShopSateEnum;
import com.dongzhic.o2o.exception.ShopOperationException;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.service.ShopService;
import com.dongzhic.o2o.util.ImageUtil;
import com.dongzhic.o2o.util.PageCalculator;
import com.dongzhic.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author dongzc
 **/
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail ) {
        // 1.空值判断
        if (shop == null) {
            return new ShopExecution(ShopSateEnum.NULL_SHOP);
        }
        try {
            // 2.给店铺信息付初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            // 3.添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    try {
                        // 4.存储图片
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    // 5.跟新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("跟新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopSateEnum.CHECK, shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
       try {
           if (shop == null || shop.getShopId() == null) {
               return new ShopExecution(ShopSateEnum.NULL_SHOP);
           } else {
               //1、判断是否处理店铺图片
               if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                   Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                   if (tempShop.getShopImg() != null ) {
                       ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                   }
                   addShopImg(shop, thumbnail);
               }
               //2、跟新店铺信息
               shop.setLastEditTime(new Date());
               int effectedNum = shopDao.updateShop(shop);
               if (effectedNum <= 0) {
                   return new ShopExecution(ShopSateEnum.INNER_ERROR);
               } else {
                   shop = shopDao.queryByShopId(shop.getShopId());
                   return new ShopExecution(ShopSateEnum.SUCCESS, shop);
               }
           }
       } catch (Exception e) {
           throw new ShopOperationException("modifyShop error:"+e.getMessage());
       }
    }

    /**
     * 添加图片路径
     * @param shop 商铺
     * @param thumbnail 图片
     */
    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        // 获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        // 处理缩略图，并返回新生成图片的相对路径
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        // 根据页数和每页条数获取查询对应的行数
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        // 根据条件、开始行数，获取行数查询店铺
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        // 相同条件获取店铺总数
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        } else {
            shopExecution.setState(ShopSateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

}
