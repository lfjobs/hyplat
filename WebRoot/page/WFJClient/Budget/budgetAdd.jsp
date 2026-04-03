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
<title>项目预算-添加</title>
<style type="text/css">
  #prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}
</style>
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var reg=new RegExp(/^\d+(\.\d+)?$/);//验证价格
</script>
<body>
	<header>
		<p>
			<a
				href="<%=basePath %>/ea/wfjbudget/ea_toBudget.jspa?user=${user}&companyId=${companyId}"><img
				src="<%=basePath%>images/WFJClient/budget/back_03.png" alt="" /></a>
		</p>
		<span>管理项目预算</span>
		<p id="done">完成</p>
	</header>
	<form name="budgetForm" action="<%=basePath %>ea/wfjbudget/ea_saveBudget.jspa?" method="post"
	onkeydown="if(event.keyCode==13||event.which==13)return false;" >
	<input type="submit" name="submit" style="display:none;"/>
	<input type="hidden" name="user" value="${user }"/>
	<input type="hidden" name="companyId" value="${companyId }"/>
	<input type="hidden" name="content" id="content" value=""/>
	<div class="main">
		<div class="main_hide">
			<ul class="list-group">
				<li class="list-group-item big_list">
					<div class="pull-left img_div1">
						<img src="<%=basePath%>images/WFJClient/budget/ico_03_03.png"
							alt="" class="img-responsiv center-block" />
					</div>
					<ul class="list-group pull-left small_list">
						<li class="list-group-item">
							<div class="pull-left">
								<input id="projectName" name="projectName" placeholder="项目名称"/>
							</div>
						</li>

					</ul>
				</li>
			</ul>
			<div class="morechan"></div>
			<ul class="list-group addmore">
				<li class="list-group-item big_list">
					<div class="pull-left img_div1">
						<img src="<%=basePath%>images/WFJClient/budget/ico_06_06.png"
							alt="" class="img-responsiv center-block" />
					</div>
					<ul class="list-group pull-left small_list">
						<li class="list-group-item">
							<div class="pull-left" style="color: #f74c31;">添加新产品</div>
						</li>

					</ul>
				</li>
			</ul>
		</div>
	</div></form>
	<div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
	<footer style="background: #3B3B3B;">
		<div class="footer_div">
			<span>总计：</span><span id="total"></span><span>元</span>
		</div>
	</footer>
</body>
</html>