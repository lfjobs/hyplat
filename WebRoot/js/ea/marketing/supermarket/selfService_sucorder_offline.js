var tz = false;
var home = 1;
$(function () {
    if (posNum != null && posNum != "") {
        tz = true;
    }
    jisuan();
    //返回首页
    $("#backhome").click(function () {
        home = 1;
        if (tz) {
            ajaxGetAccess(posNum);

        } else {
            document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";

        }

    });

    //返回首页
    $("#backshop").click(function () {
        home = 0;
        if (tz) {
            ajaxGetAccess(posNum);
        } else {
            document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";
        }
    });

    // var timer = setTimeout(function () {
    //     window.clearTimeout(timer);
    //     if (tz) {
    //         ajaxGetAccess(posNum);
    //
    //     } else {
    //         document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";
    //
    //     }
    // }, 20000);

    //打印小票
    $("#print").click(function () {
        if (t == 1) {
            return;
        }
        t = 1;
        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid("开始打印小票，请稍后!");
            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }
        $("#print").css("background-color", "#ccc");
        $(".alert_suc_").show();

        var ulp = basePath + "ea/sm/sajax_ea_printTicket.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            dataType: "json",
            async: true,
            data: {
                journalNum: journalNum,
                posNum: posNum
            },
            success: function (data) {
                var me = eval("(" + data + ")");
                staffcode = me.staffcode;
                zf = me.zf;
                if (posNum == null || posNum == "") {
                    posNum = me.posNum;
                }
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

                if (isAndroid === true) {
                    var date = new Date();
                    var year = date.getFullYear();
                    var month = date.getMonth() + 1;
                    var day = date.getDate();
                    var hour = date.getHours();
                    var minute = date.getMinutes();
                    var second = date.getSeconds();
                    var ssprice = $("ul li:eq(0)").find(".ssprice").val();
                    var zlprice = $("ul li:eq(0)").find(".zlprice").val();
                    if (paytype !== "") {
                        zf = paytype;
                    }
                    var array = new Array();
                    $(".tr").each(function () {

                        var goodsName = $(this).find(".goodsName").text();
                        var num = $(this).find(".num").text();
                        var price = $(this).find(".price").text();
                        var data = {};
                        data["goodsName"] = goodsName;
                        data["num"] = num;
                        data["price"] = price;
                        array.push(data);
                    });

                    if (staffcode == null) {
                        staffcode = "";
                    }

                    Android.printOfflineTicketAd(
                        companyName,
                        staffcode, // 操作人代码
                        posNum,    // 设备号
                        journalNum,// 订单号
                        year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second,// 时间
                        $(".totalnum").text(),// 总数量
                        $(".totalmoney").text(),// 总金额
                        JSON.stringify(array),
                        paymentType, // 线下支付方式
                        paymentName,
                        paymentParams
                    );

                    $(".alert_suc_").hide();
                    //定时跳转到首页

                    // var timer = setTimeout(function () {
                    //     window.clearTimeout(timer);
                    //     if (tz) {
                    //         ajaxGetAccess(posNum);
                    //     } else {
                    //         document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";
                    //     }
                    // }, 5000);
                } else {
                    myPrint();
                }

            },
            error: function (data) {
                console.log("打印小票失败");
            }
        });

    });

});

var LODOP; //声明为全局变量
function myPrint() {
    CreatePrintPage();
    LODOP.PRINT();
};

