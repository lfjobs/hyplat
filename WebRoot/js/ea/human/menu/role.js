var selectedRoleId= ""
let pageNumber = 1, pageSize = 20,pageCount = 0;
$(function () {
    getRoleDataByCompanyId();
    initCss();
    bindEvents();
});

/**
 * 初始化样式
 */
function initCss(){

    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
   // $("#roleList").height($(".sec-list").height());
    $("#roleList").width($(".sec-list").width() - 20);
}
/**
 * 根据公司id获取角色数据
 */
function getRoleDataByCompanyId(){
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    var url = basePath + "ea/menu/sajax_ea_getListCRole.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function (data) {
            var str = new Array();
            if (data == null){
                str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
            } else {
                var list = eval("(" + data + ")");
                var dataList = list["list"];
                pageCount = list["pageCount"];
                var dataListLength = dataList.length;
                if (dataListLength > 0){
                    for (var i = 0; i < dataList.length; i++) {
                        var roleData = dataList[i];
                        str.push("<li id='"  + roleData[0] +"' class='div-role-li'>");
                        str.push("<div class=\"solid-circle\"></div>");
                        str.push("<i class=\"layui-icon layui-person\">&#xe612;</i>");
                        str.push("<p class=\"role_name\">" + roleData[1] + "</p>" );
                        str.push("<p class=\"role_person_num person_data\">" + roleData[2] + "人</p>");
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
        var param = [];
        param.push("type=add");
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleAdd.jsp?" + param.join("") ;

    });
    $(document).on("click", ".div-role-li", function (event) {
        selectedRoleId = event.currentTarget.id;
        $(".div-role-li").removeClass("active");
        $(this).addClass("active");
        /*var personNum = $(this).find(".role_person_num").html().replace("人","");
        if (personNum == "0"){
            $(".person").hide();
        } else {
            $(".person").show();
        }*/
    })
    // 编辑
    $(".edit").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "ea/menu/ea_getRoleByRoleId.jspa?roleId=" + selectedRoleId;
    });
    // 编辑
    $(".power").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/roleAdd.jsp?type=power&roleId=" + selectedRoleId;
    });

    //删除
    var del = 0;
    $(".del").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择要删除的数据");
            return false;
        }
        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        del = 1;
        return false;
    });

    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    })
    //确认删除
    $(".close-confirm").click(function () {
        if (del == 0) {
            $(".div-tingyong").hide();
            return false;
        }
        del = 0;
        $(".div-tingyong").hide();
        var url = basePath
            + "ea/menu/sajax_ea_delCRole.jspa?roleId=" + selectedRoleId;
        $.ajax({
            type: "GET",
            url: url,
            async: false,
            dataType: "json",
            success: function (data) {
                if (data == "success") {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/human/role.jsp";
                } else if (data == "existAccount") {
                    layer.msg("该角色存在账户，不能删除");
                    return;
                }
            },
            error: function (data) {
                layer.msg("删除失败")
            }
        });
    })

    // 人员查询
    $(".person").click(function () {
        if (selectedRoleId == ""){
            layer.msg("请选择将要查看的数据");
            return false;
        }
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/cardData.jsp?type=personRole&roleId=" + selectedRoleId;
    });
    // 监听滚动事件
    const divElement = document.querySelector('.role-data');
    divElement.addEventListener('scroll', function() {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1==1) {
            if (pageNumber < pageCount){
                $("#roleList").append("<li style='display:flex;align-items:center;justify-content:center;color:#918787' id='more-data'>正在加载更多数据</li>");
                pageNumber++;
                getRoleDataByCompanyId();
            }

        }
    })
}



