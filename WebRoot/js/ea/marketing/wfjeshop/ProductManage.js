var parameter;
var pagenumber = 0;
var pagecount = 0;
var t;
var search;
$(function () {

    DataLoading();

    // 查询框显示
    $(".li-chaxun").click(function () {
        $(".div-chaxun").show();
    });

    // 查询框隐藏
    $(".div-chaxun").click(function () {
        $(".div-chaxun").hide();
    });

    $(".div-chaxun .div-box").click(function (e) {
        e.stopPropagation();
    });

    // 隐藏选择框
    $(".p-img").hide();
    // 物品显示高度
    /*$(".container").height($(window).height() - $("header").outerHeight() - $("menu").outerHeight() - 6);*/
    // 删除修改出按钮
    $(document).on("click", ".li-gai",function () {
        if ($(".p-img").is(":hidden")) {
            $(".p-img").show();
            $(".container > ul").find(".p-img").removeClass("active"); //清除所有选中
        }
        else {
            $(".p-img").hide();
        }
    });

    // 按钮点击选中
    $(document).on("click", ".container > ul",function () {
        $(".container > ul").removeClass("active");
        $(this).addClass("active");
        var ppid=$(".active").attr("id");
        var goodsID=$(".active .goodsID").text();
        var param="companyID=" + companyID + "&staffID=" + staffID+"&ppId="+ppid+"&goodsId="+goodsID;
        document.location.href = basePath + "ea/productsmanag/ea_toProductsLaunch.jspa?"+param;
    });

    $(".li-add").click(function () {
        document.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductManage_add.jsp?companyID=" + companyID + "&staffID=" + staffID;

    });

    /*$(".li-edit").click(function () {
        if($(".active").length<=0){
            alert("请选择要修改的产品！");
            return;
        }
        var ppid=$(".active").attr("id");
        var goodsID=$(".active .goodsID").text();
        var param="companyID=" + companyID + "&staffID=" + staffID+"&ppId="+ppid+"&goodsId="+goodsID;
        document.location.href = basePath + "ea/productsmanag/ea_toProductsLaunch.jspa?"+param;
    });*/

    $(".p-bottom").click(function () {
        $(".showVal").remove();
        pagenumber = 0;
        search = 1;
        DataLoading();
        $(".div-chaxun").hide();
    })
});

$(window).scroll(function () {
    var Height = $(window).height();
    var scroll = $(document).scrollTop(); //滚动高度

    var Top = $(".last").offset().top; //元素距离顶部距离

    /*console.log("窗口" + '  ' + Height);
     console.log("Top" + '  ' + Top);
     console.log("scroll" + '  ' + scroll);
     console.log("TOP-窗口："+(Top - Height));
     console.log("TOP-窗口-Scroll："+(Top - Height-scroll));*/
    if (scroll >= 80) {
        $(".bug-nav2").addClass("nav-fixed")
    } else {
        $(".bug-nav2").removeClass("nav-fixed")
    }
    if (Top - Height - scroll <= 100) {
        if (pagenumber < pagecount) {
            DataLoading();
        }
    }
});

function DataLoading() {
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: basePath + "ea/productsmanag/sajax_ea_getProductAjax.jspa",
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "companyID": companyID,
            "pageForm.pageNumber": pagenumber,
            "search": search,
            "goodsName": $.trim($("#goodsName").val()),
            "type": $.trim($("#type").val()),
            "barCode": $.trim($("#barCode").val())
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var htl = new Array();
            if (pageForm != null) {
                var list = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
            }
            if (pageForm != null && pageForm.recordCount > 0) {
                for (var i = 0; i < list.length; i++) {
                    var p = list[i];
                    if (i == list.length - 1) {
                        htl.push("<ul class='last clearfix showVal' id='" + p.ppID + "'>");
                    } else {
                        htl.push("<ul class='clearfix showVal' id='" + p.ppID + "'>");
                    }
                    htl.push("<div >");//onclick=\"toDetail('" + p.ppID + "');\"
                    htl.push("<span style='display: none;' class='goodsID'>"+p.goodsID+"</span>");
                    htl.push("<li>" + p.barCode + "</li>");
                    htl.push("<li>" + p.goodsName + "</li>");
                    htl.push("<li>" + p.variableID + "</li>");
                    htl.push("<li>" + p.standard + "</li>");
                    htl.push("<li>" + p.type + "</li>");
                    htl.push("</ul>");
                }
            } else {
                htl.push("<li style='text-align:center;'>暂无数据</li>");
            }
            $(".container").append(htl.join(""));
            //getHeight(obj);
        },
        error: function (data) {
            alert("数据获取失败！")
        }
    });
}