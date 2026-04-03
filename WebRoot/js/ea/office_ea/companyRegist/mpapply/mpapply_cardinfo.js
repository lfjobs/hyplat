
$(function() {
    bc();
  //下一步
   $("#next1").click(function () {

       var error = 0;

       $(".div-01 .notnull").each(function(){
           var tip = $(this).attr("data");
           if($.trim($(this).val())==""){

               if(tip=="身份证人像面"){
                   if($(this).attr("src").indexOf("pbapply/img_007.png")!=-1){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text("请上传"+tip);
                       error = 1;
                       return false;
                   }

               } else if(tip=="身份证国徽面"){
                   if($(this).attr("src").indexOf("pbapply/img_006.png")!=-1){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text("请上传"+tip);
                       error = 1;
                       return false;
                   }

               } else if(tip=="有效期截止时间"){
                   if(!$("#checkbox1").is(":checked")){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text(tip+"不能为空");
                       error = 1;
                       return false;
                   }

               }else {
                   $(".div-p-02").show();
                   $(".div-p-02 p").text(tip+"不能为空");
                   error = 1;
                   return false;
               }
           }

       });


       if(error==0) {
           if($("#checkbox1").is(":checked")){
                 $("#dateSelectorTwo").val("长期");
           }
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }

   })


});

function re_load() {
    /*if(token) {
        document.location.href = basePath+"ea/merch/ea_getApplyAccount.jspa?applyParam.out_request_no="+out_request_no+"&companyID="+companyID+"&staffID="+staffID;

    }*/
    alert("操作成功！");
    //回到5L5CERP人事组织管理
    document.location.href = basePath+"page/WFJClient/pc/5l5C/human/orgsystem.jsp";
}


//识别证件
function  shibieCert(img,certType) {


    var url = basePath+"ea/merch/sajax_ea_shibieCert.jspa?d="+new Date();

    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data:{
            "id_card_info.id_card_copy":img,
            certType:certType
        },
        success : function (data) {
            var m = eval("("+data+")")
            var id_card_info = m.id_card_info;
            if(certType=="01") {
                $("#id_card_name").val(id_card_info.id_card_name);
                $("#id_card_number").val(id_card_info.id_card_number);
            }else{
                var id_card_valid_time = id_card_info.id_card_valid_time;
                if(id_card_valid_time=="长期"){
                    // $("#checkbox1").attr("checked",true);
                    $("#checkbox1").trigger("click");
                    $("#date2").hide();
                }else{
                    $("#checkbox1").attr("checked",false);
                    $("#date2").show();

                    $("#dateSelectorTwo").val(id_card_valid_time.substring(0,4)+"-"+id_card_valid_time.substring(4,6)+"-"+id_card_valid_time.substring(6,8));//20370203
                }
            }




        },
        error:function(data){
            console.log("获取数据失败");
        }
    });





}

function bc(){
    if(id_card_valid_time!=""&&id_card_valid_time!=null){
         if(id_card_valid_time=="长期"){
              $("#checkbox1").attr("checked",true);
              $("#date2").hide();
         }else{
             $("#checkbox1").attr("checked",false);
             $("#date2").show();
             $("#dateSelectorTwo").val(id_card_valid_time);
         }
    }
}

//JS file 图片 即选即得 显示
//创建一个FileReader对象
var reader = new FileReader();

function f_change1(file){
    var img = document.getElementById('imgSdf1');
    //读取File对象的数据
    reader.onload = function(evt){
        //data:img base64 编码数据显示
        img.width  =  "100";
        img.height =  "100";
        img.src = evt.target.result;
        shibieCert(img.src,"01");
    }
    reader.readAsDataURL(file.files[0]);
}


function f_change(file){
    var img = document.getElementById('imgSdf');
    //读取File对象的数据
    reader.onload = function(evt){
        //data:img base64 编码数据显示
        img.width  =  "100";
        img.height =  "100";
        img.src = evt.target.result;
        shibieCert(img.src,"02");
    }
    reader.readAsDataURL(file.files[0]);
}











