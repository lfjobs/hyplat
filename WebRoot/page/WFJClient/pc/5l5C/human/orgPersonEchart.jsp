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
    <title>组织机构图</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/orgPersonEchart.css">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxdiagram.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxdiagram.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/orgPersonEchart.js"></script>
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
           组织机构图
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content" style="width:100%">
    <section class="sec-list">
        <div style="width: 100%;z-index:99;height:100%;overflow:auto">
            <div id="orgTree" style="width: 100%;z-index:99;height:100%" ></div>
        </div>


    </section>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyId = "<%=ca.getCompanyID()%>";
    var companyName = "<%=ca.getCompanyName()%>";
</script>
</body>
</html>
