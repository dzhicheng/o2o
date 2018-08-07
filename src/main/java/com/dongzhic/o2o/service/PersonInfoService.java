package com.dongzhic.o2o.service;

import com.dongzhic.o2o.pojo.PersonInfo;

/**
 * @author dongzc
 * @date 2018/7/31 17:52
 */
public interface PersonInfoService {

    /**
     * 根据用户Id获取personInfo信息
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById (Long userId);
}
