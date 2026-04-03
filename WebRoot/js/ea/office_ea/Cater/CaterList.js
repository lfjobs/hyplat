$(function() {
    $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    $('.JQueryflexme').flexigrid({
        height : 165,
        width : 'auto',
        minwidth : 30,
        title : '餐桌管理',
        minheight : 80,
        buttons : [{
            name : '添加',
            bclass : 'add',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }, {
            name : '删除',
            bclass : 'delete',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }, {
            name : '修改',
            bclass : 'edit',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '查询',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }, {
            name : '打印二维码',
            bclass : 'printer',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '设置每页显示条数',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }]
    });

    function action(com, grid) {
        switch (com) {
            case '添加' :
                caterID = "";
                $("input.JQuerypersonvalue").attr("checked", false);
                $("#companyName").attr("value",companyName);
                $("span#companyNames").text(companyName);
                $("span#deptNames").text(treePName);
                $("#deptName").attr("value",treePName);
                $t = $("table#catertable");
                $("#jqModel").jqmShow();
                break;
            case '删除' :
                if (ID == "") {
                    alert('请选择!');
                    return;
                }
                if (confirm("确定删除？")) {
                    $("#caterForm")
                        .attr("target", "hidden")
                        .attr(
                            "action",
                            basePath
                            + "ea/cater/ea_deleteCater.jspa?pageNumber="
                            + pNumber+"&caterID="+ID);
                    document.caterForm.submit.click();
                    $("tr#"+ID).remove();
                }

                break;
            case '修改' :
                if (ID == "") {
                    alert('请选择!');
                    return;
                }
                document.caterForms.reset();
                $t = $("table#caterForms");
                $("input#personName").val($("span#personName",$("tr#" + ID)).text());
                $("input#personID").val($("span#personID",$("tr#" + ID)).text());
                $("input#IDs").val(ID);
                if($("span#status",$("tr#" + ID)).text()=="使用中"){
                    $("select#statuss").val("0");
                }else {
                    $("select#statuss").val("1");
                }
                $("input#status").val($("span#status",$("tr#" + ID)).text())
                $("#jqModels").jqmShow();
                break;
            case '查询' :
                $("#jqModelcarSearch").jqmShow();
                break;
            case '打印二维码' :
                if (ID == "") {
                    alert('请选择!');
                    return;
                }
                var boardNo = $("#boardNo",$("tr#"+ID)).text();
                var ppID = $("#ppIDs",$("tr#"+ID)).text();
                window.open("/page/ea/main/office_ea/cater/qrcode.jsp?ppID="+ppID+"&boardNo="+boardNo+"&sccid="+sccid);
                break;
            case '设置每页显示条数' :
                var url = basePath
                    + "ea/cater/ea_getCaterList.jspa?search="
                    + search;
                numback(url);
                break;
        }
    }

    // 添加取消
    $("input.JQueryreturn").click(function() {
        $("#jqModel").jqmHide();
        re_load();
    });
    //修改取消
    $("input.JQueryreturns").click(function() {
        $("#jqModels").jqmHide();
    });

    // 单击事件
    $(".JQueryflexme tr[id]").click(function() {
        ID = this.id;
        if (personurl) {
            $("#mainframe").attr("src", personurl + ID);
        }
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
    });

    // 搜索窗口
    $(".close").click(function() {// 取消
        $("#jqModel").jqmHide();
        re_load();
    });

    // 修改保存
    $("input.JQuerySubmits").click(function() {
        if ($("form .error").length) {
            return;
        }
        $("#caterForms")
            .attr("target", "hidden")
            .attr(
                "action",
                basePath
                + "ea/cater/ea_updateCater.jspa?pageNumber="
                + pNumber + "&search=" + search);
        document.caterForms.submit.click();
        token = 2;

    });
    // 查询事件
    $("#searchCar").click(function() {
        $("#carSearchForm").attr(
            "action",
            basePath + "ea/carbaseinfo/ea_toSearch.jspa?pageNumber="
            + pNumber);
        document.carSearchForm.submit.click();
    });

});

