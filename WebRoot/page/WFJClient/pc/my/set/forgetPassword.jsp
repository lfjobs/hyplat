<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
	<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>&lrm;</title>
   <link type="text/css" rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/set/forgetPassword.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
	<script>
		var basePath='<%=basePath%>';
        var account = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
        var pswtype = "${param.pswtype}";




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
        <li style="width: 10%;"><a onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>/images/ea/office/contract/selectp/return.png"></a></li>
        <li style="width: 80%;text-align: center;">${param.init eq "1"?"设置":"忘记"}${param.pswtype}密码</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content">
    <form id="myform" method="post" name="myform">
        <input type="tel" name="cuscom.account" placeholder="请输入手机号" id="tel"  readonly>
        <div class="yzm">
            <input type="text"  placeholder="验证码" id="validate" onclick="yanz()" maxlength="6" autocomplete="off">
            <input type="button" value="获取验证码" class="huoquyzm" onclick="duanxin()">
        </div>
        <input type="password" name="password" placeholder="请输入${param.init ne "1"?"新":""}${param.pswtype}密码" id="password" autocomplete="off">
        <input type="password" name="password1" placeholder="再次输入${param.init ne "1"?"新":""}${param.pswtype}密码" id="password1" autocomplete="off">
        <input type="button" value="确定" id="zc2">
    </form>
</div>

<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;"></span>
        </div>
    </center>
</div>
<script type="text/javascript" src="<%=basePath%>js/WFJClient/pc/my/set/forgetPassword.js?version=20230821"></script>
</body>
</html>