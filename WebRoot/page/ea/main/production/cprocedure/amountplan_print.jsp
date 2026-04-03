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
<title>计划生产量打印预览</title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 13px;
}

.td {
	border: #cccccc 1px solid;
	white-space:nowrap;
	word-break:keep-all;
}
</style>
</head>
<body>
	<table width="99%" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>计划生产量</h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
		<thead>
				<tr class="tablewith">
					<th width="50" align="center">序号</th>
					<th width="130" align="center">行业</th>
					<th width="130" align="center">产品编号</th>
					<th width="130" align="center">产品名称</th>
					<th width="130" align="center">结构数量</th>
					<th width="200" align="center">项目产品计划生产量（个/时）</th>
					<th width="100" align="center">每日工作时间</th>
					<th width="120" align="center">日生产量</th>
					<th width="100" align="center">负责人</th>
					<th width="150" align="center">制定日期</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
					<tr>
						<td align="center"><%=number%></td>
						<td align="center"><span id="tradeCode">${tradeCode}</span></td>
						<td align="center"><span id="productCode">${productCode}</span></td>
						<td align="center"><span id="productName">${productName}</span></td>

						<td align="center"><span id="quantity">${quantity}</span></td>

						<td align="center"><span id="planProductNum">${planProductNum}</span></td>
						<td align="center"><span id="hours">${hours}</span></td>
						<td align="center"><span id="dayProductNum">${dayProductNum}</span></td>

						<td align="center"><span id="dutorName">${dutorName}</span></td>
						<td align="center"><span id="createDate">${fn:substring(createDate,0,10)}</span></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>

	</table>
</body>
</html>
