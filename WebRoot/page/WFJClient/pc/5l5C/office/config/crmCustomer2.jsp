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
    <title>注册导入粉丝</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/crmCustomerPo1.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .data-title ul,
    .data-ul1 {
        min-width: max-content;
    }

    /* 列宽定义 */
    .data-title ul li:nth-child(1) {
        width: 50px;
    }

    .data-title ul li:nth-child(2) {
        width: 90px;
    }

    .data-title ul li:nth-child(3) {
        width: 110px;
    }

    .data-title ul li:nth-child(4) {
        width: 180px;
    }

    .data-title ul li:nth-child(5) {
        width: 200px;
    }


    /* 列宽定义 */
    .data-list ul li:nth-child(1) {
        width: 50px;
        margin-left: 20px;
    }

    .data-list ul li:nth-child(2) {
        width: 90px;
    }

    .data-list ul li:nth-child(3) {
        width: 140px;
    }

    .data-list ul li:nth-child(4) {
        width: 210px;
    }

    .data-list ul li:nth-child(5) {
        width: 180px;
    }

    .data-ul1 {
        display: flex;
        align-items: stretch;
    }
    </style>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            注册导入粉丝
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
<%--    <section class="sec-nav sec-hide ">--%>
<%--        <ul class="clearfix">--%>
<%--&lt;%&ndash;            <li class="clearfix">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <p class="add" style="display: none">添加</p>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li class="clearfix">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <p class="edit">修改</p>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li class="clearfix">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <p class="del">删除</p>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li class="clearfix">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <p class="query">查询</p>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li class="clearfix" style="display: none">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <p class="importData">导入</p>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </li>&ndash;%&gt;--%>
<%--        </ul>--%>
<%--    </section>--%>
    <section class="sec-list">
        <div class="div-sec-data"  style="overflow-x: auto;overflow-y: auto;width: 100%">
            <div class="data-title">
                <ul class="flex-container">
                    <li>序号</li>
                    <li>姓名</li>
                    <li>电话</li>
                    <li>身份证</li>
                    <li>地址</li>
                    <li style="display: none">导入人</li>
                </ul>
            </div>
            <div class="data-list div-data">
            </div>
        </div>
    </section>
</div>
</body>
</html>