function CreatePrintPage() {

    var ssprice = $("ul li:eq(0)").find(".ssprice").val();
    var zlprice = $("ul li:eq(0)").find(".zlprice").val();
    if (paytype != "") {
        zf = paytype;
    }
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();

    LODOP = getLodop();
    LODOP.PRINT_INIT("微分金购物小票");
    LODOP.SET_PRINT_PAGESIZE(3, 500, 0.2, "");
    // LODOP.ADD_PRINT_RECT(10,55,360,220,0,1);
    LODOP.SET_PRINT_STYLE("FontSize", 9);
    LODOP.SET_PRINT_STYLE("Bold", 1);

    LODOP.ADD_PRINT_TEXT(0, 10, 180, 50, companyName);
    LODOP.SET_PRINT_STYLE("FontSize", 6);
    LODOP.ADD_PRINT_TEXT(15, 35, 180, 50, "http://www.impf2010.com");
    LODOP.SET_PRINT_STYLE("FontSize", 8);
    LODOP.SET_PRINT_STYLE("Bold", 0);
    LODOP.ADD_PRINT_TEXT(25, 58, 180, 50, "欢迎下次光临");

    LODOP.SET_PRINT_STYLE("FontSize", 7);
    LODOP.SET_PRINT_STYLE("Bold", 0);
    LODOP.ADD_PRINT_TEXT(45, 5, 100, 50, "操作员：" + staffcode);
    LODOP.ADD_PRINT_TEXT(45, 100, 100, 50, "POS：" + posNum);
    LODOP.ADD_PRINT_TEXT(58, 5, 200, 50, "订单号:" + journalNum);
    LODOP.ADD_PRINT_TEXT(71, 5, 210, 50, "打印时间：" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
    LODOP.ADD_PRINT_LINE(82, 5, 75, 500, 2, 0);
    LODOP.ADD_PRINT_TEXT(85, 5, 120, 50, "品名");
    LODOP.ADD_PRINT_TEXT(85, 118, 50, 50, "数量");
    LODOP.ADD_PRINT_TEXT(85, 152, 83, 50, "单价");

    var hPos = 100;//小票上边距
    var pageWidth = 100;//小票宽度
    var rowHeight = 14;//小票行距

    LODOP.SET_PRINT_STYLE("FontSize", 8);
    $(".tr").each(function () {
        var goodsName = $(this).find(".goodsName").text();
        var num = $(this).find(".num").text();
        var price = $(this).find(".price").text();
        if (goodsName.length > 20) {
            goodsName = goodsName.substr(0, 20);
            LODOP.ADD_PRINT_TEXT(hPos, 5, 120, 50, goodsName);
            hPos += rowHeight;
        } else {
            LODOP.ADD_PRINT_TEXT(hPos, 5, 120, 50, goodsName);
        }
        LODOP.ADD_PRINT_TEXT(hPos, 120, 50, 50, num);
        LODOP.ADD_PRINT_TEXT(hPos, 140, 88, 50, price);

        hPos += rowHeight;
    });
    LODOP.ADD_PRINT_LINE(hPos, 5, hPos, 500, 2, 0);
    hPos += rowHeight;
    LODOP.SET_PRINT_STYLE("FontSize", 8);
    LODOP.SET_PRINT_STYLE("Bold", 0);
    LODOP.ADD_PRINT_TEXT(hPos, 5, 80, 50, "应付金额:");
    LODOP.ADD_PRINT_TEXT(hPos, 75, 190, 50, "(共" + $(".totalnum").text() + "件) ￥" + $(".totalmoney").text());

    hPos += rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos, 5, 80, 50, "收款方式");
    LODOP.ADD_PRINT_TEXT(hPos, 132, 190, 50, paymentName);

    if (paymentType === "offline_bank") {
        var [name, account] = paymentParams.split(":");
        hPos += rowHeight;
        LODOP.ADD_PRINT_TEXT(hPos, 5, 80, 50, "银行名称");
        LODOP.ADD_PRINT_TEXT(hPos, 132, 190, 50, name);
        hPos += rowHeight;
        LODOP.ADD_PRINT_TEXT(hPos, 5, 80, 50, "银行卡号");
        LODOP.ADD_PRINT_TEXT(hPos, 132, 190, 50, account);
    } else {
        hPos += rowHeight;
        LODOP.ADD_PRINT_IMAGE(hPos + 10, 36, 150, 150, `<img src='` + paymentParams + `'/>`); // 二维码
        hPos += rowHeight + 300;
        LODOP.ADD_PRINT_LINE(hPos, 5, hPos, 500, 2, 0);
        LODOP.ADD_PRINT_TEXT(hPos + 5, 5, 178, 50, "");
    }

    $(".alert_suc_").hide();
    //定时跳转到首页

    // var timer = setTimeout(function () {
    //     window.clearTimeout(timer);
    //     if (tz) {
    //         ajaxGetAccess(posNum);
    //     } else {
    //         document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";
    //     }
    // }, 5000);
};

