let pageNumber = 1, pageSize = 20,pageCount = 0;
let selectedId= "",selectCosId="",scrollBool = true,echarsBool = true,clickTabNum = 0;
$(function () {
    if (performance.navigation.type == performance.navigation.TYPE_BACK_FORWARD) {
        clickTabNum = localStorage.getItem('clickTabNum');
    } else {
        clickTabNum = "0";
        // 页面通过其他方式加载，比如刷新
        localStorage.setItem('clickTabNum',"0");
    }
    initCss();
    bindEvents();
    if (clickTabNum === "0"){
        layer.load();
        getEntryStaffData();
    } else {
        $(".select-li-" + clickTabNum).click();
    }

});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height()  - $(".layui-tab-title").height() - $(".sec-nav").height()  - $(".spd-content").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() + 20);
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 70);
    $(".close-data").css("right","85px");
    $(".layui-tab-content").height($(".content").height()  - $(".layui-tab-title").height());

}

/**
 * 点击事件
 */
function bindEvents() {
    // 添加
    $(".add").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/staff/staffData.jsp?type=add";
    })
    // 修改
    $(".edit").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        document.location.href = basePath + "ea/staff/ea_getStaffDataByStaffId.jspa?type=edit&&staffId=" + selectedId;
    });
    // 查询
    $(".query").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        document.location.href = basePath + "ea/staff/ea_getStaffDataByStaffId.jspa?type=query&&staffId=" + selectedId;
    });
    // 删除
    $(".del").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要删除的数据");
            return false;
        }
        layer.confirm('确定删除', {
            title:'温馨提示',
            skin: 'delete-class',
            btn: ['取消','确定']
        }, function(){
            layer.close(layer.index);
        }, function() {
            var url = basePath
                + "ea/staff/sajax_ea_deleteStaffByStaffId.jspa?staffId=" + selectedId + "&&cosId=" + selectCosId;
            $.ajax({
                type: "GET",
                url: url,
                async: false,
                dataType: "json",
                success: function (data) {
                    pageNumber = 1, pageSize = 40,pageCount = 0;
                    layer.load();
                    getEntryStaffData();
                    layer.close(layer.index);
                    selectedId = "";
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            })

        });
    });
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        document.location.href = basePath + "ea/staff/ea_getStaffDataByStaffId.jspa?type=query&&staffId=" + selectedId;
    })


    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount && scrollBool){
                scrollBool = false;
                pageNumber++;
                getEntryStaffData();            }

        }
    })
    $(document).on("click", ".layui-tab-title li", function (event) {
        var type = event.currentTarget.id;
        localStorage.setItem('clickTabNum',type);
        if (type === "0"){
            layer.load();
            getEntryStaffData();
        }else if (type === "1" && echarsBool){
            /*$(".div-echarts").height($(".content").height() - $(".layui-tab-title").height() );
            entryStatistic.initData();*/
            var url ="/page/WFJClient/pc/5l5C/human/staff/entryStatistics.jsp?type=iframe";
            var original = document.getElementsByTagName("iframe")[0];
            var newFrame = document.createElement("iframe");
            newFrame.setAttribute('frameborder', '0', 0);
            newFrame.src = basePath+url;
            var parent = original.parentNode;
            parent.replaceChild(newFrame,original);
            $(".div-statistics").height($(".layui-tab-content").height());
            echarsBool = false;
        } else if (type === "2"){
            var url ="/page/WFJClient/pc/5l5C/human/staff/employmentContract.jsp?type=iframe";
            var original = document.getElementsByTagName("iframe")[1];
            var newFrame = document.createElement("iframe");
            newFrame.setAttribute('frameborder', '0', 0);
            newFrame.src = basePath+url;
            var parent = original.parentNode;
            parent.replaceChild(newFrame,original);
            $(".div-staff-contract").height($(".layui-tab-content").height());
        }
    })

}
function clearQueryName(){
    $("#queryName").val("");
    pageNumber = 1;
    getEntryStaffData();
}
function getDataByQueryName(){
    pageNumber = 1;
    getEntryStaffData();
}
function getEntryStaffData(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&queryName=" + $("#queryName").val());
    const url = basePath + "ea/staff/sajax_ea_getEntryStaffData.jspa?" + param.join("");

    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".data-list").html("暂无数据");
                $(".data-list").css({"align-items":"center","justify-content":"center","margin":"100px"});
            } else {
                $(".data-list").css({"margin":"0"});
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                $(".personData").html(codeList["recordCount"]);
                const length = list.length;
                const htmlstr = new Array();
                let name= "";
                for (let i = 0; i < length; i++) {
                    name = list[i][2];
                    if (name.length >5 ){
                        name = name.substring(0,5) + "...";
                    }
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][7] + "' class='data-ul data-ul-" + list[i][0] +"'>");
                    htmlstr.push("<li>" + (pageSize*(pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li>" + name + "</li>");
                    htmlstr.push("<li>" + (list[i][3] == null ? " " : list[i][3]) + "</li>");
                    htmlstr.push("<li>" + (list[i][6] == null ? " " : list[i][6]) + "</li>");
                    htmlstr.push("<li>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                    htmlstr.push("<li>" + (list[i][7] == "01" ? "专岗" : "兼岗") + "</li>");
                    htmlstr.push("<li>" + (list[i][1] == null ? " " : list[i][1]) + "</li>");
                    htmlstr.push("<li>" + (list[i][8] == null ? " " : list[i][8]) + "</li>");
                    htmlstr.push("<li>" + (list[i][9] == null ? "无" : (list[i][9] + "级")) + "</li>");

                    htmlstr.push("</ul>")
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
                if (pageNumber > 1){
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }

            }
            scrollBool = true;
            layer.close(layer.index);
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
            layer.close(layer.index);
        }
    });
}