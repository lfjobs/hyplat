var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function () {

    if (originPage != "" && originPage != null) {
        requestPage = originPage.split("-");
        if (requestPage[0] == "win") {
            $("header").hide();
            $(".select_div").css("margin-top","0");
        }
    }else {
        $("header").show();
        $(".select_div").css("margin-top","2.16rem");
    }

    $(".overlay_text").find("span").text("正在加载，请稍候……");
    $(".overlay_text").show();
    $(".proAll").find(".classify_wrap").remove();

    $(".div-hy").each(function () {
        let id = $(this).attr("id");
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/industry/sajax_ea_getBusinessTypeByParentNum.jspa",
            async: false,
            dataType: "json",
            data: {
                "typePID": id
            },
            success: function (data) {
                var codeList = eval("(" + data + ")");
                let htmls = "";
                for (var i = 0; i < codeList.length; i++) {
                    htmls += "<div class='div-top-box'>" + codeList[i].typeName;
                    htmls += "<input type='hidden' class='fid' value='" + codeList[i].typeId + "'/>";
                    htmls += "</div>";
                    //codeList[i].typeId
                }
                $("#" + id).append(htmls);
            }
        });
    });

    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "ea/dserve/sajax_ea_industryListNewBybtid.jspa?",
        async: false,
        dataType: "json",
        data: {
            "staffid": staffid
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            if (member.flag == "1") {
                window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
            } else {
                var beanList = member.beanList;
                if (beanList != null && beanList.length > 0) {
                    //$(".div-LV2:eq(0)").css("margin-top","2.16rem");
                    //$(".div-LV2:eq(1)").css("margin-top","");
                    let htmls = "";
                    for (var i = 0; i < beanList.length; i++) {
                        htmls += "<div class='div-top-box'>" + beanList[i][3];
                        htmls += "<input type='hidden' class='fid' value='" + beanList[i][0] + "'/>";
                        htmls += "</div>";
                        //codeList[i].typeId
                    }
                    $(".div_tj_value").append(htmls);
                } else {
                    $(".div_tj").remove();
                }
            }
        }
    });

    $("#select-img").click(function () {
        $(".proAll .div-top").remove();
        $(".proAll .classify_wrap").remove();
        $(".select_div .div-top").remove();
        let param = $("#select-input").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa",
            async: false,
            dataType: "json",
            data: {
                level: 3,
                typeName: param
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList = member.beanList;

                if (beanList.length > 0) {
                    let htmls = "";
                    if (param == null || param == "") {
                        htmls += "<div class='classify_wrap'>";
                        let numl = 0;
                        for (var i = 0; i < beanList.length; i++) {
                            let a = beanList[i];
                            let codeid = a[0];
                            let ans = a[2];
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
                        $(".isShow").show();
                        $(".proAll").append(htmls);
                    } else {
                        htmls += "<div class='div-top'>";
                        for (var i = 0; i < beanList.length; i++) {
                            var a = beanList[i];
                            htmls += "<div class='div-top-box'>" + a[3];
                            htmls += "<input type='hidden' className='fid' value='" + a[0] + "'/>";
                            htmls += "</div>";
                        }
                        htmls += "</div>";
                        $(".isShow").hide();
                        $(".select_div").append(htmls);
                    }
                }else {
                    if (param != "") {
                        $(".isShow").hide();
                    }
                }
            }
        });

    });

    $("#select-img").trigger("click");
    $(".nest_page").show();
    $(".overlay_text").hide();


    //加载三四级工种
    $(document).on("click", ".level_box", function () {
        $(".overlay_text").find("span").text("正在加载，请稍候……");
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
                            htmls += "<div class='level_3 '><div class='level_3_tit '><i class='fold_level3'></i>" + a[3] + "<input type='hidden' class='tid' value='" + a[0] + "'/></div>";
                            htmls += "<div class='level_4 clearfix'>";
                            for (var j = 0; j < beanList.length; j++) {
                                var b = beanList[j];
                                var codepid = b[1];
                                var bns = b[2];
                                if (bns == "5") {
                                    if (codeid == codepid) {
                                        numl = numl + 1;
                                        htmls += "<div class='level_4_box'>" + b[3] + "<input type='hidden' class='fid' value='" + b[0] + "'/></div>";
                                    }
                                }
                            }
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

    //如果是服务平台跳新注册用户抢单页面
    $(document).on("click", ".level_wrap", function () {
        var t = $(this).children("div.level_box").children("div.level_text").children("div.level_1").text();
        if (t == "服务平台") {
            if (isAndroid) {
                Android.callqiangkehu();
            } else if (isiOS) {
                var url = "func=" + 'iosKnockCustomer';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    });

    //添加第三级工种
    $(document).on("click", ".level_3_tit", function () {
        var L_4 = $(this).parent().find(".level_4");
        if (L_4.length) {
            $(this).find(".fold_level3").toggleClass("fold_up").parent().parent().siblings().find(".fold_level3").removeClass("fold_up");
            L_4.slideToggle(200).parent().siblings().find(".level_4").slideUp(200);
        } else {
            $(".overlay_text").find("span").text("正在操作，请稍候……");
            $(".overlay_text").show();
            cwid = "";
            var t = $(this).text();
            var b = $(this).find(".tid").val();
            map(b);
            $(".nest_page").hide();
            packInit();
            $(".overlay_text").hide();
        }
    });
    //添加第四级工种
    $(document).on("click", ".level_4_box,.div-top-box", function () {
        $(".overlay_text").find("span").text("正在操作，请稍候……");
        $(".overlay_text").show();
        var a = $(this).parent().parent().find(".level_3_tit").text();
        var t = (a!=null&&a!=""?(a+">"):"")+$(this).text();
        var b = $(this).find(".fid").val();
        let gz = {
            "val": t,
            "id": b,
            "originPage":originPage
        }
        if (originPage != "" && originPage != null) {
            requestPage = originPage.split("-");
            switch (requestPage[1]) {
                case "gz":
                    window.parent.gzfun(gz);
                    // 隐藏 iframe 并重置其 src 属性
                    $('iframe').hide();
                    $('iframe').attr("src", "");
                    break;
                case "fb":
                    if(t=="服务平台"||b=="bType20250830AKEYDEP5ZJ0000001270"){
                        alert("服务平台为系统自动发布单子的类别，客户不能自行发布");
                    }else {
                        window.parent.gzfun(gz);
                        // 隐藏 iframe 并重置其 src 属性
                        $('iframe').hide();
                        $('iframe').attr("src", "");
                    }
                    break;
                case "yjfb":
                    if(t=="服务平台"||b=="bType20250830AKEYDEP5ZJ0000001270"){
                        alert("服务平台为系统自动发布单子的行业，客户不能自行发布");
                    }else {
                        localforage.setItem('gz', gz).then(function (value) {
                            window.parent.location.href = basePath+"/ea/dserve/ea_toaddpage.jspa?staffid="+staffid+"&sccId="+sccid+"&tle=1";
                        }).catch(function (err) {
                            console.error("存储参数时出错:", err);
                            // 提示用户或采取其他措施
                            alert("数据传输失败，请稍后再试。");
                        });
                        packInit();
                    }
                    break;
                default:
                    localforage.setItem('gz', gz).then(function (value) {
                        document.location.href = basePath+"/ea/dserve/ea_toaddpage.jspa?staffid="+staffid+"&sccId="+sccid+"&tle=1";
                    }).catch(function (err) {
                        console.error("存储参数时出错:", err);
                        // 提示用户或采取其他措施
                        alert("数据传输失败，请稍后再试。");
                    });
                    packInit();
                    break;
            }
        }else {
            localforage.setItem('gz', gz).then(function (value) {
                document.location.href = basePath+"/ea/dserve/ea_toaddpage.jspa?staffid="+staffid+"&sccId="+sccid+"&tle=1";
            }).catch(function (err) {
                console.error("存储参数时出错:", err);
                // 提示用户或采取其他措施
                alert("数据传输失败，请稍后再试。");
            });
            //map(b);
            //$(".nest_page").hide();
            packInit();
        }
        $(".overlay_text").hide();
    });


    $(".nest_back").click(function () {
        $(".nest_page").hide();
        packInit();
    });
});

//跳转地图
function map(scodid) {
    if (isAndroid) {
        Android.changeQiangdan(scodid);
    } else if (isiOS) {
        var url = "func=" + 'iosQiangDanMap';
        params = {'scodid': scodid};
        for (var i in params) {
            url = url + "&" + i + "=" + params[i];
        }
        window.webkit.messageHandlers.Native.postMessage(url);
    }
};

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
