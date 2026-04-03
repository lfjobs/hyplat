var posNum = "";
$(function () {
    var url = basePath + "/st/enroll/sajax_ea_getProductByType.jspa?companyID=" + companyID;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var list = member.list;
            var str = "";
            for (var i = 0; i < list.length; i++) {
                var entity = list[i];
                str += "<div class='sx_type_box'><a>" + entity[1] + "<span style='display: none'>" + entity[0] + "</span></a></div>";
            }
            $(".sx_type_wrap").append(str);
        }
    });
    loaded();
    getPosNum();
});

function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").offset().top + $(".last").height() - $("header").height() * 3 < $(window).height()) {
        if (pagenumber < pagecount) {
            loaded();
        }
    }
}

function loaded() {
    clearTimeout(t);
    pagenumber++;
    var url = basePath + "st/enroll/sajax_ea_getAjax.jspa?";
    $.ajax({
        url: url,
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "categoryName": categoryName,
            "categoryId": categoryId,
            "search": search,
            "companyID": companyID,
            "proName": ppname,
            "parName": pname,
            "allPro": allPro
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var pct = member.pct;
            if (pct == null) {
                pct = 1;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                var productlist = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var prostr = [];
                $(".last").removeClass("last");
                for (var i = 0; i < productlist.length; i++) {
                    var pro = productlist[i];
                    if (i == productlist.length - 1) {
                        prostr.push('<li class="last" onclick="goodsDetail(this)">');
                    } else {
                        prostr.push('<li onclick="goodsDetail(this)">');
                    }
                    prostr.push('<input type="hidden" value="' + pro[1] + '" id="ppid"/>');
                    prostr.push('<input type="hidden" value="' + pro[0] + '" id="goodsName"/>');
                    prostr.push('<input type="hidden" value="' + pro[6] + '" id="companyID"/>');
                    prostr.push('<input type="hidden" value="' + pro[4] + '" id="photo"/>');
                    prostr.push('<input type="hidden" value="' + pro[9] + '" id="remark"/>');
                    prostr.push('<input type="hidden" value="' + pro[5] + '" id="goodsID"/>');
                    prostr.push('<input type="hidden" value="' + pro[12] + '" id="ccompanyId"/>');
                    prostr.push('<input type="hidden" value="' + pro[10] + '" id="type"/>');
                    prostr.push('<input type="hidden" value="' + pro[3] + '" id="brand"/>');
                    prostr.push('<input type="hidden" value="' + pro[13] + '" id="categoryName"/>');
                    prostr.push('<input type="hidden" value="' + pro[14] + '" id="categoryId"/>');
                    prostr.push('<input type="hidden" value="' + pro[2] * pct + '" id="cost"/>');
                    if (pro[17] != null && pro[17] != "" && pro[15] != null && pro[15] != "") {
                        if (pro[17] == '00') {//促销活动
                            prostr.push('<a href="###" class="pro_box">');
                            prostr.push('<input type="hidden" value="3" id="priceType"/>');
                            prostr.push('<input type="hidden" value="'+pro[20]+'" id="activityid"/>');
                            prostr.push('<img src="' + basePath + pro[4] + '" class="pro_img" alt="">');
                            prostr.push('<div class="pro_tit">' + pro[0] + '</div>');
                            prostr.push('<input type="hidden" value="' + changeTwoDecimal(pro[15] * pct) + '" id="price"/>');
                            prostr.push('<span class="cx"><i></i></span>');
                            prostr.push('<div class="pro_price">');
                            prostr.push('<p style="float: left;padding-right: 0.5rem;">¥ ' + changeTwoDecimal(pro[15] * pct) + '</p>');
                            prostr.push('<p><span style="font-size: .45rem;color:#999;">原价<del>'+pro[2] * pct+'元</del></span></p>');
                            prostr.push('<span style="width: 56%;color: #818181;font-size: 0.55rem;text-indent: 0.1em;">' + pro[8] + '人已购买</span></div></a>')
                            activeStateUpdate(pro[1]);
                        }
                        if (pro[17] == '01') {//特价活动
                            prostr.push('<a href="###" class="pro_box">');
                            prostr.push('<input type="hidden" value="4" id="priceType"/>');
                            prostr.push('<input type="hidden" value="'+pro[20]+'" id="activityid"/>');
                            prostr.push('<img src="' + basePath + pro[4] + '" class="pro_img" alt="">');
                            prostr.push('<div class="pro_tit">' + pro[0] + '</div>');
                            prostr.push('<input type="hidden" value="' + changeTwoDecimal(pro[15] * pct) + '" id="price"/>');
                            prostr.push('<span class="tj"><i></i></span>');
                            prostr.push('<div class="pro_price">');
                            prostr.push('<p style="float: left;padding-right: 0.5rem;">¥ ' + changeTwoDecimal(pro[15] * pct) + '</p>');
                            prostr.push('<p><span style="font-size: .45rem;color:#999;">原价<del>'+pro[2] * pct+'元</del></span></p>');
                            prostr.push('<span style="width: 56%;color: #818181;font-size: 0.55rem;text-indent: 0.1em;">' + pro[8] + '人已购买</span></div></a>')
                            activeStateUpdate(pro[1]);
                        }
                    } else {
                        //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                        var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                        $.ajax({
                            url: url,
                            type: 'POST',
                            data: {"ppid": pro[1]},
                            async: true,
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                if (member.code == '200') {
                                    //活动状态更改成功
                                } else {
                                    //活动状态更改异常
                                }
                            },
                            error: function (err) {
                                console.log('err')
                            }
                        })
                        if (pro[16] != null && pro[16] != "") {//vip活动
                            prostr.push('<a href="###" class="pro_box">');
                            prostr.push('<input type="hidden" value="2" id="priceType"/>');
                            prostr.push('<input type="hidden" value="'+pro[19]+'" id="activityid"/>');
                            prostr.push('<img src="' + basePath + pro[4] + '" class="pro_img" alt="">');
                            prostr.push('<div class="pro_tit">' + pro[0] + '</div>');
                            prostr.push('<input type="hidden" value="' + changeTwoDecimal(pro[16] * pct) + '" id="price"/>');
                            prostr.push('<span class="vip"><i></i></span>');
                            prostr.push('<div class="pro_price">');
                            prostr.push('<p style="float: left;padding-right: 0.5rem;">¥ ' + changeTwoDecimal(pro[16] * pct) + '</p>');
                            prostr.push('<p><span style="font-size: .45rem;color:#999;">原价<del>'+pro[2] * pct+'元</del></span></p>');
                            prostr.push('<span style="width: 56%;color: #818181;font-size: 0.55rem;text-indent: 0.1em;">' + pro[8] + '人已购买</span></div></a>')
                        } else {
                            prostr.push('<a href="###" class="pro_box">');
                            prostr.push('<input type="hidden" value="0" id="priceType"/>');
                            prostr.push('<input type="hidden" value="'+pro[18]+'" id="activityid"/>');
                            prostr.push('<img src="' + basePath + pro[4] + '" class="pro_img" alt="">');
                            prostr.push('<div class="pro_tit">' + pro[0] + '</div>');
                            prostr.push('<input type="hidden" value="' + changeTwoDecimal(pro[2] * pct) + '" id="price"/>');
                            prostr.push('<div class="pro_price">');
                            prostr.push('<p style="float: left;padding-right: 0.5rem;">¥ ' + changeTwoDecimal(pro[2] * pct) + '</p>');
                            prostr.push('<span style="width: 56%;color: #818181;font-size: 0.55rem;text-indent: 0.1em;">' + pro[8] + '人已购买</span></div></a>')
                        }
                    }
                    prostr.push("</li>");
                    // prostr.push('<img src="'+basePath+pro[4]+'" alt="">');
                    // prostr.push('<div class="txt">');
                    // prostr.push('<h4>'+pro[0]+'</h4>');
                    // prostr.push('<p>&yen;<span>'+pro[2]+'</span></p><p class="shop"><span>'+pro[8]+'</span>人已购买</p></div></li>');
                }
                $(".pro_wrap ul").append(prostr.join(""));
                $(".pro_wrap ul li img").css("height", $(".con_shop ul li img").width() + "px");
                $(".pro_wrap ul").css("background", "#fff");
                $("body").attr("style", "background: #F2F2F2");
                getHeight();
            } else {
                $(".pro_wrap ul").append('<div><img style="width: 60%;margin: 20% 20% 0 20%;" src="' + basePath + 'images/WFJClient/DigitalMall/kongbai.png"/></div>');
                $("body").removeAttr("style");
                $(".pro_wrap ul").css("background", "#fff");
            }
        },
        error: function () {
            alert("产品加载失败");
        }
    });
}

