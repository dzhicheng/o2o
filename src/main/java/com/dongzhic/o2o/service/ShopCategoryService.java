package com.dongzhic.o2o.service;

import com.dongzhic.o2o.pojo.ShopCategory;

import java.util.List;

/**
 * @author dongzc
 */
public interface ShopCategoryService {

    String SCLISTKEY = "shopCategoryList";

    /**
     * 根据查询条件，获取ShopCategory列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList (ShopCategory shopCategoryCondition);
}
