<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/deptPerson.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/menu/deptPerson.js" type="text/javascript" charset="utf-8"></script>
    <title>添加角色成员</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
    <style>

    </style>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            人员信息
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content" >
    <div class="dtd-oa-search-bar">
        <div class="dtd-oa-search-bar-icon-wrapper">
            <i class="layui-icon" >&#xe615;</i>
        </div>
        <input type="text" class="dtd-oa-search-bar-input" placeholder="搜索"  name="staffName" id="staffName" autocomplete=“off”/>
        <div class="dtd-oa-search-bar-icon-wrapper close-data" style="left:90%">
            <i class="layui-icon" >&#x1006;</i>
        </div>
    </div>
    <div class="breadcrumbWrapper" style="display:none">
        <nav class="dtd-breadcrumb res-picker-bread-crumb">
            <ol>
                <li>
                    <span class="dtd-breadcrumb-link">通讯录</span>
                    <span class="dtd-breadcrumb-separator">></span>
                </li>
                <li>
                    <span class="dtd-breadcrumb-link">测试</span>
                    <span class="dtd-breadcrumb-separator">></span>
                </li>
            </ol>
        </nav>
    </div>
    <div class="select-all-wrapper" style="display:none">
        <div class="itemWrapper ">
            <div class="leftItemWrapper">
                <input type="checkbox" name="" title="全选" lay-skin="primary" class="dtd-checkbox-check-icon">
            </div>
            <div class="itemContent ">
                <div class="itemTitleWrap" >
                    <div class="itemTitle ">全选</div>
                </div>
            </div>

        </div>
    </div>
    <div class="dept-person-body" style="overflow-y:auto">
        <ul id="list" class="data-list"></ul>
    </div>
    <div class="div-bottom">
        <div class="person-data" >
            <div style="padding:10px">
                <span>已选择：</span>
                <span class="person-num"></span>
                <i class="layui-icon" style="float:right;">&#xe619;</i>
            </div>
        </div>
        <button class="layui-btn layui-btn-radius person-submit" style="background-color:#ef8717;font-size:16px;padding:0 30px" onclick="addStaffRole()">
            确定&nbsp;&nbsp;
            <span class="div-person-data">1/100</span>
        </button>
    </div>
</div>
<!--已选数据-->
<section id="dataLayer" class="express-data-box">
    <header>
        <h3 style="font-weight:bold">已选择</h3>
        <i class="layui-icon close-mask"  onclick="closeMask()">&#x1006;</i>
        <span class="submit-mask">确定</span>
    </header>
    <article id="dataBox"  style="overflow-y:auto">
        <ul id="selectedDataList" class=" div-mask-list"></ul>
    </article>
</section>

<!--遮罩层-->
<div id="mask" class="mask"></div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const type = "${param.type}";
    const id = "${param.id}";
</script>
</body>

</html>