let listDataObject = {},dataObject =  {};
let selectedMenuId = [], selectedMenuName = [],selectedMenuIdType=[],menuParentName="";
let menuId = "", menuName = "";
$(function () {
    if (performance.navigation.type == performance.navigation.TYPE_BACK_FORWARD) {
        const value = localStorage.getItem('selectedMenuId');
        const name = localStorage.getItem("selectedMenuName");
        const type = localStorage.getItem("selectedMenuIdType")
        if (value != null && "" != value){
            selectedMenuId = JSON.parse(value);
            selectedMenuName = JSON.parse(name);
            selectedMenuIdType = JSON.parse(type);
            listDataObject = JSON.parse( localStorage.getItem('listDataObject'));
            dataObject = JSON.parse( localStorage.getItem('dataObject'));
            returnPage();
        }
    } else {
        // 页面通过其他方式加载，比如刷新
        localStorage.setItem('selectedMenuId', "");
        localStorage.setItem('selectedMenuName', "");
        localStorage.setItem('selectedMenuIdType', "");
        localStorage.setItem('listDataObject', "");
        localStorage.setItem('dataObject', "");
        initData();
    }
    $(".container").height($(".div-box").height() - $("header").height() - 90);
    bindEvents();



})
/**
 * 初始化数据
 */
function initData() {
    layer.load();
    let url = basePath + "ea/menu/sajax_ea_getMenuByStaffId.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            var listData = eval("(" + data + ")");
            listDataObject["-1"] = listData;
            selectedMenuId.push(-1);
            selectedMenuIdType.push("all");
            selectedMenuName.push(companyName);
            getHtmlStrByPid("-1");
            layer.close(layer.index);
        },
        error: function (data) {
            layer.msg("获取数据失败");
            layer.close(layer.index);
        }
    });
}
/**
 * 事件
 */
function bindEvents() {
    //返回页面
    $(".div_return_page").click(function(){
        returnPage();
    })
    document.getElementById('menuUl').addEventListener('click', function(event) {
        var targetElement = event.target;
        if (targetElement.tagName === 'A') {
            selectedMenuId.push(menuId);
            selectedMenuName.push(menuName);
            selectedMenuIdType.push("all");
            localStorage.setItem('selectedMenuId', JSON.stringify(selectedMenuId));
            localStorage.setItem('selectedMenuName', JSON.stringify(selectedMenuName));
            localStorage.setItem('listDataObject', JSON.stringify(listDataObject));
            localStorage.setItem('dataObject', JSON.stringify(dataObject));
            localStorage.setItem('selectedMenuIdType', JSON.stringify(selectedMenuIdType));
            return;
        } else {
            var str = "";
            if (targetElement.tagName === 'LABEL'){
                menuName = targetElement.outerText;
                const id = targetElement.id;
                str = getHtmlStrById(id);
                if (str.length > 0) {
                    selectedMenuId.push(id);
                    selectedMenuName.push(menuName);
                    selectedMenuIdType.push("single");
                    $(".li-companyName").html(menuName);
                }
            } else {
                str = getHtmlStrByPid(menuId);
                if (str.length > 0) {
                    selectedMenuId.push(menuId);
                    selectedMenuName.push(menuName);
                    selectedMenuIdType.push("all");
                    $(".li-companyName").html(menuName);
                }
            }


        }
    });
}

/**
 * 返回按钮
 * @returns {boolean}
 */
function returnPage(){
    const length = selectedMenuId.length;
    if (length > 1) {
        menuId = selectedMenuId[length - 2];
        menuName = selectedMenuName[length - 2];
        const menuIdType = selectedMenuIdType[length - 2];
        if (menuIdType == "all") {
            getHtmlStrByPid(menuId);
        } else {
            getHtmlStrById(menuId);
        }

        selectedMenuId.pop();
        selectedMenuName.pop();
        selectedMenuIdType.pop();
        $(".li-companyName").html(menuName);
        if(selectedMenuId.length == 1){
            menuParentName = "";
        }
    } else {
        window.history.go(-1);
        return false;
    }
}

function clickMenu(id,name){
    menuId = id;
    menuName = name;
    if ("" == menuParentName){
        menuParentName = name;
    }
}

