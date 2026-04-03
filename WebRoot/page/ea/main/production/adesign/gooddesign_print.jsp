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
<title>物品设计打印预览</title>

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
				<h2>物品设计</h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
		<thead>
				<tr class="tablewith">
					
					<th width="90" align="center">序号</th>
					<th width="100" align="center">行业分类</th>
					<th width="180" align="center">物品分类</th>
					<th width="120" align="center">物品条码</th>
					<th width="100" align="center">物品编号</th>
					<th width="100" align="center">物品名称</th>
					<th width="100" align="center">品牌</th>
					<th width="100" align="center">物品规格</th>
					<th width="100" align="center">型号</th>
					<th width="100" align="center">单位</th>
					<th width="160" align="center">创建时间</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
					<tr id="${goodsID}">
					
						<td align="center"><%=number%></td>
						<td align="center"><span id="tradeCode">${tradeCode}</span></td>

						<td align="center"><span id="typeID">${typeID}</span></td>

						<td align="center"><span id="barCode">${barCode}</span></td>
						<td align="center"><span id="goodsCoding">${goodsCoding}</span></td>
						<td align="center"><span id="goodsName">${goodsName}</span></td>
					    <td align="center"><span id="brand">${brand}</span></td>
                        <td align="center"><span id="standard">${standard}</span></td>
                        <td align="center"><span id="model">${model}</span></td>
						<td align="center"><span id="variableID">${variableID}</span></td>
						<td align="center"><span id="createdate">${fn:substring(createdate,0,19)}</span></td>

						

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>

	</table>
</body>
</html>
