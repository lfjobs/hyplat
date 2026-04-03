var num =0,selectedData=[];listData =[];selectedAll= false;
$(function () {
    initData();
});
function initData(){
    initCss();
    getRoleStandardByEmpowerId();
    bindEvent();
}

function initCss(){
    $(".content").height($(document).height() - $("header").height());
    $(".card-body").height($(".content").height() - $(".div-button").height() - 5);
    $(".div-add").show();
    $(".div-save").hide();

}
function getRoleStandardByEmpowerId() {
    const param = new Array();
    param.push("empowerId=" + empowerId);
    let url = basePath + "ea/rolestandard/sajax_ea_getRoleDataByEmpowerId.jspa?"+ param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            showData(eval("(" + data + ")"),"query")
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    })



}
function bindEvent(){
    // 监听滚动事件
    const divElement = document.querySelector('.content');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".company-ul").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getPersonDataByRoleId();
            }

        }
    })
}
function addData(){
    const param = new Array();
    param.push("empowerId=" + empowerId);
    param.push("&&type=notEmpowerId");
    let url = basePath + "ea/rolestandard/sajax_ea_getRoleDataByEmpowerId.jspa?"+ param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            const list = eval("(" + data + ")");
            if (list.length > 0){
                showData(list,"add");
                $(".div-add").hide();
                $(".div-save").show();
            } else {
                layer.msg("已添加所有角色数据");
            }
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    })

}

function showData(list,type){
    listData = list;
    var roleId = "",roleName="";
    var htmlstr = new Array();
    if ("add" == type){
        for (let i = -1; i < list.length; i++) {
            if(i == -1){
                roleId = "all";
                roleName = "全选";
            } else {
                roleId = list[i]["roleId"];
                roleName = list[i]["roleName"]
            }

            htmlstr.push("<div class=\"info-card\" id=\"card-role-id-" + roleId + "\" onclick=selectData('" + roleId + "')>");
            htmlstr.push("<div style='width:18px;float:left'>");
            const selected = selectedData.includes(roleId);
            if (selected){
                selectedNum++;
                htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:none' >&#xe63f;</i> ")
                htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:block' >&#x1005;</i> ")
            } else {
                htmlstr.push("<i class=\"layui-icon div-not-selected \" style='font-size:20px;display:block' >&#xe63f;</i> ")
                htmlstr.push("<i class=\"layui-icon div-selected\" style='font-size:20px;display:none' >&#x1005;</i> ")
            }
            htmlstr.push("</div>");
            if(i == -1){
                htmlstr.push("<div class=\"visitor\" style='float:left;margin-left:8px'>" + roleName + "</div>");
            } else {
                htmlstr.push("<div class=\"visitor\" style='float:left;margin-left:8px'>" + roleName + "</div>");
            }

            htmlstr.push("</div>");
        }
    } else {
        for (let i = 0; i < list.length; i++) {
            roleId = list[i]["roleId"];
            htmlstr.push("<div class=\"info-card card-role-id-" + roleId + " \">");
            htmlstr.push("<div class=\"visitor\" style='float:left'>" + list[i]["roleName"] + "</div>");
            htmlstr.push("<div style='float:left'><i class=\"layui-icon\"  onclick=deleteEmpowerRoleDataByRoleId('" +
                roleId +"')>&#x1006;</i> </div>")
            htmlstr.push("</div>");
        }

    }
    $(".div-card").html(htmlstr.join(""));
    $(".info-card").width($(document).width() - 45);
    $(".visitor").width($(".info-card").width() - 30);

}
function selectData(roleId){
    if("all" == roleId){

        if (selectedAll){
            $("#card-role-id-" + roleId +" .div-selected").hide();
            $("#card-role-id-" + roleId +" .div-not-selected").show();
            roleId = "";
            for (let i = 0; i < listData.length; i++) {
                roleId = listData[i]["roleId"];
                $("#card-role-id-" + roleId +" .div-selected").hide();
                $("#card-role-id-" + roleId +" .div-not-selected").show();
            }
            selectedData = [];
        } else {
            $("#card-role-id-" + roleId +" .div-selected").show();
            $("#card-role-id-" + roleId +" .div-not-selected").hide();
            roleId = "";
            selectedData = [];
            for (let i = 0; i < listData.length; i++) {
                roleId = listData[i]["roleId"];
                $("#card-role-id-" + roleId +" .div-selected").show();
                $("#card-role-id-" + roleId +" .div-not-selected").hide();
                selectedData.push(roleId);
            }
        }
        selectedAll = !selectedAll;
    } else {
        const selected = selectedData.includes(roleId);
        if (selected) {
            $("#card-role-id-" + roleId + " .div-selected").hide();
            $("#card-role-id-" + roleId + " .div-not-selected").show();
            selectedData = selectedData.filter(item => item !== roleId);
        } else {
            $("#card-role-id-" + roleId + " .div-selected").show();
            $("#card-role-id-" + roleId + " .div-not-selected").hide();
            selectedData.push(roleId);
        }
    }
}


function deleteEmpowerRoleDataByRoleId(roleId){
    layer.confirm('确定删除', {
        title:'温馨提示',
        skin: 'delete-class',
        btn: ['取消','确定']
    }, function(){
        layer.close(layer.index);
    }, function() {
        var url = basePath
            + "ea/rolestandard/sajax_ea_deleteEmpowerRoleDataByRoleId.jspa?empowerId=" + empowerId + "&&roleId=" + roleId;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                $(".card-role-id-" + roleId).remove();
                layer.close(layer.index);
            },
            error: function (data) {
                layer.msg("保存失败");
            }
        })

    });
}

function saveData(){
    var length = selectedData.length;
    if (length < 1){
        return;
    }
    var roleIds ="";
    for (let i = 0; i < length; i++){
        roleIds += "|" + selectedData[i];
    }
    let url = "ea/rolestandard/sajax_ea_saveEmpowerRole.jspa";
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: {"empowerId":empowerId,"roleIds":roleIds},
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                layer.msg("保存成功");
                initData();

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


