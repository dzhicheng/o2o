package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.pojo.PersonInfo;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/7/31 11:53
 */
public class PersonInfoDaoTest extends BaseTest {

    Logger log = LoggerFactory.getLogger(PersonInfoDaoTest.class);

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testQueryPersonInfoById () {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(2);
        log.debug("name: " + personInfo.getName());
        log.debug("email: " + personInfo.getEmail());
    }

    @Test
    public void testInsertPersonInfo () {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("Tomcat");
        personInfo.setEmail("113@qq.com");
        personInfo.setGender("男");
        personInfo.setEnableStatus(1);
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        int effected = personInfoDao.insertPersonInfo(personInfo);
        Assert.assertEquals(1, effected);
    }
}
