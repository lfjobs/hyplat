<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en" style="font-size:62.5%;">
<head>
<title>数字地球</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="中联园区,微分金,中联园区微分金,数字地球,北京天太世统科技有限公司"/>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/index.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/wfjapp.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/wfjPc.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/news_list.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/swiper-3.3.1.min.css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/myapp/style.css"></link>

	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>css/WFJClient/myapp/style-n.css"></link>
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>css/WFJClient/myapp/idangerous.swiper.css"></link>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/myapp/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/myapp/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/myapp/toucher.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/myapp/tm3d.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/font-size.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>page/newMyapp/js/idangerous.swiper.min.js"></script>

<!--[if lt IE 9]>

    <srcipt src="<%=basePath%>/js/WFJClient/html5shiv.min.js"></srcipt>
    <srcipt src="<%=basePath%>/js/WFJClient/respond.min.js"></srcipt>
	<script >
		$(function(){
			$(".carousel").css("height","300px")
			$(".carousel-inner").css({"height":"100%"})
			$(".wfj12_019_intro_con_title li").css({"font-size":"25px","line-height":"20px"})
			$(".wfj12_019_intro_con_cons li").css({"font-size":"20px","line-height":"15px"})
		})
	</script>
<![endif]-->
<script>
		$(document).on("pagecreate","#pageone",function(){
			$("p").on("swipeleft",function(){
				$("#dian").click(function(){
					alert(2)
				})
			});
		});
		//判断页面是否加载完毕
		document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法.
		function subSomething() {
			if(document.readyState=="complete"){
		//加载完毕执行
				$(".spinner_").css("display","none");
			}else{
				$(".spinner_").css("display","block");
			}
		}
	</script>
<script type="text/javascript">
var basePath='<%=basePath%>';
	var i;
	var c = 1;
	var d = 1;
	var q = 0;
	var times = 59;

	var ttoken = 0;
</script>

<script>
	$(document).on("pagecreate", "#pageone", function() {
		$("p").on("swipeleft", function() {
			$("#dian").click(function() {
				alert(2)
			})
		});
	});
</script>
</head>
<c:if test="${typeLeix=='pc' }">
	<jsp:forward page="/page/newMyapp/index.jsp" />
