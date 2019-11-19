package com.javaweb.bookstore.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.javaweb.bookstore.dao.BookDAO;
import com.javaweb.bookstore.dao.impl.BookDAOImpl;
import com.javaweb.bookstore.domain.Book;
import com.javaweb.bookstore.domain.ShoppingCartItem;
import com.javaweb.bookstore.web.CriteriaBook;
import com.javaweb.bookstore.web.Page;

public class BookDAOImplTest {
	
	BookDAO bookDAO = new BookDAOImpl();

	@Test
	public void testGetBook() {
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(50, 60, 1);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("pageNo: " + page.getPageNo());
		System.out.println("pageSize: " + page.getPageSize());
		System.out.println("totalPageNumber: " + page.getTotalPageNumber());
		System.out.println("list: " + page.getList());
		System.out.println("prevPage: " + page.getPrevPage());
		System.out.println("nextPage: " + page.getNextPage());
		
	}

	@Test
	public void testGetTotalBookNumber() {
		
	}

	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStoreNumber() {
		long count = bookDAO.getStoreNumber(5);
		System.out.println(count);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<>();
		
		Book book = bookDAO.getBook(1);
		ShoppingCartItem sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		book = bookDAO.getBook(2);
		sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		book = bookDAO.getBook(3);
		sc = new ShoppingCartItem(book);
		sc.setQuantity(10);
		items.add(sc);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
