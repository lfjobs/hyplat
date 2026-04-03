var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') > 1; //window
var obj = "tj_con";
var t;
var parameter;
var pagenumber = 0;
var pagecount = 0;
/**
 * 初始化判断是安卓/IOS终端
 */
$(function () {

    $("#xx").click(function () {
        $("#" + obj + "").empty();
        $("#wei").show();
    });

    $("#rk").click(function () {
        pagenumber = 0;
        $("#" + obj + "").empty();
        DataLoading(obj);
        $("#wei").show();
    });

    $("#sc").click(function () {
        $("#" + obj + "").empty();
    });

    $("#yd").click(function () {
        $("#" + obj + "").empty();
    });

    $("#xs").click(function () {
        pagenumber = 0;
        $("#" + obj + "").empty();
        BillLoading(obj);
        $("#wei").hide();
    });

    $("#rk").trigger('click');
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        var Top = $(".last").offset().top; //元素距离顶部距离

        /*console.log("窗口" + '  ' + Height);
        console.log("Top" + '  ' + Top);
        console.log("scroll" + '  ' + scroll);
        console.log("TOP-窗口："+(Top - Height));
        console.log("TOP-窗口-Scroll："+(Top - Height-scroll));*/
        if (scroll >= 80) {
            $(".bug-nav2").addClass("nav-fixed")
        } else {
            $(".bug-nav2").removeClass("nav-fixed")
        }
        if (Top - Height - scroll <= 100) {
            if (pagenumber < pagecount) {
                DataLoading(obj);
            }
        }
    });

    /*if (isiOS == true) {dt
        var obj_sao = document.getElementById("ttsw_smq_id");
        obj_sao.setAttribute("onclick", "callIOScamera()");
    }*/
    $("#smli").click(function () {
        if (isAndroid == true) {
            Android.callcamera();
        } else if (isiOS == true) {
            callIOScamera();
        }
        //ajaxGetGoodsHtml("6921168509256");
    });

    $("#wei").click(function () {
        /*$("#tk").text("请先设置供货商");
        $(".div-tingyong").show();*/
        window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + comid + "&staffid=" + staffid + "&sccid=" + sccid + "&showButton=true&sort=1";
    });

    $(".right").click(function () {
        document.location.href = basePath + "page/ea/main/finance/invoicing/rukuBill_add.jsp?companyid=" + comid + "&staffid=" + staffid + "&sccid=" + sccid;
    });

    $(".left").click(function () {
        $(".div-tingyong").hide();
    });

    $(document).on("keydown", "input", function (event) {
        if (event.keyCode == 13) {
            var barcode = $(this).val();
            $(this).val("");
            ajaxGetGoodsHtml(barcode);
        }
    });
});

/**
 * 接收安卓端扫码返回值
 * @param tiaoma 条码
 */
function calltiaoma(tiaoma) {
    //条形码信息
    var member = eval("(" + tiaoma + ")");
    //条形码
    var barcode = member.code;
    ajaxGetGoodsHtml(barcode);
}

/**
 * 接收IOS端扫码返回值
 * @param tiaoma
 */
function calltiaomaIOS(tiaoma) {
    //条形码信息
    var member = eval("(" + tiaoma + ")");
    //条形码
    var barcode = member.code;
    ajaxGetGoodsHtml(barcode);
}

/**
 * 异步根据隐藏域中的值获取盘库商品信息
 */
function ajaxGetGoodsHtml(barcode) {
    if (barcode.length == 19) {
        document.location.href = basePath + "ea/ruku/ea_getBill.jspa?barcode=" + barcode + "&companyid=" + comid + "&staffid=" + staffid + "&sccid=" + sccid;
    } else {
        document.location.href = basePath + "ea/ruku/ea_getProductByComid.jspa?barcode=" + barcode + "&companyid=" + comid + "&staffid=" + staffid + "&sccid=" + sccid;
    }
}

