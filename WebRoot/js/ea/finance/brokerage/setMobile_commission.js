var pageNumber = 0;
var pageSize = 25;
var notoken=0;
var requestPage;
const PRICETITLE = {
    "retail-li": "零售价",
    "wholesale-li": "批发价",
    "activity-li": "活动价",
    "special-li": "特价",
    "vip-li": "VIP价"
};
const PRICETABLE = {
    "retail-li": "DT_PRO_SETUP",
    "wholesale-li": "dt_pro_wholesale",
    "activity-li": "",
    "special-li": "",
    "vip-li": "dt_pro_vip"
};
const PRICETABLEID = {
    "retail-li": "suid",
    "wholesale-li": "wholesaleId",
    "activity-li": "",
    "special-li": "",
    "vip-li": "vipId"
};
const PRICETYPETITLE = {
    "01": "添加",
    "02": "修改",
    "03": "查看",
    "04": "审核"
};

/**
 * 自动贩卖机操作员信息
 * @type {{posNum: (string|string), loginGuid: (string|string), companyid: (string|string), companyIdentifier: (string|string), staffName: (string|string), source: (string|string), hgcode: (string|string)}}
 */
const config = {
    posNum: getcookie("posNum"), //货柜终端机编号
    hgcode: getcookie("hgcode"), //货柜库房编号
    loginGuid: getcookie("loginGuid"), //登录id
    companyid: getcookie("comID"), //公司id
    staffName: getcookie("staffName"), //人员姓名
    user: getcookie("user"), //人员帐号
    companyIdentifier: getcookie("companyIdentifier"), //公司标识
    source:getcookie("source") //来源 scan:扫码 login:登录
}

