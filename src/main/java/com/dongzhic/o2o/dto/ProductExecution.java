package com.dongzhic.o2o.dto;

import com.dongzhic.o2o.enums.ProductStateEnum;
import com.dongzhic.o2o.pojo.Product;

import java.util.List;

/**
 * @author dongzc
 * @date 2018.04.26
 */
public class ProductExecution {

    /**
     * 结果状态
     */
    private int state;
    /**
     * 结果标识
     */
    private String stateInfo;
    /**
     * 商品数量
     */
    private int count;
    /**
     * 操作的product(增上该商品的时候用)
     */
    private Product product;
    /**
     * 获取的product列表(查询商品的时候用)
     */
    private List<Product> productList;

    public ProductExecution () {}

    /**
     * 失败的构造器
     * @param stateEnum
     */
    public ProductExecution (ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 成功的构造器
     * @param stateEnum
     * @param product
     */
    public ProductExecution (ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    /**
     * 成功的构造器
     * @param stateEnum
     * @param productList
     */
    public ProductExecution (ProductStateEnum stateEnum, List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
