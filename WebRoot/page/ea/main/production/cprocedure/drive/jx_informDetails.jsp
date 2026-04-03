<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
   <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/resest1.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/mo_student.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/jquery.min.js"></script>
    <title>通知详情</title>
</head>

<body>
    <!-- header 开始  -->
    <header class="mem_header">
        <a href="javascript:history.back(-1)" class="back"></a>
        <h1>通知详情</h1>
    </header>
    <!--  header 结束  -->
    <!-- 页面内容 开始  -->
    <div class="wrap_page">
        <section class="inform_con_wrap">
	        <div class="inform_con_hd">
	            <h2>${list[0][0] }</h2>
	            <div class="inform_time clearfix">
	               <span>${list[0][1] }</span>
	               <span>通知</span>
	            </div>
	        </div>
	        <c:forEach items="${list }" var="s">
	            <div class="inform_con">
	                ${s[2] }
	            </div>
	    	</c:forEach>
        </section>
    </div>
    <!--  页面内容 结束 -->
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>

</body>

</html>
