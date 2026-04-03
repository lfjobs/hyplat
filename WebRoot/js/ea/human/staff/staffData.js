let roleList = [], fileId = "", fileIdList = [], fileData = {}, seletedType = "", deleteFileIds = "",
    regExp = /(^[1-9]\d{7}(?:0\d|10|11|12)(?:0[1-9]|[1-2][\d]|30|31)\d{3}$)|(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)/;
    type = "base",contractBool = false;

const upload = layui.upload;

$(function () {
    initCss();
    initData();
    bindEvents();
    if ("add" === pattern) {
        getNowDate();
    }

});

/**
 * 初始化样式
 */
function initCss() {
    if ("query" == pattern ) {
        $(".i-icon-right").hide();
        $(".div-bottom").height(0);
        $(".div-bottom").hide();
        $(".div-file .div-file-no").show();
        $(".div-file .div-icon-upload").hide();
        $(".div-name-edit").attr("readonly",true);
    } else {
        $(".i-icon-right").show();
        $(".div-bottom").height("2rem");
        $(".div-bottom").show();
        $("#edit").hide();
        $(".div-file .div-file-no").hide();
        $(".div-file .div-icon-upload").show();
        if ("edit" === pattern) {
            $(".div-baseData .i-icon-right").hide();
            $(".div-name-edit").attr("readonly",false);
        }
    }
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - $(".div-bottom").height() - 20);
    if ("query" !== pattern && "readonly" !== pattern) {
        new Jdate({
            el: '#entryDate',
            format: 'YYYY-MM-DD',
            beginYear: 1960,
            endYear: 2100
        })

    }
    if ("add" === pattern) {
        $(".div-resign").hide();
    }
    if (readonlyBool){
        $("#edit").hide();
        $(".div-bottom").hide();
    }

}

function initData() {
    if ("edit" == pattern || "query" == pattern) {
        const param = new Array();
        param.push("staffId=" + $("#staffID").val());
        const url = basePath + "ea/staff/sajax_ea_getFileDataByStaffId.jspa?" + param.join("");
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                const codeList = eval("(" + data + ")");
                const length = codeList.length;
                for (let i = 0; i < length; i++) {
                    fileDataHtml(codeList[i].fileID, codeList[i].fileAttr, codeList[i].fileKey, codeList[i].fileName, codeList[i].filepath);
                }

            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    }
}

/**
 * 点击事件
 */
function bindEvents() {

    $("#sex").click(function () {
        checkIdentityCard();
    })
    $("#birthday").click(function () {
        checkIdentityCard();
    })
    $("#edit").click(function () {
        pattern = "edit";
        initCss();
    })
}

/**
 * 选择数据
 * @param type
 */
