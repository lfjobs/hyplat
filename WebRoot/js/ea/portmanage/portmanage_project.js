$(function () {
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector


    /*var title="";
     if(type=="c"){
     title="车辆道路运输证管理公司汇总"
     }else if(type=="g"){
     title="车辆道路运输证管理集团汇总"
     }else{
     title="车辆道路运输证管理";
     }*/
    $('.JQueryflexme').flexigrid({
        height: 165,
        width: 'auto',
        minwidth: 30,
        title: '项目管理',
        minheight: 80,
        buttons: [{
            name: '查询',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
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
        }, /*{
            name: '导出',
            bclass: 'excel',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        },*/ {
            name: '设置每页显示条数',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '获取项目中API文档',
            bclass: 'see',
            onpress: action
            // 当点击调用方法
        }]
    });

    function action(com, grid) {
        switch (com) {
            case '添加' :
                document.addForm.reset();
                $("#jqModelAdd").jqmShow();
                break;
            case '修改' :
                var projectId = $('input[name="checkedId"]:checked').val();
                if (typeof(projectId) == "undefined" || projectId == "") {
                    alert('请选择一个项目！');
                    return
                } else {
                    document.updateForm.reset();
                    //项目修改弹框显示
                    $("#jqModelUpdate").jqmShow();
                    //项目修改回显
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_selectProjectByProjectId.jspa",
                        data: {
                            "projectId": projectId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var code = member.code;
                            var project = member.project;
                            //获取项目回显数据成功，回显数据
                            if (code == '200') {
                                $("#projectId1").val(project.projectId);
                                $("#projectName1").val(project.projectName);
                                $("#projectUrl1").val(project.projectUrl);
                                $("#projectDescription1").val(project.projectDescription);

                            } else {
                                //获取项目回显数据失败
                                alert("参数为空无法获取项目数据");
                                location.reload();
                            }

                        },
                        error: function (data) {
                            alert("获取数据失败！");
                            location.reload();
                        },
                        dateType: "json"
                    });


                }
                break;
            case '删除' :
                var projectId = $('input[name="checkedId"]:checked').val();
                if (typeof(projectId) == "undefined" || projectId == "") {
                    alert('请选择一个项目！');
                    return
                }
                if (confirm("确定继续？")) {
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_deleteProjectByProjectId.jspa",
                        data: {
                            "projectId": projectId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var boolean = member.boolean;
                            //删除成功
                            if (boolean == true) {
                                alert(member.code);
                                location.reload();
                            } else {
                                //删除失败
                                alert(member.code)
                                location.reload();
                            }

                        },
                        error: function (data) {
                            alert("数据获取失败！");
                        },
                        dateType: "text"
                    });
                }
                break;
            case '查询':
                $("#jqModelProjectSearch").jqmShow();
                break;
            case '设置每页显示条数' :
                var url = basePath + "ea/ccode1/ea_pm_selectProjects.jspa?search="
                    + search
                numback(url);

                break;
            case '获取项目中API文档' :
                var projectId = $('input[name="checkedId"]:checked').val();
                if (typeof(projectId) == "undefined" || projectId == "") {
                    alert('请选择一个项目！');
                    return;
                }
                location.href = basePath + "ea/ccode1/ea_pm_selectAPIDocuments.jspa?projectId="
                    + projectId
                break;
        }
    }

    /*//单击事件
     $(".JQueryflexme tr[id]").click(function() {
     roadID = this.id;
     if (personurl) {
     $("#mainframe").attr("src", personurl + roadID);
     }
     $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
     });*/
    /* $(".JQueryflexme tr[id]").dblclick(function () {
     action('修改');//当双击时出发 action方法.等价于先选中再点击修改按钮
     });*/
    // 项目添加取消
    $("input.JQueryreturn").click(function () {
        document.addForm.reset();
        $("#jqModelAdd").jqmHide();
        location.reload();
    });
    //项目添加保存
    $("input.JQuerySubmitAdd").click(function () {
        $(".put3").trigger("blur");
        /*if ($("form .error").length) {
         return;
         }*/
        if ($("#projectUrl2").val() == '') {
            // alert("projectUrl不能为空");
            //$("input.depotCoding").css("background-color", "red");
            return;
        }

        if ($("#projectName2").val() == '') {
            // alert("projectName2不能为空");
            //$("input.depotName").css("background-color", "red");
            return;
        }

        var pd=$("#projectDescription2").val();
        if (pd == ''||pd == null||pd.replace(/\s*/g, "") == "") {
            $("#projectDescriptionVerify1").html("项目描述不能为空")
            return;
        }
        //ajax执行项目添加
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_addProject.jspa",
            data: $("#addForm").serialize(),
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                //添加成功
                if (code == '200') {
                    alert(member.addproject);
                    location.href = basePath + "ea/ccode1/ea_pm_selectProjects.jspa";
                } else {
                    //添加失败
                    alert(member.addproject);
                    location.href = basePath + "page/ea/not_login.jsp";
                }

            },
            error: function (data) {
                alert("添加失败！");
            },
            dateType: "json"
        });

        /*	document.addForm.reset();
         return;*/
    });
    // 修改取消
    $("input.JQueryreturns").click(function () {
        $("#jqModelUpdate").jqmHide();
        location.reload();
    });
    //选择返回
    $(".JQueryreturngoods").click(function () {
        notoken = 0;
        var numes = "";
        $("#carNum", $("table#searchgood")).attr("value", numes);
        $("#carType", $("table#searchgood")).attr("value", numes);
        $(":input#parms").attr("value", numes);
        $(".jqmWindow", $("#goodsForm")).jqmHide();
    });
    /* // 新增物品
     $(".xzwp").click(function () {
     window.open(basePath
     + "ea/goodsmanage/ea_getListGoodsManage.jspa?");
     });*/
    // 修改保存
    $("input.JQuerySubmitUpdate").click(function () {
        $(".put3").trigger("blur");
        /*if ($("form .error").length) {
         return;
         }*/
        if ($("#projectUrl1").val() == '') {
           // alert("projectUrl不能为空");
            //$("input.depotCoding").css("background-color", "red");
            return;
        }

        if ($("#projectName1").val() == '') {
            //alert("projectName1不能为空");
            //$("input.depotName").css("background-color", "red");
            return;
        }

        var pd=$("#projectDescription1").val();
        if (pd == ''||pd == null||pd.replace(/\s*/g, "") == "") {
            $("#projectDescriptionVerify").html("项目描述不能为空")
            return;
        }
        //ajax执行项目修改
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_updateProjectByprojectId.jspa",
            data: $("#updateForm").serialize(),
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                //修改成功
                if (code == '200') {
                    alert(member.updateProject);
                    location.href = basePath + "ea/ccode1/ea_pm_selectProjects.jspa";
                }
                if (code=='400') {
                    //修改失败
                    alert(member.updateProject);
                    location.href = basePath + "page/ea/not_login.jsp";
                }
                if (code=='401') {
                    //修改失败
                    alert(member.updateProject);
                    location.reload();
                }
                if (code=='402') {
                    //修改失败
                    alert(member.updateProject);
                    location.reload();
                }

            },
            error: function (data) {
                alert("修改失败！");
            },
            dateType: "json"
        });
    });
    //项目查询
    $("#searchProject").click(function () {
        $("#projectSearchForm").attr(
            "action",
            basePath + "ea/ccode1/ea_pm_selectProjects.jspa?pageForm.pageNumber="
            + pNumber);
        document.getElementById("projectSearchForm").submit();
    });
});
/*function re_load() {
        location.href = basePath + "ea/ccode1/ea_pm_selectProjects.jspa?pageForm.pageNumber="
            + pNumber
}*/