</c:if>
<body class="body">
    <div class="wfj_con">
	<div class="wfj12_019">
		<div class="wfj12_002_top visible-lg"
			style="padding-right: 50px;overflow:hidden;">
			<%-- <a
				href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区">
				<img src="<%=basePath%>images/home/logo.png">
			</a> --%>
			<ul id="tops" style="float:right;width:25%;text-align: center;">
				<!--<li><a style="color:#666;"  onclick="dk()">中联园区</a></li>-->
				<a style="color:#666;"
					href="<%=basePath%>page/WFJClient/NewLogin.jsp"><li
					class="dengl" style="float:left;width:40%;font-size: 18px;padding: 5px;">登录</li>
				</a>
				<a style="color:#666;"
					href="<%=basePath%>ea/wfjshop/ea_getjspzc.jspa?user=15810799888"><li
					class="zhuc" style="float:left;width:40%;font-size: 18px;padding:5px;">注册</li>
				</a>
			</ul>
		</div>
		<div id="carousel-example-generic" class="carousel slide both"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				<li data-target="#carousel-example-generic" data-slide-to="3"></li>
				<li data-target="#carousel-example-generic" data-slide-to="4"></li>
			</ol>
			<div class="carousel-inner lb" role="listbox" id="div1">

				<c:forEach items="${companyGList }" var="entity">
					<c:if test="${entity.modalType eq '2'}">
					<c:set var="lent" value="${fn:length(entity.picPath) }"></c:set>
						<div class="item">
							<input type="hidden" id="modalName" value="${entity.modalName }" />
							<img src="<%=basePath %>${fn:substring(entity.picPath,0,lent-4)}small${fn:substring(entity.picPath,lent-4,lent)}">
						</div>
					</c:if>
				</c:forEach>

			</div>
			<a id="carleft" class="left carousel-control"
				href="#carousel-example-generic" role="button" data-slide="prev"
				style="background: transparent;"></a> <a id="carright"
				class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next" style="background: transparent;"></a>
				<div class="spinner_" style="display: none;">
			<div class="spinner">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
		</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-sm-12 row_div">
					<ul class="list-group main_nav text-center list">
						<li class="list-group-item col-xs-2 col-sm-4"
							style="border: none;"><img
							src="<%=basePath%>images/home/wfj_01.png"
							class="img-responsive center-block">
							<p>简介</p></li>
						<li class="kk list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_02.png"
							class="img-responsive center-block">
							<p>资讯</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_03.png"
							class="img-responsive center-block">
							<p>办公</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_04.png"
							class="img-responsive center-block">
							<p>入驻</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_05.png"
							class="img-responsive center-block">
							<p>会员中心</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_06.png"
							class="img-responsive center-block">
							<p>商城</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><img
							src="<%=basePath%>images/home/wfj_07.png"
							class="img-responsive center-block">
							<p>商家</p></li>
						<li class="list-group-item col-xs-2" style="border: none;"><a
							href="<%=basePath%>ea/purchasebids/ea_findbidIndexList.jspa"><img
								src="<%=basePath%>images/home/wfj_08.png"
								class="img-responsive center-block">
								<p>招标招聘</p>
						</a></li>
						<li class="list-group-item col-xs-2" style="border: none;"><a
							href="<%=basePath%>/ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid=&type=summary"><img
								src="<%=basePath%>images/home/diqu.png"
								class="img-responsive center-block">
								<p>县域联营经济平台</p>
						</a></li>
						<li class="list-group-item col-xs-2" style="border: none;"><a
								href="<%=basePath%>/page/ea/main/marketing/lottery/MeetingActivityList.jsp"><img
								src="<%=basePath%>images/home/MeetingActivity.png"
								class="img-responsive center-block">
							<p>活动</p>
						</a></li>
						<li class="list-group-item col-xs-2" style="border: none;"><a
								href="<%=basePath%>/page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp"><img
								src="<%=basePath%>images/home/ico-9.png"
								class="img-responsive center-block">
							<p>超市</p>
						</a></li>
					</ul>
				</div>
			</div>
		</div>

		<!--官方活动-->
		<div class="wfj_off">
			<div class="tit">
				<h5><img src="<%=basePath %>images/home/ico-off.png" alt=""><span>官方活动</span></h5>
			</div>
			<div class="mil">
				<div class="title">
					<i></i>
					<h5>积分活动</h5>
				</div>
				<ul id='signlist' >
				</ul>
			</div>
			<div class="mil">
				<div class="title">
					<i></i>
					<h5>抽奖活动</h5>
				</div>
				<ul id='relist' >
				</ul>
			</div>


			<div class="mil">
				<div class="title">
					<i></i>
					<h5>会员升级</h5>
				</div>
				<ul id='vip' >
				</ul>
			</div>
		</div>


		<!--新闻资讯-->
		<div class="wfj_off News">
			<div class="tit">
				<h5><img src="<%=basePath %>images/home/ico-news.png" alt=""><span>新闻资讯</span></h5>
			</div>
			<div class="mil">
				<div class="title">
					<a id="addbus" onclick="addbus('ea/wfjshop/ea_getNewsList.jspa')" class="news_h5">更多></a>
				</div>
				<ul class='zl_news' id='uls' >
				</ul>
			</div>
		</div>

		<!--数字地球商城-->
		<div class="wfj_off shop">
			<div class="tit" onclick="addbus('ea/digitalmall/ea_DigitalMall.jspa')">
				<h5><img src="<%=basePath %>images/home/ico-shop.png" alt=""><span>数字地球商城</span></h5>
			</div>
			<div class="mil">
				<div class="title">
					<a id="addMall" onclick="addbus('ea/digitalmall/ea_DigitalMall.jspa')" class="news_h5">更多></a>
				</div>
				<ul id="myMall"></ul>
			</div>
		</div>




        <!--中联园招商-->
        <div class="wfj_off attract">
            <div class="tit">
                <h5><img src="<%=basePath %>images/home/ico-attract.png" alt=""><span>中联园招商</span></h5>
            </div>
            <div class="mil">
                <div class="title">
                    <a href="<%=basePath%>/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform" id="addinvestment">更多></a>
                </div>
                <ul id="investment" class="terrace">

                </ul>
            </div>
        </div>



		<!--县域联营平台-->
		<div class="wfj_off_">
			<div class="tit">
				<h5><img src="<%=basePath %>images/home/ico-city.png" alt=""><span>县域联营平台</span></h5>
			</div>
			<div class="mil">
				<div class="title">
					<a id="bj" onclick="addbus('ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid=&type=summary')">更多></a>
				</div>
				<ul class="xianyu_n"></ul>
			</div>
		</div>

		<div class="wfj_off code">
            <div class="tit">
                <h5><img src="<%=basePath%>images/home/ico-code.png" alt=""><span>二维码专区</span></h5>
            </div>
            <ul class="code_mil">
                <li>
                    <h5>下载APP</h5>
                    <img src="<%=basePath%>images/home/download.png" alt="">
                </li>
                <li>
                    <h5>关注公众微信号</h5>
                    <img src="<%=basePath%>images/home/public.png" alt="">
                </li>
                <li>
                    <h5>中联园区首页</h5>
                    <img src="<%=basePath%>images/home/Home.png" alt="">
                </li>
            </ul>

        </div>

		<a href="#top" id="return"><img src="<%=basePath%>/images/return.png" alt=""></a>
	</div>
    </div>
	<!--暂时隐藏  -->
	<%-- <div class="alert" style="width: 100%;height: 100%;background: rgba(0, 0, 0, 0.26);position: fixed; top: 0;"></div>
			<div class="alert2" style="width: 350px;margin: 0 auto; background: #fff;position: fixed; z-index: 9;left: 50%;margin-left: -175px;top: 40%;border-radius: 10px;text-align: center;">
				<h3 style="width: 300px;line-height: 2; margin: 30px auto 20px auto;text-align: center;"><img src="<%=basePath %>images/home/alert.png" style="width: 50px;display: inline-block;float: left;">对不起您现在还未登陆</h3>
				<div class="clearfix"></div>
				<p style="font-size: 18px; width: 80%;margin:0 auto 20px auto;">关于招商模块需要登录查看未登录无法查看</p>
			</div> --%>
	<script type="text/javascript" src="<%=basePath%>js/WFJClient/myapp.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/WFJClient/myapp/myappList.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/WFJClient/NEWjsp/wfjzn.js"></script>
