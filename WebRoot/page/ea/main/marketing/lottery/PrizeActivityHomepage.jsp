<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_manger.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>活动管理首页</title>
</head>
<body>
    <header class="com_head">
<%--        <a href="<%=basePath%>mobile/office/mobileoffice_fastApplication.jspa?" class="back"></a>--%>
        <a href="<%=basePath%>ea/lottery/ea_prizeActivityHomepageBack.jspa?" class="back"></a>
        <h1>活动管理</h1>
    </header>
    <div class="wrap_page">
       <div class="act_navwrap clearfix">
           <div class="act_nav">
               <a href="<%=basePath%>ea/lottery/ea_prizeActivityList.jspa?flag=0&model.companyId=<s:property value='#request.companyId'/>" class="act_nav_box">
                   <i class="act_nav01"></i>
                   <span>抽奖</span>
               </a>
           </div>
           <div class="act_nav">
               <a href="<%=basePath%>ea/lottery/ea_prizeActivityList.jspa?flag=1&model.companyId=<s:property value='#request.companyId'/> " class="act_nav_box">
                   <i class="act_nav02"></i>
                   <span>签到</span>
               </a>
           </div>
<%--           <div class="act_nav">--%>
<%--               <a href="###" class="act_nav_box">--%>
<%--                   <i class="act_nav03"></i>--%>
<%--                   <span>优惠券</span>--%>
<%--               </a>--%>
<%--           </div>--%>
           <div class="act_nav">
               <a href="<%=basePath%>ea/lottery/ea_prizeActivityList.jspa?flag=2&model.companyId=<s:property value='#request.companyId'/>" class="act_nav_box">
                   <i class="act_nav04"></i>
                   <span>会议活动</span>
               </a>
           </div>
       </div>
    </div>
</body>
</html>