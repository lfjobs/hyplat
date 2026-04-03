<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@page import="hy.ea.bo.Company"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany"); 
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工作计划汇总——总公司、子公司</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/human/office/company/jobplansummary.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		
		<script type="text/javascript">
		   var treeID = '<%=session.getAttribute("organizationID")%>';
		   var comID = "<%=c.getCompanyID()%>";
		   var comName = "<%=c.getCompanyName()%>";
		   var select = '01';
		   var addressID = '';
		   var  basePath='<%=basePath%>';           
		   var  search='${search}';  
		   var  pNumber =${pageNumber};        	
		</script>

		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>

		<div id="main_main" class="main_main">
			<table class="address">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="180" align="center">
							公司
						</th>
						<th width="50" align="center">
							部门
						</th>
						<th width="50" align="center">
							姓名
						</th>
						<th width="50" align="center">
							编号
						</th>
						<th width="120" align="center">
							录入时间
						</th>
						<th width="160" align="center">
							起始日期
						</th>
						<th width="160" align="center">
							结束日期
						</th>
						<th width="100" align="center">
							项目编码
						</th>
						<th width="160" align="center">
							项目名称
						</th>
						<th width="100" align="center">
							项目类别
						</th>
						<th width="100" align="center">
							岗位情况管理
						</th>
						<th width="100" align="center">
							明细项目编号
						</th>
						<th width="400" align="center">
							明细项目内容
						</th>
						<th width="100" align="center">
							完成情况
						</th>

						<th width="100" align="center">
							项目要求
						</th>
						<th width="100" align="center">
							应得分数
						</th>
						<th width="100" align="center">
							扣分
						</th>
						<th width="100" align="center">
							实际得分
						</th>

						<th width="100" align="center">
							主管签字
						</th>
						<th width="100" align="center">
							人事主管
						</th>
						<th width="100" align="center">
							公司经理
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${jobPlanID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue" />
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyName}</span>
							</td>
							<td class="td_bg01">
								<span id="organizationName">${organizationName}</span>
							</td>
							<td class="td_bg01">
								<span id="staffName">${staffName}</span>
							</td>
							<td class="td_bg01">
								<span id="staffCode" class="datas">${staffCode}</span>
							</td>
							<td class="td_bg01">
								<span id="entryDate">${fn:substring(entryDate,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="startDate">${fn:substring(startDate,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="endDate">${fn:substring(endDate,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="projectcode">${projectcode}</span>
							</td>
							<td class="td_bg01">
								<span id="jobName">${jobName}</span>
							</td>
							 <td class="td_bg01" id="coval">
							 <span id="codeValue">${codeValue}</span>
							 </td>
							<td class="td_bg01">
								<span id="postmanage">${postmanage}</span>
							</td>
							<td class="td_bg01">
								<span id="projectDetailsCode">${projectDetailsCode}</span>
							</td>
							<td class="td_bg01">
								<span id="jobContent">${jobContent}</span>
							</td>
							<td class="td_bg01">
								<span id="entry">${entry}</span>
							</td>

							<td class="td_bg01">
								<span id="projectRequirements">${projectRequirements}</span>
							</td>
							<td class="td_bg01">
								<span id="fraction">${fraction}</span>
							</td>
							<td class="td_bg01">
								<span id="points">${points}</span>
							</td>
							<td class="td_bg01">
								<span id="actualScore">${actualScore}</span>
							</td>


							<td class="td_bg01">
								<span id="supervisor">${supervisor}</span>
							</td>
							<td class="td_bg01">
								<span id="humanSupervisor">${humanSupervisor}</span>
							</td>
							<td class="td_bg01">
								<span id="manager">${manager}</span>
								<input type="hidden" name="jobPlan.jobPlanKey"
									value="${jobPlanKey}" />
								<input type="hidden" name="jobPlan.jobPlanID"
									value="${jobPlanID}" />
								<input type="hidden" name="jobPlan.staffID" value="${staffID}" />
								<input type="hidden" name="staffID" value="${staffID}" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobplan/ea_getCompanyJobPlanListSummary.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>

		<!--搜索窗口 -->
		<form name="postSearchForm" id="postSearchForm" method="post">
			<div class="jqmWindow " style="width: 500px; right: 25%; top: 10%"
				id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="497" id="cataffSearchTable">
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="jobPlanVO.staffName" />
						</td>
					</tr>
					<tr>
						<td width="125" align="right">
							计划起始日期：
						</td>
						<td width="360"><input name="jobPlanVO.startDate" 
						class="put3" onfocus="date(this);" />至<input name="jobPlanVO.endDate" class="put3"
								onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID" name="jobPlanVO.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="orgID" name="jobPlanVO.organizationID">
								<option value="">
									请选择部门
								</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>


		<!--查看 -->
		<form name="cstaffForm" id="cstaffForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow jqmWindowcss3" style="width: 700px; top: 10%"
				id="jqModel">
				<div class="drag">
					项目工作计划
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
					</div>
					<table width="700" height="350" border="0" align="center"
						cellpadding="0" cellspacing="0" id="stafftable"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td align="right">
								员工编号：
							</td>
							<td>
								<input name="staffCode" type="text" id="staffCode" size="20" />
							</td>
							<td align="right">
								员工姓名：
							</td>
							<td>
								<input name="staffName" type="text" id="staffName" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								起始日期：
							</td>
							<td>
								<input id="startDate" type="text" class="input" size="20" />
							</td>
							<td align="right">
								结束日期：
							</td>
							<td>
								<input id="endDate" type="text" class="input" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								主管签字：
							</td>
							<td>
								<input id="supervisor" type="text" name="supervisor"
									class="input" size="20" />
							</td>
							<td align="right">
								人事主管理：
							</td>
							<td>
								<input type="text" class="input" name="staffingManage"
									id="humanSupervisor" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								公司经理：
							</td>
							<td>
								<input name="manager" type="text" id="manager" size="20" />
							</td>
							<td align="right">
								岗位情况管理：
							</td>
							<td>
								<input name="postmanage" type="text" id="postmanage" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								项目编码：
							</td>
							<td>
								<input name="projectcode" type="text" id="projectcode" size="20" />
							</td>
							<td align="right">
								项目内容：
							</td>
							<td>
								<input name="jobName" type="text" id="jobName" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								完成情况：
							</td>
							<td>
								<input name="entry" type="text" id="entry" size="20" />
							</td>
							<td align="right">
								项目要求：
							</td>
							<td>
								<input name="projectRequirements" type="text"
									id="projectRequirements" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								应得分数：
							</td>
							<td>
								<input name="fraction" type="text" id="fraction" size="20" />
							</td>
							<td align="right">
								扣分：
							</td>
							<td>
								<input name="points" type="text" id="points" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								实际得分：
							</td>
							<td>
								<input name="actualScore" type="text" id="actualScore" size="20" />
							</td>
							<td align="right">
								录入时间：
							</td>
							<td>
								<input name="entryDate" type="text" id="entryDate" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								明细项目编号：
							</td>
							<td>
								<input name="projectDetailsCode" type="text"
									id="projectDetailsCode" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								明细项目内容：
							</td>
							<td colspan="3">
								<textarea name="appraisalContent" cols="30" rows="6"
									class="input" id="jobContent"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</div>
		</form>
	</body>
</html>
