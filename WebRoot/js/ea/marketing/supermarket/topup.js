$(document).ready(function() {
    $("#topup p span").text(integral);

    $(".payment_con li").each(function () {
        if ($(this).hasClass("active")) {
            $(this).find(".radio").css({"background-color": "#ff5d15", "border-color": "#ff5d15"});
        }
    });
    /*支付选择*/
    $(".payment_con li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        $(this).find(".radio").css({"background-color": "#ff5d15", "border-color": "#ff5d15"});
        $(this).siblings().find(".radio").css({"background-color": "#eee", "border-color": "#cacaca"});
        methodPayment = $(this).find(".payMethod").val();
    });

    //隐藏二维码
    var code_hei=-415;
    $("#code").css("top",code_hei);

    //出现二维码
    $("#btn_5").click(function(e){
        $("#code").animate({
            "top":"200"
        },"slow")
        e.stopPropagation();
    });
    //关闭二维码
    $("#topup").not("#code").click(function(e){
        if($("#code").css("top")=="200px"){
            $("#code").animate({
                "top":code_hei
            },"slow")
        }
    })



    /*点击确认付款*/
    $(".address_bid div .payment").click(function(){
        var price =  $("#integralNumber").val();
        var reg=/^[1-9]\d*$|^0$/;  //判断输入是否是数字
        if(reg.test(price)==true){
            if(price == 0){
                alert("请重新输入！");
                $("#integralNumber").val(null);
                return;
            }
        }else{
            alert("输入格式错误，请重新输入！");
            $("#integralNumber").val(null);
            return;
        }
        //生成订单号

        price = price/100;
        $.ajax({
            url : basePath + "ea/newpcend/sajax_ea_ajaxValidatePayBills.jspa",
            type : "post",
            async : true,
            data : {
                "payJournalNum" : jum,
                "total_amount" : integral
            },
            dataType : "json",
            success : function(data){
                var result = eval("(" + data + ")");
                var login = result.login;
                var payBillsExist = result.payBillsExist;
                var payBillsMoney = result.payBillsMoney;
                // if(login=="login"){
                //     document.location.href = basePath + "page/newMyapp/login.jsp?loginPage=login";
                //     return;
                // }
                // if(payBillsExist=="false"){
                //     alert("该订单不存在！");
                //     document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
                //     return;
                // }
                // if(payBillsMoney=="false"){
                //     alert("该订单有误！");
                //     document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
                //     return;
                // }
                var payMehod = $(".payment_con .active .payMethod").val();
                if(payMehod=="aliPay"){
                    document.location.href = encodeURI(basePath + "page/newMyapp/PC_wfjAlipay.jsp?payJournalNum="+jum+"&projectName="+subject+"&projectBody="+staffid+","+sccid+","+coin+","+khd+","+flag+","+identifying+","+ccompanyId+","+isflag+"&total_amount="+price+"&isflag="+isflag);
                    return;
                } else if(payMehod=="weChat"){
                    $.ajax({
                        url : basePath + "ea/newpcend/sajax_ea_ajaxMakeWeChatPayCodeUrl.jspa",
                        type : "post",
                        async : true,
                        data : {
                            "basePath" : basePath,
                            "payJournalNum" : jum,
                            "total_amount" : price,
                            "sccid":sccid,
                            "projectName" : subject,
                        },
                        success : function(data){
                            $("#code p span").attr("style","color: red;").text("￥"+price)
                            $("#qrcode").empty();
                            //出现二维码
                                $("#code").animate({
                                    "top":"200"
                                },"slow");
                            var qrcode = new QRCode(document.getElementById("qrcode"),{
                                text: data,
                                width: 256,
                                height: 256,
                                colorDark : '#000000',
                                colorLight : '#ffffff',
                                correctLevel : QRCode.CorrectLevel.H
                            });
                            qrcode.makeCode(data);
                        }
                    });
                    /*检测是否完成微信确认付款*/
                    function validatePayComplete(){
                        $.ajax({
                            url : basePath + "ea/newpcend/sajax_ea_ajaxValidateRelatedBill.jspa",
                            type : "post",
                            async : true,
                            data : {
                                "payJournalNum" : jum
                            },
                            success : function(data){
                                if(data=="payComplete"){
                                    window.clearInterval(int);
                                    alert("支付成功！")
                                    window.close();
                                }
                            }
                        });
                    }
                    var int = self.setInterval(function(){validatePayComplete()},500);
                }
            },
            error : function(){
                /*alert("验证订单失败！");*/
            }
        });
    });
})