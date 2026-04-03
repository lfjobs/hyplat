let pageNumber = 0;
let pageSize = 25;
let clientWidth = document.documentElement.clientWidth;
let pageCount = 0;
const PRICETITLE = {
    "retail-li": "零售价",
    "wholesale-li": "批发价",
    "activity-li": "活动价",
    "special-li": "特价",
    "vip-li": "VIP价"
};
const PRICETABLE = {
    "retail-li": "DT_PRO_SETUP",
    "wholesale-li": "dt_pro_wholesale",
    "activity-li": "",
    "special-li": "",
    "vip-li": "dt_pro_vip"
};
const PRICETABLEID = {
    "retail-li": "suid",
    "wholesale-li": "wholesaleId",
    "activity-li": "",
    "special-li": "",
    "vip-li": "vipId"
};
let priceid = "";

window.onload = function () {

    /**
     * 点击列表项触发的事件处理函数
     * 当用户点击 tabs 中的某个列表项时，此函数被触发。它负责更新界面状态，以反映当前选中的列表项。
     *
     * @summary 点击列表项触发的事件
     * @description 移除当前激活状态，为被点击的列表项添加激活状态，更新标题，隐藏反馈信息，并刷新列表内容
     */
    $(".ul-tab li p").click(function () {
        // 移除所有当前激活的类
        $(".active").removeClass("active");
        // 为当前点击的列表项添加激活类
        $(this).addClass("active");
        // 触发所有列表项的点击事件，这可能用于进一步的逻辑处理
        $("#all-li p").trigger('click');
        // 更新页面标题为当前点击列表项的文本内容
        $("#titleName").text($(this).text());
        // 隐藏反馈信息区域
        $(".kickback").hide();
        // 加载或刷新列表内容
        getTitle();
        getList();
    });

    /**
     * 绑定点击事件处理程序，用于切换标签页的激活状态。
     * 当用户点击任一标签页时，该函数将移除当前激活的标签页的激活状态，并将被点击的标签页设置为激活状态。
     * 同时，它会调用getShow函数来更新显示内容，以反映新的激活标签页。
     */
    $(".ul-tab2 li p").click(function () {
        // 移除当前激活的标签页的激活状态
        $(".ul-tab2 li p.active").removeClass("active");
        // 将被点击的标签页设置为激活状态
        $(this).addClass("active");
        // 调用函数来更新显示内容
        getShow();
    });


    /**
     * 触发零售列表中所有段落的点击事件。
     * 此行代码通过jQuery选择器选取id为"retail-li"的元素下的所有<p>元素，并模拟点击事件。
     * 目的是为了触发与这些段落相关的事件处理程序，这可能是由其他JavaScript代码或框架定义的。
     * 无需参数。
     * 不返回任何值。
     */
    $("#retail-li p").trigger('click');

    //添加
    $(".add-p").click(function () {
        window.location.href = basePath + "/ea/mobile/ea_getPage.jspa?yjtype=" + $(".ul-tab .active").parent().attr("id") + "&priceType=01";
    });
    //选中
    $(document).on("click", ".ul li", function (event) {
        event.stopPropagation();
        priceid = $(this).attr("id");
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
    });
    //审核
    $(document).on("click", ".examine-p", function () {
        if (priceid == null || priceid == "") {
            $(".tittle-p").text("请选择要审核的数据！");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "审核中") {
            $(".tittle-p").text("当前状态审核中！驳回后才能再次提交审核");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "删除") {
            $(".tittle-p").text("当前状态已删除！");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "已审") {
            $(".tittle-p").text("已审核！不可重复审核");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "草稿" || $("#" + priceid).find(".state-p").text() == "驳回") {
            const $active = $(".ul-tab .active").parent();
            let title = $("#" + priceid).find(".name-p").text() + PRICETITLE[$active.attr("id")];
            $.ajax({
                url: basePath + "/ea/mobile/sajax_ea_updateState.jspa",
                type: "get",
                dataType: "html",
                data: {
                    "priceid": priceid,
                    yjtype: $(".ul-tab .active").parent().attr("id")
                },
                error: function (data) {
                    $(".tittle-p").text("审核失败！");
                    $(".div-tingyong").show();
                },
                success: function (data) {
                    localStorage.setItem("title", title + "佣金设置");
                    localStorage.setItem("source", "佣金设置");
                    localStorage.setItem("htmlUrl", "/ea/mobile/ea_getPage.jspa?priceid=" + priceid + "&yjtype=" + $(".ul-tab .active").parent().attr("id") + "&priceType=04");
                    localStorage.setItem("tableName", PRICETABLE[$active.attr("id")]);
                    localStorage.setItem("idName", PRICETABLEID[$active.attr("id")]);
                    localStorage.setItem("idValue", priceid);
                    localStorage.setItem("stateName", "state");
                    localStorage.setItem("stateValue", "00");
                    localStorage.setItem("refundValue", "04");
                    window.location.href = basePath + "page/ea/main/office_ea/contract/selectCompany.jsp?typee=L";
                }
            });
        }
    });
    //修改
    $(document).on("click", ".update-p", function () {
        if (priceid == null || priceid == "") {
            $(".tittle-p").text("请选择要修改的数据！");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "审核中") {
            $(".tittle-p").text("当前状态审核中！驳回后才能修改");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "删除") {
            $(".tittle-p").text("当前状态已删除！");
            $(".div-tingyong").show();
            return;
        }

        if ($("#" + priceid).find(".state-p").text() == "已审" || $("#" + priceid).find(".state-p").text() == "草稿" || $("#" + priceid).find(".state-p").text() == "驳回") {
            window.location.href = basePath + "/ea/mobile/ea_getPage.jspa?priceid=" + priceid + "&yjtype=" + $(".ul-tab .active").parent().attr("id") + "&priceType=02";
        }
    });
    //查看
    $(document).on("click", ".view-p", function () {
        if (priceid == null || priceid == "") {
            $(".tittle-p").text("请选择要查看的数据！");
            $(".div-tingyong").show();
            return;
        } else {
            window.location.href = basePath + "/ea/mobile/ea_getPage.jspa?priceid=" + priceid + "&yjtype=" + $(".ul-tab .active").parent().attr("id") + "&priceType=03";
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

    //分页滚动
    $(".box-fh2").scroll(function () {
        var Height = $(this).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 120) {
            if (pageNumber < pageCount) {
                getList();
            }
        }
    });

    $(".print-p").click(function () {
        CreateAllPages();
    });

}

// 当窗口大小发生改变时触发的事件处理函数
window.onresize = function () {
    // 获取浏览器用户代理字符串
    var u = navigator.userAgent;
    // 检查是否为Android设备
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;
    // 检查是否为iOS设备
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);

    // 如果既不是Android设备也不是iOS设备
    if (isAndroid == false && isiOS == false) {
        // 获取文档的视口宽度
        var clientWidth = document.documentElement.clientWidth;
        // 根据视口宽度决定移除手机端或PC端的页面部分
        if (clientWidth >= 960) {
            $("#phone-sec").remove();
        } else {
            $("#pc-sec").remove();
        }
    }
}


// 定义常量
const BASE_PATH = "/ea/mobile/sajax_ea_ajaxselectRetailList.jspa";

// 优化的getList函数
function getList() {
    // 优化：只隐藏当前相关的div-chaxun元素
    $(this).closest(".div-chaxun").hide();
    const $active = $(".ul-tab .active").parent();

    // 优化：确保pageNumber不会无限增加
    pageNumber = pageNumber < 100 ? pageNumber + 1 : pageNumber;

    $.ajax({
        url: basePath + BASE_PATH,
        type: "get",
        dataType: "json",
        async: false, // 注意：此处有意保留原始设置，实践中建议使用异步请求
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            parameter: $("#search").val(),
            "yjtype": $(".ul-tab .active").parent().attr("id")
        },
        success: function (data) {
            // 优化：使用JSON.parse代替eval
            const m = JSON.parse(data);
            const arry = m.pageForm;
            let htmlstr = [];

            if (arry == null || arry.list.length === 0) {
                $(".tittle-p").text("没有数据");
                $(".div-tingyong").show();
                return false;
            }

            pageNumber = arry.pageNumber;
            pageCount = arry.pageCount;
            $(".last1").remove();

            for (let i = 0; i < arry.list.length; i++) {
                const isLastItem = i === arry.list.length - 1;
                const listItemClass = isLastItem ? "clearfix last1" : "clearfix";
                const itemId = arry.list[i][11];

                htmlstr.push(`<li class='${listItemClass}' id='${itemId}'>`);
                // 优化：使用模板字符串和条件运算符提高代码清晰度和简洁性
                htmlstr.push(`<div class='title-pc'><div class='sex'>`);
                htmlstr.push(`<img class='img-01' src='` + basePath + `images/ea/office/contract/selectp/img_02.png'/>`);
                htmlstr.push(`<img class='img-02' src='` + basePath + `images/ea/office/contract/selectp/img_03.png'/>`);
                htmlstr.push("</div>");

                let bindState = arry.list[i][13];
                bindState = (bindState == "00" ? "已审" : bindState == "01" ? "删除" : bindState == "02" ? "草稿" : bindState == "03" ? "待审" : "驳回");
                htmlstr.push("<div class='num-p' title='" + getnotnull(arry.list[i][1]) + "' >" + getnotnull(arry.list[i][1]) + "</div>");
                htmlstr.push("<div class='name-p' title='" + getnotnull(arry.list[i][2]) + "'>" + getnotnull(arry.list[i][2]) + "</div>");
                htmlstr.push("<div class='price-p' title='" + getnotnull(arry.list[i][8]) + "'>" + getnotnull(arry.list[i][8]) + "</div>");
                htmlstr.push("<div class='cost-p pc' title='" + getnotnull(arry.list[i][3]) + "'>" + getnotnull(arry.list[i][3]) + "</div>");
                htmlstr.push("<div class='agent-p kickback' title='" + getnotnull(arry.list[i][6]) + "'>" + getnotnull(arry.list[i][6]) + "</div>");
                htmlstr.push("<div class='service-p kickback' title='" + getnotnull(arry.list[i][4]) + "'>" + getnotnull(arry.list[i][4]) + "</div>");
                htmlstr.push("<div class='expend-p kickback' title='" + getnotnull(arry.list[i][7]) + "'>" + getnotnull(arry.list[i][7]) + "</div>");
                htmlstr.push("<div class='state-p kickback' title='" + bindState + "' style='width:5%!important;'><input type='hidden' class='state-input' value='" + arry.list[i][13] + "'/>" + bindState + "</div>");
                htmlstr.push("</div></li>");

            }
            $(".ul").append(htmlstr.join(""));
            getShow();
        },
        error: function (data) {
            console.error("请求失败:", data);
            // 优化：提供用户反馈，例如显示错误消息
        }
    });
}

