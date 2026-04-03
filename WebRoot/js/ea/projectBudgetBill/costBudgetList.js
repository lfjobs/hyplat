var timer;
var status="all";

$(document).ready(function() {
    if (tenantFlag == "personal") {
        $("#sell").show();
        $("#sellSection").show();
        $("#myOrder").show();
        $("#myReturn").show();
        $("#companyReturn").hide();
        $("#order_tab_cur").addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
        $(".tab_con").eq(0).show().siblings(".tab_con").hide();
    }else{
        $("#sell").hide();
        $("#sellSection").hide();
        $("#myOrder").hide();
        $("#myReturn").hide();
        $("#companyReturn").show();
    }

    $(".tab_con").eq(0).show();
    //绑定切换事件
    $(".order_tab_box").click(function () {
        var _index = $(".order_tab>div").index(this);
        $(this).addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
        $(".tab_con").eq(_index).show().siblings(".tab_con").hide();
    });

    if(null == billsType){
        billsType = "初始项目单";
    }else {
        switch (billsType) {
            case "收入":
                billsType = "初始项目单";
                break;
            case "支出":
                billsType = "初始项目单";
                break;
        }
    }

    getOneMenu(billsType);
    getHeight();
    setUI();
    pageNumber = 0;
    pageCount = 0;
    $(".tj_con").empty();
    loaded();
    if(menuPage == "show"){
        order();
    }

    $("#init").on("click",function (){
        billsType = "初始项目单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });

    $("#buy").on("click",function (){
        billsType = "采购单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#inspection").on("click",function (){
        billsType = "验货单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#inbound").on("click",function (){
        billsType = "入库单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#launch").on("click",function (){
        billsType = "上架单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#down").on("click",function (){
        billsType = "下架单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#order").on("click",function (){
        billsType = "订货单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#collectMoney").on("click",function (){
        billsType = "收款单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#delivery").on("click",function (){
        billsType = "送货单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#outbound").on("click",function (){
        billsType = "出库单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#checkInventory").on("click",function (){
        billsType = "盘库单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#lossReported").on("click",function (){
        billsType = "报损单";
        setUI();
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });
    $("#reimbursement").on("click",function (){
        billsType = "费用报销单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
        hideOrderDialog();
    });

    //卖家订单管理
    $(".sdd").click(function () {
        document.location.href = basePath + "ea/seller/ea_getcomporder.jspa?companyid=" + (companyid == "" ? treeid : companyid) + "&staffid=" + staffId + "&sccId=" + sccid;
    });

    //新版订单管理
    $(".xbdd").click(function () {
        document.location.href = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?&companyid="+(companyid == "" ? treeid : companyid)+ "&staffid=" + staffId +"&sort=1";
    });

    //新版订单管理
    $(".zdjysz").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/my/set/szset.jsp";
    });

    //卖家退货单
    $(".sth").click(function () {
        document.location.href = basePath + "ea/seller/ea_getReturnOrder.jspa?companyid=" +(companyid == "" ? treeid : companyid) + "&sccId=" + sccid;
    });

    //买家订单管理
    $(".bdd").click(function () {
        //document.location.href=basePath+"ea/hypb/ea_getcomporder.jspa?staid="+staffid;
        document.location.href = basePath + "ea/pobuy/ea_getPhoneOrdersList.jspa?staid=" + staffId + "&sccId=" + sccid;
    });

    //买家退货单
    $(".bth").click(function () {
        document.location.href = basePath + "/ea/refundMoney/ea_getRefundList.jspa?staffid=" + staffId + "&sccId=" + sccid;
    });

    $("#importImage").click(function () {
        $(".div-tupian2").css({"opacity": "1", "transform": 'translate(0)'});
    });

    $(".div-close").click(function () {
        $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
    });

    $('#as').diyUpload({
        url: basePath + 'ea/productsmanag/sajax_ea_uplodFile.jspa?companyID=' + companyid,
        success: function (data) {
            var member = eval("(" + data + ")");
            var path = member.path;
            var name = member.name;
            var isSuccess = member.siSuccess;
            if (isSuccess) {
                var imageURL = basePath + path;
                if(importImage(imageURL) != "success"){
                    alert("订单导入失败");
                }else{
                    alert("成功导入订单");
                }
                $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
                window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?billsType=" + billsType;
            }
        },
        error: function (err) {
            console.info(err);
        },
        accept: {
            title: "Images",
            extensions: "gif,jpg,jpeg,bmp,png",
            mimeTypes: "image/*"
        },
        buttonText: '选择图片',
        chunked: false,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 9,
        fileSizeLimit: 9 * 5 * 1024 * 1024,
        fileSingleSizeLimit: 5 * 1024 * 1024
    });
});

/**
 * 定时刷新判断页面距底高度
 */
function getHeight(){
	timer=setTimeout("getHeight()", 1000);
	if($(".ttsw_last").length>0){
        //li偏移量-li的3倍高度<=页面高度时
		if($(".ttsw_last").offset().top-$(".ttsw_last").height()*3<=$(window).height()){
			if(pageNumber<pageCount){
				loaded();
			}
		}
	}
}

function buttonClicked(option) {
    pageNumber = 0;
    pageCount = 0;
    $(".tj_con").empty();
    status = option;
    loaded();
    selectModal.style.display = "none";
}

/**
 * 异步获取加载数据
 */
function loaded(){
    clearTimeout(timer);
    pageNumber += 1;
    var url = basePath+"ea/scBudget/sajax_ea_ajaxCostBudgetBillList.jspa";
    $.ajax({
        url : url,
        type : "POST",
        async : true,//默认设置为true，所有请求均为异步请求
        data : {
            "pageForm.pageNumber":pageNumber,
            "departmentID":departmentId,
            "showFlag":showFlag,
            "search":search,
            "searchType":searchType,
            "billsType":billsType,
            "approvedStatus":status
        },
        dataType : "json",
        success : function(data) {
            var pageForm = null;
            var member = (new Function("return " + data))();//格式化返回参数
            if(null != member){
                pageForm = member.pageForm;
            }
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            if(pageForm != null) {
                var htmlstr="";
                var obj;
                pageCount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var num = pageForm.list.length-1;
                $(".ttsw_last").removeClass("ttsw_last");
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    htmlstr += getHtmlStr(obj,i,num);
                }
                $(".tj_con").append(htmlstr);
                getHeight();
            }
        },
        error:function(data){
            console.log("加载失败");
        }
    });
}
/**
 * 拼接页面
 * @param obj 循环数据
 * @param i 循环自增i
 * @param num 集合总数-1
 * @returns {string} 返回拼接好的页面
 */
function getHtmlStr(obj,i,num){
    var liStr = getLi(i,num);//获取li
    var cashierDate = formatDate(obj.cashierDate);//格式化日期
    var strutsStr = getStrutsStr(obj);//拼接状态
    var htmlstr = liStr + '<section id="sec-checked">'
        + '<aside class="aside_no"  checkCasId="' + obj.cashierBillsID + '" cashierBillsStatus="' + obj.status
            + '" billsType="' + obj.billsType + '" invName="' + obj.dataDepotName + '">'
        + '<img class="img_no" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>'
        + '<img class="img_yes" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>'
        + '</aside>'
        + '<h5>单据凭证号：' + obj.journalNum + '<img src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>'
        + '</section>'
        + '<div onclick="toDetail(\'' + obj.cashierBillsID + '\');">'
        + (obj.companyName != null && obj.companyName != '' ? '<p>公司：' + obj.companyName + '</p>' : '')
        + '<p>行业分类：' + obj.tradeName + '</p>'
        // + '<p>项目编号：' + obj.projectCode + '</p>'
        + '<p>项目分类：' + obj.xmtypename + '</p>'
        // + '<p>项目名称：' + obj.projectName + '</p>'
        + '<p>单据类别：' + obj.billsType + '</p>'
        + (('入库单' == obj.billsType || '出库单' == obj.billsType || '盘库单' == obj.billsType || '报损单' == obj.billsType)
            ? '<p>仓库：' + (obj.dataDepotName != null && obj.dataDepotName != '' ? obj.dataDepotName : '') + '</p>' : '')
        + (obj.departmentName != null && obj.departmentName != '' ? '<p>责任人部门：' + obj.departmentName + '</p>' : '')
        + '<p>责任人：' + obj.staffName + '</p>'
        + '<p>制单人：' + obj.inputName + '</p>'
        + '<p>制单日期：' + cashierDate + '</p>'
        + (obj.ctUserName != null && obj.ctUserName != '' ? '<p>往来个人：' + obj.ctUserName + '</p>' : '')
        + (obj.startTime != null && obj.startTime != '' ? '<p>开始日期：' + formatDate(obj.startTime) + '</p>' : '')
        + (obj.endTime != null && obj.endTime != '' ? '<p>结束日期：' + formatDate(obj.endTime) + '</p>' : '')
        + (obj.priceSub != null && obj.priceSub != '' ? '<p>总金额：' + obj.priceSub + '</p>' : '')
        + '<p class="un">单据状态：<span>'+strutsStr+'</span></p>' +'</div>' +'</li>';
    return htmlstr;
}

/**
 * 获取li
 * @param i 循环自增i
 * @param num 集合总数-1
 * @returns {string} 返回li
 */
function getLi(i,num){
    if(i==num){
        return "<li class=\"clearfix ttsw_last\"\">";
    }else{
        return "<li class=\"clearfix\"\">";
    }
}

/**
 * 拼接状态
 * @param obj 循环数据
 * @returns {string} 返回状态值
 */
function getStrutsStr(obj){
    var strutsStr = "";
    //拼接状态html
    if(obj.paystatus != null && obj.paystatus != ""){
        if(obj.paystatus == "00"){
            strutsStr="项目未分配";
        }else if(obj.paystatus == "01"){
            strutsStr="项目已分配未跟踪";
        }else if(obj.paystatus == "02"){
            strutsStr="项目已跟踪未考评";
        }else if(obj.paystatus == "03"){
            strutsStr="项目已考评";
        }
    }else{
        if(obj.status == "00"){
            strutsStr="拟稿";
        }else if(obj.status == "01"){
            strutsStr="审核中-招标前";
        }else if(obj.status == "02"){
            strutsStr="已通过-招标前";
        }else if(obj.status == "03"){
            strutsStr="比价审核中";
        }else if(obj.status == "04"){
            strutsStr="已提交资金申请";
        }else if(obj.status == "05"){
            strutsStr=" 待会计审核";
        }else if(obj.status == "06"){
            strutsStr="待出纳审核";
        }else if(obj.status == "07"){
            strutsStr="已审核";
        }else if(obj.status == "20"){
            strutsStr="税务单据";
        }else if(obj.status == "08"){
            strutsStr="三审已归档";
        }else if(obj.status == "09"){
            strutsStr="待确认收款";
        }else if(obj.status == "40"){
            strutsStr="待确定预算收入单";
        }else if(obj.status == "45"){
            strutsStr="已收款";
        }else if(obj.status == "46"){
            strutsStr="系统生成";
        }else if(obj.status == "11"){
            strutsStr="驳回待修改";
        }else if(obj.status == "50"){
            strutsStr="传阅中";
        }else if(obj.status == "15"){
            strutsStr="已入库";
        }else if(obj.status == "16"){
            strutsStr="已出库";
        }
    }
    return strutsStr;
}

/**
 * 格式化日期
 * @param cashierDate 需格式化时间
 * @returns {string}
 */
function getFormatDate(date){
    var time = new Date(date.time);
    var y = time.getFullYear();//年
    var m = time.getMonth()+1;//月
    var da = time.getDate();//日
    var h = time.getHours();//时
    var mm = time.getMinutes();//分
    var s = time.getSeconds();//秒
    s = s < 10 ? '0' + s: s;
    return y+"-"+m+"-"+da+" "+h+":"+mm+":"+s;//拼接日期
}

function formatDate(date){
    var time = new Date(date.time);
    var y = time.getFullYear();//年
    var m = time.getMonth()+1;//月
    var d = time.getDate();//日
    m = m < 10 ? '0' + m : m;
    d = d < 10 ? '0' + d : d;
    return y+"-"+m+"-"+d;
}

function setUI() {
    // $("#title").html("项目订单" + (billsType != "" ? "-" + billsType : ""));
    $("#check").show();
    $("#inboundOper").hide();
    $("#add").hide();
    $("#importImage").hide();

    if(billsType == "采购单"){
        $("#check").html("验货");
        $("#import").show();
        $("#importImage").show();
    }else if(billsType == "验货单"){
        $("#check").html("转入库单");
        $("#import").hide();
    }else if(billsType == "入库单"){
        $("#inboundOper").show();
        $("#check").hide();
        $("#import").hide();
    }else if(billsType == "盘库单"){
        $("#check").html("报损");
        $("#import").hide();
    }else if(billsType == "报损单"){
        $("#check").hide();
        $("#import").hide();
    }else if(billsType == "初始项目单"){
        $("#add").show();
        $("#importImage").show();
        $("#check").hide();
        $("#import").hide();
    }else{
        $("#check").hide();
        $("#import").hide();
    }
}

function importBillsData() {
    let result = 'success';
    let billsData = [];
    const item= localStorage.getItem("dataImport");
    const codeList1 = item ? JSON.parse(item) : [];
    const htmlstr = [];
    const codeList = codeList1;
    if (codeList == null || codeList.length <= 3) {
        alert("没有数据导入");
        result = 'error';
    } else {
        const list = codeList;
        let counter = 1;
        let cashierBillsData = '';
        for (let i = 1; i < list.length; i++) {
            const lineNo = list[i][0];
            const goodsName = list[i][1];
            const barCode = list[i][2];
            const specs = list[i][3];
            const count = list[i][4];
            const price = list[i][5];
            const amount = list[i][6];
            const account = list[i][7];
            if(lineNo == '总计'){
                break;
            }
            if(i == 1){
                cashierBillsData = lineNo + ',' + goodsName + ',' + barCode + ',' + specs + ',' + count + ',' + price + ',' + amount + ',' + account;
            }else{
                if(i == 2){
                    continue;
                }
                const importData = {
                    lineNo: lineNo,
                    goodsName: goodsName,
                    barCode: barCode,
                    specs: specs,
                    count: count,
                    price: price,
                    amount: amount,
                    account: account
                };
                billsData.push(importData);
            }
        }

        $.ajax({
            url : basePath + "ea/scBudget/sajax_ea_importCostBudgetSheet.jspa",
            type : "POST",
            async : false,
            data : {
                "cashierBillsData":cashierBillsData,
                "goodsBillsData":JSON.stringify(billsData)
            },
            dataType : "json",
            success : function(data) {
                let resultData = eval("(" + data + ")");
                localStorage.removeItem("dataImport");
                result = resultData.result;
            },
            error : function(data){
                localStorage.removeItem("dataImport");
                result = 'error';
            }
        });
    }
    return result;
}

function importImage(imageURL) {
    var result = 'success';
    $.ajax({
        url : basePath + "ea/scBudget/sajax_ea_importCostBudgetSheetByImage.jspa",
        type : "POST",
        async : false,
        data : {
            "imageURL":imageURL,
            "billsType":billsType
        },
        dataType : "json",
        success : function(data) {
            let resultData = eval("(" + data + ")");
            result = resultData.result;
        },
        error : function(data){
            result = 'error';
        }
    });
    return result;
}