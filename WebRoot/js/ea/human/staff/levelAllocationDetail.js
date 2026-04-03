let choose ="",saveBool = false,levelExamine = false;
$(function () {
    initCss();
    bindEvents();
    initData();
    levelExamine = (/true/).test(localStorage.getItem("levelExamine"));
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - $(".div-bottom").height() - 20);
}

function initData(){
    if ("query" === pattern || "examine" === pattern){
        $(".div-examine-process").hide();
        getExamineProcessList();
    }
}

/**
 * 点击事件
 */
function bindEvents() {
    layui.use('form', function(){
        form = layui.form;
        form.render('radio');

    });
    layui.use('element', function(){
        element = layui.element;
        element.render('collapse');
    });

}

/**
 * 获取审核流程
 */
function getExamineProcessList(){
    const url = basePath + "ea/staffLevelAllocation/sajax_ea_getExamineProcessList.jspa?levelAllocationId=" +$("#levelAllocationId").val();
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            let length = codeList.length;
            const htmlStr = new Array();
            let flag = false;
            if (length > 0 ){
                for (let i = 0; i < length; i++) {
                    if(codeList[i][1] != null){
                        htmlStr.push("<li class=\"layui-timeline-item\">");
                        htmlStr.push("<i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>");
                        htmlStr.push("<div class=\"layui-timeline-content layui-text\">");
                        htmlStr.push("<h4 class=\"layui-timeline-title\" >")
                        if (codeList[i][0] == "0"){
                            htmlStr.push("<label  style='color:orange'>" + codeList[i][1] +"（待审核）</label>" );
                        } else if (codeList[i][0] == "1"){
                            htmlStr.push("<label style='color:green'>" + codeList[i][1] + "（已审核）</label>");
                            htmlStr.push(codeList[i][2]);
                        } else if (codeList[i][0] == "2"){
                            htmlStr.push("<lable style='color:red'>" + codeList[i][1] + "（已驳回）</label>");
                            htmlStr.push(codeList[i][2]);
                        } else if (codeList[i][0] == "3"){
                            htmlStr.push("<lable style='color:blue'>" + codeList[i][1] + "（已完成）</label>");
                            htmlStr.push(codeList[i][2]);
                        }
                        htmlStr.push("</h4>")
                        htmlStr.push("</div>");
                        htmlStr.push("</li>");
                        flag = true;
                    }
                }
                if (flag){
                    $(".div-examine-process").show();
                    $(".examine-process").html(htmlStr.join(""));
                } else {
                    $(".div-examine-process").hide();
                }

            } else {
                $(".div-examine-process").hide();
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

/**
 * 回调事件
 * @param id
 * @param name
 */
function callbackData(id,name){
    let url = "";
    if ("person" == choose){
        url = basePath + "ea/staffLevelAllocation/sajax_ea_getSalaryLevelDataByStaffId.jspa?staffId=" + id;
    } else if ("level" == choose){
        url = basePath + "ea/staffLevelAllocation/sajax_ea_getSalaryLevelDataBySalaryLevelId.jspa?salaryLevelId=" + id;
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            const dataJson = eval("(" + data + ")");
            if ("person" == choose){
                $("#salaryLevelSerialOld").val(dataJson["salaryLevelSerial"] + "级");
                $("#salaryLevelIdOld").val(dataJson["salaryLevelId"]);
                $("#salaryLevelNumOld").val(dataJson["salaryLevelNum"]);
                $("#salaryLevelSerialOlds").val(dataJson["salaryLevelSerial"] + "级");
                $("#guaranteeSalaryOld").val(dataJson["guaranteeSalary"]);
                $("#welfareSalaryOld").val(dataJson["welfareSalary"]);
                $("#salaryOld").val(dataJson["summarySalary"]);
                $("#workSalaryOld").val(dataJson["overtimeSalary"]);
            } else {
                $("#salaryLevelSerial").val(dataJson["salaryLevelSerial"] + "级");
                $("#salaryLevelId").val(dataJson["salaryLevelId"]);
                $("#salaryLevelNum").val(dataJson["salaryLevelNum"]);
                $("#salaryLevelSerials").val(dataJson["salaryLevelSerial"] + "级");
                $("#guaranteeSalary").val(dataJson["guaranteeSalary"]);
                $("#welfareSalary").val(dataJson["welfareSalary"]);
                $("#salary").val(dataJson["summarySalary"]);
                $("#workSalaryNow").val(dataJson["overtimeSalary"]);
            }

        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    });
}


/**
 * 选择数据
 * @param type
 */
function selectData(type){
    if("query" == pattern || "examine" == pattern){
        return false;
    }
    if("level" == type){
        choose = "level";
        //选择级别
        const param = {"titleName": "选择级别", "id": "salaryLevelId", "name": "salaryLevelSerial", "height": "40%","rightName":"级",
            "inputId":"salaryLevelId","inputName":"salaryLevelSerial","callBackBool":true};
        let url = basePath + "ea/salarylevel/sajax_ea_getLevelDataPageForm.jspa?1=1";
        eject.getDataByPage(url, param);
    } else if("person" == type){
        choose = "person";
        const param = {
            "titleName": "选择人员",
            "id": "staffID",
            "name": "staffName",
            "queryBool": true,
            "nameData": ["staffName", "(", "reference",")"],
            "callBackBool":true};
        const url = basePath + "ea/staffLevelAllocation/sajax_ea_getStaffDataList.jspa?1=1";
        spdject.getDataPageByUrl(url, param);
    }
}

function checkData(){
    let fieldArray = ["staffName","salaryLevelSerialOld","salaryLevelSerial"];
    let fieldName = ["姓名","原级别","现级别"];
    let length = fieldArray.length;
    for (let i = 0; i < length; i++){
        const field = fieldArray[i];
        value = $("#" + field).val().trim();
        if (value == ""){
            layer.msg("请填写"+ fieldName[i]);
            return false;
        }
    }
    return true;
}
/**
 * 保存数据
 */
function save(){
    if (!checkData()){
        return false;
    }
    let url = "ea/staffLevelAllocation/sajax_ea_saveAllocationData.jspa";
    const formData = new FormData($("#form")[0]);
    formData.append("type",pattern);
    saveBool = true;
    if (!saveBool){
        layer.msg("请勿重复提交");
        return false;
    }
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: formData,
        dataType:"json",
        async : true,
        processData : false,
        contentType : false,
        success: function (data) {
            saveBool = false;
            if (data === "success") {
                saveBool = false;
                layer.msg("保存成功");
                if (levelExamine && pattern === "examine"){
                    setLevel()
                } else{
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/levelAllocation.jsp?status=" + status;
                }


            } else  {
                saveBool = false;
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}

function setLevel(){
    layer.confirm('级别是否立即生效？', {
        title:'温馨提示',
        skin: 'delete-class',
        btn: ['否','是']
    }, function(){
        layer.close(layer.index);
        window.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/levelAllocation.jsp?status=" + status;
    }, function() {
        let url = "ea/staffLevelAllocation/sajax_ea_saveStaffLevelByAllocationId.jspa";
        $.ajax({
            url: basePath + url,
            type: 'POST',
            data: {"allocationId":$("#levelAllocationId").val()},
            dataType:"json",
            success: function (data) {
                if (data == "success") {
                    layer.msg("保存成功");
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/levelAllocation.jsp?status="+status;
                } else  {
                    layer.msg("保存失败");
                }
            },
            error: function (data) {
                layer.msg("保存失败");
            }
        });

    });
}
