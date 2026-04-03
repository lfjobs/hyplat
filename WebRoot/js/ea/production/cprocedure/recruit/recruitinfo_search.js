$(function(){
	$("body").css({"overflow":"hidden"});
	$(".main").css({"height":$(window).height()-$("header").outerHeight()+"px","overflow":"auto"});
	$(".zhaopin_kuang").css("width",$(window).width()*0.8+"px");
	$(".jianli_tan_mo").css({"height":$(window).height()+"px","width":$(window).width()+"px"});
	
	//行业
	if(industys!=""){
		$("#hangye").text(industys.length>10?industys.substring(0,10)+"...":industys);
		$("#industry").val(industys);
	}
	
	 //职位
	if(pos!=""){
	   $("#poss").text(pos.length>15?pos.substring(0,15)+"...":pos);
	   $("#pos").val(pos);
	}
	 //选择城市后返回获取值
	if(citys!=""){
	  $("#workplace").text(citys.length>15?citys.substring(0,15)+"...":citys);
	  $("#citys").val(citys);
	}
	
	
	
	//搜索框
	$("#search").bind('input propertychange', function() {
		var search = $("#search").val();
		if(search==""){
			$(".search_down").css("display","none");
			$(".main").css("display","block");
			return false;
		}
	   var url = basePath+"ea/bidrecruit/sajax_ea_searchPosByKeyword.jspa";
		$.ajax({
			url : url,
			type : "get",
			asycn : false,
			dataType : "json",
			data : {
				"parameter" : search
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
	
	
	//点击查询结果搜索
	$(document).on("click",".resultlis,.sousuo,.big_search",function(){
		var keyword = "";
		var classid = $(this).attr("class");
		if(classid.indexOf("resultlis")!=-1){
			keyword = $(this).text();
		}else{
			keyword =$("#search").val(); 
			
		}
		$("#keyword").val($.trim(keyword));
		$("#searchForm").attr("action",basePath+"ea/bidrecruit/ea_startSearch.jspa?type="+((type=="找工作")?"gs":"gr"));
		document.searchForm.submit.click();
		
		
	});

	 //取消查询//回到首页
	  $(".cancel").click(function(){
		  document.location.href = basePath+"/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
		
	  });
	  
	  //完成
	  $(".wancheng").click(function(){
		  if($(".condition").is(":hidden")){
			  var industrys = "";
			  $(".yixuan").find(".xuan_lis").each(function(){
			     industrys+=$(this).find("span").eq(0).text()+",";  
			  });  
			  industrys = industrys.substring(0,industrys.length-1);
		  }
		  if(type=="找工作"){
			  document.location.href = basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa?industys="+industrys+"&pos="+selectpos+"&citys="+selectcitys;  
		  }else{
			  document.location.href = basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa?industys="+industrys+"&pos="+selectpos+"&citys="+selectcitys;  
		  }
		  
	  });
	


	
	//点击行业搜索框
	$(".header_c").click(function(){
		
		if($("#search").val()==""){
			
		}
		$("#search").focus();
	});
	
	
	$("#search").blur(function(){
		if($(this).val()==""){
			$(".header_c_tet").show();
		}else{
			$(".header_c_tet").hide();
		}
	});
	//投标弹框
	$(".tou").click(function(){
		$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
		$(".tan1").show();
	});
	$(".qiang").click(function(){
		$(".tan_kuang").css("background","rgba(0,0,0,0.4)");
		$(".tan2").show();
	});
	$(".again").click(function(){
		$(".tan_kuang").css("background","");
		$(".tan_kuang").hide();
	});
	
	//导航颜色切换
	$(".zhao_nav").find(".nav_zhao").click(function(){
		$(".zhao_nav").find(".nav_zhao").find("p").css("color","#333");
		$(this).find("p").css("color","#ff6600");
	});
	

	
	//tab切换
	var tabs = function(tab, con){
    tab.click(function(){
        var indx = tab.index(this);
        tab.removeClass('current');
        $(this).addClass('current');
        con.hide();
        con.eq(indx).show();
    	});
	};
 	tabs($(".zhao_nav li"), $('.zhao_main ul'));
 	
 	//行业选择
 	$(".kexuan .xuan_lis").click(function(){
 			var _this = $(this).text();
 		var _xuan = '<div class="xuan_lis"><span>'+_this+'</span><img class="cha" src="../images/cha_03.png"/></div>';
 	
 		
 		$(".yixuan").find(".xuan_hangye").append(_xuan);
 	});
 	//删除所选
 	$(document).on('click','.cha',function(){
 		
 		$(this).parent().remove();
 	});
 	//职位
 	$(".big_zhiwei").click(function(){
 		$(this).parent().find(".qiehuan").toggleClass("dN");
 		$(this).parent().next(".zhiwei_nop").toggle();
 	});
 	$(".quan_xuan_d").click(function(){
 		$(this).find(".quan_xuan").toggleClass("dN");
 	});
 	$(".img_wai").click(function(){
 		$(this).find(".quan_xuan").toggleClass("dN");
 	});
 	$(".xuanz").click(function(){
 		var lei_main = $(this).parent().next().find(".zhiwei_fenlei").text();
 		var tianjia = '<div class="xuan_lis"><span>'+lei_main+'</span><img class="cha" src="../images/cha_03.png"/></div>';
		$(".xuan_hangye").append(tianjia);
 	});
 	//简历按钮
 	$(".jianli_footer").find(".qiangren").click(function(){
 		$(".jianli_tan_mo").show();
 	});
 	$(".jianli_tan_mo").find(".no").click(function(){
 		$(".jianli_tan_mo").hide();
 	});
 	//回退
 	$(".arrar").click(function(){
 		history.go(-1);
 	});
 	
 
	
	$(".tan_kuang").click(function(){
		$(this).hide();
	});

	
	var type = $(".dropdown-toggle").find(".toggle_text").text();
	//选择行业
 	$(".hangye").click(function(){
				window.open(basePath+"ea/bidrecruit/ea_getIndustryList.jspa?type="+type+"&selectindus="+industys+"&selectpos="+pos+"&selectcitys="+citys,"_self");
	});
 	//选择职位
	$(".zhiwei").click(function(){
		window.open(basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type="+type+"&selectpos="+pos+"&selectcitys="+citys+"&selectindus="+industys,"_self");
	});
	
	//工作地点
	$(".city").click(function(){
		
		window.open(basePath+"page/ea/main/production/cprocedure/recruit/recruitCity.jsp?type="+type+"&selectcitys="+citys+"&selectpos="+pos+"&selectindus="+industys,"_self");
	});
	
	
	//切换找工作or找人才
	$(".header ul.dropdown-menu li").click(function(){
		$(".dropdown-toggle").find(".toggle_text").text("");
		$(".dropdown-toggle").find(".toggle_text").append($(this).find("a").text());
		if($(this).text()=="找工作"){
			window.open(basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa","_self");
		}else if($(this).text()=="找人才"){
			window.open(basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa","_self");
		}
	});
	
	//清空历史记录
	$(".clear_time").click(function(){
		var $obj = $(this);
		
		 var url = basePath+"ea/bidrecruit/sajax_ea_clearSearchRecord.jspa";
			$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				success : function(data) {
					var member = eval("("+data+")");
					var  login = member.login;
					if(login=="login"){
						document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
						return;
					}
					$obj.prev().remove();
					
				},error:function(data){
					alert("清空历史记录失败");
				}
			   
		       });
	})
	
});
