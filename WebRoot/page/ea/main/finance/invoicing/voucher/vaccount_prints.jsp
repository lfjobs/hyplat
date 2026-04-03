<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 总账打印</title>
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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
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
 var kutime="<%=request.getAttribute("kutime")%>";
 var zz="${zz}";
 var stime="${stime}";
 var etime="${etime}";
 var kemu="${kemu}";
 if("${subs.sdirection }"=="借" || "${subs.sdirection }"=="平"){
 	var balance=parseFloat("${subs.startCash }");
 }else if("${subs.sdirection }"==""){
 	var balance=0.0;
 }else {
 	var balance=parseFloat("-"+"${subs.startCash }");
 }
 var direction='';
 var select=0;
 var jiez=0.0;
 var daiz=0.0;
 var nian1="${nian1}";
 var nian2="${nian2}";
</script>
</head>
<body>
<table width="95%" border="1" cellspacing="0" cellpadding="0" style="border:black">
		<tr><th colspan="10" align="center" style="font-size: 20px"><s:if test="zz==00">总账</s:if><s:elseif test="zz==01">明细账</s:elseif></th></tr>
		<tr><th colspan="10" align="center" style="font-size: 10px">期间：${stime}--${etime}</th></tr>
		<tr>
			<th align="center" colspan="2">${fn:substring(sdate, 0, 4)}年</th>
			<th align="center" rowspan="2">科目编号</th>
			<th align="center" rowspan="2">科目</th>
			<th align="center" rowspan="2">方向</th>
			<th align="center" rowspan="2">期初余额 </th>
			<th align="center" rowspan="2">借方金额 </th>
			<th align="center" rowspan="2">贷方金额</th>
			<th align="center" rowspan="2">方向</th>
			<th align="center" rowspan="2">余额</th>
		</tr>
		<tr>
			<th align="center">月</th>
			<th align="center">日</th>
		</tr>
		<c:forEach items="${pageForm.list}" var="arr">
			<script type="text/javascript">
			</script>
				<tr>
				<td align="center">&nbsp;${fn:substring(stime, 5, 7)}</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;${arr[0]}</td>
				<td align="center">&nbsp;${arr[1]}</td>
				<td align="center">&nbsp;${arr[2]}</td>
				<td align="center">&nbsp;${arr[3]}</td>
				<td align="center">&nbsp;${arr[6]}</td>
				<td align="center">&nbsp;${arr[7]}</td>
				<td align="center">&nbsp;${arr[4]}</td>
				<td align="center">&nbsp;${arr[5]}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
