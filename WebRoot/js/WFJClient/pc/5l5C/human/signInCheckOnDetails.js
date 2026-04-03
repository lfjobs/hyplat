'use strict';
var staffId = '';
var staffName = '';
var checkOnTime = '';
var startTime = '';
var endTime = '';
var salaryLevelNum = '';
var baseSalary = '';
var guaranteeSalary = '';
var welfareSalary = '';

$(document).ready(function () {
    loadData();
    $(".checkOn-top").attr("style", "overflow: auto;height:" + $(window).height() * 0.18 + "rem;position:relative;");

    $("#late").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=LATE"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#leaveEarly").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=LEAVE_EARLY"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $(".leave").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=LEAVE"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#absentee").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=ABSENTEE"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#weekendOvertime").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=WEEKEND_OVERTIME"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#outwork").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=OUTWORK"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#eightHourOvertime").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=EIGHT_HOUR_OVERTIME"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

    $("#holidayOvertime").on("click",function (){
        var url = basePath + "ea/checkOn/ea_checkOnDetails.jspa?staffId=" + staffId
            + "&checkOnType=HOLIDAY_OVERTIME"
            + "&staffName=" + staffName
            + "&checkOnTime=" + checkOnTime
            + "&startTime=" + startTime
            + "&endTime=" + endTime
            + "&salaryLevelNum=" + salaryLevelNum
            + "&baseSalary=" + baseSalary
            + "&guaranteeSalary=" + guaranteeSalary
            + "&welfareSalary=" + welfareSalary;
        window.location.href = url;
    });

});

