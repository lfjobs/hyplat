$(function () {
    //弹出层
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.50 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.026 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");
    var userinfo = userInfo();
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    object.push("khd=", khd);
    object.push("&flag=", flag);
    object.push("&identifying=", identifying);
    bz();
    if (userinfo.withDraway == "01" && userinfo.userId != null) {
        $(".sele_img").attr("src", basePath + "images/ea/finance/Gold_Pool/zfb.png");
        var html = "<input id='userId' type='hidden' value='" + userinfo.userId + "'/>" +
            "<p>支付宝账户</p><p class='nikeName'>" + userinfo.nickName + "</p>";
        $(".sele_t").html(html);
    } else if (userinfo.withDraway == "02") {
        $(".sele_img").attr("src", basePath + "images/ea/finance/Gold_Pool/weixin.png");
        var html = "<input id='openID' type='hidden' value='" + (isWeixin != true ? userinfo.appOpenId : userinfo.openId) + "'/>" +
            "<p>微信账户</p><p class='nikeName'>" + userinfo.weNickName + "</p>";
        $(".sele_t").html(html);
    } else if (userinfo.withDraway == "03" && bs == 0) {
        bankCard();//判断是否有银行卡
    } else {
        bs = 1;

        $(".sele_t").css({
            "text-align": "center",
            // "width": "95%",
            // "height": "1rem"
            "padding-top":"1.3rem"
        });
        $(".sele_img").hide();
        $(".sele_t").text("请完善绑定信息！");
        $(".sele_t").css("color", "#ff5400");
        $(".gold_inp").attr("readonly", true);//让输入框只读样式
        $("#exchange").unbind("click")//去除点击事件
        $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
    }
    //判断是否绑定
    if (bs == 0) {
        goldNum();//判断是否满足可兑换金币数
    }
    if (bs == 0) {
        charge();//判断用户金币是否被冻结
    }
    //判断是否一个小时后
    if (rdate != null && bs == 0) {
        SysSecond = parseInt((3600000 - (new Date().getTime() - new Date(rdate).getTime())) / 1000); //这里获取倒计时的起始时间
        if (Math.floor((SysSecond / 3600) % 24) == 0) {
            bs = 1;
            $(".gold_inp").attr("readonly", true);//让输入框只读样式
            $("#exchange").unbind("click")//去除点击事件
            $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
            InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
        }
    }

    //点击密码框触发输入框
    $(".pay_label").click(function () {
        $(".pay_inp").focus();
    });

    //输入密码
    $(".pay_inp").on("input", function () {
        if (jum != null && jum != "") {
            var len = $(this).val().length - 1;
            if (len >= 0) {
                $(".pay_label li").eq(len).addClass("pass").nextAll().removeClass();
                if (len == 5) {
                    setTimeout("initPay()", 400);//设置延时（验证密码方法放这位置）
                    $(".pay_inp").blur();
                    var code = $(".pay_inp").val(); //获取密码
                    var bankId = $("#sele_id").val();//获取银行id
                    //查找用户的支付密码
                    var url = basePath + "/ea/jinbi/sajax_toSearchPaymentCode.jspa";
                    $.ajax({
                        url: url,
                        type: "get",
                        async: false,
                        dataType: "json",
                        data: {
                            "paymentCode": code,
                            "user": user,
                            "identifying": identifying
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var paymentCode = member.code;
                            if (paymentCode == '00') {//支付密码正确
                                bs = 1;
                                $(".overlay").removeClass("active");
                                $(".popup_pay").hide();
                                //initPay();
                                $(".overlay_text").show();
                                SysSecond = parseInt(3600); //这里获取倒计时的起始时间
                                InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
                                $(".gold_inp").val("");
                                $(".gold_inp").attr("readonly", true);//让输入框只读样式
                                $("#exchange").unbind("click")//去除点击事件
                                $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色


                                if(!$(".chukuan_01").is(":hidden")) {
                                    if (userinfo.withDraway == "01") {
                                        //支付宝支付
                                        $.ajax({
                                            url: basePath + "/ea/jinbi/sajax_aliWithdRawal.jspa",
                                            type: "post",
                                            async: true,
                                            dataType: "json",
                                            data: {
                                                "staffid": staffid,
                                                "user": user,
                                                "userId": $("#userId").val(),
                                                "sccid": sccid,
                                                "identifying": identifying,
                                                "morre": $(".sum_num").text(),
                                                "jNumOrder": jum
                                            },
                                            success: function (data) {
                                                var member = eval("(" + data + ")");
                                                var str = member.str;
                                                $(".overlay_text").hide();
                                                if (str == "提交成功") {
                                                    alert("转出成功,金额将在五个工作日内打到您的账户,请耐心等待");
                                                    open(basePath + "/ea/jinbi/ea_gethyjifen.jspa?" + object.join("") + "&user=" + user + "&sccid=" + sccid, "_self");
                                                }
                                                alert(str);
                                            }, error: function (data) {
                                                frozenAccount(staffid);
                                                prompt("提现失败");
                                            }
                                        })
                                    } else if (userinfo.withDraway == "02") {
                                        //微信支付
                                        $.ajax({
                                            url: basePath + "/ea/jinbi/sajax_weWithdRawal.jspa",
                                            type: "post",
                                            async: true,
                                            dataType: "json",
                                            data: {
                                                "staffid": staffid,
                                                "user": user,
                                                "openID": $("#openID").val(),
                                                "sccid": sccid,
                                                "identifying": identifying,
                                                "morre": $(".sum_num").text(),
                                                "isWeChat": isWeixin,
                                                "jNumOrder": jum
                                            },
                                            success: function (data) {
                                                var member = eval("(" + data + ")");
                                                var str = member.str;
                                                $(".overlay_text").hide();
                                                if (str == "提交成功") {
                                                    alert("转出成功,金额将在五个工作日内打到您的账户,请耐心等待");
                                                    open(basePath + "/ea/jinbi/ea_gethyjifen.jspa?" + object.join("") + "&user=" + user + "&sccid=" + sccid, "_self");
                                                }
                                                alert(str);
                                            }, error: function (data) {
                                                $(".overlay_text").hide();
                                                frozenAccount(staffid);
                                                prompt("提现失败");
                                            }
                                        })
                                    } else if (userinfo.withDraway == "03") {
                                        var url = basePath + "/ea/jinbi/sajax_withdRawalByBank.jspa";
                                        $.ajax({
                                            url: url,
                                            type: "get",
                                            async: true,
                                            dataType: "json",
                                            data: {
                                                "staffid": staffid,
                                                "user": user,
                                                "sccid": sccid,
                                                "identifying": identifying,
                                                "morre": $(".sum_num").text(),
                                                "bankId": bankId,
                                                "isWeChat": isWeixin,
                                                "jNumOrder": jum
                                            },
                                            success: function (data) {
                                                var member = eval("(" + data + ")");
                                                var str = member.str;
                                                $(".overlay_text").hide();
                                                if (str == "提交成功") {
                                                    alert("转出成功,金额将在五个工作日内打到您的账户,请耐心等待");
                                                    open(basePath + "/ea/jinbi/ea_gethyjifen.jspa?" + object.join("") + "&user=" + user + "&sccid=" + sccid, "_self");
                                                }
                                                alert(str);
                                            },
                                            error: function (data) {
                                                $(".overlay_text").hide();
                                                frozenAccount(staffid);
                                                prompt("获取数据失败");
                                            }
                                        });
                                    } else {
                                        alert("请选择提现方式！");
                                    }


                                }else{
                                    var url = basePath + "/ea/jinbi/sajax_withdrawbywechatsh.jspa";
                                    $.ajax({
                                        url: url,
                                        type: "get",
                                        async: true,
                                        dataType: "json",
                                        data: {
                                            "sccid": sccid,
                                            "morre": $(".sum_num").text(),
                                            "jNumOrder": jum,
                                            companyID:companyID
                                        },
                                        success: function (data) {
                                            var member = eval("(" + data + ")");
                                            var str = member.str;
                                            $(".overlay_text").hide();
                                            if (str == "CREATE_SUCCESS") {
                                                alert("受理成功,金额将在3个工作日内打到您的账户,请耐心等待");
                                                open(basePath + "/ea/jinbi/ea_gethyjifen.jspa?" + object.join("") + "&user=" + user + "&sccid=" + sccid, "_self");

                                            }
                                        },
                                        error: function (data) {
                                            $(".overlay_text").hide();

                                            prompt("获取数据失败");
                                        }
                                    })

                                }

                            } else if (paymentCode == '01') {//支付密码不正确
                                prompt("支付密码不正确");
                            } else {//没有支付密码
                                prompt("没有支付密码");
                            }
                        }
                    });
                }
            } else {
                $(".pay_label li").removeClass();
            }
        } else {
            prompt("页面错误！");
        }
    });

    //输入密码弹窗关闭
    $(".close_btn").click(function () {
        $(".overlay").removeClass("active");
        $(".popup_pay").hide();
        initPay();
    });


    //点击立即兑换（可加判断）
    $("#exchange").click(function () {
        var coin =$(".gold_inp").val();//获得输入的金币数

        if (coin.length == 0) {
            prompt("请输入转出金额");
        } else if (!(/^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/.test(coin))) {
            prompt("转出金额为正数最多两位小数！");
        } else {

            if(!$(".chukuan_01").is(":hidden")) {
                if (coin * 100 >= 10000 && coin * 100 <= avagold) {
                    var a = $("input#branchName").val();
                    if (userinfo.withDraway == "03" && (a == null || a.length == 0)) {
                        prompt("银行卡没有分行不能兑换");
                    } else {
                        $(".overlay").addClass("active");
                        $(".popup_pay").show();
                        $(".pay_inp").focus();
                        $(".sum_num").text($(".gold_inp").val());
                    }
                }
            }else{
                if (coin * 100 >0 && coin* 100  <= available_amount) {
                        $(".overlay").addClass("active");
                        $(".popup_pay").show();
                        $(".pay_inp").focus();
                        $(".sum_num").text($(".gold_inp").val());

                }

            }
        }

    });


    $(".flip p").css("font-size", +$(window).height() * 0.03 + "px");
    $(".modal_TS h3").css("font-size", +$(window).height() * 0.04 + "px");
    $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色

    //跳转银行卡列表   clearfix
    $("#clearfix").click(function () {
        var bank = $(".sele_t").text();
        var bankId = $("#sele_id").val();
        window.location.href = basePath + "ea/perinfor/ea_getBankCardInformation.jspa?" + object.join("") + "&staffId="
            + staffid + "&sccid=" + sccid + "&bankId=" + bankId + "&user=" + user + "&editType=00&mark=00";
        // if(bank == '请完善银行卡信息！'){
        //
        //  if(flag == 'sys'){
        // 	 window.location.href = basePath+"ea/industry/ea_getaddBankCardInformation.jspa?"+object.join("")+"&staffid="
        // 	 + staffid +"&sccid="+sccid+"&ccompanyId="+ccompanyId+"&user="+user+"&editType=00&mark=00";
        //  }else{
        // 		 window.location.href = basePath+ "ea/perinfor/ea_getaddBankCardInformation.jspa?"+object.join("")+"&staffId="
        // 			+ staffid +"&sccid="+sccid+"&user="+user+"&editType=00&mark=00";
        //  }
        // }else{
        //  if(flag == 'sys'){
        // 	 window.location.href = basePath+ "ea/industry/ea_getBankCardsList.jspa?"+object.join("")+"&staffid="
        // 		+ staffid +"&sccid="+sccid+"&user="+user+"&editType=00&mark=00";
        //  }else{
        // 	 window.location.href = basePath+ "ea/perinfor/ea_getBankCardInformation.jspa?"+object.join("")+"&staffId="
        // 		+ staffid +"&sccid="+sccid+"&bankId="+bankId+"&user="+user+"&editType=00&mark=00";
        //  }
        //
        // }
    });

    //判断输入的金币数
    $(".gold_inp").on('input', function () {
        var gold = $(".gold_inp").val();
        var reg = /^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/; //两位小数正数
        var r = gold * 100;
        //r = r.toFixed(2);
        $("#exchange").val("立即转出（" + gold + " 元）");
        if(gold!=""&&gold!=null) {
            if (reg.test(gold) == true) {


                if(!$(".chukuan_01").is(":hidden")) {
                    if (r >= 10000 && r <= avagold) {
                        $("#exchange").val("立即转出（" + gold + " 元）");
                        $("#exchange").css({"background": "#e5b569"});
                    } else {
                        if (r > avagold) {
                            $("#exchange").val("糟糕，余额不足啦！");
                        }
                        $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
                    }
                }else{
                    if (r >0 && r <= available_amount) {
                        $("#exchange").val("立即转出（" + gold + " 元）");
                        $("#exchange").css({"background": "#e5b569"});
                    } else {
                        if (r > available_amount) {
                            $("#exchange").val("糟糕，余额不足啦！");
                        }
                        $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
                    }

                }
            } else {
                prompt("输入格式错误 请重新输入");
                $(".gold_inp").val(null);
                $("#exchange").val("确认");
                $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
            }
        }else{
            $("#exchange").val("确认");
            $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
        }
    });

});

