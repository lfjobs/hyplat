let pageNumber = 1, pageSize = 20,pageCount = 0,recordCount=0,selectedNum=0;
var dataList = {},selectedData=[],copySelectedData = [];
$(function () {
    initCss();
    getPersonDataByRoleId();
    bindEvent();
});

function initCss(){
    $(".content").height($(document).height() - $("header").height());
    $(".dept-person-body").height($(".content").height() - $(".dtd-oa-search-bar").height() - $(".div-bottom").height() - 10);
    $(".person-data").width( $(".content").width() - $(".person-submit").width());
}
function getPersonDataByRoleId() {
    const staffName = $("#staffName").val();
    localStorage.setItem("staffName",staffName);
    const param = new Array();
    param.push("roleId=" + id);
    param.push("&&staffName=" + staffName);
    param.push("&&type=notRole");
    param.push("&&pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    let url = basePath + "ea/menu/sajax_ea_getPersonDataByRoleId.jspa?"+ param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            var htmlstr = new Array();
            var listData = eval("(" + data + ")");
            if (pageNumber == 1){
                $(".data-list").html("");
            }
            if (listData == null){
                htmlstr.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                var list = listData["list"];
                pageCount = listData["pageCount"];
                recordCount = listData["recordCount"];
                for (var i = 0; i < list.length; i++) {
                    let staffId = list[i]["staffID"];
                    htmlstr.push("<div class=\"info-card\" id=\"staff-" + staffId + "\" onclick=selectData('" + staffId + "')>");
                    htmlstr.push("<div style='width:18px;float:left'>");
                    const selected = selectedData.includes(staffId);
                    if (selected){
                        selectedNum++;
                        htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:none' >&#xe63f;</i> ")
                        htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:block' >&#x1005;</i> ")
                    } else {
                        htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:block' >&#xe63f;</i> ")
                        htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:none' >&#x1005;</i> ")
                    }

                    htmlstr.push("</div>")
                    htmlstr.push("<span class=\"visitor\">" + list[i]["staffName"] + "(" + list[i]["reference"]+ ")</span>");
                    htmlstr.push("</div>");
                    dataList[staffId] = list[i];

                }
                if ("" == $("#staffName").val()){
                    $(".div-person-data").html(selectedData.length + "/" + recordCount );
                } else {
                    $(".div-person-data").html(selectedNum + "/" + recordCount );
                }
                $(".person-num").html(selectedData.length + "人");
                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
            }

            $(".data-list").append(htmlstr.join(""));

        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    })

}
function selectData(staffId){
    var element= document.getElementById('input-' + staffId);
    const selected = selectedData.includes(staffId);
    if (selected){
        $("#staff-" + staffId +" .div-selected").hide();
        $("#staff-" + staffId +" .div-not-selected").show();
        selectedData = selectedData.filter(item => item !== staffId);
        selectedNum--;
    } else {
        $("#staff-" + staffId +" .div-selected").show();
        $("#staff-" + staffId +" .div-not-selected").hide();
        selectedData.push(staffId);
        selectedNum++;
    }
    $(".div-person-data").html(selectedNum + "/" + recordCount );
    $(".person-num").html(selectedData.length + "人");
}
function bindEvent(){
    // 监听滚动事件
    const divElement = document.querySelector('.dept-person-body');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".company-ul").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getPersonDataByRoleId();
            }

        }
    })
    document.getElementById('staffName').addEventListener("input",function(){
        pageNumber = 1, pageSize = 20,pageCount = 0,recordCount=0,selectedNum=0;
        getPersonDataByRoleId();
    })

    $(".close-data").click(function(){
        pageNumber = 1, pageSize = 20,pageCount = 0,recordCount=0,selectedNum=0;
        $("#staffName").val("");
        localStorage.setItem("staffName","");
        getPersonDataByRoleId();
    })
    $(".person-data").click(function(){
        $("#mask").fadeIn();
        $("#dataLayer").animate({"bottom": 0});
        getSelectedData(selectedData)
        copySelectedData = selectedData.concat();
    })
    $(".submit-mask").click(function(){
        $("#mask").fadeOut();
        $("#dataLayer").animate({"bottom": "-100%"});
        selectedData = copySelectedData.concat();
        getPersonDataByRoleId();
    })
}
function getSelectedData(data){
    var htmlstr = new Array();
    var length = data.length;
    for (var i = 0; i < length; i++){
        let staffId = selectedData[i];
        let data = dataList[staffId];
        htmlstr.push("<li class='person-" + staffId + "'>");
        htmlstr.push("<div style='width:80%'>");
        htmlstr.push("<label>" + data["staffName"] + "(" + data["reference"] +")</label>");
        htmlstr.push("</div>");
        htmlstr.push("<button class=\"layui-btn layui-btn-primary layui-btn-xs\" style='font-weight:550;height:26px'onclick=deletePerson('" + staffId + "')>移除</button>")
        htmlstr.push("<div>");
        htmlstr.push("</div>");
        htmlstr.push("</li>");
    }
    $("#selectedDataList").html(htmlstr.join(""));
}
/*关闭部门选项*/
function closeMask() {
    $("#mask").fadeOut();
    $("#dataLayer").animate({"bottom": "-100%"});
}

function deletePerson(staffId){
    copySelectedData = copySelectedData.filter(item => item !== staffId);
    $(".person-" + staffId).remove();
}

function addStaffRole(){
    var length = selectedData.length;
    if (length < 1){
        return;
    }
    var staffIds ="";
    for (let i = 0; i < length; i++){
        staffIds += "|" + selectedData[i];
    }
    let url = "ea/menu/sajax_ea_saveStaffRole.jspa";
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: {"roleId":id,"staffIds":staffIds},
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/role.jsp";

            } else if (data == "exist") {
                isSubmit = false;
                layer.msg("职务名称重复");
            } else  {
                isSubmit = false;
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}

