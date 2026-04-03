var scard = "";
$(function () {
    showPassword(cardNum);
    if(openid!=null&&openid!=""){
        if(wxbind=="lc"){
            $(".je").text("首次识别请扫描预约练车手机号办理的购物卡");
        }else{
            $(".je").text("首次识别请扫描您的购物卡");
        }
    }


     $(".mm-alert div input").click(function () {
          $(".scard").focus();
         $(".mm-alert").hide();
         if($(".ct").text()=="没有预约记录请先预约"){
             document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

         }
     });



    //扫描购物卡自动结算
    document.onkeydown = function(evt){//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
    if(stoken==0) {
         scard = $(".scard").val();

    }else{
        return false;
    }
    if(scard!="") {
        stoken  = 1;

        var ulp = basePath
            + "/ea/sm/sajax_ea_checkScard.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : true,
            dataType : "json",
            data:{
                scard:scard,
                totalMoney:totalMoney,
                journalNum:journalNum,
                openid:openid
            },
            success : function(data) {
                var member = eval('(' + data + ')');
                var result = member.result;
                sccId = member.sccId;
                paymentCode = member.paymentCode;

                if(result!="0") {
                    var ct = "";
                    if (result == "1") {
                        ct = "此卡无效可联系工作人员";
                        $(".scard").val("");
                        stoken  = "0";
                    }else if (result == "2") {
                        ct = "余额不足请选择其他支付方式";
                        $(".scard").val("");
                        stoken  = "0";
                    }else if (result == "3") {
                        ct = "购物卡已经绑定其他微信";
                        $(".scard").val("");
                        stoken  = "0";
                    }else if (result == "4") {
                        //可以绑定
                        if (wxbind == "lc") {
                            if (paymentCode != "" && paymentCode != null) {
                                $(".mmshow").show();
                            }
                        }else {
                            if(directUrl!=""&&directUrl!=null) {
                                var backurls = window.location.href;
                                document.location.href = directUrl + "&cardNum=" + scard + "&mode=scard&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&paymentCode=" + paymentCode + "&sccId=" + sccId + "&journalNum=" + journalNum + "&posNum=" + posNum + "&openid=" + openid + "&backurls=" + encodeURIComponent(backurls);


                            }else {
                                if (posNum != null && posNum != "" && fh == "2") {

                                    checkPayAddress(sccId);

                                } else {
                                    if (paymentCode != "" && paymentCode != null) {
                                        $(".mmshow").show();
                                    }


                                }
                            }
                        }
                    }

                    if(ct!="") {
                        $(".mm-alert .ct").text(ct);
                        $(".mm-alert").show();
                    }



                }else{
                    vipmoney = member.vipmoney;
                    gwCard();
                }


            },
            error : function(data) {
                console.log("查询支付结果失败");
            }
        });
    }
    return false;
      }
     }

    //修改地址
     $("#editBtn").click(function(){
         $("#t1").hide();
         $("#t2").show();
         $("#t2 .adtitle").text("请修改您的收货信息");

     });
    
    //确认收货地址
     $("#confirmBtn").click(function () {

         addressID =  $(this).parent().find(".addressID").text();
         $("#t1").hide();
         $(".alert_address_").hide();
         if(paymentCode!=""&&paymentCode!=null) {
             if(vipmoney!=null&&vipmoney!=""){
                 totalMoney = vipmoney;
                 $(".hyj").show();
                 $(".hyj .vipmoney").text(vipmoney);
                 $(".je").find(".orim").css("text-decoration","line-through");
             }
             $(".mmshow").show();
         }else{
              scardPayOrder(sccId);
             document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=购物卡支付&addressID="+addressID+"&posNum="+posNum+"&fhform="+fhform;

         }
     });
     //输入密码后确定
     $("#btnEnter").click(function(){

         var pw = $(".txtNum").val();
         if(pw==""){
             $(".mm-alert .ttip").text("操作提示");
             $(".mm-alert .ct").text("请输入密码");
             $(".mm-alert").show();

         }else if(pw.length!=6){
             $(".mm-alert .ttip").text("请重新输入密码");
             $(".mm-alert .ct").text("密码为6位");
             $(".mm-alert").show();
             $(".txtNum").val("");

         }else if(pw!=paymentCode){
             $(".mm-alert .ttip").text("请重新输入密码");
             $(".mm-alert .ct").text("交易密码错误");
             $(".mm-alert").show();
             $(".txtNum").val("");
         }else{
             if(openid!=null&&openid!=""){
                     //绑定刷脸微信
                     bindWx();


             }else {
                 scardPayOrder(sccId);
                 document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付&addressID=" + addressID + "&posNum=" + posNum+"&fhform="+fhform;
             }
         }


     });


 });
