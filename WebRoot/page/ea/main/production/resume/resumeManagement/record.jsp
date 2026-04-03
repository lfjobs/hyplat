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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">

    <link rel="stylesheet" href="<%=basePath%>css/ea/production/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/zepto.min.js"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/record.js" type="text/javascript"
            charset="utf-8"></script>



    <title>${param.type eq '00'?'我的投递记录':'我的职位邀请'}</title>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var staffid = "${param.staffid}";
        var jitype = "${param.jitype}";
        var sccId = "${param.sccId}";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var type = "${param.type}";
        var back = "${param.back}";
    </script>
</head>
<body>
<!-- header start  -->
<header class="mem_header">
    <a class="back" id="returnClicks"></a>
    <h1>${param.type eq '00'?'我的投递记录':'我的职位邀请'}</h1>
</header>
<!--  header end  -->
<!-- 页面内容 start  -->
<div class="wrap_page">
    <section class="send_rec_wrap">
        <div class="send_filt_box">
            <c:if test="${param.type eq '00'}">
                <dl class="send_menu">
                    <dd><a class="active" state-data="">全部</a></dd>
                    <dd><a state-data="01">被邀面试</a></dd>
                    <dd><a state-data="03">接受邀请</a></dd>
                    <dd><a state-data="04">不合适</a></dd>

                </dl>
           </c:if>

        <c:if test="${param.type eq '01'}">
            <dl class="send_menu">
                <dd><a class="active" state-data="">全部</a></dd>
                <dd><a state-data="01">待确认</a></dd>
                <dd><a state-data="03">已接受</a></dd>
                <dd><a state-data="05">已拒绝</a></dd>

            </dl>
       </c:if>
        </div>
        <div class="sjob_rec_wrap" id="valp">
            <%--${evaluate}--%>
            <c:forEach items="${pageForm.list}" var="k" varStatus="v">
                    <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                          <a onclick="dianji(this)" class="sjob_rec_box  flex flex_align_center last1">
                    </c:if>
                    <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                        <a onclick="dianji(this)" class="sjob_rec_box  flex flex_align_center">
                    </c:if>
                    <input type="hidden" id="rcid" value="${k[3]}">
                        <input type="hidden" id="tpId" value="${k[8]}">
                    <div class="sjob_L">
                        <%--<c:if test="${k[4]==null}">--%>
                            <%--<img src="<%=basePath%>images/ea/production/dui.png/sjob_01.png" alt="">--%>
                        <%--</c:if>--%>
                        <span id="image"><img src="<%=basePath%>${k[4]}" alt=""></span>
                    </div>
                    <div class="sjob_m flex_1">
                        <p class="sjob_name">
                            <span>${k[0]}</span>
                        </p>
                        <p class="sjob_com">
                            <span id="companyName">${k[1]}</span>
                        </p>
                        <p class="sjob_area">
                            <i class="area_ico"></i><span>${k[6]}</span>
                            <i class="edu_ico"></i><span>${k[7]}</span>
                        </p>
                    </div>

                    <c:if test="${k[2]=='05'}">
                        <input type="hidden" id="vap" value="05">
                        <div style="padding-top: 1.1rem;float: left;padding-right: 0.8rem;">
                            <div class="sjob_r  sjob_1">
                                已拒绝
                            </div>
                            <p style="font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;">
                                ${fn:substring(k[5],0,10)}</p>
                        </div>
                    </c:if>
                    <c:if test="${k[2]=='03'}">
                        <input type="hidden" id="vap" value="03">
                        <div style="padding-top: 1.1rem;float: left;padding-right: 0.8rem;">
                            <div class="sjob_r  sjob_1">
                                已接受
                            </div>
                            <p style="font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;">
                                    ${fn:substring(k[5],0,10)}</p>
                        </div>
                    </c:if>
                    <c:if test="${k[2]=='04'}">
                        <input type="hidden" id="vap" value="04">
                        <div style="padding-top: 1.1rem;float: left;padding-right: 0.8rem;">
                            <div class="sjob_r  sjob_2">
                                不合适
                            </div>
                            <p style="font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;">
                                    ${fn:substring(k[5],0,10)}</p>
                        </div>
                    </c:if>

                    <c:if test="${k[2]=='01'}">
                        <input type="hidden" id="vap" value="01">
                        <div style="padding-top: 1.1rem;float: left;padding-right: 0.8rem;">
                            <div class="sjob_r  sjob_3">
                                被邀面试
                            </div>
                            <p style="font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;">
                                    ${fn:substring(k[5],0,10)}</p>
                        </div>
                    </c:if>
                    <c:if test="${k[2]=='00'}">
                        <input type="hidden" id="vap" value="00">
                        <div style="padding-top: 1.1rem;float: left;padding-right: 0.8rem;">
                            <div class="sjob_r  sjob_4">
                                已投递
                            </div>
                            <p style="font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;">
                                    ${fn:substring(k[5],0,10)}</p>
                        </div>
                    </c:if>

                </a>
            </c:forEach>
        </div>
    </section>
</div>
<div class="over_lay"></div>
<!--  页面内容 end -->
<script>



    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';

    }
    $(function () {

        //点击全部 显示筛选菜单
        $(".send_filt").tap(function (e) {
            e.stopPropagation();
            $(".send_menu").show();
            $(".over_lay").show();
            $(".over_lay").one("tap", function (e) {
                $(".send_menu").hide();
                $(".over_lay").hide();
            });
        });

    })

</script>
</body>

</html>
