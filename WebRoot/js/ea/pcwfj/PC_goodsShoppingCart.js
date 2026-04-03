$(function(){
	var cartId = "";
	bestSeller();
	$(".sc_mil .mil_list li").each(function(){
		var price=parseFloat($(this).find(".money .goodsPrice").text()).toFixed(2);
		$(this).find(".money .giftPrice").each(function(){
			var giftPrice=parseFloat($(this).text()).toFixed(2);
			$(this).text(giftPrice);
		});
		$(this).find(".money .goodsPrice").text(price);
		var count=parseInt($(this).find(".add_chose .text").val());
		var total=parseFloat(price*count).toFixed(2);
		$(this).find(".total_price span").text(total);
	});
	/*数量加减*/
    $(".add").click(function(){
        var t=$(this).parents(".add_chose").find(".text");
        var dan=$(this).parents("li").find(".money .goodsPrice").text();
        t.val(t.val()*1+1);
        if($(this).parent(".add_chose").find(".counts").val()!=""){
        	$(this).parent(".add_chose").find(".counts").val(t.val());
        }
        var totalMoney=parseFloat(dan*t.val()).toFixed(2);
        $(this).parents("li").find(".total_price span").text(totalMoney);
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    event.stopPropagation();
    });
    $(".reduce").click(function(){
        var t=$(this).parents(".add_chose").find(".text");
        var dan=$(this).parents("li").find(".money .goodsPrice").text();
        t.val(t.val()*1-1);
        if(t.val()<1){
            t.val(1);
        }
        if($(this).parent(".add_chose").find(".counts").val()!=""){
        	$(this).parent(".add_chose").find(".counts").val(t.val());
        }
        var totalMoney=parseFloat(dan*t.val()).toFixed(2);
        $(this).parents("li").find(".total_price span").text(totalMoney);
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    event.stopPropagation();
    });
	/*购物车全选或全不选*/
	/*头部全选*/
	$("#checkbox-top").click(function(){
	    if(this.checked){
	        $(".Clearing :checkbox").prop("checked", true);
	        $(".sc_cart_con .sc_mil").find(".name :checkbox").prop("checked", true);
	        $(".sc_cart_con .sc_mil").find(".mil_list li :checkbox").prop("checked", true);
	    	$(".sc_cart_con .sc_mil .mil_list li .check").each(function(){
	    		$(this).parent(".checkbox").find(".cartIds").val($(this).attr("id"));
	    		$(this).parents("li").find(".counts").val($(this).parents("li").find(".text").val());
	    	});
	    }else{
	        $(".Clearing :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil").find(".name :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil").find(".mil_list li :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil .mil_list li .cartIds").val("");
	        $(".sc_cart_con .sc_mil .mil_list li .counts").val("");
	    }
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .selected span").text($(".sc_cart_con .sc_mil .mil_list .check:checked").length);
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    if($(".sc_cart_con .sc_mil .mil_list .check:checked").length>0){
	    	$(".Clearing ul button").css({"background-color":"#eb4f00","cursor":"pointer"});
	    }else{
	    	$(".Clearing ul button").css({"background-color":"#D3D2D2","cursor":"default"});
	    }
	});
	/*公司全选*/
	$(".all_check").click(function(){
		var allNum=$(".sc_cart_con .sc_mil .mil_list li .check").length;
	    if(this.checked){
	    	$(this).parents(".sc_mil").find(".mil_list li :checkbox").prop("checked", true);
	    	$(this).parents(".sc_mil").find(".mil_list li .check").each(function(){
	    		$(this).parent(".checkbox").find(".cartIds").val($(this).attr("id"));
	    		$(this).parents("li").find(".counts").val($(this).parents("li").find(".text").val());
	    	});
			var allCheckNum=$(".sc_cart_con .sc_mil .mil_list .check:checked").length;
	    	if(allCheckNum==allNum){
	        	$(this).parents(".sc_cart_con").find(".title :checkbox").prop("checked", true);
	        	$(this).parents(".content").find("#checkbox").prop("checked", true);	
	    	}
	    }else{
	        $(this).parents(".sc_mil").find(".mil_list li :checkbox").prop("checked", false);
	        $(this).parents(".sc_mil").find(".mil_list li .cartIds").val("");
	        $(this).parents(".sc_mil").find(".mil_list li .counts").val("");
	        $(this).parents(".sc_cart_con").find(".title :checkbox").prop("checked", false);
	        $(this).parents(".content").find("#checkbox").prop("checked", false);
	    }
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .selected span").text($(".sc_cart_con .sc_mil .mil_list .check:checked").length);
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    if($(".sc_cart_con .sc_mil .mil_list .check:checked").length>0){
	    	$(".Clearing ul button").css({"background-color":"#eb4f00","cursor":"pointer"});
	    }else{
	    	$(".Clearing ul button").css({"background-color":"#D3D2D2","cursor":"default"});
	    }
	});
	/*底部全选*/
	$("#checkbox").click(function(){
	    if(this.checked){
	        $(".sc_cart_con").find(".title :checkbox").prop("checked", true);
	        $(".sc_cart_con .sc_mil").find(".name :checkbox").prop("checked", true);
	        $(".sc_cart_con .sc_mil").find(".mil_list li :checkbox").prop("checked", true);
	        $(".sc_cart_con .sc_mil .mil_list li .check").each(function(){
	        	$(this).parent(".checkbox").find(".cartIds").val($(this).attr("id"));
	        	$(this).parents("li").find(".counts").val($(this).parents("li").find(".text").val());
	        });
	    }else{
	        $(".sc_cart_con").find(".title :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil").find(".name :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil").find(".mil_list li :checkbox").prop("checked", false);
	        $(".sc_cart_con .sc_mil .mil_list li .cartIds").val("");
	        $(".sc_cart_con .sc_mil .mil_list li .counts").val("");
	    }
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .selected span").text($(".sc_cart_con .sc_mil .mil_list .check:checked").length);
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    if($(".sc_cart_con .sc_mil .mil_list .check:checked").length>0){
	    	$(".Clearing ul button").css({"background-color":"#eb4f00","cursor":"pointer"});
	    }else{
	    	$(".Clearing ul button").css({"background-color":"#D3D2D2","cursor":"default"});
	    }
	});
	/*单个产品选中*/
	$(document).on("click",".sc_cart_con .sc_mil .mil_list li .check",function(){
	    var allNum=$(".sc_cart_con .sc_mil .mil_list li .check").length;
	    var allCheckNum=$(".sc_cart_con .sc_mil .mil_list .check:checked").length;
	  	var xuan_nub = $(this).parents(".sc_mil .mil_list").find("li .check:checked").length; 
	    var all_nub =  $(this).parents(".sc_mil .mil_list").find("li").length;
	    var this_top = $(this).parents(".sc_mil");
	    if(this.checked){
	    	$(this).parent(".checkbox").find(".cartIds").val($(this).attr("id"));
	    	$(this).parents("li").find(".counts").val($(this).parents("li").find(".text").val());
	    }else{
	    	$(this).parent(".checkbox").find(".cartIds").val("");
	    	$(this).parents("li").find(".counts").val("");
	    }
	    if(xuan_nub==all_nub){
	        this_top.find(".name :checkbox").prop("checked", true);
	    }else{
	        this_top.find(".name :checkbox").prop("checked", false);
	    }
	    if(allCheckNum==allNum){
	    	$(this).parents(".sc_cart_con").find(".title :checkbox").prop("checked", true);
	    	$(this).parents(".content").find("#checkbox").prop("checked", true);	
	    }else{
	        $(this).parents(".sc_cart_con").find(".title :checkbox").prop("checked", false);
	        $(this).parents(".content").find("#checkbox").prop("checked", false);
	    }
	    var totalPrice=0;
	    $(".sc_cart_con .sc_mil .mil_list .check:checked").parents("li").find(".total_price span").each(function(){
	    	totalPrice += parseFloat($(this).text());
	    });
	    $(".Clearing ul .selected span").text($(".sc_cart_con .sc_mil .mil_list .check:checked").length);
	    $(".Clearing ul .Total_cost span").html("&yen;"+totalPrice.toFixed(2));
	    if($(".sc_cart_con .sc_mil .mil_list .check:checked").length>0){
	    	$(".Clearing ul button").css({"background-color":"#eb4f00","cursor":"pointer"});
	    }else{
	    	$(".Clearing ul button").css({"background-color":"#D3D2D2","cursor":"default"});
	    }
	    event.stopPropagation();
	});
	$(document).on("click",".sc_cart_con .sc_mil .mil_list .checkbox,.sc_cart_con .sc_mil .mil_list li",function(){
		$(this).find(".check").click();
	});
    /*删除弹框*/
    $(".sc_mil .mil_list .delete").click(function(){
    	 event.stopImmediatePropagation();
        $(".det-add_alert_").show();
        $("label").hide();
        $("#fixed_box .Clearing").css("z-index","0");
        cartId = $(this).parents("li").find(".checkbox .check").attr("id");
    });
    $(".det-add_alert .btn #abolish").click(function(){
        $(".det-add_alert_").hide();
        $("label").show();
        $("#fixed_box .Clearing").css("z-index","999");
    });
    //删除购物车商品
    $(".det-add_alert .btn #sure").click(function(){
    	var url = basePath + "ea/newpcend/sajax_ea_ajaxDelShoppingCart.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			data : {
				"cart.cartId" : cartId
			},
			success : function(result){
				if(result=="login"){
    				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
    				return;
				}
				window.location.reload();
				return;
			},
			error : function(){
				alert("删除失败！");
			}
		});
    });
    //点击购物车商品
    $(".sc_mil .mil_list #goodsShop").click(function(){
    	event.stopPropagation();
    	var ppid = $(this).parent("li").find(".ppID").val();
    	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
    	window.open(url);
    	return;
    });
    //点击购物车赠品
    $(".sc_mil .mil_list li .gift .shop_img").click(function(){
    	event.stopPropagation();
    	var pptId = $(this).find(".pptId").val();
    	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+pptId+"&titleJudge=05";
    	window.open(url);
    	return;
    });
    $(".sc_mil .mil_list li .gift").click(function(){
    	$(this).parent("li").find(".check").click();
    	event.stopPropagation();
    });
    /*购物车*/
    $(".search_mil .shop_cart").mouseover(function(){
        $(this).addClass("on_cart");
    });
    $(".search_mil .shop_cart").mouseleave(function(){
        $(this).removeClass("on_cart");
    });
    /*综合排序*/
    $(".shop-sort button").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    /*分页*/
    $(".mil_shop_con .shop_page li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    //绑定滚动条事件
    $(window).bind("scroll", function () {
        var sTop2 = $(window).scrollTop();
        var a=$("#fixed_box").offset().top+78-$(window).height();
        if (sTop2 >= a) {
            $(".Clearing").removeClass("float");
        }
        else {
            $(".Clearing").addClass("float");
        }
    });
    function init_scroll(){
            var fixed_st=$("#fixed_box").offset().top;
            var sTop = $(window).height()-78;
            if (sTop >= fixed_st) {
                $(".Clearing").removeClass("float");
            }
            else {
                $(".Clearing").addClass("float");

            }
    };
    init_scroll();
});

