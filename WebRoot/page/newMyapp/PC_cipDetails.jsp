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
    <title>公司联营</title>
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
    <script src="<%=basePath %>/js/qrcode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/join_platform.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ccompanyId = '${ccompanyId}';
		var titleJudge = '${titleJudge}';

        var hot = '${hot}';
        var goodsName = "";
        var price;
        var ppid;

        //促销品数据
        var ptppid = "";
        var companyId = "";
        var ptmorre = "";
        var ptstandard = "";
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
<div class="content">
    <div class="att_con">
        <div class="att_list3">
            <div class="tit">
                <c:choose>
                    <c:when test="${hot eq '公司'}">
                        <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-gong.png">
                    </c:when>
                    <c:when test="${hot eq '个人'}">
                        <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-ge.png">
                    </c:when>
                </c:choose>
                <h2>${hot}申请加入经济平台</h2>
            </div>
            <ul>
                <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">将企业管理分为人事、财务、办公室、生产、营销模块</li>
                <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">各种方式推广会员名片，增加会员粉丝获取“金粉丝”经济</li>
                <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">会员购买产品产交易，获取丰厚佣金，在家轻松做老板</li>
                <li><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-oval.png">公司底下加入更多会员，购买企业产品，产生更多利润</li>
            </ul>
            <input type="button" value="查看特权">
        </div>
        <div class="att_list4">
            <div class="mil huiyuan">
                <h2>选择购买${hot}经济平台会员类型：</h2>
                <ul>
                    <c:forEach items="${list}" var="a">
                        <li data-ppid="${a[1]}" data-price="${a[2]}"><p>${a[0]}</p></li>
                    </c:forEach>
                </ul>
                <h5>不同会员类型，有不同的区别</h5>
                <hr>
            </div>
            <c:if test="${hot eq '公司'}">
                <div class="mil hangye">
                    <h2>填写公司基本信息：</h2>
                    <div class="list_">
                        <div class="list">
                            <h5>行业类别：</h5>
                            <div class="ipt">
                                <p></p><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-down2.png" alt="">
                            </div>
                        </div>
                        <ul class="class1">
                        </ul>
                        <ul class="class2">
                        </ul>
                    </div>
                    <div class="list">
                        <h5>公司名称：</h5>
                        <div class="ipt2">
                            <input type="text" value="" placeholder="请输入公司名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入公司名称'">
                        </div>
                    </div>
                    <hr>
                </div>
            </c:if>
            <div class="payment">
                <h1>实际支付：<span>&yen;</span><span class="money">0.00</span>元</h1>
                <input type="button" value="立即支付" onclick="zhifu()">
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
    $(document).ready(function() {
        var txt = $(".att_con .att_list4 .payment h1 .money").text();
        var txt2 = new Number(txt).toFixed(2);
        $(".att_con .att_list4 .payment h1 .money").text(txt2);
        /*选择平台类型*/
        $(document).on("click", ".att_con .att_list4 .huiyuan ul li", function () {
            $(this).addClass("active").siblings().removeClass("active");
            $(".money").text($(this).attr("data-price"));
            price = $(this).attr("data-price");
            ppid = $(this).attr("data-ppid");
            goodsName = $(this).find("p").text();
            ajax();
        });

        /*行业类别选择*/
        $(document).on("click", ".att_con .att_list4 .mil .list .ipt", function () {
            findIndustry("", "class1");
            $(".att_con .att_list4 .mil .class1").show();
        });
        $(document).on("click", ".att_con .att_list4 .mil .class1 li", function () {
            var li_txt1 = $(this).text();
            findIndustry($(this).attr("data-codeid"), "class2");
            $(".att_con .att_list4 .mil .class2").show();
            $(".att_con .att_list4 .mil .list .ipt p").text(li_txt1);
        });
        $(document).on("click", ".att_con .att_list4 .mil .class2 li", function () {
            var li_txt2 = $(this).text();
            $(".att_con .att_list4 .mil .class1").hide();
            $(".att_con .att_list4 .mil .class2").hide();
            $(".att_con .att_list4 .mil .list .ipt p").append("/" + li_txt2);
        });
        /*选择支付方式*/
        $(".att_con .att_list4 .payment .payment_con_n li").on("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
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