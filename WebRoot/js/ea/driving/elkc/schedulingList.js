$(document).ready(function () {

    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    // .jqDrag('.drag');// 添加拖拽的selector
    


    
    var query = "<form id='searchForm' method='post'  action='" + basePath + "ea/schedul/ea_toSearch.jspa?pageNumber='"
        + pNumber + "'>班次名称&nbsp;<input type='text' style='width:120px;' name='scheduling.className' />&nbsp;&nbsp;状态&nbsp;<select name='scheduling.status'><option value=''>全部</option><option value='00'>未启用</option><option value='01'>已启用</option></select><input type='submit' value=' 查询 ' class='input-button' id='tosearch'><input type='hidden' name='search' value='search'></form>";
    $('.mode').flexigrid({
        height: 350,
        width: 'auto',
        minwidth: 30,
        title: '班次维护' + query,
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
        },  {
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
                classId == ""
                $(".tt").text("添加班次");
                $("#jqModel").jqmShow();
                document.saveForm.reset();
                break;
            case '修改' :

                if (classId == "") {
                    alert('请选择!');
                    return;
                }
                $(".tt").text("修改班次");
                $("#jqModel").jqmShow();
                $(".classId").val(classId);
                $(".className").val($("tr#"+classId).find("#className").text());
                $(".status").val($("tr#"+classId).find("#status").text());
                $(".yz").attr("value",$("tr#"+classId).find("#reservationModeId").text())
                break;
            case '删除' :

                if (classId == "") {
                    alert('请选择!');
                    return;
                }
                if ($("tr#" + classId).find("#status").text() == "01") {
                    alert('已启用不可删除!');
                    return;

                }
                $.ligerDialog.confirm('确定要删除？', function (yes) {
                    if(yes) {
                        $.ajax({
                            type: "POST",
                            url: basePath + "ea/schedul/sajax_ea_deleteSchedul.jspa",
                            data: {
                                "scheduling.classId": classId
                            },
                            dataType: "json",
                            success: function (data) {

                                $.ligerDialog.success("删除模式成功！", "提示", function (yes) {
                                    window.location.href = basePath + "ea/schedul/ea_findSchedulingList.jspa";
                                });

                            }
                        });
                    }
                    });

                break;

            case '设置每页显示条数' :
                var url = basePath
                    + "ea/schedul/ea_findSchedulingList.jspa?search="
                    + search + "&date="
                    + new Date();
                numback(url);
                break;
        }
    }


    $(".mode tr[id]").dblclick(function () {
        classId = this.id;
        action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
    });
    $(".mode tr[id]").click(function () {
        classId = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

    });
    //保存班次
    $("#saveForm").Validform({
        tiptype:function(msg,o,cssctl){
            var objId = o.obj.attr("id");
            var errDivId = "err_" + objId;
            var objtip = $("#" + errDivId);
            cssctl(objtip,o.type);
            objtip.text(msg);
        },
        datatype:{
            "z2-20" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
        },
        callback:function(form){

            $.ajax({
                type:"POST",
                url:$("#saveForm").attr("action"),
                data:$("#saveForm").serialize(),
                dataType:"json",
                success: function(data) {
                    $.ligerDialog.success("班次保存成功！", "提示", function (yes) {
                        window.location.href = basePath + "ea/schedul/ea_findSchedulingList.jspa";
                    });
                }
            });
            return false;
        }
    });





});


function re_load() {

    if (token) {

        document.location.href = basePath
            + "ea/schedul/ea_findSchedulingList.jspa";

    }
}

