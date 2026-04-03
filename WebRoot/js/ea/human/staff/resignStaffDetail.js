var contractBool = false,type = "base",seletedType="";
$(function () {
    initCss();
    getNowDate();
    //bindEvents();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - $(".div-bottom").height() - 20);
    $(".div-textarea").width($(".div-name").width() - 20);
    if ("query" !== pattern){
        new Jdate({
            el: '#dimissionDate',
            format: 'YYYY-MM-DD',
            beginYear: 1960,
            endYear: 2100
        })
    }
}
function getNowDate() {
    const dateObj = new Date();
    const year = dateObj.getFullYear(); // 获取当前年份
    const month = ("0" + (dateObj.getMonth() + 1)).slice(-2); // 获取当前月份，其中需要将月份加1，因为月份是从0开始计数的
    const day = ("0" + dateObj.getDate()).slice(-2); // 获取当前日期
    const nowDate = `${year}-${month}-${day}`; // 格式化日期
    $("#dimissionDate").val(nowDate);
}
/**
 * 选择数据
 * @param type
 */
function selectData(type) {
    seletedType = type;
    if ("query" === pattern){
        return false;
    }
    if ("staff" === type) {
        if ("edit" === pattern ){
            return false;
        }
        const param = {
            "titleName": "选择人员",
            "id": "0",
            "name": "2",
            "inputId": "staffID",
            "inputName": "staffName",
            "queryBool": true,
            "nameData": ["2", "(", "1", "---", "5", ")"],
            "callBackBool": true
        };
        const url = basePath + "ea/staff/sajax_ea_getEntryStaffData.jspa?cos.cosStatus=50";
        spdject.getDataPageByUrl(url, param);

    } else if ("dimissionStatus" === type) {
        // 选择状态
        const param = {
            "titleName": "选择类型",
            "id": "dictValue",
            "name": "dictName",
            "height": "40%",
            "inputId": "dimissionStatus",
            "inputName": "dimissionStatusName"
        };

        let url = basePath + "ea/dictdata/sajax_ea_getDictDataByDictType.jspa?type=dimissionStatus";
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
            "textLeft":"20%"
        };
        let url = basePath + "ea/contract/sajax_ea_getTempConType.jspa";
        getContractTempData(url,param);
    } else if ("salary" === type){
        document.location.href = basePath + "ea/salarySettlement/ea_find.jspa?staffId=" + $("#staffID").val();
    }
}
function callbackData(id,name,data){
    if ("staff" === seletedType){
        $("#reference").val(data[5]);
        $("#entryDate").val(data[6]);
    }
}

/**
 * 获取合同类型
 * @param urlStr
 * @param param
 * @returns {boolean}
 */
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
        //去上传
        document.location.href = basePath + "ea/androiddoc/ea_getSelectTemp.jspa?module=contract&source=rz&fileShowName=" + contractName + "&contractType=" + contractType;

    }

}
function checkData() {
    const inputId = ["dimissionDate", "dimissionStatus", "dimissionCause",  "handover"];
    const inputName = ["离职时间", "离职类型", "离职原因", "工作交接人"];
    let length = inputId.length;
    for (let i = 0; i < length; i++) {
        if ($("#" + inputId[i]).val().trim() == "") {
            layer.alert(inputName[i] + "不能为空！", {icon: 0});
            return false;
        }
    }
    return true;
}
function saveData() {
    if (!checkData()) {
        return false;
    }
    let url = "ea/staff/sajax_ea_saveResignStaffData.jspa?";
    const formData = new FormData($("#form")[0]);
    formData.append("type", pattern);
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
            if ("exist" === data){
                layer.msg("保存失败！已提交离职审核！！");
            } else {
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/resignStaff.jsp";
            }


        },
        error: function (data) {
            layer.close(layer.index);
            layer.msg("保存失败");
        }
    });
}

function getStaffContractByDocId(staffId,docId,contractName,status, v1, v1){
    type = "examine";
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

function getContract(){
    let status =  $("#contractStatusNum").val()
    let v1 = $("#contractStatusStaff").val();
    let v2 = $("#contractStatusDraft").val()
    let docId = $("#docId").val();
    viewFile(docId, status, v1, v2)
}
function viewFile(docId, status, v1, v2) {
    if (status == "I" && v1 == "" && v2 == curstaffID) {

        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=edit&opr=bd&rz=1";
    } else {
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=view&rz=1";

    }
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
