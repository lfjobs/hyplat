<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/10 0010
  Time: 11:26
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

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=path%>/js/ea/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/coach.css">
    <script src="<%=path%>/js/ea/elkc/jquery.min.js"></script>
    <title>课程管理</title>
</head>

<body>
<header class="com_head">
    <%--<a onclick="javascript: window.history.go(-1);return false;" class="back"></a>--%>
    <h1>课程管理</h1>
</header>
<div class="wrap_page">
    <div class="coach_class_wrap">
        <div class="cc_wrap">
    <c:forEach items="${listTime}" var="m">
        <c:forEach items="${map}" var="beans" varStatus="var">
            <c:if test="${beans.key eq m}">
            <div class="cc_date"> <fmt:formatDate value="${beans.key}" dateStyle="full" /></div>
            <c:if test="${empty beans.value}">
                <div class="cc_time_wrap clearfix">

                    <p class style="line-height: 2rem;text-align: center;">教练休班或驾校放假</p>

                </div>
            </c:if>
            <div class="cc_time_wrap clearfix">
                <c:forEach items="${beans.value}" var="str" varStatus="var">
                <c:if test="${str[0]=='1'}">
                <div class="cc_time_box">
                    <a href="<%=basePath%>ea/coachreserv/ea_eservationetails.jspa?staffId=${param.staffId}&companyId=${param.companyId}&odtId=${str[4]}" class="cc_time state_yy">
                        <span><fmt:formatDate value="${str[1]}" pattern="HH:mm " /> -<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        <span>已预约</span>
                    </a>
                </div>
                </c:if>
                <c:if test="${str[0]=='3'}">
                <div class="cc_time_box">
                    <a href="###" class="cc_time state_end">
                        <span><fmt:formatDate value="${str[1]}" pattern="HH:mm " /> -<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        <span>教练员请假</span>
                    </a>
                </div>
                </c:if>
                <c:if test="${str[0]=='2'}">
                <div class="cc_time_box">
                    <a href="###" class="cc_time">
                        <span><fmt:formatDate value="${str[1]}" pattern="HH:mm " /> -<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        <span>未预约</span>
                    </a>
                </div>
                </c:if>

        </c:forEach>
            </div>
        </div>
        </c:if>
        </c:forEach>
        </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>

</html>

