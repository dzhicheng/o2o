package com.dongzhic.o2o.dto;

import com.dongzhic.o2o.enums.ShopSateEnum;
import com.dongzhic.o2o.pojo.Shop;

import java.util.List;

/**
 * @author dongzc
 **/
public class ShopExecution {

    /**
     * 结果状态
     */
    private int state;
    /**
     *状态标识
     */
    private String stateInfo;
    /**
     *店铺数量
     */
    private int count;
    /**
     *操作的shop(增删改店铺的时候用到)
     */
    private Shop shop;
    /**
     * shop列表(查询店铺列表的时候使用)
     */
    private List<Shop> shopList;

    public ShopExecution(){}
    /**
     * 店铺操作失败时使用的构造器
     * @param sateEnum 结果状态
     */
    public ShopExecution(ShopSateEnum sateEnum) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
    }
    /**
     * 店铺操作成功时使用的构造器
     * @param sateEnum 结果状态
     * @param shop 商铺
     */
    public ShopExecution (ShopSateEnum sateEnum, Shop shop) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 店铺操作成功时使用的构造器
     * @param sateEnum 结果状态
     * @param shop 商铺
     * @param shopList 商铺List
     */
    public ShopExecution (ShopSateEnum sateEnum, Shop shop, List<Shop> shopList) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shop = shop;
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
