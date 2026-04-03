<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
</head>

<body>
<header>
    <ul class="pub_top1">
        <li style="width: 10%;">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
        </li>
        <li style="width: 80%;">公告详情</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content">
    <div class="pub-det_txt">
        <div class="pub-det_txt_top" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/pub-h.png"><span>${object1[0] }</span></div>
        <hr style="border-top: 1px solid #ddd;width: 90%;margin: 1px auto;">
        <div class="pub-det_txt_cen">
        	<s:iterator value="maplist1" var="ml">
		        ${ml.value[2]}
		        <br/>
	        </s:iterator>
        </div>
        <div class="pub-det_txt_bot">
            <div>
                <span>${fn:substring(object1[1], 0, 10)}</span>
            </div>
        </div>
    </div>
</div>

<script>
	var basePath="<%=basePath%>";
	var ppid ="${ppid }";
    $(document).ready(function(){
    	$(".pub_top1").find("li").eq(0).click(function(){
			document.location.href=basePath+"ea/wfjplatform/ea_platformNotice.jspa?ppid="+ppid;
		});
    
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");

    });
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