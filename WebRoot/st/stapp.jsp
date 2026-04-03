<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html  lang="en" style="font-size:62.5%;">
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>${type=="0"? ccom.companyName:ccom[1]}</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/news_list.css"></link>
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>

    <script type="text/javascript">
        var basePath='<%=basePath%>';
    </script>
    <script type="text/javascript" src="<%=basePath %>st/js/stapplist.js"></script>

</head>

<body>

<header>
    <ul>
        <li style="width: 10%;" >
            <a onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath %>st/images/left_jt.png"  id="ret"></a>
        </li>
        <li style="width: 70%;text-indent: 10%;">${type=="0"? ccom.companyName:ccom[1]}</li>
        <li style="width: 20%;" class="new_location"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <!-- <div class="friend frd" id="top">
                 <img src="<%=basePath %>st/images/shequ2.png" alt="" id="logo">
                 <div class="txt">
                     <h4>四川胜威驾校</h4>
                     <p>奔驰</p>
                 </div>
             </div>-->
            <ul class="ico_grd">
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}&type=web&miniSystemJudge=00">
                        <img src="<%=basePath %>st/images/ico_1.png" alt="">
                            <p>平台简介</p></a>
                    </li>
                </a>
                <a href="#;">
                    <li>
                        <img src="<%=basePath %>st/images/ico_2.png" alt="">
                        <p>活动</p>
                    </li>
                </a>

                <c:choose>
                    <c:when test="${applied eq '00' }">
                        <a>
                            <li>
                                <a href="<%=basePath %>/ea/coachreserv/ea_registration.jspa?companyId=${companyId}&staffId=${staffid}&flag=1">
                                    <img src="<%=basePath %>st/images/ico_6.png" alt="">
                                    <p>预约学车</p>
                                </a>
                            </li>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="#apply">
                            <li>
                                <a href="<%=basePath %>/ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&enroll=enroll&ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                                    <img src="<%=basePath %>st/images/ico_3.png" alt="">
                                    <p>我要报名</p>
                                </a>
                            </li>
                        </a>
                    </c:otherwise>
                </c:choose>
                <a href="#;">
                    <li>
                        <a href="#;" class="jishi">
                            <img src="<%=basePath %>st/images/jishilianche.png" alt="">
                            <p>计时练车</p>
                        </a>
                    </li>
                </a>
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>/ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                        <img src="<%=basePath %>st/images/ico_4.png" alt="">
                        <p>联营驾校</p>
                        </a>
                    </li>
                </a>
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>/st/enroll/ea_News.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                        <img src="<%=basePath %>st/images/ico_5.png" alt="">
                        <p>平台新闻</p>
                        </a></li>
                </a>
                <%--<a href="#;">--%>
                    <%--<li>--%>
                        <%--<img src="<%=basePath %>st/images/ico_6.png" alt="">--%>
                        <%--<p>预约学车</p>--%>
                    <%--</li>--%>
                <%--</a>--%>
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>ea/wfjshop/ea_getpk.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                        <img src="<%=basePath %>st/images/ico_7.png" alt="">
                            <p>联营招商</p></a>
                    </li>
                </a>
                <%--<a href="#;">--%>
                    <%--<li>--%>

                        <%--<img src="<%=basePath %>st/images/ico_8.png" alt="">--%>
                        <%--<p>周边服务</p>--%>
                    <%--</li>--%>
                <%--</a>--%>
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>st/enroll/ea_getByTeam.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">
                        <img src="<%=basePath %>st/images/ico_9.png" alt="">
                        <p>团队展示</p>
                        </a>
                    </li>
                </a>
                <a href="#;">
                    <li>
                        <a href="<%=basePath %>st/enroll/ea_getByEqpt.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">
                        <img src="<%=basePath %>st/images/ico_10.png" alt="">
                        <p>设备展示</p>
                        </a>
                    </li>
                </a>
                <%--<a href="#;">--%>
                    <%--<li>--%>
                        <%--<img src="<%=basePath %>st/images/ico_11.png" alt="">--%>
                        <%--<p>场地展示</p>--%>
                    <%--</li>--%>
                <%--</a>--%>
                <a href="#;">
                    <li>
                        <a href="javascript:resource()">
                        <img src="<%=basePath %>st/images/ico-jia2.png" alt="">
                            <p>加资源</p>
                        </a>
                    </li>
                </a>
            </ul>
            <!--活动-->
            <div class="activity-mil" id="activity">
                <div class="title">
                    <img src="<%=basePath %>st/images/ico-activity.png">
                    <span>活动</span>
                </div>
                <div class="list">
                    <div class="tit">
                        <h5><i></i>积分活动</h5>
                    </div>
                    <div class="left">
                        <ul id="signlist">
                        </ul>
                    </div>
                    <div class="right">
                        <img src="<%=basePath %>st/images/activity1.png">
                    </div>
                </div>
                <div class="list">
                    <div class="tit">
                        <h5><i></i>抽奖活动</h5>

                    </div>
                    <div class="left">
                        <ul id="relist">
                        </ul>
                    </div>
                    <div class="right">
                        <img src="<%=basePath %>st/images/activity2.png">
                    </div>
                </div>
            </div>
            <!--我要报名-->
            <%--<div class="apply" id="apply">--%>
                <%--<div class="ind_title">--%>
                    <%--<img src="<%=basePath %>st/new_images/bg.png">--%>
                    <%--<p>我要报名<span>（商城）</span></p>--%>
                <%--</div>--%>
                <%--<!--计时收费-->--%>
                <%--<div class="mil">--%>
                    <%--<div class="title">--%>
                        <%--<h5><i></i>计时收费（学多少扣多少）</h5>--%>
                        <%--<a href="#;" class="more"></a>--%>
                    <%--</div>--%>
                    <%--<ul id="timekeeping">--%>

                    <%--</ul>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;<p class="query">计时查询 付费查询</p>&ndash;%&gt;--%>
                <%--<!--一次性收费-->--%>
                <%--<div class="mil">--%>
                    <%--<div class="title">--%>
                        <%--<h5><i></i>一次性收费</h5>--%>
                        <%--<a href="#;" class="more"></a>--%>
                    <%--</div>--%>
                    <%--<ul id="once"></ul>--%>
                <%--</div>--%>
                <%--<!--预约培训-->--%>
                <%--<div class="mil">--%>
                    <%--<div class="title">--%>
                        <%--<h5><i></i>预约培训（已缴费的预约培训）</h5>--%>
                        <%--<a href="#;" class="more"></a>--%>
                    <%--</div>--%>
                    <%--<ul id="subscribe">--%>

                    <%--</ul>--%>
                <%--</div>--%>
                <%--<!--计时先学后付-->--%>
                <%--<div class="mil">--%>
                    <%--<div class="title">--%>
                        <%--<h5><i></i>计时先学后付</h5>--%>
                        <%--<a href="#;" class="more"></a>--%>
                    <%--</div>--%>
                    <%--<ul id="afterpay"></ul>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;<p class="query">计时查询 付费查询</p>&ndash;%&gt;--%>
            <%--</div>--%>
            <!--新闻-->
            <div class="activity-mil activity-news">
                <div class="title">
                    <img src="<%=basePath %>st/images/ico-news.png">
                    <span>新闻</span>
                </div>
                <ul class="new">

                </ul>

            </div>

            <hr class="activity-hr">
            <!--名优教练-->
            <%--<div class="activity-mil activity-coach">--%>
                <%--<div class="title">--%>
                    <%--<img src="<%=basePath %>st/images/ico-coach.png">--%>
                    <%--<span>名优教练</span>--%>
                <%--</div>--%>
                <%--<div class="list">--%>
                    <%--<div class="tit">--%>
                        <%--<a href="#;">更多></a>--%>
                    <%--</div>--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/coach.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<div class="left">--%>
                                    <%--<h5>张教练</h5>--%>
                                    <%--<h4><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"></h4>--%>
                                    <%--<p>北京远大驾校</p>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<input type="button" value="预约" class="">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/coach.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<div class="left">--%>
                                    <%--<h5>张教练</h5>--%>
                                    <%--<h4><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"></h4>--%>
                                    <%--<p>北京远大驾校</p>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<input type="button" value="预约" class="">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/coach.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<div class="left">--%>
                                    <%--<h5>张教练</h5>--%>
                                    <%--<h4><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"></h4>--%>
                                    <%--<p>北京远大驾校</p>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<input type="button" value="预约" class="">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/coach.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<div class="left">--%>
                                    <%--<h5>张教练</h5>--%>
                                    <%--<h4><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"><img src="<%=basePath %>st/images/ico-star.png"></h4>--%>
                                    <%--<p>北京远大驾校</p>--%>
                                <%--</div>--%>
                                <%--<div class="right">--%>
                                    <%--<input type="button" value="预约" class="">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<hr class="activity-hr">--%>
            <%--<!--周边服务-->--%>
            <%--<div class="activity-mil activity-news activity-ambitus">--%>
                <%--<div class="title">--%>
                    <%--<img src="<%=basePath %>st/images/ico-ambitus.png">--%>
                    <%--<span>周边服务</span>--%>
                <%--</div>--%>
                <%--<div class="list">--%>
                    <%--<div class="tit">--%>
                        <%--<a href="#;">更多></a>--%>
                    <%--</div>--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus1.png" alt="">--%>
                            <%--<p>周边超市</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus2.png" alt="">--%>
                            <%--<p>周边酒店</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus3.png" alt="">--%>
                            <%--<p>周边饭店</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus4.png" alt="">--%>
                            <%--<p>周边家政</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus5.png" alt="">--%>
                            <%--<p>周边洗车</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/ambitus6.png" alt="">--%>
                            <%--<p>周边维修</p>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<hr class="activity-hr">--%>
            <%--<!--汽车快捷服务-->--%>
            <%--<div class="activity-mil activity-car">--%>
                <%--<div class="title">--%>
                    <%--<img src="<%=basePath %>st/images/ico-car.png">--%>
                    <%--<span>汽车快捷服务</span>--%>
                <%--</div>--%>
                <%--<div class="list">--%>
                    <%--<div class="tit">--%>
                        <%--<a href="#;">更多></a>--%>
                    <%--</div>--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>汽车后市场</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>汽车租赁</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>汽车修理</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>汽车保险</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>4S店</p>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<p>汽车陪练</p>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<hr class="activity-hr">--%>
            <%--<!--驾校入住-->--%>
            <%--<div class="activity-mil activity-coach activity-Driving">--%>
                <%--<div class="title">--%>
                    <%--<img src="<%=basePath %>st/images/ico-Driving.png">--%>
                    <%--<span>驾校入住</span>--%>
                <%--</div>--%>
                <%--<div class="list">--%>
                    <%--<div class="tit">--%>
                        <%--<a href="#;">更多></a>--%>
                    <%--</div>--%>
                    <%--<ul>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<h5>北京远大驾校</h5>--%>
                                <%--<p>800m</p>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<h5>北京远大驾校</h5>--%>
                                <%--<p>800m</p>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<h5>北京远大驾校</h5>--%>
                                <%--<p>800m</p>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<img src="<%=basePath %>st/images/car.png" alt="">--%>
                            <%--<div class="text">--%>
                                <%--<h5>北京远大驾校</h5>--%>
                                <%--<p>800m</p>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<img src="<%=basePath %>st/images/banner-Driving.png" width="100%">--%>
            <a href="#top" id="return"><img src="<%=basePath %>st/images/return.png" alt=""></a>
        </div>
    </div>
