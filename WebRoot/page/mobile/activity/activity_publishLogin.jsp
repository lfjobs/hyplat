<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<title>微信发布活动登陆</title> <%@ page language="java" pageEncoding="UTF-8"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<style>
.across {
	height: 12px;
	width: 399px;
}

.inputtxt {
	border: #c2cbd8 0px solid;
	width: 80%;
	height: 28px;
	margin-top: 8px;
	line-height: 28px;
	margin-left: 3px;
	/*   background-color: #A9CBDA; /*#ACD1E1*/ */
	color: White;
	font-size: 16px;
	font-family: 微软雅黑;
}

.contain {
	width: 100% px;
	height: 1000px;
	background: #f0f0f6;
	margin: 0 auto;
}

.nav {
	width: 100% px;
	height: 90px;
	background: #2bb0c3;
}

.nav h3 {
	font-family: "微软雅黑";
	font-size: 30px;
	color: #FFF;
	line-height: 90px;
	text-align: center;
	background: url(images/j.png) no-repeat 20px;
}

.nav h3 span {
	display: inline-block;
	float: right;
	padding-right: 10px;
}

.nav h3 span a {
	color: #fff;
}

a {
	text-decoration: none;
	font-family: "微软雅黑";
	font-size: 20px;
	color: #FFF;
}

.tup {
	text-align: center;
	margin-top: 35px;
}

.shur {
	width: 90%;
	height: 204px;
	background: #FFF;
	border: 1px solid #999;
	margin: 0 auto;
	margin-top: 70px;
}

.yongh {
	font-family: " 微软雅黑";
	color: #000;
	line-height: 100px;
	font-weight: 600;
}

.shur tr td {
	padding-left: 5%;
	border: 1px solid #999;
}

.dengl {
	text-align: center;
	margin-top: 30px;
}

.zid {
	width: 50%;
	float: left;
}

label {
	font-family: "微软雅黑";
	font-size: 20px;
}

.wangj {
	width: 50%;
	float: left;
}

.wangj a {
	font-family: "微软雅黑";
	font-size: 20px;
	color: #000;
	width: 100%;
	margin-left: 80px;
}

.ann {
	text-align: center;
	margin-top: 50px;
	width: 100%;
}
.login {
	height:50%;
	width:80%;
}
</style>
	<body style="text-align:center;">
		<div class="contain">
			<div class="nav">
				<h3>
					登 录 <span><a href="<%=basePath%>\page\ea\vlogin.jsp">个人注册</a>
					</span>
				</h3>
			</div>
			<div class="tup">

				<div class="across"></div>
				<img src="<%=basePath%>images/ea/login/logo.png" />
			</div>
			<form name="loginForm" method="post" id="loginForm" action="">
				<input type="submit" name="submit" style="display: none"> <input
					type="hidden" name="companyIdentifier" id="strj1" class="inputtext"
					value="strj" />
				<table class="shur"
					style="border-collapse:collapse; border:1px solid #999;">
					<tr class="yongh">
						<div>
							<td style="border-right:0">用户名:<input type="text"
								name="customer.account" class="inputtxt" id="username"
								value="不能为空" style="color:gray;" /></td>
						</div>
						<td style="border-left:0">&nbsp;<img
							src="<%=basePath%>images/ea/login/g.png" />
						</td>
					</tr>
					<tr class="yongh">
						<div>
							<td style="border-right:0">密码： <input type="text"
								name="customer.password" class="inputtxt" id="password"
								value="不能为空"  style="color:gray;" /></td>
						</div>
						<td style="border-left:0">&nbsp;</td>
					</tr>
				</table>

				<div class="across"></div>
				<div onclick="login()" class="login">
					<img src="<%=basePath%>images/ea/login/d.png"  width="458px"/>
				</div>
				<div class="across"></div>
				<div  class="ylogin()" onclick="jlogin()">
					<a href="<%=basePath%>ea/activity/ea_publishLogin.jspa?inforType=02">	<img src="<%=basePath%>images/ea/login/y.png"  width="458px"/> </a>
				</div>
			</form>
		</div>
		<script type="text/javascript">
        $(document).ready(function () {
        	 $(".inputtxt").focus(function () {
 			 	if($(this).val()=="不能为空"){
 			 		$(this).val("");
 			 	}
 			 	$(this).css("color", "#000");
 			 }).blur(function(){
 			 	if($(this).val()==""){
 			 		$(this).val("不能为空");
 			 		$(this).css("color", "gray");
 			 	}
 			 });

             $("#loginForm").attr("action", "<%=basePath%>ea/actLogin.jspa?activityLogin=activity&companyType=02&weixinCompanyId=<%=request.getParameter("weixinCompanyId")%>");
							});
			// 登录按钮点击触发事件，判断文本框输入是否为空，是否合法
			function login() {
				document.loginForm.submit.click();
			}
		</script>
	</body>
</html>
