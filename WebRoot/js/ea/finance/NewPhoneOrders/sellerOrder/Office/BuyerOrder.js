var t;
var pagenumber = 0;
var pagecount = 0;
var obj = "divUL";
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var url;
var pl = "01";

var xzdz = "<div class='clearfix xuanze03 div-bto2 div-sh' onclick='getAddress()'><div>";
xzdz += "<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img_04.png'/></div>";
xzdz += "<p class='btn'>选择收货地址</p></div>";

var xzzf = "<div class='clearfix zffs div-bto3 div-fkfs xuanze03' onclick='getPayMethod()'><div>";
xzzf += "<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img_05.png'/></div>";
xzzf += "<p class='btn'>选择付款方式</p></div>";

var isok = "<input type='button' id='inp-qd' value='确定'/>";

//扩展jquery的格式化方法
$.fn.parseForm = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    var str = this.serialize();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};

$(document).ready(function () {
    if (sort == 2) {
        $("body").removeClass("no-header");
    } else {
        $("body").addClass("no-header");
    }
    //点击切换
    $(document).on("click", ".ul-tab li p", function () {
        $(".ul-tab li p").removeClass("active");
        $(this).addClass("active");
        var pid = $(this).attr("id");
        var url = "";
        pagenumber = 0;
        pagecount = 0;
        if (pid == "wfk") {
            pl = "01";
        } else if (pid == "yfk") {
            pl = "03";
        } else if (pid == "qk") {
            pl = "02";
        }
        $("#" + obj).empty();
        loaded(obj, pl);
    });

    $(document).on("click", ".p-top", function () {
        $("#xz1").hide();
    });
    //高度计算
    var box_fh_hei = 0;
    $(".div-shr p").each(function () {
        box_fh_hei += $(this).outerHeight(true);
        // alert($(this).outerHeight(true))
    });

    $(".p-img").height(box_fh_hei + 5);
    $(".div-bto>div:first-of-type").height(box_fh_hei + 5);
    $("#xz1").hide();
    //宽度计算
    var box_fh_wid = 0;

    $(".ul-tab-fh li").each(function () {
        box_fh_wid += $(this).outerWidth(true);
    });

    $(".ul-tab-fh").width(box_fh_wid + 5);

    loaded(obj, pl);

    //查询
    $("#search").click(function () {
        if ($("#ss").val().trim() != null && $("#ss").val().trim() != "") {
            parameter = $("#ss").val();
            $("#" + obj).empty();
            loaded(obj, pl);
        }
    });

    $(document).on("click", "#inp-qd", function () {
        /*$("#zfzt").val();
         $("#cashid").val(id);*/
        if ($("#rname").val().trim() == null || $("#rname").val().trim() == "") {
            alert("请选择收货地址");
            return false;
        } else {
            if ($("#zfzt").val().trim() == "qk") {
                clearTimeout(t);
                $.ajax({
                    url: "ea/seller/sajax_ea_getOverdraftAjax.jspa?",
                    type: "post",
                    async: false,
                    dateType: "json",
                    data: {
                        "cashid": $("#cashid").val(),
                        "raddressId": $("#raddressId").val()
                    },//company201009046vxdyzy4wg0000000025
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var falg = member.falg;
                        if (falg != null && falg == "00") {
                            alert("操作成功");
                            window.location.href = "ea/seller/ea_getBuyerOrder.jspa?companyid=" + comid + "&staffid=" + staffid + "&sort=" + sort;
                        } else {
                            if (falg == "01") {
                                alert("操作失败");
                            }
                            if (falg == "02") {
                                alert("欠款单重复生成");
                            }
                        }
                    }
                });
            } else {
                if ($("#zffs").val().trim() == null || $("#zffs").val().trim() == "") {
                    alert("请选择支付方式");
                    return false;
                } else {
                    $.ajax({
                        url: "ea/seller/sajax_ea_addAddress.jspa?",
                        type: "post",
                        async: false,
                        dateType: "json",
                        data: {
                            "cashid": $("#cashid").val(),
                            "raddressId": $("#raddressId").val()
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var falg = member.falg;
                            if (falg != null && falg == "00") {
                                var goodsname = "";
                                $.ajax({
                                    url: "ea/seller/sajax_ea_getGoodsnameAjax.jspa?",
                                    type: "post",
                                    async: false,
                                    dateType: "json",
                                    data: {
                                        "cashid": $("#cashid").val(),
                                    },
                                    success: function (data) {
                                        var member = eval("(" + data + ")");
                                        goodsname = member.goodname;
                                        var companyname = $("#" + $("#cashid").val()).find(".companyName").text();
                                        goodsname = "(" + companyname + ")" + goodsname;
                                        goodsname = goodsname.substr(0, 200) + "...";
                                    }
                                });
                                var money = $.trim($("#money").val());
                                var ddid = $.trim($("#cashid").val());
                                var jumber = $.trim($("#jumber").val());
                                var zffs = $.trim($("#zffs").val());
                                if (zffs == "alipay") {
                                    var par = new Array();
                                    par.push("/page/WFJClient/GoodsShow/wfjAlipay.jsp?");
                                    par.push("WIDout_trade_no=" + jumber);
                                    par.push("&WIDtotal_fee=" + money);
                                    par.push("&WIDsubject=" + goodsname);
                                    par.push("&WIDbody=''");//订单描述
                                    par.push("&WIDit_b_pay=''");//超时时间
                                    par.push("&WIDextern_token=''");//钱包
                                    _AP.pay(encodeURI(par.join("")));
                                } else if (zffs == "weixin") {
                                    var ua = navigator.userAgent.toLowerCase();
                                    var isWeixin = ua.indexOf('micromessenger') != -1;
                                    var attach = "other";
                                    if (!isWeixin) {
                                        $(".loading").show();
                                        //app微信支付
                                        $.ajax({
                                            url: "ea/wfjshop/sajax_ea_appWechatPay.jspa",
                                            type: "get",
                                            data: {
                                                "payDto.orderId": jumber,
                                                "payDto.totalFee": money,
                                                "payDto.body": goodsname,
                                                "payDto.attach": attach
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
                                                        Android.callAndroidappChat(appid, partnerid, prepayid, packages, noncestr, timestamp, sign, jumber, err_code, goodsname, attach);
                                                    } else if (isiOS == true) {
                                                        var url = "func=" + 'ioscallappChat';
                                                        params = {
                                                            'appid': appid,
                                                            'partnerid': partnerid,
                                                            'prepayid': prepayid,
                                                            'noncestr': noncestr,
                                                            'timestamp': timestamp,
                                                            'sign': sign,
                                                            'journalNum': jumber,
                                                            'errcode': err_code,
                                                            'goodsname': goodsname,
                                                            'attach': attach
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
                                    }
                                }
                            } else {
                                if (falg == "01") {
                                    alert("操作失败");
                                }
                            }
                        }
                    });

                }
            }
        }
    });

    window.addEventListener('pageshow', function (e) {
        localforage.getItem('formxz1').then(function (value) {
            //当离线仓库中的值被载入时，此处代码运行
            console.log(value);
            if (value != null && value != "") {
                $("#cashid").val(value.cashid);
                $("#jumber").val(value.jumber);
                $("#money").val(value.money);

                if (value.rname != null && value.rname != "") {
                    var zsdz = "<div class='clearfix div-bto1 div-shr' onclick='getAddress()'><p class='p-img'>";
                    zsdz += "<img src='images/WFJClient/Newjspim/wfj11_address_01.png'/> </p>";
                    zsdz += "<div class='div-shr'><p>收货人：";
                    zsdz += "<span class='rname'>" + value.rname + "</span>";
                    zsdz += " <span class='rtel'>" + value.rtel + "</span></p>";
                    zsdz += "<p class='p-12'>收货地址：";
                    zsdz += "<span class='address'>" + value.address + "</span></p></div></div>";

                    $("#raddressId").val(value.raddressId);
                    $("#rname").val(value.rname);
                    $("#rtel").val(value.rtel);
                    $("#address").val(value.address);
                    $(".div-bto").append(zsdz);
                } else {
                    $(".div-bto").append(xzdz);
                }

                if (value.zfzt != null && value.zfzt == "fk") {
                    $("#zfzt").val(value.zfzt);
                    $(".p-top").text("付款 ￥" + value.money);
                    if (value.zffs == null || value.zffs == "") {
                        $(".div-bto").append(xzzf);
                    } else {
                        $("#zffs").val(value.zffs);
                        var zszf = "<div class='clearfix zffs div-bto3 div-zffs' onclick='getPayMethod()'><p>";
                        if (value.zffs == "alipay") {
                            zszf += "<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img_11.png'/></p>";
                            zszf += "<p>支付宝</p></div>";
                        } else {
                            zszf += "<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img_12.png'/></p>";
                            zszf += "<p>微信</p></div>";
                        }
                        $(".div-bto").append(zszf);
                    }
                } else if (value.zfzt != null && value.zfzt == "qk") {
                    $("#zfzt").val(value.zfzt);
                    $(".p-top").text("欠款 ￥" + +value.money);
                }

                $(".div-bto").append(isok);
                $("#xz1").show();
                localforage.removeItem('formxz1').then(function () {
                    console.log("移除！");
                }).catch(function (err) {
                    // 当出错时，此处代码运行
                    console.log(err);
                });
            }
        });
    });

});

//日期格式化
function format(date, format) {
    var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "h+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds(),
        "q+": Math.floor((date.getMonth() + 3) / 3),
        "S": date.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

//数据加载
function loaded(obj, pl) {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: "ea/seller/sajax_ea_getBuyerAjax.jspa?",
        type: "post",
        async: false,
        dateType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "companyid": comid,
            "pl": pl
        },//company201009046vxdyzy4wg0000000025
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var htl = new Array();
            if (pageForm != null) {
                var list = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
            }

            if (pageForm != null && pageForm.recordCount > 0) {
                for (var i = 0; i < list.length; i++) {
                    var pp = list[i];
                    if (i == list.length - 1) {
                        htl.push("<div class='last' id='" + pp.cashierBillsID + "'>");
                    } else {
                        htl.push("<div  id='" + pp.cashierBillsID + "'>");
                    }
                    htl.push("<ul class='ul-con grd sel_nub' onclick='CashBill(this)'>");
                    htl.push("<li >订单号：<span class='jumber'>" + pp.journalNum + "</span><span>");
                    //01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
                    if (pp.paystatus == "02") {
                        htl.push('欠款');
                    } else if (pp.paystatus == "03") {
                        htl.push('已付款');
                    } else if (pp.paystatus == "01") {
                        htl.push('未付款');
                    }
                    if (pp.supplierstatus == "00" || pp.supplierstatus == null) {
                        htl.push("/待拣货");
                    } else if (pp.supplierstatus == "01") {
                        htl.push('/拣货中');
                    } else if (pp.supplierstatus == "02") {
                        htl.push('/待发货');
                    } else if (pp.supplierstatus == "03") {
                        htl.push('/待送货');
                    } else if (pp.supplierstatus == "04") {
                        htl.push('/送货中');
                    } else if (pp.supplierstatus == "05") {
                        htl.push('/待收货');
                    } else if (pp.supplierstatus == "06") {
                        htl.push('/待检货');
                    } else if (pp.supplierstatus == "07") {
                        htl.push('/待入库');
                    } else if (pp.supplierstatus == "09") {
                        htl.push('/订单完成');
                    }
                    var date = new Date();
                    htl.push("</span></li>");
                    htl.push("<li>供货商：<span class='companyName'>" + pp.companyName + "</span></li>");
                    htl.push("<li>订货时间：" + format(date, "yyyy-MM-dd") + "</li>");
                    htl.push("<li>合计金额：<span class='money'>" + pp.priceSub + "</span></li>");
                    htl.push("<li>件数：" + pp.jianshu + "</li>");
                    htl.push("<li id='receivename'>收货人：<span class='receivename'>" + pp.receivename + "</span></li>");
                    htl.push("<li id='receivetel'>收货电话：<span class='receivetel'>" + pp.receivetel + "</span></li>");
                    htl.push("<li id='receiveaddress'>收货地址：<span class='receiveaddress'>" + pp.receiveaddress + "</span></li>");
                    /*htl.push("<input type='hidden' id='addressid'/>");*/
                    htl.push('<input type="hidden" id="fkStatus" value='
                        + pp.fkStatus + '>');
                    htl.push('<input type="hidden" id="casid" value="'
                        + pp.cashierBillsID + '">');
                    htl.push('<input type="hidden" id="journalNum" value="'
                        + pp.journalNum + '">');
                    htl.push('<input type="hidden" id="pl" value="'
                        + pl + '">');
                    htl.push('<input type="hidden" id="companyId" value="'
                        + pp.companyid + '">');
                    htl.push('<input type="hidden" id="wfStatus4" value="' + pp.wfStatus4 + '">');
                    htl.push("</ul>");
                    if (pp.paystatus == "01" || pp.paystatus == "02") {
                        htl.push("<section>");
                        if (pp.paystatus == "01") {
                            htl.push("<p class='p-4' onclick='qfClick(&#39;fk&#39;,&#39;" + pp.cashierBillsID + "&#39;)'><img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-04.png' >付款</p>");
                            htl.push("<p class='p-5' onclick='qfClick(&#39;qk&#39;,&#39;" + pp.cashierBillsID + "&#39;)'><img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-05.png'>欠款</p>");

                        } else if (pp.paystatus == "02") {
                            htl.push("<p class='p-4' onclick='qfClick(&#39;fk&#39;,&#39;" + pp.cashierBillsID + "&#39;)'><img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-04.png'>付款</p>");
                        }
                        htl.push("</section>");
                    }
                    htl.push("</div>");
                }
            } else {
                htl.push("<div class='no'>");
                htl.push("<li><i><img src='images/ea/finance/NewPhoneOrders/sellerOrder/wu.png' class='wu'></i>");

                htl.push("<p>目前还没有订单哦～</p>");
                htl.push("</div>");
            }
            $("#" + obj + "").append(htl.join(""));
            getHeight();
        }
    });

}

