<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/WFJClient/base.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ea/finance/invoicing/InitialiaeList.css"/>
    <script type="text/javascript" charset="utf-8" src="/js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/ea/finance/invoicing/InitialiaeList.js"></script>
    <title>初始库存列表</title>
    <script type="text/javascript">
        var comid = '${param.compayid}';
        var staffid = '${param.staffid}';
    </script>
    <%--http://localhost:8080/page/ea/main/finance/invoicing/InitialiaeList.jsp?compayid=company201009046vxdyzy4wg0000000025&staffid=Staff2016041894IXER8ZQW0000002405--%>
</head>
<body class="fixedhea">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="/images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            初始库存
        </li>
    </ul>
</header>
<div class="fixedhea_box"></div>
<div class="banner">
    <ul class="clearfix">
        <li class="clearfix" id="add">
            <img src="/images/ea/finance/invoicing/kucun_03.png"/>
            添加
        </li>
        <li class="clearfix" id="query">
            <img src="/images/ea/finance/invoicing/kucun_06.png"/>
            查询
        </li>
        <li class="clearfix" id="print">
            <img src="/images/ea/finance/invoicing/kucun_08.png"/>
            打印
        </li>
    </ul>
</div>
<div class="content" id="initList">
</div>
</body>
</html>
