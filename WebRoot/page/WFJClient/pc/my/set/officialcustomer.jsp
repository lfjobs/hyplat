<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/set/officalcustomer.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            官方客服
        </li>
    </ul>
</header>

<div class="content">
    <div class="head-div"><img src="<%=basePath%>images/WFJClient/pc/my/customer.png"/></div>
    <div class="info-div">
        <p>亲，请拨打"数字地球"田女士、客服热线电话</p>
        <p><a href="tel:010-64167113"><img style="padding-right:0.2rem;"src="<%=basePath%>images/WFJClient/pc/my/zj.png"/>010-64167113</a><a href="tel:13641106263"><img src="<%=basePath%>images/WFJClient/pc/my/sj.png"/>13641106263</a>,</p>
        <p>竭诚为您服务！</p>

    </div>
</div>
</body>


</html>
