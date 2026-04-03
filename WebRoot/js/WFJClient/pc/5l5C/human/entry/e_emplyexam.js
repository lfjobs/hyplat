$(function(){



    $(".close-tingyong,.close-confirm").click(function(){
        $(".div-tingyong").hide();
        window.history.go(-1)
    })




})
//考试结果
function examResult(status){
    if(vm=="b"){
        $("#bsState").val(status);
    }if(vm=="k"){
        $("#ksState").val(status);
    }else{
        $("#zpState").val(status);
    }

  $("#form").attr("target","hidden").attr("action",basePath+"ea/entry/ea_passExam.jspa");
  document.form.submit.click();
  token  = 3;

}



function re_load(){

    if(token){
        $(".titlep").text("操作成功");
        $(".div-dqd").show();


    }

}