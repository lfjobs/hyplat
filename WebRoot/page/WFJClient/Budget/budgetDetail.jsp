<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
<link rel="stylesheet" href="<%=basePath %>css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>css/WFJClient/budget.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<script src="<%=basePath %>js/bootstrap.js"></script>
<script src="<%=basePath %>js/ea/marketing/wfjeshop/budget.js"></script>
    <title></title>
</head>
<body onload="detailLoaded()">
<script type="text/javascript">
var user='${user}';
var companyId='${companyId}';
var basePath='<%=basePath%>';
var cashierBillsID='${cashierBillsID}';
</script>
<header>
    <p><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/WFJClient/budget/back_03.png" alt=""/></a></p>
 	<span></span>
    <p><a href="<%=basePath%>ea/wfjbudget/ea_toBudget.jspa?user=${user}&companyId=${companyId}">项目管理</a></p>
</header>

	<div class="main">
		<div class="main_hide">
		</div>
	</div>
	<footer>
		<ul>
			<li>
				<p class="total"></p>
				<p>预算</p>
			</li>
			<li>
				<p class="real" style="color:#4EB35A"></p>
				<p style="color:#4EB35A">实际</p>
			</li>
			<li onclick="joinCart()">
				<p>一键加入</p>
				<p>购物车</p>
			</li>
		</ul>
	</footer>
	<form id="cartForm" style="display:none;" action="<%=basePath %>ea/wfjbudget/sajax_ea_oneKeyJoinCart.jspa?" method="post">	
        	<input type="hidden" value="${user }" name="user"/>
        	<input type="hidden" value="${goodsBillsID }" name="goodsBillsID"/>
        	<input type="hidden" value="${cashierBillsID }" name="cashierBillsID"/>
        	<input type="hidden" value="${companyId }" name="companyId"/>
        	<input type="hidden" value="" name="ppId" id="ppids"/>
	</form>
</body>
</html>