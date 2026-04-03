let pageNumber = 1, pageSize = 40,pageCount = 0;
let element = layui.element;
$(function () {
    initCss();
    bindEvents();
    getLevelSalaryDataList();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height() - 20);
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".sec-data-list").height($(".sec-list").height() - $(".sec-data-title").height());
}

/**
 * 点击事件
 */
function bindEvents() {
    // 监听级别滚动事件
    const divElement = document.querySelector('.sec-data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".sec-data-list").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getLevelSalaryDataList();
            }

        }
    })
    const iphoneElement = document.querySelector('.div-iphone');
    iphoneElement.addEventListener('scroll', function() {
        if (iphoneElement.scrollTop + iphoneElement.clientHeight >= iphoneElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".sec-data-list").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getLevelSalaryDataList();
            }

        }
    })

}

/**
 * 获取级别工资
 */
function getLevelSalaryDataList(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/salarylevel/sajax_ea_getLevelSalaryDataList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const pcList = document.getElementById("pcList");
            var display = window.getComputedStyle(pcList).display;
            if (data == "null" ){
                if(display == "none"){
                    $(".div-iphone").html("请先配置级别级差");
                    $(".div-iphone").css({"display":"flex","align-items":"center","justify-content":"center"});
                } else {
                    $(".sec-data-list").html("请先配置级别级差");
                    $(".sec-data-list").css({"display":"flex","align-items":"center","justify-content":"center"});
                }

            } else {
                const codeList = eval("(" + data + ")");
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const today = new Date();
                const year = today.getFullYear();
                let month = today.getMonth() + 1;  // 月份从0开始，所以要加1
                if (month < 10){
                    month = "0" + month
                }
                const date = year + '-' + month;
                const htmlWidth = $(window).width()
                if (htmlWidth > 450){
                    getPCHtmlStr(date,list);
                } else {
                    getIphoneHtmlStr(date,list);
                }

                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }

            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

/**
 * PC端页面
 * @param date
 * @param list
 * @param htmlstr
 */
function getPCHtmlStr(date,list){
    const htmlstr = new Array();
    const length = list.length;
    for (let i = 0; i < length; i++) {
        htmlstr.push("<ul >");
        htmlstr.push("<li>" + date + "</li>");
        htmlstr.push("<li>" + list[i]["salaryLevelSerial"] + "级</li>");
        htmlstr.push("<li>" + list[i]["salaryLevelNum"] + "</li>");
        htmlstr.push("<li>" + (list[i]["baseSalary"] == undefined ? "" :  list[i]["baseSalary"]) + "</li>");
        htmlstr.push("<li>" + (list[i]["roleSalary"] == undefined ? "" : list[i]["roleSalary"])  + "</li>");
        htmlstr.push("<li>" + (list[i]["dutySalary"] == undefined ? "" :  list[i]["dutySalary"]) + "</li>");
        htmlstr.push("<li>" + (list[i]["competeSalary"] == undefined ? "" : list[i]["competeSalary"])  + "</li>");
        htmlstr.push("<li>" + (list[i]["secrecySalary"] == undefined ? "" :  list[i]["secrecySalary"]) + "</li>");
        htmlstr.push("<li>" + (list[i]["levelSalary"] == undefined ? "" : list[i]["levelSalary"] ) + "</li>");
        htmlstr.push("<li>" + (list[i]["guaranteeSalary"] == undefined ? "" :  list[i]["guaranteeSalary"]) + "</li>");
        htmlstr.push("</ul>")
    }
    if (pageNumber > 1){
        $(".sec-data-list").append(htmlstr.join(""));
    } else {
        $(".sec-data-list").html(htmlstr.join(""));
    }
}
/**
 * 手机端页面
 * @param date
 * @param list
 * @param htmlstr
 */
function getIphoneHtmlStr(date,list) {
    const htmlstr = new Array();
    const length = list.length;
    const filedData = ["baseSalary","roleSalary","dutySalary","competeSalary","secrecySalary","levelSalary","guaranteeSalary"];
    const fieldName = ["基本工资","职能工资","职责工资","竞职金","保密工资","调平工资","合计"];
    const fieldLength = filedData.length;
    for (let i = 0; i < length; i++) {
        const salaryLevelSerial = list[i]["salaryLevelSerial"];
        const salaryLevelNum = list[i]["salaryLevelNum"];
        let h2Html = salaryLevelSerial + "级（" + salaryLevelNum + "）--- " + list[i]["guaranteeSalary"] ;
        if(i > 2){
            h2Html += "元";
        }
        htmlstr.push("<div class=\"layui-colla-item\">")
        htmlstr.push("<h2 class=\"layui-colla-title\">" +h2Html + "</h2>");
        htmlstr.push("<div class=\"layui-colla-content layui-show\">");
        for (let j = 0; j < fieldLength; j++){
            const filed = filedData[j];
            htmlstr.push("<div class=\"layui-form-item\">");
            htmlstr.push("<label class=\"layui-form-label\"  style=\"color:#0d9b21\">" + fieldName[j] + "</label>");
            htmlstr.push("<div class=\"layui-input-block\">");
            htmlstr.push("<label class=\"form-label\" >" + (list[i][filed] == undefined ? "" : list[i][filed]) + "</label>");
            htmlstr.push("</div>");
            htmlstr.push("</div>");
        }
        htmlstr.push("</div>");
        htmlstr.push("</div>");
    }
    if (pageNumber > 1){
        $(".div-iphone").append(htmlstr.join(""));
    } else {
        $(".div-iphone").html(htmlstr.join(""));
    }

    layui.use('element', function(){
        element = layui.element;
        element.render('collapse');
    });

}

