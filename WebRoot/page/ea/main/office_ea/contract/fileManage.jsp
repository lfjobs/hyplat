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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/fileManage.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/fileManage.js" type="text/javascript" charset="utf-8"></script>
    <title>${product[0]}</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>"

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

        var pageNumber1  = 0;
        var pageCount1  = 0;
        var recordCount1 = 0;

        var sccId = "${sccId}";

        var  deal = "${param.deal}";

    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li >
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >


            <img src="<%=basePath%>css/ea/production/back.png" >
                </a>
        </li>
        <li>
            签约文件
        </li>
        <li class="stamp">
            <%--<a href = "<%=basePath%>/ea/enterprisestamp/ea_getStampList.jspa?sccId=${sccId}">--%>
           <%--<img src="<%=basePath%>images/ea/office/contract/img_05.png"/>个人印章--%>
            <%--</a>--%>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="搜索文件" />
        </div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix" >


            <li class="clearfix active" id="myfile">
                <p><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>我的文件</p>
            </li>
            <li class="clearfix">
                <p class="draft"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>起草文件</p>
            </li>
            <li class="clearfix" id="dealfile">
                <p><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>待处理<span class="dcnum" style="color:red;">(0)</span></p>
            </li>
        </ul>
    </section>
    <section class="sec-list">
        <ul class="ul">
            <c:forEach  items="${pageForm.list}" var="item" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${item.docId}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${item.docId}">

                </c:if>

                    <p class="title">${item.title}</p>
                    <span style="display: none;" class="status">${item.status}</span>
                    <span style="display: none;" class="companyName">${item.companyName}</span>
                    <span style="display: none;" class="companyID">${item.companyID}</span>
                    <span style="display: none;" class="scene">${item.scene}</span>

                    <c:choose>
                        <c:when test="${item.status=='A'}">
                            <p class="p-wq">待签约</p>
                        </c:when>
                        <c:when test="${item.status=='R'}">
                             <p class="p-bh">已驳回</p>
                        </c:when>
                        <c:when test="${item.status=='U'}">
                            <p class="p-bh">不批准</p>
                        </c:when>
                        <c:when test="${item.status=='F'}">
                            <p class="p-cg">已签约</p>
                       </c:when>
                        <c:when test="${item.status=='P'}">
                            <p class="p-wq">待分发</p>
                        </c:when>
                        <c:when test="${item.status=='S'||item.status=='T'}">
                            <p class="p-wq">审核中</p>
                        </c:when>
                        <c:when test="${item.status=='I'}">
                            <p class="p-cg">草稿</p>
                        </c:when>
                        <c:when test="${item.status=='O'}">
                            <p class="p-bh">待阅读</p>
                        </c:when>
                        <c:when test="${item.status=='K'}">
                            <p class="p-dzf">待支付</p>
                        </c:when>
                        <c:otherwise>
                            <p class="p-cg">已阅读</p>
                        </c:otherwise>
                    </c:choose>

                </li>
            </c:forEach>

            <%--<li class="clearfix">--%>
                <%--<p>天津市可名世纪有限公司文件</p>--%>
                <%--<p class="p-dzf">待支付</p>--%>
            <%--</li>--%>

        </ul>
    </section>
</div>

<div class="con">
    <div class="div-sel">
         <button class="btn" id="bd"><span>本地上传</span></button>
        <button class="btn" id="lineedit"><span>在线编辑</span></button>

    </div>
</div>
</body>
<script type="text/javascript">

</script>
</html>
