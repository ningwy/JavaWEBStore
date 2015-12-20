package com.javaweb.bookstore.domain;

import com.javaweb.bookstore.domain.Book;

/**
 * 该类封装了 购物车中某种书的Book的信息 和该书的数量
 * @author ningwy
 *
 */
public class ShoppingCartItem {

	private Book book;
	
	private Integer quantity = 1;
	
	public ShoppingCartItem(Book book) {
		this.book = book;
	}
	
	public Book getBook() {
		return book;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 获取购物车中某种书的的总金额
	 * @return
	 */
	public float getItemMoney() {
		return book.getPrice() * quantity;
	}
	
	public void increment() {
		quantity++;
	}
	
}