$(function () {
    $(".div-bottom p").hide();
    $("input").attr("readonly", true);
    $("#title_li").text(PRICETITLE[yjtype]+PRICETYPETITLE[priceType]);

    $(".content .div-name .main_inp_right p:first-child").css("color", "#222");
    $(".content .div-name .main_inp_right p input").css("color", "#222");

    if (priceType == "03") {
        $(".submitUpdate").show();
    }

    if (priceType == "01" || priceType == "02") {
        $(".content .div-name .main_inp_right p:first-child").css("color", "#e8642d");
        $(".content .div-name .main_inp_right p input").css("color", "#e8642d");
        $("input").attr("readonly", false);
        $(".submitDraft").show();
        $(".submitAudit").show();
    }

    if (priceType == "02"&&($(".pname-p").text().trim()==null||$(".pname-p").text().trim()==""||$(".pname-p").text().trim()=="请选择")){
        localforage.getItem('params').then(function (value) {
            if (value && typeof value === 'object' && Object.keys(value).length > 0) {
                $("#ppid").val(value["ppId"]);
                $("#pname").val(value["ppname"]);
                $("#product-div p").text(value["ppname"]);
            }
            // 移除存储项并处理异常
            return localforage.removeItem('params');
        });
    }

    if (originPage!=""&&originPage!=null){
        requestPage=originPage.split("-");
        //iframe加载
        if (requestPage[0]=="win"){
            $("header").hide();
        }
        //调价
        if (requestPage[1]=="tj"){
            $(".div-bottom p").hide();
            $(".submitIsOk").show();
            if(codeing=="02"&&config!=null){
                MQTTconnect(config.posNum);
            }
        }
    }

    $(".num_input").each(function () {
        if ($(this).val() == '') $(this).val(0);
    });

    $(".num_input").focus(function () {
        if ($(this).val() == 0) $(this).val('');
    });

    $(".num_input").blur(function () {
        if ($(this).val() == '') $(this).val(0);
    });

    //弹出投资类型
    $(document).on("click", "#investment", function () {
        if (priceType == "03") {
            return false;
        } else {
            $(".investment").addClass("act");
        }
    });

    $(".investment_con").click(function (e) {
        e.stopPropagation();
    });

    $(".investment_con ul li").click(function (e) {
        e.stopPropagation();
        var t = $(this).text();

        $("#tz_type").text($(this).text());
        $("#tzt").val($(this).attr("value"));
        $(".investment").removeClass("act");
    });

    $(".close-confirm").click(function () {
        $(".tittle-p").text("");
        $(".div-tingyong").hide();
    });

    //选择产品
    $("#product-div").click(function () {
        if (priceType == "02") {
            return false;
        } else {
            $(".li-text").text("产品");
            pageNumber = 0;
            $(".ul-list").empty();
            $("#divList").show();
            getList();
        }
    });

    $("#close_li").click(function () {
        $("#divList").hide();
    });

    $(document).on("input", "#search", function () {
        $(".ul-list").empty();
        pageNumber = 0;
        pageCount = 0;
        getList();
    });

    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                getList();
            }
        }
    });

    //选中
    $(document).on("click", ".ul-list li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul-list .active").removeClass("active");
            $(this).addClass("active");
        }
    });

    //确定按钮
    $(".sec-bottom").click(function () {
        var li = $(".ul-list li.active");
        var length = $(li).length;
        if (length < 1) {
            return false;
        }
        $("#ppid").val($(li).attr("id"));
        $("#pname").val($(li).find(".ttext").text());
        $("#product-div p").text($(li).find(".ttext").text());

        $("#search").val("");
        $("#divList").hide();
    });

    $(".sumNum").on("input", function () {
        if ($(this).val() != "" && $(this).val() != " " && $(this).val() != null) {
            var re = /^(1-9|[1-9][0-9]?|100)%$/;
            if (isNaN($(this).val())) {
                $(this).css("color", "red");
            } else {
                sumPrice();
            }
        } else {
            $("input#yj").attr("value", "");
        }
    });

    //提交审核
    $(document).on("click", ".submitAudit", function () {
        $(this).removeClass("submitAudit");
        $(this).addClass("readonlyAudit");
        save("03");
    });

    //保存草稿
    $(document).on("click", ".submitDraft", function () {
        $(this).removeClass("submitAudit");
        $(this).addClass("readonlyAudit");
        save("02");
    });

    //确定
    $(".submitIsOk").on("click", function () {
        $(this).removeClass("submitAudit");
        $(this).addClass("readonlyAudit");
        save("00");
    });

    //修改
    $(".submitUpdate").on("click", function () {
        if ($("#state").val() == "03") {
            $(".tittle-p").text("当前状态审核中！驳回后才能修改");
            $(".div-tingyong").show();
            return;
        }

        if ($("#state").val() == "01") {
            $(".tittle-p").text("当前状态已删除！");
            $(".div-tingyong").show();
            return;
        }

        if ($("#state").val() == "00" || $("#state").val() == "02" || $("#state").val() == "04") {
            window.location.href = basePath + "/ea/mobile/ea_getPage.jspa?priceid=" + $("#priceid").val() + "&yjtype=" + yjtype + "&priceType=02";
        }
    });


    /*$(".jisuan", $("table#cpid")).live("keyup keydown focus blur", function (event) {
        if (this.value != "" && this.value != " " && this.value != null) {
            var re = /^(1-9|[1-9][0-9]?|100)%$/;
            if (isNaN(this.value)) {
                $(this).css("color", "red");
            } else {
                var $trThis = $(this).parent().parent().parent().parent().parent().parent();
                //出厂价
                var cc = parseFloat($.trim($trThis.find("input#cc").val()) == "" ? "0.00" : $.trim($trThis.find("input#cc").val()));
                //零售价
                var ls = parseFloat($.trim($trThis.find("input#ls").val()) == "" ? "0.00" : $.trim($trThis.find("input#ls").val()));
                //代理佣金
                var dl = 0;
                if (showType == "editYj") {
                    $(".editDlyj").each(function () {
                        dl = dl + parseFloat($.trim($(this).val()));
                    });
                } else {
                    $(".dl").each(function () {
                        dl = dl + parseFloat($.trim($(this).val()));
                    });
                }
                //佣金
                var yj = ls - cc - dl;
                $trThis.find("input#yj").attr("value", yj.toFixed(4));
                $trThis.find("input.bfb").each(function () {
                    var t = $(this).attr("title")
                    if ($(this).val() != "" && $(this).val() != null && $(this).val() != " ") {
                        var bfb = yj * (parseFloat($(this).val() == "" || $(this).val() == null ? "0.00" : $(this).val()) / 100);
                        if (bfb < 0)
                            bfb = 0;
                        $(this).parent().parent().find("span.jbs" + t).text(bfb.toFixed(3));
                    }
                });
            }
        } else {
            $(this).parent().parent().find("input#yj").attr("value", "");
        }
    });*/
});

