<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <title>生产场地分配</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/production/cprocedure/fieldDistribution_list.js"  type="text/javascript" ></script>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
		var basePath="<%=basePath%>";
		var id="";
		var category="${category}",grType="${type}";
		var fiveClear="${fiveClear}";
		</script>
  </head>
  
  <body>
  <div  id="main_main" class="main_main"> 	<div id="fexdiv">
  	<table class="fexlist">
  	  <thead>
  	  	<tr>
  	  		<th width="30" align="center">选择</th>
  	  		<th width="138" align="center">项目产品编号</th>
  	  		<th width="150" align="center">项目产品名称</th>
  	  		<th width="100" align="center">开始时间</th>
  	  		<th width="100" align="center">结束时间</th>
  	  		<th width="100" align="center">场地位置</th>
  	  		<th width="100" align="center">分配责任人</th>
  	  		<th width="80" align="center">分配时间</th>
  	  		<th width="80" align="center">职责</th>
  	  		<th width="80" align="center">备注</th>
  	  	</tr>
  	  </thead>
  	  <tbody>
  	  		<c:forEach items="${pageForm.list}" var="l">
  	  			<tr id="${l.fieldDistributionId}" class="dps">
  	  				<td><input type="radio" name="radio" class="radio"></td>
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
  </div>
  
	<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/fielddistr/ea_getHomePage.jspa?pageNumber=${pageNumber}&fiveClear=${fiveClear}">
		</c:param>
	</c:import>
		<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
  </body>
</html>
