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
<title>视频素材</title>

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
        <a href="javascript:history.go(-1)" class="back"></a>
        <h1>视频素材</h1>
    </header>
    <div class="wrap_page clearfix">
        <a href="<%=basePath%>ea/scm/ea_newOrEditorGroup.jspa?type=novideo"  class="suc_wrap">
            <div class="suc_addgroup">
                <i class="add_group"></i><br>
                <span>新建分组</span>
            </div>
        </a>
         <c:forEach items="${list}" var="item">
        <a href="<%=basePath%>ea/scm/ea_findFilelist.jspa?materialContent.fileType=01&materialGroup.groupname=${item.groupname}&materialGroup.mgId=${item.mgId}"  class="suc_wrap">
            <div class="suc_box"><img src="<%=basePath%>${item.groupcover}" alt="">
            <i class="video_ico"></i></div>
            <div class="suc_boxtit">${item.groupname}</div>
            <div class="suc_num">${item.filenum}个</div>
        </a>
        </c:forEach>
    </div>
    <!--  header end  -->
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