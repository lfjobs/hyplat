$(function(){
	var addressID="";
	bestSeller();
	selAllAddress();
	if($("#showParam").val()){
		var allMoney = 0;
		$(".con_bid li .unit-price .money span").each(function(){
			var money=parseFloat($(this).text()).toFixed(2);
			var count=$(this).parents("li").find(".amount .nub").text();
			$(this).text(money);
			$(this).parents("li").find(".total .all-money span").text(parseFloat(money*count).toFixed(2));
			$(this).parents("ul").find(".gift").each(function(){
				var giftMoney=parseFloat($(this).find(".giftPrice .money span").text()).toFixed(2);
				$(this).find(".giftPrice .money span").text(giftMoney);
			});
			allMoney += parseFloat($(this).parents("li").find(".total .all-money span").text());
		});
		$(".close_btn #total").html("&yen;"+allMoney.toFixed(2));
		$(".address_bid .title .company").each(function(){
			var companyIds=$(this).parent(".title").next(".con_bid").find("li:first .companyID").val();
			$(this).val(companyIds);
		});
	}else{
    	var allMoney=parseFloat($(".con_bid .total .all-money").find("span").text()).toFixed(2);
    	$(".con_bid .money span").each(function(){
    		var price = parseFloat($(this).text()).toFixed(2);
    		$(this).text(price);
    	});
    	$(".con_bid .total .all-money").find("span").text(allMoney);
    	$(".close_btn p").find("span").html("&yen;"+allMoney);
	}
    /*已保存的收货地址*/
    /*点击状态*/
    $(".address_mil .add_con li .address_set").click(function(){
        var flag = true;
        /*$(this).parents(".address_mil .add_con li").addClass("active").siblings().removeClass("active");*/
        var url=basePath+"ea/newpcend/sajax_ea_ajaxChangeDefaultAddress.jspa";
        var addressID=$(this).parent("li").find(".addressID").val();
        var consignee=$(this).parent("li").find(".mingzi").text();
        var addressDetailed=$(this).parent("li").find(".addressStr").val();
        var phone=$(this).parent("li").find(".addressPhone").val();
        if(consignee==""){
        	alert("请填写收款人姓名");
            flag = false;
		}
         if(addressDetailed==""){
            alert("请填写收款人地址");
            flag = false;
        }
        if(phone==null){
            alert("请填写收款人电话");
            flag = false;
        }
		/*if($(".information").length > 0){
			$(".information #accountName").val($(this).parent("li").find(".mingzi").text());
			$(".information #reference").val($(this).parent("li").find(".addressPhone").val());
			$(".information #staffAddress").val($(this).parent("li").find(".addressStr").val().replace(/,/g,"")+$(this).parent("li").find(".addressDetail").val());
		}*/
		if(flag){
            $(this).parents(".address_mil .add_con li").addClass("active").siblings().removeClass("active");
		}
        if(flag){$.ajax({
        	url:url,
        	type:"post",
        	async:true,
        	data:{"staffAddress.addressID":addressID},
        	dataType:"json",
        	success:function(result){
        		if(result == "login"){
            		document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
            		return;
        		}
        	},
        	error:function(){
        		alert("默认地址设置失败！");
        	}
        });
        }
    });
	/*鼠标放上状态*/
	$(" .address_mil .add_con li").mouseenter(function(){
    	$(this).addClass("on").siblings().removeClass("on");
	});
	/*鼠标放上状态*/
		$(" .address_mil .add_con li").mouseleave(function(){
    	$(this).removeClass("on");
	});
	/*修改收货地址-设置默认地址*/
	$(".add_address .add_add_con .default_address .right").click(function(){
    	$(this).toggleClass("on");
	});
	/*删除弹框*/
	$(".address_mil .add_con li .delete_").click(function(){
    	$(".det-add_alert_").show();
    	addressID=$(this).parent("li").find(".addressID").val();
	});
	$(".det-add_alert .btn #abolish").click(function(){
    	$(".det-add_alert_").hide();
	});
	$(".det-add_alert .btn #sure").click(function(){
    	$(".det-add_alert_").hide();
    	var url=basePath+"ea/newpcend/sajax_ea_ajaxDeleteStaffAddress.jspa";
		$.ajax({
			url: url,
			type: "post",
			async : true,
			data: {"staffAddress.addressID":addressID},
			success:function(result){
				if(result == "login"){
            		document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
            		return;
        		}
        		window.location.reload();
        		return;
			},
			error:function(){
				alert("删除收货地址失败！");
			}
		});
	});
	//数量加减
    $(".jia").click(function(){
        var t=$(this).parents(".choose_bid").find(".nub");
        var dan=$(this).parents(".select_bid .con_bid li").find(".money span");
        var zong=$(this).parents(".select_bid .con_bid li").find(".total .all-money span");
        t.text(parseInt(t.text())+1);
        $(this).parent(".choose_bid").find(".counts").val(t.text());
        zong.text((dan.text()* t.text()).toFixed(2));
        setTotal();
    });
    $(".jian").click(function(){
        var t=$(this).parents(".choose_bid").find(".nub");
        var dan=$(this).parents(".select_bid .con_bid li").find(".unit-price span");
        var zong=$(this).parents(".select_bid .con_bid li").find(".total .all-money span");
        t.text(parseInt(t.text())-1);
        zong.text((dan.text()* t.text()).toFixed(2));
        if(parseInt(t.text())<1){
            t.text(1);
            zong.text((dan.text()*1).toFixed(2));
        }
        $(this).parent(".choose_bid").find(".counts").val(t.text());
        setTotal();
    });
    //点击商品
    $(".con_bid li #goodsShop").click(function(){
    	var ppid = $(this).parent("li").find(".ppID").val();
    	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
    	window.open(url);
    	return;
    });
    //点击赠品
    $(".con_bid .gift .shop_img .shop").click(function(){
    	var pptID = $(this).parent(".shop_img").find(".pptID").val();
    	var url=basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+pptID+"&titleJudge=05";
    	window.open(url);
    	return;
    });
    function setTotal(){
    	if($("#showParam").val()){
    		var allGoodsMoney = 0;
    		$(".con_bid li .unit-price .money span").each(function(){
    			allGoodsMoney += parseFloat($(this).parents("li").find(".total .all-money span").text());
    		});
    		$(".close_btn #total").html("&yen;"+allGoodsMoney.toFixed(2));
    	}else{
        	var tol = $("#total");
        	tol.html("&yen;"+$(".select_bid .con_bid li .total .all-money span").text());
    	}
    }
	//跳转至新增收货地址
    $("#add_address").click(function(){
    	var count=$(".con_bid .amount .choose_bid_ .choose_bid .nub").text();
		var ppID=$("#ppID").val();
		var standard=$("#standard").val();
		var showParam = $("#showParam").val();
		var url=basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa";
		$.ajax({
			url: url,
			type:"post",
			async : true,
			success:function(data){
				var result=eval("(" + data + ")");
				var login = result.login;
				if(login == "login"){
					document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
					return;
				}
				if(showParam == "payShoppingCart"){
					var params = "";
					$(".con_bid li").each(function(){
						params += "&cartIds=" + $(this).find(".cartIds").val() +"&counts=" + $(this).find(".counts").val();
					});
					window.location.href = encodeURI(basePath+"page/newMyapp/PC_addGoodsStaffAddress.jsp?showParam=" + showParam + params);
					return;
				}
				window.location.href = encodeURI(basePath+"page/newMyapp/PC_addGoodsStaffAddress.jsp?ppID="+ppID+"&standard="+standard+"&count="+count);
				return;
			},
			dataType : "json",
			error:function(){
				/*alert("验证失败！");*/
			}
		});
    });
    //提交订单
	$("#mainForm").submit(function(){
		if($("#formLimit").val()=="false"){
		   	$.ajax({
	    		url : basePath+"ea/newpcend/sajax_ea_ajaxMakePayBills.jspa",
	    		type: "post",
	    		async: true,
	    		data: $("#mainForm").serialize(),
	    		dataType: "json",
	    		success: function(data){
	    			var result=eval("(" + data + ")");
	    			var login = result.login;
	    			var payJournalNum = result.payJournalNum;
	    			if(login=="login"){
	    				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
	    				return;
	    			}
	    			document.location.href = basePath + "ea/newpcend/ea_toGoodsPayMethod.jspa?payJournalNum="+payJournalNum;
	    			return;
	    		},
	    		error: function(data){
	    			alert("订单提交失败！");
	    		},
				complete: function(){
					$("#formLimit").val("true");
					
				}
	    	});
		}else{
			alert("您已提交订单，请勿重复提交。");
		}
		return false;
	});
    //用户下订单
    $("#makeBills").click(function(){
    	var flag = true;
    	$("#addressID").val($(".add_con").find(".active .addressID").val());
    	if($("#addressID").val()==""){
    		alert("请选择收货地址！");
    		flag=false;
    	}
    	if($("#showParam").val()){
    		$(".con_bid li").not(".gift").each(function(){
    			var goodsMoney = parseFloat($(this).find(".total .all-money span").text());
    			var goodsCount = parseInt($(this).find(".amount .choose_bid .nub").text());
    			var giftMoney = 0;
    			$(this).nextAll(".gift").each(function(){
    				giftMoney += parseFloat($(this).find(".giftPrice .money span").text()*$(this).find(".choose_bid_ .giftNub p").text());
    				var giftCount = parseInt($(this).find(".giftAmount .choose_bid_ .giftNub p").text());
    				if(giftCount>goodsCount){
    					alert("选择的商品的每样赠品数量不能超过商品数量");
    					flag=false;
    					return false;
    				}
    			});
    			if(flag && giftMoney > goodsMoney){
    				alert("每个结算商品的总价必须大于赠品总价!");
    				flag=false;
    				return false;
    			}
    		});
    	}else{
    		$("#totalMoney").val($(".close_btn p").find("span").html().replace("¥",""));
    		$("#count").val($(".choose_bid_ .choose_bid .nub").text());
    		if(parseFloat($("#ptppMoney").val())>parseFloat($("#totalMoney").val())){
    			alert("选择的赠品总价格不能超过产品总价!");
    			flag=false;
    		}
    	}
    	if($(".information").length > 0){
    		$(".information ul li").each(function(){
    			if($(this).find(".right input").val()==""){
    				alert("请填写所有学员信息！");
    				flag=false;
    				return false;
    			}
    		});
    	}
    	if(flag){
    		$("#mainForm").submit();
    	}else{
    		return false;
    	}
     });
    /*30分钟计时*/
    /*var x = 30, interval;
    window.onload = function() {
        var d = new Date("1111/1/1,0:" + x + ":0");
        interval = setInterval(function() {
            var m = d.getMinutes();
            var s = d.getSeconds();
            m = m < 10 ? "0" + m : m;
            s = s < 10 ? "0" + s : s;
            time.innerHTML = m + ":" + s;
            if (m == 0 && s == 0) {
                clearInterval(interval);
                return;
            }
            d.setSeconds(s - 1);
        }, 1000);
    }*/
    jQuery.fn.limit=function(){
        var self = $("[limit]");
        self.each(function(){
            var objString = $(this).html().split("&nbsp;");
            var phoneNumber=objString[objString.length-1];
            objString.pop();
            objString = objString.join("&nbsp;");
            var objLength = objString.length;
            var num = $(this).attr("limit");
            if(objLength > num){
                $(this).attr("title",objString);
                objString = $(this).html(objString.substring(0,num) + "...&nbsp;"+phoneNumber);
            }
        })
    };
    $("[limit]").limit();
});

