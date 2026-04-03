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
<title>5L5C微办公公司员工联系汇总列表</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/websuitMini/pub.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style>
		
body{ background-color:#b2ebff;}
#topbar_back img{ width:14px;}
.con{width:360px; margin:auto;}
.contact_nav{ width:100%; height:60px; line-height:60px;border-bottom:1px dashed #ccc; background-color:#f0f0f0;}
.contact_nav label{ float:left; display:block; height:60px; line-height:60px; color:#4297b6; font-size:14px; font-weight:bold;}
.contact_nav .name{ width:100px; text-align:center;}
.contact_nav .iphone{ width:160px;}
.contact_nav a img{ float:left; width:38px; height:38px; margin-left:5px; margin-top:10px;}
</style>
</head>

<body style="margin-top: 63px;">
	<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">查询范围</div>
   <div id="topbar_back" ontouchstart="" style="display: block;" onclick="history.back()"><img src="<%=basePath%>images/websuitMini/button_back.png"/></div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""><img src="<%=basePath%>images/websuitMini/fdj_03.png"/></div>
</div>
<div class="con">
					<c:forEach var="arr" items="${comlist}" > 

                         <div class="contact_nav fl">
     <label class="name">${arr[4]}</label>
     <label class="iphone">${arr[15]}</label>
     <a href="tel:${arr[15]}"><img alt="" title=""  src="<%=basePath%>images/websuitMini/iphone.png"/></a>
     <a href="#"><img alt="" title="" src="<%=basePath%>images/websuitMini/email.png"/></a>
   </div>
   <div class="clear"></div>

                  </c:forEach> 

   
</div>
</body>
</html>
