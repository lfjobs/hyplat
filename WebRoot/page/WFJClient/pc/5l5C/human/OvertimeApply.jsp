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
    <title>加班申请</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/overtimeApply.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <!--选择日期时间插件 开始-->
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_002.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_004.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/ea/lottery/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_003.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_005.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_003.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_004.css" rel="stylesheet" type="text/css">
    <!--选择日期时间插件 结束-->
    <!--选择插件 开始-->
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/mobiscroll-select.min.css">
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll-select.min.js"></script>
    <!--选择插件 结束-->

    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/overtimeApply.js"></script>
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
                <li style="width: 100px;padding-left: 1.7rem;">
                    加班申请
                </li>
            </ul>
        </header>
        <div class="top">
            <div class="con-one">
                <ul>
                    <li>
                        <label>加班方式</label>
                        <span id="day">按整天</span><span id="hour">按小时</span>
                    </li>
                    <li>
                        <label>开始时间 <span style="color: red;">*</span></label>
                        <input type="text" id="startTime" readonly placeholder="请选择开始时间">
                    </li>
                    <li>
                        <label>结束时间 <span style="color: red;">*</span></label>
                        <input type="text" id="endTime" readonly placeholder="请选择结束时间">
                    </li>
                    <li>
                        <label>加班时长</label>
                        <span id="overtimeHour"></span> 小时
                    </li>
                    <li>
                        <label>加班事由</label>
                        <input id="overtimeReason" type="text" placeholder="请输入加班事由">
                    </li>
                    <li>
                        <label>状态</label>
                        <span id="status">未审核</span>
                    </li>
                    <li>
                        <label>审核人 <span style="color: red;">*</span></label>
                        <select id="reviewerId">
                        </select>
                    </li>
                    <li id="reviewTimeLi">
                        <label>审核时间</label>
                        <span id="reviewTime"></span>
                    </li>
                </ul>
            </div>
            <div class="con-two">
                <ul class="top-ul2">
                    <li class="top-right">
                        <a class="button-a">
                            <span class="button-span">提交</span>
                        </a>
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
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".checkOn").addClass("pc-bottom");
    }
</script>


</body>
</html>
