<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-2.1.4.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>

</head>
<body>

	<div><img src="${ pageContext.request.contextPath }/static/fudan_logo.png" alt="" width="80" height="80"></div>
	<center>
	
		<br><br>
		书名: ${book.title }
		<br><br>
		作者: ${book.author }
		<br><br>
		价格: ${book.price }
		<br><br>
		出版时间: ${book.publishingDate }
		<br><br>
		评价: ${book.remark }
		<br><br>
		
		<a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
		
	</center>

</body>
</html>