package com.javaweb.bookstore.test;

import org.junit.Test;

import com.javaweb.bookstore.dao.AccountDAO;
import com.javaweb.bookstore.dao.impl.AccountDAOImpl;
import com.javaweb.bookstore.domain.Account;

public class AccountDAOImplTest {
	
	AccountDAO accountDao = new AccountDAOImpl();

	@Test
	public void testGet() {
		Account account = accountDao.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		accountDao.updateBalance(1, 40);
	}

}
