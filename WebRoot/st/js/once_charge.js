//学员信息
function applicationdiv() {
    $(".noviceName").val($("#noviceName").val());
    $(".novicePhone").val($("#novicePhone").val());
    $(".idCard").val($("#idCard").val());
    $(".div-xy").show();
}

//教练车
function coachByCar(searchName) {
    var staffid = $("#coach_").text();
    $("#car_list").html("");
    var url = basePath + "/st/enroll/sajax_ea_coachByCar.jspa?staffId=" + staffid + "&carType=" + brand;
    if (searchName) {
        url = basePath + "/st/enroll/sajax_ea_coachByCar.jspa?staffId=" + staffid + "&carType=" + brand + "&searchName=" + searchName;
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var carList = member.carList;
            var str = [];
            if (carList != null && carList.length > 0) {
                for (i = 0; i < carList.length; i++) {
                    var entity = carList[i];
                    str.push('<li id=' + entity[3] + '>');
                    str.push('<img src=' + basePath + (entity[0] == null ? 'st/images/car.png' : entity[0]) + ' class="left">');
                    str.push('<div class="text">');
                    str.push('<h4>' + (entity[2] == null ? "" : entity[2]) + '</h4>');
                    str.push('<p></p>');
                    str.push('<p class="tel">' + entity[1] + '</p>')
                    str.push('</div>')
                    str.push('<img class="gou" src=' + basePath + 'st/new_images/ico-gou.png>');
                    str.push('</li>');

                }
                $("#car_list").append(str.join(""));
                $(".body4 .coach_list li").click(function () {
                    $(".body4 .search").val("")
                    var text4 = $(this).find(".text .tel").text();
                    $(".car_").text(text4);
                    $("#car_").text($(this).attr("id"));
                    $("input[name='enrollForm.carNumber']").val($(this).attr("id"));
                    $(".body4").hide();
                });
            }
        }
    })
}

/**
 * 教练
 * @param selectType
 * @param searchName
 */
function selectPeo(selectType, searchName) {
    $("#cos_list").html("");
    if (selectType == 1) {
        $(".body3 #selectPeo").text("选择教练");
        $(".body3 #search_text p").text("搜索教练姓名/手机号")
    } else if (selectType == 2) {
        $(".body3 #selectPeo").text("选择客服");
        $(".body3 #search_text p").text("搜索客服姓名/手机号")
    } else {
        $(".body3 #selectPeo").text("选择主管");
        $(".body3 #search_text p").text("搜索培训主管姓名/手机号")
    }
    var url = basePath + "/st/enroll/sajax_ea_selectPro.jspa?companyID=" + companyID + "&selectType=" + selectType + "&categoryName=" + categoryName;
    if (searchName) {
        url = basePath + "/st/enroll/sajax_ea_selectPro.jspa?companyID=" + companyID + "&selectType=" + selectType + "&searchName=" + searchName + "&categoryName=" + categoryName;
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var cosList = member.cosList;
            var str = [];
            if (cosList != null && cosList.length > 0) {
                for (i = 0; i < cosList.length; i++) {
                    var entity = cosList[i];
                    str.push('<li id=' + entity[0] + '>');
                    str.push('<img src=' + basePath + (entity[3] == null ? 'images/ea/driving/elkc/head.png' : entity[3]) + ' class="left">');
                    str.push('<div class="text">');
                    str.push('<h4 class="myh4">' + entity[1] + '</h4>');
                    str.push('<p>会员级别：' + entity[8] + '</p>');
                    str.push('<p class="tel">联系方式:<span>' + entity[4] + '</span></p>');
                    str.push('</div>');
                    str.push('<img class="gou" src=' + basePath + 'st/new_images/ico-gou.png>');
                    str.push('<input type="hidden" class="ljcustype" value="' + entity[7] + '"/>');
                    str.push('<input type="hidden" class="ljsccid" value="' + entity[5] + '"/>');
                    str.push('<input type="hidden" class="ljaccount" value="' + entity[6] + '"/>');
                    str.push('<img class="gou" src=' + basePath + 'st/new_images/ico-gou.png>');
                    str.push('</li>');
                }
                $("#cos_list").append(str.join(""));
                $(".body3 .coach_list li").click(function () {
                    $(".body3 .search").val("")
                    var text3 = $(this).find(".text h4").text();
                    if (selectType == 1) {
                        $(".coach_").text(text3);
                        $("#coach_").text($(this).attr("id"));
                        var jlcustype = $(this).find(".ljcustype").val();
                        var custype = $("#custype").val();
                        $("input[name='enrollForm.coachStaffID']").val($(this).attr("id"));
                        $("input[name='enrollForm.coachSccID']").val($(this).find(".ljsccid").val());
                        $("input[name='enrollForm.coachName']").val($(this).find("h4").text());
                        //findDeviceBind($(this).find(".ljsccid").val());
                        if ((supername == "17310850569" || supername == "" || supername == null) && (jlcustype < 6 && custype == 7)) {
                            $("#marketing").val($(this).find("h4").text() + " " + $(this).find(".ljaccount").val());
                            $("#msccid").val($(this).find(".ljsccid").val());
                            $("input[name='enrollForm.reserved1']").val($(this).find(".ljsccid").val());
                            $("input[name='enrollForm.reserved2']").val($(this).find(".ljaccount").val());
                        }
                    } else if (selectType == 2) {
                        $(".service_").text(text3);
                        $("#service_").text($(this).attr("id"));
                        $("input[name='enrollForm.suppoutStaffID']").val($(this).attr("id"));
                        $("input[name='enrollForm.suppoutName']").val($(this).find("h4").text());
                    } else if (selectType == 3) {
                        $(".charge_").text(text3);
                        $("#charge_").text($(this).attr("id"));
                        $("input[name='enrollForm.directorStaffID']").val($(this).attr("id"));
                        $("input[name='enrollForm.directorName']").val($(this).find("h4").text());
                    }
                    $(".body3").hide();
                });
            }
        }
    })
}

