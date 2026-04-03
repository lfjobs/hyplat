<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>开门2</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/container/selectDoor2.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/font-size.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/container/mqttLock.js"></script>
</head>
<body>
<header>
    <ul>
        <li>
            <a onclick="javascript: window.history.go(-1);return false;">
                <img src="<%=basePath%>images/ea/collage/arrow_left.gif"/>
            </a>
        </li>
        <li>选择柜门<a id="status" onclick="getDoorStatus()">-</a></li>
        <li></li>
    </ul>
</header>
<div class="container">
    <div class="p-id">货柜编号:<span>${hgcode eq ""||hgcode eq null?param.hgcode:hgcode}</span></div>
    <div class="p-name">登录人姓名：<span></span></div>
    <div class="p-select">请选择开几号门</div>
    <div class="door">
        <div class="div-door-01">
            <p class="p-01">1号门</p>
            <div class="door-img"><!-- 有active为开门状态 -->
                <img class="img-close" src="<%=basePath%>images/supermarket/container/left-close.jpg" alt=""/>
                <img class="img-open" src="<%=basePath%>images/supermarket/container/left-open.jpg" alt=""/>
                <!-- 下面两个是右边门图 -->
                <!--
                <img class="img-close" src="images/right-close.jpg" alt="" />
                <img class="img-open" src="images/right-open.jpg" alt="" />
                 -->
            </div>
            <div class="div-btn-door btn-door" id="1"><!-- 有active为开门状态 -->
                开门
            </div>
        </div>

        <div class="div-door-01">
            <p class="p-01">2号门</p>
            <div class="door-img">
                <img class="img-close" src="<%=basePath%>images/supermarket/container/right-close.jpg" alt=""/>
                <img class="img-open" src="<%=basePath%>images/supermarket/container/right-open.jpg" alt=""/>
            </div>
            <div class="div-btn-door btn-door" id="2">
                开门
            </div>
        </div>
        <!--
        <div class="div-door-01">
            <p class="p-01">3号门</p>
            <div class="door-img">
                <img class="img-close" src="images/left-close.jpg" alt="" />
                <img class="img-open" src="images/left-open.jpg" alt="" />
            </div>
            <div class="div-btn-door btn-door" id="door">
                开门
            </div>
        </div>

        <div class="div-door-01">
            <p class="p-01">4号门</p>
            <div class="door-img">
                <img class="img-close" src="images/left-close.jpg" alt="" />
                <img class="img-open" src="images/left-open.jpg" alt="" />
            </div>
            <div class="div-btn-door btn-door" id="door">
                开门
            </div>
        </div>

        <div class="div-door-01">
            <p class="p-01">5号门</p>
            <div class="door-img">
                <img class="img-close" src="images/left-close.jpg" alt="" />
                <img class="img-open" src="images/left-open.jpg" alt="" />
            </div>
            <div class="div-btn-door btn-door" id="door">
                开门
            </div>
        </div> -->


    </div>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var hgcode = "${hgcode}";
    if (hgcode == "") {
        hgcode = "${param.hgcode}";
    }
    var staffName = "${param.staffName}";
    var posNum = "${param.posNum}";

    MQTTconnect(posNum);
    $(".p-name span").text(staffName);
    // 使用事件委托绑定 click 事件
    $('.door').on('click', '.btn-door', doorClick);

    $(".btn-door").each(function () {
        $(this).on('click', doorClick());
    });


    function doorClick() {
        var canClick = true;
        return function () {
            if (!canClick) {
                return;
            }
            canClick = false;
            if ($(this).siblings(".door-img").is(".active")) {
                // 已经开门(点击关门)
                $(this).text("开门");
                $(this).removeClass("active");
                $(this).siblings(".door-img").removeClass("active");
                //关门
                closeDoor($(this).attr("id"));
            } else {
                // 没开门（点击开门）
                $(this).text("关门");
                $(this).addClass("active");
                $(this).siblings(".door-img").addClass("active");
                //开门
                openDoor($(this).attr("id"));
            }

            setTimeout(function () {
                canClick = true;
            }, 1000);
        };
    }

    //开关门状态
    function onDoorStateChange(data) {
        var jsonArray = JSON.parse(data);
        for (var i = 0; i < jsonArray.length; i++) {
            const {doorNumber, status} = jsonArray[i];
            if (status == "OPENED") {
                if (!$("#" + doorNumber).is(".active")) {
                    //$('.door').on('click', '#' + doorNumber, doorClick());
                    $('#' + doorNumber).trigger("click");
                }
            } else {
                if ($("#" + doorNumber).is(".active")) {
                    //$('.door').on('click', '#' + doorNumber, doorClick());
                    $('#' + doorNumber).trigger("click");
                }
            }
        }
    }

    //$("#status").trigger("click");
</script>
</html>