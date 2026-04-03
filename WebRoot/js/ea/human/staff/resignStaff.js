let pageNumber = 1, pageSize = 20,pageCount = 0;
let selectedId= "",scrollBool = true;
$(function () {
    initCss();
    bindEvents();
    getResignStaffData();
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
        document.location.href = basePath + "ea/staff/ea_getResignStaffDataByStaffId.jspa?type=edit&&staffId=" + selectedId;
    })

    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount && scrollBool){
                scrollBool = false;
                pageNumber++;
                getResignStaffData();            }

        }
    })

}
function clearQueryName(){
    $("#queryName").val("");
    pageNumber = 1;
    getResignStaffData();
}
function getDataByQueryName(){
    pageNumber = 1;
    getResignStaffData();
}
function getResignStaffData(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&queryName=" + $("#queryName").val());
    const url = basePath + "ea/staff/sajax_ea_getResignStaffData.jspa?" + param.join("");

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
                    htmlstr.push("<ul id='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] +"'>");
                    htmlstr.push("<li>" + (pageSize*(pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li>" + name + "</li>");
                    htmlstr.push("<li>" + (list[i][3] == null ? " " : list[i][3]) + "</li>");
                    htmlstr.push("<li>" + (list[i][7] == null ? " " : list[i][7]) + "</li>");
                    htmlstr.push("<li>" + (list[i][13] == null ? " " : list[i][13]) + "</li>")
                    htmlstr.push("<li>" + (list[i][9] == null ? " " : list[i][9]) + "</li>");
                    htmlstr.push("<li>" + (list[i][6] == null ? " " : list[i][6]) + "</li>");
                    htmlstr.push("<li>" + (list[i][1] == null ? " " : list[i][1]) + "</li>");
                    htmlstr.push("<li>" + (list[i][12] == null ? " " : list[i][12]) + "</li>");
                    htmlstr.push("<li>" + (list[i][11] == null ? " " : list[i][11]) + "</li>");
                    htmlstr.push("<li>" + (list[i][5] == null ? "无" : (list[i][5] + "级")) + "</li>");

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