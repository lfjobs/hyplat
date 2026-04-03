<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page import="net.sf.json.JSONObject" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta name="referrer" content="strict-origin-when-cross-origin">

    <link rel="stylesheet" href="<%=basePath%>js/swiper/swiper-bundle.min.css">
    <link rel="stylesheet" href="https://unpkg.byted-static.com/xgplayer/3.0.10/dist/index.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css"/>

    <script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://unpkg.byted-static.com/xgplayer/3.0.10/dist/index.min.js" charset="utf-8"></script>
    <script src="//unpkg.byted-static.com/xgplayer-hls.js/3.0.0-alpha.1/dist/index.min.js" charset="utf-8"></script>
    <script src="<%=basePath%>js/swiper/swiper-bundle.min.js"></script>

    <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>

    <script src="<%=basePath%>js/ea/collage/dsp/dsp-view-list.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/collage/dsp/dsp-share.js" type="text/javascript" charset="utf-8"></script>

    <title>${profile}</title>

    <style>
        .swiper {
            width: 100%;
            height: 100vh;
        }

        .active {
            text-decoration: underline;
            text-decoration-color: white;
            text-decoration-style: solid;
            text-underline-offset: 8px;
            text-decoration-thickness: 2px;
            font-weight: bold;
        }
    </style>
</head>
<body class="bg-black text-white">

<div class="absolute m-2 h-10 text-center flex flex-row w-full space-x-2" onclick="window.history.back()" style="z-index: 99999999">
    <img style="height: 24px; margin: 8px" src="<%=basePath%>images/ea/office/contract/selectp/return.png">
    <div class="font-bold" style="margin: auto; text-wrap: nowrap; overflow: hidden" id="title"></div>
    <div style="height: 24px; margin: 8px"></div>
</div>

<style>
    .side-menu {
        position: absolute;
        display: none;
        width: 100%;
        height: 100%;
        background: rgba(10, 10, 10, 0.50);
        z-index: 80;
    }
</style>

<div id="side-menu" class="absolute hidden w-full h-full"
     style="z-index: 80; background: rgba(10,10,10,0.50)">

    <div class="absolute right-4 bottom-32 space-y-4 text-small rounded p-2 text-center">
        <div>
            <img id="cart" class="w-12 h-12" src="<%=basePath%>images/ea/collage/dsp/pic_07.png"/>
            <p>购物车</p>
        </div>
        <div>
            <img id="order" class="w-12 h-12" src="<%=basePath%>images/ea/collage/dsp/pic_10.png"/>
            <p>订单</p>
        </div>
        <div>
            <img id="bag" class="w-12 h-12" src="<%=basePath%>images/ea/collage/dsp/pic_20.png"/>
            <p>钱包</p>
        </div>
        <div onclick="$('#side-menu').toggleClass('hidden')">
            <img class="w-12 h-12" src="<%=basePath%>images/ea/collage/dsp/pic_12.png"/>
            <p>关闭</p>
        </div>
    </div>

</div>

<div class="swiper mySwiper">
    <div class="swiper-wrapper">
    </div>
</div>

</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";

    initShareSDK();

    var videoStaffId = "${param.videoStaffId}";
    var videoStaffName = "${param.videoStaffName}";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
    var viewType = "${param.viewType}";
    var videoIndex = Number("${param.videoIndex}");

    var pageNumber = 1
    var pageSize = "${param.pageSize}";
    var totalPages = 1
    var videoList = []

    let player = null

    var firstUpdate = true
    var mySwiper = new Swiper('.mySwiper', {
        init: true,
        observer: true, //开启动态检查器
        on: {
            slideChangeTransitionStart: function () {
                console.log("slideChangeTransitionStart:" + this.previousIndex)
                removePlayer()
            },
            slideChangeTransitionEnd: function () {
                console.log("slideChangeTransitionEnd:" + this.activeIndex)
                initPlayer(videoList[this.activeIndex])

                // 为视频添加播放次数
                var preVideoData = videoList[this.previousIndex]
                console.log(preVideoData)
                addReadNum(preVideoData)

                // 是否允许上滑
                mySwiper.allowSlidePrev = this.activeIndex > 0;//设置
                // 条数小于5时拉取更多视频
                if (videoList.length - this.activeIndex < 5) {
                    loadData()
                }
            },
            observerUpdate: function () {
                console.log("observerUpdate: ", this.activeIndex)
                console.log("firstUpdate: ", firstUpdate)
                if (this.activeIndex > videoList.length - 1) {
                    return;
                }

                if (player === null && firstUpdate === true) {
                    firstUpdate = false
                    initPlayer(videoList[this.activeIndex])
                    // 是否允许上滑
                    mySwiper.allowSlidePrev = false; // 设置
                }
            },
        },

        direction: 'vertical', // 垂直切换选项
        loop: false, // 循环模式选项

        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',
        },

        // 如果需要前进后退按钮
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

        // 如果需要滚动条
        scrollbar: {
            el: '.swiper-scrollbar',
        },
    })

    mySwiper.allowSlidePrev = false;//设置

    function removePlayer() {
        if (player !== null) {
            player.destroy()
            player = null
        }
    }

    function initPlayer(playVideo) {

        if (playVideo === null || playVideo.videoId === undefined) {
            return
        }
        var videoId = playVideo.videoId
        var title = playVideo.title
        var coverUrl = playVideo.coverUrl
        var playUrl = playVideo.playUrl
        var vodProvider = playVideo.vodProvider

        document.title = title

        if (vodProvider === "aliyun" || playUrl.endsWith(".m3u8")) {
            player = new window.Player({
                id: "video-" + videoId,
                url: playUrl,
                poster: coverUrl,
                autoplay: true,
                loop: true,
                volume: 1.0,
                playsinline: true,
                "x5-video-player-type": "",
                "x5-video-player-fullscreen": "false",
                "x5-video-orientation": "portraint",
                height: window.innerHeight,
                width: window.innerWidth,
                plugins: [window.HlsJsPlugin],
                controls: false
            });
        }

        if (playUrl.endsWith(".mp4")) {
            player = new Player({
                id: "video-" + videoId,
                url: playUrl,
                poster: coverUrl,
                autoplay: true,
                loop: true,
                volume: 1.0,
                playsinline: true,
                "x5-video-player-type": "",
                "x5-video-player-fullscreen": "false",
                "x5-video-orientation": "portraint",
                height: window.innerHeight,
                width: window.innerWidth,
                controls: false
            });
        }

        updateShareData(title, "", playUrl, coverUrl)
    }

    function addReadNum(videoData) {
        var videoId = videoData[0]
        $.ajax({
            url: basePath + "ea/dsp/sajax_ea_addReadNum.jspa",
            type: "post",
            dataType: "json",
            async: true,
            data: {
                videoId: videoId,
                staffId: staffId
            },
            success: function (data) {
                console.log("增加阅读量完成");
            },
            error: function (data) {
                console.log("增加阅读量失败");
            }
        });
    }
</script>
</html>
