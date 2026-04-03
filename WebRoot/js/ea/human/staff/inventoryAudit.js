// let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
let type = getParameterByName("type");
let cashierBillsIDNew = getParameterByName("cashierBillsID");
// let cashierBillsIDNew ="cashierTally20250228PHICG2QVAK0000000083";
// let status = "08";
let status = "100";
// let billsType = "noReceivebox";
let billsType = "allReceivebox";
let showFlag = false;
let reviewerName;
let reviewTime;
let examineAndVerifyReason = "";
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
    $(".sec-list").height($(".content").height());
    $(".data-list").height($(".sec-list").height() - 50);
    $(".bug-con").height($(".sec-list").height() - 150);
}

/**
 * 点击事件
 */
function bindEvents() {

    $(document).on('click', '.payment', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "payment";
        status = "100";
        getEntryStaffData();
        $(".send").hide();
        $(".send1").css({
            "height": "0px", "line-height": "0px"
        });
        $(".isPayment").show();
        $(".noPayment").show();
        $(".Payment1").css({
            "height": "1.2rem", "line-height": "1.2rem"
        });
    });

    $(document).on('click', '.allReceivebox', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "allReceivebox";
        status = "100";
        getEntryStaffData();
        $(".send").hide();
        $(".send1").css({
            "height": "0px", "line-height": "0px"
        });
        $(".isPayment").hide();
        $(".noPayment").hide();
        $(".Payment1").css({
            "height": "0px", "line-height": "0px"
        });
    });

    $(document).on('click', '.noReceivebox', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "noReceivebox";
        status = "08";
        getEntryStaffData();
        $(".send").hide();
        $(".send1").css({
            "height": "0px", "line-height": "0px"
        });
        $(".isPayment").hide();
        $(".noPayment").hide();
        $(".Payment1").css({
            "height": "0px", "line-height": "0px"
        });
    });
    $(document).on('click', '.isReceivebox', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "isReceivebox";
        status = "07";
        getEntryStaffData();
        $(".send").show();
        $(".send1").css({
            "height": "1.2rem", "line-height": "1.2rem"
        });
        $(".isPayment").hide();
        $(".noPayment").hide();
        $(".Payment1").css({
            "height": "0px", "line-height": "0px"
        });
    });
    $(document).on('click', '.reject', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "reject";
        status = "11";
        getEntryStaffData();
        $(".send").show();
        $(".send1").css({
            "height": "1.2rem", "line-height": "1.2rem"
        });
        $(".isPayment").hide();
        $(".noPayment").hide();
        $(".Payment1").css({
            "height": "0px", "line-height": "0px"
        });
    });
    $(document).on('click', '.isSend', function () {
        $(".tj_con").css({"display": "block"});
        $(".tj_con").html("");
        billsType = "isSend";
        status = "42";
        getEntryStaffDataIsSend();
        $(".send").hide();
        $(".send1").css({
            "height": "0px", "line-height": "0px"
        });
        $(".isPayment").hide();
        $(".noPayment").hide();
        $(".Payment1").css({
            "height": "0px", "line-height": "0px"
        });
    });
    // 添加图片切换事件
    $(document).on('click', '.aside_no', function () {
        // 切换选中状态的图片
        $(this).find('.img_no').hide();
        $(this).find('.img_yes').css("display", "block");
        $(this).removeClass('aside_no').addClass('aside_yes');
    });

    $(document).on('click', '.aside_yes', function () {
        // 切换回未选中状态的图片
        $(this).find('.img_yes').hide();
        $(this).find('.img_no').show();
        $(this).removeClass('aside_yes').addClass('aside_no');
    });

