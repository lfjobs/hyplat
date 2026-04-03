$(document).ready(function() {
	var price=0;
	loaded();
	particulars();
	$(".detail_page .det_mil .right .color .xuan li .attrivalue").each(function(){
		if($(this).val()!=""){
			$(this).parent("li").addClass("active").siblings().removeClass("active");
		}
	});
	$(document).on("click",".swiper-container-hot li",function(){
        ppid = $(this).find(".ppID").val();
        var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
        window.open(url);
	});
    $("#focus-banner").css("height","350px");
    $("#focus-banner-list li a").css("height","350px");
    /*规格选择*/
    $(".detail_page .det_mil .right .color .xuan li").click(function(){
    	var attriname=$(this).parent("ul").prev("h4").text().trim();
        $(this).addClass("active").siblings().removeClass("active");
        $(".detail_page .det_mil .right .color .xuan li").each(function(){
        	if($(this).parent("ul").prev("h4").text().trim()==attriname){
        	$(this).find(".attrivalue").val("");
        	}
        });
        $(this).parent("ul").prev("h4").find(".attriname").val(attriname);
        $(this).find(".attrivalue").val($(this).text().trim());
    });
    /*赠品*/
    $(".detail_page .det_mil .right .gift .gift_con ul .list").click(function(){
    	if($(this).hasClass("active")){
        	$(this).find(".text .ptppids").val("");
        	$(this).find(".text .ptStandards").val("");
    		$(this).removeClass("active");
    		$(this).find(".ComboBox .gift_color ul li").each(function(){
    			$(this).parents(".gift_color").find("h5 .giftAttriname").val("");
    			$(this).removeClass("active");
    		});
    	}
    }); 
    /*赠品规格*/
    $(".detail_page .det_mil .right .gift .gift_con ul .list").mouseover(function(){
        var btm = $(this).find(".ComboBox").height()+2;
        $(this).find(".ComboBox").css("bottom",-btm+"px");
        $(this).find(".ComboBox").show();
    });
    $(".detail_page .det_mil .right .gift .gift_con ul .list").mouseout(function(){
        $(this).find(".ComboBox").hide();
        $(this).find(".ComboBox").css("border-color","#ddd");
    });
    /*赠品规格选择*/
    $(".detail_page .det_mil .right .gift .gift_con li .ComboBox .gift_color ul li").click(function(){
        var giftAttriname=$(this).parent("ul").prev("h5").text().trim();
        var giftStandardArray=[];
        var flag=true;
    	$(this).addClass("active").siblings().removeClass("active");
    	$(".detail_page .det_mil .right .gift .gift_con li .ComboBox .gift_color ul li").each(function(){
    		if($(this).parent("ul").prev("h5").text().trim()==giftAttriname){
    			$(this).find(".giftAttrivalue").val("");
    		}
    	}); 
    	$(this).parent("ul").prev("h5").find(".giftAttriname").val(giftAttriname);
    	$(this).find(".giftAttrivalue").val($(this).text().trim());
    	$(this).parents(".ComboBox").find("h5 .giftAttriname").each(function(){
    		if($(this).val()==""){
    			flag=false;
    		}
    	});
    	if(flag){
    		$(this).parents(".ComboBox").find("h5 .giftAttriname").each(function(){
    			if($(this).val()!= "数量"){
    				giftStandardArray.push($(this).val()+":");
    			}
    			$(this).parents(".gift_color").find("ul li .giftAttrivalue").each(function(){
    				if($(this).val()!= "" && $(this).val()!= "1"){
    					giftStandardArray.push($(this).val()+",")
    				}
    			});
    		});
    		var giftStandard = giftStandardArray.join("");
    		var ptppidHide=$(this).parents(".list").find(".text .ptppidHide").val();
    		$(this).parents(".list").find(".ptStandards").val(giftStandard.substring(0,giftStandard.length-1));
    		if($(this).parents(".list").find(".ptStandards").val()==""){
    			$(this).parents(".list").find(".ptStandards").val("默认规格");
    		}
    		$(this).parents(".list").find(".text .ptppids").val(ptppidHide);
    		$(this).parents(".list").addClass("active");
    	}else{
    		$(this).parents(".list").removeClass("active");
    	}
    	event.stopPropagation();
    });
    /*商品详情*/
    $(".shop-details .top li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
        /*详情*/
        $(".shop-details .top #det").click(function(){
            $(".shop-details .shop-con .det").show().siblings().show();
        });
        /*售后*/
        $(".shop-details .top #sale").click(function(){
            $(".shop-details .shop-con .sale").show().siblings().hide();
            $(".shop-details .shop-con .eval").show();
            $(".pages").show();
            $(".page_my").show();
        });
        /*评价*/
        $(".shop-details .top #eval").click(function(){
            $(".shop-details .shop-con .eval").show().siblings().hide();
            $(".pages").show();
            $(".page_my").show();
        });
    //绑定滚动条事件
    $(window).bind("scroll", function () {
        var sTop = $(window).scrollTop();
        var sTop = parseInt(sTop);

        if (sTop >= 1100) {
            $(".return").slideDown();
            $(".return").show();
        }
        else {
            $(".return").hide();
        }
    });
     /*分页*/
     $(".page_my li").click(function(){
         $(this).addClass("active").siblings().removeClass("active");
     });
     /*数量加减*/
     $("#cut").click(function(){
         price=$("#num").val();
         if($("#num").val()==1){
             return
         }
         else{
             price--;
         }
         $("#num").val(price);
     });
     $("#add").click(function(){
         price=$("#num").val();
         price++;
         $("#num").val(price);
     });
});
// 热卖推荐
function loaded() {
	var url = basePath + "ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
	$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"pageForm.pageNumber" : 1,
				"search":"smart",
				"proName":goodsname
			},
			success : function(data) {
				var search = eval("(" + data + ")");
				var pageForm = search.pageForm;
				var recommend = [];
				if(pageForm!=null){
                    $(pageForm.list).each(function(i, dom) {
                        recommend.push("<div class='swiper-slide'>");
                        recommend.push("<ul class='shop_list'>");
                        recommend.push("<li>");
                        recommend.push("<input class='ppID' type='hidden' value='"+ this[1] + "'/>");
                        recommend.push("<img src='"+basePath+this[4]+"' alt=''>");
                        recommend.push("<p>"+this[0]+"</p>");
                        recommend.push("<h5>&yen;<span>"+this[2]+"</span></h5>");
                        recommend.push("</li>");
                        recommend.push("</ul>");
                        recommend.push("</div>");
                    });
                    mySwiper.appendSlide(recommend.join("")); //加到Swiper的最后
				}else{
				    $(".hot_recommend").hide();
                }
			},
			error : function(data) {
				/*alert("获取失败");*/
			}
		});
	}

