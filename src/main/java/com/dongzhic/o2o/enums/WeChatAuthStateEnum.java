package com.dongzhic.o2o.enums;

/**
 * @author dongzc
 * @date 2018/7/31 15:48
 */
public enum WeChatAuthStateEnum {

    /**
     * openId输入有误
     */
    LOGINFAIL(-1, "openId输入有误"),
    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 注册信息为空
     */
    NULL_AUTH_INFO(-1006, "注册信息为空");

    private int state;
    private String stateInfo;

    WeChatAuthStateEnum (int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static WeChatAuthStateEnum stateOf (int index){
        for (WeChatAuthStateEnum state : values()) {
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
