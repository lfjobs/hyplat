var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') > 1; //window
$(function () {

    //$("#companyid").val(comid);
    //缓存单据数据加载
    localforage.getItem('billform').then(function (value) {
        //当离线仓库中的值被载入时，此处代码运行
        console.log(value);
        if (value != null && value != "") {
            $(value).each(function () {
                $("#" + this.name).val(this.value);
                /*$("#" + this.name).text(this.value);*/
            });
        } else {
            //获取订单号
            $.ajax({
                url: basePath + "ea/initialize/sajax_ea_getStaff.jspa",
                type: "get",
                async: false,
                dataType: "json",
                data: {
                    "compayid": comid,
                    "staffid": staffid
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var jum = member.jum;
                    var staffname = member.staffname;
                    $("#jum").val(jum);
                    $("#inistaffname").val(staffname);
                    $("#inistaffid").val(staffid);
                    $("#inidate").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
                }
            });
        }
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });
    //缓存产品数据加载
    localforage.getItem('serializeJson').then(function (value) {
        //当离线仓库中的值被载入时，此处代码运行
        if (value != null && value != "") {
            $(".ul_con3").attr("style", "display:none");
            $(".sec_con2").empty();
            var strHtml = [];
            strHtml.push("<table><tr><td>名称及规格</td><td>发货数</td><td>收货数</td>");
            strHtml.push("<td>验货数</td><td>入库数</td></tr>");
            serializeJson = value;
            var num = 0;
            $.each(value, function (index, item) {
                strHtml.push("<tr id='" + item.ppID + "-" + index + "' class='tr-zl'>");
                strHtml.push("<input type='hidden' name='ppid' value='" + item.ppID + "'/>");
                strHtml.push("<td>" + item.goodsName + "</td>");
                strHtml.push("<td>" + item.fhnum + "</td>");
                strHtml.push("<td class='td-dds'>" + item.shnum + "</td>");
                strHtml.push("<td class='num'>" + item.yhnum + "</td>");
                strHtml.push("<td class='num'>" + item.rknum + "</td></tr>");
                num += 1;
                if ($("#warehouse").val() == null || $("#warehouse").val().trim() == "") {
                    $("#warehouse").val(item.kfid);
                    $("#warehousename").val(item.kfname);
                }
            });
            strHtml.push("</table>");
            strHtml.push("<div class='clearfix'><div class='left'>");
            strHtml.push("<p>共" + num + "种商品</p>");
            strHtml.push("</div><div class='right'><p class='smli'>扫码</p><p  class='czli'>无码称重</p><p id='add'>保存</p>");
            strHtml.push("</div></div>");
            $(".sec_con2").append(strHtml.join(""));
        }
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });

    if ($(".pid").length <= 0) {
        //缓存数据加载
        localforage.getItem('hiddenform').then(function (value) {
            //当离线仓库中的值被载入时，此处代码运行
            console.log(value);
            if (value != null && value != "") {
                $("#jn").val(value.jn);
                $("#caid").val(value.caid);
                $("#trid").val(value.trid);
                var strHtml = [];
                $.each(value.goods, function (index, item) {
                    strHtml.push("<div id='" + item.pid + "' class='hiddendiv' style='display: none'>");
                    strHtml.push("<input type='hidden' class='pid' name='pid' value='" + item.pid + "'/>");
                    strHtml.push("<input type='hidden' class='gid' name='gid' value='" + item.gid + "'/>");
                    strHtml.push("<input type='hidden' class='gname' name='gname' value='" + item.gname + "'/>");
                    strHtml.push("<input type='hidden' class='gborcode' name='gborcode' value=''/>");
                    strHtml.push("<input type='hidden' class='gqnum' name='gqnum' value='" + item.gqnum + "'/>");
                    strHtml.push("</div>");
                });
                $("#hiddenform").append(strHtml.join(""));
            }
        }).catch(function (err) {
            // 当出错时，此处代码运行
            console.log(err);
        });
    }

    $("#div_table").height(($(".div-name").height() - $(".header").outerHeight(true) - $(".button").outerHeight(true)) * 0.835 +
        "px");

    //无码称重按键点击
    $(document).on("click", ".czli", function () {
        addForm();
        hiddenform();
        gcid = $("#gcid").val();
        if ($("#gcid").val() == "" || $("#gcid").val() == null) {
            gcid = $("#companyid").val();
        }
        window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + gcid + "&staffid=" +
            staffid + "&sccid=" + sccid + "&showButton=true&sort=1";

    });


    //供货商数据加载
    $(".gys").click(function () {
        $.ajax({
            type: "get",
            url: basePath + "ea/ruku/sajax_ea_getCom.jspa",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var objvalue = member.objvalue;
                var s = [];
                var d = [];
                for (var i = 0; i < objvalue.length; i++) {
                    var obj = objvalue[i];
                    s[i] = obj[1];
                    d[i] = obj[0];
                }
                depSelectSwiperCom(s, d);
            }
        });
    });

    //入库责任人数据加载
    $(".cgy").click(function () {
        $.ajax({
            type: "get",
            url: basePath + "ea/ruku/sajax_ea_getStaff.jspa",
            async: false,
            dataType: "json",
            data: {
                'comid': comid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var objvalue = member.objvalue;
                var s = [];
                var d = [];
                for (var i = 0; i < objvalue.length; i++) {
                    var obj = objvalue[i];
                    s[i] = obj[1];
                    d[i] = obj[0];
                }
                depSelectSwiperStaff(s, d);
            }
        });
    });

    //扫码按键点击
    $(document).on("click", ".smli", function () {
        addForm();
        if (isAndroid == true) {
            Android.callcamera();
        } else if (isiOS == true) {
            callIOScamera();
        }
        //ajaxGetGoodsHtml("6921168509256");
    });

    //保存按键点击
    $(document).on("click", "#add", function () {

        var serializeObj = $("#billform").parseForm();

        localforage.getItem('serializeJson').then(function (value) {
            //当离线仓库中的值被载入时，此处代码运行
            if (value != null && value != "") {
                $.ajax({
                    url: basePath + "ea/ruku/sajax_ea_ajaxSaveRuku.jspa",
                    type: "post",
                    async: true,
                    dataType: "json",
                    data: {
                        "billform": JSON.stringify(serializeObj),
                        "serializeJson": JSON.stringify(value)
                    },
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var falg = member.falg;
                        if (falg == "完成") {
                            localforage.clear();
                            window.location.href = basePath + "/page/ea/main/finance/invoicing/rukuBill.jsp?compayid=" +
                                $("#companyid").val() + "&staffid=" + staffid;
                        } else {
                            alert("保存失败");
                        }
                    }
                });
            }
        });
    });

    $(".kf").click(function () {
        $("#ckiframe").attr("src", "/ea/initialize/ea_getListDepotmanageByPID.jspa?depotID=001&sort=2&compayid=" + comid);
        $(".div-kc").show();
    });

    $("#span-close").click(function () {
        $(".div-kc").hide();
    });
});

