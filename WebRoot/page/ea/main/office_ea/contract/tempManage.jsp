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
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/tempManage.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/tempManage.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            公文模板
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <div class="h-head">
        <div class="fenlei"><img src="<%=basePath%>images/ea/office/contract/selectp/fl.png"/>
            <span>分类</span>
        </div>
        <section class="sec-search">
            <div class="box clearfix">
                <label for="">
                    <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
                </label>
                <input type="text" name="" id="search" placeholder="搜索模板" />
            </div>
            <div><input type="button" name="" id="qsearch" value="搜索" /></div>
        </section>

    </div>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">


            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>新建</p>

            </li>

            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>
            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <%--<li class="clearfix">--%>
            <%--<p class="down"><img src="<%=basePath%>images/ea/office/contract/selectp/down.png"/>下载</p>--%>
            <%--</li>--%>
            <li class="clearfix">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>


        </ul>
    </section>
    <section class="sec-ul">
        <ul class="ul-list">
            <c:forEach items="${pageForm.list}" var="item" varStatus="v">
                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${item[0]}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${item[0]}">

                </c:if>


                <div class='sex'>
                    <img class='img-01' src='<%=basePath%>images/ea/office/contract/selectp/img_02.png'/>
                    <img class='img-02' src='<%=basePath%>images/ea/office/contract/selectp/img_03.png'/>
                </div>
                <div class='div-img'>
                    <c:if test="${item[3] eq 'W'}">


                        <img src='<%=basePath%>images/ea/office/contract/word.png'>
                    </c:if>
                    <c:if test="${item[3] eq 'E'}">


                        <img src='<%=basePath%>images/ea/office/contract/excel-ext.png'>
                    </c:if>
                </div>
                <p class="fileShowName">${item[4]}</p>

                <p>
                    <input type="hidden" id="sysSet" value="${item[7]}"/>
                    <input type="hidden" id="templateTypeName" value="${item[8]}"/>
                    <input type="hidden" id="templatePath" value="${item[1]}"/>
                    <input type="hidden" id="fileType" value="${item[3]}"/>
                    <input type="hidden" class="temptId" value="${item[10]}"/>


                    <span>${fn:substring(item[5],0,10)}</span>


                    <c:if test="${item[7] eq '00'}">
                       <span style="color:red;">
                             <%--<img src='<%=basePath%>images/ea/office/contract/selectp/sf.png'>--%>
                             限时免费
                       </span>
                    </c:if>
                    <c:if test="${item[7] ne '00'}"><span> ${item[6]}</span></c:if>


                </p>


                </li>

            </c:forEach>
        </ul>
    </section>

</div>

<div class="con">
    <div class="div-sel">
        <button class="btn" id="bd"><span>本地上传</span></button>
        <button class="btn" id="lineedit"><span>在线编辑</span></button>

    </div>
</div>
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>


<div class="iframecom" >
    <iframe id="iframe" src="<%=basePath%>ea/androiddoc/ea_getDocTempTypeList.jspa?pos=cx" width="100%" height="100%" frameborder="0"></iframe>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
    var pageCount = ${pageForm==null?0:pageForm.pageCount};
    var isSet = "${isSet}";


</script>


</html>

