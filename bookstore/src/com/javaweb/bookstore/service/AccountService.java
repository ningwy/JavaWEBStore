package com.javaweb.bookstore.service;

import com.javaweb.bookstore.dao.AccountDAO;
import com.javaweb.bookstore.dao.impl.AccountDAOImpl;
import com.javaweb.bookstore.domain.Account;

public class AccountService {

	private AccountDAO accountDAO = new AccountDAOImpl();
	
	public Account getAccountByAccountId(Integer accountId) {
		return accountDAO.get(accountId);
	}
	
}
