<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>    <script type="text/javascript">
    var basePath="<%=basePath%>";
    var pageNumber1  = ${pageForm==null?0:pageForm.pageNumber};
    var pageCount1  = ${pageForm==null?0:pageForm.pageCount};
    var sccId = "${param.sccId}";
    var back = "${param.back}";

</script>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/posPosition.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/positionManage.js" type="text/javascript" charset="utf-8"></script>

    <title>职位管理</title>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a  onclick="backs();">
            <img src="<%=basePath%>css/ea/production/back.png" >
            </a>
        </li>
        <li>
            职位管理
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="div-del">
    <div class="box">
        <p class="titlep">确定要删除吗？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-q">确定</p>
        </div>
    </div>
</div>
<section class="content">
    <section class="sec-nav">
        <ul class="clearfix">
            <li class="active">
                在线中职位
            </li>
            <li>
                已下线职位
            </li>
        </ul>
    </section>
    <div class="div-tab1">
        <ul class="ul-con">
      <c:forEach items="${pageForm.list}" var="item" varStatus="v">
          <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
              <li class="last1" id="${item.riId}">
          </c:if>
          <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
              <li id="${item.riId}">

          </c:if>

                <h3>
                        ${item.jobTitle}
                </h3>
                <div class="div-01 clearfix">
                    <p>
                            ${item.salary}
                    </p>
                    <span></span>
                    <p>
                            ${item.education}
                    </p>
                    <span></span>
                    <p>
                            ${item.workYears}
                    </p>
                </div>
                <p class="p-xm">
                    发布人：${item.staffName}<span style="float:right;">发布日期：${fn:substring(item.publishDate,0,19)}</span>
                </p>
                <section class="clearfix">
                    <p class="p-del">删除</p>
                    <p class="p-edit">修改</p>
                    <p class="p-onoff">下线</p>
                </section>
            </li>
</c:forEach>
            <%--<li>--%>
                <%--<h3>--%>
                    <%--软件测试工程师--%>
                <%--</h3>--%>
                <%--<div class="div-01 clearfix">--%>
                    <%--<p>--%>
                        <%--6千-8千--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--天津--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--1-3年--%>
                    <%--</p>--%>
                <%--</div>--%>
                <%--<p class="p-xm">--%>
                    <%--刘女士--%>
                <%--</p>--%>
                <%--<section class="clearfix">--%>
                    <%--<p class="p-del">删除</p>--%>
                    <%--<p>修改</p>--%>
                <%--</section>--%>
            <%--</li>--%>

        </ul>
        <div class="footer-box">

        </div>
        <div class="footer">
            <p class="publish">
                立即发布
            </p>
        </div>
    </div>
    <div class="div-tab2">
        <ul class="ul-con">
            <%--<li>--%>
                <%--<h3>--%>
                    <%--软件测试工程师--%>
                <%--</h3>--%>
                <%--<div class="div-01 clearfix">--%>
                    <%--<p>--%>
                        <%--6千-8千--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--天津--%>
                    <%--</p>--%>
                    <%--<span></span>--%>
                    <%--<p>--%>
                        <%--1-3年--%>
                    <%--</p>--%>
                <%--</div>--%>
                <%--<p class="p-xm">--%>
                    <%--刘女士--%>
                <%--</p>--%>
                <%--<section class="clearfix">--%>
                    <%--<p class="p-del">删除</p>--%>
                    <%--<p>修改</p>--%>
                    <%--<p>上线</p>--%>
                <%--</section>--%>
            <%--</li>--%>
        <%----%>

        </ul>
    </div>

</section>
</body>


</html>
