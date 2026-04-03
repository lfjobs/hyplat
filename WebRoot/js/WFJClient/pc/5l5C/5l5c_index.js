let posNum;
let cwts;
let wts;
let u = navigator.userAgent;
let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function () {
    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    });
    if (typeof Android!="undefined"){
        posNum = Android.forAndroidDeviceId();
        if (posNum != null && posNum != "") {
        }
    }
    $.ajax({
        url: basePath + '/ea/dserve/sajax_ea_ajax_demandList.jspa',
        type: 'GET',//POST
        dataType: 'json',
        success: function (response) {
            // 请求成功时的处理
            if (response!=null){
                var member = eval("(" +response + ")");
                cwts = member.cwts;
                wts=member.wts;
                if (cwts==null||cwts==""||cwts=="undefined"){
                    cwts=0;
                }
                $(".ygauth").html("用工认证("+cwts+")");
            }
        },
        error: function (xhr, status, error) {
            // 请求失败时的处理
            console.log(error)
        }
    });

});

//考场约车
function preCar() {

    if (sccid == null || sccid == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else {
        document.location.href = basePath + "ea/mappointment/ea_theTestTime.jspa?sccId=" + sccid + "&sc=web";
    }

}


//收付码
function skm() {
    if (sccid == null || sccid == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
    }else {
        document.location.href = basePath+"page/WFJClient/pc/5l5C/coListPay.jsp?sccId="+sccid;
    }
    /*else if (companyId == null || companyId == "" || companyId == "null") {

        document.location.href = basePath + "ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany";

    } else {
        document.location.href = basePath + "ea/productslaunch/ea_getErWeiMa2.jspa?companyId=" + companyId + "&falg=2";
    }*/

}

//发布项目
function publishpro() {
    if (sccid == null || sccid == "") {
        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
    } else {
        if (posNum != null && posNum != "") {
            map(wts);
        }else {
            document.location.href = basePath + "/page/ea/main/marketing/edmandServe/mapList.jsp";
            //document.location.href = basePath + "/ea/dserve/ea_toaddpage.jspa?staffid=" + staffId + "&sccId=" + sccid + "&tle=1";
        }
    }

}


//收单信息和发布记录
function sdinfo() {
    if (sccid == null || sccid == "") {
        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
    } else {
        if (posNum != null && posNum != "") {
            map(wts);
        }else {
            document.location.href = basePath + "/page/ea/main/marketing/edmandServe/mapList.jsp";
            //document.location.href = basePath + "ea/dserve/ea_detailListBySccid.jspa?staffid=" + staffId + "&sccId=" + sccid + "&tle=1&type=0";
        }
    }

}

//客户榜单

function kfbd() {

    if (sccid == null || sccid == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else {
        document.location.href = basePath + "ea/dserve/ea_toPage_demandListBydssccid.jspa?sccid=" + sccid + "&tle=1";
    }

}

//抢单列表
function qdlist() {

    if (sccid == null || sccid == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else {
        if (posNum != null && posNum != "") {
            map(wts);
        }else {
            document.location.href = basePath + "/page/ea/main/marketing/edmandServe/mapList.jsp";
            //document.location.href = basePath + "ea/dserve/ea_toPage_demandList.jspa?wts=" + wts + "&sccid=" + sccid + "&tle=1";
        }
    }

}

//用工认证
function ygauth() {
    if (sccid == null || sccid == "") {
        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
    } else {
        if (posNum != null && posNum != "") {
            map(wts);
        }else {
            document.location.href = basePath + "/page/ea/main/marketing/edmandServe/mapList.jsp";
            //document.location.href = basePath + "page/ea/main/marketing/edmandServe/workType_list.jsp?";
        }
    }
}

//扫一扫
function sys() {

    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
        var url = basePath
            + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";
        var retUrl = location.href.split('#')[0];
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                retUrl: retUrl
            },
            success: function (data) {
                var m = eval("(" + data + ")");

                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: m.appId, // 必填，公众号的唯一标识
                    timestamp: m.timestamp, // 必填，生成签名的时间戳
                    nonceStr: m.nonceStr, // 必填，生成签名的随机串
                    signature: m.signature,// 必填，签名
                    jsApiList: ["scanQRCode"] // 必填，需要使用的JS接口列表
                });

                wx.error(function (res) {
                    console.log(res);
                });


                wx.ready(function () {
                    wx.scanQRCode({
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode", "barCode", "datamatrix", "pdf417"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                            if (result.indexOf("http") != -1) {
                                window.location.href = result;
                            } else {
                                var barcode = "";
                                if (result.indexOf(",") != -1) {
                                    barcode = result.substring(result.indexOf(",") + 1);
                                } else {
                                    barcode = result;

                                }

                                $.ajax({
                                    url: basePath + "ea/androiddoc/sajax_ea_scanBarcode.jspa",
                                    type: "post",
                                    async: true,
                                    dataType: "json",
                                    data: {
                                        barcode: barcode
                                    },
                                    success: function (data) {
                                        var m = eval("(" + data + ")");
                                        var urls = m.urls;
                                        if (urls == "") {
                                            $(".div-tingyong").show();
                                            $(".titlep").text(barcode);

                                        } else {


                                            window.location.href = basePath + urls + "?barcode=" + barcode;
                                        }

                                    }, error: function (data) {

                                    }
                                })

                            }
                        }
                    });
                });

            }
        });


    } else {

        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        if (isAndroid == true) {

            Android.callcamera();
        } else {
            var url = "func=" + 'calltiaomaIOS';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }

}

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

