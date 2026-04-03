let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    initCss();
    bindEvents();
    getSalaryRangeData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".data-list").height($(".div-data").height() - $(".data-title").height())
}


function bindEvents() {

    // 监听列表滚动事件
    const divElementList = document.querySelector('.data-list');
    divElementList.addEventListener('scroll', function() {
        if (divElementList.scrollTop + divElementList.clientHeight >= divElementList.scrollHeight || 1==1) {
            if (pageNumber < pageCount){
                $(".data-list").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getSalaryRangeData();
            }

        }
    })
    $(document).on("click", ".data-list ul", function (event) {
        $(".data-list ul").removeClass("active");
        $(this).addClass("active");
    })
}
/**
 * 获取级别列表
 */
function getSalaryRangeData(){
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
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i]["salaryId"] + "'  >");
                    htmlstr.push("<li >" + (list[i]["salaryLevelSerial"] == null ? " " : (list[i]["salaryLevelSerial"] + "级")) + "</li>");
                    htmlstr.push("<li>" +  (parseInt(list[i]["salaryLevelSerial"]) < 4 ? list[i]["guaranteeSalary"] : (list[i]["guaranteeSalary"] + "元"))  + "</li>");
                    htmlstr.push("<li>" +  (parseInt(list[i]["salaryLevelSerial"]) < 4 ? list[i]["welfareSalary"] : (list[i]["welfareSalary"] + "元"))  + "</li>");
                    htmlstr.push("<li>" +  (parseInt(list[i]["salaryLevelSerial"]) < 4 ? list[i]["overtimeSalary"] : (list[i]["overtimeSalary"] + "元"))  + "</li>");
                    htmlstr.push("<li>" +  (parseInt(list[i]["salaryLevelSerial"]) < 4 ? list[i]["summarySalary"] : (list[i]["summarySalary"] + "元"))  + "</li>");
                    htmlstr.push("</ul>")
                }

                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
                if (pageNumber > 1){
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}