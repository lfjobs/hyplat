<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer_manger.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <script src="<%=basePath%>js/WFJClient/ProductAgent/investmentPro.js"></script>
    <title>招商发布</title>
</head>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var t;
    var flag = '00';
    var companyId = '${companyId}';
</script>
<body>
    <header class="com_head">
        <a href="<%=basePath%>ea/vipcenter/ea_vipDemand.jspa?" class="back"></a>
        <h1>招商发布</h1>
    </header>
    <div class="wrap_page">
       <div class="tab_wrap clearfix">
           <div class="tab_box tab_cur">
               <a href="javascript:;" id="00">招商中</a>
           </div>
           <div class="tab_box">
               <a href="javascript:;" id="01">招商结束</a>
           </div>
       </div>
       <div class="tab_content">
       </div>
       <div class="tab_content end_wrap">
       </div>
    </div>
    <a href="<%=basePath%>ea/productAgent/ea_investmentProducts.jspa?companyId=${companyId}" class="issue_btn"><i></i>招商产品发布</a>

</body>
</html>