<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <title>招商服务要求</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>招商服务要求</h1>
</header>
<div class="wrap_page">
    <div class="mer_rule">
        <!--规则内容-->
        要求内容:<br>
        <%--<img src="images/rule.png" alt="">--%>
        <s:property value="#request.de.func"/>
    </div>
</div>

</body>
</html>
