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
		<title>公司-人事汇总</title>
		<link href="<%=basePath%>css/mobilecss.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
<!--
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
-->
</style>
	<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
	<script type="text/javascript">
		function clickAction(action,parater){
			if(parater == '1'){
				/*var treeid =  parent.frames["leftFrame"].tree.getSelectedItemId();*/
				/*window.location.href= action+treeid;*/
				window.location.href= action;
				return;
			}
			window.location.href= action;
		}
	</script>
	</head>
	<body>
        <div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" class="actived"><span>人事部</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<div id="subNav2" align="center">
						<p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/ea/responsibilitiessummary/ea_getResponsibilitiesListSummary.jspa?companyID=','1')">职责汇总</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/ea/soincumbent/ea_getCompanyListForIncumbent.jspa?result=','1')">员工汇总</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="parent.left_tree.document.location.href='<%=basePath%>/ea/cosdimissionCompany/ea_getListCOSDimissionCompany.jspa'">离职汇总</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/ea/jobplan/ea_getCompanyJobPlanListSummary.jspa?companyID=','1')">计划汇总</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/ea/jobtask/ea_getCompanyJobTaskListSummary.jspa?companyID=','1')">任务汇总</a>
						</p>
                       <%--  <p class="secondNav">
							<a href="#"
								onClick="parent.left_tree.document.location.href='<%=basePath%>/ea/staffappraisalsummary/ea_getcompanyAppraisalList.jspa'">考评汇总</a>
						</p>
                        <p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/page/ea/main/human/office/company/SalaryIntergral_search.jsp?result=','1')">工资汇总</a>
						</p> --%>
                        <p class="secondNav">
							<a href="#"
								onClick="parent.left_tree.document.location.href='<%=basePath%>/ea/payscale/ea_getPayScaleList.jspa'">级别汇总</a>
						</p>
                        <p class="secondNav">
							<a href="#"
								onClick="parent.left_tree.document.location.href='<%=basePath%>/ea/payscale/ea_getStaffPayScaleSummaryList.jspa'">员工级别</a>
						</p>
                        <%-- <p class="secondNav">
							<a href="#"
								onClick="parent.left_tree.document.location.href='<%=basePath%>/ea/water/ea_getListForPage.jspa?'">工资计算</a>
						</p> --%>
                        <p class="secondNav">
							<a href="#"
								onClick="clickAction('<%=basePath%>/ea/logbooksummary/ea_getListcompanyLogBook.jspa?result=','1')">日志汇总</a>
						</p>
					</div>
				</div>
			</div>
	</body>
</html>
