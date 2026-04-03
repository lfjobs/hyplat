$(function () {

    $(window).scroll(function () {
        var windowHeight = $(window).height();
        var scrollHeight = $(document).scrollTop(); //滚动高度

        var offsetTop = $(".goods-wrapper").last().offset().top; //元素距离顶部距离

        if (offsetTop - windowHeight - scrollHeight <= 20) {
            if (pageNumber < pagecount) {
                loadData();
            }
        }
    });

    //详情
    $(document).on("click", ".goods-wrapper li", function () {
        var ppid = $(this).attr("id");
        var goodsid = $(this).find(".goodsid").val();
        var companyId = $(this).find(".companyId").val();
        var ccompanyId = $(this).find(".ccompanyId").val();
        var pricetype = $(this).find(".pricetype").val();

        document.location.href = basePath + "ea/wfjshop/ea_doodsDetail.jspa?ppid=" + ppid + "&goodsid=" + goodsid + "&companyId=" + companyId + "&ccompanyId=" + ccompanyId + "&pricetype=" + pricetype;
    })

    loadData();
})

function loadData() {
    if (pageNumber > totalPages) {
        return;
    }

    var url = basePath + "ea/dsp/sajax_ea_getVideoProductList.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        data: {
            pageNumber: pageNumber,
            videoId: videoId
        },
        dataType: "json",
        success: function (data) {
            if (data.content.length === 0) {
                return false;
            }
            var htmls = new Array();

            var goodsList = data.content
            for (var i = 0; i < goodsList.length; i++) {
                var goods = goodsList[i];
                htmls.push("<li id='" + goods.goodsId + "' class='clearfix' >");
                htmls.push("<input type='hidden' value='" + goods.priceType + "' class='pricetype'/>");
                htmls.push("<input type='hidden' value='" + goods.goodsId + "' class='goodsid'/>");
                htmls.push("<input type='hidden' value='" + goods.companyId + "' class='companyId'/>");
                htmls.push("<input type='hidden' value='" + goods.ccompanyId + "' class='ccompanyId'/>");

                htmls.push("<div class='div-img'>");
                htmls.push("<img src='" + basePath + goods.image + "'    onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'/>");
                htmls.push("</div>");
                htmls.push("<div class='div-right'>");
                htmls.push("<div class='top'>");
                htmls.push(obj[2]);
                htmls.push("</div>");
                htmls.push("<div class='bottom'>");
                htmls.push("<p>￥" + obj[4] + "</p>");
                htmls.push("<p>去抢购</p>");
                htmls.push("</div>");
                htmls.push("</div>");
                htmls.push("</li>");
            }

            totalPages = data.totalPages;
            pageNumber++

            $(".goods-wrapper").append(htmls.join(""));
        },
        error: function (data) {
            console.log("加载下一页失败");
        }
    });

}