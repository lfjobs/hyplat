<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE>
<html>
<head>
    <base href="<%=basePath%>">

    <title>送货单列表</title>

    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/transportBill.css"/>
    <script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/transporBill.js"></script>
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
            送货
        </li>
        <li>
            <%--打印--%>
        </li>
    </ul>
</header>
<div class="container">
    <section class="sec-serch clearfix">
        <div class="clearfix">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/search_03.png">
            <input type="text" name="" placeholder="搜索" id="" value=""/>
        </div>
        <div>
            <input type="button" value="确定" name=""/>
        </div>
    </section>
    <ul class="ul-tab clearfix">
        <li>
            <p class="active" id="wsh">送货</p>
        </li>
        <li>
            <p id="ysh">送货记录</p>
        </li>
    </ul>
    <div id="divUL"></div>
</div>
</body>
</html>