//商品详情
function particulars() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxProductParticulars.jspa?";
	$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"ppk.ppID":ppID
			},
			success : function(data) {
				var search = eval("(" + data + ")");
				var txtList= search.urlList;
				var array=[];
				if(txtList!=null&&txtList!=""&&txtList.length!=0){
					for(var i=0;i<txtList.length;i++){
						array.push("<li class='det'>"+txtList[i]+"</li>");
					}
				}else{
					array.push("<li class='det'><div class='noDetail'>抱歉，该商品暂无详情</div></li>");
				}
				$(".shop-con .det").remove();
				$(".shop-con .sale").remove();
				$(".shop-con .eval").remove();
				$(".shop-con .page_my").remove();
				$(".shop-con").append(array.join(""));
				if($(".noDetail").length > 0){
					$(".noDetail").css("line-height","200px");
				}
				if($(".editablesmall").length > 0){
					$(".editablesmall").each(function(){
						$(this).removeAttr("style");
					})
				}
			},
			error : function(data) {
				/*alert("获取失败");*/
			}
		});
	}
//上一步
function lastStep(){
	if(pageNumber>1){
		pageNumber = parseInt(pageNumber)-1;
		comment();
	}
}
//下一步
function nextStep(){
	if(pageNumber<pageCount){
		pageNumber = parseInt(pageNumber)+1;
		comment();
	}
}
//选取
function choose(obj){
	pageNumber = $(obj).text();
	comment();
}

//售后服务
function afterSales(){
	$(".shop-con .det").remove();
	$(".shop-con .sale").remove();
	$(".shop-con .eval").remove();
	$(".shop-con .page_my").remove();
	$(".shop-con").append("<li class='sale'><div><img src='"+basePath+"page/newMyapp/images/newPCHomepage/sale.png' width='100%' /></div></li>");
}