function getnotnull(data) {
    return (data == null || data == "") ? 0.00 : data;
}

function getTitle() {
    pageNumber = 0;
    $(".ul").empty();
    let htmlTitle = [];
    let titlePrice;
    const $active = $(".ul-tab .active").parent();
    titlePrice = PRICETITLE[$active.attr("id")];
    // 使用switch-case结构提高可读性
    //titlePrice=gettitlePrice($active.attr("id"));
    htmlTitle.push("<li class='clearfix'>");
    htmlTitle.push("<div class='title-pc'>");
    htmlTitle.push("<div class='sex'> 选择</div>");
    htmlTitle.push("<div class='num-p' title='条码'>条码</div>");
    htmlTitle.push("<div class='name-p' title='名称'>名称</div>");
    htmlTitle.push("<div class='price-p' title='售价'>" + titlePrice + "</div>");
    htmlTitle.push("<div class='cost-p pc' title='芯片类型'>成本</div>");
    htmlTitle.push("<div class='agent-p kickback' title='芯片类型'>代理佣金</div>");
    htmlTitle.push("<div class='service-p kickback' title='芯片类型'>业务佣金</div>");
    htmlTitle.push("<div class='expend-p kickback' title='芯片类型'>红包佣金</div>");
    htmlTitle.push("<div class='state-p' title='状态'>状态</div>");
    htmlTitle.push("</div>");
    htmlTitle.push("</li>");
    $(".ul").append(htmlTitle.join(""));
}

