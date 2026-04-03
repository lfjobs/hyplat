var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {

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
            parent.document.location.href = basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&isRead=1&date=" + new Date();

        }else{
            parent.window.open(basePath
            + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
            + docId + "&type=draftupdate&poe=view&isRead=1&date=" + new Date());



        }
    })

    //转发
    $(".trans").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");
        var specificTemplate = $(".ul li.active").find(".specificTemplate").text();
        var opr = "";
        if(specificTemplate==""||specificTemplate==null){
            opr= "bd";
        }else{
            opr = "edit";

        }
        if (isAndroid == true || isiOS == true) {
            document.location.href = basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=edit&opr="+opr+"&date=" + new Date()+"&trans=1";

        }else{
            window.open(basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=edit&opr="+opr+"&date=" + new Date()+"&trans=1");



        }



    })

    //删除
    $(".del").click(function(){
        $(".div-tingyong").show();
        $(".titlep").text("确定放入回收站？");
        return false;


    })


    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
    })
    //确认放入回收站
    $(".close-confirm").click(function(){

        $(".div-tingyong").hide();
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");
        var ulp = basePath
            + "ea/androiddoc/sajax_ea_putRecycleBin.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "document.docId": docId,
                stage:"reject"
            },
            success: function (data) {

                $li.remove();


            },
            error: function (data) {
                console.log("失败")

            }


        });
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
                    parent.document.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype=2&title="+title+"&isRead=1";


                }else{
                    parent. window.open(basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath="+pdfpath+"&docId="+docId+"&status="+status+"&filetype=2&title="+title+"&isRead=1");




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
        + "ea/androiddoc/sajax_ea_getAllFiles.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            parameter: parameter
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


            if($(window).width()>960) {
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj.docId).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj.docId + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj.docId + "'>");
                        }
                        html.push("<div class='title-pc'>");
                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");

                        html.push("<div class='docNum-p' title='"+obj.docNum+"'>"+obj.docNum+"</div>");
                        html.push("<div class='title-p' title='"+obj.title+"'>"+((obj.title==null||obj.title=="")?'无':obj.title)+"</div>");

                        // html.push("<div class='theme-p' title='"+obj.theme+"'>"+((obj.theme==null||obj.theme=="")?'无':obj.theme)+"</div>");

                        if(obj.docType=="aa"){
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        }else if(obj.docType=="bb"){
                            html.push("<div class='docType-p' title='董事长办公室文件'>董事长办公室文件</div>");

                        }else if(obj.docType=="cc"){
                            html.push("<div class='docType-p' title='总裁办公室文件'>总裁办公室文件</div>");
                        }else if(obj.docType=="dd"){
                            html.push("<div class='docType-p' title='总部人事处文件'>总部人事处文件</div>");
                        }else if(obj.docType=="ee"){
                            html.push("<div class='docType-p' title='总部办公室文件'>总部办公室文件</div>");
                        }else if(obj.docType=="ff"){
                            html.push("<div class='docType-p' title='总部财务处文件'>总部财务处文件</div>");
                        }else if(obj.docType=="gg"){
                            html.push("<div class='docType-p' title='总部教务(生产)处文件'>总部教务(生产)处文件</div>");
                        }else if(obj.docType=="hh"){
                            html.push("<div class='docType-p' title='总部营销处文件'>总部营销处文件</div>");
                        }else if(obj.docType=="ii"){
                            html.push("<div class='docType-p' title='总部服务(创收)平台'>总部服务(创收)平台</div>");
                        }else if(obj.docType=="jj"){
                            html.push("<div class='docType-p' title='总部教务部文件'>总部教务部文件</div>");
                        }else{
                            html.push("<div class='docType-p' title='董事会会议决定文件'>董事会会议决定文件</div>");
                        }

                        if(obj.emergencyType=="p"){
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        }else if(obj.emergencyType=="j"){
                            html.push("<div class='emergencyType-p' title='急件'>急件</div>");

                        }else if(obj.emergencyType=="t"){
                            html.push("<div class='emergencyType-p' title='特急'>特急</div>");

                        }else{
                            html.push("<div class='emergencyType-p' title='普通'>普通</div>");
                        }
                        html.push("<div class='com-p' title='" + ((obj.companyName == null ||obj.companyName == "")?'无' : obj.companyName) + "'>" + ((obj.companyName == null ||obj.companyName == "") ? '无' : obj.companyName) + "</div>");
                        html.push("<div class='dept-p' title='" + ((obj.deptNameOfDraft == null||obj.deptNameOfDraft == "") ? '无' : obj.deptNameOfDraft) + "'>" + ((obj.deptNameOfDraft == null||obj.deptNameOfDraft == "") ? '无' : obj.deptNameOfDraft) + "</div>");
                        html.push("<div class='draft-p' title='" + ((obj.drafterName == null||obj.drafterName == "") ? '无' : obj.drafterName) + "'>" + ((obj.drafterName == null||obj.drafterName == "") ? '无' : obj.drafterName) + "</div>");


                        html.push("<div class='p-wq date-p' title='"+obj.updateTime+"'>"+((obj.updateTime == null||obj.updateTime == "") ? '无' : obj.updateTime)+"</div>");


                        if (obj.status == "A") {

                            html.push("<div class='p-wq status-p' title='待签约'>待签约</div>");
                        } else if (obj.status == "R") {

                            html.push("<div class='p-bh status-p' title='已驳回'>已驳回</div>");
                        } else if (obj.status == "U") {

                            html.push("<div class='p-bh status-p' title='不批准'>不批准</div>");
                        } else if (obj.status == "F") {

                            html.push("<div class='p-cg status-p' title='已签约'>已签约</div>");
                        } else if (obj.status == "P") {

                            html.push("<div class='p-wq status-p' title='待分发'>待分发</div>");
                        } else if (obj.status == "S" || obj.status == "T") {

                            html.push("<div class='p-wq status-p' title='待审批'>待审批</div>");
                        } else if (obj.status == "I") {

                            html.push("<div class='p-cg status-p' title='传阅中'>传阅中</div>");
                        } else if (obj.status == "O") {

                            html.push("<div class='p-bh status-p' title='待阅读'>待阅读</div>");
                        } else if (obj.status == "K") {

                            html.push("<div class='p-dzf status-p' title='待支付'>待支付</div>");
                        } else if(obj.status=="y") {
                            html.push("<div class='p-cg status-p' title='邀请注册尚未接收'>邀请</div>");
                        }else {
                            html.push("<div class='p-cg status-p' title='已阅读'>已阅读</div>");
                        }


                        html.push("</div>");


                        html.push("</li>");
                    }


                }
            }else {
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj.docId).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj.docId + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj.docId + "'>");
                        }

                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");
                        html.push("<p class='title'>" + obj.title + "</p>");
                        html.push("<span style='display: none;' class='status'>" + obj.status + "</span>");
                        html.push("<span style='display: none;' class='companyName'>" + obj.companyName + "</span>");
                        html.push("<span style='display: none;' class='companyID'>" + obj.companyID + "</span>");
                        html.push("<span style='display: none;' class='scene'>" + obj.scene + "</span>");
                        html.push("<span style='display: none;' class='specificTemplate'>" + obj.specificTemplate + "</span>");


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
                        } else if (obj.status == "S" || obj.status == "T") {

                            html.push("<p class='p-wq'>待审批</p>");
                        } else if (obj.status == "I") {

                            html.push("<p class='p-cg'>传阅中</p>");
                        } else if (obj.status == "O") {

                            html.push("<p class='p-bh'>待阅读</p>");
                        } else if (obj.status == "K") {

                            html.push("<p class='p-dzf'>待支付</p>");
                        } else if (obj.status == "y") {

                            html.push("<p class='p-dzf'>邀请</p>");
                        } else {
                            html.push("<p class='p-cg'>已阅读</p>");
                        }
                        html.push("<p class='p-wq'>" + obj.updateTime + "</p>");

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
    if(d==null||d==""){
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())   + ' ';

    return Y+M+D;//不显示时分秒
}
