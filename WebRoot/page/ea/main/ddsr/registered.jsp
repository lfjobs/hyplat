<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>设置密码</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />


<link rel="stylesheet" href="<%=basePath%>/css/ea/ddsr/qz_home.css" />

</head>
<body>
	<div id="main_body">
		<section class="s_reg s_login">
			<div></div><div></div>
			<form action="<%=basePath%>/ea/appointmentbymicroletter/ea_registered.jspa?" id="form1" >
				<input type="text" id="username" name="dssrstudent.dtHrStaff.staffIdentityCard"
					placeholder="身份证">
				<p class="wrong_tip" id="username_tip"></p>
				<input type="password" id="password" name="dssrstudent.studPassword" placeholder="密码">
				<p class="wrong_tip" id="password_tip"></p>
				<input type="password" id="passwordOther" name="dssrstudent.studPasswordOther" placeholder="重复密码">
				<p class="wrong_tip" id="passwordOther_tip"></p>
				<input type="submit" id="submit" value="确定">
			</form>
		</section>
	</div>
</body>
</html>