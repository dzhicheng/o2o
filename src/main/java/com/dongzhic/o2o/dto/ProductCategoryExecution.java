package com.dongzhic.o2o.dto;

import com.dongzhic.o2o.enums.ProductCategoryStateEnum;
import com.dongzhic.o2o.pojo.ProductCategory;

import java.util.List;

/**
 * @date 2018.03.30
 * @author dongzhic
 */
public class ProductCategoryExecution {
    /**
     * 结果状态
     */
    private int state;
    /**
     * 状态标识
     */
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution () {}

    /**
     * 操作失败时使用的构造器
     * @param productCategoryStateEnum
     */
    public ProductCategoryExecution (ProductCategoryStateEnum productCategoryStateEnum) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
    }

    /**
     * 操作成功时使用的构造器
     */
    public ProductCategoryExecution (ProductCategoryStateEnum productCategoryStateEnum,
                                     List<ProductCategory> productCategoryList) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
