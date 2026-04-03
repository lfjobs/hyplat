var length, currentIndex = 0, interval, hasStarted = false, // 是否已经开始轮播
t = 2000; // 轮播时间间隔
var s = 0;
var distance = 0;
var browser = navigator

//.appName

//var b_version = navigator

//.appVersion

/*var version = b_version.split(";");

var trim_Version = version[1].replace(/[ ]/g, "");

if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE8.0") {
	// alert("IE 8.0");
	$(".slider").css("float", "left")
	$(".slider").find("img").css("height", "295px")
	$(".wfj12_019").css({
				"width" : "70%",
				"margin" : "0 auto"
			})
	$(".wfj12_019_intro_vadio").css("display", "none")
}*/
function text() {
	$.load($("#text").text);
}

/**
 * 向前翻页
 */
function pre() {
	var preIndex = currentIndex;
	currentIndex = (--currentIndex + length) % length;
	play(preIndex, currentIndex);
}
/**
 * 向后翻页
 */
function next() {
	var preIndex = currentIndex;
	currentIndex = ++currentIndex % length;
	play(preIndex, currentIndex);
}
/**
 * 从preIndex页翻到currentIndex页 preIndex 整数，翻页的起始页 currentIndex 整数，翻到的那页
 */
function play(preIndex, currentIndex) {
	$(".slider-panel").eq(preIndex).fadeOut(500).parent().children()
			.eq(currentIndex).fadeIn(1000);
	$(".slider-item").removeClass("slider-item-selected");
	$(".slider-item").eq(currentIndex).addClass("slider-item-selected");
}
/**
 * 开始轮播
 */
/*function start() {
	// alert(hasStarted)
	if (!hasStarted) {
		hasStarted = true;
		interval = setInterval(next, t);
	}
}*/
/**
 * 停止轮播
 */
function stop() {
	clearInterval(interval);
	hasStarted = false;
}

//轮播图跳转至过年购物赚钱步骤
$(document).on("click",".active",function() {
	var modalName = $(this).find("#modalName").val();
	if(modalName == "头部大图片1"){
		document.location.href = basePath + "page/WFJClient/PersonalJoining/MyappFlow.jsp";
	}
});

