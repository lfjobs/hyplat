var del = 0;
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
            document.location.href = basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&date=" + new Date();

        }else{
            window.open(basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=view&date=" + new Date());



        }



    })
//修改
    $(".edit").click(function(){
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
                + docId + "&type=draftupdate&poe=edit&opr="+opr+"&date=" + new Date();

        }else{
          window.open(basePath
                + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
                + docId + "&type=draftupdate&poe=edit&opr="+opr+"&date=" + new Date());



        }



    })
    //打印档案
    $(".print").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");

        document.location.href = basePath
            + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="
            + docId + "&type=draftupdate&poe=print&date=" + new Date();


    })
    //传阅
    $(".passDraft").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");
        
        if(module==""){

            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=p";

        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=p";


        }

    })

    //提交审核至领导审批
    $(".submitAudit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");

        if(module==""){
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee=A";


        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee=A";


        }

    })


    //删除
    $(".del").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var docId = $(".ul li.active").attr("id");
        $(".div-tingyong").show();
        $(".titlep").text("确定放入回收站？");
        del = 1;
        return false;


    })


    $(".close-tingyong").click(function(){
        del = 0;
        $(".div-tingyong").hide();
    })
    //确认放入回收站
    $(".close-confirm").click(function(){

        $(".div-tingyong").hide();
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        if(del==0){
            $li.remove();
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
                stage:"cg"
            },
            success: function (data) {

                $li.remove();


            },
            error: function (data) {
                console.log("失败")

            }


        });
    })
    $(".zyi").click(function(){
        $(".div-tingyong1").show();
    })
    $(".close-tingyong1").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        $(".div-tingyong1").hide();
    })


    $(".close-confirm1").click(function(){

        $(".div-tingyong1").hide();
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var docId = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");
        var ulp = basePath
            + "ea/androiddoc/sajax_ea_transferDoc.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                "document.docId": docId,
                module:(module=="doc"?"contract":"doc")
            },
            success: function (data) {
                $(".div-tingyong").show();
                $(".titlep").text("操作成功");

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

    var parameter = $.trim($("#search").val());


    var ulp = basePath
        + "ea/contract/sajax_ea_getDraftList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            parameter: parameter,
            state:"receivelist"
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


                        html.push("<div class='p-wq date-p' title='"+obj.passTime+"'>"+obj.passTime+"</div>");


                       if(obj.status=="R"){
                           html.push("<div class='hsw-p status-p' title='已驳回'>已驳回</div>");


                       }else{
                           html.push("<div class='csw-p status-p' title='传阅中'>传阅中</div>");

                       }


                        html.push("</div>");
                        html.push("<span style='display: none;' class='status'>" + obj.status + "</span>");
                        html.push("<span style='display: none;' class='companyName'>" + obj.companyName + "</span>");
                        html.push("<span style='display: none;' class='companyID'>" + obj.companyID + "</span>");
                        html.push("<span style='display: none;' class='scene'>" + obj.scene + "</span>");
                        html.push("<span style='display: none;' class='specificTemplate'>" + obj.specificTemplate + "</span>");


                        html.push("</li>");
                    }


                }
            }else{
                for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if($("#"+obj.docId).length==0) {
                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj.docId + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.docId + "'>");
                    }

                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");
                    html.push("<div class='title-div'>");
                    html.push("<p class='title'>" +obj.title + "</p>");
                    if(obj.status = "R") {
                        html.push("<p class='hsw-p'>已驳回</p>");
                    }else{
                        html.push("<p class='csw-p'>传阅中</p>");
                    }

                    html.push("<p class='p-wq'>" +obj.passTime + "</p>");

                    html.push("</div>");
                    html.push("<div class='draftor-div'><div>"+obj.companyName+"</div><div>"+obj.deptNameOfDraft+"</div><div>"+obj.drafterName+"</div></div>");
                    html.push("<span style='display: none;' class='status'>" + obj.status + "</span>");
                    html.push("<span style='display: none;' class='companyName'>" + obj.companyName + "</span>");
                    html.push("<span style='display: none;' class='companyID'>" + obj.companyID + "</span>");
                    html.push("<span style='display: none;' class='scene'>" + obj.scene + "</span>");
                    html.push("<span style='display: none;' class='specificTemplate'>" + obj.specificTemplate + "</span>");


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



