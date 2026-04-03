<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'EnterpriseStampSave.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/ea/organization.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
		//关闭本窗口
	function CloseWindowFrame(){
		window.history.back();
	}
		
	</script>
	</head>

	<body>
		<object classid="clsid:8E501809-AB3F-44af-90DD-8BDA897EE069"
			width="800" height="800" id="helloBossma">

			<param name="Userid" value="${account.accountID}" />
			<param name="Companyid" value="${account.companyID}" />
			<param name="Serverpath"
				value="<%=request.getServerName() + ":"
					+ request.getServerPort()%>">
			<param name="Contextpath" value="<%=request.getContextPath()%>">
			<param name="username" value="${account.accountName}">
			<param name="JSessionId" value="<%=session.getId()%>">
			<param name="orgid"
				value="<%=session.getAttribute("organizationID")%>">
		</object>
	</body>
</html>
