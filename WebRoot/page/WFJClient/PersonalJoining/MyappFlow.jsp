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
<html lang="en">
<head lang="en">
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>过年购物赚钱步骤</title>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/WFJClient/style_myapp.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>

<body>
<div class="content_sign">
    <header>
        <a href="javascript:history.go(-1)"><img src="<%=basePath%>images/WFJClient/PersonalJoining/left_jtt.png" alt=""></a>
    </header>
    <img src="<%=basePath%>images/WFJClient/PersonalJoining/bg.png" alt="" class="bg">
    <a href="#;"><img src="<%=basePath%>images/WFJClient/PersonalJoining/join.png" alt="" class="btn"></a>
</div>

<script>
    $(document).ready(function(){

    });
</script>

</body>
</html>