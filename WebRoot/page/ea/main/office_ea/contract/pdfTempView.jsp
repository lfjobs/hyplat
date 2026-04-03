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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/pdfh5/css/pdfh5.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/pdfView.css"/>
<script src="<%=basePath %>js/pdfh5/js/pdf.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>js/pdfh5/js/pdf.worker.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>js/pdfh5/js/pdfh5.js" type="text/javascript" charset="utf-8"></script>

<head>
    <title>文件详情</title>



</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >


            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            ${param.title}
        </li>

        <li>
&nbsp;
        </li>

    </ul>
</header>
<div id="demo"></div>

<script type="text/javascript">
    var pdfpath = "${param.pdfpath}";
    var basePath = "<%=basePath %>";
    var pdfh5 = new Pdfh5('#demo', {
        pdfurl: basePath+pdfpath
    });



</script>
</body>
</html>
