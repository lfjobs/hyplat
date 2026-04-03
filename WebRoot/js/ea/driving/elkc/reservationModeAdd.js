$(function(){

    $("#status").val($("#status").attr("selectedValue"));
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
                    var m = eval("(" + data + ")");
                    var msg = m.msg;


                    if (msg.indexOf("false") == -1) {
                        $.ligerDialog.success("模式信息保存成功！", "提示", function (yes) {
                            window.location.href = basePath + "ea/reservmode/ea_findReservationModeList.jspa";
                        });
                    }else{
                        $.ligerDialog.success("部分时间段不合理");

                    }
                }
            });
            return false;
        }
    });
    $("#createDetail").click(function() {
        $.ajax({
              url:basePath+"/ea/reservmode/sajax_ea_showReservationInit.jspa",
              type:"get",
              dataType:"json",
              success: function(data) {
                var m = eval("("+data+")");
                var msg = m.msg;
                if(msg != null && msg != "") {
                    $.ligerDialog.warn(msg, "提示");
                } else {
                    var obj = m.detailList;
                    $('div[id*="content_"]').remove();
                    $('div[id*="tip_"]').remove();
                    var lenght = 0;
                    $(obj).each(function(n) {
                        var val = obj[n];
                        $('#changeMain').before(
                            '<div class="add_time" id="content_'+n+'"><ul><li class="Btime">开始时间</li>'
                            +'<li><input type="text" id="startTime'+n+'" onfocus="WdatePicker({dateFmt:\'HH:mm\',minDate:\'1:00\',maxDate:\'23:00\'})" name="detailList['
                            +n+'].startTime" class="textBox-10" datatype="*" nullmsg="请选择开始时间" value="'+val.startTime+'"/></li>'+
                            '<li class="Otime">结束时间</li><li><input type="text" id="endTime'+n+'" onfocus="WdatePicker({dateFmt:\'HH:mm\',minDate:\'1:00\',maxDate:\'23:00\'})" name="detailList['
                            +n+'].endTime" class="textBox-10" value="'+val.endTime+'" datatype="*" nullmsg="请选择结束时间"/></li><li><img src="'+basePath+'images/ea/driving/elkc/delete.png" width="23" onclick="deleteLine('
                            +n+')"/></li></ul></div>'
                            +'<div class="add_time" id="tip_'+n+'"><ul><li class="add_time-reg" id="err_startTime'
                            +n+'"></li><li class="add_time-reg1" id="err_endTime'
                            +n+'"></li><li class="add_time-reg2" id="err_lcPrice'
                            +n+'"></li></ul></div>');
                        lenght++;
                    });
                    v_detail = lenght;
                }
            }
        });
    });
});

function addLine(ob) {
    if(v_detail == 0) {
        v_detail = 1;
    }
    $('#changeMain').before(
        '<div class="add_time"id="content_'+v_detail+'"><ul><li class="Btime">开始时间</li>'
        +'<li><input type="text" id="startTime'+v_detail+'" onfocus="WdatePicker({dateFmt:\'HH:mm\',minDate:\'1:00\',maxDate:\'23:00\'})" name="detailList['
        +v_detail+'].startTime" class="textBox-10" value="" datatype="*" nullmsg="请选择开始时间"/></li>'+
        '<li class="Otime">结束时间</li><li><input type="text" id="endTime'+v_detail+'" onfocus="WdatePicker({dateFmt:\'HH:mm\',minDate:\'1:00\',maxDate:\'23:00\'})" name="detailList['
        +v_detail+'].endTime" class="textBox-10" value="" datatype="*" nullmsg="请选择结束时间"/></li><li><img src="'+basePath+'images/ea/driving/elkc/delete.png" width="23" onclick="deleteLine('
        +v_detail+')"/></li></ul></div>'
        +'<div class="add_time" id="tip_'+v_detail+'"><ul><li class="add_time-reg" id="err_startTime'
        +v_detail+'"></li><li class="add_time-reg1" id="err_endTime'
        +v_detail+'"></li><li class="add_time-reg2" id="err_lcPrice'
        +v_detail+'"></li></ul></div>');
    v_detail = parseInt(v_detail)+parseInt(1);
}
function deleteLine(ob) {
    $("#content_" + ob).remove();
    $("#tip_" + ob).remove();
}