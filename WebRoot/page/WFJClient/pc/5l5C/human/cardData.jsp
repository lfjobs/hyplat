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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/cardData.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/menu/cardData.js"></script>
    <title>商家信息</title>
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
    <div class="card-body" style="overflow-y:auto">
        <div class="div-card">
        </div>
    </div>
    <div class="div-button">

        <button class="layui-btn layui-btn-radius person-add" style="background-color:#FF5722;font-size:16px;padding:0 30px" onclick="addData()">
            <i class="layui-icon" style="font-size: 20px; color: #fff;">&#xe61f;</i>
            添加成员&nbsp;&nbsp;
        </button>
    </div>

</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const type = "${param.type}";
    const roleId = "${param.roleId}";
</script>
</body>

</html>