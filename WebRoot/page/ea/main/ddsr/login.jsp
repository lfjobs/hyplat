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
<title>预约登录</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />


<link rel="stylesheet" href="<%=basePath%>/css/ea/ddsr/qz_home.css" />
<style type="text/css">
div#main_body{
	text-align: center;
}
.button {  
    display: inline-block;  
    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
    *display: inline;  
    vertical-align: baseline;  
    margin: 0 2px;  
    outline: none;  
    cursor: pointer;  
    text-align: center;  
    text-decoration: none;  
    font: 14px/100% Arial, Helvetica, sans-serif;  
    padding: .5em 2em .55em;  
    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
    -webkit-border-radius: .5em;   
    -moz-border-radius: .5em;  
    border-radius: .5em;  
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    box-shadow: 0 1px 2px rgba(0,0,0,.2);  
}
.button:hover {  
    text-decoration: none;  
}  
.button:active {  
    position: relative;  
    top: 1px;  
}    
</style>
</head>
<body>
	<div id="main_body" >
		<section class="s_reg s_login">
			<div></div><div></div>
			<form action="<%=basePath%>/ea/appointmentbymicroletter/ea_login.jspa?" id="form1" >
				<input type="text" id="username" name="dssrstudent.dtHrStaff.staffIdentityCard" value="${staffIdentityCard}" placeholder="身份证" >
				<p class="wrong_tip" id="username_tip"></p>
				<input type="password" id="password" name="dssrstudent.studPassword" value="${studPassword}"
					placeholder="密码">
				<p class="wrong_tip" id="password_tip"></p>
				<input type="submit" id="submit" value="登录">
				<p class="wrong_tip" id="submit_tip"></p>
				<input type="button" id="submit" value="设置密码" class="button"  onclick="javascript:document.location.href='<%=basePath%>/ea/appointmentbymicroletter/ea_toRegistered.jspa?'">
			</form>
		</section>
	</div>
	<div style="text-align: center;color: red;">${message}</div>
</body>
</html>