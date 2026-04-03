<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />   
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" /> 
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
	
<base href="<%=basePath%>">
    <title>新成功页面展示订单信息</title>
<script>
var basePath="<%=basePath%>";
function fan(){
	document.location.href=basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
}
</script>
  </head>
  <body>
  
  <div style="text-align:left;margin-left:5px;margin-right:5px;">
  <img src="<%=basePath %>images/WFJClient/Newjspim/auditing.png"  style="margin-top:10%;margin-left:30%;width:50%;margin-bottom:10%;"/><br />

   <span>订单号:${ddid}</span><br/>
    <span>金额:${cashier.priceSub}</span><br/>
    <span>交易日期:<fmt:formatDate value="${cashier.boxDate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><br/>
    <input type="button" value="继续购物" id="fan" onclick="fan()" style="margin-left:40%;margin-top:10%;"/>
 </div>

  </body>
</html>
