package com.dongzhic.o2o.service;

/**
 * @author dongzc
 * @date 2018/8/11 15:34
 */
public interface CacheService {

    /**
     * 依据key前缀删除匹配该模式下的所有key-value，如传入：shopCategory,则删除"shopCategory_allFirstLevel"等
     * 以shopCategory打开的key-value都被清空
     * @param keyPrefix
     */
    void removeFromCache (String keyPrefix);

}
