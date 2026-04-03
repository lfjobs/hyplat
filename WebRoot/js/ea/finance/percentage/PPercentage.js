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
        title: '价格管理',
        minheight: 80,
        buttons: [/*{
            name: '查询',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        },*/ {
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
        },/* {
            name: '删除',
            bclass: 'delete',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, */{
            name: '设置每页显示条数',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        },{
            name: '导出',
            bclass: 'excel',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });

    function action(com, grid) {
        switch (com) {
            case '添加' :
                //查询价格百分比判断是否存在
                $.ajax({
                    type: "POST",
                    url: basePath + "ea/percentage/sajax_ea_ajaxSelectPPercentage.jspa",
                    data: null,
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var code = member.code;
                        if (code == "200") {//已登录
                            var pageForm = member.pageForm;
                          /*  if (pageForm != null && pageForm.list != null) {
                                alert("价格百分比已经存在，您不能再添加了！")
                            } else {*/
                                url = basePath + "ea/percentage/ea_toPPercentageAdd.jspa"
                                window.open(url);
                         /*   }*/

                        } else if (code == "400") {//去登陆页面登陆
                            location.href = basePath + "page/ea/not_login.jsp"
                        }
                    },
                    error: function (data) {
                        alert("获取数据失败！");
                        //location.reload();
                    },
                    dateType: "json"
                });
                break;
            case '修改' :
                var percentageId = $('input[name="checkedId"]:checked').val();
                if (typeof(percentageId) == "undefined" || percentageId == "") {
                    alert('请选择！');
                    return
                } else {
                    url = basePath + "ea/percentage/ea_toPPercentageUpdate.jspa?ppercentage.percentageId="+percentageId
                    open(url);
                }
                break;
            /*case '删除' :
                /!* var projectId = $('input[name="checkedId"]:checked').val();
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
                 }*!/
                break;*/
           /* case '查询':
                break;*/
            case '设置每页显示条数' :
                var url = basePath + "ea/percentage/ea_selectPPercentage.jspa?";
                numback(url);
                break;
            case '导出' :
                location.href= basePath + "ea/percentage/ea_PPercentageShowExcel.jspa";
                break;

        }
    }

     //单击事件
     $(".JQueryflexme tr[id]").click(function() {
     $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
     });
     $(".JQueryflexme tr[id]").dblclick(function () {
     action('修改');//当双击时出发 action方法.等价于先选中再点击修改按钮
     });


});

