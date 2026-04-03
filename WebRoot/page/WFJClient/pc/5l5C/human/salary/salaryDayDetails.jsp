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
    <title>工资明细详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryDetails.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/salary/salaryDayDetails.js"></script>
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
                        <label>日期</label>
                        <span id="date"></span>
                    </li>
                    <li>
                        <label>工资级别</label>
                        <span id="salaryLevelNum"></span>
                    </li>
                </ul>
                <ul>
                    <li>
                        <label>出勤时长</label>
                        <span id="signInDayCount"></span>
                    </li>
<%--                    <li>--%>
<%--                        <label>迟到早退合计天数</label>--%>
<%--                        <span id="lateLeaveEarlyDayCount"></span>--%>
<%--                    </li>--%>
                    <li>
                        <label>请假类别</label>
                        <span id="leaveType"></span>
                    </li>
                    <li>
                        <label>请假时长</label>
                        <span id="leaveDayCount"></span>
                    </li>
                    <li>
                        <label>保障工资</label>
                        <span id="realGuaranteeSalary"></span>
                    </li>
                    <li>
                        <label>非工资福利津贴</label>
                        <span id="realWelfareSalary"></span>
                    </li>
<%--                    <li>--%>
<%--                        <label>出勤工资</label>--%>
<%--                        <span id="signInSalary"></span>--%>
<%--                    </li>--%>
                </ul>
                <ul class="leave">
                    <li>
                        <label>加班类别</label>
                        <span id="overtimeType"></span>
                    </li>
                    <li>
                        <label>加班时长</label>
                        <span id="overtimeDayCount"></span>
                    </li>
                    <li>
                        <label>加班工资</label>
                        <span id="overtimeMoney"></span>
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
