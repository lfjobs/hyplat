var timer;
var pageNumber = 0;
var pageCount = 0;
var pageYear = "2025";

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

    $("#salaryTime").change(function () {
        pageYear = $(this).val();
        pageNumber = 0;
        pageCount = 0;
        $(".salary ul").empty();
        ajax();
        $(this).blur();
        $(".salary ul").focus();
    });

});

function getHeight() {
    $("#salaryTime").val(pageYear);

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
    loadYear();
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/salarySettlement/sajax_ea_salarySettlementList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize" : 12,
            "settlementTime" : $("#salaryTime").val(),
            "staffId" : staffId
         },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                var dataList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < dataList.length; i++) {
                    var salary = dataList[i];
                    dataHtml.push('<li class="li" onclick="salaryDetail(this)" ' +
                        'id="'+ salary[1] +','+ salary[2] + ',' +
                        '' + salary[3] + ',' + salary[4] + ',' + salary[5] + '' +
                        ',' + salary[6] + ',' + salary[7] + '' +
                        ',' + salary[8] + ',' + salary[9] + ',' + salary[10] +
                        ',' + salary[11] + ',' + salary[12] + ',' + salary[13] +
                        ',' + salary[14] + ',' + salary[15] +',' + salary[16] + ',' + salary[17] +
                        ',' + salary[18] + ',' + salary[19] +',' + salary[20] +
                        ',' + salary[21] + ',' + salary[22] +',' + salary[23] +
                        ',' + salary[24] + ',' + salary[25] +',' + salary[26] +
                        '">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 5.5rem;">');
                    dataHtml.push(salary[4]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.8rem;">');
                    dataHtml.push(salary[5]);
                    dataHtml.push('</span>');
                    // dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    // dataHtml.push(salary[6]);
                    // dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[7]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary[16]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 3.3rem;">');
                    dataHtml.push('更多');
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
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
                    dataHtml.push('<span class="txtSpan" style="width: 8.2rem;">');
                    dataHtml.push(salary[14]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.3rem;">');
                    dataHtml.push(salary[15]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.3rem;">');
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
            + "&holidayOvertimeDayCount=" + arr[25];
    window.location.href = url;
}

function loadYear() {
    var yearList = [];
    var now = new Date();
    yearList.push(now.getFullYear());
    yearList.push(now.getFullYear() - 1);

    for (var i = 0; i < yearList.length; i++) {
        var year = yearList[i];
        if(year == now.getFullYear()){
            $("#salaryTime").append('<option value=' + year + ' selected>'+ year +'</option>');
        }else{
            $("#salaryTime").append('<option value=' + year + '>'+ year +'</option>');
        }
    }
    $("#salaryTime").css("background", "#fff");
    pageYear = now.getFullYear();
}

