<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文流转顶部导航栏</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
			<link rel="stylesheet" type="text/css" href="<%=basePath%>css/websuitMini/websuit.css" />
</head>

<body>
<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">5L5C办公系统</div>
   <div id="topbar_back" ontouchstart="" style="display: block;"><a href="#" data-reveal-id="myModal"><img src="<%=basePath%>images/websuitMini/button_back.png" onclick="history.back()"></a>	</div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""><img src="<%=basePath%>images/websuitMini/Up.png"/></div>
</div>
</body>
</html>
