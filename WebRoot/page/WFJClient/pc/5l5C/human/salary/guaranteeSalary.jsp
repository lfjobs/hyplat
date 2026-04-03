<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>基本保障工资</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/salary/guaranteeSalary.css">
        <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/salary/guaranteeSalary.js"></script>

</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);  return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            基本保障
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content" >
    <section class="sec-list" >
        <div class="div-pc list-data" id="pcList">
            <div class="sec-data-title">
                <ul>
                    <li>时间</li>
                    <li>级别序号</li>
                    <li>级别编码</li>
                    <li>基本工资</li>
                    <li>职能工资</li>
                    <li>职责工资</li>
                    <li>竞职金</li>
                    <li>保密工资</li>
                    <li>调平工资</li>
                    <li>合计</li>
                </ul>
            </div>
            <div class="sec-data-list"  style="overflow-y:auto">
            </div>
        </div>
        <div class="div-iphone list-data layui-collapse " style="overflow-y:auto" >
        </div>
    </section>


</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";

</script>
</body>
</html>
