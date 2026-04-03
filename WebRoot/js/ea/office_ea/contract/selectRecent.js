$(document).ready(function () {
    $("#search").focus();
    $(".sec-bottom").click(function () {

        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }

        if(typee=="") {
            var htmlstr= new Array();
            $(".ul-list li.active").each(function () {
                var receiverID = $(this).attr("id");
                var receiverDeptID = orgID;
                var receiverCompanyID = companyID;
                var staffname = $(this).attr("staffname");
                var imgsrc = $("li#"+receiverID).find(".div-img").find("img").attr("src");

                htmlstr.push("<li data-receiverID='"+receiverID+"' data-receiverCompanyID='"+receiverCompanyID+"' data-receiverDeptID='"+receiverDeptID+"'>");
                htmlstr.push("<div class='z-div'>");
                htmlstr.push("<img class='del-btn' src='"+basePath+"images/ea/office/contract/del.png'>")
                htmlstr.push("<img class='h-btn' src='"+imgsrc+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                htmlstr.push("<p>"+staffname+"</p>");
                htmlstr.push("</div>");
                htmlstr.push("</li>");
            })

            $(window.parent.document).find(".ul-list").append(htmlstr.join(""));

            $(window.parent.document).find(".iframecom").hide();
        }else {

            var receiverID = $(".ul-list li.active").attr("id");
            var receiverDeptID="";
            var receiverCompanyID = "";
            if(orgID!=null&&orgID!="") {
                 receiverDeptID = orgID;
            }else{
                receiverDeptID = $(".ul-list li.active").find(".orgID").attr("orgid-data");
            }

            if(companyID!=null&&companyID!="") {
                receiverCompanyID = companyID;
            }else{
                receiverCompanyID = $(".ul-list li.active").find(".comID").attr("comid-data");
            }

            var ulp = "";
            if (typee == "p" || typee == "A") {//传阅和提交审批
                if (typee == "p") {
                    ulp = basePath
                        + "ea/androiddoc/sajax_ea_passDraftDocuments.jspa";

                } else if (typee == "A") {
                    ulp = basePath
                        + "ea/androiddoc/sajax_ea_createDocument.jspa";

                }
                $(".loading").show();
                $.ajax({
                    type: "GET",
                    url: ulp,
                    async: false,
                    dataType: "json",
                    data: {
                        docId: docId,
                        "document.receiverID": receiverID,
                        "document.receiverDeptID": receiverDeptID,
                        "document.receiverCompanyID": receiverCompanyID

                    },
                    success: function (data) {
                        var m = data.result;
                        if (m == "suc") {
                            $(".loading").hide();
                            $(".div-tingyong").show();
                        }


                    },
                    error: function (data) {

                        console.log("获取链接失败");
                    }


                });
            } else if (typee == "approve" || typee == "transfer") {//审批通过去盖章||转交他人审批
                var url = basePath + "/ea/androiddoc/sajax_ea_examine.jspa";
                $(".loading").show();
                $.ajax({
                    url: url,
                    type: "get",
                    dataType: "json",
                    aysnc: false,
                    data: {
                        "document.docId": docId,
                        "document.subscriberComment": "",
                        "document.receiverID": receiverID,
                        "document.receiverDeptID": receiverDeptID,
                        "document.receiverCompanyID": receiverCompanyID,
                        jump: typee

                    },
                    success: function (data) {
                        var m = data.result;
                        if (m == "success") {
                            $(".loading").hide();
                            $(".div-tingyong").show();
                        }
                    },
                    error: function (data) {

                    }


                })

            } else if (typee == "publish" || typee == "seal") {
                var url = basePath + "/ea/androiddoc/sajax_ea_sealDocument.jspa";
                $(".loading").show();
                $.ajax({
                    url: url,
                    type: "get",
                    dataType: "json",
                    aysnc: false,
                    data: {
                        "document.docId": docId,
                        "document.receiverID": receiverID,
                        "document.receiverDeptID": receiverDeptID,
                        "document.receiverCompanyID": receiverCompanyID,
                        jump: typee

                    },
                    success: function (data) {
                        $(".loading").hide();
                        $(".div-tingyong").show();



                    },
                    error: function (data) {

                    }


                })

            }else if(typee=="xr"){
                      //签章人用于选人
                var receiverID = $(".ul-list li.active").attr("id");
                var staffname = $(".ul-list li.active").attr("staffname");

                $(window.parent.document).find("#responsibleID").val(receiverID);
                $(window.parent.document).find("#responsibleName").val(staffname);

                $(window.parent.document).find(".iframecom").hide();
            }
        }

    })
    getMember();









  //选中
    $(document).on("click",".ul-list li",function(){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{
            if(typee!="") {
                $(".ul-list .active").removeClass("active");
            }
            $(this).addClass("active");
        }
    })


    $(".close-tingyong").click(function(){

        if (typee == "p"||typee == "A") {
            window.location.replace(basePath+"ea/contract/ea_getDraftList.jspa?state=draftlist");

        }else  if (typee == "approve" || typee == "transfer"){
            window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=auditlist");

        }else  if (typee == "publish" || typee == "seal"){
            window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist");

        }
    })

})


function getMember(){


    $.ajax({
        url:basePath+"ea/androiddoc/sajax_ea_getRecentInfo.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var m = eval("("+data+")");
                var arry = m.list;
                if(arry==null){
                    return false;
                }

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

                $(".ul-list").html(htmlstr.join(""));
            }



        },
        error:function (data) {

        }

    });
}

