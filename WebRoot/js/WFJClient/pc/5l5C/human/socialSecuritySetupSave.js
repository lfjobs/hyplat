/**
 * 新版数字地球商城
 */

'use strict';

$(document).ready(function () {
    loadData();
    $(".socialSecuritySetup-top").attr("style", "overflow: auto;height:" + $(window).height() * 0.9 + "px;position:relative;");
});

function loadData(){
    $("#unified").attr("checked",true);

    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        $(".button-a .button-span").html("保存");
        findSocialSecuritySetup(params.get("id"));
        $(".top-right .button-a").on("click",function (){
            updateSocialSecuritySetup(params.get("id"));
        });
    }else{
        $(".button-a .button-span").html("新增");
        findCurrentUser();
        $(".top-right .button-a").on("click",function (){
            addSocialSecuritySetup();
        });
    }

}

function addSocialSecuritySetup() {
    if(!check()){
        return;
    }
    var url = basePath + "ea/socialSecuritySetup/sajax_ea_addSocialSecuritySetup.jspa";
    var referenceGuaranteeSalary = "Y";
    var referenceWelfareSalary = "Y";
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

    var deductionType = "COMPUTE";
    if($("#compute").is(':checked')){
        deductionType = "COMPUTE";
    }else {
        deductionType = "UNIFIED";
    }

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "socialSecuritySetup.deductionType": deductionType,
            "socialSecuritySetup.socialSecurityLowLevel": $("#socialSecurityLowLevel").val().trim(),
            "socialSecuritySetup.socialSecurityLevel": $("#socialSecurityLevel").val().trim(),
            "socialSecuritySetup.elderlyCareRate": $("#elderlyCareRate").val().trim(),
            "socialSecuritySetup.medicalRate": $("#medicalRate").val().trim(),
            "socialSecuritySetup.unemploymentRete": $("#unemploymentRete").val().trim(),
            "socialSecuritySetup.elderlyCareDiscountMoney": $("#elderlyCareDiscountMoney").val().trim(),
            "socialSecuritySetup.medicalDiscountMoney": $("#medicalDiscountMoney").val().trim(),
            "socialSecuritySetup.unemploymentDiscountMoney": $("#unemploymentDiscountMoney").val().trim(),
            "socialSecuritySetup.referenceGuaranteeSalary": referenceGuaranteeSalary,
            "socialSecuritySetup.referenceWelfareSalary": referenceWelfareSalary
        },
        success: function(data) {
            alert("设置新增成功");
        },
        error: function () {
            alert("设置新增失败，每个公司的设置不能重复");
        }
    });
}

function updateSocialSecuritySetup(id) {
    if(!check()){
        return;
    }
    var url = basePath + "ea/socialSecuritySetup/sajax_ea_updateSocialSecuritySetup.jspa";
    var referenceGuaranteeSalary = "Y";
    var referenceWelfareSalary = "Y";
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

    var deductionType = "COMPUTE";
    if($("#compute").is(':checked')){
        deductionType = "COMPUTE";
    }else {
        deductionType = "UNIFIED";
    }

    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "socialSecuritySetup.socialSecuritySetupId": id,
            "socialSecuritySetup.deductionType": deductionType,
            "socialSecuritySetup.socialSecurityLowLevel": $("#socialSecurityLowLevel").val().trim(),
            "socialSecuritySetup.socialSecurityLevel": $("#socialSecurityLevel").val().trim(),
            "socialSecuritySetup.elderlyCareRate": $("#elderlyCareRate").val().trim(),
            "socialSecuritySetup.medicalRate": $("#medicalRate").val().trim(),
            "socialSecuritySetup.unemploymentRete": $("#unemploymentRete").val().trim(),
            "socialSecuritySetup.elderlyCareDiscountMoney": $("#elderlyCareDiscountMoney").val().trim(),
            "socialSecuritySetup.medicalDiscountMoney": $("#medicalDiscountMoney").val().trim(),
            "socialSecuritySetup.unemploymentDiscountMoney": $("#unemploymentDiscountMoney").val().trim(),
            "socialSecuritySetup.referenceGuaranteeSalary": referenceGuaranteeSalary,
            "socialSecuritySetup.referenceWelfareSalary": referenceWelfareSalary
        },
        success: function(data) {
            alert("设置更新成功");
        },
        error: function () {
            alert("设置更新失败，每个公司的设置不能重复");
        }
    });
}

