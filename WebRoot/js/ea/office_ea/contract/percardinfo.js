

$(function() {

    $(".edit").click(function(){

        if($(this).text()=="编辑") {
            $(".div-bottom").show();
            $("#realname").attr("readonly", false);
            $("#staffIdentityCard").attr("readonly", false);
            $(this).text("取消");
        }else{
            $(".div-bottom").hide();
            $("#realname").attr("readonly", true);
            $("#staffIdentityCard").attr("readonly", true);
            $(this).text("编辑");
        }
    });

//提交审核 咱不审核
    $(".save").click(function(){
        var input = document.getElementById('personImageInfo');
        var img = document.getElementById('personImage');
        var src = img.getAttribute('src');
        if (input.files.length <= 0 && src==basePath) {
            $(".div-tingyong").show();
            $(".titlep").text("请上传真实头像");
            return false;
        }

       if ($.trim($("#realname").val()) == "") {
                $(".div-tingyong").show();
                $(".titlep").text("请填写真实姓名");
                return false;
       }

        if($.trim($("#staffIdentityCard").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写身份证号");
            return false;
        }
        $("#loading").fadeIn();
        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/mycenter/ea_editInfo.jspa");

        document.form.submit.click();
        token = 13;
    });


    $(".close-tingyong,.close-confirm").click(function(){
        $("#loading").fadeOut();
        if($(".titlep").text()=="操作成功"){
            //document.location.href = basePath+"ea/mycenter/ea_myIndex.jspa";
            localStorage.setItem('refreshType', "1");
            window.history.back();
        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })

});

function re_load(){
    $("#loading").fadeOut();
    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;

}


