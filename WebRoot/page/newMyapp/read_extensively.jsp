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
    <title>中联园博览</title>
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>js/html5shiv.min.js"></script>
    <script src="<%=basePath %>js/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/read_extensively.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var titleJudge = '${titleJudge}';
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';


        var cmt = new Array();
        var pagenumber = 0;
        var pagecount;
        var industryType = "";
        var companyname = "";

        var acquiesceLoGo = "images/WFJClient/PersonalJoining/logo@2x.png";
        var acquiesceIntro = "images/WFJClient/PersonalJoining/picture@2x.png";

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
        <!--搜索-->
        <div class="mil">
            <div class="shop-search">
                <input type="text" placeholder="请输入您要查找的公司" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您要查找的公司'" onkeypress="if (event.keyCode == 13)dianji();" value="" class="sousuo">
                <button onclick="dianji()">搜索</button>
            </div>
        </div>

        <!--行业分类-->
        <div class="shop-industry shop-industry_1">
            <div class="ind_left">
                <p>一级行业分类：</p>
            </div>
            <div class="ind_con">
                <ul>
                    <!-- js拼接 -->
                </ul>
            </div>
            <div class="ind_left ind_right">
                <p class="yes"><button>更多</button><span><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/down.png"></span></p>
                <p class="no"><button>收起</button><span><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/up.png"></span></p>
            </div>
        </div>

        <div class="shop-industry shop-industry_2">
            <div class="ind_left">
                <p>二级行业分类：</p>
            </div>
            <div class="ind_con">
                <ul>
                    <%-- js拼接 --%>
                </ul>
            </div>
            <div class="ind_left ind_right">
                <p class="yes"><button>更多</button><span><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/down.png"></span></p>
                <p class="no"><button>收起</button><span><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/up.png"></span></p>
            </div>
        </div>

        <div class="Expo_con">
            <ul>
                <!--js拼接-->
            </ul>
        </div>
        <div class="page_my">
            <!--js拼接-->
        </div>

    </div>

<!--尾部-->
<div id="footer">
    <div class="text footer_txt">
        <%--<p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p>--%>
        <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
        <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
        <p>版权所有：北京天太世统科技有限公司</p>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#focus-banner").css("height","350px");
        $("#focus-banner-list li a").css("height","350px");

        /*一级行业分类*/
        $(document).on("click",".shop-industry_1 .ind_con ul li",function(){
            var codeID = $(this).attr("data-codeID");
            $(this).addClass("active").siblings().removeClass("active");
            if(codeID!=null && codeID!=""){
                cmt.splice(0,cmt.length);
                $(".shop-industry_2 .ind_con ul").empty();
                getMoreIndustry(codeID,0);
                $(".shop-industry_2").show();
            }else{
                $(".shop-industry_2").hide();
                $(".shop-industry_2 .ind_con ul li").removeClass("active");
                industryType = "";
                loaded (industryType);
            }
        });
        /*二级行业分类*/
        $(document).on("click",".shop-industry_2 .ind_con ul li",function(){
            $(this).addClass("active").siblings().removeClass("active");
            industryType = $(".shop-industry_1 .ind_con ul li.active").text()+"/"+$(".shop-industry_2 .ind_con ul li.active").text();
            loaded (industryType);
        });
        $(document).on("click",".shop-industry_1 .ind_right .yes",function(){
            $(".shop-industry_1").attr("style","height:auto;ovflow:auto;");
            $(".shop-industry_1 .ind_right").addClass("more_")
        });
        $(document).on("click",".shop-industry_1 .ind_right .no",function(){
            $(".shop-industry_1").attr("style","height:90px;ovflow:hidden;");
            $(".shop-industry_1 .ind_right").removeClass("more_")
        });
        $(document).on("click",".shop-industry_2 .ind_right .yes",function(){
            $(".shop-industry_2").attr("style","height:auto;ovflow:auto;display:block;");
            $(".shop-industry_2 .ind_right").addClass("more_");

        });
        $(document).on("click",".shop-industry_2 .ind_right .no",function(){
            $(".shop-industry_2").attr("style","height:90px;ovflow:hidden;display:block;");
            $(".shop-industry_2 .ind_right").removeClass("more_")
        });

        /*分页*/
        $(document).on("click",".page_my li",function(){
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