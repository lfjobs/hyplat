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
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_examiframe.js"
            type="text/javascript"></script>



    <title>&lrm;</title>
<script type="text/javascript">
    var basePath="<%=basePath%>";

    var answer = "${examAnswer.answer}";
    var quesType = "${questionsExam.quesType}";

    var  tqeID = "${totalQuestionExam.tqeID}";

    var  tqID = "${totalQuestionExam.tqID}";

    var startDate = "${examRelate.startDate}";

    var duration = ${duration};



   $(function(){

       $("#iframe").attr("height",$(window).height()-$(".res_top").height());

   })
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a onclick="back()" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
            <li class="time"><img src="<%=basePath%>images/ea/office/contract/selectp/time.png"/><span id="timer"></span></li>
            <li>

            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div>
        <iframe name="myFrame" id="iframe" width="100%" height="100%" frameborder="0" src = "<%=basePath%>ea/quest/ea_getExamPage.jspa?totalQuestionExam.tqeID=${totalQuestionExam.tqeID}&totalQuestionExam.tqID=${totalQuestionExam.tqID}" ></iframe>


    </div>

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