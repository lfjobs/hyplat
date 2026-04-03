<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/tempTypeAdd.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/tempTypeAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>


</head>

<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            模板分类添加
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">

    <div class="div-leixing clearfix">
        <c:if test="${param.isSet eq '0'}">
        <div class="div-left">
            <label for="">是否共享</label>

        </div>


        <div class="div-right clearfix" id="div-leixing">
            <p class="p-leixing">${param.sysSet eq null||param.sysSet eq ""||param.sysSet eq "01"?"否":"是"}</p>

            <input type="hidden" id="sysSet"  name="templateType.sysSet" value="${param.sysSet eq null?"01":param.sysSet}"/>




        </div>
        </c:if>
    </div>
    <div class="div-name div-htfl">
        <label for="" class="htfl">分类类别</label>
        <p class="p-htfl">${param.module eq "doc"?"公文模板分类":"合同模板分类"}</p>


    </div>
    <div class="div-name">
        <label for="">分类名称</label>
        <input type="hidden" id="temptId"  name="templateType.temptId" value="${param.temptId}"/>
        <input type="text"  placeholder="请填写分类名称"  name="templateType.templateTypeName" id="title" value="${param.templateTypeName}"/>
        <input type="hidden"  name="templateType.parentId" value="${param.parentId}"/>

        <input type="hidden"  name="templateType.module" value="${param.module}"/>

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
<!--选择-->
<div class="div-yinzhang">
    <div class="box">
        <ul>
            <li data-value="00">
               是
            </li>
            <li data-value="01">
             否
            </li>


        </ul>
    </div>
</div>



<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep">更换模板会清空内容确定更换么？</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";

 var sysSet = "${param.sysSet};"
var module = "${param.module}";
var companyID = "${param.companyID}";
var ifr="${param.ifr}";
</script>
</html>
