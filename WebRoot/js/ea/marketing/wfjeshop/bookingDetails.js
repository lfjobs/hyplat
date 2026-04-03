var timer1 = "";
var signstate = "";
$(document).ready(function(){

    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
        }
    }catch(error){
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;

        }
    }
	//切换支付方式
	 $(".wfj11_015_choice").click(function(){
                $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
                $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
                zffs = $(this).find(".second").find("img").attr("name");

	});

    paySuc();
    if(posNum==""||posNum==null) {
        initZF();
    }else{
        searchBookInfo(ddid);
        $(".barcode").focus();
        $(".barcode").blur(function(){
            $(".barcode").focus();
        });
    }

});

//点击确认支付
function zf() {


	if (zffs == null) {
		alert("请选择支付方式");
		return false;
	}else {

          var money = $(".xzf").text();
		if (zffs == '1') {			
			var par = new Array();
			par.push(basePath);

            par.push("page/WFJClient/ShopOwner/wfjAlipay.jsp?");

			par.push("WIDout_trade_no=" + ddid);
			
			par.push("&WIDtotal_fee="+ money);
		
			par.push("&WIDsubject=" + goodsname);

             par.push("&WIDbody=book");//订单描述


			par.push("&WIDit_b_pay=''");//超时时间
			par.push("&WIDextern_token=''");//钱包
        //    par.push("&buyIsOkPage="+buyIsOkPage);
			_AP.pay(encodeURI(par.join("")));
		} else if (zffs == '3') {

			var ua = navigator.userAgent.toLowerCase();
			var isWeixin = ua.indexOf('micromessenger') != -1;
            var attach = "book";
			if (!isWeixin) {
                //调用ios/android
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                $(".alert_dh").show();

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
                        $(".alert_dh").hide();
						var mb=eval("("+data+")");
						var appPackage=mb.appPackage;

			            var  appid = appPackage.appid
			            var partnerid = appPackage.partnerid;
			            var prepayid = appPackage.prepayid;
			            var packages = appPackage.packages;
			            var noncestr = appPackage.noncestr;
			            var timestamp = appPackage.timestamp;
			            var err_code = appPackage.err_code;
			            var sign = appPackage.sign;
                        try
                        {
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
                        catch(err)
                        {
                           alert("微信支付升级中，请改用其他支付方式");
							return;
                        }

					}
					
				});
				
			}else{
				window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsname+"-"+money+"-"+staffID+"-1-1-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
				
			}	
			
			//return false;
		}else if(zffs == "6"){
		    if(Number(money)*100>Number(jinbi)){
               alert("金币不足，请选用其它支付方式");
               return false;
            }

            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);


        }else if(zffs == "7"){
            if(Number(money)*100>Number(jifen)){
                alert("积分不足，请选用其它支付方式");
                return false;
            }
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(money);


        }
	}
}




function buy(formData,companyName,buyPatter) {
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_diaoyongJifen.jspa";
    $.ajax({
        type : "GET",
        url : ulp+formData,
        async : true,
        dataType : "json",
        data:{
            buyPatter:buyPatter,
            ddid:ddid,
            book:"book"
        },
        beforeSend : function() {
            $(".alert_dh").show();
        },
        success : function(data) {
            $(".alert_dh").hide();
            document.location.href = basePath + "page/conacts/restaurant/paySuc.jsp?jump=book";

        },
        error : function(data) {

        }
    });

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

                       initPay();
                        var formData = $("#formsutm").serialize();
                        formData = decodeURIComponent(formData,true);
                        var companyName="";
                        $(".wfj11_015_width>a").each(function(){
                            companyName+=$(this).text().trim()+",";
                        });
                        companyName=companyName.substr(0,companyName.length-1);

                        if(zffs == "6"){
                            //金币支付

                                buy(formData,companyName,"07");



                        }else if(zffs == "7"){
                            //积分支付

                          buy(formData,companyName,"05");

                        }
                    }else if(paymentCode == '01'){//支付密码不正确
                        prompt("支付密码不正确");
                    }else{//没有支付密码
                        prompt("未设置支付密码");
                    }
                }
            });
        }
    } else {
        $(".pay_label li").removeClass();
    }

}


