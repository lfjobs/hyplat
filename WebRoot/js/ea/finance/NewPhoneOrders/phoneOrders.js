/**
 * 手机订单列表-买家
 */
var isAndroid;
var isiOS;
var date;
var gg="";
$(document).ready(function(){

    var u = navigator.userAgent;
    isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    loaded();

    $(".header ul li").css("line-height",$(window).height()*0.08+"px");
    $(".header").css("height",$(window).height()*0.08+"px");
    $(".content_hidden").css("height",$(window).height()*0.92-$(".header").height()+"px");
    $(".content").css("height",$(window).height()*0.92-$(".header").height()+"px");

    $(".wfj12_014_hidden_buy").css({"bottom":"0"})
    $(".wfj12_014_hidden_buy").find("td").attr("style"," height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.025+"px; color:#660000;");
    $(".wfj12_014_buy_width").find("td").attr("style"," height:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;");
    $(".wfj12_014_buy_width").find("td").eq(0).css("height",$(window).height()*0.06+"px")
    $(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
    $(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
    $("#money").attr("style","color:#F74C31;");
    $("#money").css("color","#F74C31");
    $("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.023+"px;padding-left:2px;");

    //选项卡
    $(".header .rec_head li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        $(".rec_eva").empty();
        pagenumber=0;
    });

    $(".all_order").click(function(){
        $("#all_order").css("display","block").siblings().css("display","none");
        pl="";
        loaded();
    });
    $(".obligation").click(function(){
        $("#obligation").css("display","block").siblings().css("display","none");
        pl="01";
        loaded();
    });
    $(".overhang").click(function(){
        $("#overhang").css("display","block").siblings().css("display","none");
        pl="00";
        loaded();
    });
    $(".pra").click(function(){
        $("#pra").css("display","block").siblings().css("display","none");
        pl="02";
        loaded();
    });

    //删除订单弹窗
    $(document).on("click",".shanchu",function(){
        cbid=$(this).parent().parent().find("input[class='cbid']").val();
        $("#shanchu").show();
        $(".alert_ .alert").show();
    });
    $("#isDel").click(function(){
        if(pl=="01"){
            pl="";
        }
        document.location.href=basePath+"/ea/pobuy/ea_delBill.jspa?cbid="+cbid+"&staid="+staid+"&pl="+pl+"&sccId="+sccId;
    });


    $(".alert_").click(function(){
        $(".alert_ .alert").hide();
        $(".alert_").hide();
        $(".pays").hide();
    });
    $(".alert123").click(function(){
        $(".alert123").hide();
        $(".wfj12_010_jqm").hide();
        $("#jqModeladd").hide();
    });
    //取消订单
    $(".quxiao").click(function(){
        $("#quxiao").show();
    });
    $(".alert p").click(function(){
        $("#quxiao").hide();
        $("#shanchu").hide();
    });
    //选择支付方式
    $(".wfj12_014_choice").click(function(){
        if($(this).find("span").text()!="(您金币为0,无法选择)"&& $(this).find("span").text()!="(您的积分不足，无法选择)"&& $(this).find("span").text()!="(您的金币不足，无法选择)"&& $(this).find("span").text()!="(您积分为0,无法选择)"&& $(this).find("span").text()!="(您的金币已冻结，无法选择)") {
            $(".wfj12_014_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
            $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
            zffs = $(this).find(".second").find("img").attr("name");
        }
    });
//    	$(document).on("click",".pay",function(){
//    		$(".pays").show();
//    		/*弹出蒙板*/
//    		$(".alert_").show();
//    		$(".alert_ .alert").hide();
//		});
    //返回
    $("#back").click(function(){
        $("#jqModeladd").hide();
        $(".alert123").hide();
    });
    //延迟收货加，减号
    $("#jia").click(function(){
        var zhi=parseInt($("#zhi").val())+1;
        $("#zhi").val(zhi>=3?3:zhi);
    });
    $("#jian").click(function(){
        var zhi=parseInt($("#zhi").val())-1;
        $("#zhi").val(zhi<=1?1:zhi);
    });
    // 延迟收货取消
    $("#jqmOverlay").click(function(){
        $(".wfj12_010_jqm").hide();
        $(".alert123").hide();
    });
    //确定延迟收货
    $("#qr").click(function(){
        $.ajax({
            url:basePath+"/ea/hypb/sajax_extendedReceipt.jspa?cashierbillsID="+cashierID+"&zhi="+$("#zhi").val(),
            type:"post",
            success:function(data){
                alert("操作成功！");
                $(".wfj12_010_jqm").fadeOut(1000);
                $(".alert123").hide();
            },error:function(data){
                alert("数据获取失败");
            }
        });
    });


    /*搜索*/
    $(".header ul li:nth-child(3) img").click(function(){
        $(".alert123").show();
        $(".alert_search").show();
    });
    $(".alert_search input:nth-child(1)").keyup(function () {
        var t = $(".alert_search input:nth-child(1)");
        if (t.val() == "") {
            $(".alert_search .top #qx").show();
            $(".alert_search .top #ss").hide();
        }
        else{
            $(".alert_search .top #ss").show();
            $(".alert_search .top #qx").hide();
        }
    });

    $("#qx").click(function(){
        $(".alert123").hide();
        $(".alert_search").hide();
    })
    $("#ss").click(function(){
        pagenumber=0;
        search=$(".sousuo").val();
        $(".rec_eva").empty();
        if(search==''){
            alert("请输入订单号或产品名称");
        }else{
            ajax($.trim(search));
            $(".alert123").hide();
            $(".alert_search").hide();
        }
    });

})//加载完毕
function getHeight(){
    t=setTimeout("getHeight()",200);
    if($(".last").length>0){
        if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
}
function loaded(){
    ajax($.trim(search));
}
function ajax(s){
    clearTimeout(t);
    pagenumber++;
    var url=basePath+"ea/pobuy/sajax_getAjax.jspa?";
    $.ajax({
        url : url,
        type: "get",
        async:true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
            "staid":staid,
            "pl": pl,
            "search":s,
            "sccId":sccId,
            "etype":etype
        },
        success : function cbf(data){
            var member=eval("("+data+")");
            var pageForm=member.pageForm;
            var str=new Array();
            if(pageForm!=null&&pageForm.recordCount>0){
                pagenumber=pageForm.pageNumber;
                pagecount=pageForm.pageCount;
                var cashlist=pageForm.list;
                $(".last").removeClass("last");
                var tradeCode="";
                for(var i=0;i<cashlist.length;i++){
                    var cash=cashlist[i];
                    if(i==cashlist.length-1){
                        str.push("<div class='grd last' id='"+cash.cashierBillsID+"'>");
                    }else{
                        str.push("<div class='grd' id='"+cash.cashierBillsID+"'>");
                    }
                    str.push("<input id='jnum' type='hidden' value='"+cash.journalNum+"'>");


                    str.push("<ul class='com_name'>");
                    str.push("<li><div class='head'><img src='"+basePath+(cash.companyLogo!=null&&cash.companyLogo!=""?cash.companyLogo:"images/WFJClient/PersonalJoining/logo@2x.png")+"'></div></li>");
                    str.push("<li class='companyName'>"+cash.companyName+"<a href='#;'><img src='"+basePath+"images/ea/finance/NewPhoneOrders/rigth.png' alt=''></a></li>");
                    if(cash.fkStatus=='00'||cash.fkStatus=='09'){
                        str.push("<li><span>待发货</span></li></ul>");
                    }else if(cash.fkStatus=='01'){
                        str.push("<li><span>待付款</span></li></ul>");
                    }else if(cash.fkStatus=='02'){
                        str.push("<li><span>待收货</span></li></ul>");
                    }else if(cash.fkStatus=='05'||cash.fkStatus=='06'||cash.fkStatus=='07'||cash.fkStatus=='08'||cash.fkStatus=='16'){
                        str.push("<li><span>退货退款中</span></li></ul>");
                    }else {
                        str.push("<li><span>交易完成</span></li></ul>");
                    }

                    //填充商品
                    var goodslist=cash.goodsList;
                    var ptgoodslist=cash.ptgoodsList;
                    for (var j = 0; j < goodslist.length; j++) {
                        var goodbill = goodslist[j];
                        tradeCode = goodbill[13];
                        date = goodbill[14];
                        str.push("<div class='mil'><a href='javascript:;' onclick='xq(\"" + goodbill[10] + "\",\"" + cash.fkStatus + "\",\"" + goodbill[15] + "\")'>");
                        str.push("<div class='left'>");
                        if (goodbill[16] != null && goodbill[16] != "" && goodbill[16] == "1") {//批发订单
                        } else if (goodbill[16] != null && goodbill[16] != "" && goodbill[16] == "2") {//VIP订单
                            str.push("<span class='vip'><i></i></span>");
                        } else if (goodbill[16] != null && goodbill[16] != "" && goodbill[16] == "3") {//普通活动订单
                            str.push("<span class='cx'><i></i></span>");
                        } else if (goodbill[16] != null && goodbill[16] != "" && goodbill[16] == "4") {//特价活动订单
                            str.push("<span class='tj'><i></i></span>");
                        } else {//零售订单
                        }
                        str.push("<img src='" + basePath + goodbill[6] + "' alt=''>");
                        str.push("</div>");
                        str.push("<input id='ppid' type='hidden' value='" + goodbill[7] + "'/>");
                        str.push("<div class='right'>");
                        str.push("<h3>" + goodbill[5] + "</h3>");
                        str.push("<h3>产品规格：" + (goodbill[11] == null ? "" : goodbill[11]) + "</span></h3></div>");
                        str.push("<div class='right right2'>");
                        str.push("<h3>&yen;<span>" + goodbill[3] + "</span></h3>");
                        str.push("<p>x" + goodbill[2] + "</p></div></div></a>")

                    }
                    //填充促销品
                    if(ptgoodslist!=null&&ptgoodslist.length>0){
                        for(var x=0;x<ptgoodslist.length;x++){
                            var ptgb=ptgoodslist[x];
                            str.push("<div class='mil'><a href='#'>");
                            str.push("<div class='left'><img src='"+basePath+ptgb[6]+"' alt=''></div>");
                            str.push("<div class='right'>");
                            str.push("<h3>"+ptgb[2]+"</h3>");
                            str.push("<h3>产品规格："+(ptgb[5]==null?"":ptgb[5])+"</span></h3></div>");
                            str.push("<div class='right right2'>");
                            str.push("<p>x"+ptgb[7]+"</p></div></a><img src='"+basePath+"images/ea/finance/NewPhoneOrders/ico-cu.png' class='cu'></div>")
                        }
                    }
                    str.push("<ul class='com_total'>");
                    if(isAndroid==true){
                        str.push("<li><p>订单号：<span>"+cash.journalNum+"</span>&nbsp;&nbsp;<input class='copy_btn' type='button' value='复制' onclick='Androidcopy(this)'/></p></li>");
                    }else if(isiOS==true){
                        str.push("<li><p>订单号：<span>"+cash.journalNum+"</span>&nbsp;&nbsp;<input class='copy_btn' type='button' value='复制' onclick='IOSCopy(this)'/></p></li>");
                    }
                    str.push("<li><p>共计<span>"+cash.goodsList.length+"</span>件商品 合计：&yen;<span>"+cash.priceSub+"</span>（含运费&yen;<span>0.00</span>）</p></li></ul>");
                    str.push("<div class='com_btn com_btn2' align='right'>");
                    str.push("<input type='hidden' class='cbid' value='"+cash.cashierBillsID+"'/>");
                    str.push("<input type='hidden' id='status' value='"+cash.fkStatus+"' />");
                    str.push("<input type='hidden' id='status4' value='"+cash.wfStatus4+"'/>");
                    str.push("<input type='hidden' id='journalNum' value='"+cash.journalNum+"'/>");
                    str.push("<input type='hidden' id='priceSub' value='"+cash.priceSub+"'/>");
                    str.push("<input id='projectname' type='hidden' value='"+cash.projectName+"'/>");
                    str.push("<input id='goodsName' type='hidden' value='"+cash.goodsName+"'>");
                    str.push("<input id='goodsCoding' type='hidden' value='"+cash.goodsCoding+"'>");
                    if (cash.fkStatus !='04')
                    {
                        str.push("<a href='#;'><input type='hidden' value='" + cash.sellSccid + "'>");
                        str.push("<input type='hidden' value='" +cash.sellAccount + "'>");
                        str.push("<input type='hidden' value='" +cash.sellStaffName + "'>");
                        if(isAndroid==true){
                            str.push("<input type='button' value='联系商家' onclick='AndroidtoChat(this)'></a>")
                        }else if(isiOS==true){
                            str.push("<input type='button' value='联系商家' onclick='ioschat(this)'></a>")
                        }
                    }
                    if(tradeCode!=null&&tradeCode=='z30001汽车驾校'){
                        str.push("<a href='#;'><input type='button' id='update"+cash.journalNum+"' value='修改学员' onclick='update(\""+cash.journalNum +"\")'></a>");
                    }
                    if(cash.fkStatus=='00'){
                        if (cash.priceSub!='0')
                        {
                            str.push("<a href='#;'><input type='button' value='申请退款' onclick='refund(\""+cash.cashierBillsID +"\")'></a>");
                        }
                        str.push("<a href='#;'><input type='button' id='cpq' value='提醒发货' onclick='Reminder(this)'></a>");
                    }else if(cash.fkStatus=='11'||cash.fkStatus=='10'||cash.fkStatus=='12'||cash.fkStatus=='13'){
                        str.push("<a href='#;'><input type='button' value='售后详情' ></a>");
                    }else if(cash.fkStatus=='05'||cash.fkStatus=='06'||cash.fkStatus=='07'||cash.fkStatus=='08'){
                        str.push("<a href='#;'><input type='button' value='退货详情' onclick='ref(\""+cash.cashierBillsID +"\")'></a>");
                    }else if(cash.fkStatus=='16'||cash.fkStatus=='17'||cash.fkStatus=='18'){
                        str.push("<a href='#;'><input type='button' value='退款详情' onclick='detail(\""+cash.cashierBillsID +"\")'/></a>");
                    }else if(cash.fkStatus=='03'){
                        if(date!=null){
                            //收货七天不可以在申请退货了
                            var curDate = new Date();
                            curDate.setDate(curDate.getDate()-7);
                            var time = curDate.toLocaleDateString();

                            var date1 = new Date(date.replace(/\-/g, "\/"));//获取收货时间
                            var date2 = new Date(time.replace(/\-/g, "\/"));//获取当前时间-7天   的时间
                            if(date1!=''&&date2!=''&&date1>date2){
                                str.push("<a href='#;'><input type='button' value='申请退货' onclick='refback(\""+cash.cashierBillsID+"\")'></a></div></div>");
                            }
                            //     str.push("<a href='#;'><input type='button' value='申请售后'></a></div></div>");
                        }

                    }else if(cash.fkStatus=='01'){
                        if(cash.wfStatus4=='03'){
                            str.push("<a href='#;'><input type='button' id='"+cash.journalNum+ "' value='转账确认' onclick='zzqr(\""+cash.journalNum+"\")'></a>");
                        }else{
                            /*str.push("<a href='#;'><input type='button' value='删除订单' class='shanchu'></a>"); 20180224隐藏*/
                            str.push("<a href='#;'><input type='hidden' value='"+tradeCode +"'><input class='pay' type='button' value='付款' onclick='ljzf(this)'></a>");
                        }
                    }else if(cash.fkStatus=='02'){
                        str.push("<a href='#;'><input type='button' value='申请退货' onclick='refback(\""+cash.cashierBillsID+"\")'></a>");
                        str.push("<a href='#;'><input type='button' value='延长收货' onclick='ycsh(\""+cash.cashierBillsID+"\")'></a>");
                        str.push("<a href='#;'><input type='button' value='查看物流' onclick='wuliu(\""+cash.cashierBillsID+"\")'></a>");
                        str.push("<a href='#;'><input type='button' value='确认收货' onclick='wfj_sh(\""+cash.cashierBillsID+"\",\""+cash.wfStatus4+"\",\""+cash.journalNum+"\",\""+cash.companyid+"\")'></a>");
                    }
                    str.push("</div></div>");
                }
            }else{
                //<!--无货显示-->
                str.push("<div class='no'>");
                str.push("<img src='"+basePath+"images/ea/finance/NewPhoneOrders/wu.png' alt='' class='wu'>");
                str.push("<p>目前还没有订单哦～</p></div>");
            }
            if(pl==null||pl==""){
                $("#all_order").append(str.join(""));
            }else if(pl=='01'){
                $("#obligation").append(str.join(""));
            }else if(pl=='00'){
                $("#overhang").append(str.join(""));
            }else if(pl=='02'){
                $("#pra").append(str.join(""));
            }
            getHeight();
        }
    });
}
//立即支付的方法
function ljzf(el){
    var num = $(el).parent().parent().find("#journalNum").val();
    if($(el).siblings().val()!=""&&$(el).siblings().val()=='z30001汽车驾校')
    {
        if(confirm("是否要修改学员的信息？")){

            var price = $(el).parent().parent().find("#priceSub").val();
            update(num,price); //调   修改学员    界面
        }
    }
    else
    {



        var mo=$(el).parent().parent().find("#priceSub").val();
        companyName=$(el).parents(".grd").find(".com_name").find(".companyName").text();
        var ret  =checkGoldInte(mo);
        if(ret){
            $("#money").text("￥"+mo);
            $(".pays").show();
            /*弹出蒙板*/
            $(".alert_").show();
            $(".alert_ .alert").hide();
            var $tb=$(el).parent().parent().parent();
            jum=$tb.attr("id");
            morre=mo;
            goodsName=$(el).parent().parent().find("#projectname").val();
            goodsName="("+companyName+")"+goodsName;
            $("#jqModeladd").hide();


            var gd = $(el).parent().parent().find("#goodsName").val();
            var ge = $(el).parent().parent().find("#goodsCoding").val();
            if(gd=="智能货柜"&&ge!=""&&ge!=null){

                getUnPayMoney(num);
                $(".gd").hide();
                gg = gd;
            }else{
                gg = "";
            }

        }else {
            alert("积分查询错误")
        }
    }
}
function getUnPayMoney(num){

    $.ajax({
        url:basePath+"/ea/pobuy/sajax_getUnPayMoney.jspa",
        type:"get",
        async:false,
        dataType:"json",
        data:{
            journalNum:num
        },
        success:function(data){
            var member=eval("("+data+")");
            var unpay = member.unPayRecord;
            if(unpay!=null&&unpay!=""){
                var remainNum = unpay.remainNum;
                var totalMoney = unpay.totalMoney;
                morre = remainNum;
                $(".gd").hide();
                $(".pays #money").text("待支付：￥"+remainNum);
                $(".xzf").text("已抵扣：￥"+(Number(totalMoney)-Number(remainNum)).toFixed(2)+" ");

            }
        },
        error:function(data){
            console.log("错误")
        }
    });
}
//订单详情
function xq(cashid,fk,type){
    //退货
    if(fk=='05'||fk=='06'||fk=='07'||fk=='08'||fk=='16'||fk=='17'||fk=='18'){
        window.location.href=basePath+"/ea/pobuy/ea_getCashBill.jspa?cbid="+cashid+"&refund=1";
    }else{
        if(type.indexOf("驾校预约")>-1){
            document.location.href=basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cashid+"&sccId="+sccId;
        }else{
            document.location.href=basePath+"/ea/pobuy/ea_getCashBill.jspa?cbid="+cashid+"&sccId="+sccId;
        }
    }
}

function zf(){
    if(goodsName!=""){
        goodsName=goodsName.substring(0,goodsName.length-1);
        if(goodsName.length>10){
            goodsName=goodsName.substr(0,10)+"...";
        }
    }

    if(zffs==null){
        alert("请选择支付方式");
        return false;
    }else{
        if(zffs=='1'){
            var par=new Array();
            par.push(basePath);
            par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
            par.push("WIDout_trade_no="+$("#"+jum).find("#jnum").val());
            par.push("&WIDtotal_fee="+morre);
            par.push("&WIDsubject="+goodsName);
            if(gg=="智能货柜"){
                par.push("&WIDbody='selfpay'");//订单描述
            }else{
                par.push("&WIDbody=" + flag +"," + sccId + ","+ staid);// 订单描述
            }

            par.push("&WIDit_b_pay=''");// 超时时间
            par.push("&WIDextern_token=''");// 钱包
            _AP.pay(encodeURI(par.join("")));
        }else if(zffs=='2'){
            document.forms[0].reset();
            $("#formsutm").find("#ddid").val($("#"+jum).find("#jnum").val());
            $("#formsutm").find("#baseUrl").val(basePath);
            $("#formsutm").find("#morre").val(morre);
            $("#formsutm").attr("action",basePath + "ea/wfjshop/ea_zfgs.jspa");
            $("#submit").click();

            /*
             * alert("银联支付暂无法使用"); return false;
             */
        }else if(zffs=='3'){
            var ddid=$("#"+jum).find("#jnum").val();
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;

            if (!isWeixin) {
                $(".loading").show();
                //调用ios/android
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                var elkc = "";
                try {
                    if(isAndroid==true){
                        Android.isElKCApp();
                    }else if(isiOS==true){
                        var url= "func=" + 'isElKCApp';
                        window.webkit.messageHandlers.Native.postMessage(url);
                    }
                    elkc = "elkc";
                }catch(e){
                    elkc = "";
                }
                var attach = "other";
                if(gg=="智能货柜"){
                    attach = "selfpay";
                }
                //app微信支付
                $.ajax({
                    url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa",
                    type:"get",
                    data:{
                        "payDto.orderId":ddid,
                        "payDto.totalFee":morre,
                        "payDto.body":goodsName,
                        "payDto.attach":attach,
                         elkc:elkc,
                        isiOS:isiOS
                    },
                    success:function suc(data){
                        var mb=eval("("+data+")");
                        var appPackage=mb.appPackage;

                        var  appid = appPackage.appid
                        var partnerid = appPackage.partnerid;
                        var prepayid = appPackage.prepayid;
                        var packages = appPackage.packages;
                        var noncestr = appPackage.noncestr;
                        var timestamp = appPackage.timestamp;
                        var err_code = appPackage.err_code;
                        var sign = appPackage.sign;

                        $(".loading").hide();
                        if(isAndroid==true){
                            Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,ddid,err_code,goodsName,attach);
                        }else if(isiOS==true){
                            var url= "func=" + 'ioscallappChat';
                            params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':ddid,'errcode':err_code,'goodsname':goodsName,'attach':attach};
                            for(var i in params){
                                url = url + "&" + i + "=" + params[i];
                            }
                            window.webkit.messageHandlers.Native.postMessage(url);
                        }
                    }

                });

            }else{
                window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsname+"-"+money+"-"+staffID+"-1-1-"+attach+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

            }



        }else if(zffs=='5'){
            // 钱盒子支付
            document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+$("#"+jum).find("#jnum").val()+"&morre="+morre;
        }else if(zffs=='6'){
            // $.ajax({
            //     url:basePath+"ea/wfjshop/sajax_ea_panduanJifen.jspa",
            //     type:"get",
            //     data:{
            //         fenlei:"4",
            //         "ddid":$("#"+jum).find("#jnum").val()
            //     },
            //     success:function suc(data){
            //         var mb=eval("("+data+")");
            //         var panduan=mb.panduan;
            //         if(panduan=="meiyou"){
            //             alert("亲，您还没有积分呢");
            //         }else if(panduan=="bugou"){
            //             alert("您积分不足");
            //         }else if(panduan=="shibai"){
            //             alert("该支付方式暂时不能使用，请使用其他支付方式");
            //         }else if(panduan =="cg"){
            //             document.location.href = basePath+"page/WFJClient/suc/fansOk.jsp?ddid="+$("#"+jum).find("#jnum").val();
            //         }else {
            //             alert("该支付方式暂时不能使用，请使用其他支付方式");
            //         }
            //     }
            // });



            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(morre);
            return;
          //  buy($("#"+jum).find("#jnum").val(),companyName,"05")
        }else if(zffs=='7'){

            $(".overlay").addClass("active");
            $(".popup_pay").show();
            $(".pay_inp").focus();
            $(".sum_num").text(morre);
            return;
           // buy($("#"+jum).find("#jnum").val(),companyName,"07")
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
function zzqr(jnum){
    $("input#"+jnum).attr("disabled","disabled");
    $.ajax({
        url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
        type:"get",
        data:"ddid="+jnum,
        success:function suc(data){
            var member=eval("("+data+")");
            var suc=member.succ;
            if(suc=="success"){
                $("input#"+jnum).attr("style","display:none");
                alert("确认成功");
                window.location.reload();
            }
        }
    });
}

//退款(退货退款)
function refund(cashId){

    window.location.href=basePath+"ea/refundMoney/ea_ref.jspa?cashId="+cashId+"&tp=00";//仅退款
}

//退款详情
function detail(cashid){
    window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=00";
}

//退货详情
function ref(cashid){
    window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=01";
}


function refback(cashid){
    window.location.href=basePath+"ea/refundMoney/ea_ref.jspa?cashId="+cashid+"&tp=01";
}




function Reminder(obj){
//var ddid = $(obj).parents(".com_btn").find("#jnum").val();
    var casid = $(obj).parents().find(".cbid").val();
    $.ajax({
        url:basePath+"ea/wfjshop/sajax_ea_ReminderDelivery.jspa?cashierBillsID="+casid,
        type:"get",
        async : false,
        dataType : "json",
        success:function suc(data){
            var member=eval("("+data+")");
            var suc=member.txin;
            alert(suc);
            window.location.reload();
        }
    });
    $("#cpq").css("display","none");
}
function ycsh(obj){
    $.ajax({
        url:basePath+"/ea/hypb/sajax_QueryWhetherToExtendTheReceipt.jspa?cashierbillsID="+obj,
        type:"post",
        success:function(data){
            var member=eval("("+data+")");
            var sst=member.status;
            if(sst=="00"){
                var time=member.time;
                cashierID=obj;
                $(".alert123").show();
                $(".wfj12_010_jqm").fadeIn(1000);
                $(".wfj12_010_jqm").find("#estimatedTime").text("预计将在"+time+"收货哦");
            }else{
                alert("收货时间已延长")
            }
        },error:function(data){
            alert("数据获取失败");
        }
    });
}

function wfj_sh(cid,wfStatus4,journalNum,companyID){
    if(confirm("确定收货？")){
        $.ajax({
            url: basePath+"/ea/pobuy/sajax_userConfirmationReceipt.jspa?",
            type:"get",
            async: false,
            data:{
                "staid":staid,
                "id":cid,
                wfStatus4:wfStatus4,
                journalNum:journalNum,
                companyID:companyID
            },
            success: function (data){
                var member=eval("("+data+")");
                if(member.s=='1'){
                    alert("确认收货成功！");
                    window.location.href=basePath+"/ea/pobuy/ea_getReceiptList.jspa?user="+member.user+"&state=00";
                }else if(member.s=='2'){
                    alert("请先把主产品收货后再试！");
                }else if(member.s=='3'){
                    alert("确认收货成功,账款结清才可获取金币！");
                    window.location.href=basePath+"/ea/pobuy/ea_getReceiptList.jspa?user="+member.user+"&state=00";
                }else {
                    alert("收货失败，稍后再试！");
                }
            }
        });
    }
};
//显示修改学员信息界面
function update(num,price){
    $("#nu").text(num);
    $("#prices").val($("input#update"+num).parent().parent().find("#priceSub").val());
    status = $("input#update"+num).parent().parent().find("#status").val();
    status4 = $("input#update"+num).parent().parent().find("#status4").val();
    var url=basePath+"/ea/bdbill/sajax_getInformation.jspa?jo="+num;
    $.ajax({
        url:url,
        type:"get",
        async:false,
        dataType:"json",
        success:function(data){
            var member = eval("(" + data + ")");
            var UserName = member.UserName;
            var Reference = member.Reference;
            var ReferenceCode = member.ReferenceCode;
            var ReferrerAddress = member.ReferrerAddress;

            $("#noviceName").val(UserName);
            $("#novicePhone").val(Reference);
            $("#noviceCode").val(ReferenceCode);
            $("#noviceAddress").val(ReferrerAddress);
        }
    });
    $(".alert123").show();
    $("#jqModeladd").show();

};
//保存修改后学员信息
function tosave(){
    var num = $("#nu").text();
    var noviceName = $("#noviceName").val();
    var novicePhone = $("#novicePhone").val();
    var noviceCode = $("#noviceCode").val();
    var noviceAddress = $("#noviceAddress").val();

    var url = basePath +"ea/bdbill/sajax_updateNovice.jspa?jo="+num+"&noviceName="+noviceName+"&novicePhone="+novicePhone+"&noviceCode="+noviceCode+"&noviceAddress="+noviceAddress;
    $.ajax({

        url : url,
        type: "get",
        dataType:"json",
        success:function(data){
            var member = eval("("+data+")")
            var type = member.type;
            if(type == 1){
                alert("修改成功！");
                $("#jqModeladd").hide();
                $(".alert123").hide();
            }
        }
    });
    //调出支付页面


    if(status=="01" && status4=="03" ){
        $("#jqModeladd").hide();
        $(".alert123").hide();
    }else if(status !="01"){
        $("#jqModeladd").hide();
        $(".alert123").hide();
    }else{
        var mo=$("#prices").val();
        $("#money").text("￥"+mo);
        $(".pays").show();
        /*弹出蒙板*/
        $(".alert_").show();
        $(".alert_ .alert").hide();
        var $tb=$(el).parent().parent().parent();
        jum=$tb.attr("id");
        morre=mo;
        goodsName=$(el).parent().parent().find("#projectname").val();
        $("#jqModeladd").hide();
    }
};
//查看物流
function wuliu(id){
    window.location.href=basePath+"ea/pobuy/ea_toGetWuliu.jspa?cbid="+id;
    // window.location.href=basePath+"/page/ea/main/finance/NewPhoneOrders/RefundReimburse/viewLogistics.jsp?cbid="+id;
}
//复制
function IOSCopy(obj){
    var content=$(obj).prev('span').text();
    var url= "func=" + 'iosstick';
    params={'content':content};
    for(var i in params){
        url = url + "&" + i + "=" + params[i];
    }
    alert("复制成功");
    window.webkit.messageHandlers.Native.postMessage(url);
}
function Androidcopy(obj){
    var content=$(obj).prev('span').text();
    Android.callcopy(content);
}
//联系商家
function ioschat(obj){
    var url= "func=" + 'ioschat';
    params={'sccid':$(obj).siblings().eq(0).val(),
        'account':$(obj).siblings().eq(1).val(),
        'username':$(obj).siblings().eq(2).val()};
    for(var i in params){
        url = url + "&" + i + "=" + params[i];
    }
    window.webkit.messageHandlers.Native.postMessage(url);

}
function AndroidtoChat(obj){
    var userId= $(obj).siblings().eq(1).val();
    var sccId= $(obj).siblings().eq(0).val();
    var userPickeName= $(obj).siblings().eq(2).val();

    Android.toChat(userId,sccId,userPickeName);
}
function checkGoldInte(price) {
    var isOk;
    $.ajax({
        type: "GET",
        url: basePath + "/ea/jinbi/sajax_checkGoldInte.jspa?morre="+price,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("("+data+")");
            var integral  = member.integral;
            var gold = member.gold;
            var status = member.status;
            if (data == null) {
                alert("数据提交失败。请重试");
            } else {
                if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
                    $(".integral_ span").text("(您的积分不足，无法选择)").show();
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (integral == "" || isNaN(integral) || integral == null) {
                    $(".integral_ span").text("(您积分为0,无法选择)");
                    $(".integral_ .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(integral) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".integral_ span").text("(您的积分不足，无法选择)");
                    $("integral_").attr("style","pointer-events: none")
                    $(".integral_ .second").hide()
                }
                if(status!=null && status!=""){
                    $(".gold span").text("(您的金币已冻结，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) < 0) {
                    $(".gold span").text("(您的金币不足，无法选择)").show();
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (gold == "" || isNaN(gold) || gold == null) {
                    $(".gold span").text("(您金币为0,无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                } else if (parseFloat(gold) - accMul(parseFloat(price) , parseFloat("100")) >= 0) {
                    //goumaitype = "jifen";
                } else {
                    $(".gold span").text("(您的金币不足，无法选择)");
                    $(".gold .second").hide()
                    $("integral_").attr("style","pointer-events: none")
                }

                isOk = true;
            }
        },
        error: function (data) {
            isOk = false
        }
    });
    return isOk;
}



function buy(ddid,companyName,buyPatter) {
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_diaoyongJifen.jspa?buyPatter="+buyPatter+"&ddid="+ddid;
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        beforeSend : function() {
            $(".loading").show();
        },
        success : function(data) {
            $(".loading").hide();
            var json = eval('(' + data + ')');
            var ss=json.chenggong;
            if(ss == "cg"){
                // if (buyIsOkPage=="buyIsOkPage"){
                //     document.location.href = basePath+"st/buyIsOkPage.jsp?ddid="+ddid+"&companyName="+companyName+"&staffName="+staffName+"&companyIdentifier="+companyIdentifier;
                // }else {
                    document.location.replace(basePath + "page/WFJClient/suc/fansOk.jsp?ddid=" + ddid + "&companyName=" + companyName);
                // }
            }else  if(ss=="shibai"){
                alert("您的帐号有问题，请联系工作人员");
                bbb=true;
            }
        },
        error : function(data) {
            alert("提交订单失败");
            bbb=true;
        }
    });
}

function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function payInput(obj){
    var len = $(obj).val().length - 1;
    if (len >= 0) {
        $(".pay_label li").eq(len).addClass("pass").nextAll().removeClass();
        if (len == 5) {
            //    setTimeout("initPay()", 400);//设置延时（验证密码方法放这位置）
            $(".pay_inp").blur();
            var code = $(".pay_inp").val(); //获取密码
            //查找用户的支付密码
            var url = basePath + "/ea/jinbi/sajax_toSearchPaymentCode.jspa?paymentCode="+code+"&user="+user;
            $.ajax({
                url : url,
                type : "get",
                async : false,
                dataType : "json",
                success : function (data) {
                    var member = eval("(" + data + ")");
                    var paymentCode=member.code;
                    if(paymentCode == '00'){//支付密码正确
                        if(zffs == "6"){
                            //金币支付
                            buy($("#"+jum).find("#jnum").val(),companyName,"07")


                            //积分支付
                        }else if(zffs == "7"){

                            buy($("#"+jum).find("#jnum").val(),companyName,"05")
                        }
                    }else if(paymentCode == '01'){//支付密码不正确
                        prompt("支付密码不正确");
                    }else{//没有支付密码
                        prompt("没有支付密码请到");
                    }
                }
            });
        }
    } else {
        $(".pay_label li").removeClass();
    }

}