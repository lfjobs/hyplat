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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>基本信息</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/calendar.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/new_style.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/calendar.min.js"></script>
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var staffId ='${param.staffId}';
    </script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/driving/elkc/left_jt.png"></a>
        </li>
        <li style="width: 80%;">基本资料</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <ul class="basics">
                <a href="<%=basePath%>/driving/elkc/ea_studentBasicInfoPage.jspa?staffId=${param.staffId}">
                    <li>
                        学员基本资料
                        <img src="<%=basePath%>/images/ea/driving/elkc/right.png">
                    </li>
                </a>
                <a href="<%=basePath%>/driving/elkc/ea_studentApplyInfoPage.jspa?staffId=${param.staffId}">
                    <li>
                        学员报名信息
                        <img src="<%=basePath%>/images/ea/driving/elkc/right.png">
                    </li>
                </a>
            </ul>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");


    });
</script>
</body>
</html>
