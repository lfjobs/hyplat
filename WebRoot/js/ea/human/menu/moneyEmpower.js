var empowerListData = {};
var selectData = {},isSubmit=false;
$(function () {
    window.addEventListener('load', (event) => {
        const navType = window.performance.navigation.type;
        if (navType === window.performance.navigation.TYPE_BACK_FORWARD) {
            if (localStorage.getItem('backState') === "1"){
                window.location.reload();
            } else {
                initData();
            }
        } else {
            localStorage.setItem('backState', "0");
            initData();
        }
    });
});
function initData() {
    getEmpowerListByType();
    initCss();
    bindEvents();
}

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height() - $(".layui-tab-brief").height() - 20);
     $(".layui-tab-content").height($(".sec-list").height() - 20)
}

function getEmpowerListByType() {
    let url = basePath + "ea/menu/sajax_ea_getMoneyEmpowerList.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            var listData = eval("(" + data + ")");
            var Str = new Array();
            var empowerId = "",ccomType="";
            let listDataLength = listData.length;
            if (listDataLength > 0){
                Str.push("<ul>");
                for (var i = 0 ; i < listData.length; i++) {
                    empowerId = listData[i].empowerId;
                    ccomType = listData[i].ccomType;
                    Str.push("<li class='div-money-li'  attr-id='" +  empowerId + "'>" + getTypeNameByNum(ccomType) + "(¥" + listData[i].empowerName + "）</li>");

                    empowerListData[empowerId] = listData[i];
                }
                Str.push("</ul>");

            } else {
                Str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            }

            $(".div-money").html(Str.join(""));
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    });
}
function getTypeNameByNum(num){
    const companyTypeList = ["大型企业平台管理商城系统", "中型企业平台管理商城系统", "小型企业平台管理商城系统","微型企业平台管理商城系统","小微型企业平台管理商城系统","供应商企业平台管理商城系统",
        "10元加入"];
    return companyTypeList[num];
}


/**
 * 点击事件
 */
function bindEvents(){
    // 新建
    $(".draft").click(function () {
        var param = [];
        param.push("type=add");
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/moneyEmpowerAdd.jsp?" + param.join("") ;
        selectData = {};
        $(".div-money-li").removeClass("active");

    });
    $(".div-money-li").click(function(){
        var empowerId = $(this).attr("attr-id");
        selectData = empowerListData[empowerId];
        $(".div-money-li").removeClass("active");
        $(this).addClass("active");

    })
    // 编辑
    $(".edit").click(function () {
        if (selectData.empowerId == undefined){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "ea/menu/ea_getMoneyEmpowerByKey.jspa?empowerKey=" + selectData.empowerKey;
    });
    //删除
    var del = 0;
    $(".del").click(function () {
        if (selectData.empowerId == undefined){
            layer.msg("请选择要删除的数据");
            return false;
        }
        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        del = 1;
        return false;
    });
    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    })

    //确认删除
    $(".close-confirm").click(function () {
        if (del == 0) {
            $(".div-tingyong").hide();
            return false;
        }
        del = 0;
        $(".div-tingyong").hide();
        if (selectData.empowerId == undefined){
            layer.msg("请选择要删除的数据");
            return false;
        }

        var url = basePath
            + "ea/menu/sajax_ea_delMoneyEmpower.jspa?empowerKey=" + selectData.empowerKey;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                if (data == "success") {
                    layer.msg("删除成功");
                    getEmpowerListByType();
                }
            },
            error: function (data) {
                layer.msg("删除失败");
            }
        });
    })
    $(".power").click(function () {
        if (selectData.empowerKey == undefined){
            layer.msg("请选择要设置权限的数据");
            return false;
        }
        const param = [];
        param.push("type=powerInstall");
        param.push("&empowerId="+selectData.empowerId);
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/menu.jsp?" + param.join("");
    });

    $(".standard").click(function () {
        if (selectData.empowerKey == undefined){
            layer.msg("请选择要设置标准岗位的数据");
            return false;
        }
        const param = [];
        param.push("&empowerId="+selectData.empowerId);
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleStandardSelect.jsp?" + param.join("");
    });

    // 组织机构
    $(".organization").click(function () {
        if (selectData.empowerKey == undefined){
            layer.msg("请选择要设置组织机构的数据");
            return false;
        }
        const param = [];
        param.push("type=powerInstall");
        param.push("&empowerId="+selectData.empowerId);
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/organizationDict.jsp?" + param.join("");
    });
    // 公司授权
    $(".companyPower").click(function () {
        if (selectData.empowerKey == undefined){
            layer.msg("请选择要设置组织机构的数据");
            return false;
        }
        // 选择岗位
        const param = {
            "titleName": "选择公司", "id": "0", "name": "2",
            "nameData": ["2"],  "type": "checkbox",queryBool:true,callBackBool:true,closePage:true,
        };
        const paramData = new Array();
        paramData.push("ccomType=" + selectData.ccomType);
        let url = basePath + "ea/menu/sajax_ea_getAllCompanyData.jspa?" + paramData.join("");
        spdject.getDataPageByUrl(url, param);
    });


}
function callbackData(id,name){
    if (isSubmit) return false;
    var empowerId = selectData.empowerId;
    isSubmit = true;
    let url = basePath + "ea/menu/sajax_ea_saveCompanyMenuByEmpowerId.jspa?empowerId=" + empowerId + "&companyIds=" + id;
    $.ajax({
        url: encodeURI(url),
        type: 'POST',
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data === "success") {
                isSubmit = false;
                layer.closeAll();
                layer.msg("保存成功");
            } else{
                isSubmit = false;
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}





