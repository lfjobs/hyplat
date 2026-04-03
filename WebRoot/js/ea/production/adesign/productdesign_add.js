$(document).ready(function() {
    // 为弹出框准备下拉表内容
    $(".jqmWindow").jqm({
        modal : true,//
        overlay : 20
        //
    }).jqmAddClose('.close');//

    // 各个弹出框的关闭功能
    $(".JQueryreturns").click(function() {
        notoken = 0;
        $(".jqmWindow").jqmHide();
    });

    // 切换
    $(".tab li").click(function() {

        if($(this).attr("id")=="showfunc"){
            //如果是点击的是功能介绍
            var goodsID = $("#gdid").val();
            if(goodsID==""){
                alert("请先填写产品名称");
                return;
            }else{
                getFunctionList(goodsID);
            }

        }

        $("." + $(".selected").attr("id")).addClass("hidcontent");
        $(".selected").removeClass("selected");

        $(this).addClass("selected");
        $("." + $(this).attr("id")).removeClass("hidcontent");

    });




    // 文本域默认值效果
    $(".defaults").live("focus", function() {
        if ($(this).val() == '结构') {
            $(this).val("");
            $(this).css("color", "#000");
        }

    }).live("blur", function() {
        if ($(this).val() == "") {
            $(this).val("结构");
            $(this).css("color", "#999");
        }

    });

    // 计算金额
    $(".jisuan").bind('input propertychange', function() {
        var a1 = $(this).val();
        if (isNaN(a1)) {
            // 非数字
            return;
        }

        var a2;

        if ($(this).attr("id") == "price") {
            a2 = $("#quantity").val();

        } else {
            a2 = $("#price").val();
        }
        $("#money").val(a1 * a2);

    });

    //选择父级项目
    $("#selectpr").click(function(){

        pcx("");

    });
    //选择物品
    $("#selgood").click(function(){

        cx("typeID=");

    });

    // 提交保存
    $(".save").click(function() {

        $("form :input:.ckTextLength").trigger("blur");
        $("form :input:.input3").trigger("blur");
        $("form :input:.posnum").trigger("blur");
        $("form :input:.positiveNumZ").trigger("blur");
        $("form :input:.put3").trigger("blur");
        if($("form .error").length)
        {
            return;
        }


        $("#mainForm").attr("target","hidden").attr("action",
            basePath + "ea/prodesign/ea_saveProducts.jspa?fiveClear="+fiveClear+"&category=1");
        document.mainForm.submit.click();
        token = 2;

    });


    // 科目查询
    $(document).ready(function() {
        tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
        tree.enableDragAndDrop(false);
        tree.enableHighlighting(1);
        tree.enableCheckBoxes(0);
        tree.enableThreeStateCheckboxes(false);
        tree.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree.insertNewChild("0", "002", "科目树", 0, 0, 0, 0);
        getChildItem(3);

        tree.setOnClickHandler(function() {

            getChildItem(1);

        });

        tree.setOnDblClickHandler(function() {
            getChildItem(2);

        });

    });

    // 项目产品分类
    $(document).ready(function() {
        tree2 = new dhtmlXTreeObject("projectTree", "100%", "100%", 0);
        tree2.enableDragAndDrop(false);
        tree2.enableHighlighting(1);
        tree2.enableCheckBoxes(0);
        tree2.enableThreeStateCheckboxes(false);
        tree2.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree2.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree2.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree2.insertNewChild("0", "scode201410284shpd9x4fa0000000005",
            "项目产品分类", 0, 0, 0, 0);
        getProjectItem(3);
        tree2.setOnClickHandler(function() {

            getProjectItem(1);

        });

        tree2.setOnDblClickHandler(function() {
            getProjectItem(2);

        });

    });

//产品类目
	 $(document).ready(function() {
	 tree1 = new dhtmlXTreeObject("industryTree", "100%", "100%", 0);
	 tree1.enableDragAndDrop(false);
	 tree1.enableHighlighting(1);
	 tree1.enableCheckBoxes(1);
	 tree1.enableThreeStateCheckboxes(true);
	 tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
	 tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
	 tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
	 tree1.insertNewChild("0", industryId, "产品类目",
	 0, 0, 0, 0);




         //分类保存
         $(".savecate").click(function(){

            var checkid= tree1.getAllChecked();
            var sp = checkid.split(",");
            var checkname = "";
            for(var i = 0;i<sp.length;i++){
               var level = tree1.getLevel(sp[i]);
               if(level!=3) {
                   sp = $.grep(sp, function (value) {
                       return value != sp[i];
                   });
               }
               checkname+= tree1.getItemText(sp[i])+",";

            }
            $("#procate").val(sp.join(","));
            if(checkname.indexOf(",")){
                checkname = checkname.substring(0,checkname.length-1);
            }
            $("#industry").hide();
            $("#catename").val(checkname);

         });
         $(".procate").click(function(){
             getIndustryItem();

         });

	 });

    // 物品
    $(document).ready(function() {
        tree3 = new dhtmlXTreeObject("gtree", "100%", "100%", 0);
        tree3.enableDragAndDrop(false);
        tree3.enableHighlighting(1);
        tree3.enableCheckBoxes(0);
        tree3.enableThreeStateCheckboxes(false);
        tree3.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree3.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree3.loadXML(basePath + "js/tree/common/tree_b.xml");
        var getcodeurl = basePath
            + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
            + new Date().toLocaleString();
        tree3.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0,
            0, 0, 0);
        $.ajax({
            url : encodeURI(getcodeurl),
            type : "get",
            async : false,
            dataType : "json",
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }

                var codeList = member.codeList;
                if (null == codeList) {
                    return;
                }
                for (var i = 0; i < codeList.length; i++) {

                    tree3.insertNewChild("scode20101014v5zed7cukk0000000002",
                        codeList[i].codeID, codeList[i].codeValue, 0, 0, 0,
                        0);
                    tree3.setUserData(codeList[i].codeID, "codeNumber",
                        codeList[i].codeNumber);

                }
            },
            error : function cbf(data) {
                alert("数据获取失败！");
            }
        });
        tree3.setOnClickHandler(function() {

            treeid = tree3.getSelectedItemId();
            treename = tree3.getItemText(treeid);

            if (treeid != "scode20101014v5zed7cukk0000000002") {
                $(":input#parms").val("typeID=" + treename);
                cx("typeID=" + treename);
                return;
            }

        });

        // 双击选中物品
        $("table#gotable tr[id]").live("click", function(event) {
            var b = $("input.ragood", $(this)).attr("checked");
            $("input.ragood", $(this)).attr("checked", !b);
        });

        // 复选框选中物品
        $(".ragood").live("click", function(event) {
            var b = $(this).attr("checked");
            $(this).attr("checked", !b);
        });



        // 上一页
        $("#wpsy").click(function() {
            var sy = $("#wpsy").attr("title");
            if (sy != 0) {
                var typeName = $(":input#parms").val();
                var typeCN = typeName + "&pageForm.pageNumber=" + sy;
                cx(typeCN);
            } else {
                alert("已是首页！");
            }
        });

        // 下一页
        $("#wpxy").click(function() {
            var xy = $("#wpxy").attr("title");
            if (xy != 0) {
                var typeName = $(":input#parms").val();
                var typeCN = typeName + "&pageForm.pageNumber=" + xy;
                cx(typeCN);
            } else {
                alert("已是尾页！");
            }
        });

        // 根据输入的物品编号或物品名称查询
        $("input#searchGood").click(function() {
            var typeName = $("#typeID", $("table#searchgood")).val();
            $(":input#parms").val("parameter=" + typeName);

            cx("parameter=" + typeName);
        });

        // 选择物品后确定
        $("#selectGood").click(function() {

            if ($("[name='check']").is(':checked')) {
                var goodsID = $("input[name='check']:checked").val();
                $("#gotable tr#" + goodsID).find("td").each(function() {
                    $("table#productbl").find("input." + $(this).attr("id"))
                        .val($(this).text());
                    if ($(this).attr("id") == "photoPath") {
                        $("#image").attr("src", basePath + $(this).text());
                    }

                });
                $("#goods").hide();
            } else {
                alert("请选择物品");
            }
        });

        // 新增物品
        $(".xzwp").click(function() {
            window
                .open(basePath
                    + "ea/gooddesign/ea_getGoodDesignList.jspa?");
        });

    });


    //调用产品
    $(function(){


        // 双击选中产品
        $("table#protable tr[id]").live("click", function(event) {
            var b = $("input.raporducts", $(this)).attr("checked");
            $("input.raporducts", $(this)).attr("checked", !b);
        });

        // 单选框选中物品
        $(".raporducts").live("click", function(event) {
            var b = $(this).attr("checked");
            $(this).attr("checked", !b);
        });



        // 上一页
        $("#wpsyp").click(function() {
            var sy = $("#wpsyp").attr("title");
            if (sy != 0) {
                var typeName = $(":input#parameter").val();
                var typeCN = "&parameter=" +typeName + "&pageForm.pageNumber=" + sy;
                pcx(typeCN);
            } else {
                alert("已是首页！");
            }
        });

        // 下一页
        $("#wpxyp").click(function() {
            var xy = $("#wpxyp").attr("title");
            if (xy != 0) {
                var typeName = $(":input#parameter").val();
                var typeCN = "&parameter=" +typeName + "&pageForm.pageNumber=" + xy;
                pcx(typeCN);
            } else {
                alert("已是尾页！");
            }
        });

        // 根据输入的产品编号或产品名称查询
        $("input#searchProduct").click(function() {
            var typeName = $("#parameter", $("table#searchpro")).val();
            pcx("&parameter=" + typeName);
        });

        // 选择产品确定
        $("#selectProduct").click(function() {

            if ($("[name='check']").is(':checked')) {
                var ppID = $("input[name='check']:checked").val();
                $("#ppid").val(ppID);
                $("#pp").val($("#protable tr#" + ppID).find("#goodsName").text());

                $("#products").hide();

            } else {
                alert("请选择项目产品");
            }
        });



    });



});


