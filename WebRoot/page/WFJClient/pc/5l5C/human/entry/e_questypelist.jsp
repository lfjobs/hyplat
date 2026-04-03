<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";


    // 设置不要缓存页面
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // 设置过期时间为0.

%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_questypelist.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_questypelist.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

         var type="${type}";

         var qbtId = "${quesBaseType.qbtId}";




    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li class="title-li">
           题库类别
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
            <input type="text" name="" id="search" placeholder="题库类别" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix" >

            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加</p>

            </li>
            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>
            <li class="clearfix">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>

        </ul>
    </section>

    <section class="sec-list" id="pc-sec" >
        <ul class="ul">

            <li class="clearfix">
                <div class="title-pc">

                    <div class="sex">
                        选择
                    </div>
                    <div class="date-s" title="题库类别">题库类别</div>
                    <div class="date-p" title="创建人姓名">创建人</div>
                    <div class="date-s pc" title="创建时间">创建时间</div>
                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="f" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${f.qbtId}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${f.qbtId}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">

                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png">
                    </div>
                    <div class="date-s " title="${f.typeName}">${f.typeName}</div>
                    <div class="date-p" title="${f.staffName}">${f.staffName eq null?'无':f.staffName}</div>
                    <div class="date-s pc" title="${fn:substring(f.createDate,0, 10)}">${fn:substring(f.createDate,0, 19)}</div>

                </div>
                </li>
            </c:forEach>



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
<script>
    window.onload =  function () {



        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){

            $(".pc").show();
        }else {
            $(".pc").hide();
        }
    }



</script>
</html>
