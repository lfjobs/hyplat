$(function(){
    $("body").css({"overflow":"hidden"});
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".search_down").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	$(".img_wai").css({"width":$(".zhao_main_lis_left").width()+"px"});
	$(".Sort-height,.grade-eject").css({"height":$(window).height()*0.6+"px"});
	$(".sex-eject").css({"height":$(window).height()*0.25+"px"});

	//搜索框获取焦点 效果
	$("#search").focus(function(){
		$(".paixu_nav").find(".orange").removeClass("orange");
    	$('.sel').removeClass('grade-w-roll');
		$(".asd").show();
	});
	//取消
	$(".cancel").click(function(){
		$("#search").val("");
		$(".asd").hide();
	});
	
	
	//取消
	$(".tipcan").click(function(){
		$(".tiptan").hide();
		token1 = 0;
	});
	
	
	//确认
	$(".tipconfirm").click(function(){
		if($(this).text()=="立即升级会员"){
			document.location.href = basePath+"/ea/wfjshop/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&zlyq=1"
		}else if($(this).text()=="查看金币池"){
			document.location.href = basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+users+"&khd=1";
		}
		token1 = 0;
	});

	//搜索框输入搜索词
	$("#search").bind('input propertychange', function() {
		var search = $("#search").val();
		if(search==""){
			$(".search_down").css("display","none");
			$(".main").css("display","block");
			$(".sousuo").hide();
			$(".cancel").show();
			//return false;
		}
		
	   var url = basePath+"ea/bidrecruit/sajax_ea_searchPosByKeyword.jspa";
		$.ajax({
			url : url,
			type : "get",
			asycn : false,
			dataType : "json",
			data : {
				"parameter" : $.trim(search)
			},
			success : function(data) {
				var member = eval("(" + data + ")");
				var searchlist = member.searchlist;
				$(".search_down").html("");
		
				$(".sousuo").css("display","block");
				$(".cancel").css("display","none");
				$(".search_down").css("display","block");
				$(".main").css("display","none");
				if(searchlist==null){
					return false;
				}
				var html = "";
				var obj = "";
				for ( var i = 0; i < searchlist.length; i++) {
					obj=searchlist[i];
					var codeValue = obj[1];
					var codeID = obj[0];
					var codePID = obj[2];
					html+="<div class='resultlis'><img src='"+basePath+"images/ea/recruit/search999.png' alt=''> "+codeValue.replace(search,"<span class='keyword'>"+search+"</span>")+"</div>";	
				}
				$(".search_down").html(html);
				
			},error:function(data){
				alert("搜索结果失败");
			}
		   
	       });
		
	});
	
	//搜索 查询结果
	$(document).on("click",'.resultlis,.sousuo',function(){
		var keyword = "";
		if($(this).attr("class").indexOf("resultlis")!=-1){
			keyword = $(this).text();
		}else{
			keyword = $("#search").val();
			
		}
		$("#searchForm").find("#keyword").val(keyword);
		startSearch();
		
		
	});
	
	
	
	 //抢人才
	$(document).on("click",".qiang",function(){
	     if($(this).text()=="已抢"){
	    	 return;
	     }
	     resumeIDs = $(this).parents("li").attr("id");
		 $(".tan_mo").show();

		
	});
	
	 //一起抢
	$(".toqiang").click(function(){
	     
	     resumeIDs="";
		 $(".selected").find(".resumeID").each(function(){
			 resumeIDs+=$(this).text()+",";
		 });
		 if(resumeIDs!=""){
			 resumeIDs = resumeIDs.substring(0,resumeIDs.length-1);
		 }
		
		 $(".tan_mo").show();

	});
	
	

	
	 //单抢
	$(document).on("click",".qiang",function(){
	     resumeIDs = $(this).parents("li").attr("id");
		 $(".tan_mo").show();

		
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

					}else{

					//成功后跳转
					$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
					$(".tan2").show();
					$(".tan_mo").hide();
			
					var array = resumeIDs.split(",");
					for ( var j = 0; j < array.length; j++) {
						$("li#"+array[j]).find(".qiang").text("已抢");
						$("li#"+array[j]).find(".qiang").removeClass("qiang").addClass("yiqiang");
						var classid = $("li#"+array[j]).find(".img_wai").find("img").eq(0).attr("class");
						if(classid.indexOf("dN")!=-1){
							$(".tou_j_k p:first").text(Number($(".tou_j_k p:first").text())-1);
							if($(".tou_j_k p:first").text()==0){
								$(".tou_j_k").hide();
							}
						}
						
						$("li#"+array[j]).find(".img_wai").find("img").hide();
						
					}
					}
					token = 0;
					
				},error:function(data){
					alert("抢人才失败");
				}
			   
		       });
		
	});
	
	

	//点击求职信息展示简历详情
	$(document).on("click",".zhao_main_lis_center",function(){
		var resumeID = $(this).parents("li").find(".resumeID").text();
		var position = $(this).parents("li").find(".position").text();
		window.open(basePath+"ea/bidrecruit/ea_showResumedetail.jspa?resumeID="+resumeID+"&position="+position+"&type=抢人才&back=2","_self");
	});
	
	
	


	

	$(".again").click(function(){
		$(".tan_kuang").css("background","");
		$(".tan_kuang").hide();
	});
	
	//选择
	$(document).on("click",".img_wai",function(){
 		$(this).find(".quan_xuan").toggleClass("dN");
 		var classid = $(this).find("img").eq(0).attr("class");
 		var ypvalue = $(".tou_j_k p:first").text();
 		if(classid.indexOf("dN")!=-1){//说明选中
 			$(this).parents("li").addClass("selected");
 			ypvalue++;
 		}else{
 			$(this).parents("li").removeClass("selected");
 			ypvalue--;
 		}
 		$(".tou_j_k p:first").text(ypvalue);
 		if(ypvalue==0){
  		   $(".tou_j_k").hide();
       	}else{
       	   $(".tou_j_k").show();
       	}
 	});
	

 	
 	
 	//回退
 	$(".arrar").click(function(){

 		window.open(basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa","_self");

 	});
 	
 
	
	$(".tan_kuang").click(function(){
		$(this).hide();
	});
	
	
	//工作经验
	$(".teli").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')) {
        	$(this).removeClass("orange");
            $('.grade-eject').removeClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
        } else {
        	$(".paixu_nav").find(".orange").removeClass("orange");
        	$(this).addClass("orange");
        	$('.sel').removeClass('grade-w-roll');
            $('.grade-eject').addClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
   
        }
    });
	
	// j经验
	$("#gradew").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				var value = $(this).find("span").text();
				if (value== "全部") {
				     value="经验";
				}
				$(".teli").find("span").text(value);
				$('.grade-eject').removeClass('grade-w-roll');
				$(".teli").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				$("#searchForm").find("#experience").val(value);
				startSearch();

	});
	
	
	
	
	//年龄
	$(".Sort").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')) {
        	$(this).removeClass("orange");
            $('.Sort-eject').removeClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
        } else {
        	$(".paixu_nav").find(".orange").removeClass("orange");
        	$(this).addClass("orange");
        	$('.sel').removeClass('grade-w-roll');
            $('.Sort-eject').addClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
        	
        }
    });
	//年龄
	$("#Sort-Sort").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				var value = $(this).find("span").text();
				if (value== "全部") {
					value="年龄";
				}
				$(".Sort").find("span").text(value);
				$('.Sort-eject').removeClass('grade-w-roll');
				$(".Sort").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				$("#searchForm").find("#age").val(value);
				startSearch();

			});
	
	//学历
	$(".learn").click(function(){
        if ($('.learn-eject').hasClass('grade-w-roll')) {
        	$(this).removeClass("orange");
            $('.learn-eject').removeClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
        } else {
        	$(".paixu_nav").find(".orange").removeClass("orange");
        	$(this).addClass("orange");
        	$('.sel').removeClass('grade-w-roll');
            $('.learn-eject').addClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
        	
        }
    });
	//学历
	$("#learn-Sort").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				var value = $(this).find("span").text();
				if (value== "全部") {
					
					value="学历";
				}
				$(".learn").find("span").text(value);
				$('.learn-eject').removeClass('grade-w-roll');
				$(".learn").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				$("#searchForm").find("#education").val(value);
				startSearch();
			});
	

	//性别
	$(".sex").click(function(){
        if ($('.sex-eject').hasClass('grade-w-roll')) {
        	$(this).removeClass("orange");
            $('.sex-eject').removeClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
        } else {
        	$(".paixu_nav").find(".orange").removeClass("orange");
        	$(this).addClass("orange");
        	$('.sel').removeClass('grade-w-roll');
            $('.sex-eject').addClass('grade-w-roll');
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
        	
        }
    });
	//性别
	$("#sex-Sort").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				var value = $(this).find("span").text();
				if (value== "全部") {
					
					value="性别";
				}
				$(".sex").find("span").text(value);
				$('.sex-eject').removeClass('grade-w-roll');
				$(".sex").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				$("#searchForm").find("#sex").val(value);
				startSearch();
			});
	
	getHeight();
	initCri();

});

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").length>0) {
		if ($(".last").offset().top - $(".last").height() * 2 <= $(window)
				.height()) {
			if (pagenumber < pagecount) {
				loaded();
			}
		}
	}
}
    

