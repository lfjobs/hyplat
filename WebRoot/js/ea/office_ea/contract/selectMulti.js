var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
    getRecentInfo();
    $(".close-tingyong").click(function(){
        // if(typee=="") {
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=publishlist");
        //
        //
        // }else if(typee=="republish"){
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=publishedlist");
        //
        // }else if(typee=="read"){
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=readlist");
        //
        // }else if(typee=="readed"){
        //
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=readedlist");
        //
        // }
        window.history.back();
        // if (isAndroid == true || isiOS == true) {
        //       window.history.go(-2);
        // } else {
        //     if(window.parent==window.top){
        //         window.history.go(-2);
        //     }else{
        //         window.history.go(-1);
        //     }
        //
        // }
    })

    $("#iframe").attr("height",$(window).height());
     //添加按钮
    $(".plus").click(function(){
        $("#overlay").addClass("overlay");
        if(module==""){
            $("#iframe").attr("src",basePath+"page/ea/main/office_ea/contract/selectWldw.jsp");
            
        }else{
            $("#iframe").attr("src",basePath+"page/ea/main/office_ea/contract/selectCompany.jsp");


        }
        $(".iframecom").show();


    })
    //选中
    $(document).on("click",".ul-list2 li",function(){
        var receiverID = $(this).attr("id");
        if($(this).is(".active")){
            $(this).removeClass("active");

            $('[data-receiverid="'+receiverID+'"]').remove();



        }else{
            $(this).addClass("active");


            var htmlstr= new Array();



                var receiverDeptID = $(".ul-list2 li.active").find(".orgID").attr("orgid-data");


                var   receiverCompanyID = $(".ul-list2 li.active").find(".comID").attr("comid-data");

                var staffname = $(this).attr("staffname");
                var imgsrc = $("li#"+receiverID).find(".div-img").find("img").attr("src");

                htmlstr.push("<li data-receiverID='"+receiverID+"' data-receiverCompanyID='"+receiverCompanyID+"' data-receiverDeptID='"+receiverDeptID+"' data-source='01'>");
                htmlstr.push("<div class='z-div'>");
                htmlstr.push("<img class='del-btn' src='"+basePath+"images/ea/office/contract/del.png'>")
                htmlstr.push("<img class='h-btn' src='"+imgsrc+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                htmlstr.push("<p>"+staffname+"</p>");
                htmlstr.push("</div>");
                htmlstr.push("</li>");


           $(".ul-list").append(htmlstr.join(""));






        }
    })
    $(document).on("click",".ul-list .del-btn",function(){

        $(this).parents("li").remove();
       var  receiverid = $(this).parents("li").attr("data-receiverid");
        $(".ul-list2 li#"+receiverid).removeClass("active");

    })
    //确认分发
    $(".sec-bottom").click(function(){
        var readers = "";
        var dreaders = "";
        $(".ul-list li").each(function () {
            var receiverID = $(this).attr("data-receiverID");
            var receiverCompanyID = $(this).attr("data-receiverCompanyID");
            if(receiverCompanyID==undefined||receiverCompanyID=="undefined"){
                receiverCompanyID = "";
            }
            var receiverDeptID = $(this).attr("data-receiverDeptID");
            if(receiverDeptID==undefined||receiverDeptID=="undefined"){
                receiverDeptID = "";
            }
            var telphone = $(this).attr("data-telphone");
            var dyj = $(this).attr("data-dyj");

            var source = $(this).attr("data-source");
            if(dyj=="1"){
                if(telphone!="") {
                    dreaders += receiverID+"-"+telphone+"-"+source+",";
                }
            }else{
                readers+=receiverID+"-"+receiverCompanyID+"-"+receiverDeptID+"-"+source+",";



            }


        })
        readers = readers.substring(0, readers.length - 1);

        dreaders = dreaders.substring(0, dreaders.length - 1);
        if(readers==""&&dreaders==""){
            return false;
        }

        if(typee==""){
            var url =  basePath + "/ea/androiddoc/sajax_ea_publishDocument.jspa";
            $.ajax({
                url:url,
                type:"get",
                dataType:"json",
                aysnc:false,
                data:{
                    "document.docId":docId,
                    readers:readers,
                    dreaders:dreaders

                },
                success:function(data){
                    $(".div-tingyong").show();
                },
                error:function (data) {

                }


            })
        }else{

            var url2 = basePath
                + "/ea/androiddoc/sajax_ea_rePublishDocument.jspa";
            $.ajax({
                url : encodeURI(url2),
                type : "post",
                async : false,
                dataType : "json",
                data : {
                    readers : readers,
                    docId : docId,
                    dreaders:dreaders
                },
                success : function cbf(data) {
                    var member = eval("(" + data + ")");
                    var re = member.result;

                    if (re == "") {
                        $(".titlep").text("发送成功");
                    }
                    else if (re == "fail") {
                        $(".titlep").text("文档已结束任务无法再发送");
                    } else {
                        $(".titlep").text(re + "已存在该文档，无需再分发，其余已发送成功！");
                    }
                    $(".div-tingyong").show();
                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
        }


    })

});



function getRecentInfo(){


    $.ajax({
        url:basePath+"ea/androiddoc/sajax_ea_getRecentInfoByOpr.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            oprState:"pub"
        },
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var m = eval("("+data+")");
                var arry = m.list;
                if(arry==null){
                    return false;
                    $(".navrecent").hide();
                }
                $(".navrecent").show();
                for(var i=0;i<arry.length;i++){
                    htmlstr.push("<li class='clearfix' id='"+arry[i][0]+"'  staffname='"+arry[i][3]+"'>");

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+arry[i][6]+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");
                    if((arry[i][5]==null||arry[i][5]=="")&&(orgID==null||orgID=="")){
                        htmlstr.push("用户-");
                    }else{
                        htmlstr.push("员工-");
                    }
                    if(arry[i][7]==null||arry[i][7]==""){
                        htmlstr.push(arry[i][3]);
                    }else {
                        htmlstr.push(arry[i][3] + "(" + arry[i][7] + ")");
                    }
                    htmlstr.push("</p>")

                    if(orgID==null||orgID==""){
                        if(arry[i][4]!=null&&arry[i][4]!="") {
                            htmlstr.push("<p orgid-data='"+arry[i][1]+"' class='orgID'>");
                            htmlstr.push(arry[i][4]);
                            htmlstr.push("</p>")
                        }
                        if(arry[i][5]!=null&&arry[i][5]!="") {
                            htmlstr.push("<p comid-data='"+arry[i][2]+"' class='comID'>");
                            htmlstr.push(arry[i][5]);
                            htmlstr.push("</p>")
                        }


                    }


                    htmlstr.push("</li>")


                }

                $(".ul-list2").html(htmlstr.join(""));
            }



        },
        error:function (data) {

        }

    });
}

