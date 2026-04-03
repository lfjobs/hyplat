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
		<title>没有权限</title>
	<script type="text/javascript">
		  var no_authority = parent.token; 
		  if(no_authority == 11)parent.document.location.href="<%=basePath%>page/ea/no_authority.jsp";
		  if(no_authority == 2)parent.document.location.href="<%=basePath%>page/ea/no_authority.jsp";
		  if(no_authority == 1)parent.document.location.href="<%=basePath%>page/ea/no_authority.jsp";
	</script>
	</head>
	<body>
		<form method="post" action="<%=basePath%>plat/login.jspa">
			<span>您没有权限进行此操作，可能是此用户没有该权限或者该功能已经欠费</span>
		</form>
	</body>
</html>
