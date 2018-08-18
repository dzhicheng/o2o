package com.dongzhic.o2o.service;

import com.dongzhic.o2o.dto.LocalAuthExecution;
import com.dongzhic.o2o.exception.LocalAuthOperationException;
import com.dongzhic.o2o.pojo.LocalAuth;

/**
 * @author dongzc
 * @date 2018/8/15 12:50
 */
public interface LocalAuthService {

    /**
     * 通过账号和密码获取平台账号信息
     * @param userName
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUserNameAmdPwd (String userName, String password);

    /**
     * 通过用户Id获取平台账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId (long userId);

    /**
     * 绑定微信账号，生成平台专属账号
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution bindLocalAuth (LocalAuth localAuth) throws LocalAuthOperationException;

    /**
     * 修改平台登陆的密码
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution modifyLocalAuth (long userId, String userName, String password,
                          String newPassword) throws LocalAuthOperationException;
}
