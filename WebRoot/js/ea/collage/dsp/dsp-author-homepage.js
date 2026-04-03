var key = "60f2afc9a34ec851b0baf7fd83eb75ba";
var tab = 1
var t = "";
var tt = "";
var head = "";
var loading = false;

$(function () {
    //查看
    $(document).on("click", "#ttsw_list li", function () {

    });
    //查看
    $("#select-tab1").on("click", function () {
        tab = 1;
        $(this).addClass("active");
        $("#select-tab2").removeClass("active");
        reloadData()
    });
    $("#select-tab2").on("click", function () {
        tab = 2;
        $("#select-tab1").removeClass("active");
        $(this).addClass("active");
        reloadData()
    });

    reloadData();
});

function getHeight() {
    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(document).scrollTop() <= $(window).height()) {
            loadData();
        }
    }
}

function reloadData() {
    clearInterval(t);
    $("#ttsw_list").html("");
    pageNumber = 1;
    loadData();
}

function loadData() {
    if (pageNumber > totalPages) {
        return;
    }
    if (loading) {
        return;
    }
    loading = true;
    var url = basePath + "/ea/dsp/sajax_ea_myVideos.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: true,
        dataType: "json",
        data: {
            staffId: videoStaffId,
            pageNumber,
            pageSize
        },
        success: function (data) {
            var list = data.content;
            if (list === null || data.content.length === 0) {
                return;
            }
            var innerHtml = new Array();
            $(".last").remove();

            for (let i = 0; i < list.length; i++) {
                const video = list[i];

                innerHtml.push(`
                        <div class="relative basis-1/3 w-full p-1" style="aspect-ratio: 3/4">
                            <a href="${basePath}ea/dsp/ea_getVideoViewListPage.jspa?videoId=${video.videoId}&videoStaffId=${videoStaffId}&viewType=1&videoIndex=${Number((pageNumber - 1) * pageSize) + Number(i) }&pageSize=${pageNumber * pageSize}">
                                <img style="width: 100%;aspect-ratio: 3/4" src="${video.coverUrl}" onerror="this.src='${basePath}images/ea/collage/dsp/pic_03.png'"/>
                            </a>
                            <span class="absolute flex flex-row space-x-1 text-center bottom-3 left-2 bg-white-100 text-sm text-white w-full p-1">
                                <img style="width: 12px; height: 12px"  src="${basePath}images/ea/collage/dsp/img-13.png"/>
                                <span style="line-height: 12px">${video.likedCount}</span>
                            </span>
                        </div>
                    `);
            }

            innerHtml.push('<div class="clearfix last"></div>')

            $("#ttsw_list").append(innerHtml.join(""))

            totalPages = data.totalPages;
            pageNumber++;

            if (tt === "") {
                t = setInterval("getHeight()", 500);
            }

            loading = false;
        },
        error: function (data) {
            console.log("加载下一页失败");
            loading = false;
        }
    });
}
