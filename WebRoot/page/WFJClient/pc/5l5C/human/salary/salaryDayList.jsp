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
    <title>工资明细</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryList.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/salary/salaryDayList.js"></script>
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
                    工资明细
                </li>
            </ul>
        </header>
        <div class="con-one">
            <div>
                <ul>
                    <li>
                        <label>姓名</label>
                        <span id="staffName"></span>
                    </li>
                    <li>
                        <label id="settlementMonthLabel">月份</label>
                        <span id="settlementMonth"></span>
                    </li>
                </ul>
            </div>
            <div class="txt back_color">
                <p class="txtP">
                    <span class="txtSpan" style="width: 6rem;">日期</span>
                    <span class="txtSpan" style="width: 6rem;">工资级别</span>
                    <span class="txtSpan" style="width: 4.5rem;">出勤时长</span>
                    <span class="txtSpan" style="width: 5rem;">合计应发</span>
                    <span class="txtSpan" style="width: 3rem;"></span>
                    <span class="txtSpan" style="width: 5rem;">请假类别</span>
                    <span class="txtSpan" style="width: 5rem;">请假时长</span>
                    <span class="txtSpan" style="width: 5rem;">保障工资</span>
                    <span class="txtSpan" style="width: 8rem;">非工资福利津贴</span>
<%--                    <span class="txtSpan" style="width: 5rem;">出勤工资</span>--%>
                    <span class="txtSpan" style="width: 6rem;">加班类别</span>
                    <span class="txtSpan" style="width: 5rem;">加班时长</span>
                    <span class="txtSpan" style="width: 5rem;">加班工资</span>
                </p>
            </div>
        </div>

        <div class="content_hidden">
            <div class="content intro">
                <div class="salary">
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
        $(".salary").addClass("pc-bottom");
    }
</script>


</body>
</html>