// 添加审批按钮点击事件
    $(document).ready(function () {
        // 审批按钮点击事件
        $(".submitAudit").click(async function () {
            var selectedItems = $(".aside_yes");
            if (selectedItems.length === 0) {
                layer.msg("请先选择要审批的数据");
                return;
            }

            // 获取选中的数据ID
            var selectedIds = [];
            selectedItems.each(function () {
                var id = $(this).attr("checkCasId");
                selectedIds.push(id);
            });

            if (billsType === "出库单" || billsType === "入库单") {
                try {
                    const result = await examineAndVerify(selectedIds);
                    if (result === "success") {
                        // 可以调用后台接口进行审批操作
                        audit(selectedIds);
                    } else {
                        layer.msg("审核失败,存在尚未审核的单据");
                    }
                } catch (error) {
                    layer.msg("审核过程出现错误");
                }
            } else {
                try {
                    const result = await examineAndVerify(selectedIds);
                    if (result === "success") {
                        // 可以调用后台接口进行审批操作
                        executeReject(selectedIds, "审核");
                    } else {
                        layer.msg("审核失败,存在尚未审核的单据");
                    }
                } catch (error) {
                    layer.msg("审核过程出现错误");
                }
            }
        });

        // 驳回按钮点击事件
        $(".stampReject").click(async function () {
            var selectedItems = $(".aside_yes");
            if (selectedItems.length === 0) {
                layer.msg("请先选择要驳回的数据");
                return;
            }
            // 获取选中的数据ID
            var selectedIds = [];
            selectedItems.each(function () {
                var id = $(this).attr("checkCasId");
                selectedIds.push(id);
            });

            try {
                const result = await examineAndVerify(selectedIds);
                if (result === "success") {
                    // 可以调用后台接口进行审批操作
                    executeReject(selectedIds, "驳回");
                } else {
                    layer.msg("驳回失败,存在尚未审核的单据");
                }
            } catch (error) {
                layer.msg("驳回过程出现错误");
            }
        });

    });


    $(document).on("click", ".rowDiv", function (event) {
        // selectedId = event.currentTarget.id;
        // selectCosId = event.currentTarget.attributes["cosid"].value;
        let ulElement = $(this).closest('.data-ul');
        selectedId = ulElement.attr('id');
        selectCosId = ulElement.attr('cosid');
        document.location.href = basePath + "ea/afterSales/ea_updAfterSales.jspa?goodsbillsId=" + selectedId;
    })
    // 监听滚动事件
    const divElement = document.querySelector('.main_hide');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                // pageNumber++;
                getEntryStaffData();
            }

        }
    })


    $(document).on("click", ".send", function (event) {
        localStorage.setItem("companyId", getParameterByName("companyId"));
        var selectedItems = $(".aside_yes");
        if (selectedItems.length === 0) {
            layer.msg("请先选择要传阅的数据");
            return;
        }
        // 获取选中的数据ID
        var selectedIds = [];
        var id;
        selectedItems.each(function () {
            id = $(this).attr("checkCasId");
            selectedIds.push(id);
        });
        // const result = getIsSend(selectedIds);
        // if (result === "success") {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/selectCompanySh.jsp?type=cy&keyId=" + id;
        // } else {
        //     layer.msg("传阅失败,存在已入库或已出库的单据");
        // }


    })


}


