$(document).ready(function () {
    $('#add').click(function(){
        var $options = $('#select1 .active');
        $options.appendTo('#select2');
    })


    $('#del').click(function(){
        var $options = $('#select2 .active');
        $options.appendTo('#select1');
    })



    $('#add_all').click(function(){
        var $options = $('#select1 li');
        $options.appendTo('#select2');
    });

    $('#del_all').click(function(){
        var $options = $('#select2 li');
        $options.appendTo('#select1');
    });
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
            $(".titlep").text("您必须对所选机构全部排序");
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
        $('#childrenform').attr("target","hidden").attr("action",basePath + "ea/corganization/n_ea_sortChildOrganization.jspa");
        document.childrenform.submit.click();
        token = 13;




    })

    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="排序成功"){
         window.history.back();

            return false;


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