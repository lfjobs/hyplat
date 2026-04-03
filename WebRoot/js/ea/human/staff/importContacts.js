let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let selectedData = [];
let selectedItems = "";
$(function () {
    initCss();
    bindEvents();
    getEntryStaffData();
});

/**
 * 初始化样式
 */
function initCss() {
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height())
}

/**
 * 点击事件
 */
function bindEvents() {
    // 修改
    $(".edit").click(function () {
        if (selectedId == "") {
            layer.msg("请选择将要修改的数据");
            return false;
        }
        // 获取当前选中的数据项
        const selectedItem = $(".data-ul.active li");
        // 假设：第2个 li 是姓名，第3个是电话
        const currentName = selectedItem.eq(1).find('.scrollable').text().trim();
        const currentPhone = selectedItem.eq(2).find('.scrollable').text().trim();
        // // 设置弹窗初始值
        $("#editName").val(currentName);
        $("#editPhone").val(currentPhone);
        // 显示弹窗
        $("#btnModal").show();
        // 关闭弹窗
        $("#modal-btn-cancel1").click(function () {
            $("#btnModal").hide();
        });
        // 确认按钮点击事件
        $("#modal-btn-confirm1").off("click").on("click", function () {
            const newName = $("#editName").val().trim();
            const newPhone = $("#editPhone").val().trim();

            if (!newName || !newPhone) {
                layer.msg("请填写完整信息");
                return;
            }
            // 发送 AJAX 请求
            $.ajax({
                url: basePath + "ea/importContacts/sajax_ea_updateImportContacts.jspa",
                type: "POST",
                data: {
                    id: selectedId,
                    name: newName,
                    phone: newPhone
                },
                dataType: "json",
                success: function (data) {
                    layer.msg("修改成功");
                    $("#btnModal").hide(); // 关闭弹窗
                    pageNumber = 1;         // 重置分页
                    getEntryStaffData();    // 刷新数据
                }, error: function (data) {
                    layer.msg("修改失败");
                }
            });
        });
    });

    // 导入
    $(".importData").click(function () {
        document.location.href = basePath + "page/pc/5L5C/marketing/visitorImportContacts.jsp";
    });
    // 分配
    // $(".allocation").click(function () {
    //     selectedItems = document.querySelectorAll('.selected img');
    //     ///page/pc/5L5C/marketing/companys.jsp?type=bh
    //     if (selectedItems == "") {
    //         layer.msg("请选择要分配的数据");
    //         return false;
    //     }
    //     // 假设：第2个 li 是姓名，第3个是电话
    //     for (let i = 0; i < selectedItems.length; i++) {
    //         if (selectedItems[i].src.includes('iconCheckAll.gif')) {
    //             const ul = selectedItems[i].closest('ul');
    //             const phone = ul.querySelector('.push-li3').getAttribute('phone');
    //             const name = ul.querySelector('.push-li1').getAttribute('name');
    //             const sccid = ul.querySelector('.push-li4').getAttribute('sccId');
    //             const staffId = ul.id;
    //             const ulData = {
    //                 name: name,
    //                 phone:phone,
    //                 staffId:staffId,
    //                 sccid:sccid
    //             };
    //             selectedData.push(ulData);
    //         }
    //     }
    //
    //     localStorage.setItem('fp', JSON.stringify(selectedData));
    //     document.location.href = basePath + "page/pc/5L5C/marketing/companys.jsp?type=fp";
    // });
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
                + "ea/importContacts/sajax_ea_deleteImportContacts.jspa?id=" + selectedId;
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
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        $(".data-ul").removeClass("active");
        $(this).addClass("active");
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
    const url = basePath + "ea/importContacts/sajax_ea_selectImportContactsList.jspa?" + param.join("");
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
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    //序号
                    htmlstr.push("<li><div class='scrollable'>" + (pageSize * (pageNumber - 1) + i + 1) + "</div></li>");
                    //姓名
                    htmlstr.push("<li name='" + (list[i][1] == null ? " " : list[i][1]) + "' class='push-li1'><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                    //电话
                    htmlstr.push("<li phone='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li3'><div class='scrollable'>" + (list[i][2] == null ? " " : list[i][2]) + "</div></li>");
                    htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                    htmlstr.push("<li style='display: none' sccId='" + (list[i][3] == null ? " " : list[i][3]) + "' class='push-li4'><div class='scrollable'>" + (list[i][3] == null ? " " : list[i][3]) + "</div></li>");
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
$(document).on("click", ".allocation", function (event) {
    var selectedItem = document.querySelectorAll('.selected img');
    ///page/pc/5L5C/marketing/companys.jsp?type=bh
    // 假设：第2个 li 是姓名，第3个是电话
    for (let i = 0; i < selectedItem.length; i++) {
        if (selectedItem[i].src.includes('iconCheckAll.gif')) {
            const ul = selectedItem[i].closest('ul');
            const phone = ul.querySelector('.push-li3').getAttribute('phone');
            const name = ul.querySelector('.push-li1').getAttribute('name');
            const sccid = ul.querySelector('.push-li4').getAttribute('sccId');
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
    document.location.href = basePath + "page/pc/5L5C/marketing/companys.jsp?type=fp";
});
