$(function () {
    var u = window.navigator.userAgent;
    var isWin=u.match(/IEMobile/i)? true : false;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    //LodopFuncs.js

    //价格计算 自定义保留几位小数（四舍五入）
    $.getFloat = function (number, n) {
        n = n ? parseInt(n) : 0;
        if (n <= 0) {
            return Math.round(number);
        }
        number = Math.round(number * Math.pow(10, n)) / Math.pow(10, n); //四舍五入
        //number = Number(number).toFixed(n); //补足位数
        return number;
    };

    //获取地址栏参数值
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    };
    staffID=$.getUrlParam("staffID");
    var tt = "";

    //点击一级
    $(document).on("click", ".nav-1 li", function () {
        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");

        if ($(this).index() == 0) {//点击全部
            $(".nav-2").hide();
            codeID = "";
            pageNumber = 0;
            pageCount = 0;
            $(".ul_list_sp").html("");
            if (tt != null && tt != "") {
                clearTimeout(tt);
            }
            productCate(codeID, "");
        } else {
            var codePID = $(this).find(".codePID").text();
            secondCate(codePID);
        }


    })


    //二级标签点击选中
    $(document).on("click", ".nav-2 li", function () {
        pageNumber = 0;
        pageCount = 0;
        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");
        codeID = $(this).find(".codeID").text();
        productCate(codeID, "");
    })


    //商品点击
    $(document).on("click", ".ul_list_sp li", function () {
        $(".weigh").show();
        $("body").addClass("body_yc");

        $(".chengzhong_js .goodsname").val($(this).find(".gp").text());
        $(".chengzhong_js .plu").val($(this).find(".plu").text());
        $(".chengzhong_js .pr").val($(this).find(".pr").text());
        $(".chengzhong_js .prcc").text($(this).find(".prc").text());
        $(".chengzhong_js .hhh").text($(this).find(".al").text());
        $(".chengzhong_js .pd").text($(this).find(".ppd").text());
        $(".chengzhong_js .ppprit").text($(this).find(".prit").text());
        $(".chengzhong_js .cccostm").text($(this).find(".costm").text());
        $(".chengzhong_js .aaactivityID").text($(this).find(".activityIDm").text());

        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");


        if ($(this).find(".prc").text() == "KGM") {
            $(".chengzhong_js .weight").show();
            $(".chengzhong_js .inputnum").hide();
            $(".chengzhong_js .wvalue").val("0.00");


        } else {
            $(".chengzhong_js .weight").hide();
            $(".chengzhong_js .inputnum").show();
            $(".chengzhong_js #inputnum").val("");
            $(".chengzhong_js #inputnum").focus();
        }

        $(".totalMoney").text("0.00");

    });
    //加载一级分类
    firstCate();
    //初始化电子秤设备
    initWeightDevice();
    codeID = "";
    productCate(codeID, "");
    //清零
    $("input.zero").click(function () {
        if (isAndroid == true) {
            Android.weighingZeroAndroid();
            if (!$(".chengzhong_js .inputnum").is(':hidden')) {
                $(".chengzhong_js #inputnum").val("");
            } else {
                $(".chengzhong_js .wvalue").val("0.0");
            }
            $(".totalMoney").text("0.00");
        }else if(isWin==true){
            $.ajax({
                url: "http://" + window.location.host + "/api/Scale/Zero",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                    if (!$(".chengzhong_js .inputnum").is(':hidden')) {
                        $(".chengzhong_js #inputnum").val("");
                    } else {
                        $(".chengzhong_js .wvalue").val("0.0");
                    }
                },
                error: function (XMLHttpRequest) {
                    alert("归零失败, " + XMLHttpRequest.responseJSON.Message);
                }
            })
        } else {
            alert("请在安卓设备或者windows设备访问");
        }
    });

    //去皮
    $("input.peeled").click(function () {
        if (isAndroid == true) {
            Android.weighingTareAndroid();

        }else if(isWin==true){
            $.ajax({
                url: "http://" + window.location.host + "/api/Scale/Tare",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                    alert("去皮成功");
                },
                error: function (XMLHttpRequest) {
                    alert("去皮失败, " + XMLHttpRequest.responseJSON.Message);
                }
            })
        } else {
            alert("请在安卓设备访问");
        }
    });


    //加入购物车

    $(".isok").click(function () {


        var money = $(".chengzhong_js .totalMoney").text();
        if (money == "" || Number(money) <= 0) {
            $(".mm-alert .ct").text("重量不能小于等于0");
            $(".mm-alert").show();
            return false;
        }

        var al = $(".chengzhong_js .hhh").text();

        var ppID = $(".chengzhong_js .pd").text();
        var barCode = getEnBarCode(al, money);
        var value = "";
        if (!$(".inputnum").is(':hidden')) {
            value = $("#inputnum").val();
        } else {
            if (type == "S") {
                value = $(".chengzhong_js .wvalue").val();
            } else {
                return false;
            }

        }

        $(".weigh").hide();
        $("body").removeClass("body_yc");

        if (posNum != null && posNum != "") {
            //说明是社区首页入口，需要增加社区购物车
            var sendNum = 0;
            var showNum = 1;
            var price = $(".chengzhong_js .pr").val();
            var goodsname = $(".chengzhong_js .goodsname").val();
            var sgrId = joinScanRecord(posNum, ppID, companyID, value, barCode, "默认规格", price, goodsname);
            if (sgrId != null && sgrId != "" && pcid != null && pcid != "") {
                supplementPrice(pcid, sgrId);
            }
            sqCartChange(ppID, "默认规格", value, "jia", barCode, sendNum, showNum, sgrId);
            $(".tonum").text(Number($(".tonum").text()) + 1);
        }
    });

    //输入数量计算合计
    $(".chengzhong_js .inputnum").on('input', function () {
        var price = $(".chengzhong_js .pr").val();

        $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));


    });

    //取消
    $(".cancel").click(function () {
        $(".weigh").hide();
        $("body").removeClass("body_yc");

    });

    //去结算
    $(".tojiesuan").click(function () {
        try {

            if (isAndroid == true) {
                Android.weighingCloseAndroid();
            } else {
                console.log("请在安卓设备访问！");
            }

        } catch (error) {
            console.log("刷脸设备中使用");
        }
        document.location.href = basePath + "/ea/sm/ea_qdlist.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID + "&back=2&lxType="+lxType;

    });

    //弹框提示
    $(".mm-alert div input").click(function () {
        //var ct = $(".mm-alert .ct").text();
        $(".mm-alert").hide();
    });

    //搜索
    document.getElementById('search_from').onsubmit = function (e) {
        var parameter = $.trim($("#search").val());
        $(".ul_list_sp").html("");
        productCate("", parameter);
        document.activeElement.blur();//软键盘收起

        return false;

    }

    //改价
    $(document).on("click", ".in-tj", function () {
        /*$(".txtNum").val("");
        $(".div-tiaojia").show();*/
        confirm();
    });


    /*$(document).on("click", ".close", function (event) {
        $(".txtNum").val("");
        $(".div-tiaojia").hide();
    });*/

    //关闭变价div
    $(document).on("click", ".close-2", function (event) {
        $(".chengzhong_js").show();
        $(".container").hide();
        //$(".container").html("");
    });


    //变价保存
    $(document).on("click", "#btnEnter", function () {
        var a = true;
        var je = $("#je").val();
        var dj = $("#xtdj").val();
        var sl = $("#sl").text();
        var sj = $("#dj").val();
        var cb = $("#cb").val();
        var yj = $("#yj").val();
        var dl = $("#dl").val();
        var pri = $("#pri").val();

        if(je==""||je==null||sj==""||sj==null||cb==""||cb==null){
            $(".mm-alert .ct").text("不可为空,必须为有效数字");
            $(".mm-alert").show();
            a = false;
            return false;
        }

        if (cb < 0 || je < 0 || dj < 0) {
            $(".mm-alert .ct").text("不可为负数,必须为有效数字");
            $(".mm-alert").show();
            a = false;
            return false;
        }

        if (dj == 0) {
            if (confirm("确定要把价格设置成0吗？")) {
                a = true;
            } else {
                a = false;
                return false;
            }
        }
        $(".mm-div-tiaojia .ct").html("调整单价为<h3 style='font-size:1rem;margin:2%'>"+sj+"</h3>元").css("margin-top","3%");
        $(".mm-alert h1").remove();
        $(".mm-alert").show();
        if (a) {
            var url = basePath + "ea/sm/sajax_ea_savePrice.jspa?";
            $.ajax({
                url: url,
                type: "get",
                async: false,
                dataType: "json",
                data: {
                    "dj": dj,
                    "cb": cb,
                    "yj": yj,
                    "dl": dl,
                    "pri": pri,
                    "userid":staffID,
                    "priceid": $(".box").find(".aaactivityID").text(),
                    "ppid": $(".box").find(".pd").text(),
                    "pritype": $(".box").find(".ppprit").text(),
                },
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var falg = member.falg;
                    if (falg) {
                        console.log("调价成功");
                        pcid = member.pcid;
                        $(".box").find(".ppprit").text(5);
                        $(".box").find(".aaactivityID").text(pcid);
                        $(".box").find(".pr").val(sj);
                        $(".box").find(".totalMoney").text(je);
                        $(".box").find(".cccostm").val(cb);
                        $(".close-2").click();
                    } else {
                        console.log("调价失败");
                    }
                },
                error: function cbf(data) {
                    console.log("失败");
                }
            });
        }
    });

    $(document).on("keyup", ".upinput", function () {
        upinput($(this));
    });

    $(document).on("click", "#dj2", function () {
        $(this).text("");
    });

    
    //分页
    $(".sec-con").scroll(function () {
        if ($(".last").length > 0) {
            if ($(".last").offset().top <= $(window)
                    .height()) {
                if (pageNumber < pageCount) {
                	var parameter = $.trim($("#search").val());
                	productCate(codeID, parameter);
                }
            }
        }
    });
});


