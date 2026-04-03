var timer;
var pageNumber = 0;
var pageCount = 0;
var overtimeApplyTime = '1M';
var status = '-1';

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

    $("#overtimeApplyTime").change(function () {
        pageNumber = 0;
        pageCount = 0;
        overtimeApplyTime = $(this).val();
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });

    $("#status").change(function () {
        pageNumber = 0;
        pageCount = 0;
        status = $(this).val();
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });


});

function getHeight() {
    $("#overtimeApplyTime").val(overtimeApplyTime);
    $("#status").val(status);
    timer = setTimeout("getHeight()", 200);
    if ($(".list ul li:last-child").offset().top + $(".list ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    overtimeApplyTime = '1M';
    status = '-1';
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/overtimeReview/sajax_ea_overtimeApplyList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":10,
            "overtimeApply.status":$("#status").val(),
            "overtimeApplyTime":$("#overtimeApplyTime").val()
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }

            if (pageForm != null && pageForm.recordCount > 0) {
                var overtimeApplyList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < overtimeApplyList.length; i++) {
                    var overtimeApply = overtimeApplyList[i];
                    dataHtml.push('<li class="overtimeApplyLi" onclick="overtimeApplyDetail(this)" id="'+ overtimeApply[0] +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 21%;">');
                    dataHtml.push(overtimeApply[4]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 63%;">');
                    dataHtml.push(overtimeApply[10]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 20%;">');
                    if(overtimeApply[2]=="01"){
                        dataHtml.push("未审核");
                    }else if(overtimeApply[2]=="02"){
                        dataHtml.push("已审核");
                    }else if(overtimeApply[2]=="03"){
                        dataHtml.push("驳回");
                    }else{
                        dataHtml.push("未审核");
                    }
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".list ul").append(dataHtml.join(""));
                $(".list ul").css("background", "#fff");
                getHeight();
            } else {
                $(".list ul").empty();
                $(".list ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".list ul").empty();
            $(".list ul").css("background", "#fff");
            alert("加班申请查询失败");
        }
    });
}

function overtimeApplyDetail(obj) {
    var url = basePath + "ea/overtimeReview/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}



