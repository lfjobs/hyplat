
var id = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function() {

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){

            if (isAndroid == true || isiOS == true) {


                if (ifr=="true") {
                    // 当前页面是通过parent.location.href打开的
                    var defaults="";

                    if(module=="doc"){
                        defaults = "gwfl";
                    }else if(module=="contract"){
                        defaults = "htfl";

                    }
                    document.location.replace(basePath+"/page/WFJClient/pc/5l5C/office/templateManage.jsp?defaults="+defaults);
                } else {
                    // 当前页面不是通过parent.location.href打开的
                         window.history.back();
                }

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
            basePath + "ea/androiddoc/ea_addDocTempType.jspa");

        document.form.submit.click();
        token = 13;
    });
    


});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


