var timer;
var pageNumber = 0;
var pageCount = 0;
var showType = "company";

$(document).ready(function() {
    loaded();
    $("#showCompany").click(function () {
        pageNumber = 0;
        pageCount = 0;
        showType = "company";
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });
    $("#showPerson").click(function () {
        pageNumber = 0;
        pageCount = 0;
        showType = "person";
        $(".list ul").empty();
        ajax();
        $(this).blur();
        $(".list ul").focus();
    });
    $("#showOther").click(function () {

    });
});

/**
 * 定时刷新判断页面距底高度
 */
function getHeight() {
    timer = setTimeout("getHeight()", 1000);
    if ($(".list ul li:last-child").offset().top + $(".list ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            ajax();
        }
    }
}

/**
 * 异步获取加载数据
 */
function loaded(){
    showType = "company";
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/scBudget/sajax_ea_costBudgetBillDealingsList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: true,
        dataType: "json",
        data: {
            "pageForm.pageNumber":pageNumber,
            "pageForm.pageSize":20,
            "showType":showType
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