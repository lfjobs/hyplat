//点击确认支付
var zffs = "2";
var ddid = "";
var attach = "smsk";

$(function(){

	if(money==null||money==""){
         var price = $(".div-js .active").find(".price").val();
        $("#morre").val(price);
        $(".hj").text(price);
	}









    //计时宽度
    var box_fh_wid=0;
    $(".div-js ul li").each(function(){
        box_fh_wid+=$(this).outerWidth(true);

    })
    $(".div-js ul").width(box_fh_wid+5);
    $(".div-js ul li").eq().click();
    //计时选中
    $(".div-js ul li").click(function () {
        $(this).addClass("active").siblings("li").removeClass("active");
        var totalMoney = $(this).find(".price").val();
           if(money==""||money==null){
               $("#morre").val(totalMoney);
               $(".hj").text(totalMoney);
                return false;
		   }

            var tu= $(this).find(".timeUnits").val();
            if(tu==timeUnits){
                $("#morre").val(money);
                $(".hj").text(money);
			}else {

                if(Number(totalMoney)>=Number(money)) {  //会员价格大于实际付钱
                    $("#morre").val(totalMoney);
                    $(".hj").text(totalMoney);
                }else{
                    //会员价格小于实际付钱
                    var yz = 0;
					 if(timeUnits=="0"&&tu=="1"){
                        //如果默认的是小时
						 var mf = Math.ceil(getHours(time)/24);
                         yz = (mf==0?1:mf)*Number(totalMoney);

					 }else if((timeUnits=="0"||timeUnits=="1")&&tu=="2"){
                         var mf = Math.ceil(getHours(time)/24/30);
                         yz =(mf==0?1:mf)*Number(totalMoney);
					 }
                    $("#morre").val(yz);
                    $(".hj").text(yz);



				}
            }
            ppid = $(this).find(".ppid").val();

    })

});



function pay() {
    var mm = $(".hj").text();
    if(mm==""||mm==null){
        return false;
    }

    var timeUnits = $(".div-js .active").find(".timeUnits").val();
      ppid = $(".div-js .active").find(".ppid").val();
    if(timeUnits=="1"){
        var  carNumber = $(".zjcp").text();
        if(!checkbday(carNumber,ppid)){
            $(".tipcontent").text("已购买过包天请明天再买");
            $(".alert_w_").show();
            $(".alert_w").show();
            return false;
        }
    }


	// 生成订单号
	$.ajax({
		url : basePath + "/ea/assicode/sajax_ea_getJum.jspa",
		type : "get",
		aysnc : false,
		data : {
			sccid : sccid,
			"payBackupBill.sccid" : sccid,
			"payBackupBill.attach" : attach,
            "payBackupBill.coID":carmID,
            "payBackupBill.ppid":ppid,
            "payBackupBill.carNum":$(".zjcp").text()

		},
		success : function suc(data) {
			var mb = eval("(" + data + ")");
			ddid = mb.jum;
			zf();
		}
	});

}

