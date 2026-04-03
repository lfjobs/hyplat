'use strict';
var mode = "day";
var signInSetupStartTime = "09:00";
var signInSetupEndTime = "18:00";

//选择时间插件
var currYear = (new Date()).getFullYear();
var opt = {};
opt.date = {
    preset: 'date'
};
opt.datetime = {
    preset: 'datetime'
};
opt.time = {
    preset: 'time'
};
opt.default = {
    theme: 'android-ics light', //皮肤样式
    display: 'modal', //显示方式
    mode: 'scroller', //日期选择模式
    dateFormat: 'yyyy-mm-dd',
    lang: 'zh',
    showNow: true,
    nowText: "今天",
    startYear: currYear - 1, //开始年份
    endYear: currYear + 1 //结束年份
};

$(document).ready(function () {
    loadData();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");


});

function loadData(){
    findCheckOnType();
    findCheckOnReviewer();
    findSignInSetup();
    setBackgroundColor($("#day"));
    mode = "day";
    dateMode();

    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });

    if(params.size > 0){
        $(".button-a .button-span").html("保存");
        findLeaveApply(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateLeaveApply(params.get("id"));
        });
    }else{
        $("#reviewTimeLi").hide();
        $(".button-a .button-span").html("提交");
        $(".top-right .button-a").on("click",function (){
            addLeaveApply();
        });
    }

    if($("#status").html() == "未审核"){
        $("#day").click(function() {
            setBackgroundColor(this);
            mode = "day";
            dateMode();
            computeLeaveHours();
        });

        $("#hour").click(function() {
            setBackgroundColor(this);
            mode = "hour";
            timeMode();
            computeLeaveHours();
        });
    }else{
        disableElement();
    }

    $("#startTime").change(function() {
        if(checkTime(true)){
            computeLeaveHours();
        }else {
            $("#leaveHour").html("");
        }
    });

    $("#endTime").change(function() {
        if(checkTime(true)){
            computeLeaveHours();
        }else {
            $("#leaveHour").html("");
        }
    });
}

function addLeaveApply() {
    if(!check()){
        return;
    }
    var url = basePath + "ea/leaveApply/sajax_ea_addLeaveApply.jspa";

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "leaveApply.leaveType": $("#leaveType").val(),
            "leaveApply.leaveStartDate": $("#startTime").val(),
            "leaveApply.leaveEndDate": $("#endTime").val(),
            "leaveApply.leaveReason": $("#leaveReason").val().trim(),
            "leaveApply.status": "01",
            "leaveApply.auditorId": $("#reviewerId").val()
        },
        success: function(data) {
            alert("请假申请提交成功");
        },
        error: function () {
            alert("请假申请提交失败，请假时间重复");
        }
    });
}

function computeLeaveHours() {
    var url = basePath + "ea/leaveApply/sajax_ea_computeLeaveHours.jspa";
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    if(null != startTime && "" != startTime && null != endTime && "" != endTime){
        if(!checkTime(false)){
            return;
        }
    }else {
        return;
    }
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "leaveApply.leaveStartDate": $("#startTime").val(),
            "leaveApply.leaveEndDate": $("#endTime").val(),
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member) {
                var leaveHours = member.leaveHours;
                if (leaveHours != null) {
                    $("#leaveHour").html(leaveHours);
                }
            }
        },
        error: function () {
        }
    });
}

function updateLeaveApply(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/leaveApply/sajax_ea_updateLeaveApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "leaveApply.key": id,
            "leaveApply.leaveType": $("#leaveType").val(),
            "leaveApply.leaveStartDate": $("#startTime").val(),
            "leaveApply.leaveEndDate": $("#endTime").val(),
            "leaveApply.leaveReason": $("#leaveReason").val().trim(),
            "leaveApply.auditorId": $("#reviewerId").val()
        },
        success: function(data) {
            alert("请假申请更新成功");
        },
        error: function () {
            alert("请假申请更新失败，请假时间重复");
        }
    });
}