//热卖商品
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
						product.push("<li onclick='selHotGoods(this)'>");
						product.push("<input type='hidden' class='ppID' value='"+this[1]+"'/>");
						product.push("<img src='"+basePath+this[4]+"' alt='' class='top'>");
						product.push("<div class='bottom'>");
						product.push("<h3>&yen;<span>"+this[2]+"</span></h3>");
						product.push("<p class='text'>"+this[0]+"</p>");
						product.push("</div></li>");
					}
				})
				$(".hot_shop").find("ul").append(product.join(""));
				/*给数字地球商城5的倍数元素添加类名*/
		        var collection2 = jQuery('.mil_shop_con ul').children();
		        var maxcount2 = collection2.length;
		        if (maxcount2>=4) {
		            for (var x=4; x<=maxcount2;x++) {
		                if ((x-4)%5 == 0) {
		                    collection2.eq(x).addClass('mil_shop_con_li');
		                }
		            }
		        }
		        
		        $(".mil_shop_con .shop_page ul li").removeClass("mil_shop_con_li");
			}
		},
		error: function(){
			/*alert("产品加载失败");*/
		}
	});	
}

//查询所有收货地址
function selAllAddress(){
	var url=basePath+"ea/newpcend/sajax_ea_ajaxPcStaffAddress.jspa";
	$.ajax({
		url:url,
		type:'post',
		async:false,
		success:function(data){
			var result=eval("(" + data + ")");
			var staffAddress=result.staffAddress;
			var addressDefaultObj=result.staffAddress.addressDefaultObj;
			var addressObjs=result.staffAddress.addressObjs;
			var addressCount=result.staffAddress.addressCount;
			var array=[];
			if(staffAddress!=null&&addressCount!=null){
				$(".title h5 .addressCount").text(addressCount);
			}
			if(staffAddress!=null&&addressDefaultObj!=null&&addressDefaultObj.length>0){
				if( addressDefaultObj[1]!=null){
				var addressDefaultStr = addressDefaultObj[1].replace(/,/g,"");
                }else {
                    var addressDefaultStr ="";
				}
				if(addressDefaultObj[3]!=null){
                    var addressDefaultp = addressDefaultObj[3].replace(/,/g,"");
                }else {
                    var addressDefaultp ="";
                }
                if(addressDefaultObj[4]!=null){
                    var addressDefaultx = addressDefaultObj[4].replace(/,/g,"");
                }else {
                    var addressDefaultx ="";
                }
                if(addressDefaultObj[2]!=null){
                    var addressDefaultName = addressDefaultObj[2].replace(/,/g,"");
                }else {
                    var addressDefaultName ="";
                }
				array.push("<li class='active'><h5>");
				array.push("<span class='mingzi'>"+addressDefaultName+"</span>");
				array.push("<span>收</span></h5>");
				array.push("<input type='hidden' class='addressID' value='"+addressDefaultObj[0]+"'>");
				array.push("<input type='hidden' class='addressStr' value='"+addressDefaultObj[1]+"'>");
				array.push("<div class='text'>");
				array.push("<p limit='28'>"+addressDefaultStr+"&nbsp;"+addressDefaultp+"&nbsp;"+addressDefaultx+"</p>");
				array.push("<input type='hidden' class='addressDetail' value='"+addressDefaultObj[3]+"'>");
				array.push("<input type='hidden' class='addressPhone' value='"+addressDefaultObj[4]+"'>");
				array.push("</div>");
				array.push("<img src='"+basePath+"page/newMyapp/images/address.png' class='address' />");
				array.push("<img src='"+basePath+"page/newMyapp/images/address_.png' class='address_set'/>");
				array.push("<p class='revise_' onclick='jumpToShowStaffAddress(this);'>修改</p><p class='delete_'>删除</p>");
				array.push("<img src='"+basePath+"page/newMyapp/images/add_gou.png' class='gou' /></li>");
				if($(".information").length > 0){
					$(".information #accountName").val(addressDefaultObj[2]);
					$(".information #reference").val(addressDefaultObj[4]);
					$(".information #staffAddress").val(addressDefaultObj[1].replace(/,/g,"")+addressDefaultObj[3]);
					$(".address_bid .con_bid").each(function(){
						if($(this).next("div").hasClass("information")){
							$(this).find("li:last").css("border-bottom","0px solid #ddd");
						}
					})
				}
			}
			if(staffAddress!=null&&addressObjs!=null&&addressObjs.length>0){
				$(addressObjs).each(function(){
					if($(this)[1]!=null){
					var addressObjStr = $(this)[1].replace(/,/g,"");
                    }else {
                        var addressObjStr ="";
					}
                    if($(this)[3]!=null){
                        var addressObjp = $(this)[3].replace(/,/g,"");
                    }else {
                        var addressObjp ="";
                    }
                    if($(this)[4]!=null){
                        var addressObjx = $(this)[4].replace(/,/g,"");
                    }else {
                        var addressObjx ="";
                    }
                    if($(this)[2]!=null){
                        var addressObjName = $(this)[2].replace(/,/g,"");
                    }else {
                        var addressObjName ="";
                    }
					array.push("<li><h5>");
					array.push("<span class='mingzi'>"+addressObjName+"</span>");
					array.push("<span>收</span></h5>");
					array.push("<input type='hidden' class='addressID' value='"+$(this)[0]+"'>");
					array.push("<input type='hidden' class='addressStr' value='"+$(this)[1]+"'>");
					array.push("<div class='text'>");
					array.push("<p limit='28'>"+addressObjStr+"&nbsp;"+addressObjp+"&nbsp;"+addressObjx+"</p>");
					array.push("<input type='hidden' class='addressDetail' value='"+$(this)[3]+"'>");
					array.push("<input type='hidden' class='addressPhone' value='"+$(this)[4]+"'>");
					array.push("</div>");
					array.push("<img src='"+basePath+"page/newMyapp/images/address.png' class='address' />");
					array.push("<img src='"+basePath+"page/newMyapp/images/address_.png' class='address_set'/>");
					array.push("<p class='revise_' onclick='jumpToShowStaffAddress(this);'>修改</p><p class='delete_'>删除</p>");
					array.push("<img src='"+basePath+"page/newMyapp/images/add_gou.png' class='gou' /></li>");
				});
			}
			$(".add_con").append(array.join(""));
		},
		error:function(data){
			/*alert("获取地址失败！");*/
		}
	});
}

