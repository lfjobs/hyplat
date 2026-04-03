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
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/elkc/traineeInformation.js"></script>
	<title>学员任务消息</title>





	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var staffID = "${caccount.staffID}";
        var pageNumber = 0;
        var pageCount;

	</script>

</head>

<body>
<header class="com_head">
	<!--<a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>-->
	<h1>任务消息</h1>
</header>
<div class="wrap_page">
	<div class="task_tab_wrap clearfix">
		<div class="task_tab tack_cur new_tab">
			<a href="javascript:void(0)">我的消息</a>
		</div>
	</div>
	<div class="task_search_wrap">
		<input type="text" class="task_search">
		<div class="search_bg">搜索</div>
	</div>

	<div class="task_con">
		<ul class="task_list">
            <!--js拼接-->
		</ul>
	</div>
</div>
<script>
    $(".task_search").focus(function() {
        $(this).next().hide();
    }).blur(function() {
        if ($(this).val() == '') {
            $(this).next().show();
        }
    })

</script>
</body>

</html>
