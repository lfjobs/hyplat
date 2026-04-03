$(function(){
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话  
        overlay: 20 // 遮罩程度%  
    }).jqmAddClose('.close');// 添加触发关闭的selector  
    $(".JQueryreturnsCcompany").click(function(){
        notoken = 0;
        $(".jqmWindow", $("#selectcompanyForm")).jqmHide();
    });
    $(".JQueryreturnsUser").click(function(){
        notoken = 0;
        $(".jqmWindow", $("#selectuserForm")).jqmHide();
    });
    $('.flexme11').flexigrid({
        height: 180,
        width: 'auto',
        minwidth: 30,
        title: "现金申请明细审批单",
        minheight: 80,
        buttons: [{
            name: '添加',
            bclass: 'add',
            onpress: action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '删除',
            bclass: 'delete',
            onpress: action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '全选/全不选',
            bclass: 'mysearch',
            onpress: action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '打印预览',
            bclass: 'printer',
            onpress: action//当点击调用方法
        }, {
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
            case '添加':
                var num = 0;
                var str = '';
                $("#cashframe").contents().find("tr[id]", grid).each(function(i){
                    if ($("#cashframe").contents().find("tr").find(".trSelected").eq(i).attr("checked")) {
                        //var trid = $("#cashframe").contents().find("#cashapply").find("tr").eq(i).attr("id");
                        var trid = $(this).attr("id");
                        if ($("#" + trid).length == 0) {
                            str = "<tr id='" + trid + "'>" + $("#cashframe").contents().find("#" + trid).html() + "</tr>";
                            $("#cashapply").append(str);
                            str = '';
                            num++;
                        }
                        else {
                            num++;
                        }
                    }
                });
                if (num == 0) {
                    alert("请选择");
                }
                /*
             else {
             alert("操作成功");
             }
             */
                break;
            case '删除':
                var num = 0;
                var count = $(".trSelected").length;
                if (count > 0) {
                    $("#cashapply tr[id]").each(function(){
                        if ($("#cashapply").find("tr").find(".trSelected").attr("checked")) {
                            //var trSelected = $("#cashapply").find("tr").find(".trSelected").attr("id");
                            $(this).remove();
                            num++;
                        }
                    });
                    if (num == 0) {
                        alert("请选择");
                    }
                }
                else {
                    alert("请添加");
                }
                break;
            case '全选/全不选':
                if ($(".trSelected").attr("checked")) {
                    $(".trSelected").attr("checked", false);
                }
                else {
                    $(".trSelected").attr("checked", true);
                }
                break;
            case '打印预览':
                var count = $(".trSelected").length;
                if (count > 0) {
                    $("#cashform").attr("target", "hidden").attr("action", basePath + "ea/cashapplybills/ea_SaveCashApplyBills.jspa");
                    document.cashform.submit.click();
                    token = 2;
                }
                else {
                    alert("请添加");
                }
                break;
        }
    }
});
function re_load(){
	window.open(basePath + "ea/cashapplybills/ea_QueryCashApplyBills.jspa");
    window.location.reload();
}
