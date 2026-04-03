$(function(){
    if (posNum != null && posNum != ""&&address==""&&cardNum=="") {
        if(fh=="2"){
            $(".alert_address_").show();
            $("#t2").show();
            $(".jp").hide();
        }


    }

    if (posNum != null && posNum != ""&&comID=="") {
        $(".zc").show();
    }else{
        $("figure").addClass("wfj");
    }



    if(posNum != null && posNum != ""&&cardNum!=""&&comID==""){
        $(".zc").show();
    }


    $(".txtNum2").val("");
    $(".txtNum").val("");

    $(".mm-alert div input").click(function () {

        var ct = $(".mm-alert .ct").text();
        if(ct=="请先输入实收金额"||ct=="实收金额少于应收金额"){
            $(".mm-text .oop input").focus();
            $(".mm-text .oop input").val("");
            $(".zlprice").text(0);
        }
        $(".mm-alert").hide();
    });

});

//确认
function confirm(){
    if(!$(".zc").is(":hidden")) {
        var tel = $(".tel").val();
        if(tel==""){
            $(".mm-alert .ct").text("请输入微分金账号");
            $(".mm-alert").show();
            return;
        }
    }
    var pw = $(".txtNum").val();
    if(pw==""){
        $(".mm-alert .ct").text("请输入密码");
        $(".mm-alert").show();
        return;
    }else if(pw.length!=6){
        $(".mm-alert .ct").text("密码为6位");
        $(".mm-alert").show();
        $(".txtNum").val("");
        return;
    }

    if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){

        totalMoney = remainMoney;
    }
        var ulp = basePath
            + "/ea/sm/sajax_ea_checkUser.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : false,
            dataType : "json",
            data:{
                pw:pw,
                staffID:staffID,
                ssprice:$(".txtNum2").val(),
                zlprice:$(".zlprice").text(),
                journalNum:journalNum,
                comID:comID,
                telphone:$(".tel").val(),
                totalMoney:totalMoney
            },
            success : function(data) {
                var member = eval('(' + data + ')');
                var result = member.result;

                if(result!="2") {
                    var ct = "";
                    if (result == "1") {
                        ct = "操作员账户没有绑定微分金账户";

                    }else if (result == "0") {


                    }else if (result == "3") {
                        ct = "密码错误";


                    }else if (result == "4") {
                        ct = "积分不足请工作人员充值";


                    }
                    $(".txtNum").val("");
                    $(".mm-alert .ct").text(ct);
                    $(".mm-alert").show();

                }else{
                    if(token==0) {
                         token = 1;
                         if(comID==""||comID==null){
                             comID = member.comID;
                         }
                        if(staffID==""||staffID==null){
                            staffID =  member.staffID;
                        }
                        if(staffName==""||staffName==null){
                            staffName =  member.staffName;
                        }
                        cashPayOrder()
                        document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=现金支付&posNum="+posNum+"&carmID="+carmID+"&fhform="+fhform;
                    }
                }


            },
            error : function(data) {
                console.log("查询支付结果失败");
            }
        });



}

function cashPayOrder() {
    var ulp = basePath
        + "/ea/sm/sajax_ea_cashPayOrder.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            comID: comID,
            totalMoney:totalMoney,
            journalNum:journalNum,
             oprID:staffID,
              oprName:staffName,
              ppid:ppid,
            fhform:fhform
        },
        success: function (data) {
            console.log("现金支付成功");
        },
        error: function (data) {
            console.log("现金支付失败");
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