function  getHtmlStrByPid(menuPID){
    let listData = listDataObject[menuPID];

    const str = new Array();
    let listDataLength = listData.length;
    if (listDataLength > 0) {
        for (let i = 0; i < listData.length; i++) {
            const childrenData = listData[i].children;
            const menuId = listData[i].menuId;
            const menuName = listData[i].menuName;
            const menuURL = listData[i].menuURL
            const menuType = listData[i].menuType;
            dataObject[menuId] = listData[i];
            if (menuType != 0){
                continue;
            }
            str.push("<li class=\"clearfix div_menu_li\" onclick=\"clickMenu('" + menuId + "','" + menuName + "');\">");
            str.push("<p class='p-title'>");
            if(menuURL !== ""){
                str.push("<a class='title-a' href=" + basePath + menuURL + " class='div_menu_a'>" + listData[i].menuName + "</a>");
            } else {
                str.push(listData[i].menuName);
            }
            str.push("</p>");
            str.push(" <p class='p-height'>");
            const childrenLength = childrenData.length;
            listDataObject[menuId] = childrenData;
            for (let j = 0; j < childrenLength; j++){
                getStrHtml(childrenData[j],str);
            }
            str.push(" </p>");
            str.push("<div class='div-more'>")
            str.push("<img src='" + basePath + "images/WFJClient/pc/5l5c/pic_01.png'>")
            str.push(" </div>");
            str.push("</li>");
        }
        if (str.length > 0) {
            $(".ul-con").html(str.join(""));
            initCss();
        }
    } else {
        if (menuPID == "-1"){
            $(".ul-con").html("<li style='display:flex;align-items:center;justify-content:center;margin-top:40%'>权限未分配！请联系管理员</li>");
        }
    }
    return str;
}
function  getHtmlStrById(menuPID){
    let listData = listDataObject[menuPID];
    let data = dataObject[menuPID];
    let menuIdData = menuPID;
    if (listData == undefined ){
        const data = listDataObject[menuId];
        const length = data.length;
        if (length > 0){
            for (let i = 0; i < length; i++) {
                const childrenData = data[i].children;
                menuIdData = data[i].menuId;
                listDataObject[menuIdData] = childrenData;
                if(menuIdData == menuPID ){
                    listData = listDataObject[menuIdData];
                    break;
                }
            }
        }
    }
    const str = new Array();
    let listDataLength = listData.length;
    let catalogueBool = true;
    if (listDataLength > 0) {
        for (let i = 0; i < listData.length; i++) {
            if (listData[i].menuType != "0"){
                catalogueBool = false;
            }
        }
        if (catalogueBool){
            return getHtmlStrByPid(menuIdData);
        }else  {
            str.push("<li class=\"clearfix div_menu_li\" onclick=\"clickMenu('" + menuPID + "','" + menuName + "');\">");
            /*str.push("<p class='p-title'>" + menuName + "</p>");*/
            str.push("<p class='p-title'>");
            if(data.menuURL !== ""){
                str.push("<a class='title-a' href=" + basePath + data.menuURL + " class='div_menu_a'>" + data.menuName + "</a>");
            } else {
                str.push(data.menuName);
            }
            str.push("</p>");
            str.push(" <p class='p-height'>");
            for (let i = 0; i < listData.length; i++) {
                getStrHtml(listData[i],str);
            }
            str.push(" </p>");
            str.push("<div class='div-more'>")
            str.push("<img src='" + basePath + "images/WFJClient/pc/5l5c/pic_01.png'>")
            str.push(" </div>");
            str.push("</li>");
            if (str.length > 0) {
                $(".ul-con").html(str.join(""));
                initCss();
            }
        }

    } else {
        if (menuPID == "-1"){
            $(".ul-con").html("<li style='display:flex;align-items:center;justify-content:center;margin-top:40%'>权限未分配！请联系管理员</li>");
        }
    }
    return str;
}

function getStrHtml(data,str){
    dataObject[data.menuId] = data;
    var childrenUrl = data.menuURL;
    var childrenMenuType = data.menuType;
    if ("" != childrenUrl && childrenUrl != null && childrenMenuType == "1"){
        if (childrenUrl.indexOf("[companyId]") > -1){
            childrenUrl = childrenUrl.replace("[companyId]",companyId);
        }
        if (childrenUrl.indexOf("[staffId]") > -1){
            childrenUrl = childrenUrl.replace("[staffId]",staffId);
        }
        if (childrenUrl.indexOf("[companyName]") > -1){
            childrenUrl = childrenUrl.replace("[companyName]",companyName);
        }
        if (childrenUrl.indexOf("[menuParentName]") > -1){
            childrenUrl = childrenUrl.replace("[menuParentName]",menuParentName);
        }

        str.push("<a class='height-a' href=" + basePath + childrenUrl + " class='div_menu_a'>" + data.menuName + "</a>");
    } else {
        str.push("<label id='" + data.menuId + "'>" +data.menuName + "</label>");
    }
}

/**
 * 初始化样式
 */
function initCss(){
    //计算中间区域宽度
    $(".p-height").each(function () {
        var pWth = $(".pc-box").width() - $(this).prev().width() - 80;
        $(this).width(pWth + "px")
    })
    //计算列表高度
    $(".p-height").each(function () {
        var pHei = $(this).parent().outerHeight() - 20;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 20 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
}