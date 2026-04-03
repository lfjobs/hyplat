<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>单条打印所有员工工资</title>
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
	font-size: 10px;
}

.table {
	border-collapse: collapse;
	border: 1px solid ;
}

.table th {
	border: 1px solid;
	
}

.table td {
	border: 1px solid;
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
						<div style="height: 520px; overflow: hidden;">
							<div style="padding: 60px"></div>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" height="35" style="margin-top: 15px;">
								<tr>
									<s:if test="arg==1">
										<td align="center" >
											<strong>${companyname}${entity.logBookKey}月份工资条 </strong>
										</td>
									</s:if>
									<s:else>
										<td align="center">
											<strong>${companyname}${sdate}--${edate}月份工资条 </strong>
										</td>
									</s:else>
								</tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>公司：</td>
									<td>${companyname}</td>
									<td>责任人：</td>
									<td>${entity.staffName }</td>
									<td>制单日期：</td>
									<td>_____年____月___日</td>
								</tr>
								
							</table>
								<table width="643" align="center" cellpadding="0"
									cellspacing="0" class="table">
									<tr>
										<th height="20" align="center" 
											colspan="${fn:length(add)+(arg==1?18:17)}">
											考评
										</th>
										<th height="20" align="center" 
											colspan="${fn:length(cut)+7}">
											折扣
										</th>
										<th width="20" height="20" align="center" 
											rowspan="2">
											应得积分
										</th>
										<th width="20" height="20" align="center" 
											rowspan="2">
											实得积分
										</th>
										<th width="20" height="20" align="center" 
											rowspan="2">
											实得工资
										</th>
										<th width="30" height="20" align="center" 
											rowspan="2">
											公司承担积分
										</th>
									</tr>
									<tr>
										<s:if test="arg==1">
											<th width="90" height="20" align="center" >
												日期月份
											</th>
										</s:if>
										<th width="50" height="20" align="center" >
											姓 名
										</th>
										<th width="100" height="20" align="center">
											工种类别
										</th>
										<th width="100" height="20" align="center" >
											岗位名称
										</th>
										<th width="30" height="20" align="center" >
											职务职责积分
										</th>
										<th width="20" height="20" align="center" >
											基本积分
										</th>
										<th width="20" height="20" align="center" >
											目标任务考核积分
										</th>
										<th width="20" height="20" align="center" >
											加班积分
										</th>
										<th width="30" height="20" align="center" >
											月考评积分
										</th>
										<th width="20" height="20" align="center" >
											奖励积分
										</th>
										<th width="30" height="20" align="center" >
											特殊人才积分
										</th>
										<th width="30" height="20" align="center" >
											保密工资积分
										</th>
										<th width="20" height="20" align="center" >
											安全积分
										</th>
										<th width="20" height="20" align="center" >
											孝道金积分
										</th>
										<th width="20" height="20" align="center" >
											竞职金积分
										</th>
										<th width="20" height="20" align="center" >
											话费补助积分
										</th>
										<th width="20" height="20" align="center" >
											生活补助积分
										</th>
										<%--<th width="20" height="20" align="center" >
								计件积分
							</th>
							--%>
										<th width="20" height="20" align="center" >
											任务得分
										</th>
										<s:iterator value="add" var="a">
											<th width="20" height="20" align="center" >
												${a}
											</th>
										</s:iterator>
										<%--<th width="20" height="20" align="center" >
								生活补助
							</th>
							--%>
										<th width="30" height="20" align="center" >
											个人所得税
										</th>
										<th width="30" height="20" align="center" >
											个人应交社保
										</th>
										<th width="30" height="20" align="center" >
											个人应交公积金
										</th>
										<th width="20" height="20" align="center" >
											违规折扣
										</th>
										<th width="20" height="20" align="center" >
											任务折扣
										</th>
										<th width="30" height="20" align="center" >
											安全积分
										</th>
										<th width="20" height="20" align="center" >
											考勤折扣
										</th>
										<s:iterator value="cut" var="c">
											<th width="20" height="20" align="center" >
												${c}
											</th>
										</s:iterator>
									</tr>
									<tr>
										<c:set value="0" var="result" />
										<c:set value="0" var="discount" />
										<!-- 日期月份 -->
										<s:if test="arg==1">
											<td align="center" >
												${entity.logBookKey}
											</td>
										</s:if>
										<!-- 姓名 -->
										<td align="center" >
											${entity.staffName }
										</td>
										<!-- 工种类别 -->
										<td align="center" >
											${entity.categoryname }
										</td>
										<!-- 岗位名称 -->
										<td align="center" >
											${entity.postname }
										</td>
										<!-- 职务职责积分 -->
										<td align="center" >
											${entity.funzioneIntegral }
											<c:set value="${result+entity.funzioneIntegral}" var="result" />
										</td>
										<!-- 基本积分 -->
										<td align="center" >
											${entity.basicIntegral }
											<c:set value="${result+entity.basicIntegral}" var="result" />
										</td>
										<!-- 目标任务考核积分 -->
										<td align="center">
											${entity.targetTaskIntegral }
											<c:set value="${result+entity.targetTaskIntegral}"
												var="result" />
										</td>
										<!-- 加班积分 -->
										<td align="center" >
											${entity.workNightIntegral+entity.weekendIntegral+entity.workHolidaysIntegral
											}
											<c:set
												value="${result+entity.workNightIntegral+entity.weekendIntegral+entity.workHolidaysIntegral}"
												var="result" />
										</td>
										<!-- 月考评积分 -->
										<td align="center" >
											${entity.appraisalIntegral }
											<c:set value="${result+entity.appraisalIntegral}"
												var="result" />
										</td>
										<!-- 奖励积分 -->
										<td align="center" >
											${entity.rewardIntegral }
											<c:set value="${result+entity.rewardIntegral}" var="result" />
										</td>
										<!-- 特殊人才积分 -->
										<td align="center" >
											${entity.stPay }
											<c:set value="${result+entity.stPay}" var="result" />
										</td>
										<!-- 保密工资积分 -->
										<td align="center" >
											${entity.secrecyPay }
											<c:set value="${result+entity.secrecyPay}" var="result" />
										</td>
										<!-- 安全积分 -->
										<td align="center" >
											${entity.safetyIntegral }
											<c:set value="${result+entity.safetyIntegral}" var="result" />
										</td>
										<!-- 安全积分 -->
										<td align="center" >
											${entity.pietypay }
											<c:set value="${result+entity.pietypay}" var="result" />
										</td>
										<!-- 安全积分 -->
										<td align="center" >
											${entity.campaignpay }
											<c:set value="${result+entity.campaignpay}" var="result" />
										</td>
										<!-- 安全积分 -->
										<td align="center" >
											${entity.telecompay }
											<c:set value="${result+entity.telecompay}" var="result" />
										</td>
										<!-- 安全积分 -->
										<td align="center" >
											${entity.living }
											<c:set value="${result+entity.living}" var="result" />
										</td>
										<!-- 计件积分 -->
										<%--<td align="center" >
								${entity.pieceIntegral }
								<c:set value="${result+entity.pieceIntegral}" var="result" />
							</td>
							--%>
										<!-- 任务得分 -->
										<td align="center" >
											${entity.taskIntegral }
											<c:set value="${result+entity.taskIntegral}" var="result" />
										</td>
										<c:forEach items="${entity.customWageAdd}" var="add">
											<td align="center" >
												${add}
											</td>
											<c:set value="${result+add}" var="result"></c:set>
										</c:forEach>
										<!-- 生活补助 -->
										<%--
							<td align="center" >
								${entity.phoneSubsidy + entity.livingSubsidy }
								<c:set
									value="${result+entity.phoneSubsidy + entity.livingSubsidy}"
									var="result" />
							</td>
							--%>
										<!-- 个人所得税 -->
										<td align="center" >
											${entity.personalDiscount }
											<c:set value="${discount+entity.personalDiscount}"
												var="discount" />
										</td>
										<!-- 个人应交社保 -->
										<td align="center" >
											${entity.personalSocialSecurity }
											<c:set value="${discount+entity.personalSocialSecurity}"
												var="discount" />
										</td>
										<!-- 个人应交公积金 -->
										<td align="center" >
											${entity.personalReservedFunds }
											<c:set value="${discount+entity.personalReservedFunds}"
												var="discount" />
										</td>
										<!-- 违规折扣 -->
										<td align="center" >
											${entity.violationDiscount }
											<c:set value="${discount+entity.violationDiscount}"
												var="discount" />
										</td>
										<!-- 任务折扣 -->
										<td align="center" >
											${entity.taskDiscount }
											<c:set value="${discount+entity.taskDiscount}" var="discount" />
										</td>
										<!-- 暂扣安全积分 -->
										<td align="center" >
											${entity.safetyDiscount }
											<c:set value="${discount+entity.safetyDiscount}"
												var="discount" />
										</td>
										<!-- 考勤折扣 -->
										<td align="center" >
											${entity.attendanceDiscount }
											<c:set value="${discount+entity.attendanceDiscount}"
												var="discount" />
										</td>
										<c:forEach items="${entity.customWageCut}" var="cut">
											<td align="center" >
												${cut}
											</td>
											<c:set value="${discount+cut}" var="discount"></c:set>
										</c:forEach>
										<td align="center" >
											<span class="result"> <script>
										var tt = ${result };
										document.write(tt.toFixed(3));
									</script> </span>
										</td>
										<td align="center" >
											<span> <script>
										var tt = ${result-discount};
										document.write(tt.toFixed(3));
									</script> </span>
										</td>
										<td align="center" >
											<span> 
										<script>
										var tt = ${(result-discount)*20};
										document.write(tt.toFixed(2));
									</script> </span>
										</td>
										<td align="center" >
											${entity.holidaysIntegral }
										</td>
									</tr>
								</table>
							<table width="643" border="0" align="center" cellpadding="10"
								   cellspacing="0" >
								<tr>
									<td  align="left" colspan="8" style="">
										备注：本月本人工资已核对清楚，无误。
									</td>

								</tr>
							</table>
							<table width="643" border="0" align="center" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td width="70" height="30" align="center">
										公司经理：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										部门主管：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="80" align="center">
										人事处：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										财务审核：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										责任人签字：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="90" height="30" align="center">
										总部总经理：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="100" align="center">
										总部部门主管：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										总部人事处：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
									<td width="90" align="center">
										总财务审核：
									</td>
									<td width="100" align="center">
										&nbsp;
									</td>
								</tr>
							</table>


						</div>
					</c:forEach>
				</td>
			</tr>
		</table>
	</body>
</html>