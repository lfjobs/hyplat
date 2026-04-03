$.extend(validatePrompt, {
    realname: {
        onFocus: "2-20位字符，可由中文或英文组成",
        succeed: "",
        isNull: "请输入联系人姓名",
        error: {
            badLength: "联系人姓名长度只能在2-20位字符之间",
            badFormat: "联系人姓名只能由中文或英文组成"
        }
    },
    department: {
        onFocus: "",
        succeed: "",
        isNull: "请选择联系人所在部门",
        error: ""
    },
    tel: {
        onFocus: "<span>请填写联系人常用的电话，以便于我们联系，如：0527-88105500</span>",
        succeed: "",
        isNull: "请输入联系人固定电话",
        error: "电话格式错误，请重新输入"
    },
    mobile: {
        onFocus: "非北京、上海、广州三地客户，请在手机号前加“0”",
        succeed: "",
        isNull: "",
        error: "手机号格式错误，请重新输入"
    },
    companyname: {
        onFocus: "<span>请填写工商局注册的全称。4-40位字符，可由中英文、数字及“_”、“-”、()、（）组成</span>",
        succeed: "",
        isNull: "请输入公司名称",
        error: {
            badLength: "公司名称长度只能在4-40位字符之间",
            badFormat: "<span>公司名称只能由中文、英文、数字及“_”、“-”、()、（）组成</span>"
        }
    },
    companyarea: {
        onFocus: "请选择公司所在地",
        succeed: "",
        isNull: "请选择公司所在地",
        error: ""
    },
    companyaddr: {
        onFocus: "<span>请详细填写公司经营地址　如：北京市海淀区苏州街20号银丰大厦B座3层</span>",
        succeed: "",
        isNull: "请输入公司地址",
        error: {
            badLength: "公司地址长度只能在4-50位字符之间",
            badFormat: "<span>公司地址只能由中文、英文、数字及“_”、“-”、()、（）、#组成</span>"
        }
    },
    purpose: {
        onFocus: "",
        succeed: "",
        isNull: "请选择购买类型/用途",
        error: ""
    },
    companysite: {
        onFocus: "如：http://www.jd.com",
        succeed: "",
        isNull: "",
        error: {
            badLength: "公司网址长度只能在80位字符之内",
            badFormat: "公司网址格式不正确。应如：http://www.jd.com"
        }
    },
    mail: {
        onFocus: "请输入常用的邮箱",
        succeed: "",
        isNull: "请输入邮箱",
        error: {
            beUsed: "该邮箱已被使用，请更换其它邮箱",
            badFormat: "请输入有效的邮箱地址",
            badLength: "您填写的邮箱过长，邮箱地址只能在50个字符以内"
        }
    }
});

