package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;
import com.dongzhic.o2o.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongzc
 **/
public interface ShopDao {

    /**
     * 新增商铺
     * @param shop 商铺
     * @return 0：插入成功,-1：插入失败
     */
    int insertShop (Shop shop);

    /**
     * 跟新店铺
     * @param shop 商铺
     * @return 跟新数量
     */
    int updateShop (Shop shop);

    /**
     * 通过shopId获取商铺
     * @param shopId 商铺Id
     * @return 商铺Id
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可查询的店铺有：店铺名(模糊)，店铺状态，店铺类别，区域Id和owner
     * @param shopCondition 查询条件
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return 店铺List
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition, @Param("rowIndex")int rowIndex,
                             @Param("pageSize")int pageSize);

    /**
     * 查询queryShopList的总数
     * @param shopCondition 查询条件
     * @return queryShopList的总数
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);

}
