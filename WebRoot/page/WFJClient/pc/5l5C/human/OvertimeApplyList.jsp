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
    <title>我的加班</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/overtimeApplyList.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/human/overtimeApplyList.js"></script>
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
<body id="">
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
                    我的加班
                </li>
                <li>
                </li>
            </ul>
        </header>
        <div class="top">
            <div class="con-one">
                <ul>
                    <li>
                        <label>加班申请时间</label>
                        <select id="overtimeApplyTime">
                            <option value="1M" selected>近1个月</option>
                            <option value="3M">近3个月</option>
                            <option value="6M">近6个月</option>
                            <option value="1Y">近1年</option>
                            <option value="2Y">近2年</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="txt back_color">
                <p class="txtP">
                    <span class="txtSpan" style="width: 62%;">加班申请时间</span>
                    <span class="txtSpan" style="width: 20%;">状态</span>
                </p>
            </div>
        </div>
        <div class="content_hidden">
            <div class="content intro">
                <div class="list">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
        <div class="content_bottom">
            <div class="con-two">
                <ul class="top-ul2">
                    <li class="top-right">
                        <a class="button-a">
                            <span class="button-span">新增</span>
                        </a>
                    </li>
                </ul>
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
        $(".signInSetup").addClass("pc-bottom");
    }
</script>


</body>
</html>