/**
 * 时间控件加载
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o
            [k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 扩展jquery的格式化方法
 * @returns {{}}
 */
$.fn.parseForm = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    var str = this.serialize();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};

/**
 * 责任人下拉框加载
 * @param s
 * @param d
 */
function depSelectSwiperStaff(s, d) {
    //模拟下拉框
    var hgS1 = new selectSwiper({
        el: '.select_box1',
        data: s,
        init: function (index) {
            if (index !== -1) {
                $('.btn1').html(this.data[index]);
            }
        },
        okFunUndefind: function () {
            alert('必须选择一项');
            return false;
        },
        okFun: function (index) {
            console.log(index);
            $('#cgstaffname').val(this.data[index]);
            $('#cgstaffid').val(d[index]);
            $("body").removeClass("body_yc");
        },
        closeFun: function () {
            console.log('取消');
            $("body").removeClass("body_yc");
        },
    });
    hgS1.openSelectSwiper();
    $("body").addClass("body_yc");
}

/**
 * 供货商下拉框加载
 * @param s
 * @param d
 */
function depSelectSwiperCom(s, d) {
    //模拟下拉框
    var hgS1 = new selectSwiper({
        el: '.select_box1',
        data: s,
        init: function (index) {
            if (index !== -1) {
                $('.btn1').html(this.data[index]);
            }
        },
        okFunUndefind: function () {
            alert('必须选择一项');
            return false;
        },
        okFun: function (index) {
            console.log(index);
            $('#gcname').val(this.data[index]);
            $('#gcid').val(d[index]);
            $("body").removeClass("body_yc");
        },
        closeFun: function () {
            console.log('取消');
            $("body").removeClass("body_yc");
        },
    });
    hgS1.openSelectSwiper();
    $("body").addClass("body_yc");
}

