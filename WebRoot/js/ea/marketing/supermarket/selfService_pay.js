var device_id = "";
$(function(){
    try {
        var scaleNo = getCookie("scaleNo");
        if (scaleNo != null && scaleNo != "") {
            $("#amend").hide();
        } else {
            $("#amend").show();
        }
    }catch(error){

    }



    if(cardNum!=null&&cardNum!=""){
        $("#amend").attr("disabled",true);
    }
    $(".barcode").focus();
    $(".barcode").blur(function(){

        $(".barcode").focus();
    });

    if(posNum!=null&&posNum!=""){
       $("#cancel").hide();
        $("#amend").hide();
    }
    var c = "";
    if(directUrl!=""&&directUrl!=null) {
        c = basePath + "ea/restaurant/ea_scancode.jspa?scancode=08" + journalNum + "&totalMoney=" + totalMoney + "&tj=" + sccid + "&cardNum=" + cardNum + "&posNum=" + posNum +"&remainMoney="+remainMoney+"&directUrl=" + encodeURIComponent(directUrl + "&mode=scan");
    }else {
        c = basePath + "ea/restaurant/ea_scancode.jspa?scancode=08" + journalNum + "&totalMoney=" + totalMoney + "&tj=" + sccid + "&cardNum=" + cardNum + "&posNum=" + posNum+"&fh="+fh+"&remainMoney="+remainMoney;
      //  c = "http://localhost:8080/hyplat/ea/restaurant/ea_scancode.jspa?scancode=082025031411540000001&totalMoney=35.0&tj=sccid201601239QHAIZP9560000000191&cardNum=&posNum=26a261b7fe51032df994ec8bf9b107083&fh=1&remainMoney=35.0";
    }
    jQuery("#qrcode").qrcode({
        render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
        text :c,
        width : "378",               //二维码的宽度
        height : "378",              //二维码的高度
        background : "#ffffff",       //二维码的后景色
        foreground : "#000000"    //二维码的前景色

    })
    //     // 创建二维码
    //     var   qrcode = new QRCode(document.getElementById("qrcode"), {
    //         width : 378,//设置宽高
    //         height : 378
    //     });
    // if(directUrl!=""&&directUrl!=null) {
    //     qrcode.makeCode(basePath + "ea/restaurant/ea_scancode.jspa?scancode=08" + journalNum + "&totalMoney=" + totalMoney + "&tj=" + sccid + "&cardNum=" + cardNum + "&posNum=" + posNum +"&remainMoney="+remainMoney+"&directUrl=" + encodeURIComponent(directUrl + "&mode=scan"));
    // }else{
    //   //  qrcode.makeCode(basePath + "ea/restaurant/ea_scancode.jspa?scancode=08" + journalNum + "&totalMoney=" + totalMoney + "&tj=" + sccid + "&cardNum=" + cardNum + "&posNum=" + posNum+"&fh="+fh+"&remainMoney="+remainMoney);
    //     qrcode.makeCode("http://localhost:8080/hyplat/ea/restaurant/ea_scancode.jspa?scancode=082025031411540000001&totalMoney=35.0&tj=sccid201601239QHAIZP9560000000191&cardNum=&posNum=26a261b7fe51032df994ec8bf9b107083&fh=1&remainMoney=35.0");
    //
    // }
    //

    //
    // // 创建二维码
    // var   qrcode1 = new QRCode(document.getElementById("qrcode1"), {
    //     width : 169,//设置宽高
    //     height : 169
    // });
    // qrcode1.makeCode(basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid);
    jQuery("#qrcode1").qrcode({
        render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
        text :basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid,
        width : "169",               //二维码的宽度
        height : "169",              //二维码的高度
        background : "#ffffff",       //二维码的后景色
        foreground : "#000000"    //二维码的前景色

    })
        //取消订单

        $("#cancel").click(function(){

            if(posNum!=null&&posNum!=""){
                var ulp = basePath
                    + "/ea/sm/sajax_ea_cancelOrder.jspa";
                $.ajax({
                    type : "GET",
                    url : ulp,
                    async : false,
                    dataType : "json",
                    data:{
                       posNum:posNum
                    },
                    success : function(data) {
                        var me = eval("("+data+")");

                        document.location.href = basePath+"ea/smg/sm_toSupermarketGoods.jspa?ccompanyID="+me.ccompanyID+"&industryType="+me.industryType+"&companyName="+me.companyName+"&posNum="+posNum;
                    },
                    error : function(data) {
                        console.log("查询支付结果失败");
                    }
                });
            }else{
                document.location.href = basePath+"ea/sm/ea_cancelOrder.jspa?journalNum="+journalNum;

            }


        });


        //修改订单

        $("#amend").click(function(){

            if(cardNum!=null&&cardNum!=""){
                return;
            }
            var param = "";
            if(posNum!=null&&posNum!=""){
                param = "&addressID=1";
            }



           document.location.href = basePath+"ea/sm/ea_qdlist.jspa?journalNum="+journalNum+"&posNum="+posNum+param+"&ccompanyID="+ccompanyID;


        });
    /*点击下载*/
    $(".code-pay figure .p1").click(function () {
        $(".alert_cancel_").show();
        $(".alert_download").show();
    });
    /*关闭取消弹框*/
    $(".alert_cancel_").click(function () {
        $(this).hide();
        $(".alert_cancel").hide();
        $(".alert_download").hide();
    });
    //每3秒查询一次是否支付成功

    timer = setInterval(searchResult,2000);




});

/**
 *
 * 定时查询支付结果
 */
