function pwdLevel(value) {
    var pattern_1 = /^.*([\W_])+.*$/i;
    var pattern_2 = /^.*([a-zA-Z])+.*$/i;
    var pattern_3 = /^.*([0-9])+.*$/i;
    var level = 0;
    if (value.length > 10) {
        level++;
    }
    if (pattern_1.test(value)) {
        level++;
    }
    if (pattern_2.test(value)) {
        level++;
    }
    if (pattern_3.test(value)) {
        level++;
    }
    if (level > 3) {
        level = 3;
    }
    return level;
}

function verc() {
    $("#JD_Verification1").click();
}

var validateRegExp = {
    decmal: "^([+-]?)\\d*\\.\\d+$", //浮点数
    decmal1: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", //正浮点数
    decmal2: "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", //负浮点数
    decmal3: "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$", //浮点数
    decmal4: "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$", //非负浮点数（正浮点数 + 0）
    decmal5: "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$", //非正浮点数（负浮点数 + 0）
    intege: "^-?[1-9]\\d*$", //整数
    intege1: "^[1-9]\\d*$", //正整数
    intege2: "^-[1-9]\\d*$", //负整数
    num: "^([+-]?)\\d*\\.?\\d+$", //数字
    num1: "^[1-9]\\d*|0$", //正数（正整数 + 0）
    num2: "^-[1-9]\\d*|0$", //负数（负整数 + 0）
    ascii: "^[\\x00-\\xFF]+$", //仅ACSII字符
    chinese: "^[\\u4e00-\\u9fa5]+$", //仅中文
    color: "^[a-fA-F0-9]{6}$", //颜色
    date: "^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$", //日期
    email: "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
    idcard: "^[1-9]([0-9]{14}|[0-9]{17})$", //身份证
    ip4: "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$", //ip地址
    letter: "^[A-Za-z]+$", //字母
    letter_l: "^[a-z]+$", //小写字母
    letter_u: "^[A-Z]+$", //大写字母
    mobile: "^0?(13|15|18|14)[0-9]{9}$", //手机
    notempty: "^\\S+$", //非空
    password: "^.*[A-Za-z0-9\\w_-]+.*$", //密码
    fullNumber: "^[0-9]+$", //数字
    picture: "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$", //图片
    qq: "^[1-9]*[1-9][0-9]*$", //QQ号码
    rar: "(.*)\\.(rar|zip|7zip|tgz)$", //压缩文件
    tel: "^[0-9\-()（）]{7,18}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
    url: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", //url
    username: "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$", //户名
    deptname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$", //单位名
    zipcode: "^\\d{6}$", //邮编
    realname: "^[A-Za-z\\u4e00-\\u9fa5]+$", // 真实姓名
    companyname: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
    companyaddr: "^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
    companysite: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$"
};
//主函数
(function ($) {
    $.fn.jdValidate = function (option, callback, def) {
        var ele = this;
        var id = ele.attr("id");
        var type = ele.attr("type");
        var rel = ele.attr("rel");
        var _onFocus = $("#" + id + validateSettings.onFocus.container);
        var _succeed = $("#" + id + validateSettings.succeed.container);
        var _isNull = $("#" + id + validateSettings.isNull.container);
        var _error = $("#" + id + validateSettings.error.container);
        if (def == true) {
            var str = ele.val();
            var tag = ele.attr("sta");
            if (str == "" || str == "-1") {
                validateSettings.isNull.run({
                    prompts: option,
                    element: ele,
                    isNullEle: _isNull,
                    succeedEle: _succeed
                }, option.isNull);
            } else if (tag == 1 || tag == 2) {
                return;
            } else {
                callback({
                    prompts: option,
                    element: ele,
                    value: str,
                    errorEle: _error,
                    succeedEle: _succeed
                });
            }
        } else {
            if (typeof def == "string") {
                ele.val(def);
            }
            if (type == "checkbox" || type == "radio") {
                if (ele.attr("checked") == true) {
                    ele.attr("sta", validateSettings.succeed.state);
                }
            }
            switch (type) {
                case "text":
                case "password":
                    ele.bind("focus", function () {
                        var str = ele.val();
                        if (str == def) {
                            ele.val("");
                        }
                        validateSettings.onFocus.run({
                            prompts: option,
                            element: ele,
                            value: str,
                            onFocusEle: _onFocus,
                            succeedEle: _succeed
                        }, option.onFocus, option.onFocusExpand);
                    })
                        .bind("blur", function () {
                            var str = ele.val();
                            if (str == "") {
                                ele.val(def);
                            }
                            if (validateRules.isNull(str)) {
                                validateSettings.isNull.run({
                                    prompts: option,
                                    element: ele,
                                    value: str,
                                    isNullEle: _isNull,
                                    succeedEle: _succeed
                                }, "");
                            } else {
                                callback({
                                    prompts: option,
                                    element: ele,
                                    value: str,
                                    errorEle: _error,
                                    isNullEle: _isNull,
                                    succeedEle: _succeed
                                });
                            }
                        });
                    break;
                default:
                    if (rel && rel == "select") {
                        ele.bind("change", function () {
                            var str = ele.val();
                            callback({
                                prompts: option,
                                element: ele,
                                value: str,
                                errorEle: _error,
                                isNullEle: _isNull,
                                succeedEle: _succeed
                            });
                        })
                    } else {
                        ele.bind("click", function () {
                            callback({
                                prompts: option,
                                element: ele,
                                errorEle: _error,
                                isNullEle: _isNull,
                                succeedEle: _succeed
                            });
                        })
                    }
                    break;
            }
        }
    }
})(jQuery);

