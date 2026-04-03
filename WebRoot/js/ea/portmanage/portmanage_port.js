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
        title: '接口管理',
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
            name: '接口详情',
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
        },{
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
                location.href=basePath+'page/ea/main/portmanage/portmanage_portAdd.jsp?documentsId='+documentsId;
                break;
            case '接口详情' :
                var portId = $('input[name="checkedId"]:checked').val();
                if (typeof(portId) == "undefined" || portId == "") {
                    alert('请选择一个接口！');
                    return;
                } else {
                    //去接口修改页面
                    location.href=basePath + "ea/ccode1/ea_pm_toPortUpdate.jspa?portId="+portId+"&documentsId="+documentsId;
                }
                break;
            case '删除' :
                //参数校验
                var portId = $('input[name="checkedId"]:checked').val();
                if (typeof(portId) == "undefined" || portId == "") {
                    alert('请选择一个接口！');
                    return;
                }
                if (confirm("您确定要删除吗？")) {
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_deletePortByPortId.jspa",
                        data: {
                            "portId": portId
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
                $("#jqModelPortSearch").jqmShow();
                break;
            case '设置每页显示条数' :
                var url = basePath + "ea/ccode1/ea_pm_selectPorts.jspa?search="
                    + search +"&documentsId=" + documentsId + "&allport=" +allport
                numback(url);
                break;
        }
    }

    //接口查询
    $("#searchPort").click(function () {
        $("#portSearchForm").attr(
            "action",
            basePath + "ea/ccode1/ea_pm_selectPorts.jspa?pageForm.pageNumber="
            + pNumber + "&documentsId=" + documentsId);
        document.getElementById("portSearchForm").submit();
    });

});

