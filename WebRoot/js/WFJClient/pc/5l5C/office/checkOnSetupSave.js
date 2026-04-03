/**
 * 新版数字地球商城
 */

'use strict';

$(document).ready(function () {
    loadData();
    $(".checkOnSetup-top").attr("style", "overflow: auto;height:" + $(window).height() * 0.9 + "px;position:relative;");
});

function loadData(){
    findCheckOnType();
    findSalaryLevel();
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        $(".button-a .button-span").html("保存");
        findCheckOnSetup(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateCheckOnSetup(params.get("id"));
        });
    }else{
        $(".button-a .button-span").html("新增");
        findCurrentUser();
        $(".top-right .button-a").on("click",function (){
            addCheckOnSetup();
        });
    }

    // $("#reward").on("click",function (e){
    //     if($(this).is(':checked')){
    //         $("#checkOnAmountLi").show();
    //     }
    // });
    //
    // $("#discount").on("click",function (e){
    //     if($(this).is(':checked')){
    //         $("#checkOnAmount").val(0);
    //         $("#checkOnAmountLi").hide();
    //     }
    // });

    $("#rankId").on("change",function (e){
        if("-1" != $("#rankId").val()){
            findRankSalary($("#rankId").val());
        }
    });

    $("#referenceBaseSalary").on("click",function (e){
        computeTotal();
    });
    $("#referenceGuaranteeSalary").on("click",function (e){
        computeTotal();
    });
    $("#referenceWelfareSalary").on("click",function (e){
        computeTotal();
    });
}

function addCheckOnSetup() {
    if(!check()){
        return;
    }
    var url = basePath + "ea/checkOnSetup/sajax_ea_addCheckOnSetup.jspa";
    var referenceBaseSalary = "N";
    var referenceGuaranteeSalary = "Y";
    var referenceWelfareSalary = "Y";
    // if(!$("#referenceBaseSalary").is(':checked')){
    //     referenceBaseSalary = "N";
    // }
    if(!$("#referenceGuaranteeSalary").is(':checked')){
        referenceGuaranteeSalary = "N";
    }
    if(!$("#referenceWelfareSalary").is(':checked')){
        referenceWelfareSalary = "N";
    }
    if($("#compute").is(':checked')){
        if("N" == referenceGuaranteeSalary && "N" == referenceWelfareSalary){
            alert("请至少勾选一个需要参考的工资");
            return;
        }
    }

    var amountType = "REWARD";
    if($("#reward").is(':checked')){
        amountType = "REWARD";
    }else {
        amountType = "DISCOUNT";
    }

    var rewardDeductType = "COMPUTE";
    if($("#compute").is(':checked')){
        rewardDeductType = "COMPUTE";
    }else {
        rewardDeductType = "SETUP";
    }

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "checkOnSetup.checkOnTypeId": $("#checkOnTypeId").val(),
            "checkOnSetup.rankId": $("#rankId").val(),
            "checkOnSetup.referenceBaseSalary": referenceBaseSalary,
            "checkOnSetup.referenceGuaranteeSalary": referenceGuaranteeSalary,
            "checkOnSetup.referenceWelfareSalary": referenceWelfareSalary,
            "checkOnSetup.discountRate": $("#discountRate").val().trim(),
            "checkOnSetup.amountType": amountType,
            "checkOnSetup.checkOnAmount": "0",
            "checkOnSetup.rewardDeductAmount": $("#rewardDeductAmount").val().trim(),
            "checkOnSetup.rewardDeductType": rewardDeductType
        },
        success: function(data) {
            alert("考勤设置新增成功");
        },
        error: function () {
            alert("考勤设置新增失败，考勤类别，工资级别不能重复");
        }
    });
}

