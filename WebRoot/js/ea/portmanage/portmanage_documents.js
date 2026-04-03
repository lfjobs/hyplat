$(function () {
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector

    $('.JQueryflexme').flexigrid({
        height: 165,
        width: 'auto',
        minwidth: 30,
        title: '文档管理',
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
            name: '获取API文档中接口',
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
                //参数校验
                var documentsId = $('input[name="checkedId"]:checked').val();
                if (typeof(documentsId) == "undefined" || documentsId == "") {
                    alert('请选择一个文档！');
                    return;
                } else {
                    document.updateForm.reset();
                    //文档修改弹框显示
                    $("#jqModelUpdate").jqmShow();
                    //文档修改回显
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_selectDocumentsBydocumentsId.jspa",
                        data: {
                            "documentsId": documentsId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var code = member.code;
                            var aDocuments = member.aDocuments;
                            //获取文档数据成功，回显数据
                            if (code == '200') {
                                $("#documentsId1").val(aDocuments.documentsId);
                                $("#documentsName1").val(aDocuments.documentsName);

                            } else {
                                //获取文档数据失败
                                alert("参数为空无法获取文档数据");
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
                //参数校验
                var documentsId = $('input[name="checkedId"]:checked').val();
                if (typeof(documentsId) == "undefined" || documentsId == "") {
                    alert('请选择一个文档！');
                    return;
                }
                if (confirm("确定继续？")) {
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_deleteAPIDocumentsByDocumentsId.jspa",
                        data: {
                            "documentsId": documentsId
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
                $("#jqModelDocumentsSearch").jqmShow();
                break;
            case '设置每页显示条数' :
                var url = basePath + "ea/ccode1/ea_pm_selectAPIDocuments.jspa?search="
                    + search +"&projectId=" + projectId
                numback(url);
                break;
            case '获取API文档中接口' :
               var documentsId = $('input[name="checkedId"]:checked').val();
                if (typeof(documentsId) == "undefined" || documentsId == "") {
                    alert('请选择一个文档！');
                    return;
                }
                location.href = basePath + "ea/ccode1/ea_pm_selectPorts.jspa?documentsId="
                    + documentsId
                break;
        }
    }

    // api文档添加取消
    $("input.JQueryreturn").click(function () {
        document.addForm.reset();
        $("#jqModelAdd").jqmHide();
        location.reload();
    });
    //api文档添加保存
    $("input.JQuerySubmitAdd").click(function () {
        $(".put3").trigger("blur");
        if ($("#documentsName2").val() == '') {
            return;
        }
        //ajax执行api文档添加
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_addAPIDocuments.jspa",
            data: $("#addForm").serialize(),
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                //添加成功
                if (code == '200') {
                    alert(member.addAPIDocuments);
                    location.href = basePath + "ea/ccode1/ea_pm_selectAPIDocuments.jspa?projectId="
                        + projectId;
                } else {
                    //添加失败
                    alert(member.addAPIDocuments);
                    location.href = basePath + "page/ea/not_login.jsp";
                }

            },
            error: function (data) {
                alert("添加失败！");
            },
            dateType: "json"
        });
    });
    // 修改取消
    $("input.JQueryreturns").click(function () {
        $("#jqModelUpdate").jqmHide();
        //location.reload();
    });
    // 修改保存
    $("input.JQuerySubmitUpdate").click(function () {
        $(".put3").trigger("blur");
        if ($("#documentsName1").val() == '') {
            return;
        }
        //ajax执行文档修改
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_updateAPIDocumentsByDocumentsId.jspa",
            data: $("#updateForm").serialize(),
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                //修改成功
                if (code == '200') {
                    alert(member.updateAPIDocuments);
                    location.href = basePath + "ea/ccode1/ea_pm_selectAPIDocuments.jspa?projectId="
                        + projectId
                }
                if (code == '400') {
                    //修改失败
                    alert(member.updateAPIDocuments);
                    location.href = basePath + "page/ea/not_login.jsp";
                }
                if (code == '401') {
                    //修改失败
                    alert(member.updateAPIDocuments);
                    location.reload();
                }
                if (code == '402') {
                    //修改失败
                    alert(member.updateAPIDocuments);
                    location.reload();
                }

            },
           error: function (data) {
                alert("修改失败！");
                location.reload();
            }

        });
    });
    //文档查询
    $("#searchDocuments").click(function () {
        $("#documentsSearchForm").attr(
            "action",
            basePath + "ea/ccode1/ea_pm_selectAPIDocuments.jspa?pageForm.pageNumber="
            + pNumber + "&projectId=" + projectId);
        document.getElementById("documentsSearchForm").submit();
    });

});

