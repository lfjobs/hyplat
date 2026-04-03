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
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/awardRotate.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/lottery.js"></script>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var activityId = '${model.activityId}';
        var companyId = '${model.companyId}';
        var ccompanyId = '${ccompanyId}';
        var prizeLevelList = '<%= request.getAttribute("prizeLevelList")%>';
        var selectType = '${selectType}';
        var bonusPoints = '<%= request.getAttribute("bonusPoints")%>';
    </script>

    <title>抽奖</title>
</head>

<body>
    <div class="com_head">
        <a href="javascript:history.go(-1)" class="back"></a>
        <h1>抽奖活动</h1>
        <a href="javascript:;" class="head_R share"></a>
    </div>
    <div class="wrap_page" style="background:#ffe532;overflow-x:hidden;">
        <div id="activityName"><%= request.getAttribute("activityName")%></div>
        <img src="<%=basePath%>images/ea/lottery/2.png" id="sorry-img" style="display:none;" />
        <div class="banner">
            <div class="turnplate">
                <canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
                <img class="pointer" src="<%=basePath%>images/ea/lottery/turnplate-pointer.png" />
            </div>
        </div>
        <div class="shadow"></div>
        <div  id="pool" style="display: none;">
            <ul>
                <s:iterator value="#request.list" var="entity">
                    <li><s:property value="#entity.ppName"/></li>
                    <li>不要灰心，还有机会哦！</li>
                </s:iterator>
            </ul>
        </div>
        <div class="notice_wrap">
            <i class="notice_L"></i>
            <i class="notice_R"></i>
            <a href="<%=basePath%>ea/lottery/ea_lotteryRecords.jspa?model.activityId=${model.activityId}&ccompanyId=${ccompanyId}&type=allCustomer&selectType=${selectType}" class="record_btn">
                <div class="notice_list_wrap">
                        <ul class="notice_list">
                            <s:iterator value="#request.records" var="entity">
                            <li>
                                <span>恭喜<s:property value="#entity[0]"/></span>
                                <span>抽中</span>
                                <span><s:property value="#entity[1]"/></span>
                            </li>
                            </s:iterator>
                        </ul>
                </div>
            </a>
        </div>
        <div class="award_wrap">
            <a href="<%=basePath%>ea/lottery/ea_goPrizeList.jspa?model.activityId=${model.activityId}&ccompanyId=${ccompanyId}&selectType=${selectType}" class="record_btn">
                <s:iterator value="#request.list" var="entity">
                    <span><s:property value="#entity.prizeLvl"/>: <s:property value="#entity.ppName"/></span>
                </s:iterator>
            </a>
        </div>
        <img src="<%=basePath%>images/ea/lottery/draw_02.jpg" class="bg_img" alt="">
        <p class="draw_rule_wrap">
            1.抽奖时间：<s:property value="#request.startDate"/> -- <s:property value="#request.endDate"/> <br>
            2.抽奖次数：活动期间，每位用户可免费抽奖1次；1次免费机会结束，可消耗积分参与，${bonusPoints}个积分/次。<br>
            3.中奖实物产品需要在活动期间提交订单，超过活动时间未领取则视做放弃奖品。<br>
            4.中奖实物产品需要精确您的收货地址，请您在下单之前确认订单中的收货地址。<br>
            5.活动最终解释权归微分金数字地球所有
        </p>
        <img src="<%=basePath%>images/ea/lottery/draw_03.jpg" class="bg_img" alt="">
        <p class="hint_wrap">
            如有疑问欢迎致电询问<br> 微分金客服：010-64167113
        </p>
        <img src="<%=basePath%>images/ea/lottery/draw_04.jpg" class="bg_img" alt="">
        <div class="draw_btn_wrap clearfix">
            <div class="draw_btn_box">
                <a href="<%=basePath%>ea/bonuspoints/ea_getSign.jspa?ccompanyId=${ccompanyId}" class="sign_btn"><i></i><span>积分签到</span></a>
            </div>
            <div class="draw_btn_box">
                <a href="<%=basePath%>ea/lottery/ea_lotteryRecords.jspa?model.activityId=${model.activityId}&ccompanyId=${ccompanyId}&selectType=${selectType}" class="record_btn"><i></i><span>获奖记录</span></a>
            </div>
        </div>
    </div>
    <div class="overlay">
        <div class="popup_box">
            <a href="javascript:;" class="close_btn"></a>
            <div></div>
            <div class="popup_fd">
                <span class="popup_text"></span>
                <input type="hidden" value="" id="recordId"/>
                <input type="hidden" value="" id="ppId">
                <input type="hidden" value="" id="free_count">
                <input type="hidden" value="" id="staffId">
                <a href="###" class="sure_btn"></a>
            </div>
        </div>
    </div>
</body>

</html>
