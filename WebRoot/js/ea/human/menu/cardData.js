var num =0;
let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    initCss();
    getPersonDataByRoleId();
    bindEvent();
});

function initCss(){
    $(".content").height($(document).height() - $("header").height());
    $(".card-body").height($(".content").height() - $(".div-button").height() - 5);
    $(".div-card").html("");
}
function getPersonDataByRoleId() {
    const param = new Array();
    param.push("roleId=" + roleId);
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
            var list = listData["list"],staffId = "";
            pageCount = list["pageCount"];
            var htmlstr = new Array();
            let dateObj ;
            for (var i = 0; i < list.length; i++) {
                staffId = list[i]["staffID"];
                htmlstr.push("<div class=\"info-card card-staff-id-" + staffId + " \">");
                htmlstr.push("<div class=\"visitor\" + style='float:left'>" + list[i]["staffName"] + "(" + list[i]["reference"]+ ")</div>");
                htmlstr.push("<div style='float:left'><i class=\"layui-icon\"  onclick=deleteRoleStaffByStaffId('" +
                    staffId +"')>&#x1006;</i> </div> ")
                htmlstr.push("</div>");
            }
            const moreData = document.getElementById('more-data');
            if (moreData != null){
                moreData.remove()
            }
            $(".div-card").append(htmlstr.join(""));
            $(".info-card").width($(document).width() - 45);
            $(".visitor").width($(".info-card").width() - 20);

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
function addData(){
    document.location.href = basePath + "page/WFJClient/pc/5l5C/human/deptPerson.jsp?type=personRole&id=" + roleId;
}
function deleteRoleStaffByStaffId(staffId){
    layer.confirm('确定删除', {
        title:'温馨提示',
        skin: 'delete-class',
        btn: ['取消','确定']
    }, function(){
        layer.close(layer.index);
    }, function() {
        var url = basePath
            + "ea/menu/sajax_ea_deleteRoleStaffByStaffId.jspa?roleId=" + roleId + "&&staffId=" + staffId;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                $(".card-staff-id-" + staffId).remove();
                layer.close(layer.index);
            },
            error: function (data) {
                layer.msg("保存失败");
            }
        })

    });
}


