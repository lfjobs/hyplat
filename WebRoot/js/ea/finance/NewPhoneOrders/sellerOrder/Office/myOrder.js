var t;
var pagenumber = 0;
var pagecount = 0;
var obj = "divUL";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var url;
var parameter;
var pl;

$(document).ready(function () {


    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        var Top = $(".last").offset().top; //元素距离顶部距离

        console.log("窗口" + '  ' + Height);
        console.log("Top" + '  ' + Top);
        console.log("scroll" + '  ' + scroll);
        console.log("TOP-窗口："+(Top - Height));
        console.log("TOP-窗口-Scroll："+(Top - Height-scroll));
        if (Top - Height - scroll <= 100) {
            if (pagenumber < pagecount) {
                loaded(obj, pl);
            }


        }

    })





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
        parameter = null;
        pagenumber = 0;
        pagecount = 0;
        if (pid == "dd") {
            pl = null;
            $("#" + obj).empty();
            loaded(obj, null);
        } else if (pid == "jh") {
            url = "page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/SortGoods.jsp?companyid=" + companyid + "&staffid=" + staffid + "&sort=" + sort;
            window.location.href = url;
        } else if (pid == "zf") {
            $("#" + obj).empty();
            loaded(obj, null);
        } else if (pid == "fh") {
            url = "page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/sendBill.jsp?companyid=" + companyid + "&staffid=" + staffid + "&sort=" + sort;
            window.location.href = url;
        } else if (pid == "sh") {
            url = "page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/transporBill.jsp?companyid=" + companyid + "&staffid=" + staffid + "&sort=" + sort;
            window.location.href = url;
        } else if (pid == "wl") {
            pl = "04";
            $("#" + obj).empty();
            loaded(obj, "04");
        }
    });

    //宽度计算
    var box_fh_wid = 0;
    $(".ul-tab-fh li").each(function () {
        box_fh_wid += $(this).outerWidth(true);
    });
    $(".ul-tab-fh").width(box_fh_wid + 5);

    //查询
    $("#search").click(function () {
        if ($("#ss").val().trim() != null && $("#ss").val().trim() != "") {
            parameter = $("#ss").val();
            $("#" + obj).empty();
            loaded(obj, pl);
        }
    });

    loaded(obj, null);
});

//日期格式化
/*function format(date, format) {
    var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "h+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds(),
        "q+": Math.floor((date.getMonth() + 3) / 3),
        "S": date.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}*/

//数据加载
function loaded(obj, pl) {
    clearTimeout(t);
    pagenumber++;
    url = "ea/seller/sajax_ea_getAjaxNew.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: true,
        dateType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "companyid": companyid,
            "pl": pl,
            "source": "office",
            "parameter": parameter
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
            }

            if (pageForm != null && pageForm.recordCount > 0) {
                for (var i = 0; i < list.length; i++) {
                    var pp = list[i];
                    if (i == list.length - 1) {
                        htl.push("<div class='last'>");
                    } else {
                        htl.push("<div>");
                    }
                    htl.push("<ul class='ul-con grd sel_nub' onclick='CashBill(&#39;"+pp.cashierBillsID+"&#39;)' id="+pp.cashierBillsID+">");
                    htl.push("<li>订单号：" + pp.journalNum + "<span>");
                    //01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
                    if (pp.paystatus == "02") {
                        htl.push("欠款");
                    } else if (pp.paystatus == "03") {
                        htl.push("已付款");
                    }
                    if ((pp.supplierstatus == "00" || pp.supplierstatus == null || pp.supplierstatus == "")&&(pp.fkStatus=='00'||pp.fkStatus=='01')) {
                        htl.push("/待拣货");
                    } else if (pp.supplierstatus == "01"&&(pp.fkStatus=='00'||pp.fkStatus=='01')) {
                        htl.push("/拣货中");
                    } else if (pp.supplierstatus == "02"&&pp.fkStatus=='02') {
                        htl.push("/待发货");
                    } else if (pp.supplierstatus == "03") {
                        htl.push("/待送货");
                    } else if (pp.supplierstatus == "04") {
                        htl.push("/送货中");
                    } else if (pp.supplierstatus == "05") {
                        htl.push("/待收货");
                    } else if (pp.supplierstatus == "06") {
                        htl.push("/待检货");
                    } else if (pp.supplierstatus == "07") {
                        htl.push("/待入库");
                    } else if (pp.supplierstatus == "09") {
                        htl.push("/订单完成");
                    }
                    var date = new Date();
                    htl.push("</span></li>");
                    htl.push("<li>采购商：" + pp.cgscomname + "</li>");
                    htl.push("<li>订货时间：" + pp.cashierdate + "</li>");
                    htl.push("<li>合计金额：" + pp.priceSub + "</li>");
                    htl.push("<li>件数：" + pp.jianshu + "</li>");
                    htl.push("<input type='hidden' id='fkStatus' value="
                        + pp.fkStatus + ">");
                    htl.push("<input type='hidden' id='casid' value="
                        + pp.cashierBillsID + ">");
                    htl.push("<input type='hidden' id='journalNum' value="
                        + pp.journalNum + ">");
                    htl.push("<input type='hidden' id='pl' value="
                        + pl + ">");
                    htl.push("<input type='hidden' id='companyId' value="
                        + pp.companyid + ">");
                    htl.push("<input type='hidden' id='wfStatus4' value=" + pp.wfStatus4 + ">");
                    htl.push("</ul>");
                    if (pp.fkStatus != '04') {
                        if (pp.contactInfo.length > 0) {
                            htl.push("<section class='sec-con2 clearfix'><input type='hidden' value='" + pp.contactInfo[0][0] + "'>");
                            htl.push("<input type='hidden' value='" + pp.contactInfo[0][1] + "'>");
                            htl.push("<input type='hidden' value='" + pp.contactInfo[0][2] + "'>");
                            if (isAndroid == true) {
                                htl.push("<p onclick='AndroidtoChat(this)' class='p-1'>");
                                htl.push("<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-03.png'>联系买家</p>");

                            } else if (isiOS == true) {
                                htl.push("<p onclick='ioschat(this)' class='p-1'>");
                                htl.push("<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-03.png'>联系买家</p>");
                            }
                        }
                    }
                    if (pp.fkStatus == "00" || pp.fkStatus == "01") {
                        htl.push("<p onclick='shareAddress(&#39;" + pp.journalNum + "&#39;)' class='p-2'>");
                        htl.push("<img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-01.png' >更改地址</p>");
                        //htl.push('<a href="#;"><input type="button" value="发货" onclick="cheg(this)"></a>');
                        if (pp.supplierstatus == "00" || pp.supplierstatus == "" || pp.supplierstatus == null) {
                            htl.push("<p class='p-3' onclick='CashBill(\"" + pp.cashierBillsID + "\")'><img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-02.png'>提交拣货</p>");
                        }
                    } else if (pp.fkStatus == "05") {
                        htl.push("<a onclick='orderReturns(this)'><input type='button' value='同意退货' id='tongyi'></a>");
                        htl.push("<a onclick='orderReturns(this)'><input type='button' value='拒绝退货' id='jujue'></a>");
                    } else if (pp.fkStatus == "16") {
                        htl.push("<a onclick='moneyReturns(this)'><input type='button' value='同意退款' id='tongyi'></a>");
                        htl.push("<a onclick='moneyReturns(this)'><input type='button' value='拒绝退款' id='jujue'></a>");
                    }

                    if (pp.supplierstatus == "04") {
                        htl.push("<p class='p-6' onclick='wuliu(\"" + pp.cashierBillsID + "\")'><img src='images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-dd-06.png'>查看物流</p>");
                    }
                    htl.push("</section>");

                    htl.push("</div>");
                }
            } else {
                htl.push("<div class='no'>");
                htl.push("<li><i><img src='images/ea/finance/NewPhoneOrders/sellerOrder/wu.png' class='wu'></i>");

                htl.push("<p>目前还没有订单哦～</p>");
                htl.push("</div>");
            }
            $("#" + obj + "").append(htl.join(""));
           // getHeight(obj, pl);
        }
    });

}

