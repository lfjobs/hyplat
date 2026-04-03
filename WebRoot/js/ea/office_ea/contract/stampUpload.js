$(document).ready(function () {


    if(noauth=="0"){
        $(".div-pro").show();
    }
    $(".close-edits").click(function () {
        $(".div-pro1").hide();
        document.location.replace(basePath+"/ea/qrshare/ea_queryState.jspa?auditSkip=01");
    })
    $(".close-edit").click(function () {
        $(".div-pro").hide();
        document.location.replace(basePath+"ea/mycenter/ea_getInfo.jspa");

    })
    $("#iframe").attr("height",$(window).height());
    $("#responsibleName").click(function(){

        $(".iframecom").show();
    });
    //确定停用
    $(".li-tingyong").click(function(){
        $(".div-tingyong").show();
    })

    $(".close-tingyong").click(function(){
        if($(".titlep").text()=="操作成功"){
            window.location.replace(basePath+"ea/enterprisestamp/ea_getStampList.jspa");

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })

    $(".close-zb").click(function(){
        window.location.replace(basePath+"ea/enterprisestamp/ea_getStampList.jspa");
    })



    //提交审核
    $(".submitAudit").click(function(){

        if($.trim($("#stampName").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写印章名称");
            return false;
        }

        if($("#imgSdf").attr("src").indexOf("contract/stamp/img_09.png")!=-1){
            $(".div-tingyong").show();
            $(".titlep").text("请上传印章图片尺寸180x180px不留边缘");
            return false;
        }


        if($(".p-leixing").text()=="印章类型"){
            $(".div-tingyong").show();
            $(".titlep").text("请选择印章类型");
            return false;
        }else{
            $("#stampType").val($(".p-leixing").text());
        }


        $(".loading").show();
        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/enterprisestamp/ea_addUpdateStamp.jspa");

        document.form.submit.click();
        token = 13;

    });
})

function re_load() {
    $(".loading").hide();
    $(".div-tingyong").show();
    $(".titlep").text("操作成功");

}