

$(function () {
    initCss();

});

/**
 * 初始化样式
 */
function initCss() {
    $(".content .div-base-data .div-input").width($(".content").width() - 76 - 30);
    $(".content").height($(window).height() - $("header").height());
    $(".div-staffAddress").css("height","auto");
    $(".div-staffAddress .label-staffAddress").width($(".content").width() - 76 - 30);
}



/**
 * 选择数据
 * @param type
 */
function selectData(type) {
    if("interviewReceipt" === type){
        //面试收件
        document.location.href = basePath+"ea/bidrecruit/ea_getTalentResumeList.jspa?staffID="+$("#staffId").val();
    } else if("resume" === type){
        // 个人简历
        document.location.href = basePath+"/ea/staffRecord/ea_getResumeDataByStaffID.jspa?staffID="+$("#staffId").val();
    } else if ("interviewResult" === type){
        // 面试结果
        document.location.href = basePath+"/page/WFJClient/pc/5l5C/human/staff/staffRecordDetail.jsp?type=interviewResult&staffID="+$("#staffId").val();
    } else if ("staffData" === type){
        // 入职信息
        document.location.href = basePath + "ea/staff/ea_getStaffDataByStaffId.jspa?type=readonly&&staffId=" + $("#staffId").val();
    } else if ("salaryLevel" === type){
        // 升降级
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/levelAllocation.jsp?staffId=" + $("#staffId").val();
    } else if ("interviewRegistration" === type){
        // 面试登记
        document.location.href = basePath+"/page/WFJClient/pc/5l5C/human/staff/staffRecordDetail.jsp?type=interviewRegistration&staffID="+$("#staffId").val();
    } else if ("entryContract" === type){
        // 入职合同
        document.location.href = basePath+"/page/WFJClient/pc/5l5C/human/staff/employmentContract.jsp?staffID="+$("#staffId").val();
    } else if ("projectPlan" === type){
        // 项目计划
        document.location.href = basePath+"/ea/scBudget/ea_toPlanCostBudgetBillList.jspa?tenantFlag=other&menuId=ng&menuName=&companyid=" + companyId + "&staffID="+$("#staffId").val();
    } else if ("signData" === type){
        // 签到考勤
        document.location.href = basePath+"/ea/checkOn/ea_find.jspa" ;
    } else if ("staffSalary" === type){
        // 人员工资
        document.location.href = basePath+"/ea/salarySettlement/ea_find.jspa?staffId="+$("#staffId").val();
    } else if ("salaryLevelData" === type){
        // 工资级差
        document.location.href = basePath+"/page/WFJClient/pc/5l5C/human/salary/salaryData.jsp";
    } else if ("resignationData" === type){
        // 离职人员
        document.location.href = basePath+"/ea/staff/ea_getResignStaffDataByStaffId.jspa?type=query&staffId=" + $("#staffId").val();
    }
}
