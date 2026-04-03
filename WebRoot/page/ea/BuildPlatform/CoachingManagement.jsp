<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/17 0017
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/zy_sys.css">
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <title>教练管理系统</title>
</head>
<script type="text/javascript">
    var staffId='${personalInfo[0][2]}';
    var companyId='${personalInfo[0][3]}';
</script>
<body>
<header class="com_head">
    <a onclick="javascript: window.history.go(-1);return false;"
       class="back"></a>
    <h1>5L5C资源系统</h1>
</header>
<div class="wrap_page">
    <div class="zy_home">
        <a href="javascript:;" class="head_img">
            <s:if test="#request.personalInfo[0][0]==null||request.personalInfo[0][0]==''">
                <img src="<%=basePath %>/images/BuildPlatform/touxiang.png" alt="">
            </s:if>
            <s:else>
                <img src="<%=basePath %>${personalInfo[0][0]}" alt="">
            </s:else>
        </a>
        <div class="head_name">${personalInfo[0][1] }</div>
        <div class="home_list">
            <a href="<%=basePath %>/ea/elkcInform/ea_coachNews.jspa">消息管理<b class="info_num"></b></a>
            <a href="<%=basePath %>/driving/elkc/ea_coachInfo.jspa?staffId=${personalInfo[0][2]}">个人信息</a>
            <a href="<%=basePath%>ea/coachreserv/ea_courseManagement.jspa?companyId=${personalInfo[0][3]}&staffId=${personalInfo[0][2]}">预约管理</a>
            <a href="<%=basePath%>/mobile/office/mobileoffice_learningProcess.jspa?staffId=${personalInfo[0][2]}">学员管理</a>
            <a href="###">评价信息</a>
            <a href="###">历史培训</a>
        </div>
    </div>
</div>
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