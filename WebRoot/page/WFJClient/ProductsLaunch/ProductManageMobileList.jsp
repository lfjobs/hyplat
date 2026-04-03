<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/ProductManageMobileList.css"/>

    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/comm.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/container/mqttLock.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/wfjeshop/ProductManageMobileList.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>

    <title>库存列表</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        const companyID = "${param.companyID}";
    </script>
</head>
<body id="overfover">
<%--http://www.impf2010.com/page/WFJClient/ProductsLaunch/ProductManageMobileList.jsp--%>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li class="title">
            库存列表
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">
            <li class="clearfix ">
                <p class="add"><img src="<%=basePath%>images\WFJClient/fb.png"/>发布</p>
            </li>
            <li class="clearfix">
                <p class="supplement"><img src="<%=basePath%>images\WFJClient/bh.png"/>补货</p>
            </li>
            <li class="clearfix ">
                <p class="price"><img src="<%=basePath%>images\WFJClient/price.png"/>调价</p>
            </li>
            <li class="clearfix">
                <p class="up-p"><img src="<%=basePath%>images\WFJClient/up1.png"/>上架</p>
            </li>
            <li class="clearfix">
                <p class="down-p"><img src="<%=basePath%>images\WFJClient/down.png"/>下架</p>
            </li>
            <li class="clearfix ">
                <p class="browse"><img src="<%=basePath%>images\WFJClient/browse (2).png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="query-p"><img src="<%=basePath%>images\WFJClient/select.png"/>查询</p>
            </li>
            <li class="clearfix">
                <p class="print-p"><img src="<%=basePath%>images\WFJClient/print.png"/>打印</p>
            </li>
            <li class="clearfix door">
                <p class="door-p"><img src="<%=basePath%>images/WFJClient/openedFireDoor.png"/>开门</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc"></div>
            </li>
        </ul>
    </section>
</div>
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <div class="div-header">
            温馨提示
            <p>x</p>
<%--            <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/>--%>
        </div>
        <div class="div-box">
            <p class="title-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
<%--查询--%>
<div class="div-chaxun">
    <div class="div-box">
        <p class="p-top">
            请输入查询信息
        </p>
        <p class="p-inp clearfix">
            <label>产品名称</label>
            <input type="text" placeholder="请填写产品名称" name="pname" id="pname"  min="1" oninput="this.value.replace(/\D/g);"/>
        </p>
        <p class="p-inp clearfix">
            <label>入库仓库</label>
            <input type="hidden" name="depotID" id="depotID"/>
            <input type="text" placeholder="请填写入库仓库" name="depotname" id="depotname" readonly="readonly"/>
        </p>
        <p class="p-bottom">
            查询
        </p>
    </div>
</div>
<%--补货\調價\仓库--%>
<div class="div-iframe">
    <div class="div-box">
        <div class="paytype-header">
            <ul class="clearfix">
                <li class="div-close">
                    <%--<img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png">--%>
                </li>
                <li class="li-title"></li>
                <li class="close-iframe">
                    <img src="<%=basePath%>images\scMobile\wholesaleMall/img_3_11.png">
                </li>
            </ul>
        </div>
        <div class="div-con clearfix jylx">
            <iframe name="main" marginwidth="0" scrolling="yes" marginheight="0"  frameborder="0" id="iframe-" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
        </div>
    </div>
</div>
</body>
</html>
