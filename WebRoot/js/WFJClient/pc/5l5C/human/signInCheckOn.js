var timer;
var pageNumber = 0;
var pageCount = 0;

$(document).ready(function () {
    loaded();
    $(".checkOn-top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $("#all").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "ALL";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#today").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "TODAY";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#yesterday").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "YESTERDAY";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentWeek").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "CURRENT_WEEK";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeWeek").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "BEFORE_WEEK";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentMonth").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "CURRENT_MONTH";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeMonth").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "BEFORE_MONTH";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentQuarter").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "CURRENT_QUARTER";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeQuarter").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "BEFORE_QUARTER";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#currentYear").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "CURRENT_YEAR";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });
    $("#beforeYear").on("click",function (){
        setBackgroundColor(this);
        checkOnTimeType = "BEFORE_YEAR";
        $(".checkOn ul").empty();
        pageNumber = 0;
        pageCount = 0;
        ajax();
    });

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


});

function getHeight() {
    timer = setTimeout("getHeight()", 200);
    if ($(".checkOn ul li:last-child").offset().top + $(".checkOn ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    //默认查本周数据
    checkOnTimeType = "CURRENT_MONTH";
    setBackgroundColor($("#currentMonth"));
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/checkOn/sajax_ea_ajaxCheckOnList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize" : 100,
            "signInCheckOn.checkOnTimeType" : checkOnTimeType,
            "employee" : employee
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                var checkOnList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < checkOnList.length; i++) {
                    var checkOn = checkOnList[i];
                    dataHtml.push('<li class="checkOnLi" onclick="checkOnDetail(this)" ' +
                        'id="'+ checkOn.staffId +','+ checkOn.staffName + ',' +
                        '' + checkOn.startTime + ',' + checkOn.endTime + ',' + checkOn.lateCount + '' +
                        ',' + checkOn.lateMinutes + ',' + checkOn.lateDiscountMoney + '' +
                        ',' + checkOn.leaveEarlyCount + ',' + checkOn.leaveEarlyMinutes + ',' + checkOn.leaveEarlyDiscountMoney +
                        ',' + checkOn.leaveAbsenceDayCount + ',' + checkOn.leaveAbsenceHours + ',' + checkOn.leaveAbsenceDiscountMoney +
                        ',' + checkOn.leaveSickDayCount + ',' + checkOn.leaveSickHours + ',' + checkOn.leaveSickDiscountMoney +
                        ',' + checkOn.leaveMarriageDayCount + ',' + checkOn.leaveMarriageHours + ',' + checkOn.leaveMarriageDiscountMoney +
                        ',' + checkOn.leaveAnnualDayCount + ',' + checkOn.leaveAnnualHours + ',' + checkOn.leaveAnnualDiscountMoney +
                        ',' + checkOn.leaveMaternityDayCount + ',' + checkOn.leaveMaternityHours + ',' + checkOn.leaveMaternityDiscountMoney +
                        ',' + checkOn.leaveFuneralDayCount + ',' + checkOn.leaveFuneralHours + ',' + checkOn.leaveFuneralDiscountMoney +
                        ',' + checkOn.leaveWorkInjuryDayCount + ',' + checkOn.leaveWorkInjuryHours + ',' + checkOn.leaveWorkInjuryDiscountMoney +
                        ',' + checkOn.leaveRotationRestDayCount + ',' + checkOn.leaveRotationRestHours + ',' + checkOn.leaveRotationRestDiscountMoney +
                        ',' + checkOn.absenteeDayCount + ',' + checkOn.absenteeHours + ',' + checkOn.absenteeDiscountMoney +
                        ',' + checkOn.salaryLevelNum + ',' + checkOn.baseSalary + ',' + checkOn.guaranteeSalary + ',' + checkOn.welfareSalary +
                        ',' + checkOn.totalDiscountMoney +
                        ',' + checkOn.checkOnTimeType +
                        ',' + checkOn.outworkDayCount + ',' + checkOn.outworkHours + 
                        ',' + checkOn.weekendOverTimeDayCount + ',' + checkOn.weekendOverTimeHours + ',' + checkOn.weekendOverTimeMoney +
                        ',' + checkOn.eightHourOverTimeDayCount + ',' + checkOn.eightHourOverTimeHours + ',' + checkOn.eightHourOverTimeMoney +
                        ',' + checkOn.holidayOverTimeDayCount + ',' + checkOn.holidayOverTimeHours + ',' + checkOn.holidayOverTimeMoney +
                        '">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 5.5rem;">');
                    dataHtml.push(checkOn.staffName);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.2rem;">');
                    dataHtml.push(checkOn.lateCount + " 次");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.2rem;">');
                    dataHtml.push(checkOn.leaveEarlyCount + " 次");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.3rem;">');
                    dataHtml.push(checkOn.leaveAbsenceDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 3.5rem;">');
                    dataHtml.push(checkOn.leaveSickDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4rem;">');
                    dataHtml.push("更多");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.3rem;">');
                    dataHtml.push(checkOn.leaveMarriageDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.3rem;">');
                    dataHtml.push(checkOn.leaveAnnualDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.3rem;">');
                    dataHtml.push(checkOn.leaveMaternityDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.3rem;">');
                    dataHtml.push(checkOn.leaveFuneralDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(checkOn.leaveWorkInjuryDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 6.2rem;">');
                    dataHtml.push(checkOn.leaveRotationRestDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.4rem;">');
                    dataHtml.push(checkOn.absenteeDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4rem;">');
                    dataHtml.push(checkOn.outworkDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(checkOn.weekendOverTimeDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 6.3rem;">');
                    dataHtml.push(checkOn.eightHourOverTimeDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5rem;">');
                    dataHtml.push(checkOn.holidayOverTimeDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".checkOn ul").append(dataHtml.join(""));
                $(".checkOn ul").css("background", "#fff");
                getHeight();
            } else {
                $(".checkOn ul").empty();
                $(".checkOn ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".checkOn ul").empty();
            $(".checkOn ul").css("background", "#fff");
            alert("考勤查询失败");
        }
    });
}

function checkOnDetail(obj) {
    var data = new String(obj.id);
    var arr = data.split(",");

    var url = basePath + "ea/checkOn/ea_details.jspa?staffId=" + arr[0]
        + "&staffName=" + arr[1]
            + "&startTime=" + arr[2]
            + "&endTime=" + arr[3]
            + "&lateCount=" + arr[4]
            + "&lateMinutes=" + arr[5]
            + "&lateDiscountMoney=" + arr[6]
            + "&leaveEarlyCount=" + arr[7]
            + "&leaveEarlyMinutes=" + arr[8]
            + "&leaveEarlyDiscountMoney=" + arr[9]
            + "&leaveAbsenceDayCount=" + arr[10]
            + "&leaveAbsenceHours=" + arr[11]
            + "&leaveAbsenceDiscountMoney=" + arr[12]
            + "&leaveSickDayCount=" + arr[13]
            + "&leaveSickHours=" + arr[14]
            + "&leaveSickDiscountMoney=" + arr[15]
            + "&leaveMarriageDayCount=" + arr[16]
            + "&leaveMarriageHours=" + arr[17]
            + "&leaveMarriageDiscountMoney=" + arr[18]
            + "&leaveAnnualDayCount=" + arr[19]
            + "&leaveAnnualHours=" + arr[20]
            + "&leaveAnnualDiscountMoney=" + arr[21]
            + "&leaveMaternityDayCount=" + arr[22]
            + "&leaveMaternityHours=" + arr[23]
            + "&leaveMaternityDiscountMoney=" + arr[24]
            + "&leaveFuneralDayCount=" + arr[25]
            + "&leaveFuneralHours=" + arr[26]
            + "&leaveFuneralDiscountMoney=" + arr[27]
            + "&leaveWorkInjuryDayCount=" + arr[28]
            + "&leaveWorkInjuryHours=" + arr[29]
            + "&leaveWorkInjuryDiscountMoney=" + arr[30]
            + "&leaveRotationRestDayCount=" + arr[31]
            + "&leaveRotationRestHours=" + arr[32]
            + "&leaveRotationRestDiscountMoney=" + arr[33]
            + "&absenteeDayCount=" + arr[34]
            + "&absenteeHours=" + arr[35]
            + "&absenteeDiscountMoney=" + arr[36]
            + "&salaryLevelNum=" + arr[37]
            + "&baseSalary=" + arr[38]
            + "&guaranteeSalary=" + arr[39]
            + "&welfareSalary=" + arr[40]
            + "&totalDiscountMoney=" + arr[41]
            + "&checkOnTimeType=" + arr[42]
            + "&outworkDayCount=" + arr[43]
            + "&outworkHours=" + arr[44]
            + "&weekendOverTimeDayCount=" + arr[45]
            + "&weekendOverTimeHours=" + arr[46]
            + "&weekendOverTimeMoney=" + arr[47]
        + "&eightHourOverTimeDayCount=" + arr[48]
        + "&eightHourOverTimeHours=" + arr[49]
        + "&eightHourOverTimeMoney=" + arr[50]
        + "&holidayOverTimeDayCount=" + arr[51]
        + "&holidayOverTimeHours=" + arr[52]
        + "&holidayOverTimeMoney=" + arr[53];
    window.location.href = url;
}

function setBackgroundColor(obj){
    $("#all").removeClass("active_color");
    $("#today").removeClass("active_color");
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

