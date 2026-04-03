'use strict';

$(document).ready(function () {
    loadData();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");


});

function loadData(){
    $("#reviewTimeLi").hide();
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        findOutworkApply(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateOutworkApply(params.get("id"));
        });
    }

}

function updateOutworkApply(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/outworkReview/sajax_ea_updateOutworkApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "outworkApply.key": id,
            "outworkApply.status": $("#reviewStatus").val()
        },
        success: function(data) {
            alert("外勤审核提交成功");
            findOutworkApply(id);
        },
        error: function () {
            alert("外勤审核提交失败");
        }
    });
}

function findOutworkApply(id){
    var url = basePath + "ea/outworkReview/sajax_ea_findOutworkApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "outworkApply.key": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var outworkApply = member.dtMytravel;
                if (outworkApply != null) {
                    $("#staffname").html(outworkApply.staffname);
                    $("#applyDate").html(outworkApply.applyDate);
                    var startDate = outworkApply.travelStartDate;
                    if(null != startDate){
                        if(startDate.indexOf(" ") == -1){
                            $("#outworkWay").html("按整天");
                        }else{
                            $("#outworkWay").html("按小时");
                        }
                    }
                    $("#startTime").html(outworkApply.travelStartDate);
                    $("#endTime").html(outworkApply.travelEndDate);
                    $("#outworkHour").html(outworkApply.travelHours);
                    $("#outworkReason").html(outworkApply.travelcause);
                    if(outworkApply.status=="01"){
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }else if(outworkApply.status=="02"){
                        $("#status").html("已审核");
                        $("#reviewStatus").val("02");
                    }else if(outworkApply.status=="03"){
                        $("#status").html("驳回");
                        $("#reviewStatus").val("03");
                    }else{
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }
                    $("#reviewer").html(outworkApply.auditor);
                    if(outworkApply.status=="01"){
                        $("#reviewTimeLi").hide();
                    }else{
                        $("#reviewTime").html(outworkApply.auditTime);
                        $("#reviewTimeLi").show();
                    }
                }
            }
        },
        error: function () {
        }
    });
}

function check(){
    if(null == $("#reviewStatus").val() || undefined == $("#reviewStatus").val() || "-1" == $("#reviewStatus").val()){
        alert("请选择审核结果");
        return false;
    }
    return true;
}

