<%@ page language="java" pageEncoding="UTF-8" %>
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
    <title>搜索</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var ccompanyID = '${ccompanyID}';
        var industryType = '${industryType}';
        var companyId = "${companyId}";
        var posNum = "${param.posNum}";
        var lxType = "${lxType}";
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var relateID = "";
    </script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/unmannedsupermarket/goodsSearch.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/jquery.fly.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/scMobile/wholesaleMall/dpGoodsSearch.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<header class="clearfix">
    <menu class="clearfix">
        <li>
            <a href="<%=basePath%>ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=${ccompanyID}&companyName=${companyName}&industryType=${industryType}&posNum=${param.posNum}">
                <img src="<%=basePath%>images/unmannedsupermarket/img_1_03.png"/></a>
        </li>
        <li>
            <a href="#" class="txt">${companyName}</a>
        </li>
        <li>
            <img src="<%=basePath%>images/unmannedsupermarket/img_1_06.png"/>
        </li>
        <li>
            <a href="#" class="txt" id="getLocation"></a>
        </li>
    </menu>
</header>
<section class="shopping_cart">
    <a onclick="getShoppingCar()">
        <div id="end">
            <img src="<%=basePath%>images/unmannedsupermarket/shopping_cart.png"/>
            <span id="num_shop">0</span>
        </div>
    </a>
</section>
<div class="content_search">
    <section>
        <%--<a href="<%=basePath%>ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=${ccompanyID}&companyName=${companyName}&industryType=${industryType}&posNum=${param.posNum}">
            <img src="<%=basePath%>images/unmannedsupermarket/left_jt.png"/></a>--%>
        <a href="#" onclick="javascript:window.history.back();return false;">
            <img src="<%=basePath%>images/unmannedsupermarket/left_jt.png"/></a>
        <input type="search" id="supermarketGoodsName" value="${search}" placeholder="搜索超市商品" autofocus="autofocus"/>
        <input type="button" value="搜索" onclick="supermarketGoodsSearch()"/>
    </section>
    <menu id="supermarketGoodsBox">

    </menu>
</div>
<%--隐藏元素--%>
<div class="pecifications">
</div>
<input type="hidden" id="hiddenNumber"/>
<input type="hidden" id="companyId"/>
<input type="hidden" class="skuId"/>
<ul id="specifications"></ul>
</body>
<script>
    //后退
    function toBack() {
        window.location.href = basePath + "ea/wholesaleMall/ea_toWholesaleMall.jspa";
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
</script>
</html>
