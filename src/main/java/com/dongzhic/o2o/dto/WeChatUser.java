package com.dongzhic.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 微信用户实体类
 * @author dongzc
 * @date 2018/7/17 16:40
 */
public class WeChatUser implements Serializable {

    /**
     * openId,标志该公众号下，该用户的唯一Id
     */
    @JsonProperty("openid")
    private String openId;
    /**
     * 昵称
     */
    @JsonProperty("nickname")
    private String nickName;
    /**
     * 性别
     */
    @JsonProperty("sex")
    private String sex;
    /**
     * 省份
     */
    @JsonProperty("province")
    private String province;
    /**
     * 城市
     */
    @JsonProperty("city")
    private String city;
    /**
     * 区
     */
    @JsonProperty("country")
    private String country;
    /**
     * 头像图片地址
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;
    /**
     * 语言
     */
    @JsonProperty("language")
    private String language;
    /**
     *  用户权限
     */
    @JsonProperty("privilege")
    private String[] privilege;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "openId: " + this.getOpenId() + ", nickName: " +this.getNickName();
    }
}
