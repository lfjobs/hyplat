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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/shareTempTree.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js" type="text/javascript"></script>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
    <script src="<%=basePath%>js/ea/office_ea/contract/shareTempTree.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
    <script type="text/javascript">
        var module = "${param.module}";
        $(function(){

//            if(module!=null&&module!=""){
//                $(".tdhead").css({ visibility: 'hidden' });
//            }
        })

        var basePath = "<%=basePath%>";
        var pageNumber  = 0;
        var pageCount  = 0;


        var pos = "${param.pos}";
        var sccId = "${sccId}";
        var  isSet = "${isSet}";

    </script>
</head>
<body>
<header class="tdhead">
    <ul class="clearfix">

        <c:if test="${param.pos eq 'add'||param.pos eq 'cx'}">
            <li>
                <a href="javascript:close()" target="_self">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />
                </a>
            </li>

        </c:if>
        <c:if test="${param.pos ne 'add'&&param.pos ne 'cx'}">
            <li>
                <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />

                </a>
            </li>
        </c:if>

        <li>
           共享模板
        </li>
        <li>

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

    <c:if test='${param.pos!= "add"&&param.pos!="cx"}'>
        <section class="sec-nav sec-hide">
            <!--sec-hide-->
            <ul class="clearfix" >



                <li class="clearfix">
                    <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
                </li>




            </ul>
        </section>
    </c:if>
    <section class="sec-list">

        <div id="mbfl" style="width: 100%;z-index:99;"></div>

    </section>

    <section class="sec-ul">
        <ul class="ul-list">


        </ul>
    </section>

</div>



<!--表单提示-->
<div class="div-tingyong">
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