$(function() {
    // 添加保存
    $("input.JQuerySubmit").click(function() {
        if($("#goodsNames").text()==""|| $("#goodsNames").text()== null){
            alert("请选择物品");
            return;
        }
        $(".put3").trigger("blur");
        if ($("form .error").length) {
            return;
        }
        if (caterID == "") {
            $("#caterForm").attr("target", "hidden").attr(
                "action",
                basePath + "ea/cater/ea_saveCater.jspa?pageNumber="
                + pNumber + "&search=" + search);
            document.caterForm.submit.click();
            document.caterForm.reset();
            token = 2;
            return;
        }
        $("#caterForm").attr("target", "hidden").attr(
            "action",
            basePath + "ea/cater/ea_saveCater.jspa?pageNumber="
            + pNumber + "&search=" + search);
        document.caterForm.submit.click();
        token = 2;
    });

    $("#DaoRuFan").click(function() {// 返回
        $("#bankJqm").jqmHide();
    });

    $("#DaoRuFanqd").click(function() {// 选择确定
        var checkopertionID = $("#checkopertionID", $("#bankJqm"))
            .attr("value");
        var checkform = $("#checkform", $("#bankJqm")).attr("value");
        var checkopertionName = $("#checkopertionName", $("#bankJqm"))
            .attr("value");
        var childopertionName = $("#childopertionName", $("#bankJqm"))
            .attr("value");
        var childopertionID = window.frames["daoRu"].opertionID;
        if (childopertionID == "") {
            alert("请选择")
            return;
        }
        var no = window.frames["daoRu"].$('tr#' + childopertionID)
            .find("span#" + checkopertionName).text();
        var childopertionName = window.frames["daoRu"].$('tr#'
            + childopertionID).find("span#" + childopertionName)
            .text();
        if (checkopertionID != "")
            $("#" + checkopertionID, $("#" + checkform)).attr("value",
                childopertionID).trigger("blur");
        if (checkopertionName != "") {
            $("#" + checkopertionName, $("#" + checkform)).attr(
                "value", childopertionName).trigger("blur");
        }
        if (checkopertionName == "staffName") {
            var final = no + "---" + childopertionName;
            $("#personName", $("#" + checkform)).attr(
                "value", final).trigger("blur");
        }
        $("#daoRu").attr("src", "");
        $("#bankJqm").jqmHide();
    });
});

// 刷新事件
function re_load() {
    if (token)
        document.location.href = basePath
            + "ea/cater/ea_getCaterList.jspa?pageNumber="
            + pNumber + "&pageForm.pageNumber="
            + $("#pageNumber").attr("value");
}

function importGY(attachSearchTable, checkopertionID, checkopertionName,
                  childopertionName, url) { // 打开页面
    if (checkopertionName == "bankNum") {
        var departmentID = $("#departmentID").attr("value");
        url = url + "?departmentID=" + departmentID + "&title1=title1";
    }
    url = url + "?title1=title1";
    $("#checkopertionID", $("#bankJqm")).attr("value", checkopertionID);
    $("#checkform", $("#bankJqm")).attr("value", attachSearchTable);
    $("#checkopertionName", $("#bankJqm")).attr("value", checkopertionName);
    $("#childopertionName", $("#bankJqm")).attr("value", childopertionName);
    $("#daoRu").attr("src", basePath + url);
    $("#bankJqm").jqmShow();
}

/** **********************************选择物品**************************************** */
$(document).ready(function() {
    var PPID = "";
    $("table#gotable tr[id]").live("click", function(event) {
        PPID = this.id;
        $("input.rauser", $(this)).attr("checked", "checked");
    });

    // 物品返回
    $("#JQueryreturngoods").click(function() {
        notoken = 0;
        var numes = "";
        $("#productName", $("table#searchgood")).attr("value", numes);
        $(":input#parms").attr("value", numes);
        $(".jqmWindow", $("#productForm")).jqmHide();
    });

// 根据物品名称查询
    $("#chaxun").click(function() {
        var borName = $("#productName", $("table#searchproduct")).val();
        $(":input#parms").val("parameter=" + borName);
        cx("parameter=" + borName);
    });

    // 导入数据（选择物品）
    $("#newG").click(function() {
        cx("");
        $(".jqmWindow", $("#productForm")).jqmShow();
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
    $("#qdpro").click(function() {
        if (PPID != "") {
            $("span#goodsNames",$("table#catertable")).text($("td#goodsName",$("tr #" + PPID)).text());
            $("input#goodsName",$("table#catertable")).val($("td#goodsName",$("tr #" + PPID)).text());
            $("input#ppID", $("table#catertable")).val(PPID);

            PPID="";
            $(".jqmWindow", $("#productForm")).jqmHide();
        } else {
            alert("请选择物品！");
        }
    });
    // 查询事件
    $("#searchCater").click(function() {
        $("#caterSearchForm").attr(
            "action",
            basePath + "ea/cater/ea_toSearch.jspa?pageNumber="
            + pNumber);
        document.caterSearchForm.submit.click();
    });
    function cx(typeCN){
        if (notoken) {
            alert("正在获取数据！请稍等");
            return;
        }
        notoken = 1;
        $("#wpsy").attr("title", 0);
        $("#wpxy").attr("title", 0);
        $("#wpzy").attr("title", 0);
        var searchurl = basePath
            + "ea/cater/sajax_ea_getProduct.jspa?";
        $.ajax({
            url : encodeURI(searchurl+ typeCN),
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
                $("span#wpzycount").text(zys);
                var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
                tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>";
                for (var i = 0; i < pageForm.list.length; i++) {
                    tabletr += "<tr style='cursor: hand;' id = "
                        + pageForm.list[i].ppID + ">";
                    tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
                        + pageForm.list[i].ppID + " name='check'/></td>";
                    tabletr += "<td id='goodsName' align='center'>"
                        +pageForm.list[i].goodsName+"</td>";
                    tabletr += " </tr>";
                }
                tabletr += " </table>";
                $("#body_02").html(tabletr);
                $("#body_02").show();

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