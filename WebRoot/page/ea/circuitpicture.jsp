<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Á÷³ÌÍ¼</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jquery.ganttView.css" />
	<style type="text/css">
		body {
			font-family: tahoma, verdana, helvetica;
			font-size: 0.8em;
			padding: 10px;
		}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div id="ganttChart"></div>
	<br/><br/>
	<div id="eventMessage"></div>

	<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/date.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.ganttView.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/data.js"></script>
	<script type="text/javascript">
		$(function () {
			$("#ganttChart").ganttView({ 
				data: ganttData,
				start: new Date(2010,09,01),
				end: new Date(2010,10,30),
				slideWidth: 1200,
				behavior: {
					onClick: function (data) { 
						var msg = "You clicked on an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
						$("#eventMessage").text(msg);	
					},
					onResize: function (data) { 
						var msg = "You resized an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
						$("#eventMessage").text(msg);	
					},
					onDrag: function (data) { 
						var msg = "You dragged an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
						$("#eventMessage").text(msg);	
					}
				}
			});
		});
	</script>
  </body>
</html>