function searchResult(){
    var ulp = basePath
        + "/ea/sm/sajax_ea_searchPayResult.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            journalNum:journalNum
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var result = member.result;
            if(result==true){
                window.clearInterval(timer);
                //跳转到支付成功页面
                var param = "";
                if(posNum!=null&&posNum!=""){
                    param = "&addressID=1";
                }
                document.location.href = basePath+"ea/sm/ea_viewOrderDetail.jspa?journalNum="+journalNum+"&posNum="+posNum+param;

            }

        },
        error : function(data) {
            console.log("查询支付结果失败");
        }
    });


}


document.onkeydown = function(evt){//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        if(posNum!=null&&posNum!=""){
            if(cardNum!=null&&cardNum!="") {
            }else{
                if(fh=="1"){
                }else  if(fh=="2"){
                      return false;
                }

            }
        }
        var barcode = $(".barcode").val();
        if(barcode!=""){
            var arr = ["10","11","12","13","14","15"];
            var zfbarr = ["25","26","27","28","29","30"];
            if(barcode!=""&&barcode.length==18&&($.inArray(barcode.substring(0,2), arr)!=-1)){

               try {

                   var u = window.navigator.userAgent;
                   var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

                   if (isAndroid == true) {
                       device_id = Android.obtainWinXinFaceDeviceId();//调用安卓接口
                   }
               }catch (error){
                      device_id = "";
               }


                if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){

                    totalMoney = remainMoney;
                }
                    var ulp = basePath
                        + "/ea/sm/sajax_ea_payWCode.jspa";
                    $.ajax({
                        type : "GET",
                        url : ulp,
                        async : true,
                        dataType : "json",
                        data:{
                            "micropay.out_trade_no":journalNum,
                            totalMoney:totalMoney,
                            barCode:barcode,
                            device_id:device_id,
                            remainMoney:remainMoney
                        },
                        success : function(data) {
                            var member = eval('(' + data + ')');
                            var result_code = member.result_code;
                            cresccid = member.cresccid;
                            if(result_code=="SUCCESS") {
                                var trade_state = member.trade_state;

                                if (trade_state != "SUCCESS") {
                                    if (trade_state == "USERPAYING") {
                                        tim = setInterval(weChatBaseRep(), 2000);
                                    }
                                }
                            }else{
                                var err_code = member.err_code;
                                if (err_code == "USERPAYING") {
                                    tim = setInterval(weChatBaseRep, 2000);
                                }
                                var shot = showTime();
                                $(".barcode").val("");
                                var shot = showTime();
                                var tnc = "";
                                if(err_code=="USERPAYING"){
                                    tnc = "等待用户输入微信密码";
                                }else{
                                    tnc = "付款失败，可以选择其他支付方式";
                                }
                                $(".tipcon").text(tnc);
                                $(".alert_weigh_").show();
                                shot;

                            }


                        },
                        error : function(data) {
                            console.log("查询支付结果失败");
                        }
                    });

            }else if(barcode.length>=16&&barcode.length<=25&&($.inArray(barcode.substring(0,2), zfbarr)!=-1)){
                    var ulp = basePath
                        + "/ea/sm/sajax_ea_alipayWCode.jspa";
                    $.ajax({
                        type : "GET",
                        url : ulp,
                        async : true,
                        dataType : "json",
                        data:{
                            journalNum:journalNum,
                            totalMoney:totalMoney,
                            barCode:barcode,
                            comID:comID
                        },
                        success : function(data) {
                            var member = eval('(' + data + ')');
                            var code = member.code;
                            cresccid = member.cresccid;
                            var tnc = "";
                            var shot = showTime();
                            if(code=="10003") {

                                   altim = setInterval(alipayBaseRep, 5000);
                                   tnc = "等待用户付款";
                            }

                            // else if(code="40004"){
                            //
                            //      tnc = "付款失败，可以选择其他支付方式";
                            //
                            // }

                            $(".tipcon").text(tnc);
                            $(".alert_weigh_").show();
                            shot;


                        },
                        error : function(data) {
                            console.log("查询支付结果失败");
                        }
                    });

            }


        }

        return false;
    }
}
//定时查询微信支付结果
function weChatBaseRep(){


        var ulp = basePath
            + "/ea/sm/sajax_ea_weChatBaseRep.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : false,
            dataType : "json",
            data:{
                journalNum:journalNum,
                totalMoney:totalMoney,
                cresccid:cresccid,
                device_id:device_id
            },
            success : function(data) {
                var member = eval('(' + data + ')');
                var result = member.result;
                if(result=="SUCCESS") {
                     clearTimeout(tim);
                }
            },
            error : function(data) {
                console.log("查询支付结果失败");
            }
        });


}


//定时查询阿里支付结果
function alipayBaseRep(){


    var ulp = basePath
        + "/ea/sm/sajax_ea_alipayBaseRep.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            journalNum:journalNum,
            totalMoney:totalMoney,
            cresccid:cresccid
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var result = member.result;
            if(result=="TRADE_SUCCESS") {
                    clearTimeout(altim);

            }else if(result=="TRADE_CLOSED") {

                var tnc = "付款失败，点击修改商品，重新下单";


                $(".tipcon").text(tnc);
                $(".alert_weigh_").show();
                shot;
            }


        },
        error : function(data) {
            console.log("查询支付结果失败");
        }
    });


}
//设定倒数秒数
var t = 4;
function showTime(){
    t -= 1;
    $(".alert_weigh p span").text(t);

    //每秒执行一次,showTime()
    var s = setTimeout("showTime()",1000);

    if(t==0){
        t = 4;
        $(".alert_weigh_").hide();
        clearTimeout(s);
        $(".alert_weigh p span").text(t);
    }
    /*商品称重弹框*/
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        clearTimeout(s);
        t = 4;
        $(".alert_weigh p span").text(t);
    });
}

function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return decodeURI(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}
