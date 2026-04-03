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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_addQuesBase.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_addQuesBase.js"
	type="text/javascript"></script>

    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var  type = "${param.type}";

	
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
            <li>题库</li>
            <li>

            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
    <form action="" id="from1" method="post" name="form" enctype="multipart/form-data">
    <input type="submit" style="display:none;" name="submit" value="" />
	<input type="hidden" id="tqID" name="totalQuestion.tqID" value="${totalQuestion.tqID}">
        <input type="hidden" id="qbtID" name="totalQuestion.qbtID" value="${totalQuestion.qbtID}">
    <input type="hidden" id="type" name="totalQuestion.type" value="${type}">

        <div class="basics">

            <div class="basics_mil basics_mil2">
                <p>题库类别</p>
                <s:select list="%{#request.queTypelist}" cssClass="qbtID"
                          headerKey="" headerValue="请选择" listKey="qbtId"
                        listValue="typeName"
                          theme="simple">
                </s:select><span class="addfl">添加</span>
                <input type="hidden" id="typeName" name="totalQuestion.typeName" value="${totalQuestion.typeName}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>题库名称</p>
                <input type="text"
                       name="totalQuestion.titleBase" value="${totalQuestion.titleBase}" id="titleBase" >


            </div>


            <div class="basics_mil basics_mil2">
                <p>合格分数线</p>
                <input type="text"
                       name="totalQuestion.qualifiedSocre" value="${totalQuestion.qualifiedSocre}" id="qualifiedSocre" placeholder="请填写分数线">


            </div>

            <div class="basics_mil basics_mil2">
                <p>时长</p>
                <input type="text"
                       name="totalQuestion.duration" value="${totalQuestion.duration}" id="duration"  placeholder="请填写时长">


            </div>
            <div class="div-bottom">
                <p class="saveDraft">
                    保存
                </p>

            </div>
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