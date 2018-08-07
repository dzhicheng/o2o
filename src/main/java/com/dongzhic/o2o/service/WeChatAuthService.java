package com.dongzhic.o2o.service;

import com.dongzhic.o2o.dto.WeChatAuthExecution;
import com.dongzhic.o2o.exception.WeChatAuthOperationException;
import com.dongzhic.o2o.pojo.WeChatAuth;

/**
 * @author dongzc
 * @date 2018/7/31 15:45
 */
public interface WeChatAuthService {

    /**
     * 通过openId查找平台对应的微信账号
     * @param openId
     * @return
     */
    WeChatAuth getWeChatAuthByOpenId (String openId);

    /**
     * 注册本平台的微信账号
     * @param weChatAuth
     * @return
     * @throws WeChatAuthOperationException
     */
    WeChatAuthExecution register (WeChatAuth weChatAuth) throws WeChatAuthOperationException;
}
