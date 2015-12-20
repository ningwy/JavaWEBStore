package com.javaweb.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaweb.bookstore.domain.User;
import com.javaweb.bookstore.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		User user = userService.getUserWithTrades(username);
		
		if (user != null) {
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/pages/trade.jsp").forward(request, response);
		}
	}

}
