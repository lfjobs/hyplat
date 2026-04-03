$(document).ready(function(){
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20
	}).jqmAddClose('.close');
	//确认订单
	$("#commit").click(function(){
		var liname = $(".wfj11_015_mo").find("li.on").text();
		if(liname=="餐厅就餐"){
			if($("#privateRoom").val()==""){
				alert("请扫您所在餐桌的桌号");
	          	return false;
			}
		}else{
			if($("#addressDetailed").val() == ""&&(noviceAddress==""||noviceAddress==null)) {
	        	alert("请选择收货地址");
	          	return false;
			
		   }
		}
		
		
		var companyIds="";
		$(".comid").each(function(){
			companyIds+=$(this).val()+",";
		});

		$(".leave").val(($(".leavemessage").val()==""?"无":$(".leavemessage").val())+",");
        var pid = "";
        $(".ptbl").each(function () {
            var ppid = $(this).find(".ppid").val();
            var num = $(this).find(".number").val();
            var stardard = $(this).find(".stardard").val();
            var priceid= $(this).find(".priceid").val();
            var prt= $(this).find(".prt").val();
            var price= $(this).find(".price").val();
            var ccj= $(this).find(".ccj").val();
            if (stardard == "") {
                stardard = "默认规格";
            }
            pid += ppid + "-" + num + "-" + stardard + "-"+ priceid + "-"+ prt + "-"+ price + "-"+ ccj;
            pid += "zz";
        });
		
		var companyId=companyIds;
		$(".pid").val(pid);
		$(".companyId").val(companyId);
	
		if(pid==""){
			alert("请选择产品");
			return false;
		}else{
	        ajaxsut();
		}
	});
	
	//切换支付方式
	$(".wfj11_015_choice").click(function(){
        if($(this).find("span").text()!="(您金币为0,无法选择)"&& $(this).find("span").text()!="(您的积分不足，无法选择)"&& $(this).find("span").text()!="(您的金币不足，无法选择)"&& $(this).find("span").text()!="(您积分为0,无法选择)"&& $(this).find("span").text()!="(您的金币已冻结，无法选择)") {
            $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
            $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
            zffs = $(this).find(".second").find("img").attr("name");
        }
});
	
	//隐藏支付方式
	$(".jqmOverlay").live("click",function(){
		$(".wfj11_015_buy_commit").fadeOut();
		$("#occlusion2").jqmHide();
		$(".wfj11_015_bottom").css("display","");

		//document.location.href=basePath+"ea/industry/ea_CompanyProducts.jspa?companyId="+companyId+"&ccompanyId="+ccompanyId;

            document.location.href=basePath+"/ea/pobuy/ea_getPhoneOrdersList.jspa?staid="+staffID+"&sccId="+sccid
		
	});
	
	
	$(".jqmOverlay").live("click",function(){
		$(".tanchu").fadeOut();
		$("#occlusion2").jqmHide();
	});





});

function payInput(obj){
    var len = $(obj).val().length - 1;
    if (len >= 0) {
        $(".pay_label li").eq(len).addClass("pass").nextAll().removeClass();
        if (len == 5) {
            //    setTimeout("initPay()", 400);//设置延时（验证密码方法放这位置）
            $(".pay_inp").blur();
            var code = $(".pay_inp").val(); //获取密码
            //查找用户的支付密码
            var url = basePath + "/ea/jinbi/sajax_toSearchPaymentCode.jspa?paymentCode="+code+"&user="+user;
            $.ajax({
                url : url,
                type : "get",
                async : false,
                dataType : "json",
                success : function (data) {
                    var member = eval("(" + data + ")");
                    var paymentCode=member.code;
                    if(paymentCode == '00'){//支付密码正确
                        initPay();
                        if(zffs == "6"){
                            //金币支付
                            if(journalNum!=""&&journalNum!=null){
                                selfBuyPB("07");
                            }else{
                                buy(ddid,$(".wfj11_015_com").find("a").text().trim(),"07")
                            }


                        }else if(zffs == "7"){
                            //积分支付

                            if(journalNum!=""&&journalNum!=null){
                                selfBuyPB("05");
                            }else{
                                buy(ddid,$(".wfj11_015_com").find("a").text().trim(),"05")
                            }
                        }
                    }else if(paymentCode == '01'){//支付密码不正确
                        prompt("支付密码不正确");
                    }else{//没有支付密码
                        prompt("没有支付密码请到");
                    }
                }
            });
        }
    } else {
        $(".pay_label li").removeClass();
    }

}

