
$(function() {
  //下一步
   $("#next1").click(function () {
       $(".div-p-02").hide();
       var error = 0;

       $(".content .notnull").each(function(){
           var tip = $(this).attr("data");
           if($.trim($(this).val())==""){

               if(tip=="银行卡照片"){
                   if($(this).attr("src").indexOf("pbapply/img_015.png")!=-1){
                       $(".div-p-02").show();
                       $(".div-p-02 p").text("请上传"+tip);
                       error = 1;
                       return false;
                   }
               }else if(tip=="开户银行全称"){
                   if(!$(this).is(":hidden")) {
                       $(".div-p-02").show();
                       $(".div-p-02 p").text(tip+"不能为空");
                       error = 1;
                       return false;
                   }

               }
               else{
                   $(".div-p-02").show();
                   $(".div-p-02 p").text(tip+"不能为空");
                   error = 1;
                   return false;
               }



           }

       });


       if(error==0) {
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }



   })
   //账户类型
    $(".btn5").bind("DOMNodeInserted",function(){
       var bank_account_type =  $(this).text();
       if(bank_account_type=="对公账户"){
              $(".bank_account_type").val("74");
              $(".sec-show").hide();
       }else{
           $(".bank_account_type").val("75");
           $(".sec-show").show();
       }
    });


    //开户银行
    $(".btn3").bind("DOMNodeInserted",function(){
        var account_bank =  $(this).text();
           $("#account_bank").val(account_bank);
           if(account_bank=="其他银行"){
               $(".bank_name").show();

           }else{
               $(".bank_name").hide();
           }
    });

});

function re_load() {
    /*if(token) {
        document.location.href = basePath+"ea/merch/ea_getApplyContact.jspa?applyParam.out_request_no="+out_request_no+"&companyID="+companyID+"&staffID="+staffID;

    }*/
    alert("操作成功！");
    //回到5L5CERP人事组织管理
    document.location.href = basePath+"page/WFJClient/pc/5l5C/human/orgsystem.jsp";
}

var reader = new FileReader();

function f_change1(file){
    var img = document.getElementById('imgSdf1');
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


//识别证件
function  shibieCert(img) {

    var url = basePath+"ea/merch/sajax_ea_shibieCert.jspa?d="+new Date();

    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data:{
            "account_info.account_copy":img,
            certType:"03"
        },
        success : function (data) {
              var m = eval("("+data+")")
             var account_info = m.account_info;
             $("#account_bank").val(account_info.account_bank);
             if(account_info.account_bank!=""){
                    $(".btn3").text(account_info.account_bank);
             }
            $("#account_number").val(account_info.account_number.replace(" ",""));



        },
        error:function(data){
            console.log("获取数据失败");
        }
    });





}







