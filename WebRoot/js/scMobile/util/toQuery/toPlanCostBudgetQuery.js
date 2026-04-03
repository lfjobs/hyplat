//点击选择
function toCheck(obj,num){
    //清除所有li样式
    $(".ttsw_li").removeAttr("style"); //ie,ff均支持
    //点击添加样式
    $(obj).attr("style","color: red");
    $("#ttsw_li_check").val(num);
}

//点击取消
function toCancel(){
    //搜索栏输入空
    $("#ttsw_search").val("");
    //清除所有li样式
    $(".ttsw_li").removeAttr("style"); //ie,ff均支持
    $("#ttsw_li_check").val(0);
    window.history.back(-1);
}

//点击查询
function toSearch(){
    var searchType = $("#ttsw_li_check").val();//搜索类型
    var search = $("#ttsw_search").val();//搜索的值
    if(search == "" || search == null){
        alert("请输入要搜索的内容");
        return false;
    }
    var url = "";
    //判断是跳转的那个查询页面
    switch (menuId) {
        case "ng"://预算单列表
        case "cy":
            console.log("项目计划列表");
            url = "ea/scBudget/ea_toPlanCostBudgetBillList.jspa?tenantFlag="+tenantFlag;
            break;
        case "receive":
            url = "ea/scBudget/ea_toPlanCostBudgetBillReceiveList.jspa";
            break;
        case "sent":
            url = "ea/scBudget/ea_toPlanCostBudgetBillSentList.jspa";
            break;
        default:
            console.log("其他列表");
            url = "ea/scBudget/ea_toPlanCostBudgetBillList.jspa";
    }
    console.log(search+'----'+searchType+'-------'+menuId);
    var parame = "?search="+search + "&searchType="+searchType;
    window.location.href = basePath + url + parame;
}