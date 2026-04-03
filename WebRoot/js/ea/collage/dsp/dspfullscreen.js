var t = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
var loading = false;

$(function () {

    $("#recommend").on('click', function (e) {
        $("#follow").removeClass("active")
        $(this).addClass('active')
        type = '00'
        $("section").remove()
        currentTab = "RECOMMEND"
        refreshSwiper()
    })

    $("#follow").on('click', function (e) {
        if (staffId === '') {
            document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
            return;
        }
        $("#recommend").removeClass("active")
        $(this).addClass('active')
        type = ''
        $("section").remove()
        currentTab = "FOLLOW"
        refreshSwiper()
    })

//我的订单
    $('#order').click(function () {
        if (sccId === "") {
            document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            document.location.href = basePath + "ea/pobuy/ea_getPhoneOrdersList.jspa?staffId=" + staffId + "&sccId=" + sccId;
        }
    });

//钱包
    $("#bag").click(function () {
        if (sccId == "") {
            document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            document.location.href = basePath + "/ea/jinbi/ea_gethyjifen.jspa?user=" + user + "&sccid=" + sccId + "&khd=0&app=00"
        }
    });

//购物车
    $("#cart").click(function () {
        if (sccId == "") {
            document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            if (pricetype == "2") {
                document.location.href = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa";
            } else {
                document.location.href = basePath + "ea/wfjshop/ea_getcity.jspa";
            }
        }
    });

    $("#center").click(function () {
        if (sccId == "") {
            document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
        }
        $(".div-list").show();
    });
    $("#close-div").click(function () {
        $(this).parents(".div-list").hide();
    })
    if (isAndroid == true) {

    } else if (isiOS == true) {
        $(".div-box img").addClass("div-iosimg");
    }

    dealNum();
    dealListNum();
    var myVideo = document.getElementById("video");
    //图片高度判断
    if (($(".div-box img").height() / 2) >= ($(".div-box img").width())) {
        $(".div-box img").addClass("imgtop");
    }
    if (($(".div-box img").width() / 2) <= ($(".div-box img").height())) {
        $(".div-box img").addClass("imgleft");
    }
    //点击播放暂停
    $(".div-play").click(function () {
        $('.player').trigger("click");
    })
    //点击关注
    $(".div-01").click(function (e) {

        e.stopPropagation();
        if ($(this).find("div").is(".active")) {
            $(this).find("div").removeClass("active");  //取消
        } else {
            $(this).find("div").addClass("active");//关注
        }
        var url = basePath + "ea/dsp/sajax_ea_careUser.jspa";
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: true,
            data: {
                videoStaffId: videoStaffId,
                ajax: "ajax"
            },
            success: function (data) {
                var login = data.login;
                if (login == "login") {
                    document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
            },
            error: function (data) {
                console.log("关注失败");
            }

        })
    })
    //点击红心
    var i = 0;
    $(".div-02").click(function (e) {
        e.stopPropagation();
        b();
    })
    //点击分享
    $(".div-04").click(function () {
        $(".fenxiang").show();
        $("body").addClass("body-fenxiang");
    })
    $(".qx").click(function () {
        $(".fenxiang").hide();
        $("body").removeClass("body-fenxiang");
    })
    //点击去掉缩略图

    $('.box').on('click', function () {
        $('.video-img').hide();
    });
    //全屏暂停播放
    $('.player').on('click', function () {
        clicking();
    });
    $('.nobox').on('click', function () {
        clicking();
    });
    let clicked = 1;
    let time = null;

    function clicking() {
        if (clicked === 1) {
            clicked++
            time = setTimeout(() => {
                clicked = 1
                // 单击
                a();
            }, 300)
        } else if (clicked === 2) {
            clearInterval(time)
            clicked = 1
            // 双击
            b();
        }
    }

    //点击作品查看
    $(document).on("click", ".morevideo li", function () {
        var myVideo = document.getElementById("video");
        $("#video").attr("autoplay", "true");//直接播放
        myVideo.play();
        $("#video").attr("data-play", "pause");
        $(".div-play img").hide();
        $('.video-img').hide();
        setVideo($(this));
        $("html,body").animate({scrollTop: '0px'}, 200); //回到顶部

    });

    //下载
    $(".shopping").click(function (e) {
        e.stopPropagation();
        //  down();
        document.location.href = basePath + "ea/dsp/ea_getdspProductsList.jspa?videoId=" + videoId + "&web=web";
    });

    // 模拟推荐点击
    $("#recommend").click();
});

function a() {
    //console.log('单击')
    if ($("#video").data("play") == "play") {
        $(".player").find('video')[0].play();
        $("#video").data("play", "pause");
        $(".div-play img").hide();
        $('.video-img').hide();
    } else {
        $(".player").find('video')[0].pause();
        $("#video").data("play", "play");
        $(".div-play img").show();
    }
}

