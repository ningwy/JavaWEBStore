<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
		<br><br>
		
		
		您要购买的书籍数量为:${sessionScope.shoppingCart.bookNumber } 本
		<br><br>
		
		需要支付的金额为: ￥${sessionScope.shoppingCart.totalMoney }
		<br><br>
		
		<c:if test="${requestScope.errors != null }">
			<font color="red">${requestScope.errors }</font>
		</c:if>
		
		<form action="bookServlet?method=cash" method="post">
		
			<table>
				<tr>
					<td>信用卡姓名:</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>信用卡账号:</td>
					<td><input type="text" name="accountId" /></td>
				</tr>
				
				<tr>
					<td colspan="2"><input type="submit" value="Submit" /></td>
				</tr>
			</table>
		
		</form>
		
	
	</center>

</body>
</html>