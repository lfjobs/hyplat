var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(document).ready(function () {


    getRecentInfo();

    //分配人公司输入
    // document.getElementById("sjrgs").addEventListener("compositionend",function(e){
    $('#sjrgs').bind('input propertychange', function () {

        $("#staffid").val("");
        $("#orgid").val("");
        $("#comid").val("");
        $("#sjr").val("");
        $("#staffname").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);
        if ($.trim($p.val()) == "" || $.trim($p.val()).length < 4) {
            return false;

        }
        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "document.companyName": $p.val()
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var companylist = member.companylist;
                var str = "";
                for (var i = 0; i < companylist.length; i++) {
                    var obj = companylist[i];
                    str += "<li class='company' id='" + obj.companyID + "'><p>" + obj.companyName + "</p></li>";
                }
                $(".ul-list").html(str);
                if (str != "") {
                    $(".sec-ul").css({
                        position: "absolute",
                        left: left - 12,
                        top: top + 45
                    }).show();
                }

            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });


    //分配人输入
    //  document.getElementById("sjr").addEventListener("compositionend",function(e){
    $('#sjr').bind('input propertychange', function () {
        $("#staffid").val("");
        $("#staffname").val("");
        if ($("#comid").val() == "") {
            $(".titlep").text("请先填写分配人公司");
            $(".div-tingyong").show();
            $("#sjr").val("");
            return false;
        }

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);
        if ($.trim($p.val()) == "" || $.trim($p.val()).length < 2) {
            return false;

        }

        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllPeople.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "document.drafterName": $p.val(),
                "document.companyID": $("#comid").val()
            },
            success: function cbf(data) {

                var member = eval("(" + data + ")");
                var plist = member.plist;


                var htmlstr = new Array();

                for (var i = 0; i < plist.length; i++) {
                    var obj = plist[i];
                    htmlstr.push("<li class='clearfix staff' id='" + obj[1] + "'  staffname='" + obj[0] + "'  phone='" + obj[2] + "'>");

                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='" + basePath + obj[8] + "' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p orgid-data='" + obj[3] + "'>");

                    var orgname = obj[4];
                    if (orgname.length > 7) {
                        orgname = orgname.substring(0, 7);
                    }
                    htmlstr.push(orgname + "-");

                    if (obj[2] == null || obj[2] == "") {
                        htmlstr.push(obj[0]);
                    } else {
                        htmlstr.push(obj[0] + "(" + obj[2] + ")");
                    }
                    htmlstr.push("</p>")


                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
                if (plist.length != 0) {
                    $(".sec-ul").css({
                        position: "absolute",
                        left: left - 12,
                        top: top + 45
                    }).show();
                }
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });
    });


    //选中
    $(document).on("click", ".ul-list2 li", function () {

        if ($(this).is(".active")) {
            $(this).removeClass("active");

        } else {
            if (typee != "") {
                $(".ul-list2 .active").removeClass("active");
            }

            $(this).addClass("active");

        }
    })


    //选中公司
    $(document).on("click", ".ul-list li.company", function () {

        $("#comid").val($(this).attr("id"));

        $("#sjrgs").val($(this).find("p").text());
        $(".sec-ul").hide();

    })


    //选中人员
    $(document).on("click", ".ul-list li.staff", function () {

        $("#staffid").val($(this).attr("id"));

        $("#sjr").val($(this).text());
        $("#staffname").val($(this).attr("staffname"));
        $("#orgid").val($(this).find("p").attr("orgid-data"));
        $(".sec-ul").hide();
    })

    //提示确定
    $(".close-tingyong").click(function () {
        if ($(".titlep").text() == "操作成功") {
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

        } else {
            $(".div-tingyong").hide();
        }
    })

})


function getRecentInfo() {


    $.ajax({
        url: basePath + "ea/androiddoc/sajax_ea_getRecentInfo.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            source: "02"
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

                $(".ul-list2").html(htmlstr.join(""));
            }


        },
        error: function (data) {

        }

    });
}

$(document).on("click", "#submitBtn", function () {
    var selectedData = [];
    if ($("#sjr").val() && $("#sjrgs").val()) {
        var companyId = $("#comid").val();
        var staffID = $("#staffid").val();
        var staffName = $("#staffname").val();
        var phone = $("li.staff").attr("phone");
        selectedData.push({
            staffId: staffID,
            name: staffName,
            phone: phone,
            companyId: companyId
        });
    } else {
        $('.ul-list2 li').each(function () {
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
    }
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

