$(function(){
	$(".information").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getReceivingAddressDetails.jspa?editType="+editType+"&staffId="+$("#staffId").val()+"&addressID="+$(this).attr("id")+"&backurl="+backurl +"&flag="+flag;
	});
	$(".arrow").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
});

$(document).ready(function(e){
		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			//头部
        $(".top").attr("style","height:"+$(window).height()*0.058+"px;line-height:"+$(window).height()*0.058+"px;");
        $(".top").find("li").attr("style","width:10%;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:80%;font-size:"+$(window).height()*0.035+"px;");
		//add
		$(".add button").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px; margin:"+$(window).height()*0.02+"px auto;");
		
		//line
		$(".line").attr("style","height:"+$(window).height()*0.01+"px;");
		//content
		$(".content").attr("style","height:"+$(window).height()*0.83+"px;");
		//li;
		 $(".content ul").find("li").attr("style","padding:"+$(window).height()*0.02+"px 0;");
		 $(".content ul").find("li").eq(0).attr("style","background:#5e6b85;color:#fff;padding:"+$(window).height()*0.02+"px 0;");
		 $(".content ul").find("li").eq(0).find("h6").prepend("【默认】");
		 $("h3").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px;");
		 $("h6").attr("style","line-height:"+$(window).height()*0.04+"px;font-size:"+$(window).height()*0.02+"px;");
		 //img
		 $(".right img").attr("style","width:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px; margin:"+$(window).height()*0.03+"px auto;");
		var u = window.navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		if(isAndroid==true){
			$(".new_").attr("style","height:"+$(window).height()*0.058+"px;");
		}else if(isiOS==true){
		$(".new_").hide();
		}

		 $(".address").css("height",window.innerHeight*0.05+"px");
		if(flag==2){
        $(".top").show();
    }else {
        $(".top").hide();
    }
});
		