//加载称重一级分类
function firstCate() {
    var ulp = basePath
        + "/ea/scale/sajax_ea_findScaleGoodsCate.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            companyID: companyID
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var catelist = member.catelist;
            var htmlstr = new Array();
            for (var i = 0; i < catelist.length; i++) {
                htmlstr.push("<li>");
                htmlstr.push(catelist[i][1]);
                htmlstr.push("<span class='codePID'style='display: none;'>" + catelist[i][0] + "</span>");
                htmlstr.push("</li>");

            }
            $(".fcate").append(htmlstr.join(""));
            var listWidth_1 = $(".nav-1 ul li").length;
            var listWidth = 0;
            for (var i = 0; i < listWidth_1; i++) {
                listWidth += $(".nav-1 ul").children("li").eq(i).outerWidth(true);
            }
            $(".nav-1 ul").width(listWidth + 6);
        },
        error: function (data) {
            console.log("加载一级分类失败");
        }
    });

}

//加载称重二级分类
function secondCate(codePID) {
    var ulp = basePath
        + "/ea/scale/sajax_ea_findSecondCate.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyID: companyID,
            codePID: codePID
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var twolist = member.twolist;
            var htmlstr = new Array();
            if (twolist != null && twolist.length > 0) {
                $(".nav-2").show();
            } else {
                $(".nav-2").hide();
            }
            for (var i = 0; i < twolist.length; i++) {
                if (i == 0) {
                    htmlstr.push("<li class='active'>");
                } else {
                    htmlstr.push("<li>");
                }
                htmlstr.push(twolist[i][1]);
                htmlstr.push("<span class='codeID'style='display: none;'>" + twolist[i][0] + "</span>");
                htmlstr.push("</li>");

            }
            $(".scate").html(htmlstr.join(""));
            $(".scate .active").click();
            var listWidth_1 = $(".nav-2 ul li").length;
            var listWidth = 0;
            for (var i = 0; i < listWidth_1; i++) {
                listWidth += $(".nav-2 ul").children("li").eq(i).outerWidth(true);
            }
            $(".nav-2 ul").width(listWidth + 6);
        },
        error: function (data) {
            console.log("加载二级分类失败");
        }
    });

}
//加载称重商品
function productCate(codeID, parameter) {
    if (pageNumber == 0) {
        $(".ul_list_sp").html("");
    }
    pageNumber += 1;
    var ulp = basePath
        + "/ea/scale/sajax_ea_findProductByCate.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyID: companyID,
            codeID: codeID,
            parameter: parameter,
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize": pageSize,
            lxType:lxType
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var htmlstr = new Array();
            $(".last").removeClass("last");

            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var list = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < list.length; i++) {
                    if (i == list.length - 1) {
                        htmlstr.push("<li data-name='" + list[i][0] + "' class='last'>");
                    } else {
                        htmlstr.push("<li data-name='" + list[i][0] + "'>");
                    }

                    htmlstr.push("<p>");

                    if (list[i][3] == null || list[i][3] == "") {
                        htmlstr.push("<img  alt='' src='" + basePath + "images/ea/production/forum/default.jpg'/>");
                    } else {
                        htmlstr.push("<img  alt='' src='" + basePath + list[i][3] + "'  onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/default.jpg\'\" />");
                    }


                    htmlstr.push("</p>");
                    htmlstr.push("<p>");
                    htmlstr.push(list[i][1]);
                    htmlstr.push("</p>");
                    htmlstr.push("<span class='gp' style='display: none;'>" + list[i][1] + "</span>");
                    htmlstr.push("<span class='pr'style='display: none;'>" + list[i][4] + "</span>");
                    htmlstr.push("<span class='prc'style='display: none;'>" + list[i][6] + "</span>");
                    htmlstr.push("<span class='plu'style='display: none;'>" + list[i][7] + "</span>");
                    htmlstr.push("<span class='al'style='display: none;'>" + list[i][8] + "</span>");
                    htmlstr.push("<span class='ppd'style='display: none;'>" + list[i][0] + "</span>");
                    htmlstr.push("<span class='prit'style='display: none;'>" + list[i][9] + "</span>");
                    htmlstr.push("<span class='costm'style='display: none;'>" + list[i][10] + "</span>");
                    htmlstr.push("<span class='activityIDm'style='display: none;'>" + list[i][11] + "</span>");
                    htmlstr.push("</li>");

                }
            }
            $(".ul_list_sp").append(htmlstr.join(""));



        },
        error: function (data) {
            console.log("加载商品");
        }
    });

}
//初始化电子秤
function initWeightDevice() {
	try{
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    if (isAndroid == true) {
        Android.weighingInitAndroid();
    } else {
        console.log("请在安卓设备访问！");
    }
	}catch(error){}
}

