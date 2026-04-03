<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<s:head theme="xhtml" />
		<sx:head />


		<title>My JSP 'userlist.jsp' starting page</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<LINK href="<%=basePath%>/jsp/css/admin.css" type="text/css"
			rel="stylesheet" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<br>
		<h1 align="center">
			<b>通话记录查询</b>
		</h1>
		<hr>
		<form action="queryUserTelRec.do" method="post">
			&nbsp;&nbsp;&nbsp;&nbsp; 查询日期：
			<input type="text" name="startdate" onfocus="date(this)">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="查询" align="right">
		</form>
	</body>
</html>
