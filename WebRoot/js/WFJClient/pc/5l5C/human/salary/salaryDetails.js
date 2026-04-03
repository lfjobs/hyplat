'use strict';
var staffId = '';
var companyId = '';
var settlementTimeType = '';

$(document).ready(function () {
    loadData();
    $(".con-one").click(function () {
        salaryDayDetail();
    });
    $(".checkOn-top").attr("style", "overflow: auto;height:" + $(window).height() * 0.18 + "rem;position:relative;");
});

function loadData(){
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        staffId = params.get("staffId");
        companyId = params.get("companyId");
        $("#staffName").html(params.get("staffName"));
        settlementTimeType = params.get("settlementTimeType");
        var settlementMonth = params.get("settlementMonth").trim();
        $("#settlementMonth").html(settlementMonth);
        if(settlementMonth.length > 7){
            $("#settlementMonthLabel").html("时间");
        }else{
            $("#settlementMonthLabel").html("月份");
        }
        $("#salaryLevelNum").html(params.get("salaryLevelNum"));
        // $("#baseSalary").html(params.get("baseSalary") + " 元");
        $("#guaranteeSalary").html(params.get("guaranteeSalary") + " 元");
        $("#welfareSalary").html(params.get("welfareSalary") + " 元");
        // $("#totalSalary").html(params.get("totalSalary") + " 元");
        $("#signInDayCount").html(parseFloat(params.get("signInDayCount")).toFixed(2)+ " 天");
        $("#signInSalary").html(parseFloat(params.get("signInSalary")).toFixed(2)+ " 元");
        $("#leaveAddMoney").html(parseFloat(params.get("leaveAddMoney")).toFixed(2)+ " 元");
        // $("#absenteeDiscountMoney").html(parseFloat(params.get("absenteeDiscountMoney")).toFixed(2)+ " 元");
        $("#weekendOvertimeMoney").html(parseFloat(params.get("weekendOvertimeMoney")).toFixed(2)+ " 元");
        $("#eightHourOvertimeMoney").html(parseFloat(params.get("eightHourOvertimeMoney")).toFixed(2)+ " 元");
        $("#holidayOvertimeMoney").html(parseFloat(params.get("holidayOvertimeMoney")).toFixed(2)+ " 元");
        $("#realSalary").html(parseFloat(params.get("realSalary")).toFixed(2)+ " 元");
        $("#socialSecurityLevel").html(parseFloat(params.get("socialSecurityLevel")).toFixed(2)+ " 元");
        $("#elderlyCareDiscountMoney").html(parseFloat(params.get("elderlyCareDiscountMoney")).toFixed(2)+ " 元");
        $("#medicalDiscountMoney").html(parseFloat(params.get("medicalDiscountMoney")).toFixed(2)+ " 元");
        $("#unemploymentDiscountMoney").html(parseFloat(params.get("unemploymentDiscountMoney")).toFixed(2)+ " 元");
        $("#signInDayTotalCount").html(parseFloat(params.get("signInDayTotalCount")).toFixed(2)+ " 天");
        $("#lateLeaveEarlyDayCount").html(parseFloat(params.get("lateLeaveEarlyDayCount")).toFixed(2)+ " 天");
        $("#leaveAddDayCount").html(parseFloat(params.get("leaveAddDayCount")).toFixed(2)+ " 天");
        $("#weekendOvertimeDayCount").html(parseFloat(params.get("weekendOvertimeDayCount")).toFixed(2)+ " 天");
        $("#eightHourOvertimeDayCount").html(parseFloat(params.get("eightHourOvertimeDayCount")).toFixed(2)+ " 天");
        $("#holidayOvertimeDayCount").html(parseFloat(params.get("holidayOvertimeDayCount")).toFixed(2)+ " 天");
    }else{
    }
}

function salaryDayDetail() {
    var url = basePath + "ea/salarySettlement/ea_dayList.jspa?staffId=" + staffId
        + "&staffName=" + $("#staffName").html()
        + "&settlementMonth=" + $("#settlementMonth").html()
        + "&companyId=" + companyId
        + "&settlementTimeType=" + settlementTimeType;
    window.location.href = url;
}


