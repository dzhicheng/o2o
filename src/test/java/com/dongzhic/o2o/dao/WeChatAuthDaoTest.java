package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.pojo.WeChatAuth;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/7/31 11:54
 */
public class WeChatAuthDaoTest extends BaseTest {

    Logger log = LoggerFactory.getLogger(WeChatAuthDaoTest.class);

    @Autowired
    private WeChatAuthDao weChatAuthDao;

    @Test
    public void testInsertWeCharAuth () {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        WeChatAuth weChatAuth = new WeChatAuth();
        weChatAuth.setPersonInfo(personInfo);
        weChatAuth.setOpenId("123123ssdfdsfdsf");
        weChatAuth.setCreateTime(new Date());
        int effected = weChatAuthDao.insertWeCharAuth(weChatAuth);
        Assert.assertEquals(1, effected);
    }

    @Test
    public void testQueryWeChatInfoByOpenId () {
        WeChatAuth weChatAuth = weChatAuthDao.queryWeChatInfoByOpenId("123123ssdfdsfdsf");
        log.debug("userId: " + weChatAuth.getPersonInfo().getUserId());
        log.debug("name: " + weChatAuth.getPersonInfo().getName());
        log.debug("createTime: " + weChatAuth.getCreateTime());
    }
}
