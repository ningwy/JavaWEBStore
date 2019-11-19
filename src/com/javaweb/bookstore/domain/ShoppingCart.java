package com.javaweb.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	/**
	 * 向购物车中添加某种书
	 * @param book
	 */
	public void add(Book book) {
		//1.检查购物车中是否含有该种书，若包含，则让其 quantity + 1;
		//若不包含，则新创建一个该种书对应的 ShoppingCartItem 对象
		
		ShoppingCartItem sci = books.get(book.getId());
		if (sci == null) {
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		} else {
			sci.increment();
		}
	}
	
	/**
	 * 判断购物车中是否含有id 对应的某种书
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}
	
	/**
	 * 返回购物车中所有的书对应的 集合:Map<Integer, ShoppingCartItem>
	 * @return
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	/**
	 * 返回购物车中所有ShoppingCartItem的集合
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}
	
	/**
	 * 返回购物车中总的书本的数量
	 * @return
	 */
	public int getBookNumber() {
		int count = 0;
		
		for (ShoppingCartItem sci : books.values()) {
			count += sci.getQuantity();
		}
		
		return count;
	}
	
	/**
	 * 返回购物车中结账时需要的总的钱数
	 * @return
	 */
	public float getTotalMoney() {
		float totalMoney = 0;
		
		for (ShoppingCartItem sci : getItems()) {
			totalMoney += sci.getItemMoney();
		}
		
		return totalMoney;
	}
	
	/**
	 * 判断购物车是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}
	
	/**
	 * 清空购物车
	 */
	public void clear() {
		books.clear();
	}
	
	/**
	 * 移除购物车上某种书
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}
	
	/**
	 * 改变指定id的书的数量为count的数量
	 * @param id
	 */
	public void updateItemQuantity(Integer id, Integer count) {
		ShoppingCartItem sci = books.get(id);
		if (sci != null) {
			sci.setQuantity(count);
		}
	}
	
}
