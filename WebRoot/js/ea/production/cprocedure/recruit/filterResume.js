
$(function () {
    $("#search").click(function(){
        var jobTitle = $("#inp-search").val();
        document.location.href = basePath+"ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&jobTitle="+jobTitle+"&back="+back;

    });

    $(".p-confirm").click(function(){
        var state = "";
        var jobTitle = "";
        $(".sec-con .ul1").find(".active").each(function () {
            state+=$.trim($(this).attr("state-data"))+",";
        })

        $(".sec-con .ul2").find(".active").each(function () {
            jobTitle+=$.trim($(this).text())+",";
        })




         if(state==""&&jobTitle==""){
             return false;
         }
        document.location.href = basePath+"ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&jobTitle="+jobTitle+"&state="+state+"&back="+back;

    });

});


function backs(){

    // document.location.href = basePath+"/ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&back="+back;
    window.history.go(-1);
    return false;

}

