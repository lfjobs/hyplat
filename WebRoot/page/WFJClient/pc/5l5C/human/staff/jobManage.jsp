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
    <title>在职管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link href="//unpkg.com/layui@2.11.5/dist/css/layui.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/jobManage.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="//unpkg.com/layui@2.11.5/dist/layui.js"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/jobManage.js"></script>
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
            在职人员
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="sec-nav sec-hide ">
        <div class="layui-tabs layui-tabs-card" lay-options="{index: 0}" style="float:left">
            <ul class="layui-tabs-header div-tab">
                <li id ="0">全部</li>
                <li id ="1">专岗</li>
                <li id ="2">兼岗</li>
            </ul>
        </div>
        <div class="div-person-num"  style="width:110px;float:left;margin-top:10px" >
            <lable class="div-person-title">入职</lable>人数：<label class="personData">0</label>人
        </div>
    </section>

    <div class="spd-content">
        <div class="dtd-oa-search-bar" style="margin-top:-10px">
            <div class="dtd-oa-search-bar-icon-wrapper query-data  ">
                <i class="layui-icon">&#xe615;</i>
            </div>
            <input type="text" class="dtd-oa-search-bar-input" placeholder="姓名/手机号"  name="queryName" id="queryName" autocomplete="off">
            <div class="dtd-oa-search-bar-icon-wrapper close-data" onclick="clearQueryName()" style="right: 75px;">
                <i class="layui-icon">&#x1006;</i>
            </div>
            <button class="layui-btn layui-btn-primary layui-btn-sm " onclick="getDataByQueryName()" style="margin-left:10px;margin-top:-4px">搜索</button>
        </div>
    </div>
    <section class="sec-list "  >
        <div class="div-sec-data" >
            <div class="data-title">
                <ul>
                    <li>序号</li>
                    <li>姓名</li>
                    <li>性别</li>
                    <li>入职时间</li>
                    <li>电话</li>
                    <li>专兼职</li>
                </ul>
            </div>
            <div class="data-list div-data"  style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>

</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";


</script>
</body>
</html>
