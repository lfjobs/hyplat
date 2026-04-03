<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/10 0010
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <script type="text/javascript" src="<%=path%>/js/ea/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/coach.css">
    <script src="<%=path%>/js/ea/elkc/jquery.min.js"></script>
    <title>课程管理</title>
</head>

<body>
<header class="com_head">
    <a onclick="javascript: window.history.go(-1);return false;" class="back"></a>
    <h1>课程管理</h1>
</header>
<div class="wrap_page">
    <div class="jl_info clearfix">
        <img src="<%=basePath%>${listof[0][9]==null||listof[0][9]==''?'images/contacts/Network/defaultbig.png':listof[0][9]}" class="jl_info_L" alt="">
        <ul class="jl_info_R">
            <li>${listof[0][0]}</li>
            <li>性别：${listof[0][10]==1?'男':'女'}</li>
            <li>准学车型：${listof[0][8]}</li>
            <li>所属驾校：${listof[0][7]}</li>
            <li>联系电话：${listof[0][1]}</li>
        </ul>
    </div>
    <ul class="order_state">
        <li>预约车型：${listof[0][8]}</li>
        <c:if test="${listof[0][6]==1}"><li>预约科目：科目一</li></c:if>
        <c:if test="${listof[0][6]==2}"><li>预约科目：科目二</li></c:if>
        <c:if test="${listof[0][6]==3}"><li>预约科目：科目三</li></c:if>
        <c:if test="${listof[0][6]==4}"><li>预约科目：科目四</li></c:if>
        <li>预约时间：<fmt:formatDate value="${listof[0][3]}" pattern="MM月dd日 HH:mm " />-<fmt:formatDate value="${listof[0][4]}" pattern="MM月dd日 HH:mm " /></li>
        <li>预约教练：${listof[0][2]}</li>
        <c:if test="${listof[0][5]==1}"><li>课程状态：已预约</li></c:if>
        <c:if test="${listof[0][5]==3}"><li>课程状态：已完成</li></c:if>

    </ul>
</div>
</body>

</html>
