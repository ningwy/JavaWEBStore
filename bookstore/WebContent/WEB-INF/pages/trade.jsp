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
		user: ${user.username }
	
		<c:forEach items="${user.trades }" var="trade">
		
			TradeTime: ${trade.tradeTime }
			<br><br>
			
			<table>
				<tr>
					<th>书名</th>
					<th colspan="2">单价</th>
					<th></th>
					<th>数量</th>
				</tr>
				
				<c:forEach items="${trade.items }" var="item">
				
					<tr>
						<td>${item.book.title }</td>
						<td colspan="2">${item.book.price }</td>
						<td></td>
						<td>${item.quantity }</td>
					</tr>
				
				</c:forEach>
				
			</table>
			
			<br>
			<hr>
			<br>
			
		</c:forEach>
	
	</center>

</body>
</html>