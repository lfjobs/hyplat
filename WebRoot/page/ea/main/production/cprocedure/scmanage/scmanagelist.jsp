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
<title>素材管理</title>

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
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/sc_manger.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
</script>
</head>

<body>
	<!-- header start  -->
	<header class="com_head">
		 <a href="javascript:history.go(-1)"  class="back"></a>
		<h1>素材管理</h1>
	</header>
	<div class="wrap_page clearfix">
		<a href="<%=basePath%>ea/scm/ea_findCateManageIndex.jspa?type=nopic" class="suc_wrap">
			<div class="suc_box suc_img"></div>
			<div class="suc_boxtit">图片素材</div>
			<div class="suc_num">${picnum}</div>
		</a> <a href="<%=basePath%>ea/scm/ea_findCateManageIndex.jspa?type=novideo"  class="suc_wrap">
			<div class="suc_box suc_video"></div>
			<div class="suc_boxtit">视频素材</div>
			<div class="suc_num">${videonum}</div>
		</a> <a href="<%=basePath%>ea/scm/ea_findCateManageIndex.jspa?type=noaudio"  class="suc_wrap">
			<div class="suc_box suc_audio"></div>
			<div>音频素材</div>
			<div class="suc_num">${audionum}</div>
		</a>
	</div>
	<!--  header end  -->
	<script>
		window.onload = window.onresize = function() {
			//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
			//获取窗口的尺寸
			var clientWidth = document.documentElement.clientWidth;
			//通过屏幕宽度去设置不同的后台根字体的大小
			document.getElementsByTagName('html')[0].style.fontSize = clientWidth
					/ 640 * 40 + 'px';
		}
	</script>
</body>
</html>