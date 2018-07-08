package com.dongzhic.o2o.enums;

/**
 * @author dongzhic
 * @date 2018.03.12
 */
public enum ProductCategoryStateEnum {
    /**
     * 创建成功
     */
    SUCCESS(1, "创建成功"),
    /**
     * 操作失败
     */
    INNER_ERROR(-1001, "操作失败"),
    /**
     * 添加数少于1
     */
    EMPTY_LIST(-1002, "添加数少于1");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum (int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateOf (int index) {
        for (ProductCategoryStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
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
