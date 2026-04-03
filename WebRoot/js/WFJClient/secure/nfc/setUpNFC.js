var pageNumber = 0;
var pageSize = 25;
$(function () {
    var paramtype = 0;// 0：公司  1：责任人  2：安全类别
    //公司列表
    $(".companyName").click(function () {
        pageNumber = 0;
        $(".li-text").text("请选择公司");
        $("#search").attr("placeholder", "请输入公司名称");
        paramtype = 0;
        $(".ul-list").children().remove()
        $("#divList").show();
        /*getList(paramtype);*/
    });

    //责任人列表
    $(".staffName").click(function () {
        if ($("#companyId").val().length <= 0) {
            $(".titlep").text("请先选择公司！");
            $(".div-tingyong").show();
        } else {
            pageNumber = 0;
            $(".li-text").text("请选择责任人");
            $("#search").attr("placeholder", "请输入责任人姓名");
            paramtype = 1;
            $(".ul-list").children().remove();
            /*getList(paramtype);*/
            $("#divList").show();
        }
    });

    $(".oaskName").click(function () {
        pageNumber = 0;
        $(".li-text").text("请选择安全类别");
        $("#search").attr("placeholder", "请输入关键字");
        paramtype = 2;
        $(".ul-list").children().remove();
        getList(paramtype);
        $("#divList").show();
    });

    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                getList(paramtype);
            }
        }
    });
    //搜索查询
    $("#search").bind('input propertychange', function () {
        //if (($("#companyId").val().length <= 0 && $(this).val().length >= 4) || ($("#companyId").val().length > 0 && $(this).val().length >= 1)) {
            $(".ul-list").html("");
            pageNumber = 0;
            pageCount = 0;
            getList(paramtype);
        //}
    });


    //选中
    $(document).on("click", ".ul-list li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul-list .active").removeClass("active");
            $(this).addClass("active");
        }
    });

    //确定按钮
    $(".sec-bottom").click(function () {
        var li = $(".ul-list li.active");
        var length = $(li).length;
        if (length < 1) {
            return false;
        }
        if (paramtype == 1) {
            $("#StaffId").val($(li).attr("id"));
            $("#staffName").val($(li).find(".ttext").text());
        } else if (paramtype == 2) {
            $("#oaskId").val($(li).attr("id"));
            $("#oaskName").val($(li).find(".ttext").text());
        } else {
            $("#companyId").val($(li).attr("id"));
            $("#companyName").val($(li).find(".ttext").text());
        }
        $("#search").val("");
        $("#divList").hide();
    });


    $(".close-confirm").click(function () {
        $(".div-dqd").hide();
    });

    $(".close-a").click(function () {
        $("#divList").hide();
    });

    $(".div-bottom").click(function () {
        saveNfc();
    });
})

function getList(paramtype) {
    /*if(pageNumber==0){
        $("#search").val("");
    }*/
    var url;
    var comid = $("#companyId").val();
    if (comid.length <= 0) {
        if (paramtype == 0) {
            url = "/ea/bindnfc/sajax_ea_getCom.jspa";
        } else {
            $(".titlep").text("请先选择公司！");
            $(".div-tingyong").show();
            return false;
        }
    } else {
        if (paramtype == 2) {
            url = "/ea/bindnfc/sajax_ea_getOASafeKindList.jspa?date="
                + new Date().toLocaleString() + "&companyID=" + comid;
        } else if (paramtype == 1) {
            url = "/ea/bindnfc/sajax_ea_getStaff.jspa?companyID=" + comid;
        } else {
            url = "/ea/bindnfc/sajax_ea_getCom.jspa";
        }
    }
    pageNumber = pageNumber + 1;
    $.ajax({
        url: basePath + url,
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            parameter: $("#search").val()
        },
        success: function (data) {
            if (data != null) {
                var m = eval("(" + data + ")");
                var arry = m.pageForm;
                var htmlstr = [];
                if (arry == null) {
                    if (paramtype == 2) {
                        $(".titlep").text($("#companyName").val() + "没有初始化！");
                    } else if (paramtype == 1) {
                        $(".titlep").text($("#companyName").val() + "没有在职员工！");
                    }
                    $(".div-tingyong").show();
                    return false;
                }
                pageNumber = arry.pageNumber;
                pageCount = arry.pageCount;
                $(".last1").remove();
                for (var i = 0; i < arry.list.length; i++) {
                    if (i == arry.list.length - 1) {
                        htmlstr.push("<li class='clearfix last1' id='" + arry.list[i][0] + "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i][0] + "'>");
                    }
                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<p class='ttext'>");
                    htmlstr.push(arry.list[i][1]);
                    htmlstr.push("</p>");
                    htmlstr.push("</li>");
                }

                $(".ul-list").append(htmlstr.join(""));

            }
            console.log(data);
        },
        error: function (data) {
            console.log(data);
        }

    });
}

function saveNfc() {
    var isnull=true;
    $(".isNotnull").each(function () {
        console.log($(this).val());
        if ($(this).val()==null||$(this).val()==""){
            isnull=false;
        }
    });
    if (isnull){
        $(".loading").show();
        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/bindnfc/ea_saveNFC.jspa");
        document.form.submit.click();
        token = 13;
        $(".loading").hide();
        localforage.removeItem('dataKey');
    }else {
        $(".titlep").text("必填项为空！不能提交");
        $(".div-tingyong").show();
        return false;
    }
}