function BillLoading(obj) {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: basePath + "ea/ruku/sajax_ea_getRukuAjax.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "staffid": staffid,
            "pageForm.pageNumber": pagenumber,
            "type": "Bill"
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var htl = [];
            if (pageForm != null) {
                var list = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                for (var i = 0; i < list.length; i++) {
                    var pp = list[i];
                    if (i == list.length - 1) {
                        htl.push("<li class='last clearfix' id='" + pp.csid + "' onclick=\"ajaxGetGoodsHtml('" + pp.orderCode + "');\">");
                    } else {
                        htl.push("<li class='clearfix' id='" + pp.csid + "' onclick=\"ajaxGetGoodsHtml('" + pp.orderCode + "');\">");
                    }
                    htl.push("<section id='sec-checked'>");
                    //htl.push("<aside class='aside_no' checkCasId='"+pp.csid+"'>");
                    //htl.push("<img class='img_no' src='"+basePath+"images/scMobile/payBudget/budgetList/wupinguanli_07.png'/>");
                    //htl.push("<img class='img_yes' src='"+basePath+"images/scMobile/payBudget/budgetList/wupinguanli_10.png'/>");
                    //htl.push("</aside>");
                    htl.push("<h5>订单单号：" + pp.orderCode + "<img src='" + basePath + "images/scMobile/payBudget/budgetList/wupinguanli_img_13.png'/></h5>");
                    htl.push("<h5>收货单号：" + pp.consigneCode + "</h5>");
                    htl.push("</section>");
                    htl.push("<div onclick=\"toDetail('" + pp.csid + "');\">");
                    htl.push("<p>供货商：" + pp.companyName + "</p>");
                    htl.push("<p>收货时间：" + pp.consignneDate + "</p>");
                    htl.push("</div></li>");
                }
            } else {
                htl.push("<li style='text-align:center;'>暂无数据</li>");

            }
            $("#" + obj + "").append(htl.join(""));
            //getHeight(obj);
        },
        error: function (data) {
            alert("数据获取失败！")
        }
    });
}

function DataLoading(obj) {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: basePath + "ea/ruku/sajax_ea_getRukuAjax.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "compayid": comid,
            "pageForm.pageNumber": pagenumber,
            "type": "Ruku"
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var htl = [];
            if (pageForm != null) {
                var list = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                for (var i = 0; i < list.length; i++) {
                    var pp = list[i];
                    if (i == list.length - 1) {
                        htl.push("<li class='last clearfix' id='" + pp.rkid + "'>");
                    } else {
                        htl.push("<li class='clearfix' id='" + pp.rkid + "'>");
                    }
                    htl.push("<section id='sec-checked'>");
                    htl.push("<aside class='aside_no' checkCasId='" + pp.rkid + "'>");
                    /*htl.push("<img class='img_no' src='"+basePath+"images/scMobile/payBudget/budgetList/wupinguanli_07.png'/>");
                    htl.push("<img class='img_yes' src='"+basePath+"images/scMobile/payBudget/budgetList/wupinguanli_10.png'/>");*/
                    htl.push("</aside>");
                    htl.push("<h5>入库单号：" + pp.journalnum + "<img src='" + basePath + "images/scMobile/payBudget/budgetList/wupinguanli_img_13.png'/></h5>");
                    htl.push("</section>");
                    htl.push("<div onclick=\"toDetail('" + pp.rkid + "');\">");
                    htl.push("<p>供货商：" + pp.ghcomname + "</p>");
                    htl.push("<p>制单时间：" + pp.adddate + "</p>");
                    htl.push("<p>仓库名称：" + pp.warehousename + "</p>");
                    htl.push("<p>入库责任人：" + pp.cgstaffname + "</p>");
                    htl.push("</div></li>");
                }
            } else {
                htl.push("<li style='text-align:center;'>暂无数据</li>");

            }
            $("#" + obj + "").append(htl.join(""));
            //getHeight(obj);
        },
        error: function (data) {
            alert("数据获取失败！")
        }
    });
}

//后退
function toBack() {
    if (isAndroid == true) {
        console.log("安卓");
        Android.callAndroidjianli();//调用安卓接口
    } else if (isiOS == true) {
        console.log("IOS");
        window.history.back();
    } else if (iswin) {
        console.log("win");
        window.history.go(-1);
        return false;
    } else {
        window.history.go(-1);
        return false;
    }
}

//监听点击浏览器后退
$(function () {
    pushHistory();
    window.addEventListener("popstate", function (e) {
        //alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能
        toBack();
    }, false);

    function pushHistory() {
        var state = {
            title: "title",
            url: ""
        };
        window.history.pushState(state, "title", "");
    }
});

