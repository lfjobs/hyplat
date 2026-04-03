<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存盘点</title>
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
 var kutime="<%=request.getAttribute("kutime")%>";
	var line1 = [];
	var line2 = [];
	var line3 = [];
	var maxnum = 0;
	var maxnum2 = 0;
	$(function() {
		$("#dis").css("height",document.body.offsetHeight-356).css("overflow","auto");
		$("#JQueryPrint").click(function() {
			$("#isShow").hide();
		});
	});
</script>
</head>
<body>
<div id="dis">
<table width="100%" border="1" cellspacing="0" cellpadding="0" style="border:black">
		<tr>
			<th colspan="9" align="center" style="font-size: 20px">库存盘点(${kutime})</th>
		</tr>
		<tr>
			<th align="center">物品名称</th>
			<th align="center">物品类型</th>
			<th align="center">上月库存量</th>
			<th align="center">本月入库量</th>
			<th align="center">本月出库量</th>
			<th align="center">当前库存量</th>
			<th align="center">标准库存范围</th>
			<th align="center">库存预警</th>
			<th align="center">库存总金额</th>
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
		
		<c:forEach items="${pageForm.list}" var="arr">
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
			<tr id="${arr[0]}">
				<td align="center">&nbsp;${arr[2]}</td>
				<td align="center">&nbsp;${arr[3]}</td>
				<td align="center">&nbsp;${arr[4]}</td>
				<td align="center">&nbsp;${arr[5]}</td>
				<td align="center">&nbsp;${arr[6]}</td>
				<td align="center">&nbsp;${arr[7]}</td>
				<td align="center">&nbsp;(${arr[8]}----${arr[9]})</td>
				<td align="center">&nbsp;${arr[10]}</td>
				<td align="center">&nbsp;${arr[11]}</td>
			</tr>
			
		</c:forEach>
		<tr id="isShow" style="display:show">
			<th colspan="9" align="center">
			<input type="button" class="input-button " id="JQueryPrint"
				style="cursor: pointer; width: 80px;" value="打印预览" />
				<input type="button" class="input-button JQuerySubmitpost" id="queding"
				style="cursor: pointer; width: 80x;" value="${kutime}总物品库存预警折线图" />
				<input type="button" class="input-button JQuerySubmitpost" id="queding2"
				style="cursor: pointer; width: 200px;" value="不同月份物品总价变化趋势折线图" /></th>
		</tr>
	</table>
	</div>
	<div class="jqmWindow" style="width: 1000px;margin-left: 210px;top:5%;height: 650px"
		id="tubiao" align="center">
		<div class="drag">
			<div class="close"></div>
		</div>
		<table>
			<tr>
				<td height="600px">
					<div id="jqChart" style="width: 900px;height: 500px"></div>
				</td>
			</tr>
		</table>
	</div>
	<div class="jqmWindow" style="width: 800px;margin-left: 400px;top:15%;height: 650px"
		id="tubiao2" align="center">
		<div class="drag">
			<div class="close"></div>
		</div>
		<table>
			<tr>
				<td height="600px">
					<div id="jqChart2" style="width: 700px;height: 500px"></div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
