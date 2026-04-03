
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


$(document).ready(function () {
    $("#search").focus();
    $(".sec-bottom").click(function () {

        var li = $(".ul-list li.active").length;
        if(li<1){
            return false;
        }

        if(type=="") {
            var htmlstr= new Array();
            $(".ul-list li.active").each(function () {
                var receiverID = $(this).attr("id");
                var receiverDeptID = orgID;
                var receiverCompanyID = companyID;
                var staffname = $(this).attr("staffname");
                var imgsrc = $("li#"+receiverID).find(".div-img").find("img").attr("src");

                htmlstr.push("<li data-receiverID='"+receiverID+"' data-receiverCompanyID='"+receiverCompanyID+"' data-receiverDeptID='"+receiverDeptID+"' data-source='01'>");
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
            if (type == "cy" || type == "commitApprove") {//传阅和提交审批
                if (type == "cy") {
                    ulp = basePath
                        + "ea/scBudget/sajax_ea_circularizeBudgetBill.jspa";

                } else if (type == "commitApprove") {
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
                        keyId: keyId,
                        "receiverID": receiverID,
                        "receiverDeptID": receiverDeptID,
                        "receiverCompanyID": receiverCompanyID,
                        source:"04"//初始项目——集团内部最近联系人

                    },
                    success: function (data) {
                        var message = JSON.parse(data);
                        if (message.result == "suc") {
                            $(".loading").hide();
                            $(".titlep").text("操作成功");
                            $(".div-tingyong").show();
                            if(tenantFlag=="personal"){
                                window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=personal&billsType="+billsType;
                            }else{
                                window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=other&billsType="+billsType;
                            }
                        }

                    },
                    error: function (data) {
                        console.log("获取链接失败");
                    }


                });
            } else if (type == "approve" || type == "transfer") {//审批通过去盖章||转交他人审批
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
                        jump: type,
                        source:"04"//初始项目——集团内部最近联系人

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

            } else if (type == "publish" || type == "seal") {
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
                        jump: type,
                        source:"04"//初始项目——集团内部最近联系人

                    },
                    success: function (data) {
                        $(".loading").hide();
                        $(".div-tingyong").show();



                    },
                    error: function (data) {

                    }


                })

            }else if(type=="L"){//第三方
                //   window.location.href=basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?type=L&docRelateOther.title=测试第三方&docRelateOther.source=yj&docRelateOther.htmlUrl=page/WFJClient/pc/earth/test.jsp&docRelateOther.tableName=Staff&docRelateOther.idName=staffID&docRelateOther.stateName=price&docRelateOther.stateValue=1111";
                var title = localStorage.getItem("title");
                var source = localStorage.getItem("source");
                var htmlUrl = localStorage.getItem("htmlUrl");
                var tableName = localStorage.getItem("tableName");
                var idName = localStorage.getItem("idName");
                var idValue = localStorage.getItem("idValue");
                var stateName = localStorage.getItem("stateName");
                var stateValue = localStorage.getItem("stateValue");
                var refundValue = localStorage.getItem("refundValue");
                var url = basePath + "/ea/androiddoc/sajax_ea_toAuditOthers.jspa";

                $.ajax({
                    url: url,
                    type: "get",
                    dataType: "json",
                    aysnc: false,
                    data: {
                        "docRelateOther.title": title,
                        "docRelateOther.source": source,
                        "docRelateOther.htmlUrl": htmlUrl,
                        "docRelateOther.tableName": tableName,
                        "docRelateOther.idName": idName,
                        "docRelateOther.idValue":idValue,
                        "docRelateOther.stateName":stateName,
                        "docRelateOther.stateValue":stateValue,
                        "docRelateOther.refundValue":refundValue,
                        "document.receiverID": receiverID,
                        "document.receiverDeptID": receiverDeptID,
                        "document.receiverCompanyID": receiverCompanyID,
                        source:"04"//初始项目——集团内部最近联系人


                    },
                    success: function (data) {
                        $(".loading").hide();
                        $(".div-tingyong").show();



                    },
                    error: function (data) {

                    }
                })


            }else if(type=="xr"){
                      //签章人用于选人
                var receiverID = $(".ul-list li.active").attr("id");
                var staffname = $(".ul-list li.active").attr("staffname");

                $(window.parent.document).find("#responsibleID").val(receiverID);
                $(window.parent.document).find("#responsibleName").val(staffname);

                $(window.parent.document).find(".iframecom").hide();
            }
        }

    })
   if(companyID!=null&&companyID!=""){
    getMember(companyID,orgID);
   }else{
       getRecentInfo();
   }



    $("#qsearch").click(function() {
        var parameter = $("#search").val();
        if($.trim(parameter)==""){

            return false;
        }
        if(parameter.length<2){
            alert("请输入长度大于等于2的搜索条件");
            return false;
        }

        $(".ul-list").html("");
        $(".navrecent").hide();
        getMember(companyID,orgID);

    })
    //搜索查询
    // $("#search").bind('input propertychange', function(){
    //     var parameter = $("#search").val();
    //     if($.trim(parameter)==""){
    //
    //         return false;
    //     }
    //     if(parameter.length<2){
    //         return false;
    //     }
    //
    //     $(".ul-list").html("");
    //     getMember(companyID,orgID);
    //
    // });




  //选中
    $(document).on("click",".ul-list li",function(){

        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{
            if(type!="") {
                $(".ul-list .active").removeClass("active");
            }
            $(this).addClass("active");
        }
    })


    $(".close-tingyong").click(function(){

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
        // if (type == "p"||type == "A") {
        //     window.location.replace(basePath+"ea/contract/ea_getDraftList.jspa?state=draftlist");
        //
        // }else  if (type == "approve" || type == "transfer"){
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=auditlist");
        //
        // }else  if (type == "publish" || type == "seal"){
        //     window.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist");
        //
        // }
    })

})


