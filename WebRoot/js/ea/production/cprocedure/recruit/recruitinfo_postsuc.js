$(function(){
	$(".main").css({"height":$(window).height()-$("header").outerHeight()-$("footer").outerHeight()+"px"});
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	$(".img_wai").css({"width":$(".zhao_main_lis_left").width()+"px"});

	
	$(".tou").css("background","#ccc");
	//处理时间
	$(".date").each(function(){
		var cur  = new Date();
		var zcur = new Date();
		var qcur = new Date();
		zcur.setTime(zcur.getTime()-24*60*60*1000);
		qcur.setTime(qcur.getTime()-2*24*60*60*1000);
		
		var curs = formatDate(cur);
		var zcurs = formatDate(zcur);
		var qcurs = formatDate(zcur);
		
        var time = $(this).text();
        if(curs==time){
    	   $(this).text("今天");
        }else if(zcurs==time){
     	   $(this).text("昨天");
        }else if(qcurs==time){
      	   $(this).text("前天");
        }else{
        	$(this).text(time.substring(time.indexOf("-")+1));
        }
		
	});
	

	
    //选择
 	$(".img_wai").click(function(){
 		$(this).find(".quan_xuan").toggleClass("dN");
 	
 		if($(this).find("img").eq(1).attr("class").indexOf("dN") != -1){//没选中
 			 $(".full").find("img").eq(1).addClass("dN");
 			 $(".full").find("img").eq(0).removeClass("dN");
 			 $(this).parents("li").removeClass("selected");
 			 if($(".selected").length==0){
 				 $(".tou").css("background","#ccc"); 
 			 }
 		}else{
 			 $(this).parents("li").addClass("selected");
 			 $(".tou").css("background","#FF6600");
 		}
 	});


 	//回退
 	$(".arrar").click(function(){
 		window.open(basePath+"/ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&back="+back,"_self");
 		
 	});

 	
 	//全选
	 $(".full").click(function() {
		$(this).find("img").toggleClass("dN");
		if ($(this).find("img").eq(0).attr("class").indexOf("dN") != -1) {//说明选中
			$(".img_wai").each(function() {
				$(this).find("img").eq(0).addClass("dN");
				$(this).find("img").eq(1).removeClass("dN");
				$(".yaoqing_tui_main").find("li").addClass("selected");
				$(".tou").css("background","#FF6600");

			});
		} else {
			$(".img_wai").each(function() {
				$(this).find("img").eq(1).addClass("dN");
				$(this).find("img").eq(0).removeClass("dN");
				$(".yaoqing_tui_main").find("li").removeClass("selected");
				 $(".tou").css("background","#ccc");
			});
		}


  });
	 //全部 投
	 $(".tou").click(function(){
		if( $(".selected").length>0){
		   $(".tan_mo").show();
		}
	 
	 });
	 //取消
	 $(".no").click(function(){
		 $(".tan_mo").hide();
		 
	 });
		//投简历
	$(".yue").click(function(){
			
			if(token==1){
				return false;
			}
			token = 1;
			
		    
	         var riIds = "";
	         
	         $(".selected").each(function(){
	        	 
	        	 riIds+= $(this).find(".riId").text()+",";
	         });
	         if(riIds!=""){
	        	 riIds = riIds.substring(0,riIds.length-1);
	         }
	         
			
			   var url = basePath+"ea/bidrecruit/sajax_ea_postResume.jspa";
			   $.ajax({
					url : url,
					type : "get",
					asycn : false,
					dataType : "json",
					data : {
						riIds:riIds
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						var login = member.login;
						if(login=="login"){
							document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
							return;
						}
						if(result=="noresume"){
							$(".tipcontent").text("投递失败，请完善您的简历");
							$(".tan_mo").hide();
							$(".tiptan").show();
							
						}else if(result=="success")
						{
                          	//成功后跳转
							window.open(encodeURI(basePath+"ea/bidrecruit/ea_postSuccess.jspa?riId="+riId+"&position="+position),"_self");	
						}
						
						
					},error:function(data){
						alert("投递失败");
					}
				   
			       });
			
		});
		
});

//日期格式化
function formatDate(date) {
        var datetime = date.getFullYear()
            + "-"// "年"
            + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                    + (date.getMonth() + 1))
            + "-"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                    .getDate());
    return datetime;
}

