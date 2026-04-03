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
    <meta name="referrer" content="strict-origin-when-cross-origin">

    <link rel="stylesheet" href="<%=basePath%>js/swiper/swiper-bundle.min.css">
    <link rel="stylesheet" href="https://unpkg.byted-static.com/xgplayer/3.0.10/dist/index.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css"/>

    <script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://unpkg.byted-static.com/xgplayer/3.0.10/dist/index.min.js" charset="utf-8"></script>
    <script src="//unpkg.byted-static.com/xgplayer-hls.js/3.0.0-alpha.1/dist/index.min.js" charset="utf-8"></script>
    <script src="<%=basePath%>js/swiper/swiper-bundle.min.js"></script>
    <script src="<%=basePath%>js/ea/collage/dsp/dspfullscreen.js" type="text/javascript" charset="utf-8"></script>

    <title>小视频</title>

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

<span class="absolute m-2 h-10 text-center flex flex-row" onclick="jumpToHome()" style="z-index: 99999999">
    <img style="height: 24px; margin: 8px" src="<%=basePath%>images/ea/office/contract/selectp/return.png">
    <p class="font-bold" style="margin: auto">首页</p>
</span>

<div class="absolute flex flex-row p-4 m-auto w-full" style="z-index: 9999">
    <div></div>
    <div class="grow text-center space-x-8">
        <span id="recommend" class="text-center active">推荐</span>
        <span id="follow" class="text-center">关注</span>

    </div>
    <div></div>
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

<iframe style="background: white; height: 80vh; z-index: 99999; position: absolute; width: 100vw; bottom: 0; display: none" id="iframe-video-comments"></iframe>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var videoStaffId = "${video.authorId}";
    var videoId = "${video.videoId}";
    var pricetype = "${video.priceType}";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
    var type = '00'

    let currentTab = "RECOMMEND"

    var pageNumber = 1
    var pageSize = 20
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

                console.log("player === null && firstUpdate === true: ", player === null && firstUpdate === true)
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
        console.log("Init video player")
        console.log(playVideo)
        if (playVideo === null || playVideo.videoId === undefined) {
            return
        }
        var videoId = playVideo.videoId
        var coverUrl = playVideo.coverUrl
        var playUrl = playVideo.playUrl
        var vodProvider = playVideo.vodProvider
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

    function jumpToHome() {
        window.location = '<%=basePath%>ea/earth/ea_earthIndex.jspa'
    }

    function jumpToLogin() {
        window.location = '<%=basePath%>/page/WFJClient/pc/pc_login.jsp'
    }

    window.addEventListener("message", function (event) {
        $("#iframe-video-comments").hide();
        $("#iframe-video-comments").attr("src", ``);
    })
</script>
</body>
</html>