function updateCheckOnSetup(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/checkOnSetup/sajax_ea_updateCheckOnSetup.jspa";
    var referenceBaseSalary = "N";
    var referenceGuaranteeSalary = "Y";
    var referenceWelfareSalary = "Y";
    // if(!$("#referenceBaseSalary").is(':checked')){
    //     referenceBaseSalary = "N";
    // }
    if(!$("#referenceGuaranteeSalary").is(':checked')){
        referenceGuaranteeSalary = "N";
    }
    if(!$("#referenceWelfareSalary").is(':checked')){
        referenceWelfareSalary = "N";
    }
    if($("#compute").is(':checked')){
        if("N" == referenceGuaranteeSalary && "N" == referenceWelfareSalary){
            alert("请至少勾选一个需要参考的工资");
            return;
        }
    }

    var amountType = "REWARD";
    if($("#reward").is(':checked')){
        amountType = "REWARD";
    }else {
        amountType = "DISCOUNT";
    }

    var rewardDeductType = "COMPUTE";
    if($("#compute").is(':checked')){
        rewardDeductType = "COMPUTE";
    }else {
        rewardDeductType = "SETUP";
    }

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "checkOnSetup.checkOnSetupId": id,
            "checkOnSetup.checkOnTypeId": $("#checkOnTypeId").val(),
            "checkOnSetup.rankId": $("#rankId").val(),
            "checkOnSetup.referenceBaseSalary": referenceBaseSalary,
            "checkOnSetup.referenceGuaranteeSalary": referenceGuaranteeSalary,
            "checkOnSetup.referenceWelfareSalary": referenceWelfareSalary,
            "checkOnSetup.discountRate": $("#discountRate").val().trim(),
            "checkOnSetup.amountType": amountType,
            "checkOnSetup.checkOnAmount": "0",
            "checkOnSetup.rewardDeductAmount": $("#rewardDeductAmount").val().trim(),
            "checkOnSetup.rewardDeductType": rewardDeductType
        },
        success: function(data) {
            alert("考勤设置更新成功");
        },
        error: function () {
            alert("考勤设置更新失败，考勤类别，工资级别不能重复");
        }
    });
}

function findCheckOnSetup(id) {
    var url = basePath + "ea/checkOnSetup/sajax_ea_findCheckOnSetup.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "checkOnSetup.checkOnSetupId": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var checkOnSetup = member.checkOnSetup;
                if (checkOnSetup != null) {
                    $("#checkOnTypeId").val(checkOnSetup.checkOnTypeId);
                    $("#rankId").val(checkOnSetup.rankId);
                    // if("Y" == checkOnSetup.referenceBaseSalary){
                    //     $("#referenceBaseSalary").attr("checked",true);
                    // }else {
                    //     $("#referenceBaseSalary").attr("checked",false);
                    // }
                    if("Y" == checkOnSetup.referenceGuaranteeSalary){
                        $("#referenceGuaranteeSalary").attr("checked",true);
                    }else {
                        $("#referenceGuaranteeSalary").attr("checked",false);
                    }
                    if("Y" == checkOnSetup.referenceWelfareSalary){
                        $("#referenceWelfareSalary").attr("checked",true);
                    }else {
                        $("#referenceWelfareSalary").attr("checked",false);
                    }
                    $("#discountRate").val(checkOnSetup.discountRate);
                    if("REWARD" == checkOnSetup.amountType){
                        $("#reward").attr("checked",true);
                        // $("#checkOnAmountLi").show();
                        // $("#checkOnAmount").val(checkOnSetup.checkOnAmount);
                    } else{
                        $("#discount").attr("checked",true);
                        // $("#checkOnAmount").val(0);
                        // $("#checkOnAmountLi").hide();
                    }
                    if("COMPUTE" == checkOnSetup.rewardDeductType){
                        $("#compute").attr("checked",true);
                    } else{
                        $("#setup").attr("checked",true);
                    }
                    if(null != checkOnSetup.staffName && "" != checkOnSetup.staffName){
                        $("#setupName").val(checkOnSetup.staffName);
                    }
                    if("-1" != $("#rankId").val()){
                        findRankSalary($("#rankId").val());
                    }
                    $("#rewardDeductAmount").val(checkOnSetup.rewardDeductAmount);
                }
            }
        },
        error: function () {
        }
    });
}

