<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>下载</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/WFJClient/download_app.css">
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<bdoy>
	<div class="download_app">
		<header class="clearfix">
			<menu class="clearfix">
				<li>
					<a href="${param.redirectUrl}"><img src="<%=basePath%>images/unmannedsupermarket/img_1_03.png"/></a>
				</li>
				<li>
					<a>扫码下载APP</a>
				</li>
			</menu>
		</header>
		<div class="box clearfix">
			<div>
				<img src="<%=basePath%>images/WFJClient/zhuce/download_app_01.png"/>
			</div>
			<section>
				<div>
					<img src="<%=basePath%>images/WFJClient/zhuce/download_app_02.png"/>
				</div>
				<div>
					<img src="<%=basePath%>images/WFJClient/zhuce/ewfzd.png" alt="" />
					<p>扫一扫，注册并下载数字地球APP</p>
				</div>
			</section>
		</div>
	</div>
</bdoy>
</html>