//配置
var validateSettings = {
    onFocus: {
        state: null,
        container: "_error",
        style: "focus",
        run: function (option, str, expands) {
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style2).addClass(validateSettings.INPUT_style1);
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.onFocusEle.removeClass().addClass(validateSettings.onFocus.style).html(str);
            if (expands) {
                expands();
            }
        }
    },
    isNull: {
        state: 0,
        container: "_error",
        style: "null",
        run: function (option, str) {
            option.element.attr("sta", 0);
            if (!validateRules.checkType(option.element)) {
                if (str == "") {
                    option.element.removeClass(validateSettings.INPUT_style2).removeClass(validateSettings.INPUT_style1);
                } else {
                    option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
                }
            }

            option.succeedEle.removeClass(validateSettings.succeed.style);
            if (str == "") {
                option.isNullEle.removeClass().addClass(validateSettings.isNull.style).html(str);
            } else {
                option.isNullEle.removeClass().addClass(validateSettings.error.style).html(str);
            }
        }
    },
    error: {
        state: 1,
        container: "_error",
        style: "error",
        run: function (option, str) {
            option.element.attr("sta", 1);
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
            }

            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.errorEle.removeClass().addClass(validateSettings.error.style).html(str);
        }
    },
    succeed: {
        state: 2,
        container: "_succeed",
        style: "succeed",
        run: function (option) {
            option.element.attr("sta", 2);
            option.errorEle.empty();
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).removeClass(validateSettings.INPUT_style2);
            }

            option.succeedEle.addClass(validateSettings.succeed.style);
            option.errorEle.removeClass();
        }
    },
    INPUT_style1: "highlight1",
    INPUT_style2: "highlight2"
}

