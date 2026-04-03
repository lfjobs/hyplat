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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作目标进度跟踪</title>
	<meta http-equiv="pragma" content="no-cache">
  	
  	 <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui-1.4/themes/default/easyui.css">

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<SCRIPT type="text/javascript">
   var token = 0;
   var select = 1;
   var jobTaskID='';
   var pbasePath = '<%=basePath%>';
   var ppageNumber = "${pageNumber}";
   var pstaffID = '${staffID}';
   var notoken = 0;
   var pserch='${search}';
   var personName='${personName}';
   var scoreSortlist = '${scoreSortlist}';
   var summarytype="${summarytype}";
</SCRIPT>

<script type="text/javascript" src="<%=basePath %>js/ea/person/cstaff_jobtasktrack.js"></script>
  </head>
  <body style="overflow:auto;">
  <div>
  <iframe name="hidden" id="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		

	
	
	<form id="searchForm" name="searchForm" method="post" >
	<input type="submit" name="submit" style="display:none;">
	<div style="margin-left:30px;padding-top:5px;text-align:center;"><h2 style="height:60px;">项目任务目标跟踪表</h2></div>
	<div style="float:right;"><span style="background:green;">&nbsp;&nbsp;&nbsp;</span>&nbsp;计划完成时间
				<span style="background:orange;">&nbsp;&nbsp;&nbsp;</span>&nbsp;实际完成时间
				<span style="background:red;">&nbsp;&nbsp;&nbsp;</span>&nbsp;实际完成时间与计划不同</div>
	
	<input type="hidden" name="search" style="display:none;" value="search"/>
	<input type="hidden" name="summarytype" style="display:none;" value="${summarytype}"/>
		<div></div>
		<table cellpadding="10" cellspacing="10" style="width:80%;">
			<tr>
			    <td class="com">公司:</td>
			    <td class="com"><s:select list="companylist" id="companyid" class="sel" style="width:130px;"
										name="jobTask.companyID" listKey="companyID" listValue="companyName"  headerKey="" headerValue="全部"></s:select></td>
				<td class="org">部门</td>
				<td class="org"><s:select list="orglist" id="orgid" class="sel" style="width:90px;"
										name="jobTask.organizationID" listKey="organizationID" listValue="organizationName" headerKey="" headerValue="全部"></s:select></td>
				<td>项目计划:</td>
				<td><s:select list="costlist" id="projectID" class="sel" style="width:150px;"
										name="jobTask.projectID" listKey="csbid" listValue="projectname"  headerKey="" headerValue="全部"></s:select>
				<input type="hidden"  name="jobTask.projectName" id="projectName" />
				</td>
				<td>任务时间:从<input name="start" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>到:<input name="end" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
				
				<td align="left">责任人:<input style="width:50px;"type="text"  name="jobTask.staffName" /></td>
				<td><input type="button" id="search" class="input-button"  value=" 查询 "/></td>
		
			</tr>
		</table>
	</div>
	</form>
  
    <div style="margin:20px 0;"></div>
    <table class="easyui-datagrid" data-options=" loadMsg:'数据加载中,请稍后……'" style="width:100%;height:550px" >
        <thead>
            <tr>
                <th data-options="field:'sernum'" width= "50" rowspan="2"  align="center">序号</th>
                <th data-options="field:'code'" width= "50" rowspan="2"  align="center">编号</th>
                <th data-options="field:'name'" width= "80" rowspan="2"  ali	gn="center">项目名称</th>
                <th data-options="field:'type'" width= "50" rowspan="2"  align="center">任务类别</th>
                <th data-options="field:'title'" width= "140" rowspan="2"  align="center">任务目标</th>
                <th data-options="field:'staff'" width= "50" rowspan="2"  align="center">责任人</th>
                <th data-options="field:'ask'" width= "80" rowspan="2"  align="center">指标要求</th>
                <th data-options="field:'weight'"width= "50" rowspan="2"  align="center">权重</th>
                <th data-options="field:'first'" width= "50" rowspan="2"  align="center">优先级</th>
                <th data-options="field:'shoulder'" width= "50"  rowspan="2">应得分</th>
                <th data-options="field:'task'" width= "78" rowspan="2"  align="center">任务月份</th>
                <th data-options="field:'jindu',editor:'numberbox'" width= "279" colspan="31" align="center">任务进度</th>
                <th data-options="field:'complate'" rowspan="2"  align="center">实际完成度</th>
            </tr>
           <tr>
			  <% for(int i=1;i<32;i++){ %>
				  <th  data-options="field:'<%=i%>'" width="20" style="align:center;" ><%=i%></th>
				
				<% } %>
			</tr>
        </thead>
        <tbody >
          <% int number=1; %>
			<s:iterator value="beans">
			<tr class="checkgoods">
			    	<td align="center"><%=number %></td>
				<td align="center">${taskNumber}</td>
				<td align="center">${projectName}</td>
			    <td align="center">${codeValue}</td>
				<td align="left">${taskName}</td>
				<td align="center">${staffName}</td>
				<td align="center">${target}</td>
				<td align="center">${weight}</td>
				<td align="center" >${prioritys}</td>
				<td align="center">${bonusPoint}
				<span id="time" style="display:none;">${time}</span>
				<span id="facttime" style="display:none;">${facttime}</span>
				<span id="factrateday" style="display:none;">${factrateday}</span>
				</td>
				<td align="center" >${fn:substring(datemonth, 0, 7)}</td>
				 <% for(int i=1;i<32;i++){ %>
				  <td data-options="field:'<%=i%>'" ><div id="time<%=i%>" style="width:100%;height:20px;"></div></td>
				<% } %>
				<td align="center" >${finishrate}</td>
			</tr>
			<% number++; %>
			</s:iterator>
        </tbody>
    </table>
  </body>
</html>
