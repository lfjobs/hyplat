$(function(){
     if(vipmoney!=""){
         $(".vipjg").show();
         $(".tm").css("text-decoration","line-through");
         totalMoney = vipmoney;
     }



    if(posNum==null||posNum==""){
        $(".address").hide();
    }else if(cardNum!=null&&cardNum!=""){

        $(".address").hide();
    }else if(fh=="2"){
        $(".address").show();

    }

        $("section").css("height",$(window).height()-$("header").height()+"px");
        /*勾中选择支付方式*/
        $(".mode ul li").click(function () {
            $(this).find(".gou img").show().parents("li").siblings().find(".gou img").hide();
            zffs = $(this).attr("class");
        });

       //积分金币
        jfAndJb();

        //选择收货地址
        $("#addr").click(function () {
            var backurls = window.location.href;
              backurls = backurls.replace("&code=","&codef=");
            if(backurls.indexOf("&staffAddress.addressID=")!=-1){
                backurls = backurls.substring(0,backurls.indexOf("&staffAddress.addressID="))

            }
            document.location.href = basePath+"ea/wfjshop/ea_getAddressList.jspa?intf=31&backurls="+encodeURIComponent(backurls);

        })



});
//点击确认支付
function zf() {

    if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){

        totalMoney = remainMoney;
    }
    if(checkRepeatPay()=="2"){
        alert("订单已支付请勿重复支付");
        return false;
    }
    if (t == 1) {
        return false;
    }
    if(posNum!=null&&posNum!="") {
        if (cardNum == null || cardNum == "") {
            if ((addressID == null || addressID == "")&&fh!="1") {
                alert("请选择收货地址");
                return false;
            }
        }
    }
    if (zffs == null) {
        alert("请选择支付方式");
        return false;
    } else {


        if (zffs == '1') {
            t = 1;
            var par = new Array();
            par.push(basePath);
            par.push("page/ea/main/marketing/supermarket/selfservice/wfjAlipay.jsp?");
            par.push("WIDout_trade_no=" + ddid);

            par.push("&WIDtotal_fee=" + totalMoney);

            par.push("&WIDsubject=("+companyName+")微分金超市消费");

            par.push("&WIDbody=selfpay");//订单描述
            par.push("&WIDit_b_pay=''");//超时时间
            par.push("&WIDextern_token=''");//钱包
            _AP.pay(encodeURI(par.join("")));
        } else if (zffs == '2') { //积分
            if ($(".jftip").text() == "积分不足") {
                alert("您的积分不足，请选择其他支付方式");
                return;
            }

            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(totalMoney);
            return;
        }else if (zffs == '4') { //金币
            if ($(".jbtip").text() == "金币不足") {
                alert("您的金币不足，请选择其他支付方式");
                return;
            }else if($(".jbtip").text() == "金币冻结"){
                alert("您的金币冻结，请选择其他支付方式");
                return;
            }
            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(totalMoney);
            return;

        } else if (zffs == '3') {
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {
                $(".alert_dh").show();
                //app微信支付
                $.ajax({
                    url: basePath + "ea/wfjshop/sajax_ea_appWechatPay.jspa",
                    type: "get",
                    data: {
                        "payDto.orderId": ddid,
                        "payDto.totalFee": totalMoney,
                        "payDto.body": "("+companyName+")微分金超市消费",
                        "payDto.attach": "selfpay"
                    },
                    success: function suc(data) {
                        $(".alert_dh").hide();
                        var mb = eval("(" + data + ")");
                        var appPackage = mb.appPackage;
                        //调用ios/android
                        var u = navigator.userAgent;
                        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                        var appid = appPackage.appid
                        var partnerid = appPackage.partnerid;
                        var prepayid = appPackage.prepayid;
                        var packages = appPackage.packages;
                        var noncestr = appPackage.noncestr;
                        var timestamp = appPackage.timestamp;
                        var err_code = appPackage.err_code;
                        var sign = appPackage.sign;


                        try {
                            if (isAndroid == true) {
                                Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, ddid, err_code, "微分金超市消费", "selfpay");
                            } else if (isiOS == true) {
                                var url = "func=" + 'ioscallappChat';
                                params = {
                                    'appid': appid,
                                    'partnerid': partnerid,
                                    'prepayid': prepayid,
                                    'noncestr': noncestr,
                                    'timestamp': timestamp,
                                    'sign': sign,
                                    'journalNum': ddid,
                                    'errcode': err_code,
                                    'goodsname': "微分金超市消费",
                                    'attach': "selfpay"
                                };
                                for (var i in params) {
                                    url = url + "&" + i + "=" + params[i];
                                }
                                window.webkit.messageHandlers.Native.postMessage(url);
                            }
                        }
                        catch (err) {
                            alert(err);
                            alert("微信支付升级中，请改用其他支付方式");
                            return;
                        }

                    }

                });
            }else{

                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-微分金超市消费-"+totalMoney+"----selfpay&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";


            }


            //return false;
        }
    }
}

