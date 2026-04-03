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
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/AppParkong.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/AppParkingList.js"></script>
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>

    <title>停车收费</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var sccId = '${tcc.sccId}';
        var eastLongitude = "0";//东经
        var northLatitude = "0";//北纬
        var city;//所在城市
    </script>
</head>

<body style="background:#f6f6f6;">
<div id="allmap"></div>
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="back()"></a>
    <h1>停车收费</h1>
    <a href="javascript:void(0)" class="head_R park_area"></a>
</header>
<div class="wrap_page park_wrap">
    <div class="fixed_wrap">
        <div class="park_tab_wrap clearfix">
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_parkingIsIntroduced.jspa?" class="park_tab park_tab_cur">停车场</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_countdown.jspa?" class="park_tab">停车记录</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_viewVehicle.jspa?" class="park_tab">绑定车牌</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${tcc.account}&sccid=${tcc.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${tcc.staffid}" class="park_tab">充值金币</a></div>
        </div>
        <div class="park_search_wrap">
            <input type="text" class="park_search" placeholder="请输入停车场名称">
        </div>
    </div>
    <div class="park_list_wrap">
        <!--js拼接-->
    </div>
    <%--<div class="QR_fixed"></div>--%>
</div>
<script>
    function back() {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'doneClose';
				window.webkit.messageHandlers.Native.postMessage(url);
        }
    }
</script>
</body>
</html>