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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/consult/consultlist.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/my/consult/consultlist.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};


    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li >
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">

            <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
                </a>
        </li>
        <li>
           客户咨询
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div><select class="returnv"><option value="">全部</option><option value="00">未回访</option><option value="01">已回访</option></select></div>
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="搜索姓名或手机号" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/edit.png"/>回访记录</p>
            </li>

            <%--<li class="clearfix" >--%>
                <%--<p class="mark"><img src="<%=basePath%>images/ea/office/contract/selectp/bj.png"/>标记</p>--%>
            <%--</li>--%>

            <li class="clearfix">
                <p class="excel"><img src="<%=basePath%>images/ea/office/contract/selectp/excel.png"/>导出excel</p>
            </li>
            <li class="clearfix">
                <p class="print"><img src="<%=basePath%>images/ea/office/contract/selectp/print.png"/>打印</p>
            </li>

        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">


            <li class="clearfix">
                <div class="title-pc">

                    <div class="sex">选择</div>

                    <div class="docNum-p" title="姓名">姓名</div>
                    <div class="title-p" title="电话">电话</div>

                    <div class="theme-p" title="时间">时间</div>


                    <div class="docType-p" title="咨询内容">咨询内容</div>


                    <div class="emergencyType-p" title="回访">回访</div>



                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="item" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${item.crId}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${item.crId}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">
                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"></div>

                    <div class="docNum-p" title="${item.consultantName}">${item.consultantName}</div>
                    <div class="title-p" title="${item.consultantPhone}"><a href="tel:${item.consultantPhone}">${item.consultantPhone}</a></div>

                    <div class="theme-p" title="${fn:substring(item.consultingDate,0,19)}"> ${fn:substring(item.consultingDate,0,19)}</div>
                    <div class="docType-p" title="${item.consultantContent}"> ${item.consultantContent eq null?"其他":item.consultantContent}</div>

                    <c:choose>
                        <c:when test='${item.returnVisit=="00"}'><div class="emergencyType-p" title="否">否</div></c:when>
                        <c:when test='${item.returnVisit=="01"}'><div class="emergencyType-p" title="是">是</div></c:when>

                    </c:choose>



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
<iframe style="display:none" id="printIframe" src=""></iframe>


</body>

</html>
