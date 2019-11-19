package com.javaweb.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javaweb.bookstore.dao.BookDAO;
import com.javaweb.bookstore.domain.Book;
import com.javaweb.bookstore.domain.ShoppingCartItem;
import com.javaweb.bookstore.web.CriteriaBook;
import com.javaweb.bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
	

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		Book book = query(sql, id);
		return book;
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		
		Page<Book> page = new Page<>(cb.getPageNo());
		
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//校验pageNo的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(id) from mybooks where price >= ? and price <= ?";
		
		return getSingleVal(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "select id, author, title, price, publishingDate, "
				+ "salesAmount, storeNumber, remark from mybooks "
				+ "where price >= ? and price <= ? limit ?,?";
		List<Book> lists = queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(), 
				(cb.getPageNo() - 1) * pageSize, pageSize);
		return lists;
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storenumber from mybooks where id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, "
				+ "storeNumber = storeNumber - ? WHERE id = ?";
		
		List<ShoppingCartItem> scis = new ArrayList<>(items);
		Object[][] params = null;
		params = new Object[items.size()][3];
		
		for (int i = 0; i < items.size(); i++) {
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getId();
		}
		
		batch(sql, params);
	}

}
