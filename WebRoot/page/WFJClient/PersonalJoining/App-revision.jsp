<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>

    <title>数字地球</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="keywords" content="中联园区,微分金,中联园区微分金,数字地球,北京天太世统科技有限公司"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/WFJClient/myapp/swiper-3.3.1.min.css"></link>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/WFJClient/myapp/new_style.css"></link>
    <script type="text/javascript"
            src="<%=basePath%>js/WFJClient/myapp/jquery-1.9.1.min.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>js/font-size.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>page/newMyapp/js/swiper.min.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>js/WFJClient/myapp/app-revision.js"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>
    				<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    

    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var i;
        var c = 1;
        var d = 1;
        var q = 0;
        var times = 59;
        var acquiesceLoGo = "images/WFJClient/PersonalJoining/logo@2x.png";
        var ttoken = 0;
        var sccid='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
        var staffID='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
        var companyID='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';
    </script>

    <script></script>
</head>
<c:if test="${typeLeix=='pc' }">
    <jsp:forward page="/page/newMyapp/index.jsp" />
</c:if>
<body>
<!--logo-->
<div class="logo">
    <img src="<%=basePath%>images/home/logo.png" alt=""/>
</div>
<!--轮播banner-->
<div class="swiper-container index-swiper-container">
    <div class="swiper-wrapper">

        <c:forEach items="${newslist}" var="entity">

                <div class="swiper-slide">
                      <a class="banner_img" style="background: url('<%=basePath %>${entity[2]}');background-repeat:no-repeat;background-position:center center;background-size: 100%;width: 100%;height: 5rem;"  href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${entity[5]}&ccompanyId=&type=time&miniSystemJudge=03">

                    </a>
                </div>

        </c:forEach>


    </div>
    <div class="swiper-pagination"></div>


</div>

<!--搜索-->
<div class="search">
    <img src="<%=basePath%>images/home/ico-search.png" alt="">
    <input id="search" type="search" onfocus="this.value=''" onblur="this.value='搜索'" value="搜索">
</div>
<!--导航列表-->
<nav>
    <ul class="nav">
        <li>
            <img src="<%=basePath%>images/home/ico-1.png" alt="">
            <p>简介</p>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-2.png" alt="">
            <p>办公</p>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-3.png" alt="">
            <p>资讯</p>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-4.png" alt="">
            <p>商城</p>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-5.png" alt="">
            <p>会员</p>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-6.png" alt="">
            <p>商家</p>
        </li>
        <li>
            <a href="<%=basePath%>ea/purchasebids/ea_findbidIndexList.jspa">
                <img src="<%=basePath%>images/home/ico-7.png" alt="">
                <p>招标</p>
            </a>
        </li>
        <li>
            <img src="<%=basePath%>images/home/ico-8.png" alt="">
            <p>入驻</p>
        </li>
        <li>
            <a href="javascript:superjump();">
                <img src="<%=basePath%>images/home/ico-9.png" class="img-responsive center-block">
                <p>超市</p>
            </a>
        </li>
        <li>
            <a href="<%=basePath%>/page/ea/main/marketing/lottery/MeetingActivityList.jsp">
            <img src="<%=basePath%>images/home/ico-10.png" alt="">
            <p>活动</p>
            </a>
        </li>
        <li>
           <a href="javascript:phl();">
           <img src="<%=basePath%>images/home/phl.png" alt="">
              <p>批发市场</p>
         </a>
      </li> 
        <li>
        <img src="<%=basePath%>images/home/yc.png" alt="">
        <p>考场约车</p>
        </li>
         <li style="display:none;" class="kqin">
        <img src="<%=basePath%>images/home/sign.png" alt="">
        <p>考勤打卡</p>
        </li>
        <li>
            <a href="<%=basePath%>/ea/qyrz/ea_toPeriphery.jspa">
            <img src="<%=basePath%>images/home/ico-12.png" alt="">
            <p>周边经济</p>
        </a>
        </li>
    </ul>
</nav>
<hr>
<!--广告banner-->
<img src="<%=basePath%>images/home/poster.png" alt="" width="100%">
<section>
    <!--联营平台-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-123.png" alt="">
            <a href="<%=basePath%>/page/WFJClient/PersonalJoining/AddPlatformColumn.jsp"><img src="<%=basePath%>images/home/ico-add.png" alt="" class="right"></a>
            <p>共享市场</p>
        </div>
        <ul class="Consortium_con">
        </ul>
    </div>
    <!--招商-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-member.png" alt="">
            <p>招商</p>
            <a onclick="addbus('ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform')" class="more">更多>></a>
        </div>
        <ul class="mem_con">
        </ul>
    </div>
    <hr>
    <!--商家-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-merchant.png" alt="">
            <p>商家</p>
            <a onclick="addbus('ea/industry/ea_getAllCompanyList.jspa')" class="more">更多>></a>
        </div>
        <ul class="mer_con"></ul>
    </div>
    <hr>
    <!--商城-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-store.png" alt="">
            <p>商城</p>
            <a onclick="addbus('ea/digitalmall/ea_DigitalMall.jspa')" class="more">更多>></a>
        </div>
        <ul class="sto_con"></ul>
    </div>
    <hr>
    <!--招标-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-bids.png" alt="">
            <p>招标</p>
            <a onclick="addbus('ea/purchasebids/ea_findGoodbidList.jspa')" class="more">更多>></a>
        </div>
        <ul class="bids_con">
        </ul>
    </div>
    <hr>
    <!--新闻资讯-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-news.png" alt="">
            <p>新闻资讯</p>
            <a onclick="addbus('ea/wfjshop/ea_getNewsList.jspa')" class="more">更多>></a>
        </div>
        <ul class="news_con">
        </ul>
    </div>
    <hr>
    <!--招聘-->
    <div class="list">
        <div class="tit">
            <img src="<%=basePath%>images/home/ico-recruit.png" alt="">
            <p>招聘</p>
            <a onclick="addbus('ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs')" class="more">更多>></a>
        </div>
        <ul class="rec_con">

        </ul>
    </div>
</section>
<footer>
    <p onclick="showDeviceID()">Copyright © 2010-2018北京天太世统科技有限公司<br/><a href="https://beian.miit.gov.cn/" target="_blank">京ICP备11016224号-2</a></p>
</footer>
<c:if test = "${param.pc eq 'y'}">
	<div class="div-bottom">
					<ul class="clearfix">
						<li>
							<p>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_34.png"/>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_23.png"/>
							</p>
							<p>
								通讯消息
							</p>
						</li>
						<li  class="active">
							<p>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_20.png"/>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_33.png"/>
							</p>
							<p>
								数字地球
							</p>
						</li>
						<li >
							<p>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_26.png"/>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_35.png"/>
							</p>
							<p>
								5L5C
							</p>
						</li>
						<li>
							<p>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_17.png"/>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_32.png"/>
							</p>
							<p>
								我的中心
							</p>
						</li>
					</ul>
				</div>
				</c:if>
<script>
    var mySwiper = new Swiper ('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay:{
            delay: 3000,
            disableOnInteraction: false,
        }
        ,
        //分页器
        pagination: {
            el: '.swiper-pagination',
        },
    })


    function showDeviceID() {
        try {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                Android.showDeviceId();
            }
        }catch(error){


        }
    }
</script>
</body>
</html>
