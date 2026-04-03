<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String companyName = (String) request.getAttribute("companyName");
%>

<%--http://localhost:8080/page/WFJClient/pc/5l5C/office/inventory/depotManageMobile.jsp--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>库房管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/businessType/businessTypeManage.css"/>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/WFJClient/pc/5l5C/office/inventory/depotManageMobile.js"></script>
    <style>
        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyName = "${param.companyName}";
        var pNumber = '${pageNumber}';
        var treeid;
        var treename;
        var tree;
        var date;
        var token = 0;
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            库房管理
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">
            <li class="clearfix ">
                <p class="add_p"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加下级</p>
            </li>
            <li class="clearfix">
                <p class="edit_p"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>
            <li class="clearfix">
                <p class="del_p"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>
            <li class="clearfix">
                <p class="upgrade"><img src="<%=basePath%>images\ea\office\contract/upgrade.png"/>升级</p>
            </li>
        </ul>
    </section>

    <section class="sec-list">
        <div id="businessTree" style="width: 100%;z-index:99;"></div>
    </section>

    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
        <center>
            <div>
                <span style="position: relative; top: 19.8%;"></span>
            </div>
        </center>
    </div>


    <!--表单提示-->
    <div class="div-tingyong">
        <div class="box">
            <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/>
            </p>
            <div class="div-box">
                <p class="titlep"></p>
                <div class="clearfix">
                    <p class="left close-tingyong">取消</p>
                    <p class="right close-confirm">确定</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
</body>
</html>
