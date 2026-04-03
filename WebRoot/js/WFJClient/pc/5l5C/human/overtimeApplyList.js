var timer;
var pageNumber = 0;
var pageCount = 0;
var overtimeApplyTime = '1M';

$(document).ready(function () {
    loaded();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/overtimeApply/ea_add.jspa";
        window.location.href = url;
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

    $("#overtimeApplyTime").change(function () {
        pageNumber = 0;
        pageCount = 0;
        overtimeApplyTime = $(this).val();
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });


});

function getHeight() {
    $("#overtimeApplyTime").val(overtimeApplyTime);
    timer = setTimeout("getHeight()", 200);
    if ($(".list ul li:last-child").offset().top + $(".list ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    overtimeApplyTime = '1M';
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/overtimeApply/sajax_ea_overtimeApplyList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":10,
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
                    dataHtml.push('<span class="txtSpan" style="width: 62%;">');
                    dataHtml.push(overtimeApply[9]);
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
                    if(overtimeApply[2] == "01"){
                        // dataHtml.push('<span id="'+leaveApply[0]+'" onclick="deleteLeaveApply(this,event)" class="txtSpan" style="width:50px;' +
                        //     'height:30px;line-height:30px;float:right;margin-top:3px;text-align:center;background: green;color: #fff;">');
                        dataHtml.push('<span id="'+overtimeApply[0]+'" onclick="deleteOvertimeApply(this,event)" class="txtSpan" ' +
                            'style="width:70px;text-align:center;background: green;color: #fff;position: absolute;right: 0px;">');
                        dataHtml.push('删除');
                        dataHtml.push('</span>');
                    }
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
    var url = basePath + "ea/overtimeApply/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}

function deleteOvertimeApply(obj,event){
    event.stopPropagation();
    if(confirm("确认删除加班申请？")){
        var url = basePath + "ea/overtimeApply/sajax_ea_delete.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "overtimeApply.key": obj.getAttribute("id")
            },
            success: function(data) {
                alert("删除加班申请成功！");
                var url = basePath + "ea/overtimeApply/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除加班申请失败！");
            }
        });
    }
}

function dateToString(date){
    if(null == date){
        return "";
    }
    return (date.year + 1900) + "-" + (date.month + 1) + "-" + date.date + " " + date.hours + ":" + date.minutes + ":" + date.seconds;
}

function timeToString(date){
    if(null == date){
        return "";
    }
    return date.hours + ":" + date.minutes + ":" + date.seconds;
}