//验证规则
var validateRules = {
    isNull: function (str) {
        return (str == "" || typeof str != "string");
    },
    betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isUid: function (str) {
        return new RegExp(validateRegExp.username).test(str);
    },
    fullNumberName: function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isPwd: function (str) {
        return /^.*([\W_a-zA-z0-9-])+.*$/i.test(str);
    },
    isPwdRepeat: function (str1, str2) {
        return (str1 == str2);
    },
    isEmail: function (str) {
        return new RegExp(validateRegExp.email).test(str);
    },
    isTel: function (str) {
        return new RegExp(validateRegExp.tel).test(str);
    },
    isMobile: function (str) {
        return new RegExp(validateRegExp.mobile).test(str);
    },
    checkType: function (element) {
        return (element.attr("type") == "checkbox" || element.attr("type") == "radio" || element.attr("rel") == "select");
    },
    isRealName: function (str) {
        return new RegExp(validateRegExp.realname).test(str);
    },
    isCompanyname: function (str) {
        return new RegExp(validateRegExp.companyname).test(str);
    },
    isCompanyaddr: function (str) {
        return new RegExp(validateRegExp.companyaddr).test(str);
    },
    isCompanysite: function (str) {
        return new RegExp(validateRegExp.companysite).test(str);
    },
    simplePwd: function (str) {
//        var pin = $("#regName").val();
//        if (pin.length > 0) {
//            pin = strTrim(pin);
//            if (pin == str) {
//                return true;
//            }
//        }
        return pwdLevel(str) == 1;
    }
};
//验证文本
var validatePrompt = {
    regName: {
        onFocus: "请输入邮箱/用户名/手机号",
        succeed: "",
        isNull: "请输入邮箱/用户名/手机号",
        error: {
            beUsed: "该用户名已被使用，请重新输入。",
            badLength: "用户名长度只能在4-20位字符之间",
            badFormat: "用户名只能由中文、英文、数字及“_”、“-”组成",
            fullNumberName: "<span>用户名不能是纯数字，请确认输入的是手机号或者重新输入</span>"
        },
        onFocusExpand: function () {
            $("#morePinDiv").removeClass().addClass("intelligent-error hide");
        }
    },

    pwd: {
        onFocus: "<span>6-20位字符，可使用字母、数字或符号的组合，不建议使用纯数字，纯字母，纯符号</span>",
        succeed: "",
        isNull: "请输入密码",
        error: {
            badLength: "密码长度只能在6-20位字符之间",
            badFormat: "密码只能由英文、数字及标点符号组成",
            simplePwd: "<span>该密码比较简单，有被盗风险，建议您更改为复杂密码，如字母+数字的组合</span>"
        },
        onFocusExpand: function () {
            $("#pwdstrength").hide();
        }
    },
    pwdRepeat: {
        onFocus: "请再次输入密码",
        succeed: "",
        isNull: "请输入密码",
        error: {
            badLength: "密码长度只能在6-20位字符之间",
            badFormat2: "两次输入密码不一致",
            badFormat1: "密码只能由英文、数字及标点符号组成"
        }
    },
    mobileCode: {
        onFocus: "",
        succeed: "",
        isNull: "请输入短信验证码",
        error: "验证码错误"
    },
    protocol: {
        onFocus: "",
        succeed: "",
        isNull: "请先阅读并同意《用户注册协议》",
        error: ""
    },
    empty: {
        onFocus: "",
        succeed: "",
        isNull: "",
        error: ""
    }
};

