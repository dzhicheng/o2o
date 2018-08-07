package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.PersonInfo;

/**
 * 用户类Dao
 * @author dongzc
 * @date 2018/7/31 11:23
 */
public interface PersonInfoDao {

    /**
     * 通过用户Id查询信息
     * @param userId
     * @return
     */
    PersonInfo queryPersonInfoById (long userId);

    /**
     * 添加用户信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo (PersonInfo personInfo);
}
