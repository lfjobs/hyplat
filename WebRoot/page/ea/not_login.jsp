<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>没有登录</title>
		<script>
		alert("该帐号已经失效或者在其他地方登录！请注意保护好密码")
		parent.parent.parent.parent.parent.window.location.href= "<%=basePath%>page/ea/index.jsp";
		</script>
	</head>
	<body>
	</body>
</html>
