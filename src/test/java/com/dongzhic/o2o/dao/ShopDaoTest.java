package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author dongzc
 **/
public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testQueryShopCount () {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
//        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
//        Assert.assertEquals(5, shopList.size());

        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 2);
        System.out.println("店铺列表的大小:"+shopList.size());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺总数:"+count);

    }

    @Test
    @Ignore
    public void testQueryByShopId ()  {
        Shop shop = shopDao.queryByShopId(11L);
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testInsertShop () {
        Shop shop = new Shop();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        PersonInfo personInfo = new PersonInfo();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        personInfo.setUserId(1L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(personInfo);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述1");
        shop.setShopAddr("测试地址1");
        shop.setShopImg("upload/item/shop8/2018013111511587653.jpg");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testShopListAndCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(3L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shops = shopDao.queryShopList(shopCondition, 0, 6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表大小："+shops.size());
        System.out.println("店铺总数："+count);

    }
}
