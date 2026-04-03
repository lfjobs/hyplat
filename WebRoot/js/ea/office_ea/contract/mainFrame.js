$(document).ready(function () {
    $("#mainframe").attr("height",$(window).height());
    $(window).resize(function () {
        if($(window).width()>=960){
            $(".main_main").attr("style","height:"+($(window).height()-10)+"px;width:18%; float:left;border: 1px solid #e3dbdb;");
        }else{
            $(".main_main").attr("style","height:"+($(window).height()-10)+"px;width:100%;");

        }
    });
    if($(window).width()>=960){
        $(".main_main").attr("style","height:"+($(window).height()-10)+"px;width:18%; float:left;border: 1px solid #e3dbdb;");
    }else{
        $(".main_main").attr("style","height:"+($(window).height()-10)+"px;width:100%;");

    }

    var result1 = 0, result2 = 0, result3 = 0, result4 = 0;
    var result5 = 0, result6 = 0;

    // 查询草稿箱个数，收件箱个数(包括驳回和传阅)
    urlPath = basePath
        + "ea/documentinfo/sajax_ea_getCountsByDraftList2.jspa?data="
        + Math.random();
    $.ajax({
        url : urlPath,
        type : "get",
        dataType : 'json',
        async : true,
        success : function scanback(data) {
            var member = eval("(" + data + ")");
            var counts = member.result;
            result1 = counts[0];
            result2 = counts[1];
            $("#navigation #draft").text("("+result1+")");
            $("#navigation #receivebox").text("("+result2+")");
        },
        error : function scanback(data) {

        }
    });
    // 查询待审批，待盖章，待分发，待阅读个数
    var urlPath = basePath
        + "ea/documentflow/sajax_n_ea_checkUnFinishedDocument2.jspa?data="
        + Math.random();
    $.ajax({
        url : urlPath,
        type : "get",
        dataType : 'json',
        async : true,
        data : {
            type : "examine"
        },
        success : function scanback(data) {
            var member = eval("(" + data + ")");
            result3 = member.result;
            $("#navigation #approvalno").text("("+result3+")");

        },
        error : function scanback(data) {

        }
    });

    urlPath = basePath
        + "ea/documentflow/sajax_n_ea_checkUnFinishedDocument2.jspa?data="
        + Math.random();
    $.ajax({
        url : urlPath,
        type : "get",
        async : true,
        dataType : 'json',
        data : {
            type : "seal"
        },
        success : function scanback(data) {
            var member = eval("(" + data + ")");
            result4 = member.result;
            $("#navigation #stampno").text("("+result4+")");
        },
        error : function scanback(data) {

        }
    });

    urlPath = basePath
        + "ea/documentflow/sajax_n_ea_checkUnFinishedDocument2.jspa?data="
        + Math.random();
    $.ajax({
        url : urlPath,
        type : "get",
        dataType : 'json',
        async : true,
        data : {
            type : "publish"
        },
        success : function scanback(data) {
            var member = eval("(" + data + ")");
            result5 = member.result;
            $("#navigation #publish").text("("+result5+")");
        },
        error : function scanback(data) {

        }
    });
    urlPath = basePath
        + "ea/documentflow/sajax_n_ea_checkUnFinishedDocument2.jspa?data="
        + Math.random();
    $.ajax({
        url : urlPath,
        type : "get",
        dataType : 'json',
        async : true,
        data : {
            type : "read"
        },
        success : function scanback(data) {
            var member = eval("(" + data + ")");
            result6 = member.result;
            $("#navigation #read").text("("+result6+")");
        },
        error : function scanback(data) {

        }
    });

    // setInterval(function() {
    //     var FormObj = document.getElementById("mainframe").contentWindow;
    //     var total = FormObj.document.getElementById("totals");
    //     if (total != null) {
    //         total.innerText = Number(result1) + Number(result2)
    //             + Number(result3) + Number(result4)
    //             + Number(result5) + Number(result6);
    //
    //     }
    //
    // }, 100);
});


