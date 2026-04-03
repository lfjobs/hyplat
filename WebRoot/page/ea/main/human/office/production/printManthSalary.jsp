<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>统计员工月工资打印</title>
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
		style=" overflow: scroll; margin-left: 10px;text-align: center">
		<table id="content" width="940px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<td height="32" colspan="${fn:length(add)+fn:length(cut)+28}" align="center">
					<span class="STYLE1">${monthSalary.companyname}${fn:replace(monthSalary.months, "-","年")}月</span>
				</td>
			</tr>
			<tr>
				<th height="120" align="center" width="40">
					序号
				</th>
				<th align="center" width="80">
					姓名
				</th>
				<th align="center" width="110">
					工种类别
				</th>
				<th align="center" width="110">
					岗位名称
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
				</th>
				<th align="center" width="20">
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
				</th>
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
				</th>
				<th align="center" width="20">
					违规折扣
				</th>
				<th align="center" width="20">
					任务折扣
				</th>
				<th align="center" width="30">
					安全积分
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
				<tr>
					<td align="center" height="25">
						${step.index+1 }
					</td>
					<!-- 姓名 -->
					<td align="center">
						${entity.staffname }
					</td>
					<!-- 工种类别 -->
					<td align="center">
						${entity.categoryname }
					</td>
					<!-- 岗位名称 -->
					<td align="center" bgcolor="#FFFFFF">
						${entity.postname }
					</td>
					<!-- 职务职责积分 -->
					<td align="center">
						${entity.funzioneintegral }
					</td>
					<!-- 基本积分 -->
					<td align="center">
						${entity.basicintegral }
					</td>
					<!-- 目标任务考核积分 -->
					<td align="center">
						${entity.targettaskintegral }
					</td>
					<!-- 周末加班积分 -->
					<td align="center">
						${entity.weekendintegral }
					</td>
					<!-- 工作日加班积分 -->
					<td align="center">
						${entity.worknightintegral }
					</td>
					<!-- 节假日加班积分 -->
					<td align="center">
						${entity.workholidaysintegral }
					</td>
					<!-- 月考评积分 -->
					<td align="center">
						${entity.appraisalintegral }
					</td>
					<!-- 奖励积分 -->
					<td align="center">
						${entity.rewardintegral }
					</td>
					<!-- 特殊人才积分 -->
					<td align="center">
						${entity.stpay }
					</td>
					<!-- 保密工资积分 -->
					<td align="center">
						${entity.secrecypay }
					</td>
					<!-- 安全积分 -->
					<td align="center">
						${entity.safetyintegral }
					</td>
					<!-- 任务得分 -->
					<td align="center">
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
					<td align="center">
						${entity.personaldiscount }
					</td>
					<!-- 个人应交社保 -->
					<td align="center">
						${entity.personalsocialsecurity }
					</td>
					<!-- 个人应交公积金 -->
					<td align="center">
						${entity.personalreservedfunds }
					</td>
					<!-- 违规折扣 -->
					<td align="center">
						${entity.violationdiscount }
					</td>
					<!-- 任务折扣 -->
					<td align="center">
						${entity.taskdiscount }
					</td>
					<!-- 暂扣安全积分 -->
					<td align="center">
						${entity.safetydiscount }
					</td>
					<!-- 考勤折扣 -->
					<td align="center">
						${entity.attendancediscount }
					</td>
					
					<c:if test="${cutlength != 0}">
				    <c:forEach var="i" begin="0" end="${cutlength}" step="1">
                      <td>
                        ${fn:split(fn:substring(entity.customwagecut,1,fn:length(entity.customwagecut)-1),',')[i]}
                      </td>
				    </c:forEach>
				    </c:if>
					<td align="center"> 
						<script>
						var tt = ${entity.dueintegral };
						document.write("<span class='result'>" +tt.toFixed(3) + "</span>");
						</script>
					</td>
					<td align="center"> 
						<script>
						var tt = ${entity.obtainedintegral };
						document.write("<span class='fentotal'>" +tt.toFixed(3) + "</span>");
						</script>
					</td>
					<td align="center"> 
						<script>
						var tt = ${entity.obtainedmenoy };
						document.write("<span class='qiantotal'>" + tt.toFixed(2) + "</span>");
						</script>
					</td>
					<td align="center">
						${entity.holidaysintegral }
					</td>
					<td align="center">
					</td>
				</tr>

			</c:forEach>
			<tr>
				<td align="center" colspan="2">
					合计：
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
				<td align="center" height="40">
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
				<td colspan="${fn:length(add)+fn:length(cut)+4}" align="center"
					style="font: small-caps 600 12pts/ 16pts 宋体;">
					人事文员：
				</td>
				<td colspan="4" align="center">
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