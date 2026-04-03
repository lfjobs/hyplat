<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公司-人事汇总</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script type="text/javascript">
function clickAction(action,parater){
if(parater == '1'){
 var treeID = '<%=session.getAttribute("organizationID")%>';
			window.location.href = action + treeID;
			return;
		}
		window.location.href = action;
	}
</script>
</head>
<body>
	<br />
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>人事部</strong>
							</div>
						</td>
						<td>
							<div class="na_back_img_jt_xs"></div></td>
						<td>
							<table>
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/ea/responsibilitiessummary/ea_getResponsibilitiesListSummary.jspa?companyID=','1')"></div>
										<div class="center_a">
											<span>职责汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/ea/soincumbent/ea_getCompanyListForIncumbent.jspa?result=','1')"></div>
										<div class="center_a">
											<span>员工汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/cosdimissionCompany/ea_getListCOSDimissionCompany.jspa'"></div>
										<div class="center_a">
											<span>离职汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/ea/jobplan/ea_getCompanyJobPlanListSummary.jspa?companyID=','1')"></div>
										<div class="center_a">
											<span>计划汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/ea/jobtask/ea_getCompanyJobTaskListSummary.jspa?companyID=','1')"></div>
										<div class="center_a">
											<span>任务汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/staffappraisalsummary/ea_getcompanyAppraisalList.jspa'"></div>
										<div class="center_a">
											<span>考评汇总</span>
										</div>
									</td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td><div class="na_back_img_jt_xx"></div></td>
						<td>
							<table>
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/page/ea/main/human/office/company/SalaryIntergral_search.jsp?result=','1')"></div>
										<div class="center_a">
											<span>工资汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getPayScaleList.jspa'"></div>
										<div class="center_a">
											<span>级别汇总</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getStaffPayScaleSummaryList.jspa'"></div>
										<div class="center_a">
											<span>员工级别</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"></div>
										<div class="center_a">
											<span>工资计算</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="clickAction('<%=basePath%>/ea/logbooksummary/ea_getListcompanyLogBook.jspa?result=','1')"></div>
										<div class="center_a">
											<span>日志汇总</span>
										</div>
									</td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>


</body>
</html>
