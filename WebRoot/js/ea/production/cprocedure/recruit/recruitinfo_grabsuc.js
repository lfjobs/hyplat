$(function(){

	$(".main").css({"height":$(window).height()-$("header").outerHeight()-$("footer").outerHeight()+"px"});
	$(".img_wai").css({"width":$(".zhao_main_lis_left").width()+"px"});
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
    $(".tou").css("background","#ccc");

	
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
		window.open(basePath+"ea/bidrecruit/ea_showResumedetail.jspa?resumeID="+resumeID+"&position="+position+"&type=抢人才&back="+back,"_self");
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
	 
	   //抢人才
		$(".tou").click(function(){
			if($(".selected").length>0){
			     $(".tan_mo").show();
			}
			
		});
		
		
		//取消
		$(".no").click(function(){
			$(".tan_mo").hide();
		});
		
		//确定
		$(".confrim").click(function(){
			$(".tiptan").hide();
			token = 0;
		});
		
		//抢人才
		$(".yue").click(function(){
	         if(token == 1){
	        	 return;
	         }
	         token = 1;
	         
	         var resumeIDs = "";
	         
	         $(".selected").each(function(){
	        	 
	        	 resumeIDs+= $(this).find(".resumeID").text()+",";
	         });
	         if(resumeIDs!=""){
	        	 resumeIDs = resumeIDs.substring(0,resumeIDs.length-1);
	         }
	         
	         
			   var url = basePath+"ea/bidrecruit/sajax_ea_grabResume.jspa";
			   $.ajax({
					url : url,
					type : "get",
					asycn : false,
					dataType : "json",
					data : {
						resumeIDs:resumeIDs
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						var login = member.login;
						if(login=="login"){
							document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
							return;
						}
						if(result!="success"){
							
							if(result=="nocom"){
								
								$(".tipcontent").text("公司会员才能进行此操作");
								
							}else if(result=="nomoney"){
								$(".tipcontent").text("金币池余额不足,请到会员中心金币池充值");
							}
							
							$(".tan_mo").hide();
							$(".tiptan").show();
							
							return false;
							
						}
						
						//成功后跳转
						window.open(encodeURI(basePath+"ea/bidrecruit/ea_grabSuccess.jspa?position="+position),"_self");
						
						
						
					},error:function(data){
						alert("抢人才失败");
					}
				   
			       });
			
		});
		
	

});
