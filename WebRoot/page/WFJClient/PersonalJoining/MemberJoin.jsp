<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>会员列表</title>
  	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companyWeb.css" />
   	<link type="text/css" rel="stylesheet" href="<%=basePath %>css/bootstrap.css">
   	<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
   	<script type="text/javascript" src="<%=basePath %>js/bootstrap.js"></script>
   	<script type="text/javascript" src="<%=basePath %>js/toucher.js"></script>
</head>
<body>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1);"> <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/left_jt.png"></a>
       </li>
       <li style="width: 80%;">会员列表</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">

<div class="content">
   <ul class="age-list_mil">
   		<c:forEach items="${beans }" var="entity">
       <a href="partnership-listing.html">
        <li>
            <div class="age-list_mil_head"><img src="<%=basePath %>${entity[3]}" alt=""> </div>
            <div class="age-list_mil_txt">
                <h3>${entity[0] }</h3>
            </div>
        </li>
       </a></c:forEach>
   </ul>
</div>

</div>

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position:fixed;");
        $(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
        $(".content").attr("style",";overflow: auto;");


    })
</script>
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
</body>
</html>