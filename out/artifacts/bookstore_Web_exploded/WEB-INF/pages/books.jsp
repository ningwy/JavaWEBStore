<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WEB BOOKSTORE</title>

<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-2.1.4.js"></script>
<script type="text/javascript">

	$(function() {
		
		$("#pageNo").change(function() {
			
			var val = $(this).val();
			val = $.trim(val);
			var reg = /^\d+$/g;
			var flag = false;
			var pageNo = 0;
			if (reg.test(val)) {
				pageNo = parseInt(val);
				if (pageNo >= 1 && pageNo <= parseInt("${page.totalPageNumber}")) {
					flag = true;
				}
			}
			
			if (!flag) {
				alert("输入的页码不合法，请重新输入！");
				$(this).val("");
				return;
			}
			
			var href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
			window.location.href = href;
			
		});
		
	});

</script>
<%@ include file="/commons/queryCondition.jsp" %>

</head>
<body>

	<div><img src="${ pageContext.request.contextPath }/static/fudan_logo.png" alt="" width="80" height="80"></div>
	<center>
	
		<c:if test="${param.title != null }">
			加入购物车成功
		</c:if>
		<br><br>
	
		<c:if test="${!empty sessionScope.shoppingCart.books }">
			您的购物车中有${sessionScope.shoppingCart.bookNumber }本书,<a href="bookServlet?method=fordward&page=cart&pageNo=${page.pageNo }">查看购物车</a>
		</c:if>
	
		<br><br>
		<form action="bookServlet?method=getBooks" method="post">
			price:
			<input type="text" size="1" name="minPrice" />-
			<input type="text" size="1" name="maxPrice" />
			
			<input type="submit" value="搜索" />
		</form>
		
		<br><br>
		
		<table cellpadding="10">
			
			<tr>
				<th>信息</th>
				<th>价格</th>
				<th>购买</th>
			</tr>
		
			<c:forEach items="${page.list }" var="book">
				<tr>
					<td>
						<a href="bookServlet?method=getBook&pageNo=${page.pageNo }&id=${book.id }">${book.title }</a>
						<br>
						${book.author }
					</td>
					<td>${book.price }</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${page.pageNo }&id=${book.id }&title=${book.title }">加入购物车</a></td>
				</tr>
			</c:forEach>
		
		</table>
		<br>
		<br>
		总共有 ${page.totalPageNumber } 页
		&nbsp;&nbsp;
		当前是第 ${page.pageNo } 页
		&nbsp;&nbsp;
		<c:if test="${page.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${page.prevPage }">上一页</a>
		</c:if>
		
		&nbsp;&nbsp;
		
		<c:if test="${page.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${page.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${page.totalPageNumber }">末页</a>
		</c:if>
		
		&nbsp;&nbsp;
		
		转到第 <input type="text" size="1" id="pageNo" />页
	
	</center>

</body>
</html>