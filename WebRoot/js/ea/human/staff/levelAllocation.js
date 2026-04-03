let pageNumber = 1, pageSize = 40,pageCount = 0;
let selectedId= "",scrollBool = true,type="",dataList={};
let levelExamine = false;
$(function () {
    initCss();
    bindEvents();
    getData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height())
}

/**
 * 点击事件
 */
function bindEvents() {
    // 添加
    $(".add").click(function () {
        localStorage.setItem('levelExamine', "0");
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/levelAllocationDetail.jsp?type=add&&status=0";
    })
    // 修改
    $(".edit").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        jumpPage("edit");
    });
    // 查询
    $(".query").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        jumpPage("query");
    });
    // 查询
    $(".examine").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        jumpPage("examine");
    });
    // 删除
    $(".del").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要删除的数据");
            return false;
        }
        layer.confirm('确定删除', {
            title:'温馨提示',
            skin: 'delete-class',
            btn: ['取消','确定']
        }, function(){
            layer.close(layer.index);
        }, function() {
            var url = basePath
                + "ea/staffLevelAllocation/sajax_ea_deleteLevelAllocationById.jspa?salaryLevelId=" + selectedId ;
            $.ajax({
                type: "GET",
                url: url,
                async: false,
                dataType: "json",
                success: function (data) {
                    pageNumber = 1, pageSize = 40,pageCount = 0;
                    getData();
                    layer.close(layer.index);
                    selectedId = "";
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            })

        });
    });

    // 转交审核
    $(".approverPerson").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        type = "approverPerson";
        var url = ["ea/staffLevelAllocation/sajax_ea_getRoleDataByQueryName.jspa?1=1","ea/staffLevelAllocation/sajax_ea_getStaffDataList.jspa?1=1"]
        const param = {"titleName":"选择审核人","tabName":["角色","人员"],"url":url,
           "callBackBool":true,"selectedData": [],
            "idList":["roleID","staffID"],"nameList":["roleName","staffName"],
            "nameData":[["roleName"],["staffName","(","reference",")"]],
        };
        stab.getData(param);
    });
    // 级别生效
    $(".level").click(function () {
        layer.confirm('确定生效', {
            title:'温馨提示',
            skin: 'delete-class',
            btn: ['取消','确定']
        }, function(){
            layer.close(layer.index);
        }, function() {
            let url = "ea/staffLevelAllocation/sajax_ea_saveStaffLevelByAllocationId.jspa";
            $.ajax({
                url: basePath + url,
                type: 'POST',
                data: {"allocationId":selectedId},
                dataType:"json",
                success: function (data) {
                    if (data == "success") {
                        layer.msg("保存成功");
                        getData();
                    } else  {
                        layer.msg("保存失败");
                    }
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            });
        })

    });

    $(document).on("click", ".data-li", function (event) {
        selectedId = event.currentTarget.parentNode.id;
        const type = showTab();
        jumpPage(type);
    })
    $(document).on("click", ".select-li", function (event) {
        selectedId = event.currentTarget.parentNode.id;
        showTab();
    })

    $(".select").click(function () {
        const param = {"titleName": "选择审核状态", "id": "id", "name": "name", "height": "40%","callBackBool":true};
        const data = [{"id":"0","name":"全部"},{"id":"1","name":"我的申请"},{"id":"0","name":"我的创建"},
            {"id":"3","name":"待我审核"},{"id":"4","name":"我已审核"}];
        type = "select";
        eject.getData(data, param);
    })

    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount && scrollBool){
                scrollBool = false;
                pageNumber++;
                getEntryStaffData();            }

        }
    })
}



