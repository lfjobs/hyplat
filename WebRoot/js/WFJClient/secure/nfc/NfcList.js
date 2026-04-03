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
        $(".sn-p").attr("style", "width:30%!important;");
        $(".state-p").attr("style", "width:20%!important;");
        $(".location-p").attr("style", "width:20%!important;");
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
        $(".div-chaxun").show();
    });

    // 查询框隐藏
    $(".div-chaxun").click(function () {
        $(".div-chaxun").hide();
    });

    $(".div-chaxun .div-box").click(function (e) {
        e.stopPropagation();
    });

    //查询
    $(".p-bottom").click(function () {
        var ln = $("#ln").val();
        var staffName = $("#staffName").val();
        var oaskName = $("#oaskName").val();
        if ((ln == null || ln == "") && (staffName == null || staffName == "") && (oaskName == null || oaskName == "")) {
            $(".tittle-p").text("请填写查询条件");
            $(".div-tingyong").show();
            $(".div-chaxun").hide();
        } else {
            getList();
        }
    });


    //解绑
    $(".draft").click(function () {
        var f = confirm("确定要解绑吗？");
        if (f) {
            var li = $(".ul li.active").length;
            if (li < 1) {
                return false;
            }
            var id = $(".ul li.active").attr("id");
            $.ajax({
                type: "GET",
                url: basePath + "/ea/bindnfc/sajax_ea_UnbindingNFC.jspa",
                async: true,
                dataType: "json",
                data: {
                    "id": id
                },
                success: function (data) {
                    var member = eval('(' + data + ')');
                    var flag = member.flag;
                    if (flag) {
                        $("#" + id).hide();
                    }
                },
                error: function (data) {
                    alert("操作失误");
                }
            });
        }
    });

    $(".view").click(function () {
        var id = $(".active").attr("id");
        if (id.length > 0) {
            window.parent.location.href = basePath + "/ea/bindnfc/ea_getNfcByid.jspa?nfcid=" + id;
        } else {

        }
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
    var ln = $("#ln").val();
    var staffName = $("#staffName").val();
    var oaskName = $("#oaskName").val();
    var url = "/ea/bindnfc/sajax_ea_getNfcList.jspa";
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
            "oaskName": oaskName
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
                        htmlstr.push("<li class= 'clearfix last1' id='" + arry.list[i].nfcID + "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i].nfcID + "'>");
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
                    if (clientWidth >= 960) {
                        htmlstr.push("<div class='ln-p' title='" + arry.list[i].ln + "' style='width:7%!important;'>" + arry.list[i].ln + "</div>");
                        htmlstr.push("<div class='sn-p' title='" + arry.list[i].sn + "' style='width:15%!important;'>" + arry.list[i].sn + "</div>");
                        htmlstr.push("<div class='model-p' title='" + arry.list[i].model + "' style='width:10%!important;'>" + arry.list[i].model + "</div>");
                        htmlstr.push("<div class='oask-p' title='" + arry.list[i].oaskName + "' style='width:8%!important;'>" + arry.list[i].oaskName + "</div>");
                        htmlstr.push("<div class='staff-p' title='" + arry.list[i].staffName + "' style='width:8%!important;'>" + arry.list[i].staffName + "</div>");
                        htmlstr.push("<div class='location-p' title='" + arry.list[i].bindLocation + "' style='width:15%!important;'>" + arry.list[i].bindLocation + "</div>");
                        htmlstr.push("<div class='en-p' title='" + arry.list[i].en + "' style='width:10%!important;'>" + arry.list[i].en + "</div>");
                        htmlstr.push("<div class='date-p' title='" + arry.list[i].bindDate + "' style='width:10%!important;'>" + arry.list[i].bindDate + "</div>");
                        htmlstr.push("<div class='state-p' title='" + bindState + "' style='width:5%!important;'>" + bindState + "</div>");
                    } else {
                        htmlstr.push("<div class='ln-p' title='" + arry.list[i].ln + "' style='width:15%!important;'>" + arry.list[i].ln + "</div>");
                        htmlstr.push("<div class='sn-p' title='" + arry.list[i].sn + "' style='width:30%!important;'>" + arry.list[i].sn + "</div>");
                        htmlstr.push("<div class='location-p' title='" + arry.list[i].bindLocation + "' style='width:20%!important;'>" + arry.list[i].bindLocation + "</div>");
                        htmlstr.push("<div class='state-p' title='" + bindState + "' style='width:20%!important;'>" + bindState + "</div>");
                    }
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

//打印标签
function CreateAllPages() {
    LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM1'));
    LODOP.PRINT_INIT("打印芯片标签");
    LODOP.SET_PRINT_PAGESIZE(2,"54mm","85mm");
    LODOP.NewPage();
    LODOP.SET_PRINT_STYLE("Bold", 1);
    LODOP.SET_PRINT_STYLE("FontName","微软雅黑");
    LODOP.SET_PRINT_STYLE("FontSize",15);
    /*LODOP.ADD_PRINT_IMAGE("5mm", "5mm", "10mm", "10mm", "C:/Users/Administrator/Desktop/O1CN0131Hz9G2EBOtNKbJgD_!!1651138706.jpg");*/
    LODOP.ADD_PRINT_IMAGE("5mm", "12mm", "15mm", "15mm", basePath + "images/WFJClient/pc/login/sLogo.png");
    LODOP.ADD_PRINT_TEXT("7mm", "27mm", "80mm", "10mm", '中国    安全    巡查');
    LODOP.ADD_PRINT_TEXT("20mm", "5mm", "80mm", "10mm", '安全      卫生      管理      签到'); // 区域
    LODOP.ADD_PRINT_TEXT("30mm", "5mm", "80mm", "10mm", '自编号：0012'); // 货架
    LODOP.ADD_PRINT_TEXT("43mm", "55mm", "80mm", "10mm", '撕毁无效'); // 展位
    /*if(vipPrice==null||vipPrice==""){
        LODOP.SET_PRINT_STYLEA(10*i+9,"FontSize",28-((price.toString().length-4)*3));
        LODOP.SET_PRINT_STYLEA(10*i+9,"FontName","微软雅黑");
    }else{
        LODOP.SET_PRINT_STYLEA(10*i+10,"FontSize",28-((vipPrice.toString().length-4)*3));
        LODOP.SET_PRINT_STYLEA(10*i+10,"FontName","微软雅黑");
    }*/
    LODOP.PREVIEW();
    //LODOP.PRINT();//直接打印
}