function getList() {
    pageNumber = pageNumber + 1;
    $.ajax({
        url: basePath + "/ea/mobile/sajax_ea_getProductByStatus.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            search: $("#search").val(),
            yjtype: yjtype
        },
        success: function (data) {
            if (data != null) {
                var m = eval("(" + data + ")");
                var arry = m.pageForm;
                var htmlstr = [];
                pageNumber = arry.pageNumber;
                pageCount = arry.pageCount;
                $(".last1").remove();
                for (var i = 0; i < arry.list.length; i++) {
                    if (i == arry.list.length - 1) {
                        htmlstr.push("<li class='clearfix last1' id='" + arry.list[i][0] + "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i][0] + "'>");
                    }
                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<p class='ttext'>");
                    htmlstr.push(((arry.list[i][1] == null || arry.list[i][1] == "") ? "" : arry.list[i][1] + "  ") + arry.list[i][2]);
                    htmlstr.push("</p>");
                    htmlstr.push("</li>");
                }

                $(".ul-list").append(htmlstr.join(""));

            }
            console.log(data);
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function sumPrice() {
    //出厂价
    var cc = parseFloat($("input#cb_input").val() == "" ? "0.00" : $("input#cb_input").val());
    //零售价
    var ls = parseFloat($("#price_input").val() == "" ? "0.00" : $("#price_input").val());
    //代理佣金
    var dl = 0;
    $(".dl").each(function () {
        dl = dl + parseFloat($.trim($(this).val()));
    });
    //佣金
    var yj = ls - cc - dl;
    $("input#yw_input").attr("value", yj.toFixed(4));
}

function class_su(data){
    let classname ="";
    if (data == "02"){
        classname="submitDraft";
    }
    if (data == "03"){
        classname="submitAudit";
    }
    $(".readonlyAudit").addClass(classname);
    $(".readonlyAudit").removeClass("readonlyAudit");
    $(".loading").hide();
}

function save(state) {
    $(".loading").show();
    var bp = false;

    if(state == ""||state==null){
        bp=true;
        $(".tittle-p").text("数据错误!");
        $(".div-tingyong").show();
    }
    $(".sumNum").each(function () {
        if ($(this).val() == null || $(this).val() == "" || parseFloat($(this).val()) < 0) {
            bp = true;
            $(".tittle-p").text("成本价、零售价和佣金不能为空,并且为正数!");
            $(".div-tingyong").show();
        }
    });

    //出厂价
    var cc = parseFloat($.trim($("input#cb_input").val()) == "" ? "0.00" : $.trim($("input#cb_input").val()));
    //零售价
    var ls = parseFloat($.trim($("#price_input").val()) == "" ? "0.00" : $.trim($("#price_input").val()));
    //代理佣金
    var dl = 0;
    $(".dl").each(function () {
        dl = dl + parseFloat($.trim($(this).val()));
    });
    //佣金
    var yj1 = Number(ls - cc - dl).toFixed(4);
    var yj2 = Number($("#yw_input").val()).toFixed(4);
    if (yj1 != yj2) {
        bp = true;
        $(".tittle-p").text("佣金计算有误请重新填写！");
        $(".div-tingyong").show();
    }

    $(".qian").each(function () {
        var num = $("#p20170605KY3VAANZJG0000000003").val();
        if (parseFloat(num) > 0 && ($("#tzt").val() == ""||$("#tzt").val() =="00")) {
            bp = true;
            $(".tittle-p").text("请选择正确的投资设备类型");
            $(".div-tingyong").show();
        }
    });
    if (!bp) {
        var tt = false;
        $(".datatbl").each(function () {
            var re = /^(1-9|[1-9][0-9]?|100)%$/;
            $(".yjbl").each(function () {
                if ($(this).val() != null && $(this).val() != "" && $(this).val() != " ") {
                    if (!re.test($(this).val())) {
                        $(this).css({color: "red",background_color:"balck"});
                        tt = true;
                    } else {
                        $(this).css({color: "gray"});
                    }
                }
            });
        });
        if (tt) {
            bp = true;
            $(".tittle-p").text("数据格式不正确");
            $(".div-tingyong").show();
        }
    }

    if (bp) {
        class_su(state);
        return;
    }else {
        var formData = $("#proForm").serialize();
        $.ajax({
            url: basePath + "/ea/mobile/sajax_ea_save.jspa?state="+state,
            type: "get",
            dataType: "json",
            aysnc: false,
            data: formData,
            success: function (data) {
                if (data != null) {
                    var m = eval("(" + data + ")");
                    if (m.flag) {
                        var priceid = m.priceid;
                        //alert("提交成功");
                        if(originPage!=null&&originPage=="win-tj"){
                            //更新显示屏商品信息
                            updatePro($("#depotCoding").val());
                            const $parentWin = $(window.parent.document);
                            /*$('.div-buhuo', $parentWin).hide();
                            $('.div-buhuo', $parentWin).find("iframe").attr("src", "");*/
                            window.parent.location.reload();
                        }else {
                            if (state == "02"){
                                //$(".tittle-p").text("保存成功");
                                window.location.href = basePath + "/page/ea/main/finance/brokerage/mobile_commission_list.jsp";
                            }else {
                                let title = $("#pname").val() + PRICETITLE[yjtype];
                                localStorage.setItem("title", title + "佣金设置");
                                localStorage.setItem("source", "佣金设置");
                                localStorage.setItem("htmlUrl", "/ea/mobile/ea_getPage.jspa?priceid=" + priceid + "&yjtype=" + yjtype + "&priceType=" + priceType);
                                localStorage.setItem("tableName", PRICETABLE[yjtype]);
                                localStorage.setItem("idName", PRICETABLEID[yjtype]);
                                localStorage.setItem("idValue", priceid);
                                localStorage.setItem("stateName", "state");
                                localStorage.setItem("stateValue", "00");
                                localStorage.setItem("refundValue", "04");
                                window.location.href = basePath + "page/ea/main/office_ea/contract/selectCompany.jsp?typee=L";
                                $(".loading").hide();
                            }
                        }
                    } else {
                        console.log(m.message);
                        class_su(state);
                    }
                }
            },
            error: function (data) {
                class_su(state);
                console.log(data);
            }
        });
    }
}