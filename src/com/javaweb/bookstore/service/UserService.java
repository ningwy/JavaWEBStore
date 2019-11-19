package com.javaweb.bookstore.service;

import java.util.Set;

import com.javaweb.bookstore.dao.BookDAO;
import com.javaweb.bookstore.dao.TradeDAO;
import com.javaweb.bookstore.dao.TradeItemDAO;
import com.javaweb.bookstore.dao.UserDAO;
import com.javaweb.bookstore.dao.impl.BookDAOImpl;
import com.javaweb.bookstore.dao.impl.TradeDAOImpl;
import com.javaweb.bookstore.dao.impl.TradeItemDAOImpl;
import com.javaweb.bookstore.dao.impl.UserDAOImpl;
import com.javaweb.bookstore.domain.Trade;
import com.javaweb.bookstore.domain.TradeItem;
import com.javaweb.bookstore.domain.User;

public class UserService {
	
	private UserDAO userDAO = new UserDAOImpl();
	
	private TradeDAO tradeDAO = new TradeDAOImpl();
	
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	private BookDAO bookDAO = new BookDAOImpl();

	public User getUserByUserName(String username) {
		return userDAO.getUser(username);
	}
	
	public User getUserWithTrades(String username) {
		
		User user = userDAO.getUser(username);
		
		if (user == null) {
			return null;
		}
		
		if (user != null) {
			Set<Trade> trades = tradeDAO.getTradesWithUserId(user.getUserId());
			if (trades != null) {
				
				for (Trade trade : trades) {
					int tradeId = trade.getTradeId();
					Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
					
					if (items != null) {
						for (TradeItem item : items) {
							item.setBook(bookDAO.getBook(item.getBookId()));
						}
						
						if (items != null && items.size() != 0) {
							trade.setItems(items);
						}
					}
				}
				
				if (trades != null && trades.size() != 0) {
					user.setTrades(trades);
				}
			}
		}
		
		return user;
	}
	
}
