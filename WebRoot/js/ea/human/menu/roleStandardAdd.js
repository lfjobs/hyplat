
$(function () {
    initCss();
    initData();
    bindEvents();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $("#menuTree").height($(".content").height() - $(".div-bottom").height() - 25 );
}

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
function initData(){
    if ("power" == type){
        initTree();
        getRoleMenuByRoleKey();
    }
}

/**
 * 根据角色id获取角色菜单
 */
function getRoleMenuByRoleKey() {
    layer.load();
    const url = basePath + "ea/rolestandard/sajax_ea_getRoleMenuByRoleId.jspa?roleId=" + roleId;
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
                const dataList = eval('(' + data + ')');
                const menuList = dataList["menuList"];
                showMenu(menuList);
                const checkedMenuList = dataList["checkedMenuList"];
                for (let i = 0; i < checkedMenuList.length; i++){
                    tree1.setCheck(checkedMenuList[i].menuId, true);
                }
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });

}

/**
 * 点击事件
 */
function bindEvents() {
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
 * 保存数据
 */
function save(){
    let url;
    const formData = new FormData($("#form")[0]);
    if (type == "power"){
        const checkedData = tree1.getAllChecked();
        formData.append("roleId",roleId);
        formData.append("checkedData",checkedData);
        url = "ea/rolestandard/sajax_ea_saveRoleMenu.jspa";
    } else {
        url = "ea/rolestandard/sajax_ea_saveRoleStandardData.jspa";
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
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleStandard.jsp?empowerId=" + empowerId;

            } else if (data == "existName") {
                isSubmit = false;
                layer.msg("角色名称重复");
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