function selectData(type) {
    if ("query" === pattern) {
        return false;
    }
    /*if ("edit" === pattern && ("staff" === type || "level" === type)) {
        return false;
    }*/
    if ("edit" === pattern && ("staff" === type)) {
        return false;
    }
    seletedType = type;
    if ("staff" === type) {
        const param = {
            "titleName": "选择人员",
            "id": "staffID",
            "name": "staffName",
            "queryBool": true,
            "nameData": ["staffName", "(", "staffCode", "---", "reference", ")"],
            "callBackBool": true
        };
        const url = basePath + "ea/staff/sajax_ea_getNeedJoinStaffData.jspa?1=1";
        spdject.getDataPageByUrl(url, param);

    } else if ("dept" === type) {
        // 选择部门
        const param = new Array();
        param.push("selectedId=" + $("#deptId").val());
        param.push("&&type=checkbox");
        const url = basePath + "page/WFJClient/pc/5l5C/human/staff/selectDept.jsp?" + param.join("");
        layer.open({
            type: 2,
            title: false,
            closeBtn: false,
            anim: 5,
            isOutAnim: false,
            content: url,
            area: ['100%', '100%'],
        });
    } else if ("post" === type) {
        // 选择岗位
        const param = {
            "titleName": "选择岗位", "id": "0", "name": "1",
            "nameData": ["3", "-", "1",], "inputId": "depPostID", "inputName": "postName", "type": "checkbox"
        };
        const url = basePath + "ea/post/sajax_ea_getPostListByDeptIds.jspa?";
        spdject.getDataPageByUrl(url, param);
        //eject.getDataByUrl(url, param);
    } else if ("role" === type) {
        // 选择角色
        const param = {
            "titleName": "选择角色",
            "id": "roleID",
            "name": "roleName",
            "height": "40%",
            "type": "checkbox"
        };
        const url = basePath + "ea/staff/sajax_ea_getRoleList.jspa?";
        eject.getDataByUrl(url, param);
    } else if ("level" === type) {
        //选择级别
        const param = {
            "titleName": "选择级别",
            "id": "salaryLevelId",
            "name": "salaryLevelSerial",
            "height": "40%",
            "rightName": "级",
            "inputId": "salaryLevelId",
            "inputName": "salaryLevelSerial"
        };
        let url = basePath + "ea/salarylevel/sajax_ea_getLevelDataPageForm.jspa?1=1";
        eject.getDataByPage(url, param);
    } else if ("status" === type) {
        // 选择状态
        const param = {
            "titleName": "选择类型",
            "id": "dictValue",
            "name": "dictName",
            "height": "40%",
            "inputId": "statusId",
            "inputName": "statusName",
            "callBackBool":true
        };
        let url = basePath + "ea/dictdata/sajax_ea_getDictDataByDictType.jspa?type=staffStatus";
        eject.getDataByUrl(url, param);
    } else if ("contractType" === type) {
        // 选择合同类型
        const param = {
            "titleName": "选择类型",
            "id": "7",
            "name": "4",
            "height": "40%",
            "inputId": "contractTypeId",
            "inputName": "contractTypeName",
            "type": "checkbox",
            "textLeft":"25%"
        };
        let url = basePath + "ea/contract/sajax_ea_getTempConType.jspa";
        getContractTempData(url,param);


        //eject.getDataBySqlUrl(url, param);
        //uploadContract();
    } else if ("education" === type) {
        // 选择合同类型
        const param = {
            "titleName": "选择学历", "id": "dictName", "name": "dictName", "height": "40%", "inputId": "culturalDegree",
            "inputName": "culturalDegree"
        };
        let url = basePath + "ea/dictdata/sajax_ea_getDictDataByDictType.jspa?type=education";
        eject.getDataByUrl(url, param);
    }


}

