var treeid = "";
var treename = "";
var parentid = "";
var menuList = {};
var checkDataListAllBool = {};
var checkDataList = [];


$(function () {
    initTree();
    initCss();
    bindEvents();
});

/**
 * 初始化Tree
 */
function initTree(){
    tree1 = new dhtmlXTreeObject("menuTree", "100%", "100%", 0);
    tree1.enableDragAndDrop(false);
    tree1.enableHighlighting(1);
    tree1.enableCheckBoxes(0);
    tree1.enableThreeStateCheckboxes(false);
    tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree1.loadXML(basePath + "js/tree/common/tree_b.xml");

    tree1.setOnClickHandler(function () {
        if ("powerInstall" == type || "companyMenu" == type) {
            return false;
        } else {
            getMenuData(2);
        }

    });
    if ("powerInstall" == type || "companyMenu" == type) {
        tree1.enableCheckBoxes(true);
        getAllMenuData();
    } else {
        getMenuData(1);
    }


}
function initCss(){
    if ("powerInstall" == type  || "companyMenu" == type) {
        $(".sec-nav").hide();
    } else {
        $(".sec-nav").show();
    }
    if ("iframe" === iframe){
        $(".content").height($(window).height());
    } else {
        $(".content").height($(window).height() - $("header").height());
    }
    if ("powerInstall" == type || "companyMenu" == type) {
       $(".sec-save").show();
       $(".sec-list").height($(".content").height() - $(".sec-save").height() - 10);
    } else {
        $(".sec-save").hide();
        $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    }

}

// 获取菜单列表
function getMenuData(typeData) {
    layer.load();
    var url;
    if (typeData == 1) {
        //查询一级菜单
        treeid1 = "0";
        url = basePath + "ea/menu/sajax_ea_getMenuList.jspa";
    } else {
        // 2: 根据父级id查询菜单
        treeid1 = tree1.getSelectedItemId();
        url = basePath + "ea/menu/sajax_ea_getMenuByQueryData.jspa?menuPID=" + treeid1;
    }
    tree1.deleteChildItems(treeid1);
    if (menuList[treeid1] == undefined){
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                layer.close(layer.index);
                if (data == null){
                    $("#menuTree").html("暂无数据");
                    $("#menuTree").css({"display":"flex","align-items":"center","justify-content":"center"});
                } else {
                    var codeList = eval("(" + data + ")");
                    menuList[treeid1] = codeList;
                    for (let i = 0; i < codeList.length; i++) {
                        tree1.insertNewChild(treeid1, codeList[i].menuId, codeList[i].menuName, 0, 0, 0, 0);
                        tree1.setUserData(codeList[i].menuId, "pId", codeList[i].menuPID);
                        tree1.setUserData(codeList[i].menuId, "menuKey", codeList[i].menuKey);
                        tree1.setUserData(codeList[i].menuId, "pIdList", codeList[i].menuPIDList);
                        if (codeList[i].menuType == 1){
                            tree1.setItemImage(codeList[i].menuId, "lea.gif", null);
                        }
                    }
                }

            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    } else {
        layer.close(layer.index);
        const codeList = menuList[treeid1];
        for (let i = 0; i < codeList.length; i++) {
            tree1.insertNewChild(treeid1, codeList[i].menuId, codeList[i].menuName, 0, 0, 0, 0);
            tree1.setUserData(codeList[i].menuId, "pId", codeList[i].menuPID);
            tree1.setUserData(codeList[i].menuId, "menuKey", codeList[i].menuKey);
            tree1.setUserData(codeList[i].menuId, "pIdList", codeList[i].menuPIDList);
            if (codeList[i].menuType == 1){
                tree1.setItemImage(codeList[i].menuId, "lea.gif", null);
            }
        }
    }

}