// 确认订单 生成订单方法
function ajaxsut() {

	var goodsname = "";
	$(".goodsname").each(function(){
		goodsname+=$(this).val()+" ";
		
	});
	if(goodsname!=""){
	goodsname = $.trim(goodsname);
	}
	$("#sortid").val(goodsname);
	var formData = $("#formsutm").serialize();
	formData = decodeURIComponent(formData,true);

    if(postype=="04"){
        journalNum = genOrderNum();
    }
    if(journalNum!=null&&journalNum!="") {
        ajaxPayBackUp();
        return;
    }
	var ulp = basePath
	+ "/ea/wfjshop/sajax_ea_ShoppingMulti.jspa?";
   $.ajax({
        type : "GET",
        url : ulp+formData,
        async : false,
        dataType : "json",
        success : function(data) {
	
	   if (data == null) {
		   alert("数据提交失败。请重试");
	   } else {
		    ddid = data;
           var ret = checkGoldInte();
           if(ret){
               $(".wfj11_015_bottom").css("display","none");
               $("#occlusion2").css("z-index",$(".wfj11_015").css("z-index")+1);
               $("#occlusion2").jqmShow();
               $(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
               $(".wfj11_015_buy_commit").fadeIn(1000);
           }else {
                alert("积分查询错误")
           }
	}
    },
  error : function(data) {
	alert("提交订单失败");
}
});
}

//点击确认支付
function zf() {
	goodsname = "("+$(".wfj11_015_width>a").text().trim()+")"+$("#sortid").val();
    if(goodsname.length>200){
        goodsname=goodsname.substr(0,200)+"...";
    }
	
	var liname = $(".wfj11_015_mo").find("li.on").text();
	
	if (zffs == null) {
		alert("请选择支付方式");
		return false;
	} else if (liname=="外卖送货"&&$("#addressDetailed").val() == "") {
		alert("请选择收货地址");
		return false;
	} else {

		if (zffs == '1') {
			
			var par = new Array();
			par.push(basePath);
            if(journalNum!=""&&journalNum!=null){
                par.push("page/ea/main/marketing/supermarket/selfservice/wfjAlipay.jsp?");
            }else{
                par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
            }
			par.push("WIDout_trade_no=" + ddid);
			par.push("&WIDtotal_fee="+ $("#morre").val());
			par.push("&WIDsubject=" + goodsname);
            if(journalNum!=null&&journalNum!="") {
                par.push("&WIDbody=selfpay");//订单描述
            }else{
                par.push("&WIDbody=''");//订单描述
            }
			par.push("&WIDit_b_pay=''");//超时时间
			par.push("&WIDextern_token=''");//钱包
			//window.location.href = encodeURI(par.join(""));
			_AP.pay(encodeURI(par.join("")));
		} else if (zffs == '2') {
			$("#formsutm").attr(
					"action",
					basePath + "ea/wfjshop/ea_zfgs.jspa?ddid=" + ddid
							+ "&baseUrl=" + basePath+"&code=2");
			document.formsutm.submit.click();
		}else if (zffs == '3') {
            var attach = "other";
            if(journalNum!=""&&journalNum!=null){
                attach = "selfpay";
            }
			var money = $("#morre").val();
			var ua = navigator.userAgent.toLowerCase();
			var isWeixin = ua.indexOf('micromessenger') != -1;
			if (!isWeixin) {
                $(".loading").show();
			 //app微信支付
				$.ajax({
					url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa",
					type:"get",
					data:{
						"payDto.orderId":ddid,
						"payDto.totalFee":money,
						"payDto.body":goodsname,
                        "payDto.attach":attach
					},
					success:function suc(data){
						var mb=eval("("+data+")");
						var appPackage=mb.appPackage;
						//调用ios/android
						var u = navigator.userAgent;
			        	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			        	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
			            var  appid = appPackage.appid
			            var partnerid = appPackage.partnerid;
			            var prepayid = appPackage.prepayid;
			            var packages = appPackage.packages;
			            var noncestr = appPackage.noncestr;
			            var timestamp = appPackage.timestamp;
			            var err_code = appPackage.err_code;
			            var sign = appPackage.sign;

                        $(".loading").hide();

                        if(isAndroid==true){
			         		Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,ddid,err_code,goodsname,attach);
			        	}else if(isiOS==true){   		
			        		 var url= "func=" + 'ioscallappChat';        
			                  params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':ddid,'errcode':err_code,'goodsname':goodsname,'attach':attach};
			                  for(var i in params){
			                  url = url + "&" + i + "=" + params[i];
			                  }   
			                  window.webkit.messageHandlers.Native.postMessage(url);    
			        	}
					}
					
				});
				
			}else{
				window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsname+"-"+money+"-"+staffID+"-1-1-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
				
			}	
			//return false;
		}else if(zffs=='5'){
			//钱盒子支付
			var morre = $("#morre").val();
			document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+ddid+"&morre="+morre;
			return false;
		}else if(zffs=='6'){
            var money = $("#morre").val();
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);
            return;
            // //金币
            // if(journalNum!=""&&journalNum!=null){
            //     selfBuyPB("07");
            // }else{
            //     buy(ddid,$(".wfj11_015_com").find("a").text().trim(),"07")
            // }

        }else if(zffs=='7'){
            var money = $("#morre").val();
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);
            // //积分
            // if(journalNum!=""&&journalNum!=null){
            //     selfBuyPB("05");
            // }else{
            //     buy(ddid,$(".wfj11_015_com").find("a").text().trim(),"05")
            // }

        }else{
			if(token!=0){
				return;
			}
			token=1;
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"fenlei=03&ddid="+ddid,
				success:function suc(data){
					var mb=eval("("+data+")");
					var succ=mb.succ;
					var threeNo=mb.threeNo;
					if(succ=="success"){
						window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo;
							
					};
				}
				
			});
			
		}
	}
}


