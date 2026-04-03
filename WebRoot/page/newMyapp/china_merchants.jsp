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
    <title>中联园招商</title>
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/china_merchants.js" type="text/javascript"></script>
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
        <div class="att_con">
            <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/banner-attr.png" class="banner">
            <h3 class="title">经济平台联营加入</h3>
            <ul class="att_list top">
                <!-- js拼接 -->
            </ul>
            <ul class="att_list middle">
                <li>
                    <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-gong.png">
                    <h2>公司申请加入经济平台</h2>
                    <a href="<%=basePath%>/ea/newpcend/ea_joinPlatform.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&hot=公司&titleJudge=03&seven=MyjiaruCompany"><input type="button" value="申请加入"></a>
                </li>
                <li>
                    <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-ge.png">
                    <h2>个人申请加入经济平台</h2>
                    <a href="<%=basePath%>/ea/newpcend/ea_joinPlatform.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&hot=个人&titleJudge=03&seven="><input type="button" value="申请加入"></a>
                </li>
            </ul>
            <h3 class="title">产品代理商加入</h3>
            <ul class="att_list2">
                <!-- js拼接 -->
            </ul>
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

        $(".Eco-ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

        /*行业分类*/
        $(".shop-industry .ind_con ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".shop-industry .ind_right .yes").click(function(){
            $(".shop-industry").attr("style","height:auto;ovflow:auto;");
            $(".shop-industry .ind_right").addClass("more_")
        });
        $(".shop-industry .ind_right .no").click(function(){
            $(".shop-industry").attr("style","height:90px;ovflow:hidden;");
            $(".shop-industry .ind_right").removeClass("more_")
        });

        /*分页*/
        $(".page_my li").click(function(){
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