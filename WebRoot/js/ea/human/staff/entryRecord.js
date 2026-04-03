let pageNumber = 1, pageSize = 20,pageCount = 0;
let selectedId= "",scrollBool = true,echarsBool = true,clickTabNum = 0;
$(function () {
    initCss();
    bindEvents();
    getEntryStaffData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 70);
    $(".close-data").css("right","85px");
    $(".sec-list").height($(".content").height()  - $(".layui-tab-title").height() - $(".sec-nav").height()  - $(".spd-content").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() - 10);
}

/**
 * 点击事件
 */
function bindEvents() {
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        document.location.href = basePath + "ea/staff/ea_getEntryStaffRecordByStaffId.jspa?staffId=" + selectedId + "&&companyId="+companyId;
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
                $(".data-list").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
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