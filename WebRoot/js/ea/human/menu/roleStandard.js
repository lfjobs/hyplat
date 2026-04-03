var selectedRoleId= ""
$(function () {
    getRoleStandardList();
    initCss();
    bindEvents();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $("#roleList").width($(".sec-list").width() - 20);
}
/**
 * 根据公司id获取角色数据
 */
function getRoleStandardList(){
    var url = basePath + "ea/rolestandard/sajax_ea_getRoleStandardList.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            var str = new Array();
            if (data == ""){
                str.push("<li style='display:flex;align-items:center;justify-content:center;margin-top:50%'>暂无数据</li>");
            } else {
                var dataList = eval("(" + data + ")");
                var dataListLength = dataList.length;
                if (dataListLength > 0){
                    for (var i = 0; i < dataList.length; i++) {
                        const roleData = dataList[i];
                        const roleId = roleData["roleId"];
                        str.push("<li id='"  + roleId + "'class=\"div-role-li role-id-" + roleId + " \">");
                        str.push("<div class=\"solid-circle\"></div>");
                        str.push("<i class=\"layui-icon layui-person\">&#xe612;</i>");
                        str.push("<p class=\"role_name\">" + roleData["roleName"] + "</p>" );
                        str.push("</li>");
                    }

                } else {
                    str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
                }

            }
            const moreData = document.getElementById('more-data');
            if (moreData != null){
                moreData.remove()
            }
            $("#roleList").append(str.join(""));

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });


}

/**
 * 点击事件
 */
function bindEvents() {
    // 新建
    $(".draft").click(function (event) {
        const param = [];
        param.push("type=add");
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleStandardAdd.jsp?" + param.join("") ;

    });
    $(document).on("click", ".div-role-li", function (event) {
        selectedRoleId = event.currentTarget.id;
        $(".div-role-li").removeClass("active");
        $(this).addClass("active");
    })
    // 编辑
    $(".edit").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        const param = [];
        param.push("roleId=" + selectedRoleId);
        document.location.href = basePath + "ea/rolestandard/ea_getRoleStandardByRoleId.jspa?" + param.join("");
    });
    // 页面授权
    $(".power").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleStandardAdd.jsp?type=power&roleId=" + selectedRoleId;
    });

    //删除
    $(".del").click(function () {
        if (selectedRoleId == ""){
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
                + "ea/rolestandard/sajax_ea_deleteRoleStandardByRoleId.jspa?roleId=" + selectedRoleId ;
            $.ajax({
                type: "GET",
                url: url,
                async: false,
                dataType: "json",
                success: function (data) {
                    $(".role-id-" + selectedRoleId).remove();
                    layer.close(layer.index);
                    selectedRoleId = "";
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            })

        });
    });



}



