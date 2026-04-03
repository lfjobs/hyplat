<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>error</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
	  alert("${message}");
	  if(parent.notoken != "undefined"){
	    parent.notoken = 0;
	  }
	  if(parent.token == 1||parent.token==11){
		    parent.re_load();
	  }
	  if(parent.token == 2){
	  parent.re_load();
	  }
	</script>
  </head>
  
  <body>
  <span>${message}</span>
  </body>
</html>
