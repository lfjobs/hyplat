<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>流程设计</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />	
<script src="<%=basePath%>js/ea/production/adesign/processdesign.js" type="text/javascript"></script>
<script type="text/javascript">
var search = "${search}";
var basePath="<%=basePath%>";
var ppageNumber = "${pageNumber}";
var fiveClear="${fiveClear}";
var category="${category}";
var maid="";
</script>

</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<th width="50" align="center">选择</th>
				<th width="40" align="center">序号</th>
				<th width="180" align="center">产品名称</th>
				<th width="180" align="center">产品编号</th>
				<th width="100" align="center">数量</th>
				<th width="100" align="center">单价</th>
				<th width="100" align="center">金额</th>		
			</thead>
			<tbody>
				<c:forEach items="${pageForm.list}" var="l" varStatus="a">
					<tr id="${l.ppID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${l.ppID}" />
						</td>
						<td>${a.index+1}</td>
						<td>${l.goodsName}</td>
						<td>${l.goodsNum}</td>
						<td>${l.price}</td>
						<td>${l.quantity}</td>
						<td>${l.money}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/prodesign/ea_getProductPage.jspa?category=01&fiveClear=${fiveclear}&pageNumber=${pageNumber}">
		</c:param>
	</c:import>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>