//倒计时 将时间减去1秒，计算天、时、分、秒
function SetRemainTime() {
    if (SysSecond > 0) {
        SysSecond = SysSecond - 1;
        var second = Math.floor(SysSecond % 60);            // 计算秒
        var minite = Math.floor((SysSecond / 60) % 60);      //计算分
        var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时
        var day = Math.floor((SysSecond / 3600) / 24);       //计算天

        var hourDiv = "<span id='hourSpan'>" + hour + "小时" + "</span>";
        var dayDiv = "<span id='daySpan'>" + day + "天" + "</span>";

        $("#exchange").html(dayDiv + hourDiv + minite + "分" + second + "秒后可再次提现");

        if (hour === 0) {//当不足1小时时隐藏小时
            $('#hourSpan').css('display', 'none');
        }
        if (day === 0) {//当不足1天时隐藏天
            $('#daySpan').css('display', 'none');
        }
        $("#exchange").attr("value",(day==0?"": (day+ "天"))+(hour==0?"": (hour+ "小时"))+ minite + "分" + second + "秒后可再次提现");

    } else {//剩余时间小于或等于0的时候，就停止间隔函数
        window.clearInterval(InterValObj);
        //这里可以添加倒计时时间为0后需要执行的事件
        $("#exchange").val("确认");
        $(".gold_inp").attr("readonly", false);//让输入框只读样式
        alert("时间为0");
    }
}