function findCurrentUser() {
    var url = basePath + "ea/checkOnSetup/sajax_ea_findCurrentUser.jspa";
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

function findCheckOnType() {
    var url = basePath + "ea/checkOnSetup/sajax_ea_findCheckOnType.jspa";
    $("#checkOnTypeId").append('<option value="-1" selected>请选择考勤类别</option>');
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
                    $("#checkOnTypeId").append('<option value=' + checkOnTypeItem.checkOnTypeId + '>'+ checkOnTypeItem.checkOnTypeName +'</option>');
                }
                $("#checkOnTypeId").css("background", "#fff");
            } else {
                $("#checkOnTypeId").empty();
                $("#checkOnTypeId").append('<option value="-1" selected>请选择考勤类别</option>');
                $("#checkOnTypeId").css("background", "#fff");
            }
        },
        error: function () {
            $("#checkOnTypeId").empty();
            $("#checkOnTypeId").append('<option value="-1" selected>请选择考勤类别</option>');
            $("#checkOnTypeId").css("background", "#fff");
        }
    });
}

function findSalaryLevel() {
    var url = basePath + "ea/checkOnSetup/sajax_ea_findSalaryLevel.jspa";
    $("#rankId").append('<option value="-1" selected>请选择工资级别</option>');
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
        },
        success: function(data) {
            var salaryLevel = null;
            var member = eval("(" + data + ")");
            if(null != member){
                salaryLevel = member.salaryLevel;
            }
            if (salaryLevel != null && salaryLevel.recordCount > 0) {
                var salaryLevelList = salaryLevel.list;
                for (var i = 0; i < salaryLevelList.length; i++) {
                    var salaryLevelItem = salaryLevelList[i];
                    $("#rankId").append('<option value=' + salaryLevelItem.salaryLevelId + '>'+ salaryLevelItem.salaryLevelNum +'</option>');
                }
                $("#rankId").css("background", "#fff");
            } else {
                $("#rankId").empty();
                $("#rankId").append('<option value="-1" selected>请选择工资级别</option>');
                $("#rankId").css("background", "#fff");
            }
        },
        error: function () {
            $("#rankId").empty();
            $("#rankId").append('<option value="-1" selected>请选择工资级别</option>');
            $("#rankId").css("background", "#fff");
        }
    });
}

function findRankSalary(rankId){
    // $("#baseSalary").html("参考基本工资金额: ￥0");
    $("#guaranteeSalary").html("参考保障工资金额: ￥0.00 元");
    $("#welfareSalary").html("参考福利工资金额: ￥0.00 元");
    $("#total").html("￥0.00 元");
    // $("#referenceBaseSalary").val(0);
    $("#referenceGuaranteeSalary").val(0);
    $("#referenceWelfareSalary").val(0);

    var url = basePath + "ea/checkOnSetup/sajax_ea_findRankSalary.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "checkOnSetup.rankId":rankId
        },
        success: function(data) {
            var total = 0;
            var salarySet = null;
            var member = eval("(" + data + ")");
            if(null != member){
                salarySet = member.salaryData;
            }
            if (salarySet != null) {
                // if(null != salarySet.baseSalary){
                //     $("#baseSalary").html("参考基本工资金额: ￥" + salarySet.baseSalary);
                //     if($("#referenceBaseSalary").is(':checked') && !isNaN(salarySet.baseSalary)){
                //         total += parseFloat(salarySet.baseSalary);
                //     }
                //     if(!isNaN(salarySet.baseSalary)){
                //         $("#referenceBaseSalary").val(salarySet.baseSalary);
                //     }
                // }
                if(null != salarySet.guaranteeSalary){
                    $("#guaranteeSalary").html("参考保障工资金额: ￥" + parseFloat(salarySet.guaranteeSalary).toFixed(2) + " 元");
                    if($("#referenceGuaranteeSalary").is(':checked') && !isNaN(salarySet.guaranteeSalary)){
                        total += parseFloat(salarySet.guaranteeSalary);
                    }
                    if(!isNaN(salarySet.guaranteeSalary)){
                        $("#referenceGuaranteeSalary").val(salarySet.guaranteeSalary);
                    }
                }
                if(null != salarySet.welfareSalary){
                    $("#welfareSalary").html("参考福利工资金额: ￥" + parseFloat(salarySet.welfareSalary).toFixed(2) + " 元");
                    if($("#referenceWelfareSalary").is(':checked') && !isNaN(salarySet.welfareSalary)){
                        total +=  parseFloat(salarySet.welfareSalary);
                    }
                    if(!isNaN(salarySet.welfareSalary)){
                        $("#referenceWelfareSalary").val(salarySet.welfareSalary);
                    }
                }
            }
            $("#total").html("￥" + parseFloat(total).toFixed(2) + " 元");
        },
        error: function () {
        }
    });
}

