$(function(){
	$("body").css({"overflow":"hidden"});
	$(".main").css({
		"position" : "absolute",
		"height" : $(window).height() - $(".fixed").outerHeight() + "px",
		"overflow" : "auto"
	});
	$(".sort_box,.ding_city,.hot_city").css({"background":"#ffffff"});
	

	//回退
 	$(".arrar").click(function(){
 		 
 		var citys = "";
 		
 		  if($(".condition").is(":hidden")){

			  $(".yixuan").find(".xuan_lis").each(function(){
				  citys+=$.trim($(this).find("span").eq(0).text())+",";  
			  });  
			  citys = citys.substring(0,citys.length-1);

		  }
		  
		  if(citys==selectcitys){
			  history.go(-1); 
		  }else{
			  $(".alert_div2").css("display","block"); 
		  }
		
 	});
 	
 	
 	
 	
 	//离开确定
 	
 	$("#queding").click(function(){
 		 $(".alert_div2").css("display","none"); 
 		  history.go(-1); 
 		
 	});
 	
 	
 	$("#quxiao").click(function(){
		 $(".alert_div2").css("display","none"); 
		
	});
 	
 	$(".ding_main_lis").click(function(){
 		var city = $("#citys").text();
 		$(".kexuan .num_name").each(function(){
 		if($(this).text().indexOf(city)!=-1){
 			$(this).parent().trigger("click");
 			return false;
 		}
 		});
 		
 	});
	   

	//搜索框
    $("#search").bind('input propertychange', function() {
		var search =$("#search").val();
		$(".main1").show();
		if(search==""){
			$(".main").show();
			$(".initials").show();
			$(".main1").html("");
			
			return;
		}
		var result = "";
		$(".kexuan .num_name").each(function(){
			var cityid = $(this).attr("id");
			if($(this).text().indexOf(search)!=-1){
				result+="<div class='resultlis' cityid='"+cityid+"'><img src='"+basePath+"images/ea/recruit/search999.png' alt=''> "+$(this).text().replace(search,"<span class='keyword'>"+search+"</span>")+"</div>";	
				
			}
			
		});
		if(result==""){
			result="<div style='margin-top:50%;text-align:center;'>'"+search+"'相关城市还没有开通'</div>";
		}
		$(".main").hide();
		$(".initials").hide();
		$(".main1").html(result);
		
	});
	
	 //搜索框获取焦点
	$("#search").focus(function(){
			$(".wancheng").css("display","none");
			$(".quxiao").css("display","block");
			$(".arrar").css("display","none");
			$(".header_c").css("width","88%");
		});
	 
	 //取消查询
	  $(".quxiao").click(function(){
		   $(".wancheng").css("display","block");
		   $(".quxiao").css("display","none");
		   $(".search_down").css("display","none");
		   $(".arrar").css("display","block");
		   $(".dropdown").css("display","block");
		   $(".header_c").css("width","78%");
		   $(".main").css("display","block");
		   $("#search").val("");
		
	  });
	  
	  //完成
	  $(".wancheng").click(function(){
		  var citys = "";
		  if($(".condition").is(":hidden")){
			 
			  $(".yixuan").find(".xuan_lis").each(function(){
				  citys+=$(this).find("span").eq(0).text()+",";  
			  });  
			  citys = citys.substring(0,citys.length-1);
		  }
		  if(type=="找工作"){
			  document.location.href = basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa?citys="+citys+"&pos="+selectpos+"&industys="+selectindus;  
		  }else if(type=="求职意向"){
			  var url = basePath+"ea/resumes/ea_querySearch.jspa?staffid="+staffid
			  +"&work="+work+"&region="+citys+"&industrys="+industrys
			  +"&salary="+salary+"&status="+status+"&position="+position.replace(/\|/g,'%7C')+"&resumeID="+resumeID+"&type="+type+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  document.location.href = url;
		  }else{
			  document.location.href = basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa?citys="+citys+"&pos="+selectpos+"&industys="+selectindus+"&sccId="+sccId+"&jitype="+jitype;
		  }

		  
	  });
	  
       // 上下箭头
		$(".xuan_head").click(function(){
			$("#xuanze").toggleClass("xianshi");

			$(".xia").toggleClass("shang");
		});
		
		//搜索结果点击选择城市
		$(document).on('click','.resultlis',function(){
			$(".main1").hide();
			var nLen=$("#xuanze .xuan_lis").length;
			var cityid = $(this).attr("cityid");
			var _this = $(this).text();
			
			var _xuan = "<div class='xuan_lis "+cityid+"'><span>"+_this+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
			var total = $(".yixuan").find(".xuan_hangye").html();
			
			

			if (total.indexOf(cityid) == -1) {// 不存在
					if (nLen < 3) {

						$(".yixuan").find(".xuan_hangye").append(_xuan);
						$("#shuzhi").text(++nLen);
					    $(".mainlis").find("#"+cityid).css({
							"background" : "#FF6600",
							"color" : "#ffffff"
						});
					    $(".kexuan").find("#"+cityid).parent().css({"background":"#FF6600","color":"#ffffff"});	
						$(".hot_city_main").find("#"+cityid).parent().css({"background":"#FF6600","color":"#ffffff"});
						if (nLen != 0) {
							$(".condition").hide();
						}
					} else {
						 $(".alert_div").css("display","block");
			             setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
					}
				}
			   
			   $(".wancheng").css("display","block");
			   $(".quxiao").css("display","none");
			   $(".search_down").css("display","none");
			   $(".arrar").css("display","block");
			   $(".dropdown").css("display","block");
			   $(".header_c").css("width","78%");
			   $(".main").css("display","block");
			   $("#search").val("");

		});
		
		//城市选择
		$(document).on('click','.kexuan .xuan_lis,.hot_city_main .hot_city_lis',function(){

			var nLen=$("#xuanze .xuan_lis").length;
			var _this = $(this).text();
			var cityid = $(this).find(".num_name").attr("id");
			var total = $(".yixuan").find(".xuan_hangye").html();
			var _xuan = "<div class='xuan_lis "+cityid+"'><span data-span='"+cityid+"'>"+_this+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
            var ishave = 0;
            $(".yixuan").find(".xuan_lis span").each(function(){
               var dataspan = $(this).attr("data-span");
               if(dataspan==cityid){
                   ishave=1;
			   }
            });
            if(ishave==1){
				$(".kexuan").find("#"+cityid).parent().css({"background":"#f3f3f3","color":"#333"});	
				$(".hot_city_main").find("#"+cityid).parent().css({"background":"#f3f3f3","color":"#333"});	
				$(".yixuan").find(".xuan_hangye").find("."+cityid).remove();
				$("#shuzhi").text(--nLen);
				if(nLen==0){
					$(".condition").show();
				}

			}else{
				if(nLen < 3){
					$(".yixuan").find(".xuan_hangye").append(_xuan);
					$("#shuzhi").text(++nLen);
					$(".kexuan").find("#"+cityid).parent().css({"background":"#FF6600","color":"#ffffff"});	
					$(".hot_city_main").find("#"+cityid).parent().css({"background":"#FF6600","color":"#ffffff"});
					if(nLen!=0){
					   $(".condition").hide();
					}
				}else{
					 $(".alert_div").css("display","block");
		             setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
				}
				
			}
			
		});
	//移除已选城市
		$(document).on('click','.cha',function(){
			
			var cityid = $.trim($(this).parent().attr("class").replace("xuan_lis",""));
			$(".kexuan").find("#"+cityid).parent().css({"background":"#f3f3f3","color":"#333"});
			$(".hot_city_main").find("#"+cityid).parent().css({"background":"#f3f3f3","color":"#333"});
			$(this).parent().remove();
			var asd=$("#xuanze .xuan_lis").length;
			$("#shuzhi").html(asd);
			if(asd==0){
				$(".condition").show();
			}
		});
		
		
		
		
		
		
		

});

