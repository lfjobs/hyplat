$(function(){

	
	$("body").css({"overflow":"hidden"});
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});

	
	//点击职位展示职位详情
	$(document).on("click",".gs",function(){
		var riId = $(this).parents("li").find(".riId").text();
		var position = $(this).parents("li").find(".position").text();
		window.open(basePath+"ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&companyId="+companyId+"&ccompanyID="+ccompanyId+"&search="+companyname+"&back="+back,"_self");
	});
	
	//回退
 	$(".arrar").click(function(){
 		if(back=="3"){
 			// window.open(basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId,"_self");
            history.back();
 		}else{
 	 		document.location.href = basePath+"ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&back="+back;
 		}
 	});
 	
	
});
