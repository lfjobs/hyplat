let tab = {}
const stab = function () {
    this.v = "2.4.3"

};
stab.getData = function(param){
    stab.initData(param);
    stab.getDataToGet();
}
/**
 * 初始化数据
 */
stab.initData = function(param){
    tab={
        pageNumber:[1,1,1],
        pageSize:40,
        pageCount:[],
        param:{},
        selectedData:[],
        selectTabNum:0,
        dataList:{},
        selectedID:"",
        copySelectedData:[],
    }
    tab.param = param;
    const htmlStr = stab.getHtmlStr();
    stab.openPage(htmlStr);

    if (param.selectedData != null){
        tab.selectedData = param.selectedData;
    }
}
/**
 * 查询数据
 */
stab.getDataToGet = function(){
    if (tab.pageNumber[tab.selectTabNum] == 1){
        tab.dataList[tab.selectTabNum] = [];
        $(".data-list"+tab.selectTabNum).html("");
    }
    const param = new Array();
    param.push("&&pageNumber=" + tab.pageNumber[tab.selectTabNum]);
    param.push("&&pageSize=" + tab.pageSize);
    param.push("&&queryName=" + $("#queryName").val());
    let url = basePath + tab.param.url[tab.selectTabNum] + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            const dataJson = eval("(" + data + ")");
            if (dataJson == null){
                $(".data-list"+tab.selectTabNum).html("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                const list = dataJson["list"];
                tab.pageCount[tab.selectTabNum] = dataJson["pageCount"];
                stab.getHtmlByData(list);
                tab.dataList[tab.selectTabNum] = tab.dataList[tab.selectTabNum].concat(list);
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

stab.getDataByQueryName = function(){
    tab.pageNumber[tab.selectTabNum] = 1;
    stab.getDataToGet();
}

stab.getHtmlByData = function(dataList){
    const str = new Array();
    const dataListLength = dataList.length;
    let idData = "",nameData="";
    let nameDataArr = tab.param.nameData[tab.selectTabNum];
    if (dataListLength > 0){
        for (let i = 0; i < dataList.length; i++) {
            let nameStr = "";
            idData = dataList[i][tab.param.idList[tab.selectTabNum]];
            nameData = dataList[i][tab.param.nameList[tab.selectTabNum]];
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
            dataList[i]["nameStr"] = nameData;
            tab.dataList[idData] = dataList[i];
            str.push("<li>");
            str.push("<div style='width:100%;' id='data-" + idData + "'" + " onclick=stab.spdSelectData('" + idData + "','" +nameData +"')>");
            str.push("<div style='width:18px;float:left'>");
            const selected = tab.selectedData.includes(idData);
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
            str.push("</li>");
        }
        if (tab.pageNumber[tab.selectTabNum] == 1){
            $(".data-list"+tab.selectTabNum).html(str.join(""));
        } else {
            $(".data-list"+tab.selectTabNum).append(str.join(""));
        }
    } else {
        str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
        $(".data-list"+tab.selectTabNum).html(str.join(""));
    }
    stab.showSelectedData();
}

stab.openPage = function(htmlStr){
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
            const divElement = document.querySelector('.tab-data');
            divElement.addEventListener('scroll', function() {
                if (this.scrollTop + this.clientHeight >= this.scrollHeight || 1==1) {
                    if (tab.pageNumber[tab.selectTabNum] < tab.pageCount[tab.selectTabNum]){
                        tab.pageNumber[tab.selectTabNum] += 1;
                        stab.getDataToGet();
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
    const length = tab.param.tabName.length;
    const widthTab = $(window).width() * (1/length) - 30;
    $(".layui-tab-title li").width(widthTab);
    $(".layui-tab").height($("#selectBox").height() - 20);
    $(".layui-tab-content").height($(".layui-tab").height() - $(".layui-tab-title").height() - 20);
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 60);
    $(".close-data").css("right","75px");
}

/**
 * 勾选数据
 * @param id
 */
stab.spdSelectData = function(id,name){
    if (tab.selectedID != ""){0
        if (!tab.selectedData.includes(tab.selectedID)){
            tab.selectedData.push(tab.selectedID);
        }
    }
    const selected = tab.selectedData.includes(id);
    if (selected){
        $("#data-" + id +" .div-selected").hide();
        $("#data-" + id +" .div-not-selected").show();
        $("#icon-" + id).css("color","#b98e86");
        $("#icon-" + id).css("disabled",false);
        tab.selectedData = tab.selectedData.filter(item => item !== id);
    } else {
        $("#data-" + id +" .div-selected").show();
        $("#data-" + id +" .div-not-selected").hide();
        $("#icon-" + id).css("color","#c33820");
        $("#icon-" + id).css("disabled",true);
        tab.selectedData.push(id);
    }
    stab.showSelectedData();

}


stab.returnBack = function(){
    parent.layer.close(parent.layer.index);
}

stab.showSelectedData = function(){
    $(".div-select-data").html(tab.selectedData.length + "/100");
    $(".select-num").html(tab.selectedData.length + "个（含子级）");
}
stab.viewSelectedData = function(){
    $("#mask").fadeIn();
    $("#dataLayer").animate({"bottom": 0});
    stab.getSelectedData(tab.selectedData)
    tab.copySelectedData = tab.selectedData.concat();
}

/**
 * 查看已选择的数据
 * @param data
 */
stab.getSelectedData = function(data){
    const htmlstr = new Array();
    const length = data.length;
    for (let i = 0; i < length; i++){
        let id = tab.selectedData[i];
        let data = tab.dataList[id];
        htmlstr.push("<li class='selected-" + id + "'>");
        htmlstr.push("<div style='width:80%'>");
        htmlstr.push("<label>" + data["nameStr"] + "</label>");
        htmlstr.push("</div>");
        htmlstr.push("<button class=\"layui-btn layui-btn-primary layui-btn-xs\" style='font-weight:550;height:26px'onclick=stab.deleteSelectedData('" + id + "')>移除</button>")
        htmlstr.push("<div>");
        htmlstr.push("</div>");
        htmlstr.push("</li>");
    }
    $("#selectedDataList").html(htmlstr.join(""));
}

/**
 * 删除
 * @param organizationID
 */
stab.deleteSelectedData = function(id){
    tab.copySelectedData = tab.copySelectedData.filter(item => item !== id);
    $(".selected-" + id).remove();
}

/**
 * 关闭查看已选数据
 */
stab.closeMask = function() {
    $("#mask").fadeOut();
    $("#dataLayer").animate({"bottom": "-100%"});
}

stab.submitSelectedData = function(){
    stab.closeMask();
    tab.selectedData = tab.copySelectedData.concat();
    const length = tab.param.tabName.length;
    $(".data-list"+tab.selectTabNum).html("");
    stab.getHtmlByData(tab.dataList[tab.selectTabNum]);


}

/**
 * 多选提交数据
 */
stab.submitData = function(){
    let length = tab.selectedData.length;
    let id="",name = "";
    for (let i = 0; i < length; i++){
        if (tab.selectedData[i] != ""){
            id += "," + tab.selectedData[i];
            name += "," + tab.dataList[tab.selectedData[i]]["nameStr"];
        }
    }
    if (id != ""){
        id = id.substring(1);
        name = name.substring(1);
    }
    if (tab.param.id != null){
        $("#" + tab.param.id).val(id);
        $("#" + tab.param.name).val(name);
    }
    if(tab.param.callBackBool){
        parent.callbackData(id,name);
    }
    parent.layer.close(parent.layer.index);
}

stab.selectTab = function(num){
    tab.selectTabNum = num;
    $(".queryName").val("");
    tab.pageNumber[tab.selectTabNum] = 1;
    // 是否支持跨tab选择
    if (!tab.param.selectTabBool){
        tab.selectedData = [];
        stab.showSelectedData();
    }
    stab.getDataToGet();

}
stab.clearQueryName = function(){
    $("#queryName").val("");
    tab.pageNumber[tab.selectTabNum] == 1
    stab.getDataToGet();
}

/**
 * 弹出框页面
 * @returns {string}
 */
stab.getHtmlStr = function() {
    const htmlStr = new Array();
    htmlStr.push("<header class=\"header spd-header\">");
    htmlStr.push("<ul class=\"clearfix\">");
    htmlStr.push("<li>");
    htmlStr.push("<a onclick=\"stab.returnBack()\" target=\"_self\">");
    htmlStr.push("<img src=\"http://localhost:8080/images/ea/office/contract/stamp/return.png\"/>");
    htmlStr.push("</a>");
    htmlStr.push("</li>");
    htmlStr.push("<li>");
    htmlStr.push(tab.titleName);
    htmlStr.push("</li>");
    htmlStr.push("<li></li>");
    htmlStr.push("</ul>");
    htmlStr.push("</header>");
    htmlStr.push("<div class=\"spd-content\" style='overflow:hidden'>");

    htmlStr.push("<div class=\"dtd-oa-search-bar\">");
    htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper query-data  \">");
    htmlStr.push("<i class=\"layui-icon\" >&#xe615;</i>");
    htmlStr.push("</div>");
    htmlStr.push("<input type=\"text\" class=\"dtd-oa-search-bar-input\" placeholder=\"搜索\" onchange='stab.getDataByQueryName()' name=\"queryName\" id=\"queryName\" autocomplete=“off”/>");
    htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper close-data\" onclick='stab.clearQueryName()' >");
    htmlStr.push("<i class=\"layui-icon\" >&#x1006;</i>");
    htmlStr.push("</div>");
    htmlStr.push("<button class=\"layui-btn layui-btn-primary layui-btn-sm \" onclick='stab.getDataByQueryName()' style='margin-left:10px;margin-top:-4px'>搜索</button>")
    htmlStr.push("</div>");

    htmlStr.push("<article id=\"selectBox\" style='overflow-y:hidden !important'>");
    htmlStr.push("<div class=\"layui-tab layui-tab-brief\" style='margin: 5px 0 10px 0;' lay-filter=\"docDemoTabBrief\"> ");
    htmlStr.push("<ul class=\"layui-tab-title\" style='height:30px'>");
    htmlStr.push("<li class=\"layui-this\" onclick='stab.selectTab(0)' style='line-height:30px !important'>");
    htmlStr.push(tab.param.tabName[0]);
    htmlStr.push("</li>");
    const length = tab.param.tabName.length;
    for (let i = 1; i < length; i++){
        htmlStr.push("<li onclick='stab.selectTab(" + i + ")'  style='line-height:30px !important'>");
        htmlStr.push(tab.param.tabName[i]);
        htmlStr.push("</li>");
    }
    htmlStr.push("</ul>");
    htmlStr.push("<div class=\"layui-tab-content tab-data\" style='overflow-y:auto'>");
    htmlStr.push("<div class='layui-tab-item tab-item0 layui-show'>")
    htmlStr.push("<ul id=\"dataList\" class=\"data-list0 div-mask-list\">");
    htmlStr.push("</ul>");
    htmlStr.push("</div>");
    for (let i = 1; i < length; i++){
        htmlStr.push("<div class=\"layui-tab-item tab-item"+ i + "\">")
        htmlStr.push("<ul id=\"dataList\" class=\"data-list"+ i + " div-mask-list\">");
        htmlStr.push("</ul>");
        htmlStr.push("</div>");
    }
    htmlStr.push("</div>")
    htmlStr.push("</div>");
    htmlStr.push("</article>");
    htmlStr.push("<div class=\"div-bottom\" >");
    htmlStr.push("<div class=\"bottom-selected-data\" onclick='stab.viewSelectedData()'>");
    htmlStr.push("<div style=\"padding:10px\">");
    htmlStr.push("<span>已选择：</span>");
    htmlStr.push("<span class=\"select-num\"></span>");
    htmlStr.push("<i class=\"layui-icon\" style=\"float:right;\">&#xe619;</i>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    htmlStr.push("<button class=\"layui-btn layui-btn-radius data-submit\" style=\"background-color:#ef8717;font-size:16px;padding:0 30px\" onclick=\"stab.submitData()\">");
    htmlStr.push("确定&nbsp;&nbsp;");
    htmlStr.push("<span class=\"div-select-data\">1/100</span>");
    htmlStr.push("</button>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    <!--已选数据-->
    htmlStr.push("<section id=\"dataLayer\" class=\"express-data-box\">");
    htmlStr.push("<header>");
    htmlStr.push("<h3 style=\"font-weight:bold\">已选择</h3>");
    htmlStr.push("<i class=\"layui-icon close-mask\"  onclick=\"stab.closeMask()\">&#x1006;</i>");
    htmlStr.push("<span class=\"submit-mask\" onclick='stab.submitSelectedData()'>确定</span>");
    htmlStr.push("</header>");
    htmlStr.push("<article id=\"dataBox\"  style=\"overflow-y:auto\">");
    htmlStr.push("<ul id=\"selectedDataList\" class=\" div-mask-list\"></ul>");
    htmlStr.push("</article>");
    htmlStr.push("</section>");
    <!--遮罩层-->
    htmlStr.push("<div id=\"mask\" class=\"mask\"></div>");
    return htmlStr.join("");
}