function yz(){
   var shuliang=$("#shuliag").val();
   if(shuliang<0){
      alert("数量大于0"+shuliang);
 	  $("#shuliag").val("1");
   }else{
          var privace=Number($("#morre").val());
		  var shuliang=Number($("#shuliag").val());
		  var qian=privace*shuliang;
		 $("#sapnqian").html("￥"+qian);
   }
}
//////处理
function callIOSyundanhao(danhao) {
    if (danhao != null&&danhao.indexOf("canzuo") != -1) {

        danhao= danhao.substring(danhao.indexOf("canzuo=")+7,danhao.lastIndexOf("&"));

        window.location.href = callurl + "&pl=" + danhao;
    }else{

        alert("请扫码正确的桌号");

    }

}
function yundan(tiaoma){

    if (tiaoma!=null&&tiaoma.indexOf("canzuo") != -1) {

        tiaoma= tiaoma.substring(tiaoma.indexOf("canzuo=")+7,tiaoma.lastIndexOf("&"));

        window.location.href = callurl + "&pl=" + tiaoma;
    }else{
        alert("请扫码正确的桌号");

    }
}


function saoyisao() {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


    if(isAndroid==true){
        Android.callYunDan();
    }else if(isiOS==true){
        var url= "func=" + 'callioscanzuo';
        window.webkit.messageHandlers.Native.postMessage(url);
    }

}

//新增临时地址，不保存到数据库，因为没有用户
function addAddress(){
    var backurls = window.location.href;
    if(mode=="cash") {
        if(backurls.indexOf("&staffAddress.consignee=")!=-1){
            backurls = backurls.substring(0,backurls.indexOf("&staffAddress.consignee="))

        }
        document.location.href = basePath + "ea/wfjshop/ea_toaddAddress.jspa?backurls=" + encodeURIComponent(backurls);
    }else if(mode=="scard"||mode=="scan"||mode=="facecard") {
        if(backurls.indexOf("&staffAddress.addressID=")!=-1){
            backurls = backurls.substring(0,backurls.indexOf("&staffAddress.addressID="))

        }
        document.location.href = basePath+"ea/wfjshop/ea_getAddressList.jspa?backurls=" + encodeURIComponent(backurls)+"&intf=31&staffid="+staffID+"&stype=stype&companyId="+companyId+"&ccompanyId="+ccompanyId+"&scandc="+scandc;

    }else{

        document.location.href = basePath+"ea/wfjshop/ea_getAddressList.jspa?intf=&stype=stype&companyId="+companyId+"&ccompanyId="+ccompanyId+"&scandc="+scandc;
    }

}