//验证
function verifyApplication() {
    var flag=true;
    var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
    var payfee = $(".mey").text();
    var jly = $(".coach_").text();
    if ($("#noviceName").val()==""||$("#noviceName").val()==null||$("#novicePhone").val()==""||$("#novicePhone").val()==null){
        flag=false;
        $(".title-input").val("01");
        $(".titlep").text("请填写学员信息");
        $(".titlep").attr("style","");
        $(".div-tingyong").show();
    }else if (reg.test($("input[name='enrollForm.idCard']").val()) == false) {
        flag=false;
        $(".title-input").val("01");
        $(".titlep").text("身份证错误");
        $(".div-tingyong").show();
        $("input[name='enrollForm.idCard']").focus();
    }else if ($("#noviceAddress").val() == "") {
        flag=false;
        $(".title-input").val("02");
        $(".titlep").text("请填写地址信息");
        $(".titlep").attr("style","");
        $(".div-tingyong").show();
        return;
    } else if (jly == null || jly == "") {
        flag=false;
        $(".title-input").val("03");
        $(".titlep").text("请选择教练员");
        $(".titlep").attr("style","");
        $(".div-tingyong").show();
        return;
    } else if ($("input[name='enrollForm.payMethod']").val() == null || $("input[name='enrollForm.payMethod']").val() == "") {
        flag=false;
        $(".title-input").val("04");
        $(".titlep").text("请选择支付方式");
        $(".titlep").attr("style","");
        $(".div-tingyong").show();
        $(".term_li").css("border-bottom-color", "red");
        $(".li-zffs").css("border-bottom-color", "red");
        return;
    } else if (payfee == "" || payfee == 0) {
        flag=false;
        $(".title-input").val("05");
        $(".titlep").text("数据错误");
        $(".titlep").attr("style","");
        $(".div-tingyong").show();
        return;
    } else {
        $("#reference").val($("#novicePhone").text());
        $("#staffName").val($("#noviceName").text());
    }
    return flag;
}

//提交订单
function oederBtn() {
    //var ppid = $("#class_").text();
    var goodsID = $("#goodsID").text();
    var ccompanyId = $("#ccomid").text();
    var coachStaffID = $("#coach_").text();
    var enrollfee = $("#enrollfee").text();
    var flag=verifyApplication();
    if (!flag)return false;
    var buyIsOkPage = "buyIsOkPage";
    var url = basePath + "st/enroll/sajax_ea_saveEnroll.jspa";
    var from = $("#enrollSubmit").serialize()
    priceType = $("#priceType").val();
    activityid = $("#activityid").val();
    $.ajax({
        url: encodeURI(url),
        type: "post",
        processData: false,
        data: from,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var enrollID = member.enrollID;
            //促销品id ljc

            $(".con-ord_grd").each(function () {
                var temp = $(this).find("#ptppid").val();
                var pt = $(this).find("h3").text();
                ptppid += temp + ",";
                ptstandard += (pt == "" ? "无" : pt) + ",,";
            });
            if (journalNum != null && journalNum != "") {
                //验证是否重复报名
                validateEnroll(mode, enrollID);
            } else {
                ajaxsut(enrollID);
            }
        },
        error: function (data) {
            $(".titlep").text("网络问题，请稍后再试！");
            $(".titlep").attr("style","");
            $(".div-tingyong").show();
        }
    });
}

/**
 * @param searchName  查询参数
 * 场地
 */
function findSite(searchName) {
    $("#site_list").html("");
    var url = basePath + "/st/enroll/sajax_ea_findSite.jspa?companyID=" + companyID;
    if (searchName) {
        url = basePath + "/st/enroll/sajax_ea_findSite.jspa?companyID=" + companyID + "&searchName=" + searchName;
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var siteList = member.siteList;
            var str = [];
            if (siteList != null && siteList.length > 0) {
                for (i = 0; i < siteList.length; i++) {
                    var entity = siteList[i];
                    str.push('<li id=' + entity[0] + '>');
                    str.push('<img src=' + basePath + (entity[3] == null ? 'st/images/car.png' : entity[3]) + ' class="left">');
                    str.push('<div class="text">');
                    str.push('<h4>' + entity[1] + '</h4>');
                    str.push('<p></p>');
                    str.push('<p class="tel"><img src=' + basePath + 'st/new_images/ico-locat.png>' + entity[2] + '</p>')
                    str.push('</div>')
                    str.push('<img class="gou" src=' + basePath + 'st/new_images/ico-gou.png>');
                    str.push('</li>');
                }
                $("#site_list").append(str.join(""));
                $(".body7 .coach_list li").click(function () {
                    $(".body7 .search").val("")
                    var text1 = $(this).find(".text h4").text();
                    $(".site_").text(text1);
                    $("#site_").text($(this).attr("id"));
                    $("input[name='enrollForm.siteID']").val($(this).attr("id"));
                    $("input[name='enrollForm.siteName']").val($(this).find("h4").text());
                    $(".body7").hide();
                });
            }
        }
    })
}

/**
 *省、市、区地址联动
 * type s:省  x：县  q：区
 * sxqid 选中地址id
 */
function address(type, sxqid) {
    /*if (type!="stop"){*/
    $(".div-sxq").find("ul").empty();
    /*}*/
    var url = "";
    var showParam = true;
    if (type == "s") {
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
    } else if (type == "x") {
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa";
    } else {
        showParam = false;
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa";
    }
    $.ajax({
        url: basePath + url,
        type: "post",
        async: true,
        data: {
            "sdistrict.districtID": sxqid,
            "showParam": showParam
        },
        dataType: "json",
        success: function (data) {
            var subMember = eval("(" + data + ")");
            var country;
            if (type == "s") {
                country = subMember.city.districtCity
            } else {
                country = subMember.district.country;
            }
            var comtypeHtml = [];
            if (country != null && country.length > 0) {
                $("#type-input").val(type);
                $(country).each(function () {
                    comtypeHtml.push("<li id='" + $(this)[0] + "'>" + $(this)[1] + "</li>");
                });
                $(".div-sxq").find("ul").append(comtypeHtml);
                $(".div-sxq").show();
            }
        },
        error: function () {
            alert("地址获取失败！");
        }
    });
}

