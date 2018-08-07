package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.WeChatAuth;

/**
 * 微信账号Dao
 * @author dongzc
 * @date 2018/7/31 11:24
 */
public interface WeChatAuthDao {

    /**
     * 通过openId查询对应平台的微信账户
     * @param openId
     * @return
     */
    WeChatAuth queryWeChatInfoByOpenId (String openId);

    /**
     *  添加对应平台的微信账号
     * @param weChatAuth
     * @return
     */
    int insertWeCharAuth (WeChatAuth weChatAuth);
}