//热卖
function bestSeller(){
	var url = basePath + "/ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";			
	$.ajax({
		url : url,
		type : "post",
		async : true,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":1,
		  "search":"smart",
		  "proName":""
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			var product = [];
			$(".hot_shop").find("ul").empty();
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				product.push("<h3 class='title'>热卖商品</h3>");
				$(pageForm.list).each(function(i, dom) {
					if(i<5){
						product.push("<li  onclick='selHotGoods(this)'>");
						product.push("<input type='hidden' class='ppID' value='"+this[1]+"'/>");
						product.push("<img src='"+basePath+this[4]+"' alt='' class='top'>");
						product.push("<div class='bottom'>");
						product.push("<h3>&yen;<span>"+this[2]+"</span></h3>");
						product.push("<p class='text'>"+this[0]+"</p>");
						product.push("</div></li>");
					}
				})
				$(".hot_shop").find("ul").append(product.join(""));
			}
		},
		error: function(){
			alert("产品加载失败");
		}
	});	
}

//购物车查找商品
function selShoppingCartGoods(obj){
	var showParam =$(obj).parent(".shop-search").find(".selGoods").val();
	var url = basePath + "ea/newpcend/ea_pcSelShoppingCart.jspa?showParam="+showParam;
	window.location.href = encodeURI(url);
	return;
}

