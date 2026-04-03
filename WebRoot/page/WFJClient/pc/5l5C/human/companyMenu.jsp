<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>公司菜单管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/companyMenu.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
	<script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/menu/companyMenu.js"></script>
</head>
<body class="body">
<header>
	<ul class="clearfix">
		<li>
			<a onclick="window.history.go(-1);return false;" target="_self">
				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
			</a>
		</li>
		<li>
			公司菜单管理
		</li>
		<li>
		</li>
	</ul>
</header>

<div class="content">
	<section class="sec-list">
		<div class="dtd-oa-search-bar">
			<div class="dtd-oa-search-bar-icon-wrapper">
				<i class="layui-icon" >&#xe615;</i>
			</div>
			<input type="text" class="dtd-oa-search-bar-input" placeholder="搜索：公司名称"  name="companyName" id="companyName" autocomplete=“off”/>
			<div class="dtd-oa-search-bar-icon-wrapper close-company" style="left:90%">
				<i class="layui-icon" >&#x1006;</i>
			</div>
		</div>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show div-company">
				<ul class="company-ul">
				</ul>
			</div>
		</div>
	</section>
</div>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
</body>
</html>
