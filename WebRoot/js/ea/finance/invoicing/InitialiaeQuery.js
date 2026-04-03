var obj = "div-box";
var t;
var pagenumber = 0;
var pagecount = 0;
$(function () {
    $(".sec-search .div-left").click(function () {
        $(".div-tk").show();
    })
    $(".sec-searchtime .div-left").click(function () {
        $(".div-tk").show();
    })
    //日历
    new Mdate("dateSelectorOne", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelectorOne",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        //beginYear: "2020",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
    new Mdate("dateSelectorTwo", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelectorTwo",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2020",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
    //启用选择
    $(".div-tk ul li").click(function () {
        $(".div-tk ul li").find("img").attr("src", "/images/ea/finance/invoicing/xuan_03.png");
        $(".div-tk ul li").find("p").removeClass("active");
        $(this).find("img").attr("src", "/images/ea/finance/invoicing/xuan_06.png");
        $(this).find("p").addClass("active");
    })
    //启用提交
    $(".div-tj").click(function () {
        var ptext = $(".div-tk .active").parents("li").children("p").text();
        if (ptext == "时间") {
            var myDate = new Date();
            var day = myDate.getDate();
            var month = myDate.getMonth() + 1;
            if (myDate.getMonth() < 10) {
                month = "0" + (myDate.getMonth() + 1);
            }
            var day = myDate.getDate();
            if (myDate.getDate() < 10) {
                day = "0" + myDate.getDate();
            }
            var datew = myDate.getFullYear() + "-" + month + "-" + day;
            var datew = datew.toString();
            $("#dateSelectorOne").val(datew);
            $("#dateSelectorOne").attr("data-year", myDate.getFullYear());
            $("#dateSelectorOne").attr("data-month", month);
            $("#dateSelectorOne").attr("data-day", day);
            $("#dateSelectorTwo").val(datew);
            $("#dateSelectorTwo").attr("data-year", myDate.getFullYear());
            $("#dateSelectorTwo").attr("data-month", month);
            $("#dateSelectorTwo").attr("data-day", day);
            $(".sec-search").hide();
            $(".sec-searchtime").show();
        } else {
            $(".sec-search").show();
            $(".sec-searchtime").hide();
        }
        $("#pemer").val("");
        $("#dateSelectorOne").val("");
        $("#dateSelectorTwo").val("");
        $(".p-dh").find("span").text(ptext);

        $(".div-tk").hide();
    });

    //data-year="2020" data-month="1" data-day="1"

    $(".div-datatime").click(function () {
        $(this).hide();
    });
    $("#inp-cx").click(function () {
        var result = validateTimePeriod(document.getElementById("dateSelectorOne"), $("#dateSelectorTwo")); //验证是否起始时间小于等于截至时间
        if (result != true) {
            $(".div-datatime").show();
            return false;
        }
    });
    $(".query").click(function () {
        DataLoading(obj);
    });

    $("#print").click(function () {
        DataPrint();
    });

});

function DataLoading(obj) {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: "/ea/initialize/sajax_ea_getGoodsAjax.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "compayid": comid,
            "pageForm.pageNumber": pagenumber,
            "pemer": $("#pemer").val(),
            "sdate": $("#dateSelectorOne").val(),
            "edate": $("#dateSelectorTwo").val(),
            "title":$(".p-dh").find("span").text()
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var htl = new Array();
            if (pageForm != null) {
                var list = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                if (pagenumber == 1) {
                    $("." + obj + "").empty();
                    htl.push("<table class='table-s' style='BORDER-COLLAPSE: collapse' cellPadding=0 align=center border=0>");
                    htl.push("<tr><td>名称及规格</td><td>库仓</td>");
                    htl.push("<td>初始库存</td><td>是否发布在线</td></tr>");
                }
                if (pageForm.recordCount > 0) {
                    for (var i = 0; i < list.length; i++) {
                        var pp = list[i];
                        if (i == list.length - 1) {
                            htl.push("<tr class='ul-text clearfix last'>");
                        } else {
                            htl.push("<tr class='ul-text clearfix '>");
                        }
                        htl.push("<input type='hidden' name='ppid' value='" + pp[2] + "'/>");
                        htl.push("<td>" + pp[3] + "</td>");
                        htl.push("<td>" + pp[4] + "</td><td>" + pp[5] + "</td>");
                        htl.push("<td><span>" + (pp[6] == "01" ? "启动" : "未启动") + "</span></td></tr>");
                    }
                }
                htl.push("</table>");
                $("." + obj + "").append(htl.join(""));
                getHeight(obj);
            }
        },
        error: function (data) {
            alert("数据获取失败！")
        }
    });
}

