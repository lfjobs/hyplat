let form=layui.form;
let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    initCss();
    bindEvents();

});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
}

/**
 * 点击事件
 */
function bindEvents() {
    layui.use('form', function(){
        form = layui.form;
        form.render('select');
        // 监听下拉框选择事件
        form.on('select(levelSelect)', function(data){
            if(data.value === '-1') {
                layer.open({
                    type: 1,
                    title: '自定义设置级别',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['260px', '170px'],
                    content:$(".custom")
                });
            }
        });
    });

}

/**
 * 保存数据
 */
function saveData() {
    const data = document.getElementById('levelNum');
    const levelNum = data.value;
    const oldLevelNum = $("#oldGradeNum").val();
    if (parseInt(levelNum) < parseInt(oldLevelNum)){
        layer.msg("该级别范围已存在，请重新选择");
        return false;
    }
    const url = basePath + "ea/salarylevel/sajax_ea_saveSalaryLevel.jspa";
    layer.load();
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: {"levelNum":levelNum,"oldLevelNum":oldLevelNum},
        success: function (data) {
            layer.close(layer.index);
            if (data == "success") {
                layer.msg("保存成功");
                document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/salaryLevel.jsp";
                return false;
            } else {
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.close(layer.index);
            layer.msg("保存失败");
        }
    });
}


function cancel(){
    document.getElementById('levelNum').value = "";
    form.render('select');
    layer.close(layer.index);
}

/**
 * 保存自定义
 */
function saveCustom(){
    const select = document.getElementById('levelNum');
    const levelNumSet = $("#levelNumSet").val();
    $("#levelNum option[value='-1']").remove();
    $("#levelNum").append(new Option("1级 ~ " + levelNumSet + "级" , levelNumSet));
    $("#levelNum").append(new Option("自定义", "-1"));
    select.value = $("#levelNumSet").val();
    form.render('select');
    $("#levelNumSet").val("");
    layer.close(layer.index);

}


