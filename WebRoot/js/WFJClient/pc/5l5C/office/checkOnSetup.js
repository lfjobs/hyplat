var timer;
var pageNumber = 0;
var pageCount = 0;
var deleteIdList = [];

$(document).ready(function () {
    loaded();
    $(".checkOnSetup-top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/checkOnSetup/ea_add.jspa";
        window.location.href = url;
    });

    //暂时不查询
    // $(".top-left .button-a").on("click",function (){
    //     $(".checkOnSetup ul").empty();
    //     $(".checkOnSetup ul").css("background", "#fff");
    //     pageNumber=0;
    //     ajax();
    // });

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

function deleteElement(){
    //绑定删除事件
    var checkOnSetupLi = document.getElementsByClassName('checkOnSetupLi');
    for(var i=0;i < checkOnSetupLi.length;i++){
        longPressListener(checkOnSetupLi[i], function (e){
            deleteCheckOnSetup(e);
        });
    }
}

function getHeight() {
    timer = setTimeout("getHeight()", 200);
    if ($(".checkOnSetup ul li:last-child").offset().top + $(".checkOnSetup ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
        if (pageNumber < pageCount) {
            loaded();
        }
    }
}

function loaded() {
    ajax();
}

function ajax() {
    clearTimeout(timer);
    pageNumber++;
    var dataHtml = [];
    var url = basePath + "ea/checkOnSetup/sajax_ea_ajaxCheckOnSetupList.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize" : 10
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = null;
            if(null != member){
                pageForm = member.pageForm;
            }

            if (pageForm != null && pageForm.recordCount > 0) {
                var checkOnSetupList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < checkOnSetupList.length; i++) {
                    var checkOnSetup = checkOnSetupList[i];
                    dataHtml.push('<li class="checkOnSetupLi" onclick="checkOnSetupDetail(this)" id="'+ checkOnSetup.checkOnSetupId +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 31%;">');
                    dataHtml.push(checkOnSetup.checkOnTypeName);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 29%;">');
                    dataHtml.push(checkOnSetup.rankNo);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 21%;">');
                    dataHtml.push(null != checkOnSetup.amountType ? ("REWARD" == checkOnSetup.amountType ? "奖励" : "折扣") : "");
                    dataHtml.push('</span>');
                    // dataHtml.push('<span class="txtSpan" style="width: 21%;">');
                    // dataHtml.push(checkOnSetup.checkOnAmount);
                    // dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 8%;">');
                    dataHtml.push(checkOnSetup.discountRate);
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".checkOnSetup ul").append(dataHtml.join(""));
                $(".checkOnSetup ul").css("background", "#fff");
                getHeight();
                deleteElement();
            } else {
                $(".checkOnSetup ul").empty();
                $(".checkOnSetup ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".checkOnSetup ul").empty();
            $(".checkOnSetup ul").css("background", "#fff");
            alert("考勤设置查询失败");
        }
    });
}

function checkOnSetupDetail(obj) {
    var url = basePath + "ea/checkOnSetup/ea_update.jspa?id=" + obj.getAttribute("id");
    window.location.href = url;
}

// 设置长按触发的时间阈值（单位毫秒）
const LONG_PRESS_DURATION = 1000;

// 创建长按事件监听器
function longPressListener(element, callback) {
    var deleteId = $(element).attr('id');
    if ($.inArray(deleteId, deleteIdList) !== -1) {
        return;
    }

    let longPressTimer = null;
    //手机适用
    element.addEventListener('touchstart', function(e) {
        // 当按下鼠标按钮时开始计时
        longPressTimer = setTimeout(() => {
            callback(element);
        }, LONG_PRESS_DURATION);
    });

    element.addEventListener('touchend', function() {
        // 当释放鼠标按钮时取消计时
        clearTimeout(longPressTimer);
    });

    element.addEventListener('touchmove', function() {
        clearTimeout(longPressTimer);
    });

    //PC适用
    element.addEventListener('mousedown', function(e) {
        // 当按下鼠标按钮时开始计时
        longPressTimer = setTimeout(() => {
            callback(element);
        }, LONG_PRESS_DURATION);
    });

    element.addEventListener('mouseup', function() {
        // 当释放鼠标按钮时取消计时
        clearTimeout(longPressTimer);
    });

    //当鼠标离开元素范围时也取消计时
    element.addEventListener('mouseleave', function() {
        clearTimeout(longPressTimer);
    });

    deleteIdList.push(deleteId);
}

function deleteCheckOnSetup(e){
    if(confirm("确认删除考勤设置？")){
        var url = basePath + "ea/checkOnSetup/sajax_ea_deleteCheckOnSetup.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "checkOnSetup.checkOnSetupId": e.getAttribute("id")
            },
            success: function(data) {
                alert("删除考勤设置成功！");
                var url = basePath + "ea/checkOnSetup/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除考勤设置失败！");
            }
        });
    }
}


