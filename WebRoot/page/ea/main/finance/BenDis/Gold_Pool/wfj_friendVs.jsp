<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">	
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>好友排名</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/contacts/style13.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.6.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/finance/wfj_friendVs.js"></script>  
	<script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var user="${user}";//样式中用到
    	var khd="${khd}";
    	var account="${customer.account}";   	
    	var t;//计时器
		var pagenumber=0;
		var pagecount=0;
		var flag = "${flag}";
		var sccid="${sccid}";
		var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
		var object = new Array();
		
</script>

</head>
<body>  

	<s:if test="khd==0">
		<header >
		   <ul>
		       <li style="width: 10%;"><a href="javascript:history.go(-1)">   		
		           <img src="<%=basePath %>images/ea/finance/BenDis/left_jt.png"></a> 
			   </li>     
		       <li style="width: 80%;">好友排名</li>
		       <li style="width: 10%;"></li>
		       <div class="clearfix"></div>
		   </ul>
		</header>
	</s:if>
	<s:else>
    	<style type='text/css'>
			.content_hidden{
				margin-top:0;
				padding-top:0;
			}
		</style>
    </s:else>
<div class="content_hidden">
    <div class="content">
        <div class="frid_bg">
            <img src="<%=basePath %>images/ea/finance/BenDis/bg1.png" width="100%" class="frid_img1">
            <div class="frid_h3"><a></a></div>
        </div>
        <div class="frid_text_">
            <img src="<%=basePath %>images/ea/finance/BenDis/line.png" width="100%" class="frid_img2">
            <img src="<%=basePath %>images/ea/finance/BenDis/diand.png" width="100%" class="frid_img3">
            <div>
	            <ul id="head">
						<li>排名</li>
						<li>昵称</li>
						<li>金币数</li>
					</ul>
	            <div class="frid_text">					
	                <table id="frinds"></table>
	            </div>	            	            
            </div>
        </div>
    </div>
</div>
	

</body>
</html>