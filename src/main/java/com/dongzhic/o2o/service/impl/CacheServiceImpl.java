package com.dongzhic.o2o.service.impl;

import com.dongzhic.o2o.cache.JedisUtil;
import com.dongzhic.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author dongzc
 * @date 2018/8/11 15:41
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keySet = jedisKeys.keys(keyPrefix+"*");
        for (String key : keySet) {
            jedisKeys.del(key);
        }
    }
}