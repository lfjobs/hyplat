<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>跳转页面</title>
     <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache"s>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%-- <script type="text/javascript" src="<%=basePath %>js/WFJClient/topMore.js"></script> --%>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
	</script>
  </head>
  
  <body>
  
  </body>
  <script type="text/javascript">
  
  $(function()
  {
  var  number="${filepahe}";
  var priceSum="${fileContentType}";
  var name ="${userfile}";
  
  
	_AP.pay(encodeURI(encodeURI(basePath+"page/WFJClient/CustomerOrder/wfjAlipay.jsp?WIDout_trade_no="+number+"&WIDtotal_fee="+priceSum+"&WIDsubject="+name)));
				
  
  })
  
 </script>
</html>
