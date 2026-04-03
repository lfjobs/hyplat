/**
 * 新版数字地球商城
 */
var posNum = "";
$(document).ready(function () {
    getPosNum();
    search = 'newest';
    loaded();
    shopCartGoodNum();


    $("header").attr("style", "height:" + $(window).height() * 0.08 + "px;line-height:" + $(window).height() * 0.08 + "px;position:fixed;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;z-index:1;" + "top:" + $(window).height() * 0.08 + "px");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.92 + "px;position:relative;");
    $(".mil_shop ul li").css("line-height", $(window).height() * 0.08 - 7 + "px");
    $(".mil_shop").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".con_shop").attr("style", "padding-top:" + $(window).height() * 0.08 + "px;");
    $(".mil_shop .case").css("top", $(window).height() * 0.08 - 7 + "px");
    $(".grade-eject").css("top", $(window).height() * 0.16 - 6 + "px");
    $(".con_shop ul li img").css("height", $(".con_shop ul li img").width() + "px");
    $(".alert_").css("height", $(window).height() + "px");
    $(".alert_search .top").css("height", $(window).height() * 0.08 - 1 + "px");
    $(".alert_2").attr("style", "padding-top:" + $(window).height() * 0.08 + "px;")
    $(".con_shop").css("padding-top", $(window).height() * 0.08 - 6 + "px");
    $("#sales").css("color", "#ff5816");
    $(".mil_shop ul #sales .screen").hide();
    /*黑色箭头隐藏*/
    $(".mil_shop ul #sales .screen_").show();
    /*黄色箭头显示*/

    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    /*筛选分类*/
    $("#screen").click(function () {
        search = '';
        $(".overlay_text").show();
        $(".nest_bd").find(".classify_wrap").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=3",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList = member.beanList;

                if (beanList.length > 0) {
                    var htmls = "<div class='classify_wrap'>";
                    var numl = 0;
                    for (var i = 0; i < beanList.length; i++) {
                        var a = beanList[i];
                        var codeid = a[0];
                        var ans = a[2];
                        if (ans == "2") {
                            numl = numl + 1;
                            htmls += "<div class='level_wrap'><div class='level_box'><i class='classify_ico list_ico_0" + numl + "'></i>";
                            htmls += "<div class='level_text'><div class='level_1'>" + a[3] + "<input type='hidden' class='oid' value='" + a[0] + "'/></div>";
                            htmls += "<div class='level_2'>";
                            for (var j = 0; j < beanList.length; j++) {
                                var b = beanList[j];
                                var codepid = b[1];
                                var bns = b[2];
                                if (codeid == codepid) {
                                    htmls += b[3] + "/<input type='hidden' class='tid' value='" + b[0] + "'/>";
                                }
                            }
                            htmls += "</div></div><i class='fold_ico'></i></div><div class='level_fold'></div></div>";
                        }
                    }
                    htmls += "</div>";
                    $(".nest_bd").append(htmls);
                }
            }
        });
        $(".nest_page").show();
        $(".overlay_text").hide();
    });

    $(".nest_back").click(function () {
        $(".nest_page").hide();
        packInit();
    });


    //加载三四级行业
    $(document).on("click", ".level_box", function () {
        $(".overlay_text").show();
        var pid = $(this).find(".oid").val();
        var level_fold = $(this).parent().find(".level_fold");
        $(this).parent().find(".level_3").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=5&pid=" + pid,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList = member.beanList;

                if (beanList.length > 0) {
                    var htmls = "";

                    for (var i = 0; i < beanList.length; i++) {
                        var a = beanList[i];
                        var codeid = a[0];
                        var ans = a[2];
                        if (ans == "4") {
                            var numl = 0;
                            var claval = "no_level_4";
                            htmls += "<div class='level_3 '><div class='level_3_tit '>" + a[3] + "<input type='hidden' class='tid' value='" + a[0] + "'/></div>";
                            htmls += "<div style='width: 17.2rem;' class='level_4 clearfix'>";
                            // for (var j=0;j<beanList.length;j++){
                            //     var b = beanList[j];
                            //     var codepid=b[1];
                            //     var bns=b[2];
                            //     if(bns=="5"){
                            //         if(codeid==codepid){
                            //             numl=numl+1;
                            //             htmls+="<div class='level_4_box'>"+b[3]+"<input type='hidden' class='fid' value='"+b[0]+"'/></div>";
                            //         }
                            //     }
                            // }
                            htmls += "</div>";
                            claval = "";
                            htmls += "</div>";
                        }
                    }
                    level_fold.append(htmls);
                    level_fold.find(".level_3").each(function () {
                        var level_4 = $(this).find(".level_4").html();
                        if (level_4 == "") {
                            $(this).addClass("no_level_4");
                            $(this).find(".level_4").remove();
                        }
                    });

                }
            }
        });

        $(".level_4").each(function () {
            $(this).slideUp(200);
        });

        $(this).parent().find(".level_fold").slideToggle(200)
            .end().find(".fold_ico").toggleClass("fold_up")
            .end().siblings().find(".level_fold").slideUp(200)
            .end().find(".fold_ico").removeClass("fold_up");

        $(".fold_level3").each(function () {
            $(this).removeClass("fold_up");
        });
        $(".overlay_text").hide();
    });

    $(document).on("click", ".level_3_tit", function () {
        var L_4 = $(this).parent().find(".level_4");
        if (L_4.length) {
            $(this).find(".fold_level3").toggleClass("fold_up").parent().parent().siblings().find(".fold_level3").removeClass("fold_up");
            L_4.slideToggle(200).parent().siblings().find(".level_4").slideUp(200);
        } else {
            var t = $(this).text();
            var b = $(this).find(".tid").val();
            tradecode = $(this).text();
            pagenumber = 0;
            $(".con_shop ul").empty();
            ajax();
            $("#ddscodeid").val(b);
            $(".nest_page").hide();
            packInit();
        }
    });

    $(document).on("click", ".level_4_box", function () {
        var a = $(this).parent().parent().find(".level_3_tit").text();
        var t = $(this).text();
        var b = $(this).find(".fid").val();
        $("#selsct_classify").val(a + ">" + t);
        $("#ddscodeid").val(b);
        $(".nest_page").hide();
        tradecode = $(this).text();
        pagenumber = 0;
        packInit();
        $(".con_shop ul").empty();
        ajax();
    });


    // $("#screenssss").on("click",function(){
    // 	findIndustry ("", "grade-w");
    // 	$(this).css("color","#ff5816");
    //     $(".grade-eject").toggleClass("display");
    //     $(".grade-eject").css("z-index","9");
    //     $(".mil_shop .case").removeClass("display");
    //     if($(".grade-eject").hasClass("display")){
    //         $(".alert_").addClass("display");
    //     }else{
    //         $(".alert_").removeClass("display");
    //     }
    //     $(".mil_shop ul #sales .p").text("综合排序");
    //     $("#pop").css("color","#666");
    //     search='';
    //     /*去除筛选分类颜色*/
    //     $("#sales .screen").removeClass("screen2");
    //     $("#sales .screen_").removeClass("screen2");
    //     $(".mil_shop ul #sales .screen").show();   /*黑色箭头显示*/
    //     $(".mil_shop ul #sales .screen_").hide();  /*黄色箭头隐藏*/
    //     $(".mil_shop ul #sales").css("color","#666");
    //     $(".grade-w>li").css("background","");
    //     $(".grade-t>li").css("background","");
    //     $(".grade-t>li").css("border-bottom","");
    // });
    // $(document).on("click",".grade-w>li",function(){
    // 	var codepid=$(this).attr("class");
    // 	findIndustry (codepid, "grade-t");
    // 	$(this).css("background","#eee");
    // 	$(this).siblings().css("background","");
    //     $(".grade-t").css("left","50%");
    // });
    // $(document).on("click",".grade-t>li",function(){
    // 	$(this).css("border-bottom","solid 1px #ff7c08");
    // 	$(this).css("background","#fff");
    // 	$(this).siblings().css("border-bottom","");
    // 	$(this).siblings().css("background","");
    // 	$(".con_shop ul").empty();
    // 	tradecode=$(this).text();
    // 	pagenumber=0;
    // 	ajax ();
    // 	$(".alert_").hide();
    //     $(".alert_").removeClass("display");
    //     $(".grade-eject").removeClass("display");
    //     $(".grade-eject").removeClass("grade-w-roll");
    // });
    $(".case dd").on("click", function () {
        var txt = $(this).text();
        $(this).addClass("active").siblings().removeClass("active");
        $(".con_shop ul").empty();
        $("#pop").css("color", "#666");
        search = $(this).attr("id");
        pagenumber = 0;
        ajax();
        $(".mil_shop .case").hide();
        $(".mil_shop .case").removeClass("display");
        $(".alert_").hide();
        $(".alert_").removeClass("display");
        $(".mil_shop ul #sales .p").text(txt);
        $(".mil_shop ul #sales").css("color", "#ff5816");
        $(".mil_shop ul #sales .screen").hide();
        /*黑色箭头隐藏*/
        $(".mil_shop ul #sales .screen_").show();
        /*黄色箭头显示*/
        if ($(".screen").hasClass("screen2")) {
            $(".alert_").addClass("display");
        } else {
            $(".alert_").removeClass("display");
        }

        if ($(".mil_shop .case").hasClass("display")) {
            $("#sales .screen").addClass("screen2");
            $("#sales .screen_").addClass("screen2");

        } else {
            $("#sales .screen").removeClass("screen2");
            $("#sales .screen_").removeClass("screen2");
            $(".alert_").removeClass("display");
        }
    });
    $("#pop").on("click", function () {
        search = $(this).attr("id");
        $(this).css("color", "#ff5816");
        $("#screen").css("color", "#666");
        $(".mil_shop ul #sales .p").text("综合排序");
        $("#sales .screen").removeClass("screen2");
        $("#sales .screen_").removeClass("screen2");
        $(".case dd").removeClass("active");
        $(".mil_shop .case").removeClass("display");
        $(".grade-eject").removeClass("display");
        if ($(".grade-eject").hasClass("display")) {
            $(".alert_").addClass("display");
        } else {
            $(".alert_").removeClass("display");
        }

        /*去除筛选分类颜色*/
        $("#sales .screen").removeClass("screen2");
        $("#sales .screen_").removeClass("screen2");
        $(".mil_shop ul #sales .screen").show();
        /*黑色箭头显示*/
        $(".mil_shop ul #sales .screen_").hide();
        /*黄色箭头隐藏*/
        $(".mil_shop ul #sales").css("color", "#666");


        pagenumber = 0;
        $(".con_shop ul").empty();
        ajax();
    });
    $(".alert_").on("click", function () {
        $(".mil_shop .case").hide();
        $(".mil_shop .case").removeClass("display");
        $(".alert_").hide();
        $(".alert_").removeClass("display");
        $(".grade-eject").removeClass("display");
        $(".grade-eject").removeClass("grade-w-roll");
        if ($(".mil_shop .case").hasClass("display")) {
            $("#sales .screen").addClass("screen2");
            $("#sales .screen_").addClass("screen2");
        } else {
            $("#sales .screen").removeClass("screen2");
            $("#sales .screen_").removeClass("screen2");
            $(".alert_").removeClass("display");
        }
    });
    /* $(".alert_").on("touchstart",function() {
     $(".mil_shop .case").hide();
     $(".mil_shop .case").removeClass("display");
     $(".alert_").hide();
     $(".alert_").removeClass("display");
     $(".grade-eject").removeClass("display");
     $(".grade-eject").removeClass("grade-w-roll");
     });*/
    /*销量*/
    $("#sales").on("click", function () {
        $(".mil_shop .case").toggleClass("display");
        $(".grade-eject").removeClass("display");
        $("#screen").css("color", "#666");
        if ($(".mil_shop .case").hasClass("display")) {
            $(".alert_").addClass("display");
            $("#sales .screen").addClass("screen2");
            $("#sales .screen_").addClass("screen2");
        } else {
            $(".alert_").removeClass("display");
            $("#sales .screen").removeClass("screen2");
            $("#sales .screen_").removeClass("screen2");
        }
    });

    //绑定滚动条事件
    $('.content').scroll(function () {
        var sTop = $(".content").scrollTop();
        var sTop = parseInt(sTop);
        var height = $(window).height() * 1;
        if (sTop >= height) {
            $(".return").slideDown();
            $(".return").show();
        } else {
            $(".return").hide();
            $(this).unbind("bind");
        }
    });
    $(".return").click(function () {
        $(".content").scrollTop(0);
    })

    /* 搜索 */
    $("header ul li #search").click(function () {
        search = "";
        tradecode = "";
        $(".alert_2").show();
        $.ajax({
            url: basePath + "ea/digitalmall/sajax_ea_ajaxSearchHistory.jspa?",
            type: "get",
            async: false,
            success: function (data) {
                var member = eval("(" + data + ")");
                var list = member.shlist;
                if (list != null && list.length > 0) {
                    $h = $(".con").find("div#history");
                    $h.empty();
                    var pro = [];
                    pro.push('<h5>历史<img src="' + basePath + 'images/WFJClient/DigitalMall/ico-delete.png" alt="" class="det"></h5>');
                    pro.push('<div class="mil_c">');
                    for (var o in list) {
                        pro.push('<p>' + list[o].shname + '</p>');
                    }
                    pro.push('</div>');
                    $h.append(pro.join(""));
                }
            }

        });
        $("#search_sc").val("");
        $("#ss").show();
    });

    if (flag == "search") {
        $("#search").trigger("click");
    }
    $("header ul li #ssret").click(function () {
        $("#ss").hide();
    });
    $(document).on("click", ".mil_c p", function () {
        pagenumber = 0;
        $(".con_shop ul").empty();
        ppname = $(this).text();
        ajax();
        $(".alert_2").hide();
        $("#search_sc").val(ppname);
    });
    $(document).on("click", ".det", function () {
        $.ajax({
            url: basePath + "/ea/digitalmall/sajax_ea_delSearchHistory.jspa?",
            type: "get",
            async: false,
            success: function (data) {
                if (data == '1') {
                    $(".con").find("div#history").empty();
                }
            }
        })
    });
});