//读取称重数量
function getWeight(data) {
    var member = eval("(" + data + ")");
    type = member.type;
    var weight = member.weight;
   // $(".mm-alert .ct").text(member.weight);
    //var ct = $(".mm-alert .ct").text();
  //  $(".mm-alert").show();
    $(".weight .wvalue").val(weight);
    var price = $(".chengzhong_js .pr").val();
    if (!$(".weight").is(':hidden')) {
        $(".totalMoney").text((Number(price) * Number(weight)).toFixed(2));
    }


}

//社区加减购物车
function sqCartChange(ppid, stardard, amount, opr, barCode, sendNum, showNum, sgrId) {

    var url = basePath + "ea/smg/sajax_sm_addShoppingCart.jspa";
    if (opr == "jian") {
        url = basePath + "ea/smg/sajax_sm_reduceShoppingCart.jspa";

    }
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            ppid: ppid,
            stardard: stardard,
            totalNum: amount,
            posNum: posNum,
            barCode: barCode,
            sendNum: sendNum,
            showNum: showNum,
            sgrId: sgrId,
            relateID: relateID
        },
        success: function (data) {


        },
        error: function (data) {
            //商品加入购物车失败
            alert("数据处理失败!");
            return;
        }
    });
}

//补齐0
function pad2(num, n) {
    if ((num + "").length >= n) return num;
    return pad2("0" + num, n);
}
//计算生成条码
function getEnBarCode(al, money) {
    var C = "21" + al + pad2(Number(money) * 100, 5);
    var C1 = 0;
    var C2 = 0;
    var V = 0;
    for (var i = 1; i < C.length + 1; i++) {
        if (i % 2 == 1) {
            C1 += Number(C[i - 1]);
        } else {
            C2 += Number(C[i - 1]);
        }
    }
    var G1 = (Number(C1) + Number(C2) * 3).toString();
    var V = 10 - Number(G1.substring(G1.length - 1));

    return C + V + "";

}

