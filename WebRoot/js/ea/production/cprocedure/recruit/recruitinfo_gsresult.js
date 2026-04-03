$(function(){
    $("body").css({"overflow":"hidden"});
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	$(".img_wai").css({"width":$(".zhao_main_lis_left").width()+"px"});
	$(".Sort-height").css({"height":$(window).height()*0.6+"px"});
	$(".grade-eject").css({"height":$(window).height()*0.25+"px"});
	$(".paixu_nav li").css({"width":$(window).width()*0.33+"px"});

	initCri();
	
/*	//定位地区
	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
         if (remote_ip_info.ret == '1') {
           var city = remote_ip_info.city;
           var province = remote_ip_info.province;
           var isp = remote_ip_info.isp;
           var country = remote_ip_info.country;
           var district= remote_ip_info.district;

         } else {
             alert('没有找到匹配的IP地址信息！');
         }
     });*/
	
	//附近
	$(".dis").click(function(){
		 if ($('.dis-eject').hasClass('grade-w-roll')) {
			    $(this).removeClass("orange");
	            $('.dis-eject').removeClass('grade-w-roll').hide();
	            $(".dis-eject").hide();
	            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");

	        } else {
	        	$(".paixu_nav").find(".orange").removeClass("orange");
	        	$(this).addClass("orange");
	        	$('.sel').removeClass('grade-w-roll');
	            $('.dis-eject').addClass('grade-w-roll').show();
	            $(".distance-eject").hide();
	            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
	    		
	    		$(".dis-eject .grade-left").find(".init").trigger("click");
	   
	        }
    });
	
	
	//附近
	$(".dis-eject .grade-left").find("li").click(function(){
		
		$(".grade-left").find(".white").removeClass("white");
		$(this).addClass("white");
		var $img = $(".dis-eject .grade-right").find(".gou").remove();
		$img.appendTo($(".dis-eject .ful"));
		$(".ful").find(".rightbor").hide();

		var cri = $(this).find("span").eq(0).text();
		var url = basePath+"ea/bidrecruit/sajax_ea_moreSearchCri.jspa";
		  $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					cri:cri
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var countlist = member.countlist;
					var list = member.list;
					$(".cx").remove();
					var array = new Array();
					var fullnum = 0;
					for ( var k = 0; k < list.length; k++) {
						array.push("<li class='cx'><span>"+list[k].goodsName+"</span>");
						var bool = true;
						for ( var j = 0; j < countlist.length; j++) {
							if(list[k].goodsName==countlist[j][1]){
								array.push("<span class='rightbor'>"+countlist[j][0]+"</span>");
								fullnum+=Number(countlist[j][0]);
								bool = false;
							}
							
						}
						if(bool){
							array.push("<span class='rightbor'>0</span>");
						}
						array.push("</li>");
					}
					
                    $("#content").append(array.join(""));
                    $(".dis-eject").find(".ful").find(".rightbor").text(fullnum);

					
				},error:function(data){
					alert("获取条件失败");
				}
			   
		       });
		
	});
	
	//月薪搜索
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
            $(".distance-eject").hide();
            $(".paixu_nav").find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
            var  salary = $("#searchForm").find("#salary").val();
            $("#Sort-Sort").find("li").each(function(){
    			var spanv = $(this).find("span").text();
    			if(spanv==salary){
    				var $img = $(this).parents("ul").find(".gou").remove();
    				$img.appendTo($(this));
    			}
    		});
        	
        }
    });
	
	
	//智能排序
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
            $(".distance-eject").hide();
            $(".dis-eject").hide();
            $(".paixu_nav").find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
            
        	var  sortbycri = $("#searchForm").find("#sortbycri").val();
    		$("#gradew").find("li").each(function(){
    			var spanv = $(this).find("span").text();
    			if(spanv==sortbycri){
    				var $img = $(this).parents("ul").find(".gou").remove();
    				$img.appendTo($(this));
    			}
    		});
           
   
        }
    });
	
	
	//更多
	$(".more").click(function(){
        if ($('.distance-eject').hasClass('grade-w-roll')) {
        	$(this).removeClass("orange");
            $('.distance-eject').removeClass('grade-w-roll').hide();
            $(".distance-eject").hide();
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");

        } else {
        	$(".paixu_nav").find(".orange").removeClass("orange");
        	$(this).addClass("orange");
        	$('.sel').removeClass('grade-w-roll');
            $('.distance-eject').addClass('grade-w-roll').show();
            $(".dis-eject").hide();
            $(".paixu_nav").find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
            $(this).find("img").attr("src",basePath+"images/ea/recruit/icon_04.png");
             //更多
        	var $left = $(".distance-eject .grade-left").find("li");
        	
        	var  education = $("#searchForm").find("#education").val();
        	var  publishDate = $("#searchForm").find("#publishDate").val();
        	var  experience = $("#searchForm").find("#experience").val();
        	var  posType = $("#searchForm").find("#posType").val();
        	var  comPro = $("#searchForm").find("#comPro").val();
        	var  comScale = $("#searchForm").find("#comScale").val();
        	
        	$(".distance-eject .grade-left").find(".init").trigger("click");
        	

        	
        	if(education!=""){
            	 $left.find(".education").text(education);

             }
        	if(publishDate!=""){
           	 $left.find(".publishDate").text(publishDate);

            }

        	if(experience!=""){
           	 $left.find(".experience").text(experience);

            }

        	if(posType!=""){
           	 $left.find(".posType").text(posType);

            }

        	if(comPro!=""){
           	 $left.find(".comPro").text(comPro);

            }
        	

        	if(comScale!=""){
           	 $left.find(".comScale").text(comScale);

            }


    		
   
        }
    });
	
	
	
	//更多点击
	$(document).on("click",'.zmain .grade-right>li',function(){
		$(this).parents("ul").find(".orange").removeClass("orange");
		$(this).find("span").addClass("orange");
		
		var $img = $(this).parents("ul").find(".gou").remove();
		$img.appendTo($(this));
		$img.show();
		$(this).parents("ul").find(".rightbor").show();
		$(this).find(".rightbor").hide();
		var select = $(this).find("span").eq(0).text()=="全部"?"":$(this).find("span").eq(0).text();
		$(".distance-eject .grade-left").find(".white").find("span").eq(1).text(select);
		
		
	});
	
	
	//更多查询条件
	$(".distance-eject .grade-left").find("li").click(function(){
		$(".grade-left").find(".white").removeClass("white");
		$(this).addClass("white");
		var $img = $(".distance-eject .grade-right").find(".gou").remove();
		$img.appendTo($(".distance-eject .ful"));
		$(".ful").find(".rightbor").hide();

		var cri = $(this).find("span").eq(0).text();
		var url = basePath+"ea/bidrecruit/sajax_ea_moreSearchCri.jspa";
		  $.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					cri:cri
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var countlist = member.countlist;
					var list = member.list;
					$(".cx").remove();
					var array = new Array();
					var fullnum = 0;
					for ( var k = 0; k < list.length; k++) {
						array.push("<li class='cx'><span>"+list[k].goodsName+"</span>");
						var bool = true;
						for ( var j = 0; j < countlist.length; j++) {
							if(list[k].goodsName==countlist[j][1]){
								array.push("<span class='rightbor'>"+countlist[j][0]+"</span>");
								fullnum+=Number(countlist[j][0]);
								bool = false;
							}
							
						}
						if(bool){
							array.push("<span class='rightbor'>0</span>");
						}
						array.push("</li>");
					}
					
                    $("#content").append(array.join(""));
                    $(".distance-eject").find(".ful").find(".rightbor").text(fullnum);
                    
                    moreCri();
			       
					
					
				},error:function(data){
					alert("获取条件失败");
				}
			   
		       });
		
	});
	
	
	//清空条件
	$(".clearcri").click(function(){
		$(".distance-eject .grade-left").find("li").each(function(){
			
			$(this).find("span").eq(1).text("");
			var $spand = $(this).find("span").eq(1);
			$("#searchForm").find("#"+$spand.attr("class")).val("");
		});
		
	});
	//确定条件
	$(".confirmcri").click(function(){
		
		
		$(".distance-eject .grade-left").find("li").each(function(){
			var $spand = $(this).find("span").eq(1);

				$("#searchForm").find("#"+$spand.attr("class")).val($spand.text());

			
		});
		$(".more").removeClass("orange");
        $('.distance-eject').removeClass('grade-w-roll').hide();
        $(".distance-eject").hide();
    	$(".more").find("img").attr("src",basePath+"images/ea/recruit/icon_03.png");
		startSearch();
		
		
	});
	
	
		// 点击月薪条件
	$("#Sort-Sort").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				
				var value = $(this).find("span").text();
				if (value== "全部") {
					value="月薪";
				}
				$(".Sort").find("span").text(value);
				
				
				$('.Sort-eject').removeClass('grade-w-roll');
				$(".Sort").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				
				$("#searchForm").find("#salary").val(value=="月薪"?"":value);
				startSearch();

			});
	
	// 点击智能排序
	$("#gradew").find("li").click(
			function() {
				$(this).parents("ul").find(".orange").removeClass("orange");
				$(this).find("span").addClass("orange");
				
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				
				var value = $(this).find("span").text();
				$(".teli").find("span").text(value);

				
				$('.grade-eject').removeClass('grade-w-roll');
				$(".teli").find("img").attr("src",
						basePath + "images/ea/recruit/icon_03.png");
				
				$("#searchForm").find("#sortbycri").val(value);
				startSearch();

			});
	
	// 搜索框获取焦点 效果
	$("#search").focus(function(){
		$(".asd").show();
	});
	//取消
	$(".cancel").click(function(){
		$("#search").val("");
		$(".asd").hide();
	});
	
	

	//搜索框输入搜索词
	$("#search").bind('input propertychange', function() {
		var search = $("#search").val();
		if(search==""){
			$(".search_down").css("display","none");
			$(".main").css("display","block");
			$(".sousuo").hide();
			$(".cancel").show();
			return false;
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
	
	
	
	//投简历
	$(document).on("click",".tou",function(){
		if($(this).text()=="已投"){
	    	 return false;
	     }
		
		if(token1==1){
			return false;
		}
		token1 = 1;

		var riIds = "";
		if($(this).attr("class").indexOf("dan")!=-1){
			riIds = $(this).parents("li").find(".riId").text();
		}else{
		 $(".selected").find(".riId").each(function(){
			riIds+=$(this).text()+",";
		 });
		 if(riIds!=""){
			riIds = riIds.substring(0,riIds.length-1);
		   }
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
					staffid = member.staffid;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					if(result=="noresume"){
						$(".tipcontent").text("投递失败，请完善您的简历");
						$(".tipconfirm").text("去完善简历");
					    $(".tan_mo").hide();
					    $(".tiptan").show();
					} else if(result=="success"){
						//成功后跳转
						$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
						$(".tan1").show();
						$(".tan_mo").hide();
						var array = riIds.split(",");
						for ( var j = 0; j < array.length; j++) {
							$("li#"+array[j]).find(".tou").text("已投");
							$("li#"+array[j]).find(".tou").removeClass("tou").addClass("yitou");
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
					token1 = 0;
					
					
				},error:function(data){
					alert("投递失败");
				}
			   
		       });
		
	});
	
	
	
	//点击职位展示职位详情
	$(document).on("click",".zhao_main_lis_center",function(){

			var riId = $(this).parents("li").find(".riId").text();
			var position = $(this).parents("li").find(".position").text();
			window.open(basePath+"ea/bidrecruit/ea_showPosdetail.jspa?riId="+riId+"&position="+position+"&back=2","_self");
		});
	
	


	
    //继续投递
	$(".again").click(function(){
		$(".tan_kuang").css("background","");
		$(".tan_kuang").hide();
	});
	
	
	//取消
	$(".tipcan").click(function(){
		$(".tiptan").hide();
		token1 = 0;
	});
	
	
	//确认
	$(".tipconfirm").click(function(){
       if($(this).text()=="去完善简历"){
			document.location.href = basePath+"ea/resumes/ea_savePersion.jspa?staffid="+staffid+"&type=";
		}
		token = 0;
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
	
	
	
	
	
 	//确定
	$(".confrim").click(function(){
		$(".tiptan").hide();
		token1 = 0;
	});
 	
 	
 	//回退
 	$(".arrar").click(function(){

 		window.open(basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa","_self");

 	});
 	
 
	
	$(".tan_kuang").click(function(){
		$(this).hide();
	});
	
	

	
	


	
	getHeight();

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
					 ajax : "ajax"
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
								array.push("<li class='last' id='"+obj[4]+"'>");
							} else {
								array.push("<li id='"+obj[4]+"'>");
							}
							array.push("<span style='display:none;' class='riId'>"+obj[4]+"</span>");
							array.push("<span style='display:none;' class='position'>"+obj[0]+"</span>");
							array.push("<div class='zhao_main_lis_left pull-left text-center img-responsive'>");
							if(obj[6]=="0"){
							array.push("<div class='img_wai'>");
							array.push("<img class='quan_xuan' style='width: 2rem;' src='"+basePath+"images/ea/recruit/ico_zhi_06.png'/>");
							array.push("<img class='dN quan_xuan' style='width: 2rem;' src='"+basePath+"images/ea/recruit/chan_07.png'/>");
							array.push("</div>");
							}
							array.push("</div>");
							array.push("<div class='zhao_main_lis_center pull-left'>");
							array.push("<h4>" + obj[0] + "</h4>");
							array.push("<p class='comname'>" + obj[1] + "</p>");
							array.push("<div>");
							array.push("<span class='yaoqiu'>");
							
							array.push("<img class='little_img' src='"+basePath+"images/ea/recruit/ico_13.png' alt='' />");
							array.push(obj[2]);
							array.push("</span>");
							array.push("<span class='yaoqiu'>");
							array.push("<img class='little_img' src='"
									+ basePath
									+ "images/ea/recruit/ico_15.png'/>");
							array.push(obj[3]);
							array.push("</span>");
							array.push("</div>");
							array.push("</div>");
							array.push("<div class='pull-left zhao_main_lis_right'>");
							array.push("<div class='text_wai'>");
							if(obj[6]=="0"){
								   array.push("<div class='tou dan'>投</div>");
								}else{
								  array.push("<div class='yitou dan'>已投</div>");
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
	$("#searchForm").attr("action",basePath+"ea/bidrecruit/ea_startSearch.jspa");
	document.searchForm.submit.click();
}


/**
 * 
 * 初始化搜索条件
 */
function initCri(){
	var  sortbycri = $("#searchForm").find("#sortbycri").val();
	var  salary = $("#searchForm").find("#salary").val();
	
	//排序
	if(sortbycri!=""){
		$(".teli").find("span").text(sortbycri);

	}
	
	//薪水
	if(salary!=""){
		$(".Sort").find("span").text(salary);

	}
	
     


}


function moreCri(){
	
	var  education = $("#searchForm").find("#education").val();
	
	var  publishDate = $("#searchForm").find("#publishDate").val();
	var  experience = $("#searchForm").find("#experience").val();
	var  posType = $("#searchForm").find("#posType").val();
	var  comPro = $("#searchForm").find("#comPro").val();
	var  comScale = $("#searchForm").find("#comScale").val();
	
	var $right = $(".distance-eject .grade-right").find("li");
	if(education!=""){
	$right.each(function() {
		var spand = $(this).find("span").eq(0).text();
		if (spand == education) {
			var $img = $(this).parents("ul").find(".gou").remove();
			$img.appendTo($(this));
			$(".distance-eject .rightbor").show();
			$(this).find(".rightbor").hide();
			return false;

		}

	});
	}
	if(publishDate!=""){
		$right.each(function() {
			var spand = $(this).find("span").eq(0).text();
			if (spand == publishDate) {
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				$(".distance-eject .rightbor").show();
				$(this).find(".rightbor").hide();
				return false;

			}

		});
		}
	
	if(experience!=""){
		$right.each(function() {
			var spand = $(this).find("span").eq(0).text();
			if (spand == experience) {
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				$(".distance-eject .rightbor").show();
				$(this).find(".rightbor").hide();
				return false;

			}

		});
		}
	
	
	if(posType!=""){
		$right.each(function() {
			var spand = $(this).find("span").eq(0).text();
			if (spand == posType) {
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				$(".distance-eject .rightbor").show();
				$(this).find(".rightbor").hide();
				return false;

			}

		});
		}
	
	
	if(comPro!=""){
		$right.each(function() {
			var spand = $(this).find("span").eq(0).text();
			if (spand == comPro) {
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				$(".distance-eject .rightbor").show();
				$(this).find(".rightbor").hide();
				return false;

			}

		});
		}
	
	
	if(comScale!=""){
		$right.each(function() {
			var spand = $(this).find("span").eq(0).text();
			if (spand == comScale) {
				var $img = $(this).parents("ul").find(".gou").remove();
				$img.appendTo($(this));
				$(".distance-eject .rightbor").show();
				$(this).find(".rightbor").hide();
				return false;

			}

		});
		}
	
}

