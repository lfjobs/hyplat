<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/set/accountSecurity.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

	<title>&lrm;</title>
</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
			</a>
		</li>
		<li>
			账号与密码
		</li>
	</ul>
</header>

<div class="content">
<section>
	<div class="cts-div"><label>账号</label><div class="img-right"><span class="rz-span" id="zh"></span></div></div>
	<div class="cts-div" id="loginmm"><label>登录密码</label><div class="img-right"><img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png"></div></div>
	<div class="cts-div" id="jymm"><label>交易密码</label><div class="img-right"><img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png"></div><span class="rz-span" id="isSet">${isSet eq "yes"?"已保护":"未保护"}</span><img class="lock" src="<%=basePath%>images/WFJClient/pc/my/locked.png"></div>
</section>




</div>
</body>
<script type="text/javascript">
	var  basePath = "<%=basePath%>";
	var account = "${account}";
	var isSet = "${isSet}"

	$(function(){
            if(isSet=="no"){
				$(".lock").attr("src",basePath+"images/WFJClient/pc/my/unlock.png")
			}

		$("#zh").text(account);

		$("#loginmm").click(function(){

			document.location.href = basePath+"/page/WFJClient/pc/my/set/loginPassowrd.jsp";

		});

		$("#jymm").click(function(){

			if(isSet=="yes"){
				document.location.href = basePath+"/page/WFJClient/pc/my/set/payPassowrd.jsp";
			}else{
				document.location.href = basePath+"page/WFJClient/pc/my/set/forgetPassword.jsp?pswtype=交易&init=1";
			}
		});

	});

</script>
</html>