/** **********************************往来单位**************************************** */
$(document)
    .ready(
        function() {

            treedw = new dhtmlXTreeObject("dwTree", "100%", "100%", 0);
            treedw.enableDragAndDrop(false);
            treedw.enableHighlighting(1);
            treedw.enableCheckBoxes(0);
            treedw.enableThreeStateCheckboxes(false);
            treedw.setSkin(basePath + 'js/tree/dhx_skyblue');
            treedw.setImagePath(basePath + "js/tree/codebase/imgs/");
            treedw.loadXML(basePath + "js/tree/common/tree_b.xml");
            var getcodeurl = basePath
                + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110224xpd2t2jvda0000000002&date="
                + new Date().toLocaleString();
            treedw.insertNewChild(0,
                "scode20110224xpd2t2jvda0000000002", "单位类别", 0, 0,
                0, 0);
            $.ajax({
                url : encodeURI(getcodeurl),
                type : "get",
                async : true,
                dataType : "json",
                success : function cbf(data) {
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath
                            + "page/ea/not_login.jsp";
                    }
                    var codeList = member.codeList;
                    if (null == codeList) {
                        return;
                    }
                    for ( var i = 0; i < codeList.length; i++) {

                        treedw.insertNewChild(
                            "scode20110224xpd2t2jvda0000000002",
                            codeList[i].codeID,
                            codeList[i].codeValue, 0, 0, 0, 0);
                        treedw.setUserData(codeList[i].codeID,
                            "codeNumber", codeList[i].codeNumber);

                    }
                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
            treedw
                .setOnClickHandler(function() {

                    treeid = treedw.getSelectedItemId();
                    treename = treedw.getItemText(treeid);

                    var typeName = $("input#ccompanyIDs",
                        $("table#searchcompany")).val();

                    if (treeid == "scode20110224xpd2t2jvda0000000002") {
                        treename = "";
                    }
                    $(":input#dwparms").val(treename);
                    cxwldw("contactCompany.companyName=" + typeName
                        + "&cconnection.contactConnections="
                        + treename);
                    return;

                });

            var contactcID = "";// 已经添加到往来单位的ID
            var ccID = "";// ccompanyID
            $("table#gostable tr[id]").live("click", function(event) {
                contactcID = this.id;
                ccID = this.title;
                $("input.ra", $(this)).attr("checked", "checked");
            });

            // 选择往来单位
            $(".wanladw")
                .live("click",
                    function() {
                        $("#clicktr").val(
                            $(this).parent().parent()
                                .parent().attr("id"));
                        $(".jqmWindow", $("#selectcompanyForm"))
                            .jqmShow();
                        cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
                    });

            // 新增往来单位
            $(".xzdw")
                .live("click",
                    function() {
                        window
                            .open(basePath
                                + "ea/contactcompany/ea_getListContactCompany.jspa?");
                    });

            // 添加所选中的往来单位
            $("#qdcompany").click(
                function() {

                    if (contactcID != "") {

                          var ccompanyname =  $("tr#" + contactcID).find(
                                    "#ccompanyname")
                                    .text();


                          $("#spcom").val(ccompanyname);
                        $("#spcomID").val(contactcID);

                        $("#companyjqModel")
                            .jqmHide();

                    } else {
                        alert("请选择往来单位！");
                    }
                });
            //选择往来单位
            $("#selccom").click(function(){
                $("#companyjqModel")
                    .jqmShow();
                cxwldw("contactCompany.companyName=&cconnection.contactConnections=");

            });

            // 根据输入的往来单位名称查询
            $("input#searchcc").click(
                function() {
                    contactcID = "";
                    var typeName = $("input#ccompanyIDs",
                        $("table#searchcompany")).val();
                    var contactConnections = $(":input#dwparms")
                        .val();

                    cxwldw("contactCompany.companyName=" + typeName
                        + "&cconnection.contactConnections="
                        + contactConnections);
                });

            // 上一页
            $("#dwsy")
                .click(
                    function() {
                        var sy = $("#dwsy").attr("title");
                        if (sy != 0) {
                            contactcID = "";
                            var typeName = $(
                                "input#ccompanyIDs",
                                $("table#searchcompany"))
                                .val();
                            var contactConnections = $(
                                ":input#dwparms").val();
                            var typeCN = "contactCompany.companyName="
                                + typeName
                                + "&cconnection.contactConnections="
                                + contactConnections
                                + "&pageForm.pageNumber="
                                + sy;
                            cxwldw(typeCN);
                        } else {
                            alert("已是首页！");
                        }
                    });

            // 下一页
            $("#dwxy")
                .click(
                    function() {
                        var xy = $("#dwxy").attr("title");
                        if (xy != 0) {
                            contactcID = "";
                            var typeName = $(
                                "input#ccompanyIDs",
                                $("table#searchcompany"))
                                .val();
                            var contactConnections = $(
                                ":input#dwparms").val();
                            var typeCN = "contactCompany.companyName="
                                + typeName
                                + "&cconnection.contactConnections="
                                + contactConnections
                                + "&pageForm.pageNumber="
                                + xy;
                            cxwldw(typeCN);
                        } else {
                            alert("已是尾页！");
                        }
                    });

            // ajax查询往来单位列表
            function cxwldw(typeCN) {
                if (notoken) {
                    alert("正在获取数据！请稍等");
                    return;
                }
                notoken = 1;
                $("#dwsy").attr("title", 0);
                $("#dwxy").attr("title", 0);
                $("#dwzy").attr("title", 0);
                var searchurl = basePath
                    + "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
                $
                    .ajax({
                        url : encodeURI(searchurl + typeCN
                            + "&date="
                            + new Date().toLocaleString()),
                        type : "get",
                        async : true,
                        dataType : "json",
                        success : function cbf(data) {
                            var member = eval("(" + data + ")");
                            var nologin = member.nologin;
                            if (nologin) {
                                document.location.href = basePath
                                    + "page/ea/not_login.jsp";
                            }
                            var pageForm = member.pageForm;

                            if (pageForm == null) {
                                $("#body_02cc").html("");
                                alert("没有数据");
                                notoken = 0;
                                return;
                            }

                            var dqy = pageForm.pageNumber;// 当前页
                            var zys = pageForm.pageCount;// 总页数
                            if (dqy > 1) {
                                $("#dwsy").attr("title", dqy - 1);
                            }
                            if (dqy < zys) {
                                $("#dwxy").attr("title", dqy + 1);
                            }
                            $("span#dwzycount").text(zys);
                            var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
                            tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
                            tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
                            tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
                            tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
                            tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";

                            for ( var i = 0; i < pageForm.list.length; i++) {
                                tabletr += "<tr style='cursor: hand;' title="
                                    + pageForm.list[i].ccompanyID
                                    + " id = "
                                    + pageForm.list[i].ccompanyID
                                    + ">";
                                tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
                                    + pageForm.list[i].contactConnectionID
                                    + " name='checkradio'/></td>";
                                tabletr += "<td id='ccompanyname' align='center'>"
                                    + pageForm.list[i].companyName
                                    + "</td>";
                                tabletr += "<td id='contactConnections' align='center'>"
                                    + pageForm.list[i].contactConnections
                                    + "</td>";
                                tabletr += "<td id='industryType' align='center'>"
                                    + pageForm.list[i].industryType
                                    + "</td>";
                                tabletr += "<td id='companyTel'  align='center'>"
                                    + pageForm.list[i].companyTel
                                    + "</td>";
                                tabletr += "<td id='cresponsible' align='center'>"
                                    + pageForm.list[i].cresponsible
                                    + "</td>";
                                tabletr += "<td id='responsibleTel' align='center'>"
                                    + pageForm.list[i].responsibleTel
                                    + "</td>";

                                tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
                                    + pageForm.list[i].ccompanyID
                                    + "</td>";
                                tabletr += " </tr>";
                            }
                            tabletr += " </table>";
                            $("#body_02cc").html(tabletr);
                            $("#body_02cc").show();
                            // alert("数据加载成功");
                            notoken = 0;
                            window.status = "数据加载成功";
                        },
                        error : function cbf(data) {
                            notoken = 0;
                            alert("数据获取失败！");
                        }
                    });
            }
        });
// ajax查询物品列表
function cx(typeCN) {

    if (notoken) {
        alert("正在获取数据！请稍等");
        return;
    }
    notoken = 1;
    $("#wpsy").attr("title", 0);
    $("#wpxy").attr("title", 0);
    $("#wpzy").attr("title", 0);
    var searchurl = basePath
        + "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
    $.ajax({
        url : encodeURI(searchurl + typeCN + "&date="
            + new Date().toLocaleString()),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath + "page/ea/not_login.jsp";
            }
            var pageForm = member.pageForm;
            if (pageForm == null) {
                alert("没有数据");
                notoken = 0;
                return;
            }
            var dqy = pageForm.pageNumber;// 当前页
            var zys = pageForm.pageCount;// 总页数
            if (dqy > 1) {
                $("#wpsy").attr("title", dqy - 1);
            }
            if (dqy < zys) {
                $("#wpxy").attr("title", dqy + 1);
            }
            //
            $("span#wpzycount").text(zys);
            var tabletr = "<table width='100%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
            tabletr += "<table width='100%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
            tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
            tabletr += "<th align='center'  bgcolor='#E4F1FA'>物品条码</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th>"
                + "<th align='center' bgcolor='#E4F1FA'>型号</th>";
            +"<th align='center' bgcolor='#E4F1FA'>规格</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>规格</th>"
                + "<th align='center' bgcolor='#E4F1FA'>单位</th></tr>";
            for (var i = 0; i < pageForm.list.length; i++) {
                tabletr += "<tr style='cursor: hand;' id = "
                    + pageForm.list[i].goodsID + ">";
                tabletr += "<td id='check' align='center'>"
                    + "<input type ='radio' class='ragood' value="
                    + pageForm.list[i].goodsID + " name='check'/></td>";
                tabletr += "<td id='tradeCode' align='center'>"
                    + pageForm.list[i].tradeCode + "</td>";
                tabletr += "<td id='barCode' align='center'>"
                    + pageForm.list[i].barCode + "</td>";
                tabletr += "<td id='goodsCoding' align='center'>"
                    + pageForm.list[i].goodsCoding + "</td>";
                tabletr += "<td id='goodsName'  align='center'>"
                    + pageForm.list[i].goodsName + "</td>";
                tabletr += "<td id='brand' align='center'>"
                    + pageForm.list[i].brand + "</td>";
                tabletr += "<td id='model' align='center'>"
                    + pageForm.list[i].model + "</td>";
                tabletr += "<td id='standard' align='center'>"
                    + pageForm.list[i].standard + "</td>";
                tabletr += "<td id='variableID' align='center'>"
                    + pageForm.list[i].variableID + "</td>";
                tabletr += "<td id='photoPath' align='center' style='display:none;'>"
                    + pageForm.list[i].photoPath + "</td>";
                tabletr += "<td id='goodsID' align='center' style='display:none;'>"
                    + pageForm.list[i].goodsID + "</td>";
                tabletr += "<td id='tradeName' align='center' style='display:none;'>"
                    + pageForm.list[i].tradeName + "</td>";
                tabletr += "<td id='tradeID' align='center' style='display:none;'>"
                    + pageForm.list[i].tradeID + "</td>";
                tabletr += "<td id='typeID' align='center' style='display:none;'>"
                    + pageForm.list[i].typeID + "</td>";
                tabletr += "<td id='isScale' align='center' style='display:none;'>"
                    + pageForm.list[i].isScale + "</td>";
                tabletr += " </tr>";
            }
            tabletr += " </table>";
            $("#body_02").html(tabletr);
            $("#body_02").show();
            // alert("数据加载成功");
            notoken = 0;
            window.status = "数据加载成功";
        },
        error : function cbf(data) {
            notoken = 0;
            alert("数据获取失败！");
        }
    });
}




