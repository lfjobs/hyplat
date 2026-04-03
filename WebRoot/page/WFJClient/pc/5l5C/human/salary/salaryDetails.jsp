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
    <title>工资详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryDetails.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/salary/salaryDetails.js"></script>
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
                    工资详情
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
                        <label id="settlementMonthLabel">月份</label>
                        <span id="settlementMonth"></span>
                    </li>
                    <li>
                        <label>工资级别</label>
                        <span id="salaryLevelNum"></span>
                    </li>
<%--                    <li>--%>
<%--                        <label>基本工资</label>--%>
<%--                        <span id="baseSalary"></span>--%>
<%--                    </li>--%>
                    <li>
                        <label>保障工资</label>
                        <span id="guaranteeSalary"></span>
                    </li>
                    <li>
                        <label>非工资福利津贴</label>
                        <span id="welfareSalary"></span>
                    </li>
                </ul>
                <ul>
                    <li>
                        <label>打卡天数(不含周末)</label>
                        <span id="signInDayTotalCount"></span>
                    </li>
                    <li>
                        <label>迟到早退天数</label>
                        <span id="lateLeaveEarlyDayCount"></span>
                    </li>
                    <li>
                        <label>出勤天数(不含周末)</label>
                        <span id="signInDayCount"></span>
                    </li>
                    <li>
                        <label>出勤工资</label>
                        <span id="signInSalary"></span>
                    </li>
                    <li>
                        <label>请假天数(不含事假)</label>
                        <span id="leaveAddDayCount"></span>
                    </li>
                    <li>
                        <label>请假工资(不含事假)</label>
                        <span id="leaveAddMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>正常日加班天数</label>
                        <span id="eightHourOvertimeDayCount"></span>
                    </li>
                    <li>
                        <label>正常日加班工资</label>
                        <span id="eightHourOvertimeMoney"></span>
                    </li>
                    <li>
                        <label>周末日加班天数</label>
                        <span id="weekendOvertimeDayCount"></span>
                    </li>
                    <li>
                        <label>周末日加班工资</label>
                        <span id="weekendOvertimeMoney"></span>
                    </li>
                    <li>
                        <label>节假日加班天数</label>
                        <span id="holidayOvertimeDayCount"></span>
                    </li>
                    <li>
                        <label>节假日加班工资</label>
                        <span id="holidayOvertimeMoney"></span>
                    </li>
                </ul>
                <ul class="leave">
                    <li>
                        <label>社保缴纳基数</label>
                        <span id="socialSecurityLevel"></span>
                    </li>
                    <li>
                        <label>养老扣费</label>
                        <span id="elderlyCareDiscountMoney"></span>
                    </li>
                    <li>
                        <label>医保扣费</label>
                        <span id="medicalDiscountMoney"></span>
                    </li>
                    <li>
                        <label>失业扣费</label>
                        <span id="unemploymentDiscountMoney"></span>
                    </li>
                </ul>
                <ul>
                    <li>
                        <label>合计应发</label>
                        <span id="realSalary"></span>
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
