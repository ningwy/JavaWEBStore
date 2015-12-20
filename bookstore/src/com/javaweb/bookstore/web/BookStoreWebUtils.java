package com.javaweb.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.javaweb.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {

	
	/**
	 * 获取购物车:
	 * 首先从请求session中获取 shoppingCart,如果shoppingCart为空，则 new ShoppingCart() 来初始化购物车;
	 * @param request
	 * @return
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			session.setAttribute("shoppingCart", sc);
		}
		
		return sc;
	}
	
}
