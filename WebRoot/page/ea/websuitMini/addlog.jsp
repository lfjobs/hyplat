<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page  import="hy.ea.bo.CAccount" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
CAccount M = (CAccount)session.getAttribute("account"); //从session里把a拿出来，并赋值给M
String  organizationID = (String)session.getAttribute("organizationID");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>5L5C微办公日志添加</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/websuitMini/websuit.css" />
			<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
var value = '${param.id}';
var token = 0;
  var basePath='<%=basePath%>';
  var staffID = '<%=M.getStaffID()%>';
  var organizationID =  '<%=organizationID%>';
  $("#organizationID").val(organizationID);
  $("#staffID").val(staffID);
  if(value!=""){
  $("#topbar_title").text("修改日志");
  }
  var url= basePath + "ea/logbook/sajax_ea_getLogbookByID.jspa?logbook.logBookID="+ value;
			$.ajax({
	                url: url,
	                type: "get",
	                async: false,
	                dataType: "json",
	                success: function cbf(data){
				    var member = eval("(" + data + ")");
				     $("#logBookID").val(member.logbook.logBookID);
			        $("#todaydate").val(member.logbook.todaydate); $("#startdate").val(member.logbook.startdate);
			         $("#enddate").val(member.logbook.enddate); $("#bisect").val(member.logbook.bisect);
			          $("#jobLocation").val(member.logbook.jobLocation); $("#jobContent").val(member.logbook.jobContent);
			        
			        
		    },
	              error: function cbf(data){
					         alert("数据获取失败！")
					 }
			});
  $("#saveOpertion").click(function(){
	if($("#jobContent").val() == ""){
						alert("工作内容描述不能为空！");
						return;
					}
					<%-- <%=basePath%>ea/logbook/ea_savelog.jspa --%>
					$("#addForm").attr("target","hidden").attr( "action", basePath + "ea/logbook/ea_savelogMiniBook.jspa");
				document.addForm.submit.click();
				document.addForm.reset();
				token=2;
	});
})

 function re_load() {
	    if(token)
		document.location.href = basePath
				+ "ea/logbook/ea_getListLogBook.jspa?serachType=websuit";
}
</script>
<style>
body {
	background-color: #b2ebff;
}

#topbar_back img {
	width: 14px;
}

.log_con {
	width: 360px;
	margin: auto;
}

.log_nav {
	width: 100%;
	height: 40px;
	line-height: 40px;
	margin-top: 5px;
	margin-bottom: 20px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	background-color: #f0f0f0;
}

.log_nav label {
	float: left;
	width: 80px;
	line-height: 40px;
	height: 40px;
	text-align: right;
	font-size: 12px;
	font-weight: bold;
}

.log_nav input {
	background-color: transparent;
	border: none;
	font-size: 14px;
	color: #4297b6;
	line-height: 20px;
	float: left;
	padding: 10px 0;
	margin: -10px 0;
	margin-top: 0px;
	font-weight: bold;
	outline: none;
	float: left;
}

.log_nav img {
	width: 1px;
	height: 40px;
	margin-left: 4px;
	margin-right: 5px;
	float: left;
}

.log_b {
	width: 100%;
	height: 120px;
	line-height: 30px;
	margin-top: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	background-color: #f0f0f0;
}

.log_b textarea {
	width: 340px;
	height: 120px;
	line-height: 30px;
	margin-left: 10px;
	border: none;
	background-color: transparent;
	font-size: 14px;
	color: #4297b6;
	float: left;
	resize: none;
	outline: none;
}

.log_sub {
	margin-top: 10px;
}

.sub {
	width: 100%;
	height: 40px;
}

.sub img {
	width: 100%;
	height: 40px;
}

::-webkit-input-placeholder { /* WebKit browsers */
	color: #4297b6;
}

:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #4297b6;
}

::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #4297b6;
}

:-ms-input-placeholder { /* Internet Explorer 10+ */
	color: #4297b6;
}
</style>
<body style="margin-top: 63px;">
	<div id="topbar" style="display: block;">
		<div id="topbar_title" class="topbar_title">添加日志</div>
		<div id="topbar_back" ontouchstart="" style="display: block;">
			<img src="<%=basePath%>images/websuitMini/button_back.png"
				onclick="history.back()" />
		</div>
		<div id="topbar_menu" class="topbar_menu" ontouchstart=""></div>
	</div>
	<form name="addForm" id="addForm" method="post" enctype="multipart/form-data">
	<input type="submit"  name="submit" style="display: none;"/ >
	<div class="log_con">
		<div class="log_nav fl">
			<label>日期：</label> <input type="text" onfocus="date(this)" id="todaydate" class="input"
				style="width:250px;" name="logbook.todaydate" readonly="readonly"  placeholder="选择日期(2014-08-19)" maxlength="20">
			<input type="hidden" name="logbook.staffID" id="staffID"/>
			<input type="hidden" name="logbook.logBookID" id="logBookID"/>
			<input type="hidden" name="logbook.organizationID" id="organizationID"/>
		</div>
		<div class="clear"></div>
		<div class="log_nav fl">
			<label>开始时间：</label> <input type="text" id="startdate" readonly="readonly"  name="logbook.startdate" class="input"
				style="width:90px;" onfocus="WdatePicker({dateFmt:'HH:mm'});" maxlength="20"><img
				alt="" title="" src="<%=basePath%>images/websuitMini/line.png" /> <label>结束时间：</label>
			<input type="text" id="enddate" class="input" onfocus="WdatePicker({dateFmt:'HH:mm'});" readonly="readonly" name= "logbook.enddate" style="width:90px;"
				placeholder="17:30" maxlength="20">
		</div>
		<!-- onfocus="WdatePicker({dateFmt:'HH:mm'})" -->
		<div class="clear"></div>
		<div class="log_nav fl">
			<label>基本得分：</label> <input type="text" id="bisect" class="input"
				style="width:90px;" placeholder="2.26" maxlength="20" name="logbook.bisect"> <img
				alt="" title="" src="<%=basePath%>images/websuitMini/line.png" /> <label>工作地点：</label>
				<input type="text" id="jobLocation" class="input" name="logbook.jobLocation" style="width:90px;"
				placeholder="北京" maxlength="20">
		</div>
		<div class="clear"></div>
		<div class="log_b fl">
			<textarea name="logbook.jobContent" placeholder="输入工作内容" id="jobContent"></textarea>
		</div>
		<div class="clear"></div>
		<div class="log_sub fl">
			<a class="sub" href="javascript:void(0);"><img alt="" title=""
				src="<%=basePath%>images/websuitMini/bc_btn.png" id="saveOpertion" />
			</a> 
		</div>
	</div>
</form>
 <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
