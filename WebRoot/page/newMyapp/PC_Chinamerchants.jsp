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
    <title>招商服务</title>
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/pagination.css" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>/page/newMyapp/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=basePath %>page/newMyapp/js/toShareCode.js"></script>
    <!--ie8及以下浏览器不兼容-->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript" src="<%=basePath %>/page/newMyapp/js/PC_Chinamerchants.js"></script>
    
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		
		
		var titleJudge = '${titleJudge}';
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
	</script>
</head>
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
            <div class="bid_head">
                <h2>招标招聘</h2>
                <ul>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00"><li>招标商品</li></a>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=01"><li>招聘人才</li></a>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=02"><li class="active">招商产品</li></a>
                </ul>
            </div>
        </div>

    <div class="bid_con">
        <div class="top">
            <h2>招标招聘:</h2>
            <ul class="right">
                <!-- js拼接 -->
            </ul>
        </div>
        <div class="bottom">
            <ul class="tit">
                <!-- js拼接 -->
            </ul>
            <div class="bot_con">
                <div class="left">
                    <ul id="">
                        <%-- js拼接 --%>
                    </ul>
                </div>
                <div class="right">
                    <h2 class="title">热门招标<span class="hot">hot</span></h2>
                    <ul>
                        <!-- js拼接 -->
                    </ul>
                </div>
                <div class="bid_pages">
                    <div class="left_page">
                        共<span>0</span>件商品
                    </div>
                    <div class="page_my">
                    	<button style="float: left;" onclick="lastStep()"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/page-left.png"></button>
                        <ul>
                        	<!-- js拼接 -->
                        </ul>
                        <button style="float: right;" onclick="nextStep()"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/page-right.png"></button>
                	</div>
                </div>
            </div>
        </div>
    </div>

    </div>
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<!--返回顶部-->
<a href="#header" class="return"><img src="images/return.png" alt=""></a>

<script>
    $(document).ready(function(){
        /*头部*/
        $(document).on("click",".bid_head ul li",function(){
            $(this).addClass("active").parents(".bid_head ul a").siblings().find("li").removeClass("active");
        });


		$(document).on("click",".bid_con .bottom .tit li",function(){
            $(this).addClass("active").siblings().removeClass("active");
            ajax();
        });

        /*招标招聘-right*/
        $(document).on("click",".bid_con .top .right li",function(){
            $(this).addClass("active").siblings().removeClass("active");
            ajax();
        });

        /*分页居中*/
        var nub = $(".page_my ul li").length;
        $(".page_my").css("width",80+40*nub+"px");
        $(".page_my ul").css("width",40*nub+"px");

    })

</script>

</body>
</html>