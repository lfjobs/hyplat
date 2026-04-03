<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/resumeDetail.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/resumeDetail.js" type="text/javascript"
            charset="utf-8"></script>

    <title>个人信息</title>


    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var tpId = "${param.tpId}";

        var sccId = "${param.sccId}";
        var state = "${obj[17]}";
        var resumeID = "${param.resumeID}";
        var retel = " ${obj[9]}";
        var sccIdt = "${obj[19]}";
        var name = "${obj[2]}";
        var back = "${param.back}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li onclick="backs()">
            <img src="<%=basePath%>css/ea/production/back.png" >
        </li>
        <li>
            个人信息
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="grxx">
        <p class="p-zw">应聘职位：${obj[14]}</p>
        <section class="clearfix sec-xx">
            <div class="div-left">
                <div class="div-01 clearfix">
                    <h2>
                        ${obj[2]}
                    </h2>
                    <p>
                        ${obj[16]}
                    </p>
                </div>
                <div class="div-02 clearfix">
                    <p>
                        ${obj[5]}
                    </p>
                    <span></span>
                    <p>
                        ${obj[13]}岁
                    </p>
                    <span></span>
                    <p >
                        工作${edu}年
                    </p>
                    <span></span>
                    <p>
                        ${obj[15]}
                    </p>
                    <span></span>
                    <p>
                        现住${obj[7]}
                    </p>
                </div>
                <p class="hj">
                  籍贯${obj[6]}
                </p>
                <div class="div-03">
                    <div>
                        <img src="<%=basePath%>images/resume/img_05.png"/>
                    </div>
                    <p>
                        ${obj[9]}
                    </p>
                </div>
            </div>
            <div class="div-right">
                <img src="<%=basePath%>${obj[4]}" onerror="this.src='<%=basePath%>images/ea/driving/elkc/head.png'"/>
            </div>
        </section>
        <div class="div-yx clearfix">
            <div>
                <img src="<%=basePath%>images/resume/img_06.png"/>
            </div>
            <p>
                ${obj[8]}
            </p>
            <p>

                <c:choose>
                    <c:when test="${obj[17] eq '00'}">
                        待处理
                    </c:when>
                    <c:when test="${obj[17] eq '01'}">
                        已邀请
                    </c:when>
                    <c:when test="${obj[17] eq '04'}">
                        不合适
                    </c:when>
                    <c:when test="${obj[17] eq '03'}">
                        接受邀请
                    </c:when>
                    <c:when test="${obj[17] eq '02'}">
                        被查看
                    </c:when>
                    <c:when test="${obj[17] eq '05'}">
                        拒绝邀请
                    </c:when>
                </c:choose>
            </p>
        </div>
    </section>
    <section class="qzyx">
        <h2>求职意向</h2>
        <p class="p-zp">
            ${obj[18]}<span>${obj[10]}</span>
        </p>
        <div class="div-01 clearfix">

            <c:if test = "${obj[1] ne ''}">
                <c:set var="poss" value="${fn:split(obj[1], ',')}"  />
                <c:forEach items="${poss}" var="pos">
                    <p>
                            ${pos}
                    </p>
                    <span></span>
                </c:forEach>
            </c:if>
            <%--<p> ${obj[1]}</p>--%>
            <%--<span></span>--%>
            <%--<p>电子商务</p>--%>
            <%--<span></span>--%>
            <%--<p>新媒体</p>--%>
        </div>
        <div class="div-02">
            <div>
                <img src="<%=basePath%>images/resume/img_07.png"/>
            </div>
            <p>
                ${obj[3]}
            </p>
        </div>
    </section>
    <section class="rwjl clearfix">
        <h2>工作经历</h2>

        <c:if test="${fn:length(srlist) ne 0}">
            <c:forEach items="${srlist}" var="item">
                <div class="div-con clearfix">
                    <div class="div-left">
                        <p><img src="<%=basePath%>images/resume/img_08.png"/></p>
                        <div>

                        </div>
                        <p><img src="<%=basePath%>images/resume/img_08.png"/></p>
                    </div>
                    <div class="div-right">
                        <h3>
                                ${item.companyName}
                        </h3>
                        <p class="gzsj">
                                ${fn:substring(item.startTime,0,10)}-<c:if test="${item.endTime ne null}">${fn:substring(item.endTime,0,10)}</c:if><c:if test="${item.endTime eq null}">今</c:if>
                        </p>
                        <p class="gzzw">
                                ${item.postName}
                        </p>
                        <div class="div-bq clearfix">

                            <c:if test = "${item.position ne ''}">
                                <c:set var="positions" value="${fn:split(item.position, ',')}"  />
                                <c:forEach items="${positions}" var="position">
                                    <p>
                                            ${position}
                                    </p>
                                </c:forEach>
                            </c:if>
                        </div>
                        <p class="p-text">
                                ${item.duties}

                        </p>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${fn:length(srlist) eq 0}">
            无
        </c:if>


        <%--<div class="div-con clearfix">--%>
            <%--<div class="div-left">--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
                <%--<div>--%>

                <%--</div>--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<h3>--%>
                    <%--北京尚成生物科技有限公司--%>
                <%--</h3>--%>
                <%--<p class="gzsj">--%>
                    <%--2018年7月-至今（2年4个月)--%>
                <%--</p>--%>
                <%--<p class="gzzw">--%>
                    <%--人事部副经理--%>
                <%--</p>--%>
                <%--<div class="div-bq clearfix">--%>
                    <%--<p>--%>
                        <%--教育行业招聘--%>
                    <%--</p>--%>
                    <%--<p>--%>
                        <%--传媒行业招聘--%>
                    <%--</p>--%>
                    <%--<p>--%>
                        <%--互联网行业招聘--%>
                    <%--</p>--%>
                <%--</div>--%>
                <%--<p class="p-text">--%>
                    <%--本人于2018-06至今就职于上海尚成生物科技有限公司担任人事部副经理一职。在职期间主要负责带领团队去各大学校参加校企招聘会，然后招聘，宣讲，面试，录取等工作。之后进入公司负责管理团队，以及新人入职的安排培训管理等。最多在一个月招聘25人，入职23人。--%>
                <%--</p>--%>
            <%--</div>--%>
        <%--</div>--%>
    </section>
    <section class="jyjl clearfix">
        <h2>
            教育经历
        </h2>


        <c:if test="${fn:length(edulist) ne 0}">
            <c:forEach items="${edulist}" var="item">
                <%--<ul class="gong_jinyan">--%>
                    <%--<li>${fn:substring(item.admissionTime,0,10)}至${fn:substring(item.graduationTime,0,10)}</li>--%>
                    <%--<li><h4 style="border-bottom: 0;line-height:2rem;">${item.name}</h4></li>--%>
                    <%--<li class="zhuanye"><span>${item.education}</span><span>${item.professionalName}</span></li>--%>
                <%--</ul>--%>

                <div class="div-con clearfix">
                    <div class="div-left">
                        <p><img src="<%=basePath%>images/resume/img_08.png"/></p>
                        <div>

                        </div>
                        <p><img src="<%=basePath%>images/resume/img_08.png"/></p>
                    </div>
                    <div class="div-right">
                        <section>
                            <h3>${item.name}</h3>
                            <p>
                                    ${fn:substring(item.admissionTime,0,10)}-${fn:substring(item.graduationTime,0,10)}
                            </p>
                        </section>
                        <div class="clearfix">
                            <p>${item.professionalName}</p>
                            <span></span>
                            <p>${item.education}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${fn:length(edulist) eq 0}">
            无
        </c:if>


        <%--<div class="div-con clearfix">--%>
            <%--<div class="div-left">--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
                <%--<div>--%>

                <%--</div>--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<section>--%>
                    <%--<h3>北京电子信息职业技术学院</h3>--%>
                    <%--<p>--%>
                        <%--2015年09月-2017年12月--%>
                    <%--</p>--%>
                <%--</section>--%>
                <%--<div class="clearfix">--%>
                    <%--<p>土木工程学</p>--%>
                    <%--<span></span>--%>
                    <%--<p>大专</p>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </section>
    <%--<section class="xmjl clearfix">--%>
        <%--<h2>--%>
            <%--项目经历--%>
        <%--</h2>--%>
        <%--<div class="div-con clearfix">--%>
            <%--<div class="div-left">--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
                <%--<div>--%>

                <%--</div>--%>
                <%--<p><img src="<%=basePath%>images/resume/img_08.png"/></p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<section>--%>
                    <%--<h3>北京电子信息职业技术学院</h3>--%>
                    <%--<p>--%>
                        <%--2015年09月-2017年12月--%>
                    <%--</p>--%>
                <%--</section>--%>
                <%--<div>--%>
                    <%--<p>项目描述：</p>--%>
                    <%--<p>--%>
                        <%--这个主要是带领团队报名学校招聘会，从学校选取优秀毕业生，我在其中主要负责带领团队招聘。以及宣讲。面试等工作，还有就是解决应聘者各种问题。--%>
                    <%--</p>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</section>--%>
    <section class="zwpj">
        <h2>自我评价</h2>
        <p>
            ${obj[11]}        </p>
    </section>
