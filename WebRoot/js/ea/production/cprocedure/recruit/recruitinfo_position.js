$(function(){
	$("body").attr("style","overflow:hidden;");
	$(".main").attr("style","overflow:hidden;");
	$("html").attr("style","overflow:hidden;");
	$(".mainlis").css({"height":$(window).height()-$("header").outerHeight()-$(".yixuan").height()+"px","overflow":"auto"});
	
	initLoad();
	
	
	
	  // 上下箭头
   $("#headsx").click(function(){
		$("#xuanze").toggleClass("xianshi");
		$(".xia").toggleClass("shang");
	});
	
	
	//职位
 	$(".big_zhiwei").click(function(event){
 		var codePID = $(this).parents("li").attr("id");
 		if($(this).parents("li").find(".shangi").attr("class").indexOf(".dN")==-1){
 			if($("."+codePID).length==0){
 				var url = basePath+"ea/bidrecruit/sajax_ea_getPositionIndex.jspa";
 	 	 		$.ajax({
 	 				url : url,
 	 				type : "get",
 	 				asycn : false,
 	 				dataType : "json",
 	 				data : {
 	 					type :"ajax",
 	 					codePID:codePID
 	 				},
 	 				success : function(data) {
 	 					var member = eval("(" + data + ")");
 	 					var positionlist = member.positionlist;
 	 					var array = new Array();
 	 					var obj;
 	 					for ( var j = positionlist.length-1; j >= 0; j--) {
 	 						obj = positionlist[j];
 	 						array.push('<li class="list-group-item zhiwei_nop dN '+obj.codePID+'" style="display: list-item;"  id="'+obj.codeID+'">');
 	 						array.push('<span class="quan_xuan_d second">');
 	 						array.push('<img class="quan_xuan xuanz" src="'+basePath+'images/ea/recruit/ico_zhi_06.png" alt="" />');
 	 						array.push('<img class="quan_xuan noxuanz dN" src="'+basePath+'images/ea/recruit/chan_07.png" />');
 	 						array.push(' </span><span><span class="zhiwei_fenlei" style="vertical-align: middle;">'+obj.codeValue+'</span></span></li>');		
 	 					}
                        $("#"+codePID).after(array.join(""));
                        
                		$(".yixuan").find(".xuan_lis").each(function(){
    						//注意
    						var selectcode = $(this).attr("codeid");
    						var value = $(this).find(".value").text();
    						if(selectcode!=undefined){
    						$("#"+selectcode).find(".xuanz").addClass("dN");
    						$("#"+selectcode).find(".noxuanz").removeClass("dN");	
    						}else{
    							$(".mainlis .zhiwei_nop").each(
    									function() {
    										var v = $(this).find(".zhiwei_fenlei").text();
    										if (v == value) {
    											$(this).find(".second").find(".xuanz")
    													.addClass("dN");
    											$(this).find(".second").find(".noxuanz")
    													.removeClass("dN");

    										}

    							});
    						}
    						
    					});
         
                		
                		
                        
 	 				},error:function(data){
 	 					alert("获取三级职位失败");
 	 				}
 	 			   
 	 		       });
 			}
 			
 		}
 		
 		$(this).parents("li").find(".qiehuan").toggleClass("dN");
 		$("."+codePID).toggle();
 	});
 
 	

	
	
	

	//回退
 	$(".arrar").click(function(){
 		 
 		var poss = "";
 		
 		  if($(".condition").is(":hidden")){

			  $(".yixuan").find(".xuan_lis").each(function(){
				  poss+=$(this).find("span").eq(0).text()+",";  
			  });  
			  poss = poss.substring(0,poss.length-1);

		  }
 		  
		  
		  if(poss==selectpos){
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
		
				$(".wancheng").css("display","none");
				$(".quxiao").css("display","block");
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
					html+="<div class='resultlis' codeid='"+codeID+"' codepid='"+codePID+"'><img src='"+basePath+"images/ea/recruit/search999.png' alt=''> "+codeValue.replace(search,"<span class='keyword'>"+search+"</span>")+"</div>";	
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
	  
	  //完成
	  $(".wancheng").click(function(){
		  var pos = "";
		  var codeid = "";
		  if($(".condition").is(":hidden")){
			  $(".yixuan").find(".xuan_lis").each(function(){
                  codeid +=$(this).attr("codeId");
				  pos+=$(this).find("span").eq(0).text()+",";
			  });  
			  pos = pos.substring(0,pos.length-1).replace(/\|/g,'%7C');
			
		  }
		  var professionalName=pos.replace(/\|/g,'%7C');
		  var url=null;
		  if(type=="找工作"){
			  document.location.href = basePath+"ea/bidrecruit/ea_getGssearchIndex.jspa?pos="+pos+"&industys="+selectindus+"&citys="+selectcitys;   
		  }else if(type=="求职意向"){
			  
			  url= basePath+"ea/resumes/ea_querySearch.jspa?staffid="
				  +staffid+"&industrys="+industrys
				  +"&work="+work+"&region="+region
				  +"&salary="+salary+"&status="
				  +status+"&position="+pos+"&resumeID="+resumeID+"&type="+type+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				  document.location.href =url;
		  }else if(type=="教育背景"){
			 
			  url = basePath+"page/ea/main/production/resume/addEducationalBackg.jsp?staffid="+staffid
					  	+"&professionalName="+professionalName
					    +"&admissionTime="+admissionTime
					    +"&graduationTime="+graduationTime
					    +"&name="+name+"&education="+education+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;

		  }else if(type=="修改教育背景"){
			  if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
				 if(staffids==""||staffids==null||staffids==undefined){
  					staffids=staffid;
  				}
				  url = basePath+"page/ea/main/production/resume/updateEducationalBackg.jsp?staffids="+staffids
				  	+"&professionalName="+professionalName
				    +"&admissionTime="+admissionTime
				    +"&graduationTime="+graduationTime
				    +"&name="+name+"&education="
				    +education+"&typedata=修改"
				    +"&keys="+keys
				    +"&resumeIDs="+resumeIDs
				    +"&educationIDs="+educationIDs+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }else{
				  if(staffid==""||staffid==null||staffid==undefined){
	  					staffid=staffids;
	  				}
				  url = basePath+"page/ea/main/production/resume/updateEducationalBackg.jsp?staffid="+staffid
				  	+"&professionalName="+professionalName
				    +"&admissionTime="+admissionTime
				    +"&graduationTime="+graduationTime
				    +"&name="+name+"&education="
				    +education+"&typedata=修改"
				    +"&keya="+keya
				    +"&resumeIDa="+resumeIDa
				    +"&educationIDa="+educationIDa+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }
			 
				document.location.href = url;

		  }else if(type=="工作经验"){
			  var postName=pos;
			  url = basePath+"page/ea/main/production/resume/addInternshipExperience.jsp?staffid="+staffid
					  +"&admissionTime="+admissionTime+"&graduationTime="
					  +graduationTime+"&name="+name+"&position="+position
					  +"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;

              document.location.href = url;
		  }else if(type=="修改工作经验"){
			  var postName=pos;
			  var url=null;
			  if((recordKeya==""||recordKeya==undefined)&&(recordIDa==""||recordIDa==undefined)&&(resumeIDa==""||resumeIDa==undefined)){
				  url = basePath+"page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid
				  +"&admissionTime="+admissionTime+"&graduationTime="
				  +graduationTime+"&name="+name+"&position="+position
				  +"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties
				  +"&recordKeys="+recordKeys
				  +"&recordIDs="+recordIDs
				  +"&resumeIDs="+resumeIDs+"&typedata=修改&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }else{
				  url = basePath+"page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid
				  +"&admissionTime="+admissionTime+"&graduationTime="
				  +graduationTime+"&name="+name+"&position="+position
				  +"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties
				  +"&recordIDa="+recordIDa
				  +"&resumeIDa="+resumeIDa
				  +"&recordKeya="+recordKeya+"&typedata=修改&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			  }
			  
			  document.location.href = url;
		  }else if(type=="发布职位"){
		  	if(pos==""||pos==null){
		  	         return false;
			}

          document.location.href = basePath+"ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&pos="+pos+"&codeid="+codeid+"&recruitInfo.riId="+riId+"&back="+back;

          }else{
              document.location.href = basePath+"ea/bidrecruit/ea_getGrsearchIndex.jspa?pos="+pos.replace(/\|/g,'%7C')+"&industys="+selectindus+"&citys="+selectcitys+"&back="+back;

          }
		  
	  });
	  

		
		//搜索结果点击选择职位
		$(document).on('click','.resultlis',function(){
			var nLen=$("#xuanze .xuan_lis").length;
			var posID = $(this).attr("codeid");
			var posPID = $(this).attr("codepid");
			var _this = $(this).text();
			
			var _xuan = "<div class='"+posPID+"p xuan_lis "+posID+"d' codeid='"+posID+"'><span>"+_this+"</span><img class='cha' src='"+basePath+"images/ea/recruit/cha_03.png'/></div>";
			var total = $(".yixuan").find(".xuan_hangye").html();
			
			

			if (total.indexOf(posID+"d") == -1) {// 不存在

				   
				 
				        //把父对象删除
						var $p = $(".yixuan").find(".xuan_hangye").find("."+posPID+"d");
						
						//删除所选，修改选中状态
						var sub = $(".yixuan").find(".xuan_hangye").find("."+posID+"p");
						
						
						if($p.length>0){//三级
							nLen--;
							$p.remove();//把父对象删除
							$("#"+posPID).find(".xuanz").removeClass("dN");
							$("#"+posPID).find(".noxuanz").addClass("dN");
							nLen++;
							$(".yixuan").find(".xuan_hangye").append(_xuan);
							$("#"+posID).find(".xuanz").addClass("dN");
							$("#"+posID).find(".noxuanz").removeClass("dN");
						}else if(sub.length>0){
						
							 $(".yixuan").find(".xuan_hangye").find("."+posID+"p").remove();//把子对象删除
							 $("."+posID).find(".xuanz").removeClass("dN");
							 $("."+posID).find(".noxuanz").addClass("dN");
							 nLen = nLen-sub.length;
							 
							 nLen++;
							 $(".yixuan").find(".xuan_hangye").append(_xuan);
							 $("#"+posID).find(".xuanz").addClass("dN");
							 $("#"+posID).find(".noxuanz").removeClass("dN");	
						
						
						}else{
							if(nLen == 3){
								 $(".alert_div").css("display","block");
					                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
							}else{
								nLen++;
								$(".yixuan").find(".xuan_hangye").append(_xuan);
								$("#"+posID).find(".xuanz").addClass("dN");
								$("#"+posID).find(".noxuanz").removeClass("dN");	
								
							}
						}

						
						
				}
			     if (nLen != 0) {
				   $(".condition").hide();
			     }
			   $("#shuzhi").text(nLen);	
			   $(".wancheng").css("display","block");
			   $(".quxiao").css("display","none");
			   $(".search_down").css("display","none");
			   $(".arrar").css("display","block");
			   $(".dropdown").css("display","block");
			   $(".header_c").css("width","78%");
			   $(".main").css("display","block");
			   $("#search").val("");

		});
		
		
		//点击选中，取消 
		$(document).on('click','.quan_xuan_d',function(event){
			var nLen=$(".xuan_hangye .xuan_lis").length;
	 		var lei_main = $(this).parents("li").find(".zhiwei_fenlei").text();
	 		var codeID = $(this).parents("li").attr("id");
	 		var codePID = $.trim($(this).parents("li").attr("class").replace("list-group-item zhiwei_nop dN","").replace("list-group-item lisp",""));
	 		var total = $(".yixuan").find(".xuan_hangye").html();

	 		var tianjia = '<div class="'+codePID+'p xuan_lis '+codeID+'d" codeid="'+codeID+'"><span value="value">'+lei_main+'</span><img class="cha" src="'+basePath+'images/ea/recruit/cha_03.png"/></div>';
	 		
	 		if(total.indexOf(codeID+"d")!=-1){	
				$(".yixuan").find(".xuan_hangye").find("."+codeID+"d").remove();
				$(this).find(".xuanz").removeClass("dN");
				$(this).find(".noxuanz").addClass("dN");
				$("#shuzhi").text(--nLen);
				if(nLen==0){
					$(".condition").show();
				}

			}else{
					var classli = $(this).parents("li").attr("class");
					
					if(classli.indexOf("zhiwei_nop")!=-1){//说明是三级
						var codeIDs = classli.replace("list-group-item zhiwei_nop dN ","");
						var $yxuan = $(".yixuan").find(".xuan_hangye").find("."+codeIDs+"d");
						if($yxuan.length>0){
							nLen--;
							$(".yixuan").find(".xuan_hangye").find("."+codeIDs+"d").remove();
							$("#"+codeIDs).find(".one").find(".xuanz").removeClass("dN");
							$("#"+codeIDs).find(".one").find(".noxuanz").addClass("dN");
							$(".yixuan").find(".xuan_hangye").append(tianjia);
							$(this).find(".xuanz").addClass("dN");
							$(this).find(".noxuanz").removeClass("dN");
						}else{
							if(nLen == 3){
								 $(".alert_div").css("display","block");
					             setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
								nLen--;
							}else{
								$(".yixuan").find(".xuan_hangye").append(tianjia);
								$(this).find(".xuanz").addClass("dN");
								$(this).find(".noxuanz").removeClass("dN");
							}
						}
						
					}else{//说明是二级
						var ersub = $(".yixuan").find(".xuan_hangye").find("."+codeID+"p");
						if(ersub.length>0){
							ersub.remove();
							$("."+codeID).find(".xuanz").removeClass("dN");
							$("."+codeID).find(".noxuanz").addClass("dN");
							nLen=nLen-ersub.length;
							$(".yixuan").find(".xuan_hangye").append(tianjia);
							$(this).find(".xuanz").addClass("dN");
							$(this).find(".noxuanz").removeClass("dN");
						}else{
							if(nLen == 3){
								 $(".alert_div").css("display","block");
					                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
								nLen--;
							}else{
								$(".yixuan").find(".xuan_hangye").append(tianjia);
								$(this).find(".xuanz").addClass("dN");
								$(this).find(".noxuanz").removeClass("dN");
							}
						}
					}
				
					$("#shuzhi").text(++nLen);
					
					if(nLen!=0){
					   $(".condition").hide();
					}

				
			}
	 		
	 		
	 	
	 	});
		
	
		//移除已选职位
		$(document).on('click','.cha',function(){

			var codeID = $(this).parent().attr("codeid");
			if(codeID!=undefined){
			  $("#"+codeID).find(".xuanz").removeClass("dN");
			  $("#"+codeID).find(".noxuanz").addClass("dN");
			}else{
				var value = $(this).parent().find(".value").text();
				$(".xuan_zhiwei li").each(function(){
					var v = $(this).find(".zhiwei_fenlei").text();
					if(v==value){
						  $(this).find(".xuanz").removeClass("dN");
						  $(this).find(".noxuanz").addClass("dN");
						return false;
					}
					
				});
			}
			$(this).parent().remove();
			var asd=$("#xuanze .xuan_lis").length;
			$("#shuzhi").html(asd);
		
			if(asd==0){
				$(".condition").show();
			}
		});
		

});
//初始化
function initLoad() {
	if (selectpos != "") {
		var arrays = selectpos.split(",");
		var $total = $(".yixuan").find(".xuan_hangye");
		var nLen = 0;
		$.each(arrays, function(n, value) {
				nLen++;
				var tianjia = '<div class="xuan_lis a"><span class="value">'
						+ value + '</span><img class="cha" src="' + basePath
						+ 'images/ea/recruit/cha_03.png"/></div>';
				$(".yixuan").find(".xuan_hangye").append(tianjia);
				$(".condition").hide();

				$(".mainlis .lisp").each(
						function() {
							var v = $(this).find(".zhiwei_fenlei").text();
							if (v == value) {
								$(this).find(".one").find(".xuanz").addClass(
										"dN");
								$(this).find(".one").find(".noxuanz")
										.removeClass("dN");
								return false;
							}

						});
				$("#shuzhi").text(nLen);
			
		});

	}

}

