package com.dongzhic.o2o.service;

import com.dongzhic.o2o.pojo.ShopCategory;

import java.util.List;

/**
 * @author dongzc
 */
public interface ShopCategoryService {
    /**
     * 获取商铺种类列表
     * @param shopCategory
     * @return
     */
    List<ShopCategory> getShopCategoryList (ShopCategory shopCategory);
}
