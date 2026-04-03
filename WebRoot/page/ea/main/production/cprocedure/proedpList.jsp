<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设备分配管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/proedpList.js"></script>
<script>
 var basePath = "<%=basePath%>";
	var pedId = "";
	var ppageNumber = '${pageNumber}';
	var search = '${search}';
	var token = 0;
	var prodctSn = "${prodctSn}";
	var goodsName = "${goodsName}";
	var fiveClear="${fiveClear}";
</script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="50" align="center">序号</th>
					<th width="100" align="center">项目产品编号</th>
					<th width="100" align="center">项目名称</th>
					<th width="100" align="center">开始时间</th>
					<th width="100" align="center">结束时间</th>
					<th width="100" align="center">分配责任人</th>
					<th width="100" align="center">职 责</th>
					<th width="180" align="center">设备分配</th>
					<th width="180" align="center">所属场地</th>
					<th width="170" align="center">分配时间</th>
					<th width="100" align="center">分配备注</th>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list" status="idx">
					<tr id="${pedId}" class="fieClass">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${pedId}" />
							
							</td>
						<td><span>${idx.index+1 }</span></td>
						<td><span id="prodctSn">${prodctSn}</span></td>
						<td><span id="goodsName">${goodsName}</span></td>
						<td><span id="pedStartDate">${fn:substring(pedStartDate,0,10)}</span></td>
						<td><span id="pedEndDate">${fn:substring(pedEndDate,0,10)}</span></td>
						<td><span id="staffName">${staffName}</span></td>
						<td><span id="duty">${duty}</span></td>
						<td><span id="devices">${devices}</span></td>
						<td><span id="fie"></span></td>
						<td><span id="distDate">${fn:substring(distDate,0,19)}</span></td>
						<td><span id="distRemark">${distRemark}</span></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/proedpdist/ea_findList.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>

	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>