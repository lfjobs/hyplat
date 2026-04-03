<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pm_base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/costBudgetItemManage.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/ea/projectBudgetBill/costBudgetItemManage.js"></script>
    <title>招标管理</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";

    </script>
    <head>

        <title>招标管理</title>
    </head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
            </a>
        </li>
        <li>
            招标管理
        </li>
        <li>
            <!-- <img src="img/img_02.png" alt=""> -->
        </li>
    </ul>
</header>
<div class="content">
    <menu class="clearfix">
        <li class="li-add">
            添加
        </li>
        <li>
            删除
        </li>
        <li class="li-edit">
            修改
        </li>
        <li class="li-chaxun">
            查询
        </li>
        <li>
            打印
        </li>
    </menu>
    <div class="container">
        <ul class="ul-header clearfix">
            <li>
                物品条码
            </li>
            <li>
                物品名称
            </li>
            <li>
                单位
            </li>
            <li>
                规格
            </li>
            <li>
                物品类别
            </li>
        </ul>
    </div>
</div>

<%--物品查询开始--%>
<div class="div-chaxun">
    <div class="div-box">
        <p class="p-top">
            请输入查询信息
        </p>
        <p class="p-inp clearfix">
            <label for="">物品名称</label>
            <input type="text" id="goodsName" name="goodsName">
        </p>
        <p class="p-inp clearfix">
            <label for="">物品类别</label>
            <input type="text" id="type" name="type">
        </p>
        <p class="p-inp clearfix">
            <label for="">物品条码</label>
            <input type="text" id="barCode" name="barCode">
        </p>
        <p class="p-bottom">
            查询
        </p>
    </div>
</div>
<%--物品查询结束--%>
</body>

</html>