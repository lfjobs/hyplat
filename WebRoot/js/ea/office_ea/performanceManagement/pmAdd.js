var id = "";

$(function () {
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

    if(!$("#parentName").text()){
        $("#parentIdDiv").hide();
    }
});

var isSubmit = false;

function save() {
    if (isSubmit) return false;
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9、%]+$/);//商品名称
    var a = true;
    var aa = '';

    var name = $("#name").val();

    if (name == "") {
        prompt("请输入名称");
        return;
    }

    if (!reg2.test(name)) {
        prompt("名称只支持数字，英文，中文");
        return;
    }
    isSubmit = true;
    var url = basePath + "ea/specs/sajax_ea_createPerformanceManagement.jspa";
    var formData = new FormData($("#form")[0]);
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: formData,
        async: true,
        beforeSend: function () {
            $(".overlay").addClass("active");
            $(".overlay span").text("产品存入中，请稍后！！！");
            $(".loading").show();
        },
        cache: false,
        contentType: false,
        processData: false,
        beforeSend: function () {
            $(".div-tijiao").text("正在保存...").attr('disabled', true);
        },
        complete: function () {
            $(".div-tijiao").text("提交").attr('disabled', false);
        },
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                prompt("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/performanceManagement/pmManage.jsp";
            } else if (data == "exist"){
                isSubmit = false;
                prompt("同级目录下名称重复！");
            }else {
                isSubmit = false;
                prompt("保存失败");
            }
        },
        error: function (data) {
            prompt("保存失败！");
        }
    });
}

//弹框
function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    $("#prompt").show();
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 3000);
}


function update() {
    if (isSubmit) return false;
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9、%]+$/);//商品名称
    var a = true;
    var aa = '';

    var name = $("#name").val();

    if (name == "") {
        prompt("请输入名称");
        return;
    }

    if (!reg2.test(name)) {
        prompt("名称只支持数字，英文，中文");
        return;
    }

    isSubmit = true;
    var url = basePath + "ea/specs/sajax_ea_updatePerformanceManagement.jspa";
    var formData = new FormData($("#form")[0]);
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: formData,
        async: true,
        beforeSend: function () {
            $(".overlay").addClass("active");
            $(".overlay span").text("产品存入中，请稍后！！！");
            $(".loading").show();
        },
        cache: false,
        contentType: false,
        processData: false,
        beforeSend: function () {
            $(".div-tijiao").text("正在保存...").attr('disabled', true);
        },
        complete: function () {
            $(".div-tijiao").text("提交").attr('disabled', false);
        },
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                prompt("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/performanceManagement/pmManage.jsp";
            } else if (data == "exist"){
                isSubmit = false;
                prompt("同级目录下名称重复！");
            } else {
                isSubmit = false;
                prompt("保存失败");
            }
        },
        error: function (data) {
            prompt("保存失败！");
        }
    });
}
function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


