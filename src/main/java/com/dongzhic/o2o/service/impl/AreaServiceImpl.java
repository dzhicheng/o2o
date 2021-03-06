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
        // 定义redis的key
        String key = AREALISTKEY;
        // 定义接收对象
        List<Area> areaList = null;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        // 定义jackson数据转换操作类
        if (!jedisKeys.exits(key)) {
            // 若不存在，则从数据库里面取出相应数据
            areaList = areaDao.queryArea();
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
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
            // 若存在，则直接从redis里面取出相应数据
            String value = jedisStrings.get(key);
            // 指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
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
