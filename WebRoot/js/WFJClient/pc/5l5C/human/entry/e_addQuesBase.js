
$(document).ready(function(){

    show();

$(".addfl").click(function(){

    document.location.href = basePath+"ea/quest/ea_getQueTypeList.jspa";

})
    $(".saveDraft").click(function() {
        var qbtID = $(".qbtID").val();
        if(qbtID == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请选择题库类别");

            return
        }
        var typeName = $(".qbtID").find("option:selected").text();

      $("#qbtID").val(qbtID);
        $("#typeName").val(typeName);
        var titleBase = $("#titleBase").val();

        if(titleBase == ''){
            $(".div-tingyong").show();
            $(".titlep").text("请填写题库名称");

            return
        }


        $("#from1").attr("target", "hidden").attr("action",
            basePath + "ea/quest/ea_saveQueBase.jspa");
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

function show(){
    var qbtID = $("#qbtID").val();
    if(qbtID!=""){
        $(".qbtID").val(qbtID);
    }
}