//订单详情
function CashBill(onb) {
    var casid = $("#"+onb).find("#casid").val();
    var journalNum = $("#"+onb).find("#journalNum").val();

    var fkStatus = $("#"+onb).find("#fkStatus").val();
    var companyId = $("#"+onb).find("#companyId").val();
    var plt = $("#"+onb).find("#pl").val();
    var ppid = $("#"+onb).find(" #ppid").val();
    var goodid = $("#"+onb).find(" #goodid").val();

    url = basePath + "ea/seller/ea_getCashBill.jspa?cashierBillsID="
        + casid + "&oaBillId=" + journalNum + "&parameter=BILL&plt=" + plt
        + "&fkStatus=" + fkStatus + "&companyId=" + companyId + "&ppid="
        + ppid + "&staffid=" + staffid + "&sort=" + sort;
    document.location.href = url;

}

//提交拣货
function delivery(obj){
    var cashid=obj;
    var url=basePath + "ea/seller/sajax_ea_saveSorting.jspa?";
    $.ajax({
        url : encodeURI(url),
        type : "get",
        dataType : "json",
        data:{
            'cashid':cashid
        },
        success : function(data){
            var member = eval("("+data+")");
            var flag=member.flag;
            alert(flag);
            // 成功
            if (flag=="操作成功"){
                url = "page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?companyid="
                    + companyid + "&staffid=" + staffid+ "&sort="+sort;
                document.location.href = url;
            }
        },
        error : function(data) {
            alert("操作失败！");
        }
    });
}

//物流
function wuliu(str) {
    window.location.href = "ea/pobuy/ea_toGetWuliu.jspa?cbid=" + str;
}

//联系商家
function ioschat(obj) {
    var url = "func=" + 'ioschat';
    params = {
        'sccid': $(obj).siblings().eq(0).val(),
        'account': $(obj).siblings().eq(1).val(),
        'username': $(obj).siblings().eq(2).val()
    };
    for (var i in params) {
        url = url + "&" + i + "=" + params[i];
    }
    window.webkit.messageHandlers.Native.postMessage(url);

}

function AndroidtoChat(obj) {
    var userId = $(obj).siblings().eq(1).val();
    var sccId = $(obj).siblings().eq(0).val();
    var userPickeName = $(obj).siblings().eq(2).val();

    Android.toChat(userId, sccId, userPickeName);
}
//更改地址
function shareAddress(journalNum) {
    url = basePath + "ea/seller/ea_getCashBill.jspa?parameter=ADDRESS&oaBillId=" + journalNum + "&staffid=" + staffid + "&companyid=" + companyid + "&sort=" + sort;
    document.location.href = url;
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