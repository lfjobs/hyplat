<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资产管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    资产管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <p class="p-title">采购管理</p>
                    <p class="p-height clearfix">
                        项目设计 调标发布 投标预算 收标预算
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">比价审批</p>
                    <p class="p-height">
                        比选 审批 收付 <br>
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">收验管理</p>
                    <p class="p-height">
                        收货 验货 退货
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">库房管理</p>
                    <p class="p-height">
                        <span id="kcxx">库存信息</span>
                        <span id="kcgl">库房管理</span>
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    //计算中间区域宽度
    $(".p-height").each(function () {
        var pWth = $(".pc-box").width() - $(this).prev().width() - 100;
        $(this).width(pWth + "px")
    })
    //计算列表高度
    $(".p-height").each(function () {
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
        var pHei = $(this).parent().outerHeight() - 51;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 50 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }

    $("#kcxx").on('click', function () {
        window.location.href="<%=basePath%>/page/WFJClient/pc/5l5C/office/kcunList.jsp"
    })
    $("#kcgl").on('click', function () {
        window.location.href="<%=basePath%>/page/WFJClient/pc/5l5C/office/kfangmanage.jsp"
    })
</script>
</body>
</html>