function zf() {

	var money = $("#morre").val();
	var goodsname = "停车收费(" + companyName + ")";

	if (zffs == '1') {

		var par = new Array();
		par.push(basePath);
		par.push("page/ea/main/finance/BenDis/wfjAlipay.jsp?");
		par.push("WIDout_trade_no=" + ddid);
		par.push("&WIDtotal_fee=" + money);
		par.push("&WIDsubject=" + goodsname);
		par.push("&WIDbody=" + staffid + "," + sccid + "," + ppid + ",1,1,1,1,"
				+ attach);// 订单描述
		par.push("&WIDit_b_pay=''");// 超时时间
		par.push("&WIDextern_token=''");// 钱包
		_AP.pay(encodeURI(par.join("")));
	} else if (zffs == '2') {
		  var ua = navigator.userAgent.toLowerCase();
          var isWeixin = ua.indexOf('micromessenger') != -1;
          if (!isWeixin) {
        	  $(".alert_dh").show();
              //app微信支付
              $.ajax({
                  url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa?dat="+new Date(),
                  type:"get",
                  data:{
                      "payDto.orderId":ddid,
                      "payDto.totalFee":money,
                      "payDto.body":goodsname,
                      "payDto.attach":attach,
                       ppid:ppid,
                       staffid:staffid,
                       sccid:sccid,

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
                      $(".alert_dh").hide();
                      $(".payways_wrap").removeClass("show");
                      // $(".QRpay_inp").val("");
                      $(".overlay").removeClass("active");
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
                  },error:function (data) {
                      alert("生成预订单号失败");
                  }

              });

          }else{
              $(".loading").hide();
              $(".payways_wrap").removeClass("show");
              $(".overlay").removeClass("active");
              //$(".QRpay_inp").val("");
              window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsname+"-"+money+"-"+staffid+"-"+sccid+"-"+ppid+"-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

          }

	}

}
//关闭弹框返回
function back(){
	document.location.href = basePath
	+ "/ea/mappointment/ea_getAllCarNum.jspa?staffID="+staffid+"&carNum="+carNum+"&equip="+equip+"&status="+status;

}
function check() {

	var x = document.getElementById("cp").value;
	if (x == "") {
		$(".alert_w_").show();
		$(".alert_w p").text("请输入车牌号码");
		return false;
	}
    // if(!isVehicleNumber(x)){
    //     $(".alert_w_").show();
    //     $(".alert_w p").text("请输入正确的车牌号");
    //     return false;
    // }

	var url = basePath + "/ea/mappointment/sajax_ea_bindVehicle.jspa";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		data : {
			"carInformation.carNum" : x
			// "carInformation.engineNum" : $("#fdj").val()
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var member = eval("(" + data + ")");
			var result = member.result;
			if (result != "5") {
				document.location.href = basePath
						+ "/ea/mappointment/ea_parkingIndex.jspa?carNum="
						+ x+"&equip="+equip+"&status="+status;
			} else {
				var tipcontent = "";
				// if (result == "0") {
				// 	tipcontent = "不存在出入记录,请确认正确车牌";
				// } else if (result == "1") {
				// 	tipcontent = "只有进入记录没有出记录，请识别车牌";
				// } else if (result == "3") {
				// 	tipcontent = "没有进入记录";
				// } else if (result == "4") {
				// 	tipcontent = "记录有误差请联系工作人员";
				// }else

				if (result == "5") {
					tipcontent = "该车牌之前已绑定了不能再次绑定";
				}
				$(".tipcontent").text(tipcontent);

				$(".alert_w_").show();
				$(".alert_w").show();
			}
		}
	});

}
$(function() {
    if(newAdd=="new"){
    	$(".gbp img").show();
    }else{
    	$(".gbp img").hide();
    }
	$(".footer ul li").click(function() {
		$(this).addClass("active").siblings().removeClass("active");
		zffs = $(this).find("img").attr("name");
	});

	$(".alert_w input").click(function() {
		$(".alert_w_").hide();
	})
	var show = $('.alert_c_').css('display');
	if (carNum == null || carNum == "") {
		$('.alert_c_').css('display', 'block');
	} else {
		$('.alert_c_').css('display', 'none');
	}
	// 编辑
	$(".edit").click(
			function() {

				document.location.href = basePath
						+ "/ea/mappointment/ea_getAllCarNum.jspa?staffID="+staffid+"&carNum="+carNum+"&equip="+equip+"&status="+status;

			});

});

function getHours(time){
   //00时15分59秒
	var hindex = time.indexOf("时");
	var mindex = time.indexOf("分");
    var sindex = time.indexOf("秒");
    var h = Number(time.substring(0,hindex));
    var m = Number(time.substring(hindex+1,mindex)/60).toFixed(2);
    var s = Number(time.substring(mindex+1,sindex)/3600).toFixed(2);
    return h+Number(m)+Number(s);

}


function checkbday(carNumber,ppid){
    var bool = true;

    var url = basePath + "/ea/mappointment/sajax_ea_checkbday.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            carNumber:carNumber,
            ppid:ppid
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var result = jsonresult.result;
            if(result=="1"){
                bool = false;
            }

        }
    })
    return bool;
}

function isVehicleNumber(vehicleNumber) {
    var result = false;
    if (vehicleNumber.length == 7){

        var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
        result = express.test(vehicleNumber);
    }
    return result;
}