//初始化折叠选择行业分类
function packInit() {
    $(".level_fold").each(function () {
        $(this).slideUp(200);
    });
    $(".level_4").each(function () {
        $(this).slideUp(200);
    });
    $(".fold_ico,.fold_level3").each(function () {
        $(this).removeClass("fold_up");
    });
}

function ret() {
    if (flag == "search") {
        document.location.href = basePath + "/ea/wfjshop/ea_getWFJshops.jspa";
        return;
    }
    $("#ss").hide();
    ppname = '';
    search = '';
    tradecode = '';
    $(".con_shop ul").empty();
    ajax();
}

function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").offset().top + $(".last").height() - $("header").height() * 3 < $(window).height()) {
        if (pagenumber < pagecount) {
            loaded();
        }
    }
}

function loaded() {
    ajax();
}

function sousuo() {
    clearTimeout(t);
    pagenumber = 0;
    search = '';
    tradecode = '';
    $(".con_shop ul").empty();
    ppname = $("#search_sc").val();
    if (ppname == '') {
        prompt("请输入关键词！");
    } else {
        ajax();
        $(".alert_2").hide();
        $("#screen").css("color", "#666");
        $("#pop").css("color", "#666");
        $(".mil_shop ul #sales .p").text("综合排序");
        $("#sales .screen").removeClass("screen2");
        $("#sales .screen_").removeClass("screen2");
        $(".case dd").removeClass("active");
        $(".mil_shop .case").removeClass("display");
        $(".grade-eject").removeClass("display");
        if ($(".grade-eject").hasClass("display")) {
            $(".alert_").addClass("display");
        } else {
            $(".alert_").removeClass("display");
        }

        /*去除筛选分类颜色*/
        $("#sales .screen").removeClass("screen2");
        $("#sales .screen_").removeClass("screen2");
        $(".mil_shop ul #sales .screen").show();
        /*黑色箭头显示*/
        $(".mil_shop ul #sales .screen_").hide();
        /*黄色箭头隐藏*/
        $(".mil_shop ul #sales").css("color", "#666");
        $("#search_sc").val(ppname);
    }
}

