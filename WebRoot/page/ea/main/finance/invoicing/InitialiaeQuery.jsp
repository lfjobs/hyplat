<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/WFJClient/base.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ea/finance/invoicing/InitialiaeQuery.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/scMobile/payBudget/inventory/Mdate/needcss/Mdate.css"/>
    <script type="text/javascript" charset="utf-8" src="/js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/js/scMobile/payBudget/inventory/Mdate/iScroll.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/js/scMobile/payBudget/inventory/Mdate/Mdate.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/ea/finance/invoicing/InitialiaeQuery.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0 style="display: none;">
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <title>初始库存查询</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var comid = '${param.compayid}';
        var staffid = '${param.staffid}';
    </script>
</head>

<body class="fixedhea">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="/images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            查询
        </li>
        <li id="print">
            打印
        </li>
    </ul>
</header>
<div class="fixedhea_box"></div>
<div class="div-datatime">
    <p>请确保起始时间小于截至时间</p>
</div>
<div class="div-tk">
    <div class="box">
        <h4 class=""></h4>
        <ul>
            <li class="clearfix">
                <p>单号</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_03.png" />
                </div>
            </li>
            <li class="clearfix">
                <p>类别</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_03.png" />
                </div>
            </li>
            <li class="clearfix">
                <p>责任人</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_03.png" />
                </div>
            </li>
            <li class="clearfix">
                <p class="active">时间</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_06.png" />
                </div>
            </li>
        </ul>
        <div class="div-tj">
            <p>提交</p>
        </div>
    </div>
</div>
<div class="content">
    <section class="sec-search clearfix">
        <div class="div-left clearfix">
            <p class="p-dh">
                <span>单号</span>
            </p>
            <p class="p-img">
                <img src="/images/ea/finance/invoicing/xiajain.png" />
            </p>
        </div>
        <div class="div-right clearfix">
            <input type="text" name="pemer" id="pemer" autocomplete="off"/>
            <input type="button" name="" class="query" value="查询" />
        </div>
    </section>
    <section class="sec-searchtime clearfix">
        <div class="div-left clearfix">
            <p class="p-dh">
                时间
            </p>
            <p class="p-img">
                <img src="/images/ea/finance/invoicing/xiajain.png" />
            </p>
        </div>
        <section class="clearfix">
            <div class="div-rl clearfix">
                <input type="text" id="dateSelectorOne" name="sdate" autocomplete="off">
                <input type="text" id="dateSelectorTwo" name="edate" autocomplete="off">
            </div>
            <div class="div-right clearfix">
                <input type="button" name="" class="query" id="inp-cx" value="查询" />
            </div>
        </section>
    </section>
    <div class="div-box">

    </div>
</div>
</body>
</html>
