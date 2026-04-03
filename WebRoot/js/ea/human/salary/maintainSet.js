$(function () {
    initCss();
    bindEvents();

});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - 20);
}

/**
 * 点击事件
 */
function bindEvents() {
    // 级别设置
    $("#levelSet").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/levelSet.jsp?" + param.join("") ;
    });
    // 基本保障设置
    $("#guaranteeSet").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSet.jsp?" + param.join("") ;
    });
    $("#welfareSet").click(function () {
        const data = param.slice();
        data.push("&&type=welfare");
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSalarySet.jsp?" + data.join("") ;
    });
}
