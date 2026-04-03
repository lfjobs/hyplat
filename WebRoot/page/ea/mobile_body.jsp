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
		<title>My JSP 'mobile.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
function changebody(interfaceUrl,menuName){
  document.location.href="<%=basePath%>"+interfaceUrl+".jspa";
}
</script>
	</head>

	<body>

		<ul>
			<li class="txt01 sinterface"
				onclick='changebody("/ea/logbook/ea_getListLogBook","得到个人日志列表")'>
				得到个人日志列表
			</li>
		</ul>
		<li class="txt01 sinterface"
			onclick='changebody("/ea/email/ea_getEmailList","个人邮件")'>
			个人邮件
		</li>

	</body>
</html>
