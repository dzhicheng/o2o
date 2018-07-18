package com.dongzhic.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户授权token：接收accessToken以及openid等信息
 * @author dongzc
 * @date 2018/7/17 16:32
 */
public class UserAccessToken {

    /**
     * 获取到的凭证
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty("expires_in")
    private String expiresIn;
    /**
     * 表示更新令牌，用于获取下一次的访问令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * 该用户在此公众号下的身份标识，对于此公众号具有唯一性
     */
    @JsonProperty("openid")
    private String openId;
    /**
     * 权限范围
     */
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "accessToken:"+ this.getAccessToken() + ", openId:" +this.getOpenId();
    }
}
