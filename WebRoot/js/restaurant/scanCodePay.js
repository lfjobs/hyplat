
$(function(){



    //确认转账
    $(".exchange").click(function(){
        morre = Number($(".QRpay_inp").val());
        //生成订单号
        $.ajax({
            url:basePath+"/ea/assicode/sajax_ea_getJum.jspa",
            type:"get",
            aysnc:false,
            data:{
                 sccid:sccid,
                 "payBackupBill.waiterID":waiterID,
                 "payBackupBill.companyId":companyId,
                 "payBackupBill.ppid":ppid,
                 "payBackupBill.sccid":sccid,
                 "payBackupBill.attach":attach,
                 "payBackupBill.coID":coID

             },
            success:function suc(data){
                var mb=eval("("+data+")");
                jum=mb.jum;
                staffid=mb.wfj_staffid;
            }
        });
    });




});



function zf(zffs){
    if(zffs==null){
        prompt("请选择支付方式");
        return false;
    }else{
        var companyName=$("#companyName").val();
        if(zffs=='1'){
            $(".loading").hide();
            $(".payways_wrap").removeClass("show");
            $(".overlay").removeClass("active");
            // $(".QRpay_inp").val("");
            var par=new Array();
            par.push(basePath);
            par.push("page/ea/main/finance/BenDis/wfjAlipay.jsp?");
            par.push("WIDout_trade_no="+jum);
            par.push("&WIDtotal_fee="+morre);
            par.push("&WIDsubject=("+companyName+")"+goodsname);
            par.push("&WIDbody="+staffid+","+sccid+","+ppid+",1,1,1,1,"+attach);//订单描述
            par.push("&WIDit_b_pay=''");//超时时间
            par.push("&WIDextern_token=''");//钱包
          //  window.location.href = encodeURI(par.join(""));
            _AP.pay(encodeURI(par.join("")));

        }else if(zffs=='2'){
            $(".loading").hide();
            $(".payways_wrap").removeClass("show");
            $(".overlay").removeClass("active");
            document.forms[0].reset();
            $("#formsutm").find("#journalNum").val(jum);
            $("#formsutm").find("#baseUrl").val(basePath);
            $("#formsutm").find("#morre").val(morre);
            $("#formsutm").find("#staffid").val(staffid);
            $("#formsutm").find("#sccid").val(sccid);
            $("#formsutm").find("#ppid").val(ppid);
            $("#formsutm").find("#khd").val("1");
            $("#formsutm").find("#flag").val("1");
            $("#formsutm").find("#identifying").val("1");
            $("#formsutm").find("#ccompanyId").val("1");
            $("#formsutm").find("#isflag").val(attach);
            $("#formsutm").attr("action",basePath + "/ea/jinbi/ea_zfgs.jspa");
            $("#submit").click();
        }else if(zffs=='3'){

            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {
                $(".loading").show();
                //app微信支付
                $.ajax({
                    url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa?dat="+new Date(),
                    type:"get",
                    data:{
                        "payDto.orderId":jum,
                        "payDto.totalFee":morre,
                        "payDto.body":"("+companyName+")"+goodsname,
                        "payDto.attach":attach,
                         ppid:ppid,
                         staffid:staffid,
                         sccid:sccid,

                    },
                    success:function suc(data){
                        var mb=eval("("+data+")");
                        var appPackage=mb.appPackage;
                        //调用ios/android
                        var u = navigator.userAgent;
                        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                        var  appid = appPackage.appid
                        var partnerid = appPackage.partnerid;
                        var prepayid = appPackage.prepayid;
                        var packages = appPackage.packages;
                        var noncestr = appPackage.noncestr;
                        var timestamp = appPackage.timestamp;
                        var err_code = appPackage.err_code;
                        var sign = appPackage.sign;
                        $(".loading").hide();
                        $(".payways_wrap").removeClass("show");
                        // $(".QRpay_inp").val("");
                        $(".overlay").removeClass("active");
                        if(isAndroid==true){

                            Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,jum,err_code,goodsname,attach);
                        }else if(isiOS==true){
                            var url= "func=" + 'ioscallappChat';
                            params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':jum,'errcode':err_code,'goodsname':goodsname,'attach':attach};
                            for(var i in params){
                                url = url + "&" + i + "=" + params[i];
                            }
                            window.webkit.messageHandlers.Native.postMessage(url);
                        }
                    },error:function (data) {
                        alert("生成预订单号失败")
                    }

                });

            }else{
                $(".loading").hide();
                $(".payways_wrap").removeClass("show");
                $(".overlay").removeClass("active");
                //$(".QRpay_inp").val("");
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+jum+"-"+goodsname+"-"+morre+"-"+staffid+"-"+sccid+"-"+ppid+"-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

            }

        }
    }
}