function findLeaveApply(id){
    var url = basePath + "ea/leaveApply/sajax_ea_findLeaveApply.jspa";
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
                    $("#leaveType").val(leaveApply.leaveType);
                    var leaveStartDate = leaveApply.leaveStartDate;
                    if(null != leaveStartDate){
                        if(leaveStartDate.indexOf(" ") == -1){
                            setBackgroundColor($("#day"));
                            mode = "day";
                            dateMode();
                        }else{
                            setBackgroundColor($("#hour"));
                            mode = "hour";
                            timeMode();
                        }
                    }
                    $("#startTime").val(leaveApply.leaveStartDate);
                    $("#endTime").val(leaveApply.leaveEndDate);
                    $("#leaveHour").html(leaveApply.leaveHour);
                    $("#leaveReason").val(leaveApply.leaveReason);
                    if(leaveApply.status=="01"){
                        $("#status").html("未审核");
                    }else if(leaveApply.status=="02"){
                        $("#status").html("已审核");
                    }else if(leaveApply.status=="03"){
                        $("#status").html("驳回");
                    }else{
                        $("#status").html("未审核");
                    }
                    $("#reviewerId").val(leaveApply.auditorId);
                    if(leaveApply.status=="01"){
                        $(".top-right .button-a").show();
                        $("#reviewTimeLi").hide();
                    }else{
                        $(".top-right .button-a").hide();
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

function findCheckOnType() {
    var url = basePath + "ea/leaveApply/sajax_ea_findLeaveCheckOnType.jspa";
    $("#leaveType").append('<option value="-1" selected>请选择请假类别</option>');
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var checkOnType = null;
            var member = eval("(" + data + ")");
            if(null != member){
                checkOnType = member.checkOnType;
            }
            if (checkOnType != null && checkOnType.recordCount > 0) {
                var checkOnTypeList = checkOnType.list;
                for (var i = 0; i < checkOnTypeList.length; i++) {
                    var checkOnTypeItem = checkOnTypeList[i];
                    $("#leaveType").append('<option value=' + checkOnTypeItem.checkOnTypeName + '>'+ checkOnTypeItem.checkOnTypeName +'</option>');
                }
                $("#leaveType").css("background", "#fff");
            } else {
                $("#leaveType").empty();
                $("#leaveType").append('<option value="-1" selected>请选择请假类别</option>');
                $("#leaveType").css("background", "#fff");
            }
        },
        error: function () {
            $("#leaveType").empty();
            $("#leaveType").append('<option value="-1" selected>请选择请假类别</option>');
            $("#leaveType").css("background", "#fff");
        }
    });
}

function findCheckOnReviewer() {
    var url = basePath + "ea/leaveApply/sajax_ea_findCheckOnReviewer.jspa";
    $("#reviewerId").append('<option value="-1" selected>请选择审核人</option>');
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var checkOnReviewer = null;
            var member = eval("(" + data + ")");
            if(null != member){
                checkOnReviewer = member.checkOnReviewer;
            }
            if (checkOnReviewer != null && checkOnReviewer.length > 0) {
                for (var i = 0; i < checkOnReviewer.length; i++) {
                    var checkOnReviewerItem = checkOnReviewer[i];
                    $("#reviewerId").append('<option value=' + checkOnReviewerItem[0] + '>'+ checkOnReviewerItem[1] +'</option>');
                }
                $("#reviewerId").css("background", "#fff");
            } else {
                $("#reviewerId").empty();
                $("#reviewerId").append('<option value="-1" selected>请选择审核人</option>');
                $("#reviewerId").css("background", "#fff");
            }
        },
        error: function () {
            $("#reviewerId").empty();
            $("#reviewerId").append('<option value="-1" selected>请选择审核人</option>');
            $("#reviewerId").css("background", "#fff");
        }
    });
}

function dateMode(){
    $("#startTime").mobiscroll().date({
        theme: 'android-ics light',
        display: 'modal',
        mode: 'scroller',
        dateFormat: 'yyyy-mm-dd',
        animate: 'slide',
        lang: 'zh',
        nowText: "现在",
        showNow: true,
        startYear: currYear - 1, //开始年份
        endYear: currYear + 1 //结束年份
    });
    $("#endTime").mobiscroll().date({
        theme: 'android-ics light',
        display: 'modal',
        mode: 'scroller',
        dateFormat: 'yyyy-mm-dd',
        animate: 'slide',
        lang: 'zh',
        nowText: "现在",
        showNow: true,
        startYear: currYear - 1, //开始年份
        endYear: currYear + 1 //结束年份
    });
    leaveModeChange("day");
}

