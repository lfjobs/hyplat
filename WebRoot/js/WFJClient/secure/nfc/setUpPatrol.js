var pageNumber = 0;
var pageSize = 25;
$(function () {

    /*************安全类别开始*************/
    $(".oaskName").click(function () {
        pageNumber = 0;
        $(".li-text").text("请选择安全类别");
        $("#search").attr("placeholder", "请输入关键字");
        $(".ul-list").children().remove();
        getList();
        $("#divList").show();
    });

    //选中
    $(document).on("click", ".ul-list li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            /*$(".ul-list .active").removeClass("active");*/
            $(this).addClass("active");
        }
    });

    //确定按钮
    $(".sec-bottom").click(function () {
        var li = $(".ul-list li.active");
        var length = $(".ul-list li.active").length;
        if (length < 1) {
            return false;
        }
        var oask = "";
        var oaskname = "";
        $(".ul-list li.active").each(function () {
            oask += $(this).attr("id") + "-" + $(this).find(".ttext").text() + ",";
            oaskname += $(this).find(".ttext").text() + ",";
        });
        $("#oask").val(oask);
        $("#oaskName").val(oaskname);

        $("#search").val("");
        $("#divList").hide();
    });

    //搜索查询
    $("#search").bind('input propertychange', function () {
        //if (($("#companyId").val().length <= 0 && $(this).val().length >= 4) || ($("#companyId").val().length > 0 && $(this).val().length >= 1)) {
        $(".ul-list").html("");
        pageNumber = 0;
        pageCount = 0;
        getList();
    });

    $(".close-a").click(function () {
        $("#divList").hide();
    });
    /*************安全类别开始*************/

    /*************图片上传开始*************/
    $(".img").click(function () {
        $(".div-tupian2").css({"opacity": "1", "transform": 'translate(0)'});
    });

    $('#as').diyUpload({
        url: basePath + 'ea/productsmanag/sajax_ea_uplodFile.jspa',
        data: {
            "companyID": $("#companyID").val()
        },
        success: function (data) {
            console.log(data);
            var member = eval("(" + data + ")");
            var path = member.path;
            var name = member.name;
            var siSuccess = member.siSuccess;
            if (siSuccess) {
                $(".diyFileName").each(function () {
                    if ($(this).text() == name) {
                        $(this).after("<div class='diyImagesPath' style='display: none;'>" + path + "</div>");
                        var ImagesPath = $("#ImagesPath").val();
                        ImagesPath += path + ",";
                        $("#ImagesPath").val(ImagesPath);
                    }
                });
            }
        },
        error: function (err) {
            console.info(err);
        },
        accept: {
            title: "Images",
            extensions: "gif,jpg,jpeg,bmp,png",
            mimeTypes: "image/*"
        },
        buttonText: '<div><img src="' + basePath + 'images/WFJClient/add.png"/></div><p>图片上传</p>',
        chunked: false,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 9,
        fileSizeLimit: 9 * 1024 * 1024 * 1024,
        fileSingleSizeLimit: 5 * 1024 * 1024
    });

    // 	关闭
    $(".div-tupian2 .div-close").on("click", function () {
        $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-tupian2 .p-tijiao", function () {
        $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
        $(".diyImagesPath").each(function () {
            $(".div-img").append("<img src='" + basePath + $(this).text() + "'/>");
        });
        $(".tupian1").hide();
        $(".tupian2").show();
    });

    /*************图片上传结束*************/

    /*************视频上传开始*************/
    $(".video").click(function () {
        $(".div-shipin2").css({"opacity": "1", "transform": 'translate(0)'});
    });

    $('#ass').diyUpload({
        url: basePath + 'ea/productsmanag/sajax_ea_uplodFile.jspa',
        data: {
            "companyID": $("#companyID").val()
        },
        success: function (data) {
            console.log(data);
            var member = eval("(" + data + ")");
            var path = member.path;
            var name = member.name;
            var siSuccess = member.siSuccess;
            if (siSuccess) {
                $(".diyFileName").each(function () {
                    if ($(this).text() == name) {
                        $(this).after("<div class='diyVideoPath' style='display: none;'>" + path + "</div>");
                        var VideoPath = $("#VideoPath").val();
                        VideoPath += path + ",";
                        $("#VideoPath").val(VideoPath);
                    }
                });
            }
        },
        error: function (err) {
            console.info(err);
        },
        accept: {
            title: "Video",
            extensions: "wmv,asf,asx,rm,rmvb,mp4,3gp,mov,m4v,avi,dat,mkv,flv,vob",
            mimeTypes: "video/*"
        },
        buttonText: '<div><img src="' + basePath + 'images/WFJClient/add.png"/></div><p>视频上传</p>',
        chunked: true,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 9,
        fileSizeLimit: 9 * 100 * 1024 * 1024,
        fileSingleSizeLimit: 90 * 1024 * 1024
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-shipin2 .p-tijiao", function () {
        $(".div-shipin2").css({"opacity": "0", "transform": 'translate(1000000px)'});
        $(".diyVideoPath").each(function () {
            $(".div-video").append("<img src='" + basePath + "/images/ea/production/drive/k2_top03.png'/>");
        });
        $(".shipin1").hide();
        $(".shipin2").show();
    });

    // 	关闭
    $(document).on("click", ".div-shipin2 .div-close", function () {
        $(".div-shipin2").css({"opacity": "0", "transform": 'translate(1000000px)'});
    });
    /*************视频上传结束*************/

    $(".div-bottom").click(function () {
        saveInspect();
    });
});

/**
 * 安全类别
 */
function getList() {
    var url = "/ea/bindnfc/sajax_ea_getOASafeKindList.jspa?date="
        + new Date().toLocaleString();
    pageNumber = pageNumber + 1;
    $.ajax({
        url: basePath + url,
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            parameter: $("#search").val(),
            companyID: $("#companyID").val()
        },
        success: function (data) {
            if (data != null) {
                var m = eval("(" + data + ")");
                var arry = m.pageForm;
                var htmlstr = [];
                if (arry == null) {
                    $(".titlep").text($("#companyName").val() + "没有初始化！");
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
        },
        error: function (data) {
            console.log(data);
        }

    });
}

/**
 * 保存
 */
function saveInspect() {
    $(".loading").show();
    $("#form").attr("target", "hidden").attr("action",
        basePath + "ea/SafetyInspect/ea_saveSafetyInspect.jspa");
    document.form.submit.click();
    token = 13;
    localforage.removeItem('dataKey');
}