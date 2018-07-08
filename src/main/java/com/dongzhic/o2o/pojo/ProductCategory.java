package com.dongzhic.o2o.pojo;

import java.util.Date;

/**
 * 商品类别
 * @author dongzc
 */
public class ProductCategory {
	/**
	 * ID
	 */
	private Long productCategoryId;
	/**
	 * 商铺ID
	 */
	private Long shopId;
	/**
	 * 商品名称
	 */
	private String productCategoryName;
	/**
	 * 权重
	 */
	private Integer priority;
	/**
	 * 创建日期
	 */
	private Date createTime;
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
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

}
