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
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/rl.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>提交成功</title>
</head>
<body>

<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>提交成功</h1>
</header>

<div class="wrap_page">
    <div class="success_box">
        <img src="<%=basePath%>images/BuildPlatform/success.png" alt="">
        <div>提交成功</div>
    </div>
    <div class="btn_wrap clearfix">
        <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa">返回首页</a>
        <a href="###">继续添加</a>
    </div>
</div>
</body>
</html>
