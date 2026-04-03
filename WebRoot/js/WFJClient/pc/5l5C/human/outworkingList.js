var timer;
var pageNumber = 0;
var pageCount = 0;
var outworkApplyTime = '1M';

$(document).ready(function () {
    loaded();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
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

    $("#outworkApplyTime").change(function () {
        pageNumber = 0;
        pageCount = 0;
        outworkApplyTime = $(this).val();
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });


});

function getHeight() {
    $("#outworkApplyTime").val(outworkApplyTime);
    timer = setTimeout("getHeight()", 200);
    if ($(".list ul li:last-child").offset().top + $(".list ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    outworkApplyTime = '1M';
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;

    const dataHtml = [];
    const url = basePath + "ea/outworking/sajax_ea_queryWorkingList.jspa?";
    const statusMap = {
        "01": "未审核",
        "02": "已审核",
        "03": "驳回"
    };

    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        async: true, // 避免使用同步 ajax，提升性能
        data: {
            outworkApplyTime: $("#outworkApplyTime").val()
        },
        success: function (data) {
            const list = JSON.parse(data || "[]");

            if (list.length > 0) {
                list.forEach((item) => {
                    const key = item["key"];
                    const applyDate = item["applyDate"] || "";
                    const statusText = statusMap[item["status"]] || "未审核";

                    dataHtml.push(`<li class="outworkApplyLi" onclick="outworkApplyDetail(this)" id="${key}">`);
                    dataHtml.push(`<div class="txt"><p class="txtP">`);
                    dataHtml.push(`<span class="txtSpan" style="width: 50%;">${applyDate}</span>`);
                    dataHtml.push(`<span class="txtSpan" style="width: 20%;">${statusText}</span>`);

                    try {
                        const start = new Date(item["travelStartDate"]);
                        const end = new Date(item["travelEndDate"]);

                        if (isTodayBetween(start, end)) {
                            dataHtml.push(`
                                <span 
                                    id="${key}working"
                                    onclick="working(event,this)"
                                    class="txtSpan"
                                    style="width: 15%; text-align:center; background: orangered; color: #fff; position: absolute; right: 70px;">
                                    打卡
                                </span>
                            `);
                        }
                    } catch (e) {
                        console.warn("无效的日期", e);
                    }

                    dataHtml.push(`</p></div></li>`);
                });

                $(".list ul").append(dataHtml.join(""));
                $(".list ul").css("background", "#fff");
                getHeight();
            } else {
                $(".list ul").empty().css("background", "#fff");
            }
        },
        error: function () {
            $(".list ul").empty().css("background", "#fff");
            alert("外勤申请查询失败");
        }
    });
}


function outworkApplyDetail(obj) {
    var url = basePath + "ea/outworking/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}

function working(event,el) {
    event.stopPropagation();
    if (typeof Android !== 'undefined') {
        result = Android.CallWorking(el.id);
    }

    // result["local"] = '111,222';
    // result["img"] = 'https://www.impf2010.com/upload_files/android/webimg/ffa2252f-c339-4229-92d9-672e6e009a75.png';
    // result["tpye"] = '111,222';
    // console.log(result);
    //
    // var url = basePath + "ea/outworking/ea_working.jspa?local=" + result["local"] + "&img=" + result["img"] + "&tpye=" + result["type"];
    // window.location.href = url;
}

function workingCallBack(obj) {
    const json = JSON.parse(obj);
    if (typeof obj !== 'undefined') {
        console.log(json.local);
        var url = basePath + "ea/outworking/sajax_ea_working.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "local": json.local,
                "img": json.img,
                "type": json.type,
                "id":json.id
            },
            success: function (data) {
                alert("打卡成功！");
                // var url = basePath + "ea/outworkApply/ea_find.jspa";
                // window.location.href = url;
            },
            error: function () {
                alert("打卡失败！");
            }
        });
    }

}

function deleteOutworkApply(obj, event) {
    event.stopPropagation();
    if (confirm("确认删除外勤申请？")) {
        var url = basePath + "ea/outworkApply/sajax_ea_delete.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "outworkApply.key": obj.getAttribute("id")
            },
            success: function (data) {
                alert("删除外勤申请成功！");
                var url = basePath + "ea/outworkApply/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除外勤申请失败！");
            }
        });
    }
}

function dateToString(date) {
    if (null == date) {
        return "";
    }
    return (date.year + 1900) + "-" + (date.month + 1) + "-" + date.date + " " + date.hours + ":" + date.minutes + ":" + date.seconds;
}

function timeToString(date) {
    if (null == date) {
        return "";
    }
    return date.hours + ":" + date.minutes + ":" + date.seconds;
}

function isTodayBetween(startDateStr, endDateStr) {
    const today = new Date();
    const todayStr = today.getFullYear() + '-' +
        ('0' + (today.getMonth() + 1)).slice(-2) + '-' +
        ('0' + today.getDate()).slice(-2);
    const todayDate = new Date(todayStr); // 转为00:00:00的Date对象

    const startDate = new Date(startDateStr); // 取前面 "YYYY-MM-DD"
    const endDate = new Date(endDateStr);

    return todayDate >= startDate && todayDate <= endDate;
}


