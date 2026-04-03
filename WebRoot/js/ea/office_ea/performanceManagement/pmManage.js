var treeid = "";
var treename = "";
var parentid = "";
var tree1;

$(function () {
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    tree1 = new dhtmlXTreeObject("pmTree", "100%", "100%", 0);
    tree1.enableDragAndDrop(false);
    tree1.enableHighlighting(1);
    tree1.enableCheckBoxes(0);
    tree1.enableThreeStateCheckboxes(false);
    tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree1.loadXML(basePath + "js/tree/common/tree_b.xml");

    getPmItem(0);
    tree1.setOnClickHandler(function () {
        getPmItem(1);
    });
});

// 获取行业
function getPmItem(type) {
    var url;
    var typeName;
    var flag;
    if (type == 0) {
        treeid1 = "0";
        url = basePath + "ea/specs/sajax_ea_getPerformanceManagementRootList.jspa";
    } else {
        treeid1 = tree1.getSelectedItemId();
        url = basePath + "ea/specs/sajax_ea_getPerformanceManagementByParentId.jspa?id="+treeid1;
    }
    // treename1 = tree1.getItemText(treeid1);
    tree1.deleteChildItems(treeid1);

    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            var codeList = eval("(" + data + ")");
            for (var i = 0; i < codeList.length; i++) {
                tree1.insertNewChild(treeid1, codeList[i].id, codeList[i].name, 0, 0, 0, 0);
                tree1.setUserData(codeList[i].id, "parentId", codeList[i].parentId);
                tree1.setUserData(codeList[i].id, "key", codeList[i].key);
            }

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
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/performanceManagement/pmAdd.jsp";
        } else {
            var text = tree1.getItemText(treeid);
            // pId = tree1.getUserData(treeid, 'parentId');
            // document.location.replace(basePath+"page/WFJClient/pc/5l5C/office/businessTypeAdd.jsp?parentId="+treeid);
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/performanceManagement/pmAdd.jsp?parentId=" + treeid + "&parentName=" + text;
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
            prompt("请选择要修改的数据");
            return;
        }
        key = tree1.getUserData(treeid, 'key');
        document.location.href = basePath + "ea/specs/ea_getPerformanceManagementByKey.jspa?key=" + key;
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


        key = tree1.getUserData(treeid, 'key');
        var url = basePath
            + "ea/specs/sajax_ea_delPerformanceManagement.jspa?key=" + key;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
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