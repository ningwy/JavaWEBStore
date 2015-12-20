package com.javaweb.bookstore.test;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import com.javaweb.bookstore.dao.TradeDAO;
import com.javaweb.bookstore.dao.impl.TradeDAOImpl;
import com.javaweb.bookstore.domain.Trade;

public class TradeDAOImplTest {
	
	private TradeDAO tradeDAO = new TradeDAOImpl();

	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(2);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDAO.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> trades = tradeDAO.getTradesWithUserId(2);
		System.out.println(trades);
	}

}
