<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");

%>
<html>
<head>
    <title>盘库商品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/addInventory.css">
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/iScroll.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/Mdate.js" type="text/javascript" charset="utf-8"></script>--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/Mdate/needcss/Mdate.css">
    <style>
        .content {
            background: url(<%=basePath %>images/scMobile/payBudget/inventory/bg_01.png) no-repeat left top;
            background-size: 100%
        }
    </style>
    <script>
        var basePath = "<%=basePath %>";
    </script>
    <%--下拉控制js文件--%>
    <script type="text/javascript" src="<%=basePath%>js/scMobile/payBudget/inventory/addInventory.js"></script>
</head>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/inventory/register_return.png"/>
        </li>
        <li>
            盘库商品
        </li>
        <li>
            打印
        </li>
    </ul>
</header>
<div class="content">
    <form >
        <section class="con_co">
                <ul>
                    <li class="clearfix">
                        <p class="noData">
                            未查询到产品信息，请添加产品后在尝试
                        </p>
                    </li>
                </ul>
        </section>
    </form>
</div>

</body>
<script>
    //后退
    function toBack() {
        window.history.back();
    }
</script>

</html>
