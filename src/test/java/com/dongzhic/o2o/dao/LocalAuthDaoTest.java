package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.LocalAuth;
import com.dongzhic.o2o.pojo.PersonInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/8/13 14:27
 */
public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    LocalAuthDao localAuthDao;

    private static final String userName = "zhicheng";
    private static final String password = "123456";

    @Test
    public void testInsertLocalAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(13L);
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("java");
        localAuth.setPassword("123");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        localAuth.setPersonInfo(personInfo);
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateLocalAuth () {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        String newPassword = "123";
        Date lastEditTime = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(personInfo.getUserId(), userName, password, newPassword, lastEditTime);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryLocalByUserNameAmdPwd () {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAmdPwd(userName, password);
        System.out.println(localAuth.getLocalAuthId());
        System.out.println(localAuth.getPersonInfo().getEmail());
    }

    @Test
    public void testQueryLocalByUserId () {
        long userId = 1L;
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(userId);
        System.out.println(localAuth.getLocalAuthId());
        System.out.println(localAuth.getPersonInfo().getEmail());
    }
}
