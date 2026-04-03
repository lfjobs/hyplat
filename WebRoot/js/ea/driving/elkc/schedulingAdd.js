$(function(){

    $("#createDetail").click(function(){
        $.ligerDialog.confirm('确定要初始化可预约时间记录？', function (yes) {
            if(yes) {
                $.ajax({
                    type:"POST",
                    url:basePath+"/ea/comconfig/sajax_ea_initOrderConfig.jspa?",
                    success: function(data) {
                        var m = eval("("+data+")");
                        var msg = m.msg;
                        if(msg.indexOf("成功") != -1) {
                            $.ligerDialog.success(eval(msg));
                        } else {
                            $.ligerDialog.error(eval(msg));
                        }
                    }
                });
            } else {
                return false;
            }
        });
    });


});
function changeCheck() {
    var cancelCheck = $("#cancelCheck").val();
    if (cancelCheck != null && cancelCheck != "") {
        if (cancelCheck == "1") {
            $("#firstSpan").html("天");
            $("#cancelDetail").show();
            $("#secondSpan").show();
            $("#secondSpan").html("点");
        } else {
            $("#firstSpan").html("小时");

            $("#cancelDetail").hide();
            $("#cancelDetail").attr("value","");
            $("#secondSpan").hide();
        }
    }
}

function  checkCancel1() {
    var cancelCheck = $("#cancelCheck").val();
    if (cancelCheck == null || cancelCheck == "") {
        openErrorDialog("请先选择是提前天数还是提起小时");
        return false;
    }
}
function  checkCancel2() {
    var cancelType = $("#cancelType").val();
    if (cancelType == null || cancelType == "") {
        openErrorDialog("请先填写天或小时数");
        return false;
    }
    if (isNaN(cancelType)) {
        openErrorDialog("请输入数字");
        $("#cancelType").attr("value","");
        $("#cancelType").focus();
        return false;
    }
}
function checkCancel3() {
    var cancelDetail = $("#cancelDetail").val();
    if (isNaN(cancelDetail)) {
        openErrorDialog("请输入数字");
        $("#cancelDetail").attr("value","");
        $("#cancelDetail").focus();
        return false;
    }
    if (parseInt(cancelDetail) <1 || parseInt(cancelDetail) > 24) {
        openErrorDialog("请输入正确的点数（1~24）！");
        $("#cancelDetail").attr("value","");
        $("#cancelDetail").focus();
        return false;
    }
}