//判断用户金币是否被冻结
function charge() {
    var uul = basePath + "/ea/jinbi/sajax_getUserPurview.jspa?user=" + user;
    $.ajax({
        url: uul,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var status = member.purview;
            if (status != true) {//说明该用户金币被冻结
                bs = 1;
                if(user=="15884243151"){
                  //  $(".hint_con").text("公司审核提现中，金币账户被冻结，不能进行金币兑现！");
                    $("#exchange").attr("value","公司审核提现中，金币账户被冻结，不能进行金币兑现！")
                }else {
                 //   $(".hint_con").text("金币账户被冻结，不能进行金币兑现！");
                    $("#exchange").attr("value","金币账户被冻结，不能进行金币兑现！")
                }
            //    $(".hint_con").css("color", "#ff5400");
                $(".gold_inp").attr("readonly", true);//让输入框只读样式
                $("#exchange").unbind("click")//去除点击事件
                $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色


            }
        }
    });


}


//判断是否满足可兑换金币数
function goldNum() {
    if (avagold < 10000) {
        bs = 1;
        $(".con").css({"color": "#ff5400"});
        $(".gold_inp").attr("readonly", true);//让输入框只读样式
        $("#exchange").unbind("click")//去除点击事件
        if (jifenscore > 0) {
            prompt("金币可用于购物！")
        }
    }
}

