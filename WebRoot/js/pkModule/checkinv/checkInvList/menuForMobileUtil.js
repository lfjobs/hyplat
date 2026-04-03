$(document).ready(function() {

    getOneMenu();
});

/**
 * 获取当前登录人所属一级菜单
 */
function getOneMenu(){
    var url = basePath+"ea/menuUtil/sajax_ea_findOrgByAcc.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data : {
            "organizationID":encodeURI(treeid),
            "datesete":new Date(),
        },
        dataType: "json",
        success: function cbf(data){
            var member = (new Function("return " + data))();//格式化返回参数
            var innerHtml = "";
            if(member != null){
                var oList = member.organizationList;
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
    if(showFlag){//分列表
       innerHtml += "<li onclick=\"toMenuClick('-1','-1');\">盘库单</li>";
    }else{//总列表
       innerHtml += "<li class=\"active\" onclick=\"toMenuClick('-1','-1');\">盘库单</li>";
    }
    for (var i = 0; i < oList.length; i++) {
        data[i] = {
            id : oList[i].organizationID,
            pid : oList[i].organizationPID,
            text : oList[i].organizationName,
            str : oList[i].status

        };
    }
    innerHtml += parentMenu(treeid,data);
    innerHtml += "</ul>";
    return innerHtml;
}



/**
 * 循环拼接一级菜单结果
 * @param compareID 公司id
 * @param vals 查询结果
 */
function parentMenu(compareID,vals) {//1级
    var result ="";
    for (var i = 0; i < vals.length; i++) {
        if( vals[i].pid == compareID && vals[i].str == "00" ){
            if(departmentId == vals[i].id){
                result+="<li class=\"active\" onclick=\"toMenuClick('"+vals[i].id+"','"+vals[i].text+"');\">";
            }else{
                result+="<li onclick=\"toMenuClick('"+vals[i].id+"','"+vals[i].text+"');\">";
            }
            result+= vals[i].text+"盘库</li>"
        }
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
    var url = "ea/cashinv/ea_checkInvList.jspa";
    var parameter = "?departmentID="+id+"&showFlag="+showFlag2+"&departmentName="+depName;
    window.location.href = basePath + url + parameter;
}