<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML >
<html>
<head>
<base href="<%=basePath%>">

<title>教练查询</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/ea/ddsr/qz_home.css" />
	
</head>

<body>
	<div id="main_body">
		<section class="s_reg s_login">
			<div></div><div></div>
			<form action="<%=basePath%>/ea/appointmentbymicroletter/ea_toSearch.jspa?" id="form1" >
				<input type="text" id="username" name="ddsrreservationrecord.searchStaDate" placeholder="起时间" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d+7}'})">
				<p class="wrong_tip" id="username_tip"></p>
				<input type="text" id="password" name="ddsrreservationrecord.searchEndDate"
					placeholder="止时间" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d+7}'})">
				<p class="wrong_tip" id="password_tip"></p>
				<input type="text" id="password" name="ddsrcoach.dtHrStaff.staffName"
					placeholder="教练名">
				<p class="wrong_tip" id="password_tip"></p>
				<input type="hidden" name="search" value="search">
				<input type="submit" id="submit" value="查询">
			</form>
		</section>
	</div>
</body>
</html>
