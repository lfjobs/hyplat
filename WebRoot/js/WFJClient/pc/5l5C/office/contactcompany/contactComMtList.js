var pageNumber = 0;
var pageSize = 25;
var clientWidth = document.documentElement.clientWidth;
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
    getList();

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

    $(".add-p").click(function () {
        window.location.href=basePath+"page/WFJClient/pc/5l5C/office/contactcompany/contactComMtAdd.jsp?companyID="+companyID;
    });
}

function getList() {
    $(".div-chaxun").hide();

    var url = "/ea/contactcompany/sajax_ea_getListContactCompany_Mt.jspa";
    pageNumber = pageNumber + 1;
    $.ajax({
            url: basePath + url,
            type: "get",
            dataType: "json",
            aysnc: false,
            data: {
                "pageForm.pageNumber": pageNumber,
                "pageSize": pageSize,
                "parameter": $("#search").val()
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
                            htmlstr.push("<div class='sex'>");
                            htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                            htmlstr.push("</div>");


                            htmlstr.push("<div class='ln-p' title='" + i + "' style='width:7%!important;'>" + i + "</div>");
                            htmlstr.push("<div class='sn-p' title='" + arry.list[i].companyName + "' style='width:15%!important;'>" + arry.list[i].companyName + "</div>");
                            htmlstr.push("<div class='model-p' title='" + arry.list[i].cresponsible + "' style='width:10%!important;'>" + arry.list[i].cresponsible + "</div>");
                            htmlstr.push("<div class='oask-p' title='" + arry.list[i].responsibleTel + "' style='width:8%!important;'>" + arry.list[i].responsibleTel + "</div>");
                            htmlstr.push("<div class='staff-p' title='" + arry.list[i].companyTel + "' style='width:8%!important;'>" + arry.list[i].companyTel + "</div>");
                            htmlstr.push("<div class='location-p' title='" + arry.list[i].companyAddr + "' style='width:15%!important;'>" + arry.list[i].companyAddr + "</div>");
                            htmlstr.push("<div class='en-p' title='" + arry.list[i].industryType + "' style='width:10%!important;'>" + arry.list[i].industryType + "</div>");
                            htmlstr.push("<div class='date-p' title='" + arry.list[i].remark + "' style='width:10%!important;'>" + arry.list[i].remark + "</div>");
                        }
                    } else {
                        for (var i = 0; i < arry.list.length; i++) {
                            if (i == arry.list.length - 1) {
                                htmlstr.push("<li class='clearfix last1 li-data' id='" + arry.list[i][0] + "'>");
                            } else {
                                htmlstr.push("<li class='clearfix li-data' id='" + arry.list[i][0] + "'>");
                            }

                            htmlstr.push("<div class='title-div'>");
                            htmlstr.push("<div class='sex'>");
                            htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                            htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                            htmlstr.push("</div>");
                            htmlstr.push("<div class='div-companyName'>" + arry.list[i].companyName + "</div>");
                            htmlstr.push("<div class='div-industryType'>" + arry.list[i].industryType + "</div>");
                            htmlstr.push("</div>");
                            htmlstr.push("<div class='draftor-div'>");
                            htmlstr.push("<div>" + arry.list[i].companyAddr + "</div>");
                            htmlstr.push("</div>");
                            htmlstr.push("</li>");
                        }
                    }
                    htmlstr.push("</div></li>");
                }
                $(".ul").append(htmlstr.join(""));
            },
            error: function (data) {
                console.log(data);
            }
        }
    );
}