<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
    <title>数字地球商城</title>
    <link rel="stylesheet" href="<%=basePath %>page/newMyapp/lunbo/focusStyle.css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/lunbo/lunbo.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/numeralTheEarth.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var search;
		var tradecode;
		var titleJudge = '${titleJudge}';
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
		var ppid = "";
		var goodsid = "";
		var companyid = "";
		</script>
</head>
<body>
<div id="header">
    <ul>
        <li class="logo">
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>page/newMyapp/images/wfj.png" alt="" class="log"></a>
        </li>
        <li class="scroll">
            <img src="<%=basePath%>page/newMyapp/images/scroll.png" alt="">
        </li>
    </ul>
</div>
    <div class="content">
        <div class="ind_con_head">
            <ul>
                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
               	<!-- <a href="#;" class='investment'><li>中联园招商</li></a> -->
                <!-- <a href="#;" class='member'><li>会员中心</li></a> -->
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=06" class='readExtensively'><li>中联园博览</li></a>
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
                <!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
                <!--<div class="right">
                    <input type="search">
                    <img src="new_images/ico-search2.png" alt="">
                </div>-->
            </ul>
        </div>
        <!--搜索-->
        <div class="mil">
            <div class="shop-search">
                <input type="text" placeholder="请输入您要查找的商品" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您要查找的商品'" onkeypress="if (event.keyCode == 13)seek(this);" value="" class="seek">
                <button onclick="seek(this)">搜索</button>
            </div>
        </div>
        <!--banner图-->
        <div class="shop-banner">
            <!--轮播-->
            <div id="focus-banner">
                <ul id="focus-banner-list">
                    <li>
                        <a href="#;" class="focus-banner-img">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/numeralTheEarthbanner1.jpg" alt="">
                        </a>
                    </li>
                    <li>
                        <a href="#;" class="focus-banner-img">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/numeralTheEarthbanner2.jpg" alt="">
                        </a>
                    </li>
                    <li>
                        <a href="#;" class="focus-banner-img">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/numeralTheEarthbanner3.jpg" alt="">
                        </a>
                    </li>
                </ul>
                <a href="javascript:;" id="next-img" class="focus-handle"></a>
                <a href="javascript:;" id="prev-img" class="focus-handle"></a>
                <ul id="focus-bubble"></ul>
            </div>
        </div>
        <!--行业分类-->
        <div class="shop-industry">
            <div class="ind_left">
                <p>行业分类：</p>
            </div>
            <div class="ind_con">
                <ul>
                    <!-- js拼接 -->
                </ul>
            </div>
            <div class="ind_left ind_right">
                <p class="yes"><button>更多</button><span><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/down.png"></span></p>
                <p class="no"><button>收起</button><span><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/up.png"></span></p>
            </div>
        </div>
        <!--数字地球商城-->
        <div class="">
            <div class="mil_shop_con_">
                <div class="mil_shop_con">
                    <div class="shop-sort">
                        <button class="active" data-name="">综合排序</button>
                        <button data-name="pop">销量</button>
                        <button data-name="ptop">价格<img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-down.png"></button>
                        <button data-name="plow">价格<img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-up.png"></button>

                    </div>
                    <ul class="product">
                        <!-- js拼接 -->
                    </ul>
                    <div class="shop_page">
                        <!-- js拼接 -->
                    </div>
                </div>
            </div>
        </div>
        <!--热卖商品-->
        <div class="mil_shop_con hot_shop">
            <ul>
                <h3 class="title">热卖商品</h3>
                <!-- js拼接 -->
            </ul>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
           <!--  <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<script>
    $(document).ready(function(){
        $("#focus-banner").css("height","350px");
        $("#focus-banner-list li a").css("height","350px");
        /*行业分类*/
        $(document).on("click", ".shop-industry .ind_con ul li", function() {
            $(this).addClass("active").siblings().removeClass("active");
            tradecode = $.trim($(this).text());
            if(tradecode=="全部分类"){
            	tradecode="";
            	search="";
            }
    		loaded();
        });
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