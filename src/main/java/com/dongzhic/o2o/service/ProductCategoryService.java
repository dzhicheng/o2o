package com.dongzhic.o2o.service;

import com.dongzhic.o2o.dto.ProductCategoryExecution;
import com.dongzhic.o2o.exception.ProductCategoryOperationException;
import com.dongzhic.o2o.pojo.ProductCategory;

import java.util.List;

/**
 * @author dongzc
 * @date 2018.03.07
 */
public interface ProductCategoryService {

    /**
     * 查询指定店铺下的所有商品类别信息
     *
     * @param shopId 店铺Id
     * @return 商品种类列表
     */
    List<ProductCategory> getProductCategoryList (long shopId);

    /**
     *  批量注册商品
     * @param productCategoryList 商品List
     * @throws ProductCategoryOperationException
     * @return
     */
    ProductCategoryExecution batchInsertProductCategory
            (List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别Id置为空，在删除该商品的类别
     * @param productCategoryId 商品类别Id
     * @param shopId 商铺Id
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory (long productCategoryId, long shopId)
        throws ProductCategoryOperationException;
}
