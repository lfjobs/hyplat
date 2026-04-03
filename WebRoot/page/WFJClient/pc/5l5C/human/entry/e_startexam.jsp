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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_startexam.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
            type="text/javascript"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_startexam.js"
            type="text/javascript"></script>

    <title>&lrm;</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";


        var  tqeID = "${totalQuestionExam.tqeID}";

        var  tqID = "${totalQuestionExam.tqID}";

    </script>
</head>
<body>
<div class="res_top">
    <ul>
        <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
        <li>考试信息</li>
        <li>

        </li>
        <div class="clearfix"></div>
    </ul>
</div>
<div class="res_bot">
    <form action="" id="from1" method="post" name="form" enctype="multipart/form-data">
        <input type="submit" style="display:none;" name="submit" value="" />

        <input type="hidden" id="qbtID" name="quesBaseType.qbtId" value="${quesBaseType.qbtId}">


        <div class="basics">


            <div class="basics_mil basics_mil2">
                <p class="bold-p">考生信息</p>


            </div>
            <div class="basics_mil basics_mil2">
                <p>姓名</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${examRelate.staffName}"  >


            </div>
            <div class="basics_mil basics_mil2">
                <p>身份证号</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${examRelate.card}"  >


            </div>
            <div class="basics_mil basics_mil2">
                <p class="bold-p">考题信息</p>


            </div>
            <div class="basics_mil basics_mil2">
                <p>考题</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${totalQuestionExam.titleBase}"  >


            </div>
            <div class="basics_mil basics_mil2">
                <p>时长</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${totalQuestionExam.duration}"  >


            </div>
            <div class="basics_mil basics_mil2">
                <p>题目数量</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${totalQuestionExam.totalQues}" >


            </div>
            <div class="basics_mil basics_mil2">
                <p>满分</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${totalQuestionExam.totalscore}" >


            </div>
            <div class="basics_mil basics_mil2">
                <p>及格分</p>
                <input type="text"
                       name="quesBaseType.typeName" value="${totalQuestionExam.qualifiedSocre}" >


            </div>
            <c:if test="${examRelate.status eq '00'||examRelate.status eq '01'}">
            <div class="div-bottom">
                <p class="startExam">

                    <c:if test="${examRelate.status eq '00'}">
                        开始考试

                    </c:if>
                    <c:if test="${examRelate.status eq '01'}">
                        继续考试

                    </c:if>
                </p>

            </div>
            </c:if>
        </div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
                framespacing="0" height="0"></iframe>
    </form>
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