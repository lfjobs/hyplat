<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ page import="hy.ea.bo.CAccount" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    CAccount ca = (CAccount) session.getAttribute("account");
%>

<!DOCTYPE html>
<html><head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/staffRecordDetail.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/staff/staffRecordDetail.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>

</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li class="title-li">
            面试状态
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <div class="dataList sec-list">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="date-p" title="姓名">姓名</div>
                    <div class="date-s" title="身份证">身份证</div>
                    <div class="date-p" title="类型">类型</div>
                    <div class="date-p" title="状态">状态</div>
                </div>
            </li>

        </ul>
    </div>


</div>


</body>
<script type="text/javascript">
    const  basePath = "<%=basePath%>";
    var type = "${param.type}";
    var staffID = "${param.staffID}";
    $("." + type).show();
</script>
</html>
