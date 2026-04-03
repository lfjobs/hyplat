var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {


    getRecentInfo();

     //收件人公司输入
    // document.getElementById("sjrgs").addEventListener("compositionend",function(e){
    $('#sjrgs').bind('input propertychange', function() {

        $("#staffid").val("");
        $("#orgid").val("");
        $("#comid").val("");
        $("#sjr").val("");
        $("#staffname").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<4){
                  return false;

        }
            var url = basePath
                + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
                + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(url),
                type: "post",
                async: true,
                dataType: "json",
                data:{
                    "document.companyName":$p.val()
                },
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var companylist = member.companylist;
                    var str = "";
                    for (var i = 0; i < companylist.length; i++) {
                        var obj = companylist[i];
                        str += "<li class='company' id='" + obj.companyID + "'><p>" + obj.companyName + "</p></li>";
                    }
                    $(".ul-list").html(str);
                    if(str!="") {
                        $(".sec-ul").css({
                            position: "absolute",
                            left: left - 12,
                            top: top + 45
                        }).show();
                    }

                },
                error: function cbf(data) {
                    console.log("数据获取失败！")
                }
            });

    });


   //收件人输入
   //  document.getElementById("sjr").addEventListener("compositionend",function(e){
   $('#sjr').bind('input propertychange', function() {
        $("#staffid").val("");
        $("#staffname").val("");
        if($("#comid").val()==""){
            $(".titlep").text("请先填写收件人公司");
            $(".div-tingyong").show();
            $("#sjr").val("");
            return false;
        }

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<2){
            return false;

        }

        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllPeople.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.drafterName":$p.val(),
                "document.companyID":$("#comid").val()
            },
            success: function cbf(data) {

                var member = eval("(" + data + ")");
                var plist = member.plist;
                // var str = "";
                // for (var i = 0; i < plist.length; i++) {
                //     var obj = plist[i];
                //     str += "<li id='" + obj[1] + "'  class='"+obj[3]+"'>" + obj[4]+"—"+obj[0]+"("+obj[2]+")</li>";
                // }

                var htmlstr = new Array();

                for(var i=0;i<plist.length;i++){
                    var obj = plist[i];
                    htmlstr.push("<li class='clearfix staff' id='"+obj[1]+"'  staffname='"+obj[0]+"'>");

                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+obj[8]+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p orgid-data='"+obj[3]+"'>");

                    var orgname = obj[4];
                    if(orgname.length>7){
                        orgname=orgname.substring(0,7);
                    }
                    htmlstr.push(orgname+"-");

                    if(obj[2]==null||obj[2]==""){
                        htmlstr.push(obj[0]);
                    }else {
                        htmlstr.push(obj[0] + "(" + obj[2] + ")");
                    }
                    htmlstr.push("</p>")




                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
                if(plist.length!=0) {
                    $(".sec-ul").css({
                        position: "absolute",
                        left: left - 12,
                        top: top + 45
                    }).show();
                }
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });
    });




    //选中
    $(document).on("click",".ul-list2 li",function(){

        if($(this).is(".active")){
            $(this).removeClass("active");

        }else{
            if(type!="") {
                $(".ul-list2 .active").removeClass("active");
            }

            $(this).addClass("active");

        }
    })




    //确定提交

    $(".sec-bottom").click(function () {

        var receiverID = $("#staffid").val();
        var receiverDeptID = $("#orgid").val();
        var receiverCompanyID = $("#comid").val();
        var staffname = $("#staffname").val();


        if(staffname!=""&&receiverID==""){
            $(".titlep").text("该公司查无此人");
            $(".div-tingyong").show();
            return false;
        }else   if((staffname==""||receiverID=="")&&$(".ul-list2 li.active").length==0){
            return false;

        }

        if(type=="") {
            var htmlstr= new Array();
            if(staffname!=""&&receiverID!=""){


                htmlstr.push("<li data-receiverID='"+receiverID+"' data-receiverCompanyID='"+receiverCompanyID+"' data-receiverDeptID='"+receiverDeptID+"' data-source='02'>");
                htmlstr.push("<div class='z-div'>");
                htmlstr.push("<img class='del-btn' src='"+basePath+"images/ea/office/contract/del.png'>")
                htmlstr.push("<img class='h-btn' src='"+basePath+"images/ea/production/head2x.png'>");
                htmlstr.push("<p>"+staffname+"</p>");
                htmlstr.push("</div>");
                htmlstr.push("</li>");
            }


            $(".ul-list2 li.active").each(function () {
                var receiverID = $(this).attr("id");
                var  receiverDeptID=$(this).find(".orgID").attr("orgid-data");
                var  receiverCompanyID = $(this).find(".comID").attr("comid-data");
                var staffname = $(this).attr("staffname");
                var imgsrc = $("li#" + receiverID).find(".div-img").find("img").attr("src");

                htmlstr.push("<li data-receiverID='" + receiverID + "' data-receiverCompanyID='" + receiverCompanyID + "' data-receiverDeptID='" + receiverDeptID + "' data-source='02'>");
                htmlstr.push("<div class='z-div'>");
                htmlstr.push("<img class='del-btn' src='" + basePath + "images/ea/office/contract/del.png'>");
                htmlstr.push("<img class='h-btn' src='" + imgsrc + "' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                htmlstr.push("<p>" + staffname + "</p>");

                htmlstr.push("</div>");
                htmlstr.push("</li>");
            })



            $(window.parent.document).find(".ul-list").append(htmlstr.join(""));

            $(window.parent.document).find(".iframecom").hide();
        }else {
             if($(".ul-list2 li.active").length>0){
                 receiverID = $(".ul-list2 li.active").attr("id");
                 receiverDeptID=$(".ul-list2 li.active").find(".orgID").attr("orgid-data");
                 receiverCompanyID = $(".ul-list2 li.active").find(".comID").attr("comid-data");

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
                        source:"05"//初始项目——往来公司最近联系人

                    },
                    success: function (data) {
                        var message =  JSON.parse(data);
                        if (message.result == "suc") {
                            $(".loading").hide();
                            $(".titlep").text("操作成功");
                            $(".div-tingyong").show();
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
                        source:"05"//初始项目——往来公司最近联系人

                    },
                    success: function (data) {
                        var m = data.result;
                        if (m == "success") {
                            $(".loading").hide();
                            $(".titlep").text("操作成功");
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
                        source:"05"//初始项目——往来公司最近联系人

                    },
                    success: function (data) {
                        $(".loading").hide();
                        $(".titlep").text("操作成功");
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
                        source:"05"//初始项目——往来公司最近联系人


                    },
                    success: function (data) {
                        $(".loading").hide();
                        $(".div-tingyong").show();



                    },
                    error: function (data) {

                    }
                })


            }
        }

    })


    //选中公司
    $(document).on("click",".ul-list li.company",function(){

        $("#comid").val($(this).attr("id"));

        $("#sjrgs").val($(this).find("p").text());
        $(".sec-ul").hide();

    })


  //选中人员
    $(document).on("click",".ul-list li.staff",function(){

        $("#staffid").val($(this).attr("id"));

        $("#sjr").val($(this).text());
        $("#staffname").val($(this).attr("staffname"));
        $("#orgid").val($(this).find("p").attr("orgid-data"));
        $(".sec-ul").hide();
    })

   //提示确定
    $(".close-tingyong").click(function(){
        if($(".titlep").text()=="操作成功") {
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

        }else{
            $(".div-tingyong").hide();
        }
    })

})


//返回
function back(){
    window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?menuId="+menuId;

}

function getRecentInfo(){
    $.ajax({
        url:basePath+"ea/androiddoc/sajax_ea_getRecentInfo.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            source:"05"//初始项目——往来公司最近联系人
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


