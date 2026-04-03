<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/edit.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css"/>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css"/>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <title>发布记录</title>
</head>
<body>
<s:if test="tle==1">
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>发布记录</h1>
</header>
</s:if>
<div class="wrap_page" <s:if test='tle==null||tle==0'>style="margin-top:0;" </s:if>>
    <div class="rec_tab_wrap clearfix" <s:if test='tle==null||tle==0'>style="top: 0;" </s:if>>
        <a href="javascript:;" class="rec_tab rec_tab_cur">收单信息</a>
        <a href="javascript:;" class="rec_tab">发布记录</a>
    </div>
    <div class="rec_con_wrap">
        <div class="rec_con">
            <s:iterator value="#request.detailList" var="dl">
            <div class="my_rel" id="${dl.ddid}" onclick="javascript:xp('${dl.ddid}');">
                <div class="may_rel_tit"><span>${dl.ddcontactphone}</span></div>
                <div class="may_rel_type"><span>行业/类别</span><span>${dl.ddworktype}</span></div>
                <c:if test="${dl.ddaddress != null && dl.ddaddress != ''}">
                    <div class="may_rel_site">${dl.ddaddress}</div>
                </c:if>
                <c:if test="${dl.ddstatus != null && dl.ddstatus == '0'}">
                    <a href="javascript:window.parent.location.href='<%=basePath%>/ea/dserve/ea_serveListByDdid.jspa?ddid=${dl.ddid}&wtvalue=${dl.ddworktype}&dlsccid=${dlsccid}&tle=1'" class="my_rel_btn">${dl.dscount}人抢单</a>
                </c:if>
                <c:if test="${dl.ddstatus != null && dl.ddstatus == '1'}">
                    <div class="may_rel_site">已确认订单</div>
                </c:if>
                <c:if test="${dl.ddstatus != null && dl.ddstatus == '2'}">
                    <div class="may_rel_site">已过期</div>
                </c:if>
                <c:if test="${dl.ddstatus != null && dl.ddstatus == '3'}">
                    <div class="may_rel_site">已移除</div>
                </c:if>
                <c:if test="${dl.ddstatus != null && dl.ddstatus == '4'}">
                    <div class="may_rel_site">已结单</div>
                </c:if>
            </div>
            </s:iterator>
        </div>
        <%--<div class="rec_con">
            <s:iterator value="#request.detailList2" var="dl2">
            <div class="my_rel">
                <div class="may_rel_tit"><span>${dl2.ddcontactphone}</span><span>${dl2.ddworktype}</span></div>
                <div class="may_rel_site">${dl2.ddaddress}</div>
                <div class="operation_wrap">
                    <script type="text/javascript">
                        console.log("%{#dl2.ddstatus}");
                    </script>
                    <s:if test="%{#dl2.ddstatus=='2'}">
                        <a href="###" class="state_finish">结束</a>
                    </s:if>
                    <s:elseif test="%{#dl2.ddstatus=='1'}">
                        <a href="###" class="daka_btn">未打卡</a>
                        <a href="javascript:;" class="cancel_btn">取消</a>
                    </s:elseif>
                </div>
            </div>
            </s:iterator>
        </div>--%>
    </div>
</div>

<div class="nest_page">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>详情</span>
    </div>
    <div class="nest_bd"></div>
</div>
<div class="btn">
    <a href="javascript:resource()">
        <img src="<%=basePath %>st/images/ico-jia2.png" alt="">
        <p>发布需求</p>
    </a>
</div>
<script>
    var staffid='${staffid}';
    var sccid = "${dlsccid}";
    var khd="${tle}";
    var basePath='<%=basePath%>';
    var originPage = '${param.originPage}';

    if (originPage != "" && originPage != null) {
        requestPage = originPage.split("-");
        if (requestPage[0] == "win") {
            $(".rec_con_wrap").css("padding-top","0rem");
            $(".rec_tab_wrap").hide();
        }
    }

    $(".rec_tab").click(function(){
        var _index = $(".rec_tab_wrap .rec_tab").index($(this));
        $(this).addClass("rec_tab_cur").siblings().removeClass("rec_tab_cur");
        $(".rec_con").eq(_index).show().siblings(".rec_con").hide();
    })
    function resource(){
        //document.location.href = basePath+"/ea/dserve/ea_toaddpage.jspa?staffid="+staffid+"&sccId="+sccid+"&tle="+khd;
        //window.parent.location.href=basePath + "/ea/dserve/ea_wtypeListBySccid.jspa?staffid="+staffid+"&sccId="+sccid;
        //window.open(basePath + "/ea/dserve/ea_wtypeListBySccid.jspa?originPage=win-fb&staffid="+staffid);
        window.parent.location.href=basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp?staffid="+staffid+"&sccId="+sccid;
    }

    $(function() {
        $(".rec_con").css("height",$(window).height()-$(".rec_tab_wrap").outerHeight()-$(".btn").outerHeight()+"px");
    })
</script>
</body>
</html>
