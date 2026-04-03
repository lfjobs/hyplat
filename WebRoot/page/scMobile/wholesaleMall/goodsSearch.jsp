<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
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
        var ccomIDPlatform = "${param.ccomIDPlatform}";
        var companyId = "${companyId}";
        var search = '${search}';
        var companyName = '${companyName}';
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var industryType = '${industryType}';
        var posNum = "${param.posNum}";
        var lxType = "${lxType}";
        var relateID = "";
    </script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/unmannedsupermarket/goodsSearch.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/jquery.fly.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/wholesaleMall/goodsSearch.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
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
        <a onclick="toBack();">
            <img src="<%=basePath%>images/unmannedsupermarket/left_jt.png"/>
        </a>
        <input type="search" id="ttsw_search_id" value="${search}" placeholder="搜索超市商品" autofocus="autofocus"/>
        <input type="button" value="搜索" onclick="supermarketGoodsSearch()"/>
    </section>
    <menu id="supermarketGoodsBox">

    </menu>
</div>
<%--隐藏元素--%>
<div class="pecifications" id="ttsw_ttsw_three_cm_goods_Classify">
</div>
<input type="hidden" id="hiddenNumber"/>
<input type="hidden" id="companyId"/>
<input type="hidden" class="skuId"/>
<ul id="specifications"></ul>
</body>
<script>
    //后退
    function toBack() {
      var phl = "${param.phl}";
       if(phl=="phl"){
            window.history.back();
            return false;
     
        }
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
