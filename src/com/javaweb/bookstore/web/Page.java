package com.javaweb.bookstore.web;

import java.util.List;

/**
 * Page 封装了 页码上 的各种信息， 包括页码pageNo，页码上显示 book 的集合 List<T>， 页面上该显示多少本书 pageSize,
 * 和 总的 book 的数量
 * @author ningwy
 *
 * @param <T>
 */
public class Page<T> {

	//1.当前的页码
	private int pageNo;
	
	//2.当前页显示的book 的List
	private List<T> list;
	
	//3.当前页显示book 的个数
	private int pageSize = 3;
	
	//4.总的book 的数量
	private long totalItemNumber;

	//初始化Page的时候，得知道当前页面的页码是多少，所以在构造器中初始化pageNo
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}
	
	//获取pageNo
	public int getPageNo() {
		
		//校验PageNo，当传入的pageNo小于0时，让pageNo等于 1，当大于总的页数时，让pageNo等于最后一页的页码
		if (pageNo < 0) {
			pageNo = 1;
		}
		//当大于总的页数时，让pageNo等于最后一页的页码
		int totalPageNum = getTotalPageNumber();
		if (pageNo > totalPageNum) {
			pageNo = totalPageNum;
		}
		return pageNo;
	}
	
	//获取pageSize
	public int getPageSize() {
		return pageSize;
	}
	
	//获取 List<T>
	public List<T> getList() {
		return list;
	}
	
	//设置 List<T>
	public void setList(List<T> list) {
		this.list = list;
	}
	
	//获取总的页数
	public int getTotalPageNumber() {
		
		int totalPageNumber = (int) totalItemNumber / pageSize;
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber = totalPageNumber + 1;
		}
		
		return totalPageNumber;
	}
	
	//设置 总的 book 的数量
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	//是否有下一页
	public boolean isHasNext() {
		
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		
		return false;
	}
	
	//是否有上一页
	public boolean isHasPrev() {
		
		if (getPageNo() > 1) {
			return true;
		}
		
		return false;
	}
	
	//获取下一页的页码
	public int getNextPage() {
		
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		
		return getPageNo();
	}
	
	//获取上一页的页码
	public int getPrevPage() {
		
		if (isHasPrev()) {
			return getPageNo() - 1;
		}
		
		return getPageNo();
	}
	
}
