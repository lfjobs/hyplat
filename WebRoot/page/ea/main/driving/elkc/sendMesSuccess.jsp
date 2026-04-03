<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/1 0001
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>发送成功</title>
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/elkc/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/elkc/complaint_style.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>

<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="cancel">
                <img src="<%=basePath%>/images/ea/elkc/ico-cancel.png" class="gou">
                <p>消息发送成功</p>
            </div>
        </div>
    </div>
</div>


</body>
</html>