<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<meta http-equiv="cache-control" content="no-cache" />
<title>员工考勤记录</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/ea/human/attence/attenceRecordlist.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/common/common.js"></script>


<script type="text/javascript">
    var basePath ="<%=basePath%>";
	var pNumber = ${pageForm.pageNumber};
	var  parameter = "${parameter}";
	var  start = "${start}";
	var  end = "${end}";
	var signType="${signType}";


</script>
</head>
<body>

	<table class="flexme11">
		<thead>
			<tr>
				<th width="30" align="center">选择</th>
				<th width="70" align="center">序号</th>
				<th width="90" align="center">员工编号</th>
				<th width="100" align="center">员工姓名</th>
				<th width="100" align="center">员工手机号</th>
				<th width="200" align="center">员工身份证</th>
				<th width="200" align="center">打卡时间</th>
				<th width="140" align="center">签到方式</th>
				<th width="300" align="center">签到地点</th>
                <th width="300" align="center">签到信息</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageForm.list}" var="item" varStatus="state">
				<tr id="${item[0]}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${item[0]}" /></td>
					<td>${state.index+1}</td>

					<td><span id="staffCode">${item[1]}</span></td>
					<td><span id="staffName">${item[2]}</span></td>
					<td><span id="account">${item[3]}</span></td>
					<td><span id="staffIdentityCard">${item[4]}</span></td>
					<td><span id="signDate">${fn:substring(item[5],0,19)}</span></td>

					<td><span id="signType" style="display: none;">${item[6]}</span>
						<c:choose>
							<c:when test="${item[6]=='01'}">刷脸终端机签到</c:when>
							<c:when test="${item[6]=='00'}">手机APP签到</c:when>
							<c:otherwise>手机APP签到</c:otherwise>
						</c:choose></td>
	              <td><span id="signSite">${item[7]}</span></td>
	              <td><span id="signSite">${item[8]}</span></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="/ea/attence/ea_getAttenceRcordPage.jspa?parameter=${parameter}&start=${start}&end=${end}&signType=${signType}&pageNumber=${pageNumber}">
		</c:param>
	</c:import>
	
</body>
</html>
