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
        findOvertimeApply(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateOvertimeApply(params.get("id"));
        });
    }

}

function updateOvertimeApply(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/overtimeReview/sajax_ea_updateOvertimeApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "overtimeApply.key": id,
            "overtimeApply.status": $("#reviewStatus").val()
        },
        success: function(data) {
            alert("加班审核提交成功");
            findOvertimeApply(id);
        },
        error: function () {
            alert("加班审核提交失败");
        }
    });
}

function findOvertimeApply(id){
    var url = basePath + "ea/overtimeReview/sajax_ea_findOvertimeApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "overtimeApply.key": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var overtimeApply = member.dtMyovertime;
                if (overtimeApply != null) {
                    $("#staffname").html(overtimeApply.staffname);
                    $("#applyDate").html(overtimeApply.applyDate);
                    var startDate = overtimeApply.overTimeStartDate;
                    if(null != startDate){
                        if(startDate.indexOf(" ") == -1){
                            $("#overtimeWay").html("按整天");
                        }else{
                            $("#overtimeWay").html("按小时");
                        }
                    }
                    $("#startTime").html(overtimeApply.overTimeStartDate);
                    $("#endTime").html(overtimeApply.overTimeEndDate);
                    $("#overtimeHour").html(overtimeApply.overTimeHour);
                    $("#overtimeReason").html(overtimeApply.overTimeReason);
                    if(overtimeApply.status=="01"){
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }else if(overtimeApply.status=="02"){
                        $("#status").html("已审核");
                        $("#reviewStatus").val("02");
                    }else if(overtimeApply.status=="03"){
                        $("#status").html("驳回");
                        $("#reviewStatus").val("03");
                    }else{
                        $("#status").html("未审核");
                        $("#reviewStatus").val("-1");
                    }
                    $("#reviewer").html(overtimeApply.auditor);
                    if(overtimeApply.status=="01"){
                        $("#reviewTimeLi").hide();
                    }else{
                        $("#reviewTime").html(overtimeApply.auditTime);
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

