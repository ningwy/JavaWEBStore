package com.javaweb.bookstore.web;

import java.sql.Connection;

public class ConnectionContext {
	
	private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	
	private ConnectionContext() {
		// TODO Auto-generated constructor stub
	}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}
	
	public void bind(Connection connection) {
		threadLocal.set(connection);
	}
	
	public Connection get() {
		return threadLocal.get();
	}
	
	public void remove() {
		threadLocal.remove();
	}

}
