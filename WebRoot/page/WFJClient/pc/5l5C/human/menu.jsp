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
    <title>菜单管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/menu.css">
        <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
        <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/menu/menu.js"></script>
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
            菜单管理
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">

    <section class="sec-nav sec-hide">
        <ul class="clearfix" >
            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加</p>
            </li>
            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>
            <li class="clearfix">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>
        </ul>
    </section>

    <section class="sec-list">
        <div id="menuTree" style="width: 100%;z-index:99;height:100%"></div>
    </section>
    <section class="sec-save" style="display:none">
        <div class="div-bottom"  onclick="save()" >保存</div>
    </section>
    <!--表单提示-->
    <div class="div-tingyong">
        <div class="box">
            <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
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
    const type = "${param.type}";
    const empowerId = "${param.empowerId}";
    const companyId = "${param.companyId}";
    const iframe = "${param.iframe}";
    if ("iframe" === iframe){
        $(".body header").hide();
    }
</script>
</body>
</html>
