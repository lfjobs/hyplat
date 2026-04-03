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
    <title>签到考勤</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/signInCheckOn.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/signInCheckOn.js"></script>
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
                <li style="width: 100px;">
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li style="text-align: left;padding-left: 2.9rem;">
                    签到考勤
                </li>
            </ul>
        </header>
        <div class="checkOn-top">
            <div class="txt_2 back_color">
                <p class="txtP">
                    <span id="today" class="txtSpan">今日</span>
                    <span id="yesterday" class="txtSpan">昨日</span>
                    <span id="currentWeek" class="txtSpan">本周</span>
                    <span id="beforeWeek" class="txtSpan">上周</span>
                    <span id="currentMonth" class="txtSpan">本月</span>
                    <span id="beforeMonth" class="txtSpan">上月</span>
                    <span id="currentQuarter" class="txtSpan">本季</span>
                    <span id="beforeQuarter" class="txtSpan">上季</span>
                    <span id="currentYear" class="txtSpan">本年</span>
                    <span id="beforeYear" class="txtSpan">上年</span>
                </p>
            </div>
            <div class="txt back_color">
                <p class="txtP">
                    <span class="txtSpan" style="width: 5.2rem;">姓名</span>
                    <span class="txtSpan" style="width: 4rem;">迟到</span>
                    <span class="txtSpan" style="width: 3.9rem;">早退</span>
                    <span class="txtSpan" style="width: 3.9rem;">事假</span>
                    <span class="txtSpan" style="width: 3rem;">病假</span>
                    <span class="txtSpan" style="width: 4rem;"></span>
                    <span class="txtSpan" style="width: 4rem;">婚假</span>
                    <span class="txtSpan" style="width: 4rem;">年假</span>
                    <span class="txtSpan" style="width: 4rem;">产假</span>
                    <span class="txtSpan" style="width: 4rem;">丧假</span>
                    <span class="txtSpan" style="width: 5rem;">工伤假</span>
                    <span class="txtSpan" style="width: 6rem;">轮岗休息</span>
                    <span class="txtSpan" style="width: 4rem;">旷工</span>
                    <span class="txtSpan" style="width: 3.7rem;">外勤</span>
                    <span class="txtSpan" style="width: 5rem;">周末日加班</span>
                    <span class="txtSpan" style="width: 6rem;">正常日加班</span>
                    <span class="txtSpan" style="width: 6rem;">节假日加班</span>
                </p>
            </div>
        </div>

        <div class="content_hidden">
            <div class="content intro">
                <div class="checkOn">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>

        <a href="javascript:" class="return">
            <img src="<%=basePath %>images/WFJClient/DigitalMall/return.png" alt="">
        </a>

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
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".checkOn").addClass("pc-bottom");
    }
    var employee = "CURRENT";//查询员工范围：CURRENT：当前登录者，ALL：全部员工
    var checkOnTimeType = "CURRENT_MONTH";
</script>


</body>
</html>
