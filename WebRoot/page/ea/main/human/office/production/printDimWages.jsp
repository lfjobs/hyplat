<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>统计离职员工工资打印</title>
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
body,th {
	font-size: 12px;
}

.table {
	border-collapse: collapse;
	border: 1px solid;
	font-size: 12px;
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

.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
.STYLE2 {
	font-size: 16px;
}
</style>
	</head>
	<body
		style="height: 100%; position: absolute; overflow: scroll; margin-left: 10px;">
		<table id="content" width="940px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<td height="32" colspan="${fn:length(add)+fn:length(cut)+(arg==1?28:27)}" align="center">
					<span class="STYLE1">${companyname}${titleVar}</span>
				</td>
			</tr>
			<tr>
				<th height="120" align="center" width="40">
					序号
				</th>
				<s:if test="arg==1">
	                         <th width="90" align="center">
	                            日期月份
	                        </th>
                   </s:if>
				<th align="center" width="80">
					姓名
				</th>
				<th align="center" width="110">
					工种类别
				</th>
				<th align="center" width="30">
					职务职责积分
				</th>
				<th align="center" width="20">
					基本积分
				</th>
				<th align="center" width="20">
					目标任务考核计分
				</th>
				<th align="center" width="30">
					周末加班积分
				</th>
				<th align="center" width="20">
					晚上加班
				</th>
				<th align="center" width="30">
					节假日加班积分
				</th>
				<th align="center" width="30">
					月考评积分
				</th><%--
				<th align="center" width="20">
					计件积分
				</th>
				--%><th align="center" width="20">
					奖励积分
				</th>
				<th align="center" width="40">
					特殊人才积分
				</th>
				<th align="center" width="30">
					保密工资积分
				</th>
				<th align="center" width="20">
					安全积分
				</th>
				<th align="center" width="20">
					任务得分
				</th><%--
				<th align="center" width="20">
					补助话费
				</th>
				<th align="center" width="20">
					生活补助
				</th>
				--%>
				<s:iterator value="add"  var="a">
                       	<th align="center" width="40">
                       		${a}
                       	</th>
                </s:iterator>
				<th align="center" width="30">
					个人所得税
				</th>
				<th align="center" width="30">
					个人应交社保
				</th>
				<th align="center" width="30">
					个人应交公积金
				</th><%--
				<th align="center" width="20">
					扣电话费
				</th>
				<th align="center" width="20">
					扣生活费
				</th>
				--%><th align="center" width="20">
					违规折扣
				</th>
				<th align="center" width="20">
					任务折扣
				</th>
				<th align="center" width="30">
					暂扣安全积分
				</th>
				<th align="center" width="20">
					考勤折扣
				</th>
                        <s:iterator value="cut" var="c">
                       	<th align="center" width="40">
                       		${c}
                       	</th>
                       </s:iterator>
				<th align="center" width="55">
					应得积分
				</th>
				<th align="center" width="55">
					实得积分
				</th>
				<th align="center" width="60">
					实得工资
				</th>
				<th align="center" width="40">
					公司承担积分
				</th>
			
				<th align="center" width="80">
					签字
				</th>
			</tr>
			<c:forEach var="entity" items="${wages}" varStatus="step">
			<c:set value="0" var="result" />
			<c:set value="0" var="discount" />
				<tr>
					<td align="center" height="25">
						${step.index+1 }
					</td>
					<!-- 日期月份 -->
					<s:if test="arg==1">
		             <td align="center">
                        ${entity.logBookKey}
                     </td>
                     </s:if>
					<!-- 姓名 -->
					<td align="center">
						${entity.staffName }
					</td>
					<!-- 工种类别 -->
					<td align="center">
						离职员工
					</td>
					<!-- 职务职责积分 -->
					<td align="center">
						${entity.funzioneIntegral }
						<c:set value="${result+entity.funzioneIntegral}" var="result"></c:set>
					</td>
					<!-- 基本积分 -->
					<td align="center">
						${entity.basicIntegral }
					<c:set value="${result+entity.basicIntegral}" var="result"></c:set>
					</td>
					<!-- 目标任务考核积分 -->
					<td align="center">
						${entity.targetTaskIntegral }
					<c:set value="${result+entity.targetTaskIntegral}" var="result"></c:set>
					</td>
					<!-- 周末加班积分 -->
					<td align="center">
						${entity.weekendIntegral }
						<c:set value="${result+entity.weekendIntegral}" var="result"></c:set>
					</td>
					<!-- 工作日加班积分 -->
					<td align="center">
						${entity.workNightIntegral }
						<c:set value="${result+entity.workNightIntegral}" var="result"></c:set>
					</td>
					<!-- 节假日加班积分 -->
					<td align="center">
						${entity.workHolidaysIntegral }
						<c:set value="${result+entity.workHolidaysIntegral}" var="result"></c:set>
					</td>
					<!-- 月考评积分 -->
					<td align="center">
						${entity.appraisalIntegral }
						<c:set value="${result+entity.appraisalIntegral}" var="result"></c:set>
					</td>
					<!-- 计件积分 -->
					<%--<td align="center">
						${entity.pieceIntegral }
						<c:set value="${result+entity.pieceIntegral}" var="result"></c:set>
					</td>
					--%><!-- 奖励积分 -->
					<td align="center">
						${entity.rewardIntegral }
						<c:set value="${result+entity.rewardIntegral}" var="result"></c:set>
					</td>
					<!-- 特殊人才积分 -->
					<td align="center">
						${entity.stPay }
						<c:set value="${result+entity.stPay}" var="result"></c:set>
					</td>
					<!-- 保密工资积分 -->
					<td align="center">
						${entity.secrecyPay }
						<c:set value="${result+entity.secrecyPay}" var="result"></c:set>
					</td>
					<!-- 安全积分 -->
					<td align="center">
						${entity.safetyIntegral }
						<c:set value="${result+entity.safetyIntegral}" var="result"></c:set>
					</td>
					<!-- 任务得分 -->
					<td align="center">
						${entity.taskIntegral }
						<c:set value="${result+entity.taskIntegral}" var="result"></c:set>
					</td>
					<!-- 补助电话 -->
					<%--<td align="center">
						${entity.phoneSubsidy }
						<c:set value="${result+entity.phoneSubsidy}" var="result"></c:set>
					</td>
					--%><!-- 补助生活 -->
					<%--<td align="center">
						${entity.livingSubsidy }
						<c:set value="${result+entity.livingSubsidy}" var="result"></c:set>
					</td>
					--%><!-- 个人所得税 -->
					 <c:forEach	items="${entity.customWageAdd}" var="add" >
                     			<td align="center">
                            		${add}	
                            	</td>
                          <c:set value="${result+add}" var="result"></c:set>  	
                     </c:forEach>
                     
					<td align="center">
						${entity.personalDiscount }
						<c:set value="${discount+entity.personalDiscount}" var="discount"></c:set>
					</td>
					<!-- 个人应交社保 -->
					<td align="center">
						${entity.personalSocialSecurity }
						<c:set value="${discount+entity.personalSocialSecurity}" var="discount"></c:set>
					</td>
					<!-- 个人应交公积金 -->
					<td align="center">
						${entity.personalReservedFunds }
						<c:set value="${discount+entity.personalReservedFunds}" var="discount"></c:set>
					</td>
					<!-- 扣电话费 --><%--
					<td align="center">
						${entity.phoneDiscount }
						<c:set value="${discount+entity.phoneDiscount}" var="discount"></c:set>
					</td>
					--%><!-- 扣生活费 -->
					<%--<td align="center">
						${entity.liveDiscount }
						<c:set value="${discount+entity.liveDiscount}" var="discount"></c:set>
					</td>
					--%><!-- 违规折扣 -->
					<td align="center">
						${entity.violationDiscount }
						<c:set value="${discount+entity.violationDiscount}" var="discount"></c:set>
					</td>
					<!-- 任务折扣 -->
					<td align="center">
						${entity.taskDiscount }
						<c:set value="${discount+entity.taskDiscount}" var="discount"></c:set>
					</td>
					<!-- 暂扣安全积分 -->
					<td align="center">
						${entity.safetyDiscount }
						<c:set value="${discount+entity.safetyDiscount}" var="discount"></c:set>
					</td>
					<!-- 考勤折扣 -->
					<td align="center">
						${entity.attendanceDiscount }
						<c:set value="${discount+entity.attendanceDiscount}" var="discount"></c:set>
					</td>
                     <c:forEach	items="${entity.customWageCut}" var="cut" >
                     			<td align="center">
                            	${cut}	
                            	</td>
                           <c:set value="${discount+cut}" var="discount"></c:set> 	
                     </c:forEach>
					<td align="center"> 
						<script>
						var tt = ${result }; 
						document.write("<span class='result'>" + tt.toFixed(3) + "</span>");
						</script> 
					</td>
					<td align="center"> 
						<script>
						var tt = ${result-discount}; 
						document.write("<span class='fentotal'>" + tt.toFixed(3) + "</span>");
						</script> 
					</td>
					<td align="center"> 
						<script>
						var tt = ${(result-discount)*20 };
						document.write("<span class='qiantotal'>" + tt.toFixed(2) + "</span>");
						</script> 
					</td>
					<td align="center">
						${entity.holidaysIntegral }
					</td>
					<td align="center">
					</td>
				</tr>

			</c:forEach>
			<tr>
				<td align="center" colspan="2">
					合计：
				</td>
				<s:if test="arg==1">
	                 <td align="center">
					&nbsp;
				</td>
                   </s:if>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<s:iterator value="add" status="i">
                       	<th align="center" >
                       		&nbsp;
                       	</th>
                       </s:iterator>
                        <s:iterator value="cut" status="i">
                       	<th align="center" >
                       		&nbsp;
                       	</th>
                       </s:iterator>
				<td align="center">
					<span class="total"></span>
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center">
					<span class="wargs"></span>
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="center"></td>
			</tr>
			<tr>
				<td align="center">
				</td>
				<td align="center" colspan="2"
					style="font: small-caps 600 12pts/ 16pts 宋体;">
					总经理：
				</td>
				<td colspan="3" align="center">
				</td>
				<td colspan="4" align="center"
					style="font: small-caps 600 12pts/ 16pts 宋体;">
					部门主管：
				</td>
				<td colspan="3" align="center">
				</td>
				<td colspan="4" align="center"
					style="font: small-caps 600 12pts/ 16pts 宋体;">
					人事主管：
				</td>
				<td colspan="3" align="center">
				</td>
				<td colspan="4" align="center"
					style="font: small-caps 600 12pts/ 16pts 宋体;">
					人事文员：
				</td>
				<td colspan="${fn:length(add)+fn:length(cut)+(arg==1?4:3)}" align="center">
				</td>
			</tr>
		</table>
		<script>
			$(document).ready(function(){
				var totle = 0.0;
				$("span.qiantotal").each(function(){
					totle = totle + parseFloat($.trim($(this).text()));
				});
				
				var result = 0.0;
				$("span.result").each(function(){
					result = result + parseFloat($.trim($(this).text()));
				});
				$(".total").text(result.toFixed(3));
				$(".wargs").text(totle.toFixed(2));
			});
		</script>
	</body>
</html>