$(document).ready(function(e) {

	$(".wfj12_019_news_con").find("img").attr(
			"style",
			"width:" + $(window).width() * 0.02 + "px;height:"
					+ $(window).height() * 0.04 + "px;");
	$("body").attr(
			"style",
			"width:" + $(window).width() + "px;height:" + $(window).height()
					+ "px;")

	length = $(".slider-panel").length;
	// 将除了第一张图片隐藏
	$(".slider-panel:not(:first)").hide();
	// 将第一个slider-item设为激活状态
	$(".slider-item:first").addClass("slider-item-selected");
	// 隐藏向前、向后翻按钮
	$(".slider-page").hide();
	// 鼠标上悬时显示向前、向后翻按钮,停止滑动，鼠标离开时隐藏向前、向后翻按钮，开始滑动
	$(".slider-panel, .slider-pre, .slider-next").hover(function() {
				stop();
				$(".slider-page").show();
			}, function() {
				$(".slider-page").hide();
				//start();
			});
	$(".slider-item").hover(function(e) {
		stop();
		var preIndex = $(".slider-item").filter(".slider-item-selected")
				.index();
		currentIndex = $(this).index();
		play(preIndex, currentIndex);
	}, function() {
		start();
	});
	$(".slider-pre").unbind("click");
	$(".slider-pre").bind("click", function() {
			});
	$(".slider-next").unbind("click");
	$(".slider-next").bind("click", function() {
				next();
			});

	
	// 开始轮播
	//start();

	$(".slider-panel").css("width", "100%");
	$(".slider-panel").find("img").css("width", "100%");
	// $(".slider-panel").find("img").css("height","auto");
	$(".slider-extra").css("height", $(".slider-panel").find("img").height());

	$(".slider-nav").find("li").attr(
			"style",
			" height:" + $(window).height() * 0.01 + "px; line-height:"
					+ $(window).height() * 0.01 + "px; width:"
					+ $(window).height() * 0.01 + "px; margin: 0 "
					+ $(window).height() * 0.003 + "px;");
	$(".slider").attr(
			"style",
			" height:" + $(window).height() * 0.2
					+ "px;text-align: center;position: relative;");
	if ($(".slider-panel").find("img").height() > $(window).height() * 0.3) {
		$(".wfj12_019").css("width", "70%");
		$(".slider, .slider-panel img, .slider-extra").css("width", "100%");
		$(".slider, .slider-extra").css("height",
				$(".slider-panel").find("img").height());
		$(".wfj12_019_top_title").find("li").attr("style", " line-height:5px;");
		$(".wfj12_019_top_title").find("li").find("img").attr(
				"style",
				" height:" + $(window).height() * 0.035 + "px;margin:"
						+ $(window).height() * 0.02 + "px auto;");

		$(".wfj12_019_top_title").find("li").eq(0).attr(
				"style",
				"font-size:" + $(window).height() * 0.02
						+ "px;color:#F74C31;height:" + $(window).height()
						* 0.05 + "px; line-height:" + $(window).height() * 0.05
						+ "px;");
		$(".wfj12_019_top_title").find("li").css("width", "20%");

	} else {
		$(".wfj12_019_top_title").find("ul").attr(
				"style",
				" height:" + $(window).height() * 0.05 + "px; line-height:"
						+ $(window).height() * 0.05 + "px;");
		$(".wfj12_019_top_title").find("li").attr("style",
				"font-size:" + $(window).height() * 0.023 + "px;");
		$(".wfj12_019_top_title").find("li").find("img")
				.attr(
						"style",
						" height:" + $(window).height() * 0.036 + "px;margin:"
								+ $(window).height() * 0.015
								+ "px auto;margin-top:4px");
		$(".wfj12_019_top_title").find("li").eq(0)
				.attr(
						"style",
						"font-size:" + $(window).height() * 0.023
								+ "px;color:#F74C31;");


	}

	/* 园区简介 */
	$(".wfj12_019_intro_con_title").find("li").attr(
			"style",
			"font-size:" + $(window).height() * 0.05 + "px;line-height:"
					+ $(window).height() * 0.05 + "px;");
	$(".wfj12_019_intro_con_cons").find("li").attr("style",
			"font-size:14pt;line-height:"/* + $(window).height() * 0.06*/ + "30px;letter-spacing: 1px;margin-top:0;");
	// $(".wfj12_019_intro_bottom").attr("style","margin-top:"+$(window).height()*0.1+"px;");
	$(".wfj12_019_body").attr(
			"style",
			"width: 100%;text-align: center;overflow:hidden;margin-bottom:20px;margin-top:"
					+ $(window).height() * 0.1 + "px;");
	$(".wfj12_019_body").find("img").css({
				"height" : "120px",
				"width" : "120px"
			})
	$(".wfj12_019_intro_bottom").find("li").attr(
			"style",
			"font-size:" + $(window).height() * 0.02 + "px;line-height:"
					+ $(window).height() * 0.03 + "px;");

	/* 园区新闻 */
	$(".wfj12_019_news").find("td").attr("style",
			"font-size:" + $(window).height() * 0.02 + "px;");
	$(".wfj12_019_news_bottom").attr("style",
			"margin-top:" + $(window).height() * 0.05 + "px;");
	$(".wfj12_019_news_bottom").find("li").attr(
			"style",
			"font-size:" + $(window).height() * 0.02 + "px;line-height:"
					+ $(window).height() * 0.03 + "px;");


	/* 园区招商 */
	/* 会员中心 */
	$(".wfj12_019_vip_top").attr(
			"style",
			"height:" + $(window).height() * 0.1 + "px;line-height:"
					+ $(window).height() * 0.1 + "px;");
	$(".wfj12_019_vip_top").find("li").attr("style",
			"font-size:" + $(window).height() * 0.02 + "px;");
	$(".wfj12_019_vip_avatar").attr("style",
			"height:" + $(window).height() * 0.06 + "px;");
	$(".wfj12_019_vip_avatar").find("img").attr("style",
			"height:" + $(window).height() * 0.06 + "px;width:auto;");
	$(".wfj12_019_vip_agent").attr(
			"style",
			"height:" + $(window).height() * 0.04 + "px;line-height:"
					+ $(window).height() * 0.04 + "px; margin:"
					+ $(window).height() * 0.03 + "px auto;font-size:"
					+ $(window).height() * 0.02 + "px; border-top-left-radius:"
					+ $(window).height() * 0.04
					+ "px; border-bottom-left-radius:" + $(window).height()
					* 0.04 + "px;");
	$(".wfj12_019_vip_agent").parent().attr("style", "width:55%; float:right;");
	$(".wfj12_019_ewm").attr("style",
			"height:" + $(window).height() * 0.03 + "px;width:auto;");
	$(".wfj12_019_vip_content").find("td").attr(
			"style",
			"height:" + $(window).height() * 0.06 + "px;line-height:"
					+ $(window).height() * 0.06 + "px;font-size:"
					+ $(window).height() * 0.02 + "px; border-bottom:"
					+ $(window).height() * 0.002 + "px solid #F0F0F0;");
	$(".wfj12_019_vip_content").find("td").find("img").attr("style",
			"height:" + $(window).height() * 0.03 + "px;");
	$(".wfj12_019_vip_click02").click(function() {
				open("wfj12_020.html", "_self");
			});

	/* 地球数字商城 */
	// 中联园区搜索

	
	
	$(".wfj12_019_intro_div").find("img").css({
				"height" : "100%",
				"width" : "100%",
				"display" : "none"
			})
	$(".wfj12_019_intro_img").css("height", $(window).width() * 0.12)
	if ($(window).width() < $(window).height()) {
		$(".wfj12_019_intro_vadio .bofang").css("display", "none")
		$(".wfj12_019_intro_vadio .bofang.allWindow").css("display", "none")
		$(".wfj12_019_intro_vadio video").attr("controls", "controls")
	}
	$(".wfj12_019_intro_div").mouseover(function() {

		if ($(window).width() > $(window).height()) {
			$(".wfj12_019_intro_vadio .bofang.allWindow").show()
			$(".wfj12_019_intro_vadio .bofang").show()
			$(".wfj12_019_intro_vadio .bofang").css({
				"margin-top" : -$(".wfj12_019_intro_vadio").height() * 0.6
						+ "px",
				"margin-left" : "32%"
			})
			$(".wfj12_019_intro_vadio .bofang.allWindow").css({
				"margin-top" : -$(".wfj12_019_intro_vadio").height() * 0.16
						+ "px",
				"margin-left" : "62%"
			})
		} else {
			$(".wfj12_019_intro_vadio .bofang").css("display", "none")
			$(".wfj12_019_intro_vadio .bofang.allWindow")
					.css("display", "none")
			$(".wfj12_019_intro_vadio video").attr("controls", "controls")
		}
	})
	$(".wfj12_019_intro_div").mouseout(function() {

		if ($(window).width() > $(window).height()) {
			$(".wfj12_019_intro_vadio .bofang.allWindow").hide()
			$(".wfj12_019_intro_vadio .bofang").hide()
			$(".wfj12_019_intro_vadio .bofang").css({
				"margin-top" : -$(".wfj12_019_intro_vadio").height() * 0.65
						+ "px",
				"margin-left" : "30%"
			})
			$(".wfj12_019_intro_vadio .bofang.allWindow").css({
				"margin-top" : -$(".wfj12_019_intro_vadio").height() * 0.19
						+ "px",
				"margin-left" : "62%"
			})
		} else {
			$(".wfj12_019_intro_vadio .bofang").css("display", "none")
			$(".wfj12_019_intro_vadio .bofang.allWindow")
					.css("display", "none")
			$(".wfj12_019_intro_vadio video").attr("controls", "controls")
		}
	})
	//暂时拿掉
	/*$(".wfj12_019_intro_vadio .bofang").click(function() {
				// $(this).text("暂停")
				if ($(this).text() == "播放") {
					$(this).text("暂停")
				} else {
					$(this).text("播放")
				}
				$(this).fadeOut()
			})*/
	$(".wfj12_019_intro_div").attr("style", "height", "100%")

	// 转换页面，并判断页面中的内容是否需要滚动条并隐藏
	var intro = $(".wfj12_019_intro").height();
	var news = $(".wfj12_019_news").height();
	var vip = $(".wfj12_019_vip").height();
	var numshop = $(".wfj12_019_numshop").height();

	if ($(".slider-panel").find("img").height() > $(window).height() * 0.3) {
		if (intro > $(".wfj12_019_content").height()) {
			$(".wfj12_019_hidden").css("width",
					parseInt($(".wfj12_019_content").width() + 17) + "px");
		} else {
			$(".wfj12_019_hidden").css("width",
					$(".wfj12_019_content").width() + "px");
		}
	}

	$(".list-group").find("li").on("click",function() {
		var title = $.trim($(this).text());
		
		var he = 0;// 数字商城查询块高度
		if (title == "简介") {
			document.location.href = basePath+"page/WFJClient/PersonalJoining/BriefIntroduction.jsp";
			
			/*$(".wfj12_019").css("height", "");
			$(".slider").css("display", "block");// 隐藏头部
			$(".disnone").css("display", "none");
			$(".wfj_search").css("display", "none");
			$(".wfj12_019_intro").css("display", "block");*/
			/*
			 * $(".wfj12_019_content") .css( "height", parseInt($(window)
			 * .height() - $( ".slider") .height() - $( ".wfj12_019_top_title")
			 * .height()) + "px;");
			 */
			if (intro > $(".wfj12_019_content").height()) {
				$(".wfj12_019_hidden").css("width",
						parseInt($(".wfj12_019_content").width() + 17) + "px");
			} else {
				$(".wfj12_019_hidden").css("width",
						$(".wfj12_019_content").width() + "px");
			}
		} else if (title == "中联园传播") {
			document.location.href = basePath+"/page/WFJClient/PersonalJoining/new/CompanyBuy.jsp";
			//document.location.href = basePath+"ea/wfjshop/ea_getNewsList.jspa?";
		}else if(title == "资讯"){
	
			document.location.href = basePath+"ea/wfjshop/ea_getNewsList.jspa?typeNews=";
			//document.location.href = basePath+"/page/WFJClient/PersonalJoining/new/CompanyBuy.jsp";
		} else if (title == "办公") {
			open(basePath + "/page/ea/index.jsp", "_self");
		} else if (title == "入驻") {
			//$(".slider").css("display", "none");// 隐藏头部
			
			
			open(basePath + "/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform",
					"_self");
			
		} else if (title == "会员中心") {
			//$(".slider").css("display", "none");// 隐藏头部
			open(basePath + "/ea/consignee/ea_toVipCenter.jspa?backu=2", "_self");
		} else if (title == "商家") {//ea_getAllCompanyList ea_getAllIndustryList
			document.location.href = basePath+"ea/industry/ea_getAllCompanyList.jspa?industryType="
		} else if(title=='商城'){
			document.location.href=basePath+"ea/digitalmall/ea_DigitalMall.jspa?";
		}else if(title=='产品发布'){
			alert("加入中");
			document.location.href=basePath+"ea/wfjshop/ea_consultation.jspa?";
		}
	});
	$("#table1").find("a").click(function(){
		$(".table1").find("a").css("color", "#373737");
		$(this).css("color", "#F74C31");
		if ($(this).text() == "下载微分金APP"){
			document.location.href = basePath + "ea/buyproducts/ea_queryForm.jspa";
		}else if ($(this).text() == "中联园区首页"){
			document.location.href = basePath+"ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
		}
	});
	
	$("#moneysp").click(function(){
		document.location.href=basePath+"/page/WFJClient/PersonalJoining/MakeMoneyNewYear.jsp";
	});
	
});
//暂时拿掉
/*// 播放
function playPause() {
	var myVideo = document.getElementsByTagName('video')[0];
	if (myVideo.paused) {
		myVideo.play();
	} else {

		myVideo.pause();
	}
}
// 全屏
function allWindow() {
	var elem = document.getElementById("myvideo");
	if (elem.requestFullscreen) {
		elem.requestFullscreen();
	} else if (elem.mozRequestFullScreen) {
		elem.mozRequestFullScreen();
	} else if (elem.webkitRequestFullscreen) {
		elem.webkitRequestFullscreen();
	}

}*/
