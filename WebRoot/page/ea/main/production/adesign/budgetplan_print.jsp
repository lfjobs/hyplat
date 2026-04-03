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
<title><c:if test="${type==01}">产品生产量预算打印预览</c:if><c:if test="${type==02}">生产量日周月季年计划打印预览</c:if></title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 12px;
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
				<h2><c:if test="${type==01}">产品生产量预算</c:if><c:if test="${type==02}">生产量日周月季年计划</c:if></h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
			<thead>
				<tr class="tablewith">
					<th  align="center">序号</th>
					<th  align="center">行业分类</th>
				    <th  align="center">产品条码</th>
					<th  align="center">产品编号</th>
					<th  align="center">产品名称</th>
					<th  align="center">流水线设备套数</th>
					<th  align="center">单套设备最大用人量</th>
					<th  align="center">单设备每小时最大生产量</th>
					<th  align="center">设备每天工作时间</th>
					<th  align="center">数量</th>
					<th  align="center">金额</th>
					<th  align="center">日最大生产量</th>
					<th  align="center">周最大生产量</th>
					<th  align="center">月最大生产量</th>
					<th  align="center">季最大生产量</th>
					<th  align="center">年最大生产量</th>
					<th  align="center">计划年份</th>
					<th  align="center">制定日期</th>
					

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
					<tr id="${bpid}">
						
						<td align="center"><%=number%></td>
						
						<td align="center"><span id="tradeCode">${tradeCode}</span></td>
                        <td align="center"><span id="barCode">${barCode}</span></td>
						<td align="center"><span id="productCode">${productCode}</span></td>
						<td align="center"><span id="productName">${productName}</span></td>
						<td align="center"><span id="deviceNum">${deviceNum}</span></td>

						<td align="center"><span id="maxBydevice">${maxBydevice}</span></td>
						<td align="center"><span id="maxByhbyd">${maxByhbyd}</span></td>
						<td align="center"><span id="worktimeByd">${worktimeByd}</span></td>
						<td align="center"><span id="quantity">${quantity}</span></td>
						<td align="center"><span id="money">${money}</span></td>
						<td align="center"><span id="maxDay">${maxDay}</span></td>
						<td align="center"><span id="maxWeek">${maxWeek}</span></td>
						<td align="center"><span id="maxMonth">${maxMonth}</span></td>
						<td align="center"><span id="maxSeason">${maxSeason}</span></td>
						<td align="center"><span id="maxYear">${maxYear}</span></td>
						<td align="center"><span id="year">${year}</span></td>
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
