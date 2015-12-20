package com.javaweb.bookstore.test;

import org.junit.Test;

import com.javaweb.bookstore.dao.UserDAO;
import com.javaweb.bookstore.dao.impl.UserDAOImpl;
import com.javaweb.bookstore.domain.User;

public class UserDAOImplTest {
	
	private UserDAO userDAO = new UserDAOImpl();

	@Test
	public void testGetUser() {
		User user = userDAO.getUser("AAA");
		System.out.println(user);
	}

}