$(".loading").hide();
function tonclick(id) {

   /* $(".loading").show();*/
    if (id == "draftlist") {
        // $("#mainframe").attr(
        //     "src",
        //     basePath + "ea/documentinfo/ea_getDocDraftList.jspa?data="
        //     + Math.random()+"&journalNum="+journalNum+"&projectName="+encodeURI(projectName));

        if($(window).width()>960){
   $("#mainframe").attr(
                "src",
       basePath+"ea/contract/ea_getDraftList.jspa?data="
                + Math.random()+"&state=draftlist");
            $(".loading").hide();
        }else{
            if(ifr=="nohead"){
                top.location = basePath + "ea/contract/ea_getDraftList.jspa?state=draftlist";
            }else {
                document.location.href = basePath + "ea/contract/ea_getDraftList.jspa?state=draftlist";
            }

        }



    }else if (id == "receivelist") {
        if ($(window).width() > 960) {
            $("#mainframe").attr(
                "src",
                basePath + "ea/contract/ea_getDraftList.jspa?data="
                + Math.random() + "&state=receivelist");
        } else {

            if(ifr=="nohead"){
                top.location = basePath + "ea/contract/ea_getDraftList.jspa?state=receivelist";
            }else {
                document.location.href = basePath + "ea/contract/ea_getDraftList.jspa?state=receivelist";
            }
        }
    }else if (id == "sendedlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDraftList.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDraftList.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDraftList.jspa?state="+id;
            }

        }
    }else if (id == "auditlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "auditedlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "seallist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "sealedlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "stamp") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/enterprisestamp/ea_getStampList.jspa");
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/enterprisestamp/ea_getStampList.jspa";
            }else {
                document.location.href = basePath+"ea/enterprisestamp/ea_getStampList.jspa";
            }
        }
    }else if (id == "publishlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "publishedlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "readlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id== "readedlist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id+"&companyID="+companyID);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id+"&companyID="+companyID;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id+"&companyID="+companyID;
            }
        }
    }else if (id == "archivelist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/androiddoc/ea_getArchiveTree.jspa?data="
                + Math.random()+"&companyID="+companyID);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/androiddoc/ea_getArchiveTree.jspa?companyID="+companyID;
            }else {
                document.location.href = basePath+"ea/androiddoc/ea_getArchiveTree.jspa?companyID="+companyID;
            }
        }
    }else if (id == "sharelist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDraftList.jspa?data="
                + Math.random()+"&state=sharelist");
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDraftList.jspa?state=sharelist";
            }else {
                document.location.href = basePath+"ea/contract/ea_getDraftList.jspa?state=sharelist";
            }
        }
    }else if (id == "recylelist") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"ea/contract/ea_getDealFileByState.jspa?data="
                + Math.random()+"&state="+id);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }else {
                document.location.href = basePath+"ea/contract/ea_getDealFileByState.jspa?state="+id;
            }
        }
    }else if (id == "template") {

        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"/ea/androiddoc/ea_getDocTempTree.jspa?data="
                + Math.random()+"&module="+module);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"/ea/androiddoc/ea_getDocTempTree.jspa?module="+module;
            }else {
                document.location.href = basePath+"/ea/androiddoc/ea_getDocTempTree.jspa?module="+module;
            }
        }

    }else if (id == "sharetemp") {

        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"page/ea/main/office_ea/contract/shareTempTree.jsp?module="+module);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"page/ea/main/office_ea/contract/shareTempTree.jsp?module="+module;
            }else {
                document.location.href = basePath+"page/ea/main/office_ea/contract/shareTempTree.jsp?module="+module;
            }
        }

    }else if (id == "templateType") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"/ea/androiddoc/ea_getTempTypeTree.jspa?data="
                + Math.random()+"&module="+module);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"/ea/androiddoc/ea_getTempTypeTree.jspa?module="+module;
            }else {
                document.location.href = basePath+"ea/androiddoc/ea_getTempTypeTree.jspa?module="+module;
            }
        }
    }else if (id == "archivecate") {
        if($(window).width()>960){
            $("#mainframe").attr(
                "src",
                basePath+"/ea/androiddoc/ea_getArchiveTypeTree.jspa?data="
                + Math.random()+"&companyID="+companyID);
        }else{
            if(ifr=="nohead"){
                top.location = basePath+"/ea/androiddoc/ea_getArchiveTypeTree.jspa?companyID="+companyID;
            }else {
                document.location.href = basePath+"ea/androiddoc/ea_getArchiveTypeTree.jspa?companyID="+companyID;
            }
        }
    }

}
