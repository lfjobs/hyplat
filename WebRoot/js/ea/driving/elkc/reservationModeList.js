$(document).ready(function () {

    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    // .jqDrag('.drag');// 添加拖拽的selector
    var query = "<form id='searchForm' method='post' action='" + basePath + "ea/reservmode/ea_toSearch.jspa?pageNumber='"
        + pNumber + "'>模式名称&nbsp;<input type='text' style='width:120px;' name='reservationMode.modeName' />&nbsp;状态&nbsp;<select name='reservationMode.status'><option value=''>全部</option><option value='00'>未启用</option><option value='01'>已启用</option></select><input type='submit' value=' 查询 ' class='input-button' id='tosearch'><input type='hidden' name='search' value='search'></form>";
    $('.mode').flexigrid({
        height: 350,
        width: 'auto',
        minwidth: 30,
        title: '时间段模式' + query,
        minheight: 350,
        buttons: [{
            name: '添加',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改',
            bclass: 'edit',
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
            name: '设置每页显示条数',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });

    function action(com, grid) {
        switch (com) {
            case '添加' :
                reservationModeId = "";
                document.location.href = basePath
                    + "ea/reservmode/ea_reservationModeEdit.jspa";

                break;
            case '修改' :

                if (reservationModeId == "") {
                    alert('请选择!');
                    return;
                }
                document.location.href = basePath
                    + "ea/reservmode/ea_reservationModeEdit.jspa?reservationMode.reservationModeId=" + reservationModeId;
                break;
            case '删除' :

                if (reservationModeId == "") {
                    alert('请选择!');
                    return;
                }
                if ($("tr#" + reservationModeId).find("#status").text() == "01") {
                    alert('已启用不可删除!');
                    return;

                }
                $.ligerDialog.confirm('确定要删除？', function (yes) {
                    if (yes) {
                        $.ajax({
                            type: "POST",
                            url: basePath + "ea/reservmode/sajax_ea_deleteMode.jspa",
                            data: {
                                "reservationMode.reservationModeId": reservationModeId
                            },
                            dataType: "json",
                            success: function (data) {

                                $.ligerDialog.success("删除模式成功！", "提示", function (yes) {
                                    window.location.href = basePath + "ea/reservmode/ea_findReservationModeList.jspa?da=" + new Date();
                                });

                            }
                        });
                    }
                });
                break;
            case '查询' :

                break;

            case '设置每页显示条数' :
                var url = basePath
                    + "ea/reservmode/ea_findReservationModeList.jspa?search="
                    + search + "&date="
                    + new Date();
                numback(url);
                break;
        }
    }


    $(".mode tr[id]").dblclick(function () {
        reservationModeId = this.id;
        action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
    });
    $(".mode tr[id]").click(function () {
        reservationModeId = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

    });

});


function re_load() {

    if (token) {

        document.location.href = basePath
            + "ea/reservmode/eea_findReservationModeList.jspa";

    }
}