</div>
<div class="div-footer-top"></div>
<c:if test="${param.view ne 'v'}">
<section class="footer clearfix">
    <ul class="clearfix">
        <li class="li-bhs">
            <img src="<%=basePath%>images/resume/img_09.png"/>
            <p>不合适</p>
        </li>
        <li class="li-yms">
            <img src="<%=basePath%>images/resume/img_11.png"/>
            <p>约面试</p>
        </li>
        <li class="li-ddh">
            <img src="<%=basePath%>images/resume/img_10.png" onclick="getPhone('${obj[9]}')"/>

            <p onclick="getPhone('${obj[9]}')">打电话</p>
        </li>
    </ul>
    <input type="button" value="立即沟通" onclick="sendMes()"/>
    <!--继续聊天-->
</section>
</c:if>
<div class="div-del">
    <div class="box">
        <p class="titlep">确认继续操作么？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-q">确定</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //左侧装饰div-left高度
    $(".div-con .div-left").each(function () {
        var dLeftheight=$(this).parent().find(".div-right").height()
        $(this).height(dLeftheight * 0.85);
    })
    $(".jyjl .div-left").height(($(".jyjl .div-right").height()) * 0.625);

//    $(".rwjl .div-left").height(($(".rwjl .div-right").height()) * 0.9);
//    $(".jyjl .div-left").height(($(".jyjl .div-right").height()) * 0.72);
//    $(".xmjl .div-left").height(($(".xmjl .div-right").height()) * 0.85);
</script>
</html>
