<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>System Error</title>
  </head>
  
  <body>
  	该账户没有绑定员工或未分配员工工种</br>请在后台管理下的帐号管理中给该帐号绑定员工并且分配该员工工种
  </body>
</html>