function b() {
    //console.log('双击')
    if ($(".div-02").find("div").is(".active")) {
        $(".div-02").find("div").removeClass("active");
        $(".div-02 .praisev").text(Number($(".div-02 .praisev").text()) - 1);
    } else {
        $(".div-02").find("div").addClass("active");
        $(".div-02 .praisev").text(Number($(".div-02 .praisev").text()) + 1);
    }
    dealNum();
    dz();
}

function refreshSwiper() {
    firstUpdate = true;
    removePlayer();
    $(".swiper-wrapper").html('');
    reloadData();
    mySwiper.slideTo(0, 1000, false);
}

function reloadData() {
    pageNumber = 1;
    totalPages = 1;
    videoList = [];
    loadData();
}

//作品
function loadData() {
    if (pageNumber > totalPages) {
        return;
    }
    if (loading) {
        return;
    }
    loading = true
    var url = `${basePath}ea/dsp/sajax_ea_getFullScreenVideoList.jspa`;
    $.ajax({
        url: url,
        type: "get",
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            type: type,
            staffId: staffId
        },
        dataType: "json",
        success: function (data) {
            if (data === null) {
                return false;
            }

            appendVideoSlides(data.content);

            if (data.content.length < pageSize) {
                appendEmptySlide();
            }

            totalPages = data.totalPages;
            pageNumber++;

            mySwiper.updateSlides();

            loading = false;
        },
        error: function (data) {
            console.log("加载下一页失败");

            loading = false;
        }
    });
}

function dealListNum() {
    $(".morevideo li").each(function () {
        $(this).find(".dz").text(jsw($(this).find(".praisevl").val()));
    })
}

function dealNum() {
    $(".pw").text(jsw($(".praisev").text()));
    $(".cw").text(jsw($(".plcountv").text()));
    $(".sh").text(jsw($(".sharev").text()));
}

//数字转成w单位
function jsw(num) {
    var result = num;
    if (Number(num) > 10000) {
        return formatDecimal(num / 10000, 1) + "w";
    }
    return result;
}

//保留小数，不四舍五入
function formatDecimal(num, decimal) {
    num = num.toString()
    let index = num.indexOf('.')
    if (index !== -1) {
        num = num.substring(0, decimal + index + 1)
    } else {
        num = num.substring(0)
    }
    return parseFloat(num).toFixed(decimal);
}

//下载
function down(videoId) {
    // if (isAndroid === true) {
    //     document.location.href = "https://a.app.qq.com/o/simple.jsp?pkgname=com.xiaofeng.androidframework&channel=0002160650432d595942&fromcase=60001";
    // } else if (isiOS === true) {
    //     document.location.href = "https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en";
    // }
    $("#iframe-video-comments").show();
    $("#iframe-video-comments").attr("src", `${basePath}page/ea/collage/dsp/dsp-comment.jsp?videoId=${videoId}&videoStaffId=${staffId}`);
    document.getElementById('iframe-video-comments').contentWindow.postMessage({message: 'hello'}, '*');
}

function appendVideoSlides(videos) {
    let htmls = [];

    for (let i = 0; i < videos.length; i++) {
        var video = videos[i];

        let slideHtml = `
            <section class="relative swiper-slide">
                <div class="video w-full h-full">
                    <div id="video-${video.videoId}" style="z-index: 0"></div>
                    
                    <!-- 购物车 短视频标题信息 -->
                    <div class="absolute bottom-2 p-2 space-y-2">
                        <div class="flex flex-row space-x-2">
                            <img width="24" height="24" src="${basePath}images/ea/collage/dsp/shop.png"/>
                            <span class="text-small m-auto" onclick=productList("${video.videoId}")>购物 | 视频同款</span>
                        </div>
                        <p class="text-small">${video.authorName}</p>
                        <p class="text-small">${video.title}</p>
                    </div>
                    
                    <!-- 短视频点赞 关注等信息 -->
                    <div class="absolute right-4 bottom-32 space-y-4">
                        <div class="">
                            <div class="rounded rounded-full">
                                <a>
                                    <img onclick="authorHome('${video.authorId}')" class="m-auto rounded rounded-full" width="48" height="48" src="${basePath}${video.avatar}"  onerror="this.src='${basePath}images/ea/driving/elkc/head.png'" />
                                </a>
                            </div>
                            <div class="" style="margin-top: -5px">
                                <img style="width:16px; height: 16px; margin: auto;" id="follow-${video.videoId}" onclick="follow('${video.videoId}')" src="${basePath + (video.isFollowed === '1' ? 'images/ea/collage/dsp/img_15.png' : 'images/ea/collage/dsp/img_10.png')}"/>
                            </div>
                        </div>
                        
                        <!-- 点赞 -->
                        <div class="flex flex-col">
                            <div class="">
                                <img style="width: 24px; height: 24px; margin: auto;" id="like-${video.videoId}" onclick="like('${video.videoId}')" class="${video.isLiked === '1' ? 'active' : ''}" src="${basePath + (video[10] === '1' ? 'images/ea/collage/dsp/img_19.png' : 'images/ea/collage/dsp/img_21.png')}"/>
                            </div>
                            <div id="like-text-${video.videoId}" class="text-center">
                                ${video.likedCount}
                            </div>
                        </div>
                        
                        <div class="" onclick="down('${video.videoId}')">
                            <span class="plcountv" style="display: none;">${video.commentCount}</span>
                            <img style="width: 24px; height: 24px; margin: auto;" src="${basePath}images/ea/collage/dsp/img_26.png"/>
                            <div class="text-center">
                               ${video.commentCount}
                            </div>
                        </div>
                        <div class="">
                            <p class="share" onclick=share('${video.authorName}','${video.title}','${video.playUrl}','${video.coverUrl}')> 分享</p>
                        </div>
                        <div class="" style="display:none;">
                            <span class="sharev" style="display: none;">${video.sharedCount}</span>
                            <img class="width: 24px; height: 24px; margin: auto;" src="${basePath}images/ea/collage/dsp/img_30.png"/>
                            <p class="sh">
                                ${video.sharedCount}
                            </p>
                        </div>
                        
                        <div>
                            <img onclick="$('#side-menu').toggleClass('hidden')" style="width: 24px; height: 24px; margin: auto;" id="center" src="${basePath}images/ea/collage/dsp/center.png"/>
                        </div>
                    </div>
                </div>
            </section>
        `;

        mySwiper.appendSlide(slideHtml);
    }

    videoList.push(...videos);
}