var nameold, morePinOld, emailResult;
var namestate = false;
//回调函数
var validateFunction = {
    regName: function (option) {
        $("#intelligent-regName").empty().hide();
        var regName = option.value;
        if (validateRules.isNull(regName) || regName == '邮箱/用户名/手机号') {
            option.element.removeClass(validateSettings.INPUT_style2).removeClass(validateSettings.INPUT_style1);
            $("#regName_error").removeClass().empty();
            return;
        }

        if (regName.indexOf("@") > -1) {
            $("#mobileCodeDiv").removeClass().addClass("item hide");
            $("#authcodeDiv").show();
            checkEmail(option);
            return;
        }
        if (validateRules.isMobile(regName)) {
            checkMobile(option);
            return;
        }
        $("#mobileCodeDiv").removeClass().addClass("item hide");
        $("#authcodeDiv").show();
        checkPin(option);
    },

    pwd: function (option) {
        var str1 = option.value;
        var regName = $("#regName").val();
        if ((validateRules.isNull(regName) == false) && (regName != '邮箱/用户名/手机号') && regName == str1) {
            $("#pwdstrength").hide();
            validateSettings.error.run(option, "<span>您的密码与账户信息太重合，有被盗风险，请换一个密码</span>");
            return;
        }

        var str2 = $("#pwdRepeat").val();
        var format = validateRules.isPwd(option.value);
        var length = validateRules.betweenLength(option.value, 6, 20);

        $("#pwdstrength").hide();
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else {

            validateSettings.succeed.run(option);
            validateFunction.pwdstrength();
            if (validateRules.simplePwd(str1)) {
                $("#pwd_error").removeClass().addClass("focus");
                $("#pwd_error").empty().html(option.prompts.error.simplePwd);
                return;
            }
        }
        if (str2 == str1) {
            $("#pwdRepeat").focus();
        }
    },
    pwdRepeat: function (option) {
        var str1 = option.value;
        var str2 = $("#pwd").val();
        var length = validateRules.betweenLength(option.value, 6, 20);
        var format2 = validateRules.isPwdRepeat(str1, str2);
        var format1 = validateRules.isPwd(str1);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format1) {
                validateSettings.error.run(option, option.prompts.error.badFormat1);
            } else {
                if (!format2) {
                    validateSettings.error.run(option, option.prompts.error.badFormat2);
                }
                else {
                    validateSettings.succeed.run(option);
                }
            }
        }
    },

    mobileCode: function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            validateSettings.error.run(option, option.prompts.error);
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },
    protocol: function (option) {
        if (option.element.attr("checked") == true) {
            option.element.attr("sta", validateSettings.succeed.state);
            option.errorEle.html("");
        } else {
            option.element.attr("sta", validateSettings.isNull.state);
            option.succeedEle.removeClass(validateSettings.succeed.style);
        }
    },
    pwdstrength: function () {
        var element = $("#pwdstrength");
        var value = $("#pwd").val();
        if (value.length >= 6 && validateRules.isPwd(value)) {
            $("#pwd_error").removeClass('focus');
            $("#pwd_error").empty();
            element.show();
            var level = pwdLevel(value);
            switch (level) {
                case 1:
                    element.removeClass().addClass("strengthA");
                    break;
                case 2:
                    element.removeClass().addClass("strengthB");
                    break;
                case 3:
                    element.removeClass().addClass("strengthC");
                    break;
                default:
                    break;
            }
        } else {
            element.hide();
        }
    },
    checkGroup: function (elements) {
        for (var i = 0; i < elements.length; i++) {
            if (elements[i].checked) {
                return true;
            }
        }
        return false;
    },
    checkSelectGroup: function (elements) {
        for (var i = 0; i < elements.length; i++) {
            if (elements[i].value == -1) {
                return false;
            }
        }
        return true;
    },

    FORM_submit: function (elements) {
        var bool = true;
        for (var i = 0; i < elements.length; i++) {
            if ($(elements[i]).attr("sta") == 2) {
                bool = true;
            } else {
                bool = false;
                break;
            }
        }

        return bool;
    }
};

function checkPin(option) {
    var pin = option.value;
    if (!validateRules.betweenLength(pin.replace(/[^\x00-\xff]/g, "**"), 4, 20)) {
        validateSettings.error.run(option, option.prompts.error.badLength);
        return false;
    }

    if (!validateRules.isUid(pin)) {
        validateSettings.error.run(option, option.prompts.error.badFormat);
        return;
    }
    if (validateRules.fullNumberName(pin)) {
        validateSettings.error.run(option, option.prompts.error.fullNumberName);
        return;
    }
    if (!namestate || nameold != pin) {
        if (nameold != pin) {
            nameold = pin;
            option.errorEle.html("<em style='color:#999'>检验中……</em>");
            $.getJSON("../validate/isPinEngaged?pin=" + escape(pin) + "&r=" + Math.random(), function (date) {
                if (date.success == 0) {
                    validateSettings.succeed.run(option);
                    namestate = true;
                } else {
                    validateSettings.error.run(option, "<span>" + option.prompts.error.beUsed.replace("{1}", option.value) + "</span>");
                    namestate = false;
                    morePinOld = date.morePin;
                    if (date.morePin != null && date.morePin.length > 0) {
                        var html = ""
                        for (var i = 0; i < date.morePin.length; i++) {
                            html += "<div class='item-fore'><input name='morePinRadio' onclick='selectMe(this);' type='radio' class='radio' value='" + date.morePin[i] + "'/><label>" + date.morePin[i] + "</label></div>"
                        }
                        $("#morePinGroom").empty();
                        $("#morePinGroom").html(html);
                        $("#morePinDiv").removeClass().addClass("intelligent-error");
                    }
                }
            });
        } else {
            validateSettings.error.run(option, "<span>" + option.prompts.error.beUsed.replace("{1}", option.value) + "</span>");
            if (morePinOld != null && morePinOld.length > 0) {
                $("#morePinDiv").removeClass().addClass("intelligent-error");
            }
            namestate = false;
        }
    } else {
        validateSettings.succeed.run(option);
    }
}

