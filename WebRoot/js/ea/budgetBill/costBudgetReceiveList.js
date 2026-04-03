var timer;
$(document).ready(function() {
    if("hide" == head){
        $("#head").hide();
    }else{
        $("#head").show();
    }
    if(null == billsType){
        billsType = "初始项目单";
        setBackgroundColor($("#sell"));
    }else{
        switch(billsType){
            case "采购单":
                setBackgroundColor($("#sell"));
                break;
            case "初始项目单":
                setBackgroundColor($("#init"));
                break;
            case "收入":
                setBackgroundColor($("#init"));
                billsType = "初始项目单";
                break;
            case "支出":
                setBackgroundColor($("#init"));
                billsType = "初始项目单";
                break;
            case "上架单":
                setBackgroundColor($("#launch"));
                break;
            case "下架单":
                setBackgroundColor($("#down"));
                break;
            case "订货单":
                setBackgroundColor($("#order"));
                break;
            case "收款单":
                setBackgroundColor($("#collectMoney"));
                break;
            case "送货单":
                setBackgroundColor($("#delivery"));
                break;
            case "验货单":
                setBackgroundColor($("#inspection"));
                break;
            case "入库单":
                setBackgroundColor($("#inbound"));
                break;
            case "出库单":
                setBackgroundColor($("#outbound"));
                break;
            case "盘库单":
                setBackgroundColor($("#checkInventory"));
                break;
            case "报损单":
                setBackgroundColor($("#lossReported"));
                break;
            case "费用报销单":
                setBackgroundColor($("#reimbursement"));
                break;
        }

    }
    getOneMenu(billsType);
    getHeight();

    $("#sell").on("click",function (){
        setBackgroundColor(this);
        billsType = "采购单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#init").on("click",function (){
        setBackgroundColor(this);
        billsType = "初始项目单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#launch").on("click",function (){
        setBackgroundColor(this);
        billsType = "上架单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#down").on("click",function (){
        setBackgroundColor(this);
        billsType = "下架单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#order").on("click",function (){
        setBackgroundColor(this);
        billsType = "订货单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#collectMoney").on("click",function (){
        setBackgroundColor(this);
        billsType = "收款单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#delivery").on("click",function (){
        setBackgroundColor(this);
        billsType = "送货单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#inspection").on("click",function (){
        setBackgroundColor(this);
        billsType = "验货单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#inbound").on("click",function (){
        setBackgroundColor(this);
        billsType = "入库单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#outbound").on("click",function (){
        setBackgroundColor(this);
        billsType = "出库单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#checkInventory").on("click",function (){
        setBackgroundColor(this);
        billsType = "盘库单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#lossReported").on("click",function (){
        setBackgroundColor(this);
        billsType = "报损单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
    });
    $("#reimbursement").on("click",function (){
        setBackgroundColor(this);
        billsType = "费用报销单";
        getOneMenu(billsType);
        pageNumber = 0;
        pageCount = 0;
        $(".tj_con").empty();
        loaded();
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

/**
 * 异步获取加载数据
 */
function loaded(){
    clearTimeout(timer);
    pageNumber += 1;
    var url = basePath+"ea/scBudget/sajax_ea_ajaxCostBudgetBillReceiveList.jspa";
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
            "billsType":billsType
        },
        dataType : "json",
        success : function(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var pageForm = member.pageForm;
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            if(pageForm!=null) {
                var htmlstr="";
                var obj;
                pageCount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var num = pageForm.list.length-1;
                $(".ttsw_last").removeClass("ttsw_last");
                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    htmlstr += getHtmlStr(obj,i,num);//拼接状态html
                }
                $(".tj_con").append(htmlstr);
                getHeight();
            }
        },
        error:function(data){
            // console.log("加载下一页失败");
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
    var htmlstr = liStr +'<section id="sec-checked">'
                            +'<aside class="aside_no"  checkCasId="'+obj.cashierBillsID+'">'
                                +'<img class="img_no" src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>'
                                +'<img class="img_yes" src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>'
                            +'</aside>'
                            +'<h5>单据凭证号：'+obj.journalNum+'<img src="'+basePath+'images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>'
                        +'</section>'
                        +'<div onclick="toDetail(\''+obj.cashierBillsID+'\');">'
                            + (obj.companyName != null && obj.companyName != '' ? '<p>公司：' + obj.companyName + '</p>' : '')
                            + '<p>行业分类：' + obj.tradeName + '</p>'
                            // +'<p>项目编号：'+obj.projectCode+'</p>'
                            +'<p>项目分类：'+obj.xmtypename+'</p>'
                            // +'<p>项目名称：'+obj.projectName+'</p>'
                            +'<p>单据类别：'+obj.billsType+'</p>'
                            + (('入库单' == obj.billsType || '出库单' == obj.billsType || '盘库单' == obj.billsType || '报损单' == obj.billsType)
                                ? '<p>仓库：' + (obj.dataDepotName != null && obj.dataDepotName != '' ? obj.dataDepotName : '') + '</p>' : '')
                            + (obj.departmentName != null && obj.departmentName != '' ? '<p>责任人部门：' + obj.departmentName + '</p>' : '')
                            +'<p>责任人：'+obj.staffName+'</p>'
                            +'<p>制单人：'+obj.inputName+'</p>'
                            +'<p>制单日期：'+cashierDate+'</p>'
                            + (obj.ctUserName != null && obj.ctUserName != '' ? '<p>往来个人：' + obj.ctUserName + '</p>' : '')
                            + (obj.startTime != null && obj.startTime != '' ? '<p>开始日期：' + formatDate(obj.startTime) + '</p>' : '')
                            + (obj.endTime != null && obj.endTime != '' ? '<p>结束日期：' + formatDate(obj.endTime) + '</p>' : '')
                            + (obj.priceSub != null && obj.priceSub != '' ? '<p>总金额：' + obj.priceSub + '</p>' : '')
                            +'<p class="un">单据状态：<span>'+strutsStr+'</span></p>'
                        +'</div>'
                    +'</li>';
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

function setBackgroundColor(obj) {
    $("#sell").removeClass("active_color");
    $("#init").removeClass("active_color");
    $("#launch").removeClass("active_color");
    $("#down").removeClass("active_color");
    $("#order").removeClass("active_color");
    $("#collectMoney").removeClass("active_color");
    $("#delivery").removeClass("active_color");
    $("#inspection").removeClass("active_color");
    $("#inbound").removeClass("active_color");
    $("#outbound").removeClass("active_color");
    $("#checkInventory").removeClass("active_color");
    $("#lossReported").removeClass("active_color");
    $("#reimbursement").removeClass("active_color");

    $(obj).addClass("active_color");
}