let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;

let item = localStorage.getItem("selectedIdCompany");
let localTemplateText = localStorage.getItem("localTemplateText");
let firstNumber = localStorage.getItem("firstNumber");
//filterValue 筛选条件值
let selectdeData = [], staffIds = [], names = [], filterValue = "";
let selectedData = [];

$(function () {
    initCss();
    bindEvents();
    but();
    getEntryStaffData();
});

function initCss() {
    $(".content").height($(".body").height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".input-box1").height() - $(".input-box1").height() - $(".but").height() - 25);
}

/**
 * 点击事件
 */
function bindEvents() {
    $(document).on("click", ".data-ul", function (event) {
        let selectedId = event.currentTarget.id;
        let selectCosId = event.currentTarget.attributes["cosid"].value;

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
    });
    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                if (filterValue == "") {
                    getEntryStaffData();
                } else {
                    getStaffListByRelation();
                }

            }

        }
    })
}

function getEntryStaffData() {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&companyId=" + item);
    const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getStaffList.jspa?" + param.join("");
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
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<li>" + (pageSize * (pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li name='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li1'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
                    htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                    htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                    htmlstr.push("<li style='display: none' companyId='" + (list[i][7] == null ? " " : list[i][7]) + "' class='push-li4'>" + (list[i][7] == null ? " " : list[i][7]) + "</li>");
                    htmlstr.push("</ul>")
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
                $(".data-list").css("display", "block");
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
        performSearch(query);
    } else {
        getEntryStaffData();
    }
});

// 模拟搜索函数
function performSearch(query) {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&companyId=" + item);
    param.push("&&staffName=" + query);
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getStaffListByName.jspa?" + param.join(""), // 替换为实际的后端URL
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
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<li>" + (pageSize * (pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li name='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li1'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
                    htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                    htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                    htmlstr.push("<li style='display: none' companyId='" + (list[i][7] == null ? " " : list[i][7]) + "' class='push-li4'>" + (list[i][7] == null ? " " : list[i][7]) + "</li>");
                    htmlstr.push("</ul>")
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
                $(".data-list").css("display", "block");
            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

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
});
// 当用户点击模态框外部时，关闭模态框
window.onclick = function (event) {
    if (event.target == document.getElementById("myModal")) {
        $("#myModal").css("display", "none");
    }
}
// 获取关闭按钮
const closeBtn = document.querySelector(".btn-cancel");
// 当用户点击关闭按钮时，关闭模态框
closeBtn.onclick = function () {
    $("#myModal").css("display", "none");
}
// 当用户点击模态框外部时，关闭模态框
window.onclick = function (event) {
    if (event.target == document.getElementById("myModal")) {
        $("#myModal").css("display", "none");
    }
}
//筛选弹框
$(document).on("click", ".screen", function (event) {
    const param = new Array();
    param.push("companyId=" + item);
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getCategoryList.jspa?" + param.join(""), // 替换为实际的后端URL
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = JSON.parse(data);
            if (codeList == null) {
                $(".screenData").html("暂无数据");
                $(".screenData").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const htmlstr = new Array();
                for (let i = 0; i < codeList.length; i++) {
                    const item = codeList[i];
                    htmlstr.push("<ul class='categoryList'>");
                    htmlstr.push("<li class='category-li'>" + codeList[i] + "</li>");
                    htmlstr.push("</ul>")
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                $(".screenData").html(htmlstr);
            }
            $("#myModal").css("display", "block");
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
})
$(document).on("click", ".category-li", function (event) {
    $(".data-div").removeClass("active");
    $(this).addClass("active");
    $("#myModal").css("display", "none");
    //筛选条件
    let text = $(this).text();
})
//确定发送按钮
$(document).on("click", ".btn-submit", function (event) {
    var selectedItems = document.querySelectorAll('.selected img');
    let localTemplateText1 = localTemplateText;
    for (let i = 0; i < selectedItems.length; i++) {
        if (selectedItems[i].src.includes('iconCheckAll.gif')) {
            const ul = selectedItems[i].closest('ul');
            const phone = ul.querySelector('.push-li3').getAttribute('phone');
            const name = ul.querySelector('.push-li1').getAttribute('name');
            const companyId = ul.querySelector('.push-li4').getAttribute('companyId');
            const staffId = ul.id;
            const ulData = {
                phone: phone,
                name: name,
                staffId: staffId,
                companyId: companyId
            };
            selectedData.push(ulData);
        }
    }
    var typeValue = getParameterByName("type");
    if (typeValue === 'dx') {
        $.ajax({
            url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_send.jspa?", // 替换为实际的后端URL
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "selectedData": JSON.stringify(selectedData),
                "localTemplateText": localTemplateText1,
                "firstNumber": firstNumber,
            },
            success: function cbf(data) {
                layer.msg(data);
                selectedData = [];
            },
            error: function cbf(data) {
                layer.msg("发送失败");
            }
        });
    } else if (typeValue === 'fp') {
        $.ajax({
            url: basePath + "ea/importContacts/sajax_ea_allocationImportContacts.jspa?", // 替换为实际的后端URL
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "fp":localStorage.getItem("fp"),
                "selectedData": JSON.stringify(selectedData),
            },
            success: function cbf(data) {
                localStorage.removeItem("fp");
                selectedData = [];
                layer.msg("分配成功");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/importContacts.jsp";
                }, 3000); // 3000 毫秒 = 3 秒

            },
            error: function cbf(data) {
                localStorage.removeItem("fp");
                selectedData = [];
                layer.msg("分配失败,请重新分配");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/importContacts.jsp";
                }, 3000); // 3000 毫秒 = 3 秒

            }
        })
    } else {
        localStorage.setItem('myKey', JSON.stringify(selectedData));
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/importPhone.jsp?type=qd";
        selectedData = [];
    }
})
$(document).on("click", ".category-li", function (event) {
    filterValue = $(this).text().trim();
    pageNumber = 1;
    getStaffListByRelation();
})

function getStaffListByRelation() {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&companyId=" + item);
    param.push("&&relation=" + filterValue);
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getStaffListByRelation.jspa?" + param.join(""), // 替换为实际的后端URL
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
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<li>" + (pageSize * (pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li name='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li1'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
                    htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                    htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                    htmlstr.push("<li style='display: none' companyId='" + (list[i][7] == null ? " " : list[i][7]) + "' class='push-li4'>" + (list[i][7] == null ? " " : list[i][7]) + "</li>");
                    htmlstr.push("</ul>")
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
                $(".data-list").css({"display": "block"})

            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function but() {
    var typeValue = getParameterByName("type");
    if (typeValue === "dx") {
        const button = document.querySelector('.btn-submit');
        button.innerHTML = '确认';
    } else if (typeValue === "bh") {
        const button = document.querySelector('.btn-submit');
        button.innerHTML = '导入';
    }
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
}

