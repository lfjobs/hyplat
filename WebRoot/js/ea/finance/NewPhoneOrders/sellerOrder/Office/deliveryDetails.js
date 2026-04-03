var gotrid = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') >1;
var host='127.0.0.1:33582';
$(document).ready(function () {
    if (dpFlag) {
        if ($(".wmcz").is(':hidden')) {
            $("#displayIndex").focus();
        }
    }

    if (sort == 2) {
        $("body").removeClass("no-header");
    } else {
        $("body").addClass("no-header");
    }

    $("table tr").hover(function () {
        $(this).css({"background-color": "#ffe7e3"});
        gotrid = $(this).attr("id");

        if ($("#" + gotrid).find(".isscale").val() == null || $("#" + gotrid).find(".isscale").val() == "") {
            htmlAppend();
        }
    }, function () {
        $(this).css({"background-color": "#fff"});
    });

    $("table tr:first-of-type").hover(function () {
        $(this).css({"background-color": "#fff"});
    });

    //商品加减
    $(document).on("click", ".li-add", function () {
        var li_text = $(this).parents(".num").find("li").eq(1).text();
        if ($(this).text() == "+") {
            li_text++;
        } else {
            if (li_text >= 2) {
                li_text--;
            }
        }
        $(this).parents(".num").find("li").eq(1).text(li_text);
        var dds = $(this).parents("tr").find(".td-dds").text();
        var cs = dds - li_text;
        if (cs > 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-zl");
        } else if (cs == 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-sl1");
        } else if (cs < 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-sl2");
        }
        $(this).parents("tr").find(".td-cs").text(cs);

    });

    //扫码拣货单击事件
    $("#sm").click(function () {
        if (!dpFlag) {
            if (isAndroid == true) {
                //Android.calltiaoma();
                Android.callcamera();
            } else {
                var url = "func=" + 'calltiaomaIOS';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        } else {
            $(".wmcz").empty();
            var weihtml = "<section>";
            weihtml += "<h3>";
            weihtml += "扫码拣货";
            weihtml += "</h3>";
            weihtml += "<div class='div-p1'>";
            weihtml += "<p><label>商品条码</label><input type='text' id='barcode' onfocus='onFocus()' onblur='onBlur()'/></p>";
            weihtml += "<div class='div-p2 clearfix'><p class='close'>取消</p><p class='isok'>确定</p></div>";
            weihtml += "</div></section>";
            $(".wmcz").append(weihtml);
            $(".wmcz").show();
            $("#barcode").focus();
        }
    });

    $(document).on("blur", "#barcode", function () {
        $(this).focus();
    });
    /****************************************************无码称重开始***************************************************/

    //无码称重
    $("#wei").click(function () {
        if (gotrid != null && gotrid != "") {
            if (isAndroid == true) {
                Android.weighingInitAndroid();
            }else if(iswin){
                //100毫秒一次获取秤的重量和稳定值
                setInterval(function() {
                    $.ajax({
                        url: "http://" + host + "/api/Scale/Weight",
                        dataType: "json",
                        type: 'get',
                        cache: false,//IE下要关闭cache，否则不会刷新
                        data: null,
                        success: function (data) {
                            /**
                             * 服务安装文件地址“http://www.impf2010.com/upload_files\ADSService\ADSService.7z”
                             * 下载后解压，按照安装说明配置
                             **/
                            $(".weight").val(data.Weight.toFixed(3));
                            var cashnum = $("#cashnum").val();
                            $("#weight").val(member.weight);
                            $("#difference").val(cashnum - $("#weight").val());
                            if (data.IsSteady == 0) {
                                $(".weight").css("color", "red");
                            }
                            else {
                                $(".weight").css("color", "green");
                            }
                        },
                        error: function (XMLHttpRequest) {
                            alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
                        }
                    });
                }, 100);
            }
            if ($("#" + gotrid).find(".isscale").val() != null && $("#" + gotrid).find(".isscale").val() != "") {
                htmlAppend();
            } else {
                alert("该产品不是称重产品！");
            }
        } else {
            alert("请选择称重产品！");
        }

    });

    //取消/关闭
    $(document).on("click", ".close", function () {
        close();
    });

    //清零
    $("input.zero").click(function () {
        if (isAndroid == true) {
            Android.weighingZeroAndroid();
            $(".weight").val("0.0");
        } else if(iswin){
            $.ajax({
                url: "http://" + host + "/api/Scale/Zero",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                        $(".weight").val("0.0");
                },
                error: function (XMLHttpRequest) {
                    alert("归零失败, " + XMLHttpRequest.responseJSON.Message);
                }
            })
        }
    });

    //去皮
    $("input.peeled").click(function () {
        if (isAndroid == true) {
            Android.weighingTareAndroid();
        } else if(iswin){
            $.ajax({
                url: "http://" + host + "/api/Scale/Tare",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                    alert("去皮成功");
                },
                error: function (XMLHttpRequest) {
                    alert("去皮失败, " + XMLHttpRequest.responseJSON.Message);
                }
            })
        }
    });

    //点击确定按钮
    $(document).on("click", ".isok", function () {
        document.activeElement.blur();
        var reg = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//称重商品重量
        num = $("#weight").val();

        if (!reg.test(num)) {
            alert("请确保数据有效！");
            $(".inputValue").val("");
            return false;
        }

        $("#" + gotrid).find(".num").text(num);
        $("#" + gotrid).find(".weinum").val(num);
        $("#" + gotrid).find(".td-cs").text($("#difference").val());
        var dds = $("#" + gotrid).find(".td-dds").text();
        var cs = dds - $("#difference").val();
        if (cs > 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-zl");
        } else if (cs == 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-sl1");
        } else if (cs < 0) {
            $(this).parents("tr").removeClass();
            $(this).parents("tr").addClass("tr-sl2");
        }
        $(this).parents("tr").find(".td-cs").text(cs);
        close();
        return false;
    });

    /****************************************************无码称重结束***************************************************/

    //计算误差
    $(document).on("keyup", "#weight", function () {
        parseFloat($(this).val());
        $("#difference").val($("#cashnum").val() - parseFloat($(this).val() == null || $(this).val() == "" ? 0 : $(this).val()));
    });


    $("#submit").click(function () {
        var t = false;
        $(".num").each(function () {
            if ($(this).text == null || $(this).text == "" || $(this).text == "0") {
                t = true;
                return false;
            }
        });
        if (t) {
            alter("拣货未完成！");
            return false;
        } else {
            $.ajax({
                url: "ea/seller/sajax_ea_DeliveryLogicalProcessing.jspa",
                type: "post",
                async: false,
                dateType: "json",
                data: {
                    "jsonval": JSON.stringify($("#transport").serializeArray()),
                    "orderid": $("#orderid").val(),
                    "staffid": staffid
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var flag = member.flag;
                    if (flag == "00") {
                        alert("操作成功");

                    } else if (flag == "01") {
                        alert("库存不足");
                    } else if (flag == "02") {
                        alert(" 拣货未完成");
                    } else if (flag == "03") {
                        alert("发货单已生成不可重复生成");
                    } else if (flag == "04") {
                        alert("操作失败");
                    }
                }
            });
        }

    });

    $(document).on("keydown", "input", function (event) {
        if (event.keyCode == 13) {
            var barcode = $(this).val();
            $(this).val("");
            eachgood(barcode);
        }
    });
});

