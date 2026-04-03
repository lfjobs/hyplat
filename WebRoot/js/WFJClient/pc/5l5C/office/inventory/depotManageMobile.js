$(document).ready(function () {
    tree = new dhtmlXTreeObject("businessTree", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");
    tree.insertNewChild("0", "001", companyName, 0, "depotmanage.png", "depotmanage.png", "depotmanage.png");
    /*tree.insertNewChild("0","002","资料仓库",0,"depotmanage.png","depotmanage.png","depotmanage.png");
    tree.insertNewChild("0","003","财务仓库",0,"depotmanage.png","depotmanage.png","depotmanage.png");*/
    tree.setOnClickHandler(function () {
        if (token) return;
        token = 1;
        $(".input01").attr("value", "");
        $("#desc").attr("html", "");
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        $("#codeName").attr("value", treename);
        tree.deleteChildItems(treeid);
        var getListCCodeurl = basePath + "ea/depotmanage/sajax_mobile_getListDepotmanageByPID.jspa?depotID=" + treeid + "&date=" + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(getListCCodeurl),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                }
                var depotManagelist = member.depotManagelist;

                if (null == depotManagelist) {
                    return;
                }
                for (var i = 0; i < depotManagelist.length; i++) {
                    if (depotManagelist[i].depotType == '1') {
                        tree.insertNewChild(treeid, depotManagelist[i].depotID, "(" + depotManagelist[i].depotCoding + ")" + depotManagelist[i].depotName, 0, "depotmanage.png", "depotmanage.png", "depotmanage.png");
                        //  tree.setStdImages("dept.gif");
                    } else if (depotManagelist[i].depotType == '2') {
                        tree.insertNewChild(treeid, depotManagelist[i].depotID, "(" + depotManagelist[i].depotCoding + ")" + depotManagelist[i].depotName, 0, "area.png", "area.png", "area.png");
                    } else if (depotManagelist[i].depotType == '3') {
                        tree.insertNewChild(treeid, depotManagelist[i].depotID, "(" + depotManagelist[i].depotCoding + ")" + depotManagelist[i].depotName, 0, "shelves.png", "shelves.png", "shelves.png");
                    } else if (depotManagelist[i].depotType == '4') {
                        tree.insertNewChild(treeid, depotManagelist[i].depotID, "(" + depotManagelist[i].depotCoding + ")" + depotManagelist[i].depotName, 0, "booth.png", "booth.png", "booth.png");
                    }
                    tree.setUserData(depotManagelist[i].depotID, "depotState", depotManagelist[i].depotState);
                    tree.setUserData(depotManagelist[i].depotID, "depotType", depotManagelist[i].depotType);
                    tree.setUserData(depotManagelist[i].depotID, "pId", depotManagelist[i].depotPID);
                }
                token = 0;

            },
            error: function cbf(data) {
                prompt("数据获取失败！")
            }
        });
    });

    //新建
    $(".add_p").click(function (event) {
        treeid = tree.getSelectedItemId();
        if (!treeid) {
            prompt("请先选择父级项目");
            return;
            //document.location.href = basePath + "page/WFJClient/pc/5l5C/office/inventory/depotManageMobileAdd.jsp";
        } else {
            var text = tree.getItemText(treeid);
            pId = tree.getUserData(treeid, 'pId');
            var depotType = tree.getUserData(treeid, "depotType");
            if (depotType == '4') {
                prompt("展位没有下级，请重新选择！");
                return;
            } else {
                document.location.href = encodeURI(basePath + "page/WFJClient/pc/5l5C/office/inventory/depotManageMobileAdd.jsp?typePID=" + treeid + "&parentName=" + text + "&depotType=" + depotType);
            }
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
    $(".edit_p").click(function () {
        var treeid = tree.getSelectedItemId();
        var pId = tree.getUserData(treeid, 'pId');
        var ptext = tree.getItemText(pId);
        if (!treeid) {
            prompt("请选择要修改的项目");
            return;
        }
        var depotState = tree.getUserData(treeid, "depotState");
        if (depotState == '02') {
            prompt("预设数据，无法修改！");
            return;
        }
        document.location.href = basePath + "/ea/depotmanagemobile/ea_getListDepotmanageByID.jspa?depotID=" + treeid + "&parentName=" + ptext;
    });

    //删除
    var del = 0;
    $(".del_p").click(function () {
        treeid = tree.getSelectedItemId();
        if (!treeid) {
            prompt("请选择要删除的数据");
            return;
        }
        var depotState = tree.getUserData(treeid, "depotState");
        if (depotState == '02') {
            prompt("预设数据，无法删除！");
            return;
        }
        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        del = 1;
        return false;
    });

    //关闭弹框
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
            + "ea/depotmanage/sajax_mobile_delDepotManage.jspa?";
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            data: {
                "depotID": treeid
            },
            success: function (data) {
                var falg = eval("(" + data + ")").falg;
                if (falg == "2") {
                    tree.deleteItem(treeid, true);
                } else if (falg == "1") {
                    prompt("有子库房无法删除！");
                    return;
                }
            },
            error: function (data) {
                prompt("删除失败")
            }
        });
    })

    //滚动
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
    });

    //选中
    $(document).on("click", ".ul li", function (event) {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
    });

    $(".upgrade").click(function () {
        var url = basePath
            + "ea/depotmanage/sajax_mobile_dataUpgrade.jspa?";
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                var falg = eval("(" + data + ")").falg;
                if (falg == "0") {
                    prompt("升级成功");
                } else if (falg == "1") {
                    document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                } else if (falg == "2") {
                    prompt("已升级！");
                } else if (falg == "3") {
                    prompt("升级失败！");
                }
            },
            error: function (data) {
                prompt("升级失败")
            }
        });
    });

});