package com.dongzhic.o2o.pojo;

import java.util.Date;

/**
 * 店铺类别
 * @author dongzc
 */
public class ShopCategory {
	/**
	 * ID
	 */
	private Long shopCategoryId;
	/**
	 * 名称
	 */
	private String shopCategoryName;
	/**
	 * 描述
	 */
	private String shopCategoryDesc;
	/**
	 * 图片
	 */
	private String shopCategoryImg;
	/**
	 * 权重
	 */
	private Integer priority;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date lastEditTIme;
	/**
	 * 上级ID
	 */
	private ShopCategory parent;

	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}
	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}
	public String getShopCategoryImg() {
		return shopCategoryImg;
	}
	public void setShopCategoryImg(String shopCategoryImg) {
		this.shopCategoryImg = shopCategoryImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTIme() {
		return lastEditTIme;
	}
	public void setLastEditTIme(Date lastEditTIme) {
		this.lastEditTIme = lastEditTIme;
	}
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	
	

}
