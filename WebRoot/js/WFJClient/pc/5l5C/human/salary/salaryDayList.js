var timer;
var pageNumber = 0;
var pageCount = 0;
var staffId = '';
var companyId = '';
var settlementTimeType = '';

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
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        staffId = params.get("staffId");
        companyId = params.get("companyId");
        $("#staffName").html(params.get("staffName"));
        settlementTimeType = params.get("settlementTimeType").trim();
        var settlementMonth = params.get("settlementMonth").trim();
        $("#settlementMonth").html(settlementMonth);
        if(settlementMonth.length > 7){
            $("#settlementMonthLabel").html("时间");
        }else{
            $("#settlementMonthLabel").html("月份");
        }
    }
    pageNumber = 0;
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/salarySettlement/sajax_ea_salarySettlementDayList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize" : 1000,
            "settlementTime" : $("#settlementMonth").html(),
            "settlementTimeType" : settlementTimeType,
            "staffId" : staffId,
            "companyId" : companyId
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
                    dataHtml.push('<li class="li" onclick="salaryDayDetail(this)" ' +
                        'id="'+ salary.date + ',' + salary.salaryLevelNum +','+ salary.realGuaranteeSalary + ',' +
                        '' + salary.realWelfareSalary + ',' + salary.signInSalary + ',' + salary.signInDayCount + '' +
                        ',' + salary.leaveType + ',' + salary.leaveDayCount + '' +
                        ',' + salary.overtimeType + ',' + salary.overtimeDayCount + ',' + salary.overtimeMoney +
                        ',' + salary.realSalary +
                        '">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 6.2rem;">');
                    dataHtml.push(salary.date);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 6.4rem;">');
                    dataHtml.push(salary.salaryLevelNum);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 4.8rem;">');
                    dataHtml.push(salary.signInDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5rem;">');
                    dataHtml.push(salary.realSalary);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 3.3rem;">');
                    dataHtml.push('更多');
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.6rem;">');
                    dataHtml.push(salary.leaveType);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary.leaveDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary.realGuaranteeSalary);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8.3rem;">');
                    dataHtml.push(salary.realWelfareSalary);
                    dataHtml.push('</span>');
                    // dataHtml.push('<span class="txtSpan" style="width: 7.3rem;">');
                    // dataHtml.push(salary.signInSalary);
                    // dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 6.2rem;">');
                    dataHtml.push(salary.overtimeType);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary.overtimeDayCount + " 天");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.3rem;">');
                    dataHtml.push(salary.overtimeMoney);
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

function salaryDayDetail(obj) {
    var data = new String(obj.id);
    var arr = data.split(",");

    var url = basePath + "ea/salarySettlement/ea_dayDetails.jspa?staffName=" + $("#staffName").html()
        + "&date=" + arr[0]
            + "&salaryLevelNum=" + arr[1]
            + "&realGuaranteeSalary=" + arr[2]
            + "&realWelfareSalary=" + arr[3]
        +"&signInSalary=" + arr[4]
        + "&signInDayCount=" + arr[5]
        +"&leaveType=" + arr[6]
        +"&leaveDayCount=" + arr[7]
        +"&overtimeType=" + arr[8]
        +"&overtimeDayCount=" + arr[9]
        +"&overtimeMoney=" + arr[10]
        +"&realSalary=" + arr[11];
    window.location.href = url;
}

