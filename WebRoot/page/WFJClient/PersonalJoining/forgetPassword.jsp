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
    <title>忘记密码</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_login.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
	<script>
		var basePath='<%=basePath%>';
		var i;
		var c=0;
		var d=1;
		var q=0;
		var times=59;
	</script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/WFJClient/Login/left.png"></a></li>
        <li style="width: 80%;text-align: center;">忘记密码</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content">
    <div class="login_text reg_text">
        <h2>Welcome To SZDQ</h2>
        <p>欢迎使用数字地球</p>
    </div>
    <form id="myform" method="post">
        <input type="tel" name="cuscom.account" placeholder="请输入手机号" id="tel" onblur="isshouji()">
        <div class="yzm">
        	<input type="hidden" name="user" value="${user}"/>
        	<input type="hidden" name="sccid" value="${sccid}"/>
        	<input type="hidden" name="ccompanyId" value="${ccompanyId}"/>
            <input type="text"  placeholder="验证码" id="validate" onblur="yanz()" maxlength="6">
            <input type="button" value="获取验证码" class="huoquyzm" onclick="duanxin()">
        </div>
        <input type="password" name="password" placeholder="请输入新密码" id="password">
        <input type="password" name="password1" placeholder="再次输入新密码" id="password1">
        <input type="submit" value="确定" id="zc2">
    </form>
</div>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content").css("height",$(window).height()*0.92-1+"px");


        var  width = $(window).width();


        // var num1=num2=num3=0
        window.onload = window.onresize = function(){
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //console.log(clientWidth);
            //通过屏幕宽度去设置不同的后台根字体的大小
            //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';

            if(clientWidth>900) {
                document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640*10 + 'px'
            }else{

                document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px'

            }
        }
    });
</script>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/Login/forgetPassword.js?version=20230821"></script>
</body>
</html>