function findSocialSecuritySetup(id) {
    var url = basePath + "ea/socialSecuritySetup/sajax_ea_findSocialSecuritySetup.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "socialSecuritySetup.socialSecuritySetupId": id
        },
        success: function(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                var socialSecuritySetup = member.socialSecuritySetup;
                if (socialSecuritySetup != null) {
                    if("UNIFIED" == socialSecuritySetup.deductionType){
                        $("#unified").attr("checked",true);
                    }else {
                        $("#compute").attr("checked",true);
                    }
                    $("#socialSecurityLevel").val(parseFloat(socialSecuritySetup.socialSecurityLevel).toFixed(2));
                    $("#elderlyCareDiscountMoney").val(parseFloat(socialSecuritySetup.elderlyCareDiscountMoney).toFixed(2));
                    $("#medicalDiscountMoney").val(parseFloat(socialSecuritySetup.medicalDiscountMoney).toFixed(2));
                    $("#unemploymentDiscountMoney").val(parseFloat(socialSecuritySetup.unemploymentDiscountMoney).toFixed(2));
                    if("Y" == socialSecuritySetup.referenceGuaranteeSalary){
                        $("#referenceGuaranteeSalary").attr("checked",true);
                    }else {
                        $("#referenceGuaranteeSalary").attr("checked",false);
                    }
                    if("Y" == socialSecuritySetup.referenceWelfareSalary){
                        $("#referenceWelfareSalary").attr("checked",true);
                    }else {
                        $("#referenceWelfareSalary").attr("checked",false);
                    }
                    $("#elderlyCareRate").val(socialSecuritySetup.elderlyCareRate);
                    $("#medicalRate").val(socialSecuritySetup.medicalRate);
                    $("#unemploymentRete").val(socialSecuritySetup.unemploymentRete);
                    $("#socialSecurityLowLevel").val(parseFloat(socialSecuritySetup.socialSecurityLowLevel).toFixed(2));

                    if(null != socialSecuritySetup.staffName && "" != socialSecuritySetup.staffName){
                        $("#setupName").val(socialSecuritySetup.staffName);
                    }
                }
            }
        },
        error: function () {
        }
    });
}

