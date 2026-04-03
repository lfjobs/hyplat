var key = "60f2afc9a34ec851b0baf7fd83eb75ba";
var tab = 1
var t = "";
var tt = "";
var head = "";
var loading = false

$(function () {
    //查看
    $(document).on("click", "#ttsw_list li", function () {

    });
    //查看
    $("#select-tab1").on("click", function () {
        tab = 1;
        $(this).addClass("active");
        $("#select-tab4").removeClass("active");
        reloadData()
    });
    $("#select-tab4").on("click", function () {
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
    totalPages = 1;
    loadData();
}

function loadData() {
    if (pageNumber > totalPages) {
        return;
    }
    if (loading) {
        return;
    }
    loading = true
    var url = basePath + (tab === 1 ? "/ea/dsp/sajax_ea_myVideos.jspa" : "/ea/dsp/sajax_ea_myLikedVideos.jspa");
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: true,
        dataType: "json",
        data: {
            pageNumber,
            pageSize,
            staffId
        },
        success: function (data) {
            console.log("data->")
            console.log(data)

            var list = data.content;

            var innerHtml = new Array();
            $(".last").remove();

            for (let i = 0; i < list.length; i++) {
                const video = list[i];

                if (tab === 1) {
                    // 发布的
                    innerHtml.push(`
                        <div id="rvideo-${video.videoId}" class="relative basis-1/3 w-full p-1" style="aspect-ratio: 3/4">
                            <a href="${basePath}ea/dsp/ea_getVideoViewListPage.jspa?videoId=${video.videoId}&videoStaffId=${videoStaffId}&viewType=1&videoIndex=${Number((pageNumber - 1) * pageSize) + Number(i) }&pageSize=${pageNumber * pageSize}">
                                <img style="width: 100%;aspect-ratio: 3/4" src="${video.coverUrl}" onerror="this.src='${basePath}images/ea/collage/dsp/pic_03.png'"/>
                            </a>
                            <span class="absolute flex flex-row space-x-1 text-center bottom-3 left-2 bg-white-100 text-sm text-white w-full p-1">
                                <img style="width: 12px; height: 12px"  src="${basePath}images/ea/collage/dsp/img-13.png"/>
                                <span style="line-height: 12px">${video.likedCount}</span>
                            </span>
                            <span onclick="deleteVideo('${video.videoId}')" class="absolute top-3 right-3 text-red-500 p-1" style="background: rgba(198,198,198,0.65); border-radius: 8px; font-weight: bold">
                                X
                            </span>
                        </div>
                    `);
                } else {
                    // 喜欢的
                    innerHtml.push(`
                    <div class="relative basis-1/3 w-full p-1" style="aspect-ratio: 3/4">
                        <a href="${basePath}ea/dsp/ea_getVideoViewListPage.jspa?videoId=${video.videoId}&videoStaffId=${videoStaffId}&viewType=2&videoIndex=${Number((pageNumber - 1) * pageSize) + Number(i) }&pageSize=${pageNumber * pageSize}">
                            <img style="width: 100%;aspect-ratio: 3/4" src="${video.coverUrl}" onerror="this.src='${basePath}images/ea/collage/dsp/pic_03.png'">
                        </a>
                        <div class="absolute flex flex-row space-x-1 text-center bottom-3 left-2 bg-white-100 text-sm text-white w-full p-1">
                            <img style="width: 12px; height: 12px" src="${basePath}images/ea/collage/dsp/img_21.png"/>
                            <span style="line-height: 12px">${video.likedCount}</span>
                        </div>
                    </div>
                `);
                }
            }

            innerHtml.push('<div class="clearfix last"></div>')

            $("#ttsw_list").append(innerHtml.join(""))

            totalPages = data.totalPages;
            pageNumber++;

            localVideoList.push(...data.content);

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

function deleteVideo(videoId) {
    if (!window.confirm("确认删除该视频?")) {
        return
    }
    var url = basePath + "/ea/dsp/sajax_ea_deleteVideo.jspa"
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: false,
        dataType: "json",
        data: {
            videoId,
        },
        success: function (data) {
            $("#rvideo-" + videoId).hide();
        },
        error: function (data) {
        }
    });
}
