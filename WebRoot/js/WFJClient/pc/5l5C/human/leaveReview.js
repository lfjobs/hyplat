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
        findLeaveApply(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateLeaveApply(params.get("id"));
        });
    }

}

function updateLeaveApply(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/leaveReview/sajax_ea_updateLeaveApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "leaveApply.key": id,
            "leaveApply.status": $("#reviewStatus").val()
        },
        success: function(data) {
            alert("请假审核提交成功");
            findLeaveApply(id);
        },
        error: function () {
            alert("请假审核提交失败");
        }
    });
}

function findLeaveApply(id){
    var url = basePath + "ea/leaveReview/sajax_ea_findLeaveApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "leaveApply.key": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var leaveApply = member.dtMyleave;
                if (leaveApply != null) {
                    $("#staffname").html(leaveApply.staffname);
                    $("#applyDate").html(leaveApply.applyDate);
                    $("#leaveType").html(leaveApply.leaveType);
                    var leaveStartDate = leaveApply.leaveStartDate;
                    if(null != leaveStartDate){
                        if(leaveStartDate.indexOf(" ") == -1){
                            $("#leaveWay").html("按整天");
                        }else{
                            $("#leaveWay").html("按小时");
                        }
                    }
                    $("#startTime").html(leaveApply.leaveStartDate);
                    $("#endTime").html(leaveApply.leaveEndDate);
                    $("#leaveHour").html(leaveApply.leaveHour);
                    $("#leaveReason").html(leaveApply.leaveReason);
                    if(leaveApply.status=="01"){
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }else if(leaveApply.status=="02"){
                        $("#status").html("已审核");
                        $("#reviewStatus").val("02");
                    }else if(leaveApply.status=="03"){
                        $("#status").html("驳回");
                        $("#reviewStatus").val("03");
                    }else{
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }
                    $("#reviewer").html(leaveApply.auditor);
                    if(leaveApply.status=="01"){
                        $("#reviewTimeLi").hide();
                    }else{
                        $("#reviewTime").html(leaveApply.auditTime);
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

