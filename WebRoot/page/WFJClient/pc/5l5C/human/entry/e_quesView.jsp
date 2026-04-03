<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">

    <link href="<%=basePath%>css/ea/production/common2.css" rel="stylesheet"
          type="text/css" />
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_quesView.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>


    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	
	$(function () {
        $(".res_bot").css("height",$(window).height()-10).css("overflow","auto");
    })

</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
            <li>题目详情</li>
            <li>

            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">


        <div class="basics">
            <div class="basics_mil basics_mil2">
               <div class="head-div">
                  <span>  <c:choose>
                       <c:when test='${questions.quesType=="00"}'>单选</c:when>
                       <c:when test='${questions.quesType=="01"}'>多选</c:when>
                       <c:when test='${questions.quesType=="02"}'>判断</c:when>
                       <c:when test='${questions.quesType=="03"}'>简答</c:when>

                   </c:choose></span>(${questions.score}分)${questions.seq}.${questions.title}?</div>
            </div>
            <c:if test="${questions.picpath ne ''&&questions.picpath ne null}">
            <div class="basics_mil basics_mil2 pic">
              <img src="<%=basePath%>${questions.picpath}">

            </div>
            </c:if>
            <c:if test="${not empty valueList}">
            <c:if test="${valueList.size() > 0}">

            <c:forEach  items="${valueList}" var="item" varStatus="v">
            <div class="basics_mil basics_mil2 news">
                <div><div class="letter">${item.codeName}</div></div>
                <span>${item.codeValue}</span>

            </div>

            </c:forEach>

            </c:if>
            </c:if>
            <div class="basics_mil basics_mil2 answer">
                <p>正确答案：&nbsp;${questions.correctAnswer}</p>


            </div>

        </div>


    </div>

</body>
</html>