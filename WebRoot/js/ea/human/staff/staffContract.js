

$(function () {
    initData();
});

function  initData(){
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
        if($("#guidangName").val() != null && !"" == $("#guidangName").val() ){
            dateLen = 5;
        } else {
            dateLen = 4;
        }
    }
    let status = 2,examinePerson = "",examineTime = "";
    let statusNames = "";
    for (let i = 0; i < length; i++){
        examinePerson =  $("#" +examineStatusField[i]).val();
        if (i == 4){
            examineTime = "";
        } else {
            examineTime = $("#" + examineStatusTime[i]).val();
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
        htmlStr.push("(" + examinePerson + "--" + examineTime.substring(0,19) + ")");
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
function viewFile(){
    if(contractStatus=="I"&&v1==""&&v2==curstaffID) {
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId=" + docId + "&type=draftupdate&poe=edit&opr=bd&rz=1";
    }else{
        document.location.href = basePath + "ea/androiddoc/ea_getUpdateDocument.jspa?docId="+docId+"&type=draftupdate&poe=view&rz=1";
    }
}