function jumpPage(type){
    document.location.href = basePath + "ea/staffLevelAllocation/ea_getLevelAllocationById.jspa?type="
        + type + "&&levelAllocationId=" + selectedId + "&&status=" + status;
}
function showTab(){
    var type = "query";
    var data = dataList[selectedId];
    $(".li-clearfix").hide();

    if(data[9] == staffId &&  data[5] != 3){
        $(".li-del").show();
    }
    if (data[9] == staffId &&((data[6] == null && data[7] == null) || data[5] == 2)){
        $(".li-edit").show();
    }

    if (data[7] == null){
        data[7] = "";
    }
    if(data[5] == 1 && levelExamine){
        $(".li-level").show();
    }
    if ((data[7].includes(staffId) > 0 || data[9] == staffId) && data[5] != 3  && data[6] == null){
        $(".li-approverPerson").show();
    }

    if(data[6] != null){
        var pendingReview = data[6].split(",");
        var roleIdArr = roleId.split(",");
        var bool = pendingReview.some(item => roleIdArr.includes(item));
        if((bool || pendingReview.includes(staffId)) && data[5] != 1 && data[5] != 3){
            $(".li-examine").show();
            type = "examine";
        }
    }

    return type;
}
function getData(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&status=" + status);
    param.push("&&staffId=" + queryStaffId);
    const url = basePath + "ea/staffLevelAllocation/sajax_ea_getLevelAllocationList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const dataJson = eval("(" + data + ")");
            const codeList = dataJson["pageForm"];
            levelExamine = dataJson["levelExamine"];
            localStorage.setItem("levelExamine",levelExamine);
            if (codeList == null){
                $(".data-list").html("<div class='div-data-none'>暂无数据</div>");
                $(".div-data-none").css({"margin-top":"100px","margin-left":"100px","align-items":"center","justify-content":"center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                let name= "";
                for (let i = 0; i < length; i++) {
                    dataList[list[i][0]] = list[i];
                    name = list[i][2];
                    if (name.length > 3) {
                        name = name.substring(0, 3) + "...";
                    }
                    htmlstr.push("<ul id='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<li style='margin-top:-8px' class='select-li'><input type=\"radio\" name=\"selected-li\"  >" + "</li>");
                    //htmlstr.push("<li>" + (pageSize*(pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li class='data-li'>" + list[i][1] + "</li>");
                    htmlstr.push("<li class='data-li'>" + name + "</li>");
                    htmlstr.push("<li class='data-li'>" + (list[i][3] == null ? " " : (list[i][3] + "级")) + "</li>");
                    htmlstr.push("<li class='data-li'>" + (list[i][4] == null ? " " : (list[i][4] + "级")) + "</li>");
                    if (list[i][5] == "0") {
                        htmlstr.push("<li class='data-li' style='color:orange'>待审核</li>");
                    } else if (list[i][5] == "1") {
                        htmlstr.push("<li class='data-li' style='color:green'>已审核</li>");
                    } else if (list[i][5] == "2") {
                        htmlstr.push("<li class='data-li' style='color:red'>已驳回</li>");
                    } else if (list[i][5] == "3") {
                        htmlstr.push("<li class='data-li'style='color:blue'>已完成</li>");
                    }
                    htmlstr.push("<li class='data-li'>" +  (parseInt(list[i][4]) < 4 ? list[i][10] : (list[i][10] + "元"))  + "</li>");
                    htmlstr.push("<li class='data-li'>" +  (parseInt(list[i][4]) < 4 ? list[i][11] : (list[i][11] + "元"))  + "</li>");
                    htmlstr.push("<li class='data-li'>" +  (parseInt(list[i][4]) < 4 ? list[i][12] : (list[i][12] + "元"))  + "</li>");
                    htmlstr.push("<li class='data-li'>" +  (parseInt(list[i][4]) < 4 ? list[i][13] : (list[i][13] + "元"))  + "</li>");

                    htmlstr.push("</ul>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove();
                }
                if (pageNumber > 1){
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }
                layui.use('form', function(){
                    form = layui.form;
                    form.render('radio');
                });

            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}
function callbackData(id,name){
    if("approverPerson" == type){
        if("" == id){
            layer.msg("未选择审核人员数据！！");
            return false;
        }
        saveTransmitPersonData(id,name);
    } else if("select" == type) {
        status = id;
        getData(id);
    }

}

/**
 * 保存转交审核数据
 * @param id
 * @param name
 */
function saveTransmitPersonData(id,name){
    let url = "ea/staffLevelAllocation/sajax_ea_saveTransmitPersonData.jspa";
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: {"transmitId":id,"transmitName":name,"id":selectedId},
        dataType:"json",
        success: function (data) {
            if (data == "success") {
                layer.msg("保存成功");
                $(".data-list").append("");
                getData();
            } else  {
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}