//购物卡支付
function scardPayOrder(sccId){
    if(sccId!="") {

        if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){

            totalMoney = remainMoney;
        }
        var ulp = basePath
            + "/ea/sm/sajax_ea_scardPayOrder.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : true,
            dataType : "json",
            data:{
                sccId:sccId,
                totalMoney:totalMoney,
                journalNum:journalNum,
                addressID:addressID,
                    fhform:fhform

            },
            success : function(data) {
                if(data != null && data != "" && data =="YZF"){
                    alert("该订单已支付，请查看详情");
                }
                console.log("购物卡支付成功");

            },
            error : function(data) {
                console.log("购物卡支付失败");
            }
        });
    }


}
 /**
  *
  * 获取用户地址
  * @param sccId
  */
 function checkPayAddress(sccId){
     if(sccId!="") {
         var ulp = basePath
             + "/ea/sm/sajax_ea_checkPayAddress.jspa";
         $.ajax({
             type : "GET",
             url : ulp,
             async : true,
             dataType : "json",
             data:{
                 sccId:sccId
             },
             success : function(data) {
                var m = eval("("+data+")");
                var staffaddress = m.staffaddress;
                $(".alert_address_").show();
                $("#addressform #sccId").val(sccId);
                if(staffaddress==null){
                       $("#t2").show();
                }else{
                    $("#t1").show();
                    $("#t1 .name").text(staffaddress.consignee);
                    $("#t1 .tel").text(staffaddress.phone);
                    $("#t1 .detail").text(staffaddress.area.replace(/,/g,'')+staffaddress.addressDetailed);
                    $("#t1 .addressID").text(staffaddress.addressID);

                    $("#t2 #addressID").val(staffaddress.addressID);
                    $("#t2 .name").val(staffaddress.consignee);
                    $("#t2 .tel").val(staffaddress.phone);
                    $("#t2 #city").val(staffaddress.area.replace(/,/g,'/'));
                    $("#t2 .det").val(staffaddress.addressDetailed);
                    $("#t2 #sccId").val(sccId);
                    $("#t2 #address").val(staffaddress.area);



                }

             },
             error : function(data) {
                 console.log("验证地址失败");
             }
         });
     }
 }

 function ajaxAllProvice(){
     var url = basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
     $.ajax({
         url : url,
         type : "post",
         async : false,
         dataType : "json",
         success:function(data){
             var member = eval("(" + data + ")");
             var cityMap=member.city;
             var array=[];
             $(cityMap.districtCity).each(function(){
                 array.push("<a data-level='0' data-id='"+$(this)[0]+"' data-name='"+$(this)[1]+"'>"+$(this)[1]+"</a>");
             });
             $("#_citys0").append(array.join(""));
         },
         error:function(){
            console.log("地址获取失败！");

         }
     });

 }

 //保存地址
 function addAddress(){
     $(".mm-alert .ttip").text("请完善您的收货信息");

     var phone=new RegExp("^1[3|4|5|7|8][0-9]\\d{8}$");
     if($("#addressform .verification").eq(0).val()=="") {
         $(".mm-alert .ct").text("收货人姓名不能为空");
         $(".mm-alert").show();
     }
     else if($("#addressform .verification").eq(1).val()=="") {
         $(".mm-alert .ct").text("手机号码不可为空");
         $(".mm-alert").show();
     }
     else if(!phone.test($(".verification").eq(1).val())) {
         $(".mm-alert .ct").text("手机号码格式不对");
         $(".mm-alert").show();
     }
     else if($("#addressform #hcity").val()==undefined&&$("#t2 #address").val()=="") {
         $(".mm-alert .ct").text("请选择省份城市区县");
         $(".mm-alert").show();
     }
     else if($("#addressform #hproper").val()==undefined&&$("#t2 #address").val()=="") {
         $(".mm-alert .ct").text("请选择省份城市区县");
         $(".mm-alert").show();
     }
     else if($("#addressform #harea").val()==undefined&&$("#t2 #address").val()=="") {
         $(".mm-alert .ct").text("请选择省份城市区县");
         $(".mm-alert").show();
     }
     else if($("#addressform .verification").eq(2).val()==""){
         $(".mm-alert .ct").text("详细地址不能为空");
         $(".mm-alert").show();
     }
     else{
         if($("#hcity").val()!=undefined) {
             $("#addressform #address").val($("#hcity").val() + "," + $("#hproper").val() + "," + $("#harea").val());
         }
         //保存修改地址
         $.ajax({
             url : basePath + "ea/sm/sajax_ea_addAddress.jspa",
             type : "post",
             async : false,
             data: $("#addressform").serialize(),
             dataType : "json",
             success: function(data){
                 var me = eval("("+data+")");
                   addressID = me.addressID;
                 $("#t2").hide();
                 $(".alert_address_").hide();
                 if(paymentCode!=""&&paymentCode!=null) {
                     if(vipmoney!=null&&vipmoney!=""){
                         totalMoney = vipmoney;
                         $(".hyj").show();
                         $(".hyj .vipmoney").text(vipmoney);
                         $(".je").find(".orim").css("text-decoration","line-through");
                     }
                     $(".mmshow").show();
                 }else{
                     scardPayOrder(sccId);
                     document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=购物卡支付&addressID="+addressID+"&posNum="+posNum+"&fhform="+fhform;

                 }
             },
             error:function(){
                 console.log("保存失败地址！");
             }
         });
     }

 }
 function showPassword(cardNum){
     if(cardNum!=null&&cardNum!="") {
         $(".scard").val(cardNum);
         if (paymentCode != "" && paymentCode != null) {
             if(vipmoney!=null&&vipmoney!=""){
                 totalMoney = vipmoney;
                 $(".hyj").show();
                 $(".hyj .vipmoney").text(vipmoney);
                 $(".je").find(".orim").css("text-decoration","line-through");
             }
             $(".mmshow").show();
         }
         else {
             scardPayOrder(sccId);
            document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付";

         }
     }

 }
 function closeKeyBoard(){
     try {
         Android.keyboardHide();
     }catch (e){

     }
 }
