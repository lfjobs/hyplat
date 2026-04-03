let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let gdId = "";
let contactData = [];
$(function () {
    bindEvents();
    initCss();
    // getEntryStaffData();
});

function initCss() {
    $(".content").height($(".body").height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".input-box1").height() - $(".clearfix").height() - $(".clearfix").height() - 100);
}

/**
 * 点击事件
 */
function bindEvents() {
    //跳到公司
    $("#qd").click(function () {
        $(".data-list").html("");
        document.location.href = basePath + "ea/cRMShortMessagingTemplatePO/ea_companysList.jspa?type=bh";
        // window.location.href = basePath + "page/pc/5L5C/marketing/companys.jsp?type=bh";
    })
    //短信
    $(".clearfix.dx").click(function () {
        document.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesSendNew.jsp";
    })
    // 拨通记录、未通电话、意向客户
    $(".clearfix.dialingRecord, .clearfix.noPhoneCall, .clearfix.intendedCustomers").click(function () {
        const type = $(this).attr('class').split(' ').find(className => ['dialingRecord', 'noPhoneCall', 'intendedCustomers'].includes(className));
        const typeMap = {
            dialingRecord: "拨通记录",
            noPhoneCall: "未通电话",
            intendedCustomers: "意向客户"
        };
        const url = basePath + "page/WFJClient/pc/5l5C/office/config/intendedCustomers.jsp?type=" + typeMap[type];
        window.location.href = url;
    });
    // 导入
    $(".importData").click(function () {
        var url = "ea/crmCustomerPO/ea_importData.jspa";
        window.location.href = basePath + url;
    });
    $(document).on("click", ".data-div", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        $(".data-div").removeClass("active");
        $(this).addClass("active");
    })
    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                // getEntryStaffData();
            }

        }
    })
}

function getEntryStaffData() {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getTemplate1.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                // let name= "";
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<div id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-div data-div-" + list[i][0] + "'>");
                    htmlstr.push("<div class='push-li1'>" + (list[i][3] == null ? " " : list[i][3]) + "</div>");
                    htmlstr.push("<div class='push-li2'>" + (list[i][4] == null ? " " : list[i][4]) + "</div>");
                    htmlstr.push("<div class='push-li3'>" + (list[i][2] == null ? " " : list[i][2]) + "</div>");
                    // htmlstr.push("<div class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</div>");
                    htmlstr.push("<div class='push-li3'>" + "发送约需" + (list[i][15] == null ? " " : list[i][15]) + "积分/人/条" + "</div>");

                    htmlstr.push("</div>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }

            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

const searchIn = document.getElementById('searchIn');
// 添加失焦事件监听器
searchIn.addEventListener('blur', function () {
    const query = searchIn.value.trim();
    if (query) {
        // performSearch(query);
    } else {
        // getEntryStaffData();
    }
});

// 模拟搜索函数
function performSearch(query) {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&templateHeadline=" + query);
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_selectTemplate1.jspa?" + param.join(""), // 替换为实际的后端URL
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                // let name= "";
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<div id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-div data-div-" + list[i][0] + "'>");
                    htmlstr.push("<div class='push-li1'>" + (list[i][3] == null ? " " : list[i][3]) + "</div>");
                    htmlstr.push("<div class='push-li2'>" + (list[i][4] == null ? " " : list[i][4]) + "</div>");
                    htmlstr.push("<div class='push-li3'>" + (list[i][2] == null ? " " : list[i][2]) + "</div>");
                    htmlstr.push("<div class='push-li3'>" + "发送约需" + (list[i][15] == null ? " " : list[i][15]) + "积分/人/条" + "</div>");
                    htmlstr.push("</div>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }

            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    // 获取所有的<div class="item">元素
    const items = document.querySelectorAll('.item');
    const paragraphs = document.querySelectorAll('.item p');

    // 默认选中第二个<div class="item">元素及其对应的<p>元素
    if (items[0]) {
        items[0].querySelector('p').style.borderBottom = '2px solid #f74c32';
    }

    items.forEach(item => {
        item.addEventListener('click', function (event) {
            // 移除所有<p>元素的红色边框
            paragraphs.forEach(p => {
                p.style.border = '';
            });
            // 找到当前<div class="item">元素内的<p>元素
            const currentPElement = event.target.closest('.item').querySelector('p');
            // 给当前点击的<p>元素添加红色边框
            currentPElement.style.borderBottom = '2px solid #f74c32';
        });
    });
});
$(document).on("click", ".item.item2", function (event) {
    gdId = event.currentTarget.id;
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;
    if (isAndroid) {
        Android.loadContacts();
    }
})

/**
 * 接收安卓端获取通讯录返回值
 * @param data 数据
 */
function personalAddressBook1(data) {
    pageDisplay1(data);
}

/**
 * 接收安卓端获取录音返回值
 * @param data 数据
 */
function soundRecord(data) {
}

/**
 * 接收安卓端获取录音转文字返回值
 * @param tiaoma 条码
 */
function recordingToText(data) {
}

function pageDisplay1(data1) {
    let htmlstr = [];
    let counter = 1;
    let loadContact = data1;
    loadContact.forEach((data) => {
        htmlstr.push("<div class='contact-item'>");
        // 左侧圆形头像
        htmlstr.push("<div class='avatar-container'>");
        htmlstr.push("<div class='avatar' title='" + data.name + "'>");
        htmlstr.push((data.name || "").charAt(0));
        htmlstr.push("</div>");
        htmlstr.push("</div>");

        // 中间和右侧部分容器
        htmlstr.push("<div class='content-container'>");

        // 中间部分（姓名和电话，在同一行）
        htmlstr.push("<div class='info-container'>");
        htmlstr.push("<div class='name-number'>");
        htmlstr.push("<li class='name'>" + data.name + "</li>");
        htmlstr.push("<li class='number'>" + data.number + "</li>");
        htmlstr.push("</div>");
        htmlstr.push("</div>");

        // 右侧部分（固定标签，在同一行）
        htmlstr.push("<div class='tags-container'>");
        htmlstr.push("<ul class='tags-list'>");
        htmlstr.push("<li>名片</li>");
        htmlstr.push("<li>电话录音</li>");
        htmlstr.push("<li>录音转文字</li>");
        htmlstr.push("<li>文字记录</li>");
        htmlstr.push("</ul>");
        htmlstr.push("</div>");

        htmlstr.push("</div>"); // 结束 content-container
        htmlstr.push("</div>"); // 结束 contact-item
        counter++; // 递增计数器
    });
    const moreData = document.getElementById('more-data');
    if (moreData != null) {
        moreData.remove()
    }
    $(".data-list").html(htmlstr.join(""));

}

//个人电话导入电话
$(document).on("click", ".clearfix.import", function (event) {
    if (gdId != "") {
        const contactItems = document.querySelectorAll('.content-container .name-number');
        contactItems.forEach(item => {
            const name = item.querySelector('.name')?.textContent.trim();
            const number = item.querySelector('.number')?.textContent.trim();
            const data = {
                name: name,
                number: number,
            };
            contactData.push(data);
        });
        localStorage.setItem("contactData", JSON.stringify(contactData));
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/importPhone.jsp?type=gd";
        contactData = [];
    }
    gdId = "";
})