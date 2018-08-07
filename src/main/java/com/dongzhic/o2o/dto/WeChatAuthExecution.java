package com.dongzhic.o2o.dto;

import com.dongzhic.o2o.enums.WeChatAuthStateEnum;
import com.dongzhic.o2o.pojo.WeChatAuth;

import java.util.List;

/**
 * @author dongzc
 * @date 2018/7/31 16:01
 */
public class WeChatAuthExecution {

    /**
     * 结果状态
     */
    private int state;
    /**
     * 结果状态
     */
    private String stateInfo;
    private int count;
    private WeChatAuth weChatAuth;
    private List<WeChatAuth> weChatAuthList;

    public WeChatAuthExecution () {}

    /**
     * 失败时构造器
     * @param stateEnum
     */
    public WeChatAuthExecution (WeChatAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 成功时构造器
     * @param stateEnum
     * @param weChatAuth
     */
    public WeChatAuthExecution (WeChatAuthStateEnum stateEnum, WeChatAuth weChatAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.weChatAuth = weChatAuth;
    }

    /**
     * 成功时构造器
     * @param stateEnum
     * @param weChatAuthList
     */
    public WeChatAuthExecution (WeChatAuthStateEnum stateEnum, List<WeChatAuth> weChatAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.weChatAuthList = weChatAuthList;
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

    public WeChatAuth getWeChatAuth() {
        return weChatAuth;
    }

    public void setWeChatAuth(WeChatAuth weChatAuth) {
        this.weChatAuth = weChatAuth;
    }

    public List<WeChatAuth> getWeChatAuthList() {
        return weChatAuthList;
    }

    public void setWeChatAuthList(List<WeChatAuth> weChatAuthList) {
        this.weChatAuthList = weChatAuthList;
    }
}
