$(function(){
	$("body").attr("style","overflow:hidden;");
	$(".main").attr("style","overflow:hidden;");
	$("html").attr("style","overflow:hidden;");
	$(".mainlis").css({"height":$(window).height()-$("header").outerHeight()-$(".hangye_main").height()+"px","overflow":"auto"});
	

	  //初始化已选行业(防止頁面未加載完畢，寫在最後)；
	  initLoad(1);


	
	
	//回退
 	$(".arrar").click(function(){
 		 
 		var industrys = "";
 		
 		  if($(".condition").is(":hidden")){

			  $(".yixuan").find(".xuan_lis").each(function(){
			     industrys+=$(this).find("span").eq(0).text()+",";  
			  });  
			  industrys = industrys.substring(0,industrys.length-1);

		  }
 		  
		  
		  if(industrys==selectindus){
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
 	
 	
	   
	//搜索框
	$("#search").bind('input propertychange', function() {
		var search = $("#search").val();
		if(search==""){
			$(".search_down").css("display","none");
			$(".main").css("display","block");
			return false;
		}
	   var url = basePath+"ea/bidrecruit/sajax_ea_getSecondIndustry.jspa";
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
				var sublist = member.sublist;
				$(".search_down").html("");
		
				$(".wancheng").css("display","none");
				$(".quxiao").css("display","block");
				$(".search_down").css("display","block");
				$(".main").css("display","none");
				if(sublist==null){
					return false;
				}
				var html = "";
				for ( var i = 0; i < sublist.length; i++) {
					var codeValue = sublist[i].codeValue;
					var codeID = sublist[i].codeID;
					html+="<div class='resultlis' codeid='"+codeID+"'><img src='"+basePath+"images/ea/recruit/search999.png' alt=''> "+codeValue.replace(search,"<span class='keyword'>"+search+"</span>")+"</div>";	
				}
				$(".search_down").html(html);
				
			},error:function(data){
				alert("搜索结果失败");
			}
		   
	       });
		
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
	  var pos=position.replace(/\|/g,'%7C');
	  //完成
	  $(".wancheng").click(function(){
		  var industrys = "";
		  if($(".condition").is(":hidden")){

			  $(".yixuan").find(".xuan_lis").each(function(){
			     industrys+=$(this).find("span").eq(0).text()+",";  
			  });  
			  industrys = industrys.substring(0,industrys.length-1);
		  }
		  
		  var url =null;
		  var position=industrys;
		  if(type=="找工作"){
			  document.location.href = basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa?industys="+industrys+"&pos="+selectpos+"&citys="+selectcitys;  
		  }else if(type=="求职意向"){
			  url= basePath+"ea/resumes/ea_querySearch.jspa?staffid="
					  +staffid+"&industrys="
					  +industrys+"&work="
					  +work+"&region="
					  +region+"&salary="
					  +salary+"&status="+status
					  +"&position="+pos+"&resumeID="+resumeID+"&type="+type+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  document.location.href =url;
		  }else if(type=="工作经验"){

			  url = basePath+"page/ea/main/production/resume/addInternshipExperience.jsp?staffid="+staffid
					  +"&admissionTime="+admissionTime
					  +"&graduationTime="+graduationTime
					  +"&name="+name+"&position="+position
					  +"&postName="+postName.replace(/\|/g,'%7C')
					  +"&duties="+duties+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  document.location.href = url;
		  }else if(type=="修改工作经验"){
			  if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
				  url = basePath+"page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid
						  +"&admissionTime="+admissionTime+"&graduationTime="
						  +graduationTime+"&name="+name+"&position="+position
						  +"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties+"&recordKeys="+recordKeys
						  +"&recordKey="+recordKey
						  +"&recordID="+recordID
						  +"&resumeID="+resumeID
						  +"&typedata=修改"+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }else{
				  url = basePath+"page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid
						  +"&admissionTime="+admissionTime+"&graduationTime="
						  +graduationTime+"&name="+name+"&position="+position
						  +"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties
						  +"&recordIDa="+recordIDa
						  +"&resumeIDa="+resumeIDa
						  +"&recordKeya="+recordKeya
						  +"&typedata=修改&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }
			 
			  document.location.href = url;
		  }else if(type=="zb"){
			    window.open(basePath+"ea/purchasebids/ea_findGoodbidList.jspa?search=search&tradeID=&tradeName="+$.trim(industrys),"_self");
				
		  }
		  else{
			  document.location.href = basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa?industys="+industrys+"&pos="+selectpos+"&citys="+selectcitys;  
		  }
		  
	  });
	  
       // 上下箭头
		$(".xuan_head").click(function(){
			$("#xuanze").toggleClass("xianshi");

			$(".xia").toggleClass("shang");
		});
		
		//搜索结果点击选择行业
		$(document).on('click','.resultlis',function(){
			var nLen=$("#xuanze .xuan_lis").length;
			var indusID = $(this).attr("codeid");
			var _this = $(this).text();
			
			var _xuan = "<div class='xuan_lis "+indusID+"'><span class='value'>"+_this+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
			var total = $(".yixuan").find(".xuan_hangye").html();
			
			

			if (total.indexOf(indusID) == -1) {// 不存在
					if (nLen < 3) {

						$(".yixuan").find(".xuan_hangye").append(_xuan);
						$("#shuzhi").text(++nLen);
					    $(".mainlis").find("#"+indusID).css({
							"background" : "#FF6600",
							"color" : "#ffffff"
						});
						if (nLen != 0) {
							$(".condition").hide();
						}
					} else {
		                $(".alert_div").css("display","block");
		                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
		                return;
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
		
		//行业选择
		$(document).on('click','.kexuan .xuan_lis',function(){
			var nLen=$("#xuanze .xuan_lis").length;
			
			var _this = $(this).find("span").eq(0).text();
			var indusID = $(this).find("span").eq(1).text();
			var $total = $(".yixuan").find(".xuan_hangye");
			var _xuan = "<div class='xuan_lis "+indusID+"'><span class='value'>"+_this+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
			var bool = false;
			var $rightv = "";
			$total.find(".value").each(function(){
				$rightv = $(this);
				if($rightv.text()==_this){
					bool = true;
					return false;
				}
				
			});
			if(bool){//说明存在
				$(this).css({"background":"#f3f3f3","color":"#333"});	
				$rightv.parent().remove();
				$("#shuzhi").text(--nLen);
				if(nLen==0){
					$(".condition").show();
				}

			}else{
				if(nLen < 3){
					$(".yixuan").find(".xuan_hangye").append(_xuan);
					$("#shuzhi").text(++nLen);
					$(this).css({"background":"#FF6600","color":"#ffffff"});	
					if(nLen!=0){
					   $(".condition").hide();
					}
				}else{
	                $(".alert_div").css("display","block");
	                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
				}
				
			}
			
		});
		//移除已选行业
		$(document).on('click','.cha',function(){

			$(this).parent().remove();
			var asd=$("#xuanze .xuan_lis").length;
			var indusID = $.trim($(this).parent().attr("class").replace("xuan_lis",""));
			var value = $(this).parent().find(".value").text();
			if(indusID=="a"){
				$(".mainlis .xuan_lis").each(function(){
					var v = $(this).find("span").eq(0).text();
					if(v==value){
						$(this).css({
							"background":"#f3f3f3",
							"color":"#333"	
			          });
					}
				});
			}else{
		    	$(".mainlis").find("#"+indusID).css({"background":"#f3f3f3","color":"#333"});
			}
			$("#shuzhi").html(asd);
			if(asd==0){
				$(".condition").show();
			}
		});
		getHeight();

});

function getHeight() {
	t = setTimeout("getHeight()", 200);
	if ($(".last").length>0) {
		if ($(".last").offset().top-$(".last").height()<= $(window)
				.height()) {
			if (pagenumber < pagecount) {
				loaded();
			}
		}
	}
}
    

function loaded() {
	pagenumber += 1;
	var url = basePath + "ea/bidrecruit/sajax_ea_getIndustryList.jspa";
	$.ajax({
				url : url,
				type : "get",
				asycn : false,
				dataType : "json",
				data : {
					"pageForm.pageNumber" : pagenumber,
					 type : "ajax"
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var sublist = member.sublist;
					var html = "";
					var obj;

					if (pagenumber == 1) {
						$(".mainlis").html("");
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
								array.push("<div class='kexuan last'>");
							} else {
								array.push(" <div class='kexuan'>");
							}
							array.push("<div class='kexuan_main'>");
							array.push("<span class='xuan_head'>"+obj.codeValue+"</span>");
							array.push("<div class='xuan_hangye xhy'>");

							for ( var j = 0; j < sublist.length; j++) {
								
							   if(obj.codeID==sublist[j].codePID){
								   array.push("<div class='xuan_lis' id='"+sublist[j].codeID+"'>"); 
								   array.push("<span>"+sublist[j].codeValue+"</span><span style='display:none;'>"+sublist[j].codeID+"</span>");
								   array.push("</div>");
							   }
							}
							array.push("</div></div></div>");	
						}
					}
					$(".mainlis").append(array.join(""));
					$(".yixuan").find(".xuan_lis").each(function(){
						
						var indusID = $.trim($(this).attr("class").replace("xuan_lis",""));
						
						if(indusID=="a"){
							initLoad(2);
						}
						$(".mainlis").find("#"+indusID).css({
							"background" : "#FF6600",
							"color" : "#ffffff"
						});
					});
			
					
					

					getHeight();

				},
				error : function(data) {
					alert("获取失败");
				}
			});
}

function initLoad(page){
	
	if (selectindus != "") {
		var arrays = selectindus.split(",");
        var nLen = 0;
		$.each(arrays, function(n, value) {
			if(page==1){
			var _xuan = "<div class='xuan_lis a'><span class='value'>"+value+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
			$(".yixuan").find(".xuan_hangye").append(_xuan);
			$("#shuzhi").text(++nLen);
			}
			$(".condition").hide();
			$(".mainlis .xuan_lis").each(function(){
				var v = $(this).find("span").eq(0).text();
				if(v==value){
					$(this).css({
						"background" : "#FF6600",
						"color" : "#ffffff"
		          });
				}
			});

		});
			
	}
	
}