//活动状态更改[改为01:已开启]
function activeStateUpdate(ppid) {
    var url = basePath + "ea/digitalmall/sajax_ea_activeStateUpdate.jspa";
    $.ajax({
        url: url,
        type: 'POST',
        data: {"ppid": ppid},
        async: true,
        success: function (data) {
            var member = eval("(" + data + ")");
            if (member.code == '200') {
                //活动状态更改成功
            } else {
                //活动状态更改异常
            }
        },
        error: function (err) {
            console.log('err')
        }
    })


}

function changeTwoDecimal(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        alert('function:changeTwoDecimal->parameter error');
        return false;
    }
    f_x = Math.round(f_x * 100) / 100;

    return f_x;
}

function searchPro() {
    clearTimeout(t);
    pagenumber = 0;
    search = '';
    $(".pro_wrap ul").empty();
    ppname = $(".search_inp").val();
    $("#search").hide();
    $(".filtrate_con").hide();
    $(".tab_box").removeClass("tab_act")
    loaded()
}

function sure_btnClick() {
    allPro = "";
    categoryId = $(".sx_type_box").find(".type_act span").text()
    $(".pro_wrap ul").empty();
    $(".filtrate_con").hide();
    $(".filtrate_tab a").removeClass("tab_act");
    loaded()
}

