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
<title>个人信息</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a href="###" class="back"></a>
        <h1>个人信息</h1>
        <a href="javascript:;" class="head_R">保存</a>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <a href="###" class="per_info_box flex flex_align_center">
            <span>头像</span>
            <div class="per_son_R flex flex_1 flex_justify_right flex_align_center">
                <div class="mem_head_img">
                    <img src="images/head_img.jpg" alt="会员头像">
                </div>
                <i class="per_son_Rico"></i>
            </div>
        </a>
        <a href="###" class="per_info_box flex flex_align_center">
            <span>人名</span>
            <div class="per_son_R flex flex_1 flex_justify_right flex_align_center">
                <div class="per_son_Rtext flex_1">
                    张三分
                </div>
                <i class="per_son_Rico"></i>
            </div>
        </a>
        <a href="###" class="per_info_box flex flex_align_center">
            <span>会员</span>
            <div class="per_son_R flex flex_1 flex_justify_right flex_align_center">
                <div class="per_son_Rtext flex_1">
                    代理商商城业主会员
                </div>
                <i class="per_son_Rico"></i>
            </div>
        </a>
        <a href="###" class="per_info_box flex flex_align_center">
            <span>我的二维码</span>
            <div class="per_son_R flex flex_1 flex_justify_right flex_align_center">
                <i class="per_son_QR"></i>
                <i class="per_son_Rico"></i>
            </div>
        </a>
    </div>
    <!--  页面内容 end -->
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