function appendEmptySlide() {
    let emptyHtml = `
            <section class="relative swiper-slide">
                <div class="video w-full h-full flex text-center">
                    <div class="text-white m-auto space-y-4">
                        <button class="bg-red-600 p-2 rounded" onclick="refreshSwiper()">刷新</button>
                        <div class="text-sm">没有更多视频...</div>
                    </div>
                </div>
            </section>
        `
    videoList.push({});
    $(".swiper-wrapper").append(emptyHtml);
}

function authorHome(videoStaffId) {
    if (staffId === '') {
        document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
        return;
    }

    if (staffId !== '' && staffId === videoStaffId) {
        document.location.href = `${basePath}ea/dsp/ea_getMyHomePage.jspa`;
    } else {
        document.location.href = `${basePath}ea/dsp/ea_getAuthorHomePage.jspa?videoStaffId=${videoStaffId}`;
    }
}

function follow(videoId) {
    var video = videoList.filter(video => video.videoId === videoId)[0];
    $.ajax({
        url: basePath + "ea/dsp/sajax_ea_careUser.jspa",
        type: "post",
        dataType: "json",
        async: true,
        data: {
            videoStaffId: video.authorId,
            ajax: "ajax"
        },
        success: function (data) {
            var login = data.login;
            if (login == "login") {
                document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            } else {
                if (video[11] === "0") {
                    console.log($('#follow-' + video.videoId));
                    $('#follow-' + video.videoId).attr('src', basePath + 'images/ea/collage/dsp/img_10.png');
                } else {
                    console.log($('#follow-' + video.videoId));
                    $('#follow-' + video.videoId).attr('src', basePath + 'images/ea/collage/dsp/img_15.png');
                }
            }
        },
        error: function (data) {
            console.log("关注失败");
        }
    });
}

function productList(videoId) {
    //  down();
    document.location.href = basePath + "page/ea/collage/dsp/dspgoodslist.jsp?videoId=" + videoId;
}

/**
 * LIKE
 */
function like(videoId) {
    var video = videoList.filter(video => video.videoId === videoId)[0];
    $.ajax({
        url: basePath + "ea/dsp/sajax_ea_addZan.jspa",
        type: "post",
        dataType: "json",
        async: true,
        data: {
            videoId: videoId,
            ajax: "ajax"
        },
        success: function (data) {
            var login = data.login;
            var result = data.result;
            if (login == "login") {
                document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            } else {
                var heart = $('#like-' + video.videoId);
                var likeText = $('#like-text-' + video.videoId);

                if (heart.is('.active')) {
                    heart.removeClass("active");
                    heart.attr('src', basePath + 'images/ea/collage/dsp/img_21.png');

                    var liked = Number(likeText.text()) - 1;
                    likeText.text(liked > -1 ? liked : 0);
                } else {
                    heart.addClass("active");
                    heart.attr('src', basePath + 'images/ea/collage/dsp/img_19.png');
                    likeText.text(Number(likeText.text()) + 1);
                }
            }
        },
        error: function (data) {
            console.log("点赞失败");
        }
    });
}

//数字转成w单位
function jsw(num) {
    var result = num;
    if (Number(num) > 10000) {
        return formatDecimal(num / 10000, 1) + "w";
    }
    return result;
}

function share(title,content,currentUrl,imgurl){
    if (typeof Android !=='undefined'){
        Android.showShare(content,title,currentUrl,imgurl,"1")
    }
}