var t;
var pagenumber = 0;
var pagecount = 0;
var obj = "divUL";
var url;
var parameter;
var pl="01";

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
        var id = $(this).attr("id");//01:未拣货 02:已拣货
        $("#" + obj + "").empty();
        if (id == "wsh") {
            pl= "01";
        } else {
            pl="02";
        }
        loaded(obj, pl);
    });

    //查询
    $("#search").click(function () {
        if ($("#ss").val().trim() != null && $("#ss").val().trim() != "") {
            parameter = $("#ss").val();
            loaded(obj, pl);
        }
    });

    loaded(obj, pl);
});

//数据加载
function loaded(obj, pl) {
    //clearTimeout(t);
    pagenumber++;
    url = "ea/seller/sajax_ea_getTransportAjax.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dateType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "companyid": companyid,
            "statusType": pl,
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
                    htl.push("<ul class='ul-con'  onClick='gatReansport(&quot;" + pp[0] + "&quot;)'>");
                    htl.push('<li>送货单号：' + pp[1] + "</li>");
                    htl.push('<li>订单号：' + pp[2] + "</li>");
                    htl.push('<li>下单时间：' + pp[3] + "</li>");
                    htl.push('<li>送货时间：' + pp[4] + "</li>");
                    htl.push('<li>采购商：' + pp[5] + "</li>");
                    htl.push('<li>发货人：' + pp[6] + "</li>");
                    //01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
                    htl.push('<li>送货状态：');
                    if (pp[7] == "01") {
                        htl.push('未送货');
                    } else if (pp[7] == "02") {
                        htl.push('已送货');
                    }
                    var date = new Date();
                    htl.push("</li>");
                    htl.push('</ul>');
                    if (pp[7] == '01') {
                        htl.push("<section class='tjfh clearfix'>");
                        htl.push("<p onClick='gatReansport(&quot;" + pp[0] + "&quot;)'>提交物流</p>");
                        htl.push("</section>");
                    }
                    htl.push('</div>');
                }
            } else {
                htl.push('<div class="no">');
                htl.push('<li><i><img src="images/ea/finance/NewPhoneOrders/sellerOrder/wu.png" class="wu"></i>');
                htl.push('<p>目前还没有订单哦～</p>');
                htl.push('</div>');
            }
            console.log(htl.join(""));
            $("#" + obj + "").append(htl.join(""));
            getHeight(obj, pl);
        }
    });
}
//计算页面高度
function getHeight(obj, pl) {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(window).scrollTop()+$(window).height()+200 > $("#divUL").height()) {
            if (pagenumber < pagecount) {
                loaded(obj, pl);
            }
        }
    }
}

function gatReansport(reansportid) {
    var url = "ea/seller/ea_getTransportGoods.jspa?reansportid=" + reansportid+"&staffid=" + staffid+"&sort="+sort;
    window.location.href = url;
}
function toBack() {
    history.go(-1);
}
window.onload = window.onresize = function () {
    var clientWidth = document.documentElement.clientWidth;
    document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640
        * 40 + 'px';
}