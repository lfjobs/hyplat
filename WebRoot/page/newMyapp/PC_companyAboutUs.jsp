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
    <title>关于我们</title>
    <link href="<%=basePath %>page/newMyapp/css/companyWebsite.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<%=basePath %>/css/ea/production/qrshare/qr_share.css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toHeadType.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/PC_companyAboutUs.js" type="text/javascript"></script>
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
<div class="content">
    <!--banner图-->
    <div class="message">
        <img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/banner_about.png">
    </div>
    <div class="mil">
        <div class="about_con_no">
            <ul class="left">
                <li class="intro active">公司简介</li>
                <li class="history">公司文化</li>
            </ul>
            <ul class="right">
                <li id="intro">
                    <!--js拼接-->
                </li>
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
    /*jQuery.fn.limit=function(){
        var self = $("[limit]");
        self.each(function(){
            var objString = $(this).text();
            var objLength = $(this).text().length;
            var num = $(this).attr("limit");
            if(objLength > num){
                $(this).attr("title",objString);
                objString = $(this).text(objString.substring(0,num) + "...");
            }
        })
    }
    $(function(){
        $("[limit]").limit();
    })*/
</script>
<script>
    $(document).ready(function(){
        $("#header ul .nav a").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".about_con_no .left li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

        $(".about_con_no .left .intro").click(function(){
            profile("00");
        });
        $(".about_con_no .left .history").click(function(){
            profile("01");
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