</div>

<script type="text/javascript">

    var basePath='<%=basePath%>';
    var companyId='${companyId}';
    var ccompanyId='${type=="0"? ccompanyId:ccom[2]}';
    var companyname = '${search}';
    var pageNumber = '${pageNumber}';
    var pageSize = '${pageSize}';

    $(document).ready(function(){
        getPrizeActivity();

        //查看该公司是否有抽奖
        $.ajax({
            url : basePath + "ea/lottery/sajax_ea_ajaxIsDraw.jspa?model.companyId=" + companyId,
            type : "get",
            async : false,
            success : function (data) {
                var str = new Array();
                var member = eval("(" + data + ")");
                if (member.model != null)
                {
                    str.push('<li>');
                    str.push('<a href="' + basePath + 'ea/lottery/ea_goLottery.jspa?model.companyId='
                        + companyId + '&model.activityId=' + member.model.activityId + '&ccompanyId=' + ccompanyId + '">');
                    str.push('<img src="'+basePath+'images/WFJClient/PersonalJoining/companyHomepage/lottery.png" alt="">');
                    str.push('<p>抽奖</p>');
                    str.push('</a></li>');
                    $(".ico_grd li:last").before(str.join(""));
                }
            }
        })
        $.ajax({
            url : basePath + "/ea/android/sajax_ea_addPersonKuaiJie.jspa?companyid="+companyId,
            type : "post",
            dataType : "json",
            success : function (data) {
                console.log(data);
            }
        });
    });
    function getPrizeActivity(){
        var url = basePath + "/ea/bonuspoints/sajax_getPrizeActivity.jspa?ccompanyId="+ccompanyId;
        $.ajax({
            url:url,
            type:"get",
            dataType : "json",
            success : function (data) {
                var str = new Array();
                //标识（判断是否有签到活动）
                var mark = data.flag;
                if(mark == "sign"){
                    str.push('<li>');
                    str.push('<a href="'+basePath+'ea/bonuspoints/ea_getSign.jspa?ccompanyId='+ccompanyId+'">');
                    str.push('<img src="'+basePath+'images/WFJClient/PersonalJoining/companyHomepage/bp.png" alt="">');
                    str.push('<p>签到</p>');
                    str.push('</a></li>');
                    $(".ico_grd li:last").before(str.join(""));
                }

            }
        });
    }
    function back() {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        try {
        if (isAndroid == true) {
               console.log("安卓");
               Android.callAndroidjianli();//调用安卓接口

        }else if (isiOS == true) {
            var  str=calliosOrder();
            var url= "func=" + 'iossavephotos';
            params={'saveImage':str};
            for(var i in params){
                url = url + "&" + i + "=" + params[i];
                 console.log(url);
            }
        }
        }catch(e){
            window.history.go(-1);return false;
        }
    }


    $("header").css("height",$(window).height()*0.08-1+"px");
    $("header").css("line-height",$(window).height()*0.08-1+"px");
    $("header li").css("height",$(window).height()*0.08-1+"px");
    $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
    $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
    $(".head_top").css("height",$(window).height()*0.08-1+"px");
    $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
    $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
    $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
    /*$(".con").css("height",$(window).height()*0.828+"px");*/
    $(".con").css("height",$(window).height()*0.92+"px");


</script>
<script>
    $(".jishi").click(function() {

        var url = basePath + "/ea/elkcRecord/sajax_ea_ajaxTimingToPracticeCar.jspa";
        $.ajax({
            url:url,
            type:"post",
            dataType : "json",
            success : function (data) {
                var jishi = eval("(" + data + ")");
                var status = jishi.map.status;
                var subject = jishi.map.object;
                if (status=="00"){
                    return alert("未登录");
                }else if (status=="01"){
                    return alert("未报名");
                }else if (status=="02"){
                    return alert("未预约");
                }else if (status=="03"){
                    return alert("时间未到");
                }else if (status=="04"){

                    var u = window.navigator.userAgent;
                    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                    var  card="620523198607120855";
                    if(isAndroid==true){
                        Android.changetoJiShiLianChe("620523198607120855",subject);
                    }else if(isiOS==true){
                        var url= "func=" + 'jslc';
                        params={'card':card,'subject':subject};
                        for(var i in params){
                            url = url + "&" + i + "=" + params[i];
                        }
                        window.webkit.messageHandlers.Native.postMessage(url);
                    }
                }
            }
        });
    })

</script>
</body>
</html>
