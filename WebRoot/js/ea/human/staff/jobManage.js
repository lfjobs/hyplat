let pageNumber = 1, pageSize = 40,pageCount = 0;
let selectedId= "",selectCosId="",scrollBool = true;
let type = "0"
$(function () {
    initCss();
    bindEvents();
    getStaffDataList();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() - $(".spd-content").height() + 20);
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 70);
    var a = $(".sec-hide").width();
    $(".layui-tabs-card").width($(".sec-hide").width() - $(".div-person-num").width() - 10)
    $(".close-data").css("right","85px");
}

/**
 * 点击事件
 */
function bindEvents() {
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        document.location.href = basePath + "ea/staff/ea_getStaffDataByStaffId.jspa?type=query&&staffId=" + selectedId;
    })
    $(document).on("click", ".div-tab li", function (event) {
        layer.load();
        type = event.currentTarget.id;
        if ("0" === type){
            $(".div-person-title").html("入职");
        } else if ("1" === type){
            $(".div-person-title").html("专岗");
        } else if ("2" === type){
            $(".div-person-title").html("兼岗");
        }
        pageNumber = 1;
        getStaffDataList()
    })

    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount && scrollBool){
                scrollBool = false;
                pageNumber++;
                getStaffDataList();            }

        }
    })

}
function clearQueryName(){
    $("#queryName").val("");
    pageNumber = 1;
    getStaffDataList();
}
function getDataByQueryName(){
    pageNumber = 1;
    getStaffDataList();
}
function getStaffDataList(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&queryName=" + $("#queryName").val());
    param.push("&&cos.cosStatus=50");
    if ("1" === type){
        param.push("&&cos.status=01");
    } else if ("2" === type){
        param.push("&&cos.status=00");
    }

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
                $(".data-list").css({"align-items":"center","justify-content":"center"});
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
            layer.closeAll();
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}