<script>
						if($(window).width()>1024){
						$('.ewm_img1').hover(function(){
							$('.img_ewm1').attr("style",'display:block;');
							$('.img_ewm1').stop().animate({left:"50%"},{queue:false,duration:200});
							return false;
						},function(){
							$('.img_ewm1').css("display",'none');
						});
						
						$('.ewm_img2').hover(function(){
							$('.img_ewm2').attr("style",'display:block;');
							$('.img_ewm2').stop().animate({left:"50%"},{queue:false,duration:200});
							return false;
						},function(){
							$('.img_ewm2').css("display",'none');
						});
						
						$('.ewm_img3').hover(function(){
							$('.img_ewm3').attr("style",'display:block;');
							$('.img_ewm3').stop().animate({right:"50%"},{queue:false,duration:200});
							return false;
						},function(){
							$('.img_ewm3').css("display",'none');
						});
						}
					</script>
	<script>
		$(function() {

			if ($(window).width() > $(window).height()) {
				$(".slider").css({
					"height" : "auto"
				});
				$(".slider-main").css({
					"height" : "100%"
				});
				$(".slider-extra").css({
					"position" : "absolute",
					"top" : "0",
					"height" : "100%"
				});
				$(".slider-panel").css({
					"height" : "100%",
					"width" : "100%"
				});
				/*$(".slider-panel img").css({"height":"2rem"});*/
				$(".wfj12_019_content").css({
					"width" : "100%"
				});
			}

			//轮播图追加样式
			$("#div1").find("div").eq(0).addClass("active");
			//中联的动态追加样式
			$(".list-group").each(
					function() {
						$(".list-group").find("li").addClass(
								"list-group-item col-xs-4");
						$(".list-group").find("li").css({
							"border" : "none"
						});
						$(".list-group").find("li").find("img").addClass(
								"img-responsive center-block");

					});

			$(".main_nav li p").css({
				/*"font-size" : "1.5rem",*/
				"margin-top" : "5px"
			});

			/*var imagePath = basePath + "/images/home/";
			$("#earth").tm3d({
				'ele' : $('#earth'),
				'imgLength' : 48,
				'imgPath' : imagePath,
				'imgtype' : 'png',
				'autoplay' : true,
				'auto' : {
					'dir' : 'left',
					'imgtime' : 150,
					'delaytime' : 2000,
				}
			});*/
		});
	</script>

	<script>
		// var num1=num2=num3=0
		window.onload = window.onresize = function() {
			//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
			//获取窗口的尺寸
			var clientWidth = document.documentElement.clientWidth;
			//console.log(clientWidth);
			//通过屏幕宽度去设置不同的后台根字体的大小
			//document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
			document.getElementsByTagName('html')[0].style.fontSize = clientWidth
					/ 640 * 40 + 'px'
		}
	</script>
	<script>
		var myTouch = util.toucher(document
				.getElementById('carousel-example-generic'));
		$("#carright").css("background", "transparent")
		$("#carleft").css("background", "transparent")
		$(".carousel-inner").css({
			"height" : "100%"
		})
		myTouch.on('swipeLeft', function(e) {
			$('#carright').click();
		}).on('swipeRight', function(e) {
			$('#carleft').click();
		});
	</script>
	<%--<script>
		var myTouch = util.toucher(document.getElementById('myCarousel'));
		$("#carright").css("background", "transparent")
		$("#carleft").css("background", "transparent")
		$(".carousel-inner").css({
			"height" : "100%"
		})
		myTouch.on('swipeLeft', function(e) {
			$('#carright2').click();
		}).on('swipeRight', function(e) {
			$('#carleft2').click();
		});
	</script>--%>
</body>
</html>