$(function(){
    $("#jqModelSearch").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    // .jqDrag('.drag');// 添加拖拽的selector
    $("#jqModel").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector	
    // .jqDrag('.drag');// 添加拖拽的selector
    $('.JQueryflexme').flexigrid({
        height: 180,
        width: 'auto',
        minwidth: 30,
        title: '公司在职员工',
        minheight: 80,
        buttons: [{
            name: '查询',
            bclass: 'mysearch',
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
    function action(com, grid){
        switch (com) {
            case '查询':
                $("#jqModelSearch").jqmShow();
                break;
            case '设置每页显示条数':
                var url = basePath +
                "ea/academicadmin/ea_getCompanyListEmployeeReferral.jspa?search=" +
                search;
                numback(url);
                break;
        }
    }
    
    
    $(".JQueryflexme tr[id]").click(function(){
        opertionID = this.id;
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
    });
    $("input.JQueryreturn").click(function(){// 取消
        $("#jqModelSearch").jqmHide();
    });
    // 查询相关操作
    $("#searchStaff").click(function(){
        $("#cstaffSearchForm").attr("action", basePath +
        "ea/academicadmin/ea_toSearchCompanyListEmployeeReferral.jspa?pageNumber=" +
        pNumber);
        document.getElementById("cstaffSearchForm").submit();
    });
});
