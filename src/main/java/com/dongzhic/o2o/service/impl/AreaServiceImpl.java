package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.dao.AreaDao;
import com.dongzhic.o2o.pojo.Area;
import com.dongzhic.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongzc
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
