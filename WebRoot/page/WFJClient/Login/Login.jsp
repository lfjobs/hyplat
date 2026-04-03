<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>登录</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_login.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
	<script>
		var basePath = "<%=basePath%>";
		var user="${user }";
		var ccompanyId="${ccompanyId }"
		var sccid="${sccid}";
	</script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/WFJClient/Login/left.png"></a></li>
        <li style="width: 80%;text-align: center;">登录数字地球</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content">
    <div class="login_text">
        <h2>Welcome To Digital Globe</h2>
        <p>欢迎使用数字地球</p>
    </div>
    <form id="myform" onsubmit="return check(this)" method="post">
        <input type="tel" name="customer.account" placeholder="请输入手机号" id="tel">
        <input type="password" name="customer.password" placeholder="请输入密码" id="password">
        <input type="button" value="注册" id="zc">
        <input type="submit" value="登录" id="dl">
        <a href="<%=basePath%>ea/consignee/ea_forgetPassWord.jspa?sccid=${sccid }&user=${user }&ccompanyId=${ccompanyId }"><p class="forpwd">忘记密码</p></a>
    </form>
</div>
<div class="alert">
	<p><span>手机号或密码错误</span></p>
</div>
<script>
	var paramter="<%=request.getParameter("paramter")%>";
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content").css("height",$(window).height()*0.92-1+"px");
		
		if(paramter=="error"){
			$(".alert").show().delay(2000).hide(0);
		}

        // var num1=num2=num3=0
        window.onload = window.onresize = function(){
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //console.log(clientWidth);
            //通过屏幕宽度去设置不同的后台根字体的大小
            //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
        }
    });
</script>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/Login/Login.js"></script>
</body>
</html>