<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>财务库存盘点</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
<script type="text/javascript">
	$(function(){
		$(".dds").each(function(){
		var n=parseInt($(this).find("td").eq(6).attr("name"));
		var s=parseInt($(this).find("td").eq(6).val());
		var str="";
		if(n<=10000){
			if(s<5000)
				$(this).find("font").attr("color","red").text("5000");
			else if(s<8000)
				$(this).find("font").attr("color","yellow").text("5000");
			else
				$(this).find("font").attr("color","green").text("5000");
		}else if(n>10000&&n<=1000000){
			if(s<n*0.3)
				$(this).find("font").attr("color","red").text(parseInt(n*0.3));
			else if(s<n*0.4)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.3));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.3));
		}else if(n>1000000&&n<10000000){
			if(s<n*0.2)
				$(this).find("font").attr("color","red").text(parseInt(n*0.2));
			else if(s<n*0.3)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.2));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.2));
		}else{
			if(s<n*0.15)
				$(this).find("font").attr("color","red").text(parseInt(n*0.15));
			else if(s<n*0.25)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.15));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.15));
		}
	});
	})
</script>
  </head>
  
  <body>
  <center>
  	<div style="height: 50px;">
  		<span style="position: relative; top: 10px;"><font size="5" style="font-weight:bold;">财务库存盘点(${datte})</font></span>
  	</div>
    <div>
    	<table class="table">
    		<tr height="25">
    			<th width="50px" align="center">序号</th>
    			<th width="90px" align="center">物品名称</th>
    			<th width="90px" align="center">物品类别</th>
    			<th width="90px" align="center">上月库存量</th>
    			<th width="90px" align="center">本月入库量</th>
    			<th width="90px" align="center">本月出库量</th>
    			<th width="90px" align="center">当前库存量</th>
    			<th width="90px" align="center">库存预警值</th>
    			<th width="90px" align="center">库存位置</th>
    		</tr>
    		<c:forEach items="${list}" var="l" varStatus="a">
    			<tr class="dds">
    				<td>${a.index+1}</td>
    				<td>${l[0]}</td>
    				<td>${l[1]}</td>
    				<td>${l[2]}</td>
    				<td>${l[3]}</td>
    				<td>${l[4]}</td>
    				<td name="${l[5]}" class="str">${l[6]}</td>
    				<td><font></font></td>
    				<td>${l[7]}</td>
    			</tr>
    		</c:forEach>
    	</table>
    </div>
  </body>
</html>