//订单详情
function CashBill(onb) {
    var casid = $(onb).find("#casid").val();
    var journalNum = $(onb).find("#journalNum").val();

    var fk = $(onb).find("#fkStatus").val();

    if (fk == '05' || fk == '06' || fk == '07' || fk == '08' || fk == '16' || fk == '17' || fk == '18') {
        window.location.href = basePath + "/ea/pobuy/ea_getCashBill.jspa?cbid=" + casid + "&refund=1";
    } else {
        document.location.href = basePath + "/ea/pobuy/ea_getCashBill.jspa?cbid=" + casid;
    }

}

//物流
function wuliu(str) {
    window.location.href = "ea/pobuy/ea_toGetWuliu.jspa?cbid=" + str;
}

//计算页面高度
function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(window).scrollTop() + $(window).height() + 200 > $("#divUL").height()) {
            if (pagenumber < pagecount) {
                loaded(obj, pl);
            }
        }
    }
}

//选择地址
function getAddress() {
    var formxz1 = $("#formxz1").parseForm();
    console.log(formxz1);
    localforage.setItem('formxz1', formxz1).then(function (value) {
        // 当值被存储后，可执行其他操作
        window.location.href = "ea/refund/ea_sealAddress2.jspa?type=2&flag=2&companyId=" + comid + "&staffid=" + staffid + "&sort=" + sort;
    }).catch(function (err) {    // 当出错时，此处代码运行
        console.log(err);
    });
}

