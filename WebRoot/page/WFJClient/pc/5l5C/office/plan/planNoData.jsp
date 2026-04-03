<!DOCTYPE html>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");
    String tenantFlag = (String)session.getAttribute("tenantFlag");
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>项目计划单添加</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBillAdd.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js" type="text/javascript"
            charset="utf-8"></script>
</head>
<style>
    header {
        background-color: #f74c32;
    }
    .content >p {
        height: 2rem;
        line-height: 2rem;
        font-size: .6rem;
        color: #666;
        float: left;
        text-align: center;
        width: 100%;
    }
</style>
<body class="hy">
<%--项目名称/分类显示--%>
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
            </a>
        </li>
        <li>
            无数据
        </li>
    </ul>
</header>
<div class="content">
    <p>
        未查询到该条码相关数据，请联系公司负责人在 办公室>规划管理>项目资产>项目物品>物品管理 中进行添加
    </p>
</div>
</body>
</html>
