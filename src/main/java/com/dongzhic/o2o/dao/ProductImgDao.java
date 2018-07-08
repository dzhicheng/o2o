package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.ProductImg;

import java.util.List;

/**
 * @author dongzc
 */
public interface ProductImgDao {

    /**
     * 批量添加商品图片
     * @param productImgList 商品图片List
     * @return 修改行数
     */
    int batchInsertProductImg (List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId 商品Id
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 获取指定商品下的图片列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);
}
