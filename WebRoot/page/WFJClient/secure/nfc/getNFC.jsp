<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/getPatrol.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript"
            charset="utf-8"></script>
    <title>nfc绑定-查看</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png">
            </a>
        </li>
        <li>
            查看详情
        </li>
    </ul>
</header>


<div class="content">
    <div class="div-name">
        <label for="">安全编号</label>
        <input type="text" value="${nfc.ln}" readonly/>
    </div>

    <div class="div-name">
        <label for="">芯片编号</label>
        <input type="text" value="${nfc.sn}" readonly id="title"/>
    </div>
    <div class="div-name">
        <label for="">绑定公司</label>
        <input type="text" value="${nfc.companyName}" readonly/>
    </div>
    <div class="div-name">
        <label for="">绑定人</label>
        <input type="text" value="${nfc.staffName}" readonly/>
    </div>
    <div class="div-name">
        <label for="">设备编号</label>
        <input type="text" value="${nfc.en}" readonly/>
    </div>
    <div class="div-name">
        <label for="">安全巡查地点</label>
        <input class="emergencyType" type="text" value="${nfc.bindLocation}" readonly/>
    </div>
</div>

<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt=""/>
        <p>正在准备文档...</p>
    </div>
</div>

</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var module = "<%=session.getAttribute("module")%>";
    var isRead = "${param.isRead}";
</script>
</html>
