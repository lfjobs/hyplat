<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的预约</title>
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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script
	src="<%=basePath%>js/ea/ddsr/coachreservationrecordcontentOfpersonalOfWeChat.js"></script>
<script src="<%=basePath%>js/ea/microletter/PreviewImage.js"></script>
<style type="text/css">
table.JQueryflexme {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.JQueryflexme th {
	background: #b5cfd2 url('<%=basePath%>/images/ea/ddsr/cell-blue.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}

table.JQueryflexme td {
	background: #dcddc0 url('<%=basePath%>/images/ea/ddsr/cell-grey.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}

span,a {
	font-size: 25px;
}

table.JQueryflexme span.rereStudent {
	color: blue;
	cursor: pointer;
	text-decoration: none;
}
</style>
<script type="text/javascript">
   var  rereKey = '';
   var  basePath='<%=basePath%>';
	var search = '${search}';
	var token = 0;
	var coacKey = '${ddsrcoach.coacKey}';
	var rereKeyArray = "";
	var stureKey = "";
	var notoken = 0;

	var studKey = '${dssrstudent.studKey}';
	var studName = '${dssrstudent.dtHrStaff.staffName}';
	var subjType = '${dssrsubject.subjType}';//科目导航页面传值 区分科目类别
</script>
</head>
<body>
	<div id="main_main" class="main_main">
		<table class="JQueryflexme" width="100%">
			<!-- <thead>
				<tr>
					<th width="30" align="center">选择</th>
					<th width="30" align="center">序号</th>
					<th width="70" align="center">教练姓名</th>
					<th width="50" align="center">教练性别</th>
					<th width="50" align="center">教练驾龄</th>
					<th width="50" align="center">教练教龄</th>
					<th width="90" align="center">预约日期</th>
					<th width="60" align="center">预约班次</th>
					<th width="100" align="center">预约时段</th>
					<th width="70" align="center">预约科目</th>
					<th width="70" align="center">预约人数</th>
					<th width="90" align="center">联系方式</th>
					<th width="80" align="center">预约学员</th>
				</tr>
			</thead> -->
			<tbody>
				<%
					int number = 1;
				%>
				<s:if test="ddsrreservationrecordList.size>0">
				<s:iterator value="ddsrreservationrecordList">
					<tr title="index<%=number%>" id="${rereKey}">
						<th align="center">选择</th>
						<th><c:if test="${rerePeoplesum==0}">
								<input type="checkbox" name="check" class="JQuerypersonvalue"
									value="${rereKey}" autocomplete="off" />
							</c:if></th>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td><span><%=number%></span>
						</td>
					</tr>
					<tr>
						<td align="center">教练姓名</td>
						<td><span id="staffName">${ddsrcoach.dtHrStaff.staffName}
						</span>
						</td>
					</tr>
					<tr>
						<td align="center">教练性别</td>
						<td><span id="sex">${ddsrcoach.dtHrStaff.sex}</span></td>
					</tr>
					<tr>
						<td align="center">教练驾龄</td>
						<td><span id="coacReleasedate">${ageOfSchool}</span></td>
					</tr>
					<tr>
						<td align="center">教练教龄</td>
						<td><span id="coacReleasedate">${ageOfSchool}</span></td>
					</tr>

					<tr>
						<td align="center">预约日期</td>
						<td><span id="rereAppdate">${fn:substring(rereAppdate,0,11)}</span>
						</td>
					</tr>
					<tr>
						<td align="center">预约班次</td>
						<td><span id="rereClass"><c:choose>
									<c:when test="${rereClass==10}">早间</c:when>
									<c:when test="${rereClass==20}">上午</c:when>
									<c:when test="${rereClass==30}">下午</c:when>
									<c:when test="${rereClass==40}">夜间</c:when>
									<c:otherwise>暂无</c:otherwise>
								</c:choose>
						</span></td>
					</tr>
					<tr>
						<td align="center">预约时段</td>
						<td><span id="rereStadateRereEnddate">${rereStadate}~${rereEnddate}</span>
						</td>
					</tr>

					<tr>
						<td align="center">预约科目</td>
						<td><span id="rereSubjects"> <c:choose>
									<c:when test="${rereSubjects==10}">科一</c:when>
									<c:when test="${rereSubjects==20}">科二</c:when>
									<c:when test="${rereSubjects==30}">科三</c:when>
									<c:when test="${rereSubjects==40}">科四</c:when>
									<c:otherwise>暂无</c:otherwise>
								</c:choose> </span></td>
					</tr>
					<tr>
						<td align="center">预约人数</td>
						<td><span id="rerePeoplesum">${rerePeoplesum}</span></td>
					</tr>
					<tr>
						<td align="center">联系方式</td>
						<td><span id="reference">${ddsrcoach.dtHrStaff.reference}</span>
						</td>
					</tr>
					<tr>
						<td align="center">预约学员</td>
						<td><s:iterator value="reDssrstudentDdsrresrecords">
								<span class="rereStudent" id="${stureKey}" title="index<%=number%>" >${dssrstudent.dtHrStaff.staffName}</span>
								<%-- <span class="detailsOfstaffID" id="${dssrstudent.dtHrStaff.staffID}"  style="color:blue;cursor: pointer;">详情</span> --%>
								<input id="rereStudentStudKey" class="rereStudentStudKey" type="hidden" value="${dssrstudent.studKey}" />
							</s:iterator>
						</td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
				</s:if>
				<s:else>没有预约信息</s:else>
			</tbody>
		</table>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!-- 隐藏from  学员选择多个预约时间段 -->
	<form name="ReservationRecoardForm" id="ReservationRecoardForm"
		method="post" action="">
		<input type="submit" name="submit" style="display: none;" /> <input
			name="ddsrreservationrecord.rereKey" type="hidden" id="rereKey" /> <input
			name="reDssrstudentDdsrresrecord.stureKey" type="hidden"
			id="stureKey" />
	</form>
	<!-- 隐藏from  学员选择多个预约时间段 -->
	<form name="ReservationRecoardFormYuYue"
		id="ReservationRecoardFormYuYue" method="post" action="">
		<input type="submit" name="submit" style="display: none" /> <input
			name="rereKeyString" type="hidden" id="rereKeyString" /> <input
			name="dssrstudent.studKey" type="hidden" id="studKey" /> <input
			name="dssrsubject.subjType" type="hidden"
			value="${dssrsubject.subjType}" />
	</form>
</body>
</html>
