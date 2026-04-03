let form = layui.form,basicSalaryOld = 0,type="";
$(function () {
    const guaranteeData = JSON.parse(localStorage.getItem("guaranteeData"));
    initCss();
    bindEvents();
    fillData(guaranteeData);
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-set").height($(".content").height() - $(".div-save").height() - 20);
}

/**
 * 点击事件
 */
function bindEvents() {
    layui.use('form', function(){
        form = layui.form;
        form.render('select');
        // 监听下拉框选择事件
        form.on('select', function(data){
            type = data.elem.id.replace("_salary","");
            if(data.value === '-1') {
                layer.open({
                    type: 1,
                    title: '自定义设置',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['260px', '170px'],
                    content:$(".custom")
                });
            } else if(data.value === ''){
                $("#" + type + "_multiplier").val("0");
                form.render('select',type + "_mul");
            }
        });

    });
    var basicSalaryEle = document.querySelector("#basic_basicSalary");
    basicSalaryEle.onkeyup = function(){
        const value = $("#basic_basicSalary").val();
        if("" !== value && basicSalaryOld !== 0){
            $("#level_levelSalary").val(basicSalaryOld - parseInt(value));
        }

    }
}

/**
 * 填充数据
 * @param guaranteeData
 */
function fillData(guaranteeData){
    const length = guaranteeData.length;
    for (let i = 0; i < length; i++){
        const dictKey = guaranteeData[i]["dictKey"];
        const dictId = guaranteeData[i]["dictId"];
        const dictType = guaranteeData[i]["dictType"];
        const dictName = guaranteeData[i]["dictName"];
        const dictValue = guaranteeData[i]["dictValue"];
        const id = dictType + "_" + dictName;
        if ("dateSet" === dictName){
            $("input[name='" + dictType + "_dateSet'][value='" + dictValue + "']").attr("checked", true);
        } else if ("salary" === dictName){
            if("10,20,30,40,50,".includes(dictValue + ",")){
                $("#" +id).val(dictValue);
            } else {
                $("#" + dictType + "_" + dictName + " option[value='-1']").remove();
                $("#" + id).append(new Option(dictValue + "元" , dictValue));
                $("#" + id).append(new Option("自定义", "-1"));
                $("#" + id).val(dictValue);
            }
        } else{
            $("#" + id).val(dictValue);
            if ("basic_basicSalary" === id){
                basicSalaryOld = parseInt(dictValue);
            }

        }

        $("#" + dictType + "_" + dictName + "DictKey").val(dictKey);
        $("#" + dictType + "_" + dictName + "DictId").val(dictId);
    }
}

/**
 * 保存数据
 * @returns {boolean}
 */
function saveData(){
    let url = "ea/salarylevel/sajax_ea_saveGuaranteeSalary.jspa";
    if (!checkData()){
        return false;
    }
    const data = getFieldData();
    layer.load();
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: data,
        success: function (data) {
            layer.close(layer.index);
            if (data == "success") {
                layer.msg("保存成功");
                document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSet.jsp?" + param.join("") ;
                return false;
            } else  {
                isSubmit = false;
                layer.msg("保存失败");
            }

        },
        error: function (data) {
            layer.msg("保存失败");
            layer.close(layer.index);
        }
    });
}
function getFieldData(){
    let typeArray = ["basic","role","duty","compete","secrecy","level"];
    let fieldArray;
    const typeLength = typeArray.length;
    const fieldData = {};
    for (let i = 0; i < typeLength; i++){
        let type = typeArray[i];
        if ("basic" === type){
            fieldArray = ["basicSalary","dateSet"]
        } else if ("level" === type){
            fieldArray = ["levelSalary"]
        }else {
            fieldArray = ["salary","multiplier","startValue"];
        }
        const fieldLength = fieldArray.length;
        for (let j = 0;j < fieldLength; j++){
            const field = fieldArray[j];
            const id = type + "_" + field;
            let value = "";
            if ("dateSet" == field){
                value = $("input[name='" + type + "_dateSet']:checked").val();
            } else {
                value = $("#" + id).val();
            }
            if ("multiplier" === field && "0" === value){
                value = "";
            }
            if ("startValue" === field && "0" === value){
                if (fieldData[type + "_salary"] === undefined){
                    value = "";
                }

            }
            if (value !== ""){
                fieldData[id] = value;
                fieldData[id + "DictId"] = $("#" + id  + "DictId" ).val();
                fieldData[id + "DictKey"] = $("#" + id  + "DictKey" ).val();
            }

        }
    }
    return fieldData;
}

/**
 * 检查数据
 */
function checkData(){
    let typeArray = ["basic","role","duty","compete","secrecy","level"];
    let typeName = ["基本工资","职能工资","职责工资","竞职金","保密工资","调平工资"];
    const typeLength = typeArray.length;
    let fieldArray;
    let fieldName;
    for (let i = 0; i < typeLength; i++){
        let type = typeArray[i];
        if ("basic" === type){
            fieldArray = ["basicSalary"];
            fieldName = ["基本工资"];
        } else if ("level" === type){
            fieldArray = ["levelSalary"];
            fieldName = ["调平工资"];
        }else {
            fieldArray = ["multiplier"];
            fieldName = ["升级递增倍数"];
        }
        const fieldLength = fieldArray.length;
        for (let j = 0;j < fieldLength; j++){
            const field = fieldArray[j];
            const id = type + "_" + field;
            const value = $("#" + id).val();
            if ("basic" == type || "level" === type){
                if (value === ""){
                    layer.msg("请填写"+ fieldName[j]);
                    return false;
                }
            } else {
                if (value === "0"){
                    const salary = $("#" + type  + "_salary" ).val();
                    if (salary !== ""){
                        layer.msg("请选择"+ typeName + "中的" + fieldName[i]);
                        return false;
                    }
                }
            }

        }
    }
    return true;
}

/**
 * 保存自定义
 */
function saveCustom(){
    const select = document.querySelector("#"+ type +"_salary");
    const numSet = $("#numSet").val();
    $("#"+ type +"_salary option[value='-1']").remove();
    $("#"+ type +"_salary").append(new Option(numSet + "元" , numSet));
    $("#"+ type +"_salary").append(new Option("自定义", "-1"));
    select.value = $("#numSet").val();
    form.render('select',type);
    $("#numSet").val("");
    layer.close(layer.index);

}