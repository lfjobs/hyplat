<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>入库打印列表</title>
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
				<td height="32" colspan="17" align="center">
					<span class="STYLE1">入库单</span>
				</td>
			</tr>
			<tr>
		
				<th height="80" align="center" width="40">
					序号
				</th>
				<th align="center" width="160">
					费用或品名名称
				</th>
				<th align="center" width="60">
					类型
				</th>
				<th align="center" width="60">
					品名编号
				</th>
				<th align="center" width="110">
					统一分类条码
				</th>
				<th align="center" width="120">
					采购数量
				</th>
				<th align="center" width="40">
					采购单价
				</th>
				<th align="center" width="40">
					采购金额
				</th>
				<th align="center" width="40">
					收货数量
				</th>
				<th align="center" width="80">
					合格数量
				</th>
				<th align="center" width="75">
					未入库数量
				</th>
				<th align="center" width="75">
					库存状态
				</th>
				<th align="center" width="75">
					物品状态
				</th>
				<th align="center" width="75">
					责任人
				</th>
				<th align="center" width="75">
					接收人
				</th>
				<th align="center" width="75">
					凭证号
				</th>
				<th align="center" width="50">
					单位
				</th>
			</tr>
			
			<%
			  int number = 1;
		    %>
			<c:forEach var="arr" items="${pageForm.list}" varStatus="step">
				<tr>
					<th height="30" align="center" width="40">
					<%=number%>
				</th>
				<th align="center" width="80">
					${arr[3]}
				</th>
				<th align="center" width="80">
					${arr[4]}
				</th>
				<th align="center" width="110">
					${arr[1]}
				</th>
				<th align="center" width="110">
					${arr[2]}
				</th>
				<th align="center" width="30">
					${arr[8]}
				</th>
				<th align="center" width="20">
					${arr[10]}
				</th>
				<th align="center" width="20">
					${arr[11]}
				</th>
				<th align="center" width="30">
					${arr[9]}
				</th>
				<th align="center" width="20">
					${arr[15]}
				</th>
				<th align="center" width="30">
					${arr[22]}
				</th>
				<th align="center" width="30">
					${arr[16]}
				</th>
				<th align="center" width="30">
					${arr[17]}
				</th>
				<th align="center" width="30">
					${arr[19]}
				</th>
				<th align="center" width="30">
					${arr[21]}
				</th>
				<th align="center" width="30">
					${arr[18]}
				</th>
				<th align="center" width="30">
					${arr[7]}
				</th>
				<%number++;%>
				</tr>
			</c:forEach>
			</table>
			</body>
</html>