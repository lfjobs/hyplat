let returnData = ["list"];
let form = null;
let levelListId = "";
let pageNumber = 1, pageSize = 20,pageCount = 0;
let levelData = {};
$(function () {
    initCss();
    bindEvents();
    getSalaryLevelList();

});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".div-level-data").height($(".sec-list").height() - $(".level-title").height());
}

/**
 * 点击事件
 */
function bindEvents() {
    // 新建
    $(".add").click(function () {
        const param = [];
        param.push("gradeName=" + levelData["gradeName"]);
        param.push("&&gradeNum=" + levelData["gradeNum"]);
        param.push("&&gradeNumStr=" + levelData["gradeNumStr"]);
        param.push("&&date=" + levelData["date"]);
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/maintainSet.jsp?" + param.join("") ;

    });
    // 查询
    $(".query").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/guaranteeSalary.jsp?" ;

    });


    // 监听列表滚动事件
    const divElementList = document.querySelector('.level-list');
    divElementList.addEventListener('scroll', function() {
        if (divElementList.scrollTop + divElementList.clientHeight >= divElementList.scrollHeight || 1==1) {
            if (pageNumber < pageCount){
                $(".level-list").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getSalaryLevelList();
            }

        }
    })
    // 监听级别滚动事件
    const divElement = document.querySelector('.level-data');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount){
                $(".level-data").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getSalaryLevelByNum(levelListId);
            }

        }
    })

}

/**
 * 获取级别列表
 */
function getSalaryLevelList(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/salarylevel/sajax_ea_getSalaryLevelList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".level-list").html("暂无数据");
                $(".level-list").css({"display":"flex","align-items":"center","justify-content":"center"});
                levelData["gradeName"] = "无";
                levelData["gradeNum"] = "0";
                levelData["gradeNumStr"] = "无";
                levelData["date"] = timestampToDate(new Date());
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i][0] + "' onclick=getSalaryLevelData('" + list[i][0] + "') >");
                    htmlstr.push("<li>" + timestampToDate(new Date(list[i][1].time)) + "</li>");
                    htmlstr.push("<li>" + list[i][3] + "</li>");
                    htmlstr.push("<li>" + list[i][4] + "</li>");
                    htmlstr.push("<li>会议纪要></li>");
                    htmlstr.push("</ul>")
                }
                levelData["gradeName"] = list[0][3];
                levelData["gradeNum"] = list[0][2];
                levelData["gradeNumStr"] = list[0][5];
                levelData["date"] = timestampToDate(new Date(list[0][1].time));

                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
                if (pageNumber > 1){
                    $(".level-list").append(htmlstr.join(""));
                } else {
                    $(".level-list").html(htmlstr.join(""));
                }
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
function getSalaryLevelData(id){
    levelListId = id
    pageNumber = 1, pageSize = 20,pageCount = 0;
    $(".sec-level-list").hide();
    $(".sec-level-data").show();
    $(".level-data").html("");
    initCss();
    returnData.push("detail");
    getSalaryLevelByNum();
}

function getSalaryLevelByNum(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&levelListId=" + levelListId);
    const url = basePath + "ea/salarylevel/sajax_ea_getSalaryLevelByNum.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".level-data").html("暂无数据");
                $(".level-data").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i][0] + "'>");
                    htmlstr.push("<li>" + timestampToDate(new Date(list[i][1].time)) + "</li>");
                    htmlstr.push("<li>" + list[i][2] + "级</li>");
                    htmlstr.push("<li>" + list[i][3] + "</li>");
                    htmlstr.push("<li>" + list[i][4] + "</li>");
                    htmlstr.push("<li>会议纪要></li>");
                    htmlstr.push("</ul>")
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
                if (pageNumber > 1){
                    $(".level-data").append(htmlstr.join(""));
                } else {
                    $(".level-data").html(htmlstr.join(""));
                }

            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

/**
 * 时间转化
 * @param timestamp
 * @returns {string}
 */
function timestampToDate(timestamp) {
    var date = new Date(timestamp); // 将时间戳转换为 Date 对象
    var year = date.getFullYear(); // 获取年份
    var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 获取月份并补零
    var day = date.getDate().toString().padStart(2, '0'); // 获取日期并补零
    return `${year}-${month}-${day}`; // 拼接字符串返回
}

/**
 * 返回
 * @returns {boolean}
 */
function returnPage() {
    returnData.pop();
    let length = returnData.length;
    if (length > 0){
        const data = returnData[returnData.length -1];
        $(".div-sec-data").hide();
        $(".sec-level-set").show();
        if (data == "list"){
            $(".sec-level-list").show();
        } else {
            $(".sec-level-data").show();
        }
    } else {
        window.history.go(-1);
        return false;
    }

}
