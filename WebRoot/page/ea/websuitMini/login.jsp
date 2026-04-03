<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>5L5C微办公登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=0" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/websuitMini/websuit.css" />
  	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  <style>
body{background-image:url('<%=basePath%>/images/ea/login/img/login_bg.png'); background-size:500px;}
 .con{width:360px; margin:auto;}
.logo_div { width:100%; height:200px;}
.logo_div .logo_img { width:100%; height:70px; margin-top:100px; text-align:center;}
.logo_div .logo_img img { width:116px; height:70px; margin:auto; }
.tab_div{ width:100%; height:60px; border-bottom:1px solid #fff;}
.tab_left_img{ width:40px; height:60px;}
.tab_left_img img { width:25px; height:25px; margin-left:4px; margin-top:25px;}
.tab_con_div{ width:320px; height:60px;}
.tab_con_div input{ background-color:transparent; border:none;font-size: 16px;color: #fff;line-height: 20px; float:left;padding: 10px 0;
margin: -10px 0; margin-left:10px; margin-top:15px; width:310px; font-weight:bold;}
.border_bottom_none{ border-bottom:none;}
.margin_top{margin-top:20px;}
.login_btn{ width:100%; height:60px; margin-top:20px;}
.login_btn img{ width:100%; height:60px;}
.Remember{ width:100%; text-align:center; height:30px; margin-top:30px;}
.Remember input{ margin-top:10px; padding-top:10px; }
.Remember{ color:#fff; height:30px; line-height:30px; display:block;}
</style>
  <body >
   <div class="con">
  <div class="logo_div fl">
     <div class="logo_img fl">
       <img alt="" title="" src="<%=basePath%>/images/websuitMini/logo.png"/>
     </div>
  </div>
  <div class="clear"></div>
    <form id="loginForm" name="loginForm" method="post"  action="<%=basePath%>ea/loginMini.jspa?">
     <input type="submit" name="submit" style="display: none">
      <input type="text" value="00" name="companyType" style="display: none;" id="companyType">
      <input type="text" value="mobile" name="logoType" style="display: none;" id="logoType">
      
  <div class="tab_div fl">

    <div class="tab_left_img fl">
      <img alt="" title="" src="<%=basePath%>/images/websuitMini/house.png"/>
    </div>
    
    <div class="tab_con_div fl">
        <input type="text" id="organization_institutions_name" class="input" name="companyIdentifier" placeholder="组织机构名称" maxlength="20">
    </div>
  </div>
  <div class="clear"></div>
  <div class="tab_div fl">
    <div class="tab_left_img fl">
      <img alt="" title="" src="<%=basePath%>/images/websuitMini/users.png"/>
    </div>
    <div class="tab_con_div fl">
        <input type="text" id="user_name" class="input" name="account.accountEmail" placeholder="用户名称" maxlength="20">
    </div>
  </div>
  <div class="clear"></div>
  <div class="tab_div fl">
    <div class="tab_left_img fl">
      <img alt="" title="" src="<%=basePath%>/images/websuitMini/pass.png"/>
    </div>
    <div class="tab_con_div fl">
        <input type="password" id="passwords" class="input"  name="account.accountPassword" placeholder="密码" maxlength="20">
    </div>
  </div>
  <div class="clear"></div>
  <div class="tab_div border_bottom_none margin_top fl">
    <a class="login_btn fl" ><img  id = "loginButton" src="<%=basePath%>/images/websuitMini/login.png" /></a>
  </div>
  <div class="clear"></div>
  </form>
  <div class="tab_div border_bottom_none fl">
     <div class="Remember fl">
     <input name="pass" type="checkbox" value="1">
     <label name="pass">记住密码</label>
     </div>
  </div>
  <div class="clear"></div>
</div>
 <script type="text/javascript">
   	 $(function(){ 
   		$("#loginButton").click(function(){
   			 if($("#organization_institutions_name").val()== ""){
   			alert("组织机构名称不能为空！");
   			return;
   			}if($("#user_name").val()== ""){
   			alert("用户名不能为空！");
   			return;
   			} if($("#passwords").val()== ""){
   			alert("密码不能为空！");
   			return;
   			}
   			<%-- //window.location.href= '<%=basePath%>/page/ea/websuitMini/index.jsp'; --%>
   			document.loginForm.submit.click();
   				});
   		  });
   </script>
  </body>
</html>
