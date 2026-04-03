<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/collectResume.css">
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/collectResume.js" type="text/javascript"
            charset="utf-8"></script>


    <title>简历收藏</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var sccId = "${param.sccId}";
        var back = "${param.back}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li onclick="backs()">
            <img src="<%=basePath%>css/ea/production/back.png" >
        </li>
        <li>
            简历收藏
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="div-qxsc">
    <div class="box">
        <p>取消收藏简历</p>
        <p>取消</p>
    </div>
</div>
<div class="content">
    <ul class="rurl">

        <c:forEach items="${pageForm.list}" var="l" varStatus="v">
            <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                <li class="last1 clearfix" id="${l[0]}" >
            </c:if>
            <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                <li class="clearfix" id="${l[0]}" >

            </c:if>
            <img class="img-del" src="<%=basePath%>images/supermarket/ico-del.png" alt="">
            <section class="sec-left">
                <img src="<%=basePath%>${l[5]}" onerror="this.src='<%=basePath%>images/ea/driving/elkc/head.png'"/>

                <h3>${l[1]}</h3>
                <p class="p-zw">${l[7]}</p>
                <div class="div-01 clearfix">
                    <p>
                            ${l[2]}
                    </p>
                    <span></span>
                    <p>
                            ${l[6]}岁
                    </p>
                    <span></span>
                    <p>
                            工作${l[3]}年
                    </p>
                    <span></span>
                    <p>
                            ${l[9]}
                    </p>
                </div>
            </section>
            <section class="sec-right">
                <p>
                        ${l[8]}
                </p>
            </section>
            </li>
        </c:forEach>


    </ul>
    </section>
</div>

<div class="div-del">
    <div class="box">
        <p class="titlep">确认取消收藏吗？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-q">确定</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(".div-qxsc .box p").click(function () {
        $(this).parents(".div-qxsc").hide();
    })
</script>

</html>
