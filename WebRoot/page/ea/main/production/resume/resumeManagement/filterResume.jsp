<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/filterResume.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/filterResume.js" type="text/javascript" charset="utf-8"></script>

    <title>职位筛选</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sccId = "${param.sccId}";
        var back = "${param.back}";

    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li onclick="backs();">
            <img src="<%=basePath%>css/ea/production/back.png" >
        </li>
        <li>
            职位筛选
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="inp-search"><img src="<%=basePath%>images/resume/img_search.png"/></label>
            <input type="" name="" id="inp-search" value="" placeholder="搜索职位"/>
            <input type="" name="" id="search" value="搜索"/>
        </div>
    </section>
    <section class="sec-con">
        <div>
            <h3>简历状态</h3>
            <ul class="clearfix ul1">
                <li  state-data="00">
                    待处理
                </li>
                <li state-data="01">
                    已发送邀请
                </li>
                <li state-data="03">
                    对方接受邀请
                </li>
                <li state-data="04">
                    不合适
                </li>
            </ul>
        </div>
        <div>
            <h3>招聘职位</h3>
            <ul class="clearfix ul2">
                <c:forEach items="${pageForm.list}" var="item" varStatus="v">


                    <li>
                            ${item.jobTitle}
                    </li>
                </c:forEach>
            </ul>
        </div>
    </section>
    </section>
    <div class="fooder clearfix">
        <p class="p-reset">重置</p>
        <p class="p-confirm">确定</p>
    </div>
</div>
</body>
<script type="text/javascript">
    //选中
    $(document).on("click", ".sec-con>div li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(this).addClass("active");
        }
    })
    //重置
    $(".p-reset").click(function () {
        $(".sec-con>div li").removeClass("active");
    })
</script>
</html>
