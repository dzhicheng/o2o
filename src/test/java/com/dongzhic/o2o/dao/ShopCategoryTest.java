package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dongzc
 */
public class ShopCategoryTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory () {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        ShopCategory childShopCategory = new ShopCategory();
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(1L);
        childShopCategory.setParent(parentShopCategory);
        shopCategoryList = shopCategoryDao.queryShopCategory(childShopCategory);
        Assert.assertEquals(1 , shopCategoryList.size());
        System.out.println(shopCategoryList.get(0).getShopCategoryName());

    }

}
