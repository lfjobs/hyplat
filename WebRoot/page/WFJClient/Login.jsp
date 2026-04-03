<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>客户登陆</title> 
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head> 
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	<div class="con">
		<form action="<%=basePath%>/ea/wfj/ea_customerLogin.jspa" method="post" id="submit">
		    <div class="loginDiv">
		    	<div class="logintype">
			    	 公司：<input type="radio" name="shopCusCom.cusType" value="2"/>&nbsp;&nbsp;&nbsp;
			    	 店铺：<input type="radio" name="shopCusCom.cusType" value="4"/>&nbsp;&nbsp;&nbsp;
			    	 客户：<input type="radio" name="shopCusCom.cusType" value="6" checked="checked"/>
		    	</div>
		    	 
		        <div class="user">
		            <img alt="" title="" src="<%=basePath %>/images/WFJClient/loginUser.png"/>
		            <input type="text" id="account" maxlength="70" placeholder="Account" class="input" name="customer.account" />
		        </div>
		        <div class="pwd">
		            <img alt="" title="" src="<%=basePath %>/images/WFJClient/loginPwd.png"/>
		            <input type="password" id="password" maxlength="70" placeholder="Password" class="input" name="customer.password" style="height: 28px;line-height: 28px;padding: 2px;width: 250px;"/>
		        </div>
		    </div>
		    <div class="clear"></div>
		    <div class="errormsg"><% if(request.getParameter("paramter")!=null &&request.getParameter("paramter")!="error"){%>用户名或密码错误！<%} %></div>
		    <div class="clear"></div>		    
		    <div class="loginButton" id="login">登录</div>
	    </form> 
	    <div class="loginA">
	    	<a class="fl" href="<%=basePath%>/page/WFJClient/Customer/CustomerReg.jsp">注册</a> 
	    	<a class="fr" href="<%=basePath%>/page/WFJClient/Customer/PwdRetrieve.jsp">找回密码${steep}</a>
	    </div>
	</div>
  </body>
  <script type="text/javascript">

  	$("#login").click(function () {
  		var account = $("#account").val();
 	    var password = $("#password").val();
 	    	 
 	    if (account == "") {
 	        $(".errormsg").html("用户名不能为空").show();
 	        return false;
 	    }
 	    if (password == "") {
 	        $(".errormsg").html("密码不能为空").show();
 	        return false;
 	    }
		$("#submit").submit();
  	});
  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML="登陆";
		doc.getElementById("return").onclick=function(){
			window.history.go(-1);
		}
	});
  </script>
</html>
