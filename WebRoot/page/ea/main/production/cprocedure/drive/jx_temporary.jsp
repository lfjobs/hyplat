<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>暂时作为跳转页面使用(不正式发布)</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/contacts/Restaurant/style12.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/drive/index.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
</head>

<body>
	<div>
		<a href="<%=basePath%>ea/carSchool/ea_pageSkip.jspa?skipString=inform" >驾校通知</a>
		<a href="<%=basePath%>ea/carSchool/ea_goldInform.jspa" >学车赚钱</a>
		<a href="<%=basePath%>ea/carSchool/ea_pageSkip.jspa?skipString=recommendFriend" >推荐好友</a>
		<a href="<%=basePath%>ea/carSchool/ea_pageSkip.jspa?skipString=regulations" >车辆违章</a>
		<!-- 因教练日志暂无页面暂停点击  -->
		<%-- <a href="<%=basePath%>ea/carSchool/ea_pageSkip.jspa?skipString" >教练日志</a> --%>
	</div>
</body>
</html>
