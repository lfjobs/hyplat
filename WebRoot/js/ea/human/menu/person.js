let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    initCss();
    bindEvents();
});

/**
 * 初始化样式
 */
function initCss(){

    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height() - 20);
    $(".sec-list").width($(".content").width() - 20);
}

function getPersonDataByRoleId() {
    const param = new Array();
    param.push("roleId=" + roleId)
    param.push("&&type=notRole");
    param.push("&&pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    let url = basePath + "ea/menu/sajax_ea_getPersonDataByRoleId.jspa?"+ param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            var listData = eval("(" + data + ")");
            var list = listData["list"];
            pageCount = list["pageCount"];
            var htmlstr = new Array();
            let dateObj ;
            for (var i = 0; i < list.length; i++) {
                htmlstr.push("<div class=\"info-card\">");
                htmlstr.push("<div class=\"visitor\">" + list[i]["accountName"] + "</div>");
                htmlstr.push("</div>");
            }
            const moreData = document.getElementById('more-data');
            if (moreData != null){
                moreData.remove()
            }
            $(".div-card").append(htmlstr.join(""));

        },
        error: function (data) {
            layer.msg("获取数据失败");
        }
    })



}
function bindEvent(){
    // 监听滚动事件
    const divElement = document.querySelector('.content');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight) {
            if (pageNumber < pageCount){
                $(".company-ul").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getPersonDataByRoleId();
            }

        }
    })
}




