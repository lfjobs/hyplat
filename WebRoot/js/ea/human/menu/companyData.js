var num =0;
let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    initCss();
    getVisitByCompanyId();
    bindEvent();
});

function initCss(){
    $(".content").height($(document).height() - $("header").height());
}
function getVisitByCompanyId() {
    const param = new Array();
    param.push("companyId=" + companyId);
    param.push("&&pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    let url = basePath + "ea/menu/sajax_ea_getVisitByCompanyId.jspa?"+ param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {},
        success: function (data) {
            var listData = eval("(" + data + ")");
            var list = listData["list"];
            pageCount = listData["pageCount"];
            if (pageCount == 0){
                $("#visitListName").val("无回访记录");
            } else {
                var htmlstr = new Array();
                let dateObj ;
                for (var i = 0; i < list.length; i++) {
                    var date = new Date(list[i]["visitedTime"]["time"]);
                    dateObj = date.toISOString().substring(0, 10);
                    htmlstr.push("<div class=\"info-card\">");
                    htmlstr.push("<div class=\"index\">序号：" + num ++ + "</div>" );
                    htmlstr.push("<div class=\"visitor\">被回访人：" + list[i]["visitedUser"] + "</div>");
                    htmlstr.push("<div class=\"visit-type\">访问类型：" + list[i]["telType"] + "</div>");
                    htmlstr.push("<div class=\"visit-time\">访问时间：" + dateObj + "</div>");
                    htmlstr.push("<div class=\"visit-content\">访问内容：" + list[i]["visitedContent"] + "</div>");
                    htmlstr.push("</div>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null){
                    moreData.remove()
                }
                $(".div-card").append(htmlstr.join(""));
            }

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
                getVisitByCompanyId();
            }

        }
    })
}