//绑定微信人脸
function bindWx(){

    var ulp = basePath
        + "/ea/sm/sajax_ea_bindCard.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            openid:openid,
            sccId:sccId,
            wxbind:wxbind,
            journalNum:journalNum,
             totalMoney:totalMoney
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var result = member.result;
            var ct = "";

            //进入
            if (wxbind == "lc") {//练车签到绑定
                var bifId = member.bifid;
                if (bifId != "") {
                    searchOrder(basePath + "/ea/restaurant/sajaxj_ea_scancode.jspa?scancode=04" + bifId);
                } else {
                    ct = "没有预约记录请先预约";

                    if (ct != "") {
                        $(".ttip").text("温馨提示");
                        $(".mm-alert .ct").text(ct);
                        $(".mm-alert").show();
                        try {
                            Android.speechOutputForAndroid(ct);
                        }catch (error){

                        }
                    }

                }
            }else{
                //购物绑定
                if(result=="2"){
                    ct = "余额不足请选择其他支付方式";
                    if (ct != "") {
                        $(".ttip").text("温馨提示");
                        $(".mm-alert .ct").text(ct);
                        $(".mm-alert").show();
                        try {
                            Android.speechOutputForAndroid(ct);
                        }catch (error){

                        }
                    }

                }else if(result=="0") {
                    //积分充足
                   var vipmoney = member.vipmoney;
                    if(vipmoney!=null&&vipmoney!=""){
                        totalMoney = vipmoney;
                    }
                    scardPayOrder(sccId);
                    document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付&addressID=" + addressID + "&posNum=" + posNum;
                }


            }


        },
        error:function(data){
          console.log("绑定人脸微信失败");
        }
    });

}
//练车签到签退
 function  searchOrder(code){
     var url = code;
     $.ajax({
         url : encodeURI(url),
         type : "get",
         data : {
             "pos":"pos",
         },
         dataType : "json",
         async : false,
         success : function(data) {
             var bl = data.bl;
             var os = data.os;
             var cbId = os[15];
             var xysccid = os[16];
             document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cbId+"&sccId="+xysccid+"&dp=1&sm=1";
         }
     })
 }

  //密码前验证
 function gwCard(){
     closeKeyBoard();
     $(".txtNum").val("");
     if(token==0) {
         token = 1;
         if(directUrl!=""&&directUrl!=null){
             var backurls = window.location.href;
             document.location.href = directUrl+"&cardNum="+scard+"&mode=scard&totalMoney="+totalMoney+"&totalNum="+totalNum+"&paymentCode="+paymentCode+"&sccId="+sccId+"&journalNum="+journalNum+"&posNum="+posNum+"&vipmoney="+vipmoney+"&backurls="+encodeURIComponent(backurls);


         }else {

             if (posNum != null && posNum != ""&&fh=="2") {

                 checkPayAddress(sccId);


             } else {
                 if (paymentCode != "" && paymentCode != null) {

                     if(vipmoney!=null&&vipmoney!=""){
                         totalMoney = vipmoney;
                         $(".hyj").show();
                         $(".hyj .vipmoney").text(vipmoney);
                         $(".je").find(".orim").css("text-decoration","line-through");
                     }
                     $(".mmshow").show();
                 } else {
                     scardPayOrder(sccId);
                     document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付&posNum="+posNum+"&fhform="+fhform;

                 }

             }
         }

     }
 }
