
$(function() {
    bc();
  //下一步
   $("#next1").click(function () {


       var error = 0;

       $(".content .notnull").each(function(){
           var tip = $(this).attr("data");
           if($.trim($(this).val())==""){

               if(tip=="营业执照照片"){
                   if($(this).attr("src").indexOf("pbapply/img_014.png")!=-1){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text("请上传"+tip);
                       error = 1;
                       return false;
                   }

               }else if(tip=="营业期限截止日期"){
                   if(!$("#checkbox1").is(":checked")){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text(tip+"不能为空");
                       error = 1;
                       return false;
                   }

               } else if(tip=="组织机构代码开始时间"){
                   if(!$(".sec-01 .div-radio label:first-of-type input").is(":checked")){
                           //普通营业执照
                       $(".div-p-02").show();
                       $(".div-p-02 p").text(tip+"不能为空");
                       error = 1;
                       return false;
                   }

               } else if(tip=="组织机构代码截止时间"){

                   if(!$(".sec-01 .div-radio label:first-of-type input").is(":checked")){
                       if(!$("#checkbox2").is(":checked")){
                           $(".div-p-02").show();
                           $(".div-p-02 p").text(tip+"不能为空");
                           error = 1;
                           return false;
                       }
                   }
               } else if(tip=="组织机构代码证"){

                   if(!$(".sec-01 .div-radio label:first-of-type input").is(":checked")){
                       if($(this).attr("src").indexOf("pbapply/img_015.png")!=-1){
                           $(".div-p-02").show();
                           $(".div-p-02 p").text("请上传"+tip);
                           error = 1;
                           return false;
                       }
                   }
               }else if(tip=="组织机构代码"){

                   if(!$(".sec-01 .div-radio label:first-of-type input").is(":checked")){
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
           makeup();
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }



   })


});

//验证
function  makeup(){

         //营业执照 [\"2014-01-01\",\"长期\"]
      if(!$("#checkbox1").is(":checked")){
          var final = "[a\""+$("#dateSelectorOne").val()+"a\",a\""+$("#dateSelectorTwo").val()+"a\"]";//长期

            $("#bustime").val(final);
        }else{
          var final = "[a\""+$("#dateSelectorOne").val()+"a\",a\""+$("#checkbox1").val()+"a\"]"; //非长期

            $("#bustime").val(final);

        }

        //组织机构代码
        if(!$("#checkbox2").is(":hidden")) {
            if(!$("#checkbox2").is(":checked")){
                var final = "[a\""+$("#dateSelector1").val()+"a\",a\""+$("#dateSelector2").val()+"a\"]";//长期

                $("#orgtime").val(final);
            } else {
                var final = "[a\""+$("#dateSelector1").val()+"a\",a\""+$("#checkbox2").val()+"a\"]";


                $("#orgtime").val(final);

            }
        }







}

function re_load() {
    if(token) {
        document.location.href = basePath+"ea/merch/ea_getApplyCard.jspa?applyParam.out_request_no="+out_request_no+"&companyID="+companyID+"&staffID="+staffID;

    }
}


//JS file 图片 即选即得 显示
//创建一个FileReader对象
var reader = new FileReader();
function f_change(file){
    var img = document.getElementById('imgSdf');
    //读取File对象的数据
    reader.onload = function(evt){
        //data:img base64 编码数据显示
        img.width  =  "100";
        img.height =  "100";
        img.src = evt.target.result;
        shibieCert(img.src);
    }
    reader.readAsDataURL(file.files[0]);


}
function f_change1(file){
    var img = document.getElementById('imgSdf1');
    //读取File对象的数据
    reader.onload = function(evt){
        //data:img base64 编码数据显示
        img.width  =  "100";
        img.height =  "100";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}

//识别证件
function  shibieCert(img) {

    var url = basePath+"ea/merch/sajax_ea_shibieCert.jspa";

    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data:{
            "business_license_info.business_license_copy":img,
            certType:"00"
        },
        success : function (data) {
            var m = eval("("+data+")")
             var business_license_info = m.business_license_info;
            $("#business_license_number").val(business_license_info.business_license_number);
            $("#merchant_name").val(business_license_info.merchant_name);
            $("#legal_person").val(business_license_info.legal_person);
            $("#company_address").val(business_license_info.company_address);
            var business_time = business_license_info.business_time;
            if(business_time=="长期"){
                $("#checkbox1").attr("checked",true);
                $("#date2").hide();
            }else{
                $("#checkbox1").attr("checked",false);
                $("#date2").show();
                $("#dateSelectorTwo").val(business_time.replace("年","-").replace("月","-").replace("日",""));
            }


        },
        error:function(data){
            console.log("获取数据失败");
        }
    });





}

function bc(){
    if(business_license_number!=""&&business_license_number!=null){
        if(business_license_number.length==18){
           $(".div-radio input").eq(0).attr("checked",true);
            $(".sec-show").hide();
        }else{
            $(".div-radio input").eq(1).attr("checked",true);
            $(".sec-show").show();
        }
    }


    if(business_time!=""&&business_time!=null){
            if(business_time.indexOf("长期")!=-1){
                $("#checkbox1").attr("checked",true);
                $("#date2").hide();
                $("#dateSelectorOne").val(business_time.substring(2,12));

            }else{
                $("#date2").show();
                $("#dateSelectorOne").val(business_time.substring(2,12));
                $("#dateSelectorTwo").val(business_time.substring(15,25));
            }
        }


    if(organization_time!=""&&organization_time!=null){
        if(organization_time.indexOf("长期")!=-1){
            $("#checkbox2").attr("checked",true);
            $("#date3").hide();
            $("#dateSelector1").val(organization_time.substring(2,12));

        }else{
            $("#date3").show();
            $("#dateSelector1").val(organization_time.substring(2,12));
            $("#dateSelector2").val(organization_time.substring(15,25));
        }

    }

}







