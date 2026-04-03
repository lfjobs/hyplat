<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/qd.css">
    <title>收单服务</title>
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var t;
        var pagenumber=0;
        var pagecount=0;
        var search="";
        var temp = "";
        var phone = "";
        var title = "";
    </script>
</head>
<body style="background: #f4f4f4;">
<header class="com_head">
    <a href="<%=basePath %>ea/vipcenter/ea_vipDemand.jspa?" class="back"></a>
    <h1>收单服务</h1>
</header>
<div class="wrap_page" style="background: #f4f4f4;">
    <div style="width: 100%;height: 2.5rem;background: #f4f4f4;position: fixed;z-index: 999;">
        <div class="info_search_wrap">
            <input type="text" class="info_search" value="">
            <span class="search_place"><i></i>查询</span>
        </div>
    </div>
    <%--<div class="qd_hint">恭喜您，抢单成功!</div>--%>
    <div class="qd_con">
    </div>
</div>
<div class="nest_page">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>联营商城会员抢单</span>
    </div>
    <div class="nest_bd"></div>
</div>
<script>
    //搜索框触发、失去焦点
    $(".info_search").focus(function(){
        $(".search_place").hide();
    }).blur(function(){
        if($(this).val()==""){
            $(".search_place").show()
        }
    });
</script>
<script src="<%=basePath%>/js/ea/marketing/edmandServe/receiveServiceList.js"></script>
</body>
</html>
