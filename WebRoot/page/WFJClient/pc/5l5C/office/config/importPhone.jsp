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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/importPhone.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/importPhone.js" type="module"></script>

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
            导入电话
        </li>
        <li class="screen" style="font-size: 15px;">
            批量设置
        </li>
    </ul>
</header>
<div class="content" style="height: 884px;">
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" placeholder="搜索">
    </div>
    <section class="sec-list" style=" overflow: hidden auto;">
        <div class="div-sec-data">
            <div class="data-title1">
            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden;height: 700px;">
            </div>
        </div>
    </section>
    <div class="but">
        <div class="btn-submit">开始拨打</div>
    </div>
    <div id="overlay"
         style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5);"></div>

    <!-- 自定义弹框 -->
    <div id="customModal"
         style="height: 110px; width: 64%; display: none; position: fixed; top: 35%; left: 50%; transform: translate(-50%, -50%); background: #fcf8f8; padding: 20px; box-sizing: border-box;">
        <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100%;">
            <p id="modalMessage" style="text-align: center; margin: 0;">是否是意向客户？</p>
            <div style="margin-top: auto; display: flex; gap: 40px;">
                <button id="confirmYes" style="border: none;">是</button>
                <button id="confirmNo" style="border: none;">否</button>
            </div>
        </div>
        <div id="isSend"
             style="height: 110px; width: 64%; display: none; position: fixed; top: 35%; left: 50%; transform: translate(-50%, -50%); background: #fcf8f8; padding: 20px; box-sizing: border-box;">
            <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100%;">
                <p id="sendMessage" style="text-align: center; margin: 0;">是否发送短信？</p>
                <div style="margin-top: auto; display: flex; gap: 40px;">
                    <button id="sendYes" style="border: none;">是</button>
                    <button id="sendNo" style="border: none;">否</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 自定义弹框 -->

    </div>
    <script>
    </script>
</body>
</html>