function EnrollFee() {
    var url = basePath + "/st/enroll/sajax_ea_EnrollFee.jspa?companyID=" + companyID;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var product = member.product;
            var str = [];
            if (product != null) {
                $(".details .text .btm .money span").text(product.price);
                //$(".mey").text(product.price);
                $("#enrollfee").text(product.ppID);
            } else {
                $(".titlep").text("该驾校没有报名费！");
                $(".titlep").attr("style","");
                $(".div-tingyong").show();
            }
        }
    })
}

//确认订单 生成订单方法
function ajaxsut(enrollID) {
    price = $(".mey").text();
    var url = basePath + "/ea/wfjshop/sajax_ea_validateOrdiGoods.jspa";
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        async: false,
        data: {
            ppid: ppid,
            enrollID: enrollID
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var login = m.login;
            if (posNum == null || posNum == "") {
                if (login == "login") {
                    document.location.href = basePath
                        + "page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
                var result = m.result;
                if (result != "") {
                    $(".titlep").text(result);
                    $(".div-tingyong").show();
                    return false;
                } else {
                    var noviceName = $("#noviceName").val();
                    var novicePhone = $("#novicePhone").val();
                    var noviceCode = $("input[name='enrollForm.idCard']").val();
                    var noviceAddress = $("#noviceAddress").val();
                    var staffAddressID = $("#addressDetailed").val();
                    var ulp = basePath
                        + "/ea/wfjshop/sajax_ea_Shopping.jspa?morre=" + price + "&goodsName=" + goodsName + "&indus=" + price + "&ppid=" + ppid + "&enrollId=" + enrollID + "&count=1&sort=" + goodsName
                        + "&noviceName=" + noviceName + "&novicePhone=" + novicePhone + "&noviceCode=" + noviceCode + "&noviceAddress=" + noviceAddress + "&staffAddress.addressID=" + staffAddressID;
                    $.ajax({
                        type: "POST",
                        url: ulp,
                        async: false,
                        dataType: "json",
                        data: {
                            ptppid: ptppid,
                            ptstandard: ptstandard,
                            activityid: activityid,
                            priceType: priceType
                        },
                        success: function (data) {
                            var json = eval('(' + data + ')');
                            if (json == null) {
                                $(".titlep").text("数据提交失败。请重试");
                                $(".titlep").attr("style","");
                                $(".div-tingyong").show();
                            } else {
                                ddid = json.ddid;
                                if (ddid == "fail") {
                                    $(".titlep").text("该推荐人没有注册过微分金，请重新填写，或者不填写！");
                                    $(".titlep").css("line-height","1.5rem");
                                    $(".div-tingyong").show();
                                    return false;
                                }
                                if (price == 0) {
                                    window.location.href = basePath + "st/buyIsOkPage.jsp";
                                } else {
                                    var companyName = json.companyName;
                                    /*$(".wfj11_015_bottom").css("display", "none");
                                    $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                                    $(".wfj11_015_buy_commit").fadeIn(1000);*/
                                    $(".div-commit").show();
                                }
                            }
                        },
                        error: function (data) {
                            $(".titlep").text("提交订单失败");
                            $(".titlep").attr("style","");
                            $(".div-tingyong").show();
                        }
                    });
                }
            }
        }
    });
}

//备份信息
function ajaxPayBackUp(enrollID) {
    var noviceName = $("#noviceName").val();
    var novicePhone = $("#novicePhone").val();
    var noviceCode = $("input[name='enrollForm.idCard']").val();
    var noviceAddress = $("#noviceAddress").val();
    var staffAddressID = $("#addressDetailed").val();
    var ulp = basePath
        + "/ea/sm/sajax_ea_ajaxPayBackUp.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        data: {
            "payBackupBill.addressID": staffAddressID,
            "payBackupBill.noviceName": noviceName,
            "payBackupBill.novicePhone": novicePhone,
            "payBackupBill.noviceCode": noviceCode,
            "payBackupBill.noviceAddress": noviceAddress,
            "payBackupBill.enrollID": enrollID,
            "payBackupBill.journalNum": journalNum,
            "payBackupBill.ptppid": ptppid,
            "payBackupBill.ptstandard": ptstandard
        },
        dataType: "json",
        success: function (data) {
            ddid = journalNum;
            if (mode == "scan") {
                /*$(".wfj11_015_bottom").css("display", "none");
                $(".wfj11_015_buy_commit").css("z-index", $("#occlusion2").css("z-index") + 1);
                $(".wfj11_015_buy_commit").fadeIn(1000);*/
                $(".div-commit").show();
            } else if (mode == "scard") {//购物卡支付
                //跳回输入密码页面如果有密码的话
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&paymentCode=" + paymentCode + "&sccId=" + sccId + "&vipmoney=" + vipmoney + "&openid=" + openid;

            } else if (mode == "cash") {//现金支付
                document.location.href = basePath + "ea/sm/ea_showCash.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&comID=" + companyID + "&address=1";

            } else if (mode == "face") {//刷脸支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&comID=" + companyID + "&companyName=" + companyName;

            } else if (mode == "facecard") {//刷脸购物卡支付
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&comID=" + companyID + "&companyName=" + companyName + "&sccId=" + sccId + "&zf=1";
            }
        },
        error: function (data) {
            console.log("备份信息");
        }
    });
}

