<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
  

  </head>
  
 <body>
 <br> <br> <br>
	<center>选择大学:<input type="text" name="school" id="school-name" value="请选择" onclick="pop('choose-box-wrapper')"></center>
	<div id="choose-box-wrapper" class="popMain">
	  <div class="choose-box">
		<div class="choosetitle">
			<span>选择科目</span>
		</div>
		<div class="chooseborder">
		
		
		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('choose-box-wrapper')" value="关闭" />
		</div>
	  </div>
	</div>
</div>
</body>
</html>

