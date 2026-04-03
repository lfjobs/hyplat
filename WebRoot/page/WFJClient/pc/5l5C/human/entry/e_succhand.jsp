<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>交卷成功</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_succhand.css"/>

<script>
    var basePath = "<%=basePath%>";


</script>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <%--<a onclick="javascript: window.history.go(-1);return false;"  target="_self">--%>
            <%--<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />--%>
            <%--</a>--%>
        </li>
        <li>
           <c:if test="${param.py eq 'py'}">上传成绩成功</c:if> <c:if test="${param.py ne 'py'}">交卷成功</c:if>
        </li>
    </ul>
</header>
<div class="main-content">
    <p class="p-img"><img src="<%=basePath%>images/ea/office/contract/selectp/jjsuc.png"></p>
    <p class="p-title"><span class="span-name"><c:if test="${param.py eq 'py'}">上传成绩成功</c:if> <c:if test="${param.py ne 'py'}">交卷成功</c:if></span></p>
    <c:if test='${examRelate.isHg eq "00" ||examRelate.isHg eq "01"}'>
        <p class="p-title">总得分：<span class="span-name">${examRelate.tscore}&nbsp;${examRelate.isHg eq '00'?'合格':'不合格'}</span></p>

    </c:if>

    <a  onclick="javascript: window.history.go(-1);return false;"  target="_self"><span class="close">关闭</span></a>

</div>
</body>
</html>