//选择支付方式
function getPayMethod() {
    var formxz1 = $("#formxz1").serializeArray();
    console.log(formxz1);
    localforage.setItem('formxz1', formxz1).then(function (value) {
        // 当值被存储后，可执行其他操作
        window.location.href = "page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/paymentMethod.jsp?flag=2";
    }).catch(function (err) {    // 当出错时，此处代码运行
        console.log(err);
    });
}

function qfClick(type, id) {
    $(".div-bto").empty();
    $("#zfzt").val(type);
    $("#cashid").val(id);
    $("#jumber").val($("#" + id).find(".jumber").text().trim());
    $("#money").val($("#" + id).find(".money").text().trim());

    if ($("#" + id).find(".receivename").text().trim() != null && $("#" + id).find(".receivename").text().trim() != "") {
        console.log($(".receivename").text().trim());
        var zsdz = "<div class='clearfix div-bto1 div-shr' onclick='getAddress()'><p class='p-img'>";
        zsdz += "<img src='images/WFJClient/Newjspim/wfj11_address_01.png'/> </p>";
        zsdz += "<div class='div-shr'><p>收货人：";
        zsdz += "<span class='rname'>" + $("#" + id).find(".receivename").text() + "</span>";
        zsdz += " <span class='rtel'>" + $("#" + id).find(".receivetel").text() + "</span></p>";
        zsdz += "<p class='p-12'>收货地址：";
        zsdz += "<span class='address'>" + $("#" + id).find(".receiveaddress").text() + "</span></p></div></div>";

        $("#rname").val($("#" + id).find(".receivename").text());
        $("#rtel").val($("#" + id).find(".receivetel").text());
        $("#address").val($("#" + id).find(".receiveaddress").text());
        $(".div-bto").append(zsdz);
    } else {
        $(".div-bto").append(xzdz);
    }
    if (type == "qk") {
        $(".p-top").text("欠款 ￥" + $("#" + id).find(".money").text().trim());
    } else {
        $(".p-top").text("付款 ￥" + $("#" + id).find(".money").text().trim());
        $(".div-bto").append(xzzf);
    }
    $("#xz1").show();
}

function toBack() {
    try {
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'callIOSjianli';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    } catch (error) {
        window.history.go(-1);
        return false;
    }
}
window.onload = window.onresize = function () {
    var clientWidth = document.documentElement.clientWidth;
    document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640
        * 40 + 'px';
}