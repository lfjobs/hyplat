<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>5L5C中联园区微分金系统平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <link rel="stylesheet" href="<%=basePath%>css/index.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/bootstrap.css"/>
	
    <!--html5shiv.min让更低版本的ie浏览器支持HTML5标记-->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="<%=basePath%>js/html5shiv.min.js"></script>
    <script src="<%=basePath%>js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="erwei" style="position: fixed;left: 1px;top: 10px;width: 100px;">
    <img src="<%=basePath%>images/appewm.png" width="100" height="100" alt=""/>
    <a style="color: black;">&nbsp;微分金 APP</a>
</div>
<form name="loginForm" method="post" id="loginForm" action="">
<input type="submit" name="submit" style="display: none">
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div class="head">
                <div class="logo center-block text-center">
                    <div class="logo_top">
                        <img src="<%=basePath%>images/ea/login/img/wfjlogo.png" alt=""/>
                    </div>
                    <div class="logo-bottom" id="comtitle">
                        <p>5L5C系统资源平台</p>
                    </div>
                </div>
            </div>
            <div class="content center-block text-center">
                <div class="i1 center-block text-center" >
                    <div class="left pull-left">
                    <img src="<%=basePath%>images/ea/login/img/logo_ico01.png" alt=""/>
                    </div>
                    <div class="right pull-left">
                    <input type="text" name="companyIdentifier" id="strj1" maxlength="50" value="请输入组织机构名" onfocus="if(this.value=='请输入组织机构名'){this.value='';}"  onblur="if(this.value==''){this.value='请输入组织机构名';}"/>
                    </div>
                </div>
                <div class="i1 center-block text-center" >
                <div class="left pull-left">
                    <img src="<%=basePath%>images/ea/login/img/logo_ico02.png" alt=""/>
                </div>
                <div class="right pull-left">
                    <input type="text" name="account.accountEmail" id="username" maxlength="20" value="请输入用户名" onfocus="if(this.value=='请输入用户名'){this.value='';}"  onblur="if(this.value==''){this.value='请输入用户名';}"/>
                </div>
                </div>
                <div class="i1 center-block text-center" >
                    <div class="left pull-left">
                        <img src="<%=basePath%>images/ea/login/img/logo_ico03.png" alt=""/>
                    </div>
                    <div class="right pull-left">
                        <input class="pwd" type="password" id="password" name="account.accountPassword" style="display:none"/>
                        <input class="pwd1" type="text" name="pwd" value="请输入密码"/>
                    </div>
                </div>
                <div class="yanz center-block text-center" >
                    <div class="yan_left pull-left">
                    <div class="pull-left">
                    <div class="left pull-left">
                        <img src="<%=basePath%>images/ea/login/img/logo_ico04.png" alt=""/>
                    </div>
                    <div class="right pull-left">
                        <input type="text" name="validateCode" id="validate" maxlength="4" value="验证码" onfocus="if(this.value=='验证码'){this.value='';}"  onblur="if(this.value==''){this.value='验证码';}"/>
                    </div>
                        </div>
                    </div>
                    <div class="V_img pull-left" href="javascript:void(0);">
                        <img border="0" src="<%=basePath%>page/ea/security_code.jsp?abcd=0.9140284241695912" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abcd='+Math.random()" name="validateImage" id="validateCode">
                    </div>
                </div>

                <div class="loging_v center-block text-center">
                    <a class="pull-left denglu" href="javascript:void(0);" onclick="login()">

                    </a>
                    <a class="forget pull-left" href="<%=basePath%>page/ea/RePassword/rePassLogin.jsp" style="color: black;">
                        忘记密码?
                    </a>
                    <a class="forget pull-left" target="_blank" href="<%=basePath%>//ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform&nogr=nogr" style="color: black;">
                       注册公司
                    </a>
                </div>
                <p class="floor center-block text-center" style="color:black;">版权所有：北京天太世统科技有限公司  服务热线：010-64167113 京ICP备10034132号-2</p>

            </div>
        </div>
    </div>
</div>
</form>
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/bootstrap.js"></script>
<script src="<%=basePath%>js/ea/index.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath%>';
	var pam="${param.ct}";
	var result = "${result}";
	var error = "";
	$(".pwd1").focus(function(){
        $(".pwd1").hide();
        $(".pwd").show();
        $(".pwd").focus();
    });
    $(".pwd").blur(function() {
        if($(".pwd").val()==""){
            $(".pwd").hide();
            $(".pwd1").show();
            $(".pwd").focus();
        }

    });
	if (result == "1") {
		error = "登录企业不存在!";
		alert(error);            
	} else if (result == "2") {
		error = "登录所在公司状态不正常！";
		alert(error);            
	} else if (result == "4") {
		error = "登录帐号被停用！";            
		$(".errormsg").html(error);
	} else if (result == "3" || result == "5") {
		error = "登录账号或密码不正确!";
		alert(error);            
	} else if (result == "99") {
		error = "验证码不正确!";
		alert(error);            
	} else if (result == "98") {
		error = "验证码不能为空!";
		alert(error);            
	} else if (result == "97") {
		error = "用户名和密码不能为空!";
		alert(error);            
	}
//	else if (result == "6") {
//		error = "禁止多开登录!";
//		alert(error);
//	}
	if(pam=="mysl"){
		$("#comtitle").text("四川省绵阳水利设计研究院");
		$("#strj1").val("mysl").attr("readonly","readonly");
		$("#loginForm").attr("action", basePath + "ea/login.jspa?");
	}
</script>
</body>
</html>