let pageNumber = 1, pageSize = 40,pageCount = 0;
let selectedId= "",dataObject={},scrollBool = true,type="",dataList = [];
$(function () {
    initCss();
    bindEvents();
    initData();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".div-sec-data").height( $(".sec-list").height() - $(".div-sec-data").height() )
    $(".data-list").height($(".div-sec-data").height() - $(".data-title").height() );
    const date = new Date(); // 将时间戳转换为 Date 对象
    const year = date.getFullYear(); // 获取年份
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 获取月份并补零
    const day = date.getDate().toString().padStart(2, '0'); // 获取日期并补零

    new Jdate({
        el: '#beginDate',
        format: 'YYYY-MM-DD',
        beginYear: 1960,
        endYear: 2100,
        value:`${year}-${month}-${day}`
    })
    new Jdate({
        el: '#endDate',
        format: 'YYYY-MM-DD',
        beginYear: 1960,
        endYear: 2100,
        value:`${year}-${month}-${day}`
    })


}

/**
 * 点击事件
 */
function bindEvents() {
    // 添加
    $(".add").click(function () {
        $(".sec-list").hide();
        $(".sec-detail").show();
        $(".sec-nav").hide();
        $("#account").val(account);
        type = "add";
        new Jdate({
            el: '#signDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            beginYear: 1960,
            endYear: 2100,
        })
    })
    // 修改
    $(".edit").click(function () {
        $(".sec-list").hide();
        $(".sec-detail").show();
        $(".sec-nav").hide();
        $("#account").val(account);
        const data = dataObject[selectedId];
        new Jdate({
            el: '#signDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            beginYear: 1960,
            endYear: 2100,
            value:data[3].substring(0,19)
        })
        type = "edit";
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
            type = "del";
            saveData();

        });
    });
    $(document).on("click", ".data-ul", function (event) {
        selectedId = event.currentTarget.id;
        $(".data-ul").removeClass("active");
        $(this).addClass("active");
    })


    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount && scrollBool){
                scrollBool = false;
                pageNumber++;
                initData();            }

        }
    })
}
function initData() {
    const param = new Array();
    param.push("account=" + account);
    param.push("&&beginDate=" + $("#beginDate").val());
    param.push("&&endDate=" + $("#endDate").val());
    param.push("&&pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/staff/sajax_ea_getSignData.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                dataList = list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                let name = "";
                for (let i = 0; i < length; i++) {
                    dataObject[list[i][1]] = list[i];
                    //const signDate = timestampToDate(new Date(list[i].signDate.time) );
                    //list[i]["signDate"] = signDate;
                    htmlstr.push("<ul id='" + list[i][1] + "'  class='data-ul data-ul-" + list[i][1] + "'>");
                    htmlstr.push("<li>" + list[i][2] + "</li>");
                    htmlstr.push("<li>" + list[i][4] + "</li>");
                    htmlstr.push("<li>" + list[i][3] + "</li>");
                    htmlstr.push("</ul>")
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

function saveData(){
    var data = {};
    data["type"] = type;
    if ("del" == type){
        data["signId"] = selectedId;
    } else {
        if ("edit" == type){
            data["signId"] = selectedId;
            data["signKey"] = dataObject[selectedId][0];
        }
        data["signDate"] = $("#signDate").val();
        data["account"] = $("#account").val();
    }
    let url = "ea/staff/sajax_ea_saveSignData.jspa";
    layer.load();
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: data,
        dataType:"json",
        async : true,
        success: function (data) {
            layer.close(layer.index);
            if (data == "success") {
                layer.msg("保存成功");
                $(".sec-list").show();
                $(".sec-detail").hide();
                $(".sec-nav").show();
                initData();
            } else  {
                layer.msg("保存失败");
            }
        },
        error: function (data) {
            layer.close(layer.index);
            layer.msg("保存失败");
        }
    });
}
function returnPage(){
    location.reload()
}