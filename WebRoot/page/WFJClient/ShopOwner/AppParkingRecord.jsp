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
    <script src="<%=basePath%>js/ea/office_ea/makeApp/AppParkingRecord.js"></script>
    <title>停车记录</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var eastLongitude = "0";//东经
        var northLatitude = "0";//北纬
        var city;//所在城市
    </script>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>停车收费</h1>
    <a href="###" class="head_R park_area"></a>
</header>
<div class="wrap_page park_record_wrap">
    <div class="fixed_wrap">
        <div class="park_tab_wrap clearfix">
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_parkingIsIntroduced.jspa?" class="park_tab">停车场</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_countdown.jspa?" class="park_tab park_tab_cur">停车记录</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_viewVehicle.jspa?" class="park_tab">绑定车牌</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${tcc.account}&sccid=${tcc.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${tcc.staffid}" class="park_tab">充值金币</a></div>
        </div>
    </div>
    <div class="park_rec_con">
        <c:forEach var="s" items="${list}">
            <div class="memcard_wrap park_list_box clearfix" data-tcid="${s[0]}">
                <img src="<%=basePath%>${s[1]}" alt="">
                <div class="mem_text">
                    <div class="memcard_name">${s[2]}</div>
                    <div class="validity_time">有效期：${s[4]}--${s[5]}</div>
                </div>
                <c:choose>
                    <c:when test="${s[3]>=0}">
                        <div class="mem_posi">剩余<span>${s[3]}天</span>到期</div>
                    </c:when>
                    <c:otherwise>
                        <div class="mem_posi"><span>已到期</span></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
        <c:if test="${object!=null}">
            <div class="park_list_box">
                <div class="car_state_wrap" data-carmid="${object[0]}">
                    <div class="car_num">
                        车牌号：${object[1]}
                    </div>
                    <div class="car_state clearfix">
                        <span>进入时间：${object[2]}</span>
                        <span>未出</span>
                    </div>
                </div>
            </div>
        </c:if>
        <!--js拼接-->
    </div>
</div>
</body>
</html>
