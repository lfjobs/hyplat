$(function(){

    $("#saveForm").Validform({
        tiptype:function(msg,o,cssctl){
            var objId = o.obj.attr("id");
            var errDivId = "err_" + objId;
            var objtip = $("#" + errDivId);
            cssctl(objtip,o.type);
            objtip.text(msg);
        },
        datatype:{
        },
        ignoreHidden:true,
        callback:function(form){
            $.ajax({
                type:"POST",
                url:$("#saveForm").attr("action"),
                data:$("#saveForm").serialize(),
                success: function(msg) {
                    $.ligerDialog.success("保存配置成功！", "提示", function (yes) {
                        window.location.href = basePath + "/ea/comconfig/ea_findCompanyConfig.jspa";
                    });


                }
            });
            return false;
        }
    });


    $("#createDetail").click(function(){
        $.ligerDialog.confirm('确定要初始化可预约时间记录？', function (yes) {
            if(yes) {
                $.ajax({
                    type:"POST",
                    url:basePath+"/ea/comconfig/sajax_ea_initOrderConfig.jspa?",
                    dataType:"json",
                    success: function(data) {
                        var m = eval("("+data+")");
                        var msg = m.msg;
                        $.ligerDialog.success("初始化约车时段成功！", "提示", function (yes) {
                            window.location.href = basePath + "/ea/comconfig/ea_findCompanyConfig.jspa";
                        });

                    }
                });
            } else {
                return false;
            }
        });
    });


});



function checkCancel() {
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

