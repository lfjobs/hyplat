$(document).ready(function () {

    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    $('.address').flexigrid({
        height: 345,
        width: 'auto',
        minwidth: 30,
        checkbox: true,// 是否要多选框
        title: activity + "产品管理<form name='yjForm' id='yjForm' method='post'>" +
        "<input type='submit' name='submit' style='display: none' />" +
        "活动名称：" +
        "<input name='search' value='" + search + "'/>" +
        "<input type='button' id='search' value='查询 ' />" +
        "</form>",
        minheight: 80,
        buttons: [{
            name: '活动添加',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '活动修改',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '查看',
            bclass: 'see',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '活动删除',
            bclass: 'delete',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '活动暂停',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '活动开启',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });

    $("div.bDiv", $("div.flexigrid")).css("height", "410px");

    function action(com, grid) {
        switch (com) {
            case '活动添加' :
                window.open(basePath
                    + "ea/activityPrice/ea_toActivityAdd.jspa?activityType=" + activityType);
                break;
            case '活动修改':
                var activityId = $('input[name="checkedId"]:checked').val();
                if (typeof(activityId) == "undefined" || activityId == "") {
                    alert('请选择！');
                    return
                } else {
                    url = basePath + "ea/activityPrice/ea_toActivityUpdate.jspa?activity.activityId=" + activityId + "&activityType=" + activityType;
                    open(url);
                }
                break;
            case '查看':
                var activityId = $('input[name="checkedId"]:checked').val();
                if (typeof(activityId) == "undefined" || activityId == "") {
                    alert('请选择！');
                    return
                } else {
                    url = basePath + "ea/activityPrice/ea_toActivityUpdate.jspa?activity.activityId=" + activityId + "&flag=01"
                    open(url);
                }
                break;
            case '活动删除':
                var activityId = $('input[name="checkedId"]:checked').val();
                if (typeof(activityId) == "undefined" || activityId == "") {
                    alert('请选择！');
                    return
                } else {
                    if (confirm("确定删除吗")) {
                        $.ajax({
                            type: "POST",
                            url: basePath + "ea/activityPrice/sajax_ea_activityDeleteById.jspa",
                            data: {
                                "activity.activityId": activityId
                            },
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                var code = member.code;
                                if (code == "200") {
                                    alert("删除成功！");
                                    //window.opener.location.reload();
                                    // window.close();
                                    location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa?activityType=" + activityType;
                                } else {
                                    //未登录
                                    location.href = basePath + "page/ea/not_login.jsp";
                                }
                            },
                            error: function (data) {
                                alert("删除异常！");
                            },
                            dateType: "json"
                        });
                        return true;
                    }
                    return false;
                    //window.location.reload();//刷新当前页面
                }
                break;
            case '活动暂停':
                var activityId = $('input[name="checkedId"]:checked').val();
                if (typeof(activityId) == "undefined" || activityId == "") {
                    alert('请选择！');
                    return
                } else {
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/activityPrice/sajax_ea_activitySuspendById.jspa",
                        data: {
                            "activity.activityId": activityId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var code = member.code;
                            if (code == "200") {
                                alert("已暂停！");
                                /* window.opener.location.reload();
                                 window.close();*/
                                location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa?activityType=" + activityType;
                            } else {
                                //未登录
                                location.href = basePath + "page/ea/not_login.jsp";
                            }
                        },
                        error: function (data) {
                            alert("暂停异常！");
                        },
                        dateType: "json"
                    });
                    //window.location.reload();//刷新当前页面
                }
                break;
            case '活动开启':
                var activityId = $('input[name="checkedId"]:checked').val();
                if (typeof(activityId) == "undefined" || activityId == "") {
                    alert('请选择！');
                    return
                } else {
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/activityPrice/sajax_ea_activityOpenById.jspa",
                        data: {
                            "activity.activityId": activityId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var code = member.code;
                            if (code == "200") {
                                alert("已开启！");
                                location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa?activityType=" + activityType;
                            } else if (code == "201") {
                                alert("活动已结束！已不能开启！");
                                location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa?activityType=" + activityType;
                            } else {
                                //未登录
                                location.href = basePath + "page/ea/not_login.jsp";
                            }
                        },
                        error: function (data) {
                            alert("暂停异常！");
                            console.log("开启异常！")
                        },
                        dateType: "json"
                    });
                    //window.location.reload();//刷新当前页面
                }
                break;
        }
        ;
    }

    //用于阻止复选框的冒泡行为；
    $(".address input:checkbox[name='a']").click(function (event) {
        event.stopPropagation();

    });

    $("table tr[id]").click(function () {
        opertionID = this.id;
        productdesignID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

    });


    $("#search").click(function () {
        if ($("form .error").length) {
            return false;
        }
        $("#yjForm").attr(
            "action",
            basePath + "ea/activityPrice/ea_selectActivityList.jspa?pageForm.pageNumber="
            + pNumber + "&activityType=" + activityType + "");
        document.yjForm.submit.click();
    });
});