function timeMode(){
    var optDateTime = $.extend(opt['datetime'], opt['default']);
    $("#startTime").mobiscroll().datetime(optDateTime);
    $("#endTime").mobiscroll().datetime(optDateTime);
    leaveModeChange("hour");
}

function leaveModeChange(mode){
    if("day" == mode){
        var startTime = $("#startTime").val();
        if(null != startTime && "" != startTime && startTime.indexOf(" ") != -1){
            $("#startTime").val(startTime.substring(0,startTime.indexOf(" ")));
        }
        var endTime = $("#endTime").val();
        if(null != endTime && "" != endTime && endTime.indexOf(" ") != -1){
            $("#endTime").val(endTime.substring(0,endTime.indexOf(" ")));
        }
    }else{
        var startT = $("#startTime").val();
        if(null != startT && "" != startT && "-1" == startT.indexOf(" ")){
            $("#startTime").val(startT + " " + signInSetupStartTime);
        }
        var endT = $("#endTime").val();
        if(null != endT && "" != endT && "-1" == endT.indexOf(" ")){
            $("#endTime").val(endT + " " + signInSetupEndTime);
        }
    }
}

function findSignInSetup() {
    var url = basePath + "ea/leaveApply/sajax_ea_findSignInSetup.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member) {
                var signInSetup = member.signInSetup;
                if (signInSetup != null) {
                    signInSetupStartTime = signInSetup[0];
                    signInSetupEndTime = signInSetup[1];
                }
            }
        },
        error: function () {
        }
    });
}

function getNowDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentDate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentDate;
}

function findCurrentUser() {
    var staffId = '';
    var url = basePath + "ea/leaveApply/sajax_ea_findCurrentUser.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var staff = member.staff;
                if (staff != null) {
                    staffId = staff.staffID;
                }
            }
        },
        error: function () {
        }
    });
    return staffId;
}

function check(){
    if(null == $("#leaveType").val() || undefined == $("#leaveType").val() || "-1" == $("#leaveType").val()){
        alert("请选择请假类型");
        return false;
    }
    if(null == $("#startTime").val() || undefined == $("#startTime").val() || "" == $("#startTime").val()){
        alert("请选择开始时间");
        return false;
    }
    if(null == $("#endTime").val() || undefined == $("#endTime").val() || "" == $("#endTime").val()){
        alert("请选择结束时间");
        return false;
    }
    if(!checkTime(true)){
        return false;
    }
    if(null == $("#reviewerId").val() || undefined == $("#reviewerId").val() || "-1" == $("#reviewerId").val()){
        alert("请选择审核人");
        return false;
    }
    if($("#reviewerId").val() == findCurrentUser()){
        alert("审核人不能是当前用户");
        return false;
    }
    return true;
}

function checkTime(showMessage){
    var begin = $("#startTime").val();
    var end = $("#endTime").val();
    if(null != begin && "" != begin && null != end && "" != end){
        if(mode == "day"){
            if(end < begin){
                if(showMessage){
                    alert("结束时间必须晚于开始时间");
                }
                return false;
            }
        }else{
            if(end <= begin){
                if(showMessage){
                    alert("结束时间必须晚于开始时间");
                }
                return false;
            }
        }
    }
    return true;
}

function setBackgroundColor(obj){
    $("#day").removeClass("green");
    $("#hour").removeClass("green");
    $("#day").addClass("grey");
    $("#hour").addClass("grey");

    $(obj).removeClass("grey");
    $(obj).addClass("green");
}

function disableElement(){
    $('#leaveType').prop('disabled', true);
    $('#startTime').prop('disabled', true);
    $('#endTime').prop('disabled', true);
    $('#leaveReason').prop('disabled', true);
    $('#reviewerId').prop('disabled', true);

}

