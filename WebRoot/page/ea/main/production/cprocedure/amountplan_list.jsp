<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生产量计划</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script
	src="<%=basePath%>js/ea/production/cprocedure/amountplan_list.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var paid = "";
	var fiveClear="${fiveClear}";
</script>
<style type="text/css">
 td
        {
            white-space: nowrap;
        }

</style>
</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post"
			>
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>生产计划量</strong>&nbsp;&nbsp;&nbsp;产品编号：</td>
					<td><input type="text" style="width:90px;height:18px;"
						name="plan.productCode" /></td>
					<td>&nbsp;产品名称：</td>
					<td><input type="text" name="plan.productName"
						style="width:90px;height:18px;" /></td>
					<td><input type="button" class="input-button" value="  查询  " style="margin:0px;margin-left:5px;"
						id="tosearch" /> <input type="hidden" value="search" name="search" />
						<input type="hidden" id="paid" name="plan.paid" /></td>
				</tr>
			</table>
		</form>

	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="170" align="center">行业</th>
					<th width="170" align="center">产品编号</th>
					<th width="100" align="center">产品名称</th>
					<th width="100" align="center">结构数量</th>
					<th width="100" align="center">项目产品计划生产量（个/时）</th>
					<th width="100" align="center">每日工作时间</th>
					<th width="100" align="center">日生产量</th>
					<th width="100" align="center">负责人</th>
					<th width="100" align="center">制定日期</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${paid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${paid}" />
						</td>

						<td><%=number%></td>
						<td><span id="tradeCode">${tradeCode}</span>
						</td>
						<td><span id="productCode">${productCode}</span>
						</td>
						<td><span id="productName">${productName}</span>
						</td>

						<td><span id="quantity">${quantity}</span>
						</td>

						<td><span id="planProductNum">${planProductNum}</span>
						</td>
						<td><span id="hours">${hours}</span>
						</td>
						<td><span id="dayProductNum">${dayProductNum}</span>
						</td>

						<td><span id="dutorName">${dutorName}</span>
						</td>
						<td><span id="createDate">${fn:substring(createDate,0,10)}</span>
						</td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/amountplan/ea_getAmountPlanList.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>