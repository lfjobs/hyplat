


let ject = {}
const spdject = function () {
    this.v = "2.4.3"

};
spdject.getData = function(data,param){
    spdject.initData(param);
    spdject.getDataOpen(data,param);

}
/**
 * 根据URL查询分页数据
 * @param urlStr
 * @param param
 */
spdject.getDataPageByUrl = function(urlStr,param){
    spdject.initData(param);
    ject.pageNumber = 1, ject.pageSize = 20,ject.pageCount = 0;
    ject.pagingBool = true;
    ject.url = urlStr;
    ject.paramData = param;
    spdject.getDataToGet();
}
/**
 * 根据URL查询数据(不分页)
 * @param urlStr
 * @param param
 */
spdject.getDataByUrl = function(urlStr,param){
    spdject.initData(param);
    ject.pagingBool = false;
    ject.url = urlStr;
    ject.paramData = param;
    spdject.getDataToGet();
}


spdject.getDataToGet = function(){
    const params = new Array();
    if (ject.pagingBool){
        params.push("&&pageNumber=" + ject.pageNumber);
        params.push("&&pageSize=" + ject.pageSize);
    }
    if (ject.childBool){
        if (ject.selectChildBool){
            params.push("&&" + ject.paramData["pidField"] + "=" + ject.selectedID);
        } else {
            params.push("&&" + ject.paramData["pidField"] + "=" + ject.paramData["pid"]);
        }

    }
    params.push("&&queryName=" + $("#queryName").val());
    $.ajax({
        url: encodeURI(ject.url + params.join("") ),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            const dataList = eval("(" + data + ")");
            if (dataList == null){
                $("#dataList").html("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                if (ject.pagingBool){
                    ject.pageCount = dataList["pageCount"];
                    if (dataList.list.length > 0){
                        spdject.getDataOpen(dataList.list,ject.paramData);
                    } else {
                        if (ject.selectChildBool){
                            layer.msg("无下级选项");
                        }
                    }
                } else{
                    if (dataList.length > 0){
                        spdject.getDataOpen(dataList,ject.paramData);
                    } else{
                        if (ject.selectChildBool){
                            layer.msg("无下级选项");
                        }
                    }
                }

            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
/**
 * 初始化数据
 * @param param
 */
spdject.initData = function(param){
    ject={
        idStr:"",
        nameStr:"",
        paramData:{},
        selectedData:[],
        type:"",
        dataList:{},
        pageNumber:1,
        pageSize:20,
        pageCount:0,
        // childBool是否有下级
        childBool:false,
        // queryBool 页面搜索功能
        queryBool:false,
        // 是否点击下级按钮
        selectChildBool:false,
        // pagingBool是否分页
        pagingBool:false,
        // callBackBool是否返回方法
        callBackBool:false,
        // 选择的id
        selectedID:"",
        // 选择名称
        selectedName:"",
        // 父级id列表（用于选择下级）
        parentIdList:[""],
        // copySelectedData用于查看选择的数据（用于移除）
        copySelectedData:[],
        // 标题
        titleName :""
    }
    let valueName;
    if (param.inputId != null){
        valueName = param.inputId;
    } else {
        valueName = param.id;
    }
    let value = $("#" + valueName).val();
    if (value != null && value !== ""){
        const valueArr = value.split(",");
        const length = valueArr.length;
        for (let i = 0; i < length; i++){
            ject.selectedData.push(valueArr[i]);
        }
    }
    ject.idStr = param.id;
    ject.nameStr = param.name;
    ject.paramData = param
    if (param.type != null){
        ject.type = param.type
    }
    if (param.selectedData != null){
        ject.selectedData = param.selectedData;
    }
    ject.titleName = param.titleName;
    if (param.pidField != null){
        ject.childBool = true;
    }
    if (param.queryBool != null){
        ject.queryBool = param.queryBool;
    }
    if (param.callBackBool != null){
        ject.callBackBool = param.callBackBool;
    }
    const htmlStr = spdject.getHtmlStr();
    spdject.openPage(htmlStr);
}

/**
 * 整理数据并弹框
 * @param data
 * @param param
 * @returns {boolean}
 */
spdject.getDataOpen = function(data,param){
    const htmlLi = new Array();
    const length = data.length;
    if(ject.selectChildBool){
        ject.parentIdList.push(ject.selectedID);
    }
    spdject.fillNameData();
    let nameDataArr = param.nameData;
    for (let i = 0; i < length; i++){
        const idData = data[i][ject.idStr];
        let nameData = "";
        if (nameDataArr != null){
            let length = nameDataArr.length;
            for (let j = 0; j < length; j++){
                let name = data[i][nameDataArr[j]];
                if (name != null && name != undefined){
                    nameData += name;
                } else {
                    nameData += nameDataArr[j];
                }
            }
        } else {
            nameData = data[i][ject.nameStr];
        }
        htmlLi.push("<li "+ " onclick=spdject.spdSelectData('" + idData + "','" + data[i][ject.nameStr] +  "')>");
        if (ject.childBool){
            htmlLi.push("<div style='width:80%;padding-left:10px' id='data-" + idData + "')>");
        } else{
            htmlLi.push("<div style='padding-left:10px' id='data-" + idData + "'>");
        }
        if ("checkbox" == ject.type){
            htmlLi.push("<div style='width:18px;float:left'>");
            const selected = ject.selectedData.includes(idData);
            if (selected){
                htmlLi.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:none' >&#xe63f;</i> ")
                htmlLi.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:block' >&#x1005;</i> ")
            } else {
                htmlLi.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:block' >&#xe63f;</i> ")
                htmlLi.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:none' >&#x1005;</i> ")
            }
            htmlLi.push("</div>");
        }
        htmlLi.push("<label style='float:left;margin-left:20px'>" + nameData + "</label></div>");
        if (ject.childBool){
            htmlLi.push("<div class='selected-child'  id='icon-" + idData + "'" + "onclick=spdject.getChildDataList('" + idData + "','" + data[i][ject.nameStr] + "')>");
            htmlLi.push("<i class=\"layui-icon\" >&#xe649;</i>");
            htmlLi.push("<label class='label-child-name'>下级</label>");
            htmlLi.push("</div>");
        }
        htmlLi.push("</li>");
        ject.dataList[idData] = data[i];
    }
    if (ject.pageNumber > 1){
        $("#dataList").append(htmlLi.join(""));
    } else {
        $("#dataList").html(htmlLi.join(""));
    }

    $(".div-select-data").html(ject.selectedData.length + "/100");
    if (ject.childBool){
        $(".select-num").html(ject.selectedData.length + "个（含子级）");
    } else {
        $(".select-num").html(ject.selectedData.length + "个");
    }
}

// 打开页面
spdject.openPage = function(htmlStr){
    const area = ['100%', '100%'];
    if (ject.paramData.width != null){
        area[0] = ject.paramData.width;
    }
    if (ject.paramData.height != null){
        area[1] = ject.paramData.height;
    }
    layer.open({
        type: 1,
        title:false,
        closeBtn:false,
        anim: 5,
        isOutAnim: false,
        shadeClose: true,
        content: htmlStr,
        offset: 'lb',
        area: area,
        scrollbar: true,
        success: function(layero){
            const divElementList = layero.find('#selectBox');
            divElementList.on('scroll', function() {
                if (this.scrollTop + this.clientHeight >= this.scrollHeight || 1==1) {
                    if (ject.pageNumber < ject.pageCount){
                        ject.pageNumber++;
                        spdject.getDataToGet();
                    }

                }
            })
        }

    });
    $(".spd-content").height($(window).height() - $(".spd-header").height());
    let height = $(".spd-content").height();
    if (ject.queryBool){
        height = height - $(".dtd-oa-search-bar").height();
    }
    if (ject.childBool){
        height = height - $(".select-spd-data").height() - 20;
    }
    if ("checkbox" == ject.type){
        height = height - $(".div-bottom").height()
    }
    $("#selectBox").height(height - 10);
    $(".bottom-selected-data").width( $(".content").width() - $(".data-submit").width());
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 60);
    $(".close-data").css("right","75px");

}
/**
 * 勾选数据
 * @param id
 */
spdject.spdSelectData = function(id,name){
    if ("checkbox" == ject.type){
        if (ject.selectedID != ""){
            const selectedPId = ject.selectedData.includes(ject.selectedID);
            if (!selectedPId){
                ject.selectedData.push(ject.selectedID);
            }
        }
        const selected = ject.selectedData.includes(id);
        if (selected){
            $("#data-" + id +" .div-selected").hide();
            $("#data-" + id +" .div-not-selected").show();
            ject.selectedData = ject.selectedData.filter(item => item !== id);
        } else {
            $("#data-" + id +" .div-selected").show();
            $("#data-" + id +" .div-not-selected").hide();
            ject.selectedData.push(id);
        }
        $(".div-select-data").html(ject.selectedData.length + "/100");
        if (ject.childBool){
            $(".select-num").html(ject.selectedData.length + "个（含子级）");
        } else {
            $(".select-num").html(ject.selectedData.length + "个");
        }
    } else {
        let length = ject.parentIdList.length;
        let dataId="",dataName = "";
        for (let i = 0; i < length; i++){
            if (ject.parentIdList[i] != ""){
                dataId += "," + ject.parentIdList[i];
                dataName += "-" + ject.dataList[ject.parentIdList[i]][ject.paramData.name];
            }
        }

        dataId += "," + id;
        dataName += "-" + name;
        if (dataId != ""){
            dataId = dataId.substring(1);
            dataName = dataName.substring(1);
        }
        if (ject.paramData.inputId != null){
            $("#" + ject.paramData.inputId).val(dataId);
        } else {
            $("#" + ject.idStr).val(dataId);
        }
        if (ject.paramData.inputName != null){
            $("#" + ject.paramData.inputName).val(dataName);
        } else {
            $("#" + ject.nameStr).val(dataName);
        }
        parent.layer.close(parent.layer.index);
        if(ject.callBackBool){

            parent.callbackData(id,name,ject.dataList[id]);
        }

    }

}
/**
 * 选择下级
 * @param id
 * @param name
 */
spdject.getChildDataList = function(id,name){
    ject.selectChildBool = true;
    ject.selectedID = id;
    ject.selectedName = name;
    spdject.getDataToGet();


}
/**
 * 填充路径数据
 */
spdject.fillNameData = function(){
    if (ject.selectChildBool){
        if (ject.parentIdList.length > 2){
            $(".spd-name").html($(".spd-name").html() + ">" + ject.selectedName);
        } else {
            $(".spd-name").html($(".spd-name").html() + ject.selectedName);
        }
        if (ject.selectedID != ""){
            if (!ject.parentIdList.includes(ject.selectedID)){
                ject.parentIdList.push(spdject.selectedID);
            }
        }
    } else {
        if (ject.parentIdList.length > 2){
            const position = $(".spd-name").html().lastIndexOf("&gt;");
            $(".spd-name").html($(".spd-name").html().substring(0,position));
        } else {
            $(".spd-name").html("");
        }
        if (ject.selectedID != ""){
            ject.parentIdList.pop();
        }

    }
}
spdject.clearQueryName = function(){
    $("#queryName").val("");
    ject.pageNumber = 1;
    spdject.getDataToGet();
}
spdject.getDataByQueryName = function(){
    ject.pageNumber = 1;
    spdject.getDataToGet();
}
/**
 * 弹出框页面
 * @param titleName
 * @returns {string}
 */
spdject.getHtmlStr = function() {
    const htmlStr = new Array();
    htmlStr.push("<header class=\"header spd-header\">");
    htmlStr.push("<ul class=\"clearfix\">");
    htmlStr.push("<li>");
    htmlStr.push("<a onclick=\"spdject.returnBack()\" target=\"_self\">");
    htmlStr.push("<img src=" + basePath + "images/ea/office/contract/stamp/return.png>");

    htmlStr.push("</a>");
    htmlStr.push("</li>");
    htmlStr.push("<li>");
    htmlStr.push(ject.titleName);
    htmlStr.push("</li>");
    htmlStr.push("<li></li>");
    htmlStr.push("</ul>");
    htmlStr.push("</header>");
    htmlStr.push("<div class=\"spd-content\" style='overflow:hidden'>");
    if (ject.queryBool) {
        htmlStr.push("<div class=\"dtd-oa-search-bar\">");
        htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper query-data  \">");
        htmlStr.push("<i class=\"layui-icon\" >&#xe615;</i>");
        htmlStr.push("</div>");
        htmlStr.push("<input type=\"text\" class=\"dtd-oa-search-bar-input\" placeholder=\"搜索\" onchange='spdject.getDataByQueryName()' name=\"queryName\" id=\"queryName\" autocomplete=“off”/>");
        htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper close-data\" onclick='spdject.clearQueryName()' >");
        htmlStr.push("<i class=\"layui-icon\" >&#x1006;</i>");
        htmlStr.push("</div>");
        htmlStr.push("<button class=\"layui-btn layui-btn-primary layui-btn-sm \" onclick='spdject.getDataByQueryName()' style='margin-left:10px;margin-top:-4px'>搜索</button>")
        htmlStr.push("</div>");
    }
    if (ject.childBool) {
        htmlStr.push(" <div class=\"select-spd-data\"><label class=\"div-spd\">路径：</label><label class=\"spd-name\"></label>");
        htmlStr.push("</div>");
    }
    htmlStr.push("<article id=\"selectBox\">");
    htmlStr.push("<ul id=\"dataList\" class=\"data-list div-mask-list\">");
    htmlStr.push("</ul>");
    htmlStr.push("</article>");
    if ("checkbox" == ject.type) {
        htmlStr.push("<div class=\"div-bottom\" >");
        htmlStr.push("<div class=\"bottom-selected-data\">");
        htmlStr.push("<div style=\"padding:10px\">");
        htmlStr.push("<span>已选择：</span>");
        htmlStr.push("<span class=\"select-num\"></span>");
        htmlStr.push("<i class=\"layui-icon\" style=\"float:right;\">&#xe619;</i>");
        htmlStr.push("</div>");
        htmlStr.push("</div>");
        htmlStr.push("<button class=\"layui-btn layui-btn-radius data-submit\" style=\"background-color:#ef8717;font-size:16px;padding:0 30px\" onclick=\"spdject.submitData()\">");
        htmlStr.push("确定&nbsp;&nbsp;");
        htmlStr.push("<span class=\"div-select-data\">1/100</span>");
        htmlStr.push("</button>");
        htmlStr.push("</div>");
    }
    htmlStr.push("</div>");
    <!--已选数据-->
    htmlStr.push("<section id=\"dataLayer\" class=\"express-data-box\">");
    htmlStr.push("<header>");
    htmlStr.push("<h3 style=\"font-weight:bold\">已选择</h3>");
    htmlStr.push("<i class=\"layui-icon close-mask\"  onclick=\"closeMask()\">&#x1006;</i>");
    htmlStr.push("<span class=\"submit-mask\">确定</span>");
    htmlStr.push("</header>");
    htmlStr.push("<article id=\"dataBox\"  style=\"overflow-y:auto\">");
    htmlStr.push("<ul id=\"selectedDataList\" class=\" div-mask-list\"></ul>");
    htmlStr.push("</article>");
    htmlStr.push("</section>");
    <!--遮罩层-->
    htmlStr.push("<div id=\"mask\" class=\"mask\"></div>");
    return htmlStr.join("");
}
/**
 * 返回选择菜单
 */
spdject.returnBack = function () {
    ject.selectChildBool = false;
    ject.parentIdList.pop();
    const length = ject.parentIdList.length;
    if (length > 0){
        ject.selectChildBool = false;
        ject.selectedID = ject.parentIdList[length - 1];
        ject.selectedName = "";
        spdject.getDataToGet();
    } else {
        parent.layer.close(parent.layer.index);

    }
}

/**
 * 多选提交数据
 */
spdject.submitData = function(){
    let length = ject.selectedData.length;
    let id="",name="",nameData = "";
    let nameDataArr = ject.paramData.nameData;
    for (let i = 0; i < length; i++){
        name = "";
        if (ject.selectedData[i] !== ""){
            id += "," + ject.selectedData[i];
            if (nameDataArr != null){
                nameData += "," + name;
                let length = nameDataArr.length;
                for (let j = 0; j < length; j++){
                    let nameStr = ject.dataList[ject.selectedData[i]][nameDataArr[j]];
                    if (nameStr !== null && nameStr !== undefined){
                        nameData += nameStr;
                    } else {
                        nameData += nameDataArr[j];
                    }
                }

            } else {
                nameData += "," + name;
            }

        }
    }
    if (id != ""){
        id = id.substring(1);
        name = nameData.substring(1);
    }
    if (ject.paramData.inputId != null){
        $("#" + ject.paramData.inputId).val(id);
    } else {
        $("#" + ject.idStr).val(id);
    }
    if (ject.paramData.inputName != null){
        $("#" + ject.paramData.inputName).val(name);
    } else {
        $("#" + ject.nameStr).val(name);
    }
    if (!ject.paramData.closePage){
        parent.layer.close(parent.layer.index);
    }
    if(ject.callBackBool){
        parent.callbackData(id,name);
    }
}