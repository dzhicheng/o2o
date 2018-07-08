package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.ProductCategory;
import org.apache.commons.collections.list.PredicatedList;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dongzc
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testABatchInsertProductCategory () {
        ProductCategory p1 = new ProductCategory();
        p1.setProductCategoryName("店铺商品类别4");
        p1.setPriority(4);
        p1.setCreateTime(new Date());
        p1.setShopId(1L);
        ProductCategory p2 = new ProductCategory();
        p2.setProductCategoryName("店铺商品类别5");
        p2.setPriority(6);
        p2.setCreateTime(new Date());
        p2.setShopId(1L);
        List<ProductCategory> productCategoryList = new LinkedList<ProductCategory>();
        productCategoryList.add(p1);
        productCategoryList.add(p2);
        int result = productCategoryDao.batchInsertProductCategory(productCategoryList);
        Assert.assertEquals(2, result);

    }

    @Test
    public void testBQueryProductCategory () {
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.
                queryProductCategoryList(shopId);
        System.out.println("当前店铺包含："+productCategoryList.size()+" 个商品");
    }

    @Test
    public void testCDeleteProductCategory () {
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.
                queryProductCategoryList(shopId);
        int effectedNum = 0;
        for (ProductCategory pc : productCategoryList) {
            if ("店铺商品类别1".equals(pc.getProductCategoryName()) ||
                    "店铺商品类别4".equals(pc.getProductCategoryName())) {
                effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                Assert.assertEquals(1 , effectedNum);
            }
        }

    }
}