/**
 * 接收安卓端扫码返回值
 * @param tiaoma 条码
 */
function calltiaoma(tiaoma) {
    //条形码信息
    var member = eval("(" + tiaoma + ")");
    //条形码
    var barcode = member.code;
    addForm();
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
    addForm();
    ajaxGetGoodsHtml(barcode);
}

/**
 * 异步根据隐藏域中的值获取盘库商品信息
 */
function ajaxGetGoodsHtml(barcode) {
    window.location.href = basePath + "ea/ruku/ea_getProductByComid.jspa?barcode=" + barcode + "&companyid=" + comid +
        "&staffid=" + staffid + "&sccid=" + sccid;
}

/**
 * 单据数据缓存
 */
function addForm() {
    $("#companyid").val(comid);
    localforage.setItem('billform', $("#billform").serializeArray()).then(function (value) {
        // 当值被存储后，可执行其他操作
        console.log(value);
    }).catch(function (err) {    // 当出错时，此处代码运行
        console.log(err);
    });
}

/**
 * 产品数据缓存
 */
function hiddenform() {
    var serializeJson = {};
    var goods = [];

    $(".hiddendiv").each(function () {
        var serializeObj = {};
        serializeObj.pid = $(this).find(".pid").val();
        serializeObj.gid = $(this).find(".gid").val();
        serializeObj.gname = $(this).find(".gname").val();
        serializeObj.gborcode = $(this).find(".gborcode").val();
        serializeObj.gqnum = $(this).find(".gqnum").val();
        goods.push(serializeObj);
    });
    serializeJson = {
        "caid": $("#hiddenform").find("#caid").val(),
        "trid": $("#hiddenform").find("#trid").val(),
        "jnum": $("#hiddenform").find("#jnum").val(),
        "jn": $("#hiddenform").find("#jn").val(),
        "goods": goods
    };

    localforage.setItem('hiddenform', serializeJson).then(function (value) {
        // 当值被存储后，可执行其他操作
        console.log(value);
    }).catch(function (err) {    // 当出错时，此处代码运行
        console.log(err);
    });
}

/**
 * 返回
 * @returns {boolean}
 */
function toBack() {
    localforage.clear().then(function () {
        // 当数据库被全部删除后，此处代码运行
        console.log('Database is now empty.');
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });
    try {
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'callIOSjianli';
            window.webkit.messageHandlers.Native.postMessage(url);
        } else if (iswin) {
            console.log("win");
            window.history.go(-1);
            return false;
        } else {
            window.history.go(-1);
            return false;
        }
    } catch (error) {
        window.history.go(-1);
        return false;
    }
}

/**
 * 打印
 */
