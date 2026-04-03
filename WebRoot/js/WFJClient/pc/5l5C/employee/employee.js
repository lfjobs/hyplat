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

    //查询
    $(".p-bottom").click(function () {
        var ln = $(".ul li.active").attr("id");
        var auditionDirection = $("#auditionDirection").val();
        var auditionPost = $("#auditionPost").val();
        var experience = $("#experience").val();
        $.ajax({
            type: "POST",
            url: basePath + "ea/cosincumbent/sajax_n_ea_saveAudition.jspa?",
            async: true,
            dataType: "json",
            data: {
                "staffID": ln,
                "auditionDirection": auditionDirection,
                "auditionPost": auditionPost,
                "experience": experience,
            },
            success: function (data) {
                window.parent.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/rzManage.jsp?companyID="+companyID
            },
            error: function (data) {
                alert("操作失误");
            }
        });
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
            },
            success: function (data) {
                window.location.href=basePath+"/page/WFJClient/pc/5l5C/employee/rzManage.jsp?companyID="+companyID
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
    var url = "ea/cosincumbent/sajax_n_ea_getListCStaffByCompanyID.jspa?";
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
                        htmlstr.push("<li class= 'clearfix last1' id='" + arry.list[i][0] + "'>");
                    } else {
                        htmlstr.push("<li class='clearfix' id='" + arry.list[i][0] + "'>");
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
                    if(document.documentElement.clientWidth>=960){
                        htmlstr.push("<div class='ln-p' title='" + arry.list[i][0] + "' style='width:7%!important;'>" + arry.list[i][0] + "</div>");
                        htmlstr.push("<div class='sn-p' title='" + arry.list[i][3] + "' style='width:15%!important;'>" + arry.list[i][3] + "</div>");
                        htmlstr.push("<div class='oask-p' title='" + arry.list[i][4] + "' style='width:10%!important;'>" + arry.list[i][4] + "</div>");
                        htmlstr.push("<div class='model-p pc' title='" + arry.list[i][8] + "' style='width:8%!important;'>" + arry.list[i][8] + "</div>");
                        htmlstr.push("<div class='staff-p pc' title='" + arry.list[i][9] + "' style='width:8%!important;'>" + arry.list[i][9] + "</div>");
                        htmlstr.push("<div class='location-p pc' title='" + arry.list[i][10] + "' style='width:15%!important;'>" + arry.list[i][10] + "</div>");
                        htmlstr.push("<div class='model-p pc' title='" + arry.list[i][11] + "' style='width:10%!important;'>" + arry.list[i][11] + "</div>");
                    }else{
                        htmlstr.push("<div class='ln-p' title='" + arry.list[i][0] + "' style='width:15%!important;'>" + arry.list[i][0] + "</div>");
                        htmlstr.push("<div class='sn-p' title='" + arry.list[i][3] + "' style='width:30%!important;'>" + arry.list[i][3] + "</div>");
                        htmlstr.push("<div class='oask-p' title='" + arry.list[i][4] + "' style='width:20%!important;'>" + arry.list[i][4] + "</div>");
                        htmlstr.push("<div class='ln-p' title='" + arry.list[i][8] + "' style='width:10%!important;'>" + arry.list[i][8] + "</div>");
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
    LODOP.SET_PRINT_PAGESIZE(2, "50mm", "80mm");
    LODOP.NewPage();
    LODOP.SET_PRINT_STYLE("Bold", 4);
    /*LODOP.SET_PRINT_STYLE("FontName","微软雅黑");*/
    /*LODOP.ADD_PRINT_IMAGE("5mm", "5mm", "10mm", "10mm", "C:/Users/Administrator/Desktop/O1CN0131Hz9G2EBOtNKbJgD_!!1651138706.jpg");*/
    LODOP.ADD_PRINT_TEXT("10mm", "20mm", "15mm", "15mm", '学员证');
    LODOP.ADD_PRINT_TEXT("15mm", "17mm", "40mm", "10mm", '人员编号：');
    LODOP.ADD_PRINT_IMAGE("15mm", "33mm", "15mm", "15mm", basePath + "images/WFJClient/pc/login/sLogo.png");
    LODOP.ADD_PRINT_TEXT("25mm", "3mm", "50mm", "10mm", '学员编号：'); // 区域
    LODOP.ADD_PRINT_TEXT("35mm", "3mm", "40mm", "10mm", '姓名：'); // 货架
    LODOP.ADD_PRINT_TEXT("45mm", "3mm", "40mm", "10mm", '性别：'); // 展位
    LODOP.ADD_PRINT_TEXT("55mm", "3mm", "40mm", "10mm", '职务：'); // 展位
    LODOP.ADD_PRINT_TEXT("65mm", "3mm", "40mm", "10mm", '单位名称：'); // 展位

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