<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "92985a9236cbdc3ef50593cba1c23b3f",
        };
    </script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=2.0&key=72c1339d5b2b01c35970160ccabd0aba"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/demand_list.js"></script>
    <title>开启列表抢单</title>
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var t;
        var pagenumber=0;
        var pagecount=0;
        var search="";
        var sccid="${sccid}";
        var wts="${wts}";
        var cwts="${cwts}";
    </script>
</head>

<body style="background: #f4f4f4;">
<s:if test="tle==1">
<header class="com_head">
    <a target="_self" class="back" id="ret" onclick="javascript:history.back(-1);"></a>
    <h1>联营商城会员抢单</h1>
</header>
</s:if>
<div class="wrap_page" style="background: #f4f4f4;<s:if test='tle==null||tle==0'>margin-top:0;</s:if>">
    <div class="order_num">
        目前有<span id="recordCount"></span>个订单
    </div>
    <div class="qd_con"></div>
</div>
<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text">
    <span>正在加载，请稍候……</span>
</div>
<!--正在加载/正在发布 遮罩层 结束-->
<script>
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>
