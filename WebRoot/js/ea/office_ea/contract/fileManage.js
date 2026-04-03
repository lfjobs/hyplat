var filetype = 1;
$(document).ready(function () {
    deakFileload(1);

     //本地上传
     $("#bd").click(function(){
         $('.con').hide();
         document.location.href = basePath+"page/ea/main/office_ea/contract/bdUploadFile.jsp?opr=bd";


     });
    //在线编辑
    $("#lineedit").click(function(){
        $('.con').hide();
        document.location.href = basePath+"page/ea/main/office_ea/contract/bdUploadFile.jsp?opr=edit";


    });
     $(".draft").click(function(){

         // if(module==null||module=="") {
         //
         //     var url = basePath + "ea/android/sajax_ea_findConpany.jspa";
         //     $.ajax({
         //         url: url,
         //         type: "POST",
         //         dataType: "json",
         //         aysnc: false,
         //         data: {
         //             sccId: sccId
         //         },
         //         success: function (data) {
         //             var comlist = data.company;
         //             if (comlist.length > 0) {
         //                 document.location.href = basePath + "page/WFJClient/pc/5l5C/selectCompany.jsp?sccId=" + sccId + "&bd=bd";
         //             } else {
         //                 document.location.href = basePath + "page/ea/main/office_ea/contract/bdUploadFile.jsp";
         //             }
         //
         //
         //         },
         //         error: function (data) {
         //
         //         }
         //     })
         // }else{
         //
         //     document.location.href = basePath + "page/ea/main/office_ea/contract/bdUploadFile.jsp";
         //
         // }

         //    document.location.href = basePath+"page/ea/main/office_ea/contract/bdUploadFile.jsp";
         if($(".con").css("display")=="none"){
             $('.con').show();
         }else{

             $('.con').hide();
         }

     });

    $("#search").bind('input propertychange', function() {
        $(".sec-list .ul").html("");
        pageNumber = 0;
        pageCount = 0;
        if (filetype == 1) {
            load();
        } else {
            deakFileload(2);
        }
    });
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        var Top = $(".last1").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 20) {

            if(filetype==1) {

                if (pageNumber < pageCount) {
                    load();
                }

            }else{
                if (pageNumber1 < pageCount1) {
                    deakFileload(2);
                }

            }



        }

    })

    //我的文件
    $("#myfile").click(function(){
        filetype = 1;
        $(".sec-nav .active").removeClass("active");
        $(this).addClass("active");
        $(".sec-list .ul").html("");
        pageNumber = 0;
        pageCount  = 0;
        load();

    });
      //待处理文件
    $("#dealfile").click(function(){
        filetype = 2;
        $(".sec-nav .active").removeClass("active");
        $(this).addClass("active");
        $(".sec-list .ul").html("");
        pageNumber1 = 0;
        pageCount1  = 0;
        deakFileload(2);

    });


    //预览
    $(document).on("click", ".ul li", function () {
        var scene = $(this).find(".scene").text();
        var status = $(this).find(".status").text();

        var docId = $(this).attr("id");
        if (scene == "00") {

            var companyID = $(this).find(".companyID").text();
            var companyName = $(this).find(".companyName").text();
            if (status == "K") {


                document.location.href = basePath + "ea/contract/ea_getFilePre.jspa?doc.scene=" + scene + "&doc.companyID=" + companyID + "&doc.docId=" + docId;
            } else if (status == "F") {


                var ulp = basePath
                    + "ea/contract/sajax_ea_viewUrl.jspa";
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
                        var viewUrl = member.viewUrl;

                        document.location.href = viewUrl;


                    },
                    error: function (data) {

                        console.log("获取链接失败");
                    }


                });
            }else if (status == "A") {
                //发起签约

                var ulp = basePath
                    + "ea/contract/sajax_ea_getSignUrl.jspa";
                $.ajax({
                    type: "GET",
                    url: ulp,
                    async: false,
                    dataType: "json",
                    data: {
                        "doc.scene": scene,
                        "doc.companyID": companyID,
                       "doc.docId": docId
                    },
                    success: function (data) {
                        var member = eval('(' + data + ')');
                        var signUrl = member.signUrl;
                        var docId = member.docId;
                        if(signUrl!="") {
                            document.location.href = signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId);
                        }


                    },
                    error: function (data) {

                        console.log("获取链接失败");
                    }


                });
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

                    document.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype="+filetype+"&title="+title;


                },
                error: function (data) {

                    console.log("获取链接失败");
                }


            });

        }
    })



    if(deal=="deal"){
        $("#dealfile").trigger("click");

    }

})
function load() {
    pageNumber = pageNumber + 1;

    var parameter = $("#search").val();

    var ulp = basePath
        + "ea/contract/sajax_ea_getMyFileList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            parameter: parameter,
            module:module
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
            $(".last1").remove();
            var obj = "";
            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if (i == pageForm.list.length - 1) {

                    html.push("<li class='clearfix last1' id='" + obj.docId + "'>");
                } else {
                    html.push("<li class='clearfix' id='" + obj.docId + "'>");
                }

                html.push("<p class='title'>" + obj.title + "</p>");
                html.push("<span style='display: none;' class='status'>" + obj.status + "</span>");
                html.push("<span style='display: none;' class='companyName'>" + obj.companyName + "</span>");
                html.push("<span style='display: none;' class='companyID'>" + obj.companyID + "</span>");
                html.push("<span style='display: none;' class='scene'>" + obj.scene + "</span>");
                if (obj.status == "A") {

                    html.push("<p class='p-wq'>待签约</p>");
                } else if (obj.status == "R") {

                    html.push("<p class='p-bh'>已驳回</p>");
                } else if (obj.status == "U") {

                    html.push("<p class='p-bh'>不批准</p>");
                } else if (obj.status == "F") {

                    html.push("<p class='p-cg'>已签约</p>");
                } else if (obj.status == "P") {

                    html.push("<p class='p-wq'>待分发</p>");
                } else if (obj.status == "S"||obj.status == "T") {

                    html.push("<p class='p-wq'>待审批</p>");
                } else if (obj.status == "I") {

                    html.push("<p class='p-cg'>草稿</p>");
                } else if (obj.status == "O") {

                    html.push("<p class='p-bh'>待阅读</p>");
                } else if (obj.status == "K") {

                    html.push("<p class='p-dzf'>待支付</p>");
                } else {
                    html.push("<p class='p-cg'>已阅读</p>");
                }
                html.push("</li>");


            }
            $(".sec-list .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}


function deakFileload(t) {
    pageNumber1 = pageNumber1 + 1;

    var parameter = $("#search").val();

    var ulp = basePath
        + "ea/contract/sajax_ea_getDealFileList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber1,
            ajax: "ajax",
            parameter: parameter,
            module:module
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber1 = pageForm.pageNumber;
            pageCount1= pageForm.pageCount;
            recordCount1 = pageForm.recordCount;
            if(pageNumber1==1) {
                $(".dcnum").text("("+recordCount1+")");
                if(t==1){
                    return false;
                }
            }
            $(".last1").remove();
            var obj = "";
            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if (i == pageForm.list.length - 1) {

                    html.push("<li class='clearfix last1' id='" + obj[0]+ "'>");
                } else {
                    html.push("<li class='clearfix' id='" + obj[0] + "'>");
                }

                html.push("<p class='title'>" + obj[1] + "</p>");
               html.push("<span style='display:none;' class='status'>" +obj[3] + "</span>");
                // html.push("<span style='display: none;' class='companyName'>" + obj.companyName + "</span>");
                // html.push("<span style='display: none;' class='companyID'>" + obj.companyID + "</span>");
                // html.push("<span style='display: none;' class='scene'>" + obj.scene + "</span>");
                if (obj[3] == "A") {

                    html.push("<p class='p-wq'>待签约</p>");
                }else if (obj[3] == "P") {

                    html.push("<p class='p-wq'>待分发</p>");
                } else if (obj[3] == "S"||obj[3] == "T") {

                    html.push("<p class='p-wq'>待审批</p>");
                }  else if (obj[3] == "O") {

                    html.push("<p class='p-bh'>待阅读</p>");
                }else if (obj[3] == "I") {

                    html.push("<p class='p-bh'>传阅中</p>");
                }
                html.push("</li>");


            }
            $(".sec-list .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}