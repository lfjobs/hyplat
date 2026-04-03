<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>5L5C微办公日志列表</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/websuitMini/websuit.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript">
	   var select =1;
	   var selects =1;
	   var token = 0;
	   var logBookID = '';
	   var today=new Date();
	   var month = today.getMonth()+1;
	   var toda=today.getYear()+"-"+month+"-"+today.getDate();
	   var basePath='<%=basePath%>';
	   var ppageNumber='${pageNumber}';
	   var logbookstaffID='${logbook.staffID}';
	   var staffName='${staffName}';
	   var scoreSort = '${scoreSort}';
	   var status = '${status}';
	   var notoken = 0;
</script>
</head>
<style>
body {
	
}

#topbar_back img {
	width: 14px;
}

.con {
	width: 360px;
	margin: auto;
}

.contact_nav {
	width: 360px;
	height: 80px;
	line-height: 80px;
	border-bottom: 1px dashed #ccc;
	background-color: #f0f0f0;
}
#topbar{

}
.contact_nav .time {
	float: left;
	display: block;
	height: 80px;
	line-height: 80px;
	color: #4297b6;
	font-size: 14px;
	width: 100px;
	text-align: center;
}

.contact_nav .work {
	height: 60px;
	color: #4297b6;
	font-weight: bold;
}
.contact_nav .work div {
	color: #4297b6;
	font-size:18px;
}

.contact_nav a img {
	float: left;
	width: 38px;
	height: 38px;
	margin-left: 5px;
	margin-top: 20px;
}

#on {
	border-bottom: 1px dashed #ccc;
}
</style>
<body style="margin-top: 63px;">
	<div id="topbar" style="display: block;">
		<div id="topbar_title" class="topbar_title">工作日志</div>
		<div id="topbar_back" ontouchstart="" style="display: block;">
			<img src="<%=basePath%>images/websuitMini/button_back.png"
				onclick="history.back()" />
		</div>
		<div id="topbar_menu" class="topbar_menu" ontouchstart="">
			<a href="<%=basePath%>page/ea/websuitMini/addlog.jsp"><img
				src="<%=basePath%>images/websuitMini/jh.png" />
			</a>
		</div>
	</div>
	<div class="con">
		<table style="border: 0;background-color: white" cellpadding="0"
			cellspacing="0" class="contact_nav">
			<s:iterator value="pageForm.list">

				<tr onclick="editAndSee(id)" id = "${logBookID}" style='cursor: pointer;'>
					<td class="time">${fn:substring(todaydate, 0, 10)}</td>
					<td  class="work"><div style="width: 210px;overflow: hidden;"><nobr><c:if test="${fn:length(jobContent)>12}">${fn:substring(jobContent,0,12)}...</c:if>
					<c:if test="${fn:length(jobContent)<=12}">${jobContent}</c:if> </nobr></div></td>
					<td><a onclick=""><img
							src="<%=basePath%>images/websuitMini/xz.png" />
					</a>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div id="on"></div>
					</td>
				</tr>
			</s:iterator>
			<tr style="display: none" id="addattribute"></tr>
		</table>
		<img src="<%=basePath%>images/websuitMini/more.png"
			onclick="getNextDate()" />
	</div>
	<script type="text/javascript">
var num =1;
function getNextDate(){
num++;
var url = basePath+ "ea/logbook/sajax_ea_getListLogBook.jspa?pageNumber=${pageNumber}&pageForm.pageNumber="+num+"&serachType=other";
$.ajax({
			url : encodeURI(url+"&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var pageFormList = member.pageForm.list;
				var obj ="";
				for(var i = 0;i<pageFormList.length;i++){
				obj =pageFormList[i];
				 var context="<tr onclick='editAndSee(id)' id= '"+obj.logBookID+"' style='cursor: pointer;'><td class='time'>"+obj.todaydate.substring(0,10)+"</td><td class='work'><div style='width: 213px;overflow: hidden'><nobr>"+(obj.jobContent.length>12?obj.jobContent.substring(0,12)+"...":obj.jobContent)+"</nobr></div></td><td><a><img src='"+basePath+"images/websuitMini/xz.png'/></a></td></tr><tr><td colspan='3'> <div id='on'></div></td></tr>"
				$("#addattribute").before(context); 
				}
				/*notoken = 0; */
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				
				alert("数据获取失败！");
			}
		});
}
function editAndSee(val){
var dataval = val.trim();
window.location.href="<%=basePath%>page/ea/websuitMini/addlog.jsp?id="+dataval;
}
</script>
</body>
</html>
