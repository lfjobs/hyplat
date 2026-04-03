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
<title>项目预算</title>  
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var pagenumber=0;
var pagecount=0;
var t;
var user='${user}';
var companyId='${companyId}';
var cashierBillsID='${cashierBillsID}';
var state='${state}';
</script>
<body onload="loaded()">
</body>
</html>