function findCurrentUser() {
    var url = basePath + "ea/socialSecuritySetup/sajax_ea_findCurrentUser.jspa";
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

function check(){
    if($("#unified").is(':checked')){
        if(null == $("#socialSecurityLevel").val() || "" == $("#socialSecurityLevel").val().trim()){
            alert("请填写社保缴纳基数");
            return false;
        }
        if(isNaN($("#socialSecurityLevel").val())){
            alert("社保缴纳基数必须是数字");
            return false;
        }
        if(parseFloat($("#socialSecurityLevel").val()) > 999999 || parseFloat($("#socialSecurityLevel").val()) < 0){
            alert("社保缴纳基数取值范围：0 - 999999");
            return false;
        }

        if(null == $("#elderlyCareDiscountMoney").val() || "" == $("#elderlyCareDiscountMoney").val().trim()){
            alert("请填写养老扣费金额");
            return false;
        }
        if(isNaN($("#elderlyCareDiscountMoney").val())){
            alert("养老扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#elderlyCareDiscountMoney").val()) > 999999 || parseFloat($("#elderlyCareDiscountMoney").val()) < 0){
            alert("养老扣费金额取值范围：0 - 999999");
            return false;
        }

        if(null == $("#medicalDiscountMoney").val() || "" == $("#medicalDiscountMoney").val().trim()){
            alert("请填写医疗扣费金额");
            return false;
        }
        if(isNaN($("#medicalDiscountMoney").val())){
            alert("医保扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#medicalDiscountMoney").val()) > 999999 || parseFloat($("#medicalDiscountMoney").val()) < 0){
            alert("医保扣费金额取值范围：0 - 999999");
            return false;
        }

        if(null == $("#unemploymentDiscountMoney").val() || "" == $("#unemploymentDiscountMoney").val().trim()){
            alert("请填写失业扣费金额");
            return false;
        }
        if(isNaN($("#unemploymentDiscountMoney").val())){
            alert("失业扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#unemploymentDiscountMoney").val()) > 999999 || parseFloat($("#unemploymentDiscountMoney").val()) < 0){
            alert("失业扣费金额取值范围：0 - 999999");
            return false;
        }
    }else{
        if(null == $("#elderlyCareRate").val() || "" == $("#elderlyCareRate").val().trim()){
            alert("请填写养老比率");
            return false;
        }
        if(isNaN($("#elderlyCareRate").val())){
            alert("养老比率必须是数字");
            return false;
        }
        if(parseFloat($("#elderlyCareRate").val()) > 100 || parseFloat($("#elderlyCareRate").val()) < 0){
            alert("养老比率取值范围：0 - 100");
            return false;
        }

        if(null == $("#medicalRate").val() || "" == $("#medicalRate").val().trim()){
            alert("请填写医保比率");
            return false;
        }
        if(isNaN($("#medicalRate").val())){
            alert("医保比率必须是数字");
            return false;
        }
        if(parseFloat($("#medicalRate").val()) > 100 || parseFloat($("#medicalRate").val()) < 0){
            alert("医保比率取值范围：0 - 100");
            return false;
        }

        if(null == $("#unemploymentRete").val() || "" == $("#unemploymentRete").val().trim()){
            alert("请填写失业比率");
            return false;
        }
        if(isNaN($("#unemploymentRete").val())){
            alert("失业比率必须是数字");
            return false;
        }
        if(parseFloat($("#unemploymentRete").val()) > 100 || parseFloat($("#unemploymentRete").val()) < 0){
            alert("失业比率取值范围：0 - 100");
            return false;
        }
    }
    if(null != $("#socialSecurityLevel").val() && "" != $("#socialSecurityLevel").val().trim()){
        if(isNaN($("#socialSecurityLevel").val())){
            alert("社保缴纳基数必须是数字");
            return false;
        }
        if(parseFloat($("#socialSecurityLevel").val()) > 999999 || parseFloat($("#socialSecurityLevel").val()) < 0){
            alert("社保缴纳基数取值范围：0 - 999999");
            return false;
        }
    }
    if(null != $("#elderlyCareDiscountMoney").val() && "" != $("#elderlyCareDiscountMoney").val().trim()){
        if(isNaN($("#elderlyCareDiscountMoney").val())){
            alert("养老扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#socialSecurityLevel").val()) > 999999 || parseFloat($("#socialSecurityLevel").val()) < 0){
            alert("养老扣费金额取值范围：0 - 999999");
            return false;
        }
    }
    if(null != $("#medicalDiscountMoney").val() && "" != $("#medicalDiscountMoney").val().trim()){
        if(isNaN($("#medicalDiscountMoney").val())){
            alert("医保扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#medicalDiscountMoney").val()) > 999999 || parseFloat($("#medicalDiscountMoney").val()) < 0){
            alert("医保扣费金额取值范围：0 - 999999");
            return false;
        }
    }
    if(null != $("#unemploymentDiscountMoney").val() && "" != $("#unemploymentDiscountMoney").val().trim()){
        if(isNaN($("#unemploymentDiscountMoney").val())){
            alert("失业扣费金额必须是数字");
            return false;
        }
        if(parseFloat($("#unemploymentDiscountMoney").val()) > 999999 || parseFloat($("#unemploymentDiscountMoney").val()) < 0){
            alert("失业扣费金额取值范围：0 - 999999");
            return false;
        }
    }

    if(null != $("#elderlyCareRate").val() && "" != $("#elderlyCareRate").val().trim()){
        if(isNaN($("#elderlyCareRate").val())){
            alert("养老比率必须是数字");
            return false;
        }
        if(parseFloat($("#unemploymentDiscountMoney").val()) > 100 || parseFloat($("#unemploymentDiscountMoney").val()) < 0){
            alert("养老比率值范围：0 - 100");
            return false;
        }
    }
    if(null != $("#medicalRate").val() && "" != $("#medicalRate").val().trim()){
        if(isNaN($("#medicalRate").val())){
            alert("医保比率必须是数字");
            return false;
        }
        if(parseFloat($("#medicalRate").val()) > 100 || parseFloat($("#medicalRate").val()) < 0){
            alert("医保比率取值范围：0 - 100");
            return false;
        }
    }
    if(null != $("#unemploymentRete").val() && "" != $("#unemploymentRete").val().trim()){
        if(isNaN($("#unemploymentRete").val())){
            alert("失业比率必须是数字");
            return false;
        }
        if(parseFloat($("#unemploymentRete").val()) > 100 || parseFloat($("#unemploymentRete").val()) < 0){
            alert("失业比率取值范围：0 - 100");
            return false;
        }
    }

    if(null == $("#socialSecurityLowLevel").val() || "" == $("#socialSecurityLowLevel").val().trim()){
        alert("请填写社保最低基数");
        return false;
    }
    if(isNaN($("#socialSecurityLowLevel").val())){
        alert("社保最低基数必须是数字");
        return false;
    }
    if(parseFloat($("#socialSecurityLowLevel").val()) > 999999 || parseFloat($("#socialSecurityLowLevel").val()) < 0){
        alert("社保最低基数取值范围：0 - 999999");
        return false;
    }
    return true;
}

