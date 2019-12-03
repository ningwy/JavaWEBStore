package com.javaweb.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.javaweb.bookstore.domain.Account;
import com.javaweb.bookstore.domain.Book;
import com.javaweb.bookstore.domain.ShoppingCart;
import com.javaweb.bookstore.domain.ShoppingCartItem;
import com.javaweb.bookstore.domain.User;
import com.javaweb.bookstore.service.AccountService;
import com.javaweb.bookstore.service.BookService;
import com.javaweb.bookstore.service.UserService;
import com.javaweb.bookstore.web.BookStoreWebUtils;
import com.javaweb.bookstore.web.CriteriaBook;
import com.javaweb.bookstore.web.Page;


public class BookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	private BookService bookService = new BookService();
	
	private UserService userService = new UserService();
	
	private AccountService accountService = new AccountService();
	
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		StringBuffer errors = new StringBuffer("");
		
		//1.验证表单域是否通过
		if (errors.toString().equals("")) {
			errors = validateFormField(username, accountId);
			//2.验证用户名和账户是否匹配
			if (errors.toString().equals("")) {
				errors = validateUserNameMatchAccount(username, accountId);
				//3.验证库存是否足够
				if (errors.toString().equals("")) {
					errors = validateStoreNumber(request);
					//4.验证余额是否充足
					if (errors.toString().equals("")) {
						errors = validateBalance(request, accountId);
					}
				}
			}
		}
		
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
		bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
		
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}
	
	//4.验证余额是否充足
	public StringBuffer validateBalance(HttpServletRequest request, String accountId) {
		
		StringBuffer errors = new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		float totalMoney = sc.getTotalMoney();
		Account account = accountService.getAccountByAccountId(Integer.parseInt(accountId));
		float balance = account.getBalance();
		if (totalMoney > balance) {
			errors.append("余额不足<br>");
		}
		
		return errors;
	}
	
	//3.验证库存是否足够
	public StringBuffer validateStoreNumber(HttpServletRequest request) {
		
		StringBuffer errors = new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		for (ShoppingCartItem sci : sc.getItems()) {
			int quantities = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if (quantities > storeNumber) {
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
		
		return errors;
	}
	
	//2.验证用户名和账户是否匹配
	public StringBuffer validateUserNameMatchAccount(String username, String accountId) {
		
		StringBuffer errors = new StringBuffer("");
		User user = userService.getUserByUserName(username);
		boolean flag = false;
		
		/**
		 * 这种写法不行,原因如下:
		 * 这种写法只是验证了当 user != null 的情况，但是万一 user == null 呢, 对这种情况并没有进行处理，
		 * 这样如果当 user == null 时，就会直接return errors了，转而进行了下一个验证
		 * 
		if (user != null) {
			if (!accountId.trim().equals("" + user.getAccountId())) {
				errors.append("用户名和账号不匹配<br>");
			}
		}
		 * 
		 */
		
		if (user != null) {
			int accountId2 = user.getAccountId();
			if (accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}
		
		if (!flag) {
			errors.append("用户名和账号不匹配<br>");
		}
		
		return errors;
	}
	
	//1.验证表单域是否通过
	public StringBuffer validateFormField(String username, String accountId) {
		
		StringBuffer errors = new StringBuffer("");
		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("账号不能为空<br>");
		}
		
		return errors;
	}
	
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		int id = -1;
		int quantity = -1;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {}
		
		if (id > 0 && quantity > 0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			bookService.updateItemQuantity(id, sc, quantity);
			Map<String, Object> map = new HashMap<>();
			map.put("bookNumber", sc.getBookNumber());
			map.put("totalMoney", sc.getTotalMoney());
			
			/**
			 * 以下四行代码不会，多加注意！(Ajax, response 这两个知识点掌握得不好)
			 */
			Gson gson = new Gson();
			String result = gson.toJson(map);
			response.setContentType("text/javascript");
			response.getWriter().print(result);
			
		}
		
		
	}
	
	protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		bookService.clearCart(sc);
		
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(id, sc);
		
		if (sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
	
	
	protected void fordward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}
	
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		boolean flag = false;
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		if (id > 0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			flag = bookService.addToCart(id, sc);
		}
		
		
		if (flag) {
			getBooks(request, response);
			return; 
		}
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		
	}
	
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if (id > 0) {
			book = bookService.getBook(id);
		}
		
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return; 
		}
		
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
		
	}
	
	protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		
		/*
		 * 这种写法是错误的，应该把三个转换分别放进三个 try-catch语句块中。因为如果放在同一个 try-catch中，
		 * 如果第一个出现了异常，那么下面两个就不会再执行，会直接退出
		 * 
		try {
			pageNo = Integer.parseInt(pageNoStr);
			minPrice = Integer.parseInt(minPriceStr);
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		*/
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPageBook(criteriaBook);

		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
		
	}

}
