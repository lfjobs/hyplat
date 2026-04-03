$(function(){



    $(".close-tingyong,.close-confirm").click(function(){
        $(".div-tingyong").hide();
        window.history.go(-1)
    })




})
//面试登记
function bdnotice(){

  $("#form").attr("target","hidden").attr("action",basePath+"ea/entry/ea_bdnotice.jspa");
  document.form.submit.click();
  token  = 3;

}


function re_load(){

    if(token){
        $(".titlep").text("操作成功");
        $(".div-dqd").show();


    }

}