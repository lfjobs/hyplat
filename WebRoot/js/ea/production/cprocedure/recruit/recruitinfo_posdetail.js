$(function(){
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	$(".main").css("height",$(window).height()-$("header").outerHeight()-$("footer").outerHeight()+"px");
 	//回退
 	$(".arrar").click(function(){
 		
 		 switch(back)
         {
               case "1":
                  document.location.href = basePath+"ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
                  break;
               case "2":
            	   document.location.href = basePath+"ea/bidrecruit/ea_startSearch.jspa?type=gs";
            	   break;
               case "3":
            	   open(basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId,"_self");
            	   break;
               default:
            	   history.go(-1);
               break;
         }
 		
 		
 	});
 	if(iscol=="no"){
 		$("#collect").removeClass("dN");
 		$("#cancelcol").addClass("dN");

 	}else{
 		$("#collect").addClass("dN");
 		$("#cancelcol").removeClass("dN");
 		
 	}
 	
 	
	if(istou=="yes"){
 		token = 1;
 		$(".toujianli").text("已投");

 	}
 	
 
 	//点击职位展示职位详情
	$(document).on("click",".gs",function(){
		var riId = $(this).parents("li").find(".riId").text();
		var position = $(this).parents("li").find(".position").text();
		window.open(basePath+"ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&companyId="+companyId+"&ccompanyID="+ccompanyId+"&search="+companyname+"&back="+back,"_self");
	});
	

	//投简历
	$(".toujianli").click(function(){
		if(token==1){
			return;
		}
		token = 1;
		
		   var url = basePath+"ea/bidrecruit/sajax_ea_postResume.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					riIds:riId
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					
					staffid = member.staffid;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					if(result=="noresume"){
						$(".tan_mo").show();
					//	alert("投递失败，请完善您的简历");
						token = 0;
						return;
					}
					if(result=="success"){
						//成功后跳转
						window.open(encodeURI(basePath+"ea/bidrecruit/ea_postSuccess.jspa?riId="+riId+"&position="+position+"&back="+back),"_self");
					}
					
					
				},error:function(data){
					alert("投递失败");
				}
			   
		       });
		
	});
	
	
	$(".can").click(function(){
		$(".tan_mo").hide();
		
	});
	//完善简历
	$(".wan").click(function(){
		document.location.href = basePath+"ea/resumes/ea_savePersion.jspa?staffid="+staffid+"&type=";
		
	});
	
	//收藏
	$("#collect").click(function(){
		
		   var url = basePath+"ea/bidrecruit/sajax_ea_collectPosition.jspa";
		   $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					id:riId,
					type:"00"
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					alert("收藏成功");
					
					
					
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
					id:riId
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					var login = member.login;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					
					alert("取消成功");
					
					
				},error:function(data){
					alert("收藏失败");
				}
			   
		       });
		
	});
	
	$(".qingq_gongsi").click(function(){
		var ccompanyID = $(this).find(".ccompanyID").text();
		window.open(basePath+"ea/bidrecruit/ea_showCompanydetail.jspa?ccompanyID="+ccompanyID+"&riId="+riId+"&position="+position+"&companyId="+companyId+"&search="+companyname+"&back="+back,"_self");

	});
	
	$(".shoucang").click(function(){
		$(this).find("img").toggleClass("dN");
	});
	

});

