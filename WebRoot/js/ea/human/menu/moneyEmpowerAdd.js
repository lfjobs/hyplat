var isSubmit = false;
const companyTypeList = ["大型企业平台管理商城系统", "中型企业平台管理商城系统", "小型企业平台管理商城系统","微型企业平台管理商城系统","小微型企业平台管理商城系统","供应商企业平台管理商城系统",
    "0元加入"];
$(function () {
    if ("add" == type){
        $(".div_header").html("金额授权添加");
        $("#ccomType").val(6);
    } else {
        $(".div_header").html("金额授权修改");

        $("#companyTypeSelect dl dd").html(companyTypeList[$("#ccomType").val()]);
    }
    bindEvents();
});


/**
 * 点击事件
 */
function bindEvents(){
    // 打开企业类型选项
    $("#companyTypeSelect").click(function() {
        $("#mask").fadeIn();
        $("#companyTypeLayer").animate({"bottom": 0});
    });

    /*关闭企业类型选项*/
    $("#mask, .close-mask").click(function() {
        clockMask();
    });
}
/*关闭企业类型选项*/
function clockMask() {
    $("#mask").fadeOut();
    $("#companyTypeLayer").animate({"bottom": "-100%"});
}

/**
 * 选择菜单类型
 * @param num
 */
function selectCompanyType(num){

    clockMask();
    $("#companyTypeSelect dl dd").html(companyTypeList[num]);
    $("#ccomType").val(num);

}
function save(){
    if (isSubmit) return false;
    var empowerName = $("#empowerName").val().trim();
    var regExp;
    // 金额授权
    regExp = new RegExp(/^[0-9]+(\.[0-9]{1,2})?$/);
    if (!empowerName || empowerName == "") {
        layer.msg('请输入权限金额');
        return;
    }
    if(!regExp.test(empowerName)){
        layer.msg('权限金额只支持数字');
        return;
    }
    isSubmit = true;
    let url;
    if ("add" == type){
        url = basePath + "ea/menu/sajax_ea_createMoneyEmpower.jspa";
    } else {
        url = basePath + "ea/menu/sajax_ea_updateMoneyEmpower.jspa";
    }
    const formData = new FormData($("#form")[0]);
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data == "success") {
                isSubmit = false;
                layer.msg("保存成功");
                window.location.href = basePath + "page/WFJClient/pc/5l5C/human/moneyEmpower.jsp";
            } else if (data == "existName"){
                isSubmit = false;
                layer.msg("权限金额已存在");
            } else if (data == "existType"){
                isSubmit = false;
                layer.msg("企业类型已存在");
            }else {
                isSubmit = false;
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}











