<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <title>500</title>
    <style type="text/css">
        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }

        .toolbar {
            display: flex;
            list-style: none;
            background-color: #f74c30;
            padding: 16px;
        }

        .navi-icon img {
            width: 24px;
            height: 24px;
            object-fit: contain;
            margin: auto;
        }

        .title {
            font-size: 1.25rem;
            color: white;
            margin: auto;
        }

        .error {
            display: block;
            color: gray;
            text-align: center;
            height: 60%;
            justify-content: center;
        }

        .error .error-code {
            font-size: 3rem;
        }
        .error .tips {
            font-size: 1rem;
        }
    </style>
</head>
<body>

<header>
    <ul class="toolbar">
        <li class="navi-icon" onclick="back();">
            <img src="<%=basePath%>/images/home/img-1.png">
        </li>
        <li class="title">500</li>
    </ul>
</header>

<div class="error">
    <div class="error-code">
        500
    </div>
    <div class="tips">
        哎呀，出错啦！
    </div>
</div>
</body>
<script>
    function back() {
        window.history.back()
    }
</script>
</html>
