package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.cache.JedisUtil;
import com.dongzhic.o2o.dao.HeadLineDao;
import com.dongzhic.o2o.pojo.HeadLine;
import com.dongzhic.o2o.service.HeadLineService;
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
 * @date 2018/7/9 12:36
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition){
        List<HeadLine> headLineList = null;
        String key = HLLISTKEY;
        ObjectMapper mapper = new ObjectMapper();
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_"+headLineCondition.getEnableStatus();
        }
        // 判断key是否存在
        if (!jedisKeys.exits(key)) {
            // 不存在，从数据库里取数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            // 将相关的实体类集合转换成string，存入redis里面对应的key中
            String value =  null;
            try {
                value = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("getHeadLineList:"+e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
            jedisStrings.set(key, value);
        } else {
            // 若存在，直接从redis里面取出相应的数据
            String value = jedisStrings.get(key);
            // 将指定的string类型转换成集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                // 将相关的key对应的value里的string转换成对象的实体类集合
                headLineList = mapper.readValue(value, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("getHeadLineList:"+e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
        }

        return headLineList;
    }
}