function loaded() {
	pagenumber += 1;
	var fromData = $('#searchForm').serialize();
	
	var url = basePath + "ea/bidrecruit/sajax_ea_startSearch.jspa?"+fromData;
	$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pagenumber,
					 ajax : "ajax",
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var html = "";
					var obj;

					if (pagenumber == 1) {
						$(".bodyresult").html("");
					}
					$(".last").removeClass("last");

					if (pageForm != null) {

						pagecount = pageForm.pageCount;
						count = pageForm.recordCount;
						pageSize = pageForm.pageSize;
						var obj;
						var array = new Array();
					
						for ( var i = 0; i < pageForm.list.length; i++) {
							obj = pageForm.list[i];
							if (i == pageForm.list.length - 1) {
								array.push("<li class='last' id='"+obj[0]+"'>");
							} else {
								array.push("<li id='"+obj[0]+"'>");
							}
							array.push("<span style='display:none;' class='resumeID'>"+obj[0]+"</span>");
							array.push("<span style='display:none;' class='position'>"+obj[1]+"</span>");
							array.push("<div class='zhao_main_lis_left pull-left text-center img-responsive'>");
							if(obj[8]=="0"){
								array.push("<div class='img_wai'>");
								array.push("<img class='quan_xuan' style='width: 2rem;' src='"+basePath+"images/ea/recruit/ico_zhi_06.png'/>");
								array.push("<img class='dN quan_xuan' style='width: 2rem;' src='"+basePath+"images/ea/recruit/chan_07.png'/>");
								array.push("</div>");	
							}
							
							array.push("</div>");
							array.push("<div class='zhao_main_lis_center pull-left'>");
							array.push("<h4>" + obj[1] + "</h4>");
							array.push("<p>" + obj[2] + "</p>");
							array.push("<div>");
							array.push("<span class='yaoqiu'>");
							
							array.push("<img class='little_img' src='"+basePath+"images/ea/recruit/ico_13.png' alt='' />");
							array.push(obj[3]);
							array.push("</span>");
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
									+ basePath
									+ "images/ea/recruit/ico_15.png'/>");
							array.push(obj[7]);
							array.push("</span>");
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='pull-left zhao_main_lis_right'>");
							array.push("<div class='text_wai'>");
							if(obj[8]=="0"){
								   array.push("<div class='qiang dan'>抢</div>");
								}else{
									array.push("<div class='yiqiang dan'>已抢</div>");
							 }
							
							
							array.push("</div>");
							array.push("</div>");
							array.push("</li>");
							

						}


					}
					$(".bodyresult").append(array.join(""));
					$(".img_wai").css({"width":$(".zhao_main_lis_left").width()+"px"});

					getHeight();

				},
				error : function(data) {
					alert("获取失败");
				}
			});
}

//开始搜索 提交表单
function startSearch(){
	$("#searchForm").attr("action",basePath+"ea/bidrecruit/ea_startSearch.jspa?type=gr");
	document.searchForm.submit.click();
}

/**
 * 
 * 初始化搜索条件
 */
function initCri(){
	//性别
	if(sex!=""){
		$(".sex").find("span").text(sex);
		$(".sex-eject").find("li").each(function(){
			var spanv = $(this).find("span").text();
			if(spanv==sex){
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
			}
		});
	}
	//学历
	if(education!=""){
		$(".learn").find("span").text(education);
		$(".learn-eject").find("li").each(function(){
			var spanv = $(this).find("span").text();
			if(spanv==education){
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
			}
		});
	}
	
	//经验
	if(experience!=""){
		$(".teli").find("span").text(experience);
		$(".grade-eject").find("li").each(function(){
			var spanv = $(this).find("span").text();
			if(spanv==experience){
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
			}
		});
	}
	
	//年龄
	if(age!=""){
		$(".Sort").find("span").text(age);
		$(".Sort-eject").find("li").each(function(){
			var spanv = $(this).find("span").text();
			if(spanv==age){
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
			}
		});
	}
	
}
