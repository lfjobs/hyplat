
var id = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function() {

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){

          //  document.location.replace(basePath+"/ea/androiddoc/ea_getArchiveTypeTree.jspa?module="+module+"&companyID="+companyID);

            if (isAndroid == true || isiOS == true) {

                window.history.back();
            }
            else{
                window.opener.location.reload(); // 刷新父页面
                window.close(); // 关闭当前窗口
            }
        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })




    $(".saveDraft").click(function(){


        if($.trim($("#title").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写分类名称");
            return false;
        }
        

        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/androiddoc/ea_addArchiveType.jspa");

        document.form.submit.click();
        token = 13;
    });
    


});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


