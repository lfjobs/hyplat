$(function() {
    if(zf!=null&&zf=="1"){
        scardPayOrder(sccId);
        document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付&addressID=" + addressID + "&posNum=" + posNum;

    }

    if (posNum != null && posNum != ""&&address==""&&cardNum=="") {
        if(fh=="2"&&wxbind==""){
            $(".alert_address_").show();
            $("#t2").show();
            $(".jp").hide();
        }


    }
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    //微信刷脸
    $("#wxpay").click(function(){
        if (isAndroid == true) {

            try {

                Android.speechOutputForAndroid("微信刷脸，请将脸移至画面正中央，请保持脸部在正中央");

            }catch(error){

            }
            var storeID = comID;
            if (comID.length > 32) {
                storeID = comID.substring(7);
            }
            if(wxbind=="gw") {
                Android.androidAcquireFaceID(storeID, companyName);
            }else {
                Android.useWinXinPayForAndroid(storeID, companyName, journalNum, totalMoney, comID);//调用安卓接口
            }
        }

    });


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

            scardPayOrder(sccId);
            document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=购物卡支付&addressID="+addressID+"&posNum="+posNum;


    });
    $(".mm-alert div input").click(function () {
        $(".scard").focus();
        $(".mm-alert").hide();
    });


});

//跳转生成订单并跳转到成功页面；
function  jumpSuc(){
        var ulp = basePath
            + "/ea/sm/sajax_ea_facePayCallBack.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : true,
            dataType : "json",
            data:{

                journalNum:journalNum

            },
            success : function(data) {
            },
            error : function(data) {
                console.log("查询支付结果失败");
            }
        });


        document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=微信刷脸支付&posNum="+posNum;


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
        $(".alert_address_").hide();
        $("#t2").hide();
        $(".jp").show();

        ajaxPayBackUp();
    }

}


//备份信息
function ajaxPayBackUp() {
    var  consignee = $("#addressform .verification").eq(0).val();
    var phone = $("#addressform .verification").eq(1).val();
    var noviceAddress =  $("#addressform #address").val()+$("#addressform .verification").eq(2).val();
    var ulp = basePath
        + "/ea/sm/sajax_ea_ajaxPayBackUp.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        async : false,
        data: {
            "payBackupBill.noviceName": consignee,
            "payBackupBill.novicePhone": phone,
            "payBackupBill.noviceAddress": noviceAddress,
            "payBackupBill.journalNum":journalNum
        },
        dataType : "json",
        success : function(data) {


        },
        error : function(data) {
            console.log("备份信息");
        }
    });

}

function dealFaceID(openid){
    var url =  basePath+"/ea/sm/sajax_ea_faceValiShopping.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            openid:openid,
            journalNum:journalNum,
            totalMoney:totalMoney
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var member = eval("("+data+")");
            var result = member.result;
            if(result=="3"||result=="4"){  //绑定购物卡

                 document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum+"&fh="+fh+"&wxbind=gw&openid="+openid+"&directUrl=" + encodeURIComponent(directUrl);

            }else if(result=="2"){
                ct = "余额不足请选择其他支付方式";

                if (ct != "") {
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
                 sccId = member.sccId;
                if(vipmoney!=null&&vipmoney!=""){
                    totalMoney = vipmoney;
                }
                if(directUrl!=""&&directUrl!=null) {
                    var backurls = window.location.href;
                    document.location.href = directUrl +"&mode=facecard&totalMoney=" + totalMoney + "&totalNum="+totalNum+"&posNum="+posNum+"&journalNum=" + journalNum+"&sccId=" + sccId+"&backurls=" + encodeURIComponent(backurls);


                }else {
                    if (fh == "2") {
                        checkPayAddress(sccId);
                    } else {

                        scardPayOrder(sccId);
                        document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum + "&paytype=购物卡支付&addressID=" + addressID + "&posNum=" + posNum;
                    }
                }
            }


        }
    })
}

//购物卡支付
function scardPayOrder(sccId){
    if(sccId!="") {
        var ulp = basePath
            + "/ea/sm/sajax_ea_scardPayOrder.jspa?d="+new Date();
        $.ajax({
            type : "GET",
            url : ulp,
            async : true,
            dataType : "json",
            data:{
                sccId:sccId,
                totalMoney:totalMoney,
                journalNum:journalNum,
                addressID:addressID

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
 * 获取用户地址2
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