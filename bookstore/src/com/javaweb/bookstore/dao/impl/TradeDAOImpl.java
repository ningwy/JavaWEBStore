package com.javaweb.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.javaweb.bookstore.dao.TradeDAO;
import com.javaweb.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade(userId, tradeTime) VALUES(?, ?)";
		//update(sql, trade.getUserId(), trade.getTradeTime());
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade WHERE userId = ? "
				+ "ORDER BY tradeTime DESC";
		
		Set<Trade> trades = new LinkedHashSet<>(queryForList(sql, userId));
		
		return trades;
	}

}
