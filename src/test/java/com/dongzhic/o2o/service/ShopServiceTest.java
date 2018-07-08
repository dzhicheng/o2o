package com.dongzhic.o2o.service;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ShopExecution;
import com.dongzhic.o2o.enums.ShopSateEnum;
import com.dongzhic.o2o.pojo.Area;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.pojo.ShopCategory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author dongzc
 **/
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList () {
        Shop shopConditon = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2L);
        shopConditon.setShopCategory(sc);
        ShopExecution shopExecution = shopService.getShopList(shopConditon, 1, 3);
        System.out.println("店铺列别数为：" + shopExecution.getShopList().size());
        System.out.println("店铺总数为：" + shopExecution.getCount());
    }

    @Test
    @Ignore
    public void testModifyShop () throws Exception {
        Shop shop = new Shop();
        shop.setShopId(21L);
        shop.setShopName("修改后的店铺名称");
        File file = new File("C:\\Users\\zhicheng\\Desktop\\1\\test.jpg");
        ImageHolder imageHolder = new ImageHolder("test.jpg", new FileInputStream(file));
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("新的图片地址为：" + shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop () throws FileNotFoundException {
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();
        Shop shop = new Shop();

        area.setAreaId(2);
        owner.setUserId(1L);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setShopName("测试店铺2");
        shop.setShopDesc("test2");
        shop.setShopImg("test2");
        shop.setPhone("test2");
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");
        File shopImg = new File("/home/zhicheng/img/test.jpg");
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), new FileInputStream(shopImg));
        ShopExecution se = shopService.addShop(shop, imageHolder);
        Assert.assertEquals(ShopSateEnum.CHECK.getState(), se.getState());

    }
}
