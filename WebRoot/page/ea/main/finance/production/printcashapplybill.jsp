<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>本公司现金申请对账单</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<style type="text/css">
<!--
body,th {
	font-size: 12px;
}

.table {
	border-collapse: collapse;
	border: 1px solid;
	font-size: 12px;
}

.table th {
	border: 1px solid;
}

.table td {
	border: 1px solid;
	color: #333;
}

-->
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
.STYLE2 {
	font-size: 16px;
}
</style>
	</head>
	<body
		style=" overflow: scroll; margin-left: 10px;text-align: center">
		<table id="content" width="1000px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<td height="32" colspan="${weibokuan=='weibokuan'?13:16}" align="center">
					<span class="STYLE1">${weibokuan=='weibokuan'?'本公司现金请购明细审批单':'现金申请对账单'}</span>
				</td>
			</tr>
			<tr>
				<th height="80" align="center" width="40">
					序号
				</th>
				<th align="center" width="160">
					公司
				</th>
				<th align="center" width="60">
					部门
				</th>
				<th align="center" width="60">
					申请人
				</th>
				<th align="center" width="110">
					但单据编号
				</th>
				<th align="center" width="120">
					费用或物品名称（摘要）
				</th>
				<th align="center" width="40">
					数量
				</th>
				<th align="center" width="40">
					单价
				</th>
				<th align="center" width="40">
					金额
				</th>
				<th align="center" width="80">
					申请时间
				</th>
				<th align="center" width="160">
					往来公司
				</th>
				<th align="center" width="80">
					往来个人
				</th>
				<th align="center" width="80">
					备注
				</th>
				<c:if test="${weibokuan!='weibokuan'}">
				<th align="center" width="40">
					拨款金额
				</th>
				<th align="center" width="80">
					拨款备注
				</th>
				<th align="center" width="130">
					拨款时间
				</th>
				</c:if>
			</tr>
			<c:forEach var="entity" items="${pageForm.list}" varStatus="step">
				<tr>
					<th height="30" align="center" width="40">
					${entity[0]}
				</th>
				<th align="center" width="80">
					${entity[12]}
				</th>
				<th align="center" width="80">
					${entity[2]}
				</th>
				<th align="center" width="110">
					${entity[4]}
				</th>
				<th align="center" width="110">
					${entity[3]}
				</th>
				<th align="center" width="30">
					${entity[5]}
				</th>
				<th align="center" width="20">
					${entity[6]}
				</th>
				<th align="center" width="20">
					${entity[7]}
				</th>
				<th align="center" width="30">
					${entity[8]}
				</th>
				<th align="center" width="20">
					${entity[9]}
				</th>
				<th align="center" width="20">
					${entity[17]}
				</th>
				<th align="center" width="20">
					${entity[18]}
				</th>
				<th align="center" width="30">
					${entity[10]}
				</th>
				<c:if test="${weibokuan!='weibokuan'}">
				<th align="center" width="30">
					${entity[13]}
				</th>
				<th align="center" width="20">
					${entity[14]}
				</th>
				<th align="center" width="130">
					${fn:substring(entity[15],0,19)}
				</th>
				</c:if>
				</tr>

			</c:forEach>
			</table>
			</body>
</html>