<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
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
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/AppParkong.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/AppParkingDetails.js"></script>
    <title>停车收费</title>

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var eastLongitude = "0";//东经
        var northLatitude = "0";//北纬
        var city;//所在城市
        var siteId = '${object[0]}';
        var compnayid = '${object[7]}';
        var ccompanyid = '${object[8]}';
    </script>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>停车收费</h1>
    <a href="###" class="head_R park_area"></a>
</header>
<div class="wrap_page park_wrap" style="padding: 2.6rem .6rem 1rem;">
    <div class="fixed_wrap">
        <div class="park_tab_wrap clearfix">
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_parkingIsIntroduced.jspa?" class="park_tab park_tab_cur">停车场</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_countdown.jspa?" class="park_tab">停车记录</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_viewVehicle.jspa?" class="park_tab">绑定车牌</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${tcc.account}&sccid=${tcc.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${tcc.staffid}" class="park_tab">充值金币</a></div>
        </div>
    </div>
    <div class="park_list_wrap">
        <a href="###" class="park_list_box">
            <div class="park_name_box clearfix">
                <img src="<%=basePath%>${object[7]}" class="park_logo" alt="">
                <span class="park_name">${object[1]}</span>
            </div>
            <div class="park_info clearfix">
                <span class="park_car_num">车位数：${object[4]}</span>
            </div>
            <div class="park_info clearfix">
                <span class="park_site">${object[2]}km  ▏ ${object[3]}</span>
            </div>
        </a>
        <div class="park_list_box">
            <div class="charge_tit">收费标准</div>
            <p class="charge_con">
                <span>金币充值消费（消费直接扣除金币）</span>
                <br>
                <span>按小时：</span> <span class="jlcxs"></span> <span class="sjcxs"></span>
                <br>
                <span>会员制：包天、包月、包年</span>
            </p>
        </div>
        <span class="ctext">教练车</span>

        <div class="park_list_box baoyue jlc">
            <!--js拼接-->
        </div>
        <span class="ptext" >私家车</span>
        <div class="park_list_box baoyue sjc">
            <!--js拼接-->
        </div>

    </div>
</div>
</body>
</html>