function getMember(companyID,orgID){
    var parameter = $("#search").val();

    $.ajax({
        url:basePath+"ea/android/video_getPersonList.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            companyID:companyID,
            orgID:orgID,
            sccid:sccid,
            parameter:parameter
        },
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var arry = data.result;
                if(arry==null||arry.length==0){
                    $(".resulttip").show();
                    getRecentInfo();
                    return false;
                }
                $(".resulttip").hide();
                for(var i=0;i<arry.length;i++){
                    htmlstr.push("<li class='clearfix' id='"+arry[i].staffID+"'  staffname='"+arry[i].staffName+"'>");

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+arry[i].headimage+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");
                    if((arry[i].companyname==null||arry[i].companyname=="")&&(orgID==null||orgID=="")){
                        htmlstr.push("用户-");
                    }else{
                        htmlstr.push("员工-");
                    }
                    if(arry[i].tel==null||arry[i].tel==""){
                        htmlstr.push(arry[i].staffName);
                    }else {
                        htmlstr.push(arry[i].staffName + "(" + arry[i].tel + ")");
                    }
                    htmlstr.push("</p>")

                    if(orgID==null||orgID==""){
                        if(arry[i].organizationname!=null&&arry[i].organizationname!="") {
                            htmlstr.push("<p orgid-data='"+arry[i].organizationid+"' class='orgID'>");
                            htmlstr.push(arry[i].organizationname);
                            htmlstr.push("</p>")
                        }
                        if(arry[i].companyname!=null&&arry[i].companyname!="") {
                            htmlstr.push("<p comid-data='"+arry[i].companyid+"' class='comID'>");
                            htmlstr.push(arry[i].companyname);
                            htmlstr.push("</p>")
                        }


                    }


                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
            }
            // console.log(data);
            // console.log("--");
            // console.log(parameter);


        },
        error:function (data) {

        }

    });
}


function getRecentInfo(){


    $.ajax({
        url:basePath+"ea/androiddoc/sajax_ea_getRecentInfo.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            source:"04"
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

                $(".ul-list").html(htmlstr.join(""));
            }



        },
        error:function (data) {

        }

    });
}