function toPrint() {
    try {
        var andJson={};
        $(".rgid").each(function (){
            var goodJson={
                '商品':$(this).find(".goodname").text(),
                '发货数':$(this).find(".quantity2").text(),
                '入库数':$(this).find(".quantity2").text()
            }

            andJson={"产品":goodJson};
            console.log(andJson);
        });
        andJson={
            "订单号":$("#jum").val(),
            "入库人":$("#cgstaffname").val(),
            "仓库":$("#warehousename").val(),
            '单据状态':'入库',
            '制单人':$("#inistaffname").val(),
            '制单时间':$("#inidate").val(),
            '供货商':$("#gcname").val(),
            '二维码':window.location.href
        }
        console.log(andJson);
        if (isAndroid == true) {

        } else if (isiOS == true) {
            alert("研发中，请用windows电脑打印");
        } else if(iswin) {
            var lineHeight = 0;
            var fontSize=11//单个字 11
            LODOP = getLodop();
            LODOP.PRINT_INIT("入库账单详情");
            LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
            LODOP.SET_PRINT_STYLE("FontName", "黑体");
            /*LODOP.ADD_PRINT_SETUP_BKIMG(0,0,"500mm","500mm","<img src='"+basePath+"images/ea/admin/水印.jpg'>");
            LODOP.SET_PRINT_STYLEA(0,"Repeat",true);*/
            LODOP.SET_PRINT_STYLE("FontSize", 9);
            LODOP.SET_PRINT_STYLE("Bold", 0);
            LODOP.SET_PRINT_STYLE("Alignment", 2);
            lineHeight += 30;//14char
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "打造全球互联网平台");
            lineHeight += 13;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "生成供应商第一品牌");
            lineHeight += 13;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "联营整个社会");
            lineHeight += 13;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "为人类再造一个地球");
            lineHeight += 13;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "欢迎使用数字地球平台");
            lineHeight += 20;
            LODOP.ADD_PRINT_LINE(lineHeight, 5, lineHeight, 175,2,0);
            /*lineHeight += 30;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "入库单");*/
            LODOP.SET_PRINT_STYLE("Alignment", 1);
            lineHeight += 20;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "入库单号：");
            /*LODOP.ADD_PRINT_BARCODE(lineHeight, 5+fontSize*5, 100, 30,"128A",$("#jum").val());*/
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*5, 180, 10, $("#jum").val());
            lineHeight += 40;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "入库人：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*4, 180, 10, $("#cgstaffname").val());

            textWidth=$("#cgstaffname").val().length*fontSize;
            LODOP.ADD_PRINT_TEXT(lineHeight, 45+textWidth+20, 180, 10, "仓库：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 45+textWidth+20+fontSize*3, 180, 10, $("#warehousename").val());
            LODOP.SET_PRINT_STYLE("Alignment", 2);
            lineHeight += 20;
            LODOP.ADD_PRINT_LINE(lineHeight, 5, lineHeight, 175,2,0);
            lineHeight += 15;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 20, "商品     发货数    入库数");
            lineHeight += 5;
            $(".rgid").each(function (){
                LODOP.SET_PRINT_STYLE("Alignment", 1);
                textWidth=0;
                lineHeight += 15;
                LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 20, $(this).find(".goodname").text());
                if($(this).find(".goodname").text().length*fontSize>=50){
                    lineHeight += 15;
                }
                LODOP.ADD_PRINT_TEXT(lineHeight, 70, 180, 20, $(this).find(".quantity2").text());
                LODOP.ADD_PRINT_TEXT(lineHeight, 130, 180, 20, $(this).find(".quantity2").text());
            });
            lineHeight += 30;
            LODOP.ADD_PRINT_LINE(lineHeight, 5, lineHeight, 175,2,0);
            LODOP.SET_PRINT_STYLE("Alignment", 1);

            lineHeight += 20;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "单据状态：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*5, 150, 20, "入库");
            lineHeight += 20;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "制单人：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*4, 180, 10, $("#inistaffname").val());
            lineHeight += 20;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "制单时间：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*5, 180, 10, $("#inidate").val());
            lineHeight += 20;
            LODOP.ADD_PRINT_TEXT(lineHeight, 5, 180, 10, "供货商：");
            LODOP.ADD_PRINT_TEXT(lineHeight, 5+fontSize*4, 130, 20, $("#gcname").val());
            lineHeight += 80;
            LODOP.ADD_PRINT_BARCODE(lineHeight, 45, 100, 100,"QRCode",window.location.href);
            LODOP.SET_PRINT_STYLE("Alignment", 2);
            lineHeight += 140;
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 10, "扫一扫，查看电子单");
            lineHeight += 300;
            //LODOP.ADD_PRINT_LINE(lineHeight, 5, lineHeight, 175,2,0);
            LODOP.ADD_PRINT_TEXT(lineHeight, "", 180, 20, "----------------------------");
            /*LODOP.SET_PRINT_STYLEA(1, "Bold", 1);
            LODOP.SET_PRINT_STYLEA(2, "Bold", 5);
            LODOP.SET_PRINT_STYLEA(4, "Bold", 1);
            LODOP.SET_PRINT_STYLEA(1, "FontSize", 14);
            LODOP.SET_PRINT_STYLEA(1, "FontName", "微软雅黑");
            LODOP.SET_PRINT_STYLEA(10, "HOrient", "微软雅黑");*/
            //LODOP.PREVIEW();
            /*if (LODOP.CVERSION) {
                LODOP.On_Return = function (TaskID, Value) {
                    if (Value >= 0) LODOP.PRINT(); else alert("选择失败！");
                }
                LODOP.SELECT_PRINTER();
                LODOP.PRINTB();

            }*/
            LODOP.PREVIEW();
        }else {
            alert("研发中，请用电脑打印");
        }
    } catch (e) {
        alert(e);
    }
}