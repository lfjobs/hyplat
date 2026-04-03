<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>模拟测试打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body {
	margin-left: 15px;
}

#textDate {
	text-decoration: underline;
}

body td {
	font-size: 11px;
}
</style>
<script type="text/javascript">
		    var basePath = "<%=basePath%>
	";
</script>
</head>
<body>
	<div id="tableprint" align="center">
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="background: #FFFFFF;">
			<tr>
				<td height="25" align="center"
					style="font-weight:bold;font-size:20px">&nbsp;模拟测试单</td>
			</tr>
		</table>
		<br /> <br />
		<table width="900" border="0" cellpadding="0" cellspacing="0"
			style="background: #FFFFFF;" class="table">
			<thead>
				<tr>
					<th width="130" align="center">行业分类</th>
					<th width="170" align="center">项目产品编号</th>
					<th width="120" align="center">产品条码</th>
					<th width="120" align="center">产品名称</th>
					<th width="120" align="center">产品规格</th>
					<th width="80" align="center">单价</th>
					<th width="80" align="center">数量</th>
					<th width="80" align="center">金额</th>
					<th width="100" align="center">审核人</th>
					<th width="160" align="center">审核人部门</th>
					<th width="120" align="center">审核时间</th>
					<th width="120" align="center">审核状态</th>
				</tr>

			</thead>
			<tbody>

				<s:iterator value="list" var="l">
					<tr style="height: 20px; text-align: center;">
						<td width="150px;"><span id="industryClassification">${industryClassification}</span>
						</td>
						<td><span id="itemNumber">${itemNumber}</span></td>
						<td><span id="goodBar">${goodBar}</span></td>

						<td><span id="goodName">${goodName}</span></td>
						<td><span id="goodStandard">${goodStandard}</span></td>
						<td><span id="price">${price}</span></td>

						<td><span id="btnumber">${btnumber}</span></td>
						<td><span id="money">${money}</span></td>
						<td><span id="auditor">${auditor}</span></td>
						<td><span id="organizationName">${l.organizationName}</span></td>
						<td><span id="auditTime">${auditTime}</span></td>
						<td><span id="status" style="display:none;">${status}</span>
							<s:if test="status=='00'">待检测</s:if> 
							<s:if test="status=='02'">合格</s:if>
							<s:if test="status=='03'">不合格</s:if></td>

					</tr>
				</s:iterator>
			</tbody>
		</table>
</body>
</html>