//商品评论
function comment() {
	var url = basePath + "ea/newpcend/sajax_ea_ajaxProductEvaluation.jspa?";
	$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"pageForm.pageNumber" : pageNumber,
				"pageForm.pageSize" : 10,
				"ppk.ppID":ppID
			},
			success : function(data) {
				var comment = eval("(" + data + ")");
				var pageForm = comment.pageForm;
				var comment = [];
				if (pageForm != null && pageForm.list != null
						&& pageForm.list.length > 0) {
					$(pageForm.list).each(function(i, dom) {
						comment.push("<li class='eval'>");
						comment.push("<div class='eval_mil'>");
						comment.push("<h4 class='name'>"+((this[0]=="00")?"匿名用户":this[1])+"</h4>");
						comment.push("<div class='comment'>");
						comment.push("<p>"+this[2]+"</p>");
						comment.push("<ul class='imgs'>");
						if(this[3]!=null){
							comment.push("<li class='adv'>");
							comment.push("<img src='"+basePath+this[3]+"'>");
							comment.push("</li>");
						}
						if(this[4]!=null){
							comment.push("<li class='adv'>");
							comment.push("<img src='"+basePath+this[4]+"'>");
							comment.push("</li>");
						}
						if(this[5]!=null){
							comment.push("<li class='adv'>");
							comment.push("<img src='"+basePath+this[5]+"'>");
							comment.push("</li>");
						}
						comment.push("</ul>");
						comment.push("<h5 class='time'>"+this[6]+"</h5>");
						comment.push("</div>");
						comment.push("<div class='data'>");
						comment.push("<p class='color'>规格:"+this[7]+"</p>");
						comment.push("</div>");
						comment.push("</div>");
						comment.push("</li>");
					});
					pageCount = pageForm.pageCount;
				    pageNumber = pageForm.pageNumber;
				    var paging = [];
				    paging.push("<div class='page_my'>");
				    paging.push("<button type='button' style='float: left;' onclick='lastStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
				    for ( var i = 1; i <= pageCount; i++) {
				    	if(pageNumber==i){
				    		paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
				    	}else{
				    		if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
				    			paging.push("<li onclick='choose(this)'>"+i+"</li>");
				    		}else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
				    			paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
				    		}
				    	}
				    }
				    paging.push("</ul><button type='button' style='float: right;' onclick='nextStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
				    paging.push("</div>");
				}else{
					comment.push("<li class='det'><div class='noJudge'>该商品暂无评价</div></li>");
				}
				$(".shop-con .det").remove();
				$(".shop-con .sale").remove();
				$(".shop-con .eval").remove();
				$(".shop-con .page_my").remove();
				$(".shop-con").append(comment.join(""));
				if($(".noJudge").length > 0){
					$(".noJudge").css("line-height","200px");
				}
				$(".shop-con").after().append(paging.join(""));
				var page_nub = $(".page_my li").length;
				$(" .page_my ul").css("width",page_nub*40+"px");
				$(".page_my").css("width",80+page_nub*40+"px");
			},
			error : function(data) {
				/*alert("获取失败");*/
			}
		});
	}

