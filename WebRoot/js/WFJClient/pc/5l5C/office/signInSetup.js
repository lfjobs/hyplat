var timer;
var pageNumber = 0;
var pageCount = 0;
var deleteIdList = [];

$(document).ready(function () {
    loaded();
    $(".signInSetup-top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/signInSetup/ea_add.jspa";
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


});

function deleteElement(){
    //绑定删除事件
    var signInSetupLi = document.getElementsByClassName('signInSetupLi');
    for(var i=0;i < signInSetupLi.length;i++){
        longPressListener(signInSetupLi[i], function (e){
            deleteSignInSetup(e);
        });
    }
}

function getHeight() {
    timer = setTimeout("getHeight()", 200);
    if ($(".signInSetup ul li:last-child").offset().top + $(".signInSetup ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
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
    var url = basePath + "ea/signInSetup/sajax_ea_ajaxSignInSetupList.jspa?";
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
                var signInSetupList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < signInSetupList.length; i++) {
                    var signInSetup = signInSetupList[i];
                    dataHtml.push('<li class="signInSetupLi" onclick="signInSetupDetail(this)" id="'+ signInSetup[0] +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 13%;">');
                    dataHtml.push(signInSetup[6]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 37%;">');
                    dataHtml.push(signInSetup[3]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 37%;">');
                    dataHtml.push(signInSetup[4]);
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 14%;">');
                    dataHtml.push(signInSetup[5]);
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".signInSetup ul").append(dataHtml.join(""));
                $(".signInSetup ul").css("background", "#fff");
                getHeight();
                deleteElement();
            } else {
                $(".signInSetup ul").empty();
                $(".signInSetup ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".signInSetup ul").empty();
            $(".signInSetup ul").css("background", "#fff");
            alert("签到设置查询失败");
        }
    });
}

function signInSetupDetail(obj) {
    var url = basePath + "ea/signInSetup/ea_update.jspa?id=" + obj.getAttribute("id");
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

function deleteSignInSetup(e){
    if(confirm("确认删除签到设置？")){
        var url = basePath + "ea/signInSetup/sajax_ea_deleteSignInSetup.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "signInSetup.signInSetupId": e.getAttribute("id")
            },
            success: function(data) {
                alert("删除签到设置成功！");
                var url = basePath + "ea/signInSetup/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除签到设置失败！");
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


