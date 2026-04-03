var timer;
var pageNumber = 0;
var pageCount = 0;
var deleteIdList = [];

$(document).ready(function () {
    loaded();
    $(".socialSecuritySetup-top").attr("style", "top:" + $(window).height() * 0.08 + "px;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:0px;");
    $(".content").attr("style", "overflow: auto;height:" + $(window).height() * 0.7 + "px;position:relative;");

    $(".top-right .button-a").on("click",function (){
        var url = basePath + "ea/socialSecuritySetup/ea_add.jspa";
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
    var socialSecuritySetupLi = document.getElementsByClassName('socialSecuritySetupLi');
    for(var i=0;i < socialSecuritySetupLi.length;i++){
        longPressListener(socialSecuritySetupLi[i], function (e){
            deleteSocialSecuritySetup(e);
        });
    }
}

function getHeight() {
    timer = setTimeout("getHeight()", 200);
    if ($(".socialSecuritySetup ul li:last-child").offset().top + $(".socialSecuritySetup ul li:last-child").height() - $("header").height() * 3 < $(window).height()) {
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
    var url = basePath + "ea/socialSecuritySetup/sajax_ea_ajaxSocialSecuritySetupList.jspa?";
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
                var socialSecuritySetupList = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                for (var i = 0; i < socialSecuritySetupList.length; i++) {
                    var socialSecuritySetup = socialSecuritySetupList[i];
                    dataHtml.push('<li class="socialSecuritySetupLi" onclick="socialSecuritySetupDetail(this)" id="'+ socialSecuritySetup.socialSecuritySetupId +'">');
                    dataHtml.push('<div class="txt">');
                    dataHtml.push('<p class="txtP">');
                    dataHtml.push('<span class="txtSpan" style="width: 29%;">');
                    dataHtml.push(null != socialSecuritySetup.deductionType ? ("UNIFIED" == socialSecuritySetup.deductionType ? "统一扣减" : "按工资计算") : "");
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 27%;">');
                    dataHtml.push(parseFloat(socialSecuritySetup.socialSecurityLowLevel).toFixed(2));
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 26%;">');
                    dataHtml.push(parseFloat(socialSecuritySetup.socialSecurityLevel).toFixed(2));
                    dataHtml.push('</span>');
                    dataHtml.push('<span class="txtSpan" style="width: 18%;">');
                    dataHtml.push(parseFloat(socialSecuritySetup.elderlyCareDiscountMoney).toFixed(2));
                    dataHtml.push('</span>');
                    dataHtml.push('</p>');
                    dataHtml.push('</div>');
                    dataHtml.push('</li>');
                }
                $(".socialSecuritySetup ul").append(dataHtml.join(""));
                $(".socialSecuritySetup ul").css("background", "#fff");
                getHeight();
                deleteElement();
            } else {
                $(".socialSecuritySetup ul").empty();
                $(".socialSecuritySetup ul").css("background", "#fff");
            }
        },
        error: function () {
            $(".socialSecuritySetup ul").empty();
            $(".socialSecuritySetup ul").css("background", "#fff");
            alert("社保设置查询失败");
        }
    });
}

function socialSecuritySetupDetail(obj) {
    var url = basePath + "ea/socialSecuritySetup/ea_update.jspa?id=" + obj.getAttribute("id");
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

function deleteSocialSecuritySetup(e){
    if(confirm("确认删除社保设置？")){
        var url = basePath + "ea/socialSecuritySetup/sajax_ea_deleteSocialSecuritySetup.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "socialSecuritySetup.socialSecuritySetupId": e.getAttribute("id")
            },
            success: function(data) {
                alert("删除社保设置成功！");
                var url = basePath + "ea/socialSecuritySetup/ea_find.jspa";
                window.location.href = url;
            },
            error: function () {
                alert("删除社保设置失败！");
            }
        });
    }
}