function ajax() {
    clearTimeout(t);
    pagenumber++;
    var url = basePath + "ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "proName": ppname,
            "tradecode": tradecode,
            "search": search
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
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
                    prostr.push('<input type="hidden" value="' + pro[5] + '" id="goodsid"/>');
                    prostr.push('<input type="hidden" value="' + pro[6] + '" id="companyid"/>');
                    prostr.push('<input type="hidden" value="' + pro[10] + '" id="ccompanyid"/>');

                    prostr.push('<input type="hidden" value="' + pro[0] + '" id="goodsName"/>');
                    prostr.push('<input type="hidden" value="' + pro[4] + '" id="photo"/>');
                    prostr.push('<input type="hidden" value="' + pro[16] + '" id="remark"/>');
                    prostr.push('<input type="hidden" value="' + pro[15] + '" id="type"/>');
                    prostr.push('<input type="hidden" value="' + pro[3] + '" id="brand"/>');
                    prostr.push('<input type="hidden" value="' + pro[17] + '" id="categoryName"/>');
                    prostr.push('<input type="hidden" value="' + pro[18] + '" id="categoryId"/>');
                    prostr.push('<input type="hidden" value="' + pro[2] + '" id="cost"/>');
                    prostr.push('<input type="hidden" value="' + pro[14] + '" id="hdtype"/>');
                    prostr.push('<img src="' + basePath + pro[4] + '" onerror="this.src="' + basePath + '/images/ea/production/forum/reportAnError.png\'" alt="">');
                    prostr.push('<div class="txt">');
                    prostr.push('<h4>' + pro[0] + '</h4>');
                    prostr.push('<div class="clearfix">');
                    if (pro[14] != null && pro[14] != "" && pro[12] != null && pro[12] != "") {
                        if (pro[14] == '00') {//促销活动
                            prostr.push('<p>活动价:&yen;<span>' + pro[12] + '</span></p>');
                            prostr.push('<input type="hidden" value="' + pro[12] + '" id="price"/>');
                            prostr.push('<input type="hidden" value="' + pro[20] + '" id="activityid"/>');
                            prostr.push('<input type="hidden" value="3" id="priceType"/>');
                        }
                        if (pro[14] == '01') {//特价活动
                            prostr.push('<p>特价:&yen;<span>' + pro[12] + '</span></p>');
                            prostr.push('<input type="hidden" value="' + pro[12] + '" id="price"/>');
                            prostr.push('<input type="hidden" value="' + pro[20] + '" id="activityid"/>');
                            prostr.push('<input type="hidden" value="4" id="priceType"/>');
                        }
                        activeStateUpdate(pro[1]);
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
                        });
                    }
                    if (pro[13] != null && pro[13] != "") {//vip活动
                        prostr.push('<p>VIP价:&yen;<span>' + pro[13] + '</span></p>');
                        prostr.push('<input type="hidden" value="' + pro[13] + '" id="price"/>');
                        prostr.push('<input type="hidden" value="' + pro[19] + '" id="activityid"/>');
                        prostr.push('<input type="hidden" value="2" id="priceType"/>');
                    } else {
                        prostr.push('<input type="hidden" value="' + pro[2] + '" id="price"/>');
                        prostr.push('<input type="hidden" value="0" id="priceType"/>');
                        prostr.push('<p>零售价:&yen;<span>' + pro[2] + '</span></p>');
                    }
                    prostr.push('</div>');
                    prostr.push('<p class="shop"><span>' + pro[8] + '</span>人已购买</p></div></li>');
                }
                $(".con_shop ul").append(prostr.join(""));
                $(".con_shop").attr("style", "padding-top:" + $(window).height() * 0.08 + "px;");
                $(".con_shop ul li img").css("height", $(".con_shop ul li img").width() + "px");
                $(".con_shop ul").css("background", "#fff");
                getHeight();
            } else {
                $(".con_shop ul").empty();
                $(".con_shop ul").append('<div><img style="width: 60%;margin: 20% 20% 0 20%;" src="' + basePath + 'images/WFJClient/DigitalMall/kongbai.png"/></div>');
                $(".con_shop ul").css("background", "#fff");
            }
        },
        error: function () {
            alert("产品加载失败");
        }
    });
}

