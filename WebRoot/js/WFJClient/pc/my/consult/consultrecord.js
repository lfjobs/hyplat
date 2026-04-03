
var id = "";

$(function() {

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
            document.location.href = basePath+"ea/consult/ea_getConsultslist.jspa";

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })



    $(".save").click(function(){




        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/consult/ea_saveReturnVisit.jspa");

        document.form.submit.click();
        token = 13;
    });
    


});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