//点击热卖商品
function selHotGoods(obj){
	var ppid = $(obj).find(".ppID").val();
	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
	window.open(url);
}

//购物车结算验证
function payShoppingCart(){
	if($(".sc_cart_con .sc_mil .mil_list .check:checked").length > 0){
		var flag=true;
		$(".sc_cart_con .sc_mil .mil_list .check:checked").each(function(){
			var goodsMoney = parseFloat($(this).parents("li").find(".total_price span").text());
			var goodsCount = parseInt($(this).parents("li").find(".text").val()); 
			var giftMoney = 0;
			$(this).parents("li").find(".gift").each(function(){
				giftMoney += parseFloat($(this).find(".money .giftPrice").text()*$(this).find(".number").text());
				var giftCount = parseInt($(this).find(".number").text());
				if(giftCount>goodsCount){
					alert("选择的商品的每样赠品数量不能超过商品数量");
					flag=false;
					return false;
				}
			});
			if(flag && giftMoney > goodsMoney){
				alert("选择的每个商品的总价必须大于赠品总价!");
				flag=false;
				return false;
			}
		});
		if(flag){
			var url=basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa";
			$.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				success : function(data){
					var result=eval("(" + data + ")");
					var login = result.login;
					if(login == "login"){
						document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
						return;
					}
					$("#CartForm").attr("action",basePath+"ea/newpcend/ea_pcGoodsPayNow.jspa").submit();
				},
				error : function(){
					/*alert("验证失败！");*/
				}
			});
		}else{
		return false;
		}
	}else{
		return false;
	}
}
