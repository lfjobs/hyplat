<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<title>班值分配管理</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/cprocedure/duty_list.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var token = 1;
var paid="";
var fiveClear="${fiveClear}";
</script>
</head>
<body>
    
	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">
		
		<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>
		班值分配管理&nbsp;&nbsp;&nbsp;项目产品编号：<input type="text"
			style="width:90px;height:18px;" name="onDuty.productcode"/>&nbsp;项目产品名称：<input type="text" name="onDuty.productname" style="width:90px;height:18px;" />
		<input
			type="button" class="input-button" value="  查询  "  id="tosearch" style="margin:0px;margin-left:5px;"/>
			<input
			type="hidden" value="search" name="search"/>
			<input type="hidden" id="paid" name="onDuty.dutyid"/>
		</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="170" align="center">项目产品编号</th>
					<th width="100" align="center">项目产品名称</th>
					<th width="100" align="center">开始时间</th>
					<th width="100" align="center">结束时间</th>
					<th width="100" align="center">分配责任人</th>
					<th width="100" align="center">班值类型</th>
					<th width="100" align="center">分配时间</th>
					<th width="100" align="center">职责</th>
					<th width="100" align="center">备注</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${dutyid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${dutyid}" /></td>

						<td><%=number%></td>
						<td><span id="productcode">${productcode}</span></td>
						<td><span id="productname">${productname}</span></td>
						<td><span id="startdate">${fn:substring(startdate,0,10)}</span></td>

						<td><span id="enddate">${fn:substring(enddate,0,10)}</span></td>

						<td><span id="staffname">${staffname}</span></td>
						<td><span id="dutytype">${dutyType}</span></td>
						<td><span id="allotdate">${fn:substring(allotdate,0,10)}</span></td>

						<td><span id="duty">${duty}</span></td>
						<td><span id="comments">${comments}</span></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/duty/ea_getSearchs.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>