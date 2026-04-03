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
    <script src="<%=basePath%>js/WFJClient/ProductAgent/investProducts.js"></script>
    <title>选择招商产品发布</title>
</head>
<script type="text/javascript">
    var pagenumber = 0;
    var pagecount = 0;
    var t ;
    var companyId = '${companyId}';
    var flag = '';
    var basePath = '<%=basePath%>';
    var ppId = '';
</script>
<body>
    <header class="com_head">
        <a href="<%=basePath%>ea/productAgent/ea_investmentPro.jspa?companyId=${companyId}&flag=00" class="back"></a>
        <h1>选择招商产品发布</h1>
    </header>
    <div class="wrap_page">
        <div class="pro_wrap clearfix">
        </div>
    </div>
    <a href="javascript:;" class="fix_btn save_btn"><i></i>下一步</a>
</body>

</html>
