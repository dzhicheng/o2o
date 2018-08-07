package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.PersonInfoDao;
import com.dongzhic.o2o.dao.WeChatAuthDao;
import com.dongzhic.o2o.dto.WeChatAuthExecution;
import com.dongzhic.o2o.enums.WeChatAuthStateEnum;
import com.dongzhic.o2o.exception.WeChatAuthOperationException;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.pojo.WeChatAuth;
import com.dongzhic.o2o.service.WeChatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/7/31 16:09
 */
@Service
public class WeChatAuthServiceImpl implements WeChatAuthService {

    Logger log = LoggerFactory.getLogger(WeChatAuthServiceImpl.class);

    @Autowired
    private PersonInfoDao personInfoDao;
    @Autowired
    private WeChatAuthDao weChatAuthDao;

    @Override
    public WeChatAuth getWeChatAuthByOpenId(String openId) {
        return weChatAuthDao.queryWeChatInfoByOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WeChatAuthExecution register(WeChatAuth weChatAuth) throws WeChatAuthOperationException {
        // 空值判断
        if (weChatAuth == null || weChatAuth.getOpenId() == null) {
            return new WeChatAuthExecution(WeChatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            // 设置创建时间
            weChatAuth.setCreateTime(new Date());
            // 如果微信账号带着用户信息，并且用户Id为空，则代表该用户第一次使用平台，且通过微信登陆
            if (weChatAuth.getPersonInfo() != null && weChatAuth.getPersonInfo().getUserId() == null) {
                try {
                    weChatAuth.getPersonInfo().setCreateTime(new Date());
                    weChatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = weChatAuth.getPersonInfo();
                    int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                    weChatAuth.setPersonInfo(personInfo);
                    if (effectedNum <= 0) {
                        throw new WeChatAuthOperationException("添加用户失败");
                    }
                } catch (Exception e) {
                    log.error("register insert用户失败：" + e.getMessage());
                    throw new WeChatAuthOperationException("register insert用户失败：" + e.getMessage());
                }
            }
            // 创建专属该平台的微信账号
            int effectedNum = weChatAuthDao.insertWeCharAuth(weChatAuth);
            if (effectedNum <= 0) {
                throw new WeChatAuthOperationException("账号创建失败");
            } else {
                return new WeChatAuthExecution(WeChatAuthStateEnum.SUCCESS, weChatAuth);
            }
        } catch (Exception e) {
            log.error("WeChatAuth register error, " + e.getMessage());
            throw new WeChatAuthOperationException("WeChatAuth register error, " + e.getMessage());
        }
    }
}
