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
    <title>请假审核</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/leaveReview.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/leaveReview.js"></script>
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
                    请假审核
                </li>
            </ul>
        </header>
        <div class="top">
            <div class="con-one">
                <ul>
                    <li>
                        <label>请假人</label>
                        <span id="staffname"></span>
                    </li>
                    <li>
                        <label>申请时间</label>
                        <span id="applyDate"></span>
                    </li>
                    <li>
                        <label>请假类型</label>
                        <span id="leaveType"></span>
                    </li>
                    <li>
                        <label>请假方式</label>
                        <span id="leaveWay"></span>
                    </li>
                    <li>
                        <label>开始时间</label>
                        <span id="startTime"></span>
                    </li>
                    <li>
                        <label>结束时间</label>
                        <span id="endTime"></span>
                    </li>
                    <li>
                        <label>请假时长</label>
                        <span id="leaveHour"></span> 小时
                    </li>
                    <li>
                        <label>请假事由</label>
                        <span id="leaveReason"></span>
                    </li>
                    <li>
                        <label>状态</label>
                        <span id="status">未审核</span>
                    </li>
                    <li>
                        <label>审核结果</label>
                        <select id="reviewStatus">
                            <option value="-1" selected>请选择</option>
                            <option value="02">已审核</option>
                            <option value="03">驳回</option>
                        </select>
                    </li>
                    <li>
                        <label>审核人</label>
                        <span id="reviewer"></span>
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
