<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
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
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_draw.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>奖品列表</title>
</head>

<body>
    <div class="com_head">
        <a href="<%=basePath%>ea/lottery/ea_goLottery.jspa?model.activityId=${model.activityId}&ccompanyId=${ccompanyId}&selectType=${selectType}" class="back"></a>
        <h1>奖品列表</h1>
    </div>
    <div class="wrap_page" style="background:#ffe532;padding:2.16rem 0 1rem;margin-top:0;">
        <div id="activityName"><%= request.getAttribute("activityName")%></div>
<%--        <img src="<%=basePath%>images/ea/lottery/draw_05.jpg" class="bg_img record_top" alt="">--%>
        <div class="award_wrap">
            <s:iterator value="#request.list" var="entity">
                <span><s:property value="#entity.prizeLvl"/>: <s:property value="#entity.ppName"/></span>
            </s:iterator>
        </div>
        <img src="<%=basePath%>images/ea/lottery/draw_02.jpg" class="bg_img" alt="">
        <p class="draw_rule_wrap">
            1.抽奖时间：<s:property value="#request.startDate"/> -- <s:property value="#request.endDate"/> <br>
            2.抽奖次数：活动期间，每位用户可免费抽奖1次；1次免费机会结束，可消耗积分参与，<s:property value="#request.bonusPoints"/>个积分/次。<br>
            3.中奖实物产品需要在活动期间提交订单，超过活动时间未领取则视做放弃奖品。<br>
            4.中奖实物产品需要精确您的收货地址，请您在下单之前确认订单中的收货地址。<br>
            5.活动最终解释权归微分金数字地球所有
        </p>
        <img src="<%=basePath%>images/ea/lottery/draw_03.jpg" class="bg_img" alt="">
        <p class="hint_wrap">
            如有疑问欢迎致电询问<br> 微分金客服：010-64167113
        </p>
    </div>
</body>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    $(function () {
        $("#activityName").css("background-image", "url('" + basePath + "images/ea/lottery/draw_05.jpg')");
    });
</script>

</html>
