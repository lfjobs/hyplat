'use strict';

$(document).ready(function () {
    loadData();
    $(".checkOn-top").attr("style", "overflow: auto;height:" + $(window).height() * 0.18 + "rem;position:relative;");
});

function loadData(){
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        $("#staffName").html(params.get("staffName"));
        $("#date").html(params.get("date"));
        $("#salaryLevelNum").html(params.get("salaryLevelNum"));
        $("#realGuaranteeSalary").html(params.get("realGuaranteeSalary") + " 元");
        $("#realWelfareSalary").html(params.get("realWelfareSalary") + " 元");
        $("#signInDayCount").html(parseFloat(params.get("signInDayCount")).toFixed(2)+ " 天");
        $("#signInSalary").html(parseFloat(params.get("signInSalary")).toFixed(2)+ " 元");
        $("#leaveType").html(params.get("leaveType"));
        $("#leaveDayCount").html(parseFloat(params.get("leaveDayCount")).toFixed(2)+ " 天");
        $("#overtimeType").html(params.get("overtimeType"));
        $("#overtimeDayCount").html(parseFloat(params.get("overtimeDayCount")).toFixed(2)+ " 天");
        $("#overtimeMoney").html(parseFloat(params.get("overtimeMoney")).toFixed(2)+ " 元");
        $("#realSalary").html(parseFloat(params.get("realSalary")).toFixed(2)+ " 元");
    }
}


