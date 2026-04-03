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
    <title>调查项目</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/market/marketdc_index.css">

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <div class="div-header">
            5L5C管理系统
        </div>
        <ul class="ul-header clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/WFJClient/pc/5l5c/return.png"/>
                </a>
            </li>
            <li>调查项目</li>
            <li></li>
        </ul>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/hrmanage_index.jsp"></a>
                        <p class="p-1">调查市场</p>
                        <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/contactcompany/contactComMtList.jsp?companyID=${param.companyID}"><p class="p-2">单位管理</p></a>
                        <p class="p-2">个人管理</p>
                        <p class="p-2">市场调查</p>
                        <p class="p-2">同值调查</p>
                        <p class="p-2">项目管理</p>

                </li>
                <li class="clearfix">

                    <p class="p-1">商品管理</p>
                    <a href="<%=basePath%>page/ea/main/finance/brokerage/mobile_commission_list.jsp"><p class="p-2">物品列表</p></a>
                    <p class="p-2">代理佣金</p>
                    <p class="p-2">佣金分配</p>
                    <p class="p-2">上架佣金</p>
                    <p class="p-2">已审</p>
                    <p class="p-2">未审</p>
                </li>
            </ul>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";

    //计算列表高度
    $(".p-height").each(function () {
        $(this).parent().find(".p-title").css('line-height', $(this).height() + "px");
        $(this).parent().find(".div-more").css('line-height', $(this).height() + "px");
    })
</script>
</body>
</html>
