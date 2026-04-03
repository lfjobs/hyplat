<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>单条打印离职员工工资</title>
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
						<s:if test="arg==1">
					             <td align="center" bgcolor="#FFFFFF">
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
				<div style="height: 130px;">
				<table width="643" align="center" cellpadding="0" cellspacing="0"
						class="table">
						<tr>
							<th height="20" align="center" bgcolor="#E4F1FA"
								colspan="${fn:length(add)+(arg==1?13:12)}">
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
						<s:if test="arg==1">
	                        <th width="90" height="20" align="center" bgcolor="#E4F1FA">
								 日期月份
							</th>
                   </s:if>
							<th width="50" height="20" align="center" bgcolor="#E4F1FA">
								姓 名
							</th>
							<th width="100" height="20" align="center" bgcolor="#E4F1FA">
								工种类别
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
							<%--<th width="20" height="20" align="center" bgcolor="#E4F1FA">
								计件积分
							</th>
							--%><th width="20" height="20" align="center" bgcolor="#E4F1FA">
								任务得分
							</th>
							<s:iterator value="add"  var="a">
		                       	<th width="20" height="20" align="center" bgcolor="#E4F1FA">
		                       		${a}
		                       	</th>
		                       </s:iterator>
							<%--<th width="20" height="20" align="center" bgcolor="#E4F1FA">
								生活补助
							</th>
							--%><th width="30" height="20" align="center" bgcolor="#E4F1FA">
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
								暂扣安全积分
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
							<c:set value="0" var="result" />
							<c:set value="0" var="discount" />
							<!-- 日期月份 -->
								<s:if test="arg==1">
					             <td align="center" bgcolor="#FFFFFF">
			                        ${entity.logBookKey}
			                     </td>
			                     </s:if>
							<!-- 姓名 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.staffName }
							</td>
							<!-- 工种类别 -->
							<td align="center" bgcolor="#FFFFFF">
								离职员工
							</td>
							<!-- 职务职责积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.funzioneIntegral }
								<c:set value="${result+entity.funzioneIntegral}" var="result" />
							</td>
							<!-- 基本积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.basicIntegral }
								<c:set value="${result+entity.basicIntegral}" var="result" />
							</td>
							<!-- 目标任务考核积分 -->
							<td align="center">
								${entity.targetTaskIntegral }
								<c:set value="${result+entity.targetTaskIntegral}" var="result"/>
							</td>
							<!-- 加班积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.workNightIntegral+entity.weekendIntegral+entity.workHolidaysIntegral
								}
								<c:set
									value="${result+entity.workNightIntegral+entity.weekendIntegral+entity.workHolidaysIntegral}"
									var="result" />
							</td>
							<!-- 月考评积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.appraisalIntegral }
								<c:set value="${result+entity.appraisalIntegral}" var="result" />
							</td>
							<!-- 奖励积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.rewardIntegral }
								<c:set value="${result+entity.rewardIntegral}" var="result" />
							</td>
							<!-- 特殊人才积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.stPay }
								<c:set value="${result+entity.stPay}" var="result" />
							</td>
							<!-- 保密工资积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.secrecyPay }
								<c:set value="${result+entity.secrecyPay}" var="result" />
							</td>
							<!-- 安全积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.safetyIntegral }
								<c:set value="${result+entity.safetyIntegral}" var="result" />
							</td>
							<!-- 计件积分 -->
							<%--<td align="center" bgcolor="#FFFFFF">
								${entity.pieceIntegral }
								<c:set value="${result+entity.pieceIntegral}" var="result" />
							</td>
							--%><!-- 任务得分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.taskIntegral }
								<c:set value="${result+entity.taskIntegral}" var="result" />
							</td>
							<c:forEach	items="${entity.customWageAdd}" var="add" >
                     			<td align="center" bgcolor="#FFFFFF">
                            		${add}	
                            	</td>
                        	  <c:set value="${result+add}" var="result"></c:set>  	
                   			  </c:forEach>
							<!-- 生活补助 --><%--
							<td align="center" bgcolor="#FFFFFF">
								${entity.phoneSubsidy + entity.livingSubsidy }
								<c:set
									value="${result+entity.phoneSubsidy + entity.livingSubsidy}"
									var="result" />
							</td>
							--%><!-- 个人所得税 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.personalDiscount }
								<c:set value="${discount+entity.personalDiscount}"
									var="discount" />
							</td>
							<!-- 个人应交社保 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.personalSocialSecurity }
								<c:set value="${discount+entity.personalSocialSecurity}"
									var="discount" />
							</td>
							<!-- 个人应交公积金 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.personalReservedFunds }
								<c:set value="${discount+entity.personalReservedFunds}"
									var="discount" />
							</td>
							<!-- 违规折扣 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.violationDiscount }
								<c:set value="${discount+entity.violationDiscount}"
									var="discount" />
							</td>
							<!-- 任务折扣 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.taskDiscount }
								<c:set
									value="${discount+entity.taskDiscount}"
									var="discount" />
							</td>
							<!-- 暂扣安全积分 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.safetyDiscount }
								<c:set value="${discount+entity.safetyDiscount}" var="discount" />
							</td>
							<!-- 考勤折扣 -->
							<td align="center" bgcolor="#FFFFFF">
								${entity.attendanceDiscount }
								<c:set value="${discount+entity.attendanceDiscount}"
									var="discount" />
							</td>
                   			 <c:forEach	items="${entity.customWageCut}" var="cut" >
                     			<td align="center" bgcolor="#FFFFFF">
                            	${cut}	
                            	</td>
                       	    <c:set value="${discount+cut}" var="discount"></c:set> 	
                   			  </c:forEach>
							<td align="center" bgcolor="#FFFFFF">
								<span class="result">
									<script>
										var tt = ${result };
										document.write(tt.toFixed(3));
									</script>
								</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>
									<script>
										var tt = ${result-discount};
										document.write(tt.toFixed(3));
									</script> 
								</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>
									<script>
										var tt = ${(result-discount)*20 };
										document.write(tt.toFixed(2));
									</script>
								</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								${entity.holidaysIntegral }
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