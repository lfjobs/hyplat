<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作目标任务添加</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/person/cstaff_addjobtask.js"></script>
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
var costlist =  '${costlist}'
   
</SCRIPT>
<style type="text/css">
.tbl{
border:1px solid #97CBFF;
 border-collapse:collapse;
 width:100%;

}
.tbl td,th{
border:1px solid #97CBFF;
}

.spancolor{
  cursor:pointer;
}
</style>
</head>
<body style="overflow:auto;">
<iframe name="hidden" id="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
<form id="taskTargetForm" name="taskTargetForm" method="post" target = "hidden">
<input type="submit" name="submit" style="display:none;">
	<div >
		<table cellpadding="10" cellspacing="10">
			<tr>
				<td>项目计划:</td>
				<td><s:select style="width:130px;" list="%{#request.beans}"   class="sel" name="jobTask.projectID"
								listKey="csbid" listValue="projectname" id="projectID"
							theme="simple"></s:select></td>
				<td>
				<input type="hidden"  name="jobTask.projectName" id="projectName"/>
				</td>
				<td>任务月份:<input name="start"  style="width:120px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
				<td><input type="button" id="newline" class="input-button"  value=" 新增一行 "/></td>
				<td><input type="button" id="deleteb" class="input-button"  value=" 批量删除 "/></td>
				<td><input type="button" id="fullsave" class="input-button"  value=" 全部保存 "/></td>
				
			</tr>
		</table>
	</div>
	<div style="padding-right:2px;" >

		<table class="tbl">
		<thead>
			<tr>
			    <th  align="center" rowspan="2">选择</th>
				<th align="center" rowspan="2">编号</th>
				<th align="center" rowspan="2">任务类别</th>
				<th align="center" rowspan="2">任务目标</th>
				<th align="center" rowspan="2">指标要求</th>
				<th align="center" rowspan="2">权重</th>
				<th align="center" rowspan="2">优先级</th>
				<th align="center" rowspan="2">应得分</th>
				<th align="center" colspan="31">时间进度(日)</th>
			</tr>
			<tr>
			  <% for(int i=1;i<32;i++){ %>
				  <td style="width:15px;align:center;" title="<%=i%>"><%=i%></td>
				
				<% } %>
			</tr>
			</thead>
			<tbody>
			<tr class="checkgoods" id="kelong1">
			    <td align="center"><input type="checkbox" /></td>
				<td align="center"><input type="text" name="taskNumber" style="width:60px;"/></td>
			    <td align="center"><s:select list="scoreSortlist" id="codeID" class="sel"
										name="jobPlan.codeID" listKey="codeID" listValue="codeValue"></s:select></td>
				<td align="center"><input type="text" name="taskName" style="align:left;width:300px;"/>
				</td>
				<td align="center"><input type="text" name="target" style="width:90px;"/></td>
				<td align="center"><select name="weight"><option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6</option>
				<option>7</option>
				<option>8</option>
				<option>9</option>
				<option>10</option>
					</select>
					</td>
				<td align="center" >
				<select name="prioritys"><option>低</option>
							<option>中</option>
							<option>高</option>

					</select></td>
				<td align="center"><input type="text" name="bonusPoint" style="width:30px;"/></td>
				 <% for(int i=1;i<32;i++){ %>
				  <td  class="spancolor" id="time<%=i%>"></td>
				
				<% } %>
			</tr>

				<tr class="checkgoods" id="kelong" style="display:none;">
					<td align="center"><input type="checkbox" />
					</td>
					<td align="center"><input type="text" name="taskNumber"
						style="width:60px;" />
					</td>
					<td align="center"><s:select list="scoreSortlist" id="codeID" class="sel"
										name="jobtask.codeID" listKey="codeID" listValue="codeValue"></s:select>
										
										
										</td>
					<td align="center"><input type="text" name="taskName"
						style="align:left;width:300px;" /></td>
					<td align="center"><input type="text" name="target"
						style="width:90px;" />
					</td>
					<td align="center"><select name="weight"><option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
							<option>6</option>
							<option>7</option>
							<option>8</option>
							<option>9</option>
							<option>10</option>
					</select></td>
					<td align="center"><select name="prioritys"><option>低</option>
							<option>中</option>
							<option>高</option>

					</select>
					</td>
					<td align="center"><input type="text" name="bonusPoint"
						style="width:30px;" />
					</td>
					<%
						for (int i = 1; i < 32; i++) {
					%>
					<td class="spancolor" id="time<%=i%>" title="<%=i%>"></td>

					<% } %>
				</tr>


             </tbody>
			
		</table>
		
	</div>
	</form>
	
	
</body>
</html>
