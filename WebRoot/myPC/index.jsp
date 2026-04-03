<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>网站首页</title>
    <link rel="stylesheet" href="<%=basePath %>myPC/lunbo/focusStyle.css">
    <link href="<%=basePath %>myPC/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath %>myPC/lunbo/lunbo.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>myPC/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>myPC/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="header">
    <ul>
        <li class="logo">
                           <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>myPC/images/wfj.png" alt="" class="log"></a>

            
        <li class="name">
            <div>
                <h3>中国互联网行业领导品牌</h3>
                <h5>北京天太世统科技有限公司</h5>
            </div>
        </li>
        <li class="login">
            <!--  <img src="images/phone.png" alt="">
              <div>
                  <h5>天太世统诚挚招商热线</h5>
                  <h3>010-64167133</h3>
              </div>-->

            <a id="login"><input type="button" value="登录"></a>
            <a id="register"><input type="button" value="注册" id="zc"></a>
        </li>
    </ul>
</div>
    <div class="content">
        <div class="ind_con_head">
            <ul>
                <a id="index" ><li class="active">网站首页</li></a>
                <a id="Web-New" ><li>网站资讯</li></a>
                <a id="bg" ><li>办公</li></a>
                <a id="Dress-custom"><li>服务专区</li></a>
            </ul>
        </div>
        <!--轮播-->
        <div id="focus-banner">
            <ul id="focus-banner-list">
                <li>
                    <a href="#;" class="focus-banner-img">
                        <img src="<%=basePath %>myPC/images/banner1.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="#;" class="focus-banner-img">
                        <img src="<%=basePath %>myPC/images/12.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="#;" class="focus-banner-img">
                        <img src="<%=basePath %>myPC/images/11.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="#;" class="focus-banner-img">
                        <img src="<%=basePath %>myPC/images/13.png" alt="">
                    </a>
                </li>
            </ul>
            <a href="javascript:;" id="next-img" class="focus-handle"></a>
            <a href="javascript:;" id="prev-img" class="focus-handle"></a>
            <ul id="focus-bubble"></ul>
        </div>


        <div class="grid">
            <img src="<%=basePath %>myPC/images/bg1.png" alt="" class="bg1">
            <div class="text">
                WHO WE ARE?
            </div>
            <ul class="text2">
                <a href="#intro"><li>公司简介</li></a>
                <a href="#culture"><li>企业文化</li></a>
                <a href="#show"><li>优势展示</li></a>
                <a href="#contact"><li>联系我们</li></a>

            </ul>
        </div>
        <div class="grid2" id="intro">
            <img src="<%=basePath %>myPC/images/bg2.png" alt="" class="bg2">
            <div class="headline">
                <h2>公司简介 <span>COMPANY PROFILE</span></h2>
                <img src="<%=basePath %>myPC/images/zu1.png" alt="">
            </div>
            <p>
                北京天太世统科技有限公司是一家专业的，为大中小提供企业管理，帮助企业解决方案的国际化企业。公司集产品研发、生产、销售和服务为一体。它包含企业渴望的管理思想， 科学化、标准化、大数据、 管理系统、电子商务平台、培训咨询、赢利模式系统、科学 的实施方案 。基于5L5C管理思想（五层五清孵化管理体系企 业管理新标准）完成企业四化建设、 四化融合的运营管理体 系，即5L5C大数据（中联园区）+5L5C管理系统 （人事系统、 办公系统、财务系统、生产系统、营销系统等）+网络推广即 电子商务平台 （微分金+微信平台+电子商务资源抱团+线上线 下博览）+5L5C管理培训咨询+资源型赢利模式系统+5L5C管理 实施方案。 以此开启企业原始资本+互联网时代的新纪元。
            </p>
        </div>
        <div class="grid2 grid3" id="culture">
            <img src="<%=basePath %>myPC/images/bg3.png" alt="" class="bg3">
            <div class="headline">
                <h2>企业文化<span> ENTERPRISE CULTURE </span></h2>
                <img src="<%=basePath %>myPC/images/zu1.png" alt="">
            </div>
            <p>
                天太世统以客户需求为导向，立足于固定收益及衍生品市场，专注于软件研发，产品创新，在企业管理市场为客户提供准确、专业、高效的信息服务，简化客户工作流程，帮助客户创造可持续的竞争优势。
            </p>
        </div>
        <div class="grid2" id="show">
            <div class="headline">
                <h2>优势展示<span> ADVANTAGE TO SHOW </span></h2>
                <img src="<%=basePath %>myPC/images/zu1.png" alt="">
            </div>
            <ul>
                <li>
                    <img src="<%=basePath %>myPC/images/1.png" alt="">
                    <h1>低成本</h1>
                    <hr style="border-top: 5px solid #FF2C27;width: 50px;margin: 0 auto;">
                    <p>
                        免费加入会员，100即可 成为代理商商城业主会员。
                    </p>
                </li>
                <li>
                    <img src="<%=basePath %>myPC/images/2.png" alt="">
                    <h1>金币营销</h1>
                    <hr style="border-top: 5px solid #FF2C27;width: 50px;margin: 0 auto;">
                    <p>
                        通过金币的分享，营销，刺激客户使用我们的产品，增加客户流量。
                    </p>
                </li>
                <li>
                    <img src="<%=basePath %>myPC/images/3.png" alt="">
                    <h1>管理模块化</h1>
                    <hr style="border-top: 5px solid #FF2C27;width: 50px;margin: 0 auto;">
                    <p>
                        将企业管理分为人事、办公室、财务、生产、营销。
                    </p>
                </li>
            </ul>
        </div>
        <div class="grid2 grid4" id="contact">
            <div class="headline">
                <h2>联系我们<span> CONTACT US</span></h2>
                <img src="<%=basePath %>myPC/images/zu1.png" alt="">
            </div>
            <ul>
                <li>
                    <img src="<%=basePath %>myPC/images/app.png" alt="" class="app">
                </li>
                <li>
                    <div class="text">
                        <p><img src="<%=basePath %>myPC/images/url.png" alt="">网址：www.impf2010.com</p>
                    
                        <p><img src="<%=basePath %>myPC/images/tell.png" alt="">电话：010-64167133</p>
                        <p><img src="<%=basePath %>myPC/images/faxes.png" alt="">传真：010-64167133</p>
                    </div>
                </li>
            </ul>
        </div>
        <a href="#header" class="return"><img src="<%=basePath %>myPC/images/return.png" alt=""></a>
    </div>
    <div id="footer">
        <div class="text footer_txt">
           
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
           
        </div>
    </div>
<script>
    $(document).ready(function(){

var basePath = "<%=basePath%>";
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

	
        $("#login").click(function(){
            var url = basePath+"myPC/login.jsp";
            document.location.href = url;
        });
        $("#index").click(function(){
            var url = basePath+"myPC/index.jsp";
            document.location.href = url;
        });
        
        $("#bg").click(function(){
            var url = basePath+"page/ea/index.jsp";
            document.location.href = url;
        });
        
        $("#Web-New").click(function(){
            var url = basePath+"ea/wfjshop/ea_getNews.jspa";
            document.location.href = url;
        });
        
        $("#Dress-custom").click(function(){
            var url = basePath+"myPC/Dress-custom.jsp";
            document.location.href = url;
        });
        
        $("#register").click(function(){
            var url = basePath+"myPC/register.jsp";
            document.location.href = url;
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