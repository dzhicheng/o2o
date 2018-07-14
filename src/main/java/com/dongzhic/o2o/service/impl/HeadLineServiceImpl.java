package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.HeadLineDao;
import com.dongzhic.o2o.pojo.HeadLine;
import com.dongzhic.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author dongzc
 * @date 2018/7/9 12:36
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
