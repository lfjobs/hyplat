<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>场地分配打印预览</title>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	
<style	 type="text/css">
	.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}
</style>
  </head>
  
  <body>
  <center>
    <div style=" width: 650px;">
    	<div>
    		<span><font style="font-weight:bold;" size="3">场地分配打印单据</font></span>
    	</div>    	<br>
    	
    	<table class="table">
    			<thead>
    				<tr height="30">
			  	  		<th width="148" align="center">项目产品编号</th>
			  	  		<th width="130" align="center">项目产品名称</th>
			  	  		<th width="120" align="center">开始时间</th>
			  	  		<th width="120" align="center">结束时间</th>
			  	  		<th width="130" align="center">场地位置</th>
			  	  		<th width="130" align="center">分配责任人</th>
			  	  		<th width="100" align="center">分配时间</th>
			  	  		<th width="80" align="center">职责</th>
			  	  		<th width="80" align="center">备注</th>
    				</tr>
    			</thead>
    			 <tbody>
  	  		<c:forEach items="${list}" var="l">
  	  			<tr id="${l.fieldDistributionId}" height="20">
  	  				<td><span>${l.productCode}</span></td>
  	  				<td><span>${l.ppName}</span></td>
  	  				<td><span>${l.startTime}</span></td>
  	  				<td><span>${l.endTime}</span></td>
  	  				<td><span>${l.siteAddress}</span></td>
  	  				<td><span>${l.staffName}</span></td>
  	  				<td><span>${l.distributionTime}</span></td>
  	  				<td><span>${l.duty}</span></td>
  	  				<td><span>${l.remarks}</span></td>
  	  			</tr>
  	  		</c:forEach>
  	  </tbody>
		</table>
    </div>
    </center>
  </body>
</html>
