
$(document).ready(function(){
      show();

    $("#sex,#sexjt").click(function(){
        $(".job-int_gzxz").show();
    });


    $(".job-int_d2 p").click(function(){
        $(".confirmationBox").removeClass("confirmationBox");
        $(this).addClass("confirmationBox");
        $(this).attr("style","background:url('"+basePath+"images/resume/gou2.png') 14rem 12px no-repeat;background-size: 26px;").siblings().attr("style","background:url('"+basePath+"images/resume/gou2_.png') 14rem 12px no-repeat;background-size: 26px;");
    });
    $(".job-int_d3").click(function(){
        $(".job-int_gzxz").css("display", "none");
    });
  
    $("#btn_wc").click(function () {
        $(".job-int_gzxz").css("display", "none");

        $("#sex").text($(".job-int_d2 .confirmationBox").text());
        $("#sexs").val($.trim($("#sex").text()));

    });
    $("#btn_qx").click(function () {
        $(".job-int_gzxz").css("display", "none");
    });


    $("#commit").click(function() {
        var staffName = $("#staffName").val();
        if(staffName == ''){
            $(".div-tingyong").show();
            $(".titlep").text("姓名没填呢...");

            return
        }
        var sexs = $("#sexs").val();

        if(sexs == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请选择性别...");

            return
        }
        var ipt_1 = $(".ipt_1").val();

        if(ipt_1 == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请选择出生日期...");

            return
        }
        var nation = $("#nation").val();


        if(nation == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请选择民族...");

            return
        }


        var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[0-9]|19[0-9])[0-9]{8}$/ ; //手机正则
        var phone = $("#reference").val();
        if(phone == ''){
            $(".div-tingyong").show();
            $(".titlep").text("手机还没填呢...");

            return
        }else if(!PhoneReg.test(phone)){
            $(".div-tingyong").show();
            $(".titlep").text("手机格式错误...");
            return
        }

        if(!checkCard($("#staffIdentityCard").val())){
            $(".div-tingyong").show();
            $(".titlep").text("身份证已存在");
            return
        }
        if(!checkWfjAcc($("#acc").val())){
            $(".div-tingyong").show();
            $(".titlep").text("数字地球账号尚未注册");
            return
        }


        $("#from1").attr("target", "hidden").attr("action",
            basePath + "ea/entry/ea_saveInfo.jspa");
        document.form.submit.click();
        token = 13;

    });


    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
           window.history.go(-1);
           return false;

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })
});

function re_load(){
    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;

}


function  checkCard(card){
    var result = true;
    if(card==""){
        return result;
    }


    var ulp = basePath
        + "ea/entry/sajax_ea_checkCard.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            "staff.staffID": staffID,
            "staff.staffIdentityCard":card
        },
        success: function (data) {
            var m = eval("("+data+")");
            var r = m.r;
            
            if(r=="0"){
                result = true;
            }else{
                result = false;
            }

        },
        error:function(data){
        }
    })
  return result;

}




function  checkWfjAcc(acc){
    var result = true;
    if(acc==""){
        return result;
    }


    var ulp = basePath
        + "ea/entry/sajax_ea_checkWfjAccount.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            acc: acc
        },
        success: function (data) {
            var m = eval("("+data+")");
            var sccId = m.sccId;

            if(sccId==""){
                result = false;
            }else{
                $("#sccId").val(sccId);
                result = true;
            }




        },
        error:function(data){
        }
    })
    return result;

}

function show(){
    if(staffID!=null&&staffID!=""){
        $(".st").val($("#st").val());
    }
}