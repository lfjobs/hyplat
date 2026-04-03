<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
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
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <body>
	<div class="floorNav fexBottom">
	    <div><a id="home" href="javascript:;" target="_parent"><img alt="" title=""  src="<%=basePath %>images/WFJClient/home.png"/> </a> </div>
	    <div><a id="shopCart" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/shopCart.png"/> </a> </div>
	    <div><a id="me" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/me.png"/> </a> </div>
	    <div><a id="ordersearch" href="<%=basePath%>/page/WFJClient/GoodsShow/searchOdr.jsp" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/searchOdr.png"/></a></div>
	</div>
	<script>
	document.getElementById("home").onclick=function(){
				window.history.go(0);
			}
	</script>
  </body>
</html>