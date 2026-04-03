<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/dspgoodslist.css">

    <script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/collage/dsp/dspgoodslist.js" type="text/javascript" charset="utf-8"></script>
    <title>商品列表</title>

    <script type="text/javascript">
        var pageNumber = 1
        var pageSize = 20
        var totalPages = 1
        var basePath = "<%=basePath%>";
        var videoId = "${param.videoId}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a href="javascript:history.go(-1)" target="_self">
                <img src="<%=basePath%>images/resume/return.png">
            </a>
        </li>
        <li>
            视频同款好货
        </li>
    </ul>
</header>
<div class="content">
    <ul class="goods-wrapper">
        <%--<c:forEach items="${pageForm.list}" var="item" varStatus="v">
            <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                <li class="last1 clearfix" id="${item[1]}">
            </c:if>
            <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                <li class="clearfix" id="${item[1]}">
            </c:if>
            <input type="hidden" value="${item[7]}" class="pricetype"/>
            <input type="hidden" value="${item[0]}" class="goodsid"/>
            <input type="hidden" value="${item[5]}" class="companyId"/>
            <input type="hidden" value="${item[6]}" class="ccompanyId"/>
            <div class="div-img">
                <img src="<%=basePath%>${item[3]}"
                     onerror="this.src='<%=basePath%>images/ea/production/forum/reportAnError.png'"/>
            </div>
            <div class="div-right">
                <div class="top">
                        ${item[2]}
                </div>
                <div class="bottom">
                    <p>￥${item[4]}</p>
                    <p>去抢购</p>
                </div>
            </div>
            </li>
        </c:forEach>--%>
        <%--<li class="clearfix">--%>
        <%--<div class="div-img">--%>
        <%--<img src="img/list_06.png"/>--%>
        <%--</div>--%>
        <%--<div class="div-right">--%>
        <%--<div class="top">--%>
        <%--韩版设计感小众破洞镂空露肩短袖t恤女夏 季宽松纯棉--%>
        <%--</div>--%>
        <%--<div class="bottom">--%>
        <%--<p>￥993</p>--%>
        <%--<p>去抢购</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
        <%--<div class="div-img">--%>
        <%--<img src="img/list_08.png"/>--%>
        <%--</div>--%>
        <%--<div class="div-right">--%>
        <%--<div class="top">--%>
        <%--抖音同款蔬菜水果沙拉切割碗切沙拉果蔬 分割器沙拉切碗切沙拉神器--%>
        <%--</div>--%>
        <%--<div class="bottom">--%>
        <%--<p>￥59</p>--%>
        <%--<p>去抢购</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
        <%--<div class="div-img">--%>
        <%--<img src="img/list_10.png"/>--%>
        <%--</div>--%>
        <%--<div class="div-right">--%>
        <%--<div class="top">--%>
        <%--松肉器48针敲肉断筋扎孔入味针家用猪牛 排器不锈钢打肉--%>
        <%--</div>--%>
        <%--<div class="bottom">--%>
        <%--<p>￥5993</p>--%>
        <%--<p>去抢购</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</li>--%>
    </ul>
</div>
</body>
</html>
