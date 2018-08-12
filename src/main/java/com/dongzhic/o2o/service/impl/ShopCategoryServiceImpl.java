package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.cache.JedisUtil;
import com.dongzhic.o2o.dao.ShopCategoryDao;
import com.dongzhic.o2o.pojo.ShopCategory;
import com.dongzhic.o2o.service.ShopCategoryService;
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
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (shopCategoryCondition == null) {
            // 若查询条件为空，则列出所有首页大类，即parent为空的店铺类别
            key = key + "_allFirstLevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
                        && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            // 若parent为非空，则列出parenId下所有的子类别
            key = key + "_parent"+shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null){
            // 列出所有子类别，不管其属于那个类，都列出来
            key = key + "_allSecondLevel";
        }
        if (jedisKeys.exits(key)) {
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            String value = null;
            try {
                value = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error("getShopCategoryList:"+e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
            jedisStrings.set(key, value);
        } else {
            String value = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(value, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("getShopCategoryList:"+e.getMessage());
                throw new ArithmeticException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