function loadData(){
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        staffId = params.get("staffId");
        $("#staffName").html(params.get("staffName"));
        staffName = params.get("staffName");
        $("#checkOnTime").html(null == params.get("startTime") || "" == params.get("startTime")
            ? "全部" : (swithTime(params.get("checkOnTimeType")) + "&nbsp;&nbsp;&nbsp;&nbsp;" + params.get("startTime") + "&nbsp;&nbsp;-&nbsp;&nbsp;" + params.get("endTime")));
        checkOnTime = $("#checkOnTime").html();
        startTime = params.get("startTime");
        endTime = params.get("endTime");
        $("#salaryLevelNum").html(params.get("salaryLevelNum"));
        salaryLevelNum = params.get("salaryLevelNum");
        // $("#baseSalary").html(params.get("baseSalary"));
        baseSalary = params.get("baseSalary");
        $("#guaranteeSalary").html(params.get("guaranteeSalary"));
        guaranteeSalary = params.get("guaranteeSalary");
        $("#welfareSalary").html(params.get("welfareSalary"));
        welfareSalary = params.get("welfareSalary");
        $("#lateCount").html(params.get("lateCount"));
        $("#lateMinutes").html(params.get("lateMinutes") + " 分钟");
        $("#lateDiscountMoney").html(parseFloat(params.get("lateDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveEarlyCount").html(params.get("leaveEarlyCount"));
        $("#leaveEarlyMinutes").html(params.get("leaveEarlyMinutes")+ " 分钟");
        $("#leaveEarlyDiscountMoney").html(parseFloat(params.get("leaveEarlyDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveAbsenceDayCount").html(params.get("leaveAbsenceDayCount"));
        $("#leaveAbsenceHours").html(params.get("leaveAbsenceHours")+ " 小时");
        $("#leaveAbsenceDiscountMoney").html(parseFloat(params.get("leaveAbsenceDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveSickDayCount").html(params.get("leaveSickDayCount"));
        $("#leaveSickHours").html(params.get("leaveSickHours")+ " 小时");
        $("#leaveSickDiscountMoney").html(parseFloat(params.get("leaveSickDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveMarriageDayCount").html(params.get("leaveMarriageDayCount"));
        $("#leaveMarriageHours").html(params.get("leaveMarriageHours")+ " 小时");
        $("#leaveMarriageDiscountMoney").html(parseFloat(params.get("leaveMarriageDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveAnnualDayCount").html(params.get("leaveAnnualDayCount"));
        $("#leaveAnnualHours").html(params.get("leaveAnnualHours")+ " 小时");
        $("#leaveAnnualDiscountMoney").html(parseFloat(params.get("leaveAnnualDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveMaternityDayCount").html(params.get("leaveMaternityDayCount"));
        $("#leaveMaternityHours").html(params.get("leaveMaternityHours")+ " 小时");
        $("#leaveMaternityDiscountMoney").html(parseFloat(params.get("leaveMaternityDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveFuneralDayCount").html(params.get("leaveFuneralDayCount"));
        $("#leaveFuneralHours").html(params.get("leaveFuneralHours")+ " 小时");
        $("#leaveFuneralDiscountMoney").html(parseFloat(params.get("leaveFuneralDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveWorkInjuryDayCount").html(params.get("leaveWorkInjuryDayCount"));
        $("#leaveWorkInjuryHours").html(params.get("leaveWorkInjuryHours")+ " 小时");
        $("#leaveWorkInjuryDiscountMoney").html(parseFloat(params.get("leaveWorkInjuryDiscountMoney")).toFixed(2)+ " 元");
        $("#leaveRotationRestDayCount").html(params.get("leaveRotationRestDayCount"));
        $("#leaveRotationRestHours").html(params.get("leaveRotationRestHours")+ " 小时");
        $("#leaveRotationRestDiscountMoney").html(parseFloat(params.get("leaveRotationRestDiscountMoney")).toFixed(2)+ " 元");
        $("#absenteeDayCount").html(params.get("absenteeDayCount"));
        $("#absenteeHours").html(params.get("absenteeHours")+ " 小时");
        $("#absenteeDiscountMoney").html(parseFloat(params.get("absenteeDiscountMoney")).toFixed(2)+ " 元");

        $("#totalDiscountMoney").html(parseFloat(params.get("totalDiscountMoney")).toFixed(2)+ " 元");

        $("#outworkDayCount").html(params.get("outworkDayCount"));
        $("#outworkHours").html(params.get("outworkHours")+ " 小时");
        $("#weekendOverTimeDayCount").html(params.get("weekendOverTimeDayCount"));
        $("#weekendOverTimeHours").html(params.get("weekendOverTimeHours")+ " 小时");
        $("#weekendOverTimeMoney").html(parseFloat(params.get("weekendOverTimeMoney")).toFixed(2)+ " 元");
        $("#eightHourOverTimeDayCount").html(params.get("eightHourOverTimeDayCount"));
        $("#eightHourOverTimeHours").html(params.get("eightHourOverTimeHours")+ " 小时");
        $("#eightHourOverTimeMoney").html(parseFloat(params.get("eightHourOverTimeMoney")).toFixed(2)+ " 元");
        $("#holidayOverTimeDayCount").html(params.get("holidayOverTimeDayCount"));
        $("#holidayOverTimeHours").html(params.get("holidayOverTimeHours")+ " 小时");
        $("#holidayOverTimeMoney").html(parseFloat(params.get("holidayOverTimeMoney")).toFixed(2)+ " 元");
        var totalOvertimeMoney = parseFloat(params.get("weekendOverTimeMoney")) +
            parseFloat(params.get("eightHourOverTimeMoney")) + parseFloat(params.get("holidayOverTimeMoney"));
        $("#totalOvertimeMoney").html(parseFloat(totalOvertimeMoney + '').toFixed(2)+ " 元");
    }else{
    }
}

function swithTime(checkOnTimeType){
    var checkOnTime = "本周";
    switch (checkOnTimeType){
        case "TODAY":
            checkOnTime = "今日";
            break;
        case "YESTERDAY":
            checkOnTime = "昨日";
            break;
        case "CURRENT_WEEK":
            checkOnTime = "本周";
            break;
        case "BEFORE_WEEK":
            checkOnTime = "上周";
            break;
        case "CURRENT_MONTH":
            checkOnTime = "本月";
            break;
        case "BEFORE_MONTH":
            checkOnTime = "上月";
            break;
        case "CURRENT_QUARTER":
            checkOnTime = "本季";
            break;
        case "BEFORE_QUARTER":
            checkOnTime = "上季";
            break;
        case "CURRENT_YEAR":
            checkOnTime = "本年";
            break;
        case "BEFORE_YEAR":
            checkOnTime = "上年";
            break;
    }
    return checkOnTime;
}