//备份信息
function ajaxPayBackUp() {
	var  privateRoom = $("#privateRoom").val();

    var liname = $(".wfj11_015_mo").find("li.on").text();
    if(liname=="餐厅就餐"){
            consignee="";
            phone="";
           noviceAddress="";
    }else {
        privateRoom = "";
    }
    var ulp = basePath
        + "/ea/sm/sajax_ea_ajaxPayBackUp.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        data: {
            "payBackupBill.addressID": addressID,
            "payBackupBill.noviceName": consignee,
            "payBackupBill.novicePhone": phone,
            "payBackupBill.noviceAddress": noviceAddress,
            "payBackupBill.privateRoom":privateRoom,
			"payBackupBill.remark":$(".leavemessage").val(),
            "payBackupBill.journalNum":journalNum
        },
        dataType : "json",
        success : function(data) {
            ddid = journalNum;
            if(mode=="scan") {
                var ret = checkGoldInte();
                if(ret) {
                    $(".wfj11_015_bottom").css("display", "none");
                    $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                    $(".wfj11_015_buy_commit").fadeIn(1000);
                }
            }else if(mode=="scard"){//购物卡支付
                //跳回输入密码页面如果有密码的话
                document.location.href = basePath+"/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum="+journalNum+"&totalMoney="+totalMoney+"&totalNum="+totalNum+"&cardNum="+cardNum+"&posNum="+posNum+"&paymentCode="+paymentCode+"&sccId="+sccId+"&vipmoney="+vipmoney+"&openid="+openid;

            }else if(mode=="cash"){//现金支付
                document.location.href = basePath + "ea/sm/ea_showCash.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum+"&posNum="+posNum+"&comID="+companyId+"&address=1";

            }else if(mode=="face"){//刷脸支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" +  journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum +"&posNum=" + posNum+"&comID="+companyId+"&companyName="+companyName;

            }else if(mode=="facecard"){//刷脸购物卡支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" +  journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum +"&posNum=" + posNum+"&comID="+companyId+"&companyName="+companyName+"&sccId="+sccId+"&zf=1";

            }


        },
        error : function(data) {
            console.log("备份信息");
        }
    });

}

function checkGoldInte() {
    var isOk;
    var price = $(".ptbl").find(".price").val();
    $.ajax({
        type: "GET",
        url: basePath + "/ea/jinbi/sajax_checkGoldInte.jspa?morre="+price,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("("+data+")");
            var integral  = member.integral;
            var gold = member.gold;
            var status = member.status;
            if (data == null) {
                alert("数据提交失败。请重试");
            } else {
                if (parseFloat(integral) - accMul(parseFloat(price) ,parseFloat("100")) < 0) {
                    $(".integral_ span").text("(您的积分不足，无法选择)").show();
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (integral == "" || isNaN(integral) || integral == null) {
                    $(".integral_ span").text("(您积分为0,无法选择)");
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".integral_ span").text("(您的积分不足，无法选择)");
                    $("integral_").attr("style","pointer-events: none")
                    $(".integral_ .second").hide()
                }
                if(status!=null && status!=""){
                    $(".gold span").text("(您的金币已冻结，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
                    $(".gold span").text("(您的金币不足，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (gold == "" || isNaN(gold) || gold == null) {
                    $(".gold span").text("(您金币为0,无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".gold span").text("(您的金币不足，无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }

                isOk = true;
            }
        },
        error: function (data) {
            isOk = false
        }
    });
    return isOk;
}


function buy(formData,companyName,buyPatter) {
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_diaoyongJifen.jspa?buyPatter="+buyPatter+"&ddid="+ddid;
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        beforeSend : function() {
            $(".loading").show();
        },
        success : function(data) {
            $(".loading").hide();
            var json = eval('(' + data + ')');
            var ss=json.chenggong;
            if(ss == "cg"){
                // if (buyIsOkPage=="buyIsOkPage"){
                //     document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
                // }else {
                // }
            }else  if(ss=="shibai"){
                alert("您的帐号有问题，请联系工作人员");
                bbb=true;
            }
        },
        error : function(data) {
            alert("提交订单失败");
            bbb=true;
        }
    });
    document.location.href = basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName;

}
//终端机积分金币购物
function selfBuyPB(wfStatus4){
    var totalMoney = $("#morre").val();
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_genSelfPayPoint.jspa?";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        data: {
            morre: totalMoney,
            journalNum: ddid,
            wfStatus4:wfStatus4
        },beforeSend : function() {
            $(".loading").show();
        },
        success: function (data) {
            $(".alert_dh").hide();
            var m = eval('(' + data + ')');
            var result = m.result;
           // var bo = m.bo;

            // if(bo==false){
            //
            //     alert("请勿重复支付");
            //
            //     return;
            // }
            if (result) {

                document.location.href = basePath + "page/conacts/restaurant/paySuc.jsp";

            } else {
                console.log("支付出错");
            }

        },
        error: function (data) {

            console.log("支付失败")
        }
    });
}

function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}


//生成订单号
function genOrderNum(){
    //生成订单号
    $.ajax({
        url:basePath+"/ea/restmn/sajax_ea_getJum.jspa?d="+new Date(),
        type:"get",
        async: false,
        dataType : "json",
        success:function(data){
            var mb=eval("("+data+")");
            journalNum = mb.jum;


        },error:function(data){
            console.log("生成单号");
        }
    });


    return journalNum;
}