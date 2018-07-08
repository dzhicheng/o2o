package com.dongzhic.o2o.enums;

/**
 * @author dongzc
 **/
public enum ShopSateEnum {

    /**
     * 审核中
     */
    CHECK(0, "审核中"),
    /**
     * 非法店铺
     */
    OFFLINE(-1, "非法店铺"),
    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功"),
    /**
     * 通过认证
     */
    PASS(2, "通过认证"),
    /**
     * 内部系统错误
     */
    INNER_ERROR(-1001, "内部系统错误"),
    /**
     * shopId为空
     */
    NULL_SHOPID(-1002, "shopId为空"),
    /**
     * shop信息为空
     */
    NULL_SHOP(-1003, "shop信息为空");

    private int state;
    private String stateInfo;

    ShopSateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopSateEnum stateOf (int state) {
        for (ShopSateEnum sateEnum : values() ) {
            if (sateEnum.getState() == state ) {
                return sateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