function screen(conditions) {
    $(".pro_wrap ul").empty();
    $(".filtrate_con").hide()
    $(".filtrate_tab a").removeClass("tab_act")
    pagenumber = 0;
    search = conditions;
    loaded()
}

//产品详情
function goodsDetail(obj) {
    var parms = [];
    parms.push("ppid=" + $(obj).find("#ppid").val());
    parms.push("&goodsName=" + $(obj).find("#goodsName").val());
    parms.push("&companyID=" + $(obj).find("#companyID").val());
    parms.push("&photo=" + $(obj).find("#photo").val());
    parms.push("&remark=" + $(obj).find("#remark").val());
    if (posNum != null && posNum != "") {
        if ($(obj).find("#priceType").val() == "2") {
            parms.push("&price=" + $(obj).find("#cost").val());
        } else {
            parms.push("&price=" + $(obj).find("#price").val());
        }
    }else{
        parms.push("&price=" + $(obj).find("#price").val());
    }

    parms.push("&goodsID=" + $(obj).find("#goodsID").val());
    parms.push("&ccompanyId=" + $(obj).find("#ccompanyId").val());
    parms.push("&licenceType=" + $(obj).find("#type").val())
    parms.push("&brand=" + $(obj).find("#brand").val());
    parms.push("&categoryName=" + $(obj).find("#categoryName").val());
    parms.push("&categoryId=" + $(obj).find("#categoryId").val());
    parms.push("&priceType=" + $(obj).find("#priceType").val());
    parms.push("&activityid=" + $(obj).find("#activityid").val());
    parms.push("&cost=" + $(obj).find("#cost").val());
    var url = basePath + "/st/enroll/ea_getEnroll.jspa?" + parms.join("");


    if (posNum != null && posNum != "") {
        var shuliang = "1";
        var priceType = $(obj).find("#priceType").val();
        var totalMoney = "";
        if(priceType=="2"){
            totalMoney = $(obj).find("#cost").val();
        }else {
            totalMoney = $(obj).find("#price").val();
        }
        var companyId = $(obj).find("#companyID").val();
        var ppid = $(obj).find("#ppid").val();
        //生成订单号

        $.ajax({
            url: basePath + "/ea/sm/sajax_ea_getJumBycomID.jspa",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                comID: companyId,
                ppid: ppid,
                posNum: posNum,
                price: totalMoney,
                itemNum: shuliang

            },
            success: function (data) {
                var mb = eval("(" + data + ")");
                var jum = mb.jum;


                document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?directUrl=" + encodeURIComponent(url) + "&journalNum=" + jum + "&totalMoney=" + totalMoney + "&totalNum=" + shuliang + "&posNum=" + posNum+"&fh=2&companyName="+companyName+"&comID="+companyID;


            }, error: function (data) {
                console.log("生成单号");
            }
        });

        return false;
    }
    window.location.href = url;
}
function getPosNum() {

    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
            if (posNum != "" && posNum != null) {
                var href = $(".cartList").find("a").eq(0).attr("href");
                $(".cartList").find("a").eq(0).attr("href", href + "&posNum=" + posNum);
            }
        }
    } catch (error) {
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;

            var href = $(".cartList").find("a").eq(0).attr("href");
            $(".cartList").find("a").eq(0).attr("href", href + "&posNum=" + posNum);


        }
    }

}

//判断是否是终端机器
function isExistPosNum(posNum) {
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        async: false,
        data: {
            posNum: posNum
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if (result != "0") {
                posNum = "";
            }

        },
        error: function (data) {
            // alert("验证失败");
        }

    });
    return posNum;
}