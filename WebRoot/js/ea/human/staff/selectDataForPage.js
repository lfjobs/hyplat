let sd = {}
const sdfp = function () {
    this.v = "2.4.3"

};
sdfp.getData = function(data,param){
    sdfp.initData(param);
    sdfp.getDataOpen(data,param);

}

sdfp.getDataByPage = function(urlStr,param){
    sdfp.initData(param);
    sd.pageNumber = 1;
    sd.pageCount = 0;
    if (sd.pageSize == null){
        sd.pageSize = 5;
    }
    if (sd.clickBool){
        return false;
    }
    sdfp.getDataPageByUrl(urlStr,param)
}


/**
 * 根据URL查询分页数据
 * @param urlStr
 * @param param
 */
sdfp.getDataPageByUrl = function(urlStr,param){
    sd.url = urlStr;
    const params = new Array();
    params.push("&&pageNumber=" + sd.pageNumber);
    params.push("&&pageSize=" + sd.pageSize);
    sd.paramData = param;
    $.ajax({
        url: encodeURI(sd.url + params.join("") ),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data == null){
                layer.msg("暂无数据");
            } else {
                const dataList = eval("(" + data + ")");
                sd.pageCount = dataList["pageCount"];
                sdfp.getDataOpen(dataList.list,sd.paramData);
                $(".pageCount").html(sd.pageCount);
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
sdfp.initData = function(param){
    let valueName;
    sd = {
        idStr:"",
        nameStr:"",
        paramData:{},
        selectedData:[],
        type:"",
        dataList:{},
        callBackBool:false,
        pageNumber:1,
        pageSize:5,
        pageCount:0,
        url:"",
        clickBool:false,

    }
    if (param.inputId != null){
        valueName = param.inputId;
    } else {
        valueName = param.id;
    }
    let value = $("#" + valueName).val();
    if (value != null){
        const valueArr = value.split(",");
        const length = valueArr.length;
        for (let i = 0; i < length; i++){
            sd.selectedData.push(valueArr[i]);
        }
    }


}

/**
 * 整理数据并弹框
 * @param data
 * @param param
 * @returns {boolean}
 */
sdfp.getDataOpen = function(data,param) {
    let listHtml = $(".div-sdfp-data").html();
    if (listHtml !== "" && listHtml !== undefined){
        $(".div-sdfp-data").html(sdfp.getDataListHtml(data));
        sd.clickBool = true;
        return false;
    }
    if (!sd.clickBool) {
        sd.clickBool = true;
    } else {
        return false;
    }
    const htmlStr = new Array();
    const titleName = param.titleName;
    sd.idStr = param.id;
    sd.nameStr = param.name;
    sd.paramData = param;
    let width = 0;
    const titleList = param.titleList;
    sd.fieldList = param.fieldList;
    sd.titleWidth = param.titleWidth;
    if (param.selectedData != null) {
        sd.selectedData = param.selectedData
    }
    if (param.callBackBool != null) {
        sd.callBackBool = param.callBackBool;
    }

    sd.titleLength = titleList.length;
    htmlStr.push("<div class=\"sdfp-data-title\">");
    htmlStr.push("<ul>");
    for (let i = 0; i < sd.titleLength; i++){
        htmlStr.push("<li style='width:" + sd.titleWidth[i] + "px'>" + titleList[i] + "</li>");
        width += parseInt(sd.titleWidth[i]);
    }
    htmlStr.push("</ul>");
    htmlStr.push("</div>");
    htmlStr.push(" <div class=\"sdfp-data-list div-sdfp-data\" style=\"overflow-y:auto;overflow-x:hidden\">");
    htmlStr.push(sdfp.getDataListHtml(data));
    htmlStr.push("</div>");
    const html = sdfp.getHtmlStr(titleName, htmlStr.join(""));
    sdfp.openPage(html,width)
}

sdfp.getDataListHtml = function(data){
    const htmlStr = new Array();
    const length = data.length;
    for (let j = 0; j < length; j++) {
        const idData = data[j][sd.idStr];
        htmlStr.push("<ul onClick='sdfp.selectDataEject(\"" + idData + "\",\"" + data[j][sd.nameStr] + "\");' id='select-" + idData + "'>" )
        for (let i = 0; i < sd.titleLength; i++){
            htmlStr.push("<li style='width:" + sd.titleWidth[i] + "px'>" + data[j][sd.fieldList[i]] + "</li>");
        }
        htmlStr.push("</ul>");
    }
    return htmlStr.join("");
}
// 打开页面
sdfp.openPage = function(htmlStr,width){
    const area = ['100%', '100%'];
    if (sd.paramData.width != null){
        area[0] = param.width;
    }
    if (sd.paramData.height != null){
        area[1] = param.height;
    }
    sd.index = layer.open({
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

    });
    $(".sdfp-content").height($(window).height() - $(".spd-header").height());
    let height = $(".sdfp-content").height();
    if (sd.queryBool){
        height = height - $(".dtd-oa-search-bar").height();
    }

    if ($("#sdfp-selectBox").width() < width){
        $(".sdfp-data").width(width);
    }
    $("#sdfp-selectBox").height(height- $(".sdfp-page").height());
    $(".sdfp-data").height($("#sdfp-selectBox").height() );
    $(".sdfp-data-list").height($(".sdfp-data").height() - $(".sdfp-data-title").height() - 30);
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 60);
    $(".close-data").css("right","75px");
    var leftWidth = ($("#sdfp-selectBox").width() -  60 - $(".sdfp-page-center").width() )/2
    $(".sdfp-page-center").css({"margin-left":leftWidth + "px"});
    layui.use('form', function() {
        form = layui.form;
        form.render('select');
        form.render('radio');
        form.on('select(sdfp-pageSelect)', function(data){
            sd.pageNumber = 1;
            sd.clickBool = false;
            sd.pageSize = data.value;
            sdfp.getDataPageByUrl(sd.url,sd.paramData)
        });

    })

}
sdfp.getPageNum = function(type){
    if (type === "1"){
        if (sd.clickBool && sd.pageNumber > 1){
            sd.clickBool = false;
            sd.pageNumber--;
            $(".pageNumber").html(sd.pageNumber)
            sdfp.getDataPageByUrl(sd.url,sd.paramData)
        }
    } else {
        if (sd.clickBool && sd.pageNumber < sd.pageCount){
            sd.clickBool = false;
            sd.pageNumber++;
            $(".pageNumber").html(sd.pageNumber)
            sdfp.getDataPageByUrl(sd.url,sd.paramData)

        }
    }

}
/**
 * 弹出框页面
 * @param titleName
 * @returns {any[]}
 */
sdfp.getHtmlStr = function(titleName,html) {
    const htmlStr = new Array();
    htmlStr.push("<header class=\"header spd-header\">");
    htmlStr.push("<ul class=\"clearfix\">");
    htmlStr.push("<li>");
    htmlStr.push("<a onclick=\"sdfp.closeData()\" target=\"_self\">");
    htmlStr.push("<img src=\"http://localhost:8080/images/ea/office/contract/stamp/return.png\"/>");
    htmlStr.push("</a>");
    htmlStr.push("</li>");
    htmlStr.push("<li>");
    htmlStr.push(titleName);
    htmlStr.push("</li>");
    htmlStr.push("<li></li>");
    htmlStr.push("</ul>");
    htmlStr.push("</header>");
    htmlStr.push("<div class='sdfp-content layui-form' style='overflow:hidden'>");
    if (sd.queryBool) {
        htmlStr.push("<div class=\"dtd-oa-search-bar\">");
        htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper query-data  \">");
        htmlStr.push("<i class=\"layui-icon\" >&#xe615;</i>");
        htmlStr.push("</div>");
        htmlStr.push("<input type=\"text\" class=\"dtd-oa-search-bar-input\" placeholder=\"搜索\" onchange='sdfp.getDataByQueryName()' name=\"queryName\" id=\"queryName\" autocomplete=“off”/>");
        htmlStr.push("<div class=\"dtd-oa-search-bar-icon-wrapper close-data\" onclick='sdfp.clearQueryName()' >");
        htmlStr.push("<i class=\"layui-icon\" >&#x1006;</i>");
        htmlStr.push("</div>");
        htmlStr.push("<button class=\"layui-btn layui-btn-primary layui-btn-sm \" onclick='sdfp.getDataByQueryName()' style='margin-left:10px;margin-top:-4px'>搜索</button>")
        htmlStr.push("</div>");
    }
    htmlStr.push("<article id=\"sdfp-selectBox\" >");
    htmlStr.push("<div class=\"sdfp-data\">");
    htmlStr.push(html);
    htmlStr.push("</div>");
    htmlStr.push("</article>");
    htmlStr.push("<div class=\" sdfp-set sdfp-page\" style=\"height:70px;float:left;width:100%\">");
    htmlStr.push("<div style=\"width:30px;height:100%;float:left;padding: 15px 0px;\" onclick='sdfp.getPageNum(1)'>");
    htmlStr.push("<i class=\"layui-icon\" style=\"font-size:25px;color:#f74c32\">&#xe65a;</i>");
    htmlStr.push("</div>");
    htmlStr.push("<div style=\"width:160px;height:100%;float:left;margin-left:40px;padding:10px 0px\" class=\"sdfp-page-center\">");
    htmlStr.push("<div style=\"width:60px;height:100%;float:left;font-size:20px;margin-top:5px\">");
    htmlStr.push("<label class=\"pageNumber\">1</label>/<label class=\"pageCount\">100</label>");
    htmlStr.push("</div>");
    htmlStr.push(" <div style=\"width:90px;height:100%;float:left;margin-left:10px\">");
    htmlStr.push("<select name=\"page\" lay-filter=\"sdfp-pageSelect\">");
    htmlStr.push(" <option value=\"5\">5条/页</option>");
    htmlStr.push(" <option value=\"10\">10条/页</option>");
    htmlStr.push("<option value=\"15\">15条/页</option>");
    htmlStr.push("<option value=\"20\">20条/页</option>");
    htmlStr.push("</select>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");
    htmlStr.push("<div style=\"width:30px;height:100%;float:right;padding: 15px 0px;\" onclick='sdfp.getPageNum(2)'>");
    htmlStr.push("<i class=\"layui-icon\" style=\"font-size:25px;color:#f74c32\">&#xe65b;</i>");
    htmlStr.push("</div>");
    htmlStr.push("</div>");

    htmlStr.push("</div>");
    return htmlStr.join("");


}

/**
 * 选择数据
 * @param id
 * @param name
 */
sdfp.selectDataEject = function(id,name){
    sdfp.closeData();

    if (sd.paramData.inputId != null){
        $("#" + sd.paramData.inputId).val(id);
    } else {
        $("#" + sd.idStr).val(id);
    }
    if (sd.paramData.inputName != null){
        $("#" + sd.paramData.inputName).val(name);
    } else {
        $("#" + sd.nameStr).val(name);
    }

    if (sd.callBackBool){
        parent.callbackData(id,name);
    }


}

/**
 * 管理弹框
 */
sdfp.closeData = function () {
    parent.layer.close(sd.index);
    sd.clickBool = false;
}