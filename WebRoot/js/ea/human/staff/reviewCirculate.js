$(".loading").hide();
$(function () {
    getSum();
    bindEvents();
});

function bindEvents() {
    $(".noWarehousing").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noWarehousing";
    })
    $(".isWarehousing").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isWarehousing";
    })
    $(".rejectWarehousing").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectWarehousing";
    })
    $(".noOutbound").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noOutbound";
    })
    $(".isOutbound").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isOutbound";
    })
    $(".rejectOutbound").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectOutbound";
    })


    $(".noCheck").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noCheck";
    })
    $(".isCheck").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isCheck";
    })
    $(".rejectCheck").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectCheck";
    })
    $(".noLoss").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noLoss";
    })
    $(".isLoss").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isLoss";
    })
    $(".rejectLoss").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectLoss";
    })
    $(".noProcurement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noProcurement";
    })
    $(".isProcurement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isProcurement";
    })
    $(".rejectProcurement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectProcurement";
    })
    $(".noLaunch").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noLaunch";
    })
    $(".isLaunch").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isLaunch";
    })
    $(".rejectLaunch").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectLaunch";
    })
    $(".noWithdraw").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noWithdraw";
    })
    $(".isWithdraw").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isWithdraw";
    })
    $(".rejectWithdraw").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectOutbound";
    })
    $(".noOrderGoods").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noOrderGoods";
    })
    $(".isOrderGoods").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isOrderGoods";
    })
    $(".rejectOrderGoods").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectOrderGoods";
    })
    $(".noShipment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noShipment";
    })
    $(".isShipment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isShipment";
    })
    $(".rejectShipment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectShipment";
    })
    $(".noPayment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noPayment";
    })
    $(".isPayment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isPayment";
    })
    $(".rejectPayment").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectPayment";
    })
    $(".noExpenseReimbursement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noExpenseReimbursement";
    })
    $(".isExpenseReimbursement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isExpenseReimbursement";
    })
    $(".rejectExpenseReimbursement").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectExpenseReimbursement";
    })
    $(".noInitialProject").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noInitialProject";
    })
    $(".isInitialProject").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isInitialProject";
    })
    $(".rejectInitialProject").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectInitialProject";
    })
    $(".noInspectionReport").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=noInspectionReport";
    })
    $(".isInspectionReport").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=isInspectionReport";
    })
    $(".rejectInspectionReport").click(function () {
        window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/inventoryAudit.jsp?companyID=" + companyId + "&type=rejectInspectionReport";
    })
}

/**
 * 获取条数
 */
function getSum() {
    const url = basePath + "ea/reviewCirculate/sajax_ea_getNum.jspa?companyID=" + companyId;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = JSON.parse(data);
            //入库
            $(".noReceivebox").html("(" + codeList.noWarehousing + ")");
            $(".isReceivebox").html("(" + codeList.isWarehousing + ")");
            $(".reject").html("(" + codeList.rejectWarehousing + ")");
            //出库
            $(".noReceivebox1").html("(" + codeList.noOutbound + ")");
            $(".isReceivebox1").html("(" + codeList.isOutbound + ")");
            $(".reject1").html("(" + codeList.rejectOutbound + ")");

            $(".noReceivebox2").html("(" + codeList.noWarehousing2 + ")");
            $(".isReceivebox2").html("(" + codeList.isWarehousing2 + ")");
            $(".reject2").html("(" + codeList.rejectWarehousing2 + ")");
            $(".noReceivebox3").html("(" + codeList.noWarehousing3 + ")");
            $(".isReceivebox3").html("(" + codeList.isWarehousing3 + ")");
            $(".reject3").html("(" + codeList.rejectWarehousing3 + ")");
            $(".noReceivebox4").html("(" + codeList.noWarehousing4 + ")");
            $(".isReceivebox4").html("(" + codeList.isWarehousing4 + ")");
            $(".reject4").html("(" + codeList.rejectWarehousing4 + ")");
            $(".noReceivebox5").html("(" + codeList.noWarehousing5 + ")");
            $(".isReceivebox5").html("(" + codeList.isWarehousing5 + ")");
            $(".reject5").html("(" + codeList.rejectWarehousing5 + ")");
            $(".noReceivebox6").html("(" + codeList.noWarehousing6 + ")");
            $(".isReceivebox6").html("(" + codeList.isWarehousing6 + ")");
            $(".reject6").html("(" + codeList.rejectWarehousing6 + ")");
            $(".noReceivebox7").html("(" + codeList.noWarehousing7 + ")");
            $(".isReceivebox7").html("(" + codeList.isWarehousing7 + ")");
            $(".reject7").html("(" + codeList.rejectWarehousing7 + ")");
            $(".noReceivebox8").html("(" + codeList.noWarehousing8 + ")");
            $(".isReceivebox8").html("(" + codeList.isWarehousing8 + ")");
            $(".reject8").html("(" + codeList.rejectWarehousing8 + ")");
            $(".noReceivebox9").html("(" + codeList.noWarehousing9 + ")");
            $(".isReceivebox9").html("(" + codeList.isWarehousing9 + ")");
            $(".reject9").html("(" + codeList.rejectWarehousing9 + ")");
            $(".noReceivebox10").html("(" + codeList.noWarehousing10 + ")");
            $(".isReceivebox10").html("(" + codeList.isWarehousing10 + ")");
            $(".reject10").html("(" + codeList.rejectWarehousing10 + ")");
            $(".noReceivebox11").html("(" + codeList.noWarehousing11 + ")");
            $(".isReceivebox11").html("(" + codeList.isWarehousing11 + ")");
            $(".reject11").html("(" + codeList.rejectWarehousing11 + ")");
            $(".noReceivebox12").html("(" + codeList.noWarehousing1 + ")");
            $(".isReceivebox12").html("(" + codeList.isWarehousing1 + ")");
            $(".reject12").html("(" + codeList.rejectWarehousing1 + ")");
        },
    });
}
