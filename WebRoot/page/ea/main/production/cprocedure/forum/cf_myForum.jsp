<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
<meta charset="UTF-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		
		<title>${myMessage.message[0]}</title> <script type="text/javascript"
			src="<%=basePath%>/js/BuildPlatform/font-size.js"></script>
		<link type="text/css" rel="stylesheet"
			href="<%=basePath%>/css/BuildPlatform/swiper-3.3.1.min.css">
			<link type="text/css" rel="stylesheet"
				href="<%=basePath%>/css/ea/production/forum/new_style.css">
				<script type="text/javascript"
					src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/BuildPlatform/swiper-3.3.1.min.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/ea/production/cprocedure/forum/new-page.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/ea/production/cprocedure/forum/cf_myForum.js"></script>
					<script>
if(window.name != "bencalie"){
     location.reload();
     window.name = "bencalie";
}
else{
     window.name = "";
}
</script>
</head>
<body>

	<header>
	<ul>
		<li style="width: 10%;"><a href="javascript:###;" onclick="javascript: window.history.go(-1);return false;"><img
				src="<%=basePath%>/images/BuildPlatform/left_jt.png">
		</a></li>
		<li style="width: 80%;" class="mingcheng"></li>
		<li style="width: 10%; display: none;"><img
			src="<%=basePath%>/images/BuildPlatform/ico-search.png" alt=""
			id="search">
		</li>
		<div class="clearfix"></div>
	</ul>
	</header>
	<div class="content_hidden">
		<div id="prompt" style="width: 100%; display: none;z-index: 1001">
			<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
			</center>
		</div>
		<div class="content">
			<div class="con con_pl">
				<div class="my_head">
					<div id="head">
						<img
							src="<%=basePath%>${myMessage.message[1]==null?'/images/WFJClient/PersonalJoining/headimage.png':myMessage.message[1]}"
							alt=""> 
					</div>
					<div class="name">
						<h4>${myMessage.message[0]}</h4>
						<c:if test="${whether}">
							<c:choose>
								<c:when test="${myMessage.bl}">
									<h5 class="attention active">
										<img
											src="<%=basePath%>/images/ea/production/forum/ico-attention.png"
											alt="">
											<p>已关注</p>
									</h5>
								</c:when>
								<c:otherwise>
									<h5 class="attention">
										<img
											src="<%=basePath%>/images/ea/production/forum/ico-attention.png"
											alt="">
											<p>关注</p>
									</h5>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<ul class="txt">
						<li>
							<h5>帖子</h5>
							<p>${myMessage.message[3]}</p></li>
						<li><a
							href="<%=basePath%>ea/companyforum/ea_ajaxSkip.jspa?ccom.sccId=${sccid}&judge=00&community=${community}">
								<h5>关注</h5>
								<p>${myMessage.message[4]}</p> </a></li>
						<li><a
							href="<%=basePath%>ea/companyforum/ea_ajaxSkip.jspa?ccom.sccId=${sccid}&judge=01&community=${community}">
								<h5>粉丝</h5>
								<p>${myMessage.message[5]}</p> </a></li>
					</ul>
					<div class="shequ">
						<p id="shequ">—— 我的社区 ——</p>
						<!-- Swiper -->
						<div class="swiper-container my_swiper">
							<div class="swiper-wrapper">
								<c:forEach items="${myMessage.community}" var="y">
									<div class="swiper-slide" onclick='forum("${y[0]}")'>
										<img
											src="<%=basePath%>${y[2]==null?'images/WFJClient/PersonalJoining/logo@2x.png':y[2]}"
											alt="">
											<p>${y[1]}</p>
									</div>
								</c:forEach>
							</div>
							<!-- Add Pagination -->
							<!-- <div class="swiper-pagination"></div>-->
						</div>
					</div>
				</div>
				<ul class="my_mil">
					<p id="tiezi" style="font-size: 0.7rem;text-align: center;">——
						我的帖子 ——</p>
					<!-- js拼接 -->
				</ul>
				<div class="btn_jia">
					<img src="<%=basePath%>/images/BuildPlatform/jia.png" alt=""
						onclick="whetherRegister()"> <input type="file"
						accept="image/*" multiple="" id="imgs" style="display: none">
				</div>
			</div>
			<!--<div class="alert"></div>-->
			<div class="alert_search">
				<div class="top">
					<input type="search" name="" placeholder="搜索"
						onfocus="this.placeholder=''" onblur="this.placeholder='搜索'"
						value="" class="sousuo"> <input type="submit" value="搜索"
						id="ss"> <input type="submit" value="取消" id="qx">
				</div>
			</div>
		</div>
	</div>
	<script>
	var basePath = '<%=basePath%>';
	var pageNumber = 0;
	var pageCount = 0;
	var sccid = '${sccid}';
	var wsccid = '';
	var staffid = '${ccom.staffid}';
	var companyid = '<%=session.getAttribute("companyid")%>';
	var ccompanyid = '<%=session.getAttribute("ccompanyid")%>';
	var community = '${community}';
</script>

	<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height",$(window).height()*0.92+"px");

        $(".con_pl").css("height",$(window).height()*0.86-1+"px");
        $(".btn_jia").attr("style","height:"+$(window).height()*0.06+"px");
        $(".mil_txt .mil img").css("height",$(".mil_txt .mil img").width()+"px");
        $(".lt_top").css("height",$(window).height()*0.12+"px");

      //  $("#menu_con .tag").css("max-height",$(".con_pl").height()-$(".lt_top").height()-$(".lt_lie").height()-$(".btn_jia").height()+"px");
    	 //2017.2.15
        var le = $(".con_pl .my_head .name h4");
        le.css("margin-left",-le.width()*0.5+"px");
        $(".attention").click(function(){
            $(this).toggleClass("active");
            var t =  parseInt($(this).parents(".my_head").find(".txt").children().last().find("p").text());
            if($(this).hasClass("active")){
                $(this).find("p").text("已关注");
                $(this).parents(".my_head").find(".txt").children().last().find("p").text(t+1);
           }else{
                $(this).find("p").text("关注");
                var b;
                if(t==0){
                	b = t;
                }else{
                	b = t-1;
                }
                $(this).parents(".my_head").find(".txt").children().last().find("p").text(b);
           }
       });
    
   });
</script>
	<script>
     var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination1',
        slidesPerView: 3,
        spaceBetween: 15  //图片间距
       /* loopAdditionalSlides : 0,
        loopedSlides :3,
        loop: true, //循环*/
   });
    window.onload = function(){ 
　	 if($(".shequ .swiper-slide").length==1){
        $(".con_pl .my_head .shequ .swiper-slide-active").css("margin","0 auto");
   }else{
   }
} 
    
</script>
</body>
</html>