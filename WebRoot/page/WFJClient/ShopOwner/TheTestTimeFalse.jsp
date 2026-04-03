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
    <script src="<%=basePath%>js/ea/office_ea/makeApp/TheTestTimeFalse.js"></script>
    <title>学员版计时收费</title>

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sc = "${param.sc}";
        
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var sccId = '${tcc.sccId}';
        var companyId = '${companyId}';
        var ppID = '${ppk.ppID}';
    </script>
</head>

<body style="background:#f3f3f3;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="back()"></a>
    <h1>考场计时收费</h1>
    <%--<a href="###" class="head_R time_order">计时订单</a>--%>
</header>
<div class="wrap_page exam_time_wrap no_search">
    <div class="fixed_wrap">
        <div class="order_wrap">
            <a href="javascript:void(0)" class="order_btn">一键预约练车</a>
        </div>
        <div class="exam_tab_wrap clearfix">
            <a href="javascript:void(0)" class="exam_tab exam_cur">我的练车记录</a>
        </div>
    </div>
    <div class="exam_rec_wrap">
        <div class="exam_rec_con">
            <!--js拼接-->
        </div>
    </div>
</div>
</body>
</html>
