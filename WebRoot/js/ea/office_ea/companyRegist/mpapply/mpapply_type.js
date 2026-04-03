
$(function() {
  //下一步
   $("#next").click(function () {
          var organization_type = $(".sec-rad .active").attr("data-index");
          var agree = $(".div-tiaokuan").attr("class").indexOf("active")
           if(agree==-1){
               return false;
           }else{

               var url = basePath+"ea/merch/sajax_ea_saveMaterial.jspa";

               $.ajax({
                   url : url,
                   type : "get",
                   async : false,
                   dataType : "json",
                   data:{
                       "applyParam.organization_type":organization_type,
                       "applyParam.out_request_no":out_request_no,
                       mode:"organization_type",
                       companyID:companyID,
                       ajax:"ajax"
                   },
                   success : function (data) {
                       var m = eval("("+data+")")
                       var applyParam = m.applyParam;
                       var out_request_no = applyParam.out_request_no;
                       document.location.href = basePath+"ea/merch/ea_getApplydp.jspa?organization_type="+organization_type+"&companyID="+companyID+"&out_request_no="+out_request_no+"&staffID="+staffID;
                   },
                   error:function(data){
                       console.log("获取数据失败");
                   }
               });

           }

   })

   if(organization_type!=""&&organization_type!=null) {
       var $dd = $("div[data-index='" + organization_type + "']");
       $dd.addClass("active");
   }else{
       $("div[data-index='2401']").addClass("active");
   }


});




