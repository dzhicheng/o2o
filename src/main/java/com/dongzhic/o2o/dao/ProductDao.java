package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Dao类
 * @author dongzc
 */
public interface ProductDao {

    /**
     * 插入商品
     * @param product 商品
     * @return 修改行数
     */
    int insertProduct (Product product);

    /**
     * 查询商品列表并分页，壳输入的条件有：商品名，商品状态，店铺Id，商品类别
     * @param productCondition 商品
     * @param beginIndex 从数据库第几行开始查询
     * @param pageSize 返回多少条记录
     * @return
     */
    List<Product> queryProductList (@Param("productCondition") Product productCondition, @Param("rowIndex") int beginIndex,
                                    @Param("pageSize") int pageSize);

    /**
     * 查询对应的商品总数
     * @param productCondition
     * @return
     */
    int queryProductCount (@Param("productCondition") Product productCondition);

    /**
     * 根据productId获取唯一商品信息
     * @param productId 商品id
     * @return 商品
     */
    Product getProductById (long productId);

    /**
     * 跟新商品信息
     * @param product
     * @return
     */
    int updateProduct (Product product);

    /**
     * 删除商品类别前，将商品类别Id置为空
     * @param productCategoryId 商品类别Id
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