//统计总数量/总金额
function jisuan() {

    var totalnum = 0;
    var totalmoney = 0;
    $(".tr").each(function () {
        var num = Number($(this).find(".num").text());
        var tprice = Number($(this).find(".tprice").text());
        var barCode = $(this).find(".barCode").text();
        var goodsName = $(this).find(".goodsName").text();
        var specNum = $(this).find(".specNum").text();
        if (barCode.substring(0, 2) == "21") {
            $(this).find(".num").text("1");
        }
        if (goodsName.substring(0, 1) == "#") {
            $(this).find(".num").text(Number(num) / Number(specNum));
        }

        var money = Number((num * tprice)).toFixed(2);
        $(this).find(".price").text(money);
        totalnum = totalnum + Number($(this).find(".num").text());
        totalmoney = Number(totalmoney) + Number(money);

    });
    $(".totalnum").text(totalnum);
    $(".totalmoney").text(Number(totalmoney).toFixed(2));


}

function round(value) {

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if (aArr.length > 1) {
        if (aArr[1].length > 3) {
            value = Number(aStr).toFixed(3);
        }
    }

    return Number(value);
}


function getMyCookie(key) {
    var val = "";

    // 对cookie操作
    var cookies = document.cookie;
    cookies = cookies.replace(/\s/, "");
    var cookie_array = cookies.split(";");
    for (i = 0; i < cookie_array.length; i++) {
        // yh_mch=lilei
        var cookie = cookie_array[i];
        var array = cookie.split("=");
        if (array[0] == key) {
            val = array[1];
        }
    }

    return val;
}

/**
 *
 * 获取返回的首页参数
 * @param posNum
 */
function ajaxGetAccess(posNum) {
    //回退到批发商城页面
    var backUrl = localStorage.getItem("backUrl");//回退路径
    if (backUrl != null && backUrl != "" && backUrl.indexOf("ea/wholesaleMall/ea_toWholesaleMall.jspa") != -1) {//回退路径不为空并且回退链接为批发商城链接
        localStorage.setItem("backUrl", "");
        document.location.href = backUrl;
        return false;
    }
    var ulp = basePath
        + "ea/smg/sajax_sm_ajaxGetAccess.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        data: {
            posNum: posNum
        },
        success: function (data) {
            var me = eval("(" + data + ")");
            var result = me.result;
            var industryType = me.industryType;
            var ccompanyID = me.ccompanyID;
            var companyName = me.companyName;
            var postype = me.postype;
            var app = me.app;


            if (result == "FAIL") {
                document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
            } else {
                if (app == "01") {
                    document.location.href = basePath + "ea/mappointment/ea_theTestTime.jspa?sccId=&posNum=" + posNum;
                } else {
                    if (industryType != null && industryType != "") {
                        if (industryType.indexOf("超级市场") != -1) {
                            if (home == 0) {
                                document.location.href = basePath + "ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=" + ccompanyID + "&industryType=" + industryType + "&companyName=" + companyName + "&posNum=" + posNum;
                            } else {
                                document.location.href = basePath + "ea/sm/ea_qdlist.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID;
                            }
                        } else if (industryType.indexOf("餐饮") != -1) {
                            document.location.href = basePath + "ea/industry/ea_CompanyProducts.jspa?ccompanyId=" + ccompanyID + "&pos=&posNum=" + posNum + "&postype=" + postype;
                        } else {
                            document.location.href = basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyID + "&industryType=" + industryType + "&etype=";
                        }
                    } else {
                        document.location.href = basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyID + "&industryType=" + industryType + "&etype=";

                    }
                }
            }
        },
        error: function (data) {
            console.log("获取失败入口");
        }
    });
}
