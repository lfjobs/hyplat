<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/productdescribe.css">
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<link rel="stylesheet" href="<%=basePath %>css/WFJClient/shop.css"/>
    <title>产品管理</title>
</head>
<script>
   var basePath='<%=basePath%>';
   var pagenumber=0;
   var pagecount=0;
   var flag='onsale';
   var t;
   var user='${user}';
   var companyId='${companyId}';
   var backu='<%=session.getAttribute("vipback")%>';
   var ret='${ret}';
   var sys='${sys}';
   var search = "";
</script>
<body>
<div class="product_size">
    <div class="top">
    <ul>
		<li class="arrow"><a  href="javascript:history.back();" target="_self"><img src="<%=basePath%>images/WFJClient/PersonalJoining/top_reture.png"/></a></li>

		<li>产品管理</li>
		<li id="search"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/search.png" alt=""></li>
	</ul>
	</div>
	<ul class="main_nav">
                <li><div class="on">出售中</div></li>
                <li><div>仓库中</div></li></ul>
    <div class="main">
        <div class="main_hide">
            
        
        <div class="hide1">
        	<div class="hide2">
	            <div class="onsale">
	            </div>
	       		<div class="oncang">		
	       		</div>
       		</div>
       	</div>
       	</div>
    </div>
    <footer>
        <div class="footer_right" style="width:100%;" onclick="addOrEdit()">产品发布</div>
    </footer>
</div>



<div class="alert123">
</div>
<div class="alert_search">
	<div class="top">
		<input type="search" name="" placeholder="产品名称" onfocus="this.placeholder=''" onblur="this.placeholder='产品名称'" value="" class="sousuo">
		<input type="submit" value="搜索" id="ss">
		<input type="submit" value="取消" id="qx">
	</div>
</div>
</body>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/product.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/productsmanage.js"></script>
</html>
<script>
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
</script>