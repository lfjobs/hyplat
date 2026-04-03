var filetype = 1;
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {


    //查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var delId = $(".ul li.active").attr("id");

        var docId = $("li#"+delId).find(".docId").val();

        if (isAndroid == true || isiOS == true) {
            document.location.href = basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&isRead=1";

        }else{
            window.open(basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&isRead=1");



        }

    })

    $(".restore").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        $(".div-tingyong").show();
        $(".titlep").text("确定还原？");


    });

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






    $(".close-tingyong").click(function(){
        if($(".titlep").text()=="操作成功"){
           // document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state=recylelist";
            window.location.reload();

        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })
    $(".close-confirm").click(function(){


        if($(".titlep").text()=="操作成功"){
            document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state=recylelist";

        }else {
            $(this).parents(".div-tingyong").hide();

            var delId = $(".ul li.active").attr("id");

            var delkey = $(".ul li.active").find(".delkey").val();

            var docId = $(".ul li.active").find(".docId").val();
            var url = basePath
                + "ea/documentcommon/sajax_ea_restoreDoc.jspa?date="
                + new Date();

            $.ajax({
                url : url,
                type : "get",
                async : false,
                dateType : "json",
                data : {
                    delkey : delkey,
                    docId:docId

                },
                success : function(data) {
                    $(".div-tingyong").show();
                    $(".titlep").text("操作成功");

                },
                error : function(data) {
                    alert("获取数据失败");
                }

            });


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
        $(".loading").show();
        var status = $(this).find(".status").text();

        var delId = $(this).attr("id");
        var docId = $("li#"+delId).find(".docId").val();

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
                    document.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype="+filetype+"&title="+title;


                }else{
                    window.open(basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype="+filetype+"&title="+title);



                }

            },
            error: function (data) {
                $(".loading").hide();
                console.log("获取链接失败");
            }


        });


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
            state:"recylelist"
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

                        html.push("<div class='docNum-p' title='" + obj[10] + "'>"+((obj[10]==null||obj[10]=="")?'无':obj[10])+"</div>");

                        html.push("<div class='title-p' title='"+obj[1]+"'>"+((obj[1]==null||obj[1]=="")?'无':obj[1])+"</div>");

                        // html.push("<div class='theme-p' title='"+obj[11]+"'>"+((obj[11]==null||obj[11]=="")?'无':obj[8])+"</div>");

                        if (obj[12] == "aa") {
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        } else if (obj[12] == "bb") {
                            html.push("<div class='docType-p' title='董事长办公室文件'>董事长办公室文件</div>");

                        } else if (obj[12] == "cc") {
                            html.push("<div class='docType-p' title='总裁办公室文件'>总裁办公室文件</div>");
                        } else if (obj[12] == "dd") {
                            html.push("<div class='docType-p' title='总部人事处文件'>总部人事处文件</div>");
                        } else if (obj[12] == "ee") {
                            html.push("<div class='docType-p' title='总部办公室文件'>总部办公室文件</div>");
                        } else if (obj[12] == "ff") {
                            html.push("<div class='docType-p' title='总部财务处文件'>总部财务处文件</div>");
                        } else if (obj[12] == "gg") {
                            html.push("<div class='docType-p' title='总部教务(生产)处文件'>总部教务(生产)处文件</div>");
                        } else if (obj[12] == "hh") {
                            html.push("<div class='docType-p' title='总部营销处文件'>总部营销处文件</div>");
                        } else if (obj[12] == "ii") {
                            html.push("<div class='docType-p' title='总部服务(创收)平台'>总部服务(创收)平台</div>");
                        } else if (obj[12] == "jj") {
                            html.push("<div class='docType-p' title='总部教务部文件'>总部教务部文件</div>");
                        } else {
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        }

                        if (obj[13] == "p") {
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        } else if (obj[13] == "j") {
                            html.push("<div class='emergencyType-p' title='急件'>急件</div>");

                        } else if (obj[13] == "t") {
                            html.push("<div class='emergencyType-p' title='特急'>特急</div>");

                        } else {
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        }

                        html.push("<div class='com-p' title='" + ((obj[9] == null ||obj[9] == "")?'无' : obj[9]) + "'>" + ((obj[9] == null ||obj[9] == "") ? '无' : obj[9]) + "</div>");
                        html.push("<div class='dept-p' title='" + ((obj[8] == null||obj[8] == "") ? '无' : obj[8]) + "'>" + ((obj[8] == null||obj[8] == "") ? '无' : obj[8]) + "</div>");
                        html.push("<div class='draft-p' title='" + ((obj[7] == null||obj[7] == "") ? '无' : obj[7]) + "'>" + ((obj[7] == null||obj[7] == "") ? '无' : obj[7]) + "</div>");

                        html.push("<div class='p-wq date-p' title='" +  obj[2] + "'>" +  ((obj[2] == null||obj[2] == "") ? '无' : obj[2].substring(0, 10)) + "</div>");



                        html.push("</div>");
                        html.push("<input type='hidden'  class='docId' value='"+obj[0]+"' />");
                        html.push("<input type='hidden'  class='delkey' value='"+ obj[4] +"' />");




                        html.push("</li>");
                    }


                }
            } else {
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj[0]).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj[3] + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj[3] + "'>");
                        }

                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");
                        html.push("<div class='title-div'>");
                        html.push("<p class='title'>" + obj[1] + "</p>");

                        html.push("<p class='p-wq'>" + obj[2].substring(0, 10) + "</p>");
                        html.push("</div>");
                        html.push("<input type='hidden'  class='docId' value='" + obj[0] + "' />");
                        html.push("<input type='hidden'  class='delkey' value='" + obj[4] + "' />");
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