function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 4000);
}


//判断是否有银行卡
function bankCard() {

    var url;
    if (mark == '00') {
        url = basePath + "/ea/jinbi/sajax_choiceBank.jspa?" + object.join("") + "&bankId=" + bankId + "&staffid=" + staffid + "&sccid=" + sccid + "&ccompanyId=" + ccompanyId;
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var bank = member.bank;

                if (bank == null) {
                    bs = 1;
                    $(".sele_t").css({
                        "text-align": "center",
                        // "width": "95%",
                        // "height": "1rem"
                        "padding-top":"1.3rem"
                    });
                    $(".sele_img").hide();
                    $(".sele_t").text("请完善绑定信息！");
                    $(".sele_t").css("color", "#ff5400");
                    $(".gold_inp").attr("readonly", true);//让输入框只读样式
                    $("#exchange").unbind("click")//去除点击事件
                    $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
                } else {
                    $(".sele_img").attr("src",basePath+getBankLogo(bank[0]));


                    $(".sele_name").text(bank[0]);
                    var ss1 = bank[1].substring(5, 11);
                    $(".sele_num").text(bank[1].replace(ss1, "********"));
                    $("input#branchName").val(bank[3]);
                    $("#sele_id").val(bank[2]);
                }

            }
        });

    } else {
        url = basePath + "/ea/jinbi/sajax_getShowBank.jspa?" + object.join("") + "&sccid=" + sccid + "&staffid=" + staffid + "&ccompanyId=" + ccompanyId;
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var bank = member.bank;
                if (bank == null) {
                    bs = 1;
                    $(".sele_t").css({
                        "text-align": "center",
                        "width": "95%",
                        "height": "1rem"
                    });
                    $(".sele_img").hide();
                    $(".sele_t").text("请完善绑定信息！");
                    $(".sele_t").css("color", "#ff5400");
                    $(".gold_inp").attr("readonly", true);//让输入框只读样式
                    $("#exchange").unbind("click")//去除点击事件
                    $("#exchange").css({"background": "#cdcdcd"});//改变按钮的颜色
                } else {
                    $(".sele_img").attr("src",basePath+getBankLogo(bank[1]));

                    $("#sele_id").val(bank[0]);
                    $(".sele_name").text(bank[1]);
                    var ss1 = bank[2].substring(5, 11);
                    $("input#branchName").val(bank[3]);
                    $(".sele_num").text(bank[2].replace(ss1, "********"));

                }
            }
        });
    }

}

