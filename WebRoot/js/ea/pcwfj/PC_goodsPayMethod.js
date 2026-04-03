$(document).ready(function(){
	$(".payment_con_bid li .txt p").text(goodsSize==1?body.replace(";",""):body!=""?body.substring(0,body.indexOf(";"))+"...等多个产品":"");
	$(".payment_con_bid li .cost span").text(total_amount!=""?parseFloat(total_amount).toFixed(2):"");
    $(".payment_con li").each(function(){
    	if($(this).hasClass("active")){
    		$(this).find(".radio").css({"background-color":"#ff5d15","border-color":"#ff5d15"});
    	}
    });
    /*支付选择*/
    $(".payment_con li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        $(this).find(".radio").css({"background-color":"#ff5d15","border-color":"#ff5d15"});
        $(this).siblings().find(".radio").css({"background-color":"#eee","border-color":"#cacaca"});
    });
    /*查看订单详情*/
    $(".payment_details ul .look").click(function(){
        $(".payment_details p").show();
        $(this).parent(".payment_details ul").toggleClass("on");
    });
    $(".payment_details ul .pack").click(function(){
        $(".payment_details p").hide();
        $(this).parent(".payment_details ul").toggleClass("on");
    });
    /*点击确认付款*/
    $(".address_bid div .payment").click(function(){
    	$.ajax({
    		url : basePath + "ea/newpcend/sajax_ea_ajaxValidatePayBills.jspa",
    		type : "post",
    		async : true,
    		data : {
    			"payJournalNum" : payJournalNum,
    			"total_amount" : total_amount
    		},
    		dataType : "json",
    		success : function(data){
    			var result = eval("(" + data + ")");
    			var login = result.login;
    			var payBillsExist = result.payBillsExist;
    			var payBillsMoney = result.payBillsMoney;
    			if(login=="login"){
    				document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
    				return;
    			}
    			if(payBillsExist=="false"){
    				alert("该订单不存在！");
    				document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
    				return;
    			}
    			if(payBillsMoney=="false"){
    				alert("该订单有误！");
    				document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
    				return;
    			}
    			var payMehod = $(".payment_con .active .payMethod").val();
    			if(payMehod=="aliPay"){
    				document.location.href = encodeURI(basePath + "page/newMyapp/PC_wfjAlipay.jsp?payJournalNum="+payJournalNum+"&projectName="+subject+"&projectBody="+body+"&total_amount="+total_amount);
    				return;
    			}else if(payMehod=="weChat"){
    				$.ajax({
    					url : basePath + "ea/newpcend/sajax_ea_ajaxMakeWeChatPayCodeUrl.jspa",
    					type : "post",
    					async : true,
    					data : {
    						"basePath" : basePath,
    						"payJournalNum" : payJournalNum,
    						"total_amount" : total_amount,
    						"projectName" : subject
    					},
    					success : function(data){
		    				 
    						$(".sub .title").remove();
    						$(".sub .payment_con").remove();
    						$(".sub .showPay").remove();
    						$(".sub").append("<div class='weChatICO'><img src='"+basePath+"page/newMyapp/images/WePayLogo.png'></div>");
    						$(".sub").append("<div id='qrcode'></div>");
    						var qrcode = new QRCode(document.getElementById("qrcode"),{
    							  text: data,
    							  width: 256,
    							  height: 256,
    							  colorDark : '#000000',
    							  colorLight : '#ffffff',
    							  correctLevel : QRCode.CorrectLevel.H
    						});
    						qrcode.makeCode(data);
    						$(".sub").append("<div class='weChatDesc'><img src='"+basePath+"page/newMyapp/images/weChatDesc.png'></div>");
    					}
    				});
    			    /*检测是否完成微信确认付款*/
    				function validatePayComplete(){
    			    	$.ajax({
    			    		url : basePath + "ea/newpcend/sajax_ea_ajaxValidateRelatedBill.jspa",
    			    		type : "post",
    			    		async : true,
    			    		data : {
    			    			"payJournalNum" : payJournalNum
    			    		},
    			    		success : function(data){
    			    			if(data=="payComplete"){
    			    				 window.clearInterval(int);
    			    				 $(".address_bid").not(".sub").remove();
    			    				 $(".sub").find("div").remove();
    			    				 $(".sub").append("<div class='wxPayComplete'><img src='"+basePath+"page/newMyapp/images/ico_com.png' /><p>支付成功</p></div>");
    			    				 $(".sub").append("<div class='timeJump'><p><span id='jumpTo'>5</span>秒后自动跳转至商城首页</p></div>");
    			    				 var i = $(".sub #jumpTo").text();
    			    				 function countDown(){
    			    					 if (i == 0) {
    			    						 clearInterval(intervalid); 
    			    						 window.location.href = basePath +"ea/newpcend/ea_skip.jspa?titleJudge=05";
    			    						 return;
    			    					 }
    			    					 i--;
    			    					 $(".sub #jumpTo").html(i);
    			    				  }
    			    				 var intervalid = self.setInterval(function(){countDown()},1000);
    			    			}
    			    		}
    			     	});
    			    }
    			   var int = self.setInterval(function(){validatePayComplete()},500);
    			}
    		},
    		error : function(){
    			/*alert("验证订单失败！");*/
    		}
    	});
    });
});
