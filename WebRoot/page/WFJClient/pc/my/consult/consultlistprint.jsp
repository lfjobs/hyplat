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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/consult/consultlistprint.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">


        var basePath = "<%=basePath%>";


    </script>


    <%--<style media="print">--%>
        <%--@page{--%>

            <%--size:auto;--%>
            <%--margin:0mm;--%>
        <%--}--%>

    <%--</style>--%>
</head>
<body>
<div class="content">

    <section class="sec-list" id="pc-sec">
        <ul class="ul">


            <li class="clearfix">
                <div class="title-pc">

                    <div class="sex">序号</div>

                    <div class="docNum-p" title="姓名">姓名</div>
                    <div class="title-p" title="电话">电话</div>

                    <div class="theme-p" title="时间">时间</div>


                    <div class="docType-p" title="咨询内容">咨询内容</div>


                    <div class="emergencyType-p" title="回访">是否回访</div>



                </div>


            </li>
            <c:forEach  items="${list}" var="item" varStatus="v">


                <li class="clearfix" id="${item.crId}">
                <div class="title-pc">
                    <div class="sex">
                            ${v.index+1}
                    </div>

                    <div class="docNum-p" title="${item.consultantName}">${item.consultantName}</div>
                    <div class="title-p" title="${item.consultantPhone}">${item.consultantPhone}</div>

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

</body>

</html>
