<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
    <title>成品出库单</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/production/finishproduct/finishedProduct_list.js"  type="text/javascript" ></script>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var id="";
	var dtnumber=0;
	var goodsId="";
	var status="";
	var fiveClear="${fiveClear}";
	var category="${category}";
	var fiveClearName="${fiveClearName}";
</script>
<style type="text/css">
.fieldName{
	text-align:right;
	width: 80px;
}
.goodsOutOfTheLibrary tr{
	height:30px;
}
</style>
  </head>
  
  <body>
  	 <div  id="main_main" class="main_main"> 	<div id="fexdiv">
	  	<table class="fexlist">
	  		<thead>
	  			<tr>
	  				<th width="30" align="center">选择</th>
	  				<th width="130" align="center">出库编号</th>
	  				<th width="60" align="center">仓库编号</th>
	  				<th width="60" align="center">仓库名称</th>
	  				<th width="100" align="center">仓库地址</th>
	  				<th width="60" align="center">单据状态</th>
	  				<th width="80" align="center">出库人</th>
	  				<th width="80" align="center">负责人</th>
	  				<th width="80" align="center">单据时间</th>
	  			</tr>
	  		</thead>
	  		<tbody>
	  				<c:forEach items="${pageForm.list}" var="l">
	  					<tr name="dps" class="dps" id="${l[0]}page">
	  						<td><input type="radio" name="radio" class="radio" id="${l[0]}"></td>
	  						<td><span>${l[1]}</span></td>
	  						<td><span>${l[2]}</span></td>
	  						<td><span>${l[4]}</span></td>
	  						<td><span>${l[5]}</span></td>
	  						<td><c:if test="${l[6]=='22'}"><span>未出库</span></c:if>
	  							   <c:if test="${l[6]=='16'}"><span>已出库</span></c:if></td>
	  						<td><span>${l[8]}</span></td>
	  						<td><span> ${l[10]} </span></td>
	  						<td><span>${l[11]}</span></td>
	  					</tr>
	  				</c:forEach>
	  		</tbody>
	  	</table>
  	</div>	</div>
	<form id="forms" method="post" name="forms">
		<input type="submit" id="submits" name="submits" style="display: none;">
	</form>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/finished/ea_getHomePageData.jspa?pageNumber=${pageNumber}&fiveClear=${fiveClear}&category=${category}">
		</c:param>
	</c:import>	
		<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
  </body>
</html>
