<%--
  Created by IntelliJ IDEA.
  User: ljc
  Date: 2017/5/4 0004
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_manger.css">
    <title>遮罩层</title>
<body>
<div class="overlay">
    <div class="loading">
        <img src="<%=basePath%>images\ea\lottery\loading.gif" alt="">
        <span>正在发布，请稍候！</span>
    </div>
</div>
</body>
</html>
