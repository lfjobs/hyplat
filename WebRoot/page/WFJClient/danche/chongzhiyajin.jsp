<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/danche/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/danche/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/danche/ck.css">

    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>押金充值</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:;" class="back"></a>
    <h1>押金充值</h1>
</header>
<script type="text/javascript">
    var basePath="<%=basePath%>";
</script>
<div class="wrap_page">
    <div class="topup_wrap">
        <p class="topup_tit1">充值金额（元）</p>
        <p class="pledge">￥199</p>
        <p class="topup_tit2">押金随时退</p>
    </div>
    <div class="payway_wrap">
        <label class="payway_box pay_zfb">
            <input type="radio" name="payways" class="payway_radio">
            <i></i>
        </label>
        <label class="payway_box pay_yl">
            <input type="radio" name="payways" class="payway_radio">
            <i></i>
        </label>
        <label class="payway_box pay_qh">
            <input type="radio" name="payways" class="payway_radio">
            <i></i>
        </label>
    </div>
    <a href="###" class="topup_btn">立即充值</a>
</div>
</body>
</html>