//活动状态更改
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

//产品详情
function goodsDetail(obj) {
    var type = $(obj).find("#type").val();
    var parms = [];
    var url = "";
    parms.push("ppid=" + $(obj).find("#ppid").val());
    parms.push("&ccompanyId=" + $(obj).find("#ccompanyid").val());
    if (type == "学员报名") {
        parms.push("&goodsName=" + $(obj).find("#goodsName").val());
        parms.push("&companyID=" + $(obj).find("#companyid").val());
        parms.push("&photo=" + $(obj).find("#photo").val());
        parms.push("&remark=" + $(obj).find("#remark").val());
        if (posNum != null && posNum != "") {
            if ($(obj).find("#priceType").val() == "2") {
                parms.push("&price=" + $(obj).find("#cost").val());
            } else {
                parms.push("&price=" + $(obj).find("#price").val());
            }
        } else {
            parms.push("&price=" + $(obj).find("#price").val());
        }

        parms.push("&goodsID=" + $(obj).find("#goodsid").val());
        parms.push("&licenceType=" + $(obj).find("#type").val())
        parms.push("&brand=" + $(obj).find("#brand").val());
        parms.push("&categoryName=" + $(obj).find("#categoryName").val());
        parms.push("&categoryId=" + $(obj).find("#categoryId").val());
        parms.push("&priceType=" + $(obj).find("#priceType").val());
        parms.push("&activityid=" + $(obj).find("#activityid").val());
        parms.push("&cost=" + $(obj).find("#cost").val());
        url = basePath + "/st/enroll/ea_getEnroll.jspa?" + parms.join("");
    } else {
        parms.push("&goodsid=" + $(obj).find("#goodsid").val());
        parms.push("&companyId=" + $(obj).find("#companyid").val());
        url = basePath + "ea/wfjshop/ea_doodsDetail.jspa?" + parms.join("");
    }
    window.location.href = url;
}

