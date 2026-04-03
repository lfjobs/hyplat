var t;
var pagenumber = 0;
var pagecount = 0;
var obj = "initList";
$(function () {
    DataLoading(obj);
    //localforage.clear();
    $("#add").click(function () {
        window.location.href = "/page/ea/main/finance/invoicing/InitialiaeAdd.jsp?compayid=" + comid+"&staffid="+staffid;
    });
    $("#query").click(function () {
        window.location.href = "/page/ea/main/finance/invoicing/InitialiaeQuery.jsp?compayid=" + comid+"&staffid="+staffid;
    });
    $("#print").click(function () {
        window.location.href = "/ea/pobuy/ea_toGetWuliu.jsp?cbid=" + str;
    });
});

function DataLoading(obj) {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: "/ea/initialize/sajax_ea_getInitializeAjax.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "compayid": comid,
            "pageForm.pageNumber": pagenumber,
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
                        htl.push("<ul class='last' id='" + pp.initializeid + "' onclick='print(this)'>");
                    } else {
                        htl.push("<ul id='" + pp.initializeid + "' onclick='print(this)'>");
                    }
                    htl.push("<li>初始库存单号<p>" + pp.journalnum + "</p></li>");
                    htl.push("<li>初始库存时间<p>" + pp.adddate + "</p></li>");
                    htl.push("<li>商品类别<p>" + pp.codename + "</p></li>");
                    htl.push("<li>初始库存责任人<p>" + pp.staffname + "</p></li></ul>");
                }
            } else {
                htl.push("<div class='no'>");
                htl.push("<li><div><img src='images/ea/finance/NewPhoneOrders/sellerOrder/wu.png' class='wu'></div>");
                htl.push("<p>目前还没有订单哦～</p>");
                htl.push("</div>");
            }
            $("#" + obj + "").append(htl.join(""));
            getHeight(obj);
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

function print(data) {
    var initializeid=$(data).attr("id");
    window.location.href = "/ea/initialize/ea_getInitialize.jspa?initializeid="+initializeid;
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