package com.javaweb.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.javaweb.bookstore.dao.AccountDAO;
import com.javaweb.bookstore.dao.BookDAO;
import com.javaweb.bookstore.dao.TradeDAO;
import com.javaweb.bookstore.dao.TradeItemDAO;
import com.javaweb.bookstore.dao.UserDAO;
import com.javaweb.bookstore.dao.impl.AccountDAOImpl;
import com.javaweb.bookstore.dao.impl.BookDAOImpl;
import com.javaweb.bookstore.dao.impl.TradeDAOImpl;
import com.javaweb.bookstore.dao.impl.TradeItemDAOImpl;
import com.javaweb.bookstore.dao.impl.UserDAOImpl;
import com.javaweb.bookstore.domain.Book;
import com.javaweb.bookstore.domain.ShoppingCart;
import com.javaweb.bookstore.domain.ShoppingCartItem;
import com.javaweb.bookstore.domain.Trade;
import com.javaweb.bookstore.domain.TradeItem;
import com.javaweb.bookstore.web.CriteriaBook;
import com.javaweb.bookstore.web.Page;

public class BookService {

	private BookDAO bookDAO = new BookDAOImpl();
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	
	public Page<Book> getPageBook(CriteriaBook cb) {
		return bookDAO.getPage(cb);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		if (book != null) {
			sc.add(book);
			return true;
		}
		return false;
	}

	public void removeItemFromShoppingCart(int id, ShoppingCart sc) {
		sc.removeItem(id);
	}

	public void clearCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(int id, ShoppingCart sc, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}

	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		//1.更新 mybooks 表中 salesamount 和 storenumber 的记录
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//2.更新 account 表中 balance 的记录
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3.向 trade 中插入一条记录
		Trade trade = new Trade();
		trade.setUserId(userDAO.getUser(username).getUserId());
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDAO.insert(trade);
		
		//4.向 tradeitem 中插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(item.getBook().getId());
			tradeItem.setQuantity(item.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5.清空购物车
		shoppingCart.clear();
	}
	
}
