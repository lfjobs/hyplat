<%@ taglib prefix="c" uri="/struts-tags" %>
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
    <title>全部商家</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/unmannedsupermarket/supermarket.css"/>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/wholesaleMall/businessList.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var ccompanyID = "${ccompanyID}";//公司id
        var companyName = "${companyName}";//公司名称
        var companyId = "${companyId}";
        var qbsjCompanyID = "${qbsjCompanyID}";//公司id
        console.log(ccompanyID+"-----"+companyId+"-----"+companyName+"----"+qbsjCompanyID);
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var longitude //经度
        var latitude //纬度
        //TODO 不清楚做什么的
        var posNum = "${posNum}";//社区每个机器的ID

        $(function(){
            if(posNum!=null&&posNum!=""){
                $(".sec").find("a").hide();
            }else{
                $(".sec").find("a").show();
            }

        });

    </script>
</head>
<body>
<header class="clearfix">
    <menu class="clearfix">
        <li>
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa">
                <img src="<%=basePath%>images/unmannedsupermarket/img_1_03.png"/>
            </a>
        </li>
        <li>
            <a href="#">商城</a>
        </li>
        <li>
            <img src="<%=basePath%>images/unmannedsupermarket/img_1_06.png"/>
        </li>
        <li>
            <a href="#" class="txt" id="getLocation"></a>
        </li>
    </menu>
</header>
<div class="content">
    <section class="sec">
        <a onclick="toBack();">
            <img src="<%=basePath%>images/unmannedsupermarket/left_jt.png"/>
        </a>
        <input type="search" name="" id="supermarketName" value="${search}" placeholder="搜索你要找的商家"/>
        <input type="button" value="搜索" onclick="supermarketSearch()"/>
    </section>
    <menu id="supermarketBox">

    </menu>
</div>
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
