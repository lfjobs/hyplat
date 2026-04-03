
$(function() {

    show(mode);
     //提交
    $(".submit1").click(function(){
        makeup();
        $("#form1").attr("target","hidden").attr("action",basePath+"ea/merch/ea_saveMaterial.jspa");
        token = 2;
        document.form1.submit.click();

    });
    //长期效果
    $(".cq").click(function(){
        if($(this).is(":checked")){
            $(this).parents("section").prev(".input-last").hide();
            $(this).parents("section").prev(".input-last").prev(".input-last").hide();
        }else{
            $(this).parents("section").prev(".input-last").show();
            $(this).parents("section").prev(".input-last").prev(".input-last").show();
        }
       $(this).parents("div").find(".final").val($(this).val());
    });


});
//根据修改的模块显示修改的内容
function show(mode){
   $(".info").hide();
   $("."+mode).show();

   if(mode=="organization_type"){
       $("#organization_type").val(organization_type);

   }else if(mode=="contact_info"){
        $("#contact_type").val(contact_type);

    }else if(mode=="id_card_info"){
       $("#validtime").val($("#id_card_valid_time").val());
       if(id_card_valid_time=="长期"){
           $("#validtime").hide();
           $("#cardcq").attr("checked",true);
       }else{
           $("#validtime").show();
           $("#cardcq").attr("checked",false);
       }
   }else if(mode=="business_license_info"){
       var time = business_time.substring(1,business_time.length-1).split(",");
       var start = "";
       var end = "";
       if(time.length>0) {
            start = time[0].replace(/\"/g, "");
       }
       if(time.length>1) {
           end = time[1].replace(/\"/g, "");
       }
       $("#start").val(start);
       if(end=="长期"){
           $("#end").hide();
           $("#end").next(".input-last").hide();

           $("#bucq").attr("checked",true);
       }else{
           $("#end").val(end);
           $("#end").show();
           $("#end").next(".input-last").show();
           $("#bucq").attr("checked",false);
       }

       if(!$("#orcq").is(":hidden")) {

           if (organization_time != "") {
               var time = organization_time.substring(1, organization_time.length - 1).split(",");
               var start1 = time[0].replace(/\"/g, "");
               var end1 = time[1].replace(/\"/g, "");
               $("#start1").val(start1);
               if (end1 == "长期") {
                   $("#end1").hide();
                   $("#bucq").attr("checked", true);
               } else {
                   $("#end1").val(end1);
                   $("#end1").show();
                   $("#orcq").attr("checked", false);
               }
           }
       }
   }

}

//验证

function  makeup(){
    //身份证有效期
  if(mode=="id_card_info"){

      if(!$("#cardcq").is(":checked")){
          $("#id_card_valid_time").val($("#validtime").val());

      }

  }else if(mode=="business_license_info"){
      //营业执照 [\"2014-01-01\",\"长期\"]
      if($("#bucq").attr("checked")==false){
          var final = "[a\""+$("#start").val()+"a\",a\""+$("#end").val()+"a\"]";

          $("#bustime").val(final);
      }else{
          var final = "[a\""+$("#start").val()+"a\",a\""+$("#bucq").val()+"a\"]";

          $("#bustime").val(final);

      }

      //组织机构代码
      if(!$("#orcq").is(":hidden")) {
          if ($("#orcq").attr("checked") == false) {
              var final = "[a\""+$("#start1").val()+"a\",a\""+$("#end1").val()+"a\"]";

              $("#orgtime").val(final);
          } else {
              var final = "[\"\""+$("#start1").val()+"a\",a\""+$("#orcq").val()+"\"\"]";
              $("#orgtime").val(final);

          }
      }
  }

}



function re_load() {
    if(token) {
        document.location.href = basePath
            + "ea/merch/ea_getMaterialPage.jspa?applyParam.out_request_no="+out_request_no;
    }
}







