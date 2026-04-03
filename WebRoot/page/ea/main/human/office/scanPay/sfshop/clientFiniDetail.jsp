<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

    <title>收付码商城</title>


</head>
<body>
<header class="com_head">

    <h1>客户订单</h1>
</header>
<div class="wrap_page">
    <a href="javascript:;" class="sele_tn">
        <img src="<%=basePath%>${clientOrder[4]!=null?clientOrder[4]:'images/WFJClient/PersonalJoining/logo@2x.png'}" alt="" class="o_logo">
        <span class="o_s_name">${clientOrder[5]}</span>
        <span class="o_tn">${clientOrder[2]}</span>
    </a>
    <div class="menu_wrap">
        <c:forEach items="${clientDetaillist}" var="item">
        <div class="menu_part">
            <div class="menu_name">
                <img src="<%=basePath%>${item[4]!=null?item[4]:'images/WFJClient/PersonalJoining/logo@2x.png'}" class="menu_img" alt="">
                <span>${item[5]}</span>
            </div>
            <span>就餐形式：${item[6]}</span>
            <ul class="menu_box">
                <c:forEach items="${clientGoodslist}" var="subitem">
                    <c:if test="${item[0] eq subitem.codID}">
                        <li>
                            <span>${subitem.goodsName}<c:if test="${subitem.standard!=null&&subitem.standard!='默认规格'}">(${subitem.standard})</c:if></span>
                            <span>x${subitem.quantity}</span>
                            <span>x${subitem.price}</span>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
            <div class="o_time">
                下单时间：${fn:substring(item[2],0,19)}
            </div>
            <p class="o_remark">
                备注：${item[3]}
            </p>
        </div>
  </c:forEach>

    </div>
    <div class="menu_total clearfix">
        <div class="menu_pirce">￥${clientOrder[6]}</div>
        <div class="menu_text">
            <div class="js_name">结算人：${clientOrder[8]}</div>
            <div class="js_time">结算时间：${fn:substring(clientOrder[9],0,19)}</div>
            <div class="js_time">客户：${clientOrder[12]}</div>
        </div>
    </div>
</div>

</body>
</html>