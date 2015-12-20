package com.javaweb.bookstore.test;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.javaweb.bookstore.dao.impl.BookDAOImpl;
import com.javaweb.bookstore.domain.Book;

public class BaseDAOTest {
	
	private BookDAOImpl bookDAOImpl = new BookDAOImpl();

	@Test
	public void testInsert() {
		String sql = "INSERT INTO trade (userid, tradetime) VALUES(? ,?)";
		long id = bookDAOImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "update mybooks set salesamount = ? where id = ?";
		bookDAOImpl.update(sql, 11, 4);
	}

	@Test
	public void testQuery() {
//		String sql = "select * from mybooks where id = ?";
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		Book book = bookDAOImpl.query(sql, 4);
		System.out.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql = "select * from mybooks where id < ?";
		List<Book> books = bookDAOImpl.queryForList(sql, 4);
		System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "select count(id) from mybooks";
		long ids = bookDAOImpl.getSingleVal(sql);
		System.out.println(ids);
	}

	@Test
	public void testBatch() {
		String sql = "update mybooks set salesamount = ?, storenumber = ? where id = ?";
		bookDAOImpl.batch(sql, new Object[]{1,1,1}, new Object[]{2,2,2}, new Object[]{3,3,3});
	}

}
