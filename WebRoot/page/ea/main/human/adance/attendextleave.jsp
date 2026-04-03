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
<title>申请加班请假表</title>
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
<script src="<%=basePath%>js/ea/human/adance/attendextleave.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var extleaveId = "";
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="extleaveForm" id="extleaveForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="extleavetable">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">员工编号</th>
						<th width="80" align="center">员工姓名</th>
						<th width="120" align="center">所属部门</th>
						<th width="80" align="center">项目类别</th>
						<th width="80" align="center">申请日期</th>
						<th width="120" align="center">状态类别</th>
						<th width="150" align="center">开始时间</th>
						<th width="150" align="center">结束时间</th>
						<th width="120" align="center">实际时长</th>
						<th width="180" align="center">备注</th>
						<th width="100" align="center">审核状态</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator value="pageForm.list">
						<tr id="${extleaveId}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
								value="${extleaveId}" />
							</td>
							<td><span id="staffCode">${staffCode}</span></td>
							<td><span id="staffName">${staffName }</span></td>
							<td><span id="orgName">${orgName }</span></td>
							<td><span id="status">
								<c:if test="${state == '01'}">加分项</c:if>
								<c:if test="${state == '02'}">减分项</c:if></span>
							 </td>
							<td><span id="days">${fn:substring(days,0,10)}</span></td>
							<td><span id="leaveWork">${leaveWork }</span></td>
							<td><span id="beginTime">${fn:substring(beginTime,0,19)}</span></td>
							<td><span id="endTime">${fn:substring(endTime,0,19)}</span></td>
							<td><span id="sumTime">${sumTime }</span></td>
							<td><span id="remark">${remark }</span></td>
							<td><span id="esastate">
								<c:if test="${esastate == '00'}">待审</c:if>
								<c:if test="${esastate == '01'}">通过</c:if>
								<c:if test="${esastate == '02'}">驳回</c:if>
							</span></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/attendextleave/ea_getAttendExtleave.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
