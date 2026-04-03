<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>超市</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/unmannedsupermarket/supermarket.css"/>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/supermarket_home.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var longitude //经度
        var latitude //纬度
        var posNum = "";//社区每个机器的ID

        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
        if(isAndroid) {
            try {
                posNum = Android.forAndroidDeviceId();
            }catch(error) {
                //出错说明不在APP内
            }
        }
    </script>
</head>
<body>
<header class="clearfix">
    <menu class="clearfix">
        <li>
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img
                    src="<%=basePath%>images/unmannedsupermarket/img_1_03.png"/></a>
        </li>
        <li>
            <a href="#">商城</a>
        </li>
        <li>
            <img src="<%=basePath%>images/unmannedsupermarket/img_1_06.png"/>
        </li>
        <li>
            <a href="#" class="txt" id="getLocation"></a>
        </li>
    </menu>
</header>
<div class="content">
    <section class="sec">
        <a  onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>images/unmannedsupermarket/left_jt.png"/></a>
        <input type="search" name="" id="supermarketName" value="${companyName}" placeholder="搜索你要找的超市"/>
        <input type="button" value="搜索" onclick="supermarketSearch()"/>
    </section>
    <menu id="supermarketBox">

    </menu>
</div>
</body>
</html>
