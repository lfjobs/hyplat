<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<style type="text/css">
    #prompt div {
        width: 70%;
        background: rgba(0, 0, 0, 0.5);
    }

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
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var t;
    var tradecode = '';
    var ppname = '';
    var search = '';
    var flag = '${param.flag}';
</script>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>数字地球商城</title>
    <script type="text/javascript" src="<%=basePath %>/js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/WFJClient/digitalmall_new.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath %>/js/ea/marketing/wfjeshop/digitalmall.js"></script>
    <script type="text/javascript">
        var back = "${param.back}";

        function backp() {

            try {
                if (back == "index") {
                    window.history.go(-1);
                    return false;
                    // document.location.href = basePath+"ea/wfjshop/ea_getWFJshops.jspa";
                } else if (back != null && back != "") {
                    window.history.go(-1);
                    return false;
                } else {
                    var u = window.navigator.userAgent;
                    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                    if (isAndroid == true) {
                        console.log("安卓");
                        Android.callAndroidjianli();//调用安卓接口
                    } else if (isiOS == true) {
                        console.log("IOS");
                        var url = "func=" + 'doneClose';
                        window.webkit.messageHandlers.Native.postMessage(url);
                    }
                }
            } catch (error) {
                window.history.go(-1);
                return false;

            }


        }
    </script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a onclick="backp();">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/left_jt.png">
            </a>
        </li>
        <li style="width: 70%;">数字地球商城</li>

        <li class="cartList" style="width: 20%;">
            <a href="<%=basePath %>/ea/wfjshop/ea_getcity.jspa">
                <img src="<%=basePath%>/images/WFJClient/Newjspim/shopcar.png"/>
                <div class="num0" style="display: none;">
                    <span id="span">0</span>
                </div>
            </a>&nbsp;&nbsp;
            <a href="javascript:">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/ico-search.png" id="search">
            </a>
        </li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="mil_shop">
    <ul>
        <li id="sales">
            <span class="p">最新发布</span>
            <span class="screen">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/down2.png" alt="">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/up2.png" alt="">
            </span>
            <span class="screen_">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/down2_.png" alt="">
                <img src="<%=basePath %>images/WFJClient/DigitalMall/up2_.png" alt="">
            </span>
        </li>
        <li id="pop">销量优先</li>
        <li id="screen">筛选分类</li>
    </ul>
    <div class="case">
        <dd id="newest">最新发布</dd>
        <dd id="smart">综合排序</dd>
        <dd id="praise">好评优先</dd>
        <dd id="plow">价格最低</dd>
        <dd id="ptop">价格最高</dd>
    </div>
</div>
<div class="content_hidden">
    <div class="content intro">
        <div class="con_shop">
            <ul>
            </ul>
        </div>
    </div>
</div>
<!-- 筛选分类 -->
<div class="grade-eject">
    <ul class="grade-w" id="gradew">
    </ul>
    <ul class="grade-t" id="grades">
    </ul>
</div>

<a href="javascript:" class="return">
    <img src="<%=basePath %>images/WFJClient/DigitalMall/return.png" alt="">
</a>

<!-- 搜索页面 -->
<div id="ss" class="alert_body">
    <header>
        <ul id="ssou">
            <li style="width: 10%;" onclick="ret()">
                <a href="javascript:">
                    <img src="<%=basePath %>images/WFJClient/DigitalMall/left_jt.png">
                </a>
            </li>
            <li style="width: 75%;">
                <input type="search" name="" placeholder="搜索" onfocus="this.placeholder=''"
                       onblur="this.placeholder='搜索'" value="" class="sousuo" id="search_sc"/>
            </li>
            <li style="width: 10%;">
                <input type="button" value="搜索" id="search_ss" onclick="sousuo()"/>
            </li>
            <div class="clearfix"></div>
        </ul>
    </header>
    <div class="alert_2">
        <div class="con">
            <div class="mil" id="history">
            </div>
            <div class="mil" id="tuijian">
                <h5>推荐</h5>
                <div class="mil_c">
                    <p>感恩酒</p>
                    <p>桑果酒</p>
                    <p>加油卡</p>
                    <p>c1手动档</p>
                    <p>花蜂蜜</p>
                </div>
            </div>
        </div>
    </div>
    <div id="prompt" style="width: 100%; display: none;z-index:10;">
        <center>
            <div>
                <span style="position: relative; top: 19.8%;"></span>
            </div>
        </center>
    </div>
</div>
<div class="alert_"></div>
<div class="nest_page" style="background: #f3f3f3;">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>选择行业类别</span>
    </div>
    <div class="nest_bd"></div>
</div>
<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text">
    <span>正在加载，请稍候……</span>
</div>
</body>
</html>