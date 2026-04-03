let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
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
    // 添加
    $(".add").click(function () {
        document.location.href = basePath + "ea/crmCustomerPO/ea_addCustomer.jspa";
    })
    // 修改
    $(".edit").click(function () {
        if (selectedId == "") {
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "ea/crmCustomerPO/ea_updCustomer.jspa?id=" + selectedId;
    });
    // 查询
    $(".query").click(function () {
        if (selectedId == "") {
            layer.msg("请选择将要查看的数据");
            return false;
        }
        document.location.href = basePath + "ea/crmCustomerPO/ea_queryCustomer1.jspa?id=" + selectedId;
    });
    // 导入
    $(".importData").click(function () {
        var url = "ea/crmCustomerPO/ea_importData.jspa";
        window.location.href = basePath + url;
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
    $(document).on("click", ".data-ul1", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        document.location.href = basePath + "ea/crmCustomerPO/ea_updCustomer.jspa?id=" + selectedId;
        // $(".data-ul").removeClass("active");
        // $(this).addClass("active");
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
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/crmCustomerPO/sajax_ea_crmCustomerPOList2.jspa?type=" + getParameterByName("type") + "&" + param.join("");
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
                    //序号
                    htmlstr.push("<li><div class='scrollable'>" + (pageSize * (pageNumber - 1) + i + 1) + "</div></li>");
                    //姓名
                    htmlstr.push("<li><div class='scrollable'>" + (list[i][3] == null ? " " : list[i][3]) + "</div></li>");
                    //电话
                    htmlstr.push("<li><div class='scrollable'>" + (list[i][5] == null ? " " : list[i][5]) + "</div></li>");
                    //身份证
                    htmlstr.push("<li><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                    //地址
                    htmlstr.push("<li><div class='scrollable'>" + (list[i][7] == null ? " " : list[i][7]) + "</div></li>");
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

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
}