function findIndustry(codepid, position) {
    var url = basePath + "/ea/industry/sajax_ea_getIndustry.jspa?codePID=" + codepid;
    $.ajax({
        url: url,
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var list = member.industryList;
            var htl = [];
            for (var i = 0; i < list.length; i++) {
                htl.push("<li class='" + list[i].codeID + "'>" + list[i].codeValue + "</li>");
            }
            $("." + position).html(htl.join(""));
        },
        error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
}

function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}

//查询购物车商品数目
function shopCartGoodNum() {
    var url = basePath + "ea/digitalmall/sajax_ea_shopCartGoodNum.jspa?d=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            posNum: posNum
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var goodNum = member.goodNum;
            $(".num0").find("span").text(goodNum);
            $(".num0").css("display", "inline");
        },
        error: function () {
            alert("查询购物车商品数失败");
        }
    });
}

function getPosNum() {

    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
            if (posNum != "" && posNum != null) {
                var href = $(".cartList").find("a").eq(0).attr("href");
                $(".cartList").find("a").eq(0).attr("href", href + "?posNum=" + posNum);
            }
        }
    } catch (error) {
        if (($(window).width() == 1080 && $(window).height() == 1546) || ($(window).width() == 534 && $(window).height() == 636)) {
            posNum = 123;

            var href = $(".cartList").find("a").eq(0).attr("href");
            $(".cartList").find("a").eq(0).attr("href", href + "?posNum=" + posNum);


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
