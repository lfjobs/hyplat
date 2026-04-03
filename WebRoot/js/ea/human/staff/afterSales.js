let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let selectedItems = "";
let selectedData = [], startTime = "", endTime = "";
const today = new Date();
const todayStr = formatDate(today);
startTime = todayStr;
endTime = todayStr;
$(function () {
    initCss();
    bindEvents();
    getEntryStaffData();
    let innerWidth = window.innerWidth;
});

/**
 * 初始化样式
 */
function initCss() {
    $(".content").height($(window).height() - $("header").height() - $(".inputBut").height() - 35);
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".div-sec-data").height($(".content").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() - 110);
    $(".container").height($(window).height() - $("header").height() - $(".inputBut").height() + 20);
    $(".up").height($(window).height() - $("header").height() - $(".inputBut").height() - 65);

}

/**
 * 点击事件
 */
function bindEvents() {
    const tabs = document.querySelectorAll('.tab-item1');
    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            tabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            const selectedValue = this.textContent.trim();

            switch (selectedValue) {
                case "自定义": {
                    $('.searTime').show();
                    break;
                }
                case "今日": {
                    $('.searTime').hide();
                    const today = new Date();
                    startTime = endTime = formatDate(today);
                    break;
                }
                case "昨日": {
                    $('.searTime').hide();
                    const yesterday = new Date();
                    yesterday.setDate(yesterday.getDate() - 1);
                    startTime = endTime = formatDate(yesterday);
                    break;
                }
                case "本周": {
                    $('.searTime').hide();
                    const today = new Date();
                    const dayOfWeek = today.getDay();
                    const startOfWeek = new Date(today);
                    const endOfWeek = new Date(today);
                    startOfWeek.setDate(today.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek - 1));
                    endOfWeek.setDate(today.getDate() + (dayOfWeek === 0 ? 0 : 7 - dayOfWeek));
                    startTime = formatDate(startOfWeek);
                    endTime = formatDate(endOfWeek);
                    break;
                }
                case "上周": {
                    $('.searTime').hide();
                    const today = new Date();
                    const dayOfWeek = today.getDay();
                    const startOfLastWeek = new Date(today);
                    const endOfLastWeek = new Date(today);
                    startOfLastWeek.setDate(today.getDate() - dayOfWeek - 6);
                    endOfLastWeek.setDate(today.getDate() - dayOfWeek);
                    startTime = formatDate(startOfLastWeek);
                    endTime = formatDate(endOfLastWeek);
                    break;
                }
                case "本月": {
                    $('.searTime').hide();
                    const today = new Date();
                    [startTime, endTime] = getMonthRange(today.getFullYear(), today.getMonth() + 1);
                    break;
                }
                case "上月": {
                    $('.searTime').hide();
                    const today = new Date();
                    const year = today.getMonth() === 0 ? today.getFullYear() - 1 : today.getFullYear();
                    const month = today.getMonth() === 0 ? 12 : today.getMonth();
                    [startTime, endTime] = getMonthRange(year, month);
                    break;
                }
                case "本季": {
                    $('.searTime').hide();
                    const today = new Date();
                    const quarter = Math.floor(today.getMonth() / 3) + 1;
                    [startTime, endTime] = getQuarterRange(today.getFullYear(), quarter);
                    break;
                }
                case "上季": {
                    $('.searTime').hide();
                    const today = new Date();
                    let quarter = Math.floor(today.getMonth() / 3);
                    let year = today.getFullYear();
                    if (quarter === 0) {
                        quarter = 4;
                        year -= 1;
                    }
                    [startTime, endTime] = getQuarterRange(year, quarter);
                    break;
                }
                case "本年": {
                    $('.searTime').hide();
                    const year = new Date().getFullYear();
                    startTime = `${year}-01-01`;
                    endTime = `${year}-12-31`;
                    break;
                }
                case "上年": {
                    $('.searTime').hide();
                    const year = new Date().getFullYear() - 1;
                    startTime = `${year}-01-01`;
                    endTime = `${year}-12-31`;
                    break;
                }
            }
            getEntryStaffData();
        });
    });
    $(".order").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/afterSales.jsp";
    })
    $(".opinion").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/customerFeedback.jsp";
    })
    $(".isProcess").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/process.jsp?type=isProcess";
    })
    $(".noProcess").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/process.jsp?type=noProcess";
    })
    $(document).on("click", ".rowDiv", function (event) {
        // selectedId = event.currentTarget.id;
        // selectCosId = event.currentTarget.attributes["cosid"].value;
        let ulElement = $(this).closest('.data-ul');
        selectedId = ulElement.attr('id');
        selectCosId = ulElement.attr('cosid');
        document.location.href = basePath + "ea/afterSales/ea_updAfterSales.jspa?goodsbillsId=" + selectedId;
    })
    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                getEntryStaffData();
            }

        }
    })
}

function getEntryStaffData() {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&startTime=" + startTime);
    param.push("&&endTime=" + endTime);
    const url = basePath + "ea/afterSales/sajax_ea_getCashierBillsList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".div-data").html("暂无数据");
                $(".div-data").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                // let name= "";
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<div class='rowDiv' style='display: contents;'>")
                    //序号
                    htmlstr.push("<li><div class='scrollable'>" + (pageSize * (pageNumber - 1) + i + 1) + "</div></li>");
                    //编号
                    htmlstr.push("<li name='" + (list[i][1] == null ? " " : list[i][1]) + "' class='push-li1'><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                    //名称
                    htmlstr.push("<li phone='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li3'><div class='scrollable'>" + (list[i][2] == null ? " " : list[i][2]) + "</div></li>");
                    if (window.innerWidth > 420) { // 替换 yourDeviceWidthThreshold 为具体数值
                        htmlstr.push("<li class='additional-info'><div class='scrollable'>更多&nbsp;></div></li>");
                    }
                    //品牌
                    htmlstr.push("<li style='display: none' phone='" + (list[i][3] == null ? " " : list[i][3]) + "' class='push-li3'><div class='scrollable'>" + (list[i][3] == null ? " " : list[i][3]) + "</div></li>");
                    //有效期
                    htmlstr.push("<li  style='display: none' phone='" + (list[i][4] == null ? " " : list[i][4]) + "' class='push-li3'><div class='scrollable'>" + (list[i][4] == null ? " " : list[i][4]) + "</div></li>");
                    //收费时间
                    htmlstr.push("<li  style='display: none' phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'><div class='scrollable'>" + (list[i][5] == null ? " " : list[i][5]) + "</div></li>");
                    htmlstr.push("</div>")
                    htmlstr.push("</ul>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".div-data").append(htmlstr.join(""));
                } else {
                    $(".div-data").html(htmlstr.join(""));
                }
                $(".div-data").css({"display": "block"});
            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function getMonthRange(year, month) {
    const startDate = new Date(year, month - 1, 1);
    const endDate = new Date(year, month, 0);
    return [formatDate(startDate), formatDate(endDate)];
}

function getQuarterRange(year, quarter) {
    const startMonth = (quarter - 1) * 3 + 1;
    const endMonth = startMonth + 2;
    const startDate = new Date(year, startMonth - 1, 1);
    const endDate = new Date(year, endMonth, 0);
    return [formatDate(startDate), formatDate(endDate)];
}

function searchByDateRange() {
    startTime = document.getElementById('startDate').value;
    endTime = document.getElementById('endDate').value;
    getEntryStaffData();
}