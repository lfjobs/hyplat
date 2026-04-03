$(document).ready(function () {

    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    $('.address').flexigrid({
        height: 345,
        width: 'auto',
        minwidth: 30,
        checkbox: true,// 是否要多选框
        title: "批发产品佣金设计<form name='yjForm' id='yjForm' method='post'>" +
        "<input type='submit' name='submit' style='display: none' />" +
        "商品名称或条码：" +
        "<input name='search' value='" + search + "'/>" +
        "<input type='button' id='search' value='查询 ' />" +
        "</form>",
        minheight: 80,
        buttons: [{
            name: '佣金设计添加',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改佣金',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '查看',
            bclass: 'see',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '删除',
            bclass: 'delete',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: 'excel导出',
            bclass: 'printer',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });

    $("div.bDiv", $("div.flexigrid")).css("height", "410px");

    function action(com, grid) {
        switch (com) {
            case '佣金设计添加' :
                window.open(basePath
                    + "ea/wholesale/ea_toWholesaleAdd.jspa");
                break;
            case '修改佣金':
                var ppid = $('input[name="checkedId"]:checked').val();
                var barCode = $('input[name="checkedId"]:checked').parent().parent().parent().find(".barCode").text();
                var goodsName = $('input[name="checkedId"]:checked').parent().parent().parent().find(".goodsName").text();
                var showPrice = $('input[name="checkedId"]:checked').parent().parent().parent().find(".showPrice").text();
                if (typeof(ppid) == "undefined" || ppid == "") {
                    alert('请选择！');
                    return
                } else {
                    url = basePath + "ea/wholesale/ea_toWholesaleUpdate.jspa?" +
                        "productPackaging.ppID=" + ppid + "&productPackaging.barCode=" + barCode + "&productPackaging.goodsName=" + goodsName + "&productPackaging.price=" + showPrice
                    open(url);
                }
                break;
            case '查看':
                var ppid = $('input[name="checkedId"]:checked').val();
                var barCode = $('input[name="checkedId"]:checked').parent().parent().parent().find(".barCode").text();
                var goodsName = $('input[name="checkedId"]:checked').parent().parent().parent().find(".goodsName").text();
                var showPrice = $('input[name="checkedId"]:checked').parent().parent().parent().find(".showPrice").text();
                if (typeof(ppid) == "undefined" || ppid == "") {
                    alert('请选择！');
                    return
                } else {
                    url = basePath + "ea/wholesale/ea_toWholesaleUpdate.jspa?" +
                        "productPackaging.ppID=" + ppid + "&productPackaging.barCode=" + barCode + "&productPackaging.goodsName" +
                        "=" + goodsName + "&productPackaging.price=" + showPrice + "&flag=01"
                    open(url);
                }
                break;

            case '删除':
                var ppid = $('input[name="checkedId"]:checked').val();
                var wholesaleId = $('input[name="checkedId"]:checked').siblings(".wholesaleId").val();
                if (typeof(wholesaleId) == "undefined" || wholesaleId == "") {
                    alert('请选择！');
                    return
                } else {
                    if (confirm("确定删除吗?")) {
                        $.ajax({
                            type: "POST",
                            url: basePath + "ea/wholesale/sajax_ea_wholesaleDeleteById.jspa",
                            data: {
                                "wholesale.wholesaleId": wholesaleId,
                                "wholesale.ppid": ppid
                            },
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                var code = member.code;
                                if (code == "200") {
                                    alert("删除成功！");
                                    location.href = basePath + "ea/wholesale/ea_selectWholesaleList.jspa";
                                } else if (code = "201") {
                                    alert("该产品未设置零售价佣金不能删除!");
                                } else {
                                    console.log("删除异常！")
                                }
                            },
                            error: function (data) {
                                console.log("删除异常！");
                            },
                            dateType: "json"
                        });
                        return true;
                    }
                    return false;
                    //window.location.reload();//刷新当前页面
                }
                break;
            case 'excel导出':
                window.open(basePath + "ea/wholesale/ea_WholesaleShowExcel.jspa?");
                break;
        }
        ;
    }

    //用于阻止复选框的冒泡行为；
    $(".address input:checkbox[name='a']").click(function (event) {
        event.stopPropagation();

    });

    $("table tr[id]").click(function () {
        opertionID = this.id;
        productdesignID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

    });


    $("#search").click(function () {
        if ($("form .error").length) {
            return false;
        }
        $("#yjForm").attr(
            "action",
            basePath + "ea/wholesale/ea_selectWholesaleList.jspa?pageForm.pageNumber="
            + pNumber + "");
        document.yjForm.submit.click();
    });
});