// 查询项目产品
function pcx(typeCN) {
    if (notoken) {
        alert("正在获取数据！请稍等");
        return;
    }
    notoken = 1;
    $("#wpsyp").attr("title", 0);
    $("#wpxyp").attr("title", 0);
    $("#wpzyp").attr("title", 0);
    var searchurl = basePath
        + "ea/prodesign/sajax_ea_getProductDesignList.jspa?fiveClear="+fiveClear+"&type=01";
    $.ajax({
        url : encodeURI(searchurl + typeCN + "&date="
            + new Date().toLocaleString()),
        type : "get",
        async : true,
        dataType : "json",
        data:{
            iscall:"call",
            search:"search"
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            if (pageForm == null) {
                alert(pageForm+"没有数据");
                notoken = 0;
                return;
            }
            var dqy = pageForm.pageNumber;// 当前页
            var zys = pageForm.pageCount;// 总页数
            if (dqy > 1) {
                $("#wpsyp").attr("title", dqy - 1);
            }
            if (dqy < zys) {
                $("#wpxyp").attr("title", dqy + 1);
            }

            $("span#wpzycountp").text(zys);
            var tabletr ="";
            tabletr += "<table width='100%' align='center' id='protable' cellpadding='0' cellspacing='0' class='table' style='margin-top:15px;'>";
            tabletr += " <tr><th height='27' align='center' bgcolor='#E4F1FA'>选择</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
            tabletr += "<th align='center'  bgcolor='#E4F1FA'>产品条码</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>产品编码</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>产品名称</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th>";
            tabletr	+= "<th align='center' bgcolor='#E4F1FA'>型号</th>";
            tabletr += "<th align='center' bgcolor='#E4F1FA'>规格</th>";
            tabletr	+= "<th align='center' bgcolor='#E4F1FA'>单位</th>";
            tabletr	+= "<th align='center' bgcolor='#E4F1FA'>数量</th>";
            tabletr	+= "<th align='center' bgcolor='#E4F1FA'>单价</th>";
            tabletr	+= "<th align='center' bgcolor='#E4F1FA'>金额</th></tr>";

            for (var i = 0; i < pageForm.list.length; i++) {
                tabletr += "<tr style='cursor: hand;' id = "
                    + pageForm.list[i].ppID + ">";
                tabletr += "<td height='27' id='check' align='center'>"
                    + "<input type ='radio' class='raporducts' value="
                    + pageForm.list[i].ppID + " name='check'/></td>";
                tabletr += "<td id='tradeCode' align='center'>"
                    + pageForm.list[i].tradeCode + "</td>";
                tabletr += "<td id='barCode' align='center'>"
                    + pageForm.list[i].barCode + "</td>";
                tabletr += "<td id='productCode' align='left'>"
                    + pageForm.list[i].productCode + "</td>";
                tabletr += "<td id='goodsName'  align='center'>"
                    + pageForm.list[i].goodsName + "</td>";
                tabletr += "<td id='brand' align='center'>"
                    + pageForm.list[i].brand + "</td>";
                tabletr += "<td id='model' align='center'>"
                    + pageForm.list[i].model + "</td>";
                tabletr += "<td id='standard' align='center'>"
                    + pageForm.list[i].standard + "</td>";
                tabletr += "<td id='variableID' align='center'>"
                    + pageForm.list[i].variableID + "</td>";
                tabletr += "<td id='quantity' align='center'>"
                    + pageForm.list[i].quantity + "</td>";
                tabletr += "<td id='price' align='center'>"
                    + pageForm.list[i].price + "</td>";
                tabletr += "<td id='money' align='center'>"
                    + pageForm.list[i].money + "</td>";
                tabletr += " </tr>";
            }
            tabletr += " </table>";
            $("#body_03").html(tabletr);
            $("#body_03").show();
            notoken = 0;
            window.status = "数据加载成功";
        },
        error : function cbf(data) {
            notoken = 0;
            alert("数据获取失败！");
        }
    });
}