//初始化密码输入弹窗
function initPay() {
    $(".overlay").removeClass("active");
    $(".popup_pay").hide();
    $(".pay_inp").val('');
    $(".pay_label li").each(function () {
        $(this).removeClass();
    });
}
function userInfo() {
    var customer;
    $.ajax({
        url: basePath + "/ea/jinbi/sajax_getUserInfo.jspa?sccid=" + sccid,
        type: "get",
        dataType: "json",
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            customer = member.customer;
        }, error: function (data) {
            var member = eval("(" + data + ")");
            customer = member.customer;
        }
    })
    return customer;
}

function frozenAccount(staffId) {
    $.ajax({
        url: basePath + "/ea/jinbi/sajax_frozenAccount.jspa?staffid=" + staffId,
        type: "post",
        dataType: "json",
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var isOK = member.isOK;
            if (isOK == "true") {
                prompt("提现成功，生成订单失败,账户冻结")
            } else {

            }
        }, error: function (data) {
            prompt("获取失败")
        }
    });
}

function bz() {
    $(".overlay_text").find("span").text("正在加载......");
    $(".overlay_text").show();
    var url = basePath + "/ea/jinbi/sajax_AjxaNotWithDrawGlod.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: true,
        dataType: "json",
        data:{
            wfj_jifen_id:wfjJifenId
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            gold_no=Math.ceil(member.gold_no==null?0:member.gold_no);
            avagold = Math.floor(jifenscore-gold_no>0?jifenscore-gold_no:0);

            $(".g_num_no").text(parseFormatNum(avagold/100,2));
            if(account_bank!=""){
                $(".g_num_wechat").text(parseFormatNum(available_amount/100,2));
                $(".wec_img").attr("src",basePath+getBankLogo(account_bank));


            }else{
                $(".wechatzh").hide();

            }

            $(".overlay_text").hide();
        }
    });
}
//金额处理
function parseFormatNum(number,n){

    if(n != 0 ){

        n = (n > 0 && n <= 20) ? n : 2;

    }
    number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";

    var sub_val = number.split(".")[0].split("").reverse();

    var sub_xs = number.split(".")[1];



    var show_html = "";

    for (i = 0; i < sub_val.length; i++){

        show_html += sub_val[i] + ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");

    }

    if(n == 0 ){

        return show_html.split("").reverse().join("");

    }else{

        return show_html.split("").reverse().join("") + "." + sub_xs;

    }

}

