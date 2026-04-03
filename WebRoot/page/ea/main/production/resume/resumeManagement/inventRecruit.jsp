<!DOCTYPE html>
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
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/date.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/inventRecruit.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/inventRecruit.js" type="text/javascript"
            charset="utf-8"></script>

    <title>面试邀请</title>

    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var tpId = "${param.tpId}";

        var resumeID = "${param.resumeID}";
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
            面试邀请
        </li>
        <li class="li-fs">
            发送
        </li>
    </ul>
</header>
<div class="div-msyq">
    <div class="box">
        <p>确定发送面试邀请？</p>
        <div class="div-yq clearfix">
            <p class="p-c">再想一想</p>
            <p class="p-q">确定</p>
        </div>
    </div>
</div>

<div class="div-del">
    <div class="box">
        <p class="titlep">确定要删除吗？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-c">确定</p>
        </div>
    </div>
</div>

<div class="content">
    <form name="mainForm" id="mainForm" method="post">
        <input type="submit" style="display: none;" name="submit"/>
        <section class="sec-header">
            <input type="text" placeholder="面试联系人姓名" id="contactor" value="${staff.staffName}" name="talentPool.contactor"/>
            <input type="hidden" value="${param.tpId}"
                   name="tpId"/>
            <div class="clearfix">
                <p class="p-left">
                    <input type="text" placeholder="面试联系人电话" value="${staff.reference}" id="contactTel"
                           name="talentPool.contactTel"/>
                </p>
                <p class="p-right">
                    面试联系人
                </p>
            </div>
        </section>
        <c:if test="${talentPool.type eq '01'}">

            <section class="sec-remarks ">
                <h3>
                    招聘职位
                </h3>
                <p class="zpzw">
                    <span class="zw">请选择</span>
                    <input type="hidden" id="riId"
                           name="talentPool.riId"/>
                </p>
            </section>
        </c:if>
        <section class="sec-con">
            <header>
                <ul class="clearfix">
                    <li>

                    </li>
                    <li>
                        招聘职位
                    </li>
                    <li class="">

                    </li>
                </ul>
            </header>
            <div>
                <ul class="clearfix ul2">
                    <c:forEach items="${postionlist}" var="item">
                        <li id="${item.riId}">
                                ${item.jobTitle}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
        <section class="sec-place">
            <h3>
                面试地点
            </h3>
            <input type="text" name="talentPool.interviewPlace" placeholder="请填写面试详细地址" id="interviewPlace"
                   value="${talentPool.interviewPlace}"/>
        </section>
        <section class="sec-time">
            <h3>
                面试时间
            </h3>
            <input type="text" id="date3" name="dates" placeholder="请选择面试时间"  id="interviewDate"
                   data-options="{'type':'YYYY-MM-DD hh:mm','beginYear':2019,'endYear':2021}">
        </section>
        <section class="sec-remarks">
            <h3>
                备注（选填）
            </h3>
            <input type="text" name="talentPool.remark" placeholder="请填写备注信息" id="" value=""/>

        </section>
        </section>

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
                framespacing="0" height="0"></iframe>
    </form>
</div>
</body>
<script src="<%=basePath%>js/jquery.date.js" type="text/javascript" charset="utf-8"></script>

</html>
