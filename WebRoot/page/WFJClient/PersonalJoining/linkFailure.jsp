<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
			
%>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>链接失效</title>
	<script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/ea/production/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/base.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/production/cprocedure/scmanage/swiper-3.3.1.min.js"></script>
	<style> 
		body{ text-align:center} 
		.div{ margin:20% auto;} 
		p{margin:0 auto;}
	</style> 
</head>

<body>
	<!-- header 开始  -->
    <header class="com_head">
        <a href="javascript:history.go(-1);;" class="back" style="display: none;"></a>
        <h1>该链接已失效</h1>
    </header>
    <!--  header 结束  -->
	<div class="div">
		<img alt="" src="<%=basePath %>/images/linkFailure.png">
		<p>该链接已失效</p>
	</div>
</body>
<script type="text/javascript">
	var back = '${back}';
	if(back!='1'){
		$(".back").show();
	}
</script>
</html>
