var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {
    getRecentInfo();
    $("#search").focus();
    $(".sec-bottom").click(function () {
        var dyq = "";
        if ($(".sec-bottom").attr("class").indexOf("yq-p") != -1) {
            if (typee != "") {

                yqp();
                return false;
            } else {
                dyq = $("#search").val();
            }
        } else {
            var li = $(".ul-list li.active").length;
            if (li < 1) {
                return false;
            }
        }

    })
    if (companyID != null && companyID != "") {
        getMember(companyID, orgID);
    }

    $("#qsearch").click(function () {
        var parameter = $("#search").val();
        if ($.trim(parameter) == "") {

            return false;
        }
        if (parameter.length < 2) {
            return false;
        }
        $(".navrecent").hide();
        $(".resulttip").hide();
        $(".ul-list").html("");
        getMember(companyID, orgID);

    })

    $("#search").on('input', function () {
        var parameter = $("#search").val();

        if (parameter == "") {
            $(".resulttip").hide();
            $(".sec-bottom p").text("确定提交");
            $(".sec-bottom").removeClass("yq-p");
            getRecentInfo();
        }
    });

    //选中
    $(document).on("click", ".ul-list li", function () {

        if ($(this).is(".active")) {
            $(this).removeClass("active");
            if (!$(".resulttip").is(":hidden")) {
                var parameter = $("#search").val();
                if (parameter != "") {
                    var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
                    //如果为1开头则验证手机号码
                    if (isMobile.exec(parameter) && parameter.length == 11) {
                        $(".sec-bottom p").text("邀请注册并提交");
                        $(".sec-bottom").addClass("yq-p");
                    }
                }
            } else {
                //

            }

        } else {
            var staffid = $(this).attr("id");


            if ($(".sec-bottom p").text() == "邀请注册并提交") {
                $(".sec-bottom p").text("确定提交");
                $(".sec-bottom").removeClass("yq-p");
            }
            if (typee != "") {
                $(".ul-list .active").removeClass("active");
                if (staffid == "") {
                    $(".sec-bottom p").text("邀请注册并提交");
                    $(".sec-bottom").addClass("yq-p");
                } else {
                    $(".sec-bottom p").text("确定提交");
                    $(".sec-bottom").removeClass("yq-p");
                }


            }
            $(this).addClass("active");

        }
    })


    $(".close-tingyong").click(function () {

        if (isAndroid == true || isiOS == true) {
            window.history.back()
        } else {
            if (window != window.top) {
                window.history.back()
            } else {
                window.opener.location.reload();
                window.close();
            }

        }
    })

})


function getMember(companyID, orgID) {
    var parameter = $("#search").val();

    $.ajax({
        url: basePath + "ea/android/video_getPersonList.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {

            parameter: parameter

        },
        success: function (data) {
            if (data != null) {
                var htmlstr = new Array();
                var arry = data.result;
                if (arry == null || arry.length == 0) {
                    $(".resulttip").show();
                    var isMobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
                    //如果为1开头则验证手机号码
                    if (isMobile.exec(parameter) && parameter.length == 11) {
                        //是手机号
                        $(".sec-bottom p").text("邀请注册并提交");
                        $(".sec-bottom").addClass("yq-p");
                    } else {
                        $(".resulttip").html("手机号不正确");
                    }
                    //  getRecentInfo();
                    return false;
                }

                for (var i = 0; i < arry.length; i++) {
                    htmlstr.push("<li class='clearfix' id='" + arry[i].staffID + "'  staffname='" + arry[i].staffName + "' phone='" + arry[i].tel + "' companyId = '" + arry[i].companyid + "'>");

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='" + basePath + arry[i].headimage + "' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");

                    htmlstr.push("用户-");


                    if (arry[i].tel == null || arry[i].tel == "") {
                        htmlstr.push(arry[i].staffName);
                    } else {
                        htmlstr.push(arry[i].staffName + "(" + arry[i].tel + ")");
                    }
                    htmlstr.push("</p>")


                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
                $(".sec-bottom p").text("确定提交");
            }
            console.log(data);
        },
        error: function (data) {

        }

    });
}


function yqp() {
    if (typee == "") {
        return false;
    }
    var parameter = $("#search").val();
    if (parameter == "") {
        var li = $(".ul-list li.active");
        parameter = $(li).attr("telphone");
    }

    $.ajax({
        url: basePath + "ea/androiddoc/sajax_ea_addInventRecord.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            telphone: parameter,
            status: typee,
            docId: docId
        },
        success: function (data) {
            $(".div-tingyong").show();
        },
        error: function (data) {

        }
    });
}

