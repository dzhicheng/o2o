package com.dongzhic.o2o.enums;

/**
 * @author dongzc
 * @date 2018.04.26
 */
public enum ProductStateEnum {

    /**
     * 非法商品
     */
    OFFLINE(-1, "非法商品"),
    /**
     *下架"
     */
    DOWN(0, "下架"),
    /**
     *操作成功
     */
    SUCCESS(1, "操作成功"),
    /**
     *操作失败
     */
    INNER_ERROR(-1001, "操作失败"),
    /**
     *商品为空
     */
    EMPTY(-1002, "商品为空");

    private int state;
    private String stateInfo;

    ProductStateEnum (int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductStateEnum stateOf (int index) {
        for (ProductStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