//跳转至修改收货地址
function jumpToShowStaffAddress(obj){
	var count=$(".con_bid .amount .choose_bid_ .choose_bid .nub").text();
	var ppID=$("#ppID").val();
	var standard=$("#standard").val();
	var url=basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa";
	var addressID=$(obj).parent("li").find(".addressID").val();
	var showParam = $("#showParam").val();
	$.ajax({
		url:url,
		type:"post",
		async : true,
		success:function(data){
			var result=eval("(" + data + ")");
			var login = result.login;
			if(login == "login"){
				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
				return;
			}
			if(showParam == "payShoppingCart"){
				var params = "";
				$(".con_bid li").each(function(){
					params += "&cartIds=" + $(this).find(".cartIds").val() +"&counts=" + $(this).find(".counts").val();
				});
				window.location.href = encodeURI(basePath+"ea/newpcend/ea_selShowStaffAddress.jspa?showParam=" + showParam +"&staffAddress.addressID="+ addressID + params);
				return;
			}
			window.location.href= encodeURI(basePath+"ea/newpcend/ea_selShowStaffAddress.jspa?ppID="+ppID+"&standard="+standard+"&count="+count+"&staffAddress.addressID="+addressID);
			return;
		},
		dataType : "json",
		error:function(){
			/*alert("验证失败！");*/
		}
	});
}

//点击热卖商品
function selHotGoods(obj){
	var ppid = $(obj).find(".ppID").val();
	var url = basePath+"ea/newpcend/ea_goodsDetails.jspa?ppk.ppID="+ppid+"&titleJudge=05";
	window.open(url);
}
