<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/exam_time_charge.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/TheTestTimeTrue.js"></script>
    <title>考场计时收费</title>
    <style type="text/css">
        #prompt div{
            width: 70%;
            background: rgba(0,0,0, 0.5);
        }

    </style>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var sccId = '${tcc.sccId}';
        var companyId = '${companyId}';
        var s = "0";
        var ppID = '${ppk.ppID}';
        var posNum = "${param.posNum}";
        var companyname = "${companyname}";
        var sc = "${param.sc}";

        function onFocus () {
            var target = event.target
            setTimeout(function () {
                target.readOnly = false
            },0)
        }
        function onBlur() {
            event.target.readOnly = true
        }
    </script>
</head>

<body style="background:#f3f3f3;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="back()"></a>
    <h1>考场计时收费</h1>

    <a href="javascript:void(0)" class="head_R QR_btn"></a>
    <a href="javascript:void(0)" class="head_R ScanFace"></a>
</header>
<div class="wrap_page exam_time_wrap">
    <div class="fixed_wrap">
        <div class="order_wrap">
            <a href="javascript:void(0)" class="order_btn">一键预约练车</a>
        </div>
        <div class="exam_tab_wrap clearfix">

            <a href="javascript:void(0)" class="exam_tab exam_cur" data-status="0">用户练车记录</a>
            <a href="javascript:void(0)" class="exam_tab" data-status="1">我的练车记录</a>
        </div>
    </div>
    <div class="search_box">
        <input type="text" class="barcode" onfocus="onFocus()" onblur="onBlur()"  style="opacity:0;"/>
        <input type="number" class="search_inp" placeholder="请输入用户帐号">
    </div>
    <div class="exam_rec_wrap">
        <div class="exam_rec_con my">
            <!--js拼接-->
        </div>
        <div class="exam_rec_con user">
        </div>
    </div>
</div>
<%--提示框--%>
<div class="div-ts">
    扫码成功
</div>
<!-- 提示窗口 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>

<script>
    $(function(){
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.8+"px");
        $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
        $(".exam_rec_wrap").height($(window).height()-$(".com_head").outerHeight(true)-$(".search_box").outerHeight(true));
        $(".exam_tab").click(function(){
            var index =$(".exam_tab_wrap .exam_tab").index(this);
            $(this).addClass("exam_cur").siblings().removeClass("exam_cur");
            //console.log(index);
            $(".exam_rec_con").eq(index).show().siblings().hide();

            pageNumber = 0;
            $(".exam_rec_con").empty();
            s = $(this).attr("data-status");
            if(s=="1"){
                ajax1();
            }else if(s=="0"){
                ajax();
            }
            if(index==1){
                $(".exam_time_wrap").addClass("no_search");
            }else{
                $(".exam_time_wrap").removeClass("no_search");
            }

        })
    })
</script>
</body>

</html>