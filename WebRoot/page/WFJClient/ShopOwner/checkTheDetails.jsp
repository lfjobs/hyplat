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

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/qr_share.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/news_list.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
    <title>新闻内部列表</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sccId = '${sccId}';
        var conditions = '${conditions}';
        var ppids = '${ppk.ppID}';
        var imgs = '${object[3]}';
    </script>
</head>

<body>
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>签到送积分</h1>
</header>
<div class="wrap_page">
    <div class="article_tit_wrap">
        <div class="article_tit">${object[0]}</div>
        <div class="article_time">${object[1]} 丨 签到</div>

    </div>
    <div class="submit_html"></div>
    <input type="hidden" id="submit_val">
    <div class="article_con">

        <div class="article_p article_img" style="margin:10px auto 20px">
            <p>${object[2]}</p>
            <c:if test="${not empty object[3]}">
                <c:set var="var1" value="${object[3]}"/>
                <c:forEach var="tdv" items="${fn:split(var1,',')}">
                    <img style="text-align:center;max-width:100%;display:block;margin:10px auto 0" src="<%=basePath%>${fn:replace(fn:replace(tdv,'[',''),']','')}" alt="">
                </c:forEach>
            </c:if>
        </div>

        <div class="QR_float">
            <div class="o_white"></div>
            <div class="float_con"><span>你也能发这样的图文、视频</span><a href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa?sccid=${sccId}">马上制作</a><i></i></div>
        </div>
        <div class="article_attr clearfix">

            <a href="javascript:void(0);" class="art_bR" style="float: right;">分享</a>
        </div>
    </div>
    <!--前提引入news_list.css 文件-->
    <!--内嵌新闻内部列表 开始-->
    <%--<div class="article_p article_pro clearfix">
        <a href="###" class="pro">
            <img src="images/photo_s01_200x200.jpg" alt="">
            <span>产品标题，溢出隐藏</span>
            <span>￥200</span>
        </a>
        <a href="###" class="pro">
            <img src="images/photo_s02_200x200.jpg" alt="">
            <span>产品标题，溢出隐藏</span>
            <span>￥200</span>
        </a>
    </div>
    <a href="###" class="more_pro">点击查看更多产品</a>--%>
    <c:if test="${not empty tcc.qrcodePath}">
        <p class="article_QR">
            <img src="<%=basePath%>${tcc.qrcodePath}" alt=""><br>
            <span>【扫二维码,交换名片，加入微分金】</span>
        </p>
    </c:if>
    <c:if test="${not empty concom.pmCodePath}">
        <p class="article_QR">
            <img src="<%=basePath%>${concom.pmCodePath}" alt=""><br>
            <span>【扫二维码,关注公司微信公众号】</span>
        </p>
    </c:if>

    <div class="n_news_list">
        <div class="n_news_top">
            我的新闻分享
        </div>
        <c:forEach items="${list}" var="a">
            <a href="<%=basePath%>/ea/industry/ea_informationDetails.jspa?ppId=${a[0]}&ccompanyId=${a[1]}&type=web&miniSystemJudge=03" class="n_new_box clearfix">
                <img src="<%=basePath%>${a[2]}" class="n_new_img" alt="">
                <div class="n_new_text">
                    <div class="n_new_tit">
                            ${a[3]}
                    </div>
                    <div class="n_new_state">
                        <span class="n_new_name">${a[4]}</span>
                        <span class="n_new_time">${a[5]}</span>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>


    <!--内嵌新闻内部列表 结束-->

</div>
</body>
<script language="JavaScript">
    $(document).ready(function() {
        $(".art_bR").click(function() {
            var shareurl = basePath
                + "ea/mappointment/ea_checkTheDetails.jspa?";
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid==true){
                shareurl+="ppk.ppID="+ppids+"&sccId="+sccId+"&conditions="+conditions;
                Android.showShare("签到送积分","微分金数字地球联营平台",shareurl,null,"2");
            }else if(isiOS==true){
                var url= "func=" + 'integrationSharing';
                params={'title':'签到送积分','name':"微分金数字地球联营平台",'url':shareurl,'basePanth':null,'ppId':ppids,"sccId=":sccId,"conditions":conditions,"Markcallback":"3"};
                for(var i in params){
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        })
    })

</script>
</html>