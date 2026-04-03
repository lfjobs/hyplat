

$(function() {



    $(".save").click(function(){


       if ($.trim($("#noviceName").val()) == "") {
                $(".div-tingyong").show();
                $(".titlep").text("请填写真实姓名");
                return false;
       }

        if($.trim($("#noviceCode").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写身份证号");
            return false;
        }
        if($.trim($("#reference").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写手机号");
            return false;
        }
        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/contract/ea_updateInfo.jspa");

        document.form.submit.click();
        token = 13;
    });


    $(".close-tingyong,.close-confirm").click(function(){

        if($(".titlep").text()=="操作成功"){
            window.history.back();
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




