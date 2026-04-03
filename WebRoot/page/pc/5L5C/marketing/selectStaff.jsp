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
    <title>员工</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/visitorMessagesHome2.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/selectStaff.js" type="module"></script>

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
        <li class="nameCompany">
        </li>
        <li class="screen" style="font-size: 15px;">
            筛选
        </li>
    </ul>
</header>
<div class="content" style="height: 884px;">
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" placeholder="搜索">
    </div>
    <div class="input-box2">
        <ul style="display: flex; ">
            <li>选择当前所有联系人</li>
            <%--                    <li><img src="<%=basePath%>js/tree/codebase/imgs/iconCheckAll.gif" alt="全选"></li>--%>
            <li class="selectedAll" style="margin-left: auto"><img
                    src="<%=basePath%>js/tree/codebase/imgs/iconUncheckAll.gif" alt="全不选"></li>
        </ul>
    </div>
    <section class="sec-list" style=" overflow: hidden auto;">
        <div class="div-sec-data">
            <div class="data-title1">
            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden;height: 700px;padding-left: 25px;">
            </div>
        </div>
    </section>
    <div class="but">
        <div class="btn-submit">确定</div>
    </div>
</div>
<div id="myModal" class="modal">
    <div class="modal-content">
        <div class="modal-footer">
            <div id="modal-btn-cancel" class="btn-cancel">取消</div>
            <div id="modal-btn-confirm" class="btn-confirm">确定</div>
        </div>
        <div class="screenDiv">
            <div class="screenData" style="overflow-y:auto;overflow-x:hidden;height: 170px;padding-left: 25px;">
            </div>
        </div>
    </div>
</div>
<script>
    let item = localStorage.getItem("nameCompany");
    $(".nameCompany").html(item);
</script>
</body>
</html>