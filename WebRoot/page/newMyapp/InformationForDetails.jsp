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
    <title>网站资讯</title>
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/InformationForDetails.js" type="text/javascript"></script>
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;

		var ppID = '${ppk.ppID}';
		var titleJudge = '${titleJudge}';
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
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
    <div class="content con">
        <div class="ind_con_head">
            <ul>
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <a href="#;" class='member'><li>会员中心</li></a>--%>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a> 
               	<!--  <a href="#;" class='readExtensively'><li>中联元博览</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
                <!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a>
                <a href="#;" class='aboutUs'><li>关于我们</li></a>  -->
            </ul>

        </div>
        <div class="web_con web_con_det">
            <h5 class="top_text">首页 &gt; 网站资讯 &gt;</h5>
            <div class="web_con_left">
                <div class="web_con_left1 web_con_left3">
                    <h3>推荐资料</h3>
                </div>
                <ul class="web_con_left4">
                   	<!-- js拼接 -->
                </ul>
            </div>
            <div class="web_con_right web_con_right_det">
                <div class="headline">
                    <h3>${details[1] }</h3>
                    <p>发布时间：<span>${details[2] }</span></p>
                </div>
                <div class="text">
                    ${details[0] }
                </div>
            </div>
        </div>
        <a href="#header" class="return"><img src="<%=basePath%>/page/newMyapp/images/return.png" alt=""></a>
    </div>
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>

<script>
    $(document).ready(function(){
        $(".alert").css({top:$(window).height()*0.5-134+'px',left:$(window).width()*0.5-200+'px'});

        $(".ind_con_head ul li").click(function(){
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