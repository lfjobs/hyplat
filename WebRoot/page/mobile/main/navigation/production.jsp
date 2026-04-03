
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事-生产</title>
		<link href="<%=basePath%>css/mobilecss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

a:link,a:visited,a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a {
	color: #333;
	text-decoration: none;
}

a:active {
	color: #002f76;
	font-weight: bold;
}
</style>
	</head>
	<body>
		<div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" class="actived"><span>人事管理科</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<div id="subNav2">
						<p class="secondNav">
							<!-- 
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/responsibilitiessummary/ea_getResponsibilitiesList.jspa'">职责汇总</a>
								 <a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/ResponsibilitiesSummary.jsp'">职责汇总</a>
							 	<a href="#"
								onclick="document.location.href='<%=basePath%>ea/mobileresponsibilitiessummary/ea_getResponsibilitiesList.jspa'">职责汇总</a>
							 -->
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/ResponsibilitiesSummary.jsp'">职责汇总</a>
						</p>
						<p class="secondNav">
							<!--
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/soincumbent/ea_getStaffListForIncumbent.jspa'">员工汇总</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/cstaff_incumbent1.jsp'">员工汇总</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/mobilesoincumbent/ea_getStaffListForIncumbent.jspa'">员工汇总</a>
							-->
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/cstaff_incumbent.jsp'">员工汇总</a>
						</p>
						<p class="secondNav">
							<!--
							<a href="#"
								onclick="parent.left_tree.document.location.href='<%=basePath%>/page/mobile/main/human/office/production/cstaff_dimission.jsp'">离职管理</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/cosdimission/ea_getListCOSDimission.jspa'">离职管理</a>
							-->
							<a href="#"
								onclick="parent.document.location.href='<%=basePath%>/page/mobile/main/human/office/production/cstaff_dimission.jsp'">离职管理</a>
						</p>
						<p class="secondNav">
							<!-- 
								<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/jobplansummary.jsp'">计划汇总</a>
								<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/jobplan/ea_getJobPlanListSummary.jspa'">计划汇总</a>
							 -->
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/jobplansummary.jsp'">计划汇总</a>
						</p>
						<p class="secondNav">
							<!-- 
								<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/jobtask/ea_getJobTaskListSummary.jspa'">任务汇总</a>
							 -->
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/jobtasksummary.jsp'">任务汇总</a>
						</p>
						<%--
						<p class="secondNav">
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/staffappraisalsummary/ea_getStaffAppraisalList.jspa'">考评汇总</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/ea/main/human/office/production/SalaryIntergral_search.jsp'">工资管理</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getListPayScale.jspa'">级别管理</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getStaffPayScaleList.jspa'">员工级别</a>
						</p>
						--%>
						<p class="secondNav">
							<!--
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/mobilelogbooksummary/ea_getCOrganization.jspa'">日志汇总</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/logbooksummary/ea_getListLogBook.jspa'">日志汇总</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/mobilelogbooksummary/ea_getoList.jspa'">日志汇总</a>
							<a href="#"
								onclick="document.location.href='<%=basePath%>/page/mobile/main/human/office/production/LogBookSummary.jsp'">日志汇总</a>
						 	-->
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/mobilelogbooksummary/ea_getoList.jspa'">日志汇总</a>
						</p>
						<%--
						<p class="secondNav">
							<a href="#"
								onclick="document.location.href='<%=basePath%>/ea/loglock/ea_getListLogLock.jspa'">日志加锁</a>
						</p>
						--%>
					</div>
				</div>
			</div>
	</body>
</html>
