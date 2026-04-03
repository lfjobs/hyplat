<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作记录时间表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/adance/workcalendar.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var workcalendarid = "";
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="workcalendarForm" id="workcalendarForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="orkcalendar">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="120" align="center">日期</th>
						<th width="120" align="center">星期</th>
						<th width="120" align="center">状态</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator value="pageForm.list">
						<tr id="${workcalendarid}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${workcalendarid}" /></td>
							<td><span id="day">${fn:substring(days,0,10)}</span></td>
							<td><span id="week">${week }</span></td>
							<td><span id="status">
							<c:if test="${status == '00'}">工作日</c:if>
							 <c:if test="${status == '01'}">休息日</c:if></span></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/workcalendar/ea_getWorkCalendar.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
			id="jqModelSerch">
			<form name="jqModelSerchForm" id="jqModelSerchForm" method="post">
				
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="adaSearchTable">
					<tr>
						<td align="right" width="100px">
							导入时间：
						</td>
						<td>
						<input type="submit" name="submit" style="display: none" />
							<input name="adance.importdate" id="importdate" onfocus="date(this);" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							人员编号：
						</td>
						<td>
							<input name="adance.staffCode" id="staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							日期时间：
						</td>
						<td>
							<input name="adance.workdate" id="workdate" onfocus="date(this);" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							记录状态：
						</td>
						<td>
							<select name="adance.opposite" id="opposite">
								<option value="">--选择--</option>
								<option value="上班签到">上班签到</option>
								<option value="下班签退">下班签退</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchAda"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
</body>
</html>
