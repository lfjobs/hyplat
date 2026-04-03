<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>公司招聘</title>
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/idangerous.swiper.css"  type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/companyWebsite.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/idangerous.swiper.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toHeadType.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ccompanyId = '${ccompanyId}';
		var titleJudge = '${titleJudge}';

        var pageNumber = 1;
        var pageCount;

	</script>
</head>
<body>
<div id="header" class="header2">
    <ul>
        <li class="logo">
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=00&ccompanyId=${ccompanyId}"><img src="<%=basePath%>${ccom.logoPath}" alt="" class="log"></a>
        </li>
        <li class="nav">
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=00&ccompanyId=${ccompanyId}" class="homePage">首页</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=00&ccompanyId=${ccompanyId}" class="companyShoppingMall">联营商城</a>
            <%--<a href="<%=basePath%>" class="companyMarketing">联营营销</a>--%>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=03&seven=00&ccompanyId=${ccompanyId}" class="companyChinaMerchants">联营招商</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=04&seven=00&ccompanyId=${ccompanyId}" class="companyNews">公司新闻</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=05&seven=00&ccompanyId=${ccompanyId}" class="companyRecruit">公司招聘</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=06&seven=00&ccompanyId=${ccompanyId}" class="companyAboutUs">关于我们</a>
        </li>
    </ul>
</div>
<div class="content" style="background-color: #f5f5f5;padding-top: 30px;margin-top: 0;">
    <div class="nav" style="margin-top: 0;"><a href="#;">公司招聘>职位搜索></a><a href="#;" class="present">${map.resultObj[0]}（${map.resultObj[1]}）</a></div>
    <div class="Certificate_con Cert_det">
        <div class="left">
            <div class="select">
                <div class="details_con">
                    <h3 class="tit" style="width: 100%;">${map.resultObj[0]}（${map.resultObj[1]}）</h3>
                    <div class="text_con">
                        <ul class="name">
                            <li>工作地区：${map.resultObj[2]}</li>
                            <li>职位类别：${map.resultObj[3]}</li>
                            <li>招聘人数：${map.resultObj[4]}人</li>
                        </ul>
                        <div class="text1">
                            <h5>工作内容：</h5>
                            <p>${map.resultObj[5]}</p>
                        </div>
                        <%-- <div class="text1">
                            <h5>工作要求：</h5>
                            <p>${map.resultObj[5]}</p>
                        </div> --%>
                        <button>申请岗位</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--尾部-->
<div id="footer">
    <ul class="footer_con">
        <li class="left">
            <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico_footer.png">
            <p>（9:00-21:00）</p>
            <h5>联系电话：${ccom.companyTel}</h5>
            <!-- <p class="qq">qq联系方式：258506508</p>-->
        </li>
        <li class="centre">
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=00&ccompanyId=${ccompanyId}">首页</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=00&ccompanyId=${ccompanyId}">联营商城</a>
            <a href="<%=basePath%>">联营营销</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=03&seven=00&ccompanyId=${ccompanyId}">联营招商</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=04&seven=00&ccompanyId=${ccompanyId}">公司新闻</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=05&seven=00&ccompanyId=${ccompanyId}">公司招聘</a>
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=06&seven=00&ccompanyId=${ccompanyId}">关于我们</a>
        </li>
        <li class="code">
            <img src="<%=basePath%>${ccom.qrCodePath}">
        </li>
        <p>
            蜀ICP备17020262号-1
        </p>
    </ul>
</div>
<!--返回顶部-->
<a href="#header" class="return"><img src="<%=basePath%>images/BuildPlatform/return.png" alt=""></a>

<script>
    $(document).ready(function(){
        $("#focus-banner").css("height","350px");
        $("#focus-banner-list li a").css("height","350px");
        $(document).on("click", ".shop-industry .ind_right .yes", function() {
            $(".shop-industry").attr("style","height:auto;ovflow:auto;");

            $(".shop-industry .ind_right").addClass("more_")
        });
        $(document).on("click", ".shop-industry .ind_right .no", function() {
            $(".shop-industry").attr("style","height:90px;ovflow:hidden;");

            $(".shop-industry .ind_right").removeClass("more_")
        });

        /*综合排序*/
        $(document).on("click", ".shop-sort button", function() {
            $(this).addClass("active").siblings().removeClass("active");
            search = $(this).attr("data-name");
            loaded();
        });

        /*分页*/
        $(document).on("click", ".mil_shop_con .shop_page li:not(.point)", function() {
            $(this).addClass("active").siblings().removeClass("active");
        });

        /*网站咨询/热门活动切换*/
        $(document).on("click", ".Web_news #web_news", function() {
            $(".Web_news_con .web_news").show().siblings().hide();
        });
        $(document).on("click", ".Web_news #hot_events", function() {
            $(".Web_news_con .hot_events").show().siblings().hide();
        });


        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {
                $(".return").hide();
            }
        });
    })
</script>
</body>
</html>