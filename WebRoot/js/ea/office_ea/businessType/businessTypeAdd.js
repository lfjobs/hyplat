var id = "";

$(function () {
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    $(".close-tingyong,.close-confirm").click(function () {
        if ($(".titlep").text() == "操作成功") {

            document.location.replace(basePath + "/ea/androiddoc/ea_getArchiveTypeTree.jspa?module=" + module + "&companyID=" + companyID);

        } else {
            $(this).parents(".div-tingyong").hide();
        }
    })


    $(".saveDraft2").click(function () {


        if ($.trim($("#title").val()) == "") {
            $(".div-tingyong").show();
            $(".titlep").text("请填写分类名称");
            return false;
        }


        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/industry/sajax_ea_createBusinessType.jspa");

        document.form.submit.click();
        token = 13;
    });


});

var isSubmit = false;

function save() {
    if (isSubmit) return false;
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9、]+$/);//商品名称
    var a = true;
    var aa = '';

    var typeName = $("#typeName").val();
    var typeNum = $("#typeNum").val();

    if (!typeNum || typeNum == "") {
        prompt("请输入项目编号");
        return;
    }

    if (typeName == "") {
        prompt("请输入项目名称");
        return;
    }

    if (!reg2.test(typeName)) {
        prompt("项目名称只支持数字，英文，中文");
        return;
    }
    isSubmit = true;
    var url = basePath + "ea/industry/sajax_ea_createBusinessType.jspa";
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
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeManage.jsp";
            } else if (data == "exist"){
                isSubmit = false;
                prompt("项目编号重复！");
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
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9、]+$/);//商品名称
    var a = true;
    var aa = '';

    var typeName = $("#typeName").val();
    var typeNum = $("#typeNum").val();

    if (!typeNum || typeNum == "") {
        prompt("请输入项目编号");
        return;
    }

    if (typeName == "") {
        prompt("请输入项目名称");
        return;
    }

    if (!reg2.test(typeName)) {
        prompt("项目名称只支持数字，英文，中文");
        return;
    }

    isSubmit = true;
    var url = basePath + "ea/industry/sajax_ea_updateBusinessType.jspa";
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
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeManage.jsp";
            } else if (data == "exist"){
                isSubmit = false;
                prompt("项目编号重复！");
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
function manageSave() {
    if (isSubmit) return false;
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9、]+$/);//商品名称
    var a = true;
    var aa = '';

    var typePID = $("#typePID").val();
    var typeName = $("#typeName").val();
    var typeNum = $("#typeNum").val();


    if (!typePID || typePID == "") {
        prompt("请选择项目归属");
        return;
    }

    if (!typeNum || typeNum == "") {
        prompt("请输入项目编号");
        return;
    }

    if (typeName == "") {
        prompt("请输入项目名称");
        return;
    }

    if (!reg2.test(typeName)) {
        prompt("项目名称只支持数字，英文，中文");
        return;
    }

    isSubmit = true;
    var url = basePath + "ea/industry/sajax_ea_createManageType.jspa";
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
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/businessTypeManage.jsp";
            } else if (data == "exist"){
                isSubmit = false;
                prompt("项目编号重复！");
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


