<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工作计划汇总</title>
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
			src="<%=basePath%>js/ea/human/office/production/jobplansummary.js"></script>
		
		<SCRIPT type="text/javascript">
   var addressID = '';
   var pbasePath = '<%=basePath%>';
   var ppageNumber = ${pageNumber};
   var psearch = '${search}';
   var pstaffID= '${staffID}';
	
</SCRIPT>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>
		<form style="display: none;" name="addressForm" id="addressForm"
			method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		</form>

		<div id="main_main" class="main_main">
			<table class="address">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="200" align="center">
							公司
						</th>
						<th width="100" align="center">
							部门
						</th>
						<th width="80" align="center">
							编号
						</th>
						<th width="50" align="center">
							姓名
						</th>
						<th width="93" align="center">
							起始日期
						</th>
						<th width="93" align="center">
							结束日期
						</th>
						<th width="93" align="center">
							项目名称
						</th>
						<th width="93" align="center">
							项目类别
						</th>
						<th width="93" align="center">
							项目分类
						</th>
						<th width="420" align="center">
							项目内容
						</th>
						<th width="80" align="center">
							发起人
						</th>
						<th width="80" align="center">
							主管签字
						</th>
						<th width="120" align="center">
							人事主管理
						</th>
						<th width="120" align="center">
							公司经理
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" var="arr">
						<tr class="td_bg01 saveAjax" id="${arr[0]}${arr[15]}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue" />
							</td>
							<td class="td_bg01">
								<span id="companyName">${arr[1]}</span>
							</td>
							<td class="td_bg01">
								<span id="organizationName">${arr[2]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffCode">${arr[3]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffName">${arr[4]}</span>
							</td>
							
							<td class="td_bg01">
								<span id="startDate">${fn:substring(arr[5],0,10)}</span>
							</td>
							<td class="td_bg01">
								<span id="endDate">${fn:substring(arr[6],0,10)}</span>
							</td>
							<td class="td_bg01">
								<span id="jobName">${arr[7]}</span>
							</td>
							<td class="td_bg01">
								<span id="codeValue">${arr[8]}</span>
							</td>
							<td class="td_bg01">
								<span id="jobstatus">${arr[9]}工作计划</span>
							</td>
							<td class="td_bg01">
								<span id="jobContent">${arr[10]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffNameS">${arr[11]}</span>
							</td>
							<td class="td_bg01">
								<span id="supervisor">${arr[12]}</span>
							</td>
							<td class="td_bg01">
								<span id="humanSupervisor">${arr[13]}</span>
							</td>
							<td class="td_bg01">
								<span id="manager">${arr[14]}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobplan/ea_getJobPlanListSummary.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>

		<!--搜索窗口 -->
		<form name="postSearchForm" id="postSearchForm" method="post">
			<div class="jqmWindow jqmWindowcss3"
				style="width: 500px; right: 25%; top: 10%;" id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="495" id="cataffSearchTable">
					<tr>
						<td width="119" align="right">
							人员姓名：
						</td>
						<td width="364">
							<input name="jobPlan.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							计划起始日期：
						</td>
						<td>
							<input name="jobPlan.startDate" class="put3"
								onfocus="date(this);" />
							至
							<input name="jobPlan.endDate" class="put3"
								onfocus="date(this);" />
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
		<!--打印窗口 -->
		<form name="postSearchForm1" id="postSearchForm1" method="post"
			target="_blank">
			<div class="jqmWindow jqmWindowcss3"
				style="width: 500px; right: 25%; top: 10%;" id="jqModelSearch1">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="495" id="cataffPrintTable">
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="jobplansta.staffName"  id="staffName1"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							计划日期：
						</td>
						<td>
							<input name="startDate" class="put3 startdate" id="startdate" 
								onfocus="daymonth(this);" />
							至
							<input name="endDate" class="put3 enddate" id="enddate"
								onfocus="daymonth(this);" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toprint" value=" 打印" />
					<input name="search" type="hidden" value="search" />

				</div>

			</div>
		</form>
		<!--查看 -->
		<form name="cstaffForm" id="cstaffForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow"
				style="width: 600px; top: 10%; margin-left: 20%" id="jqModel">
				<div class="drag">
					工作计划
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
					</div>
					<table width="600" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="600" height="250" border="0" align="center"
									cellpadding="0" cellspacing="0" id="stafftable2"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="114" align="right">
											公司名称：
										</td>
										<td width="181">
											<input name="companyName" type="text" id="companyName"
												size="20" />
										</td>
										<td width="122" align="right">
											部门名称：
										</td>
										<td width="183">
											<input name="organizationName" type="text"
												id="organizationName" size="20" />
										</td>
									</tr>
									<tr>
										<td width="114" align="right">
											员工编号：
										</td>
										<td width="181">
											<input name="staffCode" type="text" id="staffCode" size="20" />
										</td>
										<td width="122" align="right">
											员工姓名：
										</td>
										<td width="183">
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
											<input name="manager" type="text" class="input" id="manager"
												size="20" />
										</td>
										<td align="right">
											项目类别：
										</td>
										<td>
											<input name="codeValue" type="text" class="input"
												id="codeValue" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											项目名称：
										</td>
										<td >
											<input name="jobName" type="text" class="input" id="jobName"
												size="20" />
										</td>
										<td align="right">
											项目分类：
										</td>
										<td>
											<input name="jobstatus" type="text" class="input"
												id="jobstatus" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											项目内容：
										</td>
										<td colspan="3">
											<input name="jobContent" style="height: 50px;width: 280px" 
												class="input" id="jobContent"></input>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
