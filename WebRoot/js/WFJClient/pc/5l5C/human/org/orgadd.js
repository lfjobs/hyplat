$(function() {
    editinfo();
    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){

            window.history.back();

            return false;

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })



    $(".saveDraft").click(function(){



        if($.trim($("#organizationNumber").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写机构序号");
            return false;
        }

        $("#organizationform").attr("target","hidden").attr("action",basePath+"ea/corganization/n_ea_saveOrg.jspa");
        document.organizationform.submit.click();
        token = 13;

    });


    $(".div-yinzhang li").click(function(){
        $(this).parents(".div-yinzhang").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $(".p-org").text($.trim($(this).text()));
        $("#organizationUrl").val($.trim($(this).attr("data-value")));
    })

    $("#div-org").click(function(){
        $(".div-yinzhang").show();
    })

});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}

function editinfo(){
    if($("#organizationID").val()!=""&&$("#organizationID").val()!="") {
        $(".div-yinzhang li").each(function () {
            if ($(this).attr("data-value") == $("#organizationUrl").val()) {
                $(".p-org").text($(this).text());
                return false;
            }
        })
        
        $(".title-span").text("修改");
    }

    if(view=="view"){
          $(".content input[type=text]").attr("readonly",true);
          $(".div-bottom").hide();
        $(".title-span").text("查看");
    }
}


