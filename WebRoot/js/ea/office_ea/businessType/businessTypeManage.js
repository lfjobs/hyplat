var treeid = "";
var treename = "";
var parentid = "";
var typeMenu;

$(function () {
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    $.ajax({
        url: encodeURI(basePath + "ea/dictdata/sajax_ea_getDictDataByType.jspa?type=typeMenu"),
        type: "get",
        async: false,
        dataType: "json",
        success: function (message) {
            if (message == null){
                layer.msg("暂无数据");
            } else {
                const data = eval("(" + message + ")");
                if (data){
                    typeMenu = data.dictValue;
                }
            }

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });

    //getData();
    tree1 = new dhtmlXTreeObject("businessTree", "100%", "100%", 0);
    tree1.enableDragAndDrop(false);
    tree1.enableHighlighting(1);
    tree1.enableCheckBoxes(0);
    tree1.enableThreeStateCheckboxes(false);
    tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree1.loadXML(basePath + "js/tree/common/tree_b.xml");

    getIndustryItem(3);
    tree1.setOnClickHandler(function () {
        getIndustryItem(1);
    });

    // tree1.setOnDblClickHandler(function() {
    //     getIndustryItem(2);
    //
    // });
});

// 获取行业
function getIndustryItem(type) {
    var getListCCodeurl;
    var typeName;
    var flag;
    if (type == 3) {
        treeid1 = "0";
        getListCCodeurl = basePath + "ea/industry/sajax_ea_getBusinessTyperRootList.jspa";
    } else {
        treeid1 = tree1.getSelectedItemId();
        flag = tree1.getUserData(treeid1,"flag");
        // console.log(treeid1);
        // console.log(typeMenu);
        // console.log(flag);
        if(treeid1 == typeMenu){
            getListCCodeurl = basePath + "ea/menu/sajax_ea_getMenuByParentId.jspa";
        }else if(flag == typeMenu){
            getListCCodeurl = basePath + "ea/menu/sajax_ea_getMenuByParentId.jspa?menuId=" + treeid1;
        }else{
            getListCCodeurl = basePath + "ea/industry/sajax_ea_getBusinessTypeByParentNum.jspa?typePID=" + treeid1;
        }
    }
    // treename1 = tree1.getItemText(treeid1);
    tree1.deleteChildItems(treeid1);

    $.ajax({
        url: encodeURI(getListCCodeurl),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            // var member = eval("(" + data + ")");
            // var codeList = member.codeList;

            var codeList = eval("(" + data + ")");
            if(treeid1 == typeMenu || flag == typeMenu){
                for (var i = 0; i < codeList.length; i++) {
                    tree1.insertNewChild(treeid1, codeList[i].menuId, codeList[i].menuName, 0, 0, 0, 0);
                    tree1.setUserData(codeList[i].menuId, "pId", codeList[i].menuPID);
                    tree1.setUserData(codeList[i].menuId, "flag", typeMenu);
                }
            }else{
                for (var i = 0; i < codeList.length; i++) {
                    tree1.insertNewChild(treeid1, codeList[i].typeId, codeList[i].typeShowNum, 0, 0, 0, 0);
                    tree1.setUserData(codeList[i].typeId, "pId", codeList[i].typePID);
                    tree1.setUserData(codeList[i].typeId, "typeKey", codeList[i].typeKey);
                    tree1.setUserData(codeList[i].typeId, "typeName", codeList[i].typeShowNum);
                }
            }

            // var nodeId = tree1.getItemIdByLabel("");
            //
            // console.log("节点ID:", nodeId);
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });


}

$(document).ready(function () {
    //新建
    $(".draft").click(function (event) {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {
            /*prompt("请先选择父级项目");
            return;*/
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeManageAdd.jsp";
        } else {
            var text = tree1.getItemText(treeid);
            pId = tree1.getUserData(treeid, 'pId');
            // document.location.replace(basePath+"page/WFJClient/pc/5l5C/office/businessTypeAdd.jsp?parentId="+treeid);
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeAdd.jsp?typePID=" + treeid + "&parentName=" + text;
        }

    });


    //弹框
    function prompt(obj) {
        if ($("#prompt").css("display") != "none")
            return;
        $("#prompt").find("span").text(obj);
        $("#prompt").fadeIn(500);
        $("#prompt").show();
        setTimeout(function () {
            $("#prompt").fadeOut(500);
            $("#prompt").find("span").text("");
        }, 3000);
    }


    //修改
    $(".edit").click(function () {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {
            prompt("请选择要修改的项目");
            return;
        }
        typeKey = tree1.getUserData(treeid, 'typeKey');
        document.location.href = basePath + "ea/industry/ea_getBusinessTypeByKey.jspa?typeKey=" + typeKey;
    })


    //查看
    $(".view").click(function () {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {

            $(".div-tingyong").show();
            $(".titlep").text("请选择要修改的数据");
            return false;
        }
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeAdd.jsp?typeName=" + treename + "&adtId=" + treeid + "&parentId=" + parentid + "&module=" + module + "&companyID=" + companyID;
    })


    //删除
    var del = 0;
    $(".del").click(function () {
        treeid = tree1.getSelectedItemId();
        if (!treeid) {
            prompt("请选择要删除的数据");
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


        typeKey = tree1.getUserData(treeid, 'typeKey');
        var url = basePath
            + "ea/industry/sajax_ea_delBusinessType.jspa?typeKey=" + typeKey;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            // data: {
            //     "adtId": adtId
            // },
            success: function (data) {
                if (data == "success") {
                    tree1.deleteItem(treeid, true);
                } else if (data == "no") {
                    prompt("项目有子项目无法删除");
                    return;
                }


            },
            error: function (data) {
                prompt("删除失败")

            }
        });
    })

    $(window).scroll(function () {


        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        if (scroll > 115) {
            $(".sec-nav").addClass("nav");
        } else {
            $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last1").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                load();
            }
        }

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

    //创建模板时选择模板分类后的确定|查询选择模板后的确定查询可以多选
    $(".sec-bottom").click(function () {
        var li = $(".ul li.active");
        var length = $(li).length;
        if (length < 1) {
            return false;
        }
        var temptid = $(li).attr("id");
        $(window.parent.document).find("#temptId").val(temptid);
        if (pos == "add") {
            var templateTypeName = $(li).find(".templateTypeName").text();


            $(window.parent.document).find("#specificTemplate").val(templateTypeName);

        } else if (pos == "cx") {

            window.parent.window.searchByFenlei();
        }
        $(".ul .active").removeClass("active");
        $(window.parent.document).find(".iframecom").hide();


    })


})

//共享分类
function load() {
    tree = new dhtmlXTreeObject("mbfl", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");


    if (companyID == "") {//个人办公里面的

        tree.insertNewChild("0", "me", "档案盒", 0, 0, 0, 0);
        tree.insertNewChild("me", "doc", "公文档案盒", 0, 0, 0, 0);
        tree.setUserData("doc", "module", "doc");
        tree.insertNewChild("me", "contract", "合同档案盒", 0, 0, 0, 0);
        tree.setUserData("contract", "module", "contract");
    } else if (companyID != "") {//公司
        if (module == "doc") {
            tree.insertNewChild("0", "doc", "公文档案盒", 0, 0, 0, 0);
            tree.setUserData("doc", "module", "doc");
            getTypeList("doc");
        } else {
            tree.insertNewChild("0", "contract", "合同档案盒", 0, 0, 0, 0);
            tree.setUserData("contract", "module", "contract");
            getTypeList("contract");
        }


    }


    // tree.openAllItems("main");
    tree.setOnClickHandler(function () {
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        parentid = tree.getParentId(treeid);
        // parentname = tree.getItemText(parentid);


        var state = tree.getOpenState(treeid);


        if (state == 1) {
            tree.closeItem(treeid);
        } else {

            if (!tree.hasChildren(treeid)) {
                // tree.deleteChildItems(treeid);
                getTypeList(treeid, "00");
            }
            tree.openItem(treeid);
        }


    });


}


function getTypeList(treeid) {
    module = tree.getUserData(treeid, 'module');
    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getArchiveTypeLists.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            parentId: treeid,
            module: module

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;

            var obj = "";
            for (var i = 0; i < list.length; i++) {
                if (tree.getItemText(list[i].adtId) == 0) {
                    tree.insertNewChild(treeid,
                        list[i].adtId,
                        list[i].typeName,
                        0, 0, 0, 0);
                    tree.setUserData(list[i].adtId, "module",
                        list[i].module);
                }

            }


        },
        error: function (data) {
            console.log("失败");
        }
    });

}

