var parentIdList = [];
var selectChildBool = true;
var selectPID="";
var childData = {};

$(function () {
    bindEvents();
});

/**
 * 功能
 */
function bindEvents(){

    // 关闭菜单类型选项
    $("#mask, .close-mask").click(function() {
        clockMask();
    });

    // 选择父级菜单
    $("#menuParentTree").click(function(){
        selectChildBool = true;
        $("#mask").fadeIn();
        $("#menuParentLayer").animate({"bottom": 0});
        getCatalogueMenu("");
    })
    // 打开菜单类型选项
    $("#menuTypeSelect").click(function() {
        $("#mask").fadeIn();
        $("#menuTypeLayer").animate({"bottom": 0});
    });
}

/*关闭项目归属选项*/
function clockMask() {
    $("#mask").fadeOut();
    $("#menuTypeLayer").animate({"bottom": "-100%"});
    $("#menuParentLayer").animate({"bottom": "-100%"});
}

/**
 * 获取目录菜单
 * @param String
 */
function getCatalogueMenu(menuPID){
    if (!selectChildBool) return false;
    selectPID = menuPID;
    var url ;
    if (menuPID == null || "" == menuPID){
        url = basePath + "ea/menu/sajax_ea_getMenuList.jspa";
    } else {
        if (!parentIdList.includes(menuPID)){
            parentIdList.push(menuPID);
        }
        url = basePath + "ea/menu/sajax_ea_getMenuByQueryData.jspa?menuPID=" + menuPID + "&menuType=0&notMenuID=" + $("#menuId").val();
    }
    if (childData[selectPID] == undefined){
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                const codeList = eval("(" + data + ")");
                childData[selectPID] = codeList;
                showMenu(codeList);


            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    } else {
        showMenu(childData[selectPID]);
    }

}

function showMenu(codeList){
    if (codeList.length == 0){
        parentIdList.pop();
        layer.msg("无下级选项");
    } else {
        if (parentIdList.length > 0){
            $(".div-back").show();
        } else {
            $(".div-back").hide();
        }
        var menuStr = "";
        for (var i = 0; i < codeList.length; i++) {
            menuStr += "<li onClick=getCatalogueMenu('" + codeList[i].menuId + "');>" + codeList[i].menuName
                +"<button class='layui-btn layui-btn-xs layui-btn-radius layui-btn-danger div-menu-confirm' onclick=selectParentData('"
                + codeList[i].menuId + "','" + codeList[i].menuName + "')>" + "确认" + "</button>" + "</li>";
        }
        $("#menuList").html(menuStr);
    }
}

/**
 * 修改父级数据
 * @param menuId
 * @param menuName
 */
function selectParentData(menuId,menuName){
    $(".div_parent_name").html(menuName);
    $("#menuPID").val(menuId);
    selectChildBool = false;
    clockMask();
}

/**
 * 返回选择菜单
 */
function menuBack(){
    parentIdList.pop();
    var length = parentIdList.length;
    if (length > 0){
        getCatalogueMenu(parentIdList[length - 1]);
    } else {
        getCatalogueMenu("");
    }
}
/**
 * 选择菜单类型
 * @param num
 */
function selectMenuType(num){
    const menuTypeList = ["目录", "菜单", "按键"];
    clockMask();
    $("#menuTypeSelect dl dd").html(menuTypeList[num]);
    $("#menuType").val(num);
    if (num == 0){
        // 目录
        $(".div_menu_url").css("display","block");
        $(".div_menu_mark").css("display","none");

    } else if (num == 1) {
        // 菜单
        $(".div_menu_url").css("display","block");
        $(".div_menu_mark").css("display","none")
    } else {
        //按键
        $(".div_menu_url").css("display","none");
        $(".div_menu_mark").css("display","block")
    }
}
var isSubmit = false;
function save() {
    if (isSubmit) return false;
    var menuName = $("#menuName").val().trim();
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/);
    if (!menuName || menuName == "") {
        layer.msg('请输入名称');
        return;
    }
    if(!reg2.test(menuName)){
        layer.msg('名称只支持数字，英文，中文');
        return;
    }

    isSubmit = true;
    let url;
    if ("add" == type){
        url = basePath + "ea/menu/sajax_ea_createMenu.jspa";
    } else {
        url = basePath + "ea/menu/sajax_ea_updateMenu.jspa";
    }

    const formData = new FormData($("#form")[0]);
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/menu.jsp";
            } else {
                isSubmit = false;
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}








