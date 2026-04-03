
$(document).ready(function(){


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
            $(".titlep").text("昵称没填呢...");

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

        $("#from1").attr("target", "hidden").attr("action",
            basePath + "ea/mycenter/ea_editInfo.jspa");
        document.form.submit.click();
        token = 13;

    });


    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
            document.location.href = basePath+"ea/mycenter/ea_myIndex.jspa";

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