//第一次扫描
function joinScanRecord(posNum, ppID, companyID, num, barCode, stardard, price, goodsname) {
    var sgrId = "";
    var url = basePath + "ea/sm/sajax_ea_joinScanRecord.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            posNum: posNum,
            "scanGoods.posNum": posNum,
            "scanGoods.pid": ppID,
            "scanGoods.companyId": companyID,
            "scanGoods.itemNum": num,
            "scanGoods.barCode": barCode,
            "scanGoods.stardard": stardard,
            "scanGoods.price": price,
            "scanGoods.goodsName": goodsname,
            "scanGoods.relateID": relateID,

        },
        success: function (data) {
            var me = eval("(" + data + ")");
            sgrId = me.sgrId;

        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });
    return sgrId;

}

/*键盘*/
/*数字1-9*/
function btnNum_onclick(i) {
    var values = $('#dj2').text();
    $('#dj2').text(values + i);
    $('#dj').val(values + i);
    upinput($('#dj'));
}
/*点*/
function dian() {
    var values = $('#dj2').text();
    var dian = ".";
    $('#dj2').text(values + dian);
}
/*清空*/
function clearText() {
    $('#dj2').text("");
    $('#dj').val("");
    upinput($('#dj'));
}
/*删除*/
function delText() {
    var value = $('#dj2').text();
    var str = value.substring(0, value.length - 1);
    $('#dj2').text(str);
    $('#dj').val(str);
    upinput($('#dj'));
}
/*确定*/
function confirm() {
    /*if (user == null || user == "") {
        $(".mm-alert .ct").text("登录失效，请重新登录！");
        $(".mm-alert").show();
        return;
    }*/
   /* if (companyid != companyID) {
        $(".mm-alert .ct").text("暂无权限");
        $(".mm-alert").show();
        return;
    }*/
    /*var pw = $(".txtNum").val();
    if (pw == "") {
        $(".mm-alert .ct").text("请输入密码");
        $(".mm-alert").show();
        return;
    } else if (pw.length != 6) {
        $(".mm-alert .ct").text("密码为6位");
        $(".mm-alert").show();
        $(".txtNum").val("");
        return;
    }

    $.ajax({
        type: "GET",
        url: basePath + "/ea/sm/sajax_ea_verification.jspa",
        async: false,
        dataType: "json",
        data: {
            "pw": pw,
            "staffID":staffID,
            "comID":companyID,
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var result = member.result;

            if(result!="2") {
                var ct = "";
                if (result == "1") {
                    ct = "操作员账户没有绑定微分金账户";

                }else if (result == "3") {
                    ct = "密码错误";
                }
                $(".txtNum").val("");
                $(".mm-alert .ct").text(ct);
                $(".mm-alert").show();
            }else{
                if(companyID==""||companyID==null){
                    companyID = member.comID;
                }
                if(staffID==""||staffID==null){
                    staffID =  member.staffID;
                }

            }
        },
        error: function (data) {
            console.log("查询支付结果失败");
        }
    });*/

    var inum = 0;
    if ($(".box").find(".prcc").text() == "KGM") {
        inum = $(".box").find(".wvalue").val();
    } else {
        inum = $(".box").find("#inputnum").val();
    }
    if(inum<=0){
        $(".mm-alert .ct").text("请确认“数量/重量”为正确数值！");
        $(".mm-alert").show();
        return;
    }
    var params =
        {
            'comID': companyID,
            'ppid': $(".box").find(".pd").text(),
            'inum': (inum == null || inum == "") ? 0 : inum,
            'price': $(".box").find(".pr").val(),
            'tprice': $(".box").find(".totalMoney").text(),
            'costmoney': $(".box").find(".cccostm").text(),
            'pritype': $(".box").find(".ppprit").text(),
            'activityID': $(".box").find(".aaactivityID").text()
        };
    $.ajax({
        url: basePath + "/ea/sm/sajax_ea_getProudct.jspa?&date=" + new Date().toLocaleString(),
        type: "get",
        async: false,
        dataType: "json",
        data: params,
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var prolist = member.prolist;
            if (prolist.length > 0) {
                $(".chengzhong_js").hide();
                var pro = prolist[0];
                var a1 = (pro[9] == null || pro[9] == "") ? 0 : pro[9];
                var a2 = (pro[10] == null || pro[10] == "") ? 0 : pro[10];
                var a3 = (pro[11] == null || pro[11] == "") ? 0 : pro[11];
                var a4 = (pro[4] == null) ? "" : pro[4];
                var a5 = (pro[5] == null) ? "" : pro[5];
                var a6 = (pro[6] == null) ? "" : pro[6];
                /*var html = new Array();
                 html.push("<section><ul><a href='#' class='close-2'></a>");
                 html.push("<li class='clearfix'><div class='div-img'><img src='" + basePath + pro[1] + "'/></div>");
                 html.push("<p><input type='text' class='input-bold' disabled value='" + pro[2] + "'/></p></li>");
                 /!*html.push("<li class='clearfix'><p>计价单位</p><div><p>" + pro[3] + "</p></div></li>");
                 html.push("<li class='clearfix'><p>产品条码</p><div><p>" + pro[3] + "</p></div></li>");*!/
                 html.push("<li class='clearfix'><p>产品分类</p><div><p>" + a4 + "</p></div></li>");
                 html.push("<li class='clearfix'><p>产品规格</p><div><p>" + a5 + "</p></div></li>");
                 html.push("<li class='clearfix'><p>产品品牌</p><div><p>" + a6 + "</p></div></li>");
                 html.push("<li class='clearfix'><p>总金额</p><p><input type='number' id='je' class='upinput' value='" + params.tprice + "'/>元</p></li>");
                 html.push("<li class='clearfix'><p>数量/重量</p><p><span id='sl'>" + params.inum + "</span></p></li>");
                 html.push("<li class='clearfix'><p>售价</p><p><input type='number' id='dj' class='upinput' value='" + params.price + "'/>元</p></li>");
                 html.push("<li class='clearfix'><p>成本价</p><p><input type='number' id='cb' class='upinput' value='" + pro[8] + "'/>元</p>");
                 html.push("<input type='hidden' id='priceid' name='priceid' value='" + pro[12] + "'/>");
                 html.push("<input type='hidden' id='jlid' name='jlid' value='" + params.trid + "'/>");
                 html.push("<input type='hidden' id='ppid' name='ppid' value='" + pro[0] + "'/>");
                 html.push("<input type='hidden' id='sj' value='" + pro[7] + " '/>");//售价
                 html.push("<input type='hidden' id='yj' value='" + a1 + "'/>");//业务佣金
                 html.push("<input type='hidden' id='dl' value='" + a2 + "'/>");//代理佣金
                 html.push("<input type='hidden' id='pri' value='" + a3 + "'/>");//消费红包
                 html.push("<input type='hidden' id='xtdj' value='" + pro[7] + " '/><input type='hidden'  id='sgrid'/>");//系统单价
                 html.push("</ul><div class='footer'><input type='button' name='' id='save' value='确定'/></div></section>");
                 $(".txtNum").val("");
                 $(".close").click();
                 $(".container").html(html.join(""));*/
                /*select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,ps.re_price sj,");
                 result.append("ps.ef_price cb,ps.brokerage yj,ps.proxy_sum_price dl,1+nvl(sz.total_pct,0)/100 pri,ps.suid");
                 result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                 result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                 result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                 result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                 result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                 result.append(" where ps.com_id = ? and ps.suid = ?*/
                $(".div-img img").attr("atr",basePath + pro[1]);
                $(".input-bold").val(pro[2]);
                $("#type p").text(a4);
                $("#standard p").text(a5);
                $("#brand p").text(a6);
                $("#je").val(params.tprice);
                /*$("#dj2").text(params.tprice);*/
                $(".je").text(params.tprice+"元");
                $("#sl").text(params.inum);
                $("#dj").val(params.price);
                $(".dj").text(params.price+"元");
                $("#cb").val(pro[8]);
                $("#priceid").val(pro[12]);
                $("#jlid").val(params.trid);
                $("#ppid").val(pro[0]);
                $("#sj").val(pro[7]);
                $("#yj").val(a1);
                $("#dl").val(a2);
                $("#pri").val(a3);
                $("#xtdj").val(pro[7]);
                $(".container").show();
                token == 0;
            }
        }
    });

}
//变价计算
function upinput(event) {
    var je = "";
    var dj = "";
    var cb = "";
    var sl = parseFloat($("#sl").text());
    //alert("sl:"+sl);
    var sj = parseFloat($("#sj").val());
    //alert("sj:"+sj);
    var cb = parseFloat($("#cb").val());
    //alert("cb:"+cb);
    var yj = parseFloat($("#yj").val());
    //alert("yj:"+yj);
    var dl = parseFloat(($("#dl").val() == null || $("#dl").val()) == "" ? 0 : $("#dl").val());
    //alert("dl:"+dl);
    var pri = parseFloat($("#pri").val());
    //alert("pri:"+pri);
    //alert("id:"+event.attr("id"));
    if (event.attr("id") == "je") {
        je = $.getFloat(parseFloat(event.val()), 4);
        //alert("je:"+je);
        dj = $.getFloat(je / sl, 4);
        //alert("dj:"+dj);
        cb = $.getFloat($.getFloat(dj / pri, 4) - yj - dl, 4);
        //alert("cb:"+cb);
        $("#dj").val(dj);
        $("#cb").val(cb);
    } else if (event.attr("id") == "dj") {
        dj = $.getFloat(parseFloat(event.val()), 4);
        //alert("dj:"+dj);
        je = $.getFloat(dj * sl, 4);
        //alert("je:"+je);
        cb = $.getFloat($.getFloat(dj / pri, 4) - yj - dl, 4);
        //alert("cb:"+cb);
        $("#je").val(je);
        $("#cb").val(cb);
    } else if (event.attr("id") == "cb") {
        cb = $.getFloat(parseFloat(event.val()), 4);
        //alert("cb:"+cb);
        dj = $.getFloat((cb + yj + dl) * pri, 4);
        //alert("dj:"+dj);
        je = $.getFloat(dj * sl, 4);
        //alert("je:"+je);
        $("#je").val(je);
        $("#dj").val(dj);
    }
    //dj = $.getFloat((cb + yj + dl) * pri, 4);
    //alert("dj:"+(cb + yj + dl));
    //je = $.getFloat(dj * sl, 4);
    //alert("je:"+je);
    $("#xtdj").val($.getFloat((cb + yj + dl), 4));
    //alert("xtdj:"+$("#xtdj").val());
    /*$("#je").val(je);
     $("#dj").val(dj);
     $("#cb").val(cb);*/
}

//补充变价数据
function supplementPrice(pcid, sgrId) {
    var url = basePath + "ea/sm/sajax_ea_supplementPrice.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            "pcid": pcid,
            "sgrId": sgrId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var falg = member.falg;
            if (falg) {
                console.log("补充变价数据成功");
            }
        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });
}