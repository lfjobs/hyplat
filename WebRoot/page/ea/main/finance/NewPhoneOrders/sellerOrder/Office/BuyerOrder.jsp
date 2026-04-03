<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>供应商订单-财务</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/BuyerOrder.css"/>
    <script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="js/WFJClient/ap.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/BuyerOrder.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var sort = "${param.sort}";
        var comid = "${param.companyid}";
        var staffid = "${param.staffid}";
        var buyIsOkPage = "${buyIsOkPage}";
    </script>
</head>

<body>
<header>
    <ul>
        <li onclick="toBack()">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            订单
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="container">
    <div class="pos-top">
        <section class="sec-serch clearfix">
            <div class="clearfix">
                <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/search_03.png">
                <input type="text" name="" placeholder="搜索" id="ss"/>
            </div>
            <div>
                <input type="button" value="确定" id="search"/>
            </div>
        </section>
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li>
                    <p class="active" id="wfk">未付款</p>
                </li>
                <li>
                    <p id="yfk">已付款</p>
                </li>
                <li>
                    <p id="qk">欠款</p>
                </li>
            </ul>
        </div>
    </div>
    <!--弹框1-->
    <div id="xz1">
        <div class="box">
            <p class="p-top">
                选择
                <i></i>
            </p>
            <form id="formxz1" name="formxz1">
                <input type="hidden" id="cashid" name="cashid"/>
                <input type="hidden" id="jumber" name="jumber"/>
                <input type="hidden" name="raddressId" id="raddressId">
                <input type="hidden" name="rname" id="rname">
                <input type="hidden" name="rtel" id="rtel">
                <input type="hidden" name="address" id="address">
                <input type="hidden" name="zfzt" id="zfzt">
                <input type="hidden" name="zffs" id="zffs">
                <input type="hidden" name="money" id="money">
            </form>
            <div class="div-bto">
            </div>
        </div>
    </div>
    <div class="pos-top2"></div>
    <div id="divUL"></div>
</div>
</body>
</html>
