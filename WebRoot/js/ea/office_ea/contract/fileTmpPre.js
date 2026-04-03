var ddid = "";
var zffs = "3";
var t = 0;
$(document).ready(function () {


    $(".sec-zf").click(function () {
        $(".div-zffs").show();
        $('body,html').addClass('add');

    })
    $("#zffs").click(function () {
        $(".div-zffs").show();
        $('body,html').addClass('add');
    })
    $("#inp-close").click(function () {
        $('body,html').removeClass('add');
        $(this).parents(".div-zffs").hide();
    })
        $("#inp-yes").click(function(){
        if($(".div-zffs .box ul li span.active").parents("li.clearfix").find("p").text()!=""){
        $("#zffs .p-nr").text($(".div-zffs .box ul li span.active").parents("li.clearfix").find("p").text()).addClass("p-color");

            //生成订单号
            $.ajax({
                url: basePath + "/ea/assicode/sajax_ea_getJum.jspa",
                type: "get",
                aysnc: false,
                data: {
                    sccid: sccid,
                    "payBackupBill.ppid": ppid,
                    "payBackupBill.sccid": sccid,
                    "payBackupBill.coID":docId

                },
                success: function suc(data) {
                    var mb = eval("(" + data + ")");
                    ddid = mb.jum;
                    zf();
                }
            });

      }
    })
    //支付方式切换
    $(".input-male").click(function () {
        $(this).siblings("span").addClass("active");
        $(this).parents("li.clearfix").siblings("li").find("span").removeClass("active");


        zffs = $(this).attr("data-index");
    });

})

//点击确认支付
function zf() {
    if (checkRepeatPay() == "2") {
        alert("订单已支付请勿重复支付");
        return false;
    }
    if (t == 1) {
        return false;
    }
    if (zffs == null) {
        alert("请选择支付方式");
        return false;
    } else {

      var attach = "qyht";
        if (zffs == '1') {
            t = 1;


            var par = new Array();
            par.push(basePath);
            par.push("page/ea/main/finance/BenDis/wfjAlipay.jsp?");
            par.push("WIDout_trade_no=" + ddid);
            par.push("&WIDtotal_fee=" + totalMoney);
            par.push("&WIDsubject=(" + companyName + ")"+goodsName);

            par.push("&WIDbody=" + staffID + "," + sccid + "," + ppid + ",1,1,1,1,"
                + attach);// 订单描述
            par.push("&WIDit_b_pay=''");// 超时时间
            par.push("&WIDextern_token=''");// 钱包
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
        } else if (zffs == '4') { //金币
            if ($(".jbtip").text() == "金币不足") {
                alert("您的金币不足，请选择其他支付方式");
                return;
            } else if ($(".jbtip").text() == "金币冻结") {
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
                        "payDto.body": "(" + companyName + ")"+goodsName,
                        "payDto.attach": attach
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
                                Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, ddid, err_code, "(" + companyName + ")"+goodsName, attach);
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
                                    'goodsname': "(" + companyName + ")"+goodsName,
                                    'attach':attach
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
            } else {

                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params=" + ddid + "-(" + companyName + ")"+goodsName+"-"+ totalMoney + "----"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";


            }


            //return false;
        }
    }
}

//验证是否支付过了
function checkRepeatPay() {
    var result = "1";
    var ulp = basePath
        + "/ea/sm/sajax_ea_checkRepeatPay.jspa?";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        async: false,
        data: {
            journalNum: ddid
        },
        success: function (data) {
            var m = eval('(' + data + ')');
            result = m.result;


        },
        error: function (data) {

        }
    });

    return result;

}
