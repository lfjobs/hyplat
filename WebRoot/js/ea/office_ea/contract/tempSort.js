$(document).ready(function () {
    $('#add').click(function(){
        var $options = $('#select1 .active');
        $options.appendTo('#select2');
    })


    $('#del').click(function(){
        var $options = $('#select2 .active');
        $options.appendTo('#select1');
    })

    $(document).on("click","#select1 li",function(event){
        $("#select1 li").removeClass("active");
        $(this).addClass("active");

    })

    $(document).on("click","#select2 li",function(event){
        $("#select2 li").removeClass("active");
        $(this).addClass("active");

    })
 
    $(document).on("dblclick","#select1 li",function(event){
        var $options = $(this);
        $options.appendTo('#select2');

    })

    $(document).on("dblclick","#select2 li",function(event){
        var $options = $(this);
        $options.appendTo('#select1');

    })
    $('#save_all').click(function(){
        var $options = $('#select1 li');
        if($options.length > 0){
            $(".div-tingyong").show();
            $(".titlep").text("您必须对所选模板全部排序");
            return;
           
        }
        var childrenID = "";
        var $options = $('#select2 li');
        $options.each(function(){
            childrenID += $(this).attr("id")+'_'
        });
        if(childrenID == ""){
            return ;
        }
        $('#childrenID').val(childrenID);
        document.childrenform.submit.click();
        $("#childrenform").attr("target", "hidden").attr("action",
            basePath+"ea/androiddoc/ea_saveTempSort.jspa");
        document.childrenform.submit.click();
        token = 13;
    })

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="排序成功"){

            if(module!=""&&module!=null){
                var defaults="";
                if(module=="doc"){
                    defaults = "gwmb";
                }else if(module=="contract"){
                    defaults = "htmb";

                }
                document.location.replace(basePath+"/page/WFJClient/pc/5l5C/office/templateManage.jsp?defaults="+defaults);
            }else{
                document.location.replace(basePath+"/ea/androiddoc/ea_getDocTempTree.jspa?module="+module);
            }


        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })


});

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("排序成功");
    return false;
}