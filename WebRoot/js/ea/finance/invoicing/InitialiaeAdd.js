var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var serializeJson;
var ulid;
$(function () {
    if (dpFlag) {
        if ($(".wmcz").is(':hidden')) {
            $("#displayIndex").focus();
        }
    }

    $(".div-box ul").not(".ulnot").each(function () {
        console.log($(this).find("section").height())
        console.log($(this).find("li").height())
        if ($(this).find("section").height() != $(this).find("li").height()) {
            $(this).find("section").addClass("yc");
            $(this).find("li").eq(0).addClass("li-text");
            $(this).find("li").eq(0).append("<p class='p-yc'><img src='/images/ea/finance/invoicing/img_03.png'/></p>");
        }
    });

    $(document).on("click", ".li-text", function () {
        if ($(this).find("section").is(".yc")) {
            $(".li-text").find("section").addClass("yc");
            $(".li-text").find("section").removeClass("zi");
            $(".li-text").find(".p-yc").find("img").attr("src", "/images/ea/finance/invoicing/img_03.png");


            $(this).find("section").removeClass("yc");
            $(this).find("section").addClass("zi");
            $(this).find(".p-yc").find("img").attr("src", "/images/ea/finance/invoicing/img_10.png");
        } else {
            $(this).find("section").addClass("yc");
            $(this).find("section").removeClass("zi");
            $(this).find(".p-yc").find("img").attr("src", "/images/ea/finance/invoicing/img_03.png");
        }
    });
    //启用点击
    $(document).on("click", ".table-s tr td:last-of-type", function () {
        $(this).parents("tr").addClass("active");
        ulid = $(this).parents("tr").attr("id");
        $(".div-zx").show();
        $(".div-zx").find("h4").text($(this).parent().find("td").eq(0).text());
    });
    //启用选择
    $(".div-zx ul li").click(function () {
        $(".div-zx ul li").find("img").attr("src", "/images/ea/finance/invoicing/xuan_03.png");
        $(".div-zx ul li").find("p").removeClass("active");
        $(this).find("img").attr("src", "/images/ea/finance/invoicing/xuan_06.png");
        $(this).find("p").addClass("active");
    });
    //启用提交
    $(".div-tj").click(function () {
        var ptext = $(".div-zx .active").parents("li").children("p").text();
        if (serializeJson != null) {
            $.each(serializeJson, function (index, item) {
                var ppid = ulid.substr(0, ulid.indexOf("-"));
                var i = ulid.substr(ulid.indexOf("-") + 1);
                if (index == i && ppid == item.ppid) {
                    item.fbname = ptext;
                    if (ptext == "启用") {
                        item.fbtype = "01";
                    } else {
                        item.fbtype = "00";
                    }
                }
            });
        }
        localforage.setItem('serializeJson', serializeJson).then(function (value) {
            console.log(value);
        }).catch(function (err) {
            // 当出错时，此处代码运行
            console.log(err);
        });

        $(".table-s tr.active td:last-of-type").find("span").text(ptext);
        if (ptext == "未启用") {
            $(".table-s tr.active td:last-of-type").addClass("active");
        } else {
            $(".table-s tr.active td:last-of-type").removeClass("active");
        }
        $(".table-s tr").removeClass("active");
        $(".div-zx").hide();
    });

    /*if(cache=="1"){
     localforage.clear().then(function() {
     console.log('Database is now empty.');
     }).catch(function(err) {
     // This code runs if there were any errors
     console.log(err);
     });
     }*/

    //缓存数据加载
    localforage.getItem('formjum').then(function (value) {
        //当离线仓库中的值被载入时，此处代码运行
        console.log(value);
        if (value != null && value != "") {
            $(".div-box").empty();
            $(value).each(function () {
                $("." + this.name).val(this.value);
                $("#" + this.name).text(this.value);
            });
            var strHtml = new Array();
            strHtml.push("<table class='table-s' style='BORDER-COLLAPSE: collapse' cellPadding=0 align=center border=0>");
            strHtml.push("<tr><td>名称及规格</td><td>库仓</td>");
            strHtml.push("<td>初始库存</td><td>是否发布在线</td></tr>");
            localforage.getItem('serializeJson').then(function (value) {
                //当离线仓库中的值被载入时，此处代码运行
                console.log(value);
                if (value != null && value != "") {
                    serializeJson = value;
                    $.each(value, function (index, item) {
                        var ac;
                        if (item.fbtype == "00") {
                            ac = "active";
                        }
                        strHtml.push("<tr id='" + item.ppid + "-" + index + "'><input type='hidden' name='ppid' value='" + item.ppid + "'/>");
                        /*strHtml.push("<input type='hidden' name='goodsname' value='" + item.goodsname + "'/>");
                         strHtml.push("<input type='hidden' name='cskc' value='" + item.cskc + "'/>");
                         strHtml.push("<input type='hidden' name='fbtype' value='" + item.fbtype + "'/>");
                         strHtml.push("<input type='hidden' name='fbname' value='" + item.fbname + "'/>");
                         strHtml.push("<input type='hidden' name='kfname' value='" + item.kfname + "'/>");
                         strHtml.push("<input type='hidden' name='kfid' value='" + item.kfid + "'/>");*/
                        strHtml.push("<td>" + item.goodsname + "</td>");
                        strHtml.push("<td>" + item.kfname + "</td>");
                        strHtml.push("<td>" + item.cskc + "</td>");
                        strHtml.push("<td class='" + ac + "'><span>" + item.fbname + "</span>");
                        strHtml.push("<p><img src='/images/ea/finance/invoicing/img_07.png'/></p></td></tr>");
                    });
                    strHtml.push("</table>");
                    $(".div-box").append(strHtml.join(""));
                }
            }).catch(function (err) {
                // 当出错时，此处代码运行
                console.log(err);
            });
        } else {
            //获取订单号
            $.ajax({
                url: "/ea/initialize/sajax_ea_getStaff.jspa",
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
                    $("#jum").text(jum);
                    $(".jum").val($("#jum").text());
                    $("#staffname").text(staffname);
                    $(".staffname").val($("#staffname").text());
                    $(".staffid").val(staffid);
                    $("#inidate").text(new Date().Format("yyyy-MM-dd HH:mm:ss"));
                    $(".inidate").val($("#inidate").text());
                }
            });
        }
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });

    //扫码拣货单击事件
    $("#wei").click(function () {
        addForm();
        window.open("/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + comid + "&showButton=true&sort=1");
    });

    //扫码拣货单击事件
    $("#sm").click(function () {
        if (isAndroid == true) {
            //Android.calltiaoma();
            Android.callcamera();
        } else {
            var url = "func=" + 'calltiaomaIOS';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
        //eachgood("6925105811570");
    });

    $(".p-tj").click(function () {
        localforage.getItem('formjum').then(function (formjum) {
            //当离线仓库中的值被载入时，此处代码运行
            console.log(formjum);
            var serializeObj = {};
            if (formjum != null && formjum != "") {
                var serializeObj = {};
                $(formjum).each(function () {
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
                localforage.getItem('serializeJson').then(function (value) {
                    //当离线仓库中的值被载入时，此处代码运行
                    console.log(value);
                    if (value != null && value != "") {
                        $.ajax({
                            url: "/ea/initialize/sajax_ea_ajaxSaveInitialize.jspa",
                            type: "get",
                            async: false,
                            dataType: "json",
                            data: {
                                "formjum": JSON.stringify(serializeObj),
                                "serializeJson": JSON.stringify(value)
                            },
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                var falg = member.falg;
                                if (falg == "完成") {
                                    localforage.clear();
                                    window.location.href = "/page/ea/main/finance/invoicing/InitialiaeList.jsp?compayid=" + comid + "&staffid=" + staffid;
                                } else {
                                    alert("保存失败");
                                }
                            }
                        });
                    }
                });
            }
        });
    });
});

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
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//扩展jquery的格式化方法
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

//Android扫码回调
function calltiaoma(tiaoma) {
    var member = eval("(" + tiaoma + ")");
    eachgood(member.code);
}

//ios扫码回调
function calltiaomaIOS(tiaoma) {
    var member = eval("(" + tiaoma + ")");
    eachgood(member.code);
}

//扫码拣货
function eachgood(barcode) {
    var count = 0;
    var money = null;
    var flag=true;
    if (barcode.substring(0, 2) == "21") {
        money = parseFloat(barcode.substring(7, 12)) * 0.01;
        money = money.toFixed(2);
        barcode = barcode.substring(2, 6);
    }
    var formgoods = [];
    if (serializeJson != null) {
        $.each(serializeJson, function (index, item) {
            if (item.barcode == barcode) {
                formgoods = [];
                formgoods.push({"name": "index", "value": index});
                formgoods.push({"name": "cskc", "value": item.cskc});
                formgoods.push({"name": "ppid", "value": item.ppid});
                formgoods.push({"name": "goods", "value": item.goods});
                formgoods.push({"name": "goodsname", "value": item.goodsname});
                formgoods.push({"name": "invenquantity", "value": item.invenquantity});
                formgoods.push({"name": "variableid", "value": item.variableid});
                formgoods.push({"name": "brand", "value": item.brand});
                formgoods.push({"name": "kfid", "value": item.kfid});
                formgoods.push({"name": "kfname", "value": item.kfname});
                formgoods.push({"name": "codeid", "value": item.codeid});
                formgoods.push({"name": "codevalue", "value": item.codevalue});
                formgoods.push({"name": "barcode", "value": item.barcode});
                return false;
            }
        });
    }
    if (formgoods.length <= 0) {
        $.ajax({
            url: "/ea/initialize/sajax_ea_getProduct.jspa",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "compayid": comid,
                "barcode": barcode
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var error = member.error;
                if (error != null && error != "") {
                    alert(error);
                    flag=false;
                } else {
                    var objList = member.objList;
                    var depotid = member.销售库;
                    if(objList.length>0){
                        var obj = objList[0];
                        if (obj[7] == "00" && obj[8] == "00") {
                            alert("没有设置佣金");
                            flag=false;
                        } else {
                            formgoods.push({"name": "ppid", "value": obj[0]});
                            formgoods.push({"name": "goods", "value": obj[1]});
                            formgoods.push({"name": "goodsname", "value": obj[2]});
                            formgoods.push({"name": "invenquantity", "value": obj[3]});
                            formgoods.push({"name": "variableid", "value": obj[4]});
                            formgoods.push({"name": "brand", "value": obj[5]});
                            formgoods.push({"name": "kfid", "value": depotid});
                            formgoods.push({"name": "kfname", "value": "销售库"});
                            formgoods.push({"name": "codeid", "value": obj[6]});
                            formgoods.push({"name": "codevalue", "value": obj[7]});
                            formgoods.push({"name": "barcode", "value": barcode});
                        }
                    }else{
                        alert("没有数据，请先录入数据");
                        flag=false;
                    }
                }
            }
        });
    }
    if(flag){
        localforage.setItem('formgoods', formgoods).then(function (value) {
            // 当值被存储后，可执行其他操作
            console.log(value);
            addForm();
            window.location.href = "/page/ea/main/finance/invoicing/InitialiaeAdd_Good.jsp";
        }).catch(function (err) {    // 当出错时，此处代码运行
            console.log(err);
        });
    }
}

function addForm() {
    localforage.setItem('formjum', $("#formjum").serializeArray()).then(function (value) {
        // 当值被存储后，可执行其他操作
        console.log(value);
    }).catch(function (err) {    // 当出错时，此处代码运行
        console.log(err);
    });
}

function toBack() {
    localforage.clear().then(function () {
        console.log('Database is now empty.');
    }).catch(function (err) {
        // This code runs if there were any errors
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
        }
    } catch (error) {
        window.history.go(-1);
        return false;
    }
}