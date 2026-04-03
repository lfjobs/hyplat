/**
 * 新版数字地球商城
 */

'use strict';

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
    startYear: currYear - 10, //开始年份
    endYear: currYear + 10 //结束年份
};

$(document).ready(function () {
    loadData();
});

function loadData(){
    findSignInType();
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        $(".button-a .button-span").html("保存");
            findSignInSetup(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateSignInSetup(params.get("id"));
        });
    }else{
        $(".button-a .button-span").html("新增");
        findCurrentUser();
        $(".top-right .button-a").on("click",function (){
            addSignInSetup();
        });
    }
    timeMode();

    // $("#signInTypeId").on("change",function (e){
    //     if("普通" == $(this).find("option:selected").text()){
    //         timeMode();
    //         var startTime = $("#startTime").val();
    //         if(null != startTime && "" != startTime){
    //             $("#startTime").val(startTime.substring(startTime.indexOf(" ") + 1));
    //         }
    //         var endTime = $("#endTime").val();
    //         if(null != endTime && "" != endTime){
    //             $("#endTime").val(endTime.substring(endTime.indexOf(" ") + 1));
    //         }
    //     }else if("-1" != $("#signInTypeId").val()){
    //         dateMode();
    //         var startT = $("#startTime").val();
    //         if(null != startT && "" != startT && "-1" == startT.indexOf(" ")){
    //             $("#startTime").val(getNowDate() + " " + startT);
    //         }
    //         var endT = $("#endTime").val();
    //         if(null != endT && "" != endT && "-1" == endT.indexOf(" ")){
    //             $("#endTime").val(getNowDate() + " " + endT);
    //         }
    //     }
    // });

    $("#startTime").change(function() {
        checkTime();
    });

    $("#endTime").change(function() {
        checkTime();
    });

}

function addSignInSetup() {
    if(!check()){
        return;
    }
    var url = basePath + "ea/signInSetup/sajax_ea_addSignInSetup.jspa";
    var startTime = "";
    var endTime = "";

    startTime = $("#startTime").val();
    endTime = $("#endTime").val();

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "signInSetup.signInTypeId": $("#signInTypeId").val(),
            "signInSetup.startTime": startTime,
            "signInSetup.endTime": endTime,
            "signInSetup.signInAddress": $("#signInAddress").val().trim()
        },
        success: function(data) {
            alert("签到设置新增成功");
        },
        error: function () {
            alert("签到设置新增失败，签到类别，签到时间，签到地址不能重复");
        }
    });
}

function updateSignInSetup(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/signInSetup/sajax_ea_updateSignInSetup.jspa";
    var startTime = "";
    var endTime = "";

    startTime = $("#startTime").val();
    endTime = $("#endTime").val();

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "signInSetup.signInSetupId": id,
            "signInSetup.signInTypeId": $("#signInTypeId").val(),
            "signInSetup.startTime": startTime,
            "signInSetup.endTime": endTime,
            "signInSetup.signInAddress": $("#signInAddress").val().trim()
        },
        success: function(data) {
            alert("签到设置更新成功");
        },
        error: function () {
            alert("签到设置更新失败，签到类别，签到时间，签到地址不能重复");
        }
    });
}

function findSignInSetup(id) {
    var url = basePath + "ea/signInSetup/sajax_ea_findSignInSetup.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "signInSetup.signInSetupId": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var signInSetup = member.signInSetup;
                if (signInSetup != null) {
                    $("#signInTypeId").val(signInSetup.signInTypeId);
                    var startTime = signInSetup.startTime;
                    var endTime = signInSetup.endTime;
                    if(null != startTime && "" != startTime){
                        // if("普通" == signInSetup.signInTypeName){
                        //     timeMode();
                        // }else {
                        //     dateMode();
                        // }
                        $("#startTime").val(startTime);
                    }
                    if(null != endTime && "" != endTime){
                        $("#endTime").val(endTime);
                    }
                    $("#signInAddress").val(signInSetup.signInAddress);
                    if(null != signInSetup.staffName && "" != signInSetup.staffName){
                        $("#setupName").val(signInSetup.staffName);
                    }
                }
            }
        },
        error: function () {
        }
    });
}

function findCurrentUser() {
    var url = basePath + "ea/signInSetup/sajax_ea_findCurrentUser.jspa";
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
                    $("#setupName").val(staff.staffName);
                }
            }
        },
        error: function () {
        }
    });
}

function findSignInType() {
    var url = basePath + "ea/signInSetup/sajax_ea_findSignInType.jspa";
    $("#signInTypeId").append('<option value="-1" selected>请选择签到类别</option>');
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var signInType = null;
            var member = eval("(" + data + ")");
            if(null != member){
                signInType = member.signInType;
            }
            if (signInType != null && signInType.recordCount > 0) {
                var signInTypeList = signInType.list;
                for (var i = 0; i < signInTypeList.length; i++) {
                    var signInTypeItem = signInTypeList[i];
                    $("#signInTypeId").append('<option value=' + signInTypeItem.signInTypeId + '>'+ signInTypeItem.signInTypeName +'</option>');
                }
                $("#signInTypeId").css("background", "#fff");
            } else {
                $("#signInTypeId").empty();
                $("#signInTypeId").append('<option value="-1" selected>请选择签到类别</option>');
                $("#signInTypeId").css("background", "#fff");
            }
        },
        error: function () {
            $("#signInTypeId").empty();
            $("#signInTypeId").append('<option value="-1" selected>请选择签到类别</option>');
            $("#signInTypeId").css("background", "#fff");
        }
    });
}

function check(){
    if(null == $("#signInTypeId").val() || undefined == $("#signInTypeId").val() || "-1" == $("#signInTypeId").val()){
        alert("请选择签到类别");
        return false;
    }
    if(null == $("#startTime").val() || undefined == $("#startTime").val() || "" == $("#startTime").val()){
        alert("请选择签到时间");
        return false;
    }
    if(null == $("#endTime").val() || undefined == $("#endTime").val() || "" == $("#endTime").val()){
        alert("请选择签退时间");
        return false;
    }
    if(!checkTime()){
        return false;
    }
    if(null == $("#signInAddress").val() || "" == $("#signInAddress").val().trim()){
        alert("请填写签到地址");
        return false;
    }
    return true;
}

function timeMode(){
    $("#startTime").mobiscroll().time({
        theme: 'android-ics light',
        display: 'modal',
        mode: 'scroller',
        dateFormat: 'HH:mm',
        animate: 'slide',
        lang: 'zh',
        nowText: "现在",
        showNow: true
    })
    $("#endTime").mobiscroll().time({
        theme: 'android-ics light',
        display: 'modal',
        mode: 'scroller',
        dateFormat: 'HH:mm',
        animate: 'slide',
        lang: 'zh',
        nowText: "现在",
        showNow: true
    });
}

function dateMode(){
    var optDateTime = $.extend(opt['datetime'], opt['default']);
    $("#startTime").mobiscroll().datetime(optDateTime);
    $("#endTime").mobiscroll().datetime(optDateTime);
}

function checkTime(){
    var begin = $("#startTime").val();
    var end = $("#endTime").val();
    if(null != begin && "" != begin && null != end && "" != end){
        if(end <= begin){
            alert("签退时间必须晚于签到时间");
            return false;
        }
    }
    return true;
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

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
        " " + date.getHours() + seperator2 + date.getMinutes();
    var n = new Date(currentdate).getTime();
    return n;
}