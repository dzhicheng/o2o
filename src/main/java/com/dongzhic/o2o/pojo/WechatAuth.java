package com.dongzhic.o2o.pojo;

import java.util.Date;

/**
 * 微信账号
 * @author dongzc
 */
public class WechatAuth {
	/**
	 * ID
	 */
	private Long wechatAUthId;
	/**
	 * openID
	 */
	private String openId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 用户
	 */
	private PersonInfo personInfo;
	public Long getWechatAUthId() {
		return wechatAUthId;
	}
	public void setWechatAUthId(Long wechatAUthId) {
		this.wechatAUthId = wechatAUthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	

}
