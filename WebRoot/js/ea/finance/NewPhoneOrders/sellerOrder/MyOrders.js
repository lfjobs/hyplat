var t;
var pagenumber = 0;
var pagecount = 0;
var cla = "";
var obj = "all_order";
var pl = null;
var isAndroid;
var isiOS;
var url;
$(document).ready(function () {
    var u = navigator.userAgent;
    isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    $(".header ul li").css("line-height",
        $(window).height() * 0.08 + "px");
    $(".header").css("height", $(window).height() * 0.08 + "px");
    $(".content_hidden").css("height",
        $(window).height() * 0.92 - $(".header").height() + "px");
    $(".content").css("height",
        $(window).height() * 0.92 - $(".header").height() + "px");

    loaded(obj, pl);

    // 选项卡
    $(".header .rec_head li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        $(".rec_eva").empty();
        pagenumber = 0;

    });

    $(".all_order").click(
        function () {
            $("#all_order").css("display", "block").siblings().css(
                "display", "none");
            obj = "all_order";
            pl = "";
            loaded(obj, pl);

        });
    $(".obligation").click(
        function () {
            $("#obligation").css("display", "block").siblings()
                .css("display", "none");
            obj = "obligation";
            pl = "01";
            loaded(obj, pl);
        });
    $(".overhang").click(
        function () {
            $("#overhang").css("display", "block").siblings().css(
                "display", "none");
            obj = "overhang";
            pl = "00";
            loaded(obj, pl);
        });
    $(".pra").click(
        function () {
            $("#pra").css("display", "block").siblings().css(
                "display", "none");
            obj = "pra";
            pl = "02";
            loaded(obj, pl);
        });

    $(".alert p").click(function () {
        $("#quxiao").hide();
        $("#shanchu").hide();
    });

})
function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(".last").offset().top + $(".last").height()
            - $(".header").height() * 4 < $(window).height()) {
            if (pagenumber < pagecount) {
                loaded(obj, pl);
            }
        }
    }
}

