package com.dongzhic.o2o.service;

import com.dongzhic.o2o.dto.ImageHolder;
import com.dongzhic.o2o.dto.ProductExecution;
import com.dongzhic.o2o.exception.ProductOperationException;
import com.dongzhic.o2o.pojo.Product;
import java.util.List;

/**
 * @author dongzc
 * @date 2018.04.26
 */
public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     * @param product 商品
     * @param thumbnail 图片
     * @param productImgList 图片List
     * @throws ProductOperationException 商品操作异常
     * @return 商品操作dto
     */
    ProductExecution addProduct (Product product, ImageHolder thumbnail,
                                List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 查询商品列表并分页，可输入的条件有：商品名，商品状态，店铺Id，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList (Product productCondition, int pageIndex, int pageSize);

    /**
     * 查询指定商品的信息
     * @param productId
     * @return
     */
    Product getProductById (long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList) throws ProductOperationException;

}