function selectMe(option) {
    $("#morePinDiv").removeClass().addClass("intelligent-error hide");
    $("#regName").val(option.value);
    $("#regName").blur();
}

function checkEmail(option) {
    var email = option.value;
    var email = strTrim(option.value);
    var format = validateRules.isEmail(email);
    var format2 = validateRules.betweenLength(email, 0, 50);
    if (!format) {
        validateSettings.error.run(option, "邮箱地址不正确，请重新输入");
    } else {
        if (!format2) {
            validateSettings.error.run(option, "邮箱地址长度应在4-50个字符之间");
        } else {
            if (!namestate || nameold != email) {
                if (nameold != email) {
                    nameold = email;
                    option.errorEle.html("<em style='color:#999'>检验中……</em>");
                    $.getJSON("../validate/isEmailEngaged?email=" + escape(option.value) + "&r=" + Math.random(), function (date) {

                        emailResult = date.success;
                        if (date.success == 0) {
                            validateSettings.succeed.run(option);
                            namestate = true;
                            if ($("#mail")) {
                                $("#mail").val(option.value);
                            }
                        }
                        if (date.success == 1) {
                            validateSettings.error.run(option, "该邮箱已存在，立刻<a  class='flk13' href='#'>登录</a>");
                            namestate = false;
                        }
                        if (date.success == 2) {
                            validateSettings.error.run(option, "邮箱地址不正确，请重新输入");
                            namestate = false;
                        }
                    })
                }
                else {
                    if (emailResult == 1) {
                        validateSettings.error.run(option, "该邮箱已存在，立刻<a  class='flk13' href='#'>登录</a>");
                        namestate = false;
                    }

                    if (emailResult == 2) {
                        validateSettings.error.run(option, "邮箱地址不正确，请重新输入");
                        namestate = false;
                    }
                    namestate = false;
                }
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    }
}
function checkMobile(option) {
    var mobileValue = option.value;
    mobileValue = strTrim(mobileValue);
    var isMobile = validateRules.isMobile(mobileValue);
    if (!isMobile || mobileValue.length > 11) {
        validateSettings.error.run(option, option.prompts.error.badFormat);
    } else {
        if (!namestate || nameold != option.value) {
            if (nameold != option.value) {
                nameold = option.value;
                option.errorEle.html("<em style='color:#999'>检验中……</em>");
                $.getJSON("../validate/isMobileEngaged?mobile=" + option.value + "&r=" + Math.random(), function (date) {
                    if (date.success == 0) {
                        validateSettings.succeed.run(option);
                        $("#mobileCodeDiv").removeClass().addClass("item");
                        $("#authcodeDiv").hide();
                        namestate = true;

                        if ($("#mobile")) {
                            $("#mobile").val(option.value);
                        }
                    } else {
                        validateSettings.error.run(option, "该手机号已存在，立刻<a  class='flk13' href='#'>登录</a>");
                        namestate = false;
                    }
                })
            } else {
                validateSettings.error.run(option, "该手机号已存在，立刻<a  class='flk13' href='#'>登录</a>");
                namestate = false;
            }
        } else {
            validateSettings.succeed.run(option);
        }
    }
}

function sendMobileCode() {
    var mobile = $("#regName").val();
    if (validateRules.isNull(mobile) || !validateRules.isMobile(mobile)) {
        $("#regName_error").html("<span>手机号码格式有误，请输入以13/14/15/18开头的11位数字。<span>");
        $("#regName_error").removeClass().addClass("error");
        //  $("#regName").removeClass().addClass("text highlight2");
        $("#regName_error").show();
        return;
    }
    if ($("#sendMobileCode").attr("disabled")) {
        return;
    }
    $("#sendMobileCode").attr("disabled", "disabled");

    jQuery.ajax({
        type: "get",
        url: "../notify/mobileCode?mobile=" + $("#regName").val() + "&r=" + Math.random(),
        success: function (result) {
            if (result) {
                var obj = eval(result);
                if (obj.rs == 1 || obj.remain) {
                    $("#mobileCode_error").addClass("hide");
                    $("#dyMobileButton").html("120秒后重新获取");
                    if (obj.remain) {
                        $("#mobileCodeSucMessage").empty().html(obj.remain);
                    } else {
                        $("#mobileCodeSucMessage").empty().html("验证码已发送，请查收短信。");
                    }

                    setTimeout(countDown, 1000);
                    $("#sendMobileCode").removeClass().addClass("btn btn-15").attr("disabled", "disabled");
                    $("#mobileCode").removeAttr("disabled");
                }
                if (obj.rs == -1) {
                    $("#regName_error").text("手机号码格式有误，请输入以13/14/15/18开头的11位数字。");
                    $("#regName_error").removeClass().addClass("error");
                    $("#regName").removeClass().addClass("text highlight2");
                }
                if (obj.info) {
                    mobileCodeError(obj.info);
                }

                if (obj.rs == -2) {
                    mobileCodeError("网络繁忙，请稍后重新获取验证码");
                }
            }
        }
    });
}

function mobileCodeError(content) {
    $("#mobileCode_error").html(content);
    $("#mobileCode_error").removeClass().addClass("error");
    $("#mobileCode_error").show();
    $("#sendMobileCode").removeClass().addClass("btn").removeAttr("disabled");
}
var delayTime = 120;
function countDown() {
    delayTime--;
    $("#dyMobileButton").html(delayTime + '秒后重新获取');
    if (delayTime == 1) {
        delayTime = 120;
        $("#mobileCodeSucMessage").empty();
        $("#dyMobileButton").html("获取短信验证码");
        $("#mobileCode_error").addClass("hide");
        $("#sendMobileCode").removeClass().addClass("btn").removeAttr("disabled");
    } else {
        setTimeout(countDown, 1000);
    }
}

function strTrim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
var emailSurfixArray = ['@163.com', '@126.com', '@qq.com', '@sina.com', '@gmail.com', '@sohu.com', '@yahoo.cn', '@vip.163.com', '@vip.126.com', '@188.com', '@139.com', '@yeah.net'];

function moreName (event) {
    var sval = this.value;
    event = event ? event : window.event;
    var keyCode = event.keyCode;
    var vschool = $('#intelligent-regName');
    if (keyCode == 40 || keyCode == 38 || keyCode == 13) {
        var tipindex = $("#hnseli").val() == "" ? -1 : $("#hnseli").val();
        var fobj;
        if (keyCode == 40) {
            tipindex++;
            if (tipindex == vschool.find("li").length) {
                tipindex = 0;
                vschool.find("li").eq(vschool.find("li").length - 1).css("background-color", "");
            }
            fobj = vschool.find("li").eq(tipindex);
            vschool.find("li").eq(tipindex - 1).css("background-color", "");
            fobj.css("background-color", "#EEEEEE");
            $("#regName").val(fobj.html().replace(/<(\S*?)[^>]*>|<.*? \/>/g, ""));
            $("#schoolid").val(fobj.attr("value"));
            $("#hnseli").val(tipindex);
            return;
        } else if (keyCode == 38) {
            tipindex--;
            if (tipindex <= -1) {
                tipindex = vschool.find("li").length - 1;
                vschool.find("li").eq(0).css("background-color", "");
            }
            vschool.find("li").eq(tipindex + 1).css("background-color", "");
            fobj = vschool.find("li").eq(tipindex);
            fobj.css("background-color", "#EEEEEE");
            if (fobj.html() != null) {
                $("#regName").val(fobj.html().replace(/<(\S*?)[^>]*>|<.*? \/>/g, ""));
                $("#schoolid").val(fobj.attr("value"));
            }
            $("#hnseli").val(tipindex);
            return;
        } else if (keyCode == 13) {
            $("#hnseli").val("-1");
            if ($("#regName").val().length >= 1) {
                var combinedValue = vschool.find("li").eq(tipindex).html();
                if (combinedValue != null) {
                    $("#regName").val(combinedValue.replace(/<(\S*?)[^>]*>|<.*? \/>/g, ""));
                }
                vschool.empty().hide();
                if ($("#schoolid").val() != "") {
                    $("#hnschool").val("1");
                    $("#hnschool").attr("sta", 2);
                    $("#regName").blur();
                } else {
                    $("#hnschool").val("-1");
                    $("#hnschool").attr("sta", 0);
                    $("#regNamel_error").html("");
                    $("#regName_succeed").removeClass("succeed");
                }
                return;
            }
        }
    } else {
        //hide morePin
        $("#morePinDiv").removeClass().addClass("intelligent-error hide");

        if (sval != "") {
            var userinput = sval;
            var oldSval = "";
            var pos = sval.indexOf("@");
            if (pos >= 0) {
                oldSval = sval.substring(0, pos);
            }

            $("#schoolid").val("");
            $("#hnseli").val("-1");
            var html = "";
            if (/[\u4E00-\u9FA5]/g.test(sval) || sval.indexOf("-") > -1 || sval.indexOf("_") > -1) {
                html = "<li>" + sval + "</li>";
            } else {
                if (oldSval != '') {
                    sval = oldSval;
                }
                if (userinput.indexOf("@") == 0) {
                    sval = "";
                }
                html = "<li>" + userinput + "</li>";
                var partSurfix = initEmailSurfixArray(userinput);
                if (partSurfix != null) {
                    for (var i = 0; i < partSurfix.length; i++) {
                        html += "<li>" + sval + partSurfix[i] + "</li>";
                    }
                }
            }
            if (sval.length > 25) {
                $('#intelligent-regName').hide();
            } else {
                $('#intelligent-regName').show();
                $('#intelligent-regName').html(html).find("li").mousedown(function () {
                    $("#regName").val($(this).html());
                    $("#schoolid").val($(this).attr("value"));
                    $("#hnseli").val("-1");
                });
            }
        } else {
            $('#intelligent-regName').html("").hide();
            $("#schoolid").val("");
            $("#hnseli").val("-1");
        }
    }
}

$("#regName").keyup(moreName);
$("#regName").focus(moreName);

function initEmailSurfixArray(str) {
    var pos = str.indexOf("@");
    if (pos < 0 || pos == (str.length - 1)) {
        return emailSurfixArray;
    }
    var inputSurfix = str.substring(pos, str.length);
    var suitableSurfixArray = [];
    var j = 0;
    for (var i = 0; i < emailSurfixArray.length; i++) {
        if (emailSurfixArray[i].indexOf(inputSurfix) == 0) {
            suitableSurfixArray[j] = emailSurfixArray[i];
            j++;
        }
    }

    return suitableSurfixArray;
}

$("#intelligent-regName li").livequery("mouseover",function () {
    var vi = $(this).attr("dex");
    var tipindex = $("#hnseli").val() == "" ? -1 : $("#hnseli").val();
    if (tipindex <= 0) {
        tipindex = 0;
    }
    $('#intelligent-regName').find("li").eq(tipindex).css("background-color", "");
    $(this).css("background-color", "#EEEEEE");
    $("#hnseli").val($(this).attr("dex"));
}).livequery("mouseleave", function () {
        var tipindex = $("#hnseli").val() == "" ? -1 : $("#hnseli").val();
        if (tipindex <= 0) {
            tipindex = 0;
        }
        $(this).css("background-color", "");
        $("#hnseli").val("-1");
    });

$("#regName").blur(function () {
    setTimeout(function () {
        if ($("#schoolid").val() == "") {
            $("#schoolinput").val("");
            $("#hnschool").val("-1");
            $("#hnschool").attr("sta", 0);
            $("#schoolinput_succeed").removeClass("succeed");
        } else {
            $("#hnschool").val("1");
            $("#hnschool").attr("sta", 2);
            $("#schoolinput_error").html("");
            $("#schoolinput_succeed").addClass("succeed");
        }
        $('#intelligent-school').hide().empty();
        $("#hnseli").val("-1");
    }, 200)
})

function validateRegName() {
    var loginName = $("#regName").val();
    if (validateRules.isNull(loginName) || loginName == '邮箱/用户名/手机号') {
        $("#regName").val("");
        $("#regName").attr({ "class": "text highlight2" });
        $("#regName_error").html("请输入邮箱/用户名/手机号").show().attr({ "class": "error" });
        return false;
    }
    return true;
}
$("#regist .tab li").hover(function () {
    if ($(this).hasClass("curr")) {
    } else {
        $(this).addClass("new");
    }
}, function () {
    if ($(this).hasClass("curr")) {
    } else {
        $(this).removeClass("new");
    }
})

$("#registsubmit").hover(function () {
    $(this).addClass("hover-btn")
}, function () {

    $(this).removeClass("hover-btn")
})