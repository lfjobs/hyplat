	$(document).ready(function(){

			var u = navigator.userAgent;
			isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

			if(isAndroid){
				$(".copy_btn").attr("onclick","Androidcopy(this)");
			}else if(isiOS){
				$(".copy_btn").attr("onclick","IOSCopy(this)");
			}

		 	$(".wfj12_014_hidden_buy").css({"bottom":"0"})
			$(".wfj12_014_hidden_buy").find("td").attr("style"," height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.025+"px; color:#660000;");
			$(".wfj12_014_buy_width").find("td").attr("style"," height:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_014_buy_width").find("td").eq(0).css("height",$(window).height()*0.06+"px")
			$(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$("#money").attr("style","color:#F74C31;");
			$("#money").css("color","#F74C31");
			$("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.025+"px;padding-left:20px;");

        	$("#wfj_sh").click(function(){
        		document.location.href=basePath+"/ea/hypb/ea_userConfirmationReceipt.jspa?staid="+staid+"&id="+cashid+"&journalNum="+jum+"&wfStatus4="+wfStatus4+"&companyID="+companyID;
        	});
        	$(".wfj12_014_choice").click(function(){
    			$(".wfj12_014_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
    			$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
    			zffs=$(this).find(".second").find("img").attr("name");
    		});
        	//保存修改后学员信息
        	$("#tosave").click(function(){
        		var noviceName = $("#noviceName").val();
        		var novicePhone = $("#novicePhone").val();
        		var noviceCode = $("#noviceCode").val();
        		var noviceAddress = $("#noviceAddress").val();
        		var url = basePath +"ea/bdbill/sajax_updateNovice.jspa?jo="+jum+"&noviceName="+noviceName+"&novicePhone="+novicePhone+"&noviceCode="+noviceCode+"&noviceAddress="+noviceAddress;
        		$.ajax({
        			url : url,
        			type: "get",
        			dataType:"json",
        			success:function(data){
        				var member = eval("("+data+")")
        				var type = member.type;
        				if(type == 1){
        					alert("修改成功！");
        					$("#jqModeladd").hide();
        					$(".alert123").hide();
        				}
        			}
        		});

        	});

        	//返回
    		$("#back").click(function(){
    			$("#jqModeladd").hide();
    			$(".alert123").hide();
    		});

		    $(".alert123").click(function(){
		        	$(".alert123").hide();
		        	$("#jqModeladd").hide();
		        	$(".pays").hide();
		        });
			$("#zf").click(function(){
                checkGoldInte(morre);
                $(".alert123").show();
                $(".pays").show();

                    // if (confirm("是否要修改学员的信息？")) {
                    //     update();
                    // } else {
                    //     $(".alert123").show();
                    //     $(".pays").show();
                    // }

			});

        accountInfo();
        });//加载完毕



    	function update(){
    		var url=basePath+"/ea/bdbill/sajax_getInformation.jspa?jo="+jum;
			$.ajax({
		         url:url,
		         type:"get",
		         async:false,
		         dataType:"json",
		         success:function(data){
		         	var member = eval("(" + data + ")");
					var nologin = member.nologin;
					var UserName = member.UserName;
					var Reference = member.Reference;
					var ReferenceCode = member.ReferenceCode;
					var ReferrerAddress = member.ReferrerAddress;

					$("#noviceName").val(UserName);
					$("#novicePhone").val(Reference);
					$("#noviceCode").val(ReferenceCode);
					$("#noviceAddress").val(ReferrerAddress);
		         },error:function(data){
		          	alert("操作失败！");
		         }
	         });
			$(".alert123").show();
			$("#jqModeladd").show();
    	};
    	function accountInfo() {
            $.ajax({
                url: basePath+"/ea/pobuy/sajax_accountInfo.jspa?",
                type:"get",
                async: false,
                data:{
                    "cashid":cashid,
                },
                success : function (data) {
                    var member = eval("(" + data + ")");
                    if(member==null){
                        return;
                    }
                    var caccount = member.caccount;

                    var str = new Array();
                    if (caccount != null) {
                    	str.push("<div class='password_2'>");
                        str.push("<h5 class='tit'>平台账号和密码</h5>");
                        str.push("<ul class='password_con'><li>");
                        str.push("<h5>组织机构名称：</h5><p>"+caccount[2]+"</p></li> <li>");
                        str.push("<h5>用户名：</h5><p>"+caccount[0]+"</p></li><li>");
                        str.push(" <h5>初始密码：</h5><p>123456</p></li></ul> </div>");
                    }
                    $(".shop_mil").after(str.join(""))
                }
            })
        }
        function zf(){
        	var goodsName = "("+$(".company>div>span").text().trim()+")";
        	$(".gods").each(function(){
        		goodsName+=$(this).text()+",";

        	});
        	if(goodsName!=""){
        		goodsName=goodsName.substring(0,goodsName.length-1);
        		if(goodsName.length>10){
                    goodsName=goodsName.substr(0,10)+"...";
				}
        	}

	         if(zffs==null){
                alert("请选择支付方式");
                return false;
         	 }else {
                 if (zffs == '1') {//支付宝
                     var par = new Array();
                     par.push(basePath);
                     par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
                     par.push("WIDout_trade_no=" + jum);
                     par.push("&WIDtotal_fee=" + morre);
                     par.push("&WIDsubject=" + goodsName);
                     if (gd == "智能货柜") {
                         par.push("&WIDbody='selfpay'");//订单描述
                     } else {
                         par.push("&WIDbody=''");//订单描述
                     }
                     par.push("&WIDit_b_pay=''");//超时时间
                     par.push("&WIDextern_token=''");//钱包
                     _AP.pay(encodeURI(par.join("")));
                 } else if (zffs == '2') {
                     document.forms[0].reset();
                     $("#formsutm").find("#ddid").val(jum);
                     $("#formsutm").find("#baseUrl").val(basePath);
                     $("#formsutm").find("#morre").val(morre);
                     $("#formsutm").attr("action", basePath + "ea/wfjshop/ea_zfgs.jspa");
                     $("#submit").click();
                     //return false;
                 } else if (zffs == '3') {
                     var ua = navigator.userAgent.toLowerCase();
                     var isWeixin = ua.indexOf('micromessenger') != -1;
                     var attach = "other";
                     if (gd == "智能货柜") {
                         attach = "selfpay";
                     }
                     if (!isWeixin) {
                         $(".loading").show();

                         //调用ios/android
                         var u = navigator.userAgent;
                         var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                         var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                         var elkc = "";
                         try {
                             if (isAndroid == true) {
                                 Android.isElKCApp();
                             } else if (isiOS == true) {
                                 var url = "func=" + 'isElKCApp';
                                 window.webkit.messageHandlers.Native.postMessage(url);
                             }
                             elkc = "elkc";
                         } catch (e) {
                             elkc = "";
                         }

                         //app微信支付
                         $.ajax({
                             url: basePath + "ea/wfjshop/sajax_ea_appWechatPay.jspa",
                             type: "get",
                             data: {
                                 "payDto.orderId": jum,
                                 "payDto.totalFee": morre,
                                 "payDto.body": goodsName,
                                 "payDto.attach": attach,
                                 elkc: elkc,
                                 isiOS: isiOS
                             },
                             success: function suc(data) {
                                 var mb = eval("(" + data + ")");
                                 var appPackage = mb.appPackage;

                                 var appid = appPackage.appid
                                 var partnerid = appPackage.partnerid;
                                 var prepayid = appPackage.prepayid;
                                 var packages = appPackage.packages;
                                 var noncestr = appPackage.noncestr;
                                 var timestamp = appPackage.timestamp;
                                 var err_code = appPackage.err_code;
                                 var sign = appPackage.sign;

                                 $(".loading").hide();
                                 if (isAndroid == true) {
                                     Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, jum, err_code, goodsName, attach);
                                 } else if (isiOS == true) {
                                     var url = "func=" + 'ioscallappChat';
                                     params = {
                                         'appid': appid,
                                         'partnerid': partnerid,
                                         'prepayid': prepayid,
                                         'noncestr': noncestr,
                                         'timestamp': timestamp,
                                         'sign': sign,
                                         'journalNum': jum,
                                         'errcode': err_code,
                                         'goodsname': goodsName,
                                         'attach': attach
                                     };
                                     for (var i in params) {
                                         url = url + "&" + i + "=" + params[i];
                                     }
                                     window.webkit.messageHandlers.Native.postMessage(url);
                                 }
                             }

                         });

                     } else {
                         alert(2);
                         window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params=" + jum + "-" + goodsName + "-" + morre + "-" + staid + "-1-1-" + attach + "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

                     }
                 } else if (zffs == '5') {
                     //钱盒子支付
                     document.location.href = basePath + "ea/wfjshop/ea_moneyBoxPay.jspa?ddid=" + jum + "&morre=" + morre;
                     return false;
                 } else if (zffs == '6'){
                     $(".overlay").addClass("active");
                 $(".popup_pay").show();
                 $(".pay_inp").focus();
                 $(".sum_num").text(morre);
                 return;
               }else if(zffs=='7'){
                $(".overlay").addClass("active");
                $(".popup_pay").show();
                $(".pay_inp").focus();
                $(".sum_num").text(morre);
                return;

                }else {
	    			if(token!=0){
	    				return;
	    			}
	    			token=1;
	    			$.ajax({
	    				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
	    				type:"get",
	    				data:"fenlei=03&ddid="+jum,
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

      ///删除订单
		function del(){
			var del = confirm("确定删除该订单吗？")
			if(del == true){
				document.location.href=basePath+"/ea/pobuy/ea_delBill.jspa?cbid="+cashid+"&staid="+staid+"&pl="+pl;
		       }else{
		        	 return false;
		       }
		};

		function zzqr(){
			$("button#zzqr").attr("disabled","disabled");
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"ddid="+jum,
				success:function suc(data){
					var mb=eval("("+data+")");
					var succ=mb.succ;
					if(succ=="success"){
					$("button#zzqr").attr("style","display:none");
						alert("确认成功");
					}
				}
			});
		}
		function goodsDetail(ppid,goodsid,companyid){
			window.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyid;
		}

		//复制
		function IOSCopy(obj){
			 var content=$(obj).prev('h4').find('span').text();
			 var url= "func=" + 'iosstick';
		    params={'content':content};
		    for(var i in params){
		     url = url + "&" + i + "=" + params[i];
		    }
		    alert("复制成功");
		    window.webkit.messageHandlers.Native.postMessage(url);
		}
		function Androidcopy(obj){
			var content=$(obj).prev('h4').find("span").text();
			Android.callcopy(content);
		}

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
                            if(zffs == "6"){
                                //金币支付
                                buy(jum,companyName,"07")


                                //积分支付
                            }else if(zffs == "7"){

                                buy(jum,companyName,"05")
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

    function checkGoldInte(price) {
        var isOk;
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
                    if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
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

    function accMul(arg1,arg2)
    {
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    }

    function buy(ddid,companyName,buyPatter) {
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
                if(ss == "cg") {
                    // if (buyIsOkPage=="buyIsOkPage"){
                    //     document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
                    // }else {
                    if (gd == "智能货柜") {
                        document.location.replace(basePath + "/ea/pobuy/ea_getCashBill.jspa?&cbid="+cashid+"&sccId="+sccId+"&lastPay=close");

                    } else {

                    document.location.replace(basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName);
                }
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
    }