function getRecentInfo() {
    $.ajax({
        url: basePath + "ea/androiddoc/sajax_ea_getRecentInfo.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            source: "03"
        },
        success: function (data) {
            if (data != null) {
                var htmlstr = new Array();
                var m = eval("(" + data + ")");
                var arry = m.list;
                if (arry == null) {
                    return false;
                    $(".navrecent").hide();
                }
                $(".navrecent").show();
                for (var i = 0; i < arry.length; i++) {
                    htmlstr.push("<li class='clearfix' id='" + arry[i][0] + "'  staffname='" + arry[i][3] + "'  phone='" + arry[i][7] + "'  companyId = '" + arry[i][2] + "'>");
                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='" + basePath + arry[i][6] + "' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");
                    if ((arry[i][5] == null || arry[i][5] == "") && (orgID == null || orgID == "")) {
                        if (arry[i][3] == "被邀请") {
                            if (arry[i][8] == "1") {
                                htmlstr.push("未注册-");
                            } else {
                                htmlstr.push("已注册-");
                            }
                        } else {
                            htmlstr.push("用户-");
                        }
                    } else {
                        htmlstr.push("员工-");
                    }
                    if (arry[i][7] == null || arry[i][7] == "") {
                        htmlstr.push(arry[i][3]);
                    } else {
                        htmlstr.push(arry[i][3] + "(" + arry[i][7] + ")");
                    }
                    htmlstr.push("</p>")

                    if (orgID == null || orgID == "") {
                        if (arry[i][4] != null && arry[i][4] != "") {
                            htmlstr.push("<p orgid-data='" + arry[i][1] + "' class='orgID'>");
                            htmlstr.push(arry[i][4]);
                            htmlstr.push("</p>")
                        }
                        if (arry[i][5] != null && arry[i][5] != "") {
                            htmlstr.push("<p comid-data='" + arry[i][2] + "' class='comID'>");
                            htmlstr.push(arry[i][5]);
                            htmlstr.push("</p>")
                        }
                    }
                    htmlstr.push("</li>")
                }
                $(".ul-list").html(htmlstr.join(""));
            }
        },
        error: function (data) {
        }
    });
}

$(document).on("click", "#submitBtn", function () {
    var selectedData = [];
    $('.ul-list li').each(function () {
        // 判断 li 是否包含 active 类
        if ($(this).hasClass('active')) {
            var staffID = $(this).attr('id');
            var staffName = $(this).attr('staffname');
            selectedData.push({
                staffId: staffID,
                name: staffName,
                phone: $(this).attr('phone'),
                companyId: $(this).attr('companyId')
            });
        }
    });
    if (selectedData.length === 0) {
        layer.msg("请选择分配的员工");
        return;
    }
    if (getParameterByName("type") == "sfpt") {
        $.ajax({
            url: basePath + "ea/trilateral/sajax_ea_allocationImportContacts.jspa?", // 替换为实际的后端URL
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "sfpt": localStorage.getItem("sfptId"),
                "selectedData": JSON.stringify(selectedData),
            },
            success: function cbf(data) {
                localStorage.removeItem("fp");
                selectedData = [];
                layer.msg("分配成功");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/trilateralDataNew.jsp?type=all";
                }, 3000); // 3000 毫秒 = 3 秒

            },
            error: function cbf(data) {
                localStorage.removeItem("sfpt");
                selectedData = [];
                layer.msg("分配失败,请重新分配");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/trilateralDataNew.jsp?type=all";
                }, 3000); // 3000 毫秒 = 3 秒

            }
        })
    } else {
        $.ajax({
            url: basePath + "ea/importContacts/sajax_ea_allocationImportContacts.jspa?", // 替换为实际的后端URL
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "fp": localStorage.getItem("fp"),
                "selectedData": JSON.stringify(selectedData),
            },
            success: function cbf(data) {
                localStorage.removeItem("fp");
                selectedData = [];
                layer.msg("分配成功");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
                }, 3000); // 3000 毫秒 = 3 秒

            },
            error: function cbf(data) {
                localStorage.removeItem("fp");
                selectedData = [];
                layer.msg("分配失败,请重新分配");
                setTimeout(function () {
                    window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
                }, 3000); // 3000 毫秒 = 3 秒

            }
        })
    }
})


