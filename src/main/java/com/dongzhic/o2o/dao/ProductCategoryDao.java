package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongzc
 */
public interface ProductCategoryDao {

    /**
     * 通过shopId查询店铺商品类别
     * @param shopId 店铺Id
     * @return 商品分类列表
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量插入商品种类
     * @param productCategoryList 商品List
     * @return 插入行数
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     * @param productCategoryId 商品种类ID
     * @param shopId 商铺ID
     * @return 删除行数
     */
    int deleteProductCategory (@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
