'use strict';
var mode = "day";
var signInSetupStartTime = "07:30";
var signInSetupEndTime = "19:30";

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
        findDutyApply(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateDutyApply(params.get("id"));
        });
    }else{
        $(".button-a .button-span").html("提交");
        $(".top-right .button-a").on("click",function (){
            addDutyApply();
        });
    }

    $("#day").click(function() {
        setBackgroundColor(this);
        mode = "day";
        dateMode();
    });

    $("#hour").click(function() {
        setBackgroundColor(this);
        mode = "hour";
        timeMode();
    });

    $("#startTime").change(function() {
        checkTime(true);
    });

    $("#endTime").change(function() {
        checkTime(true);
    });
}

function addDutyApply() {
    if(!check()){
        return;
    }
    var url = basePath + "ea/dutyApply/sajax_ea_addDutyApply.jspa";

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "dutyScheduling.dutyStartDate": $("#startTime").val(),
            "dutyScheduling.dutyEndDate": $("#endTime").val(),
            "dutyScheduling.dutyContent": $("#dutyContent").val().trim(),
            "dutyScheduling.tel": $("#tel").val().trim(),
            "dutyScheduling.address": $("#address").val().trim(),
            "dutyScheduling.dutyType": $("#dutyType").val(),
        },
        success: function(data) {
            alert("值班申请提交成功");
        },
        error: function () {
            alert("值班申请提交失败，值班时间重复");
        }
    });
}

function updateDutyApply(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/dutyApply/sajax_ea_updateDutyApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "dutyScheduling.dutySchedulingId": id,
            "dutyScheduling.dutyStartDate": $("#startTime").val(),
            "dutyScheduling.dutyEndDate": $("#endTime").val(),
            "dutyScheduling.dutyContent": $("#dutyContent").val().trim(),
            "dutyScheduling.tel": $("#tel").val().trim(),
            "dutyScheduling.address": $("#address").val().trim(),
            "dutyScheduling.dutyType": $("#dutyType").val(),
        },
        success: function(data) {
            alert("值班申请更新成功");
        },
        error: function () {
            alert("值班申请更新失败，值班时间重复");
        }
    });
}

function findDutyApply(id){
    var url = basePath + "ea/dutyApply/sajax_ea_findDutyApply.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "dutyScheduling.dutySchedulingId": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var dutyScheduling = member.dutyScheduling;
                if (dutyScheduling != null) {
                    var startDate = dutyScheduling.dutyStartDate;
                    if(null != startDate){
                        if(startDate.indexOf(" ") == -1){
                            setBackgroundColor($("#day"));
                            mode = "day";
                            dateMode();
                        }else{
                            setBackgroundColor($("#hour"));
                            mode = "hour";
                            timeMode();
                        }
                    }
                    $("#startTime").val(dutyScheduling.dutyStartDate);
                    $("#endTime").val(dutyScheduling.dutyEndDate);
                    $("#dutyContent").val(dutyScheduling.dutyContent);
                    $("#tel").val(dutyScheduling.tel);
                    $("#address").val(dutyScheduling.address);
                    $("#dutyType").val(dutyScheduling.dutyType);
                }
            }
        },
        error: function () {
        }
    });
}

function findCheckOnReviewer() {
    var url = basePath + "ea/overtimeApply/sajax_ea_findCheckOnReviewer.jspa";
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
    modeChange("day");
}

function timeMode(){
    var optDateTime = $.extend(opt['datetime'], opt['default']);
    $("#startTime").mobiscroll().datetime(optDateTime);
    $("#endTime").mobiscroll().datetime(optDateTime);
    modeChange("hour");
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
    var url = basePath + "ea/overtimeApply/sajax_ea_findCurrentUser.jspa";
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
    if(null == $("#dutyType").val() || undefined == $("#dutyType").val() || "" == $("#dutyType").val()){
        alert("请选择值班类别");
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
    if(null == $("#tel").val() || undefined == $("#tel").val() || "" == $("#tel").val()){
        alert("请填写电话");
        return false;
    }
    if(null == $("#address").val() || undefined == $("#address").val() || "" == $("#address").val()){
        alert("请填写地址");
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

function modeChange(mode){
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

function setBackgroundColor(obj){
    $("#day").removeClass("green");
    $("#hour").removeClass("green");
    $("#day").addClass("grey");
    $("#hour").addClass("grey");

    $(obj).removeClass("grey");
    $(obj).addClass("green");
}


