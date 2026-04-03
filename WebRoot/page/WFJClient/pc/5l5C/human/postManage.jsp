<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    CAccount ca = (CAccount) session.getAttribute("account");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>岗位管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/postManage.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/postManage.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectDataForPage.js"></script>
</head>
<body class="body">
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="returnPage()" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            岗位管理
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="sec-nav sec-hide div-list">
        <ul class="clearfix" >
            <li class="clearfix">
                <p class="add">添加</p>
            </li>
            <li class="clearfix">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p class="del">删除</p>
            </li>
            <li class="clearfix">
                <p class="postPerson">岗位人员</p>
            </li>

        </ul>
    </section>
    <div class="spd-content">
        <div class="dtd-oa-search-bar" style="margin-top:-10px">
            <div class="dtd-oa-search-bar-icon-wrapper query-data  ">
                <i class="layui-icon">&#xe615;</i>
            </div>
            <input type="text" class="dtd-oa-search-bar-input" placeholder="岗位名称/岗位编号"  name="search" id="search" autocomplete="off">
            <div class="dtd-oa-search-bar-icon-wrapper close-data" onclick="clearQueryName()" style="right: 75px;">
                <i class="layui-icon">&#x1006;</i>
            </div>
            <button class="layui-btn layui-btn-primary layui-btn-sm " onclick="getDataByName()" style="margin-left:10px;margin-top:-4px">搜索</button>
        </div>
    </div>
    <section class="sec-list layui-form div-list"  >
        <div class="div-sec-data" >
            <div class="data-title">
                <ul>
                    <li>选择</li>
                    <li>序号</li>
                    <li>部门名称</li>
                    <li>岗位名称</li>
                    <li>岗位编号</li>
                    <li>编员人数</li>
                    <li>专岗人数</li>
                    <li>兼岗人数</li>
                    <li>任职要求</li>
                    <li>备注</li>
                </ul>
            </div>
            <div class="data-list div-data"  style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>

    </section>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyId = "<%=ca.getCompanyID()%>";

</script>
</body>
</html>
