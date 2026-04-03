
$(document).ready(function(){


    $(".saveDraft").click(function() {
        var typeName = $("#typeName").val();
        if(typeName == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请填写题库类别");

            return
        }


        $("#from1").attr("target", "hidden").attr("action",
            basePath + "ea/quest/ea_saveQueType.jspa");
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