//支付
function zf() {
    if (zffs == null) {
        $(".titlep").text("请选择支付方式");
        $(".div-tingyong").show();
        return false;
    } else {

        if (typeof (ddid) == "undefined") {
            $(".titlep").text("订单提交失败，无法支付");
            $(".div-tingyong").show();
            return;
        }

        var goodsname = "(" + $(".name>h5").text() + ")" + goodsName;
        if (goodsname.length > 200) {
            goodsname = goodsname.substr(0, 200) + "...";
        }


        if (zffs == '1') {
            var par = [];
            par.push(basePath);
            if (journalNum != "" && journalNum != null) {
                par.push("page/ea/main/marketing/supermarket/selfservice/wfjAlipay.jsp?");
            } else {
                par.push("page/WFJClient/ShopOwner/wfjAlipay.jsp?");
            }
            par.push("WIDout_trade_no=" + ddid);

            par.push("&WIDtotal_fee=" + price);

            par.push("&WIDsubject=" + goodsname);
            if (journalNum != null && journalNum != "") {
                par.push("&WIDbody=selfpay");//订单描述
            } else {
                par.push("&WIDbody=''");//订单描述
            }
            par.push("&WIDit_b_pay=''");//超时时间
            par.push("&WIDextern_token=''");//钱包
            par.push("&buyIsOkPage=buyIsOkPage");
            //window.location.href = encodeURI(par.join(""));
            _AP.pay(encodeURI(par.join("")));
        } else if (zffs == '2') {
            // $("#formsutm").attr(
            //     "action",
            //     basePath + "ea/wfjshop/ea_zfgs.jspa?ddid=" + ddid+"morre="+price
            //     + "&baseUrl=" + basePath+"&code=2&buyIsOkPage="+buyIsOkPage);
            // $("#submit").click();
            window.location.href = basePath + "ea/wfjshop/ea_zfgs.jspa?ddid=" + ddid + "&morre=" + price
                + "&baseUrl=" + basePath + "&code=2&buyIsOkPage=buyIsOkPage"
        } else if (zffs == '3') {

            var money = price;
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            var attach = "buyIsOkPage";
            if (journalNum != "" && journalNum != null) {
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
                        "payDto.orderId": ddid,
                        "payDto.totalFee": money,
                        "payDto.body": goodsname,
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
                        try {
                            if (isAndroid == true) {
                                Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, ddid, err_code, goodsname, attach);
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
                                    'goodsname': goodsname,
                                    'attach': attach
                                };
                                for (var i in params) {
                                    url = url + "&" + i + "=" + params[i];
                                }
                                window.webkit.messageHandlers.Native.postMessage(url);
                            }
                        } catch (err) {
                            $(".titlep").text("微信支付升级中，请改用其他支付方式");
                            $(".div-tingyong").show();
                        }

                    }

                });

            } else {
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params=" + ddid + "-" + goodsname + "-" + money + "-" + staffID + "-1-1-" + attach + "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

            }
            //return false;
        } else if (zffs == '5') {
            //钱盒子支付
            // var morre = $("#morre").val();
            document.location.href = basePath + "ea/wfjshop/ea_moneyBoxPay.jspa?ddid=" + ddid + "&morre=" + price;
            return false;
        } else {
            if (token != 0) {
                return;
            }
            token = 1;
            $.ajax({
                url: basePath + "ea/wfjshop/sajax_ea_changeBillState.jspa",
                type: "get",
                data: "fenlei=03&ddid=" + ddid,
                success: function suc(data) {
                    var mb = eval("(" + data + ")");
                    var succ = mb.succ;
                    var threeNo = mb.threeNo;
                    if (succ == "success") {
                        window.location.href = basePath + "page/WFJClient/suc/xxzf.jsp?threeNo=" + threeNo + "&user1=" + user1;
                    }
                }
            });

        }
    }
}

//新增临时地址，不保存到数据库，因为没有用户
function addAddress() {
    var backurls = window.location.href;
    if (backurls.indexOf("&staffAddress.addressID=") != -1) {
        backurls = backurls.substring(0, backurls.indexOf("&staffAddress.addressID="));

    }
    if (mode == "cash" || mode == "face") {
        if (backurls.indexOf("&staffAddress.consignee=") != -1) {
            backurls = backurls.substring(0, backurls.indexOf("&staffAddress.consignee="))

        }
        document.location.href = basePath + "ea/wfjshop/ea_toaddAddress.jspa?backurls=" + encodeURIComponent(backurls);
    } else if (mode == "scard" || mode == "facecard") {
        if (backurls.indexOf("&staffAddress.addressID=") != -1) {
            backurls = backurls.substring(0, backurls.indexOf("&staffAddress.addressID="))

        }
        document.location.href = basePath + "ea/wfjshop/ea_getAddressList.jspa?backurls=" + encodeURIComponent(backurls) + "&intf=31&staffid=" + staffId;

    } else if (mode == "scan") {
        document.location.href = basePath + "ea/wfjshop/ea_getAddressList.jspa?backurls=" + encodeURIComponent(backurls);
    } else {
        document.location.href = basePath + "ea/wfjshop/ea_getAddressList.jspa?backurls=" + encodeURIComponent(backurls) + "&intf=20";


    }
}

//弹框
function prompt(obj) {
    $("#prompt").find("span").text("");
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
        $("#prompt").fadeOut(500);
    }, 2000);
}

//验证重复报名
function validateEnroll(mode, enrollID) {
    var account = "";
    if (mode == "cash") {
        account = $("#novicePhone").val();
    }
    $.ajax({
        url: basePath + "/st/enroll/sajax_ea_validateEnroll.jspa",
        type: "get",
        async: false,
        data: {
            mode: mode,
            account: account,
            cardNum: cardNum
        },
        success: function suc(data) {
            var mb = eval("(" + data + ")");
            var tips = mb.result;
            if (tips != "") {
                $(".titlep").text(tips);
                $(".div-tingyong").show();
                return false;
            } else {
                ajaxPayBackUp(enrollID);
            }
        }
    });

}

