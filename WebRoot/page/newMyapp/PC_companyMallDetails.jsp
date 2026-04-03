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
    <title>商城详情</title>
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
    <script src="<%=basePath %>js/ea/pcwfj/PC_goodsDetails.js" type="text/javascript"></script>
    <script>
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ccompanyId = '${ccompanyId}';
		var titleJudge = '${titleJudge}';


        var basePath = '<%=basePath%>';
        var pageNumber = 0;
        var pageCount;
        var ppID = '${map.obj[1]}';
        var goodsId='${map.obj[4]}'
        var goodsname='${map.obj[0]}';
        var companyId='${map.obj[5]}';
        var type='${map.obj[6]}';
        var pptlength = '${fn:length(map.ppt)}';
        var colorlength = '${fn:length(map.color)}';
        var sizelength = '${fn:length(map.size)}';
        var size='${map.size}';
	</script>
</head>
<body>
<div id="header" style="border-bottom: 1px solid #ddd;">
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
    <!--数字地球商城-->
    <div class="detail_page">
        <h5 class="top_text">数字地球商城 > ${map.obj[0]} </h5>

        <div class="det_mil">
            <div class="left">
                <!--单图-->
                <!-- <img src="new_images/shop.png" alt="">-->
                <!--多图-->
                <a href="#;"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/shop_left.png" alt="" class="left_ swiper-button-prev2"></a>
                <a href="#;"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/shop_right.png" alt="" class="right_ swiper-button-next2"></a>
                <div class="swiper-container swiper-container-det">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <img src="<%=basePath%>${map.obj[3]}" alt="">
                        </div>
                        <c:forEach items="${map.images }" var="s">
                            <div class="swiper-slide">
                                <img src="<%=basePath%>${s.imgurl}" alt="">
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="swiper-pagination"></div>
                <script>
                    /*多图*/
                    var mySwiper2 = new Swiper('.swiper-container-det', {
                        autoplay: 3000,//可选选项，自动滑动
                        loop: true,
                        simulateTouch : false,
                        pagination : '.swiper-pagination',
                        paginationClickable :true,
                        prevButton:'.swiper-button-prev2',
                        nextButton:'.swiper-button-next2',
                    })
                </script>
            </div>
            <div class="right">
                <form id="mainForm" method="post">
                    <h1 class="name">${map.obj[0]}</h1>
                    <div class="money">
                        <h1>&yen;<span class="mon">${map.obj[2]}</span></h1>
                        <div class="bottom">
                            <p class="freight">运费<span>免运费</span></p>
                            <p class="sales">月销量<span>${map.obj[7]}</span>笔</p>
                            <p class="sales">${map.city}</p>
                        </div>
                    </div>
                    <c:forEach items="${map.goodsStandard}" var="goodsStandard">
                        <div class="color">
                            <h4>${goodsStandard.key}
                                <input type="hidden" class="attriname" />
                            </h4>
                            <ul class="xuan _color">
                                <c:forEach items="${goodsStandard.value}" var="c">
                                    <li>${c}
                                        <input type="hidden" class="attrivalue" /></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                    <div class="color">
                        <h4>数量</h4>
                        <div class="nub">
                            <button id="cut" type="button"></button>
                            <input type="text" value="1" readonly="readonly" name="count" id="num">
                            <button id="add" type="button"></button>
                        </div>
                    </div>
                    <c:if test="${fn:length(map.ppt)!=0}">
                        <div class="color gift">
                            <h4>赠品</h4>
                            <div class="gift_con">
                                <ul>
                                    <c:forEach items="${map.ppt}" var="p">
                                        <li class="list">
                                            <i></i>
                                            <img src="<%=basePath%>${p[3]}" class="gift">
                                            <div class="text">
                                                <input type="hidden" class="ptppidHide" value="${p[1]}" />
                                                <input type="hidden" name="ptppid" class="ptppids" />
                                                <input type="hidden" name="ptStandard" class="ptStandards" />
                                                <p>${p[0]}</p>
                                                <p class="giftPrice">&yen;${p[2]}</p>
                                            </div>
                                            <div class="ComboBox">
                                                <c:forEach items="${map.giftMap}" var="giftStandards">
                                                    <c:if test="${giftStandards.key==p[1]}">
                                                        <c:forEach items="${giftStandards.value}" var="giftStandard">
                                                            <div class="gift_color">
                                                                <h5>${giftStandard.key}
                                                                    <input type="hidden" class="giftAttriname" />
                                                                </h5>
                                                                <ul>
                                                                    <c:forEach items="${giftStandard.value}" var="giftC">
                                                                        <li>${giftC}
                                                                            <input type="hidden" class="giftAttrivalue" />
                                                                        </li>
                                                                    </c:forEach>
                                                                </ul>
                                                            </div>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                                <div class="gift_color" id="numb">
                                                    <h5>数量
                                                        <input type="hidden" class="giftAttriname" />
                                                    </h5>
                                                    <ul>
                                                        <li>1
                                                            <input type="hidden" class="giftAttrivalue" />
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                    <div class="btn">
                        <button type="button" class="add_cart" onclick="addToShoppingCart();">加入购物车</button>
                        <button type="button" class="buy" onclick="buyNow();">立即购买</button>
                    </div>
                    <input type="hidden" id="giftGoods" />
                    <input type="hidden" name="ppk.ppID" value="${map.obj[1]}" />
                    <input type="hidden" name="standard" id="standard" />
                    <input type="hidden" name="giftMoney" id="giftMoney" />
                </form>
            </div>
        </div>
        <!--热搜-->
        <div class="det_mil hot_recommend">
            <h2>热卖推荐</h2>
            <a href="#;"><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/shop_left.png" alt="" id="left" class="swiper-button-prev"></a>
            <a href="#;"><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/shop_right.png" alt="" id="right" class="swiper-button-next"></a>
            <div class="swiper-container swiper-container-hot">
                <div class="swiper-wrapper">

                </div>
            </div>
            <script>
                /*热搜*/
                var mySwiper = new Swiper('.swiper-container-hot', {
                    autoplay: false,//可选选项，自动滑动
                    /*  loop: true,*/
                    simulateTouch : false,
                    slidesPerView : 4,
                    slidesPerGroup : 4,
                    prevButton:'.swiper-button-prev',
                    nextButton:'.swiper-button-next',
                })
                /*商品*/
            </script>
        </div>
        <!--商品详情-->
        <div class="shop-details">
            <ul class="top">
                <li class="active" id="det" onclick="particulars();">商品详情</li>
                <li id="sale" onclick="afterSales();">售后服务</li>
                <li id="eval" onclick="comment();">商品评价</li>
            </ul>
            <ul class="shop-con">
                <!-- js拼接 -->
            </ul>
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