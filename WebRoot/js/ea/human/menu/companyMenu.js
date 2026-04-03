let pageNumber = 1, pageSize = 30,pageCount = 0;
$(function () {
    $("#companyName").val("");
    localStorage.setItem("companyName","");
    getAllCompanyData();
    bindEvents();
    initCss();
});
/**
 * 获取公司数据
 */
function getAllCompanyData() {
    let companyName =localStorage.getItem("companyName");
    if (companyName == null){
        companyName = "";
    }
    const param = new Array();
    param.push("companyName=" + companyName);
    param.push("&&pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    let url = basePath + "ea/menu/sajax_ea_getAllCompanyData.jspa?" + param.join("");
    getAjax("getAllCompanyData",url);
}

/**
 * ajax获取数据
 * @param name
 * @param url
 */
function getAjax(name,url){
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            if ("getAllCompanyData" == name){
                showCompanyMenuByData(data);
            }
        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    })
}

function showCompanyMenuByData(data){
    var Str = new Array();
    var companyId = "",ccomType="",ccomtype = "";
    if ("" == data || "null" == data){
        Str.push("<li class ='li-data-no' style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
    } else {
        $(".li-data-no").hide();
        var list = eval("(" + data + ")");
        var listData =  list["list"];
        pageCount = list["pageCount"];
        let listDataLength = listData.length;
        if (listDataLength > 0){
            for (var i = 0 ; i < listData.length; i++) {
                companyId = listData[i][0];
                ccomtype = listData[i][1];
                if (ccomtype == "" || ccomtype == null){
                    ccomtype = 0;
                }
                Str.push("<li class='div-company-li' >" +
                    "<div class='div-company-name' attr-id='" +  companyId + "'><label>" + listData[i][2]
                    + "(" + getTypeNameByNum(ccomtype)+ ")</label></div>" +
                    "<button class=\"layui-btn layui-btn-xs buttom-power\" " +
                    "onclick=getCompanyMenu('" + companyId + "')>授权</button>" +
                    "</li>");
            }
        } else {
            Str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
        }
    }
    const moreData = document.getElementById('more-data');
    if (moreData != null){
        moreData.remove()
    }
    if (pageCount > 1){
        $(".company-ul").append(Str.join(""));
    } else {
        $(".company-ul").html(Str.join(""));
    }

    $(".div-company-name").width($(".div-company-li").width() - 39);
}

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height()- 20);
    $(".layui-tab-content").height($(".sec-list").height() - 40);
    $(".div-company-name").width($(".div-company-li").width() - 39);

}

/**
 * 获取公司菜单
 */
function getCompanyMenu(companyId){
    var param = [];
    param.push("type=companyMenu");
    param.push("&companyId=" + companyId);
    document.location.href = basePath + "page/WFJClient/pc/5l5C/human/menu.jsp?" + param.join("");
}

function getTypeNameByNum(num){
    const companyTypeList = ["大型企业", "中型企业", "小型企业","微型企业","小微型企业","供应商企业",
        "0元加入"];
    return companyTypeList[num];
}
/**
 * 点击事件
 */
function bindEvents(){
    $(document).on("click",".div-company-name",function(){
        const companyId = $(this).attr("attr-id");
        document.location.href = basePath + "ea/menu/ea_getCompanyDataById.jspa?companyId=" + companyId;

    })
    document.getElementById('companyName').addEventListener("input",function(){
        pageCount = 1;
        const companyName = $("#companyName").val();
        localStorage.setItem("companyName",companyName);
        getAllCompanyData();
    })
    $(".close-company").click(function(){
        $("#companyName").val("");
        localStorage.setItem("companyName","");
        getAllCompanyData();
    })
    // 监听滚动事件
    const divElement = document.querySelector('.layui-tab-content');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".company-ul").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getAllCompanyData();
            }

        }
    })

}






