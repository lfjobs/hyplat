$(document).ready(function() {

    getOneMenu();
});

/**
 * 获取当前登录人所属一级菜单
 */
function getOneMenu(){
    var url = basePath + "ea/scBudget/sajax_ea_countPlanBudgetBillList.jspa?tenantFlag="+tenantFlag;
    $.ajax({
        url: url,
        type: "POST",
        // data : {
        //     "organizationID":encodeURI(treeid),
        //     "datesete":new Date(),
        // },
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");//格式化返回参数
            var innerHtml = "";
            if(member){
                var oList = [{"menuId":"ng","text":"拟稿("+member.ng+")"},{"menuId":"cy","text":"传阅("+member.ng+")"},{"menuId":"sp","text":"审批(0)"},{"menuId":"zb","text":"招标(0)"},{"menuId":"receive","text":"收件("+member.receive+")"},{"menuId":"sent","text":"已发送("+member.sent+")"}];

                innerHtml = getOneMenuHtml(oList);
            }
            $('#ttsw_one_menu_id').append(innerHtml);
        },error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}

/**
 * 获取拼接一级菜单
 * @param oList 后台返回菜单数据
 * @returns {string} 返回拼接好页面
 */
function getOneMenuHtml(oList){
    var data = new Array();
    var innerHtml = "<ul class=\"bug-nav\">";
    // if(showFlag){//分列表
    //    innerHtml += "<li onclick=\"toMenuClick('-1','-1');\">拟稿</li>";
    // }else{//总列表
    //    innerHtml += "<li class=\"active\" onclick=\"toMenuClick('-1','-1');\">拟稿</li>";
    // }

    for (var i = 0; i < oList.length; i++) {
        data[i] = {
            id : oList[i].menuId,
            text : oList[i].text,
        };
    }
    innerHtml += parentMenu(data);
    innerHtml += "</ul>";
    return innerHtml;
}

/**
 * 循环拼接一级菜单结果
 * @param vals 查询结果
 */
function parentMenu(vals) {//1级
    var result ="";
    for (var i = 0; i < vals.length; i++) {
        if(menuId == vals[i].id){
            result+="<li class=\"active\" onclick=\"toMenuClick('"+vals[i].id+"','"+vals[i].text+"');\">";
        }else{
            result+="<li onclick=\"toMenuClick('"+vals[i].id+"','"+vals[i].text+"');\">";
        }
        result+= vals[i].text+"</li>"
    }
    return result;
}

/**
 * 根据堵门id跳转
 * @param url
 */
function toMenuClick(id,depName){
    var showFlag2 = "-1" == id ? false:true;//id为-1时显示总列表
    //拼接链接
    var url = "";
    if(id == "ng" || id == "cy"){
        var cashierBillsId = "";
        $(".aside_yes").each(function (){
            cashierBillsId=$(this).attr("checkCasId");
        });
        url = "ea/scBudget/ea_toPlanCostBudgetBillList.jspa?tenantFlag="+tenantFlag+"&cashierBillsId="+cashierBillsId+"&menuId="+id;
    }else if(id == "receive"){
        url = "ea/scBudget/ea_toPlanCostBudgetBillReceiveList.jspa?menuId="+id;
    } else if(id == "sent"){
        url = "ea/scBudget/ea_toPlanCostBudgetBillSentList.jspa?menuId="+id;
    }
    // console.log("路径："+url);
    if(url){
        // var parameter = "&menuId="+id;
        window.location.href = basePath + url;
    }else{
        alert("开发中");
    }

}