function loaded(obj, pl) {
    clearTimeout(t);
    pagenumber++;
    url = basePath + "ea/seller/sajax_ea_getAjax.jspa?";

    $
        .ajax({
            url: url,
            type: "post",
            async: true,
            dateType: "json",
            data: {
                "pageForm.pageNumber": pagenumber,
                "companyid": companyid,
                "pl": pl
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
                            htl.push("<div class='grd last'>");
                        } else {
                            htl.push("<div class='grd'>");
                        }
                        htl.push('<ul class="sel_nub">');
                        htl.push('<li>订单号：<span>' + pp.journalNum
                            + '</span></li>');

                        if (pp.fkStatus == "00") {
                            htl.push('<li><span>待发货</span></li>');
                        } else if (pp.fkStatus == "02") {
                            htl.push('<li><span>待收货</span></li>');
                        } else if (pp.fkStatus == "03") {
                            htl.push('<li><span>用户已收货</span></li>');
                        } else if (pp.fkStatus == "04") {
                            htl.push('<li><span>订单已完成</span></li>');
                        }
                        else if (pp.fkStatus == "05") {
                            htl.push('<li><span>申请退货</span></li>');
                        } else if (pp.fkStatus == "16") {
                            htl.push('<li><span>申请退款</span></li>');
                        } else if (pp.fkStatus == "18") {
                            htl.push('<li><span>退款（退款退货）结束</span></li>');
                        }
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
                        htl.push('<input type="hidden" id="wfStatus4" value="' + pp.wfStatus4 + '">')
                        htl.push('</ul>');
                        var goodsList = pp.goodsList;
                        var ptlists = pp.ptgoodsList;
                        for (var j = 0; j < goodsList.length; j++) {
                            var goods = goodsList[j];
                            htl.push('<input type="hidden" id="bianh" value="'
                                + goods[15] + '">');
                            htl.push('<input type="hidden" id="bz" value="'
                                + goods[16] + '">');
                            htl.push('<input type="hidden" id="ppid" value="'
                                + goods[7] + '">');
                            htl.push('<input type="hidden" id="goodid" value="'
                                + goods[8] + '">');
                            //journalNum;// 凭证号
                            htl.push('<input type="hidden" id="journalNum" value="'
                                + goods[17] + '">');
                            //trade_no;//第三方支付交易号
                            htl.push('<input type="hidden" id="trade_no" value="'
                                + goods[18] + '">');
                            //钱
                            htl.push('<input type="hidden" id="refund_amount" value="'
                                + goods[3] + '">');
                            htl.push('<input type="hidden" id="wfStatus1" value="' + goods[20] + '">')
                            //wfStatus4
                            //在线支付00微信支付，01，支付宝支付，02银联支付 03:线下转账 04:钱盒子支付
                            htl.push('<input type="hidden" id="goodid" value="'
                                + goods[19] + '">');
                            htl.push('<div class="mil ant" onclick="CashBill(this)"><a href="#;"><div class="left">');
                            htl.push('<img src="' + basePath + goods[6]
                                + '" >');
                            if (member.flag == '300') {
                                if (goods[21] != null && goods[21] != "" && goods[21] == "1") {//批发

                                } else if (goods[21] != null && goods[21] != "" && goods[21] == "2") {//VIP
                                    htl.push('<span class="sp vip"><i></i></span>');
                                } else if (goods[21] != null && goods[21] != "" && goods[21] == "3") {//普通活动
                                    htl.push('<span class="sp cx"><i></i></span>');
                                } else if (goods[21] != null && goods[21] != "" && goods[21] == "4") {//特价活动
                                    htl.push('<span class="sp tj"><i></i></span>');
                                } else {//零售

                                }

                            } else {//flag == '200'
                                if (goods[19] != null && goods[19] != "" && goods[19] == "1") {//批发

                                } else if (goods[19] != null && goods[19] != "" && goods[19] == "2") {//VIP
                                    htl.push('<span class="sp vip"><i></i></span>');
                                } else if (goods[19] != null && goods[19] != "" && goods[19] == "3") {//普通活动
                                    htl.push('<span class="sp cx"><i></i></span>');
                                } else if (goods[19] != null && goods[19] != "" && goods[19] == "4") {//特价活动
                                    htl.push('<span class="sp tj"><i></i></span>');
                                } else {//零售

                                }
                            }


                            htl.push('</div><div class="right">');
                            htl.push('<h3>' + goods[5] + '</h3>');
                            if (goods[10] != null) {
                                htl.push('<h3><span>' + goods[10]
                                    + '</span><span></span></h3>');
                            }
                            htl.push('</div><div class="right right2"><h3>&yen;<span>'
                                + goods[3] + '</span></h3>');
                            htl.push('<p>' + goods[2] + '</p>');
                            htl.push('</div></a></div>');

                        }

                        if (ptlists != null && ptlists.length > 0) {
                            for (var y = 0; y < ptlists.length; y++) {
                                var ftp = ptlists[y];
                                htl.push('<div class="mil" onclick="CashBill(this)"><a href="#;"><div class="left">');
                                htl.push('<img src="' + basePath + ftp[2]
                                    + '" >');
                                htl.push('</div><div class="right">');
                                htl.push('<h3>' + ftp[1] + '</h3>');
                                htl.push('<h3><span>' + ftp[6]
                                    + '</span><span></span></h3>');
                                htl.push('</div><div class="right right2"><h3>&yen;<span>'
                                    + ftp[0] + '</span></h3>');
                                htl.push('<p>1</p>');
                                htl.push('</div></a><img src="'
                                    + basePath
                                    + 'images/ea/finance/NewPhoneOrders/ico-cu.png" class="cu"></div>');
                            }
                        }
                        htl.push(' <ul class="time2">');
                        htl.push('<li><i><img src="'
                            + basePath
                            + '/images/ea/finance/NewPhoneOrders/sellerOrder/ico-time.png" alt=""></i>');
                        if (goods[14] != null && goods[14]) {
                            htl.push('<p>订单时间:<span style="padding-left: 0.3rem;">'
                                + goods[14]
                                + '</span></p></li>');
                        }
                        htl.push('<li>');
                        htl.push('</ul>');
                        htl.push('<div class="com_btn" align="right">');
                        if (pp.fkStatus != '04') {
                            if (pp.contactInfo.length > 0) {
                                htl.push("<a href='#;'><input type='hidden' value='" + pp.contactInfo[0][0] + "'>");
                                htl.push("<input type='hidden' value='" + pp.contactInfo[0][1] + "'>");
                                htl.push("<input type='hidden' value='" + pp.contactInfo[0][2] + "'>");
                                if (isAndroid == true) {
                                    htl.push("<input type='button' value='联系买家' onclick='AndroidtoChat(this)'></a>")
                                } else if (isiOS == true) {
                                    htl.push("<input type='button' value='联系买家' onclick='ioschat(this)'></a>")
                                }
                            }
                        }

                        if (pp.fkStatus == "00") {
                            htl.push('<a onclick="share(this)"><input type="button" value="更换地址"></a>');
                            htl.push('<a href="#;"><input type="button" value="发货" onclick="cheg(this)"></a>');
                        } else if (pp.fkStatus == "02" && goods[16] != null && goods[15] != null) {
                            htl.push('<a href="#;"><input type="button" value="查看物流" onclick="wuliu(this)"></a>');
                        } else if (pp.fkStatus == "05") {
                            htl.push('<a onclick="orderReturns(this)"><input type="button" value="同意退货" id="tongyi"></a>');
                            htl.push('<a onclick="orderReturns(this)"><input type="button" value="拒绝退货" id="jujue"></a>');
                        } else if (pp.fkStatus == "16") {
                            htl.push('<a onclick="moneyReturns(this)"><input type="button" value="同意退款" id="tongyi"></a>');
                            htl.push('<a onclick="moneyReturns(this)"><input type="button" value="拒绝退款" id="jujue"></a>');
                        }
                        htl.push('</div>');

                        htl.push('</div>');
                    }
                } else {
                    htl.push('<div class="no">');
                    htl.push('<li><i><img src="' + basePath + '/images/ea/finance/NewPhoneOrders/sellerOrder/wu.png" class="wu"></i>');

                    htl.push('<p>目前还没有订单哦～</p>');
                    htl.push('</div>');
                }
                $("#" + obj + "").append(htl.join(""));
                getHeight(obj, pl);
            }
        });

}

window.onload = window.onresize = function () {
    var clientWidth = document.documentElement.clientWidth;
    document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640
        * 40 + 'px'
}