var pageNumber = 0;
var pageSize = 25;
var clientWidth = document.documentElement.clientWidth;
var date;//当前时间
var year;//年
var month;//月
var day;//日
var oneDayTime = 24 * 60 * 1000;
window.onload = function () {

    if (clientWidth >= 960) {
        $("#phone-sec").remove();
        $(".ln-p").attr("style", "width:7%!important;");
        $(".sn-p").attr("style", "width:15%!important;");
        $(".oask-p").attr("style", "width:6%!important;");
        $(".staff-p").attr("style", "width:8%!important;");
        $(".siType-p").attr("style", "width:5%!important;");
        $(".en-p").attr("style", "width:8%!important;");
        $(".date-p").attr("style", "width:8%!important;");
        $(".state-p").attr("style", "width:5%!important;");
        $(".img-p").attr("style", "width:5%!important;");
        $(".vodel-p").attr("style", "width:5%!important;");
        $(".pc").show();
    } else {
        $("#pc-sec").remove();
    }

    //滚动加载
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                getList();
            }
        }
    });

    //选中
    $(document).on("click", ".sec-list li", function (event) {
        event.stopPropagation();
        var siID = $(this).attr("id");
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".sec-list .active").removeClass("active");
            $(this).addClass("active");
        }
        window.parent.location.href = basePath + "/ea/SafetyInspect/ea_getInspect.jspa?siid=" + siID;
    });

    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    });

    // 查询框显示
    $(".edit").click(function () {
        $(".div-chaxun").show();
    });

    // 查询框隐藏
    $(".div-chaxun").click(function () {
        $(".div-chaxun").hide();
    });

    $(".div-chaxun .div-box").click(function (e) {
        e.stopPropagation();
    });

    $(".p-bottom").click(function () {
        var ln = $("#ln").val();
        var staffName = $("#staffName").val();
        var oaskName = $("#oaskName").val();
        var siType = $("input[name=siType]:checked").val();
        if ((siType == null || siType == "") && (ln == null || ln == "") && (staffName == null || staffName == "") && (oaskName == null || oaskName == "")) {
            $(".tittle-p").text("请填写查询条件");
            $(".div-tingyong").show();
            $(".div-chaxun").hide();
        } else {
            $(".li-data").remove();
            getList();
        }
    });

    $(".sec-nav li p").click(function () {
        event.stopPropagation();
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".sec-nav .active").removeClass("active");
            $(this).addClass("active");
        }
    });

    $(".p-today").click(function () {
        $("#ln").val("");
        $("#staffName").val("");
        $("#oaskName").val("");
        $("#radio-0").prop("checked", true);
        date = new Date();
        getdate(date);
        $("#sDate").val(year + "-" + month + "-" + day + " 00:00:00");
        $("#eDate").val(year + "-" + month + "-" + day + " 23:59:59");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-yesterday").click(function () {
        date = new Date();
        date.setDate(date.getDate() - 1);
        getdate(date);
        $("#sDate").val(year + "-" + month + "-" + day + " 00:00:00");
        $("#eDate").val(year + "-" + month + "-" + day + " 23:59:59");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-week").click(function () {
        date = new Date();
        getdate(date);
        $("#eDate").val(year + "-" + month + "-" + day + " 23:59:59");
        date.setDate(date.getDate() - 7);
        getdate(date);
        $("#sDate").val(year + "-" + month + "-" + day + " 00:00:00");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-month").click(function () {
        date = new Date();
        getdate(date);
        $("#eDate").val(year + "-" + month + "-" + day + " 23:59:59");
        date.setDate(date.getDate() - 30);
        getdate(date);
        $("#sDate").val(year + "-" + month + "-" + day + " 23:59:59");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-year").click(function () {
        date = new Date();
        getdate(date);
        $("#eDate").val(year + "-" + month + "-" + day + " 23:59:59");
        $("#sDate").val(year + "-01-01 00:00:00");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-lastYear").click(function () {
        date = new Date();
        getdate(date);
        year = year - 1;
        $("#sDate").val(year + "-01-01 00:00:00");
        $("#eDate").val(year + "-12-31 23:59:59");
        console.log($("#sDate").val());
        console.log($("#eDate").val());
        $(".li-data").remove();
        getList();
    });

    $(".p-yesterday").trigger("click");
}

function getdate(date) {
    year = date.getFullYear();//年
    month = date.getMonth() + 1;//月
    day = date.getDate();//日
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
}

window.onresize = function () {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid == false && isiOS == false) {
        if (clientWidth >= 960) {
            $("#phone-sec").remove();
        } else {
            $("#pc-sec").remove();
        }
    }
    getList();
}

