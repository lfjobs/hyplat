<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
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
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/AppParkong.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>注册</title>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>注册</h1>
</header>
<div class="wrap_page">
    <div class="reg_fix">
        <img class="reg_logo" src="<%=basePath%>images/ea/office/wfj_logo.png" alt="">
        <div class="reg_t1">数字地球</div>
        <div class="reg_t2">共享资源共享金</div>
        <a href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa?sccid=${sccId}&identify=cltjzc" class="reg_btn">注 册</a>
    </div>
    <img src="<%=basePath%>images/ea/office/register_00.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_01.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_02.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_03.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_04.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_05.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_06.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_07.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_08.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_09.jpg" class="register_img" alt="">
    <img src="<%=basePath%>images/ea/office/register_10.jpg" class="register_img" alt="">
</div>

</body>

</html>
