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
        $(".sn-p").attr("style", "width:10%!important;");
        $(".oask-p").attr("style", "width:30%!important;");
        $(".state-p").attr("style", "width:30%!important;");
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
        var auditionKey =$(".ul li.active").attr("id");
        window.parent.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/mianshiNoticeInfo.jsp?companyID="+companyID+"&auditionKey="+auditionKey
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

        var place = $("#place").val();
        var auditionDate = $("#auditionDate").val();
        var examiner = $("#examiner").val();
        var auditionDept = $("#auditionDept").val();
        $.ajax({
            type: "POST",
            url: basePath + "ea/cosincumbent/sajax_n_ea_saveAllAudition.jspa?",
            async: true,
            dataType: "json",
            data: {
                "staffID": ln,
                "place": place,
                "auditionDate": auditionDate,
                "examiner": examiner,
                "auditionDept": auditionDept,
                "auditionKey": ln,
                "status": 10
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
        window.parent.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/personal.jsp?companyID="+companyID+"&staffID="+staffID
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
    var url = "ea/cosincumbent/sajax_n_ea_getauditionList.jspa?";
    pageNumber = pageNumber + 1;
    $.ajax({
        url: basePath + url,
        type: "POST",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            parameter: $("#search").val(),
            "companyID": companyID,
            "afterStaffID":0,
            "status": "0",
            "auditionKey": 0
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
                        htmlstr.push("<li class= 'clearfix last1' id='" + arry.list[i].auditionKey + "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i].auditionKey + "'>");
                    }
                    htmlstr.push("<div class='title-pc'><div class='sex'>");
                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                    htmlstr.push("</div>");
                    var bindState
                    if (arry.list[i].bindState == "00") {
                        bindState = "已绑定";
                    } else {
                        bindState = "已解绑";
                    }
                    htmlstr.push("<div class='ln-p' title='" + arry.list[i].staffID + "' style='width:10%!important;'>" + arry.list[i].staffID + "</div>");
                    htmlstr.push("<div class='sn-p' title='" + arry.list[i].staffName + "' style='width:20%!important;'>" + arry.list[i].staffName + "</div>");
                    htmlstr.push("<div class='oask-p' title='" + arry.list[i].reference + "' style='width:25%!important;'>" + arry.list[i].reference
                        + "</div>");
                    htmlstr.push("<div class='state-p' title='" + arry.list[i].staffIdentityCard + "' style='width:30%!important;'>" + arry.list[i].staffIdentityCard + "</div>");

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
