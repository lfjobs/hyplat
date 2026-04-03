<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");

%>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>库房管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/kufang/kufang.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/kufang/swiper.min.css"/>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/javascript.js" type="application/javascript"></script>
</head>
<script>
    var basePath = "<%=basePath%>";
</script>
<body class="">
<ul class="header header2">
    <li><img src="<%=basePath %>/images/parkkingpay/ico-left.png" alt=""></li>
    <li>库房管理</li>
</ul>
<div class="kf_con">
    <ul class="ck3">
        <li>资料库</li>
        <li>固定资产</li>
        <li class="active" onclick="aquyu()">销售库</li>
        <ul class="kf_con-2">
            <li>第一销售库</li>
            <li>第一销售库</li>
            <ul class="kf_con-3">
                <li >A区域</li>
                <li>B区域</li>
                <li>C区域</li>
            </ul>
        </ul>
        <li>半成品库</li>
        <li>退货库</li>
        <li>物流库</li>
        <li>生产库</li>
        <li>检验库</li>
        <li>半成品库</li>
        <li>退货库</li>
        <li>物流库</li>
        <li>生产库</li>
        <li>检验库</li>
    </ul>
</div>

</body>
<script>
    //盘库仓库的跳转方法
    function aquyu() {
        window.location.href = basePath + "ea/cashinv/ea_aquyu.jspa";
    }
</script>
</html>

