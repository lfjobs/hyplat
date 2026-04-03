<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectTempRz.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js" type="text/javascript"></script>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
    <script src="<%=basePath%>js/ea/office_ea/contract/selectTempRz.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>

</head>
<body>
<header class="div-header">
    <ul class="clearfix">

        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />

            </a>
        </li>
        <li>
            选择已有模板
        </li>
        <li>
            <a class="qdbtn">新增模板</a>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="搜索模板名称" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <div class="div-list">
        <section class="sec-list" style="height:100%">
            <div id="mbfl" style="width: 100%;z-index:99;height:100%"></div>
        </section>

        <section class="sec-ul" style="height:100%;display:none">
            <ul class="ul-list" style="height:100%;overflow:auto">
            </ul>
        </section>
    </div>

    <div class="div-bottom">
        <p class="save">
           确定选择
        </p>
    </div>
</div>

<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "${param.module}";

    var basePath = "<%=basePath%>";
    var pageNumber  = 0;
    var pageCount  = 0;


    var pos = "${param.pos}";
    var sccId = "${sccId}";
    var  isSet = "${isSet}";
    var companyID = "${companyID}";
    var fileShowName = "${param.fileShowName}";
    var contractType = "${param.contractType}";
    var pattern = "${param.pattern}";
    $(".content").height($(window).height() - $(".div-header").height() - 30);
    if ("iframe" === pattern){
        $(".div-header").css("display","none");
        $(".content").height($(window).height() - 30);
    }

    $(".div-list").height($(".content").height() - $(".sec-search").height() - $(".div-bottom").height())

</script>
</html>
