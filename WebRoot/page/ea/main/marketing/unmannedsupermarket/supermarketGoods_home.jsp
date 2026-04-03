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
    <title>${companyName}</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script type="text/javascript">
        var basePath ='<%=basePath%>';
        var ccompanyID = '${ccompanyID}';
        var industryType = '${industryType}';
        var companyName = '${companyName}';
        var companyId = "${companyId}";
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var posNum = "${param.posNum}";//社区每个机器的ID
        var tt = 0;
        var ralateID = "";


    </script>
    <link rel="stylesheet" type="text/css"
    href="<%=basePath%>css/ea/unmannedsupermarket/supermarketGoods.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/jquery.fly.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/fastclick.js" type="application/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/slick.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/supermarketGoods_home.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        function showDeviceID() {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                Android.showDeviceId();
            }
        }

    </script>


</head>
<body>
<header class="clearfix thead" style="display: none;">
    <menu class="clearfix ">
        <li>
            <a onclick="javascript: window.history.back();return false;"><img
                    src="<%=basePath%>images/unmannedsupermarket/img_1_03.png"/></a>
        </li>
        <li>
            <a href="#" onclick="showDeviceID()" class="txt">${companyName}</a>


        </li>
        <li>
            <%--<img src="<%=basePath%>images/unmannedsupermarket/img_1_06.png"/>--%>
        </li>
        <li>
            <%--查账单--%>
            <%--<a href="#" class="txt" id="getLocation"></a>--%>
        </li>
    </menu>
</header>
<div class="banner">
    <a class="aback"  href="javascript:history.go(-1)"><img
            src="<%=basePath%>images/unmannedsupermarket/image142.png"/></a>
    <%--<img src="<%=basePath%>images/unmannedsupermarket/banne_temporary.png"/>--%>
    <div class="slider single-item">

    </div>
</div>
<section class="shopping_cart">
    <a href="#" onclick="getShoppingCar()">
        <div id="end">
            <img src="<%=basePath%>images/unmannedsupermarket/shopping_cart.png"/>
            <span id="num_shop">0</span>
        </div>
    </a>
</section>
<div class="content_search clearfix">
    <section class="clearfix">
        <input type="search" id="supermarketGoodsName" placeholder="搜索超市商品"/>
        <input type="button" value="搜索" onclick="supermarketGoodsSearch()"/>
        <span class="li_last">
					<img src="<%=basePath%>images/unmannedsupermarket/img_2_05.png"/>
				</span>
        <div class="all_classification">
            <h2>
                全部分类
                <img src="<%=basePath%>images/unmannedsupermarket/img_3_11.png"
                     class="close_all_classification"/>
            </h2>
            <div>
                <menu class="clearfix" id="allGoodsClassify">

                </menu>
            </div>
        </div>
    </section>
    <div>
        <menu class="clearfix tab_level_father" id="oneGoodsClassify">

        </menu>
    </div>
    <div class="tab_level_son">
        <menu class="element" id="twoGoodsClassify">
        </menu>
        <section class="box_right" id="threeGoodsClassify">
            <menu>
            </menu>

        </section>
    </div>

</div>

<div class="pecifications">
</div>
<input type="hidden" id="hiddenNumber"/>
<input type="hidden" id="companyId"/>
<input type="hidden" class="skuId"/>
<ul id="specifications"></ul>
<div class="alert_weigh_">
    <div class="alert_weigh">
        <h2 class="tipcon">正在查询账单请稍后...</h2>
    </div>
</div>
</body>
</html>