$.extend(validateFunction, {
    mail: function (option) {
        var format = validateRules.isEmail(option.value);
        var format2 = validateRules.betweenLength(option.value, 0, 50);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else {
            if (!format2) {
                validateSettings.error.run(option, option.prompts.error.badLength);
            } else {
                if (!emailstate || emailold != option.value) {
                    if (emailold != option.value) {
                        emailold = option.value;
                        validateSettings.succeed.run(option);
                        emailstate = true;
                    }
                    else {
                        validateSettings.error.run(option, option.prompts.error.beUsed);
                        emailstate = false;
                    }
                }
                else {
                    if ($("#email_linker")) {
                        $("#email_linker").text(option.value);
                    }
                    validateSettings.succeed.run(option);
                }
            }
        }
    },

    realname: function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 2, 20);
        var format = validateRules.isRealName(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    department: function (option) {
        var bool = (option.value == -1);
        if (bool) {
            validateSettings.isNull.run(option, "");
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    tel: function (option) {
        var format = validateRules.isTel(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    mobile: function (option) {
        var format = validateRules.isMobile(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    companyname: function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 40);
        var format = validateRules.isCompanyname(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            } else {
                validateSettings.succeed.run(option);
            }
        }
    },
    companyarea: function (option) {
        var bool = (option.value == -1);
        if (bool) {
            validateSettings.isNull.run(option, "");
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    companyaddr: function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 50);
        var format = validateRules.isCompanyaddr(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    purpose: function (option) {
        var purpose = $("input:checkbox[@name='purposetype']");
        if (validateFunction.checkGroup(purpose)) {
            validateSettings.succeed.run(option);
        } else {
            validateSettings.error.run(option, option.prompts.isNull);
        }
    },
    companysite: function (option) {
        var length = validateRules.betweenLength(option.value, 0, 80);
        var format = validateRules.isCompanysite(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    emRegCompany_validate: function () {
        if ($("#mobileCodeDiv").attr("class") == 'item') {
            $("#regName").jdValidate(validatePrompt.regName, validateFunction.regName, true);
            $("#pwd").jdValidate(validatePrompt.pwd, validateFunction.pwd, true)
            $("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat, true);
            $("#realname").jdValidate(validatePrompt.realname, validateFunction.realname, true);
            $("#department").jdValidate(validatePrompt.department, validateFunction.department, true);
            $("#tel").jdValidate(validatePrompt.tel, validateFunction.tel, true);
            $("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
            $("#hncompanyarea").jdValidate(validatePrompt.companyarea, validateFunction.companyarea, true);
            $("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyaddr, true);

            $("#purpose").jdValidate(validatePrompt.purpose, validateFunction.purpose, true);
            $("#mobileCode").jdValidate(validatePrompt.mobileCode, validateFunction.mobileCode, true);
            return validateFunction.FORM_submit(["#regName", "#pwd", "#pwdRepeat", "#realname", "#department", "#tel", "#companyname", "#hncompanyarea", "#companyaddr", "#purpose", "#mobileCode"]);
        } else {
            $("#regName").jdValidate(validatePrompt.regName, validateFunction.regName, true);
            $("#pwd").jdValidate(validatePrompt.pwd, validateFunction.pwd, true)
            $("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat, true);
            $("#realname").jdValidate(validatePrompt.realname, validateFunction.realname, true);
            $("#department").jdValidate(validatePrompt.department, validateFunction.department, true);
            $("#tel").jdValidate(validatePrompt.tel, validateFunction.tel, true);

            $("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
            $("#hncompanyarea").jdValidate(validatePrompt.companyarea, validateFunction.companyarea, true);
            $("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyaddr, true);
            $("#purpose").jdValidate(validatePrompt.purpose, validateFunction.purpose, true);

            return validateFunction.FORM_submit(["#regName", "#pwd", "#pwdRepeat", "#realname", "#department", "#tel", "#companyname", "#hncompanyarea", "#companyaddr", "#purpose"]);
        }
    }
});


$("#pwd").bind("keyup",
    function () {
        validateFunction.pwdstrength();
    }).jdValidate(validatePrompt.pwd, validateFunction.pwd)
$("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat);
$("#authcode").jdValidate(validatePrompt.authcode, validateFunction.authcode);
$("#regName").jdValidate(validatePrompt.regName, validateFunction.regName);

$("#realname").jdValidate(validatePrompt.realname, validateFunction.realname);
$("#department").jdValidate(validatePrompt.department, validateFunction.department);
$("#tel").jdValidate(validatePrompt.tel, validateFunction.tel);
$("#mobile").jdValidate(validatePrompt.mobile, validateFunction.mobile);
$("#mail").jdValidate(validatePrompt.mail, validateFunction.mail);

$("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname);
$("#hncompanyarea").jdValidate(validatePrompt.companyarea, validateFunction.companyarea);
$("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyaddr);
$("#companysite").jdValidate(validatePrompt.companysite, validateFunction.companysite);
$("#mobileCode").jdValidate(validatePrompt.mobileCode, validateFunction.mobileCode);

$("#viewpwd").bind("click", function () {
    if ($(this).attr("checked") == true) {
        validateFunction.showPassword("text");
        $("#o-password").addClass("pwdbg");
    } else {
        validateFunction.showPassword("password");
        $("#o-password").removeClass("pwdbg");
    }
});

$("select[@name2='hncompanyarea']").bind("change", function () {
    var elements = $("select[@name2='hncompanyarea']");
    if (validateFunction.checkSelectGroup(elements)) {
        $("#hncompanyarea").val("1");
        $("#hncompanyarea").attr("sta", 2);
        $("#hncompanyarea_error").html("");
        $("#hncompanyarea_error").removeClass();
        $("#hncompanyarea_succeed").addClass("succeed");
    } else {
        $("#hncompanyarea").val("-1");
        $("#hncompanyarea").attr("sta", 0);
        $("#hncompanyarea_error").html("");
        $("#hncompanyarea_succeed").removeClass("succeed");
    }
});

$("input:checkbox[@name='purposetype']").bind("click", function () {
    var value1 = $("#purpose").val();
    var value2 = $(this).val();
    if ($(this).attr("checked") == true) {
        if (value1.indexOf(value2) == -1) {
            $("#purpose").val(value1 + value2);
            $("#purpose").attr("sta", 2);
            $("#purpose_error").html("");
            $("#purpose_error").removeClass();
            $("#purpose_succeed").addClass("succeed");
        }
    } else {
        if (value1.indexOf(value2) != -1) {
            value1 = value1.replace(value2, "");
            $("#purpose").val(value1);
            if ($("#purpose").val() == "") {
                $("#purpose").attr("sta", 0);
                $("#purpose_succeed").removeClass("succeed");
            }
        }
    }
});

function protocolReg() {
    $("#closeBox").click();
    $("#registsubmit").click();
}

$("#registsubmit").click(function () {
    var agreeProtocol = checkReadMe();
    var regnameOk = validateRegName();
    var flag = validateFunction.emRegCompany_validate() && regnameOk && agreeProtocol;
    if (flag) {
        $("#registsubmit").attr({ "disabled": "disabled" }).removeClass().addClass("btn-img btn-regist wait-btn");
        $.ajax({
            type: "POST",
            url: "../reg/regService?r=" + Math.random(),
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: $("#formcompany").serialize(),
            success: function (result) {
                if (result) {
                    var obj = eval(result);
                    if (obj.info) {
                        $("#registsubmit").removeAttr("disabled").removeClass().addClass("btn-img btn-regist");
                        alert(obj.info);
                        verc();
                    }
                    if (obj.success == true) {
                        window.location = obj.dispatchUrl;
                    }
                }
            }
        });
    }
})
$(
    function () {
        refreshAreas(1, 0);
        function refreshAreas(level, parentId) {
            var myname;
            if (level == 1) {
                myname = "companycity";
                if (parentId == -1) {
                    $("#companycity").empty();
                    $("#companycity").append("<option value=\"-1\" selected>请选择</option>");
                    $("#companycity").css({"width": "auto"});
                    $("#companyarea").empty();
                    $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
                    $("#companyarea").css({"width": "auto"});
                }
            }
            else {
                myname = "companyarea";
                if (parentId == -1) {
                    $("#companyarea").empty();
                    $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
                    $("#companyarea").css({"width": "auto"});
                }
            }
            if (parentId > 0) {
                $.getJSON(
                    "../reg/area?level=" + level + "&parentId=" + parentId + "&r=" + Math.random(),
                    function (result) {
                        if (result.Areas != null) {
                            $("#" + myname).empty();
                            $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                            for (var i = 0; i < result.Areas.length; i++) {
                                var area = result.Areas[i];
                                $("#" + myname).append("<option  value=\"" + area.Id + "\">" + area.Name + "</option>");
                            }
                            $("#" + myname).css({"width": "Auto"});
                        }
                    });
            }
        }

        $("#companyprovince").change(
            function () {
                $("#hncompanyarea_error").removeClass();
                refreshAreas(1, parseInt($(this).val()));
                $("#companyarea").empty();
                $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
            }
        )

        $("#companycity").change(
            function () {
                $("#hncompanyarea_error").removeClass();
                refreshAreas(2, parseInt($(this).val()));
            });
    })

function checkReadMe() {
    if ($("#readme").attr("checked") == true) {
        $("#protocol_error").removeClass().addClass("error hide");
        return true;
    } else {
        $("#protocol_error").removeClass().addClass("error");
        return false;
    }
}

function agreeonProtocol() {
    if ($("#readme").attr("checked") == true) {
        $("#protocol_error").removeClass().addClass("error hide");
        return true;
    }
}

