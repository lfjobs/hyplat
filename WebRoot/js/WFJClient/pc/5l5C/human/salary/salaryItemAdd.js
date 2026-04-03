
var id = "";

$(function() {

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
            document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryUnitslist.jsp";

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })





    $(".save").click(function(){


        if($.trim($("#name").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写薪资项目节点");
            return false;
        }

        if($("#bsid").val()!=null&&$("#bsid").val()!=""){
            $("#form").attr("target", "hidden").attr("action",
                basePath + "ea/salarylevel/ea_updateSalary.jspa");
        }else{
            $("#form").attr("target", "hidden").attr("action",
                basePath + "ea/salarylevel/ea_addSalaryItems.jspa");
        }


        document.form.submit.click();
        token = 13;
    });



});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


