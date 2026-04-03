<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>拣货出库单</title>

    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/SortGoods.css"/>
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/SortGoods.js"></script>
    <script type="text/javascript">
        var staffid = "${param.staffid}";
        var sort = "${param.sort}";
        var companyid = "${param.companyid}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            拣货出库
        </li>
        <li>
            <%--打印--%>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <section class="sec-serch clearfix">
        <div class="clearfix">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/search_03.png">
            <input type="text" name="" placeholder="搜索" id="" value=""/>
        </div>
        <div>
            <input type="button" value="确定" id="search"/>
        </div>
    </section>
    <ul class="ul-tab clearfix">
        <li>
            <p class="active" id="wck">拣货出库</p>
        </li>
        <li>
            <p id="yck">拣货出库记录</p>
        </li>
    </ul>
    <div id="divUL"></div>
</div>
</body>
</html>
