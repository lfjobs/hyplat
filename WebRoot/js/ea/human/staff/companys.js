let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;
$(function () {
    bindEvents();
    getEntryStaffData();
});


/**
 * 点击事件
 */
function bindEvents() {
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        selectCosId = event.currentTarget.attributes["cosid"].value;
        $(".data-ul").removeClass("active");
        $(this).addClass("active");
    })
    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                getEntryStaffData();
            }

        }
    })
}

function getEntryStaffData() {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getCompanys.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                // let name= "";
                for (let i = 0; i < length; i++) {
                    //公司id
                    htmlstr.push("<div id='" + list[i][1] + "' cosid='" + list[i][1] + "' class='data-div2 data-div-" + list[i][1] + "'>");
                    //公司照片
                    htmlstr.push("<div class='cpush-li1'><img width='55rem' height='55rem' src='" + basePath + (list[i][3] == null ? "" : list[i][3]) + "'/>" + "</div>");
                    //公司名称
                    htmlstr.push("<div class='cpush-li2'>" + (list[i][0] == null ? " " : list[i][0]) + "</div>");
                    //公司名称(小)
                    htmlstr.push("<div class='cpush-li3'>" + (list[i][0] == null ? " " : list[i][0]) + "</div>");
                    //公司类型
                    htmlstr.push("<div class='cpush-li4'>" + (list[i][4] == null ? " " : list[i][4]) + "</div>");
                    //公司类别
                    htmlstr.push("<div class='cpush-li5'>" + (list[i][6] == null ? " " : list[i][6]) + "</div>");
                    htmlstr.push("</div>");
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }
            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

$(document).on("click", ".data-div2", function (event) {
    selectedId = event.currentTarget.id;
    selectCosId = event.currentTarget.attributes["cosid"].value;
    var typeValue = getParameterByName("type");
    if (selectedId != null) {
        //跳到员工页面
        localStorage.setItem("selectedIdCompany", selectedId);
        localStorage.setItem("nameCompany", $(this).find('.cpush-li2').text());
        window.location.href = basePath + "page/pc/5L5C/marketing/selectStaff.jsp?type=" + typeValue;
    }

})
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
}