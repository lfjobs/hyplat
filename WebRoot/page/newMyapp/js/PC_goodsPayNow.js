$(function(){
	bestSeller();
	selAllAddress();
	//跳转至新增收货地址
    $("#add_address").click(function(){
    	var count=$(".con_bid .amount .choose_bid_ .choose_bid .nub").text();
		var ppID=$("#ppID").val();
		var standard=$("#standard").val();
		var url=basePath+"ea/newpcend/sajax_ea_ajaxValidateCusLogin.jspa";
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
				window.location.href=basePath+"page/newMyapp/PC_addGoodsStaffAddress.jsp?ppID="+ppID+"&standard="+standard+"&count="+count;
				return;
			},
			dataType : "json",
			error:function(){
				alert("验证失败！");
			}
		});
    });
    
    //提交订单
	$("#mainForm").submit(function(){
		if($("#formLimit").val()=="false"){
		   	$.ajax({
	    		url : basePath+"ea/newpcend/sajax_ea_ajaxMakePayBills.jspa",
	    		type: "post",
	    		async : true,
	    		data:$("#mainForm").serialize(),
	    		success: function(result){
	    			if(result=="login"){
	    				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
	    				return;
	    			}
	    			alert("订单提交成功！");
	    		},
	    		error: function(data){
	    			alert("订单提交失败！");
	    		},
				complete: function(){
					$("#formLimit").val("true");
					
				}
	    	});
		}else{
			alert("您已提交订单，请耐心等候。");
		}
		return false;
	});
    
    //用户下订单
    $("#makeBills").click(function(){
    	$("#addressID").val($(".add_con").find(".active .addressID").val());
    	$("#totalMoney").val($(".close_btn p").find("span").html().replace("¥",""));
    	$("#count").val($(".choose_bid_ .choose_bid .nub").text());
    	var ptppid="";
    	$(".con_bid .gift .txt").each(function(i){
    		if(i == $(".con_bid .gift .txt").size()-1){
    			ptppid +=$(this).find("input").val();
    			return;
    		}
    		ptppid +=$(this).find("input").val()+",";
    	});
    	$("#ptppid").val(ptppid);
    	if($("#addressID").val()==""){
    		alert("请选择收货地址！");
    		return false;
    	}
    	$("#mainForm").submit();
     });
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
						product.push("<li>");
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
			alert("产品加载失败");
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
				var addressArray=addressDefaultObj[1].split(",");
				array.push("<li class='active'>");
				array.push("<h5><span class='dizhi'>"+addressArray[0]+addressArray[1]+"</span>");
				array.push("<span class='mingzi'>"+addressDefaultObj[2]+"</span>");
				array.push("<span>收</span></h5>");
				array.push("<input type='hidden' class='addressID' value='"+addressDefaultObj[0]+"'>");
				array.push("<input type='hidden' class='addressStr' value='"+addressDefaultObj[1]+"'>");
				array.push("<div class='text'>");
				array.push("<p limit='25'>"+addressArray[2]+"&nbsp;"+addressDefaultObj[3]+"&nbsp;"+addressDefaultObj[4]+"</p>");
				array.push("</div>");
				array.push("<img src='"+basePath+"page/newMyapp/images/address.png' class='address' />");
				array.push("<img src='"+basePath+"page/newMyapp/images/address_.png' class='address_set'/>");
				array.push("<p class='revise_' onclick='jumpToShowStaffAddress(this);'>修改</p><p class='delete_'>删除</p>");
				array.push("<img src='"+basePath+"page/newMyapp/images/add_gou.png' class='gou' /></li>");
			}
			if(staffAddress!=null&&addressObjs!=null&&addressObjs.length>0){
				$(addressObjs).each(function(){
					var addressArray=$(this)[1].split(",");
					array.push("<li><h5><span class='dizhi'>"+addressArray[0]+addressArray[1]+"</span>");
					array.push("<span class='mingzi'>"+$(this)[2]+"</span>");
					array.push("<span>收</span></h5>");
					array.push("<input type='hidden' class='addressID' value='"+$(this)[0]+"'>");
					array.push("<input type='hidden' class='addressStr' value='"+$(this)[1]+"'>");
					array.push("<div class='text'>");
					array.push("<p limit='25'>"+addressArray[2]+"&nbsp;"+$(this)[3]+"&nbsp;"+$(this)[4]+"</p>");
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
			alert("获取地址失败！");
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
			window.location.href=basePath+"ea/newpcend/ea_selShowStaffAddress.jspa?ppID="+ppID+"&standard="+standard+"&count="+count+"&staffAddress.addressID="+addressID;
			return;
		},
		dataType : "json",
		error:function(){
			alert("验证失败！");
		}
	});
}