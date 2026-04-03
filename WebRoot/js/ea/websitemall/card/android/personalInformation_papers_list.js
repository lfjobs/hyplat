/**
 * Created by Administrator on 2016/1/30 0030.
 */
$(function(){
	$(".wfj_return").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
    $(".add").click(function(){
        $(".add_1").css("display","block");
        
    });
    $(".add_1 .mu").click(function(){
        $(".add_1").css("display","none");
    });
	
	
	$(".add_1").find("li").click(function(){
		$(".add_1").css("display","none");
		
		var wenzi = $(this).text();
		$(".papers_body ul:last-child").append("<li style='line-height:"+(window.innerHeight*0.0734)+"px;font-size:"+$(window).height()*0.03+"px;'><div class='pic'><img src='"+basePath+"images/ea/websitemall/card/sfz1.png' style='height:"+window.innerHeight*0.0468+"px;width:"+window.innerHeight*0.0468+"px;'/></div>"+wenzi+"<div class='more'><img src='"+basePath+"images/ea/websitemall/card/more1.png'/></div></li>");
		});
	$(".papers_body").find("li").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPapersInformation.jspa?staffId="+$("#staffId").val()+"&category="+$(this).find(".more").attr("id")+"&editType="+editType+"&backurl="+backurl;
	});
});


$(document).ready(function(e) {
	$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
	
	//中联园区头部
    $(".top").attr("style","height:"+$(window).height()*0.059+"px;line-height:"+$(window).height()*0.059+"px;");
    $(".top").find("li").attr("style","width:15%;");
    $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
    $(".top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.035+"px;");
	
	$(".pic").attr("style","height:"+window.innerHeight*0.0468+"px;width:"+window.innerHeight*0.0468+"px;");
	$(".pic").find("img").attr("style","height:"+window.innerHeight*0.0468+"px;width:"+window.innerHeight*0.0468+"px;");
	$(".more img").attr("style","height:"+$(window).height()*0.04+"px;width:auto");
	
	 $(".papers_body li").attr("style","line-height:"+(window.innerHeight*0.0734)+"px;font-size:"+$(window).height()*0.03+"px;");
	
	 $(".papers_body").attr("style","width:"+window.innerWidth+"px;height:"+(window.innerHeight-$(".top").height())+"px;overflow:hidden;");
	 $(".papers_body_hidden").attr("style","width:"+(window.innerWidth+17)+"px;height:"+(window.innerHeight-$(".top").height())+"px;overflow-y:scroll;");

	if($(window).width()>$(window).height()){
		$(".main").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
	}
});