function DataPrint() {
    try {
        if (isAndroid == true) {
            Android.speechOutputForAndroid("开始打印，请稍后!");
        } else {
            console.log("请在安卓设备访问！");
        }
    } catch (error) {

    }
    $.ajax({
        url: "/ea/initialize/sajax_ea_getGoods.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "compayid": comid,
            "pageForm.pageNumber": pagenumber,
            "pemer": $("#pemer").val(),
            "sdate": $("#dateSelectorOne").val(),
            "edate": $("#dateSelectorTwo").val(),
            "title":$(".p-dh").find("span").text()
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var list = member.list;
            var htl = new Array();
            if (list != null) {
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

                if (isAndroid == true) {
                    var Json = [];
                    for (var i = 0; i < list.length; i++) {
                        var pp = list[i];
                        Json.push({
                            "goodsname": pp[3],
                            "codevalue": pp[7],
                            "invnum": pp[5],
                            "staffname": pp[9],
                            "warehousename": pp[4],
                        });
                    }
                    var printJson = {
                        "title": $(".p-dh").find("span").text(),
                        "param": $("#pemer").val(),
                        "sdate": $("#dateSelectorOne").val(),
                        "edate": $("#dateSelectorTwo").val(),
                        "comname": list[0][11],
                        //"date":list[0][10],
                        "goods": Json
                    };
                    alert(JSON.stringify(printJson));
                    Android.printInventoryReceipt(JSON.stringify(printJson));
                    $(".alert_suc_").hide();
                } else {
                    myPrint(list);
                }
            }
        },
        error: function (data) {
            alert("数据获取失败！")
        }
    });
}

//计算页面高度
function getHeight(obj) {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(window).scrollTop() + $(window).height() + 200 > $("#divUL").height()) {
            if (pagenumber < pagecount) {
                DataLoading(obj);
            }
        }
    }
}

// 查询校验,校验起始时间必须小于截至时间
function validateTimePeriod(begin, end) {

    if (!(begin instanceof jQuery)) {
        begin = $(begin);
    }
    if (!(end instanceof jQuery)) {
        end = $(end);
    }

    var beginString = new String(begin.val());
    var endString = new String(end.val());

    if (!(beginString == null || beginString == '') && !(endString == null || endString == '')) {


        var beginTime = new Date(beginString);
        var endTime = new Date(endString);

        if (beginTime <= endTime) {
            return true;
        } else {
            return false;
        }
    }
    return true;
}

var LODOP; //声明为全局变量

function myPrint(list) {
    CreatePrintPage(list);
    LODOP.PRINT();
    alert("2323232323");
}

function CreatePrintPage(list) {

    LODOP = getLodop();
    LODOP.PRINT_INIT($(".p-dh").find("span").text() + ":" + $("#pemer").val());
    LODOP.SET_PRINT_PAGESIZE(3, 500, 0.2, "");
    // LODOP.ADD_PRINT_RECT(10,55,360,220,0,1);
    LODOP.SET_PRINT_STYLE("FontSize", 9);
    LODOP.SET_PRINT_STYLE("Bold", 1);

    /*LODOP.ADD_PRINT_TEXT(0,10,180,50,companyName);
     LODOP.SET_PRINT_STYLE("FontSize",6);
     LODOP.ADD_PRINT_TEXT(15,35,180,50,"http://www.impf2010.com");
     LODOP.SET_PRINT_STYLE("FontSize",8);
     LODOP.SET_PRINT_STYLE("Bold",0);
     LODOP.ADD_PRINT_TEXT(25,58,180,50,"欢迎下次光临");

     LODOP.SET_PRINT_STYLE("FontSize",7);
     LODOP.SET_PRINT_STYLE("Bold",0);
     LODOP.ADD_PRINT_TEXT(45,5,100,50,"操作员："+staffcode);
     LODOP.ADD_PRINT_TEXT(45,100,100,50,"POS："+posNum);
     LODOP.ADD_PRINT_TEXT(58,5,200,50,"订单号:"+journalNum);
     LODOP.ADD_PRINT_TEXT(71,5,210,50,"打印时间："+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);*/
    LODOP.ADD_PRINT_LINE(82, 5, 75, 500, 2, 0);
    LODOP.ADD_PRINT_TEXT(85, 5, 120, 50, "品名");
    LODOP.ADD_PRINT_TEXT(85, 118, 50, 50, "类别");
    LODOP.ADD_PRINT_TEXT(85, 152, 83, 50, "初始库存");
    LODOP.ADD_PRINT_TEXT(85, 152, 83, 50, "责任人");
    LODOP.ADD_PRINT_TEXT(85, 152, 83, 50, "仓库");

    var hPos = 100;//小票上边距
    var pageWidth = 100;//小票宽度
    var rowHeight = 14;//小票行距

    LODOP.SET_PRINT_STYLE("FontSize", 8);
    for (var i = 0; i < list.length; i++) {
        var pp = list[i];

        LODOP.ADD_PRINT_TEXT(hPos, 5, 120, 50, pp[3]);
        LODOP.ADD_PRINT_TEXT(hPos, 70, 50, 50, pp[7]);
        LODOP.ADD_PRINT_TEXT(hPos, 90, 88, 50, pp[5]);
        LODOP.ADD_PRINT_TEXT(hPos, 120, 88, 50, pp[9]);
        LODOP.ADD_PRINT_TEXT(hPos, 140, 88, 50, pp[4]);

        hPos += rowHeight;
    }

    LODOP.ADD_PRINT_LINE(hPos, 5, hPos, 500, 2, 0);
    hPos += rowHeight;
    LODOP.SET_PRINT_STYLE("FontSize", 8);
    LODOP.SET_PRINT_STYLE("Bold", 0);
    LODOP.ADD_PRINT_TEXT(hPos, 5, 80, 50, "初始库存时间:");
    LODOP.ADD_PRINT_TEXT(hPos, 75, 190, 50, "pp[0][4]");

    hPos += rowHeight;
    LODOP.ADD_PRINT_TEXT(hPos, 120, 80, 50, "pp[0][11]");
    hPos += rowHeight;
    LODOP.ADD_PRINT_LINE(hPos, 5, hPos, 500, 2, 0);

};

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