package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.PersonInfoDao;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongzc
 * @date 2018/7/31 17:53
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}
