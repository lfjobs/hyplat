<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.CAccount"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	CAccount account = (CAccount) session.getAttribute("account");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>电子印章实例</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/jqueryui/ui.core.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/jqueryui/ui.draggable.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/signApp/signatureApp.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/js/signApp/signatureApp.css">
		<script type="text/javascript">
			var signparam={
			//取得印章列表
			"SignListUrl":"<%=basePath%>ea/signmanager/signListByAccount.jspa",
			//账户id
			"accountid":"<%=account.getAccountID()%>",
			//关联的表
			"relationtable":"testtable",
			//关联表的id
			"relationid":"test000001",
			//盖章后提交
			"sendSignUrl":"<%=basePath%>ea/signmanager/signmanager/ea_InsertSignManager.jspa",
			"basePath":"<%=basePath%>",
			"defaultImage":"<%=basePath%>/js/signApp/defaultImage.jpg"
			
			};
			
		</script>
	</head>

	<body
		background="<%=basePath%>/images/ea/office/enterpriseStamp/company201009046vxdyzy4wg0000000025/01.jpeg">
		<input type="button" onclick="initSignature()" value="  盖章  " />

		<hr>

	</body>
</html>
