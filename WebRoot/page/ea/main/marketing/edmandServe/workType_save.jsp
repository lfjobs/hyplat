<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-orientation" content="portrait"/>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css"/>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css"/>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/workType_save.js"></script>
    <title>选择抢单类型</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var a_user = "${a_user}";
        var staffid = '${param.staffid}';
        var sccid = '${param.sccId}';
        var cwid = "";
        var originPage = '${param.originPage}';
    </script>
</head>
<body style="background-color: #F3F3F3;padding: 0.25rem">
<header class="com_head">
    <a target="_self" class="back" id="ret" onclick="javascript:history.back(-1);"></a>
    <h1>选择项目工种</h1>
</header>
<div class="nest_bd select_div" style="height:6%">
    <div class="div-more">
        <div class="div-LV2" style="margin-top:0px;">搜索关注</div>
        <div class="div-input"><input type="text" id="select-input"></div>
        <img id="select-img" src="<%=basePath %>/images/WFJClient/pc/newimg/img_13.png" alt="">
    </div>
</div>
<div class="div-LV2 div-margin-bottom div_tj div_tj_title isShow">默认项目</div>
<div class="div-top div-margin div_tj div_tj_value isShow"></div>
<%--<div class="div-top2 isShow">
    <div class="div-LV2 div-margin-bottom">抢单服务</div>
    <div class="div-top div-margin div-hy" id="bType20240606XPQ8KK52P50000001464">
    </div>
</div>--%>
<div class="div-top2 isShow">
    <div class="div-LV2 div-margin-bottom">建筑装饰</div>
    <div class="div-top div-margin div-hy" id="bType202406068HJT9K9UR40000000947">
    </div>
</div>
<div class="div-top2 isShow">
    <div class="div-LV2 div-margin-bottom">出行类</div>
    <div class="div-top div-margin div-hy" id="bType2025071537IU9RKRW70000000007">
    </div>
</div>
<div class="wrap_page isShow" style="margin-top: 0">
    <div class="nest_page" style="background: #f3f3f3;position: static">
        <div class="nest_bd proAll" style="margin-top:0;">
            <div class="div-more">
                <div class="div-LV2">全部项目</div>
            </div>
        </div>
    </div>
</div>
<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text">
    <span>正在加载，请稍候……</span>
</div>
<!--正在加载/正在发布 遮罩层 结束-->
</body>
</html>
