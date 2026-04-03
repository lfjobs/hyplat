'use strict';
var staffId = '';
var checkOnType = 'LATE';

$(document).ready(function () {
    loadData();
    $(".checkOn-top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 2.5 + "px;position:relative;");
    showDetail();
});

function loadData(){
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        staffId = params.get("staffId");
        checkOnType = params.get("checkOnType");
        $("#staffName").html(params.get("staffName"));
        var checkOnTime = params.get("checkOnTime");
        var startTime = params.get("startTime");
        var endTime = params.get("endTime");
        $("#checkOnTime").html(checkOnTime + "&nbsp;&nbsp;&nbsp;&nbsp;" + startTime + "&nbsp;&nbsp;-&nbsp;&nbsp;" + endTime);
        $("#salaryLevelNum").html(params.get("salaryLevelNum"));
        // $("#baseSalary").html(params.get("baseSalary"));
        $("#guaranteeSalary").html(params.get("guaranteeSalary"));
        $("#welfareSalary").html(params.get("welfareSalary"));

        if("LATE" == checkOnType){
            $("#checkOnDate").html("迟到日期");
            $("#time").html("迟到分钟");
            $("#title").html("上班打卡时间");
        }else if("LEAVE_EARLY" == checkOnType){
            $("#checkOnDate").html("早退日期");
            $("#time").html("早退分钟");
            $("#title").html("下班打卡时间");
        }else if("LEAVE" == checkOnType){
            $("#checkOnDate").html("请假日期");
            $("#time").html("请假小时");
            $("#title").html("请假类别");
        }else if("ABSENTEE" == checkOnType){
            $("#checkOnDate").html("旷工日期");
            $("#time").html("旷工小时");
            $("#title").html("");
        }else if("OUTWORK" == checkOnType){
            $("#checkOnDate").html("外勤日期");
            $("#time").html("外勤小时");
            $("#title").html("");
        }else if("WEEKEND_OVERTIME" == checkOnType){
            $("#checkOnDate").html("周末日加班日期");
            $("#time").html("周末日加班小时");
            $("#title").html("");
        }else if("EIGHT_HOUR_OVERTIME" == checkOnType){
            $("#checkOnDate").html("正常日加班日期");
            $("#time").html("正常日加班小时");
            $("#title").html("");
        }else if("HOLIDAY_OVERTIME" == checkOnType){
            $("#checkOnDate").html("节假日加班日期");
            $("#time").html("节假日加班小时");
            $("#title").html("");
        }
    }else{
    }
}

function showDetail() {
    var dataHtml = [];
    var url = basePath + "ea/checkOn/sajax_ea_ajaxCheckOnDetails.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "signInCheckOn.staffId" : staffId,
            "detailType" : checkOnType
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var detailList = null;
            if(null != member){
                detailList = member.detailList;
            }
            if (detailList != null && detailList.length > 0) {
                for (var i = 0; i < detailList.length; i++) {
                    var checkOnDetail = detailList[i];
                    dataHtml.push('<li class="checkOnLi" onclick="" id="'+ checkOnDetail.staffId + '">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 8rem;">');
                    dataHtml.push(checkOnDetail.checkOnDate);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 5.7rem;">');
                    if("LATE" == checkOnType || "LEAVE_EARLY" == checkOnType){
                        dataHtml.push(checkOnDetail.minutes);
                    }else{
                        dataHtml.push(checkOnDetail.hours);
                    }
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 7rem;">');
                    if("LATE" == checkOnType){
                        dataHtml.push(checkOnDetail.signInTime);
                    }else if("LEAVE_EARLY" == checkOnType){
                        dataHtml.push(checkOnDetail.signOutTime);
                    }else if("LEAVE" == checkOnType){
                        dataHtml.push(leaveNameEnToch(checkOnDetail.checkOnType));
                    }
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".checkOn ul").append(dataHtml.join(""));
                $(".checkOn ul").css("background", "#fff");
            } else {
                $(".checkOn ul").empty();
                $(".checkOn ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".checkOn ul").empty();
            $(".checkOn ul").css("background", "#fff");
            alert("考勤明细查询失败");
        }
    });
}

function leaveNameEnToch(checkOnType){
    if ("LEAVE_ABSENCE" == checkOnType){
        return "事假";
    }else if("LEAVE_SICK" == checkOnType){
        return "病假";
    }else if("LEAVE_MARRIAGE" == checkOnType){
        return "婚假";
    }else if("LEAVE_ANNUAL" == checkOnType){
        return "年假";
    }else if("LEAVE_MATERNITY" == checkOnType){
        return "产假";
    }else if("LEAVE_FUNERAL" == checkOnType){
        return "丧假";
    }else if("LEAVE_WORKINJURY" == checkOnType){
        return "工伤假";
    }else if("LEAVE_ROTATIONREST" == checkOnType){
        return "轮岗休息";
    }else {
        return "事假";
    }
}

