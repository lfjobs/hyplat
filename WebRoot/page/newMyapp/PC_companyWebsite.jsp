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
    <title>网站首页</title>
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/idangerous.swiper.css"  type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/companyWebsite.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>/page/newMyapp/js/idangerous.swiper.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toHeadType.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/PC_companyWebsite.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ccompanyId = '${ccompanyId}';
		var titleJudge = '${titleJudge}';
		var companyId;
		var news = '${news[0][5]}';

	</script>
</head>
<body>
<div id="header">
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
<div class="content">
    <!--banner图-->
    <c:if test="${list!=null}">
        <div class="swiper-container swiper-container_home">
            <a id="btn1"><img src="<%=basePath %>/page/newMyapp/images/newPCHomepage/ico-left.png"></a>
            <a id="btn2"><img src="<%=basePath %>/page/newMyapp/images/newPCHomepage/ico-right.png"></a>
            <div class="swiper-wrapper">
                <c:forEach var="s" items="${list}">
                    <div class="swiper-slide"><img src="<%=basePath %>${s[0]}"></div>
                </c:forEach>
            </div>
            <div class="pagination"></div>
        </div>
    </c:if>

    <!--联营商城-->
    <div id="shoppingMall" class="mil">
        <div class="mil_head">
            <h3>联营商城<span>/ PUDOCTS</span></h3>
            <h5>我们坚持致力于为客户提供优良的服务</h5>
        </div>

        <div class="activity_mil">
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=00&ccompanyId=${ccompanyId}" class="more">更多>></a>
            <ul>
                <%--js 拼接--%>
            </ul>
        </div>
    </div>

    <!--新闻资讯-->
    <div id="news" class="mil">
        <div class="mil_head">
            <h3>新闻资讯<span>/ NEWS</span></h3>
            <h5>我们持续为您提供有价值的信息</h5>
        </div>
        <div class="message_mil">
            <a href="<%=basePath%>/ea/newpcend/ea_companyWebsite.jspa?titleJudge=04&seven=00&ccompanyId=${ccompanyId}" class="more">更多>></a>
            <ul>
                <%--js拼接--%>
            </ul>
        </div>
    </div>


    <!--公司简介-->
    <c:if test="${news!=null}">
        <div id="profile" class="mil">
            <div class="mil_head">
                <h3>公司简介<span>/ OURS</span></h3>
                <h5>我们坚持践行“一切为了学员”的理念</h5>
            </div>
            <div class="txt">
                <p></p>
            </div>
        </div>
    </c:if>

</div>
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
        $(".swiper-container_home").css("height",$(".swiper-container_home .swiper-wrapper img").height()+"px");
        var mySwiper = new Swiper('.swiper-container_home',{
            autoplay : 3000,
            loop : true,
            pagination : '.pagination',
            paginationClickable :true,
            autoplayDisableOnInteraction : false,
        });
        $(document).on("click","#btn1",function(){
            mySwiper.swipePrev();
        });
        $(document).on("click","#btn2",function(){
            mySwiper.swipeNext();
        });

        //显示简介
        if(news!=null && news!=""){
            var ns = news.split("</p>");
            var sp = [];
            $(ns).each(function(i,dom){
                sp.push(this.split("<p>")[1]);
            })
            var s = sp.join(",");
            $("#profile p").html(s.substring(0,s.length-1));
        }

    })
</script>

<script>
    $(document).ready(function(){

        $(document).on("click","#header ul .nav a",function(){
            $(this).addClass("active").siblings().removeClass("active");
        });



        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1000) {
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