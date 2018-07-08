package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongzc
 */
public interface ShopCategoryDao {
    /**
     * 查询商铺种类
     * @param shopCategory 商铺
     * @return shopCategoryList
     */
    List<ShopCategory> queryShopCategory (@Param("shopCategoryCondition") ShopCategory shopCategory);
}
