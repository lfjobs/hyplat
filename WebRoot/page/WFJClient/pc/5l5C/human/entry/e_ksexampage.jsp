<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_ksexampage.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>



    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";


    var pageNumber = ${pageForm.pageNumber};
    $(function () {
        $(".res_bot").css("height",$(window).height()-60).css("overflow","auto");
        $(".sl-div").css("height",$(window).height()*0.6);

    })

</script>
</head>
<body>
<div class="res_top">
    <ul>
        <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
        <li>查阅</li>
        <li></li>
        <div class="clearfix"></div>
    </ul>
</div>
    <div class="res_bot">

      <c:forEach items="${pageForm.list}"  var="itemt" varStatus="status">
        <div class="basics">
            <div class="basics_mil basics_mil2">
               <div class="head-div">
                  <span>  <c:choose>
                       <c:when test='${itemt[2]=="00"}'>单选</c:when>
                       <c:when test='${itemt[2]=="01"}'>多选</c:when>
                       <c:when test='${itemt[2]=="02"}'>判断</c:when>
                       <c:when test='${itemt[2]=="03"}'>简答</c:when>

                   </c:choose></span>(${itemt[5]}分)${pageForm.pageNumber}.${itemt[6]}?</div>
            </div>
            <c:if test="${itemt[7] ne ''&&itemt[7] ne null}">
            <div class="basics_mil basics_mil2 pic">
              <img src="<%=basePath%>${itemt[7]}">

            </div>
            </c:if>


            <c:if test="${not empty mapvalue[itemt[1]]}">
            <c:if test="${mapvalue[itemt[1]].size() > 0}">

            <c:forEach  items="${mapvalue[itemt[1]]}" var="item" varStatus="v">
            <div class="basics_mil basics_mil2 news">
                <div><div class="letter">${item.codeName}</div></div>
                <span>${item.codeValue}</span>

            </div>

            </c:forEach>

            </c:if>
            </c:if>


            <div class="basics_mil basics_mil2 answer">
                <p>正确答案：&nbsp;${itemt[8]}</p>


            </div>

        </div>

      </c:forEach>



</body>
</html>