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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_pyexampage.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_pyexampage.js"
            type="text/javascript"></script>



    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";

    var quesType = "${questionsExam.quesType}";

    var  tqeID = "${totalQuestionExam.tqeID}";

    var  tqID = "${totalQuestionExam.tqID}";

    var  erId = "${examRelate.erId}";
    var  sizt = ${fn:length(pageForm.list)};
    var  isHg = "${param.isHg}";


    var pageNumber = ${pageForm.pageNumber};
    $(function () {
        $(".res_bot").css("height",$(window).height()-120).css("overflow","auto");
        $(".sl-div").css("height",$(window).height()*0.6);

    })

</script>
</head>
<body>
<div class="res_top">
    <ul>
        <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
        <li class="py-li"><span class="checkc" data-value="03">人工批阅</span><span data-value="00">自动批阅</span></li>
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

                   </c:choose></span>(${itemt[7]}分)${pageForm.pageNumber}.${itemt[8]}?</div>
            </div>
            <c:if test="${itemt[9] ne ''&&itemt[9] ne null}">
            <div class="basics_mil basics_mil2 pic">
              <img src="<%=basePath%>${itemt[9]}">

            </div>
            </c:if>


            <c:if test="${not empty mapvalue[itemt[0]]}">
            <c:if test="${mapvalue[itemt[0]].size() > 0}">

            <c:forEach  items="${mapvalue[itemt[0]]}" var="item" varStatus="v">
            <div class="basics_mil basics_mil2 news">
                <div><div class="letter">${item.codeName}</div></div>
                <span>${item.codeValue}</span>

            </div>

            </c:forEach>

            </c:if>
            </c:if>
            <c:if test="${itemt[2] eq '03'}">
                <textarea placeholder="答题框" readonly class="anwser03">${itemt[4] eq 'NOANSWER'?'':itemt[4]}</textarea>

            </c:if>
            <c:if test="${itemt[2] ne '03'}">
            <div class="basics_mil basics_mil2 answer">
                <p>考生答案：&nbsp;${itemt[4]}&nbsp;&nbsp;



                     <c:if test="${itemt[11] eq '00'}">
                     <span style="color:green;display: inline-flex;">回答正确<img  style="width:1.3rem" src="<%=basePath%>images/ea/office/contract/selectp/correct.png"></span>
                     </c:if>
                    <c:if test="${itemt[11] eq '01'}">
                        <span style="color:red; display: inline-flex;">回答错误<img  style="width:1.3rem" src="<%=basePath%>images/ea/office/contract/selectp/error.png"></span>
                    </c:if>


                    &nbsp;&nbsp;得分0</p>


            </div>
            </c:if>
            <div class="basics_mil basics_mil2 answer">
                <p>正确答案：&nbsp;${itemt[10]}</p>


            </div>
            <c:if test="${itemt[2] eq '03'}">
                <div class="basics_mil basics_mil2 answer">
                    <span style="display: none;" class="qreID">${itemt[0]}</span>
                    <span style="display: none;" class="tqeID">${itemt[5]}</span>
                    <span style="display: none;" class="topscore">${itemt[7]}</span>
                    <p>评分：&nbsp;<input type="text" class="pf-input" value="${itemt[12]}"size="5"/>&nbsp;<span>(评分区间0-${itemt[7]}分)</span></p>


                </div>

            </c:if>
        </div>

      </c:forEach>
          <div class="main-bottom">
              <div class="bottom">
                  <ul>
                      <li><div class="jj">上传成绩</div></li>

                  </ul>

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