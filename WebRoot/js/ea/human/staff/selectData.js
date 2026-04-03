let t = {}
const eject = function () {
    this.v = "2.4.3"

};
eject.getData = function(data,param){
    eject.initData(param);
    eject.getDataOpen(data,param);

}

eject.getDataByPage = function(urlStr,param){
    eject.initData(param);
    t.pageNumber = 1, t.pageSize = 20, t.pageCount = 0;
    if (t.clickBool){
        return false;
    }
    eject.getDataPageByUrl(urlStr,param)
}

eject.getDataByUrl = function(urlStr,param){
    eject.initData(param);
    t.url = urlStr;
    t.paramData = param;
    $.ajax({
        url: encodeURI(t.url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data == null){
                $("#ejectList").html("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                const dataList = eval("(" + data + ")");
                if (dataList.length > 0){
                    eject.getDataOpen(dataList,t.paramData);
                }
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
eject.getDataBySqlUrl = function(urlStr,param){
    eject.initData(param);
    t.url = urlStr;
    t.paramData = param;
    $.ajax({
        url: encodeURI(t.url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data == null){
                $("#ejectList").html("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                const dataList = eval("(" + data + ")");
                const list = dataList.list;
                if (list.length > 0){
                    eject.getDataOpen(list,t.paramData);
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
eject.initData = function(param){
    let valueName;
    t = {
        idStr:"",
        nameStr:"",
        paramData:{},
        selectedData:[],
        type:"",
        dataList:{},
        callBackBool:false,
        pageNumber:1,
        pageSize:20,
        pageCount:0,
        url:"",
        clickBool:false
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
            t.selectedData.push(valueArr[i]);
        }
    }

}

/**
 * 整理数据并弹框
 * @param data
 * @param param
 * @returns {boolean}
 */
eject.getDataOpen = function(data,param){
    if (!t.clickBool){
        t.clickBool = true;
    } else {
        return false;
    }
    const htmlLi = new Array();
    const length = data.length;
    const titleName = param.titleName;
    t.idStr = param.id;
    t.nameStr = param.name;
    t.paramData = param
    if (param.type != null){
        t.type = param.type
    }
    if (param.selectedData != null){
        t.selectedData = param.selectedData
    }
    if (param.callBackBool != null){
        t.callBackBool = param.callBackBool;
    }
    for (let i = 0; i < length; i++){
        const idData = data[i][t.idStr];
        let nameData = data[i][t.nameStr];
        if (param.leftName != null){
            nameData = param.leftName + nameData;
        }
        if (param.rightName != null){
            nameData += param.rightName;
        }
        var textLeft = "35%";
        if (param.textLeft != null){
            textLeft = param.textLeft;
        }
        if ("checkbox" == t.type){
            htmlLi.push("<li onClick='eject.selectDataEject(\"" + idData + "\",\"" + data[i][t.nameStr] + "\");' id='select-" + idData + "'>" )
            htmlLi.push("<div style='width:100%'><div style='width:18px;height:23px;float:left;margin-left:" + textLeft + "'>");
            const selected = t.selectedData.includes(idData);
            if (selected){
                htmlLi.push("<i class='layui-icon div-selected' style='color:#cf5e5e;font-weight:bold'; display:block' >&#xe605;</i>");
            } else {
                htmlLi.push("<i class='layui-icon div-selected' style='color:#cf5e5e;font-weight:bold;display:none'' >&#xe605;</i>");
            }
            htmlLi.push("</div>");
            htmlLi.push("<label style='float:left;margin-left:10px'>" + nameData + "</label></div>");
            htmlLi.push("</li>")
        } else {
            htmlLi.push("<li onClick='eject.selectDataEject(\"" + idData + "\",\"" + data[i][t.nameStr] + "\");'>" + nameData + "</li>")
        }
        t.dataList[idData] = nameData;
    }
    if (t.pageNumber > 1){
        $("#ejectList").append(htmlLi.join(""));
    } else {
        const htmlStr = eject.getHtmlStr(titleName, htmlLi.join(""));
        const area = ['100%', '60%'];
        if (param.width != null){
            area[0] = param.width;
        }
        if (param.height != null){
            area[1] = param.height;
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
                const divElementList = layero.find('.eject-list');
                divElementList.on('scroll', function() {
                    if (this.scrollTop + this.clientHeight >= this.scrollHeight || 1==1) {
                        if (t.pageNumber < t.pageCount){
                            t.pageNumber++;
                            t.clickBool = false;
                            eject.getDataPageByUrl(t.url,t.paramData);
                        }

                    }
                })
            }

        });
        $("#ejectBox").height($(".express-eject-box").height() - $(".express-eject-box header").height())
        $("#ejectList").height($("#ejectBox").height());
    }
}

/**
 * 根据URL查询分页数据
 * @param urlStr
 * @param param
 */
eject.getDataPageByUrl = function(urlStr,param){
    t.url = urlStr;
    const params = new Array();
    params.push("&&pageNumber=" + t.pageNumber);
    params.push("&&pageSize=" + t.pageSize);
    t.paramData = param;
    $.ajax({
        url: encodeURI(t.url + params.join("") ),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data == null){
                layer.msg("暂无数据");
            } else {
                const dataList = eval("(" + data + ")");
                t.pageCount = dataList["pageCount"];
                eject.getDataOpen(dataList.list,t.paramData);
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
/**
 * 弹出框页面
 * @param titleName
 * @returns {any[]}
 */
eject.getHtmlStr = function(titleName,htmlLi){
    const htmlStr = new Array();
    htmlStr.push("<section id='ejectData' class='express-eject-box'>");
    htmlStr.push("<header>")
    if ("checkbox" == t.type){
        htmlStr.push(titleName);
        htmlStr.push("<span class='close select-btn' onclick='eject.closeData()' >取消</span>")
        htmlStr.push("<span class='submit select-btn' style='color:#cf5e5e;' onclick='eject.submitData()' >确认</span>");
    } else {
        htmlStr.push(titleName);
        htmlStr.push("<i class='layui-icon icon-close' onclick='eject.closeData()' >&#x1006;</i>");
    }

    htmlStr.push("</header>");
    htmlStr.push("<article id=\"ejectBox\">");
    htmlStr.push("<ul id=\"ejectList\" class=\"eject-list\">");
    htmlStr.push(htmlLi);
    htmlStr.push("</ul>");
    htmlStr.push("</article>");
    htmlStr.push("</section>")
    return htmlStr.join("");
}

/**
 * 选择数据
 * @param id
 * @param name
 */
eject.selectDataEject = function(id,name){
    if (t.type == "checkbox"){
        const selected = t.selectedData.includes(id);
        if (selected){
            $("#select-" + id +" .div-selected").hide();
            t.selectedData = t.selectedData.filter(item => item !== id);
        } else {
            $("#select-" + id +" .div-selected").show();
            t.selectedData.push(id);
        }
    } else {
        eject.closeData();
        let nameData = name;
        if (t.paramData.leftName != null){
            nameData = t.paramData.leftName + nameData;
        }
        if (t.paramData.rightName != null){
            nameData += t.paramData.rightName;
        }
        if (t.paramData.inputId != null){
            $("#" + t.paramData.inputId).val(id);
        } else {
            $("#" + t.idStr).val(id);
        }
        if (t.paramData.inputName != null){
            $("#" + t.paramData.inputName).val(nameData);
        } else {
            $("#" + t.nameStr).val(nameData);
        }

        if (t.callBackBool){
            parent.callbackData(id,name);
        }
    }


}

/**
 * 管理弹框
 */
eject.closeData = function(){
    parent.layer.close(parent.layer.index);
    t.clickBool = false;
}
eject.submitData = function(){
    let length = t.selectedData.length;
    let id="",name = "",nameData= "";
    for (let i = 0; i < length; i++){
        if (t.selectedData[i] != ""){
            id += "," + t.selectedData[i];
            nameData = t.dataList[t.selectedData[i]]
            if (t.paramData.leftName != null){
                nameData = t.paramData.leftName + nameData;
            }
            if (t.paramData.rightName != null){
                nameData += t.paramData.rightName;
            }
            name += "," + nameData;
        }
    }
    if (id != ""){
        id = id.substring(1);
        name = name.substring(1);
    }
    if (t.paramData.inputId != null){
        $("#" + t.paramData.inputId).val(id);
    } else {
        $("#" + t.idStr).val(id);
    }
    if (t.paramData.inputName != null){
        $("#" + t.paramData.inputName).val(name);
    } else {
        $("#" + t.nameStr).val(name);
    }
    if (t.callBackBool){
        parent.callbackData(id,name);
    }
    eject.closeData();
}

