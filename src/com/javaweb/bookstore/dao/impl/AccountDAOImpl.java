package com.javaweb.bookstore.dao.impl;

import com.javaweb.bookstore.dao.AccountDAO;
import com.javaweb.bookstore.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO {

	@Override
	public Account get(Integer accountId) {
		String sql = "SELECT accountId, balance FROM account where accountId = ?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "UPDATE account SET balance = balance - ? where accountId = ?";
		update(sql, amount, accountId);
	}

}
