var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {

    if(status=="O"){

        completeRead();
    }
var shareType = "current";
    var zffs = "0";
    var typee= "";
    //提交审核至领导审批
    $(".submitAudit").click(function(){
        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=A";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=A";


        }


    })
    //传阅
    $(".passDraft").click(function(){

        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=p";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=p";


        }
    })


    //转交他人审批
    $(".transfer").click(function(){
        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=transfer";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=transfer";


        }

    })


    // 转交他人盖章签字
    $(".transferStamp").click(function(){
        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=seal";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=seal";


        }

    })

    // 转至分发人
    $(".transferPublish").click(function(){
        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=publish";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=publish";


        }


    })

    $(".close-tingyong").click(function(){
        // if(typee=="reject") {//审批驳回
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=auditlist");
        //
        // }else if(typee=="seal"){//盖章驳回
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist");
        //
        // }else if(typee=="readed"){
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=readedlist");
        //
        // }else{
        //
        //     $(".div-tingyong").hide();
        // }

        if(typee=="reject"||typee=="seal"||typee=="readed") {//审批驳回

            if (isAndroid == true || isiOS == true) {
                window.history.back()
            } else {
                if(window!=window.top){
                    window.history.back()
                }else{
                    window.opener.location.reload();
                    window.close();
                }

            }

        }else {

            $(".div-tingyong").hide();
        }


    })
    // 立即盖章，调用君子签
    $(".toStamp").click(function(){
        document.location.href = basePath+"ea/androiddoc/ea_getSignUrl.jspa?document.docId="+docId;
        // var ulp = basePath
        //     + "ea/androiddoc/sajax_ea_getSignUrl.jspa";
        // $.ajax({
        //     type: "GET",
        //     url: ulp,
        //     async: false,
        //     dataType: "json",
        //     data: {
        //         "document.docId": docId
        //     },
        //     success: function (data) {
        //         var member = eval('(' + data + ')');
        //         var signUrl = member.signUrl;
        //         if(signUrl=="noauth"){
        //             $(".div-pro").show();
        //
        //         }else {
        //             if (signUrl != "") {
        //                var url =  signUrl + "&backUrl=" + encodeURI(basePath + "ea/androiddoc/ea_updateSealer.jspa?document.docId=" + docId);
        //                 document.location.href =  basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(url);
        //
        //
        //             }
        //         }
        //
        //
        //
        //     },
        //     error: function (data) {
        //
        //         console.log("获取链接失败");
        //     }
        //
        //
        // });


    })
    //立即完善
    $(".close-edit").click(function(){
        $(".div-pro").hide();
        document.location.href = basePath+"ea/mycenter/ea_getInfo.jspa";

    });


        //待审核驳回返回修改
  $(".reject").click(function(){
      var url =  basePath + "/ea/androiddoc/sajax_ea_examine.jspa";
      $(".loading").show();
      $.ajax({
          url:url,
          type:"get",
          dataType:"json",
          aysnc:false,
          data:{
               "document.docId":docId,
               "document.subscriberComment":"",
                 jump:"reject"

          },
          success:function(data){
              $(".loading").hide();
              $(".div-tip").show();
              typee = "reject";

          },
          error:function (data) {

          }


      })


  });

    // 驳回至审批
    $(".stampReject").click(function(){

        $(".loading").show();
        var url =  basePath + "/ea/androiddoc/sajax_ea_sealDocument.jspa";
        $.ajax({
            url:url,
            type:"get",
            dataType:"json",
            aysnc:false,
            data:{
                "document.docId":docId,
                "document.subscriberComment":"",
                jump:"noSeal"

            },
            success:function(data){
                $(".loading").hide();
                $(".div-tip").show();
                typee = "seal";
            },
            error:function (data) {

            }


        })


    });

   //审核通过并至盖章
    $(".adopt").click(function(){
          $(".seal").show();
    });
    //审核通过并至盖章 //审核通过并至盖章
    $(".adopt").click(function(){
        $(".seal").show();
    });
    $("#inp-close").click(function(){
        $(".share").hide();
        $(".div-zffs").hide();

    });

    
    $("#share-close").click(function(){
        $(".div-zffs").hide();



    });
   //分发文件选择多人
    $(".sec-zf").click(function(){
        document.location.href = basePath+"page/ea/main/office_ea/contract/selectMulti.jsp?docId="+docId;

    });

    //阅读转发选择多人
    $(".read-zf").click(function(){
        document.location.href = basePath+"page/ea/main/office_ea/contract/selectMulti.jsp?docId="+docId+"&typee=read";

    });
    //共享
    $(".read-share").click(function(){

        $(".share").show();

    })
    //确定共享
    $("#inp-share").click(function(){

        var url = basePath + "/ea/androiddoc/sajax_ea_setShare.jspa?date="
            + new Date();
        $(".loading").show();
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            data : {
                "docShare.docId" : docId,
                "docShare.shareType" : shareType
            },
            success : function(data) {
                $(".loading").hide();
                $(".share").hide();
                $(".div-tip").show();
                typee="readed";

            }
        });
        $(".div-tip").show();

    })




    //共享类型切换
    $(".input-share").click(function () {
        $(this).siblings("span").addClass("active");
        $(this).parents("li.clearfix").siblings("li").find("span").removeClass("active");


        shareType = $(this).attr("data-value");
    });

    //切换盖章人方式
    $(".input-male").click(function () {
        $(this).siblings("span").addClass("active");
        $(this).parents("li.clearfix").siblings("li").find("span").removeClass("active");


        zffs = $(this).attr("data-index");
        if(zffs==1){
          //  document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=approve";

        }
    });


    //切换盖章人方式确定提交
    $("#inp-yes").click(function () {
        //转自己盖章
        if(zffs=="0"){

            var url =  basePath + "/ea/androiddoc/sajax_ea_examine.jspa?date="
                + new Date();
            $.ajax({
                url:url,
                type:"get",
                dataType:"json",
                aysnc:false,
                data:{
                    "document.docId":docId,
                    jump:"selftoSeal"

                },
                success:function(data){
                    $(".div-tip").show();
                    $(".div-zffs").hide();
                    typee ="seal";


                },
                error:function (data) {

                }


            })
        }else{
            //审批通过转盖章

            if(module==""){

                document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=approve";

            }else{
                document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=approve";


            }
        }

    });

})
function completeRead(){


    var url = basePath + "/ea/androiddoc/sajax_ea_completeRead.jspa?date="
        + new Date();

    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : true,
        dataType : "json",
        data : {
            "document.docId" : docId
        },
        success : function(data) {

            // $(".div-tingyong").show();
            typee = "read";
        }
    });
}