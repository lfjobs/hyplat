<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作计划</title>
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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/person/cstaff_jobplan.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
<script type="text/javascript" src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
<SCRIPT type="text/javascript">
   var select = 1;
   var jobPlanID = '';
   var pbasePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var pstaffID = '${staffID}';
	var pserch = '${search}';
	var personName = "${personName}";
	var notoken = 0;
	var token = 0;
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<style>
.wenbenkuang input {
	width: 65px;
}

.addtable {
	border-collapse: collapse;
}

.addtable td {
	border: 1px solid gray;
	text-align: center;
}

input {
	border: 0px;
}
</style>
</head>
<body>
	<form name="jobPlanForm" id="jobPlanForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none"/>
<table class="jobPlan">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="150" align="center">录入时间</th>
						<th width="70" align="center">起始日期</th>
						<th width="70" align="center">结束日期</th>
						<th width="120" align="center">项目编码</th>
						<th width="200" align="center">项目名称</th>
						<th width="100" align="center">项目类别</th>
						<th width="100" align="center">项目分类</th>
						<th width="400" align="center">项目内容</th>
						<th width="80" align="center">项目工作状态</th>
						<th width="100" align="center">发起人</th>
						<th width="100" align="center">主管签字</th>
						<th width="100" align="center">人事主管</th>
						<th width="100" align="center">公司经理</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" var="arr">
						<tr id="${arr[0]}" >
							<td class="td_bg01"><input type="radio" name="a"
								class="JQuerypersonvalue" id="${arr[0] }" /></td>
							<td class="td_bg01"><span id="entryDate">${fn:substring(arr[1],0,19)}</span>
							</td>
							<td class="td_bg01"><span id="startDate">${fn:substring(arr[2],0,10)}</span>
							</td>
							<td class="td_bg01"><span id="endDate">${fn:substring(arr[3],0,10)}</span>
							</td>
							<td class="td_bg01"><span id="projectcode">${arr[4]}</span>
							</td>
							<td class="td_bg01"><span id="jobName">${arr[5]}</span>
							</td>
							<td class="td_bg01"><span id="codeValue">${arr[6]}</span>
							</td>
							<td class="td_bg01">
								<span id="" >
								<c:if test="${arr[7]=='00'}">日工作计划</c:if>
								<c:if test="${arr[7]=='01'}">周工作计划</c:if>
								<c:if test="${arr[7]=='02'|| arr[7]=='' || arr[7]==null}">月工作计划</c:if>
								<c:if test="${arr[7]=='03'}">季工作计划</c:if>
								<c:if test="${arr[7]=='04'}">年工作计划</c:if>
								</span>
							</td>
							<td class="td_bg01" ><span id="jobContent">${arr[8]}</span>
							</td>
							<!-- entry ${arr[9]}-->
							<td class="td_bg01"><span id="" >
								<c:if test="${arr[14]=='00'}">待办</c:if>
								<c:if test="${arr[14]=='01'}">已办</c:if>
								<c:if test="${arr[14]=='02'}">完成</c:if>
								<c:if test="${arr[14]=='03'}">退回</c:if>
							</span>
							</td>
							<td class="td_bg01"><span id="staffNameS" >${arr[10]}</span>
							</td>
							<td class="td_bg01"><span id="supervisor">${arr[11]}</span>
							</td>
							<td class="td_bg01"><span id="humanSupervisor">${arr[12]}</span>
							</td>
							<td class="td_bg01"><span id="manager">${arr[13]}</span>
								<div style="display: none">
									<span id="jobPlanKey">${arr[15]}</span>
									<span id="jobPlanID">${arr[0]}</span>
									<span id="staffID">${arr[16]}</span>
									<span id="staffIDS">${arr[17]}</span>
									<span id="companyID">${arr[18]}</span>
									<span id="companyIDS">${arr[19]}</span>
									<span id="organizationID">${arr[20]}</span>
									<span id="staffName">${arr[21]}</span>
									<span id="codeid">${arr[22]}</span>
									<span id="jobstatus">${arr[7]}</span>
									<span id="stusts">${arr[14]}</span>
								</div></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<div id="main_main" class="main_main">
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobplan/ea_getJobPlanLists.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>
	</form>
	<!--添加修改工作计划窗口 -->
	<form name="addplanForm" id="addplanForm" method="post">
		<div class="jqmWindow" style="width: 700px;right: 25%;top: 10%;"
			id="jqModelplan">

			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				添加工作计划<font style="color:red;font-size:11px;margin-left:300px;">注：带红色*号为必填项</font>
				<div class="close"></div>
			</div>
			<table id="monthjobplan">
				<tr>
					<td>
						<table id="cataffaddplanTable">
							<tr>
								<td>项目名称：<font style="color:red">*</font></td>
								<td><input name="jobPlan.jobName" id="jobName" class="aaaa" />
								</td>
								<td>责任人：</td>
								<td><input name="jobPlan.personName" id="personName"
									value="${personName}" readonly="readonly" /></td>
								<td>项目编码：</td>
								<td><input name="jobPlan.projectcode" id="projectcode" readonly="readonly"/></td>
								<td style="display: none">录入时间：</td>
								<td style="display: none"><input name="jobPlan.entryDate"
									value="自动生成" readonly="readonly" id="entryDate" /></td>
							</tr>
							<tr>
								<td>任务类别：<font style="color:red">*</font></td>
								<td><s:select list="scoreSortlist" id="codeID" class="sel"
										name="jobPlan.codeID" listKey="codeID" listValue="codeValue"></s:select></td>
								<td>起始日期：<font style="color:red">*</font></td>
								<td><input name="jobPlan.startDate" class="aaaa" id="startDate" onfocus="date(this)" /></td>
								<td>结束日期：<font style="color:red">*</font></td>
								<td><input name="jobPlan.endDate" id="endDate" class="aaaa" onfocus="date(this)" /></td>
							</tr>
							<tr>
								<td colspan="6">项目工作计划内容</td>
							</tr>
							<tr>
								<td colspan="6"><input name="jobPlan.jobContent" id="jobContent"
									style="width: 600px; height: 50px;" type="text" /></td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td>工作状态:</td>
								<td><input name="jobPlan.entry" id="entry" value="待办事项" readonly="readonly"/>
								</td>
								<td>工作时间状态</td>
								<td>
									<s:select list="#{'02':'月工作计划','00':'日工作计划','01':'周工作计划','03':'季工作计划','04':'年工作计划'}" name="jobPlan.jobstatus" id="jobstatus" class="model1" width="30" theme="simple"></s:select>
								</td>
								<td>发起人：</td>
								<td><input name="jobPlan.staffNameS" id="staffNameS" readonly="readonly"/></td>
							</tr>
							<tr>
								<td>主管签字：</td>
								<td><input type="text" name="jobPlan.supervisor" readonly="supervisor" /></td>
								<td>人事主管：</td>
								<td><input type="text" name="jobPlan.humanSupervisor" readonly="humanSupervisormanager" /></td>
								<td>公司经理：</td>
								<td><input type="text" name="jobPlan.manager" readonly="manager" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button JQuerySubmitgd" name="addProjectPlan" style="cursor:pointer;width:80px;" value="保存" />
				<input type="button" class="input-button JQueryClose" name="button2" style="cursor:pointer;width:80px;" value="返回" /> 
				<input type="hidden" name="jobPlan.jobPlanKey" id="jobPlanKey" /> 
				<input type="hidden" name="jobPlan.jobPlanID" id="jobPlanID" />
				<input type="hidden" name="jobPlan.staffID" id="staffID" />
				<input type="hidden" name="jobPlan.staffIDS" id="staffIDS" />
				<input type="hidden" name="jobPlan.companyID" id="companyID" />
				<input type="hidden" name="jobPlan.companyIDS" id="companyIDS" />
				<input type="hidden" name="jobPlan.organizationID" id="organizationID" />
				<input type="hidden" name="jobPlan.staffName" id="staffName" />
			</div>
		</div>
	</form>

	<!--搜索窗口 -->
	<form name="postSearchForm" id="postSearchForm" method="post">
		<div class="jqmWindow" style="width: 500px;right: 25%;top: 10%;"
			id="jqModelSearch">

			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table id="cataffSearchTable">
				<tr>
					<td>查询条件</td>
				</tr>
				<tr>
					<td>计划起始日期：</td>
					<td><input name="jobPlan.startDate" class="put3"
						onfocus="date(this);" />至<input name="jobPlan.endDate"
						class="put3" onfocus="date(this);" /></td>
				</tr>
				<tr>
					<td>工作日期状态：</td>
					<td><s:select list="#{'00':'日工作计划','01':'周工作计划','02':'月工作计划','03':'季工作计划','04':'年工作计划'}" headerValue='全部' headerKey='' name="jobPlan.jobstatus"  id="jobstatus" theme="simple"></s:select></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " />

				<input name="search" type="hidden" value="search" /> <input
					type="hidden" name="staffID" value="${staffID}" />
			</div>
		</div>
	</form>
					<!--工作计划进度窗口 -->
	<form name="jobplanrecordForm" id="jobplanrecordForm" method="post">
		<div class="jqmWindow" style="width: 660px;right: 25%;top: 10%;"
			id="jqModeljobplan">

			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				工作计划进度
				<div class="close"></div>
			</div>
			<table id="jobplanTable"  style="width: 550px;" >
				<tr>
					<td colspan="4" align="center">
						<div style="width: 650px;height: 400px; overflow-y:auto; overflow-x:hidden; ">
						<table id="strhtml" style="width: 550px;">
						</table>
						</div>
					</td>
				</tr>
			</table>
			<div align="center">
				&nbsp;
			</div>
		</div>
	</form>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
