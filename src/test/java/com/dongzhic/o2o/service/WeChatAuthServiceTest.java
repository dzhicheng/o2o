package com.dongzhic.o2o.service;

import com.dongzhic.o2o.BaseTest;
import com.dongzhic.o2o.dto.WeChatAuthExecution;
import com.dongzhic.o2o.enums.WeChatAuthStateEnum;
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
 * @date 2018/7/31 17:23
 */
public class WeChatAuthServiceTest extends BaseTest {

    Logger log = LoggerFactory.getLogger(WeChatAuthServiceTest.class);

    @Autowired
    private WeChatAuthService weChatAuthService;

    @Test
    public void testARegister () {
        String openId = "23423eredrfere4";
        WeChatAuth weChatAuth = new WeChatAuth();
        weChatAuth.setCreateTime(new Date());
        weChatAuth.setOpenId(openId);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("测试01");
        personInfo.setEmail("test@qq.com");
        personInfo.setGender("男");
        personInfo.setEnableStatus(1);
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        weChatAuth.setPersonInfo(personInfo);
        WeChatAuthExecution weChatAuthExecution = weChatAuthService.register(weChatAuth);
        Assert.assertEquals(WeChatAuthStateEnum.SUCCESS.getState(), weChatAuthExecution.getState());

        WeChatAuth weChatAuth1 = weChatAuthService.getWeChatAuthByOpenId(openId);
        log.debug("createTime: " + weChatAuth1.getCreateTime());
        log.debug("name: "+ weChatAuth1.getPersonInfo().getName());

    }


}
