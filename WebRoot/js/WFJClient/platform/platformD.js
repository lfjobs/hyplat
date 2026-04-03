	//全额付款
	function quan(){
		var money = $("#moneyp").val();
		$("#moneys").text(money);
	}
	//分期付款
	function dianji(obj){
		var money =$(obj).find("#money").val();
		$("#moneys").text(money);
					
	}	
	
	if($("#moneys").text()=="0"){
		$(".wfj11_015_buy_commit").addClass();
		$(".none").hide();
		
	}

	//表单提交
	function submint(){
		
		if($("#companyName").val() == "") {
        	alert("请填写公司名称");
          	return false;
		
	   }
		
		if($("#companyPhone").val() == "") {
        	alert("请填写电话");
          	return false;
		
	   }
		
		
        
		var addre=$("#addre").val();
		var companyName = $("#companyName").val();	
		var companyManager = $("#companyManager").val();
		var companyPhone = $("#companyPhone").val();
        var moneys = $("#moneys").text();
		
		
		
		var	url = basePath +"ea/wfjplatform/sajax_ea_getzhifu.jspa?goodsid=3&ppids="+ppid+"&company.companyName="+
		companyName+"&company.companyManager="
		+companyManager+"&cdl.companyPhone="+companyPhone
		+"&staffAddress.addressID="+addre
		+"&company.companyIdentifier="+companyName
		+"&cdl.companyAddress=01"
		+"&cdl.companyManager=01"
			+"&moneys="+moneys;


		 $.ajax({
		        type : "GET",
		        url : url,
		        async : false,
		        dataType : "json",
		        success : function(data) {
		        	var member = eval("(" + data + ")");
					var page = member.count;
					
			   if (page == "01") {
				  
				   ajaxsut();//确认订单 生成订单方法
				   
			   } else {
				   alert("数据提交失败。请重试");
				
			}
		    },
		  error : function(data) {
			alert("提交订单失败");
		}
		});
		
		
	}	
	// 确认订单 生成订单方法
	function ajaxsut() {
        var moneys = $("#moneys").text();
		var addre=$("#addre").val();
        var companyName = $("#companyName").val();
        var companyPhone = $("#companyPhone").val();
        var ulp = basePath
		+ "/ea/wfjshop/sajax_ea_Shopping.jspa?staffAddress.addressID="+addre+"&ppid="+ppid+"&morre="+moneys+"&count=1&indus="+moneys;

	   $.ajax({
	        type : "GET",
	        url : ulp,
	        async : false,
	        dataType : "json",
           data : {
               sort : content,
               platfromConpanyName:companyName,
               platfromid:platfromid,
               platfromAccount:companyPhone,
           },
	        success : function(data) {
	        	var member = eval("(" + data + ")");
	        	var ppt = member.ddid;
		   if (ppt == null) {
			   alert("数据提交失败。请重试");
		   } else {
			    ddid = ppt;
			    if($("#moneys").text()!="0"){
			    zf();//支付的方法
			    }else{
			    var companyName = $("#companyName").val();	
			    document.location.href=basePath+"/page/WFJClient/suc/fansOk.jsp?ddid="+ddid+"&companyName="+companyName;
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
		var moneys = $("#moneys").text();
		var liname = $(".wfj11_015_mo").find("li.on").text();
		

			if (zffs == '1') {
				
				var par = new Array();
				
				par.push(basePath);
				par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
				par.push("WIDout_trade_no=" + ddid);
				par.push("&WIDtotal_fee="+ $("#morre").val());
				
				par.push("&WIDsubject=(北京天太世统科技有限公司)" + model);
				par.push("&WIDbody=''");//订单描述
				par.push("&WIDit_b_pay=''");//超时时间
				par.push("&WIDextern_token=''");//钱包
				window.location.href = encodeURI(par.join(""));
			} else if (zffs == '3') {
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
                            "payDto.totalFee":moneys,
                            "payDto.body":"(北京天太世统科技有限公司)"+content,
                            "payDto.attach":"other"
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
                                Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,ddid,err_code,content,"other");
                            }else if(isiOS==true){
                                var url= "func=" + 'ioscallappChat';
                                params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':ddid,'errcode':err_code,'goodsname':content,'attach':'other'};
                                for(var i in params){
                                    url = url + "&" + i + "=" + params[i];
                                }
                                window.webkit.messageHandlers.Native.postMessage(url);
                            }
                        }

                    });

                }else{
                    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+content+"-"+moneys+"-"+staffID+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

                }
			}else if(zffs=='5'){
				//钱盒子支付
				
				document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+ddid+"&morre="+moneys;
				return false;
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