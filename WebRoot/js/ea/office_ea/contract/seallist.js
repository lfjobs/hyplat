var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
    var reject = 0;


    $("#qsearch").click(function() {
        if($(window).width()>960) {
            $(".sec-list .ul li").not(":first").remove();
        }else{
            $(".sec-list .ul li").remove();

        }
        pageNumber = 0;
        pageCount = 0;
        load();
    });

    $("#search").bind('keyup', function() {

        var parameter = $("#search").val();
        if(parameter=="") {
            if ($(window).width() > 960) {
                $(".sec-list .ul li").not(":first").remove();
            } else {
                $(".sec-list .ul li").remove();

            }
            pageNumber = 0;
            pageCount = 0;
            load();
        }
    });


//查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");

        if (isAndroid == true || isiOS == true) {
            document.location.href = basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&date=" + new Date();


        }else{
            window.open(basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&date=" + new Date());



        }



    })

    // 驳回至审批
    $(".stampReject").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");
        var scene = $("li#"+docId).find(".scene").text();

        if (scene == "00") {
            $(".div-tip").show();
            $(".titlep").text("学员协议不可驳回");
            return false;

        }else{
            $(".div-tip").show();
            $(".titlep").text("确定驳回至审批吗？");
            reject = 1;
            return false;
        }




    });

    // 立即盖章，调用君子签
    $(".toStamp").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");


        var scene = $("li#"+docId).find(".scene").text();
        var status = $("li#"+docId).find(".status").text();

        $(".loading").show();
        if (scene == "00") {

            var companyID = $("li#"+docId).find(".companyID").text();
            var companyName = $("li#"+docId).find(".companyName").text();
            if (status == "K") {

                document.location.href = basePath + "ea/contract/ea_getFilePre.jspa?doc.scene=" + scene + "&doc.companyID=" + companyID + "&doc.docId=" + docId;
            } else if (status == "F") {
                document.location.href = basePath+"ea/contract/ea_viewUrl.jspa?doc.docId="+docId;
                //
                // var ulp = basePath
                //     + "ea/contract/sajax_ea_viewUrl.jspa";
                // $.ajax({
                //     type: "GET",
                //     url: ulp,
                //     async: false,
                //     dataType: "json",
                //     data: {
                //         "doc.docId": docId
                //     },
                //     success: function (data) {
                //         var member = eval('(' + data + ')');
                //         var viewUrl = member.viewUrl;
                //         document.location.href =  basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(viewUrl);
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
            }else if (status == "A") {

                document.location.href = basePath+"ea/contract/ea_getSignUrl.jspa?doc.scene="+scene+"&doc.companyID="+companyID+"&doc.docId="+docId;

                //发起签约
                // var ulp = basePath
                //     + "ea/contract/sajax_ea_getSignUrl.jspa";
                // $.ajax({
                //     type: "GET",
                //     url: ulp,
                //     async: false,
                //     dataType: "json",
                //     data: {
                //         "doc.scene": scene,
                //         "doc.companyID": companyID,
                //         "doc.docId": docId
                //     },
                //     success: function (data) {
                //         $(".loading").hide();
                //         var member = eval('(' + data + ')');
                //         var signUrl = member.signUrl;
                //         var docId = member.docId;
                //
                //         if(signUrl!="") {
                //
                //             var url =  signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId);
                //             document.location.href =  basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(url);
                //         }else{
                //             //请核实身份证号
                //             $(".div-card").show();
                //             $(".titles").text("无法打开文件请核对身份信息是否正确");
                //         }
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
            }
        }else {

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
            //         $(".loading").hide();
            //         var member = eval('(' + data + ')');
            //         var signUrl = member.signUrl;
            //         if (signUrl == "noauth") {
            //             $(".div-pro").show();
            //
            //         } else {
            //
            //             if (signUrl != "") {
            //
            //                 var  url =  signUrl + "&backUrl=" + encodeURI(basePath + "ea/androiddoc/ea_updateSealer.jspa?document.docId=" + docId);
            //
            //
            //                 if (isAndroid == true || isiOS == true) {
            //                     document.location.href = basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(url);
            //
            //
            //
            //                 }else{
            //                     window.open(basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(url));
            //
            //
            //
            //                 }
            //             }
            //         }
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
        }

    })
    //立即完善
    $(".close-edit").click(function(){
        $(".div-pro").hide();
         document.location.href = basePath+"ea/mycenter/ea_getInfo.jspa";

    });

    // 转交他人盖章签字
    $(".transferStamp").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");

        var scene = $("li#"+docId).find(".scene").text();
        if (scene == "00") {
            $(".div-tip").show();
            $(".titlep").text("学员协议不可转交");
            return false;

        }
        if(module==""){
            window.location.href = basePath + "page/ea/main/office_ea/contract/selectWldw.jsp?docId=" + docId + "&typee=seal";

        }else{
            window.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=seal";


        }


    })

    // 转至分发人
    $(".transferPublish").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");
        var scene = $("li#"+docId).find(".scene").text();
        if (scene == "00") {
            $(".div-tip").show();
            $(".titlep").text("学员协议不可转至分发");
            return false;

        }
        if(module==""){
            window.location.href = basePath + "page/ea/main/office_ea/contract/selectWldw.jsp?docId=" + docId + "&typee=publish";

        }else{
            window.location.href =basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=publish";


        }
    })


   //核对身份信息
    $(".close-check").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        $(".div-card").hide();

        var docId = $(".ul li.active").attr("id");
        document.location.href = basePath+"ea/contract/ea_checkInfo.jspa?doc.docId="+docId;

    });
    $(".close-tingyong").click(function () {

        if($(".titlep").text()=="操作成功"){
            document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist";

        }else {
            $(this).parents(".div-tingyong").hide();
        }

    })


    $(".close-confirm").click(function () {
        if($(".titlep").text()=="操作成功"){
            document.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist");

        }else {
            if(reject == 1){
                var li = $(".ul li.active").length;
                if(li<1){
                    return false;
                }


                reject = 0;

                var docId = $(".ul li.active").attr("id");
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
                        $(".div-tip").show();
                        $(".titlep").text("操作成功");
                    },
                    error:function (data) {

                    }


                })


            }else{
                $(".div-tingyong").hide();
            }
        }



    })


    $(window).scroll(function () {




        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        if(scroll>115){
            $(".sec-nav").addClass("nav");
        }else{
            $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last1").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                load();
            }
        }

    })



    //选中
    $(document).on("click",".sex",function(event){
        event.stopPropagation();
        if($(this).parents("li").is(".active")){
            $(this).parents("li").removeClass("active");
        }else{

            $(".ul .active").removeClass("active");

            $(this).parents("li").addClass("active");
        }
    })

    //预览
    $(document).on("click", ".ul li", function () {

        $(".ul .active").removeClass("active");

        $(this).addClass("active");

        $(".loading").show();
        var scene = $(this).find(".scene").text();
        var status = $(this).find(".status").text();

        var docId = $(this).attr("id");
        if (scene == "00") {

            var companyID = $(this).find(".companyID").text();
            var companyName = $(this).find(".companyName").text();
            if (status == "K") {

                $(".loading").hide();
                document.location.href = basePath + "ea/contract/ea_getFilePre.jspa?doc.scene=" + scene + "&doc.companyID=" + companyID + "&doc.docId=" + docId;
            } else if (status == "F") {
                document.location.href = basePath+"ea/contract/ea_viewUrl.jspa?doc.docId="+docId;


                // var ulp = basePath
                //     + "ea/contract/sajax_ea_viewUrl.jspa";
                // $.ajax({
                //     type: "GET",
                //     url: ulp,
                //     async: false,
                //     dataType: "json",
                //     data: {
                //         "doc.docId": docId
                //     },
                //     success: function (data) {
                //         var member = eval('(' + data + ')');
                //         var viewUrl = member.viewUrl;
                //         $(".loading").hide();
                //         document.location.href = viewUrl;
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
            }else if (status == "A") {
                //发起签约
                document.location.href = basePath+"ea/contract/ea_getSignUrl.jspa?doc.scene="+scene+"&doc.companyID="+companyID+"&doc.docId="+docId;

                // var ulp = basePath
                //     + "ea/contract/sajax_ea_getSignUrl.jspa";
                // $.ajax({
                //     type: "GET",
                //     url: ulp,
                //     async: false,
                //     dataType: "json",
                //     data: {
                //         "doc.scene": scene,
                //         "doc.companyID": companyID,
                //         "doc.docId": docId
                //     },
                //     success: function (data) {
                //         var member = eval('(' + data + ')');
                //         var signUrl = member.signUrl;
                //         var docId = member.docId;
                //         $(".loading").hide();
                //         if(signUrl!="") {
                //             document.location.href = signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId);
                //         }else{
                //                       //请核实身份证号
                //             $(".div-card").show();
                //             $(".titles").text("无法打开文件请核对身份信息是否正确");
                //         }
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
            }
        }
        else {
            var title = $(this).find(".title").text();

            var ulp = basePath
                + "ea/contract/sajax_ea_getPdfView.jspa";
            $.ajax({
                type: "GET",
                url: ulp,
                async: false,
                dataType: "json",
                data: {
                    "doc.docId": docId
                },
                success: function (data) {
                    var member = eval('(' + data + ')');
                    var pdfpath = member.pdfpath;
                    $(".loading").hide();
                    if (isAndroid == true || isiOS == true) {

                        document.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype=2&title="+title;


                    }else{
                        window.open(basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype=2&title="+title);



                    }

                },
                error: function (data) {
                    $(".loading").hide();
                    console.log("获取链接失败");
                }


            });

        }
    })




})
function load() {
    if(pageNumber==0){
        if($(window).width()>960) {
            $(".sec-list .ul li").not(":first").remove();
        }else{
            $(".sec-list .ul li").remove();

        }
    }
    pageNumber = pageNumber + 1;

    var parameter = $("#search").val();

    var ulp = basePath
        + "ea/contract/sajax_ea_getDealFileByState.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            parameter: parameter,
            module:module,
            state:"seallist"
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").removeClass("last1");
            var obj = "";
            if ($(window).width() > 960) {
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj[0]).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj[0] + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj[0] + "'>");
                        }
                        html.push("<div class='title-pc'>");
                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");

                        html.push("<div class='docNum-p' title='" + obj[7] + "'>"+((obj[7]==null||obj[7]=="")?'&nbsp;':obj[7])+"</div>");
                        html.push("<div class='docNum-p' title='" + obj[11] + "'>"+((obj[11]==null||obj[11]=="")?'&nbsp;':obj[11])+"</div>");

                        html.push("<div class='title-p' title='"+obj[1]+"'>"+((obj[1]==null||obj[1]=="")?'无':obj[1])+"</div>");
                        //
                        // html.push("<div class='theme-p' title='"+obj[8]+"'>"+((obj[8]==null||obj[8]=="")?'无':obj[8])+"</div>");

                        if (obj[9] == "aa") {
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        } else if (obj[9] == "bb") {
                            html.push("<div class='docType-p' title='董事长办公室文件'>董事长办公室文件</div>");

                        } else if (obj[9] == "cc") {
                            html.push("<div class='docType-p' title='总裁办公室文件'>总裁办公室文件</div>");
                        } else if (obj[9] == "dd") {
                            html.push("<div class='docType-p' title='总部人事处文件'>总部人事处文件</div>");
                        } else if (obj[9] == "ee") {
                            html.push("<div class='docType-p' title='总部办公室文件'>总部办公室文件</div>");
                        } else if (obj[9] == "ff") {
                            html.push("<div class='docType-p' title='总部财务处文件'>总部财务处文件</div>");
                        } else if (obj[9] == "gg") {
                            html.push("<div class='docType-p' title='总部教务(生产)处文件'>总部教务(生产)处文件</div>");
                        } else if (obj[9] == "hh") {
                            html.push("<div class='docType-p' title='总部营销处文件'>总部营销处文件</div>");
                        } else if (obj[9] == "ii") {
                            html.push("<div class='docType-p' title='总部服务(创收)平台'>总部服务(创收)平台</div>");
                        } else if (obj[9] == "jj") {
                            html.push("<div class='docType-p' title='总部教务部文件'>总部教务部文件</div>");
                        } else {
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        }

                        if (obj[10] == "p") {
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        } else if (obj[10] == "j") {
                            html.push("<div class='emergencyType-p' title='急件'>急件</div>");

                        } else if (obj[10] == "t") {
                            html.push("<div class='emergencyType-p' title='特急'>特急</div>");

                        } else {
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        }

                        html.push("<div class='com-p' title='" + ((obj[6] == null ||obj[6] == "")?'无' : obj[6]) + "'>" + ((obj[6] == null ||obj[6] == "") ? '无' : obj[6]) + "</div>");
                        html.push("<div class='dept-p' title='" + ((obj[5] == null||obj[5] == "") ? '无' : obj[5]) + "'>" + ((obj[5] == null||obj[5] == "") ? '无' : obj[5]) + "</div>");
                        html.push("<div class='draft-p' title='" + ((obj[4] == null||obj[4] == "") ? '无' : obj[4]) + "'>" + ((obj[4] == null||obj[4] == "") ? '无' : obj[4]) + "</div>");

                        html.push("<div class='p-wq date-p' title='" +  obj[2] + "'>" +  ((obj[2] == null||obj[2] == "") ? '无' : obj[2].substring(0, 10)) + "</div>");



                        html.push("</div>");

                        html.push("<span class='status' style='display: none;'>"+obj[3]+"</span>");

                        html.push("<span class='scene' style='display: none;'>"+obj[12]+"</span>");

                        html.push("<span class='companyID' style='display: none;'>"+obj[13]+"</span>");

                        html.push("<span class='companyName' style='display: none;'>"+obj[6]+"</span>");

                        html.push("</li>");
                    }


                }
            } else {
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj[0]).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj[0] + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj[0] + "'>");
                        }
                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");
                        html.push("<div class='title-div'>");
                        html.push("<p class='title'>" + obj[1] + "</p>");
                        html.push("<p class='p-wq'>" +((obj[2] == null||obj[2] == "") ? '' : obj[2].substring(0, 10)) + "</p>");
                        html.push("</div>");
                        html.push("<div class='draftor-div'>");
                        html.push("<div>" +((obj[6] == null ||obj[6] == "")?'&nbsp;' : obj[6]) + "</div>");
                        html.push("<div>" +((obj[5] == null ||obj[5] == "")?'&nbsp;' : obj[5]) + "</div>");
                        html.push("<div>" + obj[4] + "</div>");
                        html.push("</div>");
                        html.push("<span class='status' style='display: none;'>"+obj[3]+"</span>");
                        html.push("<span class='scene' style='display: none;'>"+obj[12]+"</span>");
                        html.push("<span class='companyID' style='display: none;'>"+obj[13]+"</span>");

                        html.push("<span class='companyName' style='display: none;'>"+obj[6]+"</span>");
                        html.push("</li>");


                    }
                }
            }
            $(".sec-list .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}