function getBankLogo(backName){
      var img = "";
    if (backName == '中国工商银行'|| backName == '工商银行') {
        img = "images/ea/finance/Gold_Pool/gs.png";
    } else if (backName == '中国建设银行'|| backName == '建设银行') {
        img = "images/ea/finance/Gold_Pool/js.png";
    } else if (backName == '中国银行') {
        img = "images/ea/finance/Gold_Pool/zg.png";
    } else if (backName == '中国农业银行'|| backName == '农业银行') {
        img = "images/ea/finance/Gold_Pool/ny.png";
    } else if (backName == '中国交通银行'|| backName == '交通银行') {
        img = "images/ea/finance/Gold_Pool/jt.png";
    } else if (backName == '招商银行') {
        img = "images/ea/finance/Gold_Pool/zs.png";
    } else if (backName == '浦发银行') {
        img = "images/ea/finance/Gold_Pool/pf.png";
    } else if (backName == '中信银行') {
        img = "images/ea/finance/Gold_Pool/zx.png";
    } else if (backName == '中国光大银行'|| backName == '光大银行') {
        img = "images/ea/finance/Gold_Pool/gd.png";
    } else if (backName == '华夏银行') {
        img = "images/ea/finance/Gold_Pool/hx.png";
    } else if (backName == '中国民生银行'|| backName == '民生银行') {
        img = "images/ea/finance/Gold_Pool/ms.png";
    } else if (backName == '兴业银行') {
        img = "images/ea/finance/Gold_Pool/xy.png";
    } else if (backName == '平安银行') {
        img = "images/ea/finance/Gold_Pool/pa.png";
    } else if (backName == '中国邮储银行' || backName == '中国邮政储蓄银行') {//1066
        img = "images/ea/finance/Gold_Pool/youz.png";
    } else if (backName == '广发银行') {//1027
        img = "images/ea/finance/Gold_Pool/gf.png";
    } else if (backName == '北京银行') {//1032
        img = "images/ea/finance/Gold_Pool/bj.png";
    } else if (backName == '宁波银行') {//1056
        img = "images/ea/finance/Gold_Pool/nb.png";
    }

    return img;
}