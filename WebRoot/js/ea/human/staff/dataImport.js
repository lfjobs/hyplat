let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let selectedItems = "", staffPageNumber = 1, staffDataList = [];
let data = [];
const item = localStorage.getItem("dataImport");
const itemOld = localStorage.getItem("oldReferenceList");
let sum = 0;
$(function () {
    initCss();
    // oldReferenceList();
    bindEvents();
    getEntryStaffData();
});

/**
 * 初始化样式
 */
function initCss() {
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() - $(".button").height() - $(".category").height())
}

/**
 * 点击事件
 */
function bindEvents() {
    $(".imported").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
    })
    $(".noImported").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/dataImport.jsp?type=wzcfsimport";
    })
    $(document).on("click", ".data-ul1", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        // $(".data-ul").removeClass("active");
        // $(this).addClass("active");
        // 获取所有带有 'selected' 类的列表项中的图片
        let imgElements = $(this).find(".selected img");

        imgElements.each(function () {
            let imgSrc = $(this).attr('src'); // 获取当前图片的 src 属性
            // 判断当前图片是否为 'iconUncheckAll.gif'
            if (imgSrc && imgSrc.includes('iconUncheckAll.gif')) {
                // 更换为另一张图片
                $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconCheckAll.gif');
            } else {
                // 如果不是，则还原为原始图片
                $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif');
            }
        });

        // 更新选中数量文本
        updateSelectedCount();
    })
    // 监听滚动事件
    const divElement = document.querySelector('.div-sec-data');
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
    const codeList1 = item ? JSON.parse(item) : [];
    // 直接使用 itemOld，不需要解析
    const codeList2 = itemOld || [];
    // 简化处理
    let oldPhone = new Set();
    if (itemOld) {
        const parsed = JSON.parse(itemOld);
        if (Array.isArray(parsed) && parsed.length > 0) {
            oldPhone = new Set(parsed);
        }

    }
    const htmlstr = [];
    const codeList = codeList1;
    if (codeList == null || codeList.length <= 1) {
        $(".data-list").html("暂无数据");
        $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
    } else {

        const list = codeList;
        let counter = 1;
        $('.sum1 p').text(`共 ${list.length - 1} 条数据`);
        $('.sum p').text(`勾选 ${list.length - 1} 条数据`);
        for (let i = 1; i < list.length; i++) {
            const cardId = list[i][1];
            const name = list[i][0] == null ? " " : list[i][0];
            const phone = list[i][2] == null ? " " : list[i][2];
            const address = list[i][3] == null ? " " : list[i][3];
            const extend = list[i][4] == null ? " " : list[i][4];
            const social = list[i][5] == null ? " " : list[i][5];
            const unitCompany = list[i][6] == null ? " " : list[i][6];

            htmlstr.push("<ul id='" + cardId + "' cosid='" + cardId + "' class='data-ul1 data-ul-" + cardId + "'>");
            htmlstr.push("<li ><div class='scrollable'>" + counter + "</div></li>");
            htmlstr.push("<li name='" + name + "' class='push-li11'><div class='scrollable'>" + name + "</div></li>");
            htmlstr.push("<li phone='" + phone + "' class='push-li8'><div class='scrollable'>" + phone + "</div></li>");
            htmlstr.push("<li cardId='" + cardId + "' class='push-li10'><div class='scrollable'>" + cardId + "</div></li>");

            htmlstr.push("<li style='display: none' address='" + address + "' class='push-li12'><div class='scrollable'>" + address + "</div></li>");
            htmlstr.push("<li style='display: none' extend='" + extend + "' class='push-li13'><div class='scrollable'>" + extend + "</div></li>");
            htmlstr.push("<li style='display: none' social='" + social + "' class='push-li14'><div class='scrollable'>" + social + "</div></li>");
            htmlstr.push("<li style='display: none' social='" + unitCompany + "' class='push-li15'><div class='scrollable'>" + unitCompany + "</div></li>");

            // 检查当前cardId是否在旧数据中存在
            const imgSrc = oldPhone.has(phone)
                ? basePath + "js/tree/codebase/imgs/iconUncheckAll.gif"
                : basePath + "js/tree/codebase/imgs/iconCheckAll.gif";
            htmlstr.push("<li class='selected'><img src='" + imgSrc + "'></li>");
            htmlstr.push("</ul>");
            counter++;
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
}


$(document).on("click", ".btn-submit", function (event) {
    var selectedItem = document.querySelectorAll('.selected img');
    ///page/pc/5L5C/marketing/companys.jsp?type=bh
    // 假设：第2个 li 是姓名，第3个是电话
    for (let i = 0; i < selectedItem.length; i++) {
        if (selectedItem[i].src.includes('iconCheckAll.gif')) {
            const ul = selectedItem[i].closest('ul');
            const name = ul.querySelector('.push-li11').getAttribute('name');
            const phone = ul.querySelector('.push-li8').getAttribute('phone');
            const cardId = ul.querySelector('.push-li10').getAttribute('cardId');
            const address = ul.querySelector('.push-li12').getAttribute('address');
            const extend = ul.querySelector('.push-li13').getAttribute('extend');
            const social = ul.querySelector('.push-li14').getAttribute('social');
            const unitCompany = ul.querySelector('.push-li15').getAttribute('unitCompany');
            const staffId = ul.id;
            selectedItems = ul.id;
            const ulData = {
                name: name,
                number: cardId,
                contact: phone,
                address: address,
                extend: extend,
                social: social,
                unitCompany: unitCompany,
            };
            data.push(ulData);
        }
    }
    if (data.length === 0) {
        layer.msg("请选择需要导入的数据");
        return;
    }
    $.ajax({
        url: basePath + "ea/crmCustomerPO/sajax_ea_saveImportDataPo.jspa",
        type: "POST",
        data: {"data": JSON.stringify(data)},
        async: false,
        success: function (data) {
            layer.msg("导入成功");
            setTimeout(function () {
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
            }, 3000); // 3000 毫秒 = 3 秒
            localStorage.removeItem("dataImport");
            localStorage.removeItem("oldReferenceList");

        },
        error: function () {
        }
    });
});
$(document).on("click", ".selectedAll", function (event) {
    // 获取所有带有 'selected' 类的列表项中的图片
    var selectedAll = document.querySelectorAll('.selectedAll img');

    var selectedItems = document.querySelectorAll('.selected img');

    selectedAll.forEach(function (item) {
        // 判断当前图片是否为 'iconUncheckAll.gif'
        if (item.src.includes('iconUncheckAll.gif')) {
            selectedItems.forEach(function (item) {
                // 判断当前图片是否为 'iconUncheckAll.gif'
                // 更换为另一张图片
                item.src = basePath + 'js/tree/codebase/imgs/iconCheckAll.gif';
            });
            // 更换为另一张图片
            item.src = basePath + 'js/tree/codebase/imgs/iconCheckAll.gif';
        } else {
            selectedItems.forEach(function (item) {
                // 更换为另一张图片
                item.src = basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif';
            });
            // 如果不是，则还原为原始图片
            item.src = basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif';
        }
    });
    // 更新选中数量文本
    updateSelectedCount();
});
document.addEventListener('DOMContentLoaded', function () {
    // 获取所有的.option元素（在当前页面中存在）
    const options = document.querySelectorAll('.tab-item');

    // 默认选中第一个.option元素
    if (options.length > 0) {
        // options[0].style.backgroundColor = '#4a8e4a';
        options[1].style.color = '#16baaa';
        options[1].style.borderBottom = '2px solid #16baaa';
        options[1].style.fontweight = 'bold';
    }

    options.forEach(option => {
        option.addEventListener('click', function () {
            // 清除所有.option元素的背景色
            options.forEach(opt => {
                opt.style.backgroundColor = '';
            });

            // this.style.backgroundColor = '#4a8e4a';
            this.style.color = '#16baaa';
            options[1].style.borderBottom = '2px solid #16baaa';
            options[1].style.fontweight = 'bold';
        });
    });
});

function oldReferenceList() {
    $.ajax({
        url: basePath + "ea/crmCustomerPO/sajax_ea_contrastImportDataPo1.jspa?pageSize=2000&&pageNumber=" + staffPageNumber,
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = JSON.parse(data);
            var pageCount = codeList.pageCount;
            var dataList = codeList.list;
            staffDataList = [...staffDataList, ...dataList];
            staffPageNumber++;
            if (staffPageNumber < pageCount) {
                oldReferenceList();
            } else {
                console.log(codeList);
                localStorage.setItem("oldReferenceList", JSON.stringify(staffDataList));
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败!");
        }
    });
}

/**
 * 更新选中数据条数显示
 */
function updateSelectedCount() {
    const selectedCount = $('.selected img[src*="iconCheckAll.gif"]').length;
    $('.sum p').text(`勾选 ${selectedCount} 条数据`);
}