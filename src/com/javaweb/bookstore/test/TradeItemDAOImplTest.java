package com.javaweb.bookstore.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.javaweb.bookstore.dao.TradeItemDAO;
import com.javaweb.bookstore.dao.impl.TradeItemDAOImpl;
import com.javaweb.bookstore.domain.TradeItem;

public class TradeItemDAOImplTest {
	
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	@Test
	public void testBatchSave() {
		
		List<TradeItem> items = new ArrayList<>();
		
		items.add(new TradeItem(null, 1, 10, 17));
		items.add(new TradeItem(null, 2, 9, 17));
		items.add(new TradeItem(null, 3, 8, 17));
		items.add(new TradeItem(null, 4, 7, 17));
		items.add(new TradeItem(null, 5, 6, 17));
		
		tradeItemDAO.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(17);
		
		System.out.println(items);
	}

}
