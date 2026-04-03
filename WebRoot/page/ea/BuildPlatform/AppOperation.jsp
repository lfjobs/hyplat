<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/zy_sys.css">
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <title>应用操作</title>
</head>
<script type="text/javascript">
var companyId='${companyId}';
</script>
<body>
    <header class="com_head">
       <a href="<%=basePath %>mobile/office/mobileoffice_resourceSystem.jspa?" class="back"></a>
        <h1>应用操作</h1>
    </header>
    <div class="wrap_page">
        <a href="javascript:;" class="operation_box clearfix">
            <div class="shortcut_ico"></div>
            <div class="operation_text" onclick="window.location.href='<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa?'">
                <span>快捷应用</span>
                <span>人事、办公室、财务、生产、营销</span>
            </div>
        </a>
        <a href="javascript:;" class="operation_box clearfix">
            <div class="maintain_ico"></div>
            <div class="operation_text">
                <span>维护应用</span>
                <span>设计、立项、生产、验收、成果</span>
            </div>
        </a>
    </div>
</body>
</html>