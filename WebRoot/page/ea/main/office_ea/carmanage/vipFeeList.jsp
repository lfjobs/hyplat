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
<title>会员收费套餐</title>

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
<script src="<%=basePath%>js/ea/office_ea/carmanage/vipFeeList.js"
	type="text/javascript"></script>

	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";
	var ppageNumber = "${pageNumber}";
	var tcId = "";
	var search = "${search}";
</script>
</head>
<body>

	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="80" align="center">车牌号</th>
					<th width="150" align="center">开始日期</th>
					<th width="150" align="center">截止日期</th>
					<th width="80" align="center">场地编号</th>
					<th width="150" align="center">场地名称</th>
					<th width="150" align="center">备注信息</th>
					<th width="150" align="center">订单号</th>

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${c[0]}" />
						</td>
						<td><%=number%></td>
						<td><span id="carNumber">${c[1]}</span>
							<span id="tcId"
							style="display:none;">${c[0]}</span>
							<span id="tcKey"
								  style="display:none;">${c[7]}</span>
							<span id="state"
								  style="display:none;">${c[9]}</span>
							<span id="staffId"
								  style="display:none;">${c[10]}</span>
						</td>
						<td><span id="startDate">${fn:substring(c[2],0,19)}</span>
						</td>
						<td><span id="endDate">${fn:substring(c[3],0,19)}</span>
						</td>
						<td>
                      <span id="siteId"
                             	  style="display:none;">${c[8]}</span>
							<span id="sitenumber">${c[4]}</span>
						</td>
						<td><span id="sitename">${c[5]}</span>
						</td>
						<td><span id="remark">${c[11]}</span>
						</td>
						<td><span id="journalnum">${c[6]}</span>
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
				value="ea/carmanage/ea_getVipFeeList.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>

	<!--添加窗口 -->
	<form name="addForm" id="addForm" method="post">
		<div class="jqmWindow" style="width:550px;right: 30%;top:10%"
			id="jqModeladd">
			<input type="submit" name="submit" style="display:none" />

			<div class="drag">
				添加
				<div class="close"></div>
			</div>
			<table width="100%" id="addTable" cellspacing="10" cellpadding="10">
				<tr>
					<td align="right">车牌号：</td>
					<td><input id="carNumber"
						name="timingCharging.carNumber" maxlength="10"  autocomplete="off"  class="carNumber"/>
						<input id="tcKey" style="display:none;"
							   name="timingCharging.tcKey" />
						<input id="tcId" style="display:none;"
							   name="timingCharging.tcId" />
						<input id="state" style="display:none;"
							   name="timingCharging.state" value="00"/>
					</td>

				</tr>
				<tr>
					<td align="right">场地：</td>
					<td>
						<select id="siteId" name="timingCharging.siteId"
								class="site">

						</select>
					</td>

				</tr>
				<tr>
					<td align="right">开始时间：</td>
					<td><input name="start" onfocus="daytime(this)" class="put3"  autocomplete="off"
									   id="startDate" /></td>

				</tr>

				<tr>
					<td align="right">结束时间：</td>
					<td><input name="end" onfocus="daytime(this)" class="put3"  autocomplete="off"
							   id="endDate" /></td>

				</tr>
				<tr>
					<td align="right">订单号：</td>
					<td><input id="journalnum"   autocomplete="off"
							   name="timingCharging.journalnum" />
					</td>

				</tr>
				<tr>
					<td align="right">备注信息：</td>
					<td><input id="remark"   autocomplete="off"
							   name="timingCharging.remark" />
					</td>

				</tr>

			</table>
			<div align="center">
				<input type="button" class="input-button" id="save" value=" 提交 "
					style="margin: 10px;" />
			</div>
		</div>
	</form>



	<form name="searchForm" id="searchForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<input type="hidden" value="search" name="search" />
		<input type="hidden" id="carNumber" name="timingCharging.carNumber" />
		<input type="hidden" id="siteId" name="timingCharging.siteId" />


	</form>

</body>
</html>