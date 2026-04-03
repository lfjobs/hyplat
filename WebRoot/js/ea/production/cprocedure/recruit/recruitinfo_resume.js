$(function(){
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"})
	$(".main").css("height",$(window).height()-$("header").outerHeight()-$("footer").outerHeight()+"px");

	
	initData();
	
	
	if(iscol=="no"){
 		$("#collect").removeClass("dN");
 		$("#cancelcol").addClass("dN");

 	}else{
 		$("#collect").addClass("dN");
 		$("#cancelcol").removeClass("dN");
 		
 	}
 	
 	
	if(istou=="yes"){
 		token = 1;
 		$(".toujianli").text("已抢");

 	}
	
	//收藏
	$("#collect").click(function(){
		
		   var url = basePath+"ea/bidrecruit/sajax_ea_collectPosition.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					id:resumeID,
					type:"01"
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					
					
					
					
				},error:function(data){
					alert("收藏失败");
				}
			   
		       });
		
	});
	
	//取消收藏
	$("#cancelcol").click(function(){
		
		   var url = basePath+"ea/bidrecruit/sajax_ea_cancelCollect.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					id:resumeID
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					
					
					
					
				},error:function(data){
					alert("收藏失败");
				}
			   
		       });
		
	});
	
	
	
	//抢人才
	$(".yue").click(function(){
         if(token == 1){
        	 return;
         }
         token = 1;
		   var url = basePath+"ea/bidrecruit/sajax_ea_grabResume.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					resumeIDs:resumeID
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					users = member.user;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					if(result!="success"){
						
						if(result=="nocom"){
							
							$(".tipcontent").text("公司会员才能进行此操作");
							$(".tipconfirm").text("立即升级会员");
							
						}else if(result=="nomoney"){
							$(".tipcontent").text("金币池余额不足,请到会员中心金币池充值");
							$(".tipconfirm").text("查看金币池");
							
						}
						
						$(".tan_mo").hide();
						$(".tiptan").show();
						token = 0;
						return false;
						
					}
					
					//成功后跳转
					window.open(encodeURI(basePath+"ea/bidrecruit/ea_grabSuccess.jspa?resumeID="+resumeID+"&position="+position+"&back="+back),"_self");
					
					
					
				},error:function(data){
					alert("抢人才失败");
				}
			   
		       });
		
	});
	
	//抢人才
	$(".tou").click(function(){
		
		if(token==1){
			return;
		}
		$(".tan_mo").show();
		
	});
	
	//取消
	$(".no").click(function(){
		$(".tan_mo").hide();
	});
	
	//取消
	$(".tipcan").click(function(){
		$(".tiptan").hide();
		token = 0;
	});
	
	//确认
	$(".tipconfirm").click(function(){
		if($(this).text()=="立即升级会员"){
			document.location.href = basePath+"/ea/wfjshop/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&zlyq=1"
		}else if($(this).text()=="查看金币池"){
			document.location.href = basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+users+"&khd=1";
		}
		token = 0;
	});
	
	
	
	

	 
		//回退
	$(".arrar").click(function(){
		 switch(back)
         {
               case "1":
                  document.location.href = basePath+"ea/bidrecruit/ea_getResumeIndex.jspa?lei=gr";
                  break;
               case "2":
            	   document.location.href = basePath+"ea/bidrecruit/ea_startSearch.jspa?type=gr";
            	   break;

               default:
            	   history.back();
               break;
         }
	 });
	
	$(".shoucang").click(function(){
		$(this).find("img").toggleClass("dN");
	});

});

function initData(){

	//处理联系方式
    
	
	//处理工作经验
	if(date1!=""&&date2!=""&&date2!=""){
		var dates1 = new Date(date1);
		var dates2 = new Date(date2);
		var dates3 = new Date(date3);
		var result = "";
		if(dates3<dates1){
		     result = dates1.getTime()-dates2.getTime();
		  
			
		}else{
			 result = new Date().getTime()-dates2.getTime();
			
		}
		
		  var days=Math.floor(result/(24*3600*1000*365));
		 $(".edu").text(days+"年");
	}
	
	
}
