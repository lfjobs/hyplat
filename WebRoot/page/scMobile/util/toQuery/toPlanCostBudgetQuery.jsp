<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
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
<html>
<head>
    <meta charset="utf-8" />
    <title>查询</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/util/toQuery/query.css">
    <script src="<%=basePath %>js/scMobile/util/toQuery/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/util/toQuery/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var jumpType = "${jumpType}";
    var menuId = "${menuId}";
    var tenantFlag = "${tenantFlag}";
</script>
<body>
<ul class="header">
    <li onclick="toBack();"><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt=""></li>
    <li>查询</li>
</ul>
<div class="content-search">
    <section class="sec-search clearfix">
        <input type="text" placeholder="搜索" name="search" id="ttsw_search" value="" />
        <img onclick="toSearch();" src="<%=basePath %>images/scMobile/util/toQuery/search.png"/>
        <p onclick="toCancel();">取消</p>
    </section>
    <section class="sec-content">
        <p>搜索指定内容</p>
        <ul class="clearfix">
            ${innerHtml}
        </ul>
    </section>
    <input type="hidden" id="ttsw_li_check" value="0"/><%--选中那个li--%>
</div>
</body>
<%--跳转js文件--%>
<script type="text/javascript" src="<%=basePath%>js/scMobile/util/toQuery/toPlanCostBudgetQuery.js"></script>
<script>
    //后退
    function toBack() {
        window.history.back(-1);
//        window.location.href = basePath + "ea/scBudget/ea_toPayBudgetList.jspa";
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