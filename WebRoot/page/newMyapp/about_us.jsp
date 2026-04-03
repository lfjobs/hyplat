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
    <title>关于我们</title>
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var titleJudge = '${titleJudge}';
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
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
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <!-- <a href="#;" class='member'><li>会员中心</li></a> -->
               	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=06" class='readExtensively'><li>中联园博览</li></a>
              	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
           		<!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
           </ul>
        </div>
        <div class="about_con">
            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/banner-about.png" class="banner">
            <div class="attract_con_">
                <ul class="left">
                    <li class="intro active" style="margin-top: 25px">公司简介</li>
                    <li class="contact">联系我们</li>
                </ul>
                <ul class="right">
                    <li id="intro">
                        <div class="txt">
                            <p>
                                人类的发展进步经历了万年历程，伴随新生事物的诞生，旧的事物必定成为历史。改革开放以来，中国经济步入高速发展的新征程。1994年，《中华人民共和国公司法》正式实施；22年间，公司运作理念不断推进者中国经济的腾飞。以公司经济经营为核心的运营思想，促使中国迅速成为世界第二大经济体；可见，公司化运作的能量是非常巨大的。
                            </p>
                            <p>
                                社会已经步入全新的变革时代，充满竞争的时代。为顺应时代发展，北京天天太世统科技有限公司在北京注册成立；2015年5月12日，注册资金5000万元的五层五请管理有限公司成立；2016年4月28日，注册资金1亿元的中联园微分金股份有限公司成立。两家法定机构共同形成了微分金数字地球联营平台运营机构。以五层五请管理标准为核心思想，5L5C管理系统标准化、专业化建设为组织系统，形成了完整的中联园微分金股份有限公司发展战略组织系统。即：策划层：股东王委员会、监事、顾问委员会委员会	和职工代表委员会。决策层：董事局。执行层：总裁办公室、财务结算中心、财务生产委员会、营销委员会。经由简单到复杂的地球运动、变化和相互联系、相互影响，构成了具有形貌变迁，人类活动的物质地球。
                            </p>
                            <p>
                                数字地球联营平台的诞生，将为全球、社会、企业提供庞大的数据资源。在供给人类分析决策的同时，还为政府、企业和个人提供政务办公服务平台、行政服务平台、行业服务平台、企业服务平台等一系列服务内容，并且帮助企业快速搭建高度忠诚的营销团队，为企业建设好客户粉丝服务体系。同时，随着联营商城业主会员的生成，又为社会职业提供了创业创富的可靠机会。与此同是，随着联营商城业主会员的生成，又为社会职业提供了创业创富的可靠机会。与此同时，为更好的服务社会，微分金公司搭建的省级公司、县级公司和村级公司大平台服务体系也即将全面完成，一个跨时代的平台经济时代即将来临。
                            </p>
                            <p>  <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/yyzz.png"></p>
                        </div>
                    </li>
                    <li id="contact">
                        <div class="txt">
                            <p>
                                电话：010-6416 7113  &nbsp; 400-6855 569
                            </p>
                            <p>
                                传真：010-6416-4005
                            </p>
                            <p>
                                邮箱：swjx1998@163.com
                            </p>
                            <p>
                                网址：www.impf2010.com
                            </p>
                            <p>
                                地址：北京市东城区东直门外大街42号宇飞大厦801
                            </p>
                        </div>
                        <div class="code">
                            <img src="<%=basePath%>page/newMyapp/images/newPCHomepage/ico-code.png">
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

<!--尾部-->
<div id="footer">
    <div class="text footer_txt">
        <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
        <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
        <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
        <p>版权所有：北京天太世统科技有限公司</p>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#focus-banner").css("height","350px");
        $("#focus-banner-list li a").css("height","350px");


        $(".intro").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            $("#intro").show().siblings().hide()
        });
        $(".contact").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            $("#contact").show().siblings().hide()
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