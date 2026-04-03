<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>进销存明细</title>
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
				<td height="32" colspan="16" align="center">
					<span class="STYLE1">进销存明细</span>
				</td>
			</tr>
			<tr>
				<th height="80" align="center" width="40">
					序号
				</th>
				<th align="center" width="160">
					物品名称
				</th>
				<th align="center" width="60">
					物品类别
				</th>
				<th align="center" width="60">
					日期
				</th>
				<th align="center" width="110">
					业务类型
				</th>
				<th align="center" width="120">
					数量
				</th>
				<th align="center" width="40">
					总金额
				</th>
			</tr>
			<%
			  int number = 1;
		    %>
			<c:forEach var="stoinv" items="${pageForm.list}" varStatus="step">
				<tr>
					<th height="30" align="center" width="40">
					<%=number%>
				</th>
				<th align="center" width="80">
					${stoinv[1]}
				</th>
				<th align="center" width="80">
					${stoinv[2]}
				</th>
				<th align="center" width="110">
					${stoinv[3]}
				</th>
				<th align="center" width="110">
					${stoinv[4]}
				</th>
				<th align="center" width="30">
					${stoinv[5]}
				</th>
				<th align="center" width="20">
					${stoinv[6]}
				</th>
				<%number++;%>
				</tr>
			</c:forEach>
			</table>
			</body>
</html>