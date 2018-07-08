package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.Product;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author dongzc
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct () {
        //定义商铺
        Shop shop = new Shop();
        shop.setShopId(1L);
        //定义商品种类
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        //定义商品
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setEnableStatus(1);
        product1.setPriority(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop);
        product1.setProductCategory(productCategory);

        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试Desc2");
        product2.setImgAddr("test2");
        product2.setEnableStatus(1);
        product2.setPriority(2);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop);
        product2.setProductCategory(productCategory);

        Product product3 = new Product();
        product3.setProductName("测试3");
        product3.setProductDesc("测试Desc3");
        product3.setImgAddr("test3");
        product3.setEnableStatus(1);
        product3.setPriority(3);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop);
        product3.setProductCategory(productCategory);

        //判断是否添加成功
        int effectedNum = productDao.insertProduct(product1);
        Assert.assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        Assert.assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        Assert.assertEquals(1, effectedNum);

    }

    @Test
    public void testBQueryProductList () {
        Product productCondition = new Product();
        //分页查询
        List<Product> products = productDao.queryProductList(productCondition, 0, 4);
        Assert.assertEquals(4, products.size());
        //查询数量
        int count = productDao.queryProductCount(productCondition);
        Assert.assertEquals(17, count);
        //根据商品名称模糊匹配查询
        productCondition.setProductName("测试");
        products = productDao.queryProductList(productCondition, 0, 3);
        Assert.assertEquals(3, products.size());
        count = productDao.queryProductCount(productCondition);
        Assert.assertEquals(15, count);
    }

    @Test
    public void testQueryProductByProductId () {
        Product product = productDao.getProductById(8);
        Assert.assertEquals("红豆咖啡", product.getProductName());
    }

    @Test
    public void testUpdateProductByProductId () {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        Shop shop = new Shop();
        shop.setShopId(11L);

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("抹茶咖啡");
        product.setProductDesc("浓浓抹茶，很好喝");
        product.setNormalPrice("14");
        product.setPromotionPrice("12");
        product.setPriority(2);
        product.setLastEditTime(new Date());
        product.setProductCategory(productCategory);
        product.setShop(shop);

        int effectNum = productDao.updateProduct(product);
        System.out.println(effectNum);
    }

    @Test
    public void testCUpdateProductCategoryToNull () {
        int effectNum = productDao.updateProductCategoryToNull(3L);
        Assert.assertEquals(3, effectNum);
    }

}