// 获取菜单列表
function getAllMenuData() {
    layer.load();
    var url = basePath + "ea/menu/sajax_ea_getAllMenuList.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        data:{"empowerId": empowerId,"companyId":companyId},
        success: function cbf(data) {
            layer.close(layer.index);
            if (data == null){
                $("#menuTree").html("暂无数据");
                $("#menuTree").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {

                var dataObject = eval("(" + data + ")");
                var codeList = dataObject["menuList"];
                for (var i = 0; i < codeList.length; i++) {
                    var menuPID =codeList[i].menuPID;
                    if (menuPID == ""){
                        menuPID = "0";
                    }
                    tree1.insertNewChild(menuPID, codeList[i].menuId, codeList[i].menuName, 0, 0, 0, 0);
                    tree1.setUserData(codeList[i].menuId, "pId", codeList[i].menuPID);
                    tree1.setUserData(codeList[i].menuId, "menuKey", codeList[i].menuKey);
                    tree1.setUserData(codeList[i].menuId, "pIdList", codeList[i].menuPIDList)


                }
                var checkedMenuList = dataObject["checkedMenuList"];
                for (var i = 0; i < checkedMenuList.length; i++){
                    tree1.setCheck(checkedMenuList[i].menuId, true);
                }
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });

}
function bindEvents(){
    // 新建
    $(".draft").click(function (event) {
        treeid = tree1.getSelectedItemId();
        var param = [];
        param.push("type=add");
        if (!treeid) {
            document.location.href = basePath + "page/WFJClient/pc/5l5C/human/menuAdd.jsp?" + param.join("");;
        } else {
            const text = tree1.getItemText(treeid);
            const pIdList = tree1.getUserData(treeid,'pIdList');
            param.push("&menuPID=" + treeid);
            param.push("&menuPrentName=" + text);
            document.location.href = basePath + "page/WFJClient/pc/5l5C/human/menuAdd.jsp?"  + param.join("");
        }
    });
    //修改
    $(".edit").click(function () {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {
            layer.msg("请选择要修改的菜单");
            return;
        }
        menuKey = tree1.getUserData(treeid, 'menuKey');
        document.location.href = basePath + "ea/menu/ea_getMenuByKey.jspa?menuKey=" + menuKey;
    })
    //删除
    var del = 0;
    $(".del").click(function () {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {
            layer.msg("请选择要删除的数据")
            return;
        }

        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        del = 1;
        return false;
    });
    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    })
    //确认删除
    $(".close-confirm").click(function () {
        if (del == 0) {
            $(".div-tingyong").hide();
            return false;
        }
        del = 0;
        $(".div-tingyong").hide();
        if (!treeid) {
            prompt("请选择要删除的数据");
            return false;
        }
        var url = basePath
            + "ea/menu/sajax_ea_delMenu.jspa?menuId=" + treeid;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                if (data == "success") {
                    const pIdList = tree1.getUserData(treeid,'pId');
                    menuList[pIdList] = undefined;
                    tree1.deleteItem(treeid, true);

                } else if (data == "no") {
                    layer.msg("菜单有子项目无法删除");
                    return;
                }
            },
            error: function (data) {
                layer.msg("删除失败")
            }
        });
    })
    //选中
    $(document).on("click", ".ul li", function (event) {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
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
 * 权限保存
 */
function save(){
    const checkedData = tree1.getAllChecked();
    if ("" == checkedData && "powerInstall" == type){
        layer.msg("请勾选权限页面!");
        return false;
    }
    let url;
    if ("powerInstall" == type) {
        url = basePath + "ea/menu/sajax_ea_saveMoneyEmpowerMenu.jspa";
    } else {
        url = basePath + "ea/menu/sajax_ea_saveCompanyMenu.jspa";
    }

    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: {"checkedData": checkedData, "empowerId": empowerId,"companyId":companyId},
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                layer.msg("保存成功");
                if ("powerInstall" == type) {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/human/moneyEmpower.jsp";
                } else {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/human/companyMenu.jsp";
                }

            } else if (data == "exist") {
                isSubmit = false;
                layer.msg("名称重复");
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