//查询设备投资责任人
function findDeviceBind(sccid) {
    $("input[name='enrollForm.operatingStaffId']").val("");
    $("input[name='enrollForm.operatingStaffName']").val("");
    $("input[name='enrollForm.operatingSccid']").val("");
    $("input[name='enrollForm.operatingAcount']").val("");
    $(".operatingStaffName").val("");
    var b=true;
    $.ajax({
        url: basePath + "/st/enroll/sajax_ea_selectSbtz.jspa",
        type: "get",
        async: false,
        data: {
            sccid: sccid,
            companyID: companyID
        },
        success: function (data) {
            var mb = eval("(" + data + ")");
            var phone = mb.Phone;
            var sccid = mb.sccid;
            var staffid = mb.staffid;
            var staffname = mb.staffname;
            if (staffid != "" && typeof (staffid) != "undefined") {
                $("input[name='enrollForm.operatingStaffId']").val(staffid);
                $("input[name='enrollForm.operatingStaffName']").val(staffname);
                $("input[name='enrollForm.operatingSccid']").val(sccid);
                $("input[name='enrollForm.operatingAcount']").val(phone);
                /*$(".term_li").each(function () {
                    if ($(this).is(".active")) {
                        var slname = staffname;
                        if (staffname.length > 7) {
                            slname = staffname.substring(0, 7) + "...";
                        }
                        $(this).find(".operatingStaffName").val(slname + " " + phone);
                    }
                });*/
                var payMethod=$("input[name='enrollForm.payMethod']").val();
                if (payMethod!=null&&(payMethod=="01"||payMethod=="021")){
                    var slname = $("input[name='enrollForm.operatingStaffName']").val();
                    var tel=$("input[name='enrollForm.operatingAcount']").val();
                    tel=tel.replace(tel.substring(3,7), "****");
                    $(".operatingStaffName").val(slname + " " + tel);
                }
            } else {
                b=false;
                //$(".div-zffs").hide();
                $("input[name='enrollForm.operatingStaffId']").val("");
                $("input[name='enrollForm.operatingStaffName']").val("");
                $("input[name='enrollForm.operatingSccid']").val("");
                $("input[name='enrollForm.operatingAcount']").val("");
                $("input[name='enrollForm.operatingFee']").val("");
                $("input[name='enrollForm.payMethod']").val("");
                $(".operatingStaffName").val("");
                $(".operatingFee").val("");
                $(".titlep").text("教练没有绑定车辆，请联系工作人员进行绑定！");
                $(".titlep").css("line-height","1.5rem");
                $(".div-tingyong").show();
            }
        }
    });
    return b;
}

//格式化价格数字
function changeTwoDecimal(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        $(".titlep").text('function:changeTwoDecimal->parameter error');
        $(".titlep").css("line-height","1.5rem");
        $(".div-tingyong").show();
        return false;
    }
    f_x = Math.round(f_x * 100) / 100;

    return f_x;
}

