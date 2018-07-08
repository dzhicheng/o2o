package com.dongzhic.o2o.service;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ProductExecution;
import com.dongzhic.o2o.enums.ProductStateEnum;
import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dongzc
 */
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAAddProduct ()  throws Exception{
        //1.设置默认商铺，商品种类
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        //2.商品赋值
        Product product = new Product();
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setCreateTime(new Date());
        product.setPriority(20);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //3.创建缩略图文件流
        File thumbnailFile = new File("E:/o2o/image/watermark.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        //4.创建两个详细图文件流并将他们添加到详细图列表中
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        File productImg1 = new File("E:/o2o/image/1.jpg");
        File productImg2 = new File("E:/o2o/image/2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        InputStream is2 = new FileInputStream(productImg2);
        imageHolderList.add(new ImageHolder(productImg1.getName(), is1));
        imageHolderList.add(new ImageHolder(productImg2.getName(), is2));
        //5.添加商品并验证
        ProductExecution pe = productService.addProduct(product, thumbnail, imageHolderList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

    @Test
    public void testCModifyProduct () {
        Shop shop = new Shop();
        shop.setShopId(1L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);

        Product product = new Product();
        product.setProductId(2L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("原味奶茶");
        product.setProductDesc("台湾原味！");

        File thumbnailFile = new File("E:/o2o/image/dabai.jpg");
        File productImg1 = new File("E:/o2o/image/girl.jpg");
        File productImg2 = new File("E:/o2o/image/xiaohuangren.jpg");
        try {
            //创建缩略图文件流
            InputStream is = new FileInputStream(thumbnailFile);
            ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
            //创建两个详情图文件流并将他们添加到详情图列表
            InputStream is1 = new FileInputStream(productImg1);
            InputStream is2 = new FileInputStream(productImg2);

            List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
            productImgList.add(new ImageHolder(productImg1.getName(), is1));
            productImgList.add(new ImageHolder(productImg2.getName(), is2));

            //添加商品并验证
            ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
            Assert.assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
