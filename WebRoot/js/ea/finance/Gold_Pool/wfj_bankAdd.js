$(function () {

    object.push("khd=", khd);
    object.push("&flag=", flag);
    object.push("&identifying=", identifying);
    object.push("&mark=", mark);


    //单项文本选择
    $(".radio_text").click(function (e) {
        e.stopPropagation();
        var id = $(this).attr("id") + "_list";
        var which = $('#' + id);
        var that = $(this);
        which.mobiscroll().treelist({
            theme: "android-ics",
            lang: "zh",
            display: 'bottom',
            inputClass: 'tmp',
            headerText: '请您选择',
            onSelect: function (valueText) {
                var m = $(this).find("li").eq(valueText).text();
                var code = $(this).find("li").eq(valueText).find("input").val();
                that.find("input").eq(0).val(m);
                that.find("input").eq(1).val(code);
            }
        });
        $("input[id^=" + id + "]").focus();
    });

    //省市选择
    $("#area").click(function () {
        var that = $(this);
        $("#area_list").mobiscroll().treelist({
            theme: "android-ics",
            lang: "zh",
            display: 'bottom',
            inputClass: 'tmp',
            headerText: '请您选择',
            onSelect: function (valueText, inst) {
                console.log(valueText);
                var n = valueText.split(' ');
                var m1 = $(this).children("li").eq(n[0]).find("div").html();
                var m1_id = $(this).children("li").eq(n[0]).find("div").attr("data-value");
                var m2 = $(this).children("li").eq(n[0]).find("li").eq(n[1]).html();
                var m2_id = $(this).children("li").eq(n[0]).find("li").eq(n[1]).attr("data-value");
                console.log(m1);
                console.log(m2);
                console.log(m1_id);
                console.log(m2_id);
                that.find("input").val('' + m1 + '-' + m2 + '')
            }
        });
        $("input[id^=area_list]").focus();
    });


    $("#preservation").click(function () {

        var z1 = /^(\d{16}|\d{19})$/;		//判断银行卡
        var z2 = "([\u4e00-\u9fa5])";   //判断中文
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  // 身份证号码验位

        if ($("#bank_type").val() == "") {
            prompt("请选择银行卡");
        } else if ($("#city").val() == "") {
            prompt("省市不可为空");
        } else if ($("#bankid").val() == "") {
            prompt("请输入银行卡号");
        } else if ($("#banname").val() == "") {
            prompt("请填写详细分行地址");
        } else if (!z1.test($("#bankid").val()) || /\s+/.test($("#bankid").val())) {
            prompt("银行卡号格式错误");
        } else if ($("#card_type").val() == "") {
            prompt("请选择银行卡类型");
        } else if ($("#card_public").val() == "") {
            prompt("请选择银行卡用途");
        } else if ($("#tel").val() == "") {
            prompt("请输入手机号");
        } else if ($("#code").val() == "") {
            prompt("请输入验证码");
        } else if (i != $("#code").val()) {
            prompt("输入验证码错误！");
        } else if ($("#name").val() == "") {
            prompt("姓名不可为空");
        } else if (!new RegExp(z2).test($("#name").val()) || /\s+/.test($("#name").val())) {
            prompt("姓名格式错误");
        } else if ($("#IDcard").val() == "") {
            prompt("请输入身份证号");
        } else if (reg.test($("#IDcard").val()) === false) {
            prompt("身份证输入错误");
        } else {

            /*登录银行和卡号相对应的判断*/
            var province = $("#city").val().split("-")[0];//所属省份
            var bankAddress = $("#city").val().split("-")[1];// 所属城市

            var bankcardno = $("#bankid").val();//获取输入的卡号
            var realname = $("#name").val();//持卡人姓名
            var idcard = $("#IDcard").val();//身份证号
            var tel = $("#tel").val();//银行预留电话

            var bkname = $("#bank_type").val();//获取用户选择的所属银行
            var cdtype = $("#card_type").val();//获取用户选择卡类型

            var branchName = $("#banname").val();//获取用户填写的

            var uut = basePath + "/ea/perinfor/sajax_ea_checkBankNum.jspa";
            $.ajax({
                url: uut,
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    "name": realname,
                    "idCardCode": idcard,
                    "accountNo": bankcardno,
                    "bankPreMobile": tel
                },
                success: function (data) {
                    var member = eval("(" + data + ")");//获得接口提供的信息
                    var error_code = member.code;//0表示查询成功，其他表示查询失败
                    var message = member.message;
                    if (error_code == '0') {
                        var result = member.data.result;
                        if (result == "2") {
                            prompt("认证信息不匹配");
                        } else if (result == "3") {
                            prompt("无法验证");
                        } else if (result == "-1") {
                            prompt("异常情况");
                        } else if (result == "1") {
                            if (submit == '00') {
                                submit = '01';
                                var url = basePath + "ea/perinfor/sajax_ea_addBankCardInformation.jspa?staffId=" + staffid + "&province=" + province + "&bankAddress=" + bankAddress + "&branchName" + branchName;
                                $.ajax({
                                    url: url,
                                    type: "get",
                                    async: true,
                                    dataType: "json",
                                    data: $('#form').serialize(),
                                    beforeSend: function () {
                                        $(".overlay").addClass("active");
                                        $(".popup_img").show();
                                    },
                                    success: function (data) {
                                        var member = eval("(" + data + ")");
                                        var msg = member.msg;
                                        $(".overlay").removeClass("active");
                                        $(".popup_img").hide();
                                        if (msg == "ok") {
                                            alert("银行卡添加成功");
                                            window.location.href = basePath + "ea/perinfor/ea_getBankCardInformation.jspa?" + object.join("") + "&staffId=" + staffid + "&editType=" + editType + "&backurl=" + backurl + "&sccid=" + sccid + "&user=" + user + "&bankId=" + bankId;
                                        } else if (msg == "cunzai") {
                                            prompt("银行卡已经绑定过了");
                                        }
                                        else {
                                            prompt("保存失败！");
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        prompt(message);
                        //$("#bankid").val("");
                    }
                },
            });
        }
    });
});


function verifyingBankCard(bankcardno, bkname, cdtype) {
    var msg = true;
    var url = basePath + "/ea/perinfor/sajax_ea_checkBank.jspa?accountNo=" + bankcardno;
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");//获得接口提供的信息
            var error_code = member.error_code;//0表示查询成功，其他表示查询失败
            /!*var cardtype = member.result.cardtype;/ / 卡类别
            var bankname = member.result.bankname;//所属银行*!/
            var cardtype = member.result.type;//卡类别
            var bankname = member.result.bank;//所属银行

            if (error_code != '0') {
                prompt("请输入正确的卡号？");
                $("#bankid").val("");
                msg = false;
            } else if (bankname.indexOf("中国") == -1 && bankname != bkname) {
                var bankname = "中国" + member.result.bank;
                if (cardtype.indexOf("贷记") != -1) {
                    var cardtype = "信用卡";
                }
                if (bankname == bkname) {
                    if (cardtype != cdtype) {
                        prompt("银行卡类型错误！");
                        $("#card_type").val(null);
                        msg = false;
                    }
                } else {
                    prompt("银行卡与所属银行不相符！");
                    $("#bank_type").val(null);
                    msg = false;
                }
            } else if (bankname == bkname) {
                if (cardtype.indexOf("贷记") != -1) {
                    var cardtype = "信用卡";
                }
                if (cardtype != cdtype) {
                    prompt("银行卡类型错误！");
                    $("#card_type").val(null);
                    msg = false;
                }
            }
        }
    });
    return msg;
}


//获取验证码-倒计时
var clock = '';
var nums = 60;
var btn;

function sendCode(thisBtn) {

    var count = $("#tel").val();
    var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
    if (count == "") {
        prompt("请输入电话号！");
        return;
    } else if (!myreg.test(count)) {
        prompt("请输入正确格式电话号！");
        return false;
    } else {
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        btn.value = nums + '秒重新获取';
        clock = setInterval(doLoop, 1000); //一秒执行一次
        btn.className = "verification_btn disabled_btn";
    }
    q = 1;
    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + count,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = data;
            i = member.returna;
            alert(i);
        }
    });
}
function doLoop() {
    nums--;
    if (nums > 0) {
        btn.value = nums + '秒重新获取';
    } else {
        clearInterval(clock); //清除js定时器
        btn.disabled = false;
        btn.value = '获取验证码';
        nums = 60; //重置时间
        i = "";
        btn.className = "verification_btn";
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
    }, 2000);
}

$(document).ready(function (e) {

    //弹出层
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.4 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    //省市级联
    $.ajax({
        url: basePath + "ea/perinfor/sajax_ea_getCitiesList.jspa",
        type: "post",
        success: function (data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            var str = new Array();
            for (var i = 0; i < result.length; i++) {
                str.push('<li><div data-value=' + result[i].id + '>' + result[i].province + '</div>');
                str.push('<ul>')

                for (var r = 0; r < result[i].city.length; r++) {

                    if (result[i].city[r].id == "32" || result[i].city[r].id == "61" || result[i].city[r].id == "92" || result[i].city[r].id == "362") {

                        for (var j = 0; j < result[i].city[r].district.length; j++) {
                            str.push('<li data-value=' + result[i].city[r].district[j].id + '>' + result[i].city[r].district[j].district + '</li>');
                        }
                    } else {
                        str.push('<li data-value=' + result[i].city[r].id + '>' + result[i].city[r].city + '</li>');

                    }
                }
                str.push('</ul></li>')
            }

            $("#area_list").append(str.join(""));
        },
    });
});