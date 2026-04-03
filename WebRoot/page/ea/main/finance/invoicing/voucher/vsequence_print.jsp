<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>序时账报表打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
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
<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jqChart/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqChart/excanvas.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/jqChart/jquery.jqChart.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/jqChart/jquery.jqRangeSlider.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/jqChart/jquery-ui-1.8.21.css" />
<script src="<%=basePath%>js/jqChart/jquery.jqChart.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/jqChart/jquery.jqRangeSlider.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/finance/invoicing/inventory_print.js"></script>
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 9px;
}

body {
	margin-left: 15px;
}

th,TH {
	font-size: 12px;
	border-color: #000000;
	height: 18;
}
</style>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
</script>
</head>
<body>
<table width="97%" border="1" cellspacing="0" cellpadding="0" style="border:black">
		<tr><th colspan="10" align="center" style="font-size: 20px">序时账</th></tr>
		<tr>
			<th align="center" >公司名称</th>
			<th align="center" >日期</th>
			<th align="center" >凭证号</th>
			<th align="center" >科目编码</th>
			<th align="center" >科目名称</th>
			<th align="center" >摘要 </th>
			<th align="center" >借方</th>
			<th align="center">贷方</th>
		</tr>
		<c:forEach items="${pageForm.list}" var="arr">
				<tr>
				<td align="center">&nbsp;${arr[0]}</td>
				<td align="center">&nbsp;${arr[1]}</td>
				<td align="center">&nbsp;${arr[2]}</td>
				<td align="center">&nbsp;${arr[3]}</td>
				<td align="center">&nbsp;${arr[4]}</td>
				<td align="center">&nbsp;${arr[5]}</td>
				<td align="center">&nbsp;${arr[6]}</td>
				<td align="center">&nbsp;${arr[7]}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