// 获取子科目
function getChildItem(type) {
    if (type == 3) {
        treeid = "002";
    } else {
        treeid = tree.getSelectedItemId();
    }
    treename = tree.getItemText(treeid);

    tree.deleteChildItems(treeid);
    var getListCSbjectsurl = basePath
        + "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="
        + treeid + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCSbjectsurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var subjectsList = member.subjectsList;

            if (subjectsList.length == 0) {
                if (type == 2) {
                    $("#km").val(treename);
                    $("#kmid").val(treeid);
                    $("#subject").hide();
                }
                return;
            }
            for (var i = 0; i < subjectsList.length; i++) {
                tree.insertNewChild(treeid, subjectsList[i].subjectsID,
                    subjectsList[i].subjectsName, 0, 0, 0, 0);

            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}

// 项目产品分类
function getProjectItem(type) {
    if (type == 3) {
        treeid2 = "scode201410284shpd9x4fa0000000005";
    } else {
        treeid2 = tree2.getSelectedItemId();
    }
    treename2 = tree2.getItemText(treeid2);
    tree2.deleteChildItems(treeid2);
    var getListCCodeurl = basePath
        + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid2
        + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCCodeurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var codeList = member.codeList;

            if (codeList.length == 0) {
                if (type == 2) {
                    $("#pro").val(treename2);
                    $("#project").hide();

                    var b = new Array();
                    b.unshift(">");
                    b.unshift(treename2);
                    var parentid = treeid2;
                    var parentname = "";

                    while (true) {

                        parentid = tree2.getParentId(parentid);

                        if (parentid != "scode201410284shpd9x4fa0000000005") {

                            parentname = tree2.getItemText(parentid);
                            b.unshift(">");
                            b.unshift(parentname);
                        } else {
                            var str = b.join("").substring(0,
                                b.join("").length - 1);

                            $("#mulpro").val(str);
                            break;
                        }

                    }
                }
                return;
            }
            for (var i = 0; i < codeList.length; i++) {

                tree2.insertNewChild(treeid2, codeList[i].codeID,
                    codeList[i].codeSn + codeList[i].codeValue, 0, 0, 0, 0);

            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}

// 获取行业
// 获取行业
function getIndustryItem() {
    if(tree1.hasChildren(industryId)){
        return;
    }
    var getListCCodeurl = basePath
        + "ea/prodesign/sajax_ea_getProCateList.jspa?codePID=" + industryId
        + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCCodeurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var codeList = member.codeList;

            for (var i = 0; i < codeList.length; i++) {
                if(codeList[i].codePID==industryId) {

                    tree1.insertNewChild(codeList[i].codePID, codeList[i].codeID,
                        codeList[i].codeValue, 0, 0, 0, 0);
                }

            }
            for (var i = 0; i < codeList.length; i++) {
                if(codeList[i].codePID!=industryId) {

                    tree1.insertNewChild(codeList[i].codePID, codeList[i].codeID,
                        codeList[i].codeValue, 0, 0, 0, 0);
                }


            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}

//获取功能介绍列表ajax
function getFunctionList(goodsID) {
    var url = basePath + "ea/prodesign/sajax_ea_ajaxGoodFunction.jspa?d="+new Date();
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        asycn : false,
        data : {
            "productPackaging.goodsID" : goodsID

        },
        success : function(data) {

            var mem = eval("(" + data + ")");
            var functionlist = mem.functionlist;
            var maplist = mem.maplistjs;
            var str = "";
            for (var i = 0; i < functionlist.length; i++) {
                str+="<strong>"+functionlist[i].name+"</strong>";
                str+="</br></br>";

                $.each(maplist,function(key,values){
                    if(key==i+1){
                        str+=values;
                        str+"</br>";
                    }

                });
            }
            $(".showfunc").html(str);

        },
        error : function(data) {
            alert("获取功能介绍失败");
        }

    });
}

function re_load() {
    window.opener.location.href=window.opener.location.href;
    window.close();

}

