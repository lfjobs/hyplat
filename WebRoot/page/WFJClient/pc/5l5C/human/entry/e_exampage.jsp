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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_exampage.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_exampage.js"
            type="text/javascript"></script>



    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";

	var qreID = "${questionsExam.qreID}";
    var qrID = "${questionsExam.qrID}";

    var answer = "${examAnswer.answer}";
    var quesType = "${questionsExam.quesType}";

    var  tqeID = "${totalQuestionExam.tqeID}";

    var  tqID = "${totalQuestionExam.tqID}";

    var  erId = "${examRelate.erId}";

    var pageNumber = ${pageForm.pageNumber};
    $(function () {
        $(".res_bot").css("height",$(window).height()-10).css("overflow","auto");
        $(".sl-div").css("height",$(window).height()*0.6);

    })

</script>
</head>
<body>

    <div class="res_bot">


        <div class="basics">
            <div class="basics_mil basics_mil2">
               <div class="head-div">
                  <span>  <c:choose>
                       <c:when test='${questionsExam.quesType=="00"}'>单选</c:when>
                       <c:when test='${questionsExam.quesType=="01"}'>多选</c:when>
                       <c:when test='${questionsExam.quesType=="02"}'>判断</c:when>
                       <c:when test='${questionsExam.quesType=="03"}'>简答</c:when>

                   </c:choose></span>(${questionsExam.score}分)${pageForm.pageNumber}.${questionsExam.title}?</div>
            </div>
            <c:if test="${questionsExam.picpath ne ''&&questionsExam.picpath ne null}">
            <div class="basics_mil basics_mil2 pic">
              <img src="<%=basePath%>${questionsExam.picpath}">

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
            <c:if test="${questionsExam.quesType eq '03'}">
                <textarea placeholder="答题框" class="anwser03">${examAnswer.answer}</textarea>

            </c:if>

        </div>

        <c:if test="${pageForm.pageNumber ne pageForm.pageCount }">
            <div class="div-bottom">
                <p class="next">
                    <span>下一题</span>

                </p>

            </div>

        </c:if>

        <div class="main-bottom">
        <div class="bottom">
        <ul>
            <li><div class="jj">交卷</div></li>
            <li><div class="pages"><img src="<%=basePath%>images/ea/office/contract/selectp/fse.png"/><span>${pageForm.pageNumber}/${pageForm.pageCount}</span></div></li>
            <li>
                <c:if test="${isCollect eq '1'}">
                    <div class="sc checks">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/sc.png"/>
                    </div>
                </c:if>
                <c:if test="${isCollect eq '0'}">
                <div class="sc">
                <img src="<%=basePath%>images/ea/office/contract/selectp/wsc.png"/>
                </div>

                </c:if>

            </li>
            </ul>

        </div>
        <div class="sl-div">


            <ul class="sl-ul">
                <%--<li><div class="selectw">1</div><p  class="scd"></p></li>--%>
                <%--<li><div class="selected">2</div><p  class="scd"></p></li>--%>

            </ul>
        </div>
    </div>
    </div>
    <div class="over"></div>
    <!--表单提示-->
    <div class="div-tingyong div-dqd">
        <div class="box">
            <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
            <div class="div-box">
                <p class="titlep"></p>
                <div class="clearfix">
                    <p class="left close-tingyong">取消</p>
                    <p class="right close-confirm">确定</p>
                </div>
            </div>
        </div>
    </div>

</body>
</html>