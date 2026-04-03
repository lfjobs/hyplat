<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>考勤详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/signInCheckOnDetails.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/signInCheckOnDetails.js"></script>
    <style type="text/css">
        .clearfix:after {
            content: "\00A0";
            display: block;
            visibility: hidden;
            width: 0;
            height: 0;
            clear: both;
            font-size: 0;
            line-height: 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    考勤详情
                </li>
            </ul>
        </header>
        <div class="checkOn-top">
            <div class="con-one">
                <ul>
                    <li>
                        <label>姓名</label>
                        <span id="staffName"></span>
                    </li>
                    <li>
                        <label>考勤时间</label>
                        <span id="checkOnTime"></span>
                    </li>
                    <li>
                        <label>工资级别</label>
                        <span id="salaryLevelNum"></span>
                    </li>
<%--                    <li>--%>
<%--                        <label>保障工资</label>--%>
<%--                        <span id="guaranteeSalary"></span>--%>
<%--                    </li>--%>
<%--                    <li>--%>
<%--                        <label>福利工资</label>--%>
<%--                        <span id="welfareSalary"></span>--%>
<%--                    </li>--%>
                </ul>
                <ul id="late">
                    <li>
                        <label>迟到次数</label>
                        <span id="lateCount"></span>
                    </li>
                    <li>
                        <label>迟到时长</label>
                        <span id="lateMinutes"></span>
                    </li>
                    <li>
                        <label>迟到应扣</label>
                        <span id="lateDiscountMoney"></span>
                    </li>
                </ul>
                <ul id="leaveEarly">
                    <li>
                        <label>早退次数</label>
                        <span id="leaveEarlyCount"></span>
                    </li>
                    <li>
                        <label>早退时长</label>
                        <span id="leaveEarlyMinutes"></span>
                    </li>
                    <li>
                        <label>早退应扣</label>
                        <span id="leaveEarlyDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>事假天数</label>
                        <span id="leaveAbsenceDayCount"></span>
                    </li>
                    <li>
                        <label>事假时长</label>
                        <span id="leaveAbsenceHours"></span>
                    </li>
                    <li>
                        <label>事假应扣</label>
                        <span id="leaveAbsenceDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>病假天数</label>
                        <span id="leaveSickDayCount"></span>
                    </li>
                    <li>
                        <label>病假时长</label>
                        <span id="leaveSickHours"></span>
                    </li>
                    <li>
                        <label>病假应扣</label>
                        <span id="leaveSickDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>婚假天数</label>
                        <span id="leaveMarriageDayCount"></span>
                    </li>
                    <li>
                        <label>婚假时长</label>
                        <span id="leaveMarriageHours"></span>
                    </li>
                    <li>
                        <label>婚假应扣</label>
                        <span id="leaveMarriageDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>年假天数</label>
                        <span id="leaveAnnualDayCount"></span>
                    </li>
                    <li>
                        <label>年假时长</label>
                        <span id="leaveAnnualHours"></span>
                    </li>
                    <li>
                        <label>年假应扣</label>
                        <span id="leaveAnnualDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>产假天数</label>
                        <span id="leaveMaternityDayCount"></span>
                    </li>
                    <li>
                        <label>产假时长</label>
                        <span id="leaveMaternityHours"></span>
                    </li>
                    <li>
                        <label>产假应扣</label>
                        <span id="leaveMaternityDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>丧假天数</label>
                        <span id="leaveFuneralDayCount"></span>
                    </li>
                    <li>
                        <label>丧假时长</label>
                        <span id="leaveFuneralHours"></span>
                    </li>
                    <li>
                        <label>丧假应扣</label>
                        <span id="leaveFuneralDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>工伤假天数</label>
                        <span id="leaveWorkInjuryDayCount"></span>
                    </li>
                    <li>
                        <label>工伤假时长</label>
                        <span id="leaveWorkInjuryHours"></span>
                    </li>
                    <li>
                        <label>工伤假应扣</label>
                        <span id="leaveWorkInjuryDiscountMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>轮岗休息天数</label>
                        <span id="leaveRotationRestDayCount"></span>
                    </li>
                    <li>
                        <label>轮岗休息时长</label>
                        <span id="leaveRotationRestHours"></span>
                    </li>
                    <li>
                        <label>轮岗休息应扣</label>
                        <span id="leaveRotationRestDiscountMoney"></span>
                    </li>
                </ul>
                <ul id="absentee">
                    <li>
                        <label>旷工天数</label>
                        <span id="absenteeDayCount"></span>
                    </li>
                    <li>
                        <label>旷工时长</label>
                        <span id="absenteeHours"></span>
                    </li>
                    <li>
                        <label>旷工应扣</label>
                        <span id="absenteeDiscountMoney"></span>
                    </li>
                </ul>
                <ul>
                    <li>
                        <label>合计应扣</label>
                        <span id="totalDiscountMoney"></span>
                    </li>
                </ul>
                <ul id="outwork">
                    <li>
                        <label>外勤天数</label>
                        <span id="outworkDayCount"></span>
                    </li>
                    <li>
                        <label>外勤时长</label>
                        <span id="outworkHours"></span>
                    </li>
                </ul>
                <ul id="weekendOvertime">
                    <li>
                        <label>周末日加班天数</label>
                        <span id="weekendOverTimeDayCount"></span>
                    </li>
                    <li>
                        <label>周末日加班时长</label>
                        <span id="weekendOverTimeHours"></span>
                    </li>
                    <li>
                        <label>周末日加班工资</label>
                        <span id="weekendOverTimeMoney"></span>
                    </li>
                </ul>
                <ul id="eightHourOvertime">
                    <li>
                        <label>正常日加班天数</label>
                        <span id="eightHourOverTimeDayCount"></span>
                    </li>
                    <li>
                        <label>正常日加班时长</label>
                        <span id="eightHourOverTimeHours"></span>
                    </li>
                    <li>
                        <label>正常日加班工资</label>
                        <span id="eightHourOverTimeMoney"></span>
                    </li>
                </ul>
                <ul id="holidayOvertime">
                    <li>
                        <label>节假日加班天数</label>
                        <span id="holidayOverTimeDayCount"></span>
                    </li>
                    <li>
                        <label>节假日加班时长</label>
                        <span id="holidayOverTimeHours"></span>
                    </li>
                    <li>
                        <label>节假日加班工资</label>
                        <span id="holidayOverTimeMoney"></span>
                    </li>
                </ul>
                <ul>
                    <li>
                        <label>加班合计</label>
                        <span id="totalOvertimeMoney"></span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>


</body>
</html>
