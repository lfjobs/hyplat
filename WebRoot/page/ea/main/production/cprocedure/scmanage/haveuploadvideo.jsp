<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title>上传视频</title>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/base.css">
<link rel="stylesheet" href="<%=basePath%>css/ea/production/scmanage/swiper-3.3.1.min.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/sc_manger.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/scmanage/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/scmanage/haveuploadvideo.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var pagecount = 0;
var pageSize = 0;
var pagenumber = 0;
var count = 0;
var groupname = "${materialGroup.groupname}";
var mgId = "${materialGroup.mgId}";
</script>
</head>
<body>
    <!-- header start  -->
    <header class="com_head">
        <a href="javascript:history.go(-1)"  class="back"></a>
        <h1>产品视频</h1>
      <a href="<%=basePath%>ea/scm/ea_newOrEditorGroup.jspa?materialGroup.mgId=${materialGroup.mgId}&type=novideo" class="head_R">编辑分组</a>
    </header>
    <div class="wrap_page">
        <a href="<%=basePath%>ea/scm/ea_uploadFileIndex.jspa?materialGroup.mgId=${materialGroup.mgId}&materialGroup.groupname=${materialGroup.groupname}&type=novideo" class="group_btn">上 传 视 频</a>
        <ul class="photos_wrap clearfix">
           <%--  <li class="photo_sbox" data-video="video/test-video.mp4" data-tit="上传视频标题" data-time="2016年8月17日上传">
                <img src="<%=basePath%>images/ea/production/scmanage/photo_s01_200x200.jpg" alt="">
                <i class="video_ico"></i>
            </li>
            <li class="photo_sbox" data-video="video/test-video.mp4" data-tit="张国荣 倩女幽魂" data-time="2014年8月16日上传">
                <img src="<%=basePath%>images/ea/production/scmanage/photo_s02_200x200.jpg" alt="">
                <i class="video_ico"></i>
            </li> --%>
        </ul>
        <div class="load_all">已显示全部</div>
        <!--图片点击放大 开始-->
        <div class="overlay_block" id="overlay">
            <div class="overlay_block_head">
                <a href="javascript:;" class="back_overlay"></a>
                <h1 class="namevideo">产品视频</h1>
            </div>
            <div class="swiper-container gallery-top">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <video src="" controls="controls" id="video_box"></video>
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="hidden"  value=""  id="videoid">
                                <input type="text" placeholder="添加名称…" id="video_tit">
                                <div class="photo_fd">
                                    <span class="photo_time" id="video_time"></span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="swiper-container gallery-thumbs">
            </div>
        </div>
    </div>
    <!--图片点击放大 结束-->
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
