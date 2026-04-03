$(document).ready(function() {
	//页面运行时就加载该方法
	loaded ();
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	
	
	//ajax模糊查询
	$("#change").keyup(function(){
		$("#joint").html("");
		loaded ();
		
	});
	//返回上一页
	$("#backtrack").click(function() {
//		window.history.back(-1);
		ppid = $("#ppID").val();
		user = $("#user").val();
		var surl1 = basePath + "ea/productslaunch/ea_productInquiry.jspa?productPackaging.ppID="+ppid+"&user="+user; 
		
		document.location.href = surl1;
	});
	
	//获取选中的促销产品
	$("#submit").click(function() {
		 ppid = $("#ppID").val();//ppID
		 user = $("#user").val();//用户
		 var myarr = new Array();
		 var myarr1 = new Array();
         $(".shop_img2").each(function () {
             myarr.push($(this).attr("name"));
         });
         salesPromotionID = myarr.join(",");//促销产品ppid
         if(salesPromotionID.length>0){
        	 var surl1 = basePath + "ea/productslaunch/ea_queryPromotionProduct.jspa?productPackaging.ppID="+ppid+"&ppId="+salesPromotionID+"&user="+user; 
    	 	 document.location.href = surl1;
         }else{
        	 prompt("请选择促销品");
         }
	});
		$(document).on("click",".shop_img",function(){
			if($(this).is(".sag")){
				$(this).find(".shop_img1").toggleClass("shop_img2");
				$(this).find(".shop_img3").toggleClass("shop_img4");
				$(this).toggleClass("sag");
				var shop1=$(".sag").length;
				$("#shop_span").text(shop1);
			}else{
				$(this).find(".shop_img1").toggleClass("shop_img2");
				$(this).find(".shop_img3").toggleClass("shop_img4");
				$(this).toggleClass("sag");
				var shop1=$(".sag").length;
				$("#shop_span").text(shop1);
			}
	    });
});
	function getHeight(){
		pageCount=$("#pageCount").val();
		pageNumber=$("#pageNumber").val();
		t=setTimeout("getHeight()", 200);
		if($(".last").offset().top+$(".last").height()-($(".res_top").height()+$(".search_shop").height())*0.01<$(window).height()){	
	
			if(Number(pageCount)>Number(pageNumber)){
				
				loaded();
			}	
		}
		
	}

	//ajax查询
	function loaded (){
		clearTimeout(t);
		pageNumber=$("#pageNumber").val();
		change = $("#change").val();
 		user = $("#user").val();
 		var salesPromotionId = $("#salesPromotionId").val();
		pageSize=10;
		if(pageNumber==null){
			pageNumber = 1;
		}else{
			pageNumber++;
		}
		
		var surl=basePath + "ea/productslaunch/sajax_ea_allGiftsProducts.jspa";
		$.ajax({
			url:encodeURI(surl),
		 	type:"post",
		 	data:{"pageForm.pageNumber":pageNumber,"pageForm.pageSize":pageSize,"productPackaging.goodsName":change,"user":user,"promotion.ptppId":salesPromotionId},
		  	dataType:"json",
		   	async:false, 
			   	success:function (data) {
					var jsonresult = eval("(" + data + ")");
					var results = jsonresult.result;					
					if(results==null){
						var joint="";
						joint+="<div class='showImage' >";
						joint+="<p style='text-align:center;'>";
						joint+="<img src='"+basePath+"images/ea/production/no-product.png'/>";
						joint+="</p></div>";
						$("#joint").append(joint);
					}else{
						var joint="";
							$(".clearfix").empty();
							$(".last").removeClass("last");
							$(results.list).each(function(i,dom){
								joint="";
								ppid = $("#ppID").val();
								if($(results.list).length-1==i){									
										joint+="<div class='shop_grd last' >";
										joint+="<img src='"+basePath+this[0]+"' onclick='particulars1(this)''>";
										joint+="<div class='shop_txt' >";
										joint+="<p onclick='particulars2(this)' style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis;letter-spacing: 1px;'>"+this[1]+"</p>";
										joint+="<p>销售价:￥<span>"+this[2]+"</span></p>";
										joint+="<p>成本价:￥<span>"+this[7]+"</span></p>";
										joint+="</div>";
										joint+="<div class='shop_img'>";
										joint+="<img class='shop_img1' src='"+basePath+"images/ea/production/gou2.png' name='"+this[4]+"'>";
										joint+="<img class='shop_img3' src='"+basePath+"images/ea/production/gou2-.png' name='"+this[3]+"'>";
										joint+="</div>";
										joint+="<div class='clearfix'>";
										joint+="<input id='pageCount' type='hidden' value='"+results.pageCount+"'/>";//总页数
										joint+="<input id='pageNumber' type='hidden' value='"+results.pageNumber+"'/>";//当前页
										joint+="<input id='pageSize' type='hidden' value='"+results.pageSize+"'/>";//每页显示条数
										joint+="<input id='recordCount' type='hidden' value='"+results.recordCount+"'/>";//总条数
										joint+="</div>";
										joint+="<input class='companyId' type='hidden' value='"+this[3]+"'/>";
										joint+="<input class='ppid' type='hidden' value='"+this[4]+"'/>";
										joint+="<input class='ccompanyId' type='hidden' value='"+this[5]+"'/>";
										joint+="<input class='goodsid' type='hidden' value='"+this[6]+"'/>";
										joint+="</div>";
								}else{									
										joint+="<div class='shop_grd' >";
										joint+="<img src='"+basePath+this[0]+"' onclick='particulars1(this)'>";
										joint+="<div class='shop_txt'>";
										joint+="<p onclick='particulars2(this)' style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis;letter-spacing: 1px;'>"+this[1]+"</p>";
										joint+="<p>销售价:￥<span>"+this[2]+"</span></p>";
										joint+="<p>成本价:￥<span>"+this[7]+"</span></p>";
										joint+="</div>";
										joint+="<div class='shop_img'>";
										joint+="<img class='shop_img1' src='"+basePath+"images/ea/production/gou2.png' name='"+this[4]+"'>";
										joint+="<img class='shop_img3' src='"+basePath+"images/ea/production/gou2-.png' name='"+this[3]+"'>";
										joint+="</div>";
										joint+="<div class='clearfix'>";
										joint+="</div>";
										joint+="<input class='companyId' type='hidden' value='"+this[3]+"'/>";
										joint+="<input class='ppid' type='hidden' value='"+this[4]+"'/>";
										joint+="<input class='ccompanyId' type='hidden' value='"+this[5]+"'/>";
										joint+="<input class='goodsid' type='hidden' value='"+this[6]+"'/>";
										joint+="</div>";									
								    
								    
								    
								}
								$("#joint").append(joint);
							
							}); 
						       pageCount=$("#pageCount").val();
						       if(pageNumber==Number(pageCount)){
						    	   prompt("已加载所有");
						       }else{
						    	   getHeight();
						       }
						  }
				
			   			}
				});
	
	};
	function prompt(obj){
		if($("#prompt").css("display")!="none")
			return;
		$("#prompt").find("span").text(obj);
		$("#prompt").fadeIn(500);
		setTimeout(function(){
			$("#prompt").fadeOut(500);
			$("#prompt").find("span").text("");
		}, 2000);
	}
	//跳转商品详情页面
	function particulars1(obj){
	    	var parms=new Array();
	    	parms.push("ppid="+$(obj).parent().find(".ppid").val());
	    	parms.push("&goodsid="+$(obj).parent().find(".goodsid").val());
	    	parms.push("&companyId="+$(obj).parent().find(".companyId").val());
	    	parms.push("&ccompanyId="+$(obj).parent().find(".ccompanyId").val());
	    	judge="selectSalesPromotion";
	    	user=$("#user").val();
	    	promotionID=$("#ppID").val();
//	    	alert(parms+","+judge+","+user+","+promotionID)
	    	var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("")+"&judge="+encodeURI(encodeURI(judge))+"&user="+user+"&promotionID="+promotionID;
	    	document.location.href = url;
	    };
	  //跳转商品详情页面
	    function particulars2(obj){
	    	var parms=new Array();
	    	parms.push("ppid="+$(obj).parent().parent().find(".ppid").val());
	    	parms.push("&goodsid="+$(obj).parent().parent().find(".goodsid").val());
	    	parms.push("&companyId="+$(obj).parent().parent().find(".companyId").val());
	    	parms.push("&ccompanyId="+$(obj).parent().parent().find(".ccompanyId").val());
	    	judge="selectSalesPromotion";
	    	user=$("#user").val();
	    	promotionID=$("#ppID").val();
//	    	alert(parms+","+judge+","+user+","+promotionID)
	    	var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("")+"&judge="+encodeURI(encodeURI(judge))+"&user="+user+"&promotionID="+promotionID;
	    	document.location.href = url;
	    };
