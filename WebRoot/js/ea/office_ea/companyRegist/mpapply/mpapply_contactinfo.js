
$(function() {
  //下一步
   $("#next1").click(function () {
       var error = 0;

       $(".content .notnull").each(function(){
           var tip = $(this).attr("data");
           if($.trim($(this).val())==""){

               $(".div-p-02").show();
               $(".div-p-02 p").text(tip+"不能为空");
               error = 1;
               return false;



           }

       });


       if(error==0) {
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }


   })


});

function re_load() {
    /*if(token) {
        document.location.href = basePath+"ea/merch/ea_submitAudit.jspa?companyID="+companyID+"&staffID="+staffID;


    }*/
    alert("操作成功！");
    //回到5L5CERP人事组织管理
    document.location.href = basePath+"page/WFJClient/pc/5l5C/human/orgsystem.jsp";
}