function getEntryStaffData() {
    // 修复分页参数逻辑
    pageNumber += 1;
    const url = basePath + "ea/reviewCirculate/sajax_ea_ajaxCostBudgetBillList.jspa";
    $.ajax({
        url: encodeURI(url), type: "POST", async: true, dataType: "json",

        data: {
            "pageForm.pageNumber": pageNumber, "pageForm.pageSize": pageSize,  // 确保这个值是20而不是其他值
            "billsType": billsType, "status": status, "cashierBillsId": cashierBillsIDNew

        }, success: function cbf(data) {
            var member = (new Function("return " + data))();
            if (member.pageForm == null) {
                if (pageNumber === 1) {  // 只有第一页才显示"暂无数据"
                    $(".tj_con").html("暂无数据");
                    $(".tj_con").css({"display": "flex", "align-items": "center", "justify-content": "center"});
                }
            } else {
                var pageForm = member.pageForm;
                $(".ttsw_last").removeClass("ttsw_last");
                if (pageForm != null) {
                    var htmlstr = "";
                    var obj;
                    pageCount = pageForm.pageCount;
                    count = pageForm.recordCount;

                    var num = pageForm.list.length - 1;
                    $(".ttsw_last").removeClass("ttsw_last");
                    for (var i = 0; i < pageForm.list.length; i++) {
                        obj = pageForm.list[i];
                        htmlstr += getHtmlStr(obj, i, num);
                    }

                    // 根据页码决定是替换还是追加内容
                    if (pageNumber === 1) {
                        $(".tj_con").html(htmlstr);
                    } else {
                        $(".tj_con").append(htmlstr);
                    }

                    $(".div-data").css({"display": "block"});
                }
            }
            scrollBool = true;
        }, error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}


function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
}

function getHtmlStr(obj, i, num) {
    var liStr = getLi(i, num);
    var cashierDate = getFormatDate(obj.cashierDate);
    var strutsStr = getStrutsStr(status);

    // 构建基础 HTML，调整审核信息位置使其与行业分类对齐
    var htmlstr = liStr + '<section>' + '<aside class="aside_no" checkCasId="' + obj.cashierBillsID + '">' + '<img class="img_no" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_07.png" style="display:block"/>' + '<img class="img_yes" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_10.png" style="display:none"/>' + '</aside>' + '<h5>单据凭证号：' + obj.journalNum + '<img src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>' + '</section>' + '<div onclick="toDetail(\'' + obj.cashierBillsID + '\', \'' + obj.billsType + '\');" id="detail_' + obj.cashierBillsID + '">' + '<p>公司：' + obj.companyName + '</p>' + '<p>行业分类：' + obj.tradeName + '</p>' + '<p>项目分类：' + obj.xmtypename + '</p>' + '<p>单据类别：' + obj.billsType + '</p>' + '<p>责任人部门：' + obj.departmentName + '</p>' + '<p>责任人：' + obj.staffName + '</p>' + '<p>制单人：' + obj.inputName + '</p>' + '<p>制单日期：' + cashierDate + '</p>' + (obj.ctUserName != null && obj.ctUserName != '' ? '<p>往来个人：' + obj.ctUserName + '</p>' : '') + (obj.startTime != null && obj.startTime != '' ? '<p>开始日期：' + formatDate(obj.startTime) + '</p>' : '') + (obj.endTime != null && obj.endTime != '' ? '<p>结束日期：' + formatDate(obj.endTime) + '</p>' : '') + (obj.priceSub != null && obj.priceSub != '' ? '<p>总金额：' + obj.priceSub + '</p>' : '')
        + '<p class="un">单据状态：<span>' + strutsStr + '</span></p>'
        + '<p class="zf" style="width: 90%;text-align: right;color: #398fe7;' + ((obj.billsType === '支出' && status === '07') ? '' : 'display:none;') + '"><span>支付</span></p>'        // 将审核信息容器放在最后，与其他信息对齐
        + '<div class="audit-info-' + obj.cashierBillsID + '"></div>' + '</div>' + '</li>';

    // 异步获取审核信息
    getCashierBillsExtPromise(obj.cashierBillsID).then(function (data) {
        var auditInfoHtml = '';

        // 如果data是数组，遍历数组中的每个对象
        if (Array.isArray(data)) {
            data.forEach(function (item) {
                var lineHtml = '';
                if (item.reviewerName != null && item.reviewerName != '') {
                    if (item.reviewerName.includes('待审核人')) {
                        lineHtml += '<span style="color: orange;">' + item.reviewerName + '</span>';
                    } else {
                        lineHtml += '审核人：' + item.reviewerName;
                    }
                }
                if (item.reviewTime != null && item.reviewTime != '') {
                    if (lineHtml !== '') {
                        lineHtml += ' | ';  // 在审核人和审核时间之间添加分隔符
                    }
                    lineHtml += '时间：' + item.reviewTime;
                    if (item.reviewStatus != null && item.reviewStatus != '') {
                        if (item.reviewStatus === "07") {
                            lineHtml += ' | 意见：' + '同意';
                        } else {
                            lineHtml += ' | 意见：' + '驳回';
                        }
                    }
                }

                if (lineHtml !== '') {
                    auditInfoHtml += '<p style="width: 100%">' + lineHtml + '</p>';
                }
            });
        } else {
            // 如果data仍然是对象，保持类似的逻辑
            var lineHtml = '';
            if (data.reviewerName != null && data.reviewerName != '') {
                lineHtml += '审核人：' + data.reviewerName;
            }
            if (data.reviewTime != null && data.reviewTime != '') {
                if (lineHtml !== '') {
                    lineHtml += ' | ';
                }
                lineHtml += '审核时间：' + data.reviewTime;
            }

            if (lineHtml !== '') {
                auditInfoHtml += '<p style="width: 100%">' + lineHtml + '</p>';
            }
        }

        $('.audit-info-' + obj.cashierBillsID).html(auditInfoHtml);
    });


    return htmlstr;
}

function getCashierBillsExtPromise(cashierBillsID1) {
    return new Promise(function (resolve, reject) {
        const url = basePath + "ea/reviewCirculate/sajax_ea_getCashierBillsExt.jspa?cashierBillsId1=" + cashierBillsID1;
        $.ajax({
            url: encodeURI(url), type: "get", async: true, dataType: "json", success: function cbf(data) {
                const codeList = JSON.parse(data);
                resolve(codeList);
            }, error: function () {
                reject();
            }
        });
    });
}

function getLi(i, num) {
    if (i == num) {
        return "<li class=\"clearfix1 ttsw_last\"\">";
    } else {
        return "<li class=\"clearfix1\"\">";
    }
}

function getFormatDate(date) {
    var time = new Date(date.time);
    var y = time.getFullYear();//年
    var m = time.getMonth() + 1;//月
    var da = time.getDate();//日
    var h = time.getHours();//时
    var mm = time.getMinutes();//分
    var s = time.getSeconds();//秒
    s = s < 10 ? '0' + s : s;
    return y + "-" + m + "-" + da + " " + h + ":" + mm + ":" + s;//拼接日期
}

function getStrutsStr(status) {
    var strutsStr = "";
    //拼接状态html
    if (status === "07") {
        strutsStr = "已审核";
    } else if (status === "11") {
        strutsStr = "已驳回";
    } else if (status === "08") {
        strutsStr = "未审核";
    } else if (status === "42") {
        strutsStr = "审核中";
    }
    return strutsStr;
}

function formatDate(date) {
    var time = new Date(date.time);
    var y = time.getFullYear();//年
    var m = time.getMonth() + 1;//月
    var d = time.getDate();//日
    m = m < 10 ? '0' + m : m;
    d = d < 10 ? '0' + d : d;
    return y + "-" + m + "-" + d;
}

function toDetail(id, billsType) {
    var url = "ea/reviewCirculate/ea_toCostBudgetDetail.jspa";
    var parameter = "?showFlag=" + showFlag + "&menuId=" + menuId + "&cashierBillsId=" + id + "&billsType=" + billsType;
    if (showFlag) {//单独部门传部门名称过去
        parameter += "&departmentName=" + departmentName;
    }
    window.location.href = basePath + url + parameter;

}

function executeReject(selectedIds, updateSta) {
    $.ajax({
        url: basePath + "ea/reviewCirculate/sajax_ea_updateStatus.jspa?", // 替换为实际的后端URL
        type: "post", async: true, dataType: "json", data: {
            "selectedItems": JSON.stringify(selectedIds), "status": updateSta
        }, success: function cbf(data) {
            layer.msg(updateSta + "成功");
            setTimeout(function () {
                window.location.reload();
            }, 2000); // 延迟1秒后刷新，让用户看到成功提示
        }, error: function cbf(data) {
            layer.msg(updateSta + "失败");
        }
    });
}

function audit(selectedIds) {
    $.ajax({
        url: basePath + "ea/scBudget/sajax_ea_ajaxUpdateWareManagement.jspa?", // 替换为实际的后端URL
        type: "post", async: true, dataType: "json", data: {
            "selectedItems": JSON.stringify(selectedIds)
        }, success: function cbf(data) {
            layer.msg("审核成功");
            setTimeout(function () {
                window.location.reload();
            }, 2000); // 延迟1秒后刷新，让用户看到成功提示
        }, error: function cbf(data) {
            layer.msg("审核失败");
        }
    });
}

function examineAndVerify(selectedIds) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: basePath + "ea/reviewCirculate/sajax_ea_examineAndVerify.jspa?",
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "selectedItems": JSON.stringify(selectedIds)
            },
            success: function cbf(data) {
                if (data === "success") {
                    examineAndVerifyReason = "success";
                    resolve("success");
                } else {
                    examineAndVerifyReason = "noSuccess";
                    resolve("noSuccess");
                }
            },
            error: function () {
                reject(new Error("审核验证请求失败"));
            }
        });
    });
}

