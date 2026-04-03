<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>招聘职位</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link href="<%=basePath%>css/WFJClient/zhaopin.css" rel="stylesheet" />
  </head>
  
  <body>
     <div class="Jobs_Reception">
        <ul>
            <li class="title">人才招聘</li>
        </ul>
        <s:iterator value="beans" id="arr">
	            <a href="<%=basePath%>/ea/wxrecruit/ea_getPositionItem.jspa?recID=${arr[0]}">
	        <div class="divPic">
	            	<img src="<%=basePath%>images/jobs_arrows_03.png" /><label>${arr[1] }</label>
	        </div>
	            </a>
        </s:iterator>       
  </body>
</html>