function getList() {
    $(".div-chaxun").hide();
    var ln = $("#ln").val();
    var staffName = $("#staffName").val();
    var oaskName = $("#oaskName").val();
    var siType = $("input[name=siType]:checked").val();

    var url = "/ea/SafetyInspect/sajax_ea_getInspectList.jspa";
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
            "companyID": companyID,
            "ln": ln,
            "staffName": staffName,
            "oaskName": oaskName,
            "siType": siType,
            "sdate": $("#sDate").val(),
            "edate": $("#eDate").val()
        },
        success: function (data) {
            if (data != null) {
                var m = eval("(" + data + ")");
                var arry = m.pageForm;
                var htmlstr = [];
                if (arry == null) {
                    $(".tittle-p").text("没有数据了");
                    $(".div-tingyong").show();
                    return false;
                }
                pageNumber = arry.pageNumber;
                pageCount = arry.pageCount;
                $(".last1").remove();
                if (clientWidth >= 960) {
                    for (var i = 0; i < arry.list.length; i++) {
                        if (i == arry.list.length - 1) {
                            htmlstr.push("<li class= 'clearfix last1 li-data' id='" + arry.list[i][0] + "'>");
                        } else {
                            htmlstr.push("<li class='clearfix li-data' id='" + arry.list[i][0] + "'>");
                        }
                        htmlstr.push("<div class='title-pc'>");
                        /*htmlstr.push("<div class='sex'>");
                        htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                        htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                        htmlstr.push("</div>");*/


                        htmlstr.push("<div class='ln-p' title='" + arry.list[i][3] + "' style='width:7%!important;'>" + arry.list[i][3] + "</div>");
                        htmlstr.push("<div class='sn-p' title='" + arry.list[i][2] + "' style='width:15%!important;'>" + arry.list[i][2] + "</div>");
                        /*htmlstr.push("<div class='oaskName-p' title='" + arry.list[i][5] + "' style='width:6%!important;'>" + arry.list[i][5] + "</div>");*/
                        htmlstr.push("<div class='siType-p' title='" + arry.list[i][9] + "' style='width:5%!important;color: green;font-weight: 1000;'>绿");
                        if (arry.list[i][9] == "02") {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='siType-p' title='" + arry.list[i][9] + "' style='width:5%!important;color: yellow;font-weight: 1000;'>黄");
                        if (arry.list[i][9] == "01") {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='siType-p' title='" + arry.list[i][9] + "' style='width:5%!important;color: red;font-weight: 1000;'>红");
                        if (arry.list[i][9] == "00") {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02' style='width:1rem;' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='staffName-p' title='" + arry.list[i][13] + "' style='width:8%!important;'>" + arry.list[i][13] + "</div>");
                        htmlstr.push("<div class='img-p' title='img' style='width:5%!important;'>");
                        htmlstr.push("<img class='img-03' src='" + basePath + "/images/ea/production/drive/up_img.png'/>");
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='vodel-p' title='vodel' style='width:5%!important;'>");
                        htmlstr.push("<img class='img-04' src='" + basePath + "/images/ea/production/drive/k2_top03.png'/>");
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='state-p' title='siType' style='width:5%!important;'>待处理</div>");
                        htmlstr.push("</div></li>");
                    }
                } else {
                    for (var i = 0; i < arry.list.length; i++) {
                        if (i == arry.list.length - 1) {
                            htmlstr.push("<li class='clearfix last1 li-data' id='" + arry.list[i][0] + "'>");
                        } else {
                            htmlstr.push("<li class='clearfix li-data' id='" + arry.list[i][0] + "'>");
                        }

                        htmlstr.push("<div class='title-div'>");
                        htmlstr.push("<div class='div-ln'>" + arry.list[i][3] + "</div>");
                        htmlstr.push("<div class='div-sn'>" + arry.list[i][2] + "</div>");
                        /*htmlstr.push("<div class='div-type'>" + arry.list[i][5] + "</div>");*/
                        htmlstr.push("<div class='div-siType'>");
                        htmlstr.push("<div class='div-siType-green' title='" + arry.list[i][9] + "'>绿");
                        if (arry.list[i][9] == "02") {
                            htmlstr.push("<img class='img-02'  src='" + basePath + "images/ea/production/drive/gou2.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02'  src='" + basePath + "images/ea/production/drive/share_colse.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='div-siType-yellow' title='" + arry.list[i][9] + "'>黄");
                        if (arry.list[i][9] == "01") {
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/production/drive/gou2.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/production/drive/share_colse.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='div-siType-red' title='" + arry.list[i][9] + "'>红");
                        if (arry.list[i][9] == "00") {
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/production/drive/gou2.png'/>");
                        } else {
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/production/drive/share_colse.png'/>");
                        }
                        htmlstr.push("</div>");
                        htmlstr.push("</div>");
                        htmlstr.push("</div>");
                        htmlstr.push("<div class='draftor-div'>");
                        htmlstr.push("<div class='div-img'>");
                        htmlstr.push("<img class='img-03' src='" + basePath + "/images/ea/production/drive/up_img.png'/>");
                        htmlstr.push("<img class='img-04' src='" + basePath + "/images/ea/production/drive/k2_top03.png'/>");
                        htmlstr.push("</div>");
                        htmlstr.push("<div>" + arry.list[i][7] + "-" + arry.list[i][11] + "</div>");
                        htmlstr.push("<div>" + arry.list[i][13] + "</div>");
                        htmlstr.push("</div>");
                        htmlstr.push("</li>");
                    }
                }
                $(".ul").append(htmlstr.join(""));
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
}
