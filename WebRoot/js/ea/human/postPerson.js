let pageNumber = 1, pageSize = 40,pageCount = 0;
$(function () {
    initCss();
    bindEvents();
    getData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".spd-content").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() );
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 70);
    $(".close-data").css("right","85px");
}



/**
 * 点击事件
 */
function bindEvents() {
    layui.use('form', function() {
        form = layui.form;
        form.render('select');
        form.render('radio');
        form.on('select(pageSelect)', function(data){
            pageNumber = 1;
            scrollBool = false;
            pageSize = data.value;
            getData();
        });
    })
    $(document).on("click", ".page-last", function (event) {
        if (scrollBool && pageNumber > 1){
            scrollBool = false;
            pageNumber--;
            getData();
        }

    })

    $(document).on("click", ".page-next", function (event) {
        if (scrollBool && pageNumber < pageCount){
            scrollBool = false;
            pageNumber++;
            getData();

        }

    })


}

function getData(){
    layer.load();
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&search=" + $("#queryName").val());
    param.push("&&departmentPost.depPostID=" + depPostId);
    const url = basePath + "ea/post/sajax_ea_getListPerson.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null){
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display":"flex","align-items":"center","margin-left":"50%"});
            } else {
                pageCount = codeList["pageCount"];
                $(".pageNumber").html(pageNumber);
                $(".pageCount").html(pageCount);
                $(".recordCount").html(codeList["recordCount"]);
                const list = codeList.list;
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul>");
                    htmlstr.push("<li>" + (pageSize*(pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li>" + list[i][1] + "</li>");
                    htmlstr.push("<li>" + (list[i][2] === null? "" : list[i][2]) + "</li>");
                    htmlstr.push("<li>" + list[i][3] + "</li>");
                    htmlstr.push("<li>" + (list[i][4] === "01"? "专岗" : "兼岗") + "</li>");
                    htmlstr.push("<li>" + list[i][5] + "</li>");
                    htmlstr.push("<li>" + list[i][8] + "</li>");
                    htmlstr.push("<li>" + list[i][9] + "</li>");

                   /* htmlstr.push("<li>" + (list[i]["adminNum"] === null? "0" : list[i]["adminNum"]) + "</li>");*/

                    htmlstr.push("</ul>")
                }
                $(".data-list").html(htmlstr.join(""));
            }
            scrollBool = true;
            layer.close(layer.index)
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
            layer.close(layer.index)
        }
    });
}
function clearQueryName(){
    $("#queryName").val("");
    pageNumber = 1;
    getData();
}
function getDataByName(){
    pageNumber = 1;
    getData();
}
