var timer;
var pageNumber = 0;
var pageCount = 0;
var dutyApplyTime = '1M';

$(document).ready(function () {
    loaded();
    $(".top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/dutyApply/ea_add.jspa";
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

    $("#dutyApplyTime").change(function () {
        pageNumber = 0;
        pageCount = 0;
        dutyApplyTime = $(this).val();
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });
});

function getHeight() {
    $("#dutyApplyTime").val(dutyApplyTime);
    timer = setTimeout("getHeight()", 200);
    if ($(".list ul li:last-child").offset().top + $(".list ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

function loaded() {
    dutyApplyTime = '1M';
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/dutyApply/sajax_ea_dutyApplyList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":10,
            "dutyApplyTime":$("#dutyApplyTime").val()
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                var dutyApplyList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < dutyApplyList.length; i++) {
                    var dutyApply = dutyApplyList[i];
                    dataHtml.push('<li class="overtimeApplyLi" onclick="dutyApplyDetail(this)" id="'+ dutyApply[0] +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 28%;">');
                    dataHtml.push(dutyApply[6]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 40%;">');
                    dataHtml.push(dutyApply[2]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 10%;">');
                    if(dutyApply[4]=="SIGN_IN_TYPE_EARLY"){
                        dataHtml.push("早班");
                    }else if(dutyApply[4]=="SIGN_IN_TYPE_NIGHT"){
                        dataHtml.push("晚班");
                    }else if(dutyApply[4]=="SIGN_IN_TYPE_LATE_NIGHT"){
                        dataHtml.push("夜班");
                    }else{
                        dataHtml.push("");
                    }
                    dataHtml.push('</span>');
                    dataHtml.push('<span id="'+dutyApply[0]+'" onclick="deleteDutyApply(this,event)" class="txtSpan" ' +
                        'style="width:70px;text-align:center;background: green;color: #fff;position: absolute;right: 0px;">');
                    dataHtml.push('删除');
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
            alert("值班申请查询失败");
        }
    });
}

function dutyApplyDetail(obj) {
    var url = basePath + "ea/dutyApply/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}

function deleteDutyApply(obj,event){
    event.stopPropagation();
    if(confirm("确认删除值班申请？")){
        var url = basePath + "ea/dutyApply/sajax_ea_delete.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "dutyScheduling.dutySchedulingId": obj.getAttribute("id")
            },
            success: function(data) {
                alert("删除值班申请成功！");
                var url = basePath + "ea/dutyApply/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除值班申请失败！");
            }
        });
    }
}


