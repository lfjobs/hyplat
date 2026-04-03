var treeid = "";
var deptData = {};
var selectedOrganizationID="",selectedOrganizationName="";
var selectChildBool = false,returnCloseBool = false;
var parentIdList = [];
$(function () {
    initCss();
    initTree();
    initData();
    bindEvents();
});





/**
 * 初始化Tree
 */
function initTree(){
    tree1 = new dhtmlXTreeObject("menuTree", "100%", "100%", 0);
    tree1.enableDragAndDrop(false);
    tree1.enableHighlighting(1);
    tree1.enableCheckBoxes(true);
    tree1.enableThreeStateCheckboxes(false);
    tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
}
/**
 * 初始化数据
 */
function initData() {
    layer.load();
    var url = "";
    if (type == "add"){
        url = basePath + "ea/menu/sajax_ea_getAllMenuListByCompanyId.jspa";
    } else {
        url = basePath + "ea/menu/sajax_ea_getRoleMenuByRoleId.jspa?roleId=" + roleId;
    }
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function(data) {
            layer.close(layer.index);
            if (data == null){
                $("#menuTree").html("暂无数据");
                $("#menuTree").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                if (type == "add"){
                    var codeList = eval("(" + data + ")");
                    showMenu(codeList);
                } else {
                    var menuList = eval('('+data+')');
                    const companyMenuList = menuList["companyMenuList"];
                    showMenu(companyMenuList);
                    var checkedMenuList = menuList["checkedMenuList"];
                    for (var i = 0; i < checkedMenuList.length; i++){
                        tree1.setCheck(checkedMenuList[i].menuId, true);
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
 * 显示菜单
 * @param codeList
 */
function showMenu(codeList){
    if (codeList.length == 0){
        $("#menuTree").html("暂无数据");
        $("#menuTree").css({"display":"flex","align-items":"center","justify-content":"center"});
    } else {
        var closeData = [];
        for (var i = 0; i < codeList.length; i++) {
            var menuPID =codeList[i].menuPID;
            var menuId = codeList[i].menuId;
            if (menuPID == ""){
                menuPID = "0";
                closeData.push(menuId);
            }
            tree1.insertNewChild(menuPID,menuId, codeList[i].menuName, 0, 0, 0, 0);
            tree1.setUserData(menuId, "pId", codeList[i].menuPID);
            tree1.setUserData(menuId, "menuKey", codeList[i].menuKey);
            tree1.setUserData(menuId, "pIdList", codeList[i].menuPIDList);


        }
        var length = closeData.length;
        for (var i = 0; i < length; i++) {
            tree1.closeItem(closeData[i]);
        }
    }

}
/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $("#menuTree").height($(".content").height() - $(".div-bottom").height() - 25 );
}


/**
 * 点击事件
 */
function bindEvents() {
    // 选择部门
    $("#organizationNameDesc").click(function(){
        $("#mask").fadeIn();
        $("#deptLayer").animate({"bottom": 0});
        getDeptList("","",false);
    })

    tree1.attachEvent("onCheck", function(id, state){
        // 检查当前选中的节点是否为父节点
        // 该子级下的所有数据选中
        var node = tree1._globalIdStorageFind(id);
        if (node != null) {
            tree1.setSubChecked(id,state);
            getParentIdCheck(id,state);
        }
    });
    // 关闭菜单类型选项
    $("#mask, .close-mask").click(function() {
        closeMask();
    });

}
function getParentIdCheck(id,state){
    if (state === 1) {
        var parentId = tree1.getParentId(id);
        // 如果节点有父节点，设置父节点的选中状态
        if (parentId !== "" && parentId !== 0) {
            tree1.setCheck(parentId, state);
            getParentIdCheck(parentId,state);
        }
    }

}
/**
 * 返回选择菜单
 */
function deptBack(){
    parentIdList.pop();
    var length = parentIdList.length;
    if (length > 0){
        getDeptList(parentIdList[length - 1],"",true);
    } else {
        getDeptList("","",true);
    }
}

/**
 * 查询部门信息
 */
function getDeptList(organizationPID,organizationName,closeBool){
    selectedOrganizationID = organizationPID;
    selectedOrganizationName = organizationName;
    returnCloseBool = closeBool;
    if (organizationPID != ""){
        selectChildBool = true;
    }
    if (deptData[organizationPID] != undefined){
        deptDataListStr(deptData[organizationPID]);
        return;
    }
    var url = basePath + "ea/menu/sajax_ea_getDeptListByDeptPID.jspa?organizationPID=" + organizationPID;
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : true,
        dataType : "json",
        success: function cbf(data) {
            var codeList = eval("(" + data + ")");
            deptData[organizationPID] = codeList;
            deptDataListStr(codeList);
        },
        error : function cbf(data) {
            alert("数据获取失败！")
        }
    });
}

/**
 * 根据数据获取部门列表
 * @param codeList
 */
function deptDataListStr(codeList){
    if (codeList.length == 0){
        layer.msg("无下级选项");
    } else {
        if (returnCloseBool){
            if (parentIdList.length > 0){
                const position = $(".dept-name").html().lastIndexOf("&gt;");
                $(".dept-name").html($(".dept-name").html().substring(0,position));
            } else {
                $(".dept-name").html("");
            }
            parentIdList.pop();
        } else {
            if (parentIdList.length > 0){
                $(".dept-name").html($(".dept-name").html() + ">" + selectedOrganizationName);
            } else {
                $(".dept-name").html($(".dept-name").html() + selectedOrganizationName);
            }
            if (selectedOrganizationID != ""){
                if (!parentIdList.includes(selectedOrganizationID)){
                    parentIdList.push(selectedOrganizationID);
                }
            }
        }

        if (selectedOrganizationID == ""){
            $(".div-back").hide();
        } else {
            $(".div-back").show();
        }
        var menuStr = "";
        var organizationID = "",organizationName = "";
        for (var i = 0; i < codeList.length; i++) {
            organizationID = codeList[i].organizationID;
            organizationName = codeList[i].organizationName;
            menuStr += "<li onClick=deptDataFillValue('" + organizationID + "','" + organizationName + "');>" +
                "<div style='width:80%'><label style='float:left;margin-left:40px'>"
                + organizationName +"</label></div>" +
                "<div class='selected-child'"  + "onclick=getDeptList('" + organizationID + "','" + organizationName + "',false)>" +
                "<i class=\"layui-icon\" style=\"font-size: 185; color: #c33820;\">&#xe649;</i>" +
                "<label class='label-child-name'>下级</label>" +
                "</div>"
                + "</li>";
        }
        $("#deptList").html(menuStr);
    }
}
/**
 * 部门选择填充
 * @param organizationID
 * @param organizationName
 */
function deptDataFillValue(organizationID,organizationName){
    if (selectChildBool){
        selectChildBool = false;
        return false;
    }
    $("#organizationName").val(organizationID);
    $("#organizationNameDesc").val(organizationName);

    closeMask();
}
/**
 * 权限保存
 */
function save(){


    let url = "ea/menu/sajax_ea_saveRoleMenu.jspa";
    const formData = new FormData($("#form")[0]);
    if (type == "power"){
        const checkedData = tree1.getAllChecked();
        formData.append("roleId",roleId);
        formData.append("checkedData",checkedData);

        url = "ea/menu/sajax_ea_saveRoleMenu.jspa";
    } else {
        const roleName = $("#roleName").val().trim();
        if ("" == roleName){
            layer.msg("请填写职务名称!");
            return false;
        }
        url = "ea/menu/sajax_ea_saveCRoleData.jspa";
    }
    formData.append("type",type);
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: formData,
        dataType:"json",
        async : true,
        processData : false,
        contentType : false,
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


/*关闭部门选项*/
function closeMask() {
    $(".dept-name").html("");
    $("#mask").fadeOut();
    $("#deptLayer").animate({"bottom": "-100%"});
}