function jfAndJb(){
    if(Number(jifen)<Number(totalMoney)*Number("100")){
        $(".jftip").text("积分不足");
    }
    if(jb=="dj"){
        $(".jbtip").text("金币冻结");
    }else {
        if (Number(jb) < Number(totalMoney) * Number("100")) {
            $(".jbtip").text("金币不足");
        }
    }
}
//验证是否支付过了
function checkRepeatPay(){
    var  result = "1";
    var ulp = basePath
        + "/ea/sm/sajax_ea_checkRepeatPay.jspa?";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        async:false,
        data: {
            journalNum:ddid
        },
        success: function (data) {
            var m = eval('(' + data + ')');
               result = m.result;



        },
        error: function (data) {

        }
    });

    return  result;

}
function jump() {
    document.location.href = basePath + "page/conacts/restaurant/paySuc.jsp";
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
                        if(zffs == "4"){
                            //金币支付
                            buy("07");


                        }else if(zffs == "2"){
                            //积分支付

                            buy("05");
                        }
                    }else if(paymentCode == '01'){//支付密码不正确
                        prompt("支付密码不正确");
                    }else{//没有支付密码
                        prompt("未设置交易密码即将跳转至设置交易密码");

                    }
                }
            });
        }
    } else {
        $(".pay_label li").removeClass();
    }

}

 //金币支付和积分支付
function buy(type){
//type 05 积分；07 金币
    if(type=="05") {

            t = 1;
            $(".alert_dh").show();
            var ulp = basePath
                + "/ea/wfjshop/sajax_ea_genSelfPayPoint.jspa?";
            $.ajax({
                type: "GET",
                url: ulp,
                dataType: "json",
                data: {
                    morre: totalMoney,
                    journalNum: ddid,
                    addressID: addressID,
                    wfStatus4: "05"
                },
                success: function (data) {
                    $(".alert_dh").hide();
                    var m = eval('(' + data + ')');
                    var result = m.result;
                    var bo = m.bo;

                    // if(bo==false){
                    //
                    //     alert("请勿重复支付");
                    //
                    //     return;
                    // }
                    if (result) {


                    } else {
                        console.log("积分支付出错");
                    }

                },
                error: function (data) {
                    $(".alert_dh").hide();
                    console.log("积分支付失败")
                }
            });
            setTimeout('jump()', 2000);


    }else{

            t = 1;
            $(".alert_dh").show();
            var ulp = basePath
                + "/ea/wfjshop/sajax_ea_genSelfPayPoint.jspa?";
            $.ajax({
                type: "GET",
                url: ulp,
                dataType: "json",

                data: {
                    morre: totalMoney,
                    journalNum: ddid,
                    addressID:addressID,
                    wfStatus4:"07"
                },
                success: function (data) {
                    $(".alert_dh").hide();
                    var m = eval('(' + data + ')');
                    var result = m.result;
                    var bo = m.bo;

                    // if(bo==false){
                    //
                    //     alert("该订单已支付请勿重复支付");
                    //
                    //     return;
                    // }
                    if (result) {



                    } else {
                        console.log("金币支付出错");
                    }

                },
                error: function (data) {
                    $(".alert_dh").hide();
                    console.log("金币支付失败")
                }
            });

            setTimeout('jump()',2000);



    }

}
