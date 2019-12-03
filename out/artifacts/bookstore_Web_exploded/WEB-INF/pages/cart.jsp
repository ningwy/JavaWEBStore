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
<script type="text/javascript">
	
	$(function(){
		$(".delete").click(function(){
			
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除" + title + "的信息吗?");
			
			if(flag){
				return true;
			}
			
			return false;
		});
		
		//ajax 修改单个商品的数量:
		//1. 获取页面中所有的 text, 并为其添加 onchange 响应函数. 弹出确认对话框: 确定要修改吗? 
		$(":text").change(function(){
			var quantityVal = $.trim(this.value);

			var flag = false;
			
			var reg = /^\d+$/g;
			var quantity = -1;
			if(reg.test(quantityVal)){
				quantity = parseInt(quantityVal);
				if(quantity >= 0){
					flag = true;
				}
			}
			
			if(!flag){
				alert("输入的数量不合法!");
				$(this).val($(this).attr("class"));
				return;
			}
			
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());

			if(quantity == 0){
				var flag2 = confirm("确定要删除" + title + "吗?");
				if(flag2){
					//得到了 a 节点
					var $a = $tr.find("td:last").find("a");
					//执行 a 节点的 onclick 响应函数. 
					$a[0].onclick();
					
					return;
				}
			}
			
			var flag = confirm("确定要修改" + title + "的数量吗?");
			
			if(!flag){
				$(this).val($(this).attr("class"));
				return;
			}
			//2. 请求地址为: bookServlet
			var url = "bookServlet";
			
			//3. 请求参数为: method:updateItemQuantity, id:name属性值, quantity:val, time:new Date()
			var idVal = $.trim(this.name);
			var args = {"method":"updateItemQuantity", "id":idVal, "quantity":quantityVal, "time":new Date()};
			
			//4. 在 updateItemQuantity 方法中, 获取 quanity, id, 再获取购物车对象, 调用 service 的方法做修改
			//5. 传回 JSON 数据: bookNumber:xx, totalMoney
			
			//6. 更新当前页面的 bookNumber 和 totalMoney
			$.post(url, args, function(data){
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;
				
				$("#totalMoney").text("总金额: ￥" + totalMoney);
				$("#bookNumber").text("您的购物车中共有" + bookNumber + "本书");
			},"JSON");
			
		});
				
	})
	
</script>

</head>
<body>

	<div><img src="${ pageContext.request.contextPath }/static/fudan_logo.png" alt="" width="80" height="80"></div>
	<center>
		<br>
		<br>
		
		<span id="bookNumber">您的购物车中共有${sessionScope.shoppingCart.bookNumber }本书:</span>
		<br>
		<br>
		
		<table>
			<tr>
				<th>书名</th>
				<th>数量</th>
				<th>价格</th>
				<th>&nbsp;</th>
			</tr>
		<c:forEach items="${sessionScope.shoppingCart.items }" var="item">
		
			<tr>
				<td>${item.book.title }</td>
				<td>
					<input type="text" size="1" name="${item.book.id }" value="${item.quantity }" />
				</td>
				<td>${item.book.price }</td>
				<!-- bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id } -->
				<td>
					<a href="bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id }" 
					class="delete">删除</a>
				</td>
			</tr>
		
		</c:forEach>
		
			<tr>
				<td colspan="4" id="totalMoney">总金额: ￥ ${sessionScope.shoppingCart.totalMoney }</td>
			</tr>
			
			<tr>
				<td><a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a></td>
				<td><a href="bookServlet?method=clearCart">清空购物车</a></td>
				<td><a href="bookServlet?method=fordward&page=cash">结账</a></td>
			</tr>
			
		</table>
	
	</center>

</body>
</html>