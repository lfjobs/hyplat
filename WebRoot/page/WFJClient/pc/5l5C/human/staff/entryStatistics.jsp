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
    <title>入职管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/entryStatistics.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/echarts.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/entryStatistics.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            入职管理
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <div class="div-echarts" style="overflow-y: auto;height:100%">
        <div class="div-entry-data">
            <div class="div-person">
                <div><label class="person-info">入职人数</label></div>
                <div class="div-person-data"><label class="person-data entry-person-num">0</label><label class="person-unit">人</label></div>
            </div>
            <div class="div-person">
                <div><label class="person-info">专岗人数</label></div>
                <div class="div-person-data"><label class="person-data part-person-num">0</label><label class="person-unit">人</label></div>
            </div>
            <div class="div-person">
                <div><label class="person-info">兼岗人数</label></div>
                <div class="div-person-data"><label class="person-data full-person-num">0</label><label class="person-unit">人</label></div>
            </div>
        </div>
        <div class="div-education-data div-data-echarts">
            <div class="div-title-echarts">
                <label>入职学历分布</label>
            </div>
            <div id="educationData" class="" style="width:100%;height:200px"></div>

        </div>
        <div class="div-contract-data div-data-echarts">
            <div class="div-title-echarts">
                <label>入职合同类型</label>
            </div>
            <div id="contractData" class="" style="width:100%;height:300px"></div>

        </div>
        <div class="div-contract-data div-data-echarts">
            <div class="div-title-echarts">
                <label>入职人数</label>
            </div>
            <div id="entryPersonData" class="" style="width:100%;height:300px"></div>

        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const type = "${param.type}";
    if (type == "iframe"){
        $("header").hide();
    }
    entryStatistic.initData();


</script>
</body>
</html>