$(function () {
    var pr = changeTwoDecimal(((vipmoney == null && vipmoney == "") ? vipmoney : price) - sbtzfee);
    if (pr > 0) {
        $(".xzf").text(pr);
        $(".mey").text(pr);
        $("input[name='enrollForm.price']").val(pr);
    }
    //判断是否展示公司帐号
    //var mar = $("#marketing").val().trim();
    var sbsccid = $("#msccid").val().trim();
    if (mphone == null || mphone == "" || mphone == "17310850569") {
        $.ajax({
            url: basePath + "/st/enroll/sajax_ea_findComShopCus.jspa",
            type: "get",
            async: false,
            data: {
                companyID: companyID
            },
            success: function suc(data) {
                var mb = eval("(" + data + ")");
                var comPhone = mb.comPhone;
                var comsccid = mb.comsccid;
                var comstaffid = mb.comstaffid;
                if (comPhone != "") {
                    $("#marketing").val($(".name h5").text() + " " + comPhone);
                    $("#msccid").val(comsccid);
                    $("input[name='enrollForm.reserved1']").val(comsccid);
                    $("input[name='enrollForm.reserved2']").val(comPhone);
                    sbsccid = comsccid;
                }
            }
        });
    }

    /*if (sbsccid != null && sbsccid != "") {
        findDeviceBind(sbsccid);
    }*/

    $(".bottom_xy").click(function () {
        var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
        if ($(".noviceName").val()==""||$(".noviceName").val()==null||$(".novicePhone").val()==""||$(".novicePhone").val()==null){
            $(".titlep").text("请填写学员信息");
            $(".titlep").attr("style","");
            $(".div-tingyong").show();
            return;
        }else if (reg.test(($(".idCard").val()) == false)){
            $(".titlep").text("身份证错误");
            $(".div-tingyong").show();
            $("input[name='enrollForm.idCard']").focus();
            return;
        }
        var deviceList=$(".deviceList").val();
        var input = document.getElementById('headImageFile');
        var img = document.getElementById('headImage');
        var src = img.getAttribute('src');
        if(deviceList=="show"){
            //当前公司绑定人脸识别设备，需上传人脸
            if (input.files.length <= 0 && src==basePath) {
                $(".titlep").text("请上传学员真实头像");
                $(".titlep").attr("style","");
                $(".div-tingyong").show();
                return;
            }
        }
        updateStaffInfo(input,src);
        //发起ajax请求，将姓名，身份证号，头像保存数据库
        $("#noviceName").val($(".div-xy").find(".noviceName").val());
        $("#novicePhone").val($(".div-xy").find(".novicePhone").val());
        $("#idCard").val($(".div-xy").find(".idCard").val());
        $(".div-xy").hide();
    });
    function updateStaffInfo(fileInput,src){
        $("#loading").fadeIn();
        var formData = new FormData();
        var file = fileInput.files[0];
        //correctOrientation(file);
        formData.append('headImageFile', file);
        formData.append('realname',$(".div-xy").find(".noviceName").val());
        formData.append('staffIdentityCard',$(".div-xy").find(".idCard").val());
        formData.append('staffId',staffId);
        formData.append('photoPath',src==basePath?"":src);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/st/enroll/sajax_ea_updateStaffInfo.jspa', true);
        xhr.onload = function() {
            $("#loading").fadeOut();
        };
        xhr.send(formData);
    }
    /*$("input").focus(function () {
        /!*$("input").css("border","0");*!/
        $("input").css("text-align","right");
        $("input").css("background-color","#fff");
        /!*$(this).css("background-color","#eeeeee");*!/
        $(this).css("text-align","left");
    });*/

    $(".close-confirm").click(function () {
        $(".titlep").text("");
        $(".titlep").attr("style","");
        var confirmtype=$(".title-input").val();
        if(confirmtype!=null&&confirmtype!=""){
            if (confirmtype=="01"){
                applicationdiv();
            }else if (confirmtype=="02"){
                $("#noviceAddress").trigger("click");
            }else if (confirmtype=="03"){
                $(".coach").trigger("click");
            }else if (confirmtype=="04"){
                $(".li-zffs").trigger("click");
            }else if (confirmtype=="05"){
                $(".bottom_xy").show();
            }
        }
        $(".div-tingyong").hide();
    });

    // 查询框隐藏
    $(".div-xy").click(function () {
        $(".div-xy").hide();
    });

    $(".div-xy .box").click(function (e) {
        e.stopPropagation();
    });

    // 查询框隐藏
    $(".div-sxq").click(function () {
        $(".div-sxq").hide();
    });

    /*$(".div-sxq .box").click(function (e) {
        e.stopPropagation();
    });*/

    //显示支付方式页面
    $(".term_li,.li-zffs").click(function () {
        $(".glFee").val($("input[name='enrollForm.price']").val());
        $(".sumfee").val($("input[name='enrollForm.price']").val());
        var payMethod=$("input[name='enrollForm.payMethod']").val();
        var coachSccID=$("input[name='enrollForm.coachSccID']").val();
        if(coachSccID==null||coachSccID==""){
            $(".title-input").val("03");
            $(".titlep").text("还没有选择教练！");
            $(".titlep").attr("style","");
            $(".div-tingyong").show();
            return false;
        }
        $(".glFee-span").text($("input[name='enrollForm.price']").val());
        if (payMethod == null || payMethod == "") {
            $("input[name='enrollForm.payMethod']").val("021");
            $(".onlyfee-label").trigger("click");
            var b= findDeviceBind(coachSccID);
            if(!b) return b;
        }else {
            if (payMethod=="01"){
                $("#title1").trigger("click");
            }else {
                if (payMethod=="021"){
                    $(".onlyfee-label").trigger("click");
                }else {
                    $(".timefee-label").trigger("click");
                }
            }
        }
    });

    $("#noviceAddress").click(function () {
        //获取当前地址解析后，放到对应的省市县
        var txt=$("#noviceAddress").val();
        if(txt!=null){
            let sReg = /市|省|县|区|行政单位|盟|旗|海域|岛|街道办事处|街道|乡|镇/g;
            let regRes = txt.match(sReg);
            console.log(regRes)
            if(regRes!=null){
                var $i = txt.indexOf(regRes[0]) + regRes[0].length;
                var province = txt.substring(0, $i);
                $("#s").val(province);
                var provinceCode=getAddressByTextInfo('s',province,0)
                console.log(provinceCode+"==");
                $("#sid").val(provinceCode);
                if(regRes.length>1){
                    var item2 = txt.slice($i);
                    var $i2 = item2.indexOf(regRes[1]) + regRes[1].length;
                    var city = item2.substring(0, $i2);
                    $("#x").val(city);
                    var cityCode=getAddressByTextInfo('x',city,provinceCode);
                    console.log(cityCode+"==");
                    $("#xid").val(cityCode);
                    if(regRes.length>2){
                        var item3 = item2.slice($i2);
                        var $i3 = item3.indexOf(regRes[2]) + regRes[2].length;
                        var country = item3.substring(0, $i3);
                        $("#q").val(country);
                        var item4 = item3.slice($i3);
                        $("#xx").val(item4);
                    }
                }
            }
        }
        $(".div-address").show();
        $(this).parent().attr("style", "border-bottom-color:#ddd");
    });
    function getAddressByTextInfo(type,textInfo,textCode){
        var codeInfo;
        //查询当前省市区的code码
        $.ajax({
            url: basePath + "/st/enroll/sajax_ea_getAddressByTextInfo.jspa",
            type: "post",
            async: false,
            data: {
                "textType": type,
                "textInfo": textInfo,
                "textCode": textCode
            },
            dataType: "json",
            success: function (data) {
                if(data!=null){
                    var subMember = eval("(" + data + ")");
                    var list=subMember.siteList;
                    if(list.length>0){
                        codeInfo= list[0];
                    }
                }
            }
        });
        return codeInfo;
    }
    //切换加载地址数据
    $(".address-div").click(function () {
        var type;
        var sxqid;
        if ($(this).attr("title") == "省") {
            type = "s";
        } else if ($(this).attr("title") == "县" && $("#s").val().length > 0) {
            type = "x";
            sxqid = $("#sid").val();
        } else if ($(this).attr("title") == "区" && $("#x").val().length > 0) {
            type = "q";
            sxqid = $("#xid").val();
        }
        address(type, sxqid);/*
        var id=$(this).find(".c").val();
        $("li #"+id).addClass("active");*/
    });

    //地址传入选项
    $(document).on("click", ".div-sxq li", function () {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        var id = $(this).parents().find("#type-input").val();
        $("#" + id).val($.trim($(this).text()));
        $("#" + id + "id").val($.trim($(this).attr("id")));
        $("#address").val($.trim($(this).attr("id")));
        $(this).parents(".div-sxq").hide();
    });

    $(".isok").click(function () {
        var isNoll = false;
        /*$(".notnull").each(function () {
            if ($(this).val().length <= 0) {
                isNoll = true;
            }
        });*/
        if ($("#xx").val().length <= 0) {
            $(".titlep").text("地址信息不完整");
            $(".titlep").attr("style","");
            $(".div-tingyong").show();

        } else {
            $("#noviceAddress").val($("#s").val() + $("#x").val() + $("#q").val() + $("#xx").val());
            $("input[name='enrollForm.staffAddress']").val($("#s").val() + $("#x").val() + $("#q").val() + $("#xx").val());
            $(".div-address").hide();
        }
    });

    $(".div-close").click(function () {
        $(".div-data").hide();
    });

    //搜索
    $(".search_frd input").focus(function () {
        $(".search_frd .search_").hide();
    });

    $(".search_frd input").blur(function () {
        if ($(".search_frd input").val() == "") {
            $(".search_frd .search_").show();
        } else {
            $(".search_frd .search_").hide();
        }
    });

    $(".search_frd .search_").click(function () {
        $(".search_frd .search_").hide();
        $(".search_frd input").focus();
    });

    $(".search").on("input", function () {
        var ptext = $("p", $(this).siblings('.search_')).text()
        if (ptext == "搜索教练姓名/手机号") {
            $("#cos_list").empty();
            var searchName = $(this).val();
            selectPeo(1, searchName);
            searchName = "";
        } else if (ptext == "搜索场地名称") {
            $("#site_list").empty();
            var searchName = $(this).val();
            console.info(searchName);
            findSite(searchName);
            searchName = "";
        }
    });

    //列表选择
    $(".coach_list li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    });

    $("header ul li img").click(function () {
        $(".body3").hide();
        $(".body4").hide();
        $(".body7").hide();
    });

    $("input[name='enrollForm.idCard']").click(function () {
        $(this).parent().attr("style", "border-bottom-color:#ddd");
    });
    //选择教练员
    $(".coach").click(function () {
        $(".body3").show();
        $(this).attr("style", "border-bottom-color:#ddd");
    });
    //选择教练车
    $(".car").click(function () {
        if ($("#coach_").text() == "") {
            $(".title-input").val("03");
            $(".titlep").text("请选择教练！");
            $(".titlep").attr("style","");
            $(".div-tingyong").show();
            return;
        }
        $(".body4").show();
    });

    //选择场地
    $(".site").click(function () {
        $(".body7").show();
    });

    //选中
    /*$(".term_li .sex").click(function (event) {
        $(".term_li").attr("style", "border-bottom-color:#ddd");
        event.stopPropagation();
        var index = $(this).parent().attr("id");
        $(".mey").text($("input[name='enrollForm.price']").val());
        $(".xzf").text($("input[name='enrollForm.price']").val());
        $(".sumfee").val($("input[name='enrollForm.price']").val() + "元");
        $("input[name='enrollForm.operatingFee']").val("");
        if ($(this).parent().is(".active")) {
            $(this).parent().find("input").attr("disabled", true);
            $(this).parent().find(".sumfee").attr("style", "color:black;");
            $(this).parent().find("input").val("");
            $(this).parent().removeClass("active");
            $("input[name='enrollForm.payMethod']").val("");
        } else {
            var sccid = $("input[name='enrollForm.operatingStaffId']").val();
            if (sccid != null && sccid != "" && sccid != "null") {
                $(this).parent().find(".active .sumfee").attr("style", "color:black");
                $("input[name='enrollForm.payMethod']").val(index);
                $(this).parent().find(".active input").attr("disabled", true);
                $(this).parent().find(".active input").val("");
                $(this).parent().find(".active span").text($(".mey").text());
                $(this).parent().find(".active").removeClass("active");
                $(this).parent().find(".sumfee").attr("style", "color:red;");
                $(this).parent().find("input").attr("disabled", false);
                $(this).parent().addClass("active");
                var slname = $("input[name='enrollForm.operatingStaffName']").val();
                if (slname.length > 5) {
                    slname = slname.substring(0, 5) + "...";
                }
                $(this).parent().find(".operatingStaffName").val(slname + " " + $("input[name='enrollForm.operatingAcount']").val());
            } else {
            $(".titlep").text("没有选择教练或教练没有绑定车辆！请联系工作人员！");
            $(".titlep").css("line-height","1.5rem");
            $(".div-tingyong").show();
            }
        }
    });*/

    //选择合并支付还是分开支付
    $(".title-li").click(function () {
        var coachSccID=$("input[name='enrollForm.coachSccID']").val();
        if ($(this).attr("class").indexOf("active")<=0){
            $(".operatingFee").val("");
            $("input[name='enrollForm.operatingFee']").val("");
            $(".sumfee").val(pr);
        }
        var b= findDeviceBind(coachSccID);
        if(!b) return b;
        $(".title-li").removeClass("active");
        $(".title-n").show();
        $(".title-y").hide();
        $(".title2").hide();
        $(this).addClass("active");
        if ($(this).attr("id") == "title2") {
            $(".onlyfee-label").trigger("click");
            $("input[name='enrollForm.payMethod']").val("021");
            $(".title2").show();
        }else {
            $("input[name='enrollForm.payMethod']").val("01");
            $(".zffs-span").text("合并支付");
            $(".term_li span").css("color","red");
            var slname = $("input[name='enrollForm.operatingStaffName']").val();
            $(".operatingStaffName-span").text(slname + " " + $("input[name='enrollForm.operatingAcount']").val());
            $(".onlyfee-p").show();
            $(".timefee-p").hide();
            $(".onlyFee").show();
        }
        $(this).find(".title-y").show();
        $(this).find(".title-n").hide();
        $(".term_li,.li-zffs").css("border-bottom-color", "#ddd");
        $(".div-title1").show();
        $(".div-zffs").show();
    });

    //分开支付一次性结清点击
    $(".onlyfee-n,.onlyfee-label").click(function () {
        var coachSccID=$("input[name='enrollForm.coachSccID']").val();
        $(".onlyfee-n").hide();
        $(".onlyfee-y").show();
        $(".timefee-n").show();
        $(".timefee-y").hide();
        $(".onlyfee-label").css("color", "red");
        $(".onlyfee-label").css("font-size", "0.65rem");
        $(".timefee-label").css("color", "rgb(167 164 160)");
        $(".timefee-label").css("font-size", "0.6rem");
        $("input[name='enrollForm.payMethod']").val("021");
        var b= findDeviceBind(coachSccID);
        if(!b) return b;
        $(".zffs-span").text("分开支付--一次性付款");
        $(".term_li span").css("color","red");
        var slname = $("input[name='enrollForm.operatingStaffName']").val();
        $(".operatingStaffName-span").text(slname + " " + $("input[name='enrollForm.operatingAcount']").val());
        $(".onlyfee-p").show();
        $(".timefee-p").hide();
        $(".onlyFee").show();
        $(".term_li,.li-zffs").css("border-bottom-color", "#ddd");
        $(".div-title1").show();
        $(".div-zffs").show();
    });

    //分开支付计时收费点击
    $(".timefee-n,.timefee-label").click(function () {
        $(".onlyfee-n").show();
        $(".onlyfee-y").hide();
        $(".timefee-n").hide();
        $(".timefee-y").show();
        $(".timefee-label").css("color", "red");
        $(".timefee-label").css("font-size", "0.65rem");
        $(".onlyfee-label").css("color", "rgb(167 164 160)");
        $(".onlyfee-label").css("font-size", "0.6rem");
        $("input[name='enrollForm.operatingStaffId']").val("");
        $("input[name='enrollForm.operatingStaffName']").val("");
        $("input[name='enrollForm.operatingSccid']").val("");
        $("input[name='enrollForm.operatingAcount']").val("");
        $("input[name='enrollForm.operatingFee']").val("");
        $("input[name='enrollForm.payMethod']").val("022");
        $(".operatingStaffName").val("");
        $(".operatingFee").val("");
        $(".onlyFee").hide();
        $(".term_li,.li-zffs").css("border-bottom-color", "#ddd");
        $(".zffs-span").text("分开支付--计时收费");
        $(".term_li span").css("color","red");
        $(".xzf").text($(".sumfee").val());
        $(".mey").text($(".sumfee").val());
        $(".timefee-p").show();
        $(".onlyfee-p").hide();
        $(".div-title1").show();
        $(".div-zffs").show();
    });

    /*$(".operatingFee").click(function () {
        $(this).parent().parent().attr("style", "border-bottom:.1rem solid #f3eded");
    });*/
    //根据填写的操作费计算总金额
    $(".operatingFee").keyup(function () {
        var price = parseFloat($("input[name='enrollForm.price']").val());
        $(this).val($(this).val());
        var fee = parseFloat(($(this).val() == null || $(this).val() == "") ? "0" : $(this).val());
        $(".operatingFee-span").text(fee);
        $("input[name='enrollForm.operatingFee']").val(fee);
        if ($("input[name='enrollForm.payMethod']").val()!=null&&$("input[name='enrollForm.payMethod']").val()=="01") {
            $(".sumfee-span").text(price + fee);
        }else {
            $(".sumfee-span").text(price);
        }
    });

    /*$(".operatingStaffName").click(function () {
        $(this).attr("style","border-bottom-color:#ddd");
    });*/

    //支付方式保存
    $(".submitAudit").click(function () {
        var zffs=$("input[name='enrollForm.payMethod']").val();
        var fee = $("input[name='enrollForm.operatingFee").val();
        var feename = $("input[name='enrollForm.operatingStaffName']").val();
        if (zffs!=null && zffs!=""){
            if (zffs=="01"||zffs=="021") {
                if (fee == null || fee == "") {
                    $(".titlep").text("请填写操作费用");
                    $(".titlep").attr("style","");
                    $(".div-tingyong").show();/*
                    $(".operatingFee").parent().parent().attr("style", "border-bottom:0.1rem solid red;");*/
                    return false;
                } else if (feename == null || feename == "") {
                    $(".titlep").text("操作费收款人数据错误!请联系工作人员");
                    $(".titlep").css("line-height","1.5rem");
                    $(".div-tingyong").show();
                    $(".operatingStaffName").attr("style", "border-bottom-color:red;");
                    return false;
                }else {
                    $(".onlyfee-p").show();
                    $(".timefee-p").hide();
                    var slname = $("input[name='enrollForm.operatingStaffName']").val();
                    $(".operatingStaffName-span").text(slname + " " + $("input[name='enrollForm.operatingAcount']").val());
                    $(".operatingFee-span").text($("input[name='enrollForm.operatingFee").val());
                    $(".sumfee-span").text($(".sumfee").val());
                }
            }else if (zffs=="022"){
                $(".timefee-p").show();
                $(".onlyfee-p").hide();
            }
            zffs=(zffs=="01"?"合并支付":zffs=="021"?"分开支付--一次性付款":"分开支付--计时收费");
            $(".zffs_").text(zffs);
            $(".zffs-span").text(zffs);
            $(".glFee-span").text($("input[name='enrollForm.price']").val());
            $(".term_li span").css("color","red");
            $(".xzf").text($(".sumfee-span").text());
            $(".mey").text($(".sumfee-span").text());
            $(".term_li").show();
            $(".div-zffs").hide();
        }else {
            $(".titlep").text("教练没有绑定车辆，请联系工作人员进行绑定！");
            $(".titlep").css("line-height","1.5rem");
            $(".div-tingyong").show();
        }
    });

    //切换支付方式
    $(".wfj11_015_choice").click(function () {
        $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
        $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
        zffs = $(this).find(".second").find("img").attr("name");
    });

    /*$(".confi").click(function () {

    var flag=verifyApplication();
    if (!flag)return false;
    });*/


    var deviceList=$(".deviceList").val();
    if(deviceList=="show"){
        $(".headDIV").show();
        $(".personDIV").show();
    }
});
