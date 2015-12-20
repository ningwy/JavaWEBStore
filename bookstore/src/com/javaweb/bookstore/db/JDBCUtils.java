package com.javaweb.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.javaweb.bookstore.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * JDBC �Ĺ�����
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	
	static{
		dataSource = new ComboPooledDataSource("javawebapp");
	}
	
	//��ȡ��ݿ�����
	public static Connection getConnection(){  
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接失败");
		}
	}
 
	//�ر���ݿ�����
	public static void release(Connection connection) {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接失败");
		}
	}
	
	//�ر���ݿ�����
	public static void release(ResultSet rs, Statement statement) {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接失败");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接失败");
		}
	}
	
}
