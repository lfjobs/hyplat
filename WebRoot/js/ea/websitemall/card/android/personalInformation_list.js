$(function() {
	$("#more").live("click",
			function() {
				window.location.href = basePath
						+ "ea/perinfor/ea_getPersonalData.jspa?staffId="
						+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
	
	
	$("#bank").live("click",function() {
		
		if(editType=="00"){
			window.location.href = basePath
					+ "ea/perinfor/ea_getBankCardInformation.jspa?staffId="
					+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
		}
		
		
		/*if(upgrade == '00'){
			alert("系统升级，敬请原谅。");
		}else{
			if(editType=="00"){
				window.location.href = basePath
						+ "ea/perinfor/ea_getBankCardInformation.jspa?staffId="
						+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			}
		}*/						
			});
	
	
	
	$("#exchange").click(function(){
		jhmp();
	});
	$(".wfj01_006_ewm").live("click",function(){
		$("#QRCode").css("display","block");
	});
	$("#QRCode").live("click",function(){
		$("#QRCode").css("display","none");
	});

});
$(function(){
    $(".addmore").live("click",function(){
        $(".add_1").css("display","block");
		$("body,.main").css("overflow","hidden");
        
    });
    $(".add_1 .mu").live("click",function(){
        $(".add_1").css("display","none");
		$("body,.main").css("overflow","auto");
    });
    if(editType!="00"){
    	$(".add_1").find("li").each(function(){
    		$(this).attr("class","wfj01_006_click"+$(this).attr("name"));
    		$(".wfj01_006_con_list ul:last-child").append($(this));
        });
    }
    
	$(".add_1").find("li").live("click",function(){
		$(".add_1").css("display","none");
		$("body,.main").css("overflow","auto");
		$(this).attr("class","wfj01_006_click"+$(this).attr("name"));
		$(".wfj01_006_con_list ul:last-child").append($(this));
		
		//alert()
		//$(".wfj01_006_con_list ul:last-child").append("<li><img src='img/sfz.png'/>"+wenzi+"</li>")
		});
});
$(document).ready(function(e) {
	$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
	//中联园区头部
    $(".wfj_top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
    $(".wfj_top").find("li").attr("style","width:15%;");
    $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
    $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.03+"px;");
	
	//title
	$(".wfj01_006_title .title_left .title_name").attr("style","font-size:"+$(window).height()*0.02+"px;");
	$(".wfj01_006_title .title_right").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");
	$(".title_hyimg").attr("style","height:"+$(window).height()*0.0718+"px;width:"+$(window).height()*0.0718+"px");
	
	//内容
	$(".wfj01_006_content ul li").attr("style","cursor:pointer;font-size:"+$(window).height()*0.03+"px;");
	$(".add_1 h3").attr("style","font-size:"+$(window).height()*0.03+"px;");
	
	$(".wfj_01_006_con_bottom div").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;margin-bottom:"+$(window).height()*0.01+"px; border-radius:"+$(window).height()*0.015+"px; margin-top:"+$(window).height()*0.03+"px;");
	
	
	if($(window).width()>$(window).height()){
		$(".wfj01_006").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
	}

});
// 个人交换名片
function jhmp() {
	if (user != null) {
		window.location.href = basePath
				+ "ea/wfjshop/ea_personGiveCard.jspa?user=" + user
				+ "&activity=" + activity+"&weidiantype=person&editType="+editType+"&backurl="+backurl;
	}
}
$(document).ready(
		function(e) {
			$("body").attr(
					"style",
					"width:" + $(window).width() + "px;height:"
							+ $(window).height() + "px;");
			// 中联园区头部
			$(".wfj_top").attr(
					"style",
					"height:" + $(window).height() * 0.06 + "px;line-height:"
							+ $(window).height() * 0.06 + "px;");
			$(".wfj_top").find("li").attr("style", "width:15%;");
			$(".wfj_top").find("li").find("img").attr("style",
					"height:" + $(window).height() * 0.03 + "px;");
			$(".wfj_top").find("li").eq(1)
					.attr(
							"style",
							"width:70%;font-size:" + $(window).height() * 0.025
									+ "px;");

			$(".wfj01_006_top_title").attr("style",
					"height:" + $(window).height() * 0.08 + "px;");
			$(".wfj01_006_top_title_bg").attr(
					"style",
					"height:" + $(window).height() * 0.05 + "px;line-height:"
							+ $(window).height() * 0.05 + "px; margin:"
							+ $(window).height() * 0.015 + "px auto;");
			$(".wfj01_006_top_title_bg").find("li").attr("style",
					"font-size:" + $(window).height() * 0.02 + "px;");
			$(".wfj01_006_top_title_bg").find("span").attr("style",
					"font-size:" + $(window).height() * 0.025 + "px;");
			$(".wfj01_006_top_title_bg").find("img").attr(
					"style",
					"height:" + $(window).height() * 0.03
							+ "px;width:auto;margin:" + $(window).height()
							* 0.01 + "px auto;");

			$(".wfj01_006_con_cons").find("td").attr(
					"style",
					"height:" + $(window).height() * 0.06 + "px;line-height:"
							+ $(window).height() * 0.06 + "px;");
			$(".wfj01_006_con_cons").find("td").find("input").attr(
					"style",
					"height:" + $(window).height() * 0.04 + "px;font-size:"
							+ $(window).height() * 0.02 + "px;");
			$(".wfj01_006_con_cons").find("td").find("img").attr("style",
					"height:" + $(window).height() * 0.04 + "px;width:auto;");

			$(".wfj01_006_success").attr("style",
					"margin-top:" + $(window).height() * 0.005 + "px;");
			$(".wfj01_006_success").find("ul").attr("style",
					"margin-bottom:" + $(window).height() * 0.005 + "px;");
			$(".wfj01_006_success").find("li").attr(
					"style",
					"line-height:" + $(window).height() * 0.04
							+ "px;font-size:" + $(window).height() * 0.02
							+ "px;");

			$(".wfj01_006_bottom_width").find("div").attr(
					"style",
					"height:" + $(window).height() * 0.06 + "px;line-height:"
							+ $(window).height() * 0.06 + "px;font-size:"
							+ $(window).height() * 0.025 + "px;margin-bottom:"
							+ $(window).height() * 0.01 + "px; border-radius:"
							+ $(window).height() * 0.015 + "px;");
			if ($(window).width() > $(window).height()) {
				$(".wfj01_006").attr(
						"style",
						"width:" + $(window).width() * 0.7 + "px;height:"
								+ $(window).height() + "px;");
			} else {
				$(".wfj01_006").attr(
						"style",
						"width:" + $(window).width() + "px;height:"
								+ $(window).height() + "px;");
			}

			$(".wfj01_006_title").attr(
					"style",
					"height:" + $(window).height() * 0.1 + "px;line-height:"
							+ $(window).height() * 0.1 + "px;");
			$(".wfj01_006_title").find("td").attr("style",
					"font-size:" + $(window).height() * 0.02 + "px;");
			$(".wfj01_006_title").find("div").attr(
					"style",
					"font-size:" + $(window).height() * 0.02 + "px;height:"
							+ $(window).height() * 0.04 + "px; line-height:"
							+ $(window).height() * 0.04
							+ "px; border-top-left-radius:"
							+ $(window).height() * 0.02
							+ "px; border-bottom-left-radius:"
							+ $(window).height() * 0.02 + "px;");

			$(".wfj01_006_hyimg").attr(
					"style",
					"height:" + $(window).height() * 0.08
							+ "px; width:auto;margin:" + $(window).height()
							* 0.01 + "px auto;");
			$(".wfj01_006_ewm").attr("style",
					"height:" + $(window).height() * 0.02 + "px; width:auto;");

			$(".wfj01_006_content").attr(
					"style",
					"width:"
							+ $(".wfj01_006").width()
							+ "px;height:"
							+ parseInt($(window).height()
									- $(".wfj_top").height()
									- $(".wfj01_006_title").height())
							+ "px;overflow:hidden;");
			$(".wfj01_006_hidden").attr(
					"style",
					"width:" + parseInt($(".wfj01_006_content").width() + 17)
							+ "px;height:" + $(".wfj01_006_content").height()
							+ "px;overflow:auto;");
			$("#photo").attr("style","border-radius:50%;width:"+$(window).height() * 0.08+"px;height:"+$(window).height() * 0.08+"px;overflow:hidden;margin:" + $(window).height()* 0.01 + "px auto;");
			$("#QRCodeDiv").css("width",window.innerWidth*0.6+"px");
			$("#QRCodeDiv").css("height",window.innerWidth*0.6+"px");
			$(".wfj01_006_showimg").find("img").attr("style","height:"+$(window).height()*0.1406+"px");
			if(editType!="00")
				$(".display").css("display","none");
			else
				$(".changecard").css("display","none");
			$(".wfj01_006_click01").live("click",function(){
				if(editType=="00")
				window.location.href=basePath+"ea/perinfor/ea_getPapersData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});	
			$(".wfj_return").live("click",function() {
				window.location.href=basePath+backurl;
			});
			$(".wfj01_006_click09").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getReceiptAddressList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click02").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getMienList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click03").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getPersonalHistoryData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click04").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type=00&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click05").live("click",function() {
				window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type=03&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click06").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type=01&editType="+editType+"&backurl="+backurl;
			});
			$(".wfj01_006_click07").live("click",function(){
				window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type=02&editType="+editType+"&backurl="+backurl;
			});
		});