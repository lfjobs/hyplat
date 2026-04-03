var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {

    if (sort == 2) {
        $("body").removeClass("no-header");
    } else {
        $("body").addClass("no-header");
    }

    $(".close").click(function () {
        $(".wmcz").hide();
        $(".txdh").hide();
    });

    $(".btn2").click(function () {
        $(".txdh").show();
    });

    $("#smtm").click(function () {
        if (isAndroid == true) {
            Android.callYunDan();
        } else {
            var url = "func=" + 'calliosyundan';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    });

});

function isOK(str) {
    var Waybillno;//运单hao
    var ExCode;
    var flag="00"; //00成功  01失败
    /*var a=document.getElementById(str);
     var aa = $a.attr("class");*/
    if (str == "close") {
        Waybillno = $("#yd").val().trim();//运单hao
        if (Waybillno == null || Waybillno == "") {
            alert("运单号不能为空");
            return;
        }else{
            var url =  "ea/seller/sajax_ea_numberRecognition.jspa?";
            $.ajax({
                url: encodeURI(url),
                type: "get",
                async: false,
                dataType: "json",
                data: {
                    "number": Waybillno
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var shippers = member.Shippers;
                    var success = member.Success;
                    var code = member.Code;
                    if (success == true) {
                        var Identification = shippers[0];
                        if (Identification != null && Identification != "" && Identification != undefined) {
                            var biaoshi = Identification.ShipperCode;
                            var name = Identification.ShipperName;
                            $("#biaoshi").val(biaoshi);
                            ExCode=biaoshi;
                            console.log("成功");
                        }else{
                            alert("快递单号错误");
                            $("#yd").val("");
                            flag="01";
                            return;
                        }
                    } else {
                        console.log(code);
                        return;
                    }
                },
                error: function (data) {
                    console.log("错误");
                }
            });
        }
    }

    if(flag=="00"){
        var url = "ea/seller/sajax_ea_TransportLogicalProcessing.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "Waybillno": Waybillno,
                "ExCode": ExCode,
                "transportid": $("#transportid").val(),
                "staffid": staffid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var flag = member.flag;
                if (flag == "00") {
                    alert("操作成功");
                    var url = "ea/seller/ea_getTransportGoods.jspa?reansportid=" + reansportid+"&staffid=" + staffid+"&sort="+sort;
                    window.location.href = url;
                } else {
                    alert("生成失败");
                }
            },
            error: function (data) {
                console.log("错误");
            }
        });
    }
}

//Android扫码回调
function calltiaoma(tiaoma) {
    var member = eval("(" + tiaoma + ")");
    $("#yd").val(member.code);
}

//ios扫码回调
function calltiaomaIOS(tiaoma) {
    var member = eval("(" + tiaoma + ")");
    $("#yd").val(member.code);
}

function yanzhen(number) {
    var url = basePath + "ea/seller/sajax_ea_numberRecognition.jspa?number=" + number;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var shippers = member.Shippers;
            var success = member.Success;
            var code = member.Code;
            if (success == true) {
                var Identification = shippers[0];
                if (Identification != null && Identification != "" && Identification != undefined) {
                    var biaoshi = Identification.ShipperCode;
                    var name = Identification.ShipperName;
                    $("#biaoshi").val(biaoshi);
                    $("#yd").val(number);
                    console.log("成功");
                }
            } else {
                console.log(code);
                return;
            }
        },
        error: function (data) {
            console.log("错误");
        }
    });
}
function toBack() {
    history.go(-1);
}