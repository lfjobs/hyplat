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
		<title>日周月季年工作计划</title>
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
		<script src="<%=basePath%>js/ea/human/office/production/jobplan_date.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<SCRIPT type="text/javascript">
   var basePath="<%=basePath%>";
   var ppageNumber = ${pageNumber};
   var psearch = '${search}';
   var notoken = 0;
   var jobstatus = '${jobPlan.jobstatus}';
   var jobPlanID =  '';
   var select = 1 ; 
   var aid = "";
   var ent = '${jobPlan.entry}'
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>
		<div id="main_main" class="main_main">
		<form name="jobPlanForm" id="jobPlanForm"
			method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		
			<table class="jobPlan">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="110" align="center">
							项目编号
						</th>
						<th width="150" align="center">
							项目名称
						</th>
						<th width="100" align="center">
							项目类别
						</th>
						<th width="100" align="center">
							责任人
						</th>
						<th width="120" align="center">
							起始日期
						</th>
						<th width="120" align="center">
							结束日期
						</th>
						<th width="420" align="center">
							项目内容
						</th>
						<th width="120" align="center">
							发起人
						</th>
						<th width="120" align="center">
							项目工作状态
						</th>
						<th width="120" align="center">
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
				<tbody  id="tbwid">
					<s:iterator value="pageForm.list" var="arr">
						<tr class="td_bg01 saveAjax" id="${arr[0]}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue" />
							</td>
							<td class="td_bg01">
								<span id="projectcode">${arr[4]}</span>
							</td>
							<td class="td_bg01">
								<span id="jobName">${arr[5]}</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[6]}</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[20]}</span>
								
							</td>
							<td class="td_bg01">
								<span id="startDate">${fn:substring(arr[2],0,10)}</span>
							</td>
							<td class="td_bg01">
								<span id="endDate">${fn:substring(arr[3],0,10)}</span>
							</td>
							
							<td class="td_bg01">
								<span id="jobContent">${arr[8]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffNameS">${arr[10]}</span>
							</td>
							<td class="td_bg01">
								<span id="entry">${arr[9]}</span>
							</td><td class="td_bg01">
								<span id="supervisor">${arr[11]}</span>
							</td>
							<td class="td_bg01">
								<span id="humanSupervisor">${arr[12]}</span>
							</td>
							<td class="td_bg01">
								<span id="manager">${arr[13]}</span>

								<input  type="hidden"  name="jobstatus" id="jobstatus" value="${arr[7]}" />
		            			<input  type="hidden"  name="companyID" id= "companyID" value="${arr[17]}"  />
		            			<input  type="hidden"  name="companyIDS"  id= "companyIDS" value="${arr[18]}"  />
		            			<input  type="hidden"  name="organizationID" id= "organizationID" value="${arr[19]}"/>
		            			<input  type="hidden"  name="staffIDS" id="staffIDS" value="${arr[16]}" />
		            			<input  type="hidden"  name="staffID" id="staffID" value="${arr[15]}"/>
		            			<input  type="hidden"  name="jobPlanKey" id="jobPlanKey" value="${arr[14]}"/>
		            			<input  type="hidden"  name="jobPlanID" id="jobPlanID" value="${arr[0]}"/>
		            			<input  type="hidden"  name="entryDate" id="entryDate" value="${arr[1]}"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobplan/ea_getjobPlanListdate.jspa?pageNumber=${pageNumber}&search=${search}&jobPlan.jobstatus=${jobPlan.jobstatus}&jobPlan.entry=${jobPlan.entry}"></c:param>
			</c:import>
			</form>
		</div>
	<!--添加修改工作计划窗口 -->
	<form name="addplanForm" id="addplanForm" method="post">
		<div class="jqmWindow" style="width: 650px;right: 25%;top: 10%;"
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
								<td>任务类别：<font style="color:red">*</font></td>
								<td><s:select list="scoreSortlist" id="codeID" class="sel"
										name="jobPlan.codeID" listKey="codeID" listValue="codeValue" ></s:select> 
									<input name="jobPlan.entryDate" type="hidden" 
									value="自动生成" readonly="readonly" id="entryDate" /></td>
							</tr>
							<tr>
								
								<td>起始日期：<font style="color:red">*</font></td>
								<td><input name="jobPlan.startDate" class="aaaa" id="startDate" onfocus="date(this)" /></td>
								<td>结束日期：<font style="color:red">*</font></td>
								<td><input name="jobPlan.endDate" id="endDate" class="aaaa" onfocus="date(this)" /></td>
							</tr>
							<tr>
								<td>项目编码：</td>
								<td><input name="jobPlan.projectcode" id="projectcode" readonly="readonly" value="自动生成" /></td>
								<td>责任人：</td>
								<td id="atr">
									<input name="jobstamap[0].staffName" id="staffName" readonly="readonly" class="staffName"/>
									<input type="hidden"  name="jobstamap[0].staffID" id="staffID" />
									<input type="hidden"  name="jobstamap[0].companyID" id="companyID" />
									<input type="hidden"  name="jobstamap[0].orgID" id="orgID" />
									<a href="#" id="staffID0" class="staffID">选择</a>
									<a href="#" id="addstaffID">增加</a>
									</td>
							</tr>
							<tr id="addtr">
								<td></td><td></td><td></td>
								<td>
									<input name="staffName" id="staffName" readonly="readonly" class="model1"/>
									<input  type="hidden"  name="staffID" id="staffID"/>
									<input  type="hidden"  name="companyID" id="companyID"/>
									<input  type="hidden"  name="orgID" id="orgID"/>
									<a href="#" id="staffID" class="model1 staffID">选择</a>
									<a href="#" id="delstaffID" class="model1 ">删除</a>
								</td>
							</tr>
							<tr>
								<td colspan="6">项目工作计划内容</td>
							</tr>
							<tr>
								<td colspan="6"><input name="jobPlan.jobContent" id="jobContent"
									style="width: 500px; height: 50px;" type="text" /></td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td>工作状态:</td>
								<td><input name="jobPlan.entry" id="entry" value="待办事项" readonly="readonly"/>
								</td>
								<td>工作时间状态</td>
								<td>
									<s:select list="#{'00':'日工作计划','01':'周工作计划','02':'月工作计划','03':'季工作计划','04':'年工作计划'}" name="jobPlan.jobstatus" id="jobstatus" class="model1" width="30" theme="simple"></s:select>
								</td>
								
							</tr>
							<tr>
								<td>发起人：</td>
								<td><input name="jobPlan.staffNameS" id="staffNameS" readonly="readonly"/></td>
								<td>主管签字：</td>
								<td><input type="text" name="jobPlan.supervisor" readonly="supervisor" /></td>
							</tr>
							<tr>
								
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
				<input type="button" class="input-button JQueryClose" name="button2" style="cursor:pointer;width:80px;" value="关闭" /> 
				<input type="hidden" name="jobPlan.jobPlanKey" id="jobPlanKey" /> 
				<input type="hidden" name="jobPlan.jobPlanID" id="jobPlanID" />
				<input type="hidden" name="jobPlan.staffIDS" id="staffIDS" />
				<input type="hidden" name="jobPlan.companyID" id="companyID" />
				<input type="hidden" name="jobPlan.companyIDS" id="companyIDS" />
				<input type="hidden" name="jobPlan.organizationID" id="organizationID" />
				<input type="hidden" name="jobPlan.staffName" id="staffName" />
				<input type="hidden" name="jobPlan.staffID" id="staffID" />
			</div>
		</div>
	</form>
	<!-- 指派 -->	
	<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;left: 53%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择指派接收责任人
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								人员名称：
							</td>
							<td width="142">
								<input name="querytxt" class="input" id="querytxt" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchMember"
									name="button7" value="查询" />
								<input type="button" class="btn02 submitResult" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 submitResult1" id="selectGood1"
									name="button6" value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy1" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy1" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy1">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount1"></span>&nbsp;&nbsp;页</a>
								<input type="hidden" id="companyIDs"/>
								<input type="hidden" id="deptIDs"/>
								<input type="hidden" id="staffIDs"/>
								<input type="hidden" id="staffNames"/>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="30%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="addTreess" 
												style="background-color:#FFFFFF;overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="70%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
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
							项目名称：
						</td>
						<td width="364">
							<input name="jobPlan.jobName" />
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
					<input name="jobPlan.jobstatus" type="hidden" value="${jobPlan.jobstatus}" />
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
