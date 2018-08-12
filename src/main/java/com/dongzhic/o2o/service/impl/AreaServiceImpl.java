package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.cache.JedisUtil;
import com.dongzhic.o2o.dao.AreaDao;
import com.dongzhic.o2o.pojo.Area;
import com.dongzhic.o2o.service.AreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongzc
 */
@Service
public class AreaServiceImpl implements AreaService {

    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public List<Area> getAreaList() {
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exits(key)) {
            areaList = areaDao.queryArea();
            String value = null;
            try {
                value = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
            jedisStrings.set(key, value);
        } else {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            String value = jedisStrings.get(key);
            try {
                areaList = mapper.readValue(value, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
        }
        return areaList;
    }
}