function computeTotal(){
    var total = 0;
    // if($("#referenceBaseSalary").is(':checked') && !isNaN($("#referenceBaseSalary").val())){
    //     total += parseFloat($("#referenceBaseSalary").val());
    // }
    if($("#referenceGuaranteeSalary").is(':checked') && !isNaN($("#referenceGuaranteeSalary").val())){
        total += parseFloat($("#referenceGuaranteeSalary").val());
    }
    if($("#referenceWelfareSalary").is(':checked') && !isNaN($("#referenceWelfareSalary").val())){
        total += parseFloat($("#referenceWelfareSalary").val());
    }
    $("#total").html("￥" + parseFloat(total).toFixed(2) + " 元");
}

function check(){
    if(null == $("#checkOnTypeId").val() || undefined == $("#checkOnTypeId").val() || "-1" == $("#checkOnTypeId").val()){
        alert("请选择考勤类别");
        return false;
    }
    if(null == $("#rankId").val() || undefined == $("#rankId").val() || "-1" == $("#rankId").val()){
        alert("请选择工资级别");
        return false;
    }
    if($("#setup").is(':checked')){
        if(null == $("#rewardDeductAmount").val() || "" == $("#rewardDeductAmount").val().trim()){
            alert("请填写奖折金额");
            return false;
        }
        if(isNaN($("#rewardDeductAmount").val())){
            alert("奖折金额必须是数字");
            return false;
        }
        if(parseFloat($("#rewardDeductAmount").val()) > 999999 || parseFloat($("#rewardDeductAmount").val()) < 0){
            alert("奖折金额取值范围：0 - 999999");
            return false;
        }
    }
    if(null != $("#rewardDeductAmount").val() && "" != $("#rewardDeductAmount").val().trim()){
        if(isNaN($("#rewardDeductAmount").val())){
            alert("奖折金额必须是数字");
            return false;
        }
        if(parseFloat($("#rewardDeductAmount").val()) > 999999 || parseFloat($("#rewardDeductAmount").val()) < 0){
            alert("奖折金额取值范围：0 - 999999");
            return false;
        }
    }
    if(null == $("#discountRate").val() || "" == $("#discountRate").val().trim()){
        alert("请填写奖折倍数");
        return false;
    }
    if(isNaN($("#discountRate").val())){
        alert("奖折倍数必须是数字");
        return false;
    }
    if(parseFloat($("#discountRate").val()) > 99.9 || parseFloat($("#discountRate").val()) < 0){
        alert("奖折倍数取值范围：0 - 99.9");
        return false;
    }
    // if($("#reward").is(':checked')){
    //     if(null == $("#checkOnAmount").val() || "" == $("#checkOnAmount").val().trim()){
    //         alert("请填写奖励金额");
    //         return false;
    //     }
    //     if(isNaN($("#checkOnAmount").val())){
    //         alert("奖励金额必须是数字");
    //         return false;
    //     }
    //     if(parseFloat($("#checkOnAmount").val()) > 999999 || parseFloat($("#checkOnAmount").val()) < 0){
    //         alert("奖励金额取值范围：0 - 999999");
    //         return false;
    //     }
    // }
    return true;
}

