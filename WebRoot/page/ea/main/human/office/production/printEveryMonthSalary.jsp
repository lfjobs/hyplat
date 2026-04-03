<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>单条打印所有员工月工资</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}

.table {
	border-collapse: collapse;
	border: 1px solid #a8c7ce;
	font-size: 12px;
}

.table th {
	border: 1px solid #a8c7ce;
	color: #1E5494;
	background: #E4F1FA;
}

.table td {
	border: 1px solid #a8c7ce;
	color: #333;
}

-->
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
	</head>
	<body
		style="height: 100%; position: absolute; overflow: scroll; width: 700px; left: 50%; margin-left: -350px;">
		<table id="content">
			<tr>
				<td>
					<c:forEach var="entity" items="${wages}">
						<div style="height: 450px; overflow: hidden;">
							<div style="padding: 60px"></div>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" height="35" style="margin-top: 15px;">
								<tr>
									<td align="center" bgcolor="#FFFFFF">
										<strong>${monthSalary.companyname}${fn:replace(monthSalary.months, "-","年")}月工资条 </strong>
									</td>
								</tr>
							</table>
							<div style="height: 130px;">
								<table width="643" align="center" cellpadding="0"
									cellspacing="0" class="table">
									<tr>
										<th height="20" align="center" bgcolor="#E4F1FA"
											colspan="${fn:length(add)+13}">
											考评
										</th>
										<th height="20" align="center" bgcolor="#E4F1FA"
											colspan="${fn:length(cut)+7}">
											折扣
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA"
											rowspan="2">
											应得积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA"
											rowspan="2">
											实得积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA"
											rowspan="2">
											实得工资
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA"
											rowspan="2">
											公司承担积分
										</th>
									</tr>
									<tr>
										<th width="50" height="20" align="center" bgcolor="#E4F1FA">
											姓 名
										</th>
										<th width="100" height="20" align="center" bgcolor="#E4F1FA">
											工种类别
										</th>
										<th width="100" height="20" align="center" bgcolor="#E4F1FA">
											岗位名称
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											职务职责积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											基本积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											目标任务考核积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											加班积分
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											月考评积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											奖励积分
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											特殊人才积分
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											保密工资积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											安全积分
										</th>
										
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											任务得分
										</th>
										<s:iterator value="add" var="a">
											<th width="20" height="20" align="center" bgcolor="#E4F1FA">
												${a}
											</th>
										</s:iterator>
										
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											个人所得税
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											个人应交社保
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											个人应交公积金
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											违规折扣
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											任务折扣
										</th>
										<th width="30" height="20" align="center" bgcolor="#E4F1FA">
											安全积分
										</th>
										<th width="20" height="20" align="center" bgcolor="#E4F1FA">
											考勤折扣
										</th>
										<s:iterator value="cut" var="c">
											<th width="20" height="20" align="center" bgcolor="#E4F1FA">
												${c}
											</th>
										</s:iterator>
									</tr>
									<tr>
										<!-- 姓名 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.staffname }
										</td>
										<!-- 工种类别 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.categoryname }
										</td>
										<!-- 岗位名称 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.postname }
										</td>
										<!-- 职务职责积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.funzioneintegral }
										</td>
										<!-- 基本积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.basicintegral }
										</td>
										<!-- 目标任务考核积分 -->
										<td align="center">
											${entity.targettaskintegral }
										</td>
										<!-- 加班积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.weekendintegral+entity.worknightintegral+entity.workholidaysintegral
											}
										</td>
										<!-- 月考评积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.appraisalintegral }
										</td>
										<!-- 奖励积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.rewardintegral }
										</td>
										<!-- 特殊人才积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.stpay }
										</td>
										<!-- 保密工资积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.secrecypay }
										</td>
										<!-- 安全积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.safetyintegral }
										</td>
										<!-- 任务得分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.taskintegral }
										</td>
										
										<c:if test="${addlength != 0}">
					                       <c:forEach var="i" begin="0" end="${addlength}" step="1">
					                         <td>
					                          	${fn:split(fn:substring(entity.customwageadd,1,fn:length(entity.customwageadd)-1),',')[i]}
					                         </td>
									       </c:forEach>
									    </c:if>
										
										<!-- 个人所得税 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.personaldiscount }
										</td>
										<!-- 个人应交社保 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.personalsocialsecurity }
										</td>
										<!-- 个人应交公积金 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.personalreservedfunds }
										</td>
										<!-- 违规折扣 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.violationdiscount }
										</td>
										<!-- 任务折扣 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.taskdiscount }
										</td>
										<!-- 暂扣安全积分 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.safetydiscount }
										</td>
										<!-- 考勤折扣 -->
										<td align="center" bgcolor="#FFFFFF">
											${entity.attendancediscount }
										</td>
										
										<c:if test="${cutlength != 0}">
									    <c:forEach var="i" begin="0" end="${cutlength}" step="1">
					                      <td>
					                        ${fn:split(fn:substring(entity.customwagecut,1,fn:length(entity.customwagecut)-1),',')[i]}
					                      </td>
									    </c:forEach>
									    </c:if>
										
										<td align="center" bgcolor="#FFFFFF">
											<script>
											var tt = ${entity.dueintegral };
											document.write("<span class='result'>" +tt.toFixed(3) + "</span>");
											</script>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<script>
											var tt = ${entity.obtainedintegral };
											document.write("<span>" +tt.toFixed(3) + "</span>");
											</script>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<script>
											var tt = ${entity.obtainedmenoy };
											document.write("<span>" + tt.toFixed(2) + "</span>");
											</script>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											${entity.holidaysintegral }
										</td>
									</tr>
								</table>
							</div>
							<div style="padding: 20px"></div>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" style="background: #FFFFFF;">
								<tr>
									<td width="70" height="30" align="center">
										总经理：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										领款人：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="80" align="center">
										领款时间：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>

								</tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr></tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" style="background: #FFFFFF;">
								<tr>
									<td width="80" height="30" align="center">
										人事：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										会计：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="80" align="center">
										出纳：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>

								</tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr></tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td style="line-height: 15px">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第1联&nbsp;（实发工资领取联）
										<br />
								</tr>
							</table>
						</div>
					</c:forEach>
				</td>
			</tr>
		</table>
	</body>
</html>