var timer = "";
function  initZF() {
    if(sccId ==jgzsccid) {
        if ($("#state").text() == "00"||$("#state").text() == "01" || $("#state").text() == "04") {
            searchBookInfo(ddid);
            timer = setTimeout(function () {
                searchBookInfo(ddid);
            }, 5000);
        }
    }
}

function  searchBookInfo(ddid){
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

    var totalMoney = $("#morre").val();
    var ulp = basePath
        + "/ea/mappointment/sajax_ea_searchBookInfo.jspa?";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        data: {
            "bookingInformation.journalNum":ddid
        },
        success: function (data) {
         var  me = eval("("+data+")");
         var state = me.state;
         var howMuchTime = me.howMuchTime;
         var money = me.money;
         if(state=="04"){
             if(dp=="1"){
                 $(".btn").show();
                 $(".code").addClass("code-pad");

                 if(sm=="1") {
                     try {
                         if (isAndroid == true) {

                             Android.speechOutputForAndroid("签退成功练车结束请立即支付");
                             prompt("签退成功请立即支付");
                             timer1 = setInterval(searchResult,2000);
                         } else {
                             console.log("请在安卓设备访问！");
                         }

                     } catch (error) {

                     }
                 }

             }else {
                 if(sccId ==jgzsccid) {
                     $(".wfj11_015").show();
                     $("#htime").text("用时：" + howMuchTime);
                     $(".xzf").text(money);
                         timer = setTimeout(function () {
                             searchBookInfo(ddid);
                         }, 5000);

                 }
             }
         }
            if(state=="02"||state=="00"||state=="01"){
             signstate = state;
                if(dp=="1"&&(paysuc=="suc"||search!="")){
                    $(".btn").show();
                    $(".btn button").text("打印小票");
                    $(".code").addClass("code-pad");
                }
                      if (state == "02" && dp == "1" && dz != "1") {
                          $(".btn").show();
                          $(".btn button").text("打印小票");
                          $(".code").addClass("code-pad");
                      }
                        if(dp!="1"&&sccId ==jgzsccid) {
                                var s = $("#state").text();
                                if(s!=state) {
                                    window.location.reload();
                                }
                                    if (state == "02") {
                                        window.clearTimeout(timer);
                                    }else{
                                        timer = setTimeout(function () {
                                            searchBookInfo(ddid);
                                        }, 5000);
                                    }

                         }

                if(dp=="1"){
                    setInterval(function(){
                        document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

                    }, 10000);
                }
            }
            if(state=="04"){

                if((dp=="1"&&search!="")||(dp=="1"&&sm=="1")){
                    $(".btn").show();
                    $(".code").addClass("code-pad");

                }else{
                    $(".btn").hide();
                    $(".code").removeClass("code-pad");
                }
            }
            if(state=="01"){

                if(dp=="1"&&sm=="1"){
                    prompt("签到成功开始去练车");
                    try {
                        if (isAndroid == true) {

                            Android.speechOutputForAndroid("签到成功开始去练车");

                        } else {
                            console.log("请在安卓设备访问！");
                        }

                    }catch(error){

                    }

                    setInterval(function(){
                        document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

                    }, 5000);

                }
            }



        },
        error: function (data) {


        }
    });
}

//终端机去支付
function topay(money){
    var bu = $(".btn button").text();
    if(bu=="打印小票"){
        printTicket();

    }else {
        document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=" + ddid + "&totalMoney=" + money + "&totalNum=1&comID=" + companyID+"&posNum="+posNum+"&fh=1";
    }

}

//打印小票
function printTicket(){
    var url = basePath + "/ea/mappointment/sajax_ea_printInfo.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        dataType: "json",
        data: {
            journalNum: ddid
        },
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var obj = jsonresult.result;
            var array = {};
            array["companyname"] = obj[10];
            array["journalNum"] = obj[0];
            array["date"] = obj[3];
            array["stuname"] = obj[1];
            array["stuacc"] = obj[2];
            array["coachname"] = obj[7];
            array["directname"] = obj[6];
            array["watchacc"] = obj[8];
            array["watchname"] = obj[9];
            array["placeNum"] = obj[4];
            array["placeName"] = obj[5];

            array["ercode"] = "99"+obj[0];

            array["checkintime"] = obj[12];
            array["signbacktime"] = obj[13];
            array["howMuchTime"] = obj[14];
            array["money"] = obj[15];
            if(signstate=="00") {
                array["signInState"] = "未使用";
            }else if(signstate=="01") {
                array["signInState"] = "已签到";
            }else{
                array["signInState"] = "已签退";
            }



            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

            try {
                if (isAndroid == true) {
                    Android.printTicketPre(JSON.stringify(array));
                    Android.speechOutputForAndroid("小票正在打印请稍后");
                } else {
                    console.log("请在安卓设备访问！");
                }

            }catch(error){

            }
            setInterval(function(){
                document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

            }, 4000);

        }, error: function (data) {

        }
    });
}

//判断是否是终端机器
function isExistPosNum(posNum){
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:false,
        data : {
            posNum:posNum
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if(result!="0"){
                posNum = "";
            }

        },
        error : function(data) {
            // alert("验证失败");
        }

    });
    return posNum;
}

function  searchOrder(code){
    if(code.indexOf("book")!=-1){
        document.location.href = code;
        return false;
    }
    var url = basePath+"/ea/restaurant/sajaxj_ea_scancode.jspa?scancode=04"+code;
    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            "pos":"pos",
        },
        dataType : "json",
        async : false,
        success : function(data) {

            var bl = data.bl;
            var os = data.os;
            var cbId = os[15];
            var xysccid = os[16];
            document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cbId+"&sccId="+xysccid+"&dp=1&sm=1";
        }
    })
}

       function onFocus () {
           var target = event.target
           setTimeout(function () {
               target.readOnly = false
           },0)
       }
       function onBlur() {
           event.target.readOnly = true
       }
document.onkeydown = function(evt) {//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        var barcode = $(".barcode").val();
        $(".barcode").val("");

        var  kt = barcode.substring(0,2);
        if (barcode.indexOf("bif")!=-1||barcode.indexOf("book")!=-1||kt=="99") {
            if(kt=="99"){
                if(barcode.length!=21){
                    return false;
                }
                barcode =  barcode.substring(2);
            }
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            try {
                if (isAndroid == true) {

                    Android.speechOutputForAndroid("扫码成功请稍后");
                } else {
                    console.log("请在安卓设备访问！");
                }

            }catch(error){

            }
            searchOrder(barcode);
            return false;
        }
    }
}

function paySuc(){
    if(paysuc=="suc"&&state=="04"){
        $(".div-ts").show();
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        try {
            if (isAndroid == true) {

                Android.speechOutputForAndroid("正在处理请稍后");
            } else {
                console.log("请在安卓设备访问！");
            }

        }catch(error){

        }
        timer1 = setInterval(searchResult,2000);
    }
}


/**
 *
 * 定时查询支付结果
 */
function searchResult(){
    var ulp = basePath
        + "/ea/mappointment/sajax_ea_searchBookInfo.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            "bookingInformation.journalNum":ddid
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var state = member.state;
            if(state=="02"){
                       //支付成功
                window.clearTimeout(timer);
                window.location.reload();

                var u = window.navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                try {
                    if (isAndroid == true) {

                        Android.speechOutputForAndroid("支付成功请打印小票");
                    } else {
                        console.log("请在安卓设备访问！");
                    }

                }catch(error){

                }
            }

        },
        error : function(data) {
            console.log("查询支付结果失败");
        }
    });


}