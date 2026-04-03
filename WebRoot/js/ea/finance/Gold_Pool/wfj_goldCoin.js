
$(function(){

    //支付页面
    $(".wfj12_014_hidden_buy").attr("style","width:"+$(window).width()+"px;position:absolute;display:none;");
    $(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
    $(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
    $(".wfj12_014_bottom_commit").attr("style","height:"+$(window).height()*0.05+"px; padding-top:"+$(window).height()*0.01+"px;padding-bottom:"+$(window).height()*0.05+"px;");
    $(".wfj12_014_bottom_commit").find("div").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");

    //弹出层
    $("#prompt").css("position","absolute").css("top",$(window).height()*0.10+"px");
    $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

    $(".wfj12_014_choice").find("img").click(function(){
        $(".wfj12_014_choice").find("img").attr("src","<%=basePath %>images/ea/finance/BenDis/choice_02.png");
        $(this).attr("src","<%=basePath %>images/ea/finance/BenDis/choice_01.png");
        zffs=$(this).attr("name");
    });

//	 $(".gold_inp").click(function(){
//		 var gold = $(".gold_inp").val("");
//	 });

    //判断输入的金币数
    $(".gold_inp").on('input',function(){
        var gold = $(".gold_inp").val();
        var reg=/^[1-9]\d*$|^0$/;  //判断输入是否是数字
        if(reg.test(gold)==true){
            if(gold == 0){
                prompt("请重新输入！");
                $(".gold_inp").val(null);
            }
        }else{
            prompt("输入格式错误，请重新输入！");
            $(".gold_inp").val(null);
        }
    });

    //确认支付
    $(".exchange").click(function(){
        var coin = $(".gold_inp").val();
        if(coin.length == 0){
            prompt("请输入充值金币数！");
            return;
        }
        $("#alert").show();
        //生成订单号
        $.ajax({
            url:basePath+"/ea/jinbi/sajax_getJum.jspa?",
            type:"get",
            data:+object.join("")+"&user="+user+"&khd="+khd,
            success:function suc(data){
                var mb=eval("("+data+")");
                jum=mb.jum;
                staffid=mb.wfj_staffid;
            }
        });
        //	ljzf(formatCurrency(coin/100));
        ljzf(coin/100);
    });

    $(".wfj12_014_choice").find("img").click(function(){
        $(".wfj12_014_choice").find("img").attr("src",basePath+"images/ea/finance/BenDis/choice_02.png");
        $(this).attr("src",basePath+"images/ea/finance/BenDis/choice_01.png");
        zffs=$(this).attr("name");
    });
    $("#alert").click(function(){
        $(".wfj12_014_hidden_buy").fadeOut();
        $("#alert").hide();
        $(".gold_inp").val(null);
    });

});
function ljzf(el){

    $("#money2").text("￥"+el);
    $(".wfj12_014_hidden_buy").css("z-index",$("#occlusion2").css("z-index")+1);
    $(".wfj12_014_hidden_buy").fadeIn(1000);

    morre=el;
};

function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){e
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}

function zf(){
    if(zffs==null){
        prompt("请选择支付方式");
        return false;
    }else{
        if(zffs=='1'){
            khd=(khd==""?"a":khd);//khd 表头00要去表头
            flag=(flag==""?"a":flag);//flag 00进入小系统
            identifying=(identifying==""?"a":identifying); //公司 00 个人
            ccompanyId=(ccompanyId==""?"a":ccompanyId);
            var par=new Array();
            par.push(basePath);
            par.push("page/ea/main/finance/BenDis/wfjAlipay.jsp?");
            par.push("WIDout_trade_no="+jum);
            par.push("&WIDtotal_fee="+morre);
            par.push("&WIDsubject=(北京天太世统科技有限公司)微分金金币");
            par.push("&WIDbody="+staffid+","+sccid+","+coin+","+khd+","+flag+","+identifying+","+ccompanyId+","+isflag);//订单描述
            par.push("&WIDit_b_pay=''");//超时时间
            par.push("&WIDextern_token=''");//钱包
            window.location.href = encodeURI(par.join(""));
        }else if(zffs=='2'){
            document.forms[0].reset();
            $("#formsutm").find("#journalNum").val(jum);
            $("#formsutm").find("#baseUrl").val(basePath);
            $("#formsutm").find("#morre").val(morre);
            $("#formsutm").find("#staffid").val(staffid);
            $("#formsutm").find("#sccid").val(sccid);
            $("#formsutm").find("#khd").val(khd);
            $("#formsutm").find("#flag").val(flag);
            $("#formsutm").find("#identifying").val(identifying);
            $("#formsutm").find("#ccompanyId").val(ccompanyId);
            $("#formsutm").find("#isflag").val(isflag);
            $("#formsutm").attr("action",basePath + "/ea/jinbi/ea_zfgs.jspa");
            $("#submit").click();
        }else if(zffs=='3'){
            var goodsname = "(北京天太世统科技有限公司)微分金金币";
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {
                //    $(".loading").show();
                //app微信支付
                $.ajax({
                    url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa?dat="+new Date(),
                    type:"get",
                    data:{
                        "payDto.orderId":jum,
                        "payDto.totalFee":morre,
                        "payDto.body":goodsname,
                        "payDto.attach":"wfjjb",
                        staffid:staffid,
                        sccid:sccid
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
                        //   $(".loading").hide();
                        if(isAndroid==true){
                            Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,jum,err_code,goodsname,"wfjjb");
                        }else if(isiOS==true){
                            var url= "func=" + 'ioscallappChat';
                            params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':jum,'errcode':err_code,'goodsname':goodsname,'attach':'"wjfjb"'};
                            for(var i in params){
                                url = url + "&" + i + "=" + params[i];
                            }
                            window.webkit.messageHandlers.Native.postMessage(url);
                            alert("回到微分金");
                            window.location.reload();
                        }
                    },error:function (data) {
                        alert("生成预订单号失败")
                    }

                });

            }else{
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+jum+"-"+goodsname+"-"+morre+"-"+staffid+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

            }

        }else{
            if(token!=0){
                return;
            }
            token=1;
            $.ajax({
                url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
                type:"get",
                data:"fenlei=03&ddid="+$("#"+jum).find("#jnum").val(),
                success:function suc(data){
                    var mb=eval("("+data+")");
                    var succ=mb.succ;
                    var threeNo=mb.threeNo;
                    if(succ=="success"){
                        window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo;
                    };
                }
            });
        }
    }
}


/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num 数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}



