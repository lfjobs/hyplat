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
<title>产品设计打印预览</title>

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
}
</style>
</head>
<body>
	<table width="99%" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>产品设计</h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
		<thead>
				<tr class="tablewith">
					<th align="center">序号</th>
					<th align="center">行业</th>
					<th align="center">条码</th>
					<th align="center">产品编号</th>
					<th align="center">产品名称</th>
			        <th align="center">品牌</th>
					<th align="center">规格</th>
				    <th align="center">型号</th>
				    <th align="center">科目</th>
					<th align="center">单价</th>
					<th align="center">数量</th>
					<th align="center">金额</th>
					<th align="center">备注</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
					<tr id="${ppID}">
						
						<td align="center" width="90"><%=number%></td>
						<td align="center"><span id="tradeCode">${tradeCode}</span></td>
						<td align="center"><span id="barCode">${barCode}</span></td>
						<td align="center"><span id="productCode">${productCode}</span></td>
						<td align="center"><span id="goodsName">${goodsName}</span></td>
                        <td align="center"><span id="brand">${brand}</span></td>
                        <td align="center"><span id="standard">${standard}</span></td>
                        <td align="center"><span id="model">${model}</span></td>
                         <td align="center"><span id="model">${subjectName}</span></td>
                        <td align="center"><span id="price">${price}</span></td>
                        <td align="center"><span id="quantity">${quantity}</span></td>
						<td align="center"><span id="money"></span>${money}</td>
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