//商品验证
function goodsValidate(){
	var standardArray=[];
	var flag=true;
	var standardChoice="";
	//商品规格的验证
	$(".detail_page .det_mil .right .color h4 .attriname").each(function(){
		if($(this).val()==""){
			standardChoice += $(this).parent("h4").text().trim() + ",";
			flag=false;
		}else{
			standardArray.push($(this).val()+":");
			$(this).parent("h4").next("ul").find("li").find(".attrivalue").each(function(){
				if($(this).val()!=""){
					standardArray.push($(this).val()+",");
				}
			});
		}
	});
	var standard=standardArray.join("");
	$("#standard").val(standard.substring(0,standard.length-1));
	if($("#standard").val()==""){
		$("#standard").val("默认规格");
	}
	//赠品的验证
	if($(".detail_page .det_mil .right .color .gift").size()!=0){
		var giftGoodsArray=[];
		var giftGoodsPrice=[];
		$(".detail_page .det_mil .right .gift .gift_con ul .list").each(function(){
			if($(this).hasClass("active")){
				giftGoodsArray.push($(this).find(".text .ptppidHide").val()+",");
				giftGoodsPrice.push($(this).find(".text .giftPrice").html().replace("¥",""));
			}else{
				$(this).find(".text .ptppids").val("");
				$(this).find(".text .ptStandards").val("");
			}
		});
		$("#giftGoods").val(giftGoodsArray.join(""));
		var totalGiftPrice=0;
		for (var i = 0; i < giftGoodsPrice.length; i++) {
			totalGiftPrice += parseFloat(giftGoodsPrice[i]);
		}
		$("#giftMoney").val(totalGiftPrice);
		var GoodsAllMoney= parseFloat($(".right .money .mon").text()) * parseInt($(".color .nub #num").val());
		if(flag){
			if($("#giftGoods").val()==""){
				alert("请选择赠品!");
				flag=false;
			}else if(totalGiftPrice>GoodsAllMoney){
				alert("选择的赠品总价格不能超过商品总价！");
				flag=false;
			}
		}else{
			if($("#giftGoods").val()==""){
				alert("请选择商品的"+standardChoice.substring(0,standardChoice.length-1)+"及赠品！");
				flag=false;
			}else{
				if(totalGiftPrice>GoodsAllMoney){
					alert("选择的赠品总价格不能超过商品总价！");
					flag=false;
				}else{
					alert("请选择商品的"+standardChoice.substring(0,standardChoice.length-1)+"!");
					flag=false;
				}
			}
		}
	}else{
		if(flag==false){
			alert("请选择商品的"+standardChoice.substring(0,standardChoice.length-1)+"!");
		}
	}
	return flag;
}

//立即购买
function buyNow() {
	var flag=goodsValidate();
	if(flag){
		var ptppid=$("#giftGoods").val();
		if(type=="报名"){
			alert("驾校报名全额支付功能即将开放，敬请期待！");
			return false;
		}
		var url = basePath + "ea/wfjshop/sajax_ea_validateOrdiGoods.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data : {
				ppid : ppID,
				goodsid : goodsId,
				companyId : companyId
			},
			success : function(data) {
				var m = eval("(" + data + ")");
				var login = m.login;
				if (login == "login") {
					document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
					return;
				}
				var result = m.result;
				if (result != "") {
					alert(result);
					if(result=="此产品限会员购买请立即升级"){
						document.location.href = basePath+"/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
						return;
					}
					return false;
				} 
				$("#mainForm").attr("action",basePath+"ea/newpcend/ea_pcGoodsPayNow.jspa").submit();
			},
			error : function(data) {
				/*alert("验证失败");*/
			}
		});
	}else{
		return false;
	}
}

//加入购物车
function addToShoppingCart(){
	var flag=goodsValidate();
	if(flag){
		var ptppid=$("#giftGoods").val();
		var price=parseFloat($(".money .mon").text());
		if(type=="报名"){
			alert("驾校报名全额支付功能即将开放，敬请期待！");
			return false;
		}
		if(price==parseFloat(0)){
			alert("0元产品不能加入购物车，请点击立即购买！");
			return false;
		}
		var url = basePath + "ea/wfjshop/sajax_ea_validateOrdiGoods.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			data : {
				ppid : ppID,
				goodsid : goodsId,
				companyId : companyId
			},
			success : function(data) {
				var m = eval("(" + data + ")");
				var login = m.login;
				if (login == "login") {
					document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
					return;
				}
				var result = m.result;
				if (result != "") {
					alert(result);
					if(result=="此产品限会员购买请立即升级"){
						document.location.href = basePath+"/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
						return;
					}
					return false;
				}
				//设置表单为异步提交
				$("#mainForm").submit(function(){
					$.ajax({
						url : basePath+"ea/newpcend/sajax_ea_ajaxPcAddShoppingCart.jspa",
						type : "post",
						async : true,
						data : $("#mainForm").serialize(),
						dataType : "json",
						success: function(data){
							var countMap = eval("(" + data + ")");
							var message = countMap.add;
							if(message=="addLimit"){
								alert("添加失败！购物车最多添加50种商品。");
								return false;
							}
							alert("添加成功！已添加至购物车。")
							window.location.href = basePath+"ea/newpcend/ea_pcSelShoppingCart.jspa";
							return;
						},
						error: function(){
							alert("加入购物车失败!");
						}
							
					});
					return false;
				});
				$("#mainForm").submit();
			},
			error : function(data) {
				/*alert("验证失败");*/
			}
		});
	}else{
		return false;
	}
}
