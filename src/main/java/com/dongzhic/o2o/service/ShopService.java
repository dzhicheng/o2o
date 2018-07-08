package com.dongzhic.o2o.service;

import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ShopExecution;
import com.dongzhic.o2o.exception.ShopOperationException;
import com.dongzhic.o2o.pojo.ProductCategory;
import com.dongzhic.o2o.pojo.Shop;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author dongzc
 **/
public interface ShopService {
    /**
     *  * 注册商铺
     * @param shop 商铺实体类
     * @param thumbnail 图片
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop (Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId 商铺Id
     * @return 商铺
     */
    Shop getByShopId (long shopId);

    /**
     * 跟新店铺信息，包括对图片的处理
     * @param shop 商铺
     * @param thumbnail 图片
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop (Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition 查询条件
     * @param pageIndex 页数
     * @param pageSize 返回的条数
     * @return
     */
    ShopExecution getShopList (Shop shopCondition, int pageIndex, int pageSize);

}
