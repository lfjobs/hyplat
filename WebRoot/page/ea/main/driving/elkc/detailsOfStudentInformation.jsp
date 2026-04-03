<!DOCTYPE HTML>
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
	<script type="text/javascript" src="<%=basePath%>js/ea/elkc/setHtmlFont.js"></script>
	<link rel="stylesheet" href="<%=basePath%>css/ea/elkc/base.css">
	<link rel="stylesheet" href="<%=basePath%>css/ea/elkc/task_message.css">
	<script src="<%=basePath%>js/jquery.min.js"></script>
	<title>消息详情</title>
</head>

<body>
<header class="com_head">
	<a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
	<h1>消息详情</h1>
</header>
<div class="wrap_page">
	<div class="mes_wrap">
		<div class="mes_part clearfix">
			<div class="mes_L">主题</div>
			<div class="mes_R mes_tit_box">
				<input type="text" class="theme_inp" value="${map.edsn.theme}" readonly>
			</div>
		</div>
		<div class="mes_part clearfix">
			<div class="mes_L">正文</div>
			<div class="mes_R mes_con_box">
				<textarea class="mes_main_inp" readonly>${map.edsn.content}</textarea>
			</div>
		</div>

        <c:if test="${map.list!=null}">
            <div class="mes_part clearfix">
                <div class="mes_L">图片</div>
                <div class="mes_R mes_img_box clearfix">
                    <c:forEach items="${map.list}" var="l">
                        <img src="<%=basePath%>${l.picpath}" alt="">
                    </c:forEach>
                </div>
            </div>
      </c:if>

	</div>
	<div class="receive_wrap">
		<div class="receive_tit">
			发布人
		</div>
		<div class="receive clearfix">
			<div class="receive_box">
				<img src="<%=basePath%>${map.staff.headimage}" alt="" onerror="this.src='<%=basePath%>images/ea/driving/elkc/head.png'">
				<span>${map.staff.staffName}</span>
			</div>
		</div>
	</div>
</div>

</body>

</html>