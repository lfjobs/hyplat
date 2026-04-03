$(document).ready(function () {

    //搜索查询
    $("#qsearch").click(function() {
        $(".ul-con").html("");
        var parameter = $.trim($("#search").val());
        console.log(parameter);
        var url =  basePath+"ea/enterprisestamp/sajax_n_ea_getStampList.jspa";
        $.ajax({
            url:url,
            type:"get",
            dataType:"json",
            aysnc:true,
            data:{
                parameter:parameter,
                search:"search"
            },
            success:function(data){
                var  me = eval("("+data+")");
                var  stamplist = me.stamplist;
                var html = new Array();
                for(var i = 0;i<stamplist.length;i++){
                    if($("li#"+stamplist[i].enterpriseStampID).length>0){
                            break;
                    }
                    html.push("<li class='clearfix' id='"+stamplist[i].enterpriseStampID+"'>");
                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");
                    html.push("<div class='div-left clearfix'>");
                    html.push("<p>"+stamplist[i].stampName+"</p>");
                    html.push("<p>责任人："+stamplist[i].responsibleName+"</p>");
                    html.push("</div>");
                    html.push("<div class='div-right'>");
                    html.push("<p>"+stamplist[i].stampType+"</p>");

                   // if(stamplist[i].useStatus=="unuse"){
                   //     html.push("<p>已停用</p>");
                   // }else  if(stamplist[i].auditStatus=="01"){
                   //     html.push("<p>审核中</p>");
                   // }else  if(stamplist[i].auditStatus=="02"){
                   //     html.push("<p>审核通过</p>");
                   // }else  if(stamplist[i].auditStatus=="03"){
                   //     html.push("<p>驳回</p>");
                   // }

                    html.push("<input type='hidden' value='"+stamplist[i].useStatus+"'  class='useStatus' />");
                    html.push("</div>");
                    html.push("</li>");
                }

                $(".ul-con").append(html.join(""));


            },
            error:function (data) {

            }


        })


    });
    //添加
    $(".li-add").click(function(){
        enterpriseStampID = "";
        $(".ul-con .active").removeClass("li-color");
        $(".ul-con .active").removeClass("active");

       if(companyID==null||companyID==""){
        if($(".ul-con li").length>0){
            $(".tycontent").text("个人印章只能添加1个");
            $(".div-tingyong").show();
            return false;
        }
       }
          document.location.href = basePath+"/ea/enterprisestamp/ea_getAddPage.jspa";
    })

    //编辑
    $(".li-edit").click(function(){
        if(enterpriseStampID!="") {
            document.location.href = basePath + "/ea/enterprisestamp/ea_getAddPage.jspa?enterpriseStamp.enterpriseStampID=" + enterpriseStampID;
        }
        })
    //确定停用
    $(".li-tingyong").click(function(){
        if(enterpriseStampID!="") {
            var useStatus = $("li#"+enterpriseStampID).find(".useStatus").val();
            if(useStatus=="unuse"){
                $(".tycontent").text("您确认要启用吗?");
            }else{
                $(".tycontent").text("  您确定要停用吗?");

            }
            $(".div-tingyong").show();
        }
    })
    $(".close-tingyong,.qrty").click(function(){
        $(this).parents(".div-tingyong").hide();
    })

    // //确认停用
    // $(".qrty").click(function(){
    //     if(enterpriseStampID!="") {
    //         var url =  basePath + "/ea/enterprisestamp/sajax_n_ea_setUseStamp.jspa";
    //         $.ajax({
    //             url:url,
    //             type:"get",
    //             dataType:"json",
    //             aysnc:false,
    //             data:{
    //                 sccId:sccId,
    //                 "enterpriseStamp.enterpriseStampID":enterpriseStampID,
    //             },
    //             success:function(data){
    //                 window.location.reload();
    //             },
    //             error:function (data) {
    //
    //             }
    //
    //
    //         })
    //     }
    // });
    //确定删除
    $(".li-shanchu").click(function(){
        if(enterpriseStampID!="") {
            $(".div-shanchu").show();
        }
    })
    $(".close-shanchu").click(function(){
        $(this).parents(".div-shanchu").hide();
    })
    //确认删除
    $(".qrdelete").click(function(){
        if(enterpriseStampID!="") {

            var url =  basePath + "/ea/enterprisestamp/sajax_n_ea_deleteStamp.jspa";
            $.ajax({
                url:url,
                type:"get",
                dataType:"json",
                aysnc:false,
                data:{
                    "enterpriseStamp.enterpriseStampID":enterpriseStampID,
                },
                success:function(data){
                       window.location.reload();
                },
                error:function (data) {

                }


            })

        }
    });
    //点击选中
    $(document).on("click",".ul-con li",function(event){
        $(this).siblings().removeClass("li-color");
        $(this).addClass("li-color");


        if($(this).is(".active")){
            $(this).removeClass("active");
            $(this).removeClass("li-color");
            enterpriseStampID ="";
        }else{
            enterpriseStampID = $(this).attr("id");
            $(".ul-con .active").removeClass("active");

            $(this).addClass("active");
        }
    })


})
