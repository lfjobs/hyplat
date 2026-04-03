var timer;
var pageNumber = 0;
var pageCount = 0;
var settlementTimeType = "CURRENT_MONTH";

$(document).ready(function () {
    loaded();
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    //绑定滚动条事件
    $('.content').scroll(function () {
        var sTop = $(".content").scrollTop();
        sTop = parseInt(sTop);
        var height = $(window).height() * 1;
        if (sTop >= height) {
            $(".return").slideDown();
            $(".return").show();
        } else {
            $(".return").hide();
            $(this).unbind("bind");
        }
    });

    $(".return").click(function () {
        $(".content").scrollTop(0);
    });

    $("#yesterday").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "YESTERDAY";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentWeek").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "CURRENT_WEEK";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeWeek").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "BEFORE_WEEK";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentMonth").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "CURRENT_MONTH";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeMonth").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "BEFORE_MONTH";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentQuarter").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "CURRENT_QUARTER";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeQuarter").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "BEFORE_QUARTER";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentYear").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "CURRENT_YEAR";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeYear").on("click",function (){
        setBackgroundColor(this);
        settlementTimeType = "BEFORE_YEAR";
        $(".salary ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });

});

function getHeight() {
    timer = setTimeout("getHeight()", 200);
    if ($(".salary ul li:last-child").offset().top + $(".salary ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    pageNumber = 0;
    pageCount = 0;
    checkOnTimeType = "CURRENT_MONTH";
    setBackgroundColor($("#currentMonth"));
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/salarySettlement/sajax_ea_findSalaryStaffTimeList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize" : 1000,
            "settlementTimeType" : settlementTimeType
        },
        success: function cbf(data) {
            var time = findSalaryStaffTime();
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }
            var staffNameList = new Array();
            if (pageForm != null && pageForm.recordCount > 0) {
                var dataList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < dataList.length; i++) {
                    var salary = dataList[i];
                    if("YESTERDAY" == settlementTimeType || "CURRENT_WEEK" == settlementTimeType || "BEFORE_WEEK" == settlementTimeType){
                        salary = new Array();
                        salary.push(dataList[i].salaryMonthAddId);
                        salary.push(dataList[i].staffId);
                        salary.push(dataList[i].staffName);
                        salary.push(dataList[i].companyId);
                        salary.push(dataList[i].settlementMonth);
                        salary.push(dataList[i].salaryLevelNum);
                        salary.push(dataList[i].baseSalary);
                        salary.push(dataList[i].guaranteeSalary);
                        salary.push(dataList[i].welfareSalary);
                        salary.push(dataList[i].totalSalary);
                        salary.push(dataList[i].signInDayCount);
                        salary.push(dataList[i].signInSalary);
                        salary.push(dataList[i].leaveAddMoney);
                        salary.push(dataList[i].weekendOvertimeMoney);
                        salary.push(dataList[i].eightHourOvertimeMoney);
                        salary.push(dataList[i].holidayOvertimeMoney);
                        salary.push(dataList[i].realSalary);
                        salary.push(dataList[i].socialSecurityLevel);
                        salary.push(dataList[i].elderlyCareDiscountMoney);
                        salary.push(dataList[i].medicalDiscountMoney);
                        salary.push(dataList[i].unemploymentDiscountMoney);
                        salary.push(dataList[i].signInDayTotalCount);
                        salary.push(dataList[i].lateLeaveEarlyDayCount);
                        salary.push(dataList[i].leaveAddDayCount);
                        salary.push(dataList[i].weekendOvertimeDayCount);
                        salary.push(dataList[i].eightHourOvertimeDayCount);
                        salary.push(dataList[i].holidayOvertimeDayCount);
                    }
                    if(!staffNameList.includes(salary[2])){
                        staffNameList.push(salary[2]);
                    }else{
                        continue;
                    }
                    dataHtml.push('<li class="li" onclick="salaryDetail(this)" ' +
                        'id="'+ salary[1] +','+ salary[2] + ',' +
                        '' + salary[3] + ',' + time + ',' + salary[5] + '' +
                        ',' + salary[6] + ',' + salary[7] + '' +
                        ',' + salary[8] + ',' + salary[9] + ',' + salary[10] +
                        ',' + salary[11] + ',' + salary[12] + ',' + salary[13] +
                        ',' + salary[14] + ',' + salary[15] + ',' + salary[16] + ',' + salary[17] +
                        ',' + salary[18] + ',' + salary[19] +',' + salary[20] +
                        ',' + salary[21] + ',' + salary[22] +',' + salary[23] +
                        ',' + salary[24] + ',' + salary[25] +',' + salary[26] +
                        '">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 5.5rem;">');
                    dataHtml.push(salary[2]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.6rem;">');
                    dataHtml.push(salary[5]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[7]);
                    dataHtml.push('</span>');
                    // dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    // dataHtml.push(salary[6]);
                    // dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[16]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 3.3rem;">');
                    dataHtml.push('更多');
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 12.9rem;">');
                    dataHtml.push(time);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7.3rem;">');
                    dataHtml.push(salary[8]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[10]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[11]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[12]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7.3rem;">');
                    dataHtml.push(salary[13]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.3rem;">');
                    dataHtml.push(salary[14]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.3rem;">');
                    dataHtml.push(salary[15]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.2rem;">');
                    dataHtml.push(salary[17]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7.4rem;">');
                    dataHtml.push(salary[18]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7.1rem;">');
                    dataHtml.push(salary[19]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7.1rem;">');
                    dataHtml.push(salary[20]);
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".salary ul").append(dataHtml.join(""));
                $(".salary ul").css("background", "#fff");
                getHeight();
            } else {
                $(".salary ul").empty();
                $(".salary ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".salary ul").empty();
            $(".salary ul").css("background", "#fff");
            alert("工资查询失败");
        }
    });
}

function findSalaryStaffTime(){
    var salaryStaffTime = "";
    var url = basePath + "ea/salarySettlement/sajax_ea_findSalaryStaffTime.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "settlementTimeType" : settlementTimeType
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            if(null != member){
                salaryStaffTime = member.salaryStaffTime;
            }
        },
        error: function () {
        }
    });
    return salaryStaffTime;
}

function salaryDetail(obj) {
    var data = new String(obj.id);
    var arr = data.split(",");

    var url = basePath + "ea/salarySettlement/ea_details.jspa?staffId=" + arr[0]
        + "&staffName=" + arr[1]
        + "&companyId=" + arr[2]
        + "&settlementMonth=" + arr[3]
        + "&salaryLevelNum=" + arr[4]
        + "&baseSalary=" + arr[5]
        + "&guaranteeSalary=" + arr[6]
        + "&welfareSalary=" + arr[7]
        + "&totalSalary=" + arr[8]
        + "&totalSalary=" + arr[8]
        + "&signInDayCount=" + arr[9]
        + "&signInSalary=" + arr[10]
        + "&leaveAddMoney=" + arr[11]
        + "&weekendOvertimeMoney=" + arr[12]
        + "&eightHourOvertimeMoney=" + arr[13]
        + "&holidayOvertimeMoney=" + arr[14]
        + "&realSalary=" + arr[15]
        + "&socialSecurityLevel=" + arr[16]
        + "&elderlyCareDiscountMoney=" + arr[17]
        + "&medicalDiscountMoney=" + arr[18]
        + "&unemploymentDiscountMoney=" + arr[19]
        + "&signInDayTotalCount=" + arr[20]
        + "&lateLeaveEarlyDayCount=" + arr[21]
        + "&leaveAddDayCount=" + arr[22]
        + "&weekendOvertimeDayCount=" + arr[23]
        + "&eightHourOvertimeDayCount=" + arr[24]
        + "&holidayOvertimeDayCount=" + arr[25]
        + "&settlementTimeType=" + settlementTimeType;
    window.location.href = url;
}

function setBackgroundColor(obj){
    $("#yesterday").removeClass("active_color");
    $("#currentWeek").removeClass("active_color");
    $("#beforeWeek").removeClass("active_color");
    $("#currentMonth").removeClass("active_color");
    $("#beforeMonth").removeClass("active_color");
    $("#currentQuarter").removeClass("active_color");
    $("#beforeQuarter").removeClass("active_color");
    $("#currentYear").removeClass("active_color");
    $("#beforeYear").removeClass("active_color");

    $(obj).addClass("active_color");
}

