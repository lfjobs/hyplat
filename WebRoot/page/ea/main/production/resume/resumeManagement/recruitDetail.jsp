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
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/recruitDetail.css">
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitDetail.js" type="text/javascript" charset="utf-8"></script>

    <title>职位详情</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sccId = "${param.sccId}";
        var riId = "${recruitInfo.riId}";

    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a href="javascript:history.back()">
                <img src="<%=basePath%>css/ea/production/back.png" >
            </a>
        </li>
        <li>
            职位详情
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="zw">
        <h2>
            ${recruitInfo.jobTitle}
        </h2>
        <ul class="clearfix">

            <li>
                ${recruitInfo.education}
            </li>
            <li>

            </li>
            <li>
                ${recruitInfo.partorfull}
            </li>
            <li>

            </li>
            <li>
                ${recruitInfo.workYears}
            </li>
            <li>

            </li>
            <li>
                ${recruitInfo.salary}
            </li>
        </ul>
        <%--<p>--%>
            <%--8千-1万--%>
        <%--</p>--%>
    </section>
    <section class="zwms">
        <h3>
            职位描述
        </h3>
        <ul>
            <li>
                ${recruitInfo.jobRequire}
            </li>
            <%--<li>--%>
                <%--2.热爱并且关注互联网产品发展和用户体验，平时喜欢 体验各种软件产品，尤其对心理、需求或产品有自己独 到的见解者为佳；--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--3.关注用户，信奉数据，优秀的定量分析能力与缜密的 逻辑思维能力；--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--4.独立产品责任人，有项目管理相关经验者优先。--%>
            <%--</li>--%>
        </ul>
    </section>
    <section class="gsmc">
        <h3>
            招聘人数
        </h3>
        <p>
            ${recruitInfo.personNumber}
        </p>
    </section>
    <section class="gzdz">
        <h3>
            工作地址
        </h3>
        <p>
            ${recruitInfo.workCity}  ${recruitInfo.workPlace}
        </p>
    </section>
</div>
<section class="footer clearfix">
    <ul class="clearfix">
        <li  class="p-onoff">
            <img src="<%=basePath%>images/resume/img_01.png"/>
            <p> ${recruitInfo.status eq '00'?'上线':'下线'}</p>
        </li>
        <li class="p-edit">
            <img src="<%=basePath%>images/resume/img_02.png"/>
            <p>修改</p>
        </li>
        <li class="p-del">
            <img src="<%=basePath%>images/resume/img_03.png"/>
            <p >删除</p>
        </li>
    </ul>
    <%--<input type="button" value="刷新职位" />--%>
</section>
<div class="div-del">
    <div class="box">
        <p class="titlep">确定要删除吗？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-q">确定</p>
        </div>
    </div>
</div>
</body>

</html>