function getShow() {
    var $p = $(".ul-tab2 li p.active");
    $(".sex").attr("style", "width:8%!important;");
    if (clientWidth < 960) {
        $(".kickback").hide();
        $(".num-p").attr("style", "width:24%!important;");
        $(".name-p").attr("style", "width:19%!important;");
        $(".state-p").attr("style", "width:9%!important;");
        $(".pc").hide();
        if ($p.parent().attr("class") == "all-li") {
            $(".price-p").attr("style", "width:30%!important;");
        } else if ($p.parent().attr("id") == "agent-li") {
            $(".agent-p").show();
            $(".price-p").attr("style", "width:15%!important;");
            $(".agent-p").attr("style", "width:16%!important;");
        } else if ($p.parent().attr("id") == "service-li") {
            $(".service-p").show();
            $(".price-p").attr("style", "width:15%!important;");
            $(".service-p").attr("style", "width:16%!important;");
        } else if ($p.parent().attr("id") == "expend-li") {
            $(".expend-p").show();
            $(".price-p").attr("style", "width:15%!important;");
            $(".expend-p").attr("style", "width:16%!important;");
        }
    } else {
        $(".num-p").attr("style", "width:10%!important;");
        $(".name-p").attr("style", "width:15%!important;");
        $(".price-p").attr("style", "width:10%!important;");
        $(".cost-p").attr("style", "width:10%!important;");
        $(".agent-p").attr("style", "width:10%!important;");
        $(".service-p").attr("style", "width:10%!important;");
        $(".expend-p").attr("style", "width:10%!important;");
        $(".state-p").attr("style", "width:5%!important;");
        $(".pc").show();
    }
}

//打印标签
function CreateAllPages() {
    LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM1'));
    LODOP.PRINT_INIT("打印芯片标签");
    LODOP.SET_PRINT_PAGESIZE(2, "54mm", "85mm");
    LODOP.NewPage();
    LODOP.SET_PRINT_STYLE("Bold", 1);
    LODOP.SET_PRINT_STYLE("FontName", "微软雅黑");
    LODOP.SET_PRINT_STYLE("FontSize", 15);
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