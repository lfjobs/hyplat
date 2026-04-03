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
    <title>未注册粉丝-短信模块</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/visitorMessagesHome1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesHome.es1.js" type="module"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            未注册粉丝-短信模块
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" placeholder="通过模板名称进行搜索">
    </div>
    <section class="sec-nav sec-hide ">
        <ul class="clearfix center-items">
            <li>
                <div class="item item1">
                    <i class="layui-icon" style="font-size: 40px">&#xe60a;</i>
                    <p class="massSending">短信模版</p>
                </div>
            </li>
            <li>
                <div class="item item2">
                    <i class="layui-icon" style="font-size: 32px">&#xe611;</i>
                    <p class="massText">群发短信</p>
                </div>
            </li>
        </ul>
        <ul class="clearfix operate">
            <li class="clearfix">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p class="del">删除</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" style=" overflow: hidden auto; height: 700px;">
        <div class="div-sec-data">
            <div class="data-title1">

            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>
</div>
<script>

</script>
</body>
</html>