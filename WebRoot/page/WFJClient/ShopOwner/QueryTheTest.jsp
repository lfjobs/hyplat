<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/font-size.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/new_style.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/forum/new-page.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/queryTheTest.js"></script>
    <title>选择驾校</title>

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var city = "";
        var eastLongitude = "";
        var northLatitude = "";
    </script>
</head>

<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:void(0)" onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
        </li>
        <li style="width: 80%;">选择驾校</li>
        <li style="width: 10%;" class="park_area"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="search_frd_ search_coach">
            <div class="search_frd">
                <input type="search" value="" class="search">
                <div class="search_">
                    <img src="<%=basePath%>images/BuildPlatform/ico-search.png" alt="">
                    <p>搜索驾校名称</p>
                </div>
            </div>
        </div>
        <div class="con">
            <ul class="coach_list">
                <!--js拼接-->
            </ul>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height",$(window).height()*0.92-$(".search_frd_").height()-5+"px");


        /*搜索*/
        $(".search_frd input").focus(function(){
            $(".search_frd .search_").hide();
        });
        $(".search_frd input").blur(function(){
            if( $(".search_frd input").val()==""){
                $(".search_frd .search_").show();
            }else{
                $(".search_frd .search_").hide();
            }
        });
        $(".search_frd .search_").click(function(){
            $(".search_frd .search_").hide();
            $(".search_frd input").focus();
        });

        /*列表选择*/
        $(".coach_list li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        })


    });
</script>

</body>
</html>