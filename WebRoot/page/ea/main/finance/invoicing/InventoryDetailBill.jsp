<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>进销存汇总</title>
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
<script  type="text/javascript">
    var kutime="<%=request.getAttribute("kutime")%>";
	var line1 = [];
	var line2 = [];
	var line3 = [];
	var maxnum = 0;
	var maxnum2 = 0;
</script>
	</head>
	<body
		style=" overflow: scroll; margin-left: 10px;text-align: center">
		<table id="content" width="1000px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<td height="32" colspan="16" align="center">
					<span class="STYLE1">${kutime}&nbsp;进销存汇总</span>
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
					上月库存量
				</th>
				<th align="center" width="80">
					本月入库量
				</th>
				<th align="center" width="80">
					本月出库量
				</th>
				<th align="center" width="40">
					当前库存量
				</th>
				<th align="center" width="40">
					标准库存范围
				</th>
				<th align="center" width="40">
					库存预警
				</th>
				<th align="center" width="80">
					库存总金额
				</th>
			</tr>
			<c:forEach items="${lists}" var="are">
			<script type="text/javascript">
				var a1 = 0;
				if ("${are[1]}" != "" && "${are[2]}" != ""&& "${are[3]}" != "") {
					a1 = parseInt("${are[1]}") - parseInt("${are[2]}") -parseInt("${are[3]}");
				} else if ("${are[1]}" != "" && "${are[2]}" == "") {
					a1 = parseInt("${are[1]}") - parseInt("${are[2]}");
				}else if("${are[1]}" != ""){
					a1 = parseInt("${are[1]}");
				}
				if (maxnum2 < a1) {
					maxnum2 = a1+10;
				}
				var a2 = [ [ "${are[0]}", a1 ] ];
				line3 = $.merge(line3, a2);
			</script>
		   </c:forEach>
			<%
			  int number = 1;
		    %>
			<c:forEach var="arr" items="${pageForm.list}" varStatus="step">
			<script type="text/javascript">
				var wp = [ [ "${arr[2]}", "${arr[7]}" ] ];
				var jjx = [ [ "${arr[2]}", "${arr[8]}" ] ];
				var dd = [ "${arr[2]}" ];
				if (maxnum < "${arr[7]}") {
					maxnum = parseInt("${arr[7]}")+10;
				}
				line1 = $.merge(line1, wp);
				line2 = $.merge(line2, jjx);
			</script>
				<tr>
					<th height="30" align="center" width="40">
					<%=number%>
				</th>
				<th align="center" width="80">
					${arr[2]}
				</th>
				<th align="center" width="80">
					${arr[3]}
				</th>
				<th align="center" width="110">
					${arr[4]==null?'0':arr[4]}
				</th>
				<th align="center" width="110">
					${arr[5]==null?'0':arr[5]}
				</th>
				<th align="center" width="30">
					${arr[6]==null?'0':arr[6]}
				</th>
				<th align="center" width="20">
					${arr[7]==null?'0':arr[7]}
				</th>
				<th align="center" width="20">
					(${arr[8]}----${arr[9]})
				</th>
				<th align="center" width="30">
					${arr[10]}
				</th>
				<th align="center" width="20">
					${arr[11]}
				</th>
				<%number++;%>
				</tr>
			</c:forEach>
			</table>
			</body>
</html>