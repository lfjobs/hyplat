<%--
  Created by IntelliJ IDEA.
  User: ljc
  Date: 2017/3/14 0014
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <script src="<%=basePath%>js/WFJClient/ProductAgent/productAgent.js"></script>
    <title>招商服务</title>
</head>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var t;
    var flag = "p20170220ZVZR76B88M0000000018";
    var search = "";
</script>
<body>
<header class="com_head">
    <a href="javascript:history.back(-1)" class="back"></a>
    <h1>招商服务</h1>
    <a href="<%= basePath%>ea/productAgent/ea_investmentRules.jspa?" class="head_R mer_headR">规则详情</a>
</header>
<div class="wrap_page mer_wrap">
    <div class="search_wrap">
        <div class="search_box">
            <input type="text" class="search" >
            <span class="search_overly"><i class="com_search"></i>搜索</span>
        </div>
        <div class="filtrate_wrap clearfix">
            <s:iterator value="#request.list" var="entity">
                <s:if test="#entity[0]=='p20170220ZVZR76B88M0000000018'">
                    <a href="javascript:;" class="filtrate_nav mertab_cur" id="<s:property value='#entity[0]'/>"><s:property value="#entity[1]"/><br>产品</a>
                </s:if>
                <s:else>
                    <a href="javascript:;" class="filtrate_nav" id="<s:property value='#entity[0]'/>"><s:property value="#entity[1]"/><br>产品</a>
                </s:else>
            </s:iterator>
        </div>
    </div>
    <div class="mer_list">
    </div>
</div>


<script src="<%=basePath%>js/WFJClient/zepto.min.js"></script>
<script>
    Zepto(".mer_list").swipeUp(function(){
        $(".search_box").slideUp(100);
        $(".list_wrap").animate({
            "padding-top":"1.6rem"
        },100);

    })
    Zepto(".mer_list").swipeDown(function(){
        $(".search_box").slideDown(100);
        $(".list_wrap").animate({
            "padding-top":"4.2rem"
        },100);

    })
</script>
</body>
</html>
