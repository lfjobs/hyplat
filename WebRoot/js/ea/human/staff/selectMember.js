var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


$(document).ready(function () {
    $("#search").focus();
    if (companyID != null && companyID != "") {
        getMember(companyID, orgID);
    } else {
        getRecentInfo();
    }
    $("#qsearch").click(function () {
        var parameter = $("#search").val();
        if ($.trim(parameter) == "") {

            return false;
        }
        if (parameter.length < 2) {
            return false;
        }

        $(".ul-list").html("");
        $(".navrecent").hide();
        getMember(companyID, orgID);

    })

    //选中
    $(document).on("click", ".ul-list li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
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
            companyID: companyID,
            orgID: orgID,
            sccid: sccid,
            parameter: parameter
        },
        success: function (data) {
            if (data != null) {
                var htmlstr = new Array();
                var arry = data.result;
                if (arry == null || arry.length == 0) {
                    $(".resulttip").show();
                    getRecentInfo();
                    return false;
                }
                $(".resulttip").hide();
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
                    if ((arry[i].companyname == null || arry[i].companyname == "") && (orgID == null || orgID == "")) {
                        htmlstr.push("用户-");
                    } else {
                        htmlstr.push("员工-");
                    }
                    if (arry[i].tel == null || arry[i].tel == "") {
                        htmlstr.push(arry[i].staffName);
                    } else {
                        htmlstr.push(arry[i].staffName + "(" + arry[i].tel + ")");
                    }
                    htmlstr.push("</p>")

                    if (orgID == null || orgID == "") {
                        if (arry[i].organizationname != null && arry[i].organizationname != "") {
                            htmlstr.push("<p orgid-data='" + arry[i].organizationid + "' class='orgID'>");
                            htmlstr.push(arry[i].organizationname);
                            htmlstr.push("</p>")
                        }
                        if (arry[i].companyname != null && arry[i].companyname != "") {
                            htmlstr.push("<p comid-data='" + arry[i].companyid + "' class='comID'>");
                            htmlstr.push(arry[i].companyname);
                            htmlstr.push("</p>")
                        }


                    }


                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
            }
            console.log(data);
            console.log("--");
            console.log(parameter);


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
            source: "01"
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
                        htmlstr.push("用户-");
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

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
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

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }
})