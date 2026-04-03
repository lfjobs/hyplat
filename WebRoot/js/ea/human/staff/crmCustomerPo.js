let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let selectedItems = "";
let selectedData = [];
let systemData = "import";
$(function () {
    initCss();
    bindEvents();
    getEntryStaffData();
});

/**
 * 初始化样式
 */
function initCss() {
    $(".content").height($(window).height() - $("header").height() - $(".inputBut").height() - 35);
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".div-sec-data").height($(".content").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height());

    // $(".up").height(($(".content").height() - $(".content").height()) + 900);
}

/**
 * 点击事件
 */
function bindEvents() {
    $(".imported").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
        systemData = "import";
    })
    $(".noImported").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/dataImport.jsp?type=wzcfsimport";
    })
    $(".systemData").click(function () {
        systemData = "system";
        $(".systemData").css({
            "color": "#177512de"
        });
        $(".imported").css({
            "background-color": "rgb(255,255,255)",
            "color": "black",
            "border-bottom": "none"
        });
        getEntryStaffData();
    })
    // 添加
    $(".add").click(function () {
        document.location.href = basePath + "ea/crmCustomerPO/ea_addCustomer.jspa";
    })
    // 修改
    // $(".edit").click(function () {
    //     if (selectedId == "") {
    //         layer.msg("请选择将要修改的数据");
    //         return false;
    //     }
    //     document.location.href = basePath + "ea/crmCustomerPO/ea_updCustomer.jspa?id=" + selectedId;
    // });
    // 查询
    $(".query").click(function () {
        if (selectedId == "") {
            layer.msg("请选择将要查看的数据");
            return false;
        }
        document.location.href = basePath + "ea/crmCustomerPO/ea_queryCustomer.jspa?id=" + selectedId;
    });
    // 导入
    // $(".importData").click(function () {
    //
    //     // var url = "ea/crmCustomerPO/ea_importData.jspa";
    //     // window.location.href = basePath + url;
    // });
    // 拨打任务
    $(".outboundCallTask").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/importPhone.jsp?type=drfp";
    });
    // 删除
    $(".del").click(function () {
        if (selectedId == "") {
            layer.msg("请选择将要删除的数据");
            return false;
        }
        layer.confirm('确定删除', {
            title: '温馨提示',
            skin: 'delete-class',
            btn: ['取消', '确定']
        }, function () {
            layer.close(layer.index);
        }, function () {
            var url = basePath
                + "ea/crmCustomerPO/sajax_ea_deleteCrmCustomerPO.jspa?id=" + selectedId;
            $.ajax({
                type: "GET",
                url: url,
                async: false,
                dataType: "json",
                success: function (data) {
                    pageNumber = 1, pageSize = 40, pageCount = 0;
                    getEntryStaffData();
                    layer.close(layer.index);
                    selectedId = "";
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            })

        });
    });
    $(document).on("click", ".selected", function (event) {
        // 获取所有带有 'selected' 类的列表项中的图片
        let imgElements = $(this).find("img");

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
    })
    $(document).on("click", ".rowDiv", function (event) {
        // selectedId = event.currentTarget.id;
        // selectCosId = event.currentTarget.attributes["cosid"].value;
        let ulElement = $(this).closest('.data-ul1');
        selectedId = ulElement.attr('id');
        selectCosId = ulElement.attr('cosid');
        document.location.href = basePath + "ea/crmCustomerPO/ea_updCustomer.jspa?id=" + selectedId;
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
    if (systemData === "import") {
        param.push("&&type=" + "import");
    } else {
        param.push("&&type=" + "system");
    }
    const url = basePath + "ea/crmCustomerPO/sajax_ea_crmCustomerPOList1.jspa?" + param.join("");
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
                    // name = list[i][2];
                    // if (name.length > 3){
                    //     name = name.substring(0,3) + "...";
                    // }
                    htmlstr.push("<ul id='" + list[i][2] + "' cosid='" + list[i][2] + "' class='data-ul1 data-ul-" + list[i][2] + "'>");
                    htmlstr.push("<div class='rowDiv' style='display: contents;'>")
                    //序号
                    htmlstr.push("<li><div class='scrollable'>" + (pageSize * (pageNumber - 1) + i + 1) + "</div></li>");
                    //姓名
                    htmlstr.push("<li name='" + (list[i][3] == null ? " " : list[i][3]) + "' class='push-li1'><div class='scrollable'>" + (list[i][3] == null ? " " : list[i][3]) + "</div></li>");
                    //电话
                    htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'><div class='scrollable'>" + (list[i][5] == null ? " " : list[i][5]) + "</div></li>");
                    //身份证
                    htmlstr.push("<li style='display: none'><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                    htmlstr.push("<li style='display: none'><div class='scrollable'>" + (list[i][8] == null ? " " : list[i][8]) + "</div></li>");
                    htmlstr.push("<li importPerson='" + (list[i][9] == null ? " " : list[i][9]) + "' class='push-li4'><div class='scrollable'>" + (list[i][9] == null ? " " : list[i][9]) + "</div></li>");
                    htmlstr.push("<li style='display: none' importPersonSccId='" + (list[i][6] == null ? " " : list[i][6]) + "' class='push-li5'><div class='scrollable'>" + (list[i][6] == null ? " " : list[i][6]) + "</div></li>");
                    htmlstr.push("</div>")
                    htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                    htmlstr.push("</ul>");
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
                $(".data-list").css({"display": "block"});
            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

$(document).on("click", ".allocation", function (event) {
    var selectedItem = document.querySelectorAll('.selected img');
    ///page/pc/5L5C/marketing/companys.jsp?type=bh
    // 假设：第2个 li 是姓名，第3个是电话
    for (let i = 0; i < selectedItem.length; i++) {
        if (selectedItem[i].src.includes('iconCheckAll.gif')) {
            const ul = selectedItem[i].closest('ul');
            const phone = ul.querySelector('.push-li3').getAttribute('phone');
            const name = ul.querySelector('.push-li1').getAttribute('name');
            const sccid = ul.querySelector('.push-li5').getAttribute('importPersonSccId');
            const staffId = ul.id;
            selectedItems = ul.id;
            const ulData = {
                name: name,
                phone: phone,
                staffId: staffId,
                sccId: sccid
            };
            selectedData.push(ulData);
        }
    }
    if (selectedItems == "") {
        layer.msg("请选择要分配的数据");
        return false;
    }
    localStorage.setItem('fp', JSON.stringify(selectedData));
    document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/selectCompany.jsp?type=fp";
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
});
document.addEventListener('DOMContentLoaded', function () {
    // 获取所有的.option元素（在当前页面中存在）
    const options = document.querySelectorAll('.tab-item');

    // 默认选中第一个.option元素
    if (options.length > 0) {
        // options[0].style.backgroundColor = '#4a8e4a';
        options[0].style.color = '#16baaa';
        options[0].style.borderBottom = '2px solid #16baaa';
        options[0].style.fontweight = 'bold';
    }

    options.forEach(option => {
        option.addEventListener('click', function () {
            // 清除所有.option元素的背景色
            options.forEach(opt => {
                opt.style.backgroundColor = '';
            });

            // this.style.backgroundColor = '#4a8e4a';
            this.style.color = '#16baaa';
            options[0].style.borderBottom = '2px solid #16baaa';
            options[0].style.fontweight = 'bold';
        });
    });
});
//发送短信
$(document).on("click", ".sendMessage", function (event) {
    var selectedItem = document.querySelectorAll('.selected img');
    ///page/pc/5L5C/marketing/companys.jsp?type=bh
    // 假设：第2个 li 是姓名，第3个是电话
    for (let i = 0; i < selectedItem.length; i++) {
        if (selectedItem[i].src.includes('iconCheckAll.gif')) {
            const ul = selectedItem[i].closest('ul');
            const phone = ul.querySelector('.push-li3').getAttribute('phone');
            const name = ul.querySelector('.push-li1').getAttribute('name');
            const sccid = ul.querySelector('.push-li5').getAttribute('importPersonSccId');
            const staffId = ul.id;
            selectedItems = ul.id;
            const ulData = {
                name: name,
                phone: phone,
                staffId: staffId
                // sccId: sccid
            };
            selectedData.push(ulData);
        }
    }
    if (selectedItems == "") {
        layer.msg("请选择要发送短信的数据");
        return false;
    }
    localStorage.setItem('drdxfs', JSON.stringify(selectedData));
    document.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesSendNew.jsp?type=drdxfs";
});