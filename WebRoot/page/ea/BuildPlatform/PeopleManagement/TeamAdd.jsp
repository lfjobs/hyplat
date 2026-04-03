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
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>北京天太世统科技有限公司</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>

</head>
<body>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="<%=basePath%>/people/manage/ea_teamShow.jspa?"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">添加团队人员</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <ul class="team_per">
                <li>
                    <a href="javascript:;">
                    <img src="<%=basePath %>images/BuildPlatform/address.png" alt="">
                    <div class="txt">
                        <p>通讯录添加</p>
                    </div>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath%>people/manage/ea_manualFriendsAdd.jspa?">
                    <img src="<%=basePath %>images/BuildPlatform/hand.png" alt="">
                    <div class="txt">
                        <p>手动添加</p>
                    </div>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath%>people/manage/ea_friendsAdd.jspa?">
                    <img src="<%=basePath %>images/BuildPlatform/friend.png" alt="">
                    <div class="txt">
                        <p>好友粉丝添加</p>
                    </div>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath%>people/manage/ea_companyAdd.jspa?">
                    <img src="<%=basePath %>images/BuildPlatform/zz.png" alt="">
                    <div class="txt">
                        <p>在职员工添加</p>
                    </div>
                    </a>
                </li>
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
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.92+"px");
        });
</script>
</body>
</html>