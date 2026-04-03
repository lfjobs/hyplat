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
<title>人事-新工资模块导航</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	
</head>

<body>

	<br />
	<table style="width: 100%; border: 0; padding: 0; margin: 0">
		<tr>
			<td>
				<table>
					<tr>
						<td rowspan="2" width="120">
							<div class="na_back_img_ks"></div>
							<div class="center">
								<strong>基础数据管理</strong>
							</div></td>
						<td><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>/page/ea/main/human/adance/attendcycle.jsp?'"></div>
										<div class="center_a">核算周期预设</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>/page/ea/main/human/adance/attendworkhour.jsp?'"></div>
										<div class="center_a">正常工时设置</div></td>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/assort/ea_findItem.jspa'"></div>
										<div class="center_a">考评项分类</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/evaluation/ea_findItem.jspa'"></div>
										<div class="center_a">考评项管理</div></td>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/workcalendar/ea_toAdd.jspa?seaDate='"></div>
										<div class="center_a">工作日历</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/attendconf/ea_getAttendConf.jspa?'"></div>
										<div class="center_a">考勤预设</div></td>
								</tr>
								
							</table></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td rowspan="2" width="120">
							<div class="na_back_img_ks"></div>
							<div class="center">
								<strong>工资管理</strong>
							</div></td>
						<td><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/witem/ea_findWageItem.jspa'"></div>
										<div class="center_a">工资构成管理</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/wgrade/ea_findWageGrade.jspa'"></div>
										<div class="center_a">工资等级管理</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/wagestaff/ea_findItem.jspa'"></div>
										<div class="center_a">工资关联管理</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/score/ea_findItem.jspa'"></div>
										<div class="center_a">考评管理</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/payroll/ea_findItem.jspa'"></div>
										<div class="center_a">工资核算</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/tax/ea_findItem.jspa'"></div>
										<div class="center_a">个税管理</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/socialfund/ea_findItem.jspa'"></div>
										<div class="center_a">社保公积金管理</div></td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td rowspan="2" width="120">
							<div class="na_back_img_ks"></div>
							<div class="center">
								<strong>考勤管理</strong>
							</div></td>
						<td><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/attendlog/ea_getAttendLog.jspa?seaDate='"></div>
										<div class="center_a">个人考勤</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/attendlog/ea_getLogCom.jspa?'"></div>
										<div class="center_a">考勤汇总</div></td>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/attendextleave/ea_getAttendExtleave.jspa?'"></div>
										<div class="center_a">加班请假</div></td>
									<td width="110">
										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/attendtrim/ea_getAttendTrim.jspa?'"></div>
										<div class="center_a">考勤调整</div></td>
									<td width="110">

										<div class="na_back_img"
                                        	onclick="document.location.href='<%=basePath%>ea/buckl/ea_getAwarBuckl.jspa?'"></div>
										<div class="center_a">奖扣记录汇总</div></td>
								</tr>
								
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
