package com.dongzhic.o2o.service;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.dto.LocalAuthExecution;
import com.dongzhic.o2o.enums.LocalAuthStateEnum;
import com.dongzhic.o2o.pojo.LocalAuth;
import com.dongzhic.o2o.pojo.PersonInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/8/16 10:56
 */
public class LocalAuthServiceTest extends BaseTest {

    private static String userName = "tomcat";
    private static String password = "12345";


    @Autowired
    private LocalAuthService localAuthService;

    @Test
    public void testBindLocalAuth () {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8L);
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("tomcat");
        localAuth.setPassword("123");
        localAuth.setPersonInfo(personInfo);
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        LocalAuthExecution execution = localAuthService.bindLocalAuth(localAuth);
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), execution.getState());
    }

    @Test
    public void testModifyLocalAuth () {
        long userId = 8L;
        String newPassword = "12345";
        LocalAuthExecution execution = localAuthService.modifyLocalAuth(userId, userName, password, newPassword);
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), execution.getState());
    }

    @Test
    public void testGetLocalAuthByUserNameAmdPwd () {
        LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAmdPwd(userName, password);
        System.out.println(localAuth.getUserName());
    }

    @Test
    public void testGetLocalAuthByUserId () {
        long userId = 8L;
        LocalAuth localAuth = localAuthService.getLocalAuthByUserId(userId);
        System.out.println(localAuth.getUserName());
        System.out.println(localAuth.getPassword());

    }

}
