$(function () {
    $(".wfj11_015_need").attr("style", "height:" + $(window).height() * 0.06 + "px;line-height:" + $(window).height() * 0.06 + "px;");
    $(".wfj11_015_need").find("li").attr("style", "font-size:" + $(window).height() * 0.03 + "px;color:#000;");
    $(".wfj11_015_need").find("li").find("span").attr("style", "font-size:" + $(window).height() * 0.03 + "px;color:#F74C31;");
    $(".wfj11_015_allbay").find("td").attr("style", "font-size:" + $(window).height() * 0.025 + "px;height:" + $(window).height() * 0.09 + "px;line-height:" + $(window).height() * 0.06 + "px;");
    $(".wfj11_015_allbay").find("td").eq(0).css("height", $(window).height() * 0.05 + "px")
    $(".wfj11_015_allbay").find("tr").eq(5).css("height", $(window).height() * 0.1 + "px")
    $(".wfj11_015_allbay").find("td").find("img").attr("style", "height:" + $(window).height() * 0.03 + "px;width:auto");
    $(".wfj11_015_allbay").find("td").find(".all_pay").attr("style", "height:" + $(window).height() * 0.04 + "px;");
    $(".wfj11_015_allbay").find("td").find("#paycommit").attr("style", " width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:" + $(window).height() * 0.006 + "px; height:" + $(window).height() * 0.06 + "px; line-height:" + $(window).height() * 0.06 + "px;font-size:" + $(window).height() * 0.025 + "px;");

    //弹出层初始化
    $(".jqmWindow").jqm({
        modal: true,
        overlay: 20
    }).jqmAddClose('.close');

    $(".jqmWindow").css("height", $(window).height() + "px");

    /*选择打赏金额*/
    $(".reward .sj li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    });

    /*选择填写打赏金额*/
    $(".reward .sj input").click(function () {
        $(this).siblings().removeClass("active");
    });

    $("#mnvl").blur(function () {
        if ($(this).val() != null && $(this).val() != "") {
            var reg = /^\d+(\.\d{1,3})?$/;
            if (reg.test($(this).val())) {
                if ($(this).val() < '1') {
                    BulletBox("请输入大于一元的正确数字的金额");
                    $(this).val("");
                }
            } else {
                BulletBox("请输入大于一元的正确数字的金额");
                $(this).val("");
            }
        }
    });

    /*显示支付方式选择页面*/
    $("#ds").click(function () {
        mval = $(".active").text();
        if (mval == null || mval == "") {
            mval = $("#mnvl").val();
        } else {
            mval = mval.replace("元", "");
        }
        if (mval == null || mval == "") {
            BulletBox("请选择金额");
            return false;
        } else {
            $(".xzf").text(mval);
        }

        $.ajax({
            url : encodeURI(basePath+"ea/industry/sajax_ea_addReward.jspa"),
            type : "post",
            async : false,
            dataType : "json",
            data : {
                "sccid" : sccid,
                "ppid": ppid,
                "money":mval
            },
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var status = member.stuts;
                if(status){
                    journalNum= member.ordernum;
                    $("#occlusion2").css("z-index", $(".wfj11_015").css("z-index") + 1);
                    $("#occlusion2").jqmShow();
                    $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                    $(".wfj11_015_buy_commit").fadeIn(1000);
                }else{
                    BulletBox("获取状态失败！");
                }
            },error : function cbf(data) {
                BulletBox("获取状态失败！");
            }
        });
    });

    /*隐藏支付方式选择页面*/
    $(".jqmOverlay").live("click", function () {
        $("#occlusion2").jqmHide();
        $(".wfj11_015_buy_commit").fadeOut();
        panduan = "00";
    });

    $(".alert-ds_").live("click", function(){
        $("#occlusion2").jqmHide();
        $(this).fadeOut();
        $("#alertText").text("");
        clearTimeout(t);
    });

    //切换支付方式
    $(".wfj11_015_choice").click(function () {
        $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
        $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
        zffs = $(this).find(".second").find("img").attr("name");
    });

});

function BulletBox(text){
    $("#alertText").text(text);
    $("#occlusion2").css("z-index", $(".wfj11_015").css("z-index") + 1);
    $("#occlusion2").jqmShow();
    $(".alert-ds_").css("z-index", $("#occlusion2").css("z-index") + 1);
    $(".alert-ds_").fadeIn(1000);
    t=setTimeout(closeBox,7000);
}

function closeBox(){
    $("#occlusion2").jqmHide();
    $(".alert-ds_").fadeOut();
    $("#alertText").text("");
}

//点击确认支付
function zf() {
    if (zffs == null) {
        alert("请选择支付方式");
        return false;
    } else {
        if (zffs == '1') {
            var par = new Array();
            par.push(basePath);
            par.push("page/WFJClient/CustomerOrder/wfjAlipay.jsp?");
            par.push("WIDout_trade_no=" + journalNum); //商户订单号
            par.push("&WIDtotal_fee=" + mval);//打赏金额
            par.push("&WIDsubject=打赏给"+sname);//产品名称
            par.push("&WIDbody=reward");//订单描述
            par.push("&WIDflag=ShopOwner");//类型
            _AP.pay(encodeURI(par.join("")));
        } else if (zffs == '3') {
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {
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
                $(".loading").show();
                //app微信支付
                $.ajax({
                    url: basePath + "ea/wfjshop/sajax_ea_appWechatPay.jspa",
                    type: "get",
                    data: {
                        "payDto.orderId": journalNum,
                        "payDto.totalFee": mval,
                        "payDto.body": "打赏给"+sname,
                        "payDto.attach": "reward",
                        elkc: elkc
                    },
                    success: function suc(data) {
                        var mb = eval("(" + data + ")");
                        var appPackage = mb.appPackage;
                        var appid = appPackage.appid;
                        var partnerid = appPackage.partnerid;
                        var prepayid = appPackage.prepayid;
                        var packages = appPackage.packages;
                        var noncestr = appPackage.noncestr;
                        var timestamp = appPackage.timestamp;
                        var err_code = appPackage.err_code;
                        var sign = appPackage.sign;
                        $(".loading").hide();
                        try {
                            if (isAndroid == true) {
                                Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, journalNum, err_code, "打赏给"+sname, "reward");
                            } else if (isiOS == true) {
                                var url = "func=" + 'ioscallappChat';
                                params = {
                                    'appid': appid,
                                    'partnerid': partnerid,
                                    'prepayid': prepayid,
                                    'noncestr': noncestr,
                                    'timestamp': timestamp,
                                    'sign': sign,
                                    'journalNum': journalNum,
                                    'errcode': err_code,
                                    'goodsname': "打赏给"+sname,
                                    'attach': "reward"
                                };
                                for (var i in params) {
                                    url = url + "&" + i + "=" + params[i];
                                }
                                window.webkit.messageHandlers.Native.postMessage(url);
                            }
                        }
                        catch (err) {
                            alert("微信支付升级中，请改用其他支付方式");
                            return;
                        }
                    }
                });
            }else {
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params=" + journalNum + "-打赏给"+sname+"-" + mval + "-" + staffID + "-1-1-reward&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
            }
        }
    }
}
