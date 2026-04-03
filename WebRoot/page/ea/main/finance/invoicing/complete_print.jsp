<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 成功率打印</title>
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
<script type="text/javascript" src="<%=basePath%>js/jqChart/excanvas.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
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
 var qb="${qb}";
 var nian="${nian}";
</script>
</head>
<body>
<table width="95%" border="1" cellspacing="0" cellpadding="0" style="border:black">
		<tr>
			<th colspan="5" align="center" style="font-size: 20px">
				<s:if test="nian==10">月收入成功率</s:if><s:elseif test="nian==01">年收入成功率</s:elseif>
			</th>
		</tr>
		<tr>
			<th align="center" >公司</th>
			<th align="center" >日期</th>
			<th align="center" >预算前收入</th>
			<th align="center" >预算后收入</th>
			<th align="center" >成功率 </th>
			
		</tr>
		<c:forEach items="${lists}" var="arr">
		<tr>
			<td align="center">&nbsp;${arr[0]}</td>
			<td align="center">&nbsp;${arr[1]}</td>
			<td align="center">&nbsp;${arr[2]}</td>
			<td align="center">&nbsp;${arr[3]}</td>
			<td align="center">&nbsp;${arr[4]}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>
