<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
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
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/order_man.css">
    <script src="<%=basePath%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/bookingDetails.js"></script>
    <title>预约详情</title>
</head>
<script>
    var basePath = '<%=basePath%>';
    var etoId = '${tbElycOrderRecord.etoId}';
</script>
<body>
<header class="com_head">
    <a onclick="javascript:window.history.go(-1);return false;" class="back"></a>
    <h1>预约详情</h1>
</header>
<div class="wrap_page">
    <%--js拼接--%>
</div>
</body>

</html>