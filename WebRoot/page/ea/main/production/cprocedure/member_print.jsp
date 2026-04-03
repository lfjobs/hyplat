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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员分配打印预览</title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 14px;
}

.td {
	border: #cccccc 1px solid;
}
</style>
</head>
<body>
	<table width="99%" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>人员分配</h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
		<thead>
				<tr class="tablewith">
					<th width="40" align="center">序号</th>
					<th width="150" align="center">项目产品编号</th>
					<th width="100" align="center">项目产品名称</th>
					<th width="100" align="center">开始时间</th>
					<th width="100" align="center">结束时间</th>
					<th width="100" align="center">分配负责人</th>
					<th width="100" align="center">交接人</th>
					<th width="100" align="center">接班人</th>
					<th width="100" align="center">分配时间</th>
					<th width="100" align="center">职责</th>
					<th width="170" align="center">备注</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
					<tr>
						<td align="center"><%=number%></td>
						<td align="center"><span id="productCode">${productCode}</span></td>
						<td align="center"><span id="productName">${productName}</span></td>
						<td align="center"><span id="startDate">${fn:substring(startDate,0,10)}</span></td>
						<td align="center"><span id="endDate">${fn:substring(endDate,0,10)}</span></td>
						<td align="center"><span id="allotorName">${allotorName}</span></td>
						<td align="center"><span id="transferName">${transferName}</span></td>
						<td align="center"><span id="areceiverName">${areceiverName}</span></td>
						<td align="center"><span id="allotDate">${fn:substring(allotDate,0,10)}</span></td>
						<td align="center"><span id="duty">${duty}</span></td>
						<td align="center"><span id="remark">${remark}</span></td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
	</table>
</body>
</html>