function getHeight() {
    timer = setTimeout("getHeight()", 1000);
    if ($(".sec-list").length > 0) {
        //li偏移量-li的3倍高度<=页面高度时
        if ($(".sec-list").offset().top - $(".sec-list").height() * 3 <= $(window).height()) {
            if (pageNumber < pageCount) {
                getEntryStaffData();
            }
        }
    }
}

function getEntryStaffDataIsSend() {
    // 修复分页参数逻辑
    pageNumber += 1;
    const url = basePath + "ea/reviewCirculate/sajax_ea_ajaxCostBudgetBillList1.jspa";
    $.ajax({
        url: encodeURI(url), type: "POST", async: true, dataType: "json",

        data: {
            "pageForm.pageNumber": pageNumber, "pageForm.pageSize": pageSize,  // 确保这个值是20而不是其他值
            "billsType": billsType, "status": status

        }, success: function cbf(data) {
            var member = (new Function("return " + data))();
            if (member.pageForm == null) {
                if (pageNumber === 1) {  // 只有第一页才显示"暂无数据"
                    $(".tj_con").html("暂无数据");
                    $(".tj_con").css({"display": "flex", "align-items": "center", "justify-content": "center"});
                }
            } else {
                var pageForm = member.pageForm;
                $(".ttsw_last").removeClass("ttsw_last");
                if (pageForm != null) {
                    var htmlstr = "";
                    var obj;
                    pageCount = pageForm.pageCount;
                    count = pageForm.recordCount;

                    var num = pageForm.list.length - 1;
                    $(".ttsw_last").removeClass("ttsw_last");
                    for (var i = 0; i < pageForm.list.length; i++) {
                        obj = pageForm.list[i];
                        htmlstr += getHtmlStr(obj, i, num);
                    }

                    // 根据页码决定是替换还是追加内容
                    if (pageNumber === 1) {
                        $(".tj_con").html(htmlstr);
                    } else {
                        $(".tj_con").append(htmlstr);
                    }

                    $(".div-data").css({"display": "block"});
                }
            }
            scrollBool = true;
        }, error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getIsSend(selectedIds) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: basePath + "ea/reviewCirculate/sajax_ea_examineAndVerify.jspa?",
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "selectedItems": JSON.stringify(selectedIds)
            },
            success: function cbf(data) {
                if (data === "success") {
                    examineAndVerifyReason = "success";
                    resolve("success");
                } else {
                    examineAndVerifyReason = "noSuccess";
                    resolve("noSuccess");
                }
            },
            error: function () {
                reject(new Error("审核验证请求失败"));
            }
        });
    });
}