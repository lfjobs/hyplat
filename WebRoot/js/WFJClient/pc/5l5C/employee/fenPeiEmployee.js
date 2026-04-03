var pageNumber = 0;
var pageSize = 25;
var clientWidth = document.documentElement.clientWidth;
window.onload = function () {
    getList();
    var clientWidth = document.documentElement.clientWidth;
    if (clientWidth >= 960) {
        $(".ln-p").attr("style", "width:7%!important;");
        $(".sn-p").attr("style", "width:15%!important;");
        $(".model-p").attr("style", "width:10%!important;");
        $(".oask-p").attr("style", "width:8%!important;");
        $(".staff-p").attr("style", "width:8%!important;");
        $(".location-p").attr("style", "width:15%!important;");
        $(".en-p").attr("style", "width:10%!important;");
        $(".date-p").attr("style", "width:10%!important;");
        $(".state-p").attr("style", "width:5%!important;");
        $(".pc").show();
    } else {
        $(".ln-p").attr("style", "width:15%!important;");
        $(".sn-p").attr("style", "width:20%!important;");
        $(".oask-p").attr("style", "width:20%!important;");
        $(".state-p").attr("style", "width:20%!important;");
        $(".pc").hide();
    }

    //分页滚动
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
    $(document).on("click", ".ul li", function (event) {
        event.stopPropagation();
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
    });
    // 提示框显示
    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    });
    // 查询框显示
    $(".query").click(function () {
        var staffID =$(".ul li.active").attr("id");
        window.parent.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/rzEmployeesInfo.jsp?companyID="+companyID+"&staffID="+staffID
    });

    // 查询框隐藏
    $(".div-chaxun").click(function () {
        $(".div-chaxun").hide();
    });

    $(".div-chaxun .div-box").click(function (e) {
        e.stopPropagation();
    });


    $(".m-bottom").click(function () {
        var ln = $(".ul li.active").attr("id");

        var registerDate = $("#registerDate").val();
        var becomesDate = $("#becomesDate").val();
        var auditionPlace = $("#auditionPlace").val();
        var remark = $("#remark").val();
        $.ajax({
            type: "POST",
            url: basePath + "ea/cosincumbent/sajax_n_ea_saveAllAudition.jspa?",
            async: true,
            dataType: "json",
            data: {
                "auditionKey": ln,
                "registerDate": registerDate,
                "becomesDate": becomesDate,
                "auditionPlace": auditionPlace,
                "remark": remark,
                "status": 21,
                "type":1
            },
            success: function (data) {
                location.reload();
            },
            error: function (data) {
                alert("操作失误");
            }
        });
    });



    //解绑
    $(".draft").click(function () {
        $(".div-chaxun").show();
    });
    $(".mianshi").click(function () {
        $(".div-mianshi").show();
    });


    $(".view").click(function () {
        var depPostID =$(".ul li.active").attr("id");
        window.parent.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/fenPeiEmployeeEnd.jsp?depPostID="+depPostID
    });

    $(".print-p").click(function () {
        CreateAllPages();
    });
}

window.onresize = function () {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid == false && isiOS == false) {
        var clientWidth = document.documentElement.clientWidth;
        if (clientWidth >= 960) {
            $("#phone-sec").remove();
        } else {
            $("#pc-sec").remove();
        }
        // document.location.reload();
    }
}

function getList() {
    $(".div-chaxun").hide();
    $(".div-mianshi").hide();
    var url = "ea/cosincumbent/sajax_n_ea_getDeployList.jspa?";
    pageNumber = pageNumber + 1;
    $.ajax({
        url: basePath + url,
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            "star": '00',
            "companyID": companyID
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
                for (var i = 0; i < arry.list.length; i++) {

                    if (i == arry.list.length - 1) {
                        htmlstr.push("<li class= 'clearfix last1' id='" + arry.list[i][0]+ "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i][0]+ "'>");
                    }
                    htmlstr.push("<div class='title-pc'><div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                    htmlstr.push("</div>");

                    htmlstr.push("<div class='sn-p' title='" + arry.list[i][6] + "' style='width:20%!important;'>" + arry.list[i][6] + "</div>");
                    htmlstr.push("<div class='oask-p' title='" + arry.list[i][4] + "' style='width:20%!important;'>" + arry.list[i][4] + "</div>");
                    htmlstr.push("<div class='ln-p' title='" + arry.list[i][1] + "' style='width:15%!important;'>" + arry.list[i][1] + "</div>");
                    htmlstr.push("<div class='ln-p' title='" + arry.list[i][2] + "' style='width:15%!important;'>" + arry.list[i][2] + "</div>");

                    htmlstr.push("</div></li>");
                }
                $(".ul").append(htmlstr.join(""));
            }
            console.log(data);
        },
        error: function (data) {
            console.log(data);
        }
    });
}