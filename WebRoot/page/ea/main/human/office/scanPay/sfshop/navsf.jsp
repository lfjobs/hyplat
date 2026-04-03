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

    <title>收付码商城</title>


</head>
<body>
<%--<header class="com_head">
    &lt;%&ndash;<a href="javascript:;" class="back"></a>&ndash;%&gt;
    <h1>收付码商城</h1>
</header>--%>
<div class="wrap_page" style="margin-top: 0.5rem;">
    <ul class="main_list">
        <li><a href="<%=basePath%>ea/assicode/ea_getAssiCompanyList.jspa">公司商城</a></li>
        <li><a href="<%=basePath%>ea/assicode/ea_getClientOrderList.jspa?state=01">客户订单</a></li>
    </ul>
</div>
</body>
</html>