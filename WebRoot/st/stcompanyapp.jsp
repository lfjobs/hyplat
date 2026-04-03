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
    <title>${ccom.companyName}</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath %>";
    </script>
    <script type="text/javascript" src="<%=basePath %>st/js/stcompanyapplist.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/swiper.min.css">
    <script src="<%=basePath%>js/swiper.min.js"></script>

</head>

<body>



<header>
    <ul>
        <li style="width: 10%;">
            <a><img src="<%=basePath %>st/images/left_jt.png" id="ret"></a>
        </li>
        <li style="width: 70%;text-indent: 10%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${ccom.companyName}</li>
    </ul>
</header>


<div class="content_hidden">
    <div class="swiper-container" id="swiper-ord">
        <div class="swiper-wrapper">
            <c:forEach items="${list }" var="li" varStatus="l">
                <c:choose>
                    <c:when test="${li[6] == '会员分享' }">
                        <a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
                            <span class="banner_tit">${li[0] }</span>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="<%=basePath%>ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=${li[8] }&search=${search }&goodsid=${li[3] }" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
                            <span class="banner_tit">${li[0] }</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div class="swiper-pagination"></div>
    </div>
    <div class="content">
        <div class="con">
            <ul class="ico_grd">
                    <li>
                        <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${ccompanyId}&type=web&miniSystemJudge=00">
                            <img src="<%=basePath %>st/images/ico_1.png" alt="">
                            <p>公司简介</p></a>
                    </li>
                    <li>
                        <img src="<%=basePath %>st/images/ico_2.png" alt="">
                        <p>活动</p>
                    </li>

                <c:choose>
                    <c:when test="${applied eq '00' }">
                            <li>
                                <a href="<%=basePath %>/ea/coachreserv/ea_registration.jspa?companyId=${stCompany[1]}&staffId=${staffid}&flag=1">
                                    <img src="<%=basePath %>st/images/ico_6.png" alt="">
                                    <p>我要预约</p>
                                </a>
                            </li>
                    </c:when>
                    <c:otherwise>
                            <li>
                                <a href="<%=basePath%>st/enroll/ea_getAssociatedMall.jspa?companyID=${stCompany[1]}&companyName=${ccom.companyName}">
                                    <img src="<%=basePath %>st/images/ico_3.png" alt="">
                                    <p>我要报名</p>
                                </a>
                            </li>
                    </c:otherwise>
                </c:choose>
                    <li>
                        <%--<a href="<%=basePath%>/st/enroll/ea_getAssociatedMall.jspa?allPro=1&companyID=${stCompany[1]}">--%>
                        <a href="<%=basePath%>ea/industry/ea_CompanyProducts.jspa?ccompanyId=${ccompanyId}&pos=${param.pos}">
                        <img src="<%=basePath %>st/images/ico_4.png" alt="">
                        <p>联营商城</p>
                        </a>
                        <%--</a>--%>
                    </li>
                    <li>
                        <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${ccompanyId}&type=web&miniSystemJudge=02">
                            <img src="<%=basePath %>st/images/ico_5.png" alt="">
                            <p>公司新闻</p>
                        </a></li>
                    <li>
                        <a href="<%=basePath %>st/enroll/ea_getByTeam.jspa?photo=${stCompany[3]}&companyID=${stCompany[1]}&companyName=${stCompany[2]}">
                        <img src="<%=basePath %>st/images/ico_9.png" alt="">
                        <p>团队展示</p>
                        </a>
                    </li>
                    <li>
                        <a href="<%=basePath %>st/enroll/ea_getByEqpt.jspa?photo=${stCompany[3]}&companyID=${stCompany[1]}&companyName=${stCompany[2]}">
                        <img src="<%=basePath %>st/images/ico_10.png" alt="">
                        <p>设备展示</p>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:resource()">
                            <img src="<%=basePath %>st/images/ico-jia2.png" alt="">
                            <p>加资源</p>
                        </a>
                    </li>
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
            <%--<hr class="activity-hr">--%>
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

            <c:if test="${stCompany[1]=='company201009046vxdyzy4wg0000000130'}">
                <footer>
                    <p><a href="https://beian.miit.gov.cn/">蜀ICP备17020262号</a></p>
                </footer>
            </c:if>
        </div>
    </div>
</div>


<script type="text/javascript">
    var basePath='<%=basePath%>';
    var companyId='${stCompany[1]}';
    var ccompanyId='${ccompanyId}';
    var companyname = '${search}';
    var pageNumber = '${pageNumber}';
    var pageSize = '${pageSize}';
    //活动

    $(document).ready(function(){
        var reurl = basePath + "ea/lottery/sajax_ea_ajaxIsDraw.jspa?model.companyId=" + companyId;
        $.ajax({
            url : encodeURI(reurl),
            type : "get",
            async : false,
            dataType : "json",
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
                    $(".last").removeClass("last");
                    $("#relist").append('<a href="' + basePath + 'ea/lottery/ea_goLottery.jspa?model.companyId='
                        + companyId + '&model.activityId=' + member.model.activityId + '&ccompanyId=' + ccompanyId + '">'+
                        '<li class="activity" style="float: none; font-size: 0.7rem;text-decoration: underline;line-height: 1.5rem;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;height: 1.5rem;">'+member.model.activityName+'</li>');
                }else {
                    $("#relist").append('<li><span>暂无抽奖活动</span></li>')
                }
            }
        });
        $.ajax({
            url : basePath + "/ea/android/sajax_ea_addPersonKuaiJie.jspa?companyid="+companyId,
            type : "post",
            dataType : "json",
            success : function (data) {
                console.log(data);
            }
        });
    })


</script>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
//        $("header").css("position","fixed");
//        $("body").css("margin-top",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height",$(window).height()*0.92+"px");




    });
</script>
<script>
    /*banner轮播*/
    var mySwiper1 = new Swiper('#swiper-ord', {
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: false,
        },
        loop : true,
        pagination: {
            el: '#swiper-ord .swiper-pagination',
            type: 'bullets',
            bulletElement : 'li',
            clickable: false,

        },


    });
</script>

</body>
</html>
