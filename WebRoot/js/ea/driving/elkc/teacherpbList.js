$(document).ready(function () {

    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    // .jqDrag('.drag');// 添加拖拽的selector
    var query = "<form id='searchForm' method='post'  action='" + basePath + "ea/teacherpb/ea_toSearch.jspa?pageNumber='"
        + pNumber + "'>教练姓名&nbsp;<input type='text' style='width:120px;' name='teacherRScheduling.teacherName' />&nbsp;身份证&nbsp;<input type='text' style='width:120px;' name='teacherRScheduling.idcard' />&nbsp;班次&nbsp;<input type='text' style='width:120px;' name='teacherRScheduling.className' />&nbsp;状态&nbsp;<select name='teacherRScheduling.classId' ><option value=''>全部</option><option value='00'>尚未排班</option><option value='01'>已排班</option></select><input type='submit' value=' 查询 ' class='input-button' id='tosearch'><input type='hidden' name='search' value='search'></form>";
    $('.mode').flexigrid({
        height: 350,
        width: 'auto',
        minwidth: 30,
        title: '教练排班' + query,
        minheight: 350,
        buttons: [{
            name: '添加排班',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改班次',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '取消排班',
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
            case '添加排班' :
                document.saveForm.reset();
                var checkinput = document.getElementsByName("checkinput");
                var length = 0;
                var teacherid = "";
                var trsId = "";
                for (var i = 0; i < checkinput.length; i++) {
                    if (checkinput[i].checked) {
                        length += 1;
                        teacherid += checkinput[i].value + ",";
                          trsId = $("tr#" + teacherid).find("#trsId").text();
                        if(trsId!=""){

                            break;
                        }
                    }
                }
                if (length == 0) {
                    alert("请选择教练");
                    return;
                }
                if (trsId != "") {

                    alert("请不要重复给教练排班,若想修改请点击修改班次");
                    return;
                }
                $("#teachers").val(teacherid.substring(0, teacherid.length - 1));

                $(".tt").text("添加排班");
                $("#jqModel").jqmShow();
                break;
            case '修改班次' :

                var checkinput = document.getElementsByName("checkinput");
                var length = 0;

                for (var i = 0; i < checkinput.length; i++) {
                    if (checkinput[i].checked) {
                        length += 1;
                        teacherid = checkinput[i].value;

                    }
                }

                if (length == 0) {
                    alert("请选择");
                    return;
                }
                if (length > 1) {
                    alert("一次只能取消一个教练的排班");
                    return;
                }
                var trsId = $("tr#" + teacherid).find("#trsId").text();
                var status = $("tr#" + teacherid).find("#status").text();
                var classId = $("tr#" + teacherid).find("#classId").text();
                if (trsId == "") {

                    alert("尚未排班，无需取消");
                    return;
                }
                if (status == "01") {

                    alert("已启用，不能取消排班");
                    return;
                }
                $("#teachers").val(teacherid);
                $("input[name='classId'][value='" + classId + "']").attr("checked", true);
                $(".tt").text("修改班次");
                $("#jqModel").jqmShow();

                break;
            case '取消排班' :
                var checkinput = document.getElementsByName("checkinput");
                var length = 0;
                var teacherid = "";
                for (var i = 0; i < checkinput.length; i++) {
                    if (checkinput[i].checked) {
                        length += 1;
                        teacherid = checkinput[i].value;
                    }
                }
                if (length == 0) {
                    alert("请选择");
                    return;
                }
                if (length > 1) {
                    alert("一次只能取消一个教练的排班");
                    return;
                }
                var trsId = $("tr#" + teacherid).find("#trsId").text();
                var status = $("tr#" + teacherid).find("#status").text();
                if (trsId == "") {

                    alert("尚未排班，无需取消");
                    return;
                }
                if (status == "01") {

                    alert("已启用，不能取消排班");
                    return;
                }

                $.ligerDialog.confirm('确定要取消教练排班？', function (yes) {
                    if(yes) {
                        $.ajax({
                            type: "POST",
                            url: basePath+"ea/teacherpb/sajax_ea_deleteTeacherPb.jspa",
                            data: {
                                "teacherRScheduling.trsId":trsId
                            },
                            dataType: "json",
                            success: function (data) {

                                $.ligerDialog.success("取消成功！", "提示", function (yes) {
                                    window.location.href = basePath + "ea/teacherpb/ea_findTeacherPbList.jspa";
                                });

                            }
                        });
                    } else {
                        return false;
                    }
                });


                break;

            case '设置每页显示条数' :
                var url = basePath
                    + "ea/teacherpb/ea_findTeacherPbList.jspa?search="
                    + search + "&date="
                    + new Date();
                numback(url);
                break;
        }
    }

    $("#tosearch").click(function () {
        $("#searchForm #searchType").val("receiver");
        $("#searchForm").attr(
            "action",
            basePath + "ea/teacherpb/ea_toSearch.jspa?pageNumber="
            + pNumber + "&date=" + new Date());
        document.searchForm.submit.click();
    });

    $(".mode tr[id]").toggle(function () {
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

    }, function () {
        $("input.JQuerypersonvalue", $(this)).attr("checked", false);
    });


    //用于阻止复选框的冒泡行为；
    $("input.JQuerypersonvalue").click(function (event) {
        event.stopPropagation();

    });

   $(".bcmc").live("click",function(){
       $(this).parent().find("input[type=radio]").attr("checked",true);
    });

    /**
     *
     * 排班提交
     */
    $("#save").click(function () {
        var classId= $('input:radio[name="classId"]:checked').val();
        if(classId==null||classId==""){
            alert("请选择一个班次!");
            return false;
        }
        $("#jqModel").jqmHide();
        $.ajax({
            type: "POST",
            url: $("#saveForm").attr("action"),
            data: $("#saveForm").serialize(),
            dataType: "json",
            success: function (data) {

                $.ligerDialog.success("操作成功！", "提示", function (yes) {
                    window.location.href = basePath + "ea/teacherpb/ea_findTeacherPbList.jspa";
                });

            }
        });
    });

});

function re_load() {

    if (token) {

        document.location.href = basePath
            + "ea/teacherpb/ea_findTeacherPbList.jspa";

    }
}

