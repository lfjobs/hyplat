var timer;
var pageNumber = 0;
var pageCount = 0;
var outworkApplyTime = '1M';

$(document).ready(function () {
    loaded();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/outworkApply/ea_add.jspa";
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
    var dataHtml = [];
    var url = basePath + "ea/outworkApply/sajax_ea_outworkApplyList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":10,
            "outworkApplyTime":$("#outworkApplyTime").val()
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }

            if (pageForm != null && pageForm.recordCount > 0) {
                var outworkApplyList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < outworkApplyList.length; i++) {
                    var outworkApply = outworkApplyList[i];
                    dataHtml.push('<li class="outworkApplyLi" onclick="outworkApplyDetail(this)" id="'+ outworkApply[0] +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 50%;">');
                    dataHtml.push(outworkApply[9]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 20%;">');
                    if(outworkApply[2]=="01"){
                        dataHtml.push("未审核");
                    }else if(outworkApply[2]=="02"){
                        dataHtml.push("已审核");
                    }else if(outworkApply[2]=="03"){
                        dataHtml.push("驳回");
                    }else{
                        dataHtml.push("未审核");
                    }
                    dataHtml.push('</span>');

                    if(outworkApply[2] == "01"){
                        // dataHtml.push('<span id="'+leaveApply[0]+'" onclick="deleteLeaveApply(this,event)" class="txtSpan" style="width:50px;' +
                        //     'height:30px;line-height:30px;float:right;margin-top:3px;text-align:center;background: green;color: #fff;">');
                        dataHtml.push('<span id="'+outworkApply[0]+'" onclick="deleteOutworkApply(this,event)" class="txtSpan" ' +
                            'style="width: 15%;text-align:center;background: green;color: #fff;position: absolute;right: 0px;">');
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
            alert("外勤申请查询失败");
        }
    });
}

function outworkApplyDetail(obj) {
    var url = basePath + "ea/outworkApply/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}
function working(obj) {
       if (typeof  Android !=='undefined'){
           Android.CallWorking();
       }
}

function workingCallBack(obj) {
    if (typeof obj !=='undefined'){
        var url = basePath + "ea/outworkApply/sajax_ea_working.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "outworkApply.key": obj.getAttribute("id")
            },
            success: function(data) {
                alert("打卡成功！");
                var url = basePath + "ea/outworkApply/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("打卡失败！");
            }
        });
    }

}

function deleteOutworkApply(obj,event){
    event.stopPropagation();
    if(confirm("确认删除外勤申请？")){
        var url = basePath + "ea/outworkApply/sajax_ea_delete.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "outworkApply.key": obj.getAttribute("id")
            },
            success: function(data) {
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




