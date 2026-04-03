let form = layui.form,basicSalaryOld = "";
$(function () {

    initCss();
    bindEvents();
    getDictDataListByType();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-set").height($(".content").height() - $(".div-save").height() - 20);
}


function getDictDataListByType() {
    let url = basePath + "ea/salarylevel/sajax_ea_getDictDataListByType.jspa?type=" + type;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            const listData = eval("(" + data + ")");
            let listDataLength = listData.length;
            if (listDataLength > 0){
                for (let i = 0; i < listData.length; i++) {
                    const dictName = listData[i].dictName;
                    const dictValue = listData[i].dictValue;
                    const dictId = listData[i].dictId;
                    const dictKey = listData[i].dictKey;
                    if ("dateSet" == dictName){
                        $("input[name='dateSet'][value='" + dictValue + "']").attr("checked", true);
                    } else if ("salary" == dictName){
                        if("10,20,30,40,50,".includes(dictValue + ",")){
                            $("#" + dictName).val(dictValue);
                        } else {
                            $("#salary option[value='-1']").remove();
                            $("#salary").append(new Option(dictValue + "元" , dictValue));
                            $("#salary").append(new Option("自定义", "-1"));
                            $("#" + dictName).val(dictValue);
                        }
                    } else{
                        $("#" + dictName).val(dictValue);
                    }
                    $("#" + dictName + "DictId").val(dictId);
                    $("#" + dictName + "DictKey").val(dictKey);
                    if("basicSalary" === dictName){
                        basicSalaryOld = dictValue;
                    }
                }
            }
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    });
}

/**
 * 点击事件
 */
function bindEvents() {
    layui.use('form', function(){
        form = layui.form;
        form.render('select');
        // 监听下拉框选择事件
        form.on('select(salarySet)', function(data){
            if(data.value === '-1') {
                layer.open({
                    type: 1,
                    title: '自定义设置',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['260px', '170px'],
                    content:$(".custom")
                });
            }
        });

    });
}


function saveData(){
    let url = "ea/salarylevel/sajax_ea_saveDictData.jspa";
    const data = getFieldData();
    if (!checkData()){
        return false;
    }

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
    let fieldArray;
    if ("basic" === type){
        fieldArray = ["basicSalaryDictKey","basicSalaryDictId","basicSalary","dateSet","dateSetDictId","dateSetDictKey"]
    } else if ("level" === type){
        fieldArray = ["levelSalaryDictKey","levelSalaryDictId","levelSalary"]
    }else {
        fieldArray = ["salary","multiplier","salaryDictKey","salaryDictId","multiplierDictKey","multiplierDictId","startValue","startValueDictKey","startValueDictId",];
    }
    const length = fieldArray.length;
    const fieldData = {};
    for (let i = 0; i < length; i++){
        const field = fieldArray[i];
        if ("dateSet" == field){
            fieldData[field] = $('input[name="dateSet"]:checked').val();
        } else {
            fieldData[field] = $("#" + field).val();
        }
    }
    fieldData["type"] = type;
    fieldData["basicSalaryOld"] = basicSalaryOld;
    return fieldData;
}

/**
 * 检查数据
 */
function checkData(){
    let fieldArray;
    let fieldName;
    if ("basic" == type){
        fieldArray = ["basicSalary"];
        fieldName = ["基本工资"];
    } else if ("level" == type){
        fieldArray = ["levelSalary"];
        fieldName = ["调平工资"];
    }else {
        fieldArray = ["salary","multiplier"];
        fieldName = ["5级工资","升级递增倍数"];
    }
    const length = fieldArray.length;
    let value;
    for (let i = 0; i < length; i++){
        const field = fieldArray[i];
        value = $("#" + field).val();
        if (value == ""){
            if (field == "basicSalary"){
                layer.msg("请填写"+ fieldName[i]);
            } else {
                layer.msg("请选择"+ fieldName[i]);
            }
            return false;
        }
    }
    return true;
}
function cancel(){
    document.getElementById('levelNum').value = "";
    form.render('select');
    layer.close(layer.index);
}
/**
 * 保存自定义
 */
function saveCustom(){
    const select = document.getElementById('salary');
    const numSet = $("#numSet").val();
    $("#salary option[value='-1']").remove();
    $("#salary").append(new Option(numSet + "元" , numSet));
    $("#salary").append(new Option("自定义", "-1"));
    select.value = $("#numSet").val();
    form.render('select');
    $("#numSet").val("");
    layer.close(layer.index);

}