//关闭无码称重弹框
function close() {
    $(".wmcz").empty();
    $(".wmcz").hide();
    if ($("#" + gotrid).find(".isscale").val() != null && $("#" + gotrid).find(".isscale").val() != "") {
        if (isAndroid == true) {
            Android.weighingCloseAndroid();
        } else {
            alert("请在安卓设备访问");
        }
    }
    $("#displayIndex").focus();
}

//称重
function getWeight(data) {
    var member = eval("(" + data + ")");
    var type = member.type;
    var weight = member.weight;
    var cashnum = $("#cashnum").val();
    $("#weight").val(member.weight);
    $("#difference").val(cashnum - num);
}

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

//加载弹框数据
function htmlAppend(money) {
    $(".wmcz").empty();
    if ($("#" + gotrid).length > 0) {
        var num = "";
        if (money != null) {
            var price = $("#" + gotrid).find(".dw").val();
            num = round(Number(money / price));
        } else {
            num = $("#" + gotrid).find(".num").text().trim()
        }
        var weihtml = "<section>";
        weihtml += "<h3>";
        if ($("#" + gotrid).find(".isscale").val() != null && $("#" + gotrid).find(".isscale").val() != "") {
            weihtml += "无码称重";
        } else {
            weihtml += "扫码拣货";
        }
        weihtml += "</h3>";
        weihtml += "<div class='div-p1'>";
        if ($("#" + gotrid).find(".isscale").val() == null || $("#" + gotrid).find(".isscale").val() == "") {
            weihtml += "<p><label>商品条码</label><input disabled value='" + $("#" + gotrid).find(".borcode").val().trim() + "' /></p>";
        }
        weihtml += "<p><label>商品名称</label><input disabled value='" + $("#" + gotrid).find(".spanstyle").text() + "' /></p>";
        weihtml += "<p><label>订单数</label><input disabled id='cashnum' value='" + $("#" + gotrid).find(".td-dds").text() + "' /></p>";
        weihtml += "<p><label>重量/数量</label><input type='number' class='zl inputValue' id='weight' oninput='if(value<0)value=0' value='" + num + "' /></p>";
        weihtml += "<p><label>差数</label><input disabled id='difference' value='" + $("#" + gotrid).find(".td-cs").text() + "' /></p>";
        weihtml += "</div><div class='div-p2 clearfix'>";
        if ($("#" + gotrid).find(".isscale").val() != null && $("#" + gotrid).find(".isscale").val() != "") {
            weihtml += "<p>去皮</p><p>清零</p>";
        }
        weihtml += "<p class='close'>取消</p><p class='isok'>确定</p>";
        weihtml += "</div></section>";
        $(".wmcz").append(weihtml);
        $("#weight").focus();
        $(".wmcz").show();
    } else {
        alert("当前订单没有此条码产品");
    }
}

//扫码拣货
function eachgood(barcode) {
    var count = 0;
    var money = null;
    if (barcode.substring(0, 2) == "21") {
        money = parseFloat(barcode.substring(7, 12)) * 0.01;
        money = money.toFixed(2);
        barcode = barcode.substring(2, 6);
    }
    if ($("." + barcode).length > 0) {
        $("." + barcode).each(function () {
            gotrid = $(this).attr("id");
            if (count < $("." + barcode).length) {
                if ($(this).find(".num").text().trim() == 0 || $(this).find(".num").text().trim() == "") {
                    htmlAppend(money);
                    return false;
                }
                if (count + 1 == $("." + barcode).length) {
                    gotrid = $("." + barcode).eq(0).attr("id");
                    htmlAppend(money);
                }
            }
            count += 1;
        });
    } else {
        alert("当前订单没有此条码产品");
    }
}

function round(value) {

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if (aArr.length > 1) {
        if (aArr[1].length > 3) {
            value = Number(aStr).toFixed(3);
        }
    }

    return Number(value);
}

//返回
function toBack() {
    history.go(-1);
}

function toPrint(){
    if (isAndroid == true) {
        Android.androidPrintIntegralInfo3(wfjGuizeCalc + jifenDetailScore + "", wfjGuizeCalc + jifenDetailScore / 100 + "", product + "", staffname + "", fs + "", payDate + "", url + "", ccname + "");//调用安卓接口
    } else if (isiOS == true) {
        alert("研发中，请用windows电脑打印");
    } else {

    }
}