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
<html>
<base href="<%=basePath%>">

<title>卖家收到订单</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
<link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/myOrder.css"/>
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
<script type="text/javascript" charset="utf-8"
        src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/myOrder.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
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
            卖家收到订单
        </li>
        <li <%--onclick="buyer()"--%>>
            <%--打印--%>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
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
                    <p class="active" id="dd">收到订单</p>
                </li>
                <li>
                    <p id="jh">拣货出库</p>
                </li>
                <%--<li>
                    <p id="zf">
                        支付
                    </p>
                </li>--%>

                <li>
                    <p id="fh">
                        发货
                    </p>
                </li>
                <li>
                    <p id="sh">
                        送货
                    </p>
                </li>
                <li>
                    <p id="wl">
                        物流快捷
                    </p>
                </li>
            </ul>
        </div>
    </div>
    <div class="pos-top2"></div>
    <div id="divUL"></div>
</div>
</body>
</html>
