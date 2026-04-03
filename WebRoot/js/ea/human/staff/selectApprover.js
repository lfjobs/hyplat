let appro = {}
const approver = function () {
    this.v = "2.4.3"

};
approver.getData = function(param){
    approver.initData(param);
    appro.type = "parent";
    approver.getDataToGet();

}
/**
 * 初始化数据
 */
approver.initData = function(param){
    appro={
        pageNumber:1,
        pageSize:40,
        pageCount:0,
        selectedData:[],
        selectedID:"",
        childPageNumber:1,
        childPageCount:0,
        selectedParentId:"",
        dataListData:{},
        param:{},
        copySelectedData:[],
        type:"",
        parentDataList:[],
        childDataList:[],
        callBackBool:false,
    }
    const htmlStr = approver.getHtmlStr();
    approver.openPage(htmlStr);
    appro.param = param;
    if (param.selectedData != null){
        appro.selectedData = param.selectedData;
    }
}
/**
 * 查询数据
 */
approver.getDataToGet = function(){
    const param = new Array();
    param.push("&&pageNumber=" + appro.pageNumber);
    param.push("&&pageSize=" + appro.pageSize);
    let url = "";
    if ("parent" == appro.type) {
        url = basePath + appro.param.url + param.join("");
    } else if ("child" == appro.type){
        param.push("&&" +appro.param["queryChildId"] +"=" + appro.selectedParentId);
        url = basePath + appro.param.childUrl + param.join("");
    } else if ("query" == appro.type){
        param.push("&&queryName=" + $("#queryName").val());
        url = basePath + appro.param.queryUrl + param.join("");
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            const list = eval("(" + data + ")");
            if (list == null){
                str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
                $("#dataList").html(str.join(""));
            } else {
                if ("parent" == appro.type){
                    const dataList = list["list"];
                    appro.pageCount = list["pageCount"];
                    approver.getHtmlByData(dataList);
                    appro.parentDataList = appro.parentDataList.concat(dataList);
                } else if ("child" == appro.type) {
                    const dataList = list["list"];
                    appro.childPageCount = list["pageCount"];
                    approver.getHtmlByData(dataList);
                    appro.childDataList = appro.childDataList.concat(dataList);
                }


            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

approver.getChildData = function(id,name){
    appro.type = "child";
    appro.childPageNumber = 1;
    $("#dataList").html("");
    appro.selectedParentId = id;
    approver.getDataToGet();
}

approver.getDataByName = function(){
    appro.type = "query";
    approver.getDataToGet();
}



approver.getHtmlByData = function(dataList){
    const str = new Array();
    const dataListLength = dataList.length;
    let idData = "",nameData="";
    let nameDataArr = [];
    if("parent" == appro.type){
        nameDataArr = appro.param.parentNameData;
    } else if ("child" == appro.type){
        nameDataArr = appro.param.childNameData;
    }
    if (dataListLength > 0){
        for (let i = 0; i < dataList.length; i++) {
            let nameStr = "";
            if("parent" == appro.type){
                idData = dataList[i][appro.param.parentId];
                nameData = dataList[i][appro.param.parentName];
            } else if ("child" == appro.type){
                idData = dataList[i][appro.param.childId];
                nameData = dataList[i][appro.param.childName];
            }
            if (nameDataArr != null){
                let length = nameDataArr.length;
                for (let j = 0; j < length; j++){
                    let name = dataList[i][nameDataArr[j]];
                    if (name != null && name != undefined){
                        nameStr += name;
                    } else {
                        nameStr += nameDataArr[j];
                    }
                }
            } else {
                nameStr = nameData;
            }
            dataList[i]["name"] = nameData;
            appro.dataListData[idData] = dataList[i];

            str.push("<li>");
            str.push("<div style='width:100%;padding-left:10px' id='data-" + idData + "'" + " onclick=approver.spdSelectData('" + idData + "','" +nameData +"')>");
            str.push("<div style='width:18px;float:left'>");
            const selected = appro.selectedData.includes(idData);
            if (selected){
                str.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:none' >&#xe63f;</i> ")
                str.push("<i class=\"layui-icon div-selected\" style='font-size:20px;color:#c33820;display:block;' >&#x1005;</i> ")
            } else {
                str.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:block' >&#xe63f;</i> ")
                str.push("<i class=\"layui-icon div-selected\" style='font-size:20px;color:#c33820;display:none' >&#x1005;</i> ")
            }
            str.push("</div>");
            str.push("<label style='float:left;margin-left:10px'>" + nameStr + "</label>");

            str.push("</div>")
            if("parent" == appro.type){
                str.push("<div class='selected-child' disabled id='icon-" + idData + "'" + "onclick=approver.getChildData('" + idData + "','" + dataList[i][1] + "')>");
                str.push("<i class=\"layui-icon\" >&#xe649;</i>");
                str.push("<label class='label-child-name'>下级</label>");
                str.push("</div>");
            }
            str.push("</li>");
        }
        $("#dataList").append(str.join(""));
    } else {
        str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
        $("#dataList").html(str.join(""));
    }
    $(".div-select-data").html(appro.selectedData.length + "/100");
    $(".select-num").html(appro.selectedData.length + "个（含子级）");


}

approver.openPage = function(htmlStr){
    layer.open({
        type: 1,
        title:false,
        closeBtn:false,
        anim: 5,
        isOutAnim: false,
        shadeClose: true,
        content: htmlStr,
        offset: 'lb',
        area: ['100%', '100%'],
        scrollbar: true,
        success: function(layero){
            const divElementList = layero.find('#selectBox');
            divElementList.on('scroll', function() {
                if (this.scrollTop + this.clientHeight >= this.scrollHeight || 1==1) {
                    if (ject.pageNumber < ject.pageCount){
                        ject.pageNumber++;
                        ject.getDataToGet();
                    }

                }
            })
        }

    });
    $(".spd-content").height($(window).height() - $(".spd-header").height());
    let height = $(".spd-content").height();
    height = height - $(".dtd-oa-search-bar").height();
    height = height - $(".div-bottom").height()
    $("#selectBox").height(height - 20);
    $(".bottom-selected-data").width( $(".content").width() - $(".data-submit").width());
}

/**
 * 勾选数据
 * @param id
 */
approver.spdSelectData = function(id,name){
    if (appro.selectedID != ""){
        const selectedPId = appro.selectedData.includes(appro.selectedID);
        if (!selectedPId){
            appro.selectedData.push(appro.selectedID);
        }
    }
    const selected = appro.selectedData.includes(id);
    if (selected){
        $("#data-" + id +" .div-selected").hide();
        $("#data-" + id +" .div-not-selected").show();
        $("#icon-" + id).css("color","#b98e86");
        $("#icon-" + id).css("disabled",false);
        appro.selectedData = appro.selectedData.filter(item => item !== id);
    } else {
        $("#data-" + id +" .div-selected").show();
        $("#data-" + id +" .div-not-selected").hide();
        $("#icon-" + id).css("color","#c33820");
        $("#icon-" + id).css("disabled",true);
        appro.selectedData.push(id);
    }
    $(".div-select-data").html(appro.selectedData.length + "/100");
    $(".select-num").html(appro.selectedData.length + "个（含子级）");

}


approver.returnBack = function(){
    if ("" == appro.selectedParentId){
        parent.layer.close(parent.layer.index);
    } else {
        appro.selectedParentId = "";
        $("#dataList").html("");
        appro.pageNumber = 1;
        appro.type = "parent";
        approver.getHtmlByData(appro.parentDataList);
    }

}
approver.viewSelectedData = function(){
    $("#mask").fadeIn();
    $("#dataLayer").animate({"bottom": 0});
    approver.getSelectedData(appro.selectedData)
    appro.copySelectedData = appro.selectedData.concat();
}

/**
 * 查看已选择的数据
 * @param data
 */
approver.getSelectedData = function(data){
    const htmlstr = new Array();
    const length = data.length;
    for (let i = 0; i < length; i++){
        let id = appro.selectedData[i];
        let data = appro.dataListData[id];
        htmlstr.push("<li class='selected-" + id + "'>");
        htmlstr.push("<div style='width:80%'>");
        htmlstr.push("<label>" + data["name"] + "</label>");
        htmlstr.push("</div>");
        htmlstr.push("<button class=\"layui-btn layui-btn-primary layui-btn-xs\" style='font-weight:550;height:26px'onclick=approver.deleteSelectedData('" + id + "')>移除</button>")
        htmlstr.push("<div>");
        htmlstr.push("</div>");
        htmlstr.push("</li>");
    }
    $("#approver-selectedDataList").html(htmlstr.join(""));
}

/**
 * 删除部门
 * @param organizationID
 */
approver.deleteSelectedData = function(id){
    appro.copySelectedData = appro.copySelectedData.filter(item => item !== id);
    $(".selected-" + id).remove();
}

/**
 * 关闭查看已选部门数据
 */
approver.closeMask = function() {
    $("#mask").fadeOut();
    $("#dataLayer").animate({"bottom": "-100%"});
}

approver.submitSelectedData = function(){
    $("#mask").fadeOut();
    $("#dataLayer").animate({"bottom": "-100%"});
    appro.selectedData = appro.copySelectedData.concat();
    $("#dataList").html("");
    if ("parent" == appro.type){
        approver.getHtmlByData(appro.parentDataList);
    } else if ("child" == appro.type){
        approver.getHtmlByData(appro.childDataList);
    }
}

/**
 * 多选提交数据
 */
approver.submitData = function(){
    let length = appro.selectedData.length;
    let id="",name = "";
    for (let i = 0; i < length; i++){
        if (appro.selectedData[i] != ""){
            id += "," + appro.selectedData[i];
            name += "," + appro.dataListData[appro.selectedData[i]]["name"];
        }
    }
    if (id != ""){
        id = id.substring(1);
        name = name.substring(1);
    }
    $("#" + appro.param.id).val(id);
    $("#" + appro.param.name).val(name);

    if(appro.callBackBool){
        parent.callbackData(id,name);
    }
    parent.layer.close(parent.layer.index);
}


/**
 * 弹出框页面
 * @returns {string}
 */
approver.getHtmlStr = function(){
    const htmlStr = new Array();
    htmlStr.push("<header class=\"header spd-header\">");
    htmlStr.push("<ul class=\"clearfix\">");
    htmlStr.push("<li>");
    htmlStr.push("<a onclick=\"approver.returnBack()\" target=\"_self\">");
    htmlStr.push("<img src=\"http://localhost:8080/images/ea/office/contract/stamp/return.png\"/>");
    htmlStr.push("</a>");
    htmlStr.push("</li>");
    htmlStr.push("<li>");
    htmlStr.push("审批人选择");
    htmlStr.push("</li>");
    htmlStr.push("<li></li>");
    htmlStr.push("</ul>");
    htmlStr.push("</header>");
    htmlStr.push("<div class=\"spd-content\" style='overflow:hidden'>");
    htmlStr.push("<div class=\"dtd-oa-search-bar\">");
    htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper query-data  \">");
    htmlStr.push("<i class=\"layui-icon\" >&#xe615;</i>");
    htmlStr.push("</div>");
    htmlStr.push("<input type=\"text\" class=\"dtd-oa-search-bar-input\" placeholder=\"搜索\" onchange='approver.getDataByName()' name=\"queryName\" id=\"queryName\" autocomplete=“off”/>");
    htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper close-data\" >");
    htmlStr.push("<i class=\"layui-icon\" >&#x1006;</i>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    htmlStr.push("<article id=\"selectBox\">");
    htmlStr.push("<ul id=\"dataList\" class=\"data-list div-mask-list\">");
    htmlStr.push("</ul>");
    htmlStr.push("</article>");
    htmlStr.push("<div class=\"div-bottom\" >");
    htmlStr.push("<div class=\"bottom-selected-data\" onclick='approver.viewSelectedData()'>");
    htmlStr.push("<div style=\"padding:10px\">");
    htmlStr.push("<span>已选择：</span>");
    htmlStr.push("<span class=\"select-num\"></span>");
    htmlStr.push("<i class=\"layui-icon\" style=\"float:right;\">&#xe619;</i>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    htmlStr.push("<button class=\"layui-btn layui-btn-radius data-submit\" style=\"background-color:#ef8717;font-size:16px;padding:0 30px\" onclick=\"approver.submitData()\">");
    htmlStr.push("确定&nbsp;&nbsp;");
    htmlStr.push("<span class=\"div-select-data\">1/100</span>");
    htmlStr.push("</button>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    <!--已选数据-->
    htmlStr.push("<section id=\"dataLayer\" class=\"express-data-box\">");
    htmlStr.push("<header>");
    htmlStr.push("<h3 style=\"font-weight:bold\">已选择</h3>");
    htmlStr.push("<i class=\"layui-icon close-mask\"  onclick=\"approver.closeMask()\">&#x1006;</i>");
    htmlStr.push("<span class=\"submit-mask\" onclick='approver.submitSelectedData()'>确定</span>");
    htmlStr.push("</header>");
    htmlStr.push("<article id=\"dataBox\"  style=\"overflow-y:auto\">");
    htmlStr.push("<ul id=\"approver-selectedDataList\" class=\" div-mask-list\"></ul>");
    htmlStr.push("</article>");
    htmlStr.push("</section>");
    <!--遮罩层-->
    htmlStr.push("<div id=\"mask\" class=\"mask\"></div>");
    return htmlStr.join("");
}