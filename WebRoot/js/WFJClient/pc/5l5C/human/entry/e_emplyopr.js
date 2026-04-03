$(function(){

    $(".close-tingyong").click(function(){
        $(".div-tingyong").hide();
    })


    $(".close-confirm").click(function(){
      var titlep = $(".titlep").text();
      if(titlep=="操作成功"){

          window.history.go(-2);
      }
        $(".div-tingyong").hide();

    })




})
//面试登记
function msregis(){
    var examiner = $("#examiner").val();
    if(examiner==""){
        $(".titlep").text("请填写面试官");
        $(".div-dqd").show();
        return false;

    }

    $("#bstName").val($("#audition_bstID").find("option:selected").text());
    $("#mstName").val($("#audition_mstID").find("option:selected").text());

  $("#form").attr("target","hidden").attr("action",basePath+"ea/entry/ea_interviewReg.jspa");
  document.form.submit.click();
  token  = 3;

}
//转通知
function oprzt(){

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