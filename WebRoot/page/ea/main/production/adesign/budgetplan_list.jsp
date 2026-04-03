<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<title>预算计划</title>


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
<script src="<%=basePath%>js/ea/production/adesign/budgetplan_list.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>



<script type="text/javascript">

var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var type = "${type}";
var bpid = "";
var fiveClear="${fiveClear}";
var category="${category}";
</script>
</head>
<body>
    
	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">
		
		<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>
		 <c:if test="${type==01}">产品生产量预算</c:if> <c:if test="${type==02}">生产量日周月季年计划</c:if> &nbsp;&nbsp;&nbsp;产品编号：<input type="text"
			style="width:90px;height:18px;" name="plan.productCode"/>&nbsp;产品名称：<input type="text" name="plan.productName" style="width:90px;height:18px;" />&nbsp;计划年份：<input type="text"  name="plan.year"  style="width:90px;height:18px;"  
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="Wdate"/>
		<input
			type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;"/>
			<input
			type="hidden" value="search" name="search" />
			<input
			type="hidden" id="bpid" name="plan.bpid"/>
			<input
			type="hidden"  name="type" value="${type}"/>
		</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="100" align="center">行业</th><%--
					<th width="100" align="center">项目产品分类</th>
					--%><th width="100" align="center">产品条码</th>
					<th width="100" align="center">产品编号</th>
					<th width="100" align="center">产品名称</th>
					<th width="200" align="center">流水线设备套数</th>
					<th width="200" align="center">单套设备最大用人量</th>
					<th width="200" align="center">单设备每小时最大生产量</th>
					<th width="100" align="center">设备每天工作时间</th>
					<th width="100" align="center">数量</th>
					<th width="100" align="center">金额</th>
					<th width="100" align="center">日最大生产量</th>
					<th width="100" align="center">周最大生产量</th>
					<th width="100" align="center">月最大生产量</th>
					<th width="100" align="center">季最大生产量</th>
					<th width="100" align="center">年最大生产量</th>
					<th width="100" align="center">计划年份</th>
					<th width="150" align="center">制定日期</th>
					

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${bpid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${bpid}" /></td>
						<td><%=number%></td>
						
						<td><span id="tradeCode">${tradeCode}</span></td><%--

						<td><span id="producttype">${producttype}</span></td>
	                    --%><td><span id="barCode">${barCode}</span></td>
	 					<td><span id="productCode">${productCode}</span></td>
						<td><span id="productName">${productName}</span></td>
						<td><span id="deviceNum">${deviceNum}</span></td>

						<td><span id="maxBydevice">${maxBydevice}</span></td>
						<td><span id="maxByhbyd">${maxByhbyd}</span></td>
						<td><span id="worktimeByd">${worktimeByd}</span></td>
						<td><span id="quantity">${quantity}</span></td>
						<td><span id="money">${money}</span></td>
						<td><span id="maxDay">${maxDay}</span></td>
						<td><span id="maxWeek">${maxWeek}</span></td>
						<td><span id="maxMonth">${maxMonth}</span></td>
						<td><span id="maxSeason">${maxSeason}</span></td>
						<td><span id="maxYear">${maxYear}</span></td>
						<td><span id="year">${year}</span></td>
						<td><span id="createDate">${fn:substring(createDate,0,10)}</span></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/budgetplan/ea_getBudgetPlanList.jspa?type=${type}&pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>