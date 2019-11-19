package com.javaweb.bookstore.web;

/**
 * CriteriaBook 类封装了 搜索的 条件， 包括 最低价格 minPrice 和最高价格 maxPrice 还有 当前页面的页码
 * @author ningwy
 *
 */
public class CriteriaBook {
	
	//最低价格
	private float minPrice = 0;
	//最高价格
	private float maxPrice = Integer.MAX_VALUE;
	//当前页面的页码
	private int pageNo;
	
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
	}
	public CriteriaBook() {
		super();
	}
	@Override
	public String toString() {
		return "CriteriaBook [minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", pageNo=" + pageNo + "]";
	}
	
}
