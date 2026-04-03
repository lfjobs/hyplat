<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
    <script type="text/javascript" src="<%=basePath%>js/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/sfm.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/restaurant/sfshop/clientOrderList.js"></script>

    <title>客户订单</title>
<script type="text/javascript">

    var height = 0;
    var t;
    var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    var state = "${state}";
    var basePath = "<%=basePath%>";
</script>

</head>
<body>
<header class="com_head">
    <a></a>
    <h1>客户订单</h1>
</header>
<div class="wrap_page">
    <div class="tab_wrap">
        <a href="<%=basePath%>ea/assicode/ea_getClientOrderList.jspa?state=01" class="tab_box ${state=="01"?"tab_cur":""}">未结算订单</a>
        <a href="<%=basePath%>ea/assicode/ea_getClientOrderList.jspa?state=00" class="tab_box ${state=="00"?"tab_cur":""}">已结算订单</a>
    </div>
    <div class="tab_con">
        <div class="non_payment">
            <c:forEach items="${pageForm.list}" var="item">
            <a href="###" class="pay_wrap">
                <input class="coId" type="hidden" value="${item[0]}">
                <input class="companyId" type="hidden" value="${item[3]}">
                <input class="ccompanyId" type="hidden" value="${item[7]}">
                <img src="<%=basePath%>${item[4]!=null?item[4]:'images/WFJClient/PersonalJoining/logo@2x.png'}" class="s_logo" alt="">
                <div class="pay_box">
                    <div class="pay_line">
                        <span class="pay_name">${item[5]}</span>
                        <span class="pay_state ${state=="00"?"paid":"no_pay"}">${state=="01"?"待结算":"已结算"}</span>
                    </div>
                    <div class="pay_line">
                        <span class="pay_num"><b>${item[2]}</b></span>
                        <span class="pay_ordernum">订单号：${item[1]}</span>
                    </div>
                    <div class="pay_line">
                        <span class="pay_pirce">￥${item[6]}</span>
                         <c:if test="${state=='01'}">
                        <span class="pay_operation clearfix">
                                <span class="menu_add_btn">加单</span>
                                <span class="pay_btn">结算</span>
                        </span>
                        </c:if>
                    </div>
                </div>
            </a>
            </c:forEach>

        </div>
    </div>
</div>
<script>

    $(function(){
        $(".tab_box").click(function(){
            var v = $(".tab_wrap .tab_box").index($(this));
            $(this).addClass("tab_cur").siblings().removeClass("tab_cur");
         //   $(".tab_con").eq(v).show().siblings(".tab_con").hide();
        })
    })
</script>
</body>
</html>