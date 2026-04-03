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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约详细信息</title>
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
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/ddsr/coachreservationrecordcontent.js"></script>
<script src="<%=basePath%>js/ea/microletter/PreviewImage.js"></script>
<style type="text/css">
table.address span.rereStudent{
	color: blue;
	cursor: pointer;
	text-decoration:none;
}
	
</style>
<script type="text/javascript">
   var  rereKey = '';
   var  basePath='<%=basePath%>';
	var search = '${search}';
	var token = 0; 
	var coacKey='${ddsrcoach.coacKey}';
	var rereKeyArray=""; 
	var stureKey="";
	var notoken=0;
	
	var  studKey='${param.studKey}';
	var  studName='${param.studName}';
	var subjType='${dssrsubject.subjType}';//科目导航页面传值 区分科目类别
</script>
</head>
<body>
	<div id="main_main" class="main_main">
		<table class="address">
			<thead>
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
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="ddsrreservationrecordList" >
					<tr class="td_bg01 saveAjax" id="${rereKey}">
						<td class="td_bg01">
						<c:if test="${rerePeoplesum==0}"><input type="checkbox" name="check"
							class="JQuerypersonvalue" value="${rereKey}" /></c:if>
						</td>
						<td class="td_bg01"><span><%=number%></span></td>
						<td class="td_bg01"><span id="staffName">${ddsrcoach.dtHrStaff.staffName}
						</span></td>
						<td class="td_bg01"><span id="sex">${ddsrcoach.dtHrStaff.sex}</span>
						</td>
						<td class="td_bg01"><span id="coacReleasedate">${ageOfSchool}</span>
						</td>
						<td class="td_bg01"><span id="coacReleasedate">${ageOfSchool}</span>
						</td>
						<td class="td_bg01"><span id="rereAppdate">${fn:substring(rereAppdate,0,11)}</span>
						</td>
						<td class="td_bg01">
							<span id="rereClass"><c:choose>
								<c:when test="${rereClass==10}">早间</c:when>
								<c:when test="${rereClass==20}">上午</c:when>
								<c:when test="${rereClass==30}">下午</c:when>
								<c:when test="${rereClass==40}">夜间</c:when>
								<c:otherwise>暂无</c:otherwise>
							</c:choose></span>
						</td>
						<td class="td_bg01"><span id="rereStadateRereEnddate">${rereStadate}~${rereEnddate}</span>
						</td>
						<td class="td_bg01"><span id="rereSubjects">
							<c:choose>
								<c:when test="${rereSubjects==10}">科一</c:when>
								<c:when test="${rereSubjects==20}">科二</c:when>
								<c:when test="${rereSubjects==30}">科三</c:when>
								<c:when test="${rereSubjects==40}">科四</c:when>
								<c:otherwise>暂无</c:otherwise>
							</c:choose>
						</span>
						</td>
						<td class="td_bg01"><span id="rerePeoplesum">${rerePeoplesum}</span>
						</td>
						<td class="td_bg01"><span id="reference">${ddsrcoach.dtHrStaff.reference}</span>
						</td>
						<td class="td_bg01">
								<s:iterator value="reDssrstudentDdsrresrecords">
									<span class="rereStudent" id="${stureKey}"  >${dssrstudent.dtHrStaff.staffName}</span>
									<input id="rereStudentStudKey" class="rereStudentStudKey"  type="hidden" value="${dssrstudent.studKey}"/>
									<span class="detailsOfstaffID" id="${dssrstudent.dtHrStaff.staffID}"  style="color:blue;cursor: pointer;">详情</span>
								</s:iterator>
						</td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!-- 隐藏from  学员选择多个预约时间段 -->   
<form name="ReservationRecoardForm" id="ReservationRecoardForm" method="post" action="">
   			<input type="submit" name="submit" style="display: none;" />
   			<input name="ddsrreservationrecord.rereKey" type="hidden" id="rereKey"/>
   			<input name="reDssrstudentDdsrresrecord.stureKey" type="hidden" id="stureKey"/>
</form>	
</body>
</html>
