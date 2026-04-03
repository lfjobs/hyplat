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
    <title>签到设置保存</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/signInSetupSave.css">
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


    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/office/signInSetupSave.js"></script>
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
                    签到设置-保存
                </li>
            </ul>
        </header>
        <div class="signInSetup-top">
            <div class="con-one">
                <ul class="signInSetup-top-ul">
                    <li>
                        <label>签到类别</label>
                        <select id="signInTypeId"></select>
                    </li>
                    <li>
                        <label>签到时间</label>
<%--                        <input type="date" id="startDate" min="2020-01-01" max="2100-12-31">--%>
<%--                        <input type="time" id="startTime">--%>
                        <input type="text" id="startTime" readonly placeholder="请选择签到时间">
                    </li>
                    <li>
                        <label>签退时间</label>
<%--                        <input type="date" id="endDate" min="2020-01-01" max="2100-12-31">--%>
<%--                        <input type="time" id="endTime">--%>
                        <input type="text" id="endTime" readonly placeholder="请选择签退时间">
                    </li>
                    <li>
                        <label>签到地址</label>
                        <input id="signInAddress" type="text" value="" placeholder="请输入签到地址" />
                    </li>
                    <li>
                        <label>设置人</label>
                        <input id="setupName" type="text" value="" readonly="readonly"/>
                    </li>
                    <li>
                        <label>审核</label>
                        <label>会议纪要</label>
                    </li>
                </ul>
            </div>
            <div class="con-two">
                <ul class="signInSetup-top-ul2">
                    <li class="top-right">
                        <a class="button-a">
                            <span class="button-span">新增</span>
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
</script>


</body>
</html>
