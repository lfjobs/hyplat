<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>登录</title>

<link href="<%=basePath %>page/newMyapp/css/style.css" rel="stylesheet"
	type="text/css">
<script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
</head>
<body style="background: #fff">
	<div id="header" class="login_header">
		<ul>
			<li class="logo">
			                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>page/newMyapp/images/wfj.png" alt="" class="log"></a>
			
			</li>
			<li class="name login_name">
				<div>
					<h3>账户登录注册</h3>

				</div></li>
			<a id="index">
				<li class="login login_login"><img
					src="<%=basePath %>page/newMyapp/images/return2.png" alt="">
					<div>
						<h3>返回首页</h3>
					</div> <!--<input type="button" value="登录">
                <input type="button" value="注册">--></li> </a>
		</ul>
	</div>
	<div class="content login_con">
		<ul>
			<li><img src="<%=basePath %>page/newMyapp/images/login_l.png"
				alt="" class="left"></li>
			<li>
				<div class="right">
					<h2>账户登录</h2>
					<form id="submit" method="post"
						action="<%=basePath%>/ea/wfj/ea_customerLogin.jspa">
						<input type="tel"  placeholder="请输入手机号" class="user"
							id="count" 
							name="customer.account"> 
						<input type="password"
							 placeholder="密码" class="lock" id="pw_pwd"
							type="password" name="customer.password"> 
						<label>
						<div class="errormsg"><% if(request.getParameter("paramter")!=null &&request.getParameter("paramter")!="error"){%>用户名或密码错误！<%} %></div>
						<input type="checkbox" checked="checked">&nbsp;<span>自动登录</span>
						</label> 
						<!-- 隐藏 -->
						<%-- <a href="<%=basePath %>page/newMyapp/password.jsp" class="wjmm">忘记密码</a>
					 --%>
						<input type="button" value="登录" id="login">
						<input type="hidden" name="loginWay" value="pcLogin">
					</form>
					<hr style="border-top: 1px solid #ddd;margin: 0;">
					<div class="zhuce">
						<a id="register">立即注册<img
							src="<%=basePath %>page/newMyapp/images/right.png" alt="">
						</a>
					</div>
				</div></li>
		</ul>
	</div>

	
	<script>
    $(document).ready(function(){
		var basePath = "<%=basePath%>";
		if("${param.error}"){
			alert("您的账号或密码有误！");
		}
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

		$("#index").click(function(){
            var url = basePath+"ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
            document.location.href = url;
        });
        $("#register").click(function(){
            var url = basePath+"page/newMyapp/register.jsp";
            document.location.href = url;
        });
        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {

                $(".return").hide();

            }
        });
        
        $("#login").click(function(){
    		var reg=new RegExp("^1[3|4|5|7|8][0-9]\\d{8}$");
  		  	if($("#count").val()==''){
			  alert("请输入手机号");
			  $("#count").focus();
			  return;
		  	} 
  		  	if(!reg.test($("#count").val())){
		  			alert("手机号格式不正确！");
		  			$("#count").focus();
		  			return;
		  	}
    		if($("#pw_pwd").val()==''){
  			    alert("请输入密码");
  			    return;
  			  }
    		$("#submit").submit();
    	});
        
    })
</script>
</body>
</html>