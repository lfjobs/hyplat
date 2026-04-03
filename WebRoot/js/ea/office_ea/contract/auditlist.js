var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
var reject = 0;
    var zffs = "0";
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

    //待审核驳回返回修改
    $(".reject").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }


        $(".div-tingyong").show();
        $(".titlep").text("确定驳回吗？");
        reject = 1;
        return false;



    });

    //审核通过并至盖章
    $(".adopt").click(function(){
        var li = $(".ul li.active").length;
        if (li < 1) {
            return false;
        }
        var docId = $(".ul li.active").attr("id");

        $(".seal").show();
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

    $("#inp-close").click(function(){
        $(".div-zffs").hide();

    });
    //切换盖章人方式确定提交
    $("#inp-yes").click(function () {

        var li = $(".ul li.active").length;
        if (li < 1) {
            return false;
        }
        var docId = $(".ul li.active").attr("id");
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
                    $(".div-tingyong").show();
                    $(".div-zffs").hide();


                },
                error:function (data) {

                }


            })
        }else{
            //审批通过转盖章
            if(module==""){
                window.location.href=basePath + "page/ea/main/office_ea/contract/selectWldw.jsp?docId=" + docId + "&typee=approve";

            }else{
                window.location.href=basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=approve";


            }

        }

    });
    //转交他人审批
    $(".transfer").click(function () {
        var li = $(".ul li.active").length;
        if (li < 1) {
            return false;
        }
        var docId = $(".ul li.active").attr("id");

        if(module==""){
            window.location.href = basePath + "page/ea/main/office_ea/contract/selectWldw.jsp?docId=" + docId + "&typee=transfer";

        }else{
            window.location.href = basePath + "page/ea/main/office_ea/contract/selectCompany.jsp?docId=" + docId + "&typee=transfer";


        }

    })


    $(".close-tingyong").click(function () {

        if($(".titlep").text()=="操作成功"){
           // document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state=auditlist";
            window.location.reload();
        }else {
            $(this).parents(".div-tingyong").hide();
        }

    })




    $(".close-confirm").click(function () {
        if($(".titlep").text()=="操作成功"){
         //   document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state=auditlist";
            window.location.reload();
        }else {
            if(reject = 1){
                var li = $(".ul li.active").length;
                if(li<1){
                    return false;
                }


                reject = 0;

                var docId = $(".ul li.active").attr("id");
                var url =  basePath + "/ea/androiddoc/sajax_ea_examine.jspa";
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
                        $(".div-tingyong").show();
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

        if (scroll > 115) {
            $(".sec-nav").addClass("nav");
        } else {
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

        var docId = $(this).attr("id");

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

                    document.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath=" + pdfpath + "&docId=" + docId + "&status=" + status + "&filetype=2&title=" + title;


                }else{
                    window.open(basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath=" + pdfpath + "&docId=" + docId + "&status=" + status + "&filetype=2&title=" + title);



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
    if (pageNumber == 0) {
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
            module: module,
            state: "auditlist"
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
            recordCount1 = pageForm.recordCount;
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

                        html.push("<div class='docNum-p' title='" + obj[7] + "'>"+((obj[7]==null||obj[7]=="")?'无':obj[7])+"</div>");

                        html.push("<div class='title-p' title='"+obj[1]+"'>"+((obj[1]==null||obj[1]=="")?'无':obj[1])+"</div>");

                        html.push("<div class='theme-p' title='"+obj[8]+"'>"+((obj[8]==null||obj[8]=="")?'无':obj[8])+"</div>");

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
                        html.push("<span class=;status' style='display: none;'>"+obj[3]+"</span>");


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


function timestampToTime(d) {
    if (d == null || d == "") {
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';

    return Y + M + D;//不显示时分秒
}
