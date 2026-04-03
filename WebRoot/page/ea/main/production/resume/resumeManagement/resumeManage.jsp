<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/resumem.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/resumeManage.js" type="text/javascript" charset="utf-8"></script>

    <title>简历收件箱</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};
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
            简历管理
        </li>
        <li class="shaix">
            筛选

        </li>
    </ul>
</header>
<div class="content">
    <ul class="rurl">
      <c:forEach items="${pageForm.list}" var="l" varStatus="v">
    <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
        <li class="last1 clearfix" id="${l[10]}" tpId-data="${l[0]}">
    </c:if>
    <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
        <li class="clearfix" id="${l[10]}" tpId-data="${l[0]}">

    </c:if>

            <section class="sec-left">
                <h3> ${l[1]}
                    <c:if test="${l[11] eq '00'}"><img class="img-bq" src="<%=basePath%>images/resume/ds_17.png"/></c:if>
                    <c:if test="${l[11] eq '01'}"><img class="img-bq" src="<%=basePath%>images/resume/ds_18.png"/></c:if>

                </h3>
                <div class="div-01 clearfix">
                    <p>
                        ${l[2]}
                    </p>
                    <span></span>
                    <p>
                            ${l[9]}岁
                    </p>
                    <span></span>
                    <p>
                            工作${l[4]}年
                    </p>
                    <span></span>
                    <p>
                            ${l[5]}
                    </p>
                    <%--<span></span>--%>
                    <%--<p>--%>

                    <%--</p>--%>
                </div>
                <p class="p-zw">应聘职位： ${l[3]}</p>
                <p class="p-zw">投递日期： ${l[6]}</p>
            </section>
            <section class="sec-right">
                <img class="img-tx" src="<%=basePath%>${l[8]}"  onerror="this.src='<%=basePath%>images/ea/driving/elkc/head.png'"/>

                <c:choose>
                    <c:when test="${l[7] eq '00'}">
                        <img class="img-bq" src="<%=basePath%>images/resume/img_37.png"/>
                    </c:when>
                    <c:when test="${l[7] eq '01'}">
                        <img class="img-bq" src="<%=basePath%>images/resume/img_38.png"/>
                    </c:when>
                    <c:when test="${l[7] eq '04'}">
                        <img class="img-bq" src="<%=basePath%>images/resume/img_36.png"/>
                    </c:when>
                    <c:when test="${l[7] eq '03'}">
                        <img class="img-bq" src="<%=basePath%>images/resume/img_48.png"/>
                    </c:when>
                    <c:when test="${l[7] eq '05'}">
                        <img class="img-bq" src="<%=basePath%>images/resume/img_49.png"/>
                    </c:when>
                </c:choose>

            </section>
        </li>
      </c:forEach>
        <%--<li class="clearfix">--%>
            <%--<section class="sec-left">--%>
                <%--<h3>顾峰霖</h3>--%>
                <%--<div class="div-01 clearfix">--%>
                    <%--<p>--%>
                        <%--男--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--25岁--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--工作2年--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--大专--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--6千-8千--%>
                    <%--</p>--%>
                <%--</div>--%>
                <%--<p class="p-zw">应聘职位：平面设计</p>--%>
            <%--</section>--%>
            <%--<section class="sec-right">--%>
                <%--<img class="img-tx" src="<%=basePath%>images/resume/img_33.png"/>--%>
                <%--<img class="img-bq" src="<%=basePath%>images/resume/img_36.png"/>--%>
            <%--</section>--%>
        <%--</li>--%>


    </ul>
    </section>
</div>

</body>

</html>