function getContractTempData(urlStr,param){
    if (contractBool){
        return false;
    }
    contractBool = true;
    eject.initData(param);
    t.url = urlStr;
    t.paramData = param;
    $.ajax({
        url: encodeURI(t.url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            const dataList = eval("(" + data + ")");
            const list = dataList.list;
            if (list.length > 0){
                eject.getDataOpen(list,param);
                var isSet = dataList.isSet;
                for (var i = 0; i < list.length; i++) {
                    var obj = list[i];
                    var templatePath = obj[0];
                    var templateId = obj[1];
                    var fileType = obj[2];
                    var sysSet = obj[3];
                    var fileShowName = obj[4];
                    var temptId = obj[5];
                    var templateTypeName = obj[6];
                    var contractType = obj[7];
                    $("#select-" + contractType).find("label").after(
                        "<label style='float:right;margin-left:30px' onclick=deleteCompanyContract('" + templateId +"')>删除</label>" +
                        '<label style="float:right;margin-left:10px" onclick="tempview(\'' + contractType + '\',1,\'' + templatePath + '\',\'' + fileShowName + '\',\'' + temptId + '\',\'' + templateTypeName + '\',\'' + fileType + '\',\'' + sysSet + '\',\'' + templateId + '\',\'' + fileShowName + '\',\'' + isSet + '\',event)">编辑查看</label>');

                }

            }
            contractBool = false;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
function deleteCompanyContract(templateId){
    layer.confirm('确定删除', {
        title:'温馨提示',
        skin: 'delete-class',
        btn: ['取消','确定']
    }, function(){
        layer.close(layer.index);
    }, function() {

        var url = basePath
            + "ea/staff/sajax_ea_deleteRelateDoc.jspa?";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data: {
                templateId: templateId,
            },
            success: function (data) {
                if (data === "success"){
                    layer.msg("保存成功");
                    layer.closeAll();
                    selectData("contractType");
                } else {
                    layer.msg("该模板已被使用，不能删除！！");
                }

            }, error: function cbf(data) {
                console.log("数据获取失败！")
            }

        })

    });

}
function callbackDept(deptId, deptName) {
    $("#deptName").val(deptName);
    $("#deptId").val(deptId);
}

function callbackData(id, name, data) {
    if ("staff" === seletedType) {
        $("#staffIdentityCard").val(data["staffIdentityCard"]);
        $("#reference").val(data["reference"]);
        $("#staffKey").val(data["staffKey"]);
        $("#birthday").val(data["birthday"]);
        $("#sex").val(data["sex"]);
        $("#staffAddress").val(data["staffAddress"]);
        $("#workYear").val(data["workYear"]);
    } else if ("status" === seletedType){
        if ("01" !== id){
            $(".div-contract .span-red").hide();
        }
    }
}

function getNowDate() {
    const dateObj = new Date();
    const year = dateObj.getFullYear(); // 获取当前年份
    const month = ("0" + (dateObj.getMonth() + 1)).slice(-2); // 获取当前月份，其中需要将月份加1，因为月份是从0开始计数的
    const day = ("0" + dateObj.getDate()).slice(-2); // 获取当前日期
    const nowDate = `${year}-${month}-${day}`; // 格式化日期
    $("#entryDate").val(nowDate);
}

/**
 * 检查数据
 */
function checkData() {
    const inputId = ["staffName", "postName", "roleName", "salaryLevelSerial", "reference", "entryDate", "staffIdentityCard", "statusName",];
    const inputName = ["姓名", "岗位", "角色", "级别", "手机号", "入职时间", "身份证", "员工类型"];
    let length = inputId.length;
    for (let i = 0; i < length; i++) {
        if ($("#" + inputId[i]).val().trim() == "") {
            layer.alert(inputName[i] + "不能为空！", {icon: 0});
            return false;
        }
    }
    const staffIdentityCard = $("#staffIdentityCard").val().trim();
    if (!regExp.test(staffIdentityCard)) {
        layer.alert("身份证号有误，请重新填写！！", {icon: 0});
        return false;
    }
    const reference = $("#reference").val().trim();
    if (reference.length != 11) {
        layer.alert("手机号有误，请重新填写！！", {icon: 0});
        return false;
    }
    const statusId = $("#statusId").val().trim();
    if ("01" === statusId){
        if ($("#contractTypeName").val().trim() === "") {
            layer.alert( "签订合同不能为空！", {icon: 0});
            return false;
        }
    }
    return true;
}

function clickFile(id) {
    fileId = id;
    upload.render({
        elem: '#' + fileId,
        url: basePath + 'ea/file/sajax_ea_uplodFile.jspa?companyID=' + companyID,
        field: "file",
        multiple: true,
        number: 2,
        done: function (res, index, upload) { //上传后的回调
            const path = res.path;
            const name = res.name;
            const siSuccess = res.siSuccess;
            if (siSuccess) {
                fileDataHtml(index, fileId, "", name, path);
            }
        }
    })
}

function fileDataHtml(index, fileId, fileKey, name, path) {
    const htmlStr = new Array();
    htmlStr.push("<div class ='div-img div-img-" + index + "'>");
    htmlStr.push("<img src=" + basePath + path + ">");
    htmlStr.push("<div class=\"div-delete-img\" onclick=\"deleteFile('" + index + "')\">");
    htmlStr.push("<i class=\"layui-icon div-icon-delete\" style=\"font-size:26px\" >&#x1007;</i>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    $(".div-" + fileId).append(htmlStr.join(""));
    $(".div-" + fileId + " .div-file-no").hide();
    fileIdList.push(index);
    fileData[index] = {
        "fileKey": fileKey,
        "fileID": index,
        "filename": name,
        "filepath": path,
        "fileType": "staffData",
        "fileAttr": fileId
    };
}

function deleteFile(id) {
    $(".div-img-" + id).remove();
    fileIdList = fileIdList.filter(item => item !== id);
    if (deleteFileIds === "") {
        deleteFileIds = id;
    } else {
        deleteFileIds += "," + id;
    }
}

/**
 * 检查身份证号
 */
function checkIdentityCard() {
    const idCard = $("#staffIdentityCard").val();
    // 15位/18位身份证号
    let birthday, sex;
    if (regExp.test(idCard)) {
        if (idCard.length === 15) {
            // 获取出生日期
            let Y = idCard.substring(6, 1) == 0 ? '20' : '19';
            birthday = Y + [idCard.substring(6, 8), idCard.substring(8, 10), idCard.substring(10, 12)].join('-')
            // 获取性别
            sex = ['女', '男'][idCard.substring(14, 15) % 2]
        } else {
            // 获取出生日期
            birthday = [idCard.substring(6, 10), idCard.substring(10, 12), idCard.substring(12, 14)].join('-')
            // 获取性别
            sex = ['女', '男'][idCard.substring(16, 17) % 2]
        }
        $("#sex").val(sex);
        $("#birthday").val(birthday);
    } else {
        layer.msg("身份证号有误，请重新填写！！");
        return false;
    }


}

function saveData() {
    if (!checkData()) {
        return false;
    }
    if ($("#sex").val() == "" || $("#sex").val() == null) {
        checkIdentityCard();
    }
    const fileDataList = [];
    for (let i = 0; i < fileIdList.length; i++) {
        fileDataList.push(fileData[fileIdList[i]]);
    }
    let url = "ea/staff/sajax_ea_saveStaffData.jspa?";
    const formData = new FormData($("#form")[0]);
    formData.append("fileData", JSON.stringify(fileDataList));
    formData.append("type", pattern);
    formData.append("postIds", $("#depPostID").val());
    formData.append("deleteFileIds", deleteFileIds)
    layer.load();
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function (data) {
            layer.close(layer.index);
            if (data == "success") {
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/entryManage.jsp";
            } else {
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.close(layer.index);
            layer.msg("保存失败");
        }
    });
}


function uploadContract() {

    setTimeout(function () {
        $.ajax({
            url: basePath + "ea/contract/sajax_ea_getTempConType.jspa",
            type: 'POST',
            dataType: "json",
            async: true,
            processData: false,
            contentType: false,
            success: function (data) {
                var m = eval("(" + data + ")");
                var list = m.list;
                var isSet = m.isSet;

                $("#ejectList li").each(function () {
                    var id = $(this).attr("id");
                    var contractType = id.slice(-2);
                    var contractName = $(this).find("label").eq(0).text();
                    var exist = 0;
                    var viewer = "上传模板";
                    var templatePath = "";
                    var temptId = "";
                    var templateTypeName = "";
                    var fileType = "";
                    var sysSet = "";
                    var templateId = "";
                    var fileShowName = "";
                    for (var i = 0; i < list.length; i++) {
                        var obj = list[i];
                        if (contractType == obj[7]) {
                            exist = 1;
                            viewer = "编辑模板";
                            templatePath = obj[0];
                            templateId = obj[1];
                            fileType = obj[2];
                            sysSet = obj[3];
                            fileShowName = obj[4];
                            temptId = obj[5];
                            templateTypeName = obj[6];
                            break;
                        }
                    }

                    $(this).find("label").after('<label style="float:right;margin-left:10px" onclick="tempview(\'' + contractType + '\',\'' + exist + '\',\'' + templatePath + '\',\'' + contractName + '\',\'' + temptId + '\',\'' + templateTypeName + '\',\'' + fileType + '\',\'' + sysSet + '\',\'' + templateId + '\',\'' + fileShowName + '\',\'' + isSet + '\',event)">' + viewer + '</label>');

                });
            },
            error: function (data) {

            }
        });

    }, 300)

}

/**
 *
 * 查看或者上传
 * @param contractType
 * @param exist
 */
function tempview(contractType, exist, templatePath, contractName, temptId, templateTypeName, fileType, sysSet, templateId, fileShowName, isSet, event) {
    eject.closeData();
    event.stopPropagation();
    if (exist == "1") {
        document.location.href = basePath + "/page/ea/main/office_ea/contract/tempAdd.jsp?opr=update&sysSet=01&fileShowName=" + fileShowName + "&temptId=" + temptId + "&templateTypeName=" + templateTypeName + "&fileType=" + fileType + "&docPath=" + templatePath + "&templateId=" + templateId + "&isSet=" + isSet + "&module=contract&contractType=" + contractType;
    } else {
        ///  document.location.href = basePath+"/page/ea/main/office_ea/contract/tempAdd.jsp?opr=bd&isSet=0&templateTypeName=&temptId=&sysSet=01&fileShowName="+contractName+"&contractType="+contractType+"&module=contract";
        //去上传
        document.location.href = basePath + "ea/androiddoc/ea_getSelectTemp.jspa?module=contract&source=rz&fileShowName=" + contractName + "&contractType=" + contractType;

    }

}

function viewFile(docId, status, v1, v2) {
    if (status == "I" && v1 == "" && v2 == curstaffID ) {
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=edit&opr=bd&rz=1";
    } else {
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=view&rz=1";

    }
}

function getContract(){
    let status =  $("#contractStatusNum").val()
    let v1 = $("#contractStatusStaff").val();
    let v2 = $("#contractStatusDraft").val()
    let docId = $("#docId").val();
    viewFile(docId, status, v1, v2)
}
function getStaffContractByDocId(staffId,docId,contractName,status, v1, v1){
    type = "examine";
    $("#edit").hide();
    $(".div-base").hide();
    $(".div-examine-process").show();
    $("#docId").val(docId);
    $("#contractTypeNameDetail").val(contractName);
    $("#contractStatusNum").val(status);
    $("#contractStatusStaff").val(v1);
    $("#contractStatusDraft").val(v1);
    const url = basePath + "ea/staff/sajax_ea_getContractDataByDocId.jspa?staffId=" + staffId + "&&docId=" + docId;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const dataJson = eval("(" + data + ")");
            getExamineData(dataJson);
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function  getExamineData(dataJson){
    const contractStatus = dataJson["status"];
    let examineStatusName =["拟稿","审批","盖章签约","公文分发","公文阅读","公文归档"];
    let examineStatusTime =["draftTime","subscribeTime","sealTime","publishTime","readTime","guidangTime"];
    let examineStatusField =["drafterName","subscriberName","sealerName","publisherName","receiverName","guidangName"];
    let length = examineStatusName.length;
    let statusName = "";
    const htmlStr = new Array();
    let dateLen = 0;
    if (contractStatus == "I"){
        dateLen = 0;
    } else  if (contractStatus == "S" || contractStatus == "T"){
        dateLen = 1;
    }  else  if (contractStatus == "A" ){
        dateLen = 2;
        statusName = "待签约";
    } else  if (contractStatus == "F" ){
        dateLen = 2;
        statusName = "已签约";
    } else  if (contractStatus == "P" ){
        dateLen = 3;
    } else  if (contractStatus == "O" ){
        if($("#guidangName").val() != null && !"".equals($("#guidangName").val()) ){
            dateLen = 5;
        } else {
            dateLen = 4;
        }
    } else {
        dateLen = 4;
        statusName = "已阅读";
    }
    let status = 2,examinePerson = "",examineTime = "";
    let statusNames = "";
    for (let i = 0; i < length; i++){
        examinePerson =  dataJson[examineStatusField[i]];
        if (i == 4){
            examineTime = "";
        } else {
            examineTime = dataJson[examineStatusTime[i]];
        }
        if(i < dateLen){
            status = 1;
            statusNames = examineStatusName[i];
        } else if (i == dateLen){
            status = 0;
            if (statusName == ""){
                if (contractStatus == "I" && $("#subscriberName2").val() != "" && $("#subscriberName2").val() != null){
                    statusNames = examineStatusName[i];
                    status = 1;
                }else {
                    statusNames = examineStatusName[i]+"中";
                }

            } else {
                statusNames = examineStatusName[i] + "(" + statusName + ")";
            }
            $("#contractStatus").val(examineStatusName[i]);
            $("#contractStatsName").val(examinePerson);
        } else {
            status = 2;
            statusNames = examineStatusName[i];
        }
        getExamineHtml(htmlStr,status,statusNames,examinePerson,examineTime);
        if ( i == 0 && contractStatus == "I" && $("#subscriberName2").val() != "" && $("#subscriberName2").val() != null){
            if (contractStatus == "I" && i == dateLen ){
                statusName = "传阅中";
                status = 0;
            } else {
                statusName = "已传阅";
                status = 1;
            }
            examinePerson = $("#subscriberName2").val();;
            getExamineHtml(htmlStr,status,statusName,"subscriberName2",examinePerson,examineTime);
        }

    }
    $(".examine-process").html(htmlStr.join(""));
}
function getExamineHtml(htmlStr,status,statusName,examinePerson,examineTime){
    htmlStr.push("<li class=\"layui-timeline-item\">");
    htmlStr.push("<i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>");
    htmlStr.push("<div class=\"layui-timeline-content layui-text\">");
    htmlStr.push("<h4 class=\"layui-timeline-title\" >")
    if (status == 1){
        // 已执行
        htmlStr.push("<label style='color:green'>" + statusName + "</label>");
        htmlStr.push("(" + examinePerson + "--" + timestampToDateTime(examineTime) + ")");
        htmlStr.push("</label>");
    } else if (status == 0){
        // 待执行
        htmlStr.push("<label  style='color:orange'>" + statusName );
        htmlStr.push("--" +examinePerson);
        htmlStr.push("</label>");
    } else {
        htmlStr.push("<lable style='color:red'>" +  statusName + "</label>");
    }
    htmlStr.push("</h4>")
    htmlStr.push("</div>");
    htmlStr.push("</li>");
}

/**
 * 时间错转日期
 * @param timestamp
 * @returns {string}
 */
function timestampToDateTime(timestamp) {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 获取月份并补零
    var day = date.getDate().toString().padStart(2, '0'); // 获取日期并补零
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds} `; // 拼接字符串返回
}


function returnPage(){
    if ("base"== type){
        window.history.back();
        return false;
    } else {
        $("#edit").show();
        $(".div-base").show();
        $(".div-examine-process").hide();
        type = "base";
    }

}