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

<html class="nest">
<head>
<title>会员二维码分享-列表</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/qr_share.css">



</head>
<body>
	<div class="art_con"></div>
</body>
<script type="text/javascript">
	window.onload=function(){

		$(".art_con").find(".art_box").not(".art_music").not(".art_cate").find(".set_up").show().end().first().find(".set_up").hide();
		$(".art_con").find(".art_box").find(".set_down").show().end().last().find(".set_down").hide();
	};

</script>
</html>