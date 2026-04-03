

$(function(){
    $(".bug-con").height($(window).height()-$("header").outerHeight()-$(".bug-nav").outerHeight()-$(".bug-nav2").outerHeight()*2-$("footer").outerHeight()+"px");
    //计算总列表宽度
	var listWidth_1=$(".bug-nav li").length;
	var listWidth=0;
	for(var i=0;i<listWidth_1;i++){
		listWidth+=$(".bug-nav").children("li").eq(i).outerWidth(true);
	}
	$(".bug-nav").width(listWidth+10);

	$(".sec-checked").each(function (){
		console.log("单号："+cashierBillsId);
		if($(this).find("aside").attr("checkCasId")==cashierBillsId){
			if($(this).find("aside").is(".aside_yes")){
				$(this).find("aside").removeClass().addClass("aside_no");
			}else{
				$(this).find("aside").removeClass().addClass("aside_yes");
				if(menuId=="cy"){
					console.log("选中");
					circularize();
				}
			}
		}
	});
	//商品点击选中
	$(document).on("click","#sec-checked",function(){
		if($(this).find("aside").is(".aside_yes")){
			$(this).find("aside").removeClass().addClass("aside_no");
		}else{
			$(this).find("aside").removeClass().addClass("aside_yes");
		}
	})
	
    
});