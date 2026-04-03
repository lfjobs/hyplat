function fanhui(){
	
	var url = basePath + "/ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
	document.location.href = url;
	
}

function Jump(obj){
	var codepid = $(obj).find("#codepid").val();
	var url = basePath + "/ea/industry/ea_getiInvestment.jspa?stype=two&codesId="+codepid+"&ajax=";
	document.location.href = url;
	
	}
	$(function(){
		$(".nav_right").css({"width":$(".nav_left").width()+"px","height":$(".nav_left").height()+"px"})
	$(".fix_top").css("height",$(".navbar-fixed-top").height()+"px")
	
        $(".list-group-item").css({"overflow":"hidden"})
        $(".nav_img").css({"width":$(window).width()*0.08+"px","height":$(window).width()*0.08+"px"})
        $(".list-group-item-text").css({"line-height":$(".list-group-item").height()+"px","margin-left":"15px"})
        $(".list-group").css({"padding":"0 10px"})
        $(".list-group a").css({"border":"none","border-bottom":"1px solid #e3e3e3","border-bottom-right-radius":"0","border-bottom-left-radius":"0","margin-bottom":"0","